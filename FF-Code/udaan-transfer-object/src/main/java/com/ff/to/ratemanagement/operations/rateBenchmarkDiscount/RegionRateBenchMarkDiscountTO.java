package com.ff.to.ratemanagement.operations.rateBenchmarkDiscount;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.organization.EmployeeTO;

/**
 * @author preegupt
 * 
 */
public class RegionRateBenchMarkDiscountTO extends CGBaseTO {

	private static final long serialVersionUID = 5816079682079603884L;
	private int rowId;
	private Integer regionRateBenchMarkDiscount;
	private EmployeeTO employeeTO;
	private Integer industryCategory;
	private String discountApproved;
	private Integer region;
	private List<LabelValueBean> rateIndCatList;
	private Integer[] regionId = new Integer[rowId];
	private Integer[] employeeId = new Integer[rowId];
	private Integer[] regionRateBenchMarkDiscountArr = new Integer[rowId];
	private Double[] discountPercentage = new Double[rowId];
	private Double discountPercent;

	/**
	 * @return the rowId
	 */
	public int getRowId() {
		return rowId;
	}

	/**
	 * @param rowId
	 *            the rowId to set
	 */
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	/**
	 * @return the regionRateBenchMarkDiscount
	 */
	public Integer getRegionRateBenchMarkDiscount() {
		return regionRateBenchMarkDiscount;
	}

	/**
	 * @param regionRateBenchMarkDiscount
	 *            the regionRateBenchMarkDiscount to set
	 */
	public void setRegionRateBenchMarkDiscount(
			Integer regionRateBenchMarkDiscount) {
		this.regionRateBenchMarkDiscount = regionRateBenchMarkDiscount;
	}

	/**
	 * @return the employeeTO
	 */
	public EmployeeTO getEmployeeTO() {
		return employeeTO;
	}

	/**
	 * @param employeeTO
	 *            the employeeTO to set
	 */
	public void setEmployeeTO(EmployeeTO employeeTO) {
		this.employeeTO = employeeTO;
	}

	/**
	 * @return the industryCategory
	 */
	public Integer getIndustryCategory() {
		return industryCategory;
	}

	/**
	 * @param industryCategory
	 *            the industryCategory to set
	 */
	public void setIndustryCategory(Integer industryCategory) {
		this.industryCategory = industryCategory;
	}

	/**
	 * @return the discountApproved
	 */
	public String getDiscountApproved() {
		return discountApproved;
	}

	/**
	 * @param discountApproved
	 *            the discountApproved to set
	 */
	public void setDiscountApproved(String discountApproved) {
		this.discountApproved = discountApproved;
	}

	/**
	 * @return the region
	 */
	public Integer getRegion() {
		return region;
	}

	/**
	 * @param region
	 *            the region to set
	 */
	public void setRegion(Integer region) {
		this.region = region;
	}

	/**
	 * @return the rateIndCatList
	 */
	public List<LabelValueBean> getRateIndCatList() {
		return rateIndCatList;
	}

	/**
	 * @param rateIndCatList
	 *            the rateIndCatList to set
	 */
	public void setRateIndCatList(List<LabelValueBean> rateIndCatList) {
		this.rateIndCatList = rateIndCatList;
	}

	/**
	 * @return the regionId
	 */
	public Integer[] getRegionId() {
		return regionId;
	}

	/**
	 * @param regionId
	 *            the regionId to set
	 */
	public void setRegionId(Integer[] regionId) {
		this.regionId = regionId;
	}

	/**
	 * @return the employeeId
	 */
	public Integer[] getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId
	 *            the employeeId to set
	 */
	public void setEmployeeId(Integer[] employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the regionRateBenchMarkDiscountArr
	 */
	public Integer[] getRegionRateBenchMarkDiscountArr() {
		return regionRateBenchMarkDiscountArr;
	}

	/**
	 * @param regionRateBenchMarkDiscountArr
	 *            the regionRateBenchMarkDiscountArr to set
	 */
	public void setRegionRateBenchMarkDiscountArr(
			Integer[] regionRateBenchMarkDiscountArr) {
		this.regionRateBenchMarkDiscountArr = regionRateBenchMarkDiscountArr;
	}

	/**
	 * @return the discountPercentage
	 */
	public Double[] getDiscountPercentage() {
		return discountPercentage;
	}

	/**
	 * @param discountPercentage
	 *            the discountPercentage to set
	 */
	public void setDiscountPercentage(Double[] discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	/**
	 * @return the discountPercent
	 */
	public Double getDiscountPercent() {
		return discountPercent;
	}

	/**
	 * @param discountPercent
	 *            the discountPercent to set
	 */
	public void setDiscountPercent(Double discountPercent) {
		this.discountPercent = discountPercent;
	}

}
