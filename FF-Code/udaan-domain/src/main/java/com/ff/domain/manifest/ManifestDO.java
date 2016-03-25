package com.ff.domain.manifest;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.consignment.ConsignmentDOXDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.organization.OfficeTypeDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.tracking.ProcessDO;

/**
 * The Class ManifestDO.
 *
 * @author narmdr
 */
public class ManifestDO extends CGFactDO {

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
	private ConsignmentTypeDO manifestLoadContent = null;
	
	/** The bag lock no. */
	private String bagLockNo;
	
	/** The manifested product series. */
	private ProductDO manifestedProductSeries;
	
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
	private OfficeDO originOffice;
	
	/** The dest office. */
	private OfficeDO destOffice;
	
	/** The destination city. */
	private CityDO destinationCity;
	
	/** The updating process. */
	private ProcessDO updatingProcess;
	/** The misroute type. */
	private String misrouteTypeCode;
	
	/** The position. */
	private Integer position;

	/** The consignments */
	//@JsonManagedReference
	private Set<ConsignmentDO> consignments = null;
	
	/** The comails */
	private Set<ComailDO> comails = null;
	
	/** The multiple destinations. */
	private Set<OutManifestDestinationDO> multipleDestinations = null;
	
	/** The office Type. */
	private OfficeTypeDO officeType;
	
	/** The manifest mapped embed dtls. */
	@JsonManagedReference
	private Set<ManifestDO>  embeddedManifestDOs = null;
	
	/*@JsonManagedReference
	private Set<ManifestMappedEmbeddedDO>  manifestMappedEmbedDtls = null;*/
	
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
	
	private Set<ConsignmentDOXDO> doxConsignments = null;
	/** The grid Item Position. */
	private String gridItemPosition;
	
	
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

	/**
	 * @return the doxConsignments
	 */
	public Set<ConsignmentDOXDO> getDoxConsignments() {
		return doxConsignments;
	}

	/**
	 * @param doxConsignments the doxConsignments to set
	 */
	public void setDoxConsignments(Set<ConsignmentDOXDO> doxConsignments) {
		this.doxConsignments = doxConsignments;
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
	 * Gets the manifest load content.
	 *
	 * @return the manifest load content
	 */
	public ConsignmentTypeDO getManifestLoadContent() {
		return manifestLoadContent;
	}

	/**
	 * Sets the manifest load content.
	 *
	 * @param manifestLoadContent the new manifest load content
	 */
	public void setManifestLoadContent(ConsignmentTypeDO manifestLoadContent) {
		this.manifestLoadContent = manifestLoadContent;
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
	 * Gets the manifested product series.
	 *
	 * @return the manifested product series
	 */
	public ProductDO getManifestedProductSeries() {
		return manifestedProductSeries;
	}

	/**
	 * Sets the manifested product series.
	 *
	 * @param manifestedProductSeries the new manifested product series
	 */
	public void setManifestedProductSeries(ProductDO manifestedProductSeries) {
		this.manifestedProductSeries = manifestedProductSeries;
	}

	/*
	 * public Integer getOriginOfficeId() { return originOfficeId; }
	 * 
	 * public void setOriginOfficeId(Integer originOfficeId) {
	 * this.originOfficeId = originOfficeId; }
	 * 
	 * public Integer getDestinationOfficeId() { return destinationOfficeId; }
	 * 
	 * public void setDestinationOfficeId(Integer destinationOfficeId) {
	 * this.destinationOfficeId = destinationOfficeId; }
	 */

	/**
	 * Gets the destination city.
	 *
	 * @return the destination city
	 */
	public CityDO getDestinationCity() {
		return destinationCity;
	}

	/**
	 * Sets the destination city.
	 *
	 * @param destinationCity the new destination city
	 */
	public void setDestinationCity(CityDO destinationCity) {
		this.destinationCity = destinationCity;
	}

	/**
	 * Gets the updating process.
	 *
	 * @return the updating process
	 */
	public ProcessDO getUpdatingProcess() {
		return updatingProcess;
	}

	/**
	 * Sets the updating process.
	 *
	 * @param updatingProcess the new updating process
	 */
	public void setUpdatingProcess(ProcessDO updatingProcess) {
		this.updatingProcess = updatingProcess;
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
	 * Gets the origin office.
	 *
	 * @return the origin office
	 */
	public OfficeDO getOriginOffice() {
		return originOffice;
	}

	/**
	 * Sets the origin office.
	 *
	 * @param originOffice the new origin office
	 */
	public void setOriginOffice(OfficeDO originOffice) {
		this.originOffice = originOffice;
	}

	/**
	 * Gets the dest office.
	 *
	 * @return the dest office
	 */
	public OfficeDO getDestOffice() {
		return destOffice;
	}

	/**
	 * Sets the dest office.
	 *
	 * @param destOffice the new dest office
	 */
	public void setDestOffice(OfficeDO destOffice) {
		this.destOffice = destOffice;
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

	

	public OfficeTypeDO getOfficeType() {
		return officeType;
	}

	public void setOfficeType(OfficeTypeDO officeType) {
		this.officeType = officeType;
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

	public Set<ManifestDO> getEmbeddedManifestDOs() {
		return embeddedManifestDOs;
	}

	public void setEmbeddedManifestDOs(Set<ManifestDO> embeddedManifestDOs) {
		this.embeddedManifestDOs = embeddedManifestDOs;
	}
}
