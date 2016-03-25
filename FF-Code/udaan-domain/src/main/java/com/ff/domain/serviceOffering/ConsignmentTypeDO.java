/**
 * 
 */
package com.ff.domain.serviceOffering;

import com.capgemini.lbs.framework.domain.CGMasterDO;



/**
 * @author uchauhan
 *
 */
public class ConsignmentTypeDO extends CGMasterDO{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6053590501136120606L;
	private Integer consignmentId =null;
	  private String consignmentCode;
	  private String consignmentName;
	  
	  
	/**
	 * @return the consignmentName
	 */
	public String getConsignmentName() {
		return consignmentName;
	}
	/**
	 * @param consignmentName the consignmentName to set
	 */
	public void setConsignmentName(String consignmentName) {
		this.consignmentName = consignmentName;
	}
	/**
	 * @return the consignmentCode
	 */
	public String getConsignmentCode() {
		return consignmentCode;
	}
	/**
	 * @param consignmentCode the consignmentCode to set
	 */
	public void setConsignmentCode(String consignmentCode) {
		this.consignmentCode = consignmentCode;
	}
	/**
	 * @return the consignmentId
	 */
	public Integer getConsignmentId() {
		return consignmentId;
	}
	/**
	 * @param consignmentId the consignmentId to set
	 */
	public void setConsignmentId(Integer consignmentId) {
		this.consignmentId = consignmentId;
	}

	
	
	

}
