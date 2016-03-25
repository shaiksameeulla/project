/*
 * @author mohammes
 */
package com.capgemini.lbs.framework.exception;

/**
 * The Class CGBusinessException.
 */
@SuppressWarnings("serial")
public class CGBusinessException extends CGBaseException {

	/** The business message. */
	private MessageWrapper businessMessage;
	
	private String bundleName;

	/**
	 * Instantiates a new CG business exception.
	 */
	public CGBusinessException() {
		super();
	}

	/**
	 * Instantiates a new CG business exception.
	 *
	 * @param message the message
	 */
	public CGBusinessException(String message) {
		super(message);

	}
	
	/**
	 * Instantiates a new cG business exception.
	 *
	 * @param message the message
	 * @param bundleName the bundle name
	 */
	public CGBusinessException(String message,String bundleName) {
		super(message);
		this.bundleName=bundleName;

	}

	/**
	 * Instantiates a new CG business exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public CGBusinessException(String message, Throwable cause) {
		super(message, cause);

	}

	/**
	 * Instantiates a new CG business exception.
	 *
	 * @param businessMessage the business message
	 */
	public CGBusinessException(MessageWrapper businessMessage) {

		this.businessMessage = businessMessage;
	}
	
	public CGBusinessException(MessageWrapper businessMessage,String message) {
		super(message);
		this.businessMessage = businessMessage;
	}

	/**
	 * Instantiates a new CG business exception.
	 *
	 * @param businessMessage the business message
	 * @param cause the cause
	 */
	public CGBusinessException(MessageWrapper businessMessage, Throwable cause) {
		super(cause);
		this.businessMessage = businessMessage;

	}

	/**
	 * Instantiates a new CG business exception.
	 *
	 * @param cause the cause
	 */
	public CGBusinessException(Throwable cause) {
		super(cause);
	}

	/**
	 * Gets the business message.
	 *
	 * @return Returns the businessMessage.
	 */
	public MessageWrapper getBusinessMessage() {
		return businessMessage;
	}

	/**
	 * @return the bundleName
	 */
	public String getBundleName() {
		return bundleName;
	}

	/**
	 * @param bundleName the bundleName to set
	 */
	public void setBundleName(String bundleName) {
		this.bundleName = bundleName;
	}

	/**
	 * @param businessMessage the businessMessage to set
	 */
	public void setBusinessMessage(MessageWrapper businessMessage) {
		this.businessMessage = businessMessage;
	}

}
