/**
 * 
 */
package com.ff.to.stockmanagement.stockrequisition;

import java.util.Date;
import java.util.List;

import com.ff.to.stockmanagement.StockHeaderTO;

/**
 * @author mohammes
 * 
 *
 */
public class ListStockRequisitionTO extends StockHeaderTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Integer requisitionOfficeId;
	private String fromDateStr;
	private String toDateStr;
	private String  status;
	private Integer loggedInOfficeId;
	
	private Date toDate;
	private Date fromDate;
	
	List<ListStockRequisitionDtlsTO> lineItems;

	/**
	 * @return the requisitionOfficeId
	 */
	public Integer getRequisitionOfficeId() {
		return requisitionOfficeId;
	}

	/**
	 * @return the fromDateStr
	 */
	public String getFromDateStr() {
		return fromDateStr;
	}

	/**
	 * @return the toDateStr
	 */
	public String getToDateStr() {
		return toDateStr;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the lineItems
	 */
	public List<ListStockRequisitionDtlsTO> getLineItems() {
		return lineItems;
	}

	/**
	 * @param requisitionOfficeId the requisitionOfficeId to set
	 */
	public void setRequisitionOfficeId(Integer requisitionOfficeId) {
		this.requisitionOfficeId = requisitionOfficeId;
	}

	/**
	 * @param fromDateStr the fromDateStr to set
	 */
	public void setFromDateStr(String fromDateStr) {
		this.fromDateStr = fromDateStr;
	}

	/**
	 * @param toDateStr the toDateStr to set
	 */
	public void setToDateStr(String toDateStr) {
		this.toDateStr = toDateStr;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param lineItems the lineItems to set
	 */
	public void setLineItems(List<ListStockRequisitionDtlsTO> lineItems) {
		this.lineItems = lineItems;
	}

	/**
	 * @return the loggedInOfficeId
	 */
	public Integer getLoggedInOfficeId() {
		return loggedInOfficeId;
	}

	/**
	 * @param loggedInOfficeId the loggedInOfficeId to set
	 */
	public void setLoggedInOfficeId(Integer loggedInOfficeId) {
		this.loggedInOfficeId = loggedInOfficeId;
	}

	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	
	}
