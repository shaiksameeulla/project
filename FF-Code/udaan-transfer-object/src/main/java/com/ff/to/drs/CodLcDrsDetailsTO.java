package com.ff.to.drs;

import com.capgemini.lbs.framework.utils.StringUtil;

/**
 * @author nihsingh
 *
 */
public class CodLcDrsDetailsTO extends AbstractDeliveryDetailTO implements Comparable<CodLcDrsDetailsTO> {

	/**
	 * The Serial version id
	 */
	private static final long serialVersionUID = 1L;
	
	/** codAmount. */
	private Double codAmount;
	
	/** The lc amount. */
	private Double lcAmount;
	
	/** The to pay amount. */
	private Double toPayAmount;
	
	/** The other Charges. */
	private Double otherCharges;
	
	private Double baAmount;
	
	/** The mode of payment. */
	private String modeOfPayment;
	
	/** The chequeNo. */
	private String chequeNo;
	
	/** The chequeDate. */
	private String chequeDate;
	
	/** The bankNameAndBranch. */
	private String bankNameAndBranch;
	
	private String isPaymentAlreadyCaptured="N";
	
	
	
	/**
	 * Gets the cod amount.
	 *
	 * @return cod amount
	 */
	public Double getCodAmount() {
		return codAmount;
	}
	
	/**
	 * Sets the cod amount.
	 *
	 * @param codAmount the new cod amount
	 */
	public void setCodAmount(Double codAmount) {
		this.codAmount = codAmount;
	}
	
	/**
	 * Gets the lc amount.
	 *
	 * @return lc amount
	 */
	public Double getLcAmount() {
		return lcAmount;
	}
	
	/**
	 * Sets the lc amount.
	 *
	 * @param lcAmount the new lc amount
	 */
	public void setLcAmount(Double lcAmount) {
		this.lcAmount = lcAmount;
	}
	
	/**
	 * Gets the to pay amount.
	 *
	 * @return to pay amount
	 */
	public Double getToPayAmount() {
		return toPayAmount;
	}
	
	/**
	 * Sets the to pay amount.
	 *
	 * @param toPayAmount the new to pay amount
	 */
	public void setToPayAmount(Double toPayAmount) {
		this.toPayAmount = toPayAmount;
	}
	
	/**
	 * Gets the other charges.
	 *
	 * @return other charges
	 */
	public Double getOtherCharges() {
		return otherCharges;
	}
	
	/**
	 * Sets the other charges.
	 *
	 * @param otherCharges the new other charges
	 */
	public void setOtherCharges(Double otherCharges) {
		this.otherCharges = otherCharges;
	}
	
	
	
	/**
	 * Gets the mode of payment.
	 *
	 * @return ModeOfPayment
	 */
	public String getModeOfPayment() {
		return modeOfPayment;
	}
	
	/**
	 * Sets the mode of payment.
	 *
	 * @param modeOfPayment the new mode of payment
	 */
	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}
	
	/**
	 * Gets the cheque no.
	 *
	 * @return chequeNo
	 */
	public String getChequeNo() {
		return chequeNo;
	}
	
	/**
	 * Sets the cheque no.
	 *
	 * @param chequeNo the new cheque no
	 */
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}
	
	/**
	 * Gets the cheque date.
	 *
	 * @return chequeDate
	 */
	public String getChequeDate() {
		return chequeDate;
	}
	
	/**
	 * Sets the cheque date.
	 *
	 * @param chequeDate the new cheque date
	 */
	public void setChequeDate(String chequeDate) {
		this.chequeDate = chequeDate;
	}
	
	/**
	 * Gets the bank name and branch.
	 *
	 * @return bankNameAndBranch
	 */
	public String getBankNameAndBranch() {
		return bankNameAndBranch;
	}

	/**
	 * Sets the bank name and branch.
	 *
	 * @param bankNameAndBranch the new bank name and branch
	 */
	public void setBankNameAndBranch(String bankNameAndBranch) {
		this.bankNameAndBranch = bankNameAndBranch;
	}
	@Override
	public int compareTo(CodLcDrsDetailsTO arg0) {
		int result=0;
		if(!StringUtil.isEmptyInteger(rowNumber) && !StringUtil.isEmptyInteger(arg0.getRowNumber())) {
			result = this.rowNumber.compareTo(arg0.rowNumber);
		}
		else if(!StringUtil.isEmptyLong(deliveryDetailId) && !StringUtil.isEmptyLong(arg0.getDeliveryDetailId())) {
			result = this.deliveryDetailId.compareTo(arg0.deliveryDetailId);
		}
		return result;
	}

	/**
	 * @return the isPaymentAlreadyCaptured
	 */
	public String getIsPaymentAlreadyCaptured() {
		return isPaymentAlreadyCaptured;
	}

	/**
	 * @param isPaymentAlreadyCaptured the isPaymentAlreadyCaptured to set
	 */
	public void setIsPaymentAlreadyCaptured(String isPaymentAlreadyCaptured) {
		this.isPaymentAlreadyCaptured = isPaymentAlreadyCaptured;
	}

	/**
	 * @return the baAmount
	 */
	public Double getBaAmount() {
		return baAmount;
	}

	/**
	 * @param baAmount the baAmount to set
	 */
	public void setBaAmount(Double baAmount) {
		this.baAmount = baAmount;
	}
}
