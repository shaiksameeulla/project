/**
 * 
 */
package com.capgemini.lbs.framework.to;

import java.util.Date;
import java.util.List;

/**
 * @author mohammes
 *
 */
public class SequenceGeneratorConfigTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1234233324523L;
	// input  processRequesting (mandatory)
	private String processRequesting;
	
	// input  requestDate (mandatory)
	private Date requestDate;
	
	// input  noOfSequencesToBegenerated (mandatory)
	private Integer noOfSequencesToBegenerated=1;
	
	// Output generatedSequences
	private List <String> generatedSequences;
	
	//prefix of  the complete number ex : prefixCode+running number
	private String prefixCode;
	
	
	
	
	//For Stock Module ************Start*************
	// input  requestingBranch (mandatory)--for Stock-mgmnt only
	private String requestingBranchCode;
	// input  requestingBranchId (mandatory)--for Stock-mgmnt only
	private Integer requestingBranchId;
	
	// input  sequenceRunningLength(length of running number) (mandatory)--for Stock-mgmnt only
	private Integer sequenceRunningLength;
	// input  sequenceRunningLength(length of running number) (mandatory)--for Stock-mgmnt only
	private Integer lengthOfNumber;
	//For Stock Module ************END*************
	
	/**
	 * @return
	 */
	public List<String> getGeneratedSequences() {
		return generatedSequences;
	}
	/**
	 * @param generatedSequences
	 */
	public void setGeneratedSequences(List<String> generatedSequences) {
		this.generatedSequences = generatedSequences;
	}
	/**
	 * @return
	 */
	public String getProcessRequesting() {
		return processRequesting;
	}
	/**
	 * @param processRequesting
	 */
	public void setProcessRequesting(String processRequesting) {
		this.processRequesting = processRequesting;
	}
	/**
	 * @return
	 */
	public Date getRequestDate() {
		return requestDate;
	}
	/**
	 * @param requestDate
	 */
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	/**
	 * @return
	 */
	public Integer getNoOfSequencesToBegenerated() {
		return noOfSequencesToBegenerated;
	}
	/**
	 * @param noOfSequencesToBegenerated
	 */
	public void setNoOfSequencesToBegenerated(Integer noOfSequencesToBegenerated) {
		this.noOfSequencesToBegenerated = noOfSequencesToBegenerated;
	}
	
	/**
	 * @return the requestingBranchId
	 */
	public Integer getRequestingBranchId() {
		return requestingBranchId;
	}
	/**
	 * @param requestingBranchId the requestingBranchId to set
	 */
	public void setRequestingBranchId(Integer requestingBranchId) {
		this.requestingBranchId = requestingBranchId;
	}
	/**
	 * @return the requestingBranchCode
	 */
	public String getRequestingBranchCode() {
		return requestingBranchCode;
	}
	/**
	 * @param requestingBranchCode the requestingBranchCode to set
	 */
	public void setRequestingBranchCode(String requestingBranchCode) {
		this.requestingBranchCode = requestingBranchCode;
	}
	/**
	 * @return the sequenceRunningLength
	 */
	public Integer getSequenceRunningLength() {
		return sequenceRunningLength;
	}
	/**
	 * @param sequenceRunningLength the sequenceRunningLength to set
	 */
	public void setSequenceRunningLength(Integer sequenceRunningLength) {
		this.sequenceRunningLength = sequenceRunningLength;
	}
	/**
	 * @return
	 */
	public Integer getLengthOfNumber() {
		return lengthOfNumber;
	}
	/**
	 * @param lengthOfNumber
	 */
	public void setLengthOfNumber(Integer lengthOfNumber) {
		this.lengthOfNumber = lengthOfNumber;
	}
	/**
	 * @return the prefixCode
	 */
	public String getPrefixCode() {
		return prefixCode;
	}
	/**
	 * @param prefixCode the prefixCode to set
	 */
	public void setPrefixCode(String prefixCode) {
		this.prefixCode = prefixCode;
	}
}
