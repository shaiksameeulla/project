package com.ff.domain.ratemanagement.operations.cash;

import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.ratemanagement.masters.RateProductCategoryDO;

/**
 * @author hkansagr
 */

public class CashRateConfigHeaderProductDO extends CGFactDO {

	private static final long serialVersionUID = 1L;
	
	/** The headerProductMapId. */
	private Integer headerProductMapId;
	
	/** The headerDO. */
	private CashRateConfigHeaderDO headerDO;
	
	/** The productId. */
	//private Integer productId;
	/** The rateProductDO. */
	private RateProductCategoryDO rateProductDO;
	private Integer minChargeableWeight;
	
	private Set<CashRateSlabRateDO> cashRateSlabRateDOs;
	private Set<CashRateSpecialDestinationDO> cashRateSplDestDOs;
	
	//NOT USED IN HBM FILE 
	private Set<CashRateConfigFixedChargesDO> cashRateFixedChrgsDOs;
	private Set<CashRateConfigAdditionalChargesDO> cashRateAdditionalChrgsDOs;
	private Set<CashRateConfigRTOChargesDO> cashRateRtoChrgsDOs;

	
	
	/**
	 * @return the minChargeableWeight
	 */
	public Integer getMinChargeableWeight() {
		return minChargeableWeight;
	}
	/**
	 * @param minChargeableWeight the minChargeableWeight to set
	 */
	public void setMinChargeableWeight(Integer minChargeableWeight) {
		this.minChargeableWeight = minChargeableWeight;
	}
	/**
	 * @return the cashRateFixedChrgsDOs
	 */
	public Set<CashRateConfigFixedChargesDO> getCashRateFixedChrgsDOs() {
		return cashRateFixedChrgsDOs;
	}
	/**
	 * @param cashRateFixedChrgsDOs the cashRateFixedChrgsDOs to set
	 */
	public void setCashRateFixedChrgsDOs(
			Set<CashRateConfigFixedChargesDO> cashRateFixedChrgsDOs) {
		this.cashRateFixedChrgsDOs = cashRateFixedChrgsDOs;
	}
	/**
	 * @return the cashRateAdditionalChrgsDOs
	 */
	public Set<CashRateConfigAdditionalChargesDO> getCashRateAdditionalChrgsDOs() {
		return cashRateAdditionalChrgsDOs;
	}
	/**
	 * @param cashRateAdditionalChrgsDOs the cashRateAdditionalChrgsDOs to set
	 */
	public void setCashRateAdditionalChrgsDOs(
			Set<CashRateConfigAdditionalChargesDO> cashRateAdditionalChrgsDOs) {
		this.cashRateAdditionalChrgsDOs = cashRateAdditionalChrgsDOs;
	}
	/**
	 * @return the cashRateRtoChrgsDOs
	 */
	public Set<CashRateConfigRTOChargesDO> getCashRateRtoChrgsDOs() {
		return cashRateRtoChrgsDOs;
	}
	/**
	 * @param cashRateRtoChrgsDOs the cashRateRtoChrgsDOs to set
	 */
	public void setCashRateRtoChrgsDOs(
			Set<CashRateConfigRTOChargesDO> cashRateRtoChrgsDOs) {
		this.cashRateRtoChrgsDOs = cashRateRtoChrgsDOs;
	}
	/**
	 * @return the cashRateSlabRateDOs
	 */
	public Set<CashRateSlabRateDO> getCashRateSlabRateDOs() {
		return cashRateSlabRateDOs;
	}
	/**
	 * @param cashRateSlabRateDOs the cashRateSlabRateDOs to set
	 */
	public void setCashRateSlabRateDOs(Set<CashRateSlabRateDO> cashRateSlabRateDOs) {
		this.cashRateSlabRateDOs = cashRateSlabRateDOs;
	}
	/**
	 * @return the cashRateSplDestDOs
	 */
	public Set<CashRateSpecialDestinationDO> getCashRateSplDestDOs() {
		return cashRateSplDestDOs;
	}
	/**
	 * @param cashRateSplDestDOs the cashRateSplDestDOs to set
	 */
	public void setCashRateSplDestDOs(
			Set<CashRateSpecialDestinationDO> cashRateSplDestDOs) {
		this.cashRateSplDestDOs = cashRateSplDestDOs;
	}
	/**
	 * @return the headerDO
	 */
	public CashRateConfigHeaderDO getHeaderDO() {
		return headerDO;
	}
	/**
	 * @param headerDO the headerDO to set
	 */
	public void setHeaderDO(CashRateConfigHeaderDO headerDO) {
		this.headerDO = headerDO;
	}
	/**
	 * @return the headerProductMapId
	 */
	public Integer getHeaderProductMapId() {
		return headerProductMapId;
	}
	/**
	 * @param headerProductMapId the headerProductMapId to set
	 */
	public void setHeaderProductMapId(Integer headerProductMapId) {
		this.headerProductMapId = headerProductMapId;
	}
	/**
	 * @return the rateProductDO
	 */
	public RateProductCategoryDO getRateProductDO() {
		return rateProductDO;
	}
	/**
	 * @param rateProductDO the rateProductDO to set
	 */
	public void setRateProductDO(RateProductCategoryDO rateProductDO) {
		this.rateProductDO = rateProductDO;
	}
	
}
