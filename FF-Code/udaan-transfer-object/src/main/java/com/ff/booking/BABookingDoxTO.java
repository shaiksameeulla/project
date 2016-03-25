package com.ff.booking;

import java.util.ArrayList;
import java.util.List;


/**
 * The Class BABookingDoxTO.
 *
 * @author Narasimha Rao kattunga
 */
public class BABookingDoxTO extends BookingGridTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7399277554097884966L;

	/** The biz associate id. */
	//private Integer bizAssociateId;
	
	/** The biz associate code. */
	private Integer bizAssociateCode;
	
	/** The ref no. */
	private String refNo;

	/**
	 * Gets the biz associate code.
	 *
	 * @return the biz associate code
	 */
	public Integer getBizAssociateCode() {
		return bizAssociateCode;
	}

	/**
	 * Sets the biz associate code.
	 *
	 * @param bizAssociateCode the new biz associate code
	 */
	public void setBizAssociateCode(Integer bizAssociateCode) {
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

}
