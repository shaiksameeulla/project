package com.ff.domain.mec.pettycash;

import java.util.Date;

public class PettyCashReportWrapperDO {

	private Integer officeId;
	private Date date;
	private Integer collectionId;

	public PettyCashReportWrapperDO(Integer officeId, Date date) {
		this.officeId = officeId;
		this.date = date;
	}
	
	public PettyCashReportWrapperDO(Integer officeId, Date date, Integer collectionId) {
		this.officeId = officeId;
		this.date = date;
		this.collectionId = collectionId;
	}
	
	public Integer getOfficeId() {
		return officeId;
	}
	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(Integer collectionId) {
		this.collectionId = collectionId;
	}
	
}
