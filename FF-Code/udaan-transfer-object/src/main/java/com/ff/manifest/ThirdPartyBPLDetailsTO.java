package com.ff.manifest;

import com.ff.organization.OfficeTO;


/**
 * The Class ThirdPartyBPLDetailsTO.
 */
public class ThirdPartyBPLDetailsTO extends OutManifestDetailBaseTO{
	
	private static final long serialVersionUID = 1L;

	/** The no of pcs. */
	private Integer noOfPcs;
	
	/** The cn content. */
	private String cnContent ;
	
	/** The cn content id. */
	private Integer cnContentId;
	
	/** The declared values. */
	private Double declaredValues ;
	
	/** The paper work. */
	private String paperWork;
	
	/** The paper work id. */
	private Integer paperWorkId;
	
	/** The to pay amts. */
	private Double toPayAmts ;
	
	/** The cod amts. */
	private Double codAmts ;
	
	/** The lcamts. */
	private Double baAmounts ;
	
	/** The octroi amts. */
	private Double octroiAmts;
	
	/** The service charges. */
	private Double serviceCharges ;
	
	/** The state taxes. */
	private Double stateTaxes ;

	/** The consg type. */
	private String consgType;
	
	/** The scanned grid item no. */
	private String scannedGridItemNo;
	
	/** The embedded type. */
	private String embeddedType;
	
	/** The child cn. */
	private String childCn;
	
	/** The booking type. */
	private String bookingType;
	
	/** The Delivery Attempt */
	private Integer deliveryAttempt;
	
	/** manifestStatus for grid details */
	private String manifestStatus;
	
	private OfficeTO originOfficeTO;
	private String otherContent;
	
	/**
	 * @return the manifestStatus
	 */
	public String getManifestStatus() {
		return manifestStatus;
	}

	
	/**
	 * @param manifestStatus the manifestStatus to set
	 */
	public void setManifestStatus(String manifestStatus) {
		this.manifestStatus = manifestStatus;
	}

	/**
	 * @return the octroiAmts
	 */
	public Double getOctroiAmts() {
		return octroiAmts;
	}

	/**
	 * @param octroiAmts the octroiAmts to set
	 */
	public void setOctroiAmts(Double octroiAmts) {
		this.octroiAmts = octroiAmts;
	}

	/**
	 * @return the originOffice
	 */
	public String getOriginOffice() {
		return originOffice;
	}

	/**
	 * @param originOffice the originOffice to set
	 */
	public void setOriginOffice(String originOffice) {
		this.originOffice = originOffice;
	}

	/** The Bookig office code */
	private String bookingOffCode;
		
	private int noOfElement;
	
	private String originOffice;
	
	private String bookingOffName;
	/**
	 * @return the bookingOffCode
	 */
	public String getBookingOffCode() {
		return bookingOffCode;
	}

	/**
	 * @param bookingOffCode the bookingOffCode to set
	 */
	public void setBookingOffCode(String bookingOffCode) {
		this.bookingOffCode = bookingOffCode;
	}

	/**
	 * @return the deliveryAttempt
	 */
	public Integer getDeliveryAttempt() {
		return deliveryAttempt;
	}

	/**
	 * @param deliveryAttempt the deliveryAttempt to set
	 */
	public void setDeliveryAttempt(Integer deliveryAttempt) {
		this.deliveryAttempt = deliveryAttempt;
	}

	/**
	 * Gets the paper work id
	 * 
	 * @return the paperWorkId
	 */
	public Integer getPaperWorkId() {
		return paperWorkId;
	}

	/**
	 * Sets the paper work id
	 * 
	 * @param paperWorkId the paperWorkId to set
	 */
	public void setPaperWorkId(Integer paperWorkId) {
		this.paperWorkId = paperWorkId;
	}

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
	 * Gets the child CNs
	 * 
	 * @return the childCn
	 */
	public String getChildCn() {
		return childCn;
	}

	/**
	 * Sets the child CNs
	 * 
	 * @param childCn the childCn to set
	 */
	public void setChildCn(String childCn) {
		this.childCn = childCn;
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
	 * @param cnContentId the new cn content id
	 */
	public void setCnContentId(Integer cnContentId) {
		this.cnContentId = cnContentId;
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
	 * @param cnContent the new cn content
	 */
	public void setCnContent(String cnContent) {
		this.cnContent = cnContent;
	}
	
	/**
	 * Gets the embedded type.
	 *
	 * @return the embedded type
	 */
	public String getEmbeddedType() {
		return embeddedType;
	}
	
	/**
	 * Sets the embedded type.
	 *
	 * @param embeddedType the new embedded type
	 */
	public void setEmbeddedType(String embeddedType) {
		this.embeddedType = embeddedType;
	}
	
	/**
	 * Gets the scanned grid item no.
	 *
	 * @return the scanned grid item no
	 */
	public String getScannedGridItemNo() {
		return scannedGridItemNo;
	}
	
	/**
	 * Sets the scanned grid item no.
	 *
	 * @param scannedGridItemNo the new scanned grid item no
	 */
	public void setScannedGridItemNo(String scannedGridItemNo) {
		this.scannedGridItemNo = scannedGridItemNo;
	}
	
	/**
	 * Gets the consg type.
	 *
	 * @return the consg type
	 */
	public String getConsgType() {
		return consgType;
	}
	
	/**
	 * Sets the consg type.
	 *
	 * @param consgType the new consg type
	 */
	public void setConsgType(String consgType) {
		this.consgType = consgType;
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
	 * @param noOfPcs the new no of pcs
	 */
	public void setNoOfPcs(Integer noOfPcs) {
		this.noOfPcs = noOfPcs;
	}
	
	
	/**
	 * Gets the declared values.
	 *
	 * @return the declared values
	 */
	public Double getDeclaredValues() {
		return declaredValues;
	}
	
	/**
	 * Sets the declared values.
	 *
	 * @param declaredValues the new declared values
	 */
	public void setDeclaredValues(Double declaredValues) {
		this.declaredValues = declaredValues;
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
	 * @param paperWork the new paper work
	 */
	public void setPaperWork(String paperWork) {
		this.paperWork = paperWork;
	}
	
	/**
	 * Gets the to pay amts.
	 *
	 * @return the to pay amts
	 */
	public Double getToPayAmts() {
		return toPayAmts;
	}
	
	/**
	 * Sets the to pay amts.
	 *
	 * @param toPayAmts the new to pay amts
	 */
	public void setToPayAmts(Double toPayAmts) {
		this.toPayAmts = toPayAmts;
	}
	
	/**
	 * Gets the cod amts.
	 *
	 * @return the cod amts
	 */
	public Double getCodAmts() {
		return codAmts;
	}
	
	/**
	 * Sets the cod amts.
	 *
	 * @param codAmts the new cod amts
	 */
	public void setCodAmts(Double codAmts) {
		this.codAmts = codAmts;
	}
		
	/**
	 * Gets the service charges.
	 *
	 * @return the service charges
	 */
	public Double getServiceCharges() {
		return serviceCharges;
	}
	
	/**
	 * Sets the service charges.
	 *
	 * @param serviceCharges the new service charges
	 */
	public void setServiceCharges(Double serviceCharges) {
		this.serviceCharges = serviceCharges;
	}
	
	/**
	 * Gets the state taxes.
	 *
	 * @return the state taxes
	 */
	public Double getStateTaxes() {
		return stateTaxes;
	}
	
	/**
	 * Sets the state taxes.
	 *
	 * @param stateTaxes the new state taxes
	 */
	public void setStateTaxes(Double stateTaxes) {
		this.stateTaxes = stateTaxes;
	}

	public int getNoOfElement() {
		return noOfElement;
	}

	public void setNoOfElement(int noOfElement) {
		this.noOfElement = noOfElement;
	}

	/**
	 * @return the bookingOffName
	 */
	public String getBookingOffName() {
		return bookingOffName;
	}

	/**
	 * @param bookingOffName the bookingOffName to set
	 */
	public void setBookingOffName(String bookingOffName) {
		this.bookingOffName = bookingOffName;
	}


	/**
	 * @return the originOfficeTO
	 */
	public OfficeTO getOriginOfficeTO() {
		if(originOfficeTO == null)
			originOfficeTO = new OfficeTO();
		return originOfficeTO;
	}


	/**
	 * @param originOfficeTO the originOfficeTO to set
	 */
	public void setOriginOfficeTO(OfficeTO originOfficeTO) {
		this.originOfficeTO = originOfficeTO;
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


	/**
	 * @return the baAmounts
	 */
	public Double getBaAmounts() {
		return baAmounts;
	}


	/**
	 * @param baAmounts the baAmounts to set
	 */
	public void setBaAmounts(Double baAmounts) {
		this.baAmounts = baAmounts;
	}

	



	
	
}
