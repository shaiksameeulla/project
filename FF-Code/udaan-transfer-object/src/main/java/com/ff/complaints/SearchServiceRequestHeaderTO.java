package com.ff.complaints;


import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;



public class SearchServiceRequestHeaderTO extends CGBaseTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2571343979205354368L;
	
	
	
	private String searchType;
	private String searchNumber;
	List<SearchServiceRequestGridTO> gridDtls;
	/**
	 * @return the searchType
	 */
	public String getSearchType() {
		return searchType;
	}
	/**
	 * @return the searchNumber
	 */
	public String getSearchNumber() {
		return searchNumber;
	}
	/**
	 * @param searchType the searchType to set
	 */
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	/**
	 * @param searchNumber the searchNumber to set
	 */
	public void setSearchNumber(String searchNumber) {
		this.searchNumber = searchNumber;
	}
	/**
	 * @return the gridDtls
	 */
	public List<SearchServiceRequestGridTO> getGridDtls() {
		return gridDtls;
	}
	/**
	 * @param gridDtls the gridDtls to set
	 */
	public void setGridDtls(List<SearchServiceRequestGridTO> gridDtls) {
		this.gridDtls = gridDtls;
	}
	
	
}