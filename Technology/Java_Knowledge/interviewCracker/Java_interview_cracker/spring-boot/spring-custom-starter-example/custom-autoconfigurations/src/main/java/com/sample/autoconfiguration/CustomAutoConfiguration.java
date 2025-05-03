package com.sample.autoconfiguration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class CustomAutoConfiguration {
	 @Bean
	    public CustomListener customListener() {
		 
			System.out.println("*******************************");
			System.out.println("customListener");
			System.out.println("*******************************");
			
	        return new CustomListener();
	    }
}
