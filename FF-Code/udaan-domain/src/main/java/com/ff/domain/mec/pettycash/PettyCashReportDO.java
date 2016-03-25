package com.ff.domain.mec.pettycash;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author hkansagr
 * 
 */
public class PettyCashReportDO extends CGFactDO {

	private static final long serialVersionUID = 1L;

	/** The pettyCashId - Primary Key */
	private Long pettyCashId;

	/** The closingDate - dd/MM/yyyy. */
	private Date closingDate;

	/** The closingBalance - Closing balance (it may contain negative value) */
	private Double closingBalance;

	/** The officeId - Logged In Office */
	private Integer officeId;

	/** The flag for is petty cash data update required or not. */
	private String isUpdateReq = CommonConstants.NO;

	/** The list of consignment numbers considered by petty cash for calculating the closing balance. This list will be updated in the DB separately */
	private List<String> consgNosConsideredForPettyCash; 
	
	/**
	 * @return the isUpdateReq
	 */
	public String getIsUpdateReq() {
		return isUpdateReq;
	}

	/**
	 * @param isUpdateReq
	 *            the isUpdateReq to set
	 */
	public void setIsUpdateReq(String isUpdateReq) {
		this.isUpdateReq = isUpdateReq;
	}

	/**
	 * @return the pettyCashId
	 */
	public Long getPettyCashId() {
		return pettyCashId;
	}

	/**
	 * @param pettyCashId
	 *            the pettyCashId to set
	 */
	public void setPettyCashId(Long pettyCashId) {
		this.pettyCashId = pettyCashId;
	}

	/**
	 * @return the closingDate
	 */
	public Date getClosingDate() {
		return closingDate;
	}

	/**
	 * @param closingDate
	 *            the closingDate to set
	 */
	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
	}

	/**
	 * @return the closingBalance
	 */
	public Double getClosingBalance() {
		return closingBalance;
	}

	/**
	 * @param closingBalance
	 *            the closingBalance to set
	 */
	public void setClosingBalance(Double closingBalance) {
		this.closingBalance = closingBalance;
	}

	/**
	 * @return the officeId
	 */
	public Integer getOfficeId() {
		return officeId;
	}

	/**
	 * @param officeId
	 *            the officeId to set
	 */
	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}

	/**
	 * @return List<String>
	 */
	public List<String> getConsgNosConsideredForPettyCash() {
		return consgNosConsideredForPettyCash;
	}

	/**
	 * @param consgNosConsideredForPettyCash
	 */
	public void setConsgNosConsideredForPettyCash(
			List<String> consgNosConsideredForPettyCash) {
		this.consgNosConsideredForPettyCash = consgNosConsideredForPettyCash;
	}

}
