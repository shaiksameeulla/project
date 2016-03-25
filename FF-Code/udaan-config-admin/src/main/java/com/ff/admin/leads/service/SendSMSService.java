/**
 * 
 */
package com.ff.admin.leads.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.leads.LeadTO;

/**
 * @author abarudwa
 *
 */
public interface SendSMSService {
	
	LeadTO getLeadDetails(final String leadNumber) throws CGSystemException,CGBusinessException;
	
	String sendSMS(final String num,final String msg,final Integer userId)
			throws CGSystemException,CGBusinessException;
	
	

}
