
1. SWQ_MQ_POC_WEB_SPRING_JNDI:

	we can deploy it on Tomcat server
	jar files are available in lib folder.
	
2. Environment Configuration :
		Tomcat version : 7.1
		JDK : 1.7
		IBM MQ Server : 8.0
		TIBCO EMS : 8.3.0

3. This examples developed by considering TIBCO EMS and IBM MQ servers are running on localmachine with default configurations.

user need to create below configurations in MQ server & TIBCO EMS to execute these examples:
--------------------------------------------------------------------------------------------
4. For MQ : 
	for JNDI and Queue confiurations refer below files from application context
	1.spring-mqseries-jms.xml
	2.spring-mqseries-sender-re-try.xml
	
	
5. For EMS TIBCO Configurations :
	one must do the configurations as per the below mentioned files
	1.spring-mqseries-jms.xml
    
	

Reference URL:

https://docs.tibco.com/pub/enterprise_message_service/8.1.0/doc/html/wwhelp/wwhimpl/js/html/wwhelp.htm