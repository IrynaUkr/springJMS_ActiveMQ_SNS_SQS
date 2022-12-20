package com.activemq;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;

import java.io.IOException;

@SpringBootApplication
@EnableJms
public class OrderHandlerApplication {

	public static void main(String[] args) throws IOException {
		ConfigurableApplicationContext appContext = SpringApplication.run(OrderHandlerApplication.class, args);
	}

}
