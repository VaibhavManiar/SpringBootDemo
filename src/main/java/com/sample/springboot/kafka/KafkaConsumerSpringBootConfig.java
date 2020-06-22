package com.sample.springboot.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@EnableKafka
@Configuration
public class KafkaConsumerSpringBootConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${kafka.consumer.groupid}")
    private String groupId;

    @Bean("kafkaConsumerFactory")
    public ConsumerFactory<String, String> kafkaConsumerFactory() {
        KafkaConsumerConfig props = new KafkaConsumerConfig();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress)
                .put(ConsumerConfig.GROUP_ID_CONFIG, groupId)
                .put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class)
                .put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class)
                .put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, Boolean.TRUE)
                .put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100")
                .put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000")
                .put(ConsumerConfig.CLIENT_ID_CONFIG, "test-client");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean("kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(ConsumerFactory<String, String> kafkaConsumerFactory,
                                                                                                 Function<ConsumerRecord<String, String>, Boolean> kafkaRecordFilter) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(kafkaConsumerFactory);
        factory.setRecordFilterStrategy(kafkaRecordFilter::apply);
        return factory;
    }

    @Bean("kafkaRecordFilter")
    public Function<ConsumerRecord<String, String>, Boolean> kafkaRecordFilter() {
        return (record) -> Boolean.FALSE;
    }

    public static class KafkaConsumerConfig extends HashMap<String, Object> {
        @NonNull
        public KafkaConsumerConfig put(@NonNull String configName, @NonNull Object configValue) {
            super.put(configName, configValue);
            return this;
        }
    }

    //zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties & kafka-server-start /usr/local/etc/kafka/server.properties
    //kafka-console-producer --broker-list localhost:9092 --topic test
    //kafka-console-consumer --bootstrap-server localhost:9092 --topic test --from-beginning
}