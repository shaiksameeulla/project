package com.cg.lbs.bcun.to;



import java.util.List;


/**
 * @author mohammal
 * Feb 14, 2013
 * 
 */
public class OutboundBranchDataTO {

	private static final long serialVersionUID = 1L;

	private String branchCode;
	private Integer maxRecords;
	private byte[] fileData;
	private Boolean status;
	private int httpStatusCode;
	private Boolean isAborted;
	private String dataExtctrIdStr;//This holds list of primary keys of DataExtractorDoId's [ ie Format :1,2,3  ] which need to be update the record status to R
	private String randomNumber;
	private String processName;
	private String processFileName;
	private List<byte[]> zipFIles;
	private List<Long> packetIds;//This holds list of primary keys of DataExtractorDoId's[ie Format :[1,2,3]] which need to be update the record status to R
	private long branchId;
	private String updateFlagStatus;
	private String uniqueRequestId;
	
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	/**
	 * @return the branchCode
	 */
	public String getBranchCode() {
		return branchCode;
	}
	/**
	 * @param branchCode the branchCode to set
	 */
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	/**
	 * @return the fileData
	 */
	public byte[] getFileData() {
		return fileData;
	}
	public Integer getMaxRecords() {
		return maxRecords;
	}
	public void setMaxRecords(Integer maxRecords) {
		this.maxRecords = maxRecords;
	}
	public int getHttpStatusCode() {
		return httpStatusCode;
	}
	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}
	public Boolean getIsAborted() {
		return isAborted;
	}
	public void setIsAborted(Boolean isAborted) {
		this.isAborted = isAborted;
	}
	/**
	 * @param fileData the fileData to set
	 */
	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}
	
	public String getDataExtctrIdStr() {
		return dataExtctrIdStr;
	}
	
	public String getRandomNumber() {
		return randomNumber;
	}
	public void setRandomNumber(String randomNumber) {
		this.randomNumber = randomNumber;
	}
	public void setDataExtctrIdStr(String dataExtctrIdStr) {
		this.dataExtctrIdStr = dataExtctrIdStr;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getProcessFileName() {
		return processFileName;
	}
	public void setProcessFileName(String processFileName) {
		this.processFileName = processFileName;
	}
	public List<byte[]> getZipFIles() {
		return zipFIles;
	}
	public void setZipFIles(List<byte[]> zipFIles) {
		this.zipFIles = zipFIles;
	}
	public List<Long> getPacketIds() {
		return packetIds;
	}
	public void setPacketIds(List<Long> packetIds) {
		this.packetIds = packetIds;
	}
	public long getBranchId() {
		return branchId;
	}
	public void setBranchId(long branchId) {
		this.branchId = branchId;
	}
	/**
	 * @return the updateFlagStatus
	 */
	public String getUpdateFlagStatus() {
		return updateFlagStatus;
	}
	/**
	 * @param updateFlagStatus the updateFlagStatus to set
	 */
	public void setUpdateFlagStatus(String updateFlagStatus) {
		this.updateFlagStatus = updateFlagStatus;
	}
	/**
	 * @return the uniqueRequestId
	 */
	public String getUniqueRequestId() {
		return uniqueRequestId;
	}
	/**
	 * @param uniqueRequestId the uniqueRequestId to set
	 */
	public void setUniqueRequestId(String uniqueRequestId) {
		this.uniqueRequestId = uniqueRequestId;
	}
	
}
