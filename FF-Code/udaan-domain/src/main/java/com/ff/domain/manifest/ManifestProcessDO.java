package com.ff.domain.manifest;



import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;

// TODO: Auto-generated Javadoc
/**
 * The Class ManifestProcessDO.
 */
public class ManifestProcessDO extends CGFactDO{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4005155760310636661L;

	/** The manifest process id. */
	private Integer manifestProcessId;
	
	private Integer destCityId;
	
	/** The manifest process no. */
	private String manifestProcessNo;
	
	/** The manifest process code. */
	private String manifestProcessCode;
	
	/** The manifest direction. */
	private String manifestDirection;
	
	/** The manifest no. */
	private String manifestNo;
	
	/** The manifest open type. */
	private String manifestOpenType;
	
	/** The manifest date. */
	private Date manifestDate;
	
	/** The manifest weight. */
	private Double manifestWeight;
	
	/** The multiple destination. */
	private String multipleDestination="N";
	
	/** The no of elements. */
	private Integer noOfElements;
	
	/** The contains only co mail. */
	private String containsOnlyCoMail;
	
	/** The manifest status. */
	private String manifestStatus;
	
	/** The bag rfid. */
	private Integer bagRFID;
	
	/** The bpl manifest type. */
	private String bplManifestType;//PURE or TRANSIENT
	
	/** The bag lock no. */
	private String bagLockNo;
	
	/** The load lot id. */
	private Integer loadLotId;                                                   
	
	/** The third party type. */
	private String thirdPartyType;                                                       
	
	/** The ba id. */
/*	private Integer baId;                                                                                       
	
	*//** The franchisee id. *//*
	private Integer franchiseeId;   */                                                                            
	
	/** The vendor id. */
	private Integer vendorId;  
	
	/**
	 * Gets the manifest process id.
	 *
	 * @return the manifest process id
	 */
	public Integer getManifestProcessId() {
		return manifestProcessId;
	}
	
	/**
	 * Sets the manifest process id.
	 *
	 * @param manifestProcessId the new manifest process id
	 */
	public void setManifestProcessId(Integer manifestProcessId) {
		this.manifestProcessId = manifestProcessId;
	}
	
	/**
	 * Gets the manifest process no.
	 *
	 * @return the manifest process no
	 */
	public String getManifestProcessNo() {
		return manifestProcessNo;
	}
	
	/**
	 * Sets the manifest process no.
	 *
	 * @param manifestProcessNo the new manifest process no
	 */
	public void setManifestProcessNo(String manifestProcessNo) {
		this.manifestProcessNo = manifestProcessNo;
	}
	
	/**
	 * Gets the manifest process code.
	 *
	 * @return the manifest process code
	 */
	public String getManifestProcessCode() {
		return manifestProcessCode;
	}
	
	/**
	 * Sets the manifest process code.
	 *
	 * @param manifestProcessCode the new manifest process code
	 */
	public void setManifestProcessCode(String manifestProcessCode) {
		this.manifestProcessCode = manifestProcessCode;
	}
	
	/**
	 * Gets the manifest direction.
	 *
	 * @return the manifest direction
	 */
	public String getManifestDirection() {
		return manifestDirection;
	}
	
	/**
	 * Sets the manifest direction.
	 *
	 * @param manifestDirection the new manifest direction
	 */
	public void setManifestDirection(String manifestDirection) {
		this.manifestDirection = manifestDirection;
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
	 * Gets the manifest open type.
	 *
	 * @return the manifest open type
	 */
	public String getManifestOpenType() {
		return manifestOpenType;
	}
	
	/**
	 * Sets the manifest open type.
	 *
	 * @param manifestOpenType the new manifest open type
	 */
	public void setManifestOpenType(String manifestOpenType) {
		this.manifestOpenType = manifestOpenType;
	}
	
	/**
	 * Gets the manifest date.
	 *
	 * @return the manifest date
	 */
	public Date getManifestDate() {
		return manifestDate;
	}
	
	/**
	 * Sets the manifest date.
	 *
	 * @param manifestDate the new manifest date
	 */
	public void setManifestDate(Date manifestDate) {
		this.manifestDate = manifestDate;
	}
	
	/**
	 * Gets the manifest weight.
	 *
	 * @return the manifest weight
	 */
	public Double getManifestWeight() {
		return manifestWeight;
	}
	
	/**
	 * Sets the manifest weight.
	 *
	 * @param manifestWeight the new manifest weight
	 */
	public void setManifestWeight(Double manifestWeight) {
		this.manifestWeight = manifestWeight;
	}
	
	/**
	 * Gets the multiple destination.
	 *
	 * @return the multiple destination
	 */
	public String getMultipleDestination() {
		return multipleDestination;
	}
	
	/**
	 * Sets the multiple destination.
	 *
	 * @param multipleDestination the new multiple destination
	 */
	public void setMultipleDestination(String multipleDestination) {
		this.multipleDestination = multipleDestination;
	}
	
	/**
	 * Gets the no of elements.
	 *
	 * @return the no of elements
	 */
	public Integer getNoOfElements() {
		return noOfElements;
	}
	
	/**
	 * Sets the no of elements.
	 *
	 * @param noOfElements the new no of elements
	 */
	public void setNoOfElements(Integer noOfElements) {
		this.noOfElements = noOfElements;
	}
	
	/**
	 * Gets the contains only co mail.
	 *
	 * @return the contains only co mail
	 */
	public String getContainsOnlyCoMail() {
		return containsOnlyCoMail;
	}
	
	/**
	 * Sets the contains only co mail.
	 *
	 * @param containsOnlyCoMail the new contains only co mail
	 */
	public void setContainsOnlyCoMail(String containsOnlyCoMail) {
		this.containsOnlyCoMail = containsOnlyCoMail;
	}
	
	/**
	 * Gets the manifest status.
	 *
	 * @return the manifest status
	 */
	public String getManifestStatus() {
		return manifestStatus;
	}
	
	/**
	 * Sets the manifest status.
	 *
	 * @param manifestStatus the new manifest status
	 */
	public void setManifestStatus(String manifestStatus) {
		this.manifestStatus = manifestStatus;
	}
	
	/**
	 * Gets the bag rfid.
	 *
	 * @return the bag rfid
	 */
	public Integer getBagRFID() {
		return bagRFID;
	}
	
	/**
	 * Sets the bag rfid.
	 *
	 * @param bagRFID the new bag rfid
	 */
	public void setBagRFID(Integer bagRFID) {
		this.bagRFID = bagRFID;
	}
	
	/**
	 * Gets the bpl manifest type.
	 *
	 * @return the bpl manifest type
	 */
	public String getBplManifestType() {
		return bplManifestType;
	}
	
	/**
	 * Sets the bpl manifest type.
	 *
	 * @param bplManifestType the new bpl manifest type
	 */
	public void setBplManifestType(String bplManifestType) {
		this.bplManifestType = bplManifestType;
	}
	
	/**
	 * Gets the bag lock no.
	 *
	 * @return the bag lock no
	 */
	public String getBagLockNo() {
		return bagLockNo;
	}
	
	/**
	 * Sets the bag lock no.
	 *
	 * @param bagLockNo the new bag lock no
	 */
	public void setBagLockNo(String bagLockNo) {
		this.bagLockNo = bagLockNo;
	}
	
	/**
	 * Gets the load lot id.
	 *
	 * @return the load lot id
	 */
	public Integer getLoadLotId() {
		return loadLotId;
	}
	
	/**
	 * Sets the load lot id.
	 *
	 * @param loadLotId the new load lot id
	 */
	public void setLoadLotId(Integer loadLotId) {
		this.loadLotId = loadLotId;
	}
	
	/**
	 * Gets the third party type.
	 *
	 * @return the third party type
	 */
	public String getThirdPartyType() {
		return thirdPartyType;
	}
	
	/**
	 * Sets the third party type.
	 *
	 * @param thirdPartyType the new third party type
	 */
	public void setThirdPartyType(String thirdPartyType) {
		this.thirdPartyType = thirdPartyType;
	}
	
	
	
	/**
	 * Gets the vendor id.
	 *
	 * @return the vendor id
	 */
	public Integer getVendorId() {
		return vendorId;
	}
	
	/**
	 * Sets the vendor id.
	 *
	 * @param vendorId the new vendor id
	 */
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Integer getDestCityId() {
		return destCityId;
	}

	public void setDestCityId(Integer destCityId) {
		this.destCityId = destCityId;
	}
	
	
	
}
