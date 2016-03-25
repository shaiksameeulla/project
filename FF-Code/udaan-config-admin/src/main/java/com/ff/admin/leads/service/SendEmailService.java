/**
 * 
 */
package com.ff.admin.leads.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.ff.leads.LeadTO;

/**
 * @author abarudwa
 *
 */
public interface SendEmailService  {
	
	LeadTO getLeadDetails(final String leadNumber) throws CGSystemException,CGBusinessException;
	
	String sendEmail(MailSenderTO mailSenderTO);

}
