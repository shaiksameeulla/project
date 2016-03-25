package com.ff.booking;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class BABookingParcelTO.
 *
 * @author Narasimha Rao kattunga
 */
public class BABookingParcelTO extends BookingParcelTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6393996332394260391L;

	/** The biz associate id. */
	//private Integer bizAssociateId;
	
	/** The biz associate code. */
	private String bizAssociateCode;
	
	/** The ref no. */
	private String refNo;
	
	private String printRowTotal;

	// Internal use for data is traveling from UI to action

	/**
	 * Gets the biz associate id.
	 *
	 * @return the biz associate id
	 *//*
	public Integer getBizAssociateId() {
		return bizAssociateId;
	}

	*//**
	 * Sets the biz associate id.
	 *
	 * @param bizAssociateId the new biz associate id
	 *//*
	public void setBizAssociateId(Integer bizAssociateId) {
		this.bizAssociateId = bizAssociateId;
	}*/

	/**
	 * Gets the biz associate code.
	 *
	 * @return the biz associate code
	 */
	public String getBizAssociateCode() {
		return bizAssociateCode;
	}

	/**
	 * Sets the biz associate code.
	 *
	 * @param bizAssociateCode the new biz associate code
	 */
	public void setBizAssociateCode(String bizAssociateCode) {
		this.bizAssociateCode = bizAssociateCode;
	}

	/**
	 * Gets the ref no.
	 *
	 * @return the ref no
	 */
	public String getRefNo() {
		return refNo;
	}

	/**
	 * Sets the ref no.
	 *
	 * @param refNo the new ref no
	 */
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getPrintRowTotal() {
		return printRowTotal;
	}

	public void setPrintRowTotal(String printRowTotal) {
		this.printRowTotal = printRowTotal;
	}



	
}
