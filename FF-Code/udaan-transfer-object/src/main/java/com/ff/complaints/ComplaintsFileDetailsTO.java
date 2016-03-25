package com.ff.complaints;

/**
 * @author hkansagr
 */
public class ComplaintsFileDetailsTO {

	/** The paper work id. */
	private Integer paperWorkId;

	/** The file name. */
	private String fileName;

	/** The file description. */
	private String fileDescrition;

	/** The file uploaded by user name. */
	private String uploadedBy;

	/** The file uploaded date - DD/MM/YYYY format */
	private String uploadedDate;

	/** The file date - actual file content. */
	private byte[] fileData;

	
	/**
	 * @return the fileData
	 */
	public byte[] getFileData() {
		return fileData;
	}

	/**
	 * @param fileData
	 *            the fileData to set
	 */
	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

	/**
	 * @return the paperWorkId
	 */
	public Integer getPaperWorkId() {
		return paperWorkId;
	}

	/**
	 * @param paperWorkId
	 *            the paperWorkId to set
	 */
	public void setPaperWorkId(Integer paperWorkId) {
		this.paperWorkId = paperWorkId;
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
	 * @return the fileDescrition
	 */
	public String getFileDescrition() {
		return fileDescrition;
	}

	/**
	 * @param fileDescrition
	 *            the fileDescrition to set
	 */
	public void setFileDescrition(String fileDescrition) {
		this.fileDescrition = fileDescrition;
	}

	/**
	 * @return the uploadedBy
	 */
	public String getUploadedBy() {
		return uploadedBy;
	}

	/**
	 * @param uploadedBy
	 *            the uploadedBy to set
	 */
	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	/**
	 * @return the uploadedDate
	 */
	public String getUploadedDate() {
		return uploadedDate;
	}

	/**
	 * @param uploadedDate
	 *            the uploadedDate to set
	 */
	public void setUploadedDate(String uploadedDate) {
		this.uploadedDate = uploadedDate;
	}

}
