package com.ff.domain.manifest;

import com.capgemini.lbs.framework.domain.CGMasterDO;

// TODO: Auto-generated Javadoc
/**
 * The Class BagRadioFrequencyDO.
 */
public class BagRadioFrequencyDO extends CGMasterDO{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8564715375853682478L;

	/** The bag rfid id. */
	private Integer bagRfidId;
	
	/** The rfid no. */
	private String rfidNo;
	
	/**
	 * Gets the bag rfid id.
	 *
	 * @return the bag rfid id
	 */
	public Integer getBagRfidId() {
		return bagRfidId;
	}
	
	/**
	 * Sets the bag rfid id.
	 *
	 * @param bagRfidId the new bag rfid id
	 */
	public void setBagRfidId(Integer bagRfidId) {
		this.bagRfidId = bagRfidId;
	}
	
	/**
	 * Gets the rfid no.
	 *
	 * @return the rfid no
	 */
	public String getRfidNo() {
		return rfidNo;
	}
	
	/**
	 * Sets the rfid no.
	 *
	 * @param rfidNo the new rfid no
	 */
	public void setRfidNo(String rfidNo) {
		this.rfidNo = rfidNo;
	}
	
	
}
