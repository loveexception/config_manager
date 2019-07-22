package cn.tico.iot.configmanger.iot.graphql;

import org.junit.Test;
import org.nutz.ioc.impl.PropertiesProxy;

import static org.junit.Assert.*;

public class KafkaBlockTest {
    @Test
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
            public void exec(String topic, String key, String value, long offset) {
                System.out.println(topic+key+value+offset);
            }
        });
    }

}