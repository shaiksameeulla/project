package com.capgemini.lbs.framework.sms;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SmsSenderTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface SmsSenderService.
 * 
 * @author narmdr
 */
public interface SmsSenderService {

	/**
	 * Prepare and send sms.
	 *
	 * @param smsSenderTO the sms sender to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	void prepareAndSendSms(SmsSenderTO smsSenderTO) throws CGBusinessException, CGSystemException;

	
}
