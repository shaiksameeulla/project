package com.ff.domain.complaints;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class ServiceRequestPapersDO extends CGFactDO {

	private static final long serialVersionUID = 5506056459545040894L;

	/** The service request paper id - primary key. */
	private Integer serviceRequestPaperId;

	/** The service request id. */
	private Integer serviceRequestId;

	/** The upload file name. */
	private String fileName;

	/** The feedback. */
	private String feedback;

	/** The client meet. */
	private String clientMeet;

	/** The transfer ICC. */
	private Integer transferIcc;

	/** The file - in BLOB format. */
	private byte[] file;

	/** The file description. */
	private String fileDescription;

	/**
	 * @return the fileDescription
	 */
	public String getFileDescription() {
		return fileDescription;
	}

	/**
	 * @param fileDescription
	 *            the fileDescription to set
	 */
	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}

	/**
	 * @return the serviceRequestPaperId
	 */
	public Integer getServiceRequestPaperId() {
		return serviceRequestPaperId;
	}

	/**
	 * @param serviceRequestPaperId
	 *            the serviceRequestPaperId to set
	 */
	public void setServiceRequestPaperId(Integer serviceRequestPaperId) {
		this.serviceRequestPaperId = serviceRequestPaperId;
	}

	/**
	 * @return the serviceRequestId
	 */
	public Integer getServiceRequestId() {
		return serviceRequestId;
	}

	/**
	 * @param serviceRequestId
	 *            the serviceRequestId to set
	 */
	public void setServiceRequestId(Integer serviceRequestId) {
		this.serviceRequestId = serviceRequestId;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the feedback
	 */
	public String getFeedback() {
		return feedback;
	}

	/**
	 * @param feedback
	 *            the feedback to set
	 */
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	/**
	 * @return the clientMeet
	 */
	public String getClientMeet() {
		return clientMeet;
	}

	/**
	 * @param clientMeet
	 *            the clientMeet to set
	 */
	public void setClientMeet(String clientMeet) {
		this.clientMeet = clientMeet;
	}

	/**
	 * @return the transferIcc
	 */
	public Integer getTransferIcc() {
		return transferIcc;
	}

	/**
	 * @param transferIcc
	 *            the transferIcc to set
	 */
	public void setTransferIcc(Integer transferIcc) {
		this.transferIcc = transferIcc;
	}

	/**
	 * @return the file
	 */
	public byte[] getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(byte[] file) {
		this.file = file;
	}

}
