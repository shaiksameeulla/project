/**
 * 
 */
package com.ff.leads;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author abarudwa
 *
 */
public class LeadStatusTO extends CGBaseTO
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer statusCode;
	
	private String statusDescription;
	
	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	
}