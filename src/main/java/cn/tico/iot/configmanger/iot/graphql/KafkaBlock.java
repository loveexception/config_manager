package cn.tico.iot.configmanger.iot.graphql;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

@IocBean(create = "init", depose = "depose")

public  class KafkaBlock  {

    @Inject
    protected PropertiesProxy conf;

    public  Producer<String, String> producer ;
    public  KafkaConsumer<String, String> consumer;




    public void init() {
        if(consumer!=null){
            return;
        }

        String serializer = StringSerializer.class.getName();
        String deserializer = StringDeserializer.class.getName();

        Properties props = new Properties();
        props.put("bootstrap.servers", conf.get("kafka.brokers"));
        props.put("group.id", conf.get("kafka.group"));
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "earliest");
        props.put("session.timeout.ms", "30001");

        props.put("key.deserializer", deserializer);
        props.put("value.deserializer", deserializer);
        props.put("key.serializer", serializer);
        props.put("value.serializer", serializer);

        producer = new KafkaProducer<>(props);
        consumer = new KafkaConsumer<>(props);

    }

    public void consume(String topic ,Block block ) {
        consumer.subscribe(Arrays.asList(topic));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            for (ConsumerRecord<String, String> record : records) {
//                System.out.printf("%s [%d] offset=%d, key=%s, value=\"%s\"\n",
//								  record.topic(), record.partition(),
//								  record.offset(), record.key(), record.value());
                block.exec(topic,record.key(),record.value(),record.offset());


			}
        }
    }

    public void produce(String topic ,String key , String value ) {
            producer.send(new ProducerRecord<>(topic, key,value));

    }

    public void depose(){

    }




}