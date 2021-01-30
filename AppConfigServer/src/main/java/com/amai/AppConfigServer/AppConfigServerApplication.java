package com.amai.AppConfigServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@EnableConfigServer
public class AppConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppConfigServerApplication.class, args);
	}

}
