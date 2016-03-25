package com.ff.domain.bcun.reconcillation;

import java.util.Calendar;
import java.util.Date;

import com.capgemini.lbs.framework.domain.CGBaseDO;

/**
 * @author bmodala
 *
 */
public class BcunReconcillationBlobDO extends CGBaseDO {	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -355931998318986L;

	private Integer transactionOfficeId;
	private Date transactionDate;
	private String processName;
	private String transactionNumber;
	private Integer noOfElements; 
	private String transactionStatus;
	
	public Integer getTransactionOfficeId() {
		return transactionOfficeId;
	}
	public void setTransactionOfficeId(Integer transactionOfficeId) {
		this.transactionOfficeId = transactionOfficeId;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		Calendar date=null;
		if(transactionDate!=null){
			date=Calendar.getInstance();
			date.setTime(transactionDate);
			this.transactionDate = date.getTime();
		}
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getTransactionNumber() {
		return transactionNumber;
	}
	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	public Integer getNoOfElements() {
		return noOfElements;
	}
	public void setNoOfElements(Integer noOfElements) {
		this.noOfElements = noOfElements;
	}
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((noOfElements == null) ? 0 : noOfElements.hashCode());
		result = prime * result
				+ ((processName == null) ? 0 : processName.hashCode());
		result = prime * result
				+ ((transactionDate == null) ? 0 : transactionDate.hashCode());
		result = prime
				* result
				+ ((transactionNumber == null) ? 0 : transactionNumber
						.hashCode());
		result = prime
				* result
				+ ((transactionOfficeId == null) ? 0 : transactionOfficeId
						.hashCode());
		result = prime
				* result
				+ ((transactionStatus == null) ? 0 : transactionStatus
						.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof BcunReconcillationBlobDO)) {
			return false;
		}
		BcunReconcillationBlobDO other = (BcunReconcillationBlobDO) obj;
		if (noOfElements == null) {
			if (other.noOfElements != null) {
				return false;
			}
		} else if (!noOfElements.equals(other.noOfElements)) {
			return false;
		}
		if (processName == null) {
			if (other.processName != null) {
				return false;
			}
		} else if (!processName.equals(other.processName)) {
			return false;
		}
		if (transactionDate == null) {
			if (other.transactionDate != null) {
				return false;
			}
		} else if (!transactionDate.equals(other.transactionDate)) {
			return false;
		}
		if (transactionNumber == null) {
			if (other.transactionNumber != null) {
				return false;
			}
		} else if (!transactionNumber.equals(other.transactionNumber)) {
			return false;
		}
		if (transactionOfficeId == null) {
			if (other.transactionOfficeId != null) {
				return false;
			}
		} else if (!transactionOfficeId.equals(other.transactionOfficeId)) {
			return false;
		}
		if (transactionStatus == null) {
			if (other.transactionStatus != null) {
				return false;
			}
		} else if (!transactionStatus.equals(other.transactionStatus)) {
			return false;
		}
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BcunReconcillationBlobDO [transactionOfficeId="
				+ transactionOfficeId + ", transactionDate=" + transactionDate
				+ ", processName=" + processName + ", transactionNumber="
				+ transactionNumber + ", noOfElements=" + noOfElements
				+ ", transactionStatus=" + transactionStatus + "]";
	}
	
}
