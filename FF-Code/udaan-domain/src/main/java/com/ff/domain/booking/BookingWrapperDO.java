package com.ff.domain.booking;

import java.util.List;
import java.util.Set;

import com.ff.domain.consignment.ConsignmentDO;

public class BookingWrapperDO {

	private List<BookingDO> sucessConsignments;
	private List<ConsignmentDO> consingments;
	private Set<String> failureConsignments;
	private List<Integer> successBookingsIds;
	private List<Integer> successCNsIds;

	private boolean isBulkSave = true;
	

	/**
	 * @return the sucessConsignments
	 */
	public List<BookingDO> getSucessConsignments() {
		return sucessConsignments;
	}

	/**
	 * @param sucessConsignments
	 *            the sucessConsignments to set
	 */
	public void setSucessConsignments(List<BookingDO> sucessConsignments) {
		this.sucessConsignments = sucessConsignments;
	}

	/**
	 * @return the failureConsignments
	 */
	public Set<String> getFailureConsignments() {
		return failureConsignments;
	}

	/**
	 * @param failureConsignments
	 *            the failureConsignments to set
	 */
	public void setFailureConsignments(Set<String> failureConsignments) {
		this.failureConsignments = failureConsignments;
	}

	/**
	 * @return the isBulkSave
	 */
	public boolean isBulkSave() {
		return isBulkSave;
	}

	/**
	 * @param isBulkSave
	 *            the isBulkSave to set
	 */
	public void setBulkSave(boolean isBulkSave) {
		this.isBulkSave = isBulkSave;
	}

	/**
	 * @return the consingments
	 */
	public List<ConsignmentDO> getConsingments() {
		return consingments;
	}

	/**
	 * @param consingments
	 *            the consingments to set
	 */
	public void setConsingments(List<ConsignmentDO> consingments) {
		this.consingments = consingments;
	}

	public List<Integer> getSuccessBookingsIds() {
		return successBookingsIds;
	}

	public void setSuccessBookingsIds(List<Integer> successBookingsIds) {
		this.successBookingsIds = successBookingsIds;
	}

	public List<Integer> getSuccessCNsIds() {
		return successCNsIds;
	}

	public void setSuccessCNsIds(List<Integer> successCNsIds) {
		this.successCNsIds = successCNsIds;
	}
}
