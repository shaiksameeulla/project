/**
 * 
 */
package com.ff.domain.billing;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;

/**
 * @author abarudwa
 *
 */
public class InvoiceRunSheetHeaderDO extends CGFactDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer invoiceRunSheetId;
	private String invoiceRunSheetNumber;
	private Date startDate;
	private Date endDate;
	private EmployeeDO pickUpBoyEmployeeDO;//pickUpBoyId
	private String pickUpBoyName;
	private Set<InvoiceRunSheetDetailDO> invoiceRunSheetDetailDOs;
	private OfficeDO officeDO;//officeId
	private Integer createdBy;
	private Date createdDate;
	private Integer updatedBy;
	private Date updatedDate;
	
	
	/**
	 * @return the invoiceRunSheetId
	 */
	public Integer getInvoiceRunSheetId() {
		return invoiceRunSheetId;
	}
	/**
	 * @param invoiceRunSheetId the invoiceRunSheetId to set
	 */
	public void setInvoiceRunSheetId(Integer invoiceRunSheetId) {
		this.invoiceRunSheetId = invoiceRunSheetId;
	}
	/**
	 * @return the invoiceRunSheetNumber
	 */
	public String getInvoiceRunSheetNumber() {
		return invoiceRunSheetNumber;
	}
	/**
	 * @param invoiceRunSheetNumber the invoiceRunSheetNumber to set
	 */
	public void setInvoiceRunSheetNumber(String invoiceRunSheetNumber) {
		this.invoiceRunSheetNumber = invoiceRunSheetNumber;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
	/**
	 * @return the pickUpBoyEmployeeDO
	 */
	public EmployeeDO getPickUpBoyEmployeeDO() {
		return pickUpBoyEmployeeDO;
	}
	/**
	 * @param pickUpBoyEmployeeDO the pickUpBoyEmployeeDO to set
	 */
	public void setPickUpBoyEmployeeDO(EmployeeDO pickUpBoyEmployeeDO) {
		this.pickUpBoyEmployeeDO = pickUpBoyEmployeeDO;
	}
	/**
	 * @return the pickUpBoyName
	 */
	public String getPickUpBoyName() {
		return pickUpBoyName;
	}
	/**
	 * @param pickUpBoyName the pickUpBoyName to set
	 */
	public void setPickUpBoyName(String pickUpBoyName) {
		this.pickUpBoyName = pickUpBoyName;
	}
	/**
	 * @return the invoiceRunSheetDetailDOs
	 */
	public Set<InvoiceRunSheetDetailDO> getInvoiceRunSheetDetailDOs() {
		return invoiceRunSheetDetailDOs;
	}
	/**
	 * @param invoiceRunSheetDetailDOs the invoiceRunSheetDetailDOs to set
	 */
	public void setInvoiceRunSheetDetailDOs(
			Set<InvoiceRunSheetDetailDO> invoiceRunSheetDetailDOs) {
		this.invoiceRunSheetDetailDOs = invoiceRunSheetDetailDOs;
	}
	/**
	 * @return the officeDO
	 */
	public OfficeDO getOfficeDO() {
		return officeDO;
	}
	/**
	 * @param officeDO the officeDO to set
	 */
	public void setOfficeDO(OfficeDO officeDO) {
		this.officeDO = officeDO;
	}
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
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the updatedBy
	 */
	public Integer getUpdatedBy() {
		return updatedBy;
	}
	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}
	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}
	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	

}
