package com.amai.agentDashboardUI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages="com.amai.*")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class AgentDashboardApplication extends SpringBootServletInitializer{
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AgentDashboardApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(AgentDashboardApplication.class, args);
	}

}
