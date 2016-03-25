package com.ff.domain.ratemanagement.operations.ba;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.business.CustomerTypeDO;
import com.ff.domain.ratemanagement.masters.RateCustomerCategoryDO;
import com.ff.domain.ratemanagement.masters.RateMinChargeableWeightDO;
import com.ff.domain.ratemanagement.operations.ratecalculation.BARateConfigRTOChargesDO;
import com.ff.domain.ratemanagement.operations.ratecalculation.BARateConfigurationFixedChargesConfigDO;

public class BaRateConfigHeaderDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2683839339561906838L;
	private Integer headerId;
	private Integer baTypeId;
	private Integer cityId;
	private Date fromDate;
	private Date toDate;
	private RateMinChargeableWeightDO minWtDo;
	private RateCustomerCategoryDO rateCustomerCategory;
	private CustomerTypeDO customerTypeDO;

	private Set<BaRateConfigProductHeaderDO> baRateProductDO;
	
	private Set<BARateConfigAdditionalChargesDO> baAdditionalChargesDO;
	private Set<BARateConfigRTOChargesDO> baRateRTOChargesDO;
	private Set<BARateConfigurationFixedChargesConfigDO> fixedChargeConfig;
	private Set<BARateConfigCODChargesDO> baCODChargesDOs;
	

	private String headerStatus;
	private String isRenew;
	
	//NOT USED IN HBM FILE/DB CONNECTION
	private List<BARateConfigurationFixedChargesConfigDO> fixedConfig;
	private List<BARateConfigAdditionalChargesDO> baRateConfigAdditionalCharges;
	private List<BARateConfigCODChargesDO> codCharges;

	
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
	 * @return the customerTypeDO
	 */
	public CustomerTypeDO getCustomerTypeDO() {
		return customerTypeDO;
	}

	/**
	 * @param customerTypeDO
	 *            the customerTypeDO to set
	 */
	public void setCustomerTypeDO(CustomerTypeDO customerTypeDO) {
		this.customerTypeDO = customerTypeDO;
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
	 * @return the baTypeId
	 */
	public Integer getBaTypeId() {
		return baTypeId;
	}

	/**
	 * @param baTypeId
	 *            the baTypeId to set
	 */
	public void setBaTypeId(Integer baTypeId) {
		this.baTypeId = baTypeId;
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
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate
	 *            the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @param toDate
	 *            the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the minWtDo
	 */
	public RateMinChargeableWeightDO getMinWtDo() {
		return minWtDo;
	}

	/**
	 * @param minWtDo
	 *            the minWtDo to set
	 */
	public void setMinWtDo(RateMinChargeableWeightDO minWtDo) {
		this.minWtDo = minWtDo;
	}

	/**
	 * @return the rateCustomerCategory
	 */
	public RateCustomerCategoryDO getRateCustomerCategory() {
		return rateCustomerCategory;
	}

	/**
	 * @param rateCustomerCategory
	 *            the rateCustomerCategory to set
	 */
	public void setRateCustomerCategory(
			RateCustomerCategoryDO rateCustomerCategory) {
		this.rateCustomerCategory = rateCustomerCategory;
	}

	/**
	 * @return the baRateProductDO
	 */
	public Set<BaRateConfigProductHeaderDO> getBaRateProductDO() {
		return baRateProductDO;
	}

	/**
	 * @param baRateProductDO
	 *            the baRateProductDO to set
	 */
	public void setBaRateProductDO(
			Set<BaRateConfigProductHeaderDO> baRateProductDO) {
		this.baRateProductDO = baRateProductDO;
	}

	public Set<BARateConfigAdditionalChargesDO> getBaAdditionalChargesDO() {
		return baAdditionalChargesDO;
	}

	public void setBaAdditionalChargesDO(
			Set<BARateConfigAdditionalChargesDO> baAdditionalChargesDO) {
		this.baAdditionalChargesDO = baAdditionalChargesDO;
	}

	public Set<BARateConfigRTOChargesDO> getBaRateRTOChargesDO() {
		return baRateRTOChargesDO;
	}

	public void setBaRateRTOChargesDO(
			Set<BARateConfigRTOChargesDO> baRateRTOChargesDO) {
		this.baRateRTOChargesDO = baRateRTOChargesDO;
	}

	public Set<BARateConfigurationFixedChargesConfigDO> getFixedChargeConfig() {
		return fixedChargeConfig;
	}

	public void setFixedChargeConfig(
			Set<BARateConfigurationFixedChargesConfigDO> fixedChargeConfig) {
		this.fixedChargeConfig = fixedChargeConfig;
	}

	public List<BARateConfigurationFixedChargesConfigDO> getFixedConfig() {
		return fixedConfig;
	}

	public void setFixedConfig(
			List<BARateConfigurationFixedChargesConfigDO> fixedConfig) {
		this.fixedConfig = fixedConfig;
	}

	public List<BARateConfigAdditionalChargesDO> getBaRateConfigAdditionalCharges() {
		return baRateConfigAdditionalCharges;
	}

	public void setBaRateConfigAdditionalCharges(
			List<BARateConfigAdditionalChargesDO> baRateConfigAdditionalCharges) {
		this.baRateConfigAdditionalCharges = baRateConfigAdditionalCharges;
	}

	public Set<BARateConfigCODChargesDO> getBaCODChargesDOs() {
		return baCODChargesDOs;
	}

	public void setBaCODChargesDOs(Set<BARateConfigCODChargesDO> baCODChargesDOs) {
		this.baCODChargesDOs = baCODChargesDOs;
	}

	public List<BARateConfigCODChargesDO> getCodCharges() {
		return codCharges;
	}

	public void setCodCharges(List<BARateConfigCODChargesDO> codCharges) {
		this.codCharges = codCharges;
	}
	
	

}
