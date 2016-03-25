package com.capgemini.lbs.framework.sms;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.to.SmsSenderTO;

// TODO: Auto-generated Javadoc
/**
 * The Class SmsSenderUtil.
 * 
 * @author narmdr
 */
public class SmsSenderUtil implements Runnable {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger
			.getLogger(SmsSenderUtil.class);
	
	/** The sms sender to. */
	private volatile SmsSenderTO smsSenderTO;
	
	/** The SMS sender service. */
	private SmsSenderService smsSenderServic;
	
	/**
	 * Instantiates a new sms sender util.
	 */
	public SmsSenderUtil() {
		// TODO let JVM to use this
	}
	
	/**
	 * Instantiates a new sms sender util.
	 *
	 * @param smsSenderTO the sms sender to
	 */
	public SmsSenderUtil(SmsSenderTO smsSenderTO) {
		this.smsSenderTO = smsSenderTO;
	}
	
	/**smsSenderServicsmsSenderServic
	 * Sets the sms sender to.
	 *
	 * @param smsSenderTO the smsSenderTO to set
	 */
	public void setSmsSenderTO(SmsSenderTO smsSenderTO) {
		this.smsSenderTO = smsSenderTO;
	}

	/**
	 * @param smsSenderServic the smsSenderServic to set
	 */
	public void setSmsSenderServic(SmsSenderService smsSenderServic) {
		smsSenderServic = smsSenderServic;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		LOGGER.debug("SmsSenderUtil::run::Thread processing started------------>:::::::");
		try {
			smsSenderServic.prepareAndSendSms(smsSenderTO);
			
		} catch (Exception e) {
			LOGGER.error("SmsSenderUtil :: run() :: Exception Happened : ",e);
		}
		LOGGER.debug("SmsSenderUtil::run::Thread processing End------------>:::::::");
	}
	
	/**
	 * Send sms.
	 *
	 * @param smsSenderTO the sms sender to
	 */
	public static void sendSms(SmsSenderTO smsSenderTO) {
		LOGGER.debug("SmsSenderUtil :: sendSms() :: START");
		SmsSenderUtil smsSenderUtil = new SmsSenderUtil(smsSenderTO);
		new Thread(smsSenderUtil).start();
		LOGGER.debug("SmsSenderUtil :: sendSms() :: End");
	}
	
}
