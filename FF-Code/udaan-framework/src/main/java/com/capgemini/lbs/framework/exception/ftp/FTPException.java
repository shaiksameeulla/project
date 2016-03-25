/* ****************************************************************
 * CTBS+ Project
 * File <FTPException.java>
 * Created by : Hari
 * Creation date : Apr 24, 2008
 */
package com.capgemini.lbs.framework.exception.ftp;


/**
 * @author anwar
 * 
 */
public class FTPException extends Exception
{
	/**
	 * 
	 */
	public FTPException()
	{
		super();
	}
	
	
	
	/**
	 * @param message
	 */
	public FTPException(String message)
	{
		super(message);
	}
	
	
	
	/**
	 * @param message
	 * @param cause
	 */
	public FTPException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	
	
	/**
	 * @param cause
	 */
	public FTPException(Throwable cause)
	{
		super(cause);
	}
}