package com.ff.report;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class CategoryReportAliasTO extends CGBaseTO {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String categoryDesc;
	String categoryCode;
	
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getCategoryDesc() {
		return categoryDesc;
	}
	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}
	
	 

}
