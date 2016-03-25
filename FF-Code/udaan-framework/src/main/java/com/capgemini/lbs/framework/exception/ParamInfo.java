/*
 * @author mohammes
 */
package com.capgemini.lbs.framework.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class ParamInfo.
 */
@SuppressWarnings("serial")
public class ParamInfo implements Serializable {

	/** The key. */
	private String key = null;
	
	/** The value. */
	private String value = null;
	
	/** The keys. */
	private List keys = new ArrayList();

	/**
	 * Instantiates a new param info.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public ParamInfo(String key, String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Instantiates a new param info.
	 */
	public ParamInfo() {
	}

	/**
	 * Sets the key.
	 *
	 * @param key the new key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Gets the keys.
	 *
	 * @return the keys
	 */
	public List getKeys() {
		return keys;
	}

	/**
	 * Sets the keys.
	 *
	 * @param keys the new keys
	 */
	public void setKeys(List keys) {
		this.keys = keys;
	}

}
