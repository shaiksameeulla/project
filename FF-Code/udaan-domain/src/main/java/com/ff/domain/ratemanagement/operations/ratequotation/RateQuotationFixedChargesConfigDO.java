package com.ff.domain.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class RateQuotationFixedChargesConfigDO extends CGFactDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2564238737526847959L;
	private Integer quotaionFixedChargesConfigId;
	private RateQuotationDO rateQuotationDO;
	private String insuredBy;
	private String octroiBourneBy;
	
	/**
	 * @return the quotaionFixedChargesConfigId
	 */
	public Integer getQuotaionFixedChargesConfigId() {
		return quotaionFixedChargesConfigId;
	}
	/**
	 * @param quotaionFixedChargesConfigId the quotaionFixedChargesConfigId to set
	 */
	public void setQuotaionFixedChargesConfigId(Integer quotaionFixedChargesConfigId) {
		this.quotaionFixedChargesConfigId = quotaionFixedChargesConfigId;
	}
	
	
	public RateQuotationDO getRateQuotationDO() {
		return rateQuotationDO;
	}
	public void setRateQuotationDO(RateQuotationDO rateQuotationDO) {
		this.rateQuotationDO = rateQuotationDO;
	}
	/**
	 * @return the insuredBy
	 */
	public String getInsuredBy() {
		return insuredBy;
	}
	/**
	 * @param insuredBy the insuredBy to set
	 */
	public void setInsuredBy(String insuredBy) {
		this.insuredBy = insuredBy;
	}
	/**
	 * @return the octroiBourneBy
	 */
	public String getOctroiBourneBy() {
		return octroiBourneBy;
	}
	/**
	 * @param octroiBourneBy the octroiBourneBy to set
	 */
	public void setOctroiBourneBy(String octroiBourneBy) {
		this.octroiBourneBy = octroiBourneBy;
	}
	
	
	
	
}
