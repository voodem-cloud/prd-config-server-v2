package com.global.company.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@EnableConfigServer
public class PrdConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrdConfigServerApplication.class, args);
	}

}
