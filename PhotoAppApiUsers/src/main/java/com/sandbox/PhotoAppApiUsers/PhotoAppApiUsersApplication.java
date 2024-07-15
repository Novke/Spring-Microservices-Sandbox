package com.sandbox.PhotoAppApiUsers;

import com.sandbox.PhotoAppApiUsers.users.shared.FeignErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import feign.Logger;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class PhotoAppApiUsersApplication {

	@Value("${token.expiration_time}")
	String expirationTime;
	@Value("${gateway.ip}")
	String gatewayIp;
	@Value("${config.source}")
	String configSource;

	@Value("${app.environment}")
	String profile;

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppApiUsersApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public HttpExchangeRepository httpExchangeRepository(){
		return new InMemoryHttpExchangeRepository();
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onStart(){
		System.out.println("Gateway: " + gatewayIp);
		System.out.println("Configuration source: " + configSource);
		System.out.println("Starting on profile: " + profile);
	}

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate(RestTemplateBuilder builder){
		return builder
				.build();
	}

	@Bean
	@Profile("default")
	Logger.Level devLoggerLevel(){
		return Logger.Level.FULL;
	}


	@Bean
	@Profile("production")
	Logger.Level prodLoggerLevel(){
		return Logger.Level.BASIC;
	}

	@Bean
	public FeignErrorDecoder feignErrorDecoder(){
		return new FeignErrorDecoder();
	}

	@Bean
	@Profile("production")
	public String createProdBean(){
		String text = "=\n=\n=\nPRODUCTION BEAN\n=\n=\n";
		System.out.println(text);
		return text;
	}
	@Bean
	@Profile("!production")
	public String createNotProdBean(){
		String text = "=\n=\n=\nNOT PRODUCTION BEAN\n=\n=\n";
		System.out.println(text);
		return text;
	}

	@Bean
	@Profile("default")
	public String createDefaultBean(){
		String text = "=\n=\n=\nDEFAULT BEAN\n=\n=\n";
		System.out.println(text);
		return text;
	}

}
