package org.dmitrysulman.kafka.kafkastreamsspringboot;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableKafkaStreams
public class KafkaStreamsSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaStreamsSpringBootApplication.class, args);
    }

    @Bean
    public NewTopic topic1() {
        return new NewTopic("stream-topic1", 1, (short) 1);
    }

    @Bean
    public NewTopic topic2() {
        return new NewTopic("stream-topic2", 1, (short) 1);
    }

    @Bean
    public NewTopic topic3() {
        return new NewTopic("stream-topic3", 1, (short) 1);
    }

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "testStreams");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
        return new KafkaStreamsConfiguration(props);
    }

    @Bean
    public KStream<Integer, String> kStream(StreamsBuilder streamsBuilder) {
        KStream<Integer, String> stream = streamsBuilder.stream("stream-topic2");
        stream
                .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
                .groupBy((key, value) -> value)
                .count()
                .toStream()
                .to("stream-topic3", Produced.with(Serdes.String(), Serdes.Long()));
        return stream;
    }

//	spring:
//	kafka:
//	producer:
//	value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
//	consumer:
//	value-deserializer: org.apache.kafka.common.serialization.ByteArrayDeserializer
}
