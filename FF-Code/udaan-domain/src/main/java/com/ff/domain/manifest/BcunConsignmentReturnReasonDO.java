package com.ff.domain.manifest;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;


/**
 * The Class BcunConsignmentReturnReasonDO.
 *
 * @author narmdr
 */
public class BcunConsignmentReturnReasonDO extends CGFactDO {
	
	private static final long serialVersionUID = 5757787984217818242L;
	private Integer consignmentReturnReasonId;
	private Date date;
	private String time;
	private String remarks;
	//private ReasonDO reasonDO;
	private Integer reasonId;
	
	/*@JsonBackReference
	private BcunConsignmentReturnDO consignmentReturnDO;*/
	private Integer consignmentReturnId;
	
	/**
	 * @return the consignmentReturnReasonId
	 */
	public Integer getConsignmentReturnReasonId() {
		return consignmentReturnReasonId;
	}
	/**
	 * @param consignmentReturnReasonId the consignmentReturnReasonId to set
	 */
	public void setConsignmentReturnReasonId(Integer consignmentReturnReasonId) {
		this.consignmentReturnReasonId = consignmentReturnReasonId;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return the reasonId
	 */
	public Integer getReasonId() {
		return reasonId;
	}
	/**
	 * @param reasonId the reasonId to set
	 */
	public void setReasonId(Integer reasonId) {
		this.reasonId = reasonId;
	}
	/**
	 * @return the consignmentReturnId
	 */
	public Integer getConsignmentReturnId() {
		return consignmentReturnId;
	}
	/**
	 * @param consignmentReturnId the consignmentReturnId to set
	 */
	public void setConsignmentReturnId(Integer consignmentReturnId) {
		this.consignmentReturnId = consignmentReturnId;
	}
}
