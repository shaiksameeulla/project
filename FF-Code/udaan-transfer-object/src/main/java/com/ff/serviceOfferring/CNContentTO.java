package com.ff.serviceOfferring;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author nkattung
 * 
 */
public class CNContentTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1364795579206134866L;
	private Integer cnContentId;
	private String cnContentCode;
	private String cnContentName;
	private String cnContentDesc;
	private String status;
	private String otherContent;

	public Integer getCnContentId() {
		return cnContentId;
	}

	public void setCnContentId(Integer cnContentId) {
		this.cnContentId = cnContentId;
	}

	public String getCnContentCode() {
		return cnContentCode;
	}

	public void setCnContentCode(String cnContentCode) {
		this.cnContentCode = cnContentCode;
	}

	public String getCnContentName() {
		return cnContentName;
	}

	public void setCnContentName(String cnContentName) {
		this.cnContentName = cnContentName;
	}

	public String getCnContentDesc() {
		return cnContentDesc;
	}

	public void setCnContentDesc(String cnContentDesc) {
		this.cnContentDesc = cnContentDesc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOtherContent() {
		return otherContent;
	}

	public void setOtherContent(String otherContent) {
		this.otherContent = otherContent;
	}

}
