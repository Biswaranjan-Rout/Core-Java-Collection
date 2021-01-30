package com.amai.amaiAgentDashBoardUI;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@ComponentScan
public class AgentDashboardWebConfig implements WebMvcConfigurer {

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		InternalResourceViewResolver vResolver = new InternalResourceViewResolver();
		vResolver.setPrefix("/WEB-INF/view/");
		vResolver.setSuffix(".jsp");
		vResolver.setViewClass(JstlView.class);
		registry.viewResolver(vResolver);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		registry.addViewController("pendingAppointment").setViewName("pendingAppointment");
		registry.addViewController("menu").setViewName("menu");
		registry.addViewController("home").setViewName("home");
		registry.addViewController("appointmentCompleted").setViewName("appointmentCompleted");
		registry.addViewController("newAppointment").setViewName("newAppointment");
		registry.addViewController("cancelledAppointment").setViewName("cancelledAppointment");
	}
	
	

}
