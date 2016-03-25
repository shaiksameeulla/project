package com.ff.manifest.rthrto;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.to.serviceofferings.ReasonTO;

/**
 * The Class RthRtoValidationDetailsTO.
 * 
 * @author narmdr
 */
public class RthRtoValidationDetailsTO extends CGBaseTO implements
		Comparable<RthRtoValidationDetailsTO> {

	private static final long serialVersionUID = -752944331049521559L;
	private Integer consignmentReturnReasonId;
	private String dateStr;
	private String time;
	private String remarks;
	private ReasonTO reasonTO;

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
	 * @return the reasonTO
	 */
	public ReasonTO getReasonTO() {
		return reasonTO;
	}
	/**
	 * @param reasonTO the reasonTO to set
	 */
	public void setReasonTO(ReasonTO reasonTO) {
		this.reasonTO = reasonTO;
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
	 * @return the dateStr
	 */
	public String getDateStr() {
		return dateStr;
	}
	/**
	 * @param dateStr the dateStr to set
	 */
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	
	@Override
	public int compareTo(RthRtoValidationDetailsTO detailsTO) {
		return this.getConsignmentReturnReasonId().compareTo(
				detailsTO.getConsignmentReturnReasonId());
	}
	
}
