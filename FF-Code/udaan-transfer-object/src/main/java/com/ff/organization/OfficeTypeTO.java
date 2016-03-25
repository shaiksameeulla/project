/**
 * 
 */
package com.ff.organization;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author uchauhan
 *
 */
public class OfficeTypeTO extends CGBaseTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7502111673195462573L;
	private Integer offcTypeId;
	  private String offcTypeCode;
	  private String offcTypeDesc;
	  
	  
	/**
	 * @return the offcTypeId
	 */
	public Integer getOffcTypeId() {
		return offcTypeId;
	}
	/**
	 * @param offcTypeId the offcTypeId to set
	 */
	public void setOffcTypeId(Integer offcTypeId) {
		this.offcTypeId = offcTypeId;
	}
	/**
	 * @return the offcTypeCode
	 */
	public String getOffcTypeCode() {
		return offcTypeCode;
	}
	/**
	 * @param offcTypeCode the offcTypeCode to set
	 */
	public void setOffcTypeCode(String offcTypeCode) {
		this.offcTypeCode = offcTypeCode;
	}
	/**
	 * @return the offcTypeDesc
	 */
	public String getOffcTypeDesc() {
		return offcTypeDesc;
	}
	/**
	 * @param offcTypeDesc the offcTypeDesc to set
	 */
	public void setOffcTypeDesc(String offcTypeDesc) {
		this.offcTypeDesc = offcTypeDesc;
	}
	/**
	 * @see java.lang.Object#toString()
	 * Nov 27, 2012
	 * @return
	 * toString
	 * com.ff.organization.OfficeTypeTO
	 * kgajare
	 */
	@Override
	public String toString() {
	    return "OfficeTypeTO [offcTypeId=" + offcTypeId + ", offcTypeCode="
		    + offcTypeCode + ", offcTypeDesc=" + offcTypeDesc + "]";
	}
	  
	  
	  

}
