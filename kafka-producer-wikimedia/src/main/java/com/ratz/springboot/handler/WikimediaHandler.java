package com.ratz.springboot.handler;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

public class WikimediaHandler implements EventHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaHandler.class.getSimpleName());


  private KafkaTemplate<String, String> kafkaTemplate;

  @Value("${spring.kafka.topic.name}")
  private String topicName;

  public WikimediaHandler(KafkaTemplate<String, String> kafkaTemplate, String topicName) {
    this.kafkaTemplate = kafkaTemplate;
    this.topicName = topicName;
  }

  @Override
  public void onOpen() throws Exception {

  }

  @Override
  public void onClosed() throws Exception {

  }

  @Override
  public void onMessage(String s, MessageEvent messageEvent) throws Exception {

    LOGGER.info("event data -> " + messageEvent.getData());
    kafkaTemplate.send(topicName, messageEvent.getData());
  }

  @Override
  public void onComment(String s) throws Exception {

  }

  @Override
  public void onError(Throwable throwable) {

  }
}
