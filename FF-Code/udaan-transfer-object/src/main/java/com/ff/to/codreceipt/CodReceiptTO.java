package com.ff.to.codreceipt;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class CodReceiptTO extends CGBaseTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2794862806634611088L;
	private String regionName;
	private String branchName;
	private String bookDate;
	private String consgNo; 
	private String currDate;
	private List<CodReceiptDetailsTO> codReceiptDetailsTOs;
	private double grandTotal;
	private String codReceiptNo;
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public String getBookDate() {
		return bookDate;
	}
	public void setBookDate(String bookDate) {
		this.bookDate = bookDate;
	}
	public List<CodReceiptDetailsTO> getCodReceiptDetailsTOs() {
		return codReceiptDetailsTOs;
	}
	public void setCodReceiptDetailsTOs(
			List<CodReceiptDetailsTO> codReceiptDetailsTOs) {
		this.codReceiptDetailsTOs = codReceiptDetailsTOs;
	}
	public String getConsgNo() {
		return consgNo;
	}
	public void setConsgNo(String consgNo) {
		this.consgNo = consgNo;
	}
	public double getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(double grandTotal) {
		this.grandTotal = grandTotal;
	}
	public String getCurrDate() {
		return currDate;
	}
	public void setCurrDate(String currDate) {
		this.currDate = currDate;
	}
	public String getCodReceiptNo() {
		return codReceiptNo;
	}
	public void setCodReceiptNo(String codReceiptNo) {
		this.codReceiptNo = codReceiptNo;
	}
	
	

}
