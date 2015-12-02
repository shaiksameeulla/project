/*
 * @author soagarwa */
package src.com.capgemini.lbs.mdbutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import src.com.dtdc.mdbServices.booking.CashBookingMDBService;
import src.com.dtdc.mdbServices.manifest.OutgoingManifestMDBService;

// TODO: Auto-generated Javadoc
/**
 * The Class SpringContextLoader.
 */
public class SpringContextLoader {

	/** The context. */
	private static ApplicationContext context = null;
	
	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(SpringContextLoader.class);
	

	static {
		try {
			LOGGER.info("Loading application context......... ");
			context = new ClassPathXmlApplicationContext(
			"src/com/capgemini/lbs/resources/springContext/applicationContext_listener.xml");
			OutgoingManifestMDBService mftService = (OutgoingManifestMDBService)context.getBean("outgoingManifestService");
			CashBookingMDBService cbookingService = (CashBookingMDBService)context.getBean("CashBookingService");
			LOGGER.info("SpringContextLoader::static block::mftService===> " + mftService);
			LOGGER.info("SpringContextLoader::static block::cbookingService===> " + cbookingService);
			LOGGER.info("Application context loaded successfully.........! ");
		} catch (Exception e) {
			LOGGER.error("EntryTaxUtil::static::Exception occured:"
					+e.getMessage());
		}
	}
	
	/**
	 * Gets the spring context.
	 *
	 * @return the spring context
	 * @throws Exception the exception
	 */
	public static ApplicationContext getSpringContext() throws Exception {
		return context;
	}

}
