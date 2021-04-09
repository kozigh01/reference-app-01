package com.mkozi;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class ProducerDemo {
    public static void main(String[] args) {
        String bootstrapServers = "broker:29092";

        // create producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // create producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        // send data
        ProducerRecord<String, String> record = new ProducerRecord<String,String>(
            "first_topic", 
            "hello from Java producer");
        producer.send(record);
        producer.flush();  // flush data
        producer.close();  // flush data and close
    }
}
