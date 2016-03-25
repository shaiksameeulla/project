/**
 * 
 */
package com.ff.to.ratemanagement.operations.ratecontract;

import java.util.Date;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.capgemini.lbs.framework.utils.DateUtil;

/**
 * @author uchauhan
 *
 */
public class RateCustomerSearchTO extends CGBaseTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7789481649377657054L;
	
	private Date createdDate;
	private String custCreatedDate;
	private String contractNo;
	private String regionName;
	private String station;
	private String customerName;
	private String salesOfficeName;
	private String salesPerson;
	private String groupKey;
	private String status;
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
		this.custCreatedDate = DateUtil.getDateInDDMMYYYYHHMMSlashFormat(createdDate);
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getSalesOfficeName() {
		return salesOfficeName;
	}
	public void setSalesOfficeName(String salesOfficeName) {
		this.salesOfficeName = salesOfficeName;
	}
	public String getSalesPerson() {
		return salesPerson;
	}
	public void setSalesPerson(String salesPerson) {
		this.salesPerson = salesPerson;
	}
	public String getGroupKey() {
		return groupKey;
	}
	public void setGroupKey(String groupKey) {
		this.groupKey = groupKey;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the custCreatedDate
	 */
	public String getCustCreatedDate() {
		return custCreatedDate;
	}
	/**
	 * @param custCreatedDate the custCreatedDate to set
	 */
	public void setCustCreatedDate(String custCreatedDate) {
		this.custCreatedDate = custCreatedDate;
	}
}
