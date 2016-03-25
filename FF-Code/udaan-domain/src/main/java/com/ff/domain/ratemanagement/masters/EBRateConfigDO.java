/**
 * 
 */
package com.ff.domain.ratemanagement.masters;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.geography.StateDO;

/**
 * @author nkattung
 * 
 */
public class EBRateConfigDO extends CGMasterDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 90864319434385909L;
	private Integer ebRateConfigId;
	private StateDO originState;
	private String serviceTaxApplicable= "N";
	private String cessTaxApplicable= "N";
	private String hcesstaxApplicable= "N";
	private Set<EBRatePreferenceDO> preferenceDOSet;
	private Date validFromDate;
	private Date validToDate;
	private String status;
	private String stateTaxApplicable= "N";
	private String surchargeSTtaxApplicable= "N";	
	
	
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the stateTaxApplicable
	 */
	public String getStateTaxApplicable() {
		return stateTaxApplicable;
	}
	/**
	 * @param stateTaxApplicable the stateTaxApplicable to set
	 */
	public void setStateTaxApplicable(String stateTaxApplicable) {
		this.stateTaxApplicable = stateTaxApplicable;
	}
	/**
	 * @return the surchargeSTtaxApplicable
	 */
	public String getSurchargeSTtaxApplicable() {
		return surchargeSTtaxApplicable;
	}
	/**
	 * @param surchargeSTtaxApplicable the surchargeSTtaxApplicable to set
	 */
	public void setSurchargeSTtaxApplicable(String surchargeSTtaxApplicable) {
		this.surchargeSTtaxApplicable = surchargeSTtaxApplicable;
	}
	/**
	 * @return the preferenceDOSet
	 */
	public Set<EBRatePreferenceDO> getPreferenceDOSet() {
		return preferenceDOSet;
	}
	/**
	 * @param preferenceDOSet the preferenceDOSet to set
	 */
	public void setPreferenceDOSet(Set<EBRatePreferenceDO> preferenceDOSet) {
		this.preferenceDOSet = preferenceDOSet;
	}
	/**
	 * @return the ebRateConfigId
	 */
	public Integer getEbRateConfigId() {
		return ebRateConfigId;
	}
	/**
	 * @param ebRateConfigId the ebRateConfigId to set
	 */
	public void setEbRateConfigId(Integer ebRateConfigId) {
		this.ebRateConfigId = ebRateConfigId;
	}
	
	
	/**
	 * @return the originState
	 */
	public StateDO getOriginState() {
		return originState;
	}
	/**
	 * @param originState the originState to set
	 */
	public void setOriginState(StateDO originState) {
		this.originState = originState;
	}
	/**
	 * @return the serviceTaxApplicable
	 */
	public String getServiceTaxApplicable() {
		return serviceTaxApplicable;
	}
	/**
	 * @param serviceTaxApplicable the serviceTaxApplicable to set
	 */
	public void setServiceTaxApplicable(String serviceTaxApplicable) {
		this.serviceTaxApplicable = serviceTaxApplicable;
	}
	/**
	 * @return the cessTaxApplicable
	 */
	public String getCessTaxApplicable() {
		return cessTaxApplicable;
	}
	/**
	 * @param cessTaxApplicable the cessTaxApplicable to set
	 */
	public void setCessTaxApplicable(String cessTaxApplicable) {
		this.cessTaxApplicable = cessTaxApplicable;
	}
	/**
	 * @return the hcesstaxApplicable
	 */
	public String getHcesstaxApplicable() {
		return hcesstaxApplicable;
	}
	/**
	 * @param hcesstaxApplicable the hcesstaxApplicable to set
	 */
	public void setHcesstaxApplicable(String hcesstaxApplicable) {
		this.hcesstaxApplicable = hcesstaxApplicable;
	}

}
