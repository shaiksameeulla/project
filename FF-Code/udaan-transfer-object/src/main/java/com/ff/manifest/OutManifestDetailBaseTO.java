package com.ff.manifest;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.to.CGBaseTO;
import com.capgemini.lbs.framework.utils.StringUtil;

/**
 * The Class OutManifestDetailBaseTO.
 */
public class OutManifestDetailBaseTO extends CGBaseTO implements Comparable<OutManifestDetailBaseTO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3107143427030526665L;

	// common details attributes
	/** The consg no. */
	private String consgNo;
	
	/** The consg id. */
	private Integer consgId;
	//private Integer productId;
	/** The comail no. */
	private String comailNo;
	
	/** The comail id. */
	private Integer comailId;
	
	/** The is cn. */
	private String isCN = "Y";
	
	/** The manifest no. */
	private String manifestNo;
	
	/** The manifest id. */
	private Integer manifestId;

	/** The pincode. */
	private String pincode;
	
	/** The pincode id. */
	private Integer pincodeId;
	
	/** The bkg pincode id. */
	private Integer bkgPincodeId;
	
	/** The dest city. */
	private String destCity;
	
	/** The dest city id. */
	private Integer destCityId;
	
	/** The bkg weight. */
	private Double bkgWeight;
	
	/** The weight. */
	private Double weight;
	
	/** The is data mismatch. */
	private String isDataMismatch = "N";
	
	/** The position. */
	private Integer position;
	
	/** The booking id. */
	private Integer bookingId;	
	
	/** The booking type id. */
	private Integer bookingTypeId;
	
	/** The customer id. */
	private Integer customerId;
	
	/** The Runsheet no. */
	private String runsheetNo;
	
	/** The consignor id. */
	private Integer consignorId;
	
	/** The is pickup cn. */
	private String isPickupCN=CommonConstants.NO;
	
	/** The consg manifested id. */
	private Integer consgManifestedId;
	private Integer comailManifestedId;
	private Integer mapEmbeddedManifestId;
	
	/** The old weight. */
	private Double oldWeight;

	private Integer processId;
	
	private Integer gridOriginOfficeId;
	
	private String comailStatusPrint;
	
	private Integer destOfficeId;
	
	//UAT: proposed by saumya
	private String gridItemType; //C - Consignment, P - Pickup, M - Co-mail
	private CreditCustomerBookingConsignmentTO consignmentDetailsTO;
	private ComailTO comailTO;
		
	/**
	 * @return the oldWeight
	 */
	public Double getOldWeight() {
		return oldWeight;
	}

	/**
	 * @param oldWeight the oldWeight to set
	 */
	public void setOldWeight(Double oldWeight) {
		this.oldWeight = oldWeight;
	}

	/**
	 * @return the processId
	 */
	public Integer getProcessId() {
		return processId;
	}

	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}

	/**
	 * Sets the position.
	 *
	 * @param position the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}

	/**
	 * Gets the consg no.
	 *
	 * @return the consg no
	 */
	public String getConsgNo() {
		return consgNo;
	}

	/**
	 * Sets the consg no.
	 *
	 * @param consgNo the new consg no
	 */
	public void setConsgNo(String consgNo) {
		this.consgNo = consgNo;
	}

	/**
	 * Gets the comail no.
	 *
	 * @return the comail no
	 */
	public String getComailNo() {
		return comailNo;
	}

	/**
	 * Sets the comail no.
	 *
	 * @param comailNo the new comail no
	 */
	public void setComailNo(String comailNo) {
		this.comailNo = comailNo;
	}

	/**
	 * Gets the comail id.
	 *
	 * @return the comail id
	 */
	public Integer getComailId() {
		return comailId;
	}

	/**
	 * Sets the comail id.
	 *
	 * @param comailId the new comail id
	 */
	public void setComailId(Integer comailId) {
		this.comailId = comailId;
	}

	/**
	 * Gets the pincode.
	 *
	 * @return the pincode
	 */
	public String getPincode() {
		return pincode;
	}

	/**
	 * Sets the pincode.
	 *
	 * @param pincode the new pincode
	 */
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	/**
	 * Gets the pincode id.
	 *
	 * @return the pincode id
	 */
	public Integer getPincodeId() {
		return pincodeId;
	}

	/**
	 * Sets the pincode id.
	 *
	 * @param pincodeId the new pincode id
	 */
	public void setPincodeId(Integer pincodeId) {
		this.pincodeId = pincodeId;
	}
	
	/**
	 * Gets the dest city.
	 *
	 * @return the dest city
	 */
	public String getDestCity() {
		return destCity;
	}

	/**
	 * Sets the dest city.
	 *
	 * @param destCity the new dest city
	 */
	public void setDestCity(String destCity) {
		this.destCity = destCity;
	}

	/**
	 * Gets the dest city id.
	 *
	 * @return the dest city id
	 */
	public Integer getDestCityId() {
		return destCityId;
	}

	/**
	 * Sets the dest city id.
	 *
	 * @param destCityId the new dest city id
	 */
	public void setDestCityId(Integer destCityId) {
		this.destCityId = destCityId;
	}
	
	/**
	 * Gets the manifest no.
	 *
	 * @return the manifest no
	 */
	public String getManifestNo() {
		return manifestNo;
	}

	/**
	 * Sets the manifest no.
	 *
	 * @param manifestNo the new manifest no
	 */
	public void setManifestNo(String manifestNo) {
		this.manifestNo = manifestNo;
	}

	/**
	 * Gets the manifest id.
	 *
	 * @return the manifest id
	 */
	public Integer getManifestId() {
		return manifestId;
	}

	/**
	 * Sets the manifest id.
	 *
	 * @param manifestId the new manifest id
	 */
	public void setManifestId(Integer manifestId) {
		this.manifestId = manifestId;
	}

	/**
	 * Gets the consg id.
	 *
	 * @return the consg id
	 */
	public Integer getConsgId() {
		return consgId;
	}

	/**
	 * Sets the consg id.
	 *
	 * @param consgId the new consg id
	 */
	public void setConsgId(Integer consgId) {
		this.consgId = consgId;
	}
	/*public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}*/
	/**
	 * Gets the checks if is data mismatch.
	 *
	 * @return the checks if is data mismatch
	 */
	public String getIsDataMismatch() {
		return isDataMismatch;
	}

	/**
	 * Sets the checks if is data mismatch.
	 *
	 * @param isDataMismatch the new checks if is data mismatch
	 */
	public void setIsDataMismatch(String isDataMismatch) {
		this.isDataMismatch = isDataMismatch;
	}

	/**
	 * Gets the bkg weight.
	 *
	 * @return the bkg weight
	 */
	public Double getBkgWeight() {
		return bkgWeight;
	}

	/**
	 * Sets the bkg weight.
	 *
	 * @param bkgWeight the new bkg weight
	 */
	public void setBkgWeight(Double bkgWeight) {
		this.bkgWeight = bkgWeight;
	}

	/**
	 * Gets the bkg pincode id.
	 *
	 * @return the bkg pincode id
	 */
	public Integer getBkgPincodeId() {
		return bkgPincodeId;
	}

	/**
	 * Sets the bkg pincode id.
	 *
	 * @param bkgPincodeId the new bkg pincode id
	 */
	public void setBkgPincodeId(Integer bkgPincodeId) {
		this.bkgPincodeId = bkgPincodeId;
	}

	/**
	 * Gets the weight.
	 *
	 * @return the weight
	 */
	public Double getWeight() {
		return weight;
	}

	/**
	 * Sets the weight.
	 *
	 * @param weight the new weight
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	
	/**
	 * Gets the checks if is cn.
	 *
	 * @return the checks if is cn
	 */
	public String getIsCN() {
		return isCN;
	}

	/**
	 * Sets the checks if is cn.
	 *
	 * @param isCN the new checks if is cn
	 */
	public void setIsCN(String isCN) {
		this.isCN = isCN;
	}

	/**
	 * Gets the booking id.
	 *
	 * @return the bookingId
	 */
	public Integer getBookingId() {
		return bookingId;
	}

	/**
	 * Sets the booking id.
	 *
	 * @param bookingId the bookingId to set
	 */
	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	/**
	 * Gets the booking type id.
	 *
	 * @return the bookingTypeId
	 */
	public Integer getBookingTypeId() {
		return bookingTypeId;
	}

	/**
	 * Sets the booking type id.
	 *
	 * @param bookingTypeId the bookingTypeId to set
	 */
	public void setBookingTypeId(Integer bookingTypeId) {
		this.bookingTypeId = bookingTypeId;
	}

	/**
	 * Gets the customer id.
	 *
	 * @return the customerId
	 */
	public Integer getCustomerId() {
		return customerId;
	}

	/**
	 * Sets the customer id.
	 *
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	

	/**
	 * Gets the runsheet no.
	 *
	 * @return the runsheetNo
	 */
	public String getRunsheetNo() {
		return runsheetNo;
	}

	/**
	 * Sets the runsheet no.
	 *
	 * @param runsheetNo the runsheetNo to set
	 */
	public void setRunsheetNo(String runsheetNo) {
		this.runsheetNo = runsheetNo;
	}

	/**
	 * Gets the checks if is pickup cn.
	 *
	 * @return the isPickupCN
	 */
	public String getIsPickupCN() {
		return isPickupCN;
	}

	/**
	 * Sets the checks if is pickup cn.
	 *
	 * @param isPickupCN the isPickupCN to set
	 */
	public void setIsPickupCN(String isPickupCN) {
		this.isPickupCN = isPickupCN;
	}

	/**
	 * Gets the consignor id.
	 *
	 * @return the consignorId
	 */
	public Integer getConsignorId() {
		return consignorId;
	}

	/**
	 * Sets the consignor id.
	 *
	 * @param consignorId the consignorId to set
	 */
	public void setConsignorId(Integer consignorId) {
		this.consignorId = consignorId;
	}
	
	/**
	 * Gets the consg manifested id.
	 *
	 * @return the consgManifestedId
	 */
	public Integer getConsgManifestedId() {
		return consgManifestedId;
	}

	/**
	 * Sets the consg manifested id.
	 *
	 * @param consgManifestedId the consgManifestedId to set
	 */
	public void setConsgManifestedId(Integer consgManifestedId) {
		this.consgManifestedId = consgManifestedId;
	}

	/**
	 * @return the comailManifestedId
	 */
	public Integer getComailManifestedId() {
		return comailManifestedId;
	}

	/**
	 * @param comailManifestedId the comailManifestedId to set
	 */
	public void setComailManifestedId(Integer comailManifestedId) {
		this.comailManifestedId = comailManifestedId;
	}
	
	
		public Integer getGridOriginOfficeId() {
		return gridOriginOfficeId;
	}

	public void setGridOriginOfficeId(Integer gridOriginOfficeId) {
		this.gridOriginOfficeId = gridOriginOfficeId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(OutManifestDetailBaseTO arg0) {
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

	public String getComailStatusPrint() {
		return comailStatusPrint;
	}

	public void setComailStatusPrint(String comailStatusPrint) {
		this.comailStatusPrint = comailStatusPrint;
	}

	public Integer getMapEmbeddedManifestId() {
		return mapEmbeddedManifestId;
	}

	public void setMapEmbeddedManifestId(Integer mapEmbeddedManifestId) {
		this.mapEmbeddedManifestId = mapEmbeddedManifestId;
	}

	public Integer getDestOfficeId() {
		return destOfficeId;
	}

	public void setDestOfficeId(Integer destOfficeId) {
		this.destOfficeId = destOfficeId;
	}

	public CreditCustomerBookingConsignmentTO getConsignmentDetailsTO() {
		return consignmentDetailsTO;
	}

	public void setConsignmentDetailsTO(CreditCustomerBookingConsignmentTO consignmentDetailsTO) {
		this.consignmentDetailsTO = consignmentDetailsTO;
	}

	public ComailTO getComailTO() {
		return comailTO;
	}

	public void setComailTO(ComailTO comailTO) {
		this.comailTO = comailTO;
	}

	public String getGridItemType() {
		return gridItemType;
	}

	public void setGridItemType(String gridItemType) {
		this.gridItemType = gridItemType;
	}	
}
