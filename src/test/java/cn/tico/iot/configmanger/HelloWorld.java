package cn.tico.iot.configmanger;

import org.nutz.integration.jedis.JedisAgent;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.log.Logs;
import redis.clients.jedis.Jedis;

import java.util.Set;

public class HelloWorld {


    public static void main(String[] arg){

        System.out.println("ewq");

        test3();


    }
    public static  void test3(){
        Jedis jedis = new Jedis("172.16.16.9", 6379);

        Set set = jedis.keys("*");

        Logs.get().debug(set);
    }

}
