/*
 * @author soagarwa
 */
package com.capgemini.lbs.framework.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class MessageDetail.
 */
@SuppressWarnings("serial")
public class MessageDetail implements Serializable {

	/**
	 * Property propertyName for which error is to be shown. If its not set by
	 * the user, which indicates that error is not field specific and will be
	 * shown on top
	 */
	private String propertyName;
	
	/** Error key in the resource bundle. */
	private String messageKey;

	/**
	 * replacement parameters for the message defined in the resource bundle.
	 */
	private ArrayList<ParamInfo> params = new ArrayList<ParamInfo>();

	/** Type of the message Error , Warning, Information. */
	private MessageType messageType = null;

	/**
	 * Dessage Description for the message defined in the resource bundle.
	 */
	private String messageDescription = null;

	/**
	 * Instantiates a new message detail.
	 */
	public MessageDetail() {
	}

	/**
	 * Constructor to initialize with property for which error is to be shown
	 * and key.
	 *
	 * @param propertyName name of the property (maps to field on screen)
	 * @param key key in resource bundle
	 * @param type String type of the message E,W,I
	 */
	public MessageDetail(String propertyName, String key, MessageType type) {
		this.propertyName = propertyName;
		this.messageKey = key;
		this.messageType = type;

	}

	/**
	 * Constructor to initialize with key and type of the error.
	 *
	 * @param key key in resource bundle
	 * @param type String type of the message E,W,I
	 */
	public MessageDetail(String key, MessageType type) {

		this.messageKey = key;
		this.messageType = type;

	}

	/**
	 * Gets the message key.
	 *
	 * @return the message key
	 */
	public String getMessageKey() {
		return messageKey;
	}

	/**
	 * Sets the message key.
	 *
	 * @param messageKey the new message key
	 */
	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	/**
	 * Gets the property name.
	 *
	 * @return the property name
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * Sets the property name.
	 *
	 * @param propertyName the new property name
	 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * Gets the params.
	 *
	 * @return the params
	 */
	public ArrayList<ParamInfo> getParams() {
		return params;
	}

	/**
	 * Gets the message type.
	 *
	 * @return the message type
	 */
	public MessageType getMessageType() {
		return messageType;
	}

	/**
	 * Sets the message type.
	 *
	 * @param messageType the new message type
	 */
	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	/**
	 * Adds a message parameter to the existing list for this message.
	 *
	 * @param paramKey the param key
	 * @return the param info
	 */
	public ParamInfo addParamByKey(String paramKey) {

		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setKey(paramKey);
		return paramInfo;
	}

	/**
	 * Use this method if you want to print a message like this <br>
	 * list of fields required are A,B,C.
	 *
	 * @param list of keys.
	 * @return the param info
	 */
	public ParamInfo addParamByKeys(List list) {

		ParamInfo info = new ParamInfo();
		info.setKeys(list);

		return info;

	}

	/**
	 * Adds the message parameter key and its value for this message. If param
	 * value is provided , it will not be retrieved from the resource bundle
	 *
	 * @param value value of the parameter to be replaced in the message.
	 * @return the param info
	 */
	public void addParamByValue(String value) {
		ParamInfo info = new ParamInfo();
		info = new ParamInfo(null, value);
		params.add(info);
	}
	
	public void addParamByValue(String[] values) {
		for(String value :values) {
			addParamByValue(value);
		}
	}

	/**
	 * Sets the params.
	 *
	 * @param params The params to set.
	 */
	public void setParams(ArrayList<ParamInfo> params) {
		this.params = params;
	}

	/**
	 * Gets the message description.
	 *
	 * @return the messageDescription
	 */
	public String getMessageDescription() {
		return messageDescription;
	}

	/**
	 * Sets the message description.
	 *
	 * @param messageDescription the messageDescription to set
	 */
	public void setMessageDescription(String messageDescription) {
		this.messageDescription = messageDescription;
	}

}
