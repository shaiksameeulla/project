/**
 * 
 */
package com.ff.to.drs;

import java.util.List;


/**
 * @author mohammes
 *
 */
public class RtoCodDrsTO extends AbstractDeliveryTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6835020080921259485L;

	private String rowReferenceNumber[]= new String[rowCount];
	
	//private String rowConsignorName[]= new String[rowCount];
	//private String rowConsignorCode[]= new String[rowCount];
	private String rowVendorCode[]= new String[rowCount];
	private String rowVendorName[]= new String[rowCount];
	
	private Double rowCodAmount[]= new Double[rowCount];
	/** The row LC amount. */
	private Double rowLCAmount[]= new Double[rowCount];
	
	/** The row ToPay Amount. */
	private Double rowToPayAmount[]= new Double[rowCount];
	
	private List<RtoCodDrsDetailsTO> detailsToList;


	/**
	 * @return the rowReferenceNumber
	 */
	public String[] getRowReferenceNumber() {
		return rowReferenceNumber;
	}


	/**
	 * @return the detailsToList
	 */
	public List<RtoCodDrsDetailsTO> getDetailsToList() {
		return detailsToList;
	}


	/**
	 * @param rowReferenceNumber the rowReferenceNumber to set
	 */
	public void setRowReferenceNumber(String[] rowReferenceNumber) {
		this.rowReferenceNumber = rowReferenceNumber;
	}


	/**
	 * @param detailsToList the detailsToList to set
	 */
	public void setDetailsToList(List<RtoCodDrsDetailsTO> detailsToList) {
		this.detailsToList = detailsToList;
	}


	/**
	 * @return the rowCodAmount
	 */
	public Double[] getRowCodAmount() {
		return rowCodAmount;
	}


	/**
	 * @param rowCodAmount the rowCodAmount to set
	 */
	public void setRowCodAmount(Double[] rowCodAmount) {
		this.rowCodAmount = rowCodAmount;
	}


	/**
	 * @return the rowVendorCode
	 */
	public String[] getRowVendorCode() {
		return rowVendorCode;
	}


	/**
	 * @return the rowVendorName
	 */
	public String[] getRowVendorName() {
		return rowVendorName;
	}


	/**
	 * @param rowVendorCode the rowVendorCode to set
	 */
	public void setRowVendorCode(String[] rowVendorCode) {
		this.rowVendorCode = rowVendorCode;
	}


	/**
	 * @param rowVendorName the rowVendorName to set
	 */
	public void setRowVendorName(String[] rowVendorName) {
		this.rowVendorName = rowVendorName;
	}


	/**
	 * @return the rowLCAmount
	 */
	public Double[] getRowLCAmount() {
		return rowLCAmount;
	}


	/**
	 * @param rowLCAmount the rowLCAmount to set
	 */
	public void setRowLCAmount(Double[] rowLCAmount) {
		this.rowLCAmount = rowLCAmount;
	}


	/**
	 * @return the rowToPayAmount
	 */
	public Double[] getRowToPayAmount() {
		return rowToPayAmount;
	}


	/**
	 * @param rowToPayAmount the rowToPayAmount to set
	 */
	public void setRowToPayAmount(Double[] rowToPayAmount) {
		this.rowToPayAmount = rowToPayAmount;
	}

	

	
}
