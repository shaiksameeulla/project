/*
 * @author soagarwa
 */
package com.capgemini.lbs.framework.utils;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;

/**
 * The Class EmailUtility.
 */
public class EmailUtility {

	/** The logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(EmailUtility.class);
	/** The to. */
	private String[] to;

	/** The cc. */
	private String cc;

	/** The bcc. */
	private String bcc;

	/** The subject. */
	private String subject;

	/** The message. */
	private String message;

	/** The attachment_filename. */
	private String attachment_filename;

	/** The from. */
	private String from;

	/** The host. */
	private String host;

	/** The header. */
	private String header;
	
	/** The host. */
	private Integer connectionTimeOut;
	private Integer socketIOTimeOut; //mail.smtp.timeout

	/**
	 * Instantiates a new email utility.
	 */
	public EmailUtility() {
		//setFrom("ctbsweb@dtdctestmail2.com");
		setFrom(FrameworkConstants.CLIENT_USER_FROM_EMAIL_ID);//CTBS + PROD  EMAIL ID
		//setHost("dtdctestmail2.com");//CTBS + TEST  EMAIL ID
		setHost("10.10.3.13");//CTBS + PROD HOST
		setConnectionTimeOut(30000);//CONNECTION TIME OUT . 30 SEC
		setSocketIOTimeOut(30000);//SOCKET IO CONNECTION TIME OUT . 30 SEC
	}

	/**
	 * Sets the to.
	 * 
	 * @param to
	 *            the new to
	 */
	public void setTo(String[] to) {
		this.to = to;
	}

	/**
	 * @return the socketIOTimeOut
	 */
	public Integer getSocketIOTimeOut() {
		return socketIOTimeOut;
	}

	/**
	 * @param socketIOTimeOut the socketIOTimeOut to set
	 */
	public void setSocketIOTimeOut(Integer socketIOTimeOut) {
		this.socketIOTimeOut = socketIOTimeOut;
	}

	/**
	 * Sets the cC.
	 * 
	 * @param cc
	 *            the new cC
	 */
	public void setCC(String cc) {
		this.cc = cc;
	}

	/**
	 * Sets the bcc.
	 * 
	 * @param bcc
	 *            the new bcc
	 */
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	/**
	 * Sets the subject.
	 * 
	 * @param subject
	 *            the new subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Sets the message.
	 * 
	 * @param message
	 *            the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Sets the attachment.
	 * 
	 * @param attachment_filename
	 *            the new attachment
	 */
	public void setAttachment(String attachment_filename) {
		this.attachment_filename = attachment_filename;
	}

	/**
	 * Sets the header.
	 * 
	 * @param header
	 *            the new header
	 */
	public void setHeader(String header) {
		this.header = header;
	}

	/**
	 * Gets the header.
	 * 
	 * @return the header
	 */
	public String getHeader() {
		return this.header;
	}

	/**
	 * Sets the from.
	 * 
	 * @param from
	 *            the new from
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return the connectionTimeOut
	 */
	public Integer getConnectionTimeOut() {
		return connectionTimeOut;
	}

	/**
	 * @param connectionTimeOut the connectionTimeOut to set
	 */
	public void setConnectionTimeOut(Integer connectionTimeOut) {
		this.connectionTimeOut = connectionTimeOut;
	}

	/**
	 * Gets the from.
	 * 
	 * @return the from
	 */
	public String getFrom() {
		return this.from;
	}

	/**
	 * Sets the host.
	 * 
	 * @param host
	 *            the new host
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Gets the host.
	 * 
	 * @return the host
	 */
	public String getHost() {
		return this.host;
	}

	/**
	 * Gets the to.
	 * 
	 * @return the to
	 */
	public String[] getTo() {
		return this.to;
	}

	/**
	 * Gets the cC.
	 * 
	 * @return the cC
	 */
	public String getCC() {
		return this.cc;
	}

	/**
	 * Gets the bcc.
	 * 
	 * @return the bcc
	 */
	public String getBcc() {
		return this.bcc;
	}

	/**
	 * Gets the subject.
	 * 
	 * @return the subject
	 */
	public String getSubject() {
		return this.subject;
	}

	/**
	 * Gets the message.
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * Gets the attachment.
	 * 
	 * @return the attachment
	 */
	public String getAttachment() {
		return this.attachment_filename;
	}

	/**
	 * This method will send the mail.
	 * 
	 * @return true, if successful
	 */

	public boolean send() {
		boolean mailSent = false;
		InternetAddress[] addressTo = new InternetAddress[this.to.length];
		try {
			MimeMultipart mp = new MimeMultipart();
			// setting the system properties
			Properties props = System.getProperties();
			props.put("mail.smtp.host", this.getHost());
			
			//Added Connection Timeout to 30 Sec
			props.put("mail.smtp.connectiontimeout",this.getConnectionTimeOut());
			props.put("mail.smtp.timeout",this.getSocketIOTimeOut());

			Session session = Session.getDefaultInstance(props, null);
			session.setDebug(true);

			Message msg = new MimeMessage(session);

			// senders info
			msg.setFrom(new InternetAddress(this.getFrom()));
			// recievers info
			for (int i = 0; i < this.to.length; i++) {
				addressTo[i] = new InternetAddress(this.to[i]);
			}
			msg.setRecipients(Message.RecipientType.TO, addressTo);
			// CC info
			if (null != this.getCC()) {
				msg.setRecipients(Message.RecipientType.CC,
						InternetAddress.parse(this.getCC(), false));
			}
			// BCC info
			if (null != this.getBcc()) {
				msg.setRecipients(Message.RecipientType.BCC,
						InternetAddress.parse(this.getBcc(), false));
			}
			// Subject of mail
			msg.setSubject(subject);
			// Setting the header
			if (null != this.getHeader()) {
				msg.setHeader("SLA-Header", this.getHeader());
			}
			// Date on which message is send
			msg.setSentDate(new Date());
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setContent(this.getMessage(), "text/html");
			mp.addBodyPart(mbp1);

			// attachment if any
			if (null != this.getAttachment() && this.getAttachment() != "") {
				MimeBodyPart mbp2 = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(this.getAttachment());
				mbp2.setDataHandler(new DataHandler(fds));
				mbp2.setFileName(this.getAttachment());
				mp.addBodyPart(mbp2);

			}
			msg.setContent(mp);
			// Sending the mail
			// Transport tr = session.getTransport("smtp");
			// root are username and password can be configured
			// tr.connect(host, "root", "root");
			Transport.send(msg);
			logger.info("Message has been sent sucessfully");
			mailSent = true;
		} catch (Exception e) {
			logger.info("Email Cant be sent", e.getMessage());
			return false;
		}
		return mailSent;
	}

	/**
	 * Send email.
	 * 
	 * @param to
	 *            the to
	 * @param subject
	 *            the subject
	 * @param message
	 *            the message
	 * @return true, if successful
	 */
	public static boolean sendEmail(String[] to, String subject, String message) {
		EmailUtility email = new EmailUtility();
		email.setConnectionTimeOut(30000);//CONNECTION TIME OUT . 30 SEC
		email.setSocketIOTimeOut(30000);//SOCKET IO CONNECTION TIME OUT . 30 SEC
		/*TODO remove this when email is properly configured*/
		Integer toLength=to.length;
		String[] temp= new String[toLength+1];
		temp=to;
		temp[temp.length-1]=FrameworkConstants.CLIENT_USER_FROM_EMAIL_ID;
		
		email.setTo(temp);
		/*TODO remove this when email is properly configured and uncomment below*/
		//email.setTo(to);
		email.setSubject(subject);
		email.setMessage(message);
		boolean mailSent = email.send();
		return mailSent;
	}

	/**
	 * Send email.
	 * 
	 * @param to
	 *            the to
	 * @param cc
	 *            the cc
	 * @param bcc
	 *            the bcc
	 * @param subject
	 *            the subject
	 * @param message
	 *            the message
	 * @return true, if successful
	 */
	public static boolean sendEmail(String[] to, String cc, String bcc,
			String subject, String message) {
		EmailUtility email = new EmailUtility();
		email.setConnectionTimeOut(30000);//CONNECTION TIME OUT . 30 SEC
		email.setSocketIOTimeOut(30000);//SOCKET IO CONNECTION TIME OUT . 30 SEC
		email.setTo(to);
		// TODO Remove this line after mail configuration
		cc=cc+","+FrameworkConstants.CLIENT_USER_FROM_EMAIL_ID;
		
		email.setCC(cc);
		email.setBcc(bcc);
		email.setSubject(subject);
		email.setMessage(message);
		boolean mailSent = email.send();
		return mailSent;
	}

	/**
	 * Send email.
	 * 
	 * @param to
	 *            the to
	 * @param cc
	 *            the cc
	 * @param bcc
	 *            the bcc
	 * @param subject
	 *            the subject
	 * @param message
	 *            the message
	 * @param attachment_filename
	 *            the attachment_filename
	 * @return true, if successful
	 */
	public static boolean sendEmail(String[] to, String cc, String bcc,
			String subject, String message, String attachment_filename) {
		EmailUtility email = new EmailUtility();
		email.setConnectionTimeOut(5000);//CONNECTION TIME OUT . 5 SEC
		email.setSocketIOTimeOut(5000);//SOCKET IO CONNECTION TIME OUT . 5 SEC
		email.setTo(to);
		// TODO Remove this line after mail configuration
				cc=cc+","+FrameworkConstants.CLIENT_USER_FROM_EMAIL_ID;
		email.setCC(cc);
		email.setBcc(bcc);
		email.setSubject(subject);
		email.setMessage(message);
		email.setAttachment(attachment_filename);
		boolean mailSent = email.send();
		return mailSent;
	}

}
