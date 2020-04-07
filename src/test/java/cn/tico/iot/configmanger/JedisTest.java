package cn.tico.iot.configmanger;

import cn.tico.iot.configmanger.module.mao.redis.LocationManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nutz.boot.NbApp;
import org.nutz.boot.test.junit4.NbJUnit4Runner;
import org.nutz.integration.jedis.JedisAgent;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Logs;
import redis.clients.jedis.Jedis;

import java.util.Set;

import static org.nutz.integration.jedis.RedisInterceptor.jedis;


@IocBean
@RunWith(NbJUnit4Runner.class)
public class JedisTest extends Assert {
    @Inject
    private JedisAgent jedisAgent;

    @Inject
    private LocationManager locationManager;


    @Inject
    private Jedis jedis;


    public static NbApp createNbApp() {

        return new NbApp().setMainClass(MainLauncher.class).setPrintProcDoc(false);
    }

    public void init(){
        System.out.println("init");
    }


    @Test
    public void test1(){
        String object;
        jedis.del("maodajun");
        object = jedis.get("maodajun");
        System.out.println("null:"+object);
        jedis.set("maodajun","test");
        object = jedis.get("maodajun");
        System.out.println("maodajun:"+object);


    }

   @Test
  //  @Aop("jedis")
    public  void test2(){
       jedis.set("redis:location:1","test1");
       jedis.set("redis:location:2","test2");

       locationManager.init();




    }


}
