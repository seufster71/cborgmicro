package com.cborgtech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.cborgtech","org.toasthub"})
public class CborgmicroApplication {

	public static void main(String[] args) {
		SpringApplication.run(CborgmicroApplication.class, args);
	}
}
