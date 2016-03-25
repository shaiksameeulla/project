/*
 * 
 */
package com.ff.manifest;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;

/**
 * The Class OutManifestBaseTO.
 */
public class OutManifestBaseTO extends CGBaseTO {
	// common header attributes
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3117395715734043771L;

	/** The manifest id. */
	private Integer manifestId;

	/** The manifest date. */
	private String manifestDate;

	/** The manifest no. */
	private String manifestNo;

	/** The login office id. */
	private Integer loginOfficeId;

	/** The login office name. */
	private String loginOfficeName;

	/** The login office type. */
	private String loginOfficeType;

	/** The login city code. */
	private String loginCityCode;

	/** The login city id. */
	private Integer loginCityId;

	/** The login rep hub. */
	private Integer loginRepHub;

	/** The rep hub office id. */
	private Integer repHubOfficeId;
	/** The destination region id. */
	private Integer destinationRegionId;

	/** The destination city id. */
	private Integer destinationCityId;

	/** The destination office id. */
	private Integer destinationOfficeId;

	/** The destination office name. */
	private String destinationOfficeName;

	/** The office type id. */
	private Integer officeTypeId;

	/** The final weight. */
	private Double finalWeight;

	/** The manifest status. */
	private String manifestStatus = "O";

	/** The process code. */
	private String processCode;

	/** The process no. */
	private String processNo;

	/** The process id. */
	private Integer processId;

	/** The region id. */
	private Integer regionId;

	/** The product. */
	private ProductTO product;

	// for storing the list of id in grid on check
	/** The manifest id list at grid. */
	private String manifestIdListAtGrid;

	/** The consg id list at grid. */
	private String consgIdListAtGrid;

	/** The comail id list at grid. */
	private String comailIdListAtGrid;

	// For Validate Manifest No Format
	/** The series type. */
	private String seriesType;

	/** The stock no. */
	private String stockNo;

	/** The is active. */
	private String isActive;

	/** The bpl dest office city id. */
	private Integer bplDestOfficeCityId;

	// possible values : 'Y'/'N'
	// Created for if the destination office selected as 'All' then value will
	// be 'Y' otherwise 'N'
	/** The is manifest embedded. */
	private boolean isManifestEmbedded = Boolean.FALSE;

	/** The is mul destination. */
	private String isMulDestination = "N";

	/** The multi destinations. */
	private String multiDestinations;

	/** The destination region list. */
	private List<RegionTO> destinationRegionList;

	/** The destination city list. */
	private List<CityTO> destinationCityList;

	/** The destination office list. */
	private List<OfficeTO> destinationOfficeList;

	/** The rfid no. */
	private String rfidNo;
	
	private String receivedStatus;
	

	
	//added for 2WayWrite
	private Integer manifestProcessId;
	private Integer twoWayManifestId;
	

	// specific to UI
	/** The row count. */
	protected int rowCount;

	/** The consg nos. */
	private String[] consgNos = new String[rowCount];

	/** The consg manifested ids. */
	private Integer[] consgManifestedIds = new Integer[rowCount];

	private Integer[] comailManifestedIds = new Integer[rowCount];

	/** The consg ids. */
	private Integer[] consgIds = new Integer[rowCount];
	// start - Pickup integration
	/** The booking ids. */
	private Integer[] bookingIds = new Integer[rowCount];

	/** The booking type ids. */
	private Integer[] bookingTypeIds = new Integer[rowCount];

	/** The customer ids. */
	private Integer[] customerIds = new Integer[rowCount];

	/** The runsheet nos. */
	private String[] runsheetNos = new String[rowCount];

	/** The consignor ids. */
	private Integer[] consignorIds = new Integer[rowCount];
	// end - Pickup integration
	/** The product ids. */
	private Integer[] productIds = new Integer[rowCount];

	/** The comail no. */
	private String[] comailNos = new String[rowCount];

	/** The comail id. */
	private Integer[] comailIds = new Integer[rowCount];

	/** The is c ns. */
	private String[] isCNs = new String[rowCount];

	/** The manifest nos. */
	private String[] manifestNos = new String[rowCount];

	/** The manifest ids. */
	private Integer[] manifestIds = new Integer[rowCount];

	/** The pincodes. */
	private String[] pincodes = new String[rowCount];

	/** The pincode ids. */
	private Integer[] pincodeIds = new Integer[rowCount];

	/** The dest citys. */
	private String[] destCitys = new String[rowCount];

	/** The dest city ids. */
	private Integer[] destCityIds = new Integer[rowCount];

	/** The weights. */
	private Double[] weights = new Double[rowCount];

	/** The checkbox. */
	private Integer[] checkbox = new Integer[rowCount];

	/** During save the position of row in grid. */
	private Integer[] position = new Integer[rowCount];
	/** The is cn processed from pickup. */
	private String[] isCNProcessedFromPickup = new String[rowCount];
	
	private Integer[] manifestMappedEmbeddeId = new Integer[rowCount];
	
	// Individual TOs
	/** The destination city to. */
	private CityTO destinationCityTO;

	/** The destination office to. */
	private OfficeTO destinationOfficeTO;

	/** The dest region to. */
	private RegionTO destRegionTO;
	// Manifest Process
	/** The manifest process to. */
	private ManifestProcessTO manifestProcessTo = new ManifestProcessTO();

	// Manifest Details
	/** The manifest type. */
	private String manifestType;

	/** The manifest direction. */
	private String manifestDirection;

	/** The consignment type to. */
	private ConsignmentTypeTO consignmentTypeTO;

	// For Manifest Process
	/** The bag lock no. */
	private String bagLockNo;

	/** The bag rfid. */
	private Integer bagRFID;

	/** The load no id. */
	private Integer loadNoId;

	/** The configurable params. */
	private Map<String, String> configurableParams;

	/** The max c ns allowed. */
	private String maxCNsAllowed;

	/** The max comails allowed. */
	private String maxComailsAllowed;

	/** The max weight allowed. */
	private String maxWeightAllowed;

	/** The max tolerence allowed. */
	private String maxTolerenceAllowed;

	/** The max manifest allowed. */
	private String maxManifestAllowed;

	/** The office code. */
	private String officeCode;
	
	private Integer[] gridOriginOfficeId=new Integer[rowCount];
	
	/** Allowed Consg. Manifested Type i.e. "B"(BMS) or "R"(RTO) or O(Origin misroute)*/
	private String allowedConsgManifestedType;
	
	/** Allowed  Manifest Type i.e. "B"(BMS) or "R"(RTO) or O(Origin misroute)*/
	private String allowedManifestType;
	
	/** The old weights. */
	private Double[] oldWeights = new Double[rowCount];
	// noOfElements = contains no of items added in the manifest.
	private Integer noOfElements;
	
	private String loginOfficeAddress1;
	private String loginOfficeAddress2;
	private String loginOfficeAddress3;
	private String loginOfficePincode;

	//Added by shahnsha: out manifest destination ids#Office Id with Hash delimitted string
	private String outMnfstDestIds;
	
	private Integer createdBy ;
	private Integer updatedBy;
	private Date createdDate ;
	private Date updatedDate ;
	
	private Integer operatingLevel;
	
	//Newly added fields for BCUN purpose - Third Party
	private String thirdPartyName;
	private Integer vendorId;
	private String thirdPartyType;
	private List<Integer> originHubOffList;
	private List<Integer> destHubOffList;
	
	private String action;
	private String successMessage;
	
	/** The status for CN: N-Normal or P-Pickup for UI purpose 
	 *  The Status for CoMail: C-Created
	 */
	private String status[] = new String[rowCount];
	
	/** The Calculated Rate for consignments i.e. [ B991Q1234567, < Rate Components > ] */
	Map<String, ConsignmentRateCalculationOutputTO> rateCompnents;

	/**
	 * The isWMConnected - To check whether weighing machine is connected to
	 * requested machine or not. (Y or N) Added by Himal
	 */
	private String isWMConnected;

	/**
	 * @return the isWMConnected
	 */
	public String getIsWMConnected() {
		return isWMConnected;
	}

	/**
	 * @param isWMConnected
	 *            the isWMConnected to set
	 */
	public void setIsWMConnected(String isWMConnected) {
		this.isWMConnected = isWMConnected;
	}

	/**
	 * @return the rateCompnents
	 */
	public Map<String, ConsignmentRateCalculationOutputTO> getRateCompnents() {
		return rateCompnents;
	}

	/**
	 * @param rateCompnents the rateCompnents to set
	 */
	public void setRateCompnents(
			Map<String, ConsignmentRateCalculationOutputTO> rateCompnents) {
		this.rateCompnents = rateCompnents;
	}

	/**
	 * @return the status
	 */
	public String[] getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String[] status) {
		this.status = status;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	/**
	 * @return the vendorId
	 */
	public Integer getVendorId() {
		return vendorId;
	}

	/**
	 * @param vendorId the vendorId to set
	 */
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	/**
	 * @return the thirdPartyName
	 */
	public String getThirdPartyName() {
		return thirdPartyName;
	}

	/**
	 * @param thirdPartyName the thirdPartyName to set
	 */
	public void setThirdPartyName(String thirdPartyName) {
		this.thirdPartyName = thirdPartyName;
	}

	/**
	 * @return the thirdPartyType
	 */
	public String getThirdPartyType() {
		return thirdPartyType;
	}

	/**
	 * @param thirdPartyType the thirdPartyType to set
	 */
	public void setThirdPartyType(String thirdPartyType) {
		this.thirdPartyType = thirdPartyType;
	}

	/**
	 * @return the oldWeights
	 */
	public Double[] getOldWeights() {
		return oldWeights;
	}

	/**
	 * @param oldWeights the oldWeights to set
	 */
	public void setOldWeights(Double[] oldWeights) {
		this.oldWeights = oldWeights;
	}

	/**
	 * To get allowed consg manifested type.
	 * 
	 * @return the allowedConsgManifestedType
	 */
	public String getAllowedConsgManifestedType() {
		return allowedConsgManifestedType;
	}

	/**
	 * To set allowed consg manifested type.
	 * 
	 * @param allowedConsgManifestedType
	 *            the allowedConsgManifestedType to set
	 */
	public void setAllowedConsgManifestedType(String allowedConsgManifestedType) {
		this.allowedConsgManifestedType = allowedConsgManifestedType;
	}

	/**
	 * Gets the comail no.
	 * 
	 * @return the comail no
	 */

	public String[] getComailNos() {
		return comailNos;
	}

	/**
	 * Sets the comail no.
	 * 
	 * @param comailNos
	 *            the new comail nos
	 */
	public void setComailNos(String[] comailNos) {
		this.comailNos = comailNos;
	}

	/**
	 * Gets the region code.
	 * 
	 * @return the region code
	 */
	public String getRegionCode() {
		return regionCode;
	}

	/**
	 * Sets the region code.
	 * 
	 * @param regionCode
	 *            the new region code
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	/** The region code. */
	private String regionCode;

	/**
	 * Gets the office code.
	 * 
	 * @return officeCode
	 * @desc get OfficeCode
	 */
	public String getOfficeCode() {
		return officeCode;
	}

	/**
	 * Sets the office code.
	 * 
	 * @param officeCode
	 *            the new office code
	 * @desc set OfficeCode
	 */
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	/**
	 * Get the position.
	 * 
	 * @return the position
	 */
	public Integer[] getPosition() {
		return position;
	}

	/**
	 * Set the position.
	 * 
	 * @param position
	 *            the position to set
	 */
	public void setPosition(Integer[] position) {
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
	 * @param manifestId
	 *            the new manifest id
	 */
	public void setManifestId(Integer manifestId) {
		this.manifestId = manifestId;
	}

	/**
	 * Gets the manifest date.
	 * 
	 * @return the manifest date
	 */
	public String getManifestDate() {
		return manifestDate;
	}

	/**
	 * Sets the manifest date.
	 * 
	 * @param manifestDate
	 *            the new manifest date
	 */
	public void setManifestDate(String manifestDate) {
		this.manifestDate = manifestDate;
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
	 * @param manifestNo
	 *            the new manifest no
	 */
	public void setManifestNo(String manifestNo) {
		this.manifestNo = manifestNo;
	}

	/**
	 * Gets the login office id.
	 * 
	 * @return the login office id
	 */
	public Integer getLoginOfficeId() {
		return loginOfficeId;
	}

	/**
	 * Sets the login office id.
	 * 
	 * @param loginOfficeId
	 *            the new login office id
	 */
	public void setLoginOfficeId(Integer loginOfficeId) {
		this.loginOfficeId = loginOfficeId;
	}

	/**
	 * Gets the login office name.
	 * 
	 * @return the login office name
	 */
	public String getLoginOfficeName() {
		return loginOfficeName;
	}

	/**
	 * Sets the login office name.
	 * 
	 * @param loginOfficeName
	 *            the new login office name
	 */
	public void setLoginOfficeName(String loginOfficeName) {
		this.loginOfficeName = loginOfficeName;
	}

	/**
	 * Gets the login office type.
	 * 
	 * @return the login office type
	 */
	public String getLoginOfficeType() {
		return loginOfficeType;
	}

	/**
	 * Sets the login office type.
	 * 
	 * @param loginOfficeType
	 *            the new login office type
	 */
	public void setLoginOfficeType(String loginOfficeType) {
		this.loginOfficeType = loginOfficeType;
	}

	/**
	 * Gets the login city code.
	 * 
	 * @return the login city code
	 */
	public String getLoginCityCode() {
		return loginCityCode;
	}

	/**
	 * Sets the login city code.
	 * 
	 * @param loginCityCode
	 *            the new login city code
	 */
	public void setLoginCityCode(String loginCityCode) {
		this.loginCityCode = loginCityCode;
	}

	/**
	 * Gets the login city id.
	 * 
	 * @return the loginCityId
	 */
	public Integer getLoginCityId() {
		return loginCityId;
	}

	/**
	 * Sets the login city id.
	 * 
	 * @param loginCityId
	 *            the loginCityId to set
	 */
	public void setLoginCityId(Integer loginCityId) {
		this.loginCityId = loginCityId;
	}

	/**
	 * Gets the login rep hub.
	 * 
	 * @return the loginRepHub
	 */
	public Integer getLoginRepHub() {
		return loginRepHub;
	}

	/**
	 * Sets the login rep hub.
	 * 
	 * @param loginRepHub
	 *            the loginRepHub to set
	 */
	public void setLoginRepHub(Integer loginRepHub) {
		this.loginRepHub = loginRepHub;
	}

	/**
	 * Gets the destination region id.
	 * 
	 * @return the destination region id
	 */
	public Integer getDestinationRegionId() {
		return destinationRegionId;
	}

	/**
	 * Sets the destination region id.
	 * 
	 * @param destinationRegionId
	 *            the new destination region id
	 */
	public void setDestinationRegionId(Integer destinationRegionId) {
		this.destinationRegionId = destinationRegionId;
	}

	/**
	 * Gets the destination city id.
	 * 
	 * @return the destination city id
	 */
	public Integer getDestinationCityId() {
		return destinationCityId;
	}

	/**
	 * Sets the destination city id.
	 * 
	 * @param destinationCityId
	 *            the new destination city id
	 */
	public void setDestinationCityId(Integer destinationCityId) {
		this.destinationCityId = destinationCityId;
	}

	/**
	 * Gets the destination office id.
	 * 
	 * @return the destination office id
	 */
	public Integer getDestinationOfficeId() {
		return destinationOfficeId;
	}

	/**
	 * Sets the destination office id.
	 * 
	 * @param destinationOfficeId
	 *            the new destination office id
	 */
	public void setDestinationOfficeId(Integer destinationOfficeId) {
		this.destinationOfficeId = destinationOfficeId;
	}

	/**
	 * Gets the destination office name.
	 * 
	 * @return the destination office name
	 */
	public String getDestinationOfficeName() {
		return destinationOfficeName;
	}

	/**
	 * Sets the destination office name.
	 * 
	 * @param destinationOfficeName
	 *            the new destination office name
	 */
	public void setDestinationOfficeName(String destinationOfficeName) {
		this.destinationOfficeName = destinationOfficeName;
	}

	/**
	 * Gets the final weight.
	 * 
	 * @return the final weight
	 */
	public Double getFinalWeight() {
		return finalWeight;
	}

	/**
	 * Sets the final weight.
	 * 
	 * @param finalWeight
	 *            the new final weight
	 */
	public void setFinalWeight(Double finalWeight) {
		this.finalWeight = finalWeight;
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
	 * @param manifestStatus
	 *            the new manifest status
	 */
	public void setManifestStatus(String manifestStatus) {
		this.manifestStatus = manifestStatus;
	}

	/**
	 * Gets the process code.
	 * 
	 * @return the process code
	 */
	public String getProcessCode() {
		return processCode;
	}

	/**
	 * Sets the process code.
	 * 
	 * @param processCode
	 *            the new process code
	 */
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	/**
	 * Gets the process no.
	 * 
	 * @return the process no
	 */
	public String getProcessNo() {
		return processNo;
	}

	/**
	 * Sets the process no.
	 * 
	 * @param processNo
	 *            the new process no
	 */
	public void setProcessNo(String processNo) {
		this.processNo = processNo;
	}

	/**
	 * Gets the process id.
	 * 
	 * @return the process id
	 */
	public Integer getProcessId() {
		return processId;
	}

	/**
	 * Sets the process id.
	 * 
	 * @param processId
	 *            the new process id
	 */
	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	/**
	 * Gets the region id.
	 * 
	 * @return the region id
	 */
	public Integer getRegionId() {
		return regionId;
	}

	/**
	 * Sets the region id.
	 * 
	 * @param regionId
	 *            the new region id
	 */
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	/**
	 * Gets the product.
	 * 
	 * @return the product
	 */
	public ProductTO getProduct() {
		return product;
	}

	/**
	 * Sets the product.
	 * 
	 * @param product
	 *            the new product
	 */
	public void setProduct(ProductTO product) {
		this.product = product;
	}

	/**
	 * Gets the manifest id list at grid.
	 * 
	 * @return the manifest id list at grid
	 */
	public String getManifestIdListAtGrid() {
		return manifestIdListAtGrid;
	}

	/**
	 * Sets the manifest id list at grid.
	 * 
	 * @param manifestIdListAtGrid
	 *            the new manifest id list at grid
	 */
	public void setManifestIdListAtGrid(String manifestIdListAtGrid) {
		this.manifestIdListAtGrid = manifestIdListAtGrid;
	}

	/**
	 * Gets the consg id list at grid.
	 * 
	 * @return the consg id list at grid
	 */
	public String getConsgIdListAtGrid() {
		return consgIdListAtGrid;
	}

	/**
	 * Sets the consg id list at grid.
	 * 
	 * @param consgIdListAtGrid
	 *            the new consg id list at grid
	 */
	public void setConsgIdListAtGrid(String consgIdListAtGrid) {
		this.consgIdListAtGrid = consgIdListAtGrid;
	}

	/**
	 * Gets the comail id list at grid.
	 * 
	 * @return the comail id list at grid
	 */
	public String getComailIdListAtGrid() {
		return comailIdListAtGrid;
	}

	/**
	 * Sets the comail id list at grid.
	 * 
	 * @param comailIdListAtGrid
	 *            the new comail id list at grid
	 */
	public void setComailIdListAtGrid(String comailIdListAtGrid) {
		this.comailIdListAtGrid = comailIdListAtGrid;
	}

	/**
	 * Gets the series type.
	 * 
	 * @return the series type
	 */
	public String getSeriesType() {
		return seriesType;
	}

	/**
	 * Sets the series type.
	 * 
	 * @param seriesType
	 *            the new series type
	 */
	public void setSeriesType(String seriesType) {
		this.seriesType = seriesType;
	}

	/**
	 * Gets the stock no.
	 * 
	 * @return the stock no
	 */
	public String getStockNo() {
		return stockNo;
	}

	/**
	 * Sets the stock no.
	 * 
	 * @param stockNo
	 *            the new stock no
	 */
	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
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
	 * @param isActive
	 *            the new checks if is active
	 */
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	/**
	 * Gets the checks if is mul destination.
	 * 
	 * @return the checks if is mul destination
	 */
	public String getIsMulDestination() {
		return isMulDestination;
	}

	/**
	 * Sets the checks if is mul destination.
	 * 
	 * @param isMulDestination
	 *            the new checks if is mul destination
	 */
	public void setIsMulDestination(String isMulDestination) {
		this.isMulDestination = isMulDestination;
	}

	/**
	 * Gets the multi destinations.
	 * 
	 * @return the multi destinations
	 */
	public String getMultiDestinations() {
		return multiDestinations;
	}

	/**
	 * Sets the multi destinations.
	 * 
	 * @param multiDestinations
	 *            the new multi destinations
	 */
	public void setMultiDestinations(String multiDestinations) {
		this.multiDestinations = multiDestinations;
	}

	/**
	 * Gets the destination region list.
	 * 
	 * @return the destination region list
	 */
	public List<RegionTO> getDestinationRegionList() {
		return destinationRegionList;
	}

	/**
	 * Sets the destination region list.
	 * 
	 * @param destinationRegionList
	 *            the new destination region list
	 */
	public void setDestinationRegionList(List<RegionTO> destinationRegionList) {
		this.destinationRegionList = destinationRegionList;
	}

	/**
	 * Gets the destination city list.
	 * 
	 * @return the destination city list
	 */
	public List<CityTO> getDestinationCityList() {
		return destinationCityList;
	}

	/**
	 * Sets the destination city list.
	 * 
	 * @param destinationCityList
	 *            the new destination city list
	 */
	public void setDestinationCityList(List<CityTO> destinationCityList) {
		this.destinationCityList = destinationCityList;
	}

	/**
	 * Gets the destination office list.
	 * 
	 * @return the destination office list
	 */
	public List<OfficeTO> getDestinationOfficeList() {
		return destinationOfficeList;
	}

	/**
	 * Sets the destination office list.
	 * 
	 * @param destinationOfficeList
	 *            the new destination office list
	 */
	public void setDestinationOfficeList(List<OfficeTO> destinationOfficeList) {
		this.destinationOfficeList = destinationOfficeList;
	}

	/**
	 * Gets the rfid no.
	 * 
	 * @return the rfid no
	 */
	public String getRfidNo() {
		return rfidNo;
	}

	/**
	 * Sets the rfid no.
	 * 
	 * @param rfidNo
	 *            the new rfid no
	 */
	public void setRfidNo(String rfidNo) {
		this.rfidNo = rfidNo;
	}

	/**
	 * Gets the row count.
	 * 
	 * @return the row count
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * Sets the row count.
	 * 
	 * @param rowCount
	 *            the new row count
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * Gets the consg nos.
	 * 
	 * @return the consg nos
	 */
	public String[] getConsgNos() {
		return consgNos;
	}

	/**
	 * Sets the consg nos.
	 * 
	 * @param consgNos
	 *            the new consg nos
	 */
	public void setConsgNos(String[] consgNos) {
		this.consgNos = consgNos;
	}

	/**
	 * @return the consgManifestedIds
	 */
	public Integer[] getConsgManifestedIds() {
		return consgManifestedIds;
	}

	/**
	 * @param consgManifestedIds
	 *            the consgManifestedIds to set
	 */
	public void setConsgManifestedIds(Integer[] consgManifestedIds) {
		this.consgManifestedIds = consgManifestedIds;
	}

	/**
	 * Gets the booking ids.
	 * 
	 * @return the bookingIds
	 */
	public Integer[] getBookingIds() {
		return bookingIds;
	}

	/**
	 * Sets the booking ids.
	 * 
	 * @param bookingIds
	 *            the bookingIds to set
	 */
	public void setBookingIds(Integer[] bookingIds) {
		this.bookingIds = bookingIds;
	}

	/**
	 * Gets the booking type ids.
	 * 
	 * @return the bookingTypeIds
	 */
	public Integer[] getBookingTypeIds() {
		return bookingTypeIds;
	}

	/**
	 * Sets the booking type ids.
	 * 
	 * @param bookingTypeIds
	 *            the bookingTypeIds to set
	 */
	public void setBookingTypeIds(Integer[] bookingTypeIds) {
		this.bookingTypeIds = bookingTypeIds;
	}

	/**
	 * Gets the customer ids.
	 * 
	 * @return the customerIds
	 */
	public Integer[] getCustomerIds() {
		return customerIds;
	}

	/**
	 * Sets the customer ids.
	 * 
	 * @param customerIds
	 *            the customerIds to set
	 */
	public void setCustomerIds(Integer[] customerIds) {
		this.customerIds = customerIds;
	}

	/**
	 * Gets the runsheet nos.
	 * 
	 * @return the runsheetNos
	 */
	public String[] getRunsheetNos() {
		return runsheetNos;
	}

	/**
	 * Sets the runsheet nos.
	 * 
	 * @param runsheetNos
	 *            the runsheetNos to set
	 */
	public void setRunsheetNos(String[] runsheetNos) {
		this.runsheetNos = runsheetNos;
	}

	/**
	 * Gets the consg ids.
	 * 
	 * @return the consg ids
	 */
	public Integer[] getConsgIds() {
		return consgIds;
	}

	/**
	 * Sets the consg ids.
	 * 
	 * @param consgIds
	 *            the new consg ids
	 */
	public void setConsgIds(Integer[] consgIds) {
		this.consgIds = consgIds;
	}

	/**
	 * Gets the product ids.
	 * 
	 * @return the product ids
	 */
	public Integer[] getProductIds() {
		return productIds;
	}

	/**
	 * Sets the product ids.
	 * 
	 * @param productIds
	 *            the new product ids
	 */
	public void setProductIds(Integer[] productIds) {
		this.productIds = productIds;
	}

	/**
	 * Gets the comail ids.
	 * 
	 * @return the comail ids
	 */
	public Integer[] getComailIds() {
		return comailIds;
	}

	/**
	 * Sets the comail ids.
	 * 
	 * @param comailIds
	 *            the new comailIds
	 */
	public void setComailIds(Integer[] comailIds) {
		this.comailIds = comailIds;
	}

	/**
	 * Gets the checks if is c ns.
	 * 
	 * @return the checks if is c ns
	 */
	public String[] getIsCNs() {
		return isCNs;
	}

	/**
	 * Sets the checks if is c ns.
	 * 
	 * @param isCNs
	 *            the new checks if is c ns
	 */
	public void setIsCNs(String[] isCNs) {
		this.isCNs = isCNs;
	}

	/**
	 * Gets the manifest nos.
	 * 
	 * @return the manifest nos
	 */
	public String[] getManifestNos() {
		return manifestNos;
	}

	/**
	 * Sets the manifest nos.
	 * 
	 * @param manifestNos
	 *            the new manifest nos
	 */
	public void setManifestNos(String[] manifestNos) {
		this.manifestNos = manifestNos;
	}

	/**
	 * Gets the manifest ids.
	 * 
	 * @return the manifest ids
	 */
	public Integer[] getManifestIds() {
		return manifestIds;
	}

	/**
	 * Sets the manifest ids.
	 * 
	 * @param manifestIds
	 *            the new manifest ids
	 */
	public void setManifestIds(Integer[] manifestIds) {
		this.manifestIds = manifestIds;
	}

	/**
	 * Gets the pincodes.
	 * 
	 * @return the pincodes
	 */
	public String[] getPincodes() {
		return pincodes;
	}

	/**
	 * Sets the pincodes.
	 * 
	 * @param pincodes
	 *            the new pincodes
	 */
	public void setPincodes(String[] pincodes) {
		this.pincodes = pincodes;
	}

	/**
	 * Gets the pincode ids.
	 * 
	 * @return the pincode ids
	 */
	public Integer[] getPincodeIds() {
		return pincodeIds;
	}

	/**
	 * Sets the pincode ids.
	 * 
	 * @param pincodeIds
	 *            the new pincode ids
	 */
	public void setPincodeIds(Integer[] pincodeIds) {
		this.pincodeIds = pincodeIds;
	}

	/**
	 * Gets the dest citys.
	 * 
	 * @return the dest citys
	 */
	public String[] getDestCitys() {
		return destCitys;
	}

	/**
	 * Sets the dest citys.
	 * 
	 * @param destCitys
	 *            the new dest citys
	 */
	public void setDestCitys(String[] destCitys) {
		this.destCitys = destCitys;
	}

	/**
	 * Gets the dest city ids.
	 * 
	 * @return the dest city ids
	 */
	public Integer[] getDestCityIds() {
		return destCityIds;
	}

	/**
	 * Sets the dest city ids.
	 * 
	 * @param destCityIds
	 *            the new dest city ids
	 */
	public void setDestCityIds(Integer[] destCityIds) {
		this.destCityIds = destCityIds;
	}

	/**
	 * Gets the weights.
	 * 
	 * @return the weights
	 */
	public Double[] getWeights() {
		return weights;
	}

	/**
	 * Sets the weights.
	 * 
	 * @param weights
	 *            the new weights
	 */
	public void setWeights(Double[] weights) {
		this.weights = weights;
	}

	/**
	 * Gets the checkbox.
	 * 
	 * @return the checkbox
	 */
	public Integer[] getCheckbox() {
		return checkbox;
	}

	/**
	 * Sets the checkbox.
	 * 
	 * @param checkbox
	 *            the new checkbox
	 */
	public void setCheckbox(Integer[] checkbox) {
		this.checkbox = checkbox;
	}

	/**
	 * Gets the destination city to.
	 * 
	 * @return the destination city to
	 */
	public CityTO getDestinationCityTO() {
		return destinationCityTO;
	}

	/**
	 * Sets the destination city to.
	 * 
	 * @param destinationCityTO
	 *            the new destination city to
	 */
	public void setDestinationCityTO(CityTO destinationCityTO) {
		this.destinationCityTO = destinationCityTO;
	}

	/**
	 * Gets the dest region to.
	 * 
	 * @return the dest region to
	 */
	public RegionTO getDestRegionTO() {
		return destRegionTO;
	}

	/**
	 * Sets the dest region to.
	 * 
	 * @param destRegionTO
	 *            the new dest region to
	 */
	public void setDestRegionTO(RegionTO destRegionTO) {
		this.destRegionTO = destRegionTO;
	}

	/**
	 * Gets the manifest process to.
	 * 
	 * @return the manifest process to
	 */
	public ManifestProcessTO getManifestProcessTo() {
		return manifestProcessTo;
	}

	/**
	 * Sets the manifest process to.
	 * 
	 * @param manifestProcessTo
	 *            the new manifest process to
	 */
	public void setManifestProcessTo(ManifestProcessTO manifestProcessTo) {
		this.manifestProcessTo = manifestProcessTo;
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
	 * @param manifestType
	 *            the new manifest type
	 */
	public void setManifestType(String manifestType) {
		this.manifestType = manifestType;
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
	 * @param manifestDirection
	 *            the new manifest direction
	 */
	public void setManifestDirection(String manifestDirection) {
		this.manifestDirection = manifestDirection;
	}

	/**
	 * Gets the consignment type to.
	 * 
	 * @return the consignment type to
	 */
	public ConsignmentTypeTO getConsignmentTypeTO() {
		return consignmentTypeTO;
	}

	/**
	 * Sets the consignment type to.
	 * 
	 * @param consignmentTypeTO
	 *            the new consignment type to
	 */
	public void setConsignmentTypeTO(ConsignmentTypeTO consignmentTypeTO) {
		this.consignmentTypeTO = consignmentTypeTO;
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
	 * @param bagLockNo
	 *            the new bag lock no
	 */
	public void setBagLockNo(String bagLockNo) {
		this.bagLockNo = bagLockNo;
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
	 * @param bagRFID
	 *            the new bag rfid
	 */
	public void setBagRFID(Integer bagRFID) {
		this.bagRFID = bagRFID;
	}

	/**
	 * Gets the load no id.
	 * 
	 * @return the load no id
	 */
	public Integer getLoadNoId() {
		return loadNoId;
	}

	/**
	 * Sets the load no id.
	 * 
	 * @param loadNoId
	 *            the new load no id
	 */
	public void setLoadNoId(Integer loadNoId) {
		this.loadNoId = loadNoId;
	}

	/**
	 * Gets the configurable params.
	 * 
	 * @return the configurable params
	 */
	public Map<String, String> getConfigurableParams() {
		return configurableParams;
	}

	/**
	 * Sets the configurable params.
	 * 
	 * @param configurableParams
	 *            the configurable params
	 */
	public void setConfigurableParams(Map<String, String> configurableParams) {
		this.configurableParams = configurableParams;
	}

	/**
	 * Gets the max c ns allowed.
	 * 
	 * @return the max c ns allowed
	 */
	public String getMaxCNsAllowed() {
		return maxCNsAllowed;
	}

	/**
	 * Sets the max c ns allowed.
	 * 
	 * @param maxCNsAllowed
	 *            the new max c ns allowed
	 */
	public void setMaxCNsAllowed(String maxCNsAllowed) {
		this.maxCNsAllowed = maxCNsAllowed;
	}

	/**
	 * Gets the max comails allowed.
	 * 
	 * @return the max comails allowed
	 */
	public String getMaxComailsAllowed() {
		return maxComailsAllowed;
	}

	/**
	 * Sets the max comails allowed.
	 * 
	 * @param maxComailsAllowed
	 *            the new max comails allowed
	 */
	public void setMaxComailsAllowed(String maxComailsAllowed) {
		this.maxComailsAllowed = maxComailsAllowed;
	}

	/**
	 * Gets the max weight allowed.
	 * 
	 * @return the max weight allowed
	 */
	public String getMaxWeightAllowed() {
		return maxWeightAllowed;
	}

	/**
	 * Sets the max weight allowed.
	 * 
	 * @param maxWeightAllowed
	 *            the new max weight allowed
	 */
	public void setMaxWeightAllowed(String maxWeightAllowed) {
		this.maxWeightAllowed = maxWeightAllowed;
	}

	/**
	 * Gets the max tolerence allowed.
	 * 
	 * @return the max tolerence allowed
	 */
	public String getMaxTolerenceAllowed() {
		return maxTolerenceAllowed;
	}

	/**
	 * Sets the max tolerence allowed.
	 * 
	 * @param maxTolerenceAllowed
	 *            the new max tolerence allowed
	 */
	public void setMaxTolerenceAllowed(String maxTolerenceAllowed) {
		this.maxTolerenceAllowed = maxTolerenceAllowed;
	}

	/**
	 * Gets the max manifest allowed.
	 * 
	 * @return the max manifest allowed
	 */
	public String getMaxManifestAllowed() {
		return maxManifestAllowed;
	}

	/**
	 * Sets the max manifest allowed.
	 * 
	 * @param maxManifestAllowed
	 *            the new max manifest allowed
	 */
	public void setMaxManifestAllowed(String maxManifestAllowed) {
		this.maxManifestAllowed = maxManifestAllowed;
	}

	/**
	 * Checks if is manifest embedded.
	 * 
	 * @return true, if is manifest embedded
	 */
	public boolean isManifestEmbedded() {
		return isManifestEmbedded;
	}

	/**
	 * Sets the manifest embedded.
	 * 
	 * @param isManifestEmbedded
	 *            the new manifest embedded
	 */
	public void setManifestEmbedded(boolean isManifestEmbedded) {
		this.isManifestEmbedded = isManifestEmbedded;
	}

	/**
	 * Gets the office type id.
	 * 
	 * @return the office type id
	 */
	public Integer getOfficeTypeId() {
		return officeTypeId;
	}

	/**
	 * Sets the office type id.
	 * 
	 * @param officeTypeId
	 *            the new office type id
	 */
	public void setOfficeTypeId(Integer officeTypeId) {
		this.officeTypeId = officeTypeId;
	}

	/**
	 * Gets the checks if is cn processed from pickup.
	 * 
	 * @return the isCNProcessedFromPickup
	 */
	public String[] getIsCNProcessedFromPickup() {
		return isCNProcessedFromPickup;
	}

	/**
	 * Sets the checks if is cn processed from pickup.
	 * 
	 * @param isCNProcessedFromPickup
	 *            the isCNProcessedFromPickup to set
	 */
	public void setIsCNProcessedFromPickup(String[] isCNProcessedFromPickup) {
		this.isCNProcessedFromPickup = isCNProcessedFromPickup;
	}

	/**
	 * Gets the consignor ids.
	 * 
	 * @return the consignorIds
	 */
	public Integer[] getConsignorIds() {
		return consignorIds;
	}

	/**
	 * Sets the consignor ids.
	 * 
	 * @param consignorIds
	 *            the consignorIds to set
	 */
	public void setConsignorIds(Integer[] consignorIds) {
		this.consignorIds = consignorIds;
	}

	/**
	 * Gets the rep hub office id.
	 * 
	 * @return the repHubOfficeId
	 */
	public Integer getRepHubOfficeId() {
		return repHubOfficeId;
	}

	/**
	 * Sets the rep hub office id.
	 * 
	 * @param repHubOfficeId
	 *            the repHubOfficeId to set
	 */
	public void setRepHubOfficeId(Integer repHubOfficeId) {
		this.repHubOfficeId = repHubOfficeId;
	}

	/**
	 * Gets the bpl dest office city id.
	 * 
	 * @return the bpl dest office city id
	 */
	public Integer getBplDestOfficeCityId() {
		return bplDestOfficeCityId;
	}

	/**
	 * Sets the bpl dest office city id.
	 * 
	 * @param bplDestOfficeCityId
	 *            the new bpl dest office city id
	 */
	public void setBplDestOfficeCityId(Integer bplDestOfficeCityId) {
		this.bplDestOfficeCityId = bplDestOfficeCityId;
	}

	/**
	 * Gets the destination office to.
	 * 
	 * @return the destination office to
	 */
	public OfficeTO getDestinationOfficeTO() {
		return destinationOfficeTO;
	}

	/**
	 * Sets the destination office to.
	 * 
	 * @param destinationOfficeTO
	 *            the new destination office to
	 */
	public void setDestinationOfficeTO(OfficeTO destinationOfficeTO) {
		this.destinationOfficeTO = destinationOfficeTO;
	}

	/**
	 * @return the comailManifestedIds
	 */
	public Integer[] getComailManifestedIds() {
		return comailManifestedIds;
	}

	/**
	 * @param comailManifestedIds
	 *            the comailManifestedIds to set
	 */
	public void setComailManifestedIds(Integer[] comailManifestedIds) {
		this.comailManifestedIds = comailManifestedIds;
	}

	/**
	 * @return the noOfElements
	 */
	public Integer getNoOfElements() {
		return noOfElements;
	}

	/**
	 * @param noOfElements the noOfElements to set
	 */
	public void setNoOfElements(Integer noOfElements) {
		this.noOfElements = noOfElements;
	}

	public Integer[] getGridOriginOfficeId() {
		return gridOriginOfficeId;
	}

	public void setGridOriginOfficeId(Integer[] gridOriginOfficeId) {
		this.gridOriginOfficeId = gridOriginOfficeId;
	}

	public String getLoginOfficeAddress1() {
		return loginOfficeAddress1;
	}

	public void setLoginOfficeAddress1(String loginOfficeAddress1) {
		this.loginOfficeAddress1 = loginOfficeAddress1;
	}

	public String getLoginOfficeAddress2() {
		return loginOfficeAddress2;
	}

	public void setLoginOfficeAddress2(String loginOfficeAddress2) {
		this.loginOfficeAddress2 = loginOfficeAddress2;
	}

	public String getLoginOfficeAddress3() {
		return loginOfficeAddress3;
	}

	public void setLoginOfficeAddress3(String loginOfficeAddress3) {
		this.loginOfficeAddress3 = loginOfficeAddress3;
	}

	public String getLoginOfficePincode() {
		return loginOfficePincode;
	}

	public void setLoginOfficePincode(String loginOfficePincode) {
		this.loginOfficePincode = loginOfficePincode;
	}

	public String getAllowedManifestType() {
		return allowedManifestType;
	}

	public void setAllowedManifestType(String allowedManifestType) {
		this.allowedManifestType = allowedManifestType;
	}

	public String getOutMnfstDestIds() {
		return outMnfstDestIds;
	}

	public void setOutMnfstDestIds(String outMnfstDestIds) {
		this.outMnfstDestIds = outMnfstDestIds;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Integer getOperatingLevel() {
		return operatingLevel;
	}

	public void setOperatingLevel(Integer operatingLevel) {
		this.operatingLevel = operatingLevel;
	}

	public List<Integer> getOriginHubOffList() {
		return originHubOffList;
	}

	public void setOriginHubOffList(List<Integer> originHubOffList) {
		this.originHubOffList = originHubOffList;
	}

	public List<Integer> getDestHubOffList() {
		return destHubOffList;
	}

	public void setDestHubOffList(List<Integer> destHubOffList) {
		this.destHubOffList = destHubOffList;
	}

	/**
	 * @return the manifestMappedEmbeddeId
	 */
	public Integer[] getManifestMappedEmbeddeId() {
		return manifestMappedEmbeddeId;
	}

	/**
	 * @param manifestMappedEmbeddeId the manifestMappedEmbeddeId to set
	 */
	public void setManifestMappedEmbeddeId(Integer[] manifestMappedEmbeddeId) {
		this.manifestMappedEmbeddeId = manifestMappedEmbeddeId;
	}

	/**
	 * @return the manifestProcessId
	 */
	public Integer getManifestProcessId() {
		return manifestProcessId;
	}

	/**
	 * @param manifestProcessId the manifestProcessId to set
	 */
	public void setManifestProcessId(Integer manifestProcessId) {
		this.manifestProcessId = manifestProcessId;
	}

	/**
	 * @return the twoWayManifestId
	 */
	public Integer getTwoWayManifestId() {
		return twoWayManifestId;
	}

	/**
	 * @param twoWayManifestId the twoWayManifestId to set
	 */
	public void setTwoWayManifestId(Integer twoWayManifestId) {
		this.twoWayManifestId = twoWayManifestId;
	}

	public String getReceivedStatus() {
		return receivedStatus;
	}

	public void setReceivedStatus(String receivedStatus) {
		this.receivedStatus = receivedStatus;
	}	
	
	
}
