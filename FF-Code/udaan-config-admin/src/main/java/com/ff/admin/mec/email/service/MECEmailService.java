package com.ff.admin.mec.email.service;

/**
 * @author hkansagr
 */
public interface MECEmailService {

	/**
	 * To send email to all RHO office(s) with attached excel, which contain all
	 * respective branches expense/collection details
	 * 
	 * @throws Exception
	 */
	void triggerEmailToRHO() throws Exception;

}
