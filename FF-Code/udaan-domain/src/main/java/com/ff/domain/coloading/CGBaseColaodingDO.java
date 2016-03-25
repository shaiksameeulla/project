
package com.ff.domain.coloading;

import java.io.Serializable;
import java.util.Date;

public abstract class CGBaseColaodingDO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer createdBy ;
	private Integer updatedBy;
	private Date createdDate;
	private Date updatedDate;
	private char storeStatus;

	
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Integer getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public char getStoreStatus() {
		return this.storeStatus;
	}

	public void setStoreStatus(char storeStatus) {
		this.storeStatus = storeStatus;
	}
}
