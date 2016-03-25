package com.ff.serviceOfferring;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class ConsignmentTypeConfigTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5666831201710288551L;
	private Integer consignmentTypeConfigId;
	private String docType;
	private Integer docId;
	private Double declaredValue;
	private String isPaperworkMandatory;
	private String consignmentType;
	

	public Integer getConsignmentTypeConfigId() {
		return consignmentTypeConfigId;
	}

	public void setConsignmentTypeConfigId(Integer consignmentTypeConfigId) {
		this.consignmentTypeConfigId = consignmentTypeConfigId;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public Integer getDocId() {
		return docId;
	}

	public void setDocId(Integer docId) {
		this.docId = docId;
	}

	public Double getDeclaredValue() {
		return declaredValue;
	}

	public void setDeclaredValue(Double declaredValue) {
		this.declaredValue = declaredValue;
	}

	public String getIsPaperworkMandatory() {
		return isPaperworkMandatory;
	}

	public void setIsPaperworkMandatory(String isPaperworkMandatory) {
		this.isPaperworkMandatory = isPaperworkMandatory;
	}

	/**
	 * @return the consignmentType
	 */
	public String getConsignmentType() {
		return consignmentType;
	}

	/**
	 * @param consignmentType the consignmentType to set
	 */
	public void setConsignmentType(String consignmentType) {
		this.consignmentType = consignmentType;
	}

}
