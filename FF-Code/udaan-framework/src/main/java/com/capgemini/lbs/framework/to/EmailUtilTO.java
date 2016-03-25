/**
 * 
 */
package com.capgemini.lbs.framework.to;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class EmailUtilTO.
 *
 * @author mohammes
 */
@SuppressWarnings("serial")
public class EmailUtilTO extends CGBaseTO {

	/** The from. */
	private String from;
	
	/** The to. */
	private String[] to;
	
	/** The cc. */
	private String[] cc;
	
	private String[] bcc;
	private File attachedFile;
	private String attachedFileName;
	
	
	/** The mail subject. */
	private String  mailSubject;
	
	/** The template variables. */
	private Map<Object,Object> templateVariables;
	
	/** The template name. */
	private String templateName;
	
	/** The plain mail body. */
	private String plainMailBody;
	
	/**
	 * Gets the from.
	 *
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}
	
	/**
	 * Gets the to.
	 *
	 * @return the to
	 */
	public String[] getTo() {
		return to;
	}
	
	/**
	 * Gets the mail subject.
	 *
	 * @return the mail subject
	 */
	public String getMailSubject() {
		return mailSubject;
	}
	
	/**
	 * Gets the template variables.
	 *
	 * @return the template variables
	 */
	public Map<Object, Object> getTemplateVariables() {
		return templateVariables;
	}
	
	/**
	 * Gets the template name.
	 *
	 * @return the template name
	 */
	public String getTemplateName() {
		return templateName;
	}
	
	/**
	 * Sets the from.
	 *
	 * @param from the new from
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	
	/**
	 * Sets the to.
	 *
	 * @param to the new to
	 */
	public void setTo(String[] newTo) {
		if(newTo == null) { 
			this.to = new String[0]; 
		} else { 
			this.to = Arrays.copyOf(newTo, newTo.length); 
		}
	}
	
	/**
	 * Sets the mail subject.
	 *
	 * @param mailSubject the new mail subject
	 */
	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}
	
	/**
	 * Sets the template variables.
	 *
	 * @param templateVariables the template variables
	 */
	public void setTemplateVariables(Map<Object, Object> templateVariables) {
		this.templateVariables = templateVariables;
	}
	
	/**
	 * Sets the template name.
	 *
	 * @param templateName the new template name
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
	/**
	 * Gets the plain mail body.
	 *
	 * @return the plain mail body
	 */
	public String getPlainMailBody() {
		return plainMailBody;
	}
	
	/**
	 * Sets the plain mail body.
	 *
	 * @param plainMailBody the new plain mail body
	 */
	public void setPlainMailBody(String plainMailBody) {
		this.plainMailBody = plainMailBody;
	}

	/**
	 * @return the cc
	 */
	public String[] getCc() {
		return cc;
	}

	/**
	 * @return the bcc
	 */
	public String[] getBcc() {
		return bcc;
	}

	

	/**
	 * @return the attachedFileName
	 */
	public String getAttachedFileName() {
		return attachedFileName;
	}

	/**
	 * @param bcc the bcc to set
	 */
	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}

	/**
	 * @param attachedFileName the attachedFileName to set
	 */
	public void setAttachedFileName(String attachedFileName) {
		this.attachedFileName = attachedFileName;
	}

	/**
	 * @param cc the cc to set
	 */
	public void setCc(String[] cc) {
		this.cc = cc;
	}

	/**
	 * @return the attachedFile
	 */
	public File getAttachedFile() {
		return attachedFile;
	}

	/**
	 * @param attachedFile the attachedFile to set
	 */
	public void setAttachedFile(File attachedFile) {
		this.attachedFile = attachedFile;
	}
}
