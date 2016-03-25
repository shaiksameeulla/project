package com.ff.domain.mec.expense;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.consignment.BcunConsignmentDO;

public class BcunExpenseEntriesDO extends CGFactDO {

	private static final long serialVersionUID = 1L;

	/* primary key */
	private Long expenseEntryId;

	/* FK with expense table */
	private BcunExpenseDO expenseDO;

	/* FK with employee table */
	private Integer employeeDO;

	/* FK with consignment table */
	private BcunConsignmentDO consignmentDO;

	/* The amount */
	private Double amount;

	/* The service charge - Octroi */
	private Double serviceCharge;

	/* The service tax - Octroi */
	private Double serviceTax;

	/* The education cess - Octroi */
	private Double educationCess;

	/* The higher education cess - Octroi */
	private Double higherEducationCess;

	/* The stateTax - Octroi */
	private Double stateTax;

	/* The surchargeOnStateTax - Octroi */
	private Double surchargeOnStateTax;

	/* The octroiBourneBy i.e. CONSIGNOR or CONSIGNEE */
	private String octroiBourneBy;

	/* The Other charges */
	private Double otherCharges;

	/* Total expense entry amount = service Ch. + other Ch. + amount */
	private Double totalExpenseEntryAmt;

	/* the remarks */
	private String remarks;

	/* The position. */
	private Integer position;

	/*
	 * The billing flag. Y- Yes (if customer is CREDIT and octroi bourne by
	 * Consingor) else N- No.
	 */
	private String billingFlag;

	/**
	 * @return the expenseEntryId
	 */
	public Long getExpenseEntryId() {
		return expenseEntryId;
	}

	/**
	 * @param expenseEntryId
	 *            the expenseEntryId to set
	 */
	public void setExpenseEntryId(Long expenseEntryId) {
		this.expenseEntryId = expenseEntryId;
	}

	/**
	 * @return the expenseDO
	 */
	public BcunExpenseDO getExpenseDO() {
		return expenseDO;
	}

	/**
	 * @param expenseDO
	 *            the expenseDO to set
	 */
	public void setExpenseDO(BcunExpenseDO expenseDO) {
		this.expenseDO = expenseDO;
	}

	/**
	 * @return the employeeDO
	 */
	public Integer getEmployeeDO() {
		return employeeDO;
	}

	/**
	 * @param employeeDO
	 *            the employeeDO to set
	 */
	public void setEmployeeDO(Integer employeeDO) {
		this.employeeDO = employeeDO;
	}

	/**
	 * @return the consignmentDO
	 */
	public BcunConsignmentDO getConsignmentDO() {
		return consignmentDO;
	}

	/**
	 * @param consignmentDO
	 *            the consignmentDO to set
	 */
	public void setConsignmentDO(BcunConsignmentDO consignmentDO) {
		this.consignmentDO = consignmentDO;
	}

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
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
	 * @return the educationCess
	 */
	public Double getEducationCess() {
		return educationCess;
	}

	/**
	 * @param educationCess
	 *            the educationCess to set
	 */
	public void setEducationCess(Double educationCess) {
		this.educationCess = educationCess;
	}

	/**
	 * @return the higherEducationCess
	 */
	public Double getHigherEducationCess() {
		return higherEducationCess;
	}

	/**
	 * @param higherEducationCess
	 *            the higherEducationCess to set
	 */
	public void setHigherEducationCess(Double higherEducationCess) {
		this.higherEducationCess = higherEducationCess;
	}

	/**
	 * @return the stateTax
	 */
	public Double getStateTax() {
		return stateTax;
	}

	/**
	 * @param stateTax
	 *            the stateTax to set
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
	 * @param surchargeOnStateTax
	 *            the surchargeOnStateTax to set
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
	 * @param octroiBourneBy
	 *            the octroiBourneBy to set
	 */
	public void setOctroiBourneBy(String octroiBourneBy) {
		this.octroiBourneBy = octroiBourneBy;
	}

	/**
	 * @return the otherCharges
	 */
	public Double getOtherCharges() {
		return otherCharges;
	}

	/**
	 * @param otherCharges
	 *            the otherCharges to set
	 */
	public void setOtherCharges(Double otherCharges) {
		this.otherCharges = otherCharges;
	}

	/**
	 * @return the totalExpenseEntryAmt
	 */
	public Double getTotalExpenseEntryAmt() {
		return totalExpenseEntryAmt;
	}

	/**
	 * @param totalExpenseEntryAmt
	 *            the totalExpenseEntryAmt to set
	 */
	public void setTotalExpenseEntryAmt(Double totalExpenseEntryAmt) {
		this.totalExpenseEntryAmt = totalExpenseEntryAmt;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}

	/**
	 * @return the billingFlag
	 */
	public String getBillingFlag() {
		return billingFlag;
	}

	/**
	 * @param billingFlag
	 *            the billingFlag to set
	 */
	public void setBillingFlag(String billingFlag) {
		this.billingFlag = billingFlag;
	}

}
