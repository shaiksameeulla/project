package com.ff.to.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author preegupt
 *
 */
public class RateQuotationOctroiChargeTO extends CGBaseTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1228016389004373684L;
	private Integer quotationOctroiChargeId;
	private OctroiChargeTO octroiChargeId;
	private Float serviceCharge;
	/**
	 * @return the quotationOctroiChargeId
	 */
	public Integer getQuotationOctroiChargeId() {
		return quotationOctroiChargeId;
	}
	/**
	 * @param quotationOctroiChargeId the quotationOctroiChargeId to set
	 */
	public void setQuotationOctroiChargeId(Integer quotationOctroiChargeId) {
		this.quotationOctroiChargeId = quotationOctroiChargeId;
	}
	/**
	 * @return the octroiChargeId
	 */
	public OctroiChargeTO getOctroiChargeId() {
		return octroiChargeId;
	}
	/**
	 * @param octroiChargeId the octroiChargeId to set
	 */
	public void setOctroiChargeId(OctroiChargeTO octroiChargeId) {
		this.octroiChargeId = octroiChargeId;
	}
	/**
	 * @return the serviceCharge
	 */
	public Float getServiceCharge() {
		return serviceCharge;
	}
	/**
	 * @param serviceCharge the serviceCharge to set
	 */
	public void setServiceCharge(Float serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	
	
}
