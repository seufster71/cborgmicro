package com.cborgtech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.cborgtech","org.toasthub"})
public class CborgmicroApplication {

	public static void main(String[] args) {
		SpringApplication.run(CborgmicroApplication.class, args);
	}
}
