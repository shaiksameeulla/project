package com.ff.to.drs;

import java.util.List;


/**
 * @author nihsingh
 *
 */
public class CodLcDrsTO extends AbstractDeliveryTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The row Cod amount. */
	private Double rowCodAmount[]= new Double[rowCount];
	
	/** The row LC amount. */
	private Double rowLCAmount[]= new Double[rowCount];
	
	/** The row ToPay Amount. */
	private Double rowToPayAmount[]= new Double[rowCount];
	
	/** The row OtherCharges. */
	private Double rowOtherCharges[]= new Double[rowCount];
	
	private Double rowBaAmount[]= new Double[rowCount];
	
	/** The row mode of payment. */
	private String rowModeOfPayment[]= new String[rowCount];
	
	/** The row cheque no. */
	private String rowChequeNo[]= new String[rowCount];
	
	/** The row cheque date. */
	private String  rowChequeDate[]= new String[rowCount];
	
	/** The row cheque date. */
	private String rowBankNameAndBranch[]= new String[rowCount];
	
	private String rowIsPaymentAlreadyCaptured[]= new String[rowCount];
	
	
	/** The mode of payment type as cheque. */
	private String modeOfPaymentCheque;
	
	
	/** The mode of payment type as cash. */
	private String modeOfPaymentCash;
	 
 	/** The codLcDrsDetailsToList. */
	List<CodLcDrsDetailsTO> codLcDrsDetailsToList;


	/**
	 * Gets the row cod amount.
	 *
	 * @return CodAmount
	 */
	public Double[] getRowCodAmount() {
		return rowCodAmount;
	}


	/**
	 * Sets the row cod amount.
	 *
	 * @param rowCodAmount the new row cod amount
	 */
	public void setRowCodAmount(Double[] rowCodAmount) {
		this.rowCodAmount = rowCodAmount;
	}


	/**
	 * Gets the row lc amount.
	 *
	 * @return RowLCAmount
	 */
	public Double[] getRowLCAmount() {
		return rowLCAmount;
	}


	/**
	 * Sets the row lc amount.
	 *
	 * @param rowLCAmount the new row lc amount
	 */
	public void setRowLCAmount(Double[] rowLCAmount) {
		this.rowLCAmount = rowLCAmount;
	}


	/**
	 * Gets the row to pay amount.
	 *
	 * @return RowToPayAmount
	 */
	public Double[] getRowToPayAmount() {
		return rowToPayAmount;
	}


	/**
	 * Sets the row to pay amount.
	 *
	 * @param rowToPayAmount the new row to pay amount
	 */
	public void setRowToPayAmount(Double[] rowToPayAmount) {
		this.rowToPayAmount = rowToPayAmount;
	}


	/**
	 * Gets the row other charges.
	 *
	 * @return RowOtherCharges
	 */
	public Double[] getRowOtherCharges() {
		return rowOtherCharges;
	}


	/**
	 * Sets the row other charges.
	 *
	 * @param rowOtherCharges the new row other charges
	 */
	public void setRowOtherCharges(Double[] rowOtherCharges) {
		this.rowOtherCharges = rowOtherCharges;
	}


	/**
	 * Gets the cod lc drs details to list.
	 *
	 * @return List<CodLcDrsDetailsTO>
	 */
	public List<CodLcDrsDetailsTO> getCodLcDrsDetailsToList() {
		return codLcDrsDetailsToList;
	}


	/**
	 * Sets the cod lc drs details to list.
	 *
	 * @param codLcDrsDetailsToList the new cod lc drs details to list
	 */
	public void setCodLcDrsDetailsToList(
			List<CodLcDrsDetailsTO> codLcDrsDetailsToList) {
		this.codLcDrsDetailsToList = codLcDrsDetailsToList;
	}


	/**
	 * Gets the row mode of payment.
	 *
	 * @return rowModeOfPayment
	 */
	public String[] getRowModeOfPayment() {
		return rowModeOfPayment;
	}


	/**
	 * Sets the row mode of payment.
	 *
	 * @param rowModeOfPayment the new row mode of payment
	 */
	public void setRowModeOfPayment(String[] rowModeOfPayment) {
		this.rowModeOfPayment = rowModeOfPayment;
	}


	/**
	 * Gets the row cheque no.
	 *
	 * @return rowChequeNo
	 */
	public String[] getRowChequeNo() {
		return rowChequeNo;
	}


	/**
	 * Sets the row cheque no.
	 *
	 * @param rowChequeNo the new row cheque no
	 */
	public void setRowChequeNo(String[] rowChequeNo) {
		this.rowChequeNo = rowChequeNo;
	}


	/**
	 * Gets the row cheque date.
	 *
	 * @return rowChequeDate
	 */
	public String[] getRowChequeDate() {
		return rowChequeDate;
	}


	/**
	 * Sets the row cheque date.
	 *
	 * @param rowChequeDate the new row cheque date
	 */
	public void setRowChequeDate(String[] rowChequeDate) {
		this.rowChequeDate = rowChequeDate;
	}


	/**
	 * Gets the row bank name and branch.
	 *
	 * @return rowBankNameAndBranch
	 */
	public String[] getRowBankNameAndBranch() {
		return rowBankNameAndBranch;
	}


	/**
	 * Sets the row bank name and branch.
	 *
	 * @param rowBankNameAndBranch the new row bank name and branch
	 */
	public void setRowBankNameAndBranch(String[] rowBankNameAndBranch) {
		this.rowBankNameAndBranch = rowBankNameAndBranch;
	}


	/**
	 * Gets the mode of payment cheque.
	 *
	 * @return modeOfPaymentCheque
	 */
	public String getModeOfPaymentCheque() {
		return modeOfPaymentCheque;
	}


	/**
	 * Sets the mode of payment cheque.
	 *
	 * @param modeOfPaymentCheque the new mode of payment cheque
	 */
	public void setModeOfPaymentCheque(String modeOfPaymentCheque) {
		this.modeOfPaymentCheque = modeOfPaymentCheque;
	}


	/**
	 * Gets the mode of payment cash.
	 *
	 * @return the mode of payment cash
	 */
	public String getModeOfPaymentCash() {
		return modeOfPaymentCash;
	}


	/**
	 * @return the rowBaAmount
	 */
	public Double[] getRowBaAmount() {
		return rowBaAmount;
	}


	/**
	 * @param rowBaAmount the rowBaAmount to set
	 */
	public void setRowBaAmount(Double[] rowBaAmount) {
		this.rowBaAmount = rowBaAmount;
	}


	/**
	 * Sets the mode of payment cash.
	 *
	 * @param modeOfPaymentCash the new mode of payment cash
	 */
	public void setModeOfPaymentCash(String modeOfPaymentCash) {
		this.modeOfPaymentCash = modeOfPaymentCash;
	}


	/**
	 * @return the rowIsPaymentAlreadyCaptured
	 */
	public String[] getRowIsPaymentAlreadyCaptured() {
		return rowIsPaymentAlreadyCaptured;
	}


	/**
	 * @param rowIsPaymentAlreadyCaptured the rowIsPaymentAlreadyCaptured to set
	 */
	public void setRowIsPaymentAlreadyCaptured(String[] rowIsPaymentAlreadyCaptured) {
		this.rowIsPaymentAlreadyCaptured = rowIsPaymentAlreadyCaptured;
	}

}
