package cn.tico.iot.configmanger.iot.graphql;

import cn.tico.iot.configmanger.module.iot.graphql.Block;
import cn.tico.iot.configmanger.module.iot.graphql.GatewayBlock;
import cn.tico.iot.configmanger.module.iot.graphql.KafkaBlock;
import cn.tico.iot.configmanger.module.iot.models.device.Gateway;
import cn.tico.iot.configmanger.module.iot.models.device.SubGateway;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.mapl.Mapl;

import java.util.HashMap;
import java.util.Map;

public class KafkaBlockTest {
    //@Test
        public void tests(){

        String brokers = "localhost:9092";//System.getenv("CLOUDKARAFKA_BROKERS");
        String username = "test";//System.getenv("CLOUDKARAFKA_USERNAME");
        String password = "";//System.getenv("CLOUDKARAFKA_PASSWORD");
        KafkaBlock c = new KafkaBlock();
        c.conf = new PropertiesProxy();
        c.conf.set("kafka.topic","test");
        c.conf.set("kafka.group","maodajun");
        c.conf.set("kafka.brokers","localhost:9092");
        c.init();

        c.produce("config","sno","keys");
        c.produce("register",null,"{'sno':'sno_1001','outerip':'4.4.4.4'}");
        Block blokc = new Block() {
            @Override
            public Object  exec(String topic, String key, String value, long offset) {
                System.out.println("---------\r\n"
                        +topic+"\r\n"
                        +key+"\r\n"
                        +value+"\r\n"
                        +offset+
                        "\r\n--------------");
                return null;
            }
        };
        Map<String,Block> map = new HashMap<String,Block>();
        map.put("config",blokc);
        map.put("register",blokc);

        c.consume(map);
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