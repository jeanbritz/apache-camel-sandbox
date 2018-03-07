package com.britz.apachecamelsandbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CamelGateway {

	public static void main(String[] args) {
		SpringApplication.run(CamelGateway.class, args);
	}

	// @Bean
	// public ServletRegistrationBean camelServletRegistrationBean() {
	// ServletRegistrationBean registration = new ServletRegistrationBean();
	// HttpServlet servlet = new CamelHttpTransportServlet();
	// registration.setServlet(servlet);
	// registration.addUrlMappings("/camel/*");
	// registration.setName("CamelServlet");
	// return registration;
	// }
}
