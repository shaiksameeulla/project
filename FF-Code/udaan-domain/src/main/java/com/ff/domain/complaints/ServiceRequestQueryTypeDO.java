/**
 * 
 */
package com.ff.domain.complaints;

import com.capgemini.lbs.framework.domain.CGMasterDO;

/**
 * @author abarudwa
 *
 */
public class ServiceRequestQueryTypeDO extends CGMasterDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer serviceRequestQueryTypeId;
	private String queryTypeCode;
	private String queryTypeName;
	private String queryTypeDescription;
	
	/**
	 * Added By sami
	 * Type: enum{S,C,B}
	 * Comments:C-CONSIGNMENT/BOOKING REFERENCE NUMBER
	 * 			S-SERVICE REQUEST  B-BOTH-cONSIGNMENT/BOOKING REFERENCE 
	 * 
	 * Reason : identifier for service request and consignment
	 *                                                
	 */
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
