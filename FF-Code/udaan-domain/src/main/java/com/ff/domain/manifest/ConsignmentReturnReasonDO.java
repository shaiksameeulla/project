package com.ff.domain.manifest;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ff.domain.serviceOffering.ReasonDO;


/**
 * The Class ConsignmentReturnReasonDO.
 *
 * @author narmdr
 */
public class ConsignmentReturnReasonDO extends CGFactDO {

	private static final long serialVersionUID = -3834968465184751050L;
	private Integer consignmentReturnReasonId;
	private Date date;
	private String time;
	private String remarks;
	private ReasonDO reasonDO;
	
	@JsonBackReference
	private ConsignmentReturnDO consignmentReturnDO;
	
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
	 * @return the reasonDO
	 */
	public ReasonDO getReasonDO() {
		return reasonDO;
	}
	/**
	 * @param reasonDO the reasonDO to set
	 */
	public void setReasonDO(ReasonDO reasonDO) {
		this.reasonDO = reasonDO;
	}
	/**
	 * @return the consignmentReturnDO
	 */
	public ConsignmentReturnDO getConsignmentReturnDO() {
		return consignmentReturnDO;
	}
	/**
	 * @param consignmentReturnDO the consignmentReturnDO to set
	 */
	public void setConsignmentReturnDO(ConsignmentReturnDO consignmentReturnDO) {
		this.consignmentReturnDO = consignmentReturnDO;
	}
}
