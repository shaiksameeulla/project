package com.ff.to.mec;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author hkansagr
 */

public class BankTO extends CGBaseTO {
	
	private static final long serialVersionUID = 1L;
	private Integer bankId;
	private String bankName;
	private String bankGL;
	
	/**
	 * @return the bankId
	 */
	public Integer getBankId() {
		return bankId;
	}
	/**
	 * @param bankId the bankId to set
	 */
	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}
	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}
	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	/**
	 * @return the bankGL
	 */
	public String getBankGL() {
		return bankGL;
	}
	/**
	 * @param bankGL the bankGL to set
	 */
	public void setBankGL(String bankGL) {
		this.bankGL = bankGL;
	}
	
}
