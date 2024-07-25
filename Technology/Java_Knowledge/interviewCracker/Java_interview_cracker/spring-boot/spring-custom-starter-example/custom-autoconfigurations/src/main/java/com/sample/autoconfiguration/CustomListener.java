package com.sample.autoconfiguration;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class CustomListener implements ApplicationListener<ContextRefreshedEvent> {
	
@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println("*******************************");
		System.out.println("Our spring boot Custom starter is working!");
		System.out.println("*******************************");
	}
}
