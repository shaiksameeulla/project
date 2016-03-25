package com.ff.serviceOfferring;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author nkattung
 * 
 */
public class CNPaperWorksTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5121797753351573518L;
	private Integer cnPaperWorkId;
	private String cnPaperWorkCode;
	private String cnPaperWorkName;
	private String cnPaperWorkDesc;
	private String status;
	private String paperWorkRefNum;
	private String pincode;
	private Double declatedValue;
	private String docType;

	public Integer getCnPaperWorkId() {
		return cnPaperWorkId;
	}

	public void setCnPaperWorkId(Integer cnPaperWorkId) {
		this.cnPaperWorkId = cnPaperWorkId;
	}

	public String getCnPaperWorkCode() {
		return cnPaperWorkCode;
	}

	public void setCnPaperWorkCode(String cnPaperWorkCode) {
		this.cnPaperWorkCode = cnPaperWorkCode;
	}

	public String getCnPaperWorkName() {
		return cnPaperWorkName;
	}

	public void setCnPaperWorkName(String cnPaperWorkName) {
		this.cnPaperWorkName = cnPaperWorkName;
	}

	public String getCnPaperWorkDesc() {
		return cnPaperWorkDesc;
	}

	public void setCnPaperWorkDesc(String cnPaperWorkDesc) {
		this.cnPaperWorkDesc = cnPaperWorkDesc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public Double getDeclatedValue() {
		return declatedValue;
	}

	public void setDeclatedValue(Double declatedValue) {
		this.declatedValue = declatedValue;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getPaperWorkRefNum() {
		return paperWorkRefNum;
	}

	public void setPaperWorkRefNum(String paperWorkRefNum) {
		this.paperWorkRefNum = paperWorkRefNum;
	}

}
