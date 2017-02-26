package com.thesis.kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.SourcePollingChannelAdapterSpec;
import org.springframework.integration.dsl.kafka.Kafka;
import org.springframework.integration.dsl.kafka.KafkaHighLevelConsumerMessageSourceSpec;
import org.springframework.integration.dsl.support.Consumer;
import org.springframework.integration.kafka.support.ZookeeperConnect;

@Configuration
public class ConsumerConfiguration {
	//
	@Autowired
	private KafkaConfig kafkaConfig;

	private Log logger = LogFactory.getLog(ConsumerConfiguration.class);

	@Bean
	IntegrationFlow consumer() {

		logger.info("starting consumer..  Access Point -> " + "topic : " + kafkaConfig.getTopic() + "zookpeeperaddress : " + kafkaConfig.getZookeeperAddress());

		KafkaHighLevelConsumerMessageSourceSpec messageSourceSpec = Kafka.inboundChannelAdapter(new ZookeeperConnect(this.kafkaConfig.getZookeeperAddress())).consumerProperties(props -> props.put("auto.offset.reset", "smallest").put("auto.commit.interval.ms", "1")).addConsumer("iot-events-group", metadata -> metadata.consumerTimeout(100).topicStreamMap(m -> m.put(this.kafkaConfig.getTopic(), 1)).maxMessages(10).valueDecoder(String::new));

		Consumer<SourcePollingChannelAdapterSpec> endpointConfigurer = e -> e.poller(p -> p.fixedDelay(100));

		return IntegrationFlows.from(messageSourceSpec, endpointConfigurer).<Map<String, List<String>>>handle((payload, headers) -> {
			payload.entrySet().forEach(e -> logger.info(readProducer(payload)));
			return null;
		}).get();

	}

	public String readProducer(Map<String, List<String>> payload) {
		try {

			List<List<String>> values = new ArrayList<List<String>>(payload.values());
			@SuppressWarnings("unchecked")
			ConcurrentHashMap<String, List<String>> value = (ConcurrentHashMap<String, List<String>>) values.get(0);
			for (int i = 0; i < value.get(0).size(); i++) {

				logger.info(" producer data : " + value.get(0).get(i));

			}
		} catch (Exception e) {

			logger.info("Ended in failure method ConsumerConfiguration@createEvents " + e.getMessage());
		}
		return "success";
	}

}
