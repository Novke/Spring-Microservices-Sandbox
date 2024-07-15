package com.sandbox.ApiGateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	private void adressLogger() {
		String ipAdress = "";
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			ipAdress = inetAddress.getHostAddress();
		} catch (UnknownHostException ignored){
			ipAdress = "UNKNOWN";
		}

		System.out.println("====================");
		System.out.println("Gateway IP: " + ipAdress);
		System.out.println("====================");
	}

}
