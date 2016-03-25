package com.ff.to.ratemanagement.operations.rateconfiguration;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.capgemini.lbs.framework.utils.StringUtil;

/**
 * @author hkansagr
 */

public class CashRateConfigHeaderTO extends CGBaseTO {
	
	private static final long serialVersionUID = 1L;
	
	private Integer cashRateHeaderId;//The cashRateHeaderId - PK.
	private Integer regionId;// The Region Id.
	private String fromDateStr;//The from date string.
	private String toDateStr;//The to date string.
	private String headerStatus;//The header status.
	
	private String productCode;//CO-Courier, AR-Air-Cargo, TR-Train, PR-Priority
	private String servicedOn;//B- Before 14, A- After 14, S- Sunday
	private String productCatType;//N-Non-Priority, P-Priority
	private String transMsg;
	private String errorMsg;
	private String isDelReq;//Y or N
	private String isRenew;//Y or N
	//The cashRateHeaderId - PK for Previous/ Last Active Contract
	private Integer prevCashRateHeaderId;
	
	/** The Common Based TOs. */
	private CashRateSlabRateTO commonBasedSlabRateTO;
	private CashRateSpecialDestinationTO commonBasedSplDestTO;
	private CashRateConfigProductTO commonBasedProductTO;
	private CashRateConfigFixedChargesTO commonBasedFixedChargesTO;
	private CashRateConfigRTOChargesTO commonBasedRtoChargesTO;
	
	/** The Courier TOs. */
	private CashRateSlabRateTO courierSlabRateTO;
	private CashRateSpecialDestinationTO courierSplDestTO;
	private CashRateConfigProductTO courierProductTO;
	
	/** The Air-Cargo TOs. */
	private CashRateSlabRateTO airCargoSlabRateTO;
	private CashRateSpecialDestinationTO airCargoSplDestTO;
	private CashRateConfigProductTO airCargoProductTO;
	
	/** The Train TOs. */
	private CashRateSlabRateTO trainSlabRateTO;
	private CashRateSpecialDestinationTO trainSplDestTO;
	private CashRateConfigProductTO trainProductTO;
	
	private CashRateConfigFixedChargesTO fixedChargesTO;
	private CashRateConfigRTOChargesTO rtoChargesTO;
	
	/** The Priority TOs. */
	private CashRateSlabRateTO prioritySlabRateTO;
	private CashRateSpecialDestinationTO prioritySplDestTO;
	private CashRateConfigProductTO priorityProductTO;
	
	private CashRateConfigFixedChargesTO priorityFixedChargesTO;
	private CashRateConfigRTOChargesTO priorityRtoChargesTO;
	
	/** The CashRateConfigProductTO List. */
	List<CashRateConfigProductTO> cashRateProductTOList;
	/** The CashRateConfigRTOChargesTO List. */
	List<CashRateConfigRTOChargesTO> rtoChrgsTOList;
	
	String nonPriorityRatesCheck = "N";
	String priorityRatesCheck = "N";
	String priorityBRatesCheck = "N";
	String priorityARatesCheck = "N";
	String prioritySRatesCheck = "N";
	String nonPriorityChargesCheck = "N";
	String priorityChargesCheck = "N";
	Integer sectorId;
	
	/**
	 * @return the prevCashRateHeaderId
	 */
	public Integer getPrevCashRateHeaderId() {
		return prevCashRateHeaderId;
	}

	/**
	 * @param prevCashRateHeaderId the prevCashRateHeaderId to set
	 */
	public void setPrevCashRateHeaderId(Integer prevCashRateHeaderId) {
		this.prevCashRateHeaderId = prevCashRateHeaderId;
	}

	/**
	 * @return the isRenew
	 */
	public String getIsRenew() {
		return isRenew;
	}

	/**
	 * @param isRenew the isRenew to set
	 */
	public void setIsRenew(String isRenew) {
		this.isRenew = isRenew;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * @return the isDelReq
	 */
	public String getIsDelReq() {
		return isDelReq;
	}

	/**
	 * @param isDelReq the isDelReq to set
	 */
	public void setIsDelReq(String isDelReq) {
		this.isDelReq = isDelReq;
	}

	/**
	 * @return the transMsg
	 */
	public String getTransMsg() {
		return transMsg;
	}

	/**
	 * @param transMsg the transMsg to set
	 */
	public void setTransMsg(String transMsg) {
		this.transMsg = transMsg;
	}

	/**
	 * @return the rtoChrgsTOList
	 */
	public List<CashRateConfigRTOChargesTO> getRtoChrgsTOList() {
		return rtoChrgsTOList;
	}

	/**
	 * @param rtoChrgsTOList the rtoChrgsTOList to set
	 */
	public void setRtoChrgsTOList(List<CashRateConfigRTOChargesTO> rtoChrgsTOList) {
		this.rtoChrgsTOList = rtoChrgsTOList;
	}

	/**
	 * @return the productCatType
	 */
	public String getProductCatType() {
		return productCatType;
	}

	/**
	 * @param productCatType the productCatType to set
	 */
	public void setProductCatType(String productCatType) {
		this.productCatType = productCatType;
	}

	/**
	 * @return the servicedOn
	 */
	public String getServicedOn() {
		return servicedOn;
	}

	/**
	 * @param servicedOn the servicedOn to set
	 */
	public void setServicedOn(String servicedOn) {
		this.servicedOn = servicedOn;
	}

	/**
	 * @return the cashRateProductTOList
	 */
	public List<CashRateConfigProductTO> getCashRateProductTOList() {
		return cashRateProductTOList;
	}

	/**
	 * @param cashRateProductTOList the cashRateProductTOList to set
	 */
	public void setCashRateProductTOList(
			List<CashRateConfigProductTO> cashRateProductTOList) {
		this.cashRateProductTOList = cashRateProductTOList;
	}

	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return the commonBasedFixedChargesTO
	 */
	public CashRateConfigFixedChargesTO getCommonBasedFixedChargesTO() {
		if(StringUtil.isNull(commonBasedFixedChargesTO)){
			commonBasedFixedChargesTO = new CashRateConfigFixedChargesTO();
		}
		return commonBasedFixedChargesTO;
	}

	/**
	 * @param commonBasedFixedChargesTO the commonBasedFixedChargesTO to set
	 */
	public void setCommonBasedFixedChargesTO(
			CashRateConfigFixedChargesTO commonBasedFixedChargesTO) {
		this.commonBasedFixedChargesTO = commonBasedFixedChargesTO;
	}

	/**
	 * @return the commonBasedRtoChargesTO
	 */
	public CashRateConfigRTOChargesTO getCommonBasedRtoChargesTO() {
		if(StringUtil.isNull(commonBasedRtoChargesTO)){
			commonBasedRtoChargesTO = new CashRateConfigRTOChargesTO();
		}
		return commonBasedRtoChargesTO;
	}

	/**
	 * @param commonBasedRtoChargesTO the commonBasedRtoChargesTO to set
	 */
	public void setCommonBasedRtoChargesTO(
			CashRateConfigRTOChargesTO commonBasedRtoChargesTO) {
		this.commonBasedRtoChargesTO = commonBasedRtoChargesTO;
	}

	/**
	 * @return the airCargoSlabRateTO
	 */
	public CashRateSlabRateTO getAirCargoSlabRateTO() {
		if(StringUtil.isNull(airCargoSlabRateTO)){
			airCargoSlabRateTO = new CashRateSlabRateTO();
		}
		return airCargoSlabRateTO;
	}

	/**
	 * @param airCargoSlabRateTO the airCargoSlabRateTO to set
	 */
	public void setAirCargoSlabRateTO(CashRateSlabRateTO airCargoSlabRateTO) {
		this.airCargoSlabRateTO = airCargoSlabRateTO;
	}

	/**
	 * @return the airCargoSplDestTO
	 */
	public CashRateSpecialDestinationTO getAirCargoSplDestTO() {
		if(StringUtil.isNull(airCargoSplDestTO)){
			airCargoSplDestTO = new CashRateSpecialDestinationTO();
		}
		return airCargoSplDestTO;
	}

	/**
	 * @param airCargoSplDestTO the airCargoSplDestTO to set
	 */
	public void setAirCargoSplDestTO(CashRateSpecialDestinationTO airCargoSplDestTO) {
		this.airCargoSplDestTO = airCargoSplDestTO;
	}

	/**
	 * @return the airCargoProductTO
	 */
	public CashRateConfigProductTO getAirCargoProductTO() {
		if(StringUtil.isNull(airCargoProductTO)){
			airCargoProductTO = new CashRateConfigProductTO();
		}
		return airCargoProductTO;
	}

	/**
	 * @param airCargoProductTO the airCargoProductTO to set
	 */
	public void setAirCargoProductTO(CashRateConfigProductTO airCargoProductTO) {
		this.airCargoProductTO = airCargoProductTO;
	}

	/**
	 * @return the trainSlabRateTO
	 */
	public CashRateSlabRateTO getTrainSlabRateTO() {
		if(StringUtil.isNull(trainSlabRateTO)){
			trainSlabRateTO = new CashRateSlabRateTO();
		}
		return trainSlabRateTO;
	}

	/**
	 * @param trainSlabRateTO the trainSlabRateTO to set
	 */
	public void setTrainSlabRateTO(CashRateSlabRateTO trainSlabRateTO) {
		this.trainSlabRateTO = trainSlabRateTO;
	}

	/**
	 * @return the trainSplDestTO
	 */
	public CashRateSpecialDestinationTO getTrainSplDestTO() {
		if(StringUtil.isNull(trainSplDestTO)){
			trainSplDestTO = new CashRateSpecialDestinationTO();
		}
		return trainSplDestTO;
	}

	/**
	 * @param trainSplDestTO the trainSplDestTO to set
	 */
	public void setTrainSplDestTO(CashRateSpecialDestinationTO trainSplDestTO) {
		this.trainSplDestTO = trainSplDestTO;
	}

	/**
	 * @return the trainProductTO
	 */
	public CashRateConfigProductTO getTrainProductTO() {
		if(StringUtil.isNull(trainProductTO)){
			trainProductTO = new CashRateConfigProductTO();
		}
		return trainProductTO;
	}

	/**
	 * @param trainProductTO the trainProductTO to set
	 */
	public void setTrainProductTO(CashRateConfigProductTO trainProductTO) {
		this.trainProductTO = trainProductTO;
	}

	/**
	 * @return the prioritySlabRateTO
	 */
	public CashRateSlabRateTO getPrioritySlabRateTO() {
		if(StringUtil.isNull(prioritySlabRateTO)){
			prioritySlabRateTO = new CashRateSlabRateTO();
		}
		return prioritySlabRateTO;
	}

	/**
	 * @param prioritySlabRateTO the prioritySlabRateTO to set
	 */
	public void setPrioritySlabRateTO(CashRateSlabRateTO prioritySlabRateTO) {
		this.prioritySlabRateTO = prioritySlabRateTO;
	}

	/**
	 * @return the prioritySplDestTO
	 */
	public CashRateSpecialDestinationTO getPrioritySplDestTO() {
		if(StringUtil.isNull(prioritySplDestTO)){
			prioritySplDestTO = new CashRateSpecialDestinationTO();
		}
		return prioritySplDestTO;
	}

	/**
	 * @param prioritySplDestTO the prioritySplDestTO to set
	 */
	public void setPrioritySplDestTO(CashRateSpecialDestinationTO prioritySplDestTO) {
		this.prioritySplDestTO = prioritySplDestTO;
	}

	/**
	 * @return the priorityProductTO
	 */
	public CashRateConfigProductTO getPriorityProductTO() {
		if(StringUtil.isNull(priorityProductTO)){
			priorityProductTO = new CashRateConfigProductTO();
		}
		return priorityProductTO;
	}

	/**
	 * @param priorityProductTO the priorityProductTO to set
	 */
	public void setPriorityProductTO(CashRateConfigProductTO priorityProductTO) {
		this.priorityProductTO = priorityProductTO;
	}

	/**
	 * @return the priorityFixedChargesTO
	 */
	public CashRateConfigFixedChargesTO getPriorityFixedChargesTO() {
		if(StringUtil.isNull(priorityFixedChargesTO)){
			priorityFixedChargesTO = new CashRateConfigFixedChargesTO();
		}
		return priorityFixedChargesTO;
	}

	/**
	 * @param priorityFixedChargesTO the priorityFixedChargesTO to set
	 */
	public void setPriorityFixedChargesTO(
			CashRateConfigFixedChargesTO priorityFixedChargesTO) {
		this.priorityFixedChargesTO = priorityFixedChargesTO;
	}

	/**
	 * @return the priorityRtoChargesTO
	 */
	public CashRateConfigRTOChargesTO getPriorityRtoChargesTO() {
		if(StringUtil.isNull(priorityRtoChargesTO)){
			priorityRtoChargesTO = new CashRateConfigRTOChargesTO();
		}
		return priorityRtoChargesTO;
	}

	/**
	 * @param priorityRtoChargesTO the priorityRtoChargesTO to set
	 */
	public void setPriorityRtoChargesTO(
			CashRateConfigRTOChargesTO priorityRtoChargesTO) {
		this.priorityRtoChargesTO = priorityRtoChargesTO;
	}

	/**
	 * @return the commonBasedSlabRateTO
	 */
	public CashRateSlabRateTO getCommonBasedSlabRateTO() {
		if(StringUtil.isNull(commonBasedSlabRateTO)){
			commonBasedSlabRateTO = new CashRateSlabRateTO();
		}
		return commonBasedSlabRateTO;
	}

	/**
	 * @param commonBasedSlabRateTO the commonBasedSlabRateTO to set
	 */
	public void setCommonBasedSlabRateTO(CashRateSlabRateTO commonBasedSlabRateTO) {
		this.commonBasedSlabRateTO = commonBasedSlabRateTO;
	}

	/**
	 * @return the commonBasedSplDestTO
	 */
	public CashRateSpecialDestinationTO getCommonBasedSplDestTO() {
		if(StringUtil.isNull(commonBasedSplDestTO)){
			commonBasedSplDestTO = new CashRateSpecialDestinationTO();
		}
		return commonBasedSplDestTO;
	}

	/**
	 * @param commonBasedSplDestTO the commonBasedSplDestTO to set
	 */
	public void setCommonBasedSplDestTO(
			CashRateSpecialDestinationTO commonBasedSplDestTO) {
		this.commonBasedSplDestTO = commonBasedSplDestTO;
	}

	/**
	 * @return the commonBasedProductTO
	 */
	public CashRateConfigProductTO getCommonBasedProductTO() {
		if(StringUtil.isNull(commonBasedProductTO)){
			commonBasedProductTO = new CashRateConfigProductTO();
		}
		return commonBasedProductTO;
	}

	/**
	 * @param commonBasedProductTO the commonBasedProductTO to set
	 */
	public void setCommonBasedProductTO(CashRateConfigProductTO commonBasedProductTO) {
		this.commonBasedProductTO = commonBasedProductTO;
	}

	/**
	 * @return the courierProductTO
	 */
	public CashRateConfigProductTO getCourierProductTO() {
		if(StringUtil.isNull(courierProductTO)){
			courierProductTO = new CashRateConfigProductTO();
		}
		return courierProductTO;
	}

	/**
	 * @param courierProductTO the courierProductTO to set
	 */
	public void setCourierProductTO(CashRateConfigProductTO courierProductTO) {
		this.courierProductTO = courierProductTO;
	}

	/**
	 * @return the courierSlabRateTO
	 */
	public CashRateSlabRateTO getCourierSlabRateTO() {
		if(StringUtil.isNull(courierSlabRateTO)){
			courierSlabRateTO = new CashRateSlabRateTO();
		}
		return courierSlabRateTO;
	}

	/**
	 * @param courierSlabRateTO the courierSlabRateTO to set
	 */
	public void setCourierSlabRateTO(CashRateSlabRateTO courierSlabRateTO) {
		this.courierSlabRateTO = courierSlabRateTO;
	}

	/**
	 * @return the courierSplDestTO
	 */
	public CashRateSpecialDestinationTO getCourierSplDestTO() {
		if(StringUtil.isNull(courierSplDestTO)){
			courierSplDestTO = new CashRateSpecialDestinationTO();
		}
		return courierSplDestTO;
	}

	/**
	 * @param courierSplDestTO the courierSplDestTO to set
	 */
	public void setCourierSplDestTO(CashRateSpecialDestinationTO courierSplDestTO) {
		this.courierSplDestTO = courierSplDestTO;
	}

	/**
	 * @return the fixedChargesTO
	 */
	public CashRateConfigFixedChargesTO getFixedChargesTO() {
		if(StringUtil.isNull(fixedChargesTO)){
			fixedChargesTO = new CashRateConfigFixedChargesTO();
		}
		return fixedChargesTO;
	}

	/**
	 * @param fixedChargesTO the fixedChargesTO to set
	 */
	public void setFixedChargesTO(CashRateConfigFixedChargesTO fixedChargesTO) {
		this.fixedChargesTO = fixedChargesTO;
	}

	/**
	 * @return the rtoChargesTO
	 */
	public CashRateConfigRTOChargesTO getRtoChargesTO() {
		if(StringUtil.isNull(rtoChargesTO)){
			rtoChargesTO = new CashRateConfigRTOChargesTO();
		}
		return rtoChargesTO;
	}

	/**
	 * @param rtoChargesTO the rtoChargesTO to set
	 */
	public void setRtoChargesTO(CashRateConfigRTOChargesTO rtoChargesTO) {
		this.rtoChargesTO = rtoChargesTO;
	}

	/**
	 * @return the cashRateHeaderId
	 */
	public Integer getCashRateHeaderId() {
		return cashRateHeaderId;
	}

	/**
	 * @param cashRateHeaderId the cashRateHeaderId to set
	 */
	public void setCashRateHeaderId(Integer cashRateHeaderId) {
		this.cashRateHeaderId = cashRateHeaderId;
	}

	/**
	 * @return the regionId
	 */
	public Integer getRegionId() {
		return regionId;
	}

	/**
	 * @param regionId the regionId to set
	 */
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	/**
	 * @return the fromDateStr
	 */
	public String getFromDateStr() {
		return fromDateStr;
	}

	/**
	 * @param fromDateStr the fromDateStr to set
	 */
	public void setFromDateStr(String fromDateStr) {
		this.fromDateStr = fromDateStr;
	}

	/**
	 * @return the toDateStr
	 */
	public String getToDateStr() {
		return toDateStr;
	}

	/**
	 * @param toDateStr the toDateStr to set
	 */
	public void setToDateStr(String toDateStr) {
		this.toDateStr = toDateStr;
	}

	/**
	 * @return the headerStatus
	 */
	public String getHeaderStatus() {
		return headerStatus;
	}

	/**
	 * @param headerStatus the headerStatus to set
	 */
	public void setHeaderStatus(String headerStatus) {
		this.headerStatus = headerStatus;
	}

	public String getNonPriorityRatesCheck() {
		return nonPriorityRatesCheck;
	}

	public void setNonPriorityRatesCheck(String nonPriorityRatesCheck) {
		this.nonPriorityRatesCheck = nonPriorityRatesCheck;
	}

	public String getPriorityRatesCheck() {
		return priorityRatesCheck;
	}

	public void setPriorityRatesCheck(String priorityRatesCheck) {
		this.priorityRatesCheck = priorityRatesCheck;
	}

	public String getNonPriorityChargesCheck() {
		return nonPriorityChargesCheck;
	}

	public void setNonPriorityChargesCheck(String nonPriorityChargesCheck) {
		this.nonPriorityChargesCheck = nonPriorityChargesCheck;
	}

	public String getPriorityChargesCheck() {
		return priorityChargesCheck;
	}

	public void setPriorityChargesCheck(String priorityChargesCheck) {
		this.priorityChargesCheck = priorityChargesCheck;
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

	public Integer getSectorId() {
		return sectorId;
	}

	public void setSectorId(Integer sectorId) {
		this.sectorId = sectorId;
	}
	
}
