/**
 * 
 */
package com.ff.domain.bcun;

import java.util.Calendar;
import java.util.Date;

import com.capgemini.lbs.framework.domain.CGMasterDO;

/**
 * @author mohammes
 *
 */
public class OutboundOfficePacketDO extends CGMasterDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7608779867530351722L;
	private Long datasyncId;
	private Integer outboundOfficeId;
	private Date packetCreatedDate=Calendar.getInstance().getTime();
	private String transferStatus;
	private Date processedDate;
	private String processName;
	
	private OutboundDataPacketDO packetDO;
	
	
	/**
	 * @return the packetCreatedDate
	 */
	public Date getPacketCreatedDate() {
		return packetCreatedDate;
	}
	/**
	 * @return the transferStatus
	 */
	public String getTransferStatus() {
		return transferStatus;
	}
	/**
	 * @return the processedDate
	 */
	public Date getProcessedDate() {
		return processedDate;
	}
	/**
	 * @return the processName
	 */
	public String getProcessName() {
		return processName;
	}
	
	
	/**
	 * @param packetCreatedDate the packetCreatedDate to set
	 */
	public void setPacketCreatedDate(Date packetCreatedDate) {
		this.packetCreatedDate = packetCreatedDate;
	}
	/**
	 * @param transferStatus the transferStatus to set
	 */
	public void setTransferStatus(String transferStatus) {
		this.transferStatus = transferStatus;
	}
	/**
	 * @param processedDate the processedDate to set
	 */
	public void setProcessedDate(Date processedDate) {
		this.processedDate = processedDate;
	}
	/**
	 * @param processName the processName to set
	 */
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	/**
	 * @return the packetDO
	 */
	public OutboundDataPacketDO getPacketDO() {
		return packetDO;
	}
	/**
	 * @param packetDO the packetDO to set
	 */
	public void setPacketDO(OutboundDataPacketDO packetDO) {
		this.packetDO = packetDO;
	}
	/**
	 * @return the datasyncId
	 */
	public Long getDatasyncId() {
		return datasyncId;
	}
	/**
	 * @param datasyncId the datasyncId to set
	 */
	public void setDatasyncId(Long datasyncId) {
		this.datasyncId = datasyncId;
	}
	/**
	 * @return the outboundOfficeId
	 */
	public Integer getOutboundOfficeId() {
		return outboundOfficeId;
	}
	/**
	 * @param outboundOfficeId the outboundOfficeId to set
	 */
	public void setOutboundOfficeId(Integer outboundOfficeId) {
		this.outboundOfficeId = outboundOfficeId;
	}
	
	

}
