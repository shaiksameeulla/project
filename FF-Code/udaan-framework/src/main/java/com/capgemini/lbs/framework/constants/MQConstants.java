/*
 * @author mohammes
 */
package com.capgemini.lbs.framework.constants;

// TODO: Auto-generated Javadoc
/**
 * The Interface MQConstants.
 */
public interface MQConstants {

	/** The M q_ properties. */
	String MQ_PROPERTIES = "messagequeue";

	/** The NAMIN g_ factory. */
	String NAMING_FACTORY = "java.naming.factory.initial";

	/** The FACTOR y_ url. */
	String FACTORY_URL = "java.naming.factory.url.pkgs";

	/** The PROVIDE r_ url. */
	String PROVIDER_URL = "java.naming.provider.url";

	/** The CONNECTOI n_ factory. */
	String CONNECTOIN_FACTORY = "ConnectionFactory";

	/** The JNDI queue. */	
	String JNDI_QUEUE = "queue/UDAANQUEUE";
	
	String OPSMAN_QUEUE = "queue/OPSMANQUEUE";
	
	String JNDI_TEMP_QUEUE = "queue/TEMPQUEUE";

	/** The M q_ sprin g_ contex t_ path. */

	String MQ_SPRING_CONTEXT_PATH = "WebContent\\WEB-INF\\springCtx\\applicationContext_mq.xml";

	/** The M q_ sprin g_ contex t_ pat h_ centra l_ server. */
	String MQ_SPRING_CONTEXT_PATH_CENTRAL_SERVER = "WebContent\\WEB-INF\\springCtx\\applicationContext_remote.xml";

	/** The ASYN c_ queu e_ properties. */
	String ASYNC_QUEUE_PROPERTIES = "asynchqueue";

	/** The SPRIN g_ contex t_ path. */
	String SPRING_CONTEXT_PATH = "spring.context.path";
	
	String TWO_WAY_WRITE_JNDI_QUEUE = "queue/TWOWAYWRITEQUEUE";

	String JNDI_QUEUE_NOTIFICATION = "queue/NOTIFICATIONQ";
}
