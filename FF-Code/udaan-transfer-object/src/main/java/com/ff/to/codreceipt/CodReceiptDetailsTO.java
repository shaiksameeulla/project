package com.ff.to.codreceipt;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class CodReceiptDetailsTO extends CGBaseTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3041048982955621376L;
	private String expenseDescription;
	
	private Double expenseTotalAmount;

	public String getExpenseDescription() {
		return expenseDescription;
	}

	public void setExpenseDescription(String expenseDescription) {
		this.expenseDescription = expenseDescription;
	}

	public Double getExpenseTotalAmount() {
		return expenseTotalAmount;
	}

	public void setExpenseTotalAmount(Double expenseTotalAmount) {
		this.expenseTotalAmount = expenseTotalAmount;
	}
	
	

	
}
