package com.ff.to.mec;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author mohammes
 */

public class LiabilityPageTO extends CGBaseTO 
		 {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6380681651059465719L;
	private int pageNumber=-1;
	private double pageTotal;
	private Integer totalNoPages;
	
	List<LiabilityDetailsTO> pageContentList;

	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * @return the pageTotal
	 */
	public double getPageTotal() {
		return pageTotal;
	}

	/**
	 * @param pageTotal the pageTotal to set
	 */
	public void setPageTotal(double pageTotal) {
		this.pageTotal = pageTotal;
	}

	/**
	 * @return the pageContentList
	 */
	public List<LiabilityDetailsTO> getPageContentList() {
		return pageContentList;
	}

	/**
	 * @param pageContentList the pageContentList to set
	 */
	public void setPageContentList(List<LiabilityDetailsTO> pageContentList) {
		this.pageContentList = pageContentList;
	}

	/**
	 * @return the totalNoPages
	 */
	public Integer getTotalNoPages() {
		return totalNoPages;
	}

	/**
	 * @param totalNoPages the totalNoPages to set
	 */
	public void setTotalNoPages(Integer totalNoPages) {
		this.totalNoPages = totalNoPages;
	}
}
