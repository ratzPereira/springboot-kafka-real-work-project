package com.ratz.springboot.producer;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import com.ratz.springboot.handler.WikimediaHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
public class WikimediaProducer {

  private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaProducer.class.getSimpleName());

  @Value("${spring.kafka.topic.name}")
  private String topicName;

  private KafkaTemplate<String, String> kafkaTemplate;


  public WikimediaProducer(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendMessage() throws InterruptedException {

    //read in real time stream data from wikimedia, we use event source
    EventHandler eventHandler = new WikimediaHandler(kafkaTemplate,topicName);
    String url = "https://stream.wikimedia.org/v2/stream/recentchange";

    EventSource.Builder builder = new EventSource.Builder(eventHandler, URI.create(url));
    EventSource eventSource = builder.build();

    eventSource.start();

    TimeUnit.MINUTES.sleep(10);

  }

}
