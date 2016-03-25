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

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.capgemini.lbs.framework.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class EmailSenderImpl.
 */
public class EmailSenderImpl implements EmailSender {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
	.getLogger(EmailSenderImpl.class);

	/** The velocity engine. */
	private VelocityEngine velocityEngine;

	/** The mail sender. */
	private JavaMailSender javaMailSender;

	/**
	 * Sets the velocity engine.
	 *
	 * @param velocityEngine the new velocity engine
	 */
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}


	/**
	 * @return the velocityEngine
	 */
	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}


	/**
	 * @return the javaMailSender
	 */
	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}


	/**
	 * @param javaMailSender the javaMailSender to set
	 */
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}


	/**
	 * Sends e-mail using Velocity template for the body and
	 * the properties passed in as Velocity variables.
	 *
	 * @param msg the msg
	 * @param hTemplateVariables the h template variables
	 * @param templateName the template name
	 * @param fileNameAndLocation the file name and location
	 * @param plainMailBody TODO
	 */
	private void send(final SimpleMailMessage msg, 
			final Map<Object, Object> hTemplateVariables, final String templateName,final String fileNameAndLocation,final String plainMailBody) {
		try {
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				@Override
				public void prepare(MimeMessage mimeMessage) throws CGSystemException, CGBusinessException {
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
					try {
						message.setTo(msg.getTo());
						message.setFrom(FrameworkConstants.CLIENT_USER_FROM_EMAIL_ID);
						message.setSubject(msg.getSubject());
						if(fileNameAndLocation != null){
							FileSystemResource file = new FileSystemResource(fileNameAndLocation);
							message.addAttachment(file.getFilename(), file);
						}
						String body=plainMailBody;
						if(!StringUtil.isStringEmpty(templateName)){
							body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, hTemplateVariables);
						}
						LOGGER.info("body={}", body); 				
						message.setText(body, true);
					} catch (VelocityException e) {
						LOGGER.error("EmailSenderImpl::send::VelocityException occured:"
								,e);
					} catch (MessagingException e) {
						LOGGER.error("EmailSenderImpl::send::MessagingException occured:"
								,e);
					}
				}
			};

			javaMailSender.send(preparator);
			LOGGER.info("Sent e-mail to '{}'.", msg.getTo());
		} catch (MailException e) {
			LOGGER.error("EmailSenderImpl::send::Exception occured:"
					,e);
		} catch (Exception e) {
			LOGGER.error("EmailSenderImpl::send::Exception occured:"
					,e);
		}


	}

	@Override
	public void sendEmailWithTemplate(final MailSenderTO mailSenderTO) {

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(mailSenderTO.getTo());
		msg.setFrom(FrameworkConstants.CLIENT_USER_FROM_EMAIL_ID);
		msg.setSubject(mailSenderTO.getMailSubject());
		send(msg, mailSenderTO.getTemplateVariables(), mailSenderTO.getTemplateName(),null, null);
	}

	

	
	/*
	 * Sends e-mail with body as plain text.
	 * 
	 * @param   from             from address.
	 * @param   to               to address.
	 * @param   subject          subject of the email.
	 * @param   body             body of the email. 
	 */

	/* (non-Javadoc)
	 * @see com.capgemini.lbs.framework.email.EmailSender#sendEmailByPlainText(java.lang.String, java.lang.String[], java.lang.String, java.lang.String)
	 */
	@Override
	public void sendEmailByPlainText(final MailSenderTO mailSenderTO) {
		try{
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				@Override
				public void prepare(MimeMessage mimeMessage) throws CGSystemException, CGBusinessException {
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
					try {
						message.setTo(mailSenderTO.getTo());
						message.setFrom(FrameworkConstants.CLIENT_USER_FROM_EMAIL_ID);
						message.setSubject(mailSenderTO.getMailSubject());
						LOGGER.debug("body={}", mailSenderTO.getPlainMailBody()); 				
						message.setText(mailSenderTO.getPlainMailBody(), true);
					} catch (MessagingException e) {
						LOGGER.error("EmailSenderImpl::sendEmailByPlainText::MessagingException occured:"
								,e);
					}
				}
			};
			javaMailSender.send(preparator);
			LOGGER.info("Sent e-mail to '{}'.",mailSenderTO.getTo());
		} catch (MailException e) {
			LOGGER.error("EmailSenderImpl::sendEmailByPlainText::Exception occured:"
					,e);
		} catch (Exception e) {
			LOGGER.error("EmailSenderImpl::sendEmailByPlainText::Exception occured:"
					,e);
		}
	}

	

	/* (non-Javadoc)
	 * @see com.capgemini.lbs.framework.email.EmailSender#sendEmailWithAttachment(com.capgemini.lbs.framework.frameworkbaseTO.MailSenderTO)
	 */
	@Override
	public void sendEmailWithAttachment(final MailSenderTO mailSenderTO) throws CGBusinessException {
		// TODO Auto-generated method stub
		try{
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				@Override
				public void prepare(MimeMessage mimeMessage) throws CGBusinessException {
					try {
						MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true);
						if(mailSenderTO.getTo() == null || mailSenderTO.getTo().length == 0) {
							throw new CGBusinessException("No Recipient provided");
						} else {
							message.setTo(mailSenderTO.getTo());
						}
						message.setFrom(mailSenderTO.getFrom() == null || mailSenderTO.getFrom().length() == 0 ? FrameworkConstants.CLIENT_USER_FROM_EMAIL_ID : mailSenderTO.getFrom());
						message.setSubject(mailSenderTO.getMailSubject());
						
						if(null != mailSenderTO.getCc() && mailSenderTO.getCc().length != 0)
							message.setCc(mailSenderTO.getCc());
						if(null != mailSenderTO.getBcc() && mailSenderTO.getBcc().length != 0)
							message.setBcc(mailSenderTO.getBcc());
						message.setText(mailSenderTO.getPlainMailBody());
						if(mailSenderTO.getAttachedFile()!=null){
						message.addAttachment(mailSenderTO.getAttachedFileName(), mailSenderTO.getAttachedFile());
						}
					} catch (MessagingException e) {
						LOGGER.error("EmailSenderImpl::sendEmailWithAttachment::MessagingException occured:"
								,e);
					}
				}
			};
			javaMailSender.send(preparator);
		} catch (MailException e) {
			LOGGER.error("EmailSenderImpl::sendEmailWithAttachment::Exception occured:"
					,e);
			throw e;
		} finally {
			// If file is created and exists in server machine then
			// delete it after attached and sent as required
			// Added By Himal
			if (mailSenderTO.getAttachedFile() != null
					&& mailSenderTO.getAttachedFile().exists()) {
				mailSenderTO.getAttachedFile().delete();
			}
		}
	}
}
