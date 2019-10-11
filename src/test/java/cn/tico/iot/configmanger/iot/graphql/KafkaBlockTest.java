package cn.tico.iot.configmanger.iot.graphql;

import cn.tico.iot.configmanger.module.iot.graphql.Block;
import cn.tico.iot.configmanger.module.iot.graphql.GatewayBlock;
import cn.tico.iot.configmanger.module.iot.graphql.KafkaBlock;
import cn.tico.iot.configmanger.module.iot.models.device.Gateway;
import cn.tico.iot.configmanger.module.iot.models.device.SubGateway;
import org.junit.Assert;
import org.junit.Test;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.mapl.Mapl;

import java.util.HashMap;

public class KafkaBlockTest {
    //@Test
    public void tests(){

        String brokers = "172.16.16.5:9092";//System.getenv("CLOUDKARAFKA_BROKERS");
        String username = "test";//System.getenv("CLOUDKARAFKA_USERNAME");
        String password = "";//System.getenv("CLOUDKARAFKA_PASSWORD");
        KafkaBlock c = new KafkaBlock();
        c.conf = new PropertiesProxy();
        c.conf.set("kafka.topic","test");
        c.conf.set("kafka.group","maodajun");
        c.conf.set("kafka.brokers","172.16.16.5:9092");
        c.init();

        c.produce("test","maodajun","keys");
        c.consume("test", new Block() {
            @Override
            public Object  exec(String topic, String key, String value, long offset) {
                System.out.println(topic+key+value+offset);
                return null;
            }
        });
    }


    @Test
    public void testMapl(){
        Object obj = new HashMap();
        Object o = Mapl.cell(obj,"a");
        System.out.println(o);
    }
    @Test
    public void testBussiness(){

        GatewayBlock block = new GatewayBlock();
        SubGateway sub =block.buileSubGateway("{'sno':'45434'}");

        Assert.assertEquals("45434",sub.getSno());
    }

}