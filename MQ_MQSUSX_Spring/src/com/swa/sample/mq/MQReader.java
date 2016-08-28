package com.swa.sample.mq;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class MQReader {
	public static void main(String s[]){
		
		try
		 {
		System.out.println("ReadingMQSpringJMS started");
	   
	    
	    
	    ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(); 
	    appContext.setConfigLocation(new ClassPathResource("spring-mqseries-jms-reader.xml").getPath());
	    appContext.refresh();
	    
		System.out.println("Classpath loaded");
		
		System.out.println("Reading MQSpringJMS end");
		 }
		catch(Exception e){
			e.printStackTrace();
		}
		}
	}

