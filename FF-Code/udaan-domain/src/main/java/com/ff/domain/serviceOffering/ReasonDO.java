package com.ff.domain.serviceOffering;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.tracking.ProcessDO;

/**
 * The Class ReasonDO.
 * @author mohammes
 */
public class ReasonDO extends CGMasterDO  {
	
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
	private ProcessDO processDO;

	/** The is Party Letter. */
	private String isPartyLetter = "N";
	
		
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
	 * Gets the process do.
	 *
	 * @return the processDO
	 */
	public ProcessDO getProcessDO() {
		return processDO;
	}

	/**
	 * Sets the reason id.
	 *
	 * @param reasonId the reasonId to set
	 */
	public void setReasonId(Integer reasonId) {
		this.reasonId = reasonId;
	}

	/**
	 * Sets the reason code.
	 *
	 * @param reasonCode the reasonCode to set
	 */
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	/**
	 * Sets the reason name.
	 *
	 * @param reasonName the reasonName to set
	 */
	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}

	/**
	 * Sets the reason type.
	 *
	 * @param reasonType the reasonType to set
	 */
	public void setReasonType(String reasonType) {
		this.reasonType = reasonType;
	}

	/**
	 * Sets the reason type desc.
	 *
	 * @param reasonTypeDesc the reasonTypeDesc to set
	 */
	public void setReasonTypeDesc(String reasonTypeDesc) {
		this.reasonTypeDesc = reasonTypeDesc;
	}

	/**
	 * Sets the process do.
	 *
	 * @param processDO the processDO to set
	 */
	public void setProcessDO(ProcessDO processDO) {
		this.processDO = processDO;
	}
	
	
}
