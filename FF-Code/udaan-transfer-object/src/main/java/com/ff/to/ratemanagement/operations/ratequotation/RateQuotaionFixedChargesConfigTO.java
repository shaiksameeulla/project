package com.ff.to.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class RateQuotaionFixedChargesConfigTO extends CGBaseTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7680304629027424022L;
	
	private Integer quotaionFixedChargesConfigId;
	private Integer quotationId;
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
	/**
	 * @return the quotationId
	 */
	public Integer getQuotationId() {
		return quotationId;
	}
	/**
	 * @param quotationId the quotationId to set
	 */
	public void setQuotationId(Integer quotationId) {
		this.quotationId = quotationId;
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
