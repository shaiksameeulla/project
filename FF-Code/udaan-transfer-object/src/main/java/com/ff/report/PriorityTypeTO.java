package com.ff.report;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class PriorityTypeTO extends CGBaseTO{
	
	private Integer id; 
	private String stdTypeCode;
	private String description;
	/**
	 * @return the id
	 */
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the stdTypeCode
	 */
	public String getStdTypeCode() {
		return stdTypeCode;
	}
	/**
	 * @param stdTypeCode the stdTypeCode to set
	 */
	public void setStdTypeCode(String stdTypeCode) {
		this.stdTypeCode = stdTypeCode;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
