/*******************************************************************************
 * **   
 *  **    Copyright: (c) 3/20/2013 Capgemini All Rights Reserved.
 * **------------------------------------------------------------------------------
 * ** Capgemini India Private Limited  |  No part of this file may be reproduced
 * **                                  |  or transmitted in any form or by any
 * **                                  |  means, electronic or mechanical, for the
 * **                                  |  purpose, without the express written
 * **                                  |  permission of the copyright holder.
 * *
 ******************************************************************************/
package com.capgemini.lbs.framework.email;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.to.MailSenderTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface EmailSender.
 */
public interface EmailSender {

	/**
	 * Send email with template.
	 *
	 * @param mailSenderTO the mail sender to
	 */
	void sendEmailWithTemplate(final MailSenderTO mailSenderTO);

	/**
	 * Send email by plain text.
	 *
	 * @param from the from
	 * @param to the to
	 * @param subject the subject
	 * @param body the body
	 */
	void sendEmailByPlainText(final MailSenderTO mailSenderTO);

	/**
	 * Send email with attachment.
	 *
	 * @param mailSenderTO the mail sender to
	 * @throws CGBusinessException the cG business exception
	 */
	void sendEmailWithAttachment(final MailSenderTO mailSenderTO)
			throws CGBusinessException;
	
	
	
}
