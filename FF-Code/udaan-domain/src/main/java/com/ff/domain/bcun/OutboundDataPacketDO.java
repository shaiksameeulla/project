/**
 * 
 */
package com.ff.domain.bcun;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGMasterDO;

/**
 * @author mohammes
 *
 */
public class OutboundDataPacketDO extends CGMasterDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -178719985174823116L;
	
	private  Long dataPacketId;
	private byte[] packetData;
	private String fileName;
	private Date transactionCreateDate= Calendar.getInstance().getTime();
	
	private Set<OutboundOfficePacketDO> outboundDtls;
	
	/**
	 * @return the dataPacketId
	 */
	public Long getDataPacketId() {
		return dataPacketId;
	}
	
	
	
	/**
	 * @param dataPacketId the dataPacketId to set
	 */
	public void setDataPacketId(Long dataPacketId) {
		this.dataPacketId = dataPacketId;
	}
	
	
	
	/**
	 * @return the packetData
	 */
	public byte[] getPacketData() {
		return packetData;
	}
	/**
	 * @param packetData the packetData to set
	 */
	public void setPacketData(byte[] packetData) {
		this.packetData = packetData;
	}
	/**
	 * @return the outboundDtls
	 */
	public Set<OutboundOfficePacketDO> getOutboundDtls() {
		return outboundDtls;
	}
	/**
	 * @param outboundDtls the outboundDtls to set
	 */
	public void setOutboundDtls(Set<OutboundOfficePacketDO> outboundDtls) {
		this.outboundDtls = outboundDtls;
	}



	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}



	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}



	/**
	 * @return the transactionCreateDate
	 */
	public Date getTransactionCreateDate() {
		return transactionCreateDate;
	}



	/**
	 * @param transactionCreateDate the transactionCreateDate to set
	 */
	public void setTransactionCreateDate(Date transactionCreateDate) {
		this.transactionCreateDate = transactionCreateDate;
	}

}
