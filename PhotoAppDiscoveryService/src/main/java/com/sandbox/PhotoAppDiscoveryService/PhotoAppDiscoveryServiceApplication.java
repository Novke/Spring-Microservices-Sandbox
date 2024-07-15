package com.sandbox.PhotoAppDiscoveryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableEurekaServer
public class PhotoAppDiscoveryServiceApplication {

	@Value("${config.source}")
	String config;


	public static void main(String[] args) {
		SpringApplication.run(PhotoAppDiscoveryServiceApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	void start(){
		System.out.println("Config source: " + config);
	}

}
