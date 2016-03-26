package com.ff.web.drs.preparation.action;

import java.util.ArrayList;
import java.util.List;

public class Page {
	
	int pagenumber;
	int totalConsg;
	List<PageContent> firstCol;
	List<PageContent> secondCol;
	/**
	 * @return the pagenumber
	 */
	public int getPagenumber() {
		return pagenumber;
	}
	/**
	 * @param pagenumber the pagenumber to set
	 */
	public void setPagenumber(int pagenumber) {
		this.pagenumber = pagenumber;
	}
	/**
	 * @return the firstCol
	 */
	public List<PageContent> getFirstCol() {
		return firstCol;
	}
	/**
	 * @param firstCol the firstCol to set
	 */
	public void setFirstCol(List<PageContent> firstCol) {
		if(this.firstCol == null)
			this.firstCol = new ArrayList<PageContent>();
		this.firstCol = firstCol;
	}
	/**
	 * @return the secondCol
	 */
	public List<PageContent> getSecondCol() {
		return secondCol;
	}
	/**
	 * @param secondCol the secondCol to set
	 */
	public void setSecondCol(List<PageContent> secondCol) {
		if(this.secondCol == null)
			this.secondCol = new ArrayList<PageContent>();
		this.secondCol = secondCol;
	}
	/**
	 * @return the totalConsg
	 */
	public int getTotalConsg() {
		return totalConsg;
	}
	/**
	 * @param totalConsg the totalConsg to set
	 */
	public void setTotalConsg(int totalConsg) {
		this.totalConsg = totalConsg;
	}

	
}
