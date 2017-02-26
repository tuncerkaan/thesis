package com.thesis.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;

@Service
@PropertySource("classpath:/service.properties")
public class KafkaProducerService {

	@Value("${kafka.producer.topic}")
	String kafkaTopic;


	@Autowired
	Producer<String, String> producer;

	/**
	 * Kafka writes value
	 *
	 * @param data
	 *            - The type parameter String
	 * @return the method return type is void
	 * @author mkilic
	 */
	public void setMessage(String data) {
		KeyedMessage<String, String> message = new KeyedMessage<String, String>(kafkaTopic, data);

		producer.send(message);
	}

}
