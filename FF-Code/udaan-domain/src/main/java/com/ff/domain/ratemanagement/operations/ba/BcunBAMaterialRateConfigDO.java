package com.ff.domain.ratemanagement.operations.ba;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author hkansagr
 */

public class BcunBAMaterialRateConfigDO extends CGFactDO {

	private static final long serialVersionUID = 1L;
	
	/** The baMaterialRateId - Primary Key */
	private Long baMaterialRateId;
	
	/** The valid from date i.e. DD/MM/YYYY */
	private Date validFromDate;
	
	/** The valid to date i.e. DD/MM/YYYY */
	private Date validToDate;
	
	/** Record created user office */
	private Integer createdOfficeId;
	
	/** The set of baMaterialRateDtls */
	private Set<BcunBAMaterialRateDetailsDO> baMaterialRateDtls;
	
	
	/**
	 * @return the baMaterialRateDtls
	 */
	public Set<BcunBAMaterialRateDetailsDO> getBaMaterialRateDtls() {
		return baMaterialRateDtls;
	}
	/**
	 * @param baMaterialRateDtls the baMaterialRateDtls to set
	 */
	public void setBaMaterialRateDtls(
			Set<BcunBAMaterialRateDetailsDO> baMaterialRateDtls) {
		this.baMaterialRateDtls = baMaterialRateDtls;
	}
	/**
	 * @return the baMaterialRateId
	 */
	public Long getBaMaterialRateId() {
		return baMaterialRateId;
	}
	/**
	 * @param baMaterialRateId the baMaterialRateId to set
	 */
	public void setBaMaterialRateId(Long baMaterialRateId) {
		this.baMaterialRateId = baMaterialRateId;
	}
	/**
	 * @return the validFromDate
	 */
	public Date getValidFromDate() {
		return validFromDate;
	}
	/**
	 * @param validFromDate the validFromDate to set
	 */
	public void setValidFromDate(Date validFromDate) {
		this.validFromDate = validFromDate;
	}
	/**
	 * @return the validToDate
	 */
	public Date getValidToDate() {
		return validToDate;
	}
	/**
	 * @param validToDate the validToDate to set
	 */
	public void setValidToDate(Date validToDate) {
		this.validToDate = validToDate;
	}
	/**
	 * @return the createdOfficeId
	 */
	public Integer getCreatedOfficeId() {
		return createdOfficeId;
	}
	/**
	 * @param createdOfficeId the createdOfficeId to set
	 */
	public void setCreatedOfficeId(Integer createdOfficeId) {
		this.createdOfficeId = createdOfficeId;
	}
	
}
