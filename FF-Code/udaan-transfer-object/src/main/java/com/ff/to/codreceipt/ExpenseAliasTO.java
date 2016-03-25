package com.ff.to.codreceipt;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class ExpenseAliasTO extends CGBaseTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5304030606106755924L;
	private String glDescription;
	private double amount;
	private double seviceCharge;
	private double serviceTax;
	private double educationCess;
	private double higherEduCess;
	private double otherCharges;
	private double totalExpenseAmt;
	
	public String getGlDescription() {
		return glDescription;
	}
	public void setGlDescription(String glDescription) {
		this.glDescription = glDescription;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getSeviceCharge() {
		return seviceCharge;
	}
	public void setSeviceCharge(double seviceCharge) {
		this.seviceCharge = seviceCharge;
	}
	public double getServiceTax() {
		return serviceTax;
	}
	public void setServiceTax(double serviceTax) {
		this.serviceTax = serviceTax;
	}
	public double getEducationCess() {
		return educationCess;
	}
	public void setEducationCess(double educationCess) {
		this.educationCess = educationCess;
	}
	public double getHigherEduCess() {
		return higherEduCess;
	}
	public void setHigherEduCess(double higherEduCess) {
		this.higherEduCess = higherEduCess;
	}
	public double getOtherCharges() {
		return otherCharges;
	}
	public void setOtherCharges(double otherCharges) {
		this.otherCharges = otherCharges;
	}
	public double getTotalExpenseAmt() {
		return totalExpenseAmt;
	}
	public void setTotalExpenseAmt(double totalExpenseAmt) {
		this.totalExpenseAmt = totalExpenseAmt;
	}
	
	
	
}
