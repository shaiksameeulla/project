package com.ff.to.ratemanagement.operations.rateconfiguration;

import java.util.List;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.StateTO;
import com.ff.to.ratemanagement.masters.RateSectorsTO;
import com.ff.to.ratemanagement.masters.RateWeightSlabsTO;

/**
 * @author prmeher
 */
public class BARateHeaderTO extends CGBaseTO {

	private static final long serialVersionUID = 6691023869433214476L;

	private Integer headerId;
	private String frmDate;
	private String toDate;
	private Integer regionId;
	private Integer cityId;
	private Integer baType;
	private String headerStatus;
	private String isSaved;
	private String isRenew;
	private String isCourierProductHeaderSaved;
	private String isPriorityProductHeaderSaved;
	private String isBAConfigRenew;

	private Integer courierProductHeaderId;
	private Integer trainProductHeaderId;
	private Integer airCargoProductHeaderId;
	private Integer priorityProductHeaderId;

	private Integer productCategoryIdForCourier;
	private Integer productCategoryIdForTrain;
	private Integer productCategoryIdForAirCargo;
	private Integer productCategoryIdForPriority;

	// Priority
	private BARateConfigSlabRateTO baCourierSlabRateTO;// Courier
	private BARateConfigSlabRateTO baTrainSlabRateTO;// Train
	private BARateConfigSlabRateTO baAirCargoSlabRateTO;// Air-Cargo
	private BARateConfigFixedChargesTO baCourierFixedChargesTO;// Fixed
	private BARateConfigRTOChargesTO baCourierRTOChargesTO;// RTO

	// Non-priority
	private BARateConfigSlabRateTO baPrioritySlabRateTO;// Rates
	private BARateConfigFixedChargesTO baPriorityFixedChargesTO;// Fixed
	private BARateConfigRTOChargesTO baPriorityRTOChargesTO;// RTO

	private List<BaRateProductTO> baRateProductTOList;
	// Courier
	private List<RateWeightSlabsTO> wtSlabList;
	private List<RateSectorsTO> sectorsList;
	// Train
	private List<RateWeightSlabsTO> trainWtSlabList;
	private List<RateSectorsTO> trainSectorsList;
	// Air-Cargo
	private List<RateWeightSlabsTO> airCargoWtSlabList;
	private List<RateSectorsTO> airCargoSectorsList;
	// Priority
	private List<RateWeightSlabsTO> priorityWtSlabList;
	private List<RateSectorsTO> prioritySectorsList;

	private Integer oldHeaderId;
	private String oldfromDate;
	private String oldtoDate;
	private String oldCity;
	private String oldbaType;
	private String isRenewWindow = CommonConstants.NO;
	private String productCategory;

	// Non-Priority check flags
	String courierRatesCheck = CommonConstants.NO;
	String airCargoRatesCheck = CommonConstants.NO;
	String trainRatesCheck = CommonConstants.NO;
	String courierChargesCheck = CommonConstants.NO;
	String courierRTOCheck = CommonConstants.NO;

	// Priority check flags
	String priorityBRatesCheck = CommonConstants.NO;
	String priorityARatesCheck = CommonConstants.NO;
	String prioritySRatesCheck = CommonConstants.NO;
	String priorityChargesCheck = CommonConstants.NO;
	String priorityRTOCheck = CommonConstants.NO;

	String sectorCode;
	List<StateTO> statesList;

	/** The common product category code. */
	private String commonProdCatCode;
	private Integer wtSlabMinChrgTR;
	private Integer wtSlabMinChrgAR;
	private Integer wtSlabMinChrgCR;
	private Integer zeroWeightSlabId;

	private String isPriorityProduct;
	private String priorityIndicator;
	// Serviced On - Common
	private String servicedOnCmn;
	private Integer loggedInUserId;


	/**
	 * @return the courierRTOCheck
	 */
	public String getCourierRTOCheck() {
		return courierRTOCheck;
	}

	/**
	 * @param courierRTOCheck
	 *            the courierRTOCheck to set
	 */
	public void setCourierRTOCheck(String courierRTOCheck) {
		this.courierRTOCheck = courierRTOCheck;
	}

	/**
	 * @return the priorityRTOCheck
	 */
	public String getPriorityRTOCheck() {
		return priorityRTOCheck;
	}

	/**
	 * @param priorityRTOCheck
	 *            the priorityRTOCheck to set
	 */
	public void setPriorityRTOCheck(String priorityRTOCheck) {
		this.priorityRTOCheck = priorityRTOCheck;
	}

	/**
	 * @return the airCargoRatesCheck
	 */
	public String getAirCargoRatesCheck() {
		return airCargoRatesCheck;
	}

	/**
	 * @param airCargoRatesCheck
	 *            the airCargoRatesCheck to set
	 */
	public void setAirCargoRatesCheck(String airCargoRatesCheck) {
		this.airCargoRatesCheck = airCargoRatesCheck;
	}

	/**
	 * @return the trainRatesCheck
	 */
	public String getTrainRatesCheck() {
		return trainRatesCheck;
	}

	/**
	 * @param trainRatesCheck
	 *            the trainRatesCheck to set
	 */
	public void setTrainRatesCheck(String trainRatesCheck) {
		this.trainRatesCheck = trainRatesCheck;
	}

	/**
	 * @return the servicedOnCmn
	 */
	public String getServicedOnCmn() {
		return servicedOnCmn;
	}

	/**
	 * @param servicedOnCmn
	 *            the servicedOnCmn to set
	 */
	public void setServicedOnCmn(String servicedOnCmn) {
		this.servicedOnCmn = servicedOnCmn;
	}

	/**
	 * @return the baCourierSlabRateTO
	 */
	public BARateConfigSlabRateTO getBaCourierSlabRateTO() {
		return baCourierSlabRateTO;
	}

	/**
	 * @param baCourierSlabRateTO
	 *            the baCourierSlabRateTO to set
	 */
	public void setBaCourierSlabRateTO(
			BARateConfigSlabRateTO baCourierSlabRateTO) {
		this.baCourierSlabRateTO = baCourierSlabRateTO;
	}

	/**
	 * @return the baPrioritySlabRateTO
	 */
	public BARateConfigSlabRateTO getBaPrioritySlabRateTO() {
		return baPrioritySlabRateTO;
	}

	/**
	 * @param baPrioritySlabRateTO
	 *            the baPrioritySlabRateTO to set
	 */
	public void setBaPrioritySlabRateTO(
			BARateConfigSlabRateTO baPrioritySlabRateTO) {
		this.baPrioritySlabRateTO = baPrioritySlabRateTO;
	}

	/**
	 * @return the productCategory
	 */
	public String getProductCategory() {
		return productCategory;
	}

	/**
	 * @param productCategory
	 *            the productCategory to set
	 */
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	/**
	 * @return the oldHeaderId
	 */
	public Integer getOldHeaderId() {
		return oldHeaderId;
	}

	/**
	 * @param oldHeaderId
	 *            the oldHeaderId to set
	 */
	public void setOldHeaderId(Integer oldHeaderId) {
		this.oldHeaderId = oldHeaderId;
	}

	/**
	 * @return the oldfromDate
	 */
	public String getOldfromDate() {
		return oldfromDate;
	}

	/**
	 * @param oldfromDate
	 *            the oldfromDate to set
	 */
	public void setOldfromDate(String oldfromDate) {
		this.oldfromDate = oldfromDate;
	}

	/**
	 * @return the oldtoDate
	 */
	public String getOldtoDate() {
		return oldtoDate;
	}

	/**
	 * @param oldtoDate
	 *            the oldtoDate to set
	 */
	public void setOldtoDate(String oldtoDate) {
		this.oldtoDate = oldtoDate;
	}

	/**
	 * @return the oldCity
	 */
	public String getOldCity() {
		return oldCity;
	}

	/**
	 * @param oldCity
	 *            the oldCity to set
	 */
	public void setOldCity(String oldCity) {
		this.oldCity = oldCity;
	}

	/**
	 * @return the oldbaType
	 */
	public String getOldbaType() {
		return oldbaType;
	}

	/**
	 * @param oldbaType
	 *            the oldbaType to set
	 */
	public void setOldbaType(String oldbaType) {
		this.oldbaType = oldbaType;
	}

	/**
	 * @return the isRenewWindow
	 */
	public String getIsRenewWindow() {
		return isRenewWindow;
	}

	/**
	 * @param isRenewWindow
	 *            the isRenewWindow to set
	 */
	public void setIsRenewWindow(String isRenewWindow) {
		this.isRenewWindow = isRenewWindow;
	}

	/**
	 * @return the isCourierProductHeaderSaved
	 */
	public String getIsCourierProductHeaderSaved() {
		return isCourierProductHeaderSaved;
	}

	/**
	 * @param isCourierProductHeaderSaved
	 *            the isCourierProductHeaderSaved to set
	 */
	public void setIsCourierProductHeaderSaved(
			String isCourierProductHeaderSaved) {
		this.isCourierProductHeaderSaved = isCourierProductHeaderSaved;
	}

	/**
	 * @return the isPriorityProductHeaderSaved
	 */
	public String getIsPriorityProductHeaderSaved() {
		return isPriorityProductHeaderSaved;
	}

	/**
	 * @param isPriorityProductHeaderSaved
	 *            the isPriorityProductHeaderSaved to set
	 */
	public void setIsPriorityProductHeaderSaved(
			String isPriorityProductHeaderSaved) {
		this.isPriorityProductHeaderSaved = isPriorityProductHeaderSaved;
	}

	/**
	 * @return the isBAConfigRenew
	 */
	public String getIsBAConfigRenew() {
		return isBAConfigRenew;
	}

	/**
	 * @param isBAConfigRenew
	 *            the isBAConfigRenew to set
	 */
	public void setIsBAConfigRenew(String isBAConfigRenew) {
		this.isBAConfigRenew = isBAConfigRenew;
	}

	/**
	 * @return the isRenew
	 */
	public String getIsRenew() {
		return isRenew;
	}

	/**
	 * @param isRenew
	 *            the isRenew to set
	 */
	public void setIsRenew(String isRenew) {
		this.isRenew = isRenew;
	}

	/**
	 * @return the priorityProductHeaderId
	 */
	public Integer getPriorityProductHeaderId() {
		return priorityProductHeaderId;
	}

	/**
	 * @param priorityProductHeaderId
	 *            the priorityProductHeaderId to set
	 */
	public void setPriorityProductHeaderId(Integer priorityProductHeaderId) {
		this.priorityProductHeaderId = priorityProductHeaderId;
	}

	/**
	 * @return the priorityWtSlabList
	 */
	public List<RateWeightSlabsTO> getPriorityWtSlabList() {
		return priorityWtSlabList;
	}

	/**
	 * @param priorityWtSlabList
	 *            the priorityWtSlabList to set
	 */
	public void setPriorityWtSlabList(List<RateWeightSlabsTO> priorityWtSlabList) {
		this.priorityWtSlabList = priorityWtSlabList;
	}

	/**
	 * @return the prioritySectorsList
	 */
	public List<RateSectorsTO> getPrioritySectorsList() {
		return prioritySectorsList;
	}

	/**
	 * @param prioritySectorsList
	 *            the prioritySectorsList to set
	 */
	public void setPrioritySectorsList(List<RateSectorsTO> prioritySectorsList) {
		this.prioritySectorsList = prioritySectorsList;
	}

	/**
	 * @return the productCategoryIdForCourier
	 */
	public Integer getProductCategoryIdForCourier() {
		return productCategoryIdForCourier;
	}

	/**
	 * @param productCategoryIdForCourier
	 *            the productCategoryIdForCourier to set
	 */
	public void setProductCategoryIdForCourier(
			Integer productCategoryIdForCourier) {
		this.productCategoryIdForCourier = productCategoryIdForCourier;
	}

	/**
	 * @return the productCategoryIdForPriority
	 */
	public Integer getProductCategoryIdForPriority() {
		return productCategoryIdForPriority;
	}

	/**
	 * @param productCategoryIdForPriority
	 *            the productCategoryIdForPriority to set
	 */
	public void setProductCategoryIdForPriority(
			Integer productCategoryIdForPriority) {
		this.productCategoryIdForPriority = productCategoryIdForPriority;
	}

	/**
	 * @return the courierProductHeaderId
	 */
	public Integer getCourierProductHeaderId() {
		return courierProductHeaderId;
	}

	/**
	 * @param courierProductHeaderId
	 *            the courierProductHeaderId to set
	 */
	public void setCourierProductHeaderId(Integer courierProductHeaderId) {
		this.courierProductHeaderId = courierProductHeaderId;
	}

	/**
	 * @return the wtSlabList
	 */
	public List<RateWeightSlabsTO> getWtSlabList() {
		return wtSlabList;
	}

	/**
	 * @param wtSlabList
	 *            the wtSlabList to set
	 */
	public void setWtSlabList(List<RateWeightSlabsTO> wtSlabList) {
		this.wtSlabList = wtSlabList;
	}

	/**
	 * @return the sectorsList
	 */
	public List<RateSectorsTO> getSectorsList() {
		return sectorsList;
	}

	/**
	 * @param sectorsList
	 *            the sectorsList to set
	 */
	public void setSectorsList(List<RateSectorsTO> sectorsList) {
		this.sectorsList = sectorsList;
	}

	/**
	 * @return the headerStatus
	 */
	public String getHeaderStatus() {
		return headerStatus;
	}

	/**
	 * @param headerStatus
	 *            the headerStatus to set
	 */
	public void setHeaderStatus(String headerStatus) {
		this.headerStatus = headerStatus;
	}

	/**
	 * @return the isSaved
	 */
	public String getIsSaved() {
		return isSaved;
	}

	/**
	 * @param isSaved
	 *            the isSaved to set
	 */
	public void setIsSaved(String isSaved) {
		this.isSaved = isSaved;
	}

	/**
	 * @return the baRateProductTOList
	 */
	public List<BaRateProductTO> getBaRateProductTOList() {
		return baRateProductTOList;
	}

	/**
	 * @param baRateProductTOList
	 *            the baRateProductTOList to set
	 */
	public void setBaRateProductTOList(List<BaRateProductTO> baRateProductTOList) {
		this.baRateProductTOList = baRateProductTOList;
	}

	/**
	 * @return the headerId
	 */
	public Integer getHeaderId() {
		return headerId;
	}

	/**
	 * @param headerId
	 *            the headerId to set
	 */
	public void setHeaderId(Integer headerId) {
		this.headerId = headerId;
	}

	/**
	 * @return the baCourierFixedChargesTO
	 */
	public BARateConfigFixedChargesTO getBaCourierFixedChargesTO() {
		return baCourierFixedChargesTO;
	}

	/**
	 * @param baCourierFixedChargesTO
	 *            the baCourierFixedChargesTO to set
	 */
	public void setBaCourierFixedChargesTO(
			BARateConfigFixedChargesTO baCourierFixedChargesTO) {
		this.baCourierFixedChargesTO = baCourierFixedChargesTO;
	}

	/**
	 * @return the baCourierRTOChargesTO
	 */
	public BARateConfigRTOChargesTO getBaCourierRTOChargesTO() {
		return baCourierRTOChargesTO;
	}

	/**
	 * @param baCourierRTOChargesTO
	 *            the baCourierRTOChargesTO to set
	 */
	public void setBaCourierRTOChargesTO(
			BARateConfigRTOChargesTO baCourierRTOChargesTO) {
		this.baCourierRTOChargesTO = baCourierRTOChargesTO;
	}

	/**
	 * @return the baPriorityFixedChargesTO
	 */
	public BARateConfigFixedChargesTO getBaPriorityFixedChargesTO() {
		return baPriorityFixedChargesTO;
	}

	/**
	 * @param baPriorityFixedChargesTO
	 *            the baPriorityFixedChargesTO to set
	 */
	public void setBaPriorityFixedChargesTO(
			BARateConfigFixedChargesTO baPriorityFixedChargesTO) {
		this.baPriorityFixedChargesTO = baPriorityFixedChargesTO;
	}

	/**
	 * @return the baPriorityRTOChargesTO
	 */
	public BARateConfigRTOChargesTO getBaPriorityRTOChargesTO() {
		return baPriorityRTOChargesTO;
	}

	/**
	 * @param baPriorityRTOChargesTO
	 *            the baPriorityRTOChargesTO to set
	 */
	public void setBaPriorityRTOChargesTO(
			BARateConfigRTOChargesTO baPriorityRTOChargesTO) {
		this.baPriorityRTOChargesTO = baPriorityRTOChargesTO;
	}

	/**
	 * @return the frmDate
	 */
	public String getFrmDate() {
		return frmDate;
	}

	/**
	 * @param frmDate
	 *            the frmDate to set
	 */
	public void setFrmDate(String frmDate) {
		this.frmDate = frmDate;
	}

	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate
	 *            the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the regionId
	 */
	public Integer getRegionId() {
		return regionId;
	}

	/**
	 * @param regionId
	 *            the regionId to set
	 */
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	/**
	 * @return the cityId
	 */
	public Integer getCityId() {
		return cityId;
	}

	/**
	 * @param cityId
	 *            the cityId to set
	 */
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return the baType
	 */
	public Integer getBaType() {
		return baType;
	}

	/**
	 * @param baType
	 *            the baType to set
	 */
	public void setBaType(Integer baType) {
		this.baType = baType;
	}

	public String getCourierRatesCheck() {
		return courierRatesCheck;
	}

	public void setCourierRatesCheck(String courierRatesCheck) {
		this.courierRatesCheck = courierRatesCheck;
	}

	public String getPriorityBRatesCheck() {
		return priorityBRatesCheck;
	}

	public void setPriorityBRatesCheck(String priorityBRatesCheck) {
		this.priorityBRatesCheck = priorityBRatesCheck;
	}

	public String getPriorityARatesCheck() {
		return priorityARatesCheck;
	}

	public void setPriorityARatesCheck(String priorityARatesCheck) {
		this.priorityARatesCheck = priorityARatesCheck;
	}

	public String getPrioritySRatesCheck() {
		return prioritySRatesCheck;
	}

	public void setPrioritySRatesCheck(String prioritySRatesCheck) {
		this.prioritySRatesCheck = prioritySRatesCheck;
	}

	public String getCourierChargesCheck() {
		return courierChargesCheck;
	}

	public void setCourierChargesCheck(String courierChargesCheck) {
		this.courierChargesCheck = courierChargesCheck;
	}

	public String getPriorityChargesCheck() {
		return priorityChargesCheck;
	}

	public void setPriorityChargesCheck(String priorityChargesCheck) {
		this.priorityChargesCheck = priorityChargesCheck;
	}

	public String getSectorCode() {
		return sectorCode;
	}

	public void setSectorCode(String sectorCode) {
		this.sectorCode = sectorCode;
	}

	public List<StateTO> getStatesList() {
		return statesList;
	}

	public void setStatesList(List<StateTO> statesList) {
		this.statesList = statesList;
	}

	public BARateConfigSlabRateTO getBaAirCargoSlabRateTO() {
		return baAirCargoSlabRateTO;
	}

	public void setBaAirCargoSlabRateTO(
			BARateConfigSlabRateTO baAirCargoSlabRateTO) {
		this.baAirCargoSlabRateTO = baAirCargoSlabRateTO;
	}

	public BARateConfigSlabRateTO getBaTrainSlabRateTO() {
		return baTrainSlabRateTO;
	}

	public void setBaTrainSlabRateTO(BARateConfigSlabRateTO baTrainSlabRateTO) {
		this.baTrainSlabRateTO = baTrainSlabRateTO;
	}

	public String getIsPriorityProduct() {
		return isPriorityProduct;
	}

	public void setIsPriorityProduct(String isPriorityProduct) {
		this.isPriorityProduct = isPriorityProduct;
	}

	public String getPriorityIndicator() {
		return priorityIndicator;
	}

	public void setPriorityIndicator(String priorityIndicator) {
		this.priorityIndicator = priorityIndicator;
	}

	public Integer getTrainProductHeaderId() {
		return trainProductHeaderId;
	}

	public void setTrainProductHeaderId(Integer trainProductHeaderId) {
		this.trainProductHeaderId = trainProductHeaderId;
	}

	public Integer getAirCargoProductHeaderId() {
		return airCargoProductHeaderId;
	}

	public void setAirCargoProductHeaderId(Integer airCargoProductHeaderId) {
		this.airCargoProductHeaderId = airCargoProductHeaderId;
	}

	public Integer getProductCategoryIdForTrain() {
		return productCategoryIdForTrain;
	}

	public void setProductCategoryIdForTrain(Integer productCategoryIdForTrain) {
		this.productCategoryIdForTrain = productCategoryIdForTrain;
	}

	public Integer getProductCategoryIdForAirCargo() {
		return productCategoryIdForAirCargo;
	}

	public void setProductCategoryIdForAirCargo(
			Integer productCategoryIdForAirCargo) {
		this.productCategoryIdForAirCargo = productCategoryIdForAirCargo;
	}

	public List<RateWeightSlabsTO> getTrainWtSlabList() {
		return trainWtSlabList;
	}

	public void setTrainWtSlabList(List<RateWeightSlabsTO> trainWtSlabList) {
		this.trainWtSlabList = trainWtSlabList;
	}

	public List<RateSectorsTO> getTrainSectorsList() {
		return trainSectorsList;
	}

	public void setTrainSectorsList(List<RateSectorsTO> trainSectorsList) {
		this.trainSectorsList = trainSectorsList;
	}

	public List<RateWeightSlabsTO> getAirCargoWtSlabList() {
		return airCargoWtSlabList;
	}

	public void setAirCargoWtSlabList(List<RateWeightSlabsTO> airCargoWtSlabList) {
		this.airCargoWtSlabList = airCargoWtSlabList;
	}

	public List<RateSectorsTO> getAirCargoSectorsList() {
		return airCargoSectorsList;
	}

	public void setAirCargoSectorsList(List<RateSectorsTO> airCargoSectorsList) {
		this.airCargoSectorsList = airCargoSectorsList;
	}

	public String getCommonProdCatCode() {
		return commonProdCatCode;
	}

	public void setCommonProdCatCode(String commonProdCatCode) {
		this.commonProdCatCode = commonProdCatCode;
	}

	public Integer getWtSlabMinChrgTR() {
		return wtSlabMinChrgTR;
	}

	public void setWtSlabMinChrgTR(Integer wtSlabMinChrgTR) {
		this.wtSlabMinChrgTR = wtSlabMinChrgTR;
	}

	public Integer getWtSlabMinChrgAR() {
		return wtSlabMinChrgAR;
	}

	public void setWtSlabMinChrgAR(Integer wtSlabMinChrgAR) {
		this.wtSlabMinChrgAR = wtSlabMinChrgAR;
	}

	public Integer getWtSlabMinChrgCR() {
		return wtSlabMinChrgCR;
	}

	public void setWtSlabMinChrgCR(Integer wtSlabMinChrgCR) {
		this.wtSlabMinChrgCR = wtSlabMinChrgCR;
	}

	public Integer getZeroWeightSlabId() {
		return zeroWeightSlabId;
	}

	public void setZeroWeightSlabId(Integer zeroWeightSlabId) {
		this.zeroWeightSlabId = zeroWeightSlabId;
	}

	public Integer getLoggedInUserId() {
		return loggedInUserId;
	}

	public void setLoggedInUserId(Integer loggedInUserId) {
		this.loggedInUserId = loggedInUserId;
	}

}
