package com.ff.domain.billing;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class CustModificationDO extends CGFactDO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer custModificationId;
	private Integer consgId;
	private Integer oldCustId;
	private Integer newCustId;
	public Integer getCustModificationId() {
		return custModificationId;
	}
	public void setCustModificationId(Integer custModificationId) {
		this.custModificationId = custModificationId;
	}
	public Integer getConsgId() {
		return consgId;
	}
	public void setConsgId(Integer consgId) {
		this.consgId = consgId;
	}
	public Integer getOldCustId() {
		return oldCustId;
	}
	public void setOldCustId(Integer oldCustId) {
		this.oldCustId = oldCustId;
	}
	public Integer getNewCustId() {
		return newCustId;
	}
	public void setNewCustId(Integer newCustId) {
		this.newCustId = newCustId;
	}
	
	

}
