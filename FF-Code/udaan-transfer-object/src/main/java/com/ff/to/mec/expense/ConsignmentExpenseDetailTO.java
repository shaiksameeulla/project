package com.ff.to.mec.expense;

import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.consignment.ConsignmentTO;
import com.ff.to.rate.OctroiRateCalculationOutputTO;

/**
 * @author hkansagr
 */

public class ConsignmentExpenseDetailTO extends ExpenseTO implements
		Comparable<ConsignmentExpenseDetailTO> {

	private static final long serialVersionUID = 1L;

	/** The consignment id */
	private Integer consgId;

	/** The consignment no. */
	private String consgNo;

	/** The service charge. */
	private Double serviceCharge;

	/** The total Tax. */
	private Double totalTax;

	/** The serviceTax. */
	private Double serviceTax;

	/** The education Cess. */
	private Double eduCess;

	/** The higher education Cess. */
	private Double higherEduCess;

	/** The other charge */
	private Double otherCharge;

	/** The total. */
	private Double total;

	/** The stateTax. */
	private Double stateTax;
	
	/** The surchargeOnStateTax. */
	private Double surchargeOnStateTax;
	
	/** The octroiBourneBy i.e. CONSIGNOR or CONSIGNEE */
	private String octroiBourneBy; /* hidden */
	
	/** The consgTO. */
	private ConsignmentTO consgTO;

	/** The octroiRateTO. */
	OctroiRateCalculationOutputTO octroiRateTO;

	/* 
	 * The billing flag. Y- Yes 
	 * (if customer is CREDIT and octroi bourne by Consingor) else N- No.  
	 */
	private String billingFlag; /* hidden */
	
	
	/**
	 * @return the billingFlag
	 */
	public String getBillingFlag() {
		return billingFlag;
	}

	/**
	 * @param billingFlag the billingFlag to set
	 */
	public void setBillingFlag(String billingFlag) {
		this.billingFlag = billingFlag;
	}

	/**
	 * @return the stateTax
	 */
	public Double getStateTax() {
		return stateTax;
	}

	/**
	 * @param stateTax the stateTax to set
	 */
	public void setStateTax(Double stateTax) {
		this.stateTax = stateTax;
	}

	/**
	 * @return the surchargeOnStateTax
	 */
	public Double getSurchargeOnStateTax() {
		return surchargeOnStateTax;
	}

	/**
	 * @param surchargeOnStateTax the surchargeOnStateTax to set
	 */
	public void setSurchargeOnStateTax(Double surchargeOnStateTax) {
		this.surchargeOnStateTax = surchargeOnStateTax;
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

	/**
	 * @return the octroiRateTO
	 */
	public OctroiRateCalculationOutputTO getOctroiRateTO() {
		return octroiRateTO;
	}

	/**
	 * @param octroiRateTO the octroiRateTO to set
	 */
	public void setOctroiRateTO(OctroiRateCalculationOutputTO octroiRateTO) {
		this.octroiRateTO = octroiRateTO;
	}

	/**
	 * @return the consgTO
	 */
	public ConsignmentTO getConsgTO() {
		return consgTO;
	}

	/**
	 * @param consgTO
	 *            the consgTO to set
	 */
	public void setConsgTO(ConsignmentTO consgTO) {
		this.consgTO = consgTO;
	}

	/**
	 * @return the serviceTax
	 */
	public Double getServiceTax() {
		return serviceTax;
	}

	/**
	 * @param serviceTax
	 *            the serviceTax to set
	 */
	public void setServiceTax(Double serviceTax) {
		this.serviceTax = serviceTax;
	}

	/**
	 * @return the totalTax
	 */
	public Double getTotalTax() {
		return totalTax;
	}

	/**
	 * @param totalTax
	 *            the totalTax to set
	 */
	public void setTotalTax(Double totalTax) {
		this.totalTax = totalTax;
	}

	/**
	 * @return the eduCess
	 */
	public Double getEduCess() {
		return eduCess;
	}

	/**
	 * @param eduCess
	 *            the eduCess to set
	 */
	public void setEduCess(Double eduCess) {
		this.eduCess = eduCess;
	}

	/**
	 * @return the higherEduCess
	 */
	public Double getHigherEduCess() {
		return higherEduCess;
	}

	/**
	 * @param higherEduCess
	 *            the higherEduCess to set
	 */
	public void setHigherEduCess(Double higherEduCess) {
		this.higherEduCess = higherEduCess;
	}

	/**
	 * @return the consgId
	 */
	public Integer getConsgId() {
		return consgId;
	}

	/**
	 * @param consgId
	 *            the consgId to set
	 */
	public void setConsgId(Integer consgId) {
		this.consgId = consgId;
	}

	/**
	 * @return the consgNo
	 */
	public String getConsgNo() {
		return consgNo;
	}

	/**
	 * @param consgNo
	 *            the consgNo to set
	 */
	public void setConsgNo(String consgNo) {
		this.consgNo = consgNo;
	}

	/**
	 * @return the serviceCharge
	 */
	public Double getServiceCharge() {
		return serviceCharge;
	}

	/**
	 * @param serviceCharge
	 *            the serviceCharge to set
	 */
	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	/**
	 * @return the otherCharge
	 */
	public Double getOtherCharge() {
		return otherCharge;
	}

	/**
	 * @param otherCharge
	 *            the otherCharge to set
	 */
	public void setOtherCharge(Double otherCharge) {
		this.otherCharge = otherCharge;
	}

	/**
	 * @return the total
	 */
	public Double getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(Double total) {
		this.total = total;
	}

	@Override
	public int compareTo(ConsignmentExpenseDetailTO obj) {
		int result = 0;
		if (!StringUtil.isEmptyInteger(position)
				&& !StringUtil.isEmptyInteger(obj.getPosition())) {
			result = this.position.compareTo(obj.position);
		} else if (!StringUtil.isEmptyLong(expenseEntriesId)
				&& !StringUtil.isEmptyLong(obj.getExpenseEntriesId())) {
			result = this.expenseEntriesId.compareTo(obj.expenseEntriesId);
		}
		return result;
	}

}
