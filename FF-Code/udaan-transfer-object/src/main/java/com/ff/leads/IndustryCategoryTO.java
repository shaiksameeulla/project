/**
 * 
 */
package com.ff.leads;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author abarudwa
 *
 */
public class IndustryCategoryTO extends CGBaseTO
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String categoryId;
	
	private String categoryName;

	/**
	 * @return the categoryId
	 */
	

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
