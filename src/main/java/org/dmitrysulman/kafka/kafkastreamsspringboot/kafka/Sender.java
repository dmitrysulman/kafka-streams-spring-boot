package org.dmitrysulman.kafka.kafkastreamsspringboot.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Sender {
    private final KafkaTemplate<Object, Object> kafkaTemplate;

    @Autowired
    public Sender(KafkaTemplate<Object, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String text, String topic) {
        kafkaTemplate.send(topic, text);
    }
}
