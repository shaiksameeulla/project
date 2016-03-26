package com.ff.web.pickup.action;

import java.util.List;

import com.ff.web.drs.preparation.action.PageContent;

public class GeneratePickUpPage {

	List<GeneratePickUpPageContent> firstCol;
	List<GeneratePickUpPageContent> secondCol;
	/**
	 * @return the firstCol
	 */
	public List<GeneratePickUpPageContent> getFirstCol() {
		return firstCol;
	}
	/**
	 * @param firstCol the firstCol to set
	 */
	public void setFirstCol(List<GeneratePickUpPageContent> firstCol) {
		this.firstCol = firstCol;
	}
	/**
	 * @return the secondCol
	 */
	public List<GeneratePickUpPageContent> getSecondCol() {
		return secondCol;
	}
	/**
	 * @param secondCol the secondCol to set
	 */
	public void setSecondCol(List<GeneratePickUpPageContent> secondCol) {
		this.secondCol = secondCol;
	}
	
}
