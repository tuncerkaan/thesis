package com.thesis.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaConfig {
    @Value("${kafka.topic:iot-events}")
    private String topic;

    @Value("${kafka.consumer.zookeeperAddress}")
    private String zookeeperAddress; 
 
    public KafkaConfig() {
    }
 
    public KafkaConfig(String topic, String brokerAddress, String zookeeperAddress) {
        this.topic = topic;
        this.zookeeperAddress = zookeeperAddress;
    }
 
    public String getTopic() {
        return topic;
    }
 
    public String getZookeeperAddress() {
        return zookeeperAddress;
    }
}
