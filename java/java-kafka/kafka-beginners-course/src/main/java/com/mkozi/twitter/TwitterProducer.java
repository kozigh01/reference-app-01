package com.mkozi.twitter;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitterProducer {

    Logger logger = LoggerFactory.getLogger(TwitterProducer.class.getName());

    private String consumerKey = "";
    private String consumerSecret = "";
    private String token = "";
    private String secret = "";

    public TwitterProducer() {
        super();

        consumerKey = System.getenv("CONSUMER_KEY");
        consumerSecret = System.getenv("CONSUMER_SECRET");
        token = System.getenv("TOKEN");
        secret = System.getenv("SECRET");

        System.out.println(consumerKey);
        System.out.println(consumerSecret);
        System.out.println(token);
        System.out.println(secret);
    }

    public static void main(String[] args) {
        new TwitterProducer().run();
    }

    public void run() {
        // set up blocking queue
        BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(1000);
        var client = createTwitterClient(msgQueue);
        client.connect();

        // create kafka producer
        KafkaProducer<String, String> producer = createKafkaProducer();

        // loop to send tweets to kafka
        while (!client.isDone()) {
            String msg = null;
            try {
                msg = msgQueue.poll(5, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
                client.stop();
            }
            if (msg != null) {
                logger.info(msg);
                producer.send(new ProducerRecord<String,String>("twitter_tweets", null, msg), new Callback(){

                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception e) {
                        if (e == null) {
                            logger.error("Something bad happened", e);
                        } else {
                            // make sure topic exists before sending message
                        }
                        
                    }
                    
                });
            }

        }

        logger.info("End of application");
    }

    public KafkaProducer<String, String> createKafkaProducer() {
        String bootstrapServers = "broker:29092";

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return new KafkaProducer<String, String>(properties);
    }

    public Client createTwitterClient(BlockingQueue<String> msgQueue) {
        // From: https://github.com/twitter/hbc

        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();

        List<String> terms = Lists.newArrayList("bitcoin");
        // hosebirdEndpoint.followings(followings);
        hosebirdEndpoint.trackTerms(terms);

        Authentication hosebirdAuth = new OAuth1(consumerKey, consumerSecret, token, secret);

        ClientBuilder builder = new ClientBuilder().name("Hosebird-Client-01") // optional: mainly for the logs
                .hosts(hosebirdHosts).authentication(hosebirdAuth).endpoint(hosebirdEndpoint)
                .processor(new StringDelimitedProcessor(msgQueue));
        // .eventMessageQueue(eventQueue);

        Client hosebirdClient = builder.build();
        return hosebirdClient;
    }
}
