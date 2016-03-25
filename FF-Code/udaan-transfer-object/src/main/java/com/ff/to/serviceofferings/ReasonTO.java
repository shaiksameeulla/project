package com.ff.to.serviceofferings;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.tracking.ProcessTO;

/**
 * The Class ReasonTO.
 * @author mohammes
 */
public class ReasonTO extends CGBaseTO  {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2811000130323712809L;

	/** The reason id. */
	private Integer reasonId;
	
	/** The reason code. */
	private String reasonCode;
	
	/** The reason name. */
	private String reasonName;
	
	/** The reason type. */
	private String reasonType;
	
	/** The reason type desc. */
	private String reasonTypeDesc;
	
	/** The process do. */
	private ProcessTO processTO;

	/** The is Party Letter. */
	private String isPartyLetter;
		
	/**
	 * @return the isPartyLetter
	 */
	public String getIsPartyLetter() {
		return isPartyLetter;
	}

	/**
	 * @param isPartyLetter the isPartyLetter to set
	 */
	public void setIsPartyLetter(String isPartyLetter) {
		this.isPartyLetter = isPartyLetter;
	}

	/**
	 * Gets the reason id.
	 *
	 * @return the reasonId
	 */
	public Integer getReasonId() {
		return reasonId;
	}

	/**
	 * Gets the reason code.
	 *
	 * @return the reasonCode
	 */
	public String getReasonCode() {
		return reasonCode;
	}

	/**
	 * Gets the reason name.
	 *
	 * @return the reasonName
	 */
	public String getReasonName() {
		return reasonName;
	}

	/**
	 * Gets the reason type.
	 *
	 * @return the reasonType
	 */
	public String getReasonType() {
		return reasonType;
	}

	/**
	 * Gets the reason type desc.
	 *
	 * @return the reasonTypeDesc
	 */
	public String getReasonTypeDesc() {
		return reasonTypeDesc;
	}

	/**
	 * @return the processTO
	 */
	public ProcessTO getProcessTO() {
		return processTO;
	}

	/**
	 * @param reasonId the reasonId to set
	 */
	public void setReasonId(Integer reasonId) {
		this.reasonId = reasonId;
	}

	/**
	 * @param reasonCode the reasonCode to set
	 */
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	/**
	 * @param reasonName the reasonName to set
	 */
	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}

	/**
	 * @param reasonType the reasonType to set
	 */
	public void setReasonType(String reasonType) {
		this.reasonType = reasonType;
	}

	/**
	 * @param reasonTypeDesc the reasonTypeDesc to set
	 */
	public void setReasonTypeDesc(String reasonTypeDesc) {
		this.reasonTypeDesc = reasonTypeDesc;
	}

	/**
	 * @param processTO the processTO to set
	 */
	public void setProcessTO(ProcessTO processTO) {
		this.processTO = processTO;
	}

	
	
}
