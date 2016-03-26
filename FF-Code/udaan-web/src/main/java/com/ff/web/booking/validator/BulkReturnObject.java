package com.ff.web.booking.validator;

import java.util.List;

import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ConsignmentDO;

public class BulkReturnObject {
	List<BookingDO> validBookings = null;
	List<ConsignmentDO> validConsignments = null;
	List<BookingDO> invalidBookings = null;
	List<ConsignmentDO> invalidConsignments = null;
	List<List> errList = null;

	// Code Added
	List<List> successList = null;
	// Code Added Ends

	private int failedBookingCount;
	private int successBookingCount;

	private String isAllInvalidBooking = "N";

	public String getIsAllInvalidBooking() {
		return isAllInvalidBooking;
	}

	public void setIsAllInvalidBooking(String isAllInvalidBooking) {
		this.isAllInvalidBooking = isAllInvalidBooking;
	}

	/**
	 * @return the errList
	 */
	public List<List> getErrList() {
		return errList;
	}

	/**
	 * @param errList
	 *            the errList to set
	 */
	public void setErrList(List<List> errList) {
		this.errList = errList;
	}

	// Code Added

	/**
	 * @return the successList
	 */
	public List<List> getSuccessList() {
		return successList;
	}

	/**
	 * @param successList
	 *            the successList to set
	 */
	public void setSuccessList(List<List> successList) {
		this.successList = successList;
	}

	// Code Added Ends

	/**
	 * @return the validBookings
	 */
	public List<BookingDO> getValidBookings() {
		return validBookings;
	}

	/**
	 * @param validBookings
	 *            the validBookings to set
	 */
	public void setValidBookings(List<BookingDO> validBookings) {
		this.validBookings = validBookings;
	}

	/**
	 * @return the validConsignments
	 */
	public List<ConsignmentDO> getValidConsignments() {
		return validConsignments;
	}

	/**
	 * @param validConsignments
	 *            the validConsignments to set
	 */
	public void setValidConsignments(List<ConsignmentDO> validConsignments) {
		this.validConsignments = validConsignments;
	}

	/**
	 * @return the invalidBookings
	 */
	public List<BookingDO> getInvalidBookings() {
		return invalidBookings;
	}

	/**
	 * @param invalidBookings
	 *            the invalidBookings to set
	 */
	public void setInvalidBookings(List<BookingDO> invalidBookings) {
		this.invalidBookings = invalidBookings;
	}

	/**
	 * @return the invalidConsignments
	 */
	public List<ConsignmentDO> getInvalidConsignments() {
		return invalidConsignments;
	}

	/**
	 * @param invalidConsignments
	 *            the invalidConsignments to set
	 */
	public void setInvalidConsignments(List<ConsignmentDO> invalidConsignments) {
		this.invalidConsignments = invalidConsignments;
	}

	public int getFailedBookingCount() {
		return failedBookingCount;
	}

	public void setFailedBookingCount(int failedBookingCount) {
		this.failedBookingCount = failedBookingCount;
	}

	public int getSuccessBookingCount() {
		return successBookingCount;
	}

	public void setSuccessBookingCount(int successBookingCount) {
		this.successBookingCount = successBookingCount;
	}

}
