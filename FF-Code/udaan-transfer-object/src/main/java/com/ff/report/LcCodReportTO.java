package com.ff.report;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class LcCodReportTO extends CGBaseTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Integer regionTO;
	Integer productTO;
	Integer type;
	Integer sortOrder;
	Integer summaryOption;
	Integer sorting;
	Integer partyName;
	String startDate;
	String endDate;
	public Integer getRegionTO() {
		return regionTO;
	}
	public void setRegionTO(Integer regionTO) {
		this.regionTO = regionTO;
	}
	public Integer getProductTO() {
		return productTO;
	}
	public void setProductTO(Integer productTO) {
		this.productTO = productTO;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	public Integer getSummaryOption() {
		return summaryOption;
	}
	public void setSummaryOption(Integer summaryOption) {
		this.summaryOption = summaryOption;
	}
	public Integer getSorting() {
		return sorting;
	}
	public void setSorting(Integer sorting) {
		this.sorting = sorting;
	}
	public Integer getPartyName() {
		return partyName;
	}
	public void setPartyName(Integer partyName) {
		this.partyName = partyName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
	
}
