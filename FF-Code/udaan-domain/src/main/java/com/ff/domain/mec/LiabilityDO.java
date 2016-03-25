package com.ff.domain.mec;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.geography.RegionDO;
import com.ff.domain.organization.OfficeDO;

/**
 * @author amimehta
 */

public class LiabilityDO extends CGFactDO {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2140534804865018345L;

	/** primary key. */
	private Integer liabilityId;
	
	private Date liabilityDate;
	
	/** The bank GL code. */
	private RegionDO regionId;
	private CustomerDO custId;
	private String chqNo;
	private Date chqDate;
	private GLMasterDO bankId;
	private double liabiltyAmt;
	private String status; 
	private String txNumber;
	private String paymentMode;
	private OfficeDO liabilityOffice;
	
	private Set<LiabilityDetailsDO> liabilityDetailsList;
	
	public Integer getLiabilityId() {
		return liabilityId;
	}
	public void setLiabilityId(Integer liabilityId) {
		this.liabilityId = liabilityId;
	}
	
	
	public Date getLiabilityDate() {
		return liabilityDate;
	}
	public void setLiabilityDate(Date liabilityDate) {
		this.liabilityDate = liabilityDate;
	}
	public RegionDO getRegionId() {
		return regionId;
	}
	public void setRegionId(RegionDO regionId) {
		this.regionId = regionId;
	}
	public CustomerDO getCustId() {
		return custId;
	}
	public void setCustId(CustomerDO custId) {
		this.custId = custId;
	}
	public String getChqNo() {
		return chqNo;
	}
	public void setChqNo(String chqNo) {
		this.chqNo = chqNo;
	}
	public Date getChqDate() {
		return chqDate;
	}
	public void setChqDate(Date chqDate) {
		this.chqDate = chqDate;
	}
	
	
	
	public GLMasterDO getBankId() {
		return bankId;
	}
	public void setBankId(GLMasterDO bankId) {
		this.bankId = bankId;
	}
	public double getLiabiltyAmt() {
		return liabiltyAmt;
	}
	public void setLiabiltyAmt(double liabiltyAmt) {
		this.liabiltyAmt = liabiltyAmt;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Set<LiabilityDetailsDO> getLiabilityDetailsList() {
		return liabilityDetailsList;
	}
	public void setLiabilityDetailsList(Set<LiabilityDetailsDO> liabilityDetailsList) {
		this.liabilityDetailsList = liabilityDetailsList;
	}
	/**
	 * @return the txNumber
	 */
	public String getTxNumber() {
		return txNumber;
	}
	/**
	 * @param txNumber the txNumber to set
	 */
	public void setTxNumber(String txNumber) {
		this.txNumber = txNumber;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public OfficeDO getLiabilityOffice() {
		return liabilityOffice;
	}
	public void setLiabilityOffice(OfficeDO liabilityOffice) {
		this.liabilityOffice = liabilityOffice;
	}
	
	
	
}
