package org.dmitrysulman.kafka.kafkastreamsspringboot.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    private final Sender sender;

    @Autowired
    public Receiver(Sender sender) {
        this.sender = sender;
    }

    @KafkaListener(topics = "stream-topic1")
    public void processMessage(String content) {
        sender.send(content, "stream-topic2");
    }
}
