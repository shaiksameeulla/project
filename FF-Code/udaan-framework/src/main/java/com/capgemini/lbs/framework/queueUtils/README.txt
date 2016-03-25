#-------------------------------------------------------------------------------
# **   
#  **    Copyright: (c) 3/20/2013 Capgemini All Rights Reserved.
# **------------------------------------------------------------------------------
# ** Capgemini India Private Limited  |  No part of this file may be reproduced
# **                                  |  or transmitted in any form or by any
# **                                  |  means, electronic or mechanical, for the
# **                                  |  purpose, without the express written
# **                                  |  permission of the copyright holder.
# *
#-------------------------------------------------------------------------------
/***/
<-- QueueConsumerUtil.java used to read all the messages from the Queue, 
	recieveMessage() is static a method inside QueueConsumerUtil.java,
	which expects queue name as string parameter,
	this queue name is taken from queueconfiguration.properties file-->
	  
<-- QueueProducerUtil.java used to write all the messages to the Queue,
	writeMessage() is static a method inside QueueProducerUtil.java,
	which expects 2 parameters,
	i) 1st parameter queueName, as string parameter,
	   this queue name is configured in queueconfiguration.properties file
	ii) 2nd parameter messageObj, as an object, this is actual message  -->
	
<-- QueueManager.java used to create and maintain the JMS ConnectionFactory and 
	Connection from JBoss context object. All the configuration parameters are loaded
	from queueconfiguration.properties file like provider url, NamingContextFactory,... 
	And also in this class, has been take care for look up the JMS queue and getting the JMS Session-->
