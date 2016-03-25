package com.ff.manifest.rthrto;

import com.ff.serviceOfferring.ConsignmentTypeTO;

public class ConsignmentValidationTO {
	
	private String consgNumber;
	private String consignmentTypeName;
	private ConsignmentTypeTO consignmentTypeTO;
	private String manifestType;
	private Integer originOffice;
	private Integer destCityId;
	private String recentMnfstTypeOnCNo;
	private String errorMsg;
	private String manifestProcessCode;
	
	/** The consignment return type while validate i.e. RTH-H, RTO-R */
	private String consgReturnType;

	
	/**
	 * @return the consgReturnType
	 */
	public String getConsgReturnType() {
		return consgReturnType;
	}

	/**
	 * @param consgReturnType the consgReturnType to set
	 */
	public void setConsgReturnType(String consgReturnType) {
		this.consgReturnType = consgReturnType;
	}

	/**
	 * @return the consgNumber
	 */
	public String getConsgNumber() {
		return consgNumber;
	}

	/**
	 * @param consgNumber the consgNumber to set
	 */
	public void setConsgNumber(String consgNumber) {
		this.consgNumber = consgNumber;
	}

	/**
	 * @return the consignmentTypeName
	 */
	public String getConsignmentTypeName() {
		return consignmentTypeName;
	}

	/**
	 * @param consignmentTypeName the consignmentTypeName to set
	 */
	public void setConsignmentTypeName(String consignmentTypeName) {
		this.consignmentTypeName = consignmentTypeName;
	}

	/**
	 * @return the manifestType
	 */
	public String getManifestType() {
		return manifestType;
	}

	/**
	 * @param manifestType the manifestType to set
	 */
	public void setManifestType(String manifestType) {
		this.manifestType = manifestType;
	}

	public Integer getOriginOffice() {
		return originOffice;
	}

	public void setOriginOffice(Integer originOffice) {
		this.originOffice = originOffice;
	}

	public Integer getDestCityId() {
		return destCityId;
	}

	public void setDestCityId(Integer destCityId) {
		this.destCityId = destCityId;
	}
	
	public String getRecentMnfstTypeOnCNo() {
		return recentMnfstTypeOnCNo;
	}

	public void setRecentMnfstTypeOnCNo(String recentMnfstTypeOnCNo) {
		this.recentMnfstTypeOnCNo = recentMnfstTypeOnCNo;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public ConsignmentTypeTO getConsignmentTypeTO() {
		return consignmentTypeTO;
	}

	public void setConsignmentTypeTO(ConsignmentTypeTO consignmentTypeTO) {
		this.consignmentTypeTO = consignmentTypeTO;
	}

	public String getManifestProcessCode() {
		return manifestProcessCode;
	}

	public void setManifestProcessCode(String manifestProcessCode) {
		this.manifestProcessCode = manifestProcessCode;
	}
	
}
