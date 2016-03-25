package com.ff.manifest.rthrto;

import com.ff.to.serviceofferings.ReasonTO;

/**
 * The Class RthRtoDetailsTO..
 * 
 * @author narmdr
 */
public class RthRtoDetailsTO implements Comparable<RthRtoDetailsTO>{
	
	private Integer consignmentId;
	private Integer consignmentManifestId;
	private String consgNumber;
	private Integer numOfPc;
	private Double actualWeight;
	private Double toPayAmt;
	private Double codAmt;
	private String remarks;
	private String receivedDate;

	private String cnContent;
	private String cnPaperWorks;
	private String pincode;
	
	private ReasonTO reasonTO;
	
	private String errorMsg;
	
	private Integer position;
	private Integer cnOriginOfficeId;
	private Integer cnOriginCityId;
	
	/* The Consg Status RTH - H and RTO - R */
	private String consgStatus;
	
	//needed for print
	private String reasonName;
	private String reasonCode;
	private Integer consgCount;
	private String originOfficeName;
	private Double declaredValue;
	private String cnContentName;
	private String otherContent;
	
	/**
	 * @return the consgStatus
	 */
	public String getConsgStatus() {
		return consgStatus;
	}
	/**
	 * @param consgStatus the consgStatus to set
	 */
	public void setConsgStatus(String consgStatus) {
		this.consgStatus = consgStatus;
	}
	/**
	 * @return the cnOriginCityId
	 */
	public Integer getCnOriginCityId() {
		return cnOriginCityId;
	}
	/**
	 * @param cnOriginCityId the cnOriginCityId to set
	 */
	public void setCnOriginCityId(Integer cnOriginCityId) {
		this.cnOriginCityId = cnOriginCityId;
	}
	/**
	 * @return the cnOriginOfficeId
	 */
	public Integer getCnOriginOfficeId() {
		return cnOriginOfficeId;
	}
	/**
	 * @param cnOriginOfficeId the cnOriginOfficeId to set
	 */
	public void setCnOriginOfficeId(Integer cnOriginOfficeId) {
		this.cnOriginOfficeId = cnOriginOfficeId;
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
	/**
	 * @return the consignmentManifestId
	 */
	public Integer getConsignmentManifestId() {
		return consignmentManifestId;
	}
	/**
	 * @param consignmentManifestId the consignmentManifestId to set
	 */
	public void setConsignmentManifestId(Integer consignmentManifestId) {
		this.consignmentManifestId = consignmentManifestId;
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
	 * @return the numOfPc
	 */
	public Integer getNumOfPc() {
		return numOfPc;
	}
	/**
	 * @param numOfPc the numOfPc to set
	 */
	public void setNumOfPc(Integer numOfPc) {
		this.numOfPc = numOfPc;
	}
	/**
	 * @return the actualWeight
	 */
	public Double getActualWeight() {
		return actualWeight;
	}
	/**
	 * @param actualWeight the actualWeight to set
	 */
	public void setActualWeight(Double actualWeight) {
		this.actualWeight = actualWeight;
	}
	/**
	 * @return the toPayAmt
	 */
	public Double getToPayAmt() {
		return toPayAmt;
	}
	/**
	 * @param toPayAmt the toPayAmt to set
	 */
	public void setToPayAmt(Double toPayAmt) {
		this.toPayAmt = toPayAmt;
	}
	/**
	 * @return the codAmt
	 */
	public Double getCodAmt() {
		return codAmt;
	}
	/**
	 * @param codAmt the codAmt to set
	 */
	public void setCodAmt(Double codAmt) {
		this.codAmt = codAmt;
	}
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return the receivedDate
	 */
	public String getReceivedDate() {
		return receivedDate;
	}
	/**
	 * @param receivedDate the receivedDate to set
	 */
	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}
	/**
	 * @return the cnContent
	 */
	public String getCnContent() {
		return cnContent;
	}
	/**
	 * @param cnContent the cnContent to set
	 */
	public void setCnContent(String cnContent) {
		this.cnContent = cnContent;
	}
	/**
	 * @return the cnPaperWorks
	 */
	public String getCnPaperWorks() {
		return cnPaperWorks;
	}
	/**
	 * @param cnPaperWorks the cnPaperWorks to set
	 */
	public void setCnPaperWorks(String cnPaperWorks) {
		this.cnPaperWorks = cnPaperWorks;
	}
	/**
	 * @return the pincode
	 */
	public String getPincode() {
		return pincode;
	}
	/**
	 * @param pincode the pincode to set
	 */
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	/**
	 * @return the reasonTO
	 */
	public ReasonTO getReasonTO() {
		return reasonTO;
	}
	/**
	 * @param reasonTO the reasonTO to set
	 */
	public void setReasonTO(ReasonTO reasonTO) {
		this.reasonTO = reasonTO;
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
	
	public Integer getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}
	
	@Override
	public int compareTo(RthRtoDetailsTO detailsTO) {
		/*int result = 0;
		if(!StringUtil.isEmptyInteger(position) 
				&& !StringUtil.isEmptyInteger(obj.getPosition())){
			result = this.position.compareTo(obj.position);
		}
		return result;*/

		return this.getConsignmentManifestId().compareTo(
				detailsTO.getConsignmentManifestId());
	}
	/**
	 * @return the reasonName
	 */
	public String getReasonName() {
		return reasonName;
	}
	/**
	 * @param reasonName the reasonName to set
	 */
	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}
	/**
	 * @return the reasonCode
	 */
	public String getReasonCode() {
		return reasonCode;
	}
	/**
	 * @param reasonCode the reasonCode to set
	 */
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	/**
	 * @return the consgCount
	 */
	public Integer getConsgCount() {
		return consgCount;
	}
	/**
	 * @param consgCount the consgCount to set
	 */
	public void setConsgCount(Integer consgCount) {
		this.consgCount = consgCount;
	}
	/**
	 * @return the originOfficeName
	 */
	public String getOriginOfficeName() {
		return originOfficeName;
	}
	/**
	 * @param originOfficeName the originOfficeName to set
	 */
	public void setOriginOfficeName(String originOfficeName) {
		this.originOfficeName = originOfficeName;
	}
	/**
	 * @return the declaredValue
	 */
	public Double getDeclaredValue() {
		return declaredValue;
	}
	/**
	 * @param declaredValue the declaredValue to set
	 */
	public void setDeclaredValue(Double declaredValue) {
		this.declaredValue = declaredValue;
	}
	
	/**
	 * @return the cnContentName
	 */
	public String getCnContentName() {
		return cnContentName;
	}
	/**
	 * @param cnContentName the cnContentName to set
	 */
	public void setCnContentName(String cnContentName) {
		this.cnContentName = cnContentName;
	}
	/**
	 * @return the otherContent
	 */
	public String getOtherContent() {
		return otherContent;
	}
	/**
	 * @param otherContent the otherContent to set
	 */
	public void setOtherContent(String otherContent) {
		this.otherContent = otherContent;
	}
	

	
}
