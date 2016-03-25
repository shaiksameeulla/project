package com.ff.domain.mec;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author mohammes
 */

public class LiabilityCollectionWrapperDO extends CGFactDO {

	private static final long serialVersionUID = -1468938827960879104L;
	
	/** primary key. */
	private Integer collectionEntryId;
	private Integer consgId;
	private String consgNo;
	private Double billAmount;
	private Double totalBillAmount;
	private Double balanceAmount;
	private Integer position;
	private Date bookingDate;
	/**
	 * @return the collectionEntryId
	 */
	public Integer getCollectionEntryId() {
		return collectionEntryId;
	}
	/**
	 * @param collectionEntryId the collectionEntryId to set
	 */
	public void setCollectionEntryId(Integer collectionEntryId) {
		this.collectionEntryId = collectionEntryId;
	}
	/**
	 * @return the consgId
	 */
	public Integer getConsgId() {
		return consgId;
	}
	/**
	 * @param consgId the consgId to set
	 */
	public void setConsgId(Integer consgId) {
		this.consgId = consgId;
	}
	/**
	 * @return the consgNo
	 */
	public String getConsgNo() {
		return consgNo;
	}
	/**
	 * @param consgNo the consgNo to set
	 */
	public void setConsgNo(String consgNo) {
		this.consgNo = consgNo;
	}
	
	
	/**
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}
	/**
	 * @return the bookingDate
	 */
	public Date getBookingDate() {
		return bookingDate;
	}
	/**
	 * @param bookingDate the bookingDate to set
	 */
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	/**
	 * @return the billAmount
	 */
	public Double getBillAmount() {
		return billAmount;
	}
	/**
	 * @param billAmount the billAmount to set
	 */
	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}
	/**
	 * @return the totalBillAmount
	 */
	public Double getTotalBillAmount() {
		return totalBillAmount;
	}
	/**
	 * @param totalBillAmount the totalBillAmount to set
	 */
	public void setTotalBillAmount(Double totalBillAmount) {
		this.totalBillAmount = totalBillAmount;
	}
	/**
	 * @return the balanceAmount
	 */
	public Double getBalanceAmount() {
		return balanceAmount;
	}
	/**
	 * @param balanceAmount the balanceAmount to set
	 */
	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	/**
	 * @param collectionEntryId
	 * @param consgId
	 * @param consgNo
	 * @param codLcAmt
	 * @param collectedAmt
	 * @param paidAmt
	 * @param bookingDate
	 */
	public LiabilityCollectionWrapperDO(Integer collectionEntryId,
			Integer consgId, String consgNo, Double billAmount,
			Double totalBillAmount,Date bookingDate,Double balanceAmount) {
		super();
		this.collectionEntryId = collectionEntryId;
		this.consgId = consgId;
		this.consgNo = consgNo;
		this.billAmount = billAmount;
		this.totalBillAmount = totalBillAmount;
		this.bookingDate=bookingDate;
		this.balanceAmount=balanceAmount;
	}
}
