/**
 * 
 */
package com.ff.complaints;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author abarudwa
 *
 */
public class ServiceRequestQueryTypeTO extends CGBaseTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer serviceRequestQueryTypeId;
	private String queryTypeCode;
	private String queryTypeName;
	private String queryTypeDescription;
	private String queryType;
	
	public Integer getServiceRequestQueryTypeId() {
		return serviceRequestQueryTypeId;
	}
	public void setServiceRequestQueryTypeId(Integer serviceRequestQueryTypeId) {
		this.serviceRequestQueryTypeId = serviceRequestQueryTypeId;
	}
	public String getQueryTypeCode() {
		return queryTypeCode;
	}
	public void setQueryTypeCode(String queryTypeCode) {
		this.queryTypeCode = queryTypeCode;
	}
	public String getQueryTypeName() {
		return queryTypeName;
	}
	public void setQueryTypeName(String queryTypeName) {
		this.queryTypeName = queryTypeName;
	}
	public String getQueryTypeDescription() {
		return queryTypeDescription;
	}
	public void setQueryTypeDescription(String queryTypeDescription) {
		this.queryTypeDescription = queryTypeDescription;
	}
	/**
	 * @return the queryType
	 */
	public String getQueryType() {
		return queryType;
	}
	/**
	 * @param queryType the queryType to set
	 */
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	
	

}
