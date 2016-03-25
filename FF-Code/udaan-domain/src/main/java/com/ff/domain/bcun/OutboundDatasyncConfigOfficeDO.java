package com.ff.domain.bcun;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.organization.OfficeDO;

public class OutboundDatasyncConfigOfficeDO extends CGMasterDO {
	
	private static final long serialVersionUID = 1L;
	
	private int datasyncId;
	private OfficeDO outboundOffice;
	private int officeLavel;
	private Date scheduleTime;
	private String recordStatus;
	private String isActiveForOpsman;
	private String outboundOfficeCategory;
	
	public int getDatasyncId() {
		return datasyncId;
	}
	public void setDatasyncId(int datasyncId) {
		this.datasyncId = datasyncId;
	}
	public OfficeDO getOutboundOffice() {
		return outboundOffice;
	}
	public void setOutboundOffice(OfficeDO outboundOffice) {
		this.outboundOffice = outboundOffice;
	}
	public int getOfficeLavel() {
		return officeLavel;
	}
	public void setOfficeLavel(int officeLavel) {
		this.officeLavel = officeLavel;
	}
	public Date getScheduleTime() {
		return scheduleTime;
	}
	public void setScheduleTime(Date scheduleTime) {
		this.scheduleTime = scheduleTime;
	}
	public String getRecordStatus() {
		return recordStatus;
	}
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
	public String getIsActiveForOpsman() {
		return isActiveForOpsman;
	}
	public void setIsActiveForOpsman(String isActiveForOpsman) {
		this.isActiveForOpsman = isActiveForOpsman;
	}
	/**
	 * @return the outboundOfficeCategory
	 */
	public String getOutboundOfficeCategory() {
		return outboundOfficeCategory;
	}
	/**
	 * @param outboundOfficeCategory the outboundOfficeCategory to set
	 */
	public void setOutboundOfficeCategory(String outboundOfficeCategory) {
		this.outboundOfficeCategory = outboundOfficeCategory;
	}
	
}
