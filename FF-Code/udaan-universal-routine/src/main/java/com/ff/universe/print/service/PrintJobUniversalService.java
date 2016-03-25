/**
 * 
 */
package com.ff.universe.print.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;

/**
 * @author hkansagr
 * 
 */
public interface PrintJobUniversalService {

	/**
	 * To execute print job at terminal
	 * 
	 * @param printJobUrl
	 * @param template as String
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	void executePrintJob(String printJobUrl, String template) throws CGBusinessException, CGSystemException;
	
	/**
	 * To execute print job at terminal
	 * 
	 * @param printJobUrl
	 * @param template as byte[]
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	void executePrintJobInParallel(String printJobUrl, byte[] template) throws CGBusinessException, CGSystemException;

}
