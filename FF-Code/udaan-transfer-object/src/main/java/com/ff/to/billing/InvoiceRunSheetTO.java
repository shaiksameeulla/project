/**
 * 
 */
package com.ff.to.billing;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.business.CustomerTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;

/**
 * @author abarudwa
 *
 */
public class InvoiceRunSheetTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String invoiceRunSheetNumber;
	private Integer invoiceRunSheetId;
	private String startDateStr;
	private String endDateStr;
	private EmployeeTO pickUpBoy;
	private ArrayList<InvoiceRunSheetDetailsTO> invoiceRunSheetDetailsList;
	private String ifOthers;
	private Integer loginOfficeId;//hidden
	private String loginOfficeCode;//hidden
	private OfficeTO officeTO;//officeId
	private Integer createdBy;
	private String createdDateStr;
	private Integer updatedBy;
	private String updatedDateStr;
	private String saveOrUpdate;
	
	
	int rowCount;
	private Integer[] customerIds = new Integer[rowCount];
	private Integer[] invoiceIds = new Integer[rowCount];
	private String[] shipToCode = new String[rowCount];
	private String[] status = new String[rowCount];
	private String[] receiverName = new String[rowCount];
	private Integer[] invoiceRunSheetDetailId = new Integer[rowCount];
	
	private List<BillTO> billTOs;
	private List<CustomerTO> customerTOs;
	private List<String> shipToCodeList;
	private CustomerTO customerTO;
	private String transMag;
	
	
	
	/**
	 * @return the customerTO
	 */
	public CustomerTO getCustomerTO() {
		return customerTO;
	}
	/**
	 * @param customerTO the customerTO to set
	 */
	public void setCustomerTO(CustomerTO customerTO) {
		this.customerTO = customerTO;
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
	 * @return the startDateStr
	 */
	public String getStartDateStr() {
		return startDateStr;
	}
	/**
	 * @param startDateStr the startDateStr to set
	 */
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}
	/**
	 * @return the endDateStr
	 */
	public String getEndDateStr() {
		return endDateStr;
	}
	/**
	 * @param endDateStr the endDateStr to set
	 */
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
	/**
	 * @return the pickUpBoy
	 */
	public EmployeeTO getPickUpBoy() {
		if(pickUpBoy == null)
			pickUpBoy = new EmployeeTO();
		return pickUpBoy;
	}
	/**
	 * @param pickUpBoy the pickUpBoy to set
	 */
	public void setPickUpBoy(EmployeeTO pickUpBoy) {
		this.pickUpBoy = pickUpBoy;
	}
	
	/**
	 * @return the invoiceRunSheetDetailsList
	 */
	public ArrayList<InvoiceRunSheetDetailsTO> getInvoiceRunSheetDetailsList() {
		return invoiceRunSheetDetailsList;
	}
	/**
	 * @param invoiceRunSheetDetailsList the invoiceRunSheetDetailsList to set
	 */
	public void setInvoiceRunSheetDetailsList(
			ArrayList<InvoiceRunSheetDetailsTO> invoiceRunSheetDetailsList) {
		this.invoiceRunSheetDetailsList = invoiceRunSheetDetailsList;
	}
	/**
	 * @return the ifOthers
	 */
	public String getIfOthers() {
		return ifOthers;
	}
	/**
	 * @param ifOthers the ifOthers to set
	 */
	public void setIfOthers(String ifOthers) {
		this.ifOthers = ifOthers;
	}
	/**
	 * @return the loginOfficeId
	 */
	public Integer getLoginOfficeId() {
		return loginOfficeId;
	}
	/**
	 * @param loginOfficeId the loginOfficeId to set
	 */
	public void setLoginOfficeId(Integer loginOfficeId) {
		this.loginOfficeId = loginOfficeId;
	}
	/**
	 * @return the loginOfficeCode
	 */
	public String getLoginOfficeCode() {
		return loginOfficeCode;
	}
	/**
	 * @param loginOfficeCode the loginOfficeCode to set
	 */
	public void setLoginOfficeCode(String loginOfficeCode) {
		this.loginOfficeCode = loginOfficeCode;
	}
	/**
	 * @return the customerIds
	 */
	public Integer[] getCustomerIds() {
		return customerIds;
	}
	/**
	 * @param customerIds the customerIds to set
	 */
	public void setCustomerIds(Integer[] customerIds) {
		this.customerIds = customerIds;
	}
	/**
	 * @return the invoiceIds
	 */
	public Integer[] getInvoiceIds() {
		return invoiceIds;
	}
	/**
	 * @param invoiceIds the invoiceIds to set
	 */
	public void setInvoiceIds(Integer[] invoiceIds) {
		this.invoiceIds = invoiceIds;
	}
	/**
	 * @return the billTOs
	 */
	public List<BillTO> getBillTOs() {
		return billTOs;
	}
	/**
	 * @param billTOs the billTOs to set
	 */
	public void setBillTOs(List<BillTO> billTOs) {
		this.billTOs = billTOs;
	}
	/**
	 * @return the customerTOs
	 */
	public List<CustomerTO> getCustomerTOs() {
		return customerTOs;
	}
	/**
	 * @param customerTOs the customerTOs to set
	 */
	public void setCustomerTOs(List<CustomerTO> customerTOs) {
		this.customerTOs = customerTOs;
	}
	/**
	 * @return the shipToCode
	 */
	public String[] getShipToCode() {
		return shipToCode;
	}
	/**
	 * @param shipToCode the shipToCode to set
	 */
	public void setShipToCode(String[] shipToCode) {
		this.shipToCode = shipToCode;
	}
	/**
	 * @return the shipToCodeList
	 */
	public List<String> getShipToCodeList() {
		return shipToCodeList;
	}
	/**
	 * @param shipToCodeList the shipToCodeList to set
	 */
	public void setShipToCodeList(List<String> shipToCodeList) {
		this.shipToCodeList = shipToCodeList;
	}
	/**
	 * @return the status
	 */
	public String[] getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String[] status) {
		this.status = status;
	}
	/**
	 * @return the receiverName
	 */
	public String[] getReceiverName() {
		return receiverName;
	}
	/**
	 * @param receiverName the receiverName to set
	 */
	public void setReceiverName(String[] receiverName) {
		this.receiverName = receiverName;
	}
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
	 * @return the invoiceRunSheetDetailId
	 */
	public Integer[] getInvoiceRunSheetDetailId() {
		return invoiceRunSheetDetailId;
	}
	/**
	 * @param invoiceRunSheetDetailId the invoiceRunSheetDetailId to set
	 */
	public void setInvoiceRunSheetDetailId(Integer[] invoiceRunSheetDetailId) {
		this.invoiceRunSheetDetailId = invoiceRunSheetDetailId;
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
	 * @return the createdDateStr
	 */
	public String getCreatedDateStr() {
		return createdDateStr;
	}
	/**
	 * @param createdDateStr the createdDateStr to set
	 */
	public void setCreatedDateStr(String createdDateStr) {
		this.createdDateStr = createdDateStr;
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
	 * @return the updatedDateStr
	 */
	public String getUpdatedDateStr() {
		return updatedDateStr;
	}
	/**
	 * @param updatedDateStr the updatedDateStr to set
	 */
	public void setUpdatedDateStr(String updatedDateStr) {
		this.updatedDateStr = updatedDateStr;
	}
	/**
	 * @return the officeTO
	 */
	public OfficeTO getOfficeTO() {
		if(officeTO == null)
			officeTO = new OfficeTO();
		return officeTO;
	}
	/**
	 * @param officeTO the officeTO to set
	 */
	public void setOfficeTO(OfficeTO officeTO) {
		this.officeTO = officeTO;
	}
	/**
	 * @return the saveOrUpdate
	 */
	public String getSaveOrUpdate() {
		return saveOrUpdate;
	}
	/**
	 * @param saveOrUpdate the saveOrUpdate to set
	 */
	public void setSaveOrUpdate(String saveOrUpdate) {
		this.saveOrUpdate = saveOrUpdate;
	}
	/**
	 * @return the transMag
	 */
	public String getTransMag() {
		return transMag;
	}
	/**
	 * @param transMag the transMag to set
	 */
	public void setTransMag(String transMag) {
		this.transMag = transMag;
	}
	
	
}
