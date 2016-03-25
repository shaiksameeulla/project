package com.ff.booking;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;

/**
 * The Class BackdatedBookingTO.
 */
public class BackdatedBookingTO extends BookingParcelTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8462950659581787956L;

	/** The delv date time. */

	private String delvDateTime;

	/** The region id. */
	private Integer regionId;

	/** The branch id. */
	private Integer branchId;

	/** The destination region list. */
	private String destinationRegionList;

	/** The file upload. */
	private FormFile fileUpload;

	/** The file type. */
	private String fileType;

	/** The cust code. */
	private String custCode;

	/** The biz associate id. */
	private Integer bizAssociateId;

	/** The custome id. */
	private Integer customeId;

	/** The error. */
	private String error;
	
	private String backDate;
	private String backTime;
	private Integer createdBy;

	/** The valid bookings. */
	List<BackdatedBookingTO> validBookings = new ArrayList<BackdatedBookingTO>();

	/** The invalid bookings. */
	List<BackdatedBookingTO> invalidBookings = new ArrayList<BackdatedBookingTO>();

	/** The error codes. */
	private List<String> errorCodes = new ArrayList<String>();

	/** The error list. */
	private List<List> errorList;
	
	private Integer origniCityId;
	
	
	/**
	 * @return the createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the origniCityId
	 */
	public Integer getOrigniCityId() {
		return origniCityId;
	}

	/**
	 * @param origniCityId the origniCityId to set
	 */
	public void setOrigniCityId(Integer origniCityId) {
		this.origniCityId = origniCityId;
	}

	/**
	 * @return the backDate
	 */
	public String getBackDate() {
		return backDate;
	}

	/**
	 * @param backDate the backDate to set
	 */
	public void setBackDate(String backDate) {
		this.backDate = backDate;
	}

	/**
	 * @return the backTime
	 */
	public String getBackTime() {
		return backTime;
	}

	/**
	 * @param backTime the backTime to set
	 */
	public void setBackTime(String backTime) {
		this.backTime = backTime;
	}

	/**
	 * Gets the delv date time.
	 * 
	 * @return the delv date time
	 */
	public String getDelvDateTime() {
		return delvDateTime;
	}

	/**
	 * Sets the delv date time.
	 * 
	 * @param delvDateTime
	 *            the new delv date time
	 */
	public void setDelvDateTime(String delvDateTime) {
		this.delvDateTime = delvDateTime;
	}

	/**
	 * Gets the region id.
	 * 
	 * @return the region id
	 */
	public Integer getRegionId() {
		return regionId;
	}

	/**
	 * Sets the region id.
	 * 
	 * @param regionId
	 *            the new region id
	 */
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	/**
	 * Gets the branch id.
	 * 
	 * @return the branch id
	 */
	public Integer getBranchId() {
		return branchId;
	}

	/**
	 * Sets the branch id.
	 * 
	 * @param branchId
	 *            the new branch id
	 */
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	/**
	 * Gets the destination region list.
	 * 
	 * @return the destination region list
	 */
	public String getDestinationRegionList() {
		return destinationRegionList;
	}

	/**
	 * Sets the destination region list.
	 * 
	 * @param destinationRegionList
	 *            the new destination region list
	 */
	public void setDestinationRegionList(String destinationRegionList) {
		this.destinationRegionList = destinationRegionList;
	}

	/**
	 * Gets the file upload.
	 * 
	 * @return the fileUpload
	 */
	public FormFile getFileUpload() {
		return fileUpload;
	}

	/**
	 * Sets the file upload.
	 * 
	 * @param fileUpload
	 *            the fileUpload to set
	 */
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}

	/**
	 * Gets the file type.
	 * 
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * Sets the file type.
	 * 
	 * @param fileType
	 *            the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * Gets the valid bookings.
	 * 
	 * @return the valid bookings
	 */
	public List<BackdatedBookingTO> getValidBookings() {
		return validBookings;
	}

	/**
	 * Sets the valid bookings.
	 * 
	 * @param validBookings
	 *            the new valid bookings
	 */
	public void setValidBookings(List<BackdatedBookingTO> validBookings) {
		this.validBookings = validBookings;
	}

	/**
	 * Gets the invalid bookings.
	 * 
	 * @return the invalid bookings
	 */
	public List<BackdatedBookingTO> getInvalidBookings() {
		return invalidBookings;
	}

	/**
	 * Sets the invalid bookings.
	 * 
	 * @param invalidBookings
	 *            the new invalid bookings
	 */
	public void setInvalidBookings(List<BackdatedBookingTO> invalidBookings) {
		this.invalidBookings = invalidBookings;
	}

	/**
	 * Gets the error list.
	 * 
	 * @return the errorList
	 */
	public List<List> getErrorList() {
		return errorList;
	}

	/**
	 * Sets the error list.
	 * 
	 * @param errorList
	 *            the errorList to set
	 */
	public void setErrorList(List<List> errorList) {
		this.errorList = errorList;
	}

	/**
	 * Gets the error codes.
	 * 
	 * @return the error codes
	 */
	public List<String> getErrorCodes() {
		return errorCodes;
	}

	/**
	 * Sets the error codes.
	 * 
	 * @param errorCodes
	 *            the new error codes
	 */
	public void setErrorCodes(List<String> errorCodes) {
		this.errorCodes = errorCodes;
	}

	/**
	 * Gets the cust code.
	 * 
	 * @return the custCode
	 */
	public String getCustCode() {
		return custCode;
	}

	/**
	 * Sets the cust code.
	 * 
	 * @param custCode
	 *            the custCode to set
	 */
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	/**
	 * Gets the biz associate id.
	 * 
	 * @return the biz associate id
	 */
	public Integer getBizAssociateId() {
		return bizAssociateId;
	}

	/**
	 * Sets the biz associate id.
	 * 
	 * @param bizAssociateId
	 *            the new biz associate id
	 */
	public void setBizAssociateId(Integer bizAssociateId) {
		this.bizAssociateId = bizAssociateId;
	}

	/**
	 * Gets the custome id.
	 * 
	 * @return the custome id
	 */
	public Integer getCustomeId() {
		return customeId;
	}

	/**
	 * Sets the custome id.
	 * 
	 * @param customeId
	 *            the new custome id
	 */
	public void setCustomeId(Integer customeId) {
		this.customeId = customeId;
	}

	/**
	 * Gets the error.
	 * 
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * Sets the error.
	 * 
	 * @param error
	 *            the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

}
