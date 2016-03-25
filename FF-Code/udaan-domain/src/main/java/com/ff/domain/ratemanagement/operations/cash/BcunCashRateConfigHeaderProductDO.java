package com.ff.domain.ratemanagement.operations.cash;

import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author prmeher
 */

public class BcunCashRateConfigHeaderProductDO extends CGFactDO {

	private static final long serialVersionUID = 1L;
	
	
	/** The headerProductMapId. */
	private Integer headerProductMapId;
	
	/** The headerDO. */
	private Integer headerDO;
	
	/** The productId. */
	//private Integer productId;
	/** The rateProductDO. */
	private Integer rateProductDO;
	private Integer minChargeableWeight;
	
	private Set<BcunCashRateSlabRateDO> cashRateSlabRateDOs;
	private Set<BcunCashRateSpecialDestinationDO> cashRateSplDestDOs;
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
	 * @return the headerDO
	 */
	public Integer getHeaderDO() {
		return headerDO;
	}
	/**
	 * @param headerDO the headerDO to set
	 */
	public void setHeaderDO(Integer headerDO) {
		this.headerDO = headerDO;
	}
	/**
	 * @return the rateProductDO
	 */
	public Integer getRateProductDO() {
		return rateProductDO;
	}
	/**
	 * @param rateProductDO the rateProductDO to set
	 */
	public void setRateProductDO(Integer rateProductDO) {
		this.rateProductDO = rateProductDO;
	}
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
	 * @return the cashRateSlabRateDOs
	 */
	public Set<BcunCashRateSlabRateDO> getCashRateSlabRateDOs() {
		return cashRateSlabRateDOs;
	}
	/**
	 * @param cashRateSlabRateDOs the cashRateSlabRateDOs to set
	 */
	public void setCashRateSlabRateDOs(
			Set<BcunCashRateSlabRateDO> cashRateSlabRateDOs) {
		this.cashRateSlabRateDOs = cashRateSlabRateDOs;
	}
	/**
	 * @return the cashRateSplDestDOs
	 */
	public Set<BcunCashRateSpecialDestinationDO> getCashRateSplDestDOs() {
		return cashRateSplDestDOs;
	}
	/**
	 * @param cashRateSplDestDOs the cashRateSplDestDOs to set
	 */
	public void setCashRateSplDestDOs(
			Set<BcunCashRateSpecialDestinationDO> cashRateSplDestDOs) {
		this.cashRateSplDestDOs = cashRateSplDestDOs;
	}
	
}
