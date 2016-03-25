/*
 * @author soagarwa
 */
package com.capgemini.lbs.framework.exception;

/**
 * The Class MessageType.
 */
public class MessageType {

	/** The message type. */
	private String messageType;

	/** Constant to indicate that the type of the message is Error. */
	public static final MessageType Error = new MessageType("E");

	/** Constant to indicate that the type of the message is Warning. */
	public static final MessageType Warning = new MessageType("W");
	
	/** Constant to indicate that the type of the message is Information. */
	public static final MessageType Information = new MessageType("M");

	/** Constant to indicate that type of message is Confirmation. */
	public static final MessageType Confirmation = new MessageType("C");

	/**
	 * Instantiates a new message type.
	 *
	 * @param messageType the message type
	 */
	private MessageType(String messageType) {
		this.messageType = messageType;
	}

	/**
	 * Instantiates a new message type.
	 */
	public MessageType() {
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return messageType;
	}

	/**
	 * Sets the message type.
	 *
	 * @param messageType the new message type
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

}
