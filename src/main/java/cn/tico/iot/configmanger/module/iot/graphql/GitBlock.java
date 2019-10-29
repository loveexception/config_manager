package cn.tico.iot.configmanger.module.iot.graphql;


import cn.tico.iot.configmanger.module.iot.bean.GitBean;
import cn.tico.iot.configmanger.module.iot.models.device.Device;
import cn.tico.iot.configmanger.module.iot.models.device.Gateway;
import cn.tico.iot.configmanger.module.iot.models.device.SubGateway;
import cn.tico.iot.configmanger.module.iot.models.driver.Driver;
import com.google.common.collect.Lists;
import lombok.Data;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.UnsupportedCredentialItem;
import org.eclipse.jgit.transport.CredentialItem;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.URIish;
import org.nutz.castor.castor.String2File;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Files;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Logs;
import org.nutz.mapl.Mapl;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static jdk.nashorn.internal.objects.NativeError.getFileName;

@Data
@IocBean
public class GitBlock {



    @Inject
    protected PropertiesProxy conf;




    /**
     * 新建Git项目
     * @param gateway
     * @return
     * @throws GitAPIException
     * @throws IOException
     */
    public Gateway createGit(Gateway gateway,GitBean git) {


        try {
            git = createProject(git);
            git = createLocal(git);

        } catch (Exception e) {
            e.printStackTrace();
            Logs.get().errorf("create git error %s ;git_bean:%s",e,git);

        }
        Logs.get().infof("git create over "+ git.getSshPath());
        gateway.setGitPath(git.getSshPath());


        return gateway;
    }

    /**
     * 变更提交项目
     * @param gateway
     * @return
     * @throws GitAPIException
     * @throws IOException
     */
    public Gateway changGit(GitBean git ,Gateway gateway ,List<Device> devices) throws GitAPIException, IOException {


        Set<Driver> drivers = new HashSet<Driver>();
        for (int i = 0; i < devices.size(); i++) {
            Driver temp = devices.get(i).getDriver();
            drivers.add(temp);
        }


        git = createLocal(git);



        List<String> jsons = buildJson(devices);


        git = copyDrivers(git,drivers);

        git = copyJson(git,jsons);

        git = commit_push(git);


        //gateway.setGitPath(git.getSshPath());
        return gateway;
    }

    /**
     * 新建GitBean
     * @param subGateway
     * @return
     */
    public GitBean gitBeanBuilder(SubGateway subGateway) {

        String githome = conf.get("git.githome");
        String localhome = conf.get("git.localhome");
        String sshhome = conf.get("git.sshhome");
        String username = conf.get("git.username");
        String password = conf.get("git.password");

        GitBean git = new GitBean(subGateway);


        git.setGithome(githome);
        git.setLocalhome(localhome);
        git.setSshhome(sshhome);
        git.setUsername(username);
        git.setPassword(password);

        return git;
    }


    /**
     * 新建项目
     * @param git
     * @return
     */
    public GitBean createProject(GitBean git) throws GitAPIException, IOException {
        String githome = git.getGithome();
        String sno = git.getSno();
        Logs.getLog(this.getClass()).debugf("sno:%s;git_home:%s;",sno,githome);
        if(Strings.isBlank(githome)){
            return git;
        }
        if(Strings.isBlank(sno)){
            return git;
        }

        File gitfile = Files.createDirIfNoExists(git.getGitPath());
        Logs.getLog(this.getClass()).debugf("gi_tpath:%s;git_file:%s",git.getGitPath(),gitfile);

        if(!Files.deleteDir(gitfile)){
            throw new IOException("Could not delete temporary file " + git.getGitPath());
        }

        Logs.getLog(this.getClass()).infof("start:%s","git build");

        Git.init()
                .setGitDir(gitfile)
                .setDirectory(gitfile.getParentFile())
                .call();
        Logs.getLog(this.getClass()).infof("end:%s","git build");

        return git;
    }

    /**
     * 多个Json文件
     * @param devices
     * @return
     */
    public List<String> buildJson(Collection<Device> devices){
        List<String> result = Lists.newArrayList();
        for(Device device :devices){
            //String json = Json.toJson(device);
            Map dev = new NutMap();
            Map env = new NutMap();
            NutMap dri = new NutMap();



            String devjson = Json.toJson(device);
            dev.putAll(Json.fromJsonAsMap(String.class,devjson));
            env.putAll(device.getEnv());
            String drijson = Json.toJson(device.getDriver());
            dri.putAll(Json.fromJsonAsMap(String.class,drijson));
            File file = Files.createFileIfNoExists2(dri.getString("path"));
            dri.put("filename",file.getName());


            dev.put("env",env);

            dev.put("drive",dri);



            result.add(Json.toJson(dev));

        }

        return result;
    }


    /**
     * 建本地副本
     * @param git
     * @return
     */
    public GitBean createLocal(GitBean git) throws GitAPIException, IOException {

        File localfile = Files.createDirIfNoExists(git.getLocalPath());
        File gitfile = Files.createDirIfNoExists(git.getGitPath());

        Logs.getLog(this.getClass()).debugf("local:%s;git:%s;",localfile,gitfile);

        if(!Files.deleteDir(localfile)) {
            throw new IOException("Could not delete temporary file " + localfile.getPath());
        }

        // then clone
        Logs.getLog(this.getClass()).infof("start:Cloning from %s to %s",gitfile, localfile);
        Git result = Git.cloneRepository()
                .setURI(gitfile.getPath())
                .setDirectory(localfile)
                .setCredentialsProvider(allowHosts)
                .call();
        Logs.getLog(this.getClass()).infof("end:Cloning ");

        return git;
    }

    /**
     * 拷贝 py 文件
     * @param drivers
     * @return
     */
    public GitBean copyDrivers(GitBean git,Collection<Driver> drivers) throws IOException {
        String path = git.getLocalPath();
        String target = path +"/py/";

        boolean ok = true;
        for(Driver src : drivers){
            File target_file = Files.createFileIfNoExists(target+"/"+Files.getName(src.getPath()));

            File src_file = Files.createFileIfNoExists(src.getPath());

            Logs.getLog(this.getClass()).infof("start:copy driver py from %s to %s",src_file ,target_file);

            ok = Files.copyFileWithoutException( src_file, target_file,-1l);
            Logs.getLog(this.getClass()).infof("end:copy driver py  is %s",ok);

        }

        return git;
    }

    /**
     * 拷贝 配置 json
     * @param git
     * @param devices
     * @return
     */
    public GitBean copyJson(GitBean git, List<String> devices) throws IOException {

        String path = git.getLocalPath();
        String target = path +"/config/";
        File config_file =  Files.createDirIfNoExists(target);
        Files.deleteDir(config_file);


        boolean ok = true;
        for(String src : devices){
            Map<String,String> obj = Json.fromJsonAsMap(String.class, src);
            String sno = ""+obj.get("sno");
            String config_name = target+"/"+sno+".json";
            File target_file =Files.createFileIfNoExists2(config_name);
            Logs.getLog(this.getClass()).infof("start:copy config json from %s to %s",src ,target_file);

            Files.deleteFile(config_file);
            target_file = Files.createFileIfNoExists2(config_name);
             Files.appendWrite(target_file,src);

            Logs.getLog(this.getClass()).infof("end:copy driver py  is OK");

        }

        return git;

    }

    /**
     * 提交
     * @param git
     * @return
     */
    public GitBean commit_push(GitBean git) throws IOException, GitAPIException {
        File local = Files.createDirIfNoExists(git.getLocalPath());
        Logs.getLog(this.getClass()).infof("start:commit_push  from %s ",local);

        Git g = Git.open(local);
        g.add().addFilepattern(".").call();
        g.commit().setMessage("message").call();
        g.push().call();
        Logs.getLog(this.getClass()).infof("end:commit_push   no error ");


        return git;
    }


    // this is necessary when the remote host does not have a valid certificate, ideally we would install the certificate in the JVM
    // instead of this unsecure workaround!
    /* 自动点击同意  */

    CredentialsProvider allowHosts = new CredentialsProvider() {

        @Override
        public boolean supports(CredentialItem... items) {
            for(CredentialItem item : items) {
                if((item instanceof CredentialItem.YesNoType)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean get(URIish uri, CredentialItem... items) throws UnsupportedCredentialItem {
            for(CredentialItem item : items) {
                if(item instanceof CredentialItem.YesNoType) {
                    ((CredentialItem.YesNoType)item).setValue(true);
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean isInteractive() {
            return false;
        }
    };
}
