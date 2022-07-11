package com.ratz.springboot.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class WikimediaConsumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaConsumer.class.getSimpleName());


  @KafkaListener(topics = "${spring.kafka.topic.name}",groupId = "${spring.kafka.consumer.group-id}")
  public void consume(String eventMessage){

    LOGGER.info("Event message received -> " + eventMessage);
  }

}
