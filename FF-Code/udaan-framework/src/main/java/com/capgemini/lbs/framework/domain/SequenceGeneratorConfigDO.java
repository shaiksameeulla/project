package com.capgemini.lbs.framework.domain;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGBaseDO;
/**
 * @author anwar
 * 
 */
public class SequenceGeneratorConfigDO extends CGBaseDO{
	
	private static final long serialVersionUID = -29994752696149987L;
	/** primary key of the table which does autoincrement */
	private Integer sequenceGeneratorId;//SEQUENCE_GEN_ID
	
	/** Requesting processs Name for which sequence required */
	private String processType;//PROCESS_REQUESTING
	
	/** initial value of the sequence number of the process */
	private Integer initialValue; //INITIAL_VALUE
	
	/** last generated sequence number for the process ,it keeps on update after generating sequence number */
	private Long lastGeneratedNumber;//LAST_GENERATED_SEQUENCE
	
	/** increment value for each sequence number */
	private Integer incrementValue;//INCREMENT_VALUE
	
	/** Date-Time of the last generated number */
	private Date lastGeneratedDate;//DATE_TIME_LAST_GENERATED
	
	/** whether Re-initialization is required based on value in reinitInterval  and it holds Y or N*/
	private String reInitializeRequired;//REINITIALIZE_REQUIRED
	
	/** length of the  sequnce number, EX : if 4 then 0001*/
	private Integer sequenceLength;//LENGTH_OF_GENERATED_SEQUENCE
	
	/** re-initialization interval based on D-day,M-month,Y-Year*/
	private String reInitInterval;//REINIT_INTERVAL

	/**
	 * @return
	 */
	public Integer getSequenceGeneratorId() {
		return sequenceGeneratorId;
	}

	/**
	 * @param sequenceGeneratorId
	 */
	public void setSequenceGeneratorId(Integer sequenceGeneratorId) {
		this.sequenceGeneratorId = sequenceGeneratorId;
	}

	/**
	 * @return
	 */
	public String getProcessType() {
		return processType;
	}

	/**
	 * @param processType
	 */
	public void setProcessType(String processType) {
		this.processType = processType;
	}

	/**
	 * @return
	 */
	public Integer getInitialValue() {
		return initialValue;
	}

	/**
	 * @param initialValue
	 */
	public void setInitialValue(Integer initialValue) {
		this.initialValue = initialValue;
	}

	/**
	 * @return
	 */
	public Long getLastGeneratedNumber() {
		return lastGeneratedNumber;
	}

	/**
	 * @param lastGeneratedNumber
	 */
	public void setLastGeneratedNumber(Long lastGeneratedNumber) {
		this.lastGeneratedNumber = lastGeneratedNumber;
	}

	

	/**
	 * @return
	 */
	public Date getLastGeneratedDate() {
		return lastGeneratedDate;
	}

	/**
	 * @param lastGeneratedDate
	 */
	public void setLastGeneratedDate(Date lastGeneratedDate) {
		this.lastGeneratedDate = lastGeneratedDate;
	}

	/**
	 * @return
	 */
	public String getReInitializeRequired() {
		return reInitializeRequired;
	}

	/**
	 * @param reInitializeRequired
	 */
	public void setReInitializeRequired(String reInitializeRequired) {
		this.reInitializeRequired = reInitializeRequired;
	}

	/**
	 * @return
	 */
	public Integer getSequenceLength() {
		return sequenceLength;
	}

	/**
	 * @param sequenceLength
	 */
	public void setSequenceLength(Integer sequenceLength) {
		this.sequenceLength = sequenceLength;
	}

	/**
	 * @return
	 */
	public String getReInitInterval() {
		return reInitInterval;
	}

	/**
	 * @param reInitInterval
	 */
	public void setReInitInterval(String reInitInterval) {
		this.reInitInterval = reInitInterval;
	}

	/**
	 * @return
	 */
	public Integer getIncrementValue() {
		return incrementValue;
	}

	/**
	 * @param incrementValue
	 */
	public void setIncrementValue(Integer incrementValue) {
		this.incrementValue = incrementValue;
	}

	
	
}
