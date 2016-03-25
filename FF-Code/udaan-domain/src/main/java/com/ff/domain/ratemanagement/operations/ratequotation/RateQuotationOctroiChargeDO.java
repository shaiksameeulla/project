package com.ff.domain.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.ratemanagement.masters.OctroiChargeDO;

public class RateQuotationOctroiChargeDO extends CGFactDO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -305051448564626195L;
	
	private Integer rateQuotationOctroiChargeId;
	private OctroiChargeDO octroiChargeDO;
	private Double serviceCharge;
	/**
	 * @return the quotationOctroiChargeId
	 */
	public Integer getRateQuotationOctroiChargeId() {
		return rateQuotationOctroiChargeId;
	}
	/**
	 * @param quotationOctroiChargeId the quotationOctroiChargeId to set
	 */
	public void setRateQuotationOctroiChargeId(Integer rateQuotationOctroiChargeId) {
		this.rateQuotationOctroiChargeId = rateQuotationOctroiChargeId;
	}
	/**
	 * @return the octroiChargeId
	 */
	public OctroiChargeDO getOctroiChargeDO() {
		return octroiChargeDO;
	}
	/**
	 * @param octroiChargeId the octroiChargeId to set
	 */
	public void setOctroiChargeDO(OctroiChargeDO octroiChargeDO) {
		this.octroiChargeDO = octroiChargeDO;
	}
	/**
	 * @return the serviceCharge
	 */
	public Double getServiceCharge() {
		return serviceCharge;
	}
	/**
	 * @param serviceCharge the serviceCharge to set
	 */
	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	

}
