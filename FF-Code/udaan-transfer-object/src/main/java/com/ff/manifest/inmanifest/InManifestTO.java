package com.ff.manifest.inmanifest;

import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.ff.manifest.ManifestBaseTO;
import com.ff.organization.OfficeTO;

/**
 * @author nkattung
 * 
 */
public class InManifestTO extends ManifestBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2288625762533354553L;

	// Reusable attributes
	private String officeType;
	private String regionId;
	private String cityId;
	private String officeId;
	private String manifestDateTime;
	private String lessManifest;
	private String excessManifest;
	private String lessConsgs;
	private String excessConsgs;
	private String gridProcessCode;
	private String headerRemarks;
	private String manifestReceivedStatus;
	private Map<String, String> manifestReceivedStatusMap;
	
	private String isExcessConsg = CommonConstants.NO;
	
	/** The logged in office to. */
	private OfficeTO loggedInOfficeTO;
	//private Integer destinationOfficeId;

	//validation
	private List<String> manifestNoList;
	private List<String> consgNoList;
	private String consgNumber;
	private String coMailNo;
	
	//added for 2WayWrite
	private Integer manifestProcessId;
	private Integer twoWayManifestId;
	
	// Reusable UI Specific attributes
	private int count;
	private Double[] manifestWeights = new Double[count];
	private String[] manifestNumbers = new String[count];
	private Integer[] manifestIds = new Integer[count];
	private Integer[] originOfficeIds = new Integer[count];
	private Integer[] destOfficeIds = new Integer[count];
	private Integer[] destCityIds = new Integer[count];
	private String[] destCityNames = new String[count];
	private String[] remarks = new String[count];
	private String[] lockNumbers = new String[count];
	private String[] receivedStatus = new String[count];//R-Received, N-Not Received
	private Integer[] updateProcessIds = new Integer[count];
	private String[] updateProcessCodes = new String[count];
	private String[] processCodes = new String[count];
	
	//consignment
	private Integer[] consignmentIds = new Integer[count];
	private Integer[] consgOrgOffIds = new Integer[count];
	private String[] consgNumbers = new String[count];
	private Integer[] destPincodeIds = new Integer[count];
	private String[] destPincodes = new String[count];
	private Integer[] numOfPcs = new Integer[count];
	private String[] childCns = new String[count];//cn,wt#cn,wt
	private Double[] actualWeights = new Double[count];//weight in grid
	private Double[] finalWeights = new Double[count]; //chargeable Weights
	private Double[] chargeableWeights = new Double[count];
	private Double[] volWeights = new Double[count];
	private Double[] lengths = new Double[count];
	private Double[] heights = new Double[count];
	private Double[] breadths = new Double[count];
	private String[] mobileNos = new String[count];
	private Integer[] cnContentIds = new Integer[count];
	private String[] cnContentCodes = new String[count];
	private String[] cnContentNames = new String[count];
	private String[] otherCNContents = new String[count];
	private String[] cnContentOther = new String[count];
	private Double[] declaredValues = new Double[count];
	private Integer[] consignmentTypeIds = new Integer[count];
	
	private Integer[] paperWorkIds = new Integer[count];
	private String[] cnPaperWorks = new String[count];
	private String[] paperRefNums = new String[count];
	private String[] cnContents = new String[count];
	private Integer[] insuredByIds = new Integer[count];
	private String[] policyNos = new String[count];
	private Double[] toPayAmts = new Double[count];
	private Double[] codAmts = new Double[count];//cod lc amt
	private Double[] baAmts = new Double[count];
	private String[] lcBankNames = new String[count];
	private String[] refNos = new String[count];
	private Integer[] productIds = new Integer[count];
	private Integer[] priceIds = new Integer[count];

	private Integer[] consignmentManifestIds = new Integer[count];
	private String[] bookingTypes = new String[count];
	
	//private String[] pincodes = new String[count];	
	//private String[] destCities = new String[count];
	//private Integer[] pincodeIds = new Integer[count];
	//private Integer[] cityIds = new Integer[count];
	//private Integer[] bookingIds = new Integer[count];
	
	/*private String[] weightCapturedModes = new String[count];
	private String[] consgPricingDtls = new String[count];
	private String[] cneAddressDtls = new String[count];
	private String[] cnrAddressDtls = new String[count];
	//private String[] childCNDetails = new String[count];cn,wt#cn,wt
	private String[] otherCNContents = new String[count];
	private Integer[] dlvTimeMapIds = new Integer[count];	

	private String[] bookingTypes = new String[count];
	private Integer[] consigneeIds = new Integer[count];
	private String[] paperRefNums = new String[count];
	private String[] cnPaperWorkCode = new String[count];
	private String[] cnPaperWorkNames = new String[count];*/

	
	/**
	 * @return the cityId
	 */
	public String getCityId() {
		return cityId;
	}

	/**
	 * @return the cnContentOther
	 */
	public String[] getCnContentOther() {
		return cnContentOther;
	}

	/**
	 * @param cnContentOther the cnContentOther to set
	 */
	public void setCnContentOther(String[] cnContentOther) {
		this.cnContentOther = cnContentOther;
	}

	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return the officeId
	 */
	public String getOfficeId() {
		return officeId;
	}

	/**
	 * @param officeId the officeId to set
	 */
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Double[] getManifestWeights() {
		return manifestWeights;
	}

	public void setManifestWeights(Double[] manifestWeights) {
		this.manifestWeights = manifestWeights;
	}

	public String[] getManifestNumbers() {
		return manifestNumbers;
	}

	public void setManifestNumbers(String[] manifestNumbers) {
		this.manifestNumbers = manifestNumbers;
	}

	public String[] getConsgNumbers() {
		return consgNumbers;
	}

	public void setConsgNumbers(String[] consgNumbers) {
		this.consgNumbers = consgNumbers;
	}

	public Integer[] getManifestIds() {
		return manifestIds;
	}

	public void setManifestIds(Integer[] manifestIds) {
		this.manifestIds = manifestIds;
	}

	public Integer[] getConsignmentIds() {
		return consignmentIds;
	}

	public void setConsignmentIds(Integer[] consignmentIds) {
		this.consignmentIds = consignmentIds;
	}

	public Integer[] getDestPincodeIds() {
		return destPincodeIds;
	}

	public void setDestPincodeIds(Integer[] destPincodeIds) {
		this.destPincodeIds = destPincodeIds;
	}

	public Integer[] getDestCityIds() {
		return destCityIds;
	}

	public void setDestCityIds(Integer[] destCityIds) {
		this.destCityIds = destCityIds;
	}

	public String[] getDestPincodes() {
		return destPincodes;
	}

	public void setDestPincodes(String[] destPincodes) {
		this.destPincodes = destPincodes;
	}

	public String[] getDestCityNames() {
		return destCityNames;
	}

	public void setDestCityNames(String[] destCityNames) {
		this.destCityNames = destCityNames;
	}

	public String getOfficeType() {
		return officeType;
	}

	public void setOfficeType(String officeType) {
		this.officeType = officeType;
	}


	/**
	 * @return the remarks
	 */
	public String[] getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the regionId
	 */
	public String getRegionId() {
		return regionId;
	}

	/**
	 * @param regionId the regionId to set
	 */
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	/**
	 * @return the manifestDateTime
	 */
	public String getManifestDateTime() {
		return manifestDateTime;
	}

	/**
	 * @param manifestDateTime the manifestDateTime to set
	 */
	public void setManifestDateTime(String manifestDateTime) {
		this.manifestDateTime = manifestDateTime;
	}

	public String[] getLockNumbers() {
		return lockNumbers;
	}

	public void setLockNumbers(String[] lockNumbers) {
		this.lockNumbers = lockNumbers;
	}

	public String[] getReceivedStatus() {
		return receivedStatus;
	}

	public void setReceivedStatus(String[] receivedStatus) {
		this.receivedStatus = receivedStatus;
	}

	/**
	 * @return the updateProcessIds
	 */
	public Integer[] getUpdateProcessIds() {
		return updateProcessIds;
	}

	/**
	 * @param updateProcessIds the updateProcessIds to set
	 */
	public void setUpdateProcessIds(Integer[] updateProcessIds) {
		this.updateProcessIds = updateProcessIds;
	}

	/**
	 * @return the updateProcessCodes
	 */
	public String[] getUpdateProcessCodes() {
		return updateProcessCodes;
	}

	/**
	 * @param updateProcessCodes the updateProcessCodes to set
	 */
	public void setUpdateProcessCodes(String[] updateProcessCodes) {
		this.updateProcessCodes = updateProcessCodes;
	}

	/**
	 * @return the processCodes
	 */
	public String[] getProcessCodes() {
		return processCodes;
	}

	/**
	 * @param processCodes the processCodes to set
	 */
	public void setProcessCodes(String[] processCodes) {
		this.processCodes = processCodes;
	}

	/**
	 * @return the lessManifest
	 */
	public String getLessManifest() {
		return lessManifest;
	}

	/**
	 * @param lessManifest the lessManifest to set
	 */
	public void setLessManifest(String lessManifest) {
		this.lessManifest = lessManifest;
	}

	/**
	 * @return the excessManifest
	 */
	public String getExcessManifest() {
		return excessManifest;
	}

	/**
	 * @param excessManifest the excessManifest to set
	 */
	public void setExcessManifest(String excessManifest) {
		this.excessManifest = excessManifest;
	}

	/**
	 * @return the manifestNoList
	 */
	public List<String> getManifestNoList() {
		return manifestNoList;
	}

	/**
	 * @param manifestNoList the manifestNoList to set
	 */
	public void setManifestNoList(List<String> manifestNoList) {
		this.manifestNoList = manifestNoList;
	}

	/**
	 * @return the gridProcessCode
	 */
	public String getGridProcessCode() {
		return gridProcessCode;
	}

	/**
	 * @param gridProcessCode the gridProcessCode to set
	 */
	public void setGridProcessCode(String gridProcessCode) {
		this.gridProcessCode = gridProcessCode;
	}

	/**
	 * @return the numOfPcs
	 */
	public Integer[] getNumOfPcs() {
		return numOfPcs;
	}

	/**
	 * @param numOfPcs the numOfPcs to set
	 */
	public void setNumOfPcs(Integer[] numOfPcs) {
		this.numOfPcs = numOfPcs;
	}

	/**
	 * @return the childCns
	 */
	public String[] getChildCns() {
		return childCns;
	}

	/**
	 * @param childCns the childCns to set
	 */
	public void setChildCns(String[] childCns) {
		this.childCns = childCns;
	}

	/**
	 * @return the actualWeights
	 */
	public Double[] getActualWeights() {
		return actualWeights;
	}

	/**
	 * @param actualWeights the actualWeights to set
	 */
	public void setActualWeights(Double[] actualWeights) {
		this.actualWeights = actualWeights;
	}

	/**
	 * @return the finalWeights
	 */
	public Double[] getFinalWeights() {
		return finalWeights;
	}

	/**
	 * @param finalWeights the finalWeights to set
	 */
	public void setFinalWeights(Double[] finalWeights) {
		this.finalWeights = finalWeights;
	}

	/**
	 * @return the chargeableWeights
	 */
	public Double[] getChargeableWeights() {
		return chargeableWeights;
	}

	/**
	 * @param chargeableWeights the chargeableWeights to set
	 */
	public void setChargeableWeights(Double[] chargeableWeights) {
		this.chargeableWeights = chargeableWeights;
	}

	/**
	 * @return the volWeights
	 */
	public Double[] getVolWeights() {
		return volWeights;
	}

	/**
	 * @param volWeights the volWeights to set
	 */
	public void setVolWeights(Double[] volWeights) {
		this.volWeights = volWeights;
	}

	/**
	 * @return the lengths
	 */
	public Double[] getLengths() {
		return lengths;
	}

	/**
	 * @param lengths the lengths to set
	 */
	public void setLengths(Double[] lengths) {
		this.lengths = lengths;
	}

	/**
	 * @return the heights
	 */
	public Double[] getHeights() {
		return heights;
	}

	/**
	 * @param heights the heights to set
	 */
	public void setHeights(Double[] heights) {
		this.heights = heights;
	}

	/**
	 * @return the breadths
	 */
	public Double[] getBreadths() {
		return breadths;
	}

	/**
	 * @param breadths the breadths to set
	 */
	public void setBreadths(Double[] breadths) {
		this.breadths = breadths;
	}

	/**
	 * @return the mobileNos
	 */
	public String[] getMobileNos() {
		return mobileNos;
	}

	/**
	 * @param mobileNos the mobileNos to set
	 */
	public void setMobileNos(String[] mobileNos) {
		this.mobileNos = mobileNos;
	}

	/**
	 * @return the cnContentIds
	 */
	public Integer[] getCnContentIds() {
		return cnContentIds;
	}

	/**
	 * @param cnContentIds the cnContentIds to set
	 */
	public void setCnContentIds(Integer[] cnContentIds) {
		this.cnContentIds = cnContentIds;
	}

	/**
	 * @return the cnContentCodes
	 */
	public String[] getCnContentCodes() {
		return cnContentCodes;
	}

	/**
	 * @param cnContentCodes the cnContentCodes to set
	 */
	public void setCnContentCodes(String[] cnContentCodes) {
		this.cnContentCodes = cnContentCodes;
	}

	/**
	 * @return the cnContentNames
	 */
	public String[] getCnContentNames() {
		return cnContentNames;
	}

	/**
	 * @param cnContentNames the cnContentNames to set
	 */
	public void setCnContentNames(String[] cnContentNames) {
		this.cnContentNames = cnContentNames;
	}

	/**
	 * @return the declaredValues
	 */
	public Double[] getDeclaredValues() {
		return declaredValues;
	}

	/**
	 * @param declaredValues the declaredValues to set
	 */
	public void setDeclaredValues(Double[] declaredValues) {
		this.declaredValues = declaredValues;
	}

	/**
	 * @return the paperWorkIds
	 */
	public Integer[] getPaperWorkIds() {
		return paperWorkIds;
	}

	/**
	 * @param paperWorkIds the paperWorkIds to set
	 */
	public void setPaperWorkIds(Integer[] paperWorkIds) {
		this.paperWorkIds = paperWorkIds;
	}

	/**
	 * @return the insuredByIds
	 */
	public Integer[] getInsuredByIds() {
		return insuredByIds;
	}

	/**
	 * @param insuredByIds the insuredByIds to set
	 */
	public void setInsuredByIds(Integer[] insuredByIds) {
		this.insuredByIds = insuredByIds;
	}

	/**
	 * @return the policyNos
	 */
	public String[] getPolicyNos() {
		return policyNos;
	}

	/**
	 * @param policyNos the policyNos to set
	 */
	public void setPolicyNos(String[] policyNos) {
		this.policyNos = policyNos;
	}

	/**
	 * @return the toPayAmts
	 */
	public Double[] getToPayAmts() {
		return toPayAmts;
	}

	/**
	 * @param toPayAmts the toPayAmts to set
	 */
	public void setToPayAmts(Double[] toPayAmts) {
		this.toPayAmts = toPayAmts;
	}

	/**
	 * @return the codAmts
	 */
	public Double[] getCodAmts() {
		return codAmts;
	}

	/**
	 * @param codAmts the codAmts to set
	 */
	public void setCodAmts(Double[] codAmts) {
		this.codAmts = codAmts;
	}

	/**
	 * @return the refNos
	 */
	public String[] getRefNos() {
		return refNos;
	}

	/**
	 * @param refNos the refNos to set
	 */
	public void setRefNos(String[] refNos) {
		this.refNos = refNos;
	}

	/**
	 * @return the otherCNContents
	 */
	public String[] getOtherCNContents() {
		return otherCNContents;
	}

	/**
	 * @param otherCNContents the otherCNContents to set
	 */
	public void setOtherCNContents(String[] otherCNContents) {
		this.otherCNContents = otherCNContents;
	}

	/**
	 * @return the cnPaperWorks
	 */
	public String[] getCnPaperWorks() {
		return cnPaperWorks;
	}

	/**
	 * @param cnPaperWorks the cnPaperWorks to set
	 */
	public void setCnPaperWorks(String[] cnPaperWorks) {
		this.cnPaperWorks = cnPaperWorks;
	}

	/**
	 * @return the paperRefNums
	 */
	public String[] getPaperRefNums() {
		return paperRefNums;
	}

	/**
	 * @param paperRefNums the paperRefNums to set
	 */
	public void setPaperRefNums(String[] paperRefNums) {
		this.paperRefNums = paperRefNums;
	}

	/**
	 * @return the cnContents
	 */
	public String[] getCnContents() {
		return cnContents;
	}

	/**
	 * @param cnContents the cnContents to set
	 */
	public void setCnContents(String[] cnContents) {
		this.cnContents = cnContents;
	}

	/**
	 * @return the productIds
	 */
	public Integer[] getProductIds() {
		return productIds;
	}

	/**
	 * @param productIds the productIds to set
	 */
	public void setProductIds(Integer[] productIds) {
		this.productIds = productIds;
	}

	/**
	 * @return the consignmentTypeIds
	 */
	public Integer[] getConsignmentTypeIds() {
		return consignmentTypeIds;
	}

	/**
	 * @param consignmentTypeIds the consignmentTypeIds to set
	 */
	public void setConsignmentTypeIds(Integer[] consignmentTypeIds) {
		this.consignmentTypeIds = consignmentTypeIds;
	}

	/**
	 * @return the consignmentManifestIds
	 */
	public Integer[] getConsignmentManifestIds() {
		return consignmentManifestIds;
	}

	/**
	 * @param consignmentManifestIds the consignmentManifestIds to set
	 */
	public void setConsignmentManifestIds(Integer[] consignmentManifestIds) {
		this.consignmentManifestIds = consignmentManifestIds;
	}

	/**
	 * @return the priceIds
	 */
	public Integer[] getPriceIds() {
		return priceIds;
	}

	/**
	 * @param priceIds the priceIds to set
	 */
	public void setPriceIds(Integer[] priceIds) {
		this.priceIds = priceIds;
	}

	/**
	 * @return the bookingTypes
	 */
	public String[] getBookingTypes() {
		return bookingTypes;
	}

	/**
	 * @param bookingTypes the bookingTypes to set
	 */
	public void setBookingTypes(String[] bookingTypes) {
		this.bookingTypes = bookingTypes;
	}

	/**
	 * @return the lessConsgs
	 */
	public String getLessConsgs() {
		return lessConsgs;
	}

	/**
	 * @param lessConsgs the lessConsgs to set
	 */
	public void setLessConsgs(String lessConsgs) {
		this.lessConsgs = lessConsgs;
	}

	/**
	 * @return the excessConsgs
	 */
	public String getExcessConsgs() {
		return excessConsgs;
	}

	/**
	 * @param excessConsgs the excessConsgs to set
	 */
	public void setExcessConsgs(String excessConsgs) {
		this.excessConsgs = excessConsgs;
	}

	/**
	 * @return the consgNoList
	 */
	public List<String> getConsgNoList() {
		return consgNoList;
	}

	/**
	 * @param consgNoList the consgNoList to set
	 */
	public void setConsgNoList(List<String> consgNoList) {
		this.consgNoList = consgNoList;
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
	 * @return the loggedInOfficeTO
	 */
	public OfficeTO getLoggedInOfficeTO() {
		return loggedInOfficeTO;
	}

	/**
	 * @param loggedInOfficeTO the loggedInOfficeTO to set
	 */
	public void setLoggedInOfficeTO(OfficeTO loggedInOfficeTO) {
		this.loggedInOfficeTO = loggedInOfficeTO;
	}

	/**
	 * @return the headerRemarks
	 */
	public String getHeaderRemarks() {
		return headerRemarks;
	}

	/**
	 * @param headerRemarks the headerRemarks to set
	 */
	public void setHeaderRemarks(String headerRemarks) {
		this.headerRemarks = headerRemarks;
	}

	/**
	 * @return the consgOrgOffIds
	 */
	public Integer[] getConsgOrgOffIds() {
		return consgOrgOffIds;
	}

	/**
	 * @param consgOrgOffIds the consgOrgOffIds to set
	 */
	public void setConsgOrgOffIds(Integer[] consgOrgOffIds) {
		this.consgOrgOffIds = consgOrgOffIds;
	}

	/**
	 * @return the coMailNo
	 */
	public String getCoMailNo() {
		return coMailNo;
	}

	/**
	 * @param coMailNo the coMailNo to set
	 */
	public void setCoMailNo(String coMailNo) {
		this.coMailNo = coMailNo;
	}

	/**
	 * @return the originOfficeIds
	 */
	public Integer[] getOriginOfficeIds() {
		return originOfficeIds;
	}

	/**
	 * @param originOfficeIds the originOfficeIds to set
	 */
	public void setOriginOfficeIds(Integer[] originOfficeIds) {
		this.originOfficeIds = originOfficeIds;
	}

	/**
	 * @return the destOfficeIds
	 */
	public Integer[] getDestOfficeIds() {
		return destOfficeIds;
	}

	/**
	 * @param destOfficeIds the destOfficeIds to set
	 */
	public void setDestOfficeIds(Integer[] destOfficeIds) {
		this.destOfficeIds = destOfficeIds;
	}

	/**
	 * @return the lcBankNames
	 */
	public String[] getLcBankNames() {
		return lcBankNames;
	}

	/**
	 * @param lcBankNames the lcBankNames to set
	 */
	public void setLcBankNames(String[] lcBankNames) {
		this.lcBankNames = lcBankNames;
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

	/**
	 * @return the baAmts
	 */
	public Double[] getBaAmts() {
		return baAmts;
	}

	/**
	 * @param baAmts the baAmts to set
	 */
	public void setBaAmts(Double[] baAmts) {
		this.baAmts = baAmts;
	}

	/**
	 * @return the isExcessConsg
	 */
	public String getIsExcessConsg() {
		return isExcessConsg;
	}

	/**
	 * @param isExcessConsg the isExcessConsg to set
	 */
	public void setIsExcessConsg(String isExcessConsg) {
		this.isExcessConsg = isExcessConsg;
	}

	/**
	 * @return the manifestReceivedStatus
	 */
	public String getManifestReceivedStatus() {
		return manifestReceivedStatus;
	}

	/**
	 * @param manifestReceivedStatus the manifestReceivedStatus to set
	 */
	public void setManifestReceivedStatus(String manifestReceivedStatus) {
		this.manifestReceivedStatus = manifestReceivedStatus;
	}

	/**
	 * @return the manifestReceivedStatusMap
	 */
	public Map<String, String> getManifestReceivedStatusMap() {
		return manifestReceivedStatusMap;
	}

	/**
	 * @param manifestReceivedStatusMap the manifestReceivedStatusMap to set
	 */
	public void setManifestReceivedStatusMap(
			Map<String, String> manifestReceivedStatusMap) {
		this.manifestReceivedStatusMap = manifestReceivedStatusMap;
	}
}
