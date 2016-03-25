/**
 * 
 */
package com.ff.universe.util;

import javax.servlet.http.HttpServletResponse;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;

/**
 * @author abarudwa
 *
 */
public interface SMSSenderService {
	
	public Boolean sendSMS(String num, String msg,HttpServletResponse response) throws CGSystemException, CGBusinessException;

}
