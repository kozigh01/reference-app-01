package com.mkozi;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerDemo {
    public static void main(String[] args) {
        new ConsumerDemo().run();
    }

    public ConsumerDemo() { }

    public void run() {
        Logger logger = LoggerFactory.getLogger(ConsumerRunnable.class.getName());

        String bootstratpServers = "broker:29092";
        String topic = "first_topic";
        String groupId = "my-5th-app";

        // latch for dealing with multiple threads
        CountDownLatch latch = new CountDownLatch(1);

        // create the consumer runnable
        logger.info("Ceating the consumer thread");
        Runnable myConsumerRunnable = new ConsumerRunnable(latch, bootstratpServers, groupId, topic);

        // start the thread
        Thread myThread = new Thread(myConsumerRunnable);
        myThread.start();

        // add a shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread( () -> {
            logger.info("Caught shutdown hook");
            ((ConsumerRunnable) myConsumerRunnable).shutdown();

            try {
                latch.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("Application has exited");
        }));

        try {
            latch.await();
        } catch (Exception e) {
            logger.error("Application got interrupted", e);
        } finally {
            logger.info("Application is closing");
        }
    }

    public class ConsumerRunnable implements Runnable {

        private CountDownLatch latch;
        private KafkaConsumer<String, String> consumer;
        private Logger logger = LoggerFactory.getLogger(ConsumerRunnable.class.getName());

        public ConsumerRunnable(
            CountDownLatch latch, 
            String bootstratpServers,
            String groupId,
            String topic
        ) {
            this.latch = latch;

            Properties properties = new Properties();
            properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstratpServers);
            properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);

            properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            this.consumer = new KafkaConsumer<String, String>(properties);
            consumer.subscribe(Collections.singleton(topic));
        }

        @Override
        public void run() {
            try {
                while(true) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
         
                    for (ConsumerRecord<String, String> record : records) {
                        logger.info("Key: " + record.key() + ", Value: " + record.value());
                        logger.info("     Partiion: " + record.partition() + ", Offset: " + record.offset());
                    }
                 }                
            } catch (WakeupException e) {
                logger.info("Received shutdown signal!");
            } finally {
                consumer.close();

                // tell main code we're done with the consumer
                latch.countDown();
            }           
        }

        public void shutdown() {
            // the wakeup() method is a special method to interrupt consumer.poll()
            // it will throw the exception WakeUpException
            consumer.wakeup();
        }
    }
}
