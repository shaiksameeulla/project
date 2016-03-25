/*
 * @author soagarwa
 */
package com.capgemini.lbs.framework.exception;

/**
 * The Class CGBaseException.
 */
@SuppressWarnings("serial")
public class CGBaseException extends Exception {

	/**
	 * Instantiates a new CG base exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public CGBaseException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new CG base exception.
	 *
	 * @param cause the cause
	 */
	public CGBaseException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new CG base exception.
	 *
	 * @param message the message
	 */
	public CGBaseException(String message) {
		super(message);

	}

	/**
	 * Instantiates a new CG base exception.
	 */
	public CGBaseException() {

	}

}
