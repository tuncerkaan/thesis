package com.thesis.kafka;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;

@Configuration
@PropertySource("classpath:/service.properties")
public class KafkaProducerConfig {

	@Value("${kafka.producer.host}")
	private String kafkaHost;
	
	@Value("${kafka.producer.port}")
	private int kafkaPort;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public Producer<String, String> kafkaProducer() {

		Properties properties = new Properties();
		properties.put("metadata.broker.list", kafkaHost + ":" + kafkaPort);
		properties.put("serializer.class", "kafka.serializer.StringEncoder");
		;
		ProducerConfig producerConfig = new ProducerConfig(properties);
		Producer<String, String> producer = new Producer<String, String>(producerConfig);

		System.out.println("kafka is ready on " + kafkaHost + ":" + kafkaPort);

		return producer;
	}

}
