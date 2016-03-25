package com.ff.domain.ratemanagement.operations.ba;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.business.CustomerTypeDO;
import com.ff.domain.ratemanagement.masters.RateCustomerCategoryDO;

public class BaRateHeaderDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2683839339561906838L;
	private Integer headerId;
	private Integer baTypeId;
	private Integer cityId;
	private Date fromDate;
	private Date toDate;
	private RateCustomerCategoryDO rateCustomerCategory;
	private CustomerTypeDO customerTypeDO;
	private String headerStatus;

	private Date createdDate;
	private Date updatedDate;
	private Integer createdBy;
	private Integer updatedBy;

	/**
	 * @return the headerStatus
	 */
	public String getHeaderStatus() {
		return headerStatus;
	}

	/**
	 * @param headerStatus
	 *            the headerStatus to set
	 */
	public void setHeaderStatus(String headerStatus) {
		this.headerStatus = headerStatus;
	}

	/**
	 * @return the customerTypeDO
	 */
	public CustomerTypeDO getCustomerTypeDO() {
		return customerTypeDO;
	}

	/**
	 * @param customerTypeDO
	 *            the customerTypeDO to set
	 */
	public void setCustomerTypeDO(CustomerTypeDO customerTypeDO) {
		this.customerTypeDO = customerTypeDO;
	}

	/**
	 * @return the headerId
	 */
	public Integer getHeaderId() {
		return headerId;
	}

	/**
	 * @param headerId
	 *            the headerId to set
	 */
	public void setHeaderId(Integer headerId) {
		this.headerId = headerId;
	}

	/**
	 * @return the baTypeId
	 */
	public Integer getBaTypeId() {
		return baTypeId;
	}

	/**
	 * @param baTypeId
	 *            the baTypeId to set
	 */
	public void setBaTypeId(Integer baTypeId) {
		this.baTypeId = baTypeId;
	}

	/**
	 * @return the cityId
	 */
	public Integer getCityId() {
		return cityId;
	}

	/**
	 * @param cityId
	 *            the cityId to set
	 */
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate
	 *            the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @param toDate
	 *            the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the rateCustomerCategory
	 */
	public RateCustomerCategoryDO getRateCustomerCategory() {
		return rateCustomerCategory;
	}

	/**
	 * @param rateCustomerCategory
	 *            the rateCustomerCategory to set
	 */
	public void setRateCustomerCategory(
			RateCustomerCategoryDO rateCustomerCategory) {
		this.rateCustomerCategory = rateCustomerCategory;
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
