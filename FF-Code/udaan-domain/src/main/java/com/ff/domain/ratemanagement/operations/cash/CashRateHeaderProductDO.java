package com.ff.domain.ratemanagement.operations.cash;

import java.util.List;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.ratemanagement.masters.EBRatePreferenceDO;
import com.ff.domain.ratemanagement.masters.RateMinChargeableWeightDO;
import com.ff.domain.ratemanagement.masters.RateProductCategoryDO;

public class CashRateHeaderProductDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3009264567546235163L;
	private Integer headerProductMapId;
	private CashRateHeaderDO headerDO;
	private RateProductCategoryDO rateProductCategoryDO;
	private RateMinChargeableWeightDO minWtDo;
	private RateMinChargeableWeightDO minChargeableWeightDO;
	
	//Transient object.
	private List<EBRatePreferenceDO> ebPreferences;
	
	public List<EBRatePreferenceDO> getEbPreferences() {
		return ebPreferences;
	}
	public void setEbPreferences(List<EBRatePreferenceDO> ebPreferences) {
		this.ebPreferences = ebPreferences;
	}
	public Integer getHeaderProductMapId() {
		return headerProductMapId;
	}
	public void setHeaderProductMapId(Integer headerProductMapId) {
		this.headerProductMapId = headerProductMapId;
	}

	public CashRateHeaderDO getHeaderDO() {
		return headerDO;
	}
	public void setHeaderDO(CashRateHeaderDO headerDO) {
		this.headerDO = headerDO;
	}
	
	/**
	 * @return the rateProductCategoryDO
	 */
	public RateProductCategoryDO getRateProductCategoryDO() {
		return rateProductCategoryDO;
	}
	/**
	 * @param rateProductCategoryDO the rateProductCategoryDO to set
	 */
	public void setRateProductCategoryDO(RateProductCategoryDO rateProductCategoryDO) {
		this.rateProductCategoryDO = rateProductCategoryDO;
	}
	public RateMinChargeableWeightDO getMinWtDo() {
		return minWtDo;
	}
	public void setMinWtDo(RateMinChargeableWeightDO minWtDo) {
		this.minWtDo = minWtDo;
	}
	/**
	 * @return the minChargeableWeightDO
	 */
	public RateMinChargeableWeightDO getMinChargeableWeightDO() {
		return minChargeableWeightDO;
	}
	/**
	 * @param minChargeableWeightDO the minChargeableWeightDO to set
	 */
	public void setMinChargeableWeightDO(
			RateMinChargeableWeightDO minChargeableWeightDO) {
		this.minChargeableWeightDO = minChargeableWeightDO;
	}
}
