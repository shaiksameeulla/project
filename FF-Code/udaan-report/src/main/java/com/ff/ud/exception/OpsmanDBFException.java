package com.ff.ud.exception;

public class OpsmanDBFException extends Exception {

	private static final long serialVersionUID = 1L;
	
	protected Throwable cause = null;
	
	public OpsmanDBFException() {
		super();
	}

	public OpsmanDBFException(String message) {
		super(message);
	}
	
	public OpsmanDBFException(Throwable cause) {
		this.cause = cause;
	}
	
	public OpsmanDBFException(Throwable cause, String message) {
		super(message);
		this.cause = cause;
	}

	/**
	 * Returns the root cause exception.
	 * @return exception object.
	 */
	public Throwable getCause() {
		return cause;
	}

	/**
	 * Sets the root cause exception.
	 * @param the exception cause.
	 */
	public void setCause(Throwable cause) {
		this.cause = cause;
	}
	
	// String representation of exception object.
	public String toString() {
		return getMessage() + " [Cause: " + getCause() + "]";
	}

}
