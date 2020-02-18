package cn.tico.iot.configmanger.module.iot.graphql;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@IocBean(create = "init", depose = "depose")

public  class KafkaBlock  {

    @Inject
    public PropertiesProxy conf;

    public  Producer<String, String> producer ;
    public  KafkaConsumer<String, String> consumer;

    public static final String TOPIC ="config";
    public static final String TOPIC_DEPT="dept_config";
    public static final String KEY_SNO="sno";
    public static final String KEY_EXT_SNO ="extsno";






    public void init() {
        if(consumer!=null){
            return;
        }
        String serializer = StringSerializer.class.getName();
        String deserializer = StringDeserializer.class.getName();

        Properties props = new Properties();
        props.put("bootstrap.servers", conf.get("kafka.brokers"));
        props.put("group.id", conf.get("kafka.group"));
        props.put("enable.auto.end", "true");
        props.put("auto.end.interval.ms", "1000");
        props.put("auto.offset.reset", "earliest");
        props.put("session.timeout.ms", "30001");

        props.put("key.deserializer", deserializer);
        props.put("value.deserializer", deserializer);
        props.put("key.serializer", serializer);
        props.put("value.serializer", serializer);

        producer = new KafkaProducer<>(props);
        consumer = new KafkaConsumer<>(props);

    }
    public void consume(Map<String,Block> map) {
        consumer.subscribe(map.keySet());
        while ( true ) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1l));
            try {
                for (ConsumerRecord<String, String> record : records) {
                    Block block= map.get(record.topic());
                    if(Lang.isNotEmpty(block)){
                        block.exec(record.topic(),record.key(),record.value(),record.offset());
                    }

                }

                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Logs.get().error(e);
            }
        }
    }
//    public void  consume(List topic , List<Block> blocks ) {
//        consumer.subscribe(topic);
//        while (true) {
//            ConsumerRecords<String, String> records = consumer.poll(1000);
//            for (ConsumerRecord<String, String> record : records) {
//                if(Strings.equals("config",record.topic())){
//                    //block.exec(record.topic(), record.key(), record.value(), record.offset());
//                    Logs.get().infof("config record : %s",record);
//                    continue;
//                }
//                if(Strings.equals("register",record.topic())){
//                    Logs.get().infof("register , record: %s",record);
//                    //block.exec(record.topic(), record.key(), record.value(), record.offset());
//                    continue;
//                }
//
//			}
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                Logs.get().error(e);
//            }
//        }
//    }

    public void produce(String topic ,String key , String value ) {
            producer.send(new ProducerRecord<>(topic, key,value));

    }

    public void depose(){

    }



}