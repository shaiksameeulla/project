package com.ff.domain.manifest;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.consignment.ConsignmentDO;

/**
 * The Class BcunManifestDO.
 */
public class BcunManifestDO extends CGFactDO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The manifest id. */
	private Integer manifestId;
	
	/** The manifest no. */
	private String manifestNo;
	
	/** The manifest weight. */
	private Double manifestWeight;
	
	/** The manifest date. */
	private Date manifestDate;

	/** The no of elements. */
	private Integer noOfElements;
	
	/** The contains only co mail. */
	private String containsOnlyCoMail = CommonConstants.NO;
	
	/** The manifest status. */
	private String manifestStatus;
	
	/** The manifest type. */
	private String manifestType;
	
	/** The manifest embedded in. */
	private Integer manifestEmbeddedIn = null;
	
	/** The bag rfid. */
	private Integer bagRFID;
	
	/** The allow specific content. */
	private String allowSpecificContent;
	
	/** The manifest load content. */
	private Integer manifestLoadContent = null;
	
	/** The bag lock no. */
	private String bagLockNo;
	
	/** The manifested product series. */
	private Integer manifestedProductSeries;
	
	/** The is active. */
	private String isActive;
	
	/** The multiple destination. */
	private String multipleDestination;
	
	/** The manifest process code. */
	private String manifestProcessCode;
	
	/** The received status. */
	private String receivedStatus;
	
	/** The remarks. */
	private String remarks;

	/** The origin office. */
	private Integer originOffice;
	
	/** The dest office. */
	private Integer destOffice;
	
	/** The destination city. */
	private Integer destinationCity;
	
	/** The updating process. */
	private Integer updatingProcess;
	/** The misroute type. */
	private String misrouteTypeCode;
	
	/** The position. */
	private Integer position;

	/** The manifest consg dtls. */
	private Set<ConsignmentDO> consignments = null;
	
	/** The manifest comail dtls. */
	private Set<ComailDO> comails = null;
	
	/** The multiple destinations. */
	private Set<OutManifestDestinationDO> multipleDestinations = null;
	
	/** The office Type. */
	private Integer officeType;
	
	private Set<BcunManifestDO>  embeddedManifestDOs = null;
	
	
	//Common fields 
	private Integer operatingOffice;
	

	//Newly Added fields
	/** The manifest direction. */
	private String manifestDirection;
	/** The manifest open type. */
	private String manifestOpenType;
	/** The bpl manifest type. */
	private String bplManifestType;//PURE or TRANSIENT
	/** The load lot id. */
	private Integer loadLotId;
	/** The third party type. */
	private String thirdPartyType; 
	/** The vendor id. */
	private Integer vendorId; 
	
	/** The grid Item Position. */
	private String gridItemPosition;
	
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
	 * Gets the manifest type.
	 *
	 * @return the manifest type
	 */
	public String getManifestType() {
		return manifestType;
	}

	/**
	 * Sets the manifest type.
	 *
	 * @param manifestType the new manifest type
	 */
	public void setManifestType(String manifestType) {
		this.manifestType = manifestType;
	}

	/**
	 * Gets the manifest embedded in.
	 *
	 * @return the manifest embedded in
	 */
	public Integer getManifestEmbeddedIn() {
		return manifestEmbeddedIn;
	}

	/**
	 * Sets the manifest embedded in.
	 *
	 * @param manifestEmbeddedIn the new manifest embedded in
	 */
	public void setManifestEmbeddedIn(Integer manifestEmbeddedIn) {
		this.manifestEmbeddedIn = manifestEmbeddedIn;
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
	 * Gets the allow specific content.
	 *
	 * @return the allow specific content
	 */
	public String getAllowSpecificContent() {
		return allowSpecificContent;
	}

	/**
	 * Sets the allow specific content.
	 *
	 * @param allowSpecificContent the new allow specific content
	 */
	public void setAllowSpecificContent(String allowSpecificContent) {
		this.allowSpecificContent = allowSpecificContent;
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
	 * Gets the checks if is active.
	 *
	 * @return the checks if is active
	 */
	public String getIsActive() {
		return isActive;
	}

	/**
	 * Sets the checks if is active.
	 *
	 * @param isActive the new checks if is active
	 */
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	

	/**
	 * Gets the multiple destinations.
	 *
	 * @return the multiple destinations
	 */
	public Set<OutManifestDestinationDO> getMultipleDestinations() {
		return multipleDestinations;
	}

	/**
	 * Sets the multiple destinations.
	 *
	 * @param multipleDestinations the new multiple destinations
	 */
	public void setMultipleDestinations(
			Set<OutManifestDestinationDO> multipleDestinations) {
		this.multipleDestinations = multipleDestinations;
	}

	/**
	 * Gets the received status.
	 *
	 * @return the receivedStatus
	 */
	public String getReceivedStatus() {
		return receivedStatus;
	}

	/**
	 * Sets the received status.
	 *
	 * @param receivedStatus the receivedStatus to set
	 */
	public void setReceivedStatus(String receivedStatus) {
		this.receivedStatus = receivedStatus;
	}

	/**
	 * Gets the remarks.
	 *
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * Sets the remarks.
	 *
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return the misrouteTypeCode
	 */
	public String getMisrouteTypeCode() {
		return misrouteTypeCode;
	}

	/**
	 * @param misrouteTypeCode the misrouteTypeCode to set
	 */
	public void setMisrouteTypeCode(String misrouteTypeCode) {
		this.misrouteTypeCode = misrouteTypeCode;
	}

	public String getManifestDirection() {
		return manifestDirection;
	}

	public void setManifestDirection(String manifestDirection) {
		this.manifestDirection = manifestDirection;
	}

	public String getManifestOpenType() {
		return manifestOpenType;
	}

	public void setManifestOpenType(String manifestOpenType) {
		this.manifestOpenType = manifestOpenType;
	}

	public String getBplManifestType() {
		return bplManifestType;
	}

	public void setBplManifestType(String bplManifestType) {
		this.bplManifestType = bplManifestType;
	}

	public Integer getLoadLotId() {
		return loadLotId;
	}

	public void setLoadLotId(Integer loadLotId) {
		this.loadLotId = loadLotId;
	}

	public String getThirdPartyType() {
		return thirdPartyType;
	}

	public void setThirdPartyType(String thirdPartyType) {
		this.thirdPartyType = thirdPartyType;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	

	public Integer getOperatingOffice() {
		return operatingOffice;
	}

	public void setOperatingOffice(Integer operatingOffice) {
		this.operatingOffice = operatingOffice;
	}

	/**
	 * @return the manifestLoadContent
	 */
	public Integer getManifestLoadContent() {
		return manifestLoadContent;
	}

	/**
	 * @param manifestLoadContent the manifestLoadContent to set
	 */
	public void setManifestLoadContent(Integer manifestLoadContent) {
		this.manifestLoadContent = manifestLoadContent;
	}

	/**
	 * @return the manifestedProductSeries
	 */
	public Integer getManifestedProductSeries() {
		return manifestedProductSeries;
	}

	/**
	 * @param manifestedProductSeries the manifestedProductSeries to set
	 */
	public void setManifestedProductSeries(Integer manifestedProductSeries) {
		this.manifestedProductSeries = manifestedProductSeries;
	}

	/**
	 * @return the originOffice
	 */
	public Integer getOriginOffice() {
		return originOffice;
	}

	/**
	 * @param originOffice the originOffice to set
	 */
	public void setOriginOffice(Integer originOffice) {
		this.originOffice = originOffice;
	}

	/**
	 * @return the destOffice
	 */
	public Integer getDestOffice() {
		return destOffice;
	}

	/**
	 * @param destOffice the destOffice to set
	 */
	public void setDestOffice(Integer destOffice) {
		this.destOffice = destOffice;
	}

	/**
	 * @return the destinationCity
	 */
	public Integer getDestinationCity() {
		return destinationCity;
	}

	/**
	 * @param destinationCity the destinationCity to set
	 */
	public void setDestinationCity(Integer destinationCity) {
		this.destinationCity = destinationCity;
	}

	/**
	 * @return the updatingProcess
	 */
	public Integer getUpdatingProcess() {
		return updatingProcess;
	}

	/**
	 * @param updatingProcess the updatingProcess to set
	 */
	public void setUpdatingProcess(Integer updatingProcess) {
		this.updatingProcess = updatingProcess;
	}

	/**
	 * @return the officeType
	 */
	public Integer getOfficeType() {
		return officeType;
	}

	/**
	 * @param officeType the officeType to set
	 */
	public void setOfficeType(Integer officeType) {
		this.officeType = officeType;
	}

	/**
	 * @return the consignments
	 */
	public Set<ConsignmentDO> getConsignments() {
		return consignments;
	}

	/**
	 * @param consignments the consignments to set
	 */
	public void setConsignments(Set<ConsignmentDO> consignments) {
		this.consignments = consignments;
	}

	/**
	 * @return the comails
	 */
	public Set<ComailDO> getComails() {
		return comails;
	}

	/**
	 * @param comails the comails to set
	 */
	public void setComails(Set<ComailDO> comails) {
		this.comails = comails;
	}

	/**
	 * @return the embeddedManifestDOs
	 */
	public Set<BcunManifestDO> getEmbeddedManifestDOs() {
		return embeddedManifestDOs;
	}

	/**
	 * @param embeddedManifestDOs the embeddedManifestDOs to set
	 */
	public void setEmbeddedManifestDOs(Set<BcunManifestDO> embeddedManifestDOs) {
		this.embeddedManifestDOs = embeddedManifestDOs;
	}


	/**
	 * @return the gridItemPosition
	 */
	public String getGridItemPosition() {
		return gridItemPosition;
	}

	/**
	 * @param gridItemPosition the gridItemPosition to set
	 */
	public void setGridItemPosition(String gridItemPosition) {
		this.gridItemPosition = gridItemPosition;
	}
	
}
