package cn.tico.iot.configmanger.module.iot.graphql;

import cn.tico.iot.configmanger.module.iot.bean.GitBean;
import cn.tico.iot.configmanger.module.iot.models.device.Device;
import cn.tico.iot.configmanger.module.iot.models.device.Gateway;
import cn.tico.iot.configmanger.module.iot.models.device.SubGateway;
import cn.tico.iot.configmanger.module.iot.models.driver.Driver;
import com.google.common.collect.Lists;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.random.R;
import org.nutz.log.Logs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class GitBlockTest {
    GitBlock block ;
    public static  String GIT_HOME="~/IdeaProjects/maodajuntest/git/";
    private static String LOCAL_HOME="~/IdeaProjects/maodajuntest/local/";
    private static String TEST_HOME="~/IdeaProjects/maodajuntest/test/";
    private static String SSH_HOME="localhost";

    @Before
    public void setUp() throws Exception {
        block = new GitBlock();
    }

    @After
    public void tearDown() throws Exception {
        if(true){
            return;
        }
        File[] all= Files.ls(GIT_HOME,"test*",null);
        for (int i = 0; i < all.length; i++) {
            System.out.println("del:"+all[i].getAbsolutePath());
            Files.deleteDir(all[i]);
        }
        all= Files.ls(LOCAL_HOME,"test*",null);
        for (int i = 0; i < all.length; i++) {
            System.out.println("del:"+all[i].getAbsolutePath());
            Files.deleteDir(all[i]);
        }
        all= Files.ls(TEST_HOME,"test*",null);
        for (int i = 0; i < all.length; i++) {
            System.out.println("del:"+all[i].getAbsolutePath());
            Files.deleteDir(all[i]);
        }
    }

    @Test
    public void createGitNullSno() throws GitAPIException, IOException {
        GitBean git = new GitBean(new SubGateway());
        git  = block .createProject(git);
        assertEquals(git.getSno(),"");

    }
    @Test
    public void createGitSNO_test1() throws GitAPIException, IOException {
        GitBean git = getGitBean();

        git = block .createProject(git);
        assertEquals(git.getSno().length(),9);

    }
    @Test
    public void createGitSNO_Dir() throws GitAPIException, IOException {
        GitBean git = getGitBean();

        git = block .createProject(git);
        System.out.println(git.getGitPath());
        assertEquals(git.getGitPath().length(),46);

    }



    @Test
    public void createGitSNO_Dir_Git() throws IOException, GitAPIException {
        GitBean git = getGitBean();

        git = block .createProject(git);




        System.out.println(git.getGitPath());


        assertEquals(git.getGitPath().length(),46);
        File file = Files.findFile(git.getGitPath());

        System.out.println("find:"+file.exists());
        Git root = Git.open(file);
        Logs.get().debug( root.getRepository().getDirectory().getPath());



    }
    @Test
    public void createLocal() throws GitAPIException, IOException {

        GitBean git = getGitBean();
        System.out.println(git.getSshPath());

        git = block.createProject(git);

        git = block .createLocal(git);
        System.out.println(git.getLocalPath());
        assertEquals(git.getLocalPath().length(),44);

        File file = Files.checkFile(git.getLocalPath());
        System.out.println(file.getAbsolutePath());

        assertEquals(file.getAbsolutePath().length(),52);

    }


    @Test
    public void commit_push() throws GitAPIException, IOException {

        GitBean git = getGitBean();
        System.out.println(git.getSshPath());

        git = block.createProject(git);

        git = block .createLocal(git);

        File file = Files.createFileIfNoExists(git.getLocalPath()+"//abc.txt" );
        Files.appendWrite(file,"8765432");

        git = block.commit_push(git);

        git.setLocalhome(TEST_HOME);

        block.createLocal(git);

        File test =Files.createDirIfNoExists(git.getLocalPath());

        String[] list= test.list();

        assertEquals(2,list.length);
        System.out.println( git.getLocalPath()+"end"+ Arrays.toString(list));




    }
    @Test
    public void copyDrivers() throws IOException {


        GitBean git = getGitBean();
        List<Driver> drivers = build_two_Drivers();

        block.copyDrivers(git,drivers);

        File test =Files.createDirIfNoExists(git.getLocalPath()+"/py/");

        String[] list= test.list();

        System.out.println(Arrays.toString(list));

        assertEquals(2 , list.length);

        System.out.println( git.getLocalPath()+":end:"+ Arrays.toString(list));
    }
    private GitBean getGitBean() {
        GitBean git = new GitBean(new SubGateway());
        git.setSno("test1"+ R.random(1000,9999));
        git.setGithome(GIT_HOME);
        git.setLocalhome(LOCAL_HOME);
        git.setSshhome(SSH_HOME);
        git.setUsername("root");
        git.setPassword("123456");
        return git;
    }

    @Test
    public void changGit_drivers() throws GitAPIException, IOException {
        GitBean git = getGitBean();
        //random 驱动
        Driver driver = build_two_Drivers().get(0);
        // 测试 网关
        Gateway gateway = buildGateway();

        //测试设备
        Device device = buildDevice(gateway, driver,"127.0.0.1");

        block.createProject(git);
        block.createLocal(git);

        block.changGit(git , gateway,Arrays.asList(device));

        System.out.println(git);
        List<String> result = Lists.newArrayList();

        File file = Files.createDirIfNoExists(git.getLocalPath());
        String[] sonfile = file.list();
        System.out.println(Arrays.toString(sonfile));
        result.addAll(Arrays.asList(sonfile));
        for(File temp :file.listFiles()){
            String[] tempson = temp.list();
            System.out.println(Arrays.toString(tempson));
            result.addAll(Arrays.asList(tempson));

        }

        assertEquals(true,result.contains("my.html"));

    }
    @Test
    public void changGit_drivers_from_Git() throws GitAPIException, IOException {
        GitBean git = getGitBean();
        //random 驱动
        Driver driver = build_two_Drivers().get(0);
        // 测试 网关
        Gateway gateway = buildGateway();

        //测试设备
        Device device = buildDevice(gateway, driver,"127.0.0.1");

        block.createProject(git);
        block.createLocal(git);

        block.changGit(git , gateway,Arrays.asList(device));

        System.out.println(git);
        List<String> result = Lists.newArrayList();

        git.setLocalhome(TEST_HOME);
        git = block.createLocal(git);



        File file = Files.createDirIfNoExists(git.getLocalPath());
        String[] sonfile = file.list();
        System.out.println(Arrays.toString(sonfile));
        result.addAll(Arrays.asList(sonfile));
        for(File temp :file.listFiles()){
            String[] tempson = temp.list();
            System.out.println(Arrays.toString(tempson));
            result.addAll(Arrays.asList(tempson));

        }

        assertEquals(true,result.contains("my.html"));

    }

    @Test
    public void changGit_drivers_json() throws GitAPIException, IOException {
        GitBean git = getGitBean();
        //random 驱动
        Driver driver = build_two_Drivers().get(0);
        // 测试 网关
        Gateway gateway = buildGateway();

        //测试设备
        Device device = buildDevice(gateway, driver,"127.0.0.1");

        block.createProject(git);
        block.createLocal(git);

        block.changGit(git , gateway,Arrays.asList(device));

        System.out.println(git);
        File file = Files.findFile(git.getLocalPath()+"/config");

        String[] sons = file.list();


        System.out.println(Arrays.toString(sons));
        assertEquals(true, Lang.isNotEmpty(sons));

    }

    public Gateway buildGateway() {
        Gateway gateway = new Gateway();
        SubGateway sub = new SubGateway();
        gateway.setSubGateway(sub);
        gateway.setSno("sno_"+R.random(100,999));

        sub.setExtSno("ext_"+ R.random(100,999));
        sub.setId("id_"+R.random(100,999));
        return gateway;
    }

    public List<Driver> build_two_Drivers() {
        List<Driver> drivers = new ArrayList<Driver>();

        Driver driver ;
        driver = new Driver();
        driver.setPath("/Users/mao/IdeaProjects/testproject/test001/my.html");
        drivers.add(driver);

        driver = new Driver();
        driver.setPath("/Users/mao/IdeaProjects/testproject/yourgit/my.sh");
        drivers.add(driver);
        return drivers;
    }

    public Device buildDevice(Gateway gateway, Driver driver,String ip) {
        Device device = new Device();
        device.setGateway(gateway);
        device.setGatewayExtsno(gateway.getSubGateway().getExtSno());
        device.setGatewayid(gateway.getId());

        device.setDriver(driver);
        device.setDriverid(driver.getId());

        device.setSno("sno_"+R.random(100,999));

        device.setIp(ip);
        device.setDelFlag("false");
        device.setStatus("true");



        return device;
    }


    @Test
    public void buildJson() {
        Device device = new Device();
        device = buildDevice(buildGateway(),build_two_Drivers().get(0),"12.34.56.78");

        List<Device> devices = Lists.newArrayList();
        devices.add(device);
        List<String> jsons  =block.buildJson(devices);

        assertEquals(1,jsons.size());

        System.out.println(jsons);
        assertEquals(true,jsons.get(0).contains("12.34.56.78"));
    }

    @Test
    public void createProject() {
    }




    @Test
    public void changeJson() {
    }

    @Test
    public void push() {
    }

}