package com.ff.domain.leads;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author abarudwa
 *
 */
public class LeadStatusDO extends CGFactDO
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


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
