/**
 * 
 */
package com.ff.domain.bcun;

import com.capgemini.lbs.framework.domain.CGBaseDO;

/**
 * @author mohammes
 *
 */
public class OutboundDataPacketWrapperDO extends CGBaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1787123985174823116L;
	
	private  Long dataOfficePacketId;
	private byte[] packetData;
	/**
	 * @return the dataOfficePacketId
	 */
	public Long getDataOfficePacketId() {
		return dataOfficePacketId;
	}
	/**
	 * @return the packetData
	 */
	public byte[] getPacketData() {
		return packetData;
	}
	/**
	 * @param dataOfficePacketId the dataOfficePacketId to set
	 */
	public void setDataOfficePacketId(Long dataOfficePacketId) {
		this.dataOfficePacketId = dataOfficePacketId;
	}
	/**
	 * @param packetData the packetData to set
	 */
	public void setPacketData(byte[] packetData) {
		this.packetData = packetData;
	}
	public OutboundDataPacketWrapperDO(Long dataOfficePacketId,
			byte[] packetData) {
		super();
		this.dataOfficePacketId = dataOfficePacketId;
		this.packetData = packetData;
	}
	public OutboundDataPacketWrapperDO() {
		super();
	}
	
}
