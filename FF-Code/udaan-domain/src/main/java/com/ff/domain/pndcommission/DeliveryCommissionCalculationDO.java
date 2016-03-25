package com.ff.domain.pndcommission;

import java.util.Date;

import com.ff.domain.organization.EmployeeDO;

/**
 * @author hkansagr
 */
public class DeliveryCommissionCalculationDO {

	/** The deliveryCommId. PK - Auto Increment. */
	private Integer deliveryCommId;

	/** The employee DO - employee id. */
	private EmployeeDO employeeDO;

	/** The product group - CC- Credit Card, CD- COD, PR- Priority, OT- Others */
	private String productGroup;

	/** The calculated for period */
	private Date calculatedForPeriod;

	/** The dlivered count. */
	private Integer deliveredCount;

	/** The delivery day 1. */
	private Integer dlvDay1;

	/** The delivery day 2. */
	private Integer dlvDay2;

	/** The delivery day 3. */
	private Integer dlvDay3;

	/** The delivery day 4+. */
	private Integer dlvDay4Beyond;

	/** The transaction create date. */
	private Date transactionCreateDate;

	
	/**
	 * @return the employeeDO
	 */
	public EmployeeDO getEmployeeDO() {
		return employeeDO;
	}

	/**
	 * @param employeeDO
	 *            the employeeDO to set
	 */
	public void setEmployeeDO(EmployeeDO employeeDO) {
		this.employeeDO = employeeDO;
	}

	/**
	 * @return the transactionCreateDate
	 */
	public Date getTransactionCreateDate() {
		return transactionCreateDate;
	}

	/**
	 * @param transactionCreateDate
	 *            the transactionCreateDate to set
	 */
	public void setTransactionCreateDate(Date transactionCreateDate) {
		this.transactionCreateDate = transactionCreateDate;
	}

	/**
	 * @return the deliveryCommId
	 */
	public Integer getDeliveryCommId() {
		return deliveryCommId;
	}

	/**
	 * @param deliveryCommId
	 *            the deliveryCommId to set
	 */
	public void setDeliveryCommId(Integer deliveryCommId) {
		this.deliveryCommId = deliveryCommId;
	}

	/**
	 * @return the productGroup
	 */
	public String getProductGroup() {
		return productGroup;
	}

	/**
	 * @param productGroup
	 *            the productGroup to set
	 */
	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}

	/**
	 * @return the calculatedForPeriod
	 */
	public Date getCalculatedForPeriod() {
		return calculatedForPeriod;
	}

	/**
	 * @param calculatedForPeriod
	 *            the calculatedForPeriod to set
	 */
	public void setCalculatedForPeriod(Date calculatedForPeriod) {
		this.calculatedForPeriod = calculatedForPeriod;
	}

	/**
	 * @return the deliveredCount
	 */
	public Integer getDeliveredCount() {
		return deliveredCount;
	}

	/**
	 * @param deliveredCount
	 *            the deliveredCount to set
	 */
	public void setDeliveredCount(Integer deliveredCount) {
		this.deliveredCount = deliveredCount;
	}

	/**
	 * @return the dlvDay1
	 */
	public Integer getDlvDay1() {
		return dlvDay1;
	}

	/**
	 * @param dlvDay1
	 *            the dlvDay1 to set
	 */
	public void setDlvDay1(Integer dlvDay1) {
		this.dlvDay1 = dlvDay1;
	}

	/**
	 * @return the dlvDay2
	 */
	public Integer getDlvDay2() {
		return dlvDay2;
	}

	/**
	 * @param dlvDay2
	 *            the dlvDay2 to set
	 */
	public void setDlvDay2(Integer dlvDay2) {
		this.dlvDay2 = dlvDay2;
	}

	/**
	 * @return the dlvDay3
	 */
	public Integer getDlvDay3() {
		return dlvDay3;
	}

	/**
	 * @param dlvDay3
	 *            the dlvDay3 to set
	 */
	public void setDlvDay3(Integer dlvDay3) {
		this.dlvDay3 = dlvDay3;
	}

	/**
	 * @return the dlvDay4Beyond
	 */
	public Integer getDlvDay4Beyond() {
		return dlvDay4Beyond;
	}

	/**
	 * @param dlvDay4Beyond
	 *            the dlvDay4Beyond to set
	 */
	public void setDlvDay4Beyond(Integer dlvDay4Beyond) {
		this.dlvDay4Beyond = dlvDay4Beyond;
	}

}
