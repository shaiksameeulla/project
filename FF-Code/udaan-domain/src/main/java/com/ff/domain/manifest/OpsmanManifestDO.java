package com.ff.domain.manifest;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.consignment.OpsmanConsignmentDO;

public class OpsmanManifestDO extends CGFactDO {

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
	
	/** The allow specific content. */
	private String allowSpecificContent;
	
	/** The manifest load content. */
	private Integer manifestLoadContent = null;
	
	/** The bag lock no. */
	private String bagLockNo;
	
	/** The manifested product series. */
	private Integer manifestedProductSeries;
	
	/** The multiple destination. */
	private String multipleDestination;
	
	/** The manifest process code. */
	private String manifestProcessCode;
	
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

	/** The manifest consg dtls. */
	private Set<OpsmanConsignmentDO> consignments = null;
	
	/** The office Type. */
	private Integer officeType;
	
	private Set<OpsmanManifestDO>  embeddedManifestDOs = null;
	
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

	
	public Integer getManifestId() {
		return manifestId;
	}
	public void setManifestId(Integer manifestId) {
		this.manifestId = manifestId;
	}
	public String getManifestNo() {
		return manifestNo;
	}
	public void setManifestNo(String manifestNo) {
		this.manifestNo = manifestNo;
	}
	public Double getManifestWeight() {
		return manifestWeight;
	}
	public void setManifestWeight(Double manifestWeight) {
		this.manifestWeight = manifestWeight;
	}
	public Date getManifestDate() {
		return manifestDate;
	}
	public void setManifestDate(Date manifestDate) {
		this.manifestDate = manifestDate;
	}
	public Integer getNoOfElements() {
		return noOfElements;
	}
	public void setNoOfElements(Integer noOfElements) {
		this.noOfElements = noOfElements;
	}
	public String getContainsOnlyCoMail() {
		return containsOnlyCoMail;
	}
	public void setContainsOnlyCoMail(String containsOnlyCoMail) {
		this.containsOnlyCoMail = containsOnlyCoMail;
	}
	public String getManifestStatus() {
		return manifestStatus;
	}
	public void setManifestStatus(String manifestStatus) {
		this.manifestStatus = manifestStatus;
	}
	public String getManifestType() {
		return manifestType;
	}
	public void setManifestType(String manifestType) {
		this.manifestType = manifestType;
	}
	public Integer getManifestEmbeddedIn() {
		return manifestEmbeddedIn;
	}
	public void setManifestEmbeddedIn(Integer manifestEmbeddedIn) {
		this.manifestEmbeddedIn = manifestEmbeddedIn;
	}
	public String getAllowSpecificContent() {
		return allowSpecificContent;
	}
	public void setAllowSpecificContent(String allowSpecificContent) {
		this.allowSpecificContent = allowSpecificContent;
	}
	public Integer getManifestLoadContent() {
		return manifestLoadContent;
	}
	public void setManifestLoadContent(Integer manifestLoadContent) {
		this.manifestLoadContent = manifestLoadContent;
	}
	public String getBagLockNo() {
		return bagLockNo;
	}
	public void setBagLockNo(String bagLockNo) {
		this.bagLockNo = bagLockNo;
	}
	public Integer getManifestedProductSeries() {
		return manifestedProductSeries;
	}
	public void setManifestedProductSeries(Integer manifestedProductSeries) {
		this.manifestedProductSeries = manifestedProductSeries;
	}
	public String getMultipleDestination() {
		return multipleDestination;
	}
	public void setMultipleDestination(String multipleDestination) {
		this.multipleDestination = multipleDestination;
	}
	public String getManifestProcessCode() {
		return manifestProcessCode;
	}
	public void setManifestProcessCode(String manifestProcessCode) {
		this.manifestProcessCode = manifestProcessCode;
	}
	public Integer getOriginOffice() {
		return originOffice;
	}
	public void setOriginOffice(Integer originOffice) {
		this.originOffice = originOffice;
	}
	public Integer getDestOffice() {
		return destOffice;
	}
	public void setDestOffice(Integer destOffice) {
		this.destOffice = destOffice;
	}
	public Integer getDestinationCity() {
		return destinationCity;
	}
	public void setDestinationCity(Integer destinationCity) {
		this.destinationCity = destinationCity;
	}
	public Integer getUpdatingProcess() {
		return updatingProcess;
	}
	public void setUpdatingProcess(Integer updatingProcess) {
		this.updatingProcess = updatingProcess;
	}
	public String getMisrouteTypeCode() {
		return misrouteTypeCode;
	}
	public void setMisrouteTypeCode(String misrouteTypeCode) {
		this.misrouteTypeCode = misrouteTypeCode;
	}
	public Set<OpsmanConsignmentDO> getConsignments() {
		return consignments;
	}
	public void setConsignments(Set<OpsmanConsignmentDO> consignments) {
		this.consignments = consignments;
	}
	public Integer getOfficeType() {
		return officeType;
	}
	public void setOfficeType(Integer officeType) {
		this.officeType = officeType;
	}
	public Set<OpsmanManifestDO> getEmbeddedManifestDOs() {
		return embeddedManifestDOs;
	}
	public void setEmbeddedManifestDOs(Set<OpsmanManifestDO> embeddedManifestDOs) {
		this.embeddedManifestDOs = embeddedManifestDOs;
	}
	public Integer getOperatingOffice() {
		return operatingOffice;
	}
	public void setOperatingOffice(Integer operatingOffice) {
		this.operatingOffice = operatingOffice;
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
	
}
