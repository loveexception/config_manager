package cn.tico.iot.configmanger.module.iot.bean;


import cn.tico.iot.configmanger.module.iot.models.device.SubGateway;
import lombok.Data;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;
import org.nutz.mapl.Mapl;

@Data
public class GitBean {
    private String sno;

    private String localhome;
    private String githome;
    private String sshhome;


    private String username;
    private String password;

    public GitBean(Object  obj){
        //NutMap map = Lang.obj2nutmap(obj);
        //sno = map.getString("sno","");
//        sno = ""+Mapl.cell(obj,"extsno");
//        sshhome = map.getString("sshhome","");
//        localhome = map.getString("localhome","");
//        githome = map.getString("githome","");
//        username = map.getString("username","");
//        password = map.getString("password","");
    }

    public GitBean(SubGateway obj){
        sno = obj.getExtSno();

    }
    public GitBean (){}

    public String getLocalPath(){
        return localhome+"/"+sno;

    }
    public String getGitPath(){
        return githome+"/"+sno+".git";
    }
    public String getSshPath(){
        return username+"@"+sshhome+":"+githome+"/"+sno+".git";
    }


}
