package com.ff.manifest;

/**
 * The Class BranchOutManifestParcelDetailsTO.
 */
public class BranchOutManifestParcelDetailsTO extends OutManifestDetailBaseTO {

	private static final long serialVersionUID = 1L;

	/** Primary key of consignment manifested table */
	private Integer consgManifestedId;
	
	/** The no of pcs. */
	private Integer noOfPcs;

	/** The cn content. */
	private String cnContent;

	/** The cn content id. */
	private Integer cnContentId;

	/** The cn content code. */
	private String cnContentCode;

	/** The other cn content. */
	private String otherCNContent;

	/** The declared value. */
	private Double declaredValue;

	/** The paper work. */
	private String paperWork;

	/** The paper work id. */
	private Integer paperWorkId;

	/** The paper work code. */
	private String paperWorkCode;
	/** The paper ref num. */
	private String paperRefNum;

	/** The to pay amt. */
	private Double toPayAmt;

	/** The cod amt. */
	private Double codAmt;
	
	/** The lc amt. */
	private Double lcAmt;
	
	/** The lc bank name. */
	private String lcBankName;
	
	/**
	 * @return the lcAmt
	 */
	public Double getLcAmt() {
		return lcAmt;
	}

	/**
	 * @param lcAmt the lcAmt to set
	 */
	public void setLcAmt(Double lcAmt) {
		this.lcAmt = lcAmt;
	}

	/**
	 * @return the lcBankName
	 */
	public String getLcBankName() {
		return lcBankName;
	}

	/**
	 * @param lcBankName the lcBankName to set
	 */
	public void setLcBankName(String lcBankName) {
		this.lcBankName = lcBankName;
	}



	/* To be used for specifically Branch manifest Parcel and Third party BPL */
	/** The custom duty amt. */
	private Double customDutyAmt;

	/** The service charge. */
	private Double serviceCharge;

	/** The state tax. */
	private Double stateTax;
	
	/** The child cn. */
	private String childCn;

	/** The booking type. */
	private String bookingType;
	
	/**
	 * Gets the bookingType
	 * 
	 * @return the bookingType
	 */
	public String getBookingType() {
		return bookingType;
	}

	/**
	 * Sets the bookingType
	 * 
	 * @param bookingType the bookingType to set
	 */
	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
	}

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
	 * Gets the cn content id.
	 * 
	 * @return the cn content id
	 */
	public Integer getCnContentId() {
		return cnContentId;
	}

	/**
	 * Sets the cn content id.
	 * 
	 * @param cnContentId
	 *            the new cn content id
	 */
	public void setCnContentId(Integer cnContentId) {
		this.cnContentId = cnContentId;
	}

	/**
	 * Gets the paper work id.
	 * 
	 * @return the paper work id
	 */
	public Integer getPaperWorkId() {
		return paperWorkId;
	}

	/**
	 * Sets the paper work id.
	 * 
	 * @param paperWorkId
	 *            the new paper work id
	 */
	public void setPaperWorkId(Integer paperWorkId) {
		this.paperWorkId = paperWorkId;
	}

	/**
	 * Gets the cn content.
	 * 
	 * @return the cn content
	 */
	public String getCnContent() {
		return cnContent;
	}

	/**
	 * Sets the cn content.
	 * 
	 * @param cnContent
	 *            the new cn content
	 */
	public void setCnContent(String cnContent) {
		this.cnContent = cnContent;
	}

	/**
	 * Gets the paper work.
	 * 
	 * @return the paper work
	 */
	public String getPaperWork() {
		return paperWork;
	}

	/**
	 * Sets the paper work.
	 * 
	 * @param paperWork
	 *            the new paper work
	 */
	public void setPaperWork(String paperWork) {
		this.paperWork = paperWork;
	}

	/**
	 * Gets the no of pcs.
	 * 
	 * @return the no of pcs
	 */
	public Integer getNoOfPcs() {
		return noOfPcs;
	}

	/**
	 * Sets the no of pcs.
	 * 
	 * @param noOfPcs
	 *            the new no of pcs
	 */
	public void setNoOfPcs(Integer noOfPcs) {
		this.noOfPcs = noOfPcs;
	}

	/**
	 * Gets the declared value.
	 * 
	 * @return the declared value
	 */
	public Double getDeclaredValue() {
		return declaredValue;
	}

	/**
	 * Sets the declared value.
	 * 
	 * @param declaredValue
	 *            the new declared value
	 */
	public void setDeclaredValue(Double declaredValue) {
		this.declaredValue = declaredValue;
	}

	/**
	 * Gets the to pay amt.
	 * 
	 * @return the to pay amt
	 */
	public Double getToPayAmt() {
		return toPayAmt;
	}

	/**
	 * Sets the to pay amt.
	 * 
	 * @param toPayAmt
	 *            the new to pay amt
	 */
	public void setToPayAmt(Double toPayAmt) {
		this.toPayAmt = toPayAmt;
	}

	/**
	 * Gets the cod amt.
	 * 
	 * @return the cod amt
	 */
	public Double getCodAmt() {
		return codAmt;
	}

	/**
	 * Sets the cod amt.
	 * 
	 * @param codAmt
	 *            the new cod amt
	 */
	public void setCodAmt(Double codAmt) {
		this.codAmt = codAmt;
	}

	/**
	 * Gets the custom duty amt.
	 * 
	 * @return the custom duty amt
	 */
	public Double getCustomDutyAmt() {
		return customDutyAmt;
	}

	/**
	 * Sets the custom duty amt.
	 * 
	 * @param customDutyAmt
	 *            the new custom duty amt
	 */
	public void setCustomDutyAmt(Double customDutyAmt) {
		this.customDutyAmt = customDutyAmt;
	}

	/**
	 * Gets the service charge.
	 * 
	 * @return the service charge
	 */
	public Double getServiceCharge() {
		return serviceCharge;
	}

	/**
	 * Sets the service charge.
	 * 
	 * @param serviceCharge
	 *            the new service charge
	 */
	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	/**
	 * Gets the state tax.
	 * 
	 * @return the state tax
	 */
	public Double getStateTax() {
		return stateTax;
	}

	/**
	 * Sets the state tax.
	 * 
	 * @param stateTax
	 *            the new state tax
	 */
	public void setStateTax(Double stateTax) {
		this.stateTax = stateTax;
	}

	/**
	 * Gets the cn content code.
	 * 
	 * @return the cnContentCode
	 */
	public String getCnContentCode() {
		return cnContentCode;
	}

	/**
	 * Sets the cn content code.
	 * 
	 * @param cnContentCode
	 *            the cnContentCode to set
	 */
	public void setCnContentCode(String cnContentCode) {
		this.cnContentCode = cnContentCode;
	}

	/**
	 * Gets the other cn content.
	 * 
	 * @return the otherCNContent
	 */
	public String getOtherCNContent() {
		return otherCNContent;
	}

	/**
	 * Sets the other cn content.
	 * 
	 * @param otherCNContent
	 *            the otherCNContent to set
	 */
	public void setOtherCNContent(String otherCNContent) {
		this.otherCNContent = otherCNContent;
	}

	/**
	 * Gets the paper ref num.
	 * 
	 * @return the paperRefNum
	 */
	public String getPaperRefNum() {
		return paperRefNum;
	}

	/**
	 * Sets the paper ref num.
	 * 
	 * @param paperRefNum
	 *            the paperRefNum to set
	 */
	public void setPaperRefNum(String paperRefNum) {
		this.paperRefNum = paperRefNum;
	}

	/**
	 * Gets the paper work code.
	 * 
	 * @return the paperWorkCode
	 */
	public String getPaperWorkCode() {
		return paperWorkCode;
	}

	/**
	 * Sets the paper work code.
	 * 
	 * @param paperWorkCode
	 *            the paperWorkCode to set
	 */
	public void setPaperWorkCode(String paperWorkCode) {
		this.paperWorkCode = paperWorkCode;
	}

	/**
	 * @return the childCn
	 */
	public String getChildCn() {
		return childCn;
	}

	/**
	 * @param childCn
	 *            the childCn to set
	 */
	public void setChildCn(String childCn) {
		this.childCn = childCn;
	}
	
}
