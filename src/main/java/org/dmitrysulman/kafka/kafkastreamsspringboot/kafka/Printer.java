package org.dmitrysulman.kafka.kafkastreamsspringboot.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Printer {
    @KafkaListener(topics = "stream-topic3",
            properties = "value.deserializer:org.apache.kafka.common.serialization.LongDeserializer")
    void print(ConsumerRecord<String, Long> record) {
        System.out.println(record.key() + " " + record.value());
    }
}
