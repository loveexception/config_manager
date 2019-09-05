package cn.tico.iot.configmanger.module.iot.services;

import org.apache.shiro.util.ClassUtils;
import org.junit.Test;

public class Ttest {

    @Test
    public void myUtile(){
//        Object obj =  ClassUtils.newInstance("io.nutz.nutzsite.common.shiro.session.SimpleAuthorizingRealm");
//
        Object obj =  ClassUtils.newInstance("cn.tico.iot.configmanger.realui.bean.MyRealm");
//        Object obj =  ClassUtils.newInstance("cn.tico.iot.configmanger.module.sys.models.Config");
       System.out.println(obj );
    }
}
