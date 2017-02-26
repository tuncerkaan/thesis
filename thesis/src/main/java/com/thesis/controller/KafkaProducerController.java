package com.thesis.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thesis.kafka.KafkaProducerService;

@RestController
@RequestMapping("/kafka")
@PropertySource("classpath:/service.properties")
public class KafkaProducerController {
	//
	@Autowired
	KafkaProducerService kafkaProducerService;

	@RequestMapping(value = "/producer/{message}", method = RequestMethod.GET)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML })
	public String producer(@PathVariable("message") String message) {

		kafkaProducerService.setMessage(message);

		return "true";
	}

}
