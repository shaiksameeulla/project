package com.swa.sample.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class MQWriter {
	public static void main(String s[]){
		
		try
		 {
		System.out.println("SendMQSpringJMS started");
	   
	    
	    
	    ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(); 
	    appContext.setConfigLocation(new ClassPathResource("spring-mqseries-jms-writer.xml").getPath());
	    appContext.refresh();
	    
		System.out.println("Classpath loaded");
		

		
		JmsTemplate jmsSender = (JmsTemplate)
		appContext.getBean("jmsTemplate");
		
		jmsSender.send(new MessageCreator(){

			@Override
			public Message createMessage(Session paramSession) throws JMSException {
				// TODO Auto-generated method stub
				System.out.println("Message Writing to Queue");
				return paramSession.createTextMessage("THis is First Message from JMS");
			}
			
		}
			);
		
		System.out.println("SendMQSpringJMS end");
		 }
		catch(Exception e){
			e.printStackTrace();
		}
		}
	}

