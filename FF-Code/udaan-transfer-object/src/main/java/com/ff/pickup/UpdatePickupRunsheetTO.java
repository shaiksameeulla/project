package com.ff.pickup;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class UpdatePickupRunsheetTO extends CGBaseTO {
	
	private static final long serialVersionUID = 1L;
	
	//common header
	private String date;
	private Integer officeId;
	private Integer employeeId;
	
	//update pickup runsheet
	private String empName;
	private String pkupRunsheetNo;

	//Generate pickup runsheet
	private String branch;
	private String addressFlag;
	private String hubFlag;
	
	private int rowCount;

	private Integer[] customerIds = new Integer[rowCount];
	
	//update pickup runsheet grid values
	private String[] custName = new String[rowCount];
	private String[] custCode = new String[rowCount];
	private String[] time = new String[rowCount];
	private String[] startCnNo = new String[rowCount];
	private String[] endCnNo = new String[rowCount];
	private Integer[] quantity = new Integer[rowCount];
	
	//Generate pickup runsheet grid values
	private Integer[] pickupRunsheetIds = new Integer[rowCount];
	private Integer[] employeeIds = new Integer[rowCount];
	private String[] runsheetNos = new String[rowCount];
	private String[] employeeNames = new String[rowCount];
	private String[] runsheetStatus = new String[rowCount];

	private String custNameField;
	private String custCodeField;
	private String timeField;
	private String startCnNoField;
	private String endCnNoField;
	private Integer quantityField;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getPkupRunsheetNo() {
		return pkupRunsheetNo;
	}

	public void setPkupRunsheetNo(String pkupRunsheetNo) {
		this.pkupRunsheetNo = pkupRunsheetNo;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public String[] getCustName() {
		return custName;
	}

	public void setCustName(String[] custName) {
		this.custName = custName;
	}

	public String[] getCustCode() {
		return custCode;
	}

	public void setCustCode(String[] custCode) {
		this.custCode = custCode;
	}

	public String[] getTime() {
		return time;
	}

	public void setTime(String[] time) {
		this.time = time;
	}

	public String[] getStartCnNo() {
		return startCnNo;
	}

	public void setStartCnNo(String[] startCnNo) {
		this.startCnNo = startCnNo;
	}

	public String[] getEndCnNo() {
		return endCnNo;
	}

	public void setEndCnNo(String[] endCnNo) {
		this.endCnNo = endCnNo;
	}

	public Integer[] getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer[] quantity) {
		this.quantity = quantity;
	}

	public String getCustNameField() {
		return custNameField;
	}

	public void setCustNameField(String custNameField) {
		this.custNameField = custNameField;
	}

	public String getCustCodeField() {
		return custCodeField;
	}

	public void setCustCodeField(String custCodeField) {
		this.custCodeField = custCodeField;
	}

	public String getTimeField() {
		return timeField;
	}

	public void setTimeField(String timeField) {
		this.timeField = timeField;
	}

	public String getStartCnNoField() {
		return startCnNoField;
	}

	public void setStartCnNoField(String startCnNoField) {
		this.startCnNoField = startCnNoField;
	}

	public String getEndCnNoField() {
		return endCnNoField;
	}

	public void setEndCnNoField(String endCnNoField) {
		this.endCnNoField = endCnNoField;
	}

	public Integer getQuantityField() {
		return quantityField;
	}

	public void setQuantityField(Integer quantityField) {
		this.quantityField = quantityField;
	}

	public Integer getOfficeId() {
		return officeId;
	}

	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getAddressFlag() {
		return addressFlag;
	}

	public void setAddressFlag(String addressFlag) {
		this.addressFlag = addressFlag;
	}

	public String getHubFlag() {
		return hubFlag;
	}

	public void setHubFlag(String hubFlag) {
		this.hubFlag = hubFlag;
	}

	public Integer[] getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(Integer[] customerIds) {
		this.customerIds = customerIds;
	}

	public Integer[] getPickupRunsheetIds() {
		return pickupRunsheetIds;
	}

	public void setPickupRunsheetIds(Integer[] pickupRunsheetIds) {
		this.pickupRunsheetIds = pickupRunsheetIds;
	}

	public Integer[] getEmployeeIds() {
		return employeeIds;
	}

	public void setEmployeeIds(Integer[] employeeIds) {
		this.employeeIds = employeeIds;
	}

	public String[] getRunsheetNos() {
		return runsheetNos;
	}

	public void setRunsheetNos(String[] runsheetNos) {
		this.runsheetNos = runsheetNos;
	}

	public String[] getEmployeeNames() {
		return employeeNames;
	}

	public void setEmployeeNames(String[] employeeNames) {
		this.employeeNames = employeeNames;
	}

	public String[] getRunsheetStatus() {
		return runsheetStatus;
	}

	public void setRunsheetStatus(String[] runsheetStatus) {
		this.runsheetStatus = runsheetStatus;
	}
	
}
