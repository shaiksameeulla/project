package com.ff.domain.mec;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.consignment.ConsignmentDO;

/**
 * @author amimehta
 */

public class LiabilityDetailsDO extends CGFactDO {

	private static final long serialVersionUID = -1468938827960879104L;
	
	/** primary key. */
	private Integer liabilityDetailId;
	private LiabilityDO liabilityId;
	private ConsignmentDO consgId;
	private double codLcAmt;
	private double collectedAmt;
	private double paidAmt;
	private Integer position;
	private Date bookingDate;
	private boolean isPartialPayment=false;
	private Integer collectionEntriesId;
	private Double transactionBalanceAmount;  
	
	/**
	 * @return the bookingDate
	 */
	public Date getBookingDate() {
		return bookingDate;
	}

	/**
	 * @param bookingDate
	 *            the bookingDate to set
	 */
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getLiabilityDetailId() {
		return liabilityDetailId;
	}

	public void setLiabilityDetailId(Integer liabilityDetailId) {
		this.liabilityDetailId = liabilityDetailId;
	}

	public ConsignmentDO getConsgId() {
		return consgId;
	}

	public void setConsgId(ConsignmentDO consgId) {
		this.consgId = consgId;
	}

	public double getCodLcAmt() {
		return codLcAmt;
	}

	public void setCodLcAmt(double codLcAmt) {
		this.codLcAmt = codLcAmt;
	}

	public double getCollectedAmt() {
		return collectedAmt;
	}

	public void setCollectedAmt(double collectedAmt) {
		this.collectedAmt = collectedAmt;
	}

	public double getPaidAmt() {
		return paidAmt;
	}

	public void setPaidAmt(double paidAmt) {
		this.paidAmt = paidAmt;
	}

	public LiabilityDO getLiabilityId() {
		return liabilityId;
	}

	public void setLiabilityId(LiabilityDO liabilityId) {
		this.liabilityId = liabilityId;
	}

	/**
	 * @return the isPartialPayment
	 */
	public boolean isPartialPayment() {
		return isPartialPayment;
	}

	/**
	 * @param isPartialPayment the isPartialPayment to set
	 */
	public void setPartialPayment(boolean isPartialPayment) {
		this.isPartialPayment = isPartialPayment;
	}

	/**
	 * @return the collectionEntriesId
	 */
	public Integer getCollectionEntriesId() {
		return collectionEntriesId;
	}

	/**
	 * @param collectionEntriesId the collectionEntriesId to set
	 */
	public void setCollectionEntriesId(Integer collectionEntriesId) {
		this.collectionEntriesId = collectionEntriesId;
	}

	/**
	 * @return the transactionBalanceAmount
	 */
	public Double getTransactionBalanceAmount() {
		return transactionBalanceAmount;
	}

	/**
	 * @param transactionBalanceAmount the transactionBalanceAmount to set
	 */
	public void setTransactionBalanceAmount(Double transactionBalanceAmount) {
		this.transactionBalanceAmount = transactionBalanceAmount;
	}

}
