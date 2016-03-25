package com.ff.domain.ratemanagement.operations.ba;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.ratemanagement.operations.ratecalculation.BARateConfigRTOChargesDO;
import com.ff.domain.ratemanagement.operations.ratecalculation.BARateConfigurationFixedChargesConfigDO;

public class BaRateConfigProductHeaderDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3060120459470165376L;
	private Integer baProductHeaderId;
	private BaRateConfigHeaderDO baRateHeaderDO;
	private Integer rateProductCategory;
	private String isSave;

	private Set<BaRateConfigSlabRateDO> baSlabRateDO;// FIXME delete
	private Set<BARateConfigSpecialDestinationRateDO> baSpecialDestinationRateDO;// FIXME
																					// delete
	private Set<BARateConfigAdditionalChargesDO> baAdditionalChargesDO;
	private Set<BARateConfigRTOChargesDO> baRateRTOChargesDO;
	private Set<BARateConfigurationFixedChargesConfigDO> fixedChargeConfig;

	// NOT USED IN HBM FILE/DB CONNECTION
	private List<BARateConfigurationFixedChargesConfigDO> fixedConfig;
	private List<BARateConfigAdditionalChargesDO> baRateConfigAdditionalCharges;

	Set<BaRateWeightSlabDO> baRateWeightSlabDOs;
	
	private Date createdDate;
	private Date updatedDate;
	private Integer createdBy;
	private Integer updatedBy;

	/**
	 * @return the baRateWeightSlabDOs
	 */
	public Set<BaRateWeightSlabDO> getBaRateWeightSlabDOs() {
		return baRateWeightSlabDOs;
	}

	/**
	 * @param baRateWeightSlabDOs
	 *            the baRateWeightSlabDOs to set
	 */
	public void setBaRateWeightSlabDOs(
			Set<BaRateWeightSlabDO> baRateWeightSlabDOs) {
		this.baRateWeightSlabDOs = baRateWeightSlabDOs;
	}

	public String getIsSave() {
		return isSave;
	}

	public void setIsSave(String isSave) {
		this.isSave = isSave;
	}

	/**
	 * @return the fixedConfig
	 */
	public List<BARateConfigurationFixedChargesConfigDO> getFixedConfig() {
		return fixedConfig;
	}

	/**
	 * @param fixedConfig
	 *            the fixedConfig to set
	 */
	public void setFixedConfig(
			List<BARateConfigurationFixedChargesConfigDO> fixedConfig) {
		this.fixedConfig = fixedConfig;
	}

	/**
	 * @return the baRateConfigAdditionalCharges
	 */
	public List<BARateConfigAdditionalChargesDO> getBaRateConfigAdditionalCharges() {
		return baRateConfigAdditionalCharges;
	}

	/**
	 * @param baRateConfigAdditionalCharges
	 *            the baRateConfigAdditionalCharges to set
	 */
	public void setBaRateConfigAdditionalCharges(
			List<BARateConfigAdditionalChargesDO> baRateConfigAdditionalCharges) {
		this.baRateConfigAdditionalCharges = baRateConfigAdditionalCharges;
	}

	/**
	 * @return the fixedChargeConfig
	 */
	public Set<BARateConfigurationFixedChargesConfigDO> getFixedChargeConfig() {
		return fixedChargeConfig;
	}

	/**
	 * @param fixedChargeConfig
	 *            the fixedChargeConfig to set
	 */
	public void setFixedChargeConfig(
			Set<BARateConfigurationFixedChargesConfigDO> fixedChargeConfig) {
		this.fixedChargeConfig = fixedChargeConfig;
	}

	/**
	 * @return the baProductHeaderId
	 */
	public Integer getBaProductHeaderId() {
		return baProductHeaderId;
	}

	/**
	 * @param baProductHeaderId
	 *            the baProductHeaderId to set
	 */
	public void setBaProductHeaderId(Integer baProductHeaderId) {
		this.baProductHeaderId = baProductHeaderId;
	}

	/**
	 * @return the baRateHeaderDO
	 */
	public BaRateConfigHeaderDO getBaRateHeaderDO() {
		return baRateHeaderDO;
	}

	/**
	 * @param baRateHeaderDO
	 *            the baRateHeaderDO to set
	 */
	public void setBaRateHeaderDO(BaRateConfigHeaderDO baRateHeaderDO) {
		this.baRateHeaderDO = baRateHeaderDO;
	}

	/**
	 * @return the rateProductCategory
	 */
	public Integer getRateProductCategory() {
		return rateProductCategory;
	}

	/**
	 * @param rateProductCategory
	 *            the rateProductCategory to set
	 */
	public void setRateProductCategory(Integer rateProductCategory) {
		this.rateProductCategory = rateProductCategory;
	}

	/**
	 * @return the baSlabRateDO
	 */
	public Set<BaRateConfigSlabRateDO> getBaSlabRateDO() {
		return baSlabRateDO;
	}

	/**
	 * @param baSlabRateDO
	 *            the baSlabRateDO to set
	 */
	public void setBaSlabRateDO(Set<BaRateConfigSlabRateDO> baSlabRateDO) {
		this.baSlabRateDO = baSlabRateDO;
	}

	/**
	 * @return the baSpecialDestinationRateDO
	 */
	public Set<BARateConfigSpecialDestinationRateDO> getBaSpecialDestinationRateDO() {
		return baSpecialDestinationRateDO;
	}

	/**
	 * @param baSpecialDestinationRateDO
	 *            the baSpecialDestinationRateDO to set
	 */
	public void setBaSpecialDestinationRateDO(
			Set<BARateConfigSpecialDestinationRateDO> baSpecialDestinationRateDO) {
		this.baSpecialDestinationRateDO = baSpecialDestinationRateDO;
	}

	/**
	 * @return the baAdditionalChargesDO
	 */
	public Set<BARateConfigAdditionalChargesDO> getBaAdditionalChargesDO() {
		return baAdditionalChargesDO;
	}

	/**
	 * @param baAdditionalChargesDO
	 *            the baAdditionalChargesDO to set
	 */
	public void setBaAdditionalChargesDO(
			Set<BARateConfigAdditionalChargesDO> baAdditionalChargesDO) {
		this.baAdditionalChargesDO = baAdditionalChargesDO;
	}

	/**
	 * @return the baRateRTOChargesDO
	 */
	public Set<BARateConfigRTOChargesDO> getBaRateRTOChargesDO() {
		return baRateRTOChargesDO;
	}

	/**
	 * @param baRateRTOChargesDO
	 *            the baRateRTOChargesDO to set
	 */
	public void setBaRateRTOChargesDO(
			Set<BARateConfigRTOChargesDO> baRateRTOChargesDO) {
		this.baRateRTOChargesDO = baRateRTOChargesDO;
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

}
