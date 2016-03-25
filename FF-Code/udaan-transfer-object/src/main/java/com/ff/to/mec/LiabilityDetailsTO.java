package com.ff.to.mec;

import java.util.Date;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.capgemini.lbs.framework.utils.StringUtil;

/**
 * @author amimehta
 */

public class LiabilityDetailsTO extends CGBaseTO implements
		Comparable<LiabilityDetailsTO> {

	private static final long serialVersionUID = 1L;

	private Integer libilityDetailsId;
	private Integer liabilityId;
	private String consgNo;
	private Integer consgId;
	private Double codLcAmt;
	private Double collectedAmt;
	private Double paidAmt;
	private String isSelect;
	private Integer position;
	private Double balanceAmount;

	// Changes during UAT-4 24/05/2014
	private String bookingDate;
	private Date bookingUtilDate;//duplicate variable for sorting purpose only
	private Integer collectionEntriesId;

	
	/**
	 * @return the bookingDate
	 */
	public String getBookingDate() {
		return bookingDate;
	}

	/**
	 * @param bookingDate
	 *            the bookingDate to set
	 */
	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getConsgNo() {
		return consgNo;
	}

	public void setConsgNo(String consgNo) {
		this.consgNo = consgNo;
	}

	public Integer getConsgId() {
		return consgId;
	}

	public void setConsgId(Integer consgId) {
		this.consgId = consgId;
	}

	public Double getCodLcAmt() {
		return codLcAmt;
	}

	public void setCodLcAmt(Double codLcAmt) {
		this.codLcAmt = codLcAmt;
	}

	public Double getCollectedAmt() {
		return collectedAmt;
	}

	public void setCollectedAmt(Double collectedAmt) {
		this.collectedAmt = collectedAmt;
	}

	public Double getPaidAmt() {
		return paidAmt;
	}

	public void setPaidAmt(Double paidAmt) {
		this.paidAmt = paidAmt;
	}

	public String getIsSelect() {
		return isSelect;
	}

	public void setIsSelect(String isSelect) {
		this.isSelect = isSelect;
	}

	/**
	 * @return the liabilityId
	 */
	public Integer getLiabilityId() {
		return liabilityId;
	}

	/**
	 * @param liabilityId
	 *            the liabilityId to set
	 */
	public void setLiabilityId(Integer liabilityId) {
		this.liabilityId = liabilityId;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getLibilityDetailsId() {
		return libilityDetailsId;
	}

	public void setLibilityDetailsId(Integer libilityDetailsId) {
		this.libilityDetailsId = libilityDetailsId;
	}

	@Override
	public int compareTo(LiabilityDetailsTO obj) {
		int result = 0;
		if (!StringUtil.isEmptyInteger(position)
				&& !StringUtil.isEmptyInteger(obj.getPosition())) {
			result = this.position.compareTo(obj.position);
		} else if (!StringUtil.isEmptyInteger(libilityDetailsId)
				&& !StringUtil.isEmptyInteger(obj.getLibilityDetailsId())) {
			result = this.libilityDetailsId.compareTo(obj.libilityDetailsId);
		}else if(bookingUtilDate!=null && !StringUtil.isNull(obj.getBookingUtilDate())){
			result=this.bookingUtilDate.compareTo(obj.getBookingUtilDate());
		}
		return result;
	}

	/**
	 * @return the bookingUtilDate
	 */
	public Date getBookingUtilDate() {
		return bookingUtilDate;
	}

	/**
	 * @param bookingUtilDate the bookingUtilDate to set
	 */
	public void setBookingUtilDate(Date bookingUtilDate) {
		this.bookingUtilDate = bookingUtilDate;
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

}
