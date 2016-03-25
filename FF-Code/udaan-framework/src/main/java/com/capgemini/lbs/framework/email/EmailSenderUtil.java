/*******************************************************************************
 * **   
 *  **    Copyright: (c) 10/20/2013 Capgemini All Rights Reserved.
 * **------------------------------------------------------------------------------
 * ** Capgemini India Private Limited  |  No part of this file may be reproduced
 * **                                  |  or transmitted in any form or by any
 * **                                  |  means, electronic or mechanical, for the
 * **                                  |  purpose, without the express written
 * **                                  |  permission of the copyright holder.
 * *
 ******************************************************************************/
package com.capgemini.lbs.framework.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.to.MailSenderTO;


/**
 * @author mohammes
 * The Class EmailSenderUtil.
 */
public class EmailSenderUtil implements Runnable {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailSenderUtil.class);
	
	/** The mail sender. */
	private EmailSender mailSender;
	
	
	
	/** The t. */
	private Thread t= null;
	
	private MailSenderTO emailSenderTO=null;
	
	/**
	 * Gets the mail sender.
	 *
	 * @return the mail sender
	 */
	public EmailSender getMailSender() {
		return mailSender;
	}
	
	/**
	 * Sets the mail sender.
	 *
	 * @param mailSender the new mail sender
	 */
	public void setMailSender(EmailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	
	
	/**
	 * Send email util.
	 *
	 * @param from the from
	 * @param to the to
	 * @param mailSubject the mail subject
	 * @param templateVariables the template variables
	 * @param templateName the template name
	 */
	public void sendEmail(MailSenderTO emailUtilTo){
		LOGGER.debug("EmailSenderUtil::EmailSenderUtil::Start=======>");
		
		try {
			//emailUtilTo.setTo(new String[]{FrameworkConstants.CLIENT_USER_FROM_EMAIL_ID}); for testing 
			this.emailSenderTO=emailUtilTo;
			t= new Thread(this);
			t.start();
		} catch (Exception e) {
			LOGGER.error("EmailSenderUtil:sendEmailUtil:Exception occured:",e);
		}
		LOGGER.debug("EmailSenderUtil::EmailSenderUtil::End=======>");
	}
	
	
	
	

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		if(emailSenderTO!=null){
			if(emailSenderTO.getTemplateVariables()==null && emailSenderTO.getAttachedFile()==null){
				mailSender.sendEmailByPlainText(emailSenderTO);
			}else if(emailSenderTO.getAttachedFile()!=null){
				
					try {
						mailSender.sendEmailWithAttachment(emailSenderTO);
					} catch (CGBusinessException e) {
						LOGGER.error("Error in Mail Sending with attachment:",e);
					}
				
			}else if(emailSenderTO.getTemplateVariables()!=null){
				mailSender.sendEmailWithTemplate(emailSenderTO);
			}
		
		}
		t=null;
	}

	/**
	 * @return the emailSenderTO
	 */
	public MailSenderTO getEmailSenderTO() {
		return emailSenderTO;
	}

	/**
	 * @param emailSenderTO the emailSenderTO to set
	 */
	public void setEmailSenderTO(MailSenderTO emailSenderTO) {
		this.emailSenderTO = emailSenderTO;
	}
	
	
	
	
}
