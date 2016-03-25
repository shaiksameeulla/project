/*
 * @author soagarwa
 */
package com.capgemini.lbs.framework.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class MessageWrapper.
 */
@SuppressWarnings("serial")
public class MessageWrapper implements Serializable {

	/** All kinds of message list populated by service layer. */
	private List<MessageDetail> messageList = new ArrayList<MessageDetail>();

	/**
	 * Instantiates a new message wrapper.
	 */
	public MessageWrapper() {

	}

	/**
	 * adds a Business message to the list.
	 *
	 * @param msgDetail MessageDetail object
	 */
	public void addMessageDetail(MessageDetail msgDetail) {

		messageList.add(msgDetail);
	}

	/**
	 * Checks if the list of messages returned by the service has any
	 * confirmation message.
	 * 
	 * @return boolean true if confirmation message is there.
	 */
	public boolean isMessage() {
		return (messageList.size() > 0) ? true : false;
	}

	/**
	 * Gets the message list.
	 *
	 * @return the message list
	 */
	public List<MessageDetail> getMessageList() {
		return messageList;
	}

	/**
	 * @param messageList the messageList to set
	 */
	public void setMessageList(List<MessageDetail> messageList) {
		this.messageList = messageList;
	}

}
