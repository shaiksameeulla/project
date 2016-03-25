/*
 * @author soagarwa
 */
package com.capgemini.lbs.framework.exception;

/**
 * The Class CGSystemException.
 */
@SuppressWarnings("serial")
public class CGSystemException extends CGBaseException {

	/**
	 * Instantiates a new CG system exception.
	 *
	 * @param obj the obj
	 */
	public CGSystemException(Exception obj) {
		super(obj);
	}

	/**
	 * Instantiates a new CG system exception.
	 *
	 * @param message the message
	 * @param obj the obj
	 */
	public CGSystemException(String message, Exception obj) {
		super(message, obj);
	}

}
