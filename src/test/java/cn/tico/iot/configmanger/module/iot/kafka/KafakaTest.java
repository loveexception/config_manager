package cn.tico.iot.configmanger.module.iot.kafka;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.junit.Test;
import org.nutz.ioc.Ioc;


public class KafakaTest {

    //@Test
    public void test1(){

        String topic = "test";//args[0].toString();
        String group = "group";//args[1].toString();
        Properties props = new Properties();
        props.put("bootstrap.servers", "172.16.16.5:9092");
        props.put("group.id", group);
        props.put("enable.auto.commit", "true");

        props.put("auto.commit.interval.ms", "100000");

        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        consumer.subscribe(Arrays.asList(topic));
        int i = 0;

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("offset = %d, key = %s, value = %s\n",
                        record.offset(), record.key(), record.value());
        }
    }
    //@Test
    public void test2(){

        String topic = "config";//args[0].toString();
        String group = "timon-raw78i";//args[1].toString();
        Properties props = new Properties();
        props.put("bootstrap.servers", "172.16.16.5:9092");
        props.put("group.id", group);
        props.put("enable.auto.commit", "true");

        props.put("auto.commit.interval.ms", "100000");

        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        consumer.subscribe(Arrays.asList(topic));
        int i = 0;

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("offset = %d, key = %s, value = %s\n",
                        record.offset(), record.key(), record.value());
        }
    }


    //@Test
    public void test3(){

        String topic = "testrawdata";//args[0].toString();
        String group = "timon-rawc";//args[1].toString();
        Properties props = new Properties();
        props.put("bootstrap.servers", "172.16.16.5:9092");
        props.put("group.id", group);
        props.put("enable.auto.commit", "true");

        props.put("auto.commit.interval.ms", "100000");

        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        consumer.subscribe(Arrays.asList(topic));
        int i = 0;

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("offset = %d, key = %s, value = %s\n",
                        record.offset(), record.key(), record.value());
        }
    }

//    @Test
//    public void testALLSNO(){
//
//        String topic = "config";//args[0].toString();
//        String group = "config_manager";//args[1].toString();
//        Properties props = new Properties();
//        props.put("bootstrap.servers", "172.16.16.9:9092");
//        props.put("group.id", group);
//        props.put("enable.auto.commit", "true");
//
//        props.put("auto.commit.interval.ms", "100000");
//
//        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//
//        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//
//
//       KafkaProducer<String,String>         producer = new KafkaProducer<>(props);
//
//        Dao dao = Ioc.
//
//        producer.send(new ProducerRecord<>(topic, "sno",value));
//
//    }
}
