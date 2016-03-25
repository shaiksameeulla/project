package com.ff.domain.stockmanagement.operations;

import java.util.Calendar;
import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * The Class StockCommonDetailsDO.
 *
 * @author mohammes
 */
public class StockCommonBaseDO extends CGFactDO {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5499528901665909521L;
	
	private Date transactionCreateDate = Calendar.getInstance().getTime();
	private Date transactionModifiedDate = Calendar.getInstance().getTime();
	/**
	 * @return the transactionCreateDate
	 */
	public Date getTransactionCreateDate() {
		return transactionCreateDate;
	}
	/**
	 * @param transactionCreateDate the transactionCreateDate to set
	 */
	public void setTransactionCreateDate(Date transactionCreateDate) {
		this.transactionCreateDate = transactionCreateDate;
	}
	/**
	 * @return the transactionModifiedDate
	 */
	public Date getTransactionModifiedDate() {
		return transactionModifiedDate;
	}
	/**
	 * @param transactionModifiedDate the transactionModifiedDate to set
	 */
	public void setTransactionModifiedDate(Date transactionModifiedDate) {
		this.transactionModifiedDate = transactionModifiedDate;
	}
	
	
}
