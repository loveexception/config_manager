package cn.tico.iot.configmanger;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.nutz.boot.NbApp;
import org.nutz.boot.test.junit4.NbJUnit4Runner;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import redis.clients.jedis.Jedis;

@IocBean
@RunWith(NbJUnit4Runner.class)
public class TestBase extends Assert {

    @Inject
    public Jedis jedis;


    public static NbApp createNbApp() {

        return new NbApp().setMainClass(MainLauncher.class).setPrintProcDoc(false);
    }

    public void init(){
        System.out.println("init");
    }
}
