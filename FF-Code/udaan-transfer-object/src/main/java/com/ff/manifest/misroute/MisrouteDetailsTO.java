package com.ff.manifest.misroute;

import com.capgemini.lbs.framework.utils.StringUtil;

public class MisrouteDetailsTO implements Comparable<MisrouteDetailsTO> {

	private String scannedItemNo;
	private Integer scannedItemId;
	private Integer noOfPieces;
	private String origin;
	private String pincode;
	private Integer pincodeId;
	private Double actualWeight;
	private String cnContent;
	private Integer cnContentId;
	private String paperWork;
	private Integer paperWorkId;
	private Boolean ischecked;
	private String insurance;
	private String insuredBy;
	private String insurancePolicyNo;
	private Integer manifestEmbeddeIn;

	private Integer rowCount;
	private Integer originOffName;
	private String processCode;
	private Integer bookingOffId;
	private Integer position;
	private Integer mapEmbeddedManifestId;
	private Integer consgManifestedId;
	private Integer noOfElements;
	private Double topayAmt;
	private Double declaredValue;
	private Double codAmt;
	private String otherContent;
	
	
	

	/**
	 * @return the consgManifestedId
	 */
	public Integer getConsgManifestedId() {
		return consgManifestedId;
	}

	/**
	 * @param consgManifestedId the consgManifestedId to set
	 */
	public void setConsgManifestedId(Integer consgManifestedId) {
		this.consgManifestedId = consgManifestedId;
	}

	/**
	 * @return the mapEmbeddedManifestId
	 */
	public Integer getMapEmbeddedManifestId() {
		return mapEmbeddedManifestId;
	}

	/**
	 * @param mapEmbeddedManifestId the mapEmbeddedManifestId to set
	 */
	public void setMapEmbeddedManifestId(Integer mapEmbeddedManifestId) {
		this.mapEmbeddedManifestId = mapEmbeddedManifestId;
	}

	/**
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}

	/**
	 * @param originOffName
	 *            the originOffName to set
	 */
	public void setOriginOffName(Integer originOffName) {
		this.originOffName = originOffName;
	}

	private String remarks;

	/**
	 * @return the scannedItemNo
	 */
	public String getScannedItemNo() {
		return scannedItemNo;
	}

	/**
	 * @param scannedItemNo
	 *            the scannedItemNo to set
	 */
	public void setScannedItemNo(String scannedItemNo) {
		this.scannedItemNo = scannedItemNo;
	}

	/**
	 * @return the scannedItemId
	 */
	public Integer getScannedItemId() {
		return scannedItemId;
	}

	/**
	 * @param scannedItemId
	 *            the scannedItemId to set
	 */
	public void setScannedItemId(Integer scannedItemId) {
		this.scannedItemId = scannedItemId;
	}

	/**
	 * @return the noOfPieces
	 */
	public Integer getNoOfPieces() {
		return noOfPieces;
	}

	/**
	 * @param noOfPieces
	 *            the noOfPieces to set
	 */
	public void setNoOfPieces(Integer noOfPieces) {
		this.noOfPieces = noOfPieces;
	}

	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin
	 *            the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @return the pincode
	 */
	public String getPincode() {
		return pincode;
	}

	/**
	 * @param pincode
	 *            the pincode to set
	 */
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	/**
	 * @return the pincodeId
	 */
	public Integer getPincodeId() {
		return pincodeId;
	}

	/**
	 * @param pincodeId
	 *            the pincodeId to set
	 */
	public void setPincodeId(Integer pincodeId) {
		this.pincodeId = pincodeId;
	}

	/**
	 * @return the actualWeight
	 */
	public Double getActualWeight() {
		return actualWeight;
	}

	/**
	 * @param actualWeight
	 *            the actualWeight to set
	 */
	public void setActualWeight(Double actualWeight) {
		this.actualWeight = actualWeight;
	}

	/**
	 * @return the cnContent
	 */
	public String getCnContent() {
		return cnContent;
	}

	/**
	 * @param cnContent
	 *            the cnContent to set
	 */
	public void setCnContent(String cnContent) {
		this.cnContent = cnContent;
	}

	/**
	 * @return the cnContentId
	 */
	public Integer getCnContentId() {
		return cnContentId;
	}

	/**
	 * @param cnContentId
	 *            the cnContentId to set
	 */
	public void setCnContentId(Integer cnContentId) {
		this.cnContentId = cnContentId;
	}

	/**
	 * @return the paperWork
	 */
	public String getPaperWork() {
		return paperWork;
	}

	/**
	 * @param paperWork
	 *            the paperWork to set
	 */
	public void setPaperWork(String paperWork) {
		this.paperWork = paperWork;
	}

	/**
	 * @return the paperWorkId
	 */
	public Integer getPaperWorkId() {
		return paperWorkId;
	}

	/**
	 * @param paperWorkId
	 *            the paperWorkId to set
	 */
	public void setPaperWorkId(Integer paperWorkId) {
		this.paperWorkId = paperWorkId;
	}

	/**
	 * @return the insuredBy
	 */
	public String getInsuredBy() {
		return insuredBy;
	}

	/**
	 * @param insuredBy
	 *            the insuredBy to set
	 */
	public void setInsuredBy(String insuredBy) {
		this.insuredBy = insuredBy;
	}

	/**
	 * @return the insurancePolicyNo
	 */
	public String getInsurancePolicyNo() {
		return insurancePolicyNo;
	}

	/**
	 * @param insurancePolicyNo
	 *            the insurancePolicyNo to set
	 */
	public void setInsurancePolicyNo(String insurancePolicyNo) {
		this.insurancePolicyNo = insurancePolicyNo;
	}

	/**
	 * @return the rowCount
	 */
	public Integer getRowCount() {
		return rowCount;
	}

	/**
	 * @param rowCount
	 *            the rowCount to set
	 */
	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the insurance
	 */
	public String getInsurance() {
		return insurance;
	}

	/**
	 * @param insurance
	 *            the insurance to set
	 */
	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	/**
	 * @return the manifestEmbeddeIn
	 */
	public Integer getManifestEmbeddeIn() {
		return manifestEmbeddeIn;
	}

	/**
	 * @param manifestEmbeddeIn
	 *            the manifestEmbeddeIn to set
	 */
	public void setManifestEmbeddeIn(Integer manifestEmbeddeIn) {
		this.manifestEmbeddeIn = manifestEmbeddeIn;
	}

	

	/**
	 * @return the ischecked
	 */
	public Boolean getIschecked() {
		return ischecked;
	}

	/**
	 * @param ischecked
	 *            the ischecked to set
	 */
	public void setIschecked(Boolean ischecked) {
		this.ischecked = ischecked;
	}

	/**
	 * @return the originOffName
	 */
	public Integer getOriginOffName() {
		return originOffName;
	}

	public String getProcessCode() {
		return processCode;
	}

	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	public Integer getBookingOffId() {
		return bookingOffId;
	}

	public void setBookingOffId(Integer bookingOffId) {
		this.bookingOffId = bookingOffId;
	}
	

	public Integer getNoOfElements() {
		return noOfElements;
	}

	public void setNoOfElements(Integer noOfElements) {
		this.noOfElements = noOfElements;
	}

	@Override
	public int compareTo(MisrouteDetailsTO arg0) {
		int returnVal = 0;
		/*if(!StringUtil.isStringEmpty(this.consgNo)) {
			returnVal = consgNo.compareTo(arg0.consgNo);
		}*/
		if(!StringUtil.isEmptyInteger(position) &&
				!StringUtil.isEmptyInteger(arg0.getPosition())){
			returnVal = position.compareTo(arg0.position);
		}
		return returnVal;
	}

	public Double getTopayAmt() {
		return topayAmt;
	}

	public void setTopayAmt(Double topayAmt) {
		this.topayAmt = topayAmt;
	}

	public Double getDeclaredValue() {
		return declaredValue;
	}

	public void setDeclaredValue(Double declaredValue) {
		this.declaredValue = declaredValue;
	}

	public Double getCodAmt() {
		return codAmt;
	}

	public void setCodAmt(Double codAmt) {
		this.codAmt = codAmt;
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
