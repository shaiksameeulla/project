package com.ff.pickup;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;

/**
 * The Class PickupRunsheetTO.
 */
public class PickupRunsheetTO extends CGBaseTO implements
		Comparable<PickupRunsheetTO> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	// common header
	/** The date. */
	private String date;
	private String systemTime;
	/** The login office id. */
	private Integer loginOfficeId;
	private Integer loginCityId;

	/** The login office code. */
	//private String loginOfficeCode;
	// private Integer loginRepOfficeId;
	/** The employee id. */
	private String employeeId;
	private String empCode;
	// update pickup run sheet
	/** The emp name. */
	private String empName;

	// Generate pickup run sheet
	/** The branch. */
	private String branch;

	/** The address flag. */
	private String addressFlag;

	/** The hub flag. */
	//private String hubFlag;

	/** The branch list. */
	private List<LabelValueBean> branchList;

	/** The employee list. */
	private List<LabelValueBean> employeeList;

	/** The row count. */
	private int rowCount;

	// Generate pickup run sheet grid values
	/** The pickup runsheet header ids. */
	private Integer[] pickupRunsheetHeaderIds = new Integer[rowCount];

	/** The pickup runsheet dtl ids. */
	private Integer[] pickupRunsheetDtlIds = new Integer[rowCount];

	/** The generate. */
	private String[] generate = new String[rowCount];

	/** The employee ids. */
	private Integer[] employeeIds = new Integer[rowCount];

	/** The runsheet nos. */
	private String[] runsheetNos = new String[rowCount];

	/** The employee names. */
	private String[] employeeNames = new String[rowCount];

	/** The runsheet status. */
	private String[] runsheetStatus = new String[rowCount];

	/** The pkup assignment header id. */
	private Integer[] pkupAssignmentHeaderId = new Integer[rowCount];

	/** The pkup assignment dtl id. */
	private String[] pkupAssignmentDtlId = new String[rowCount];

	/** The row check box. */
	private Integer[] rowCheckBox = new Integer[rowCount];

	/** The pickup type. */
	private String[] pickupType = new String[rowCount];

	/** The rev pickup order dtl id. */
	private Integer[] revPickupOrderDtlId = new Integer[rowCount];

	/** The pickup dlv location. */
	private String[] pickupDlvLocation = new String[rowCount];

	/** The pickup dlv location field. */
	private String pickupDlvLocationField;
	/** The runsheet no field. */
	private String runsheetNoField;

	/** The runsheet status field. */
	private String runsheetStatusField;

	/** The pkup assignment header id field. */
	private Integer pkupAssignmentHeaderIdField;

	/** The pkup assignment dtl id field. */
	private Integer pkupAssignmentDtlIdField;

	/** The pickup runsheet header field. */
	private Integer pickupRunsheetHeaderField;

	/** The pickup runsheet dtl id field. */
	private Integer pickupRunsheetDtlIdField;

	/** The runsheet type field. */
	private String runsheetTypeField;

	/** The pickup type field. */
	private String pickupTypeField;

	/** The rev pickup order dtl id field. */
	private Integer revPickupOrderDtlIdField;

	// update pickup run sheet grid values
	/** The branch ids. */
	private Integer[] branchIds = new Integer[rowCount];

	/** The branch name. */
	private String[] branchName = new String[rowCount];

	/** The customer ids. */
	private Integer[] customerIds = new Integer[rowCount];

	/** The cust name. */
	private String[] custName = new String[rowCount];

	/** The cust code. */
	private String[] custCode = new String[rowCount];

	/** The order no. */
	private String[] orderNo = new String[rowCount];

	/** The time. */
	private String[] time = new String[rowCount];

	/** The start cn no. */
	private String[] startCnNo = new String[rowCount];

	/** The end cn no. */
	private String[] endCnNo = new String[rowCount];

	/** The quantity. */
	private Integer[] quantity = new Integer[rowCount];

	/** The branch id field. */
	private Integer branchIdField;

	/** The branch name field. */
	private String branchNameField;

	/** The customer id. */
	private Integer customerId;

	/** The cust name field. */
	private String custNameField;

	/** The cust code field. */
	private String custCodeField;
	private String custType;
	private String shippedToCodeField;
	private String custCategory;

	/** The order no field. */
	private String orderNoField;

	/** The time field. */
	private String timeField;

	/** The start cn no field. */
	private String startCnNoField;

	/** The end cn no field. */
	private String endCnNoField;

	/** The quantity field. */
	private Integer quantityField;

	/** The error msg. */
	private String errorMsg;

	/** The assignment dtls ids. */
	private String assignmentDtlsIds;

	// For Booking integrations
	/** The consg numbers. */
	private List<String> consgNumbers = null;

	/** The inactive consg numbers. */
	private List<String> inactiveConsgNumbers = null;

	/** The old start serial number. */
	private String[] oldStartSerialNumber = new String[rowCount];

	/** The old quantity. */
	private Integer[] oldQuantity = new Integer[rowCount];

	/** The runsheet header status. */
	private String runsheetHeaderStatus;

	private Integer pickupLocationId;
	private Integer[] pickupDlvLocationIds = new Integer[rowCount];
	/** The pickup dlv location field. */
	private String pickupDlvLocationName;
	private String processNumber;
	private String[] shippedToCode = new String[rowCount];
	private String[] isNewRow = new String[rowCount];
	private String newRowField = "N"; 
//	private String newRowHdr = "N";
	private String[] deleteRow = new String[rowCount];
	//Added Narasimha
	private List<Integer> assignedEmpIds;
	private String hubOrBranch;
	//	UAT Fixes
	private String timeHrs;
	private String timeMins;
	
	
	
	//Code refactor
	private OfficeTO loginOfficeTO;
	private List<EmployeeTO> assignedEmployeeTOList;
	private List<OfficeTO> branchTOsList;
	private Integer branchId;
	private Integer assignedEmployeeId;
	private List<PickupRunsheetHeaderTO> assignedRunsheetTOList;
	private String transactionMsg;
	private String customerStatus;
	
	// Audit columns
    private Integer loggedInUserId;
        
	//Ids for twowaywrite
	private PickupTwoWayWriteTO pickupTwoWayWriteTO;
	/**
	 * Gets the date.
	 * 
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 * 
	 * @param date
	 *            the new date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Gets the login office id.
	 * 
	 * @return the login office id
	 */
	public Integer getLoginOfficeId() {
		return loginOfficeId;
	}

	/**
	 * Sets the login office id.
	 * 
	 * @param loginOfficeId
	 *            the new login office id
	 */
	public void setLoginOfficeId(Integer loginOfficeId) {
		this.loginOfficeId = loginOfficeId;
	}

	/**
	 * Gets the login office code.
	 * 
	 * @return the login office code
	 *//*
	public String getLoginOfficeCode() {
		return loginOfficeCode;
	}

	*//**
	 * Sets the login office code.
	 * 
	 * @param loginOfficeCode
	 *            the new login office code
	 *//*
	public void setLoginOfficeCode(String loginOfficeCode) {
		this.loginOfficeCode = loginOfficeCode;
	}*/

	/**
	 * Gets the employee id.
	 * 
	 * @return the employee id
	 */
	public String getEmployeeId() {
		return employeeId;
	}

	/**
	 * Sets the employee id.
	 * 
	 * @param employeeId
	 *            the new employee id
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * Gets the emp name.
	 * 
	 * @return the emp name
	 */
	public String getEmpName() {
		return empName;
	}

	/**
	 * Sets the emp name.
	 * 
	 * @param empName
	 *            the new emp name
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	/**
	 * Gets the branch.
	 * 
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * Sets the branch.
	 * 
	 * @param branch
	 *            the new branch
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**
	 * Gets the address flag.
	 * 
	 * @return the address flag
	 */
	public String getAddressFlag() {
		return addressFlag;
	}

	/**
	 * Sets the address flag.
	 * 
	 * @param addressFlag
	 *            the new address flag
	 */
	public void setAddressFlag(String addressFlag) {
		this.addressFlag = addressFlag;
	}

	/**
	 * Gets the hub flag.
	 * 
	 * @return the hub flag
	 */
	/*public String getHubFlag() {
		return hubFlag;
	}

	*//**
	 * Sets the hub flag.
	 * 
	 * @param hubFlag
	 *            the new hub flag
	 *//*
	public void setHubFlag(String hubFlag) {
		this.hubFlag = hubFlag;
	}*/

	/**
	 * Gets the branch list.
	 * 
	 * @return the branch list
	 */
	public List<LabelValueBean> getBranchList() {
		return branchList;
	}

	/**
	 * Sets the branch list.
	 * 
	 * @param branchList
	 *            the new branch list
	 */
	public void setBranchList(List<LabelValueBean> branchList) {
		this.branchList = branchList;
	}

	/**
	 * Gets the employee list.
	 * 
	 * @return the employee list
	 */
	public List<LabelValueBean> getEmployeeList() {
		return employeeList;
	}

	/**
	 * Sets the employee list.
	 * 
	 * @param employeeList
	 *            the new employee list
	 */
	public void setEmployeeList(List<LabelValueBean> employeeList) {
		this.employeeList = employeeList;
	}

	/**
	 * Gets the row count.
	 * 
	 * @return the row count
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * Sets the row count.
	 * 
	 * @param rowCount
	 *            the new row count
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * Gets the customer ids.
	 * 
	 * @return the customer ids
	 */
	public Integer[] getCustomerIds() {
		return customerIds;
	}

	/**
	 * Sets the customer ids.
	 * 
	 * @param customerIds
	 *            the new customer ids
	 */
	public void setCustomerIds(Integer[] customerIds) {
		this.customerIds = customerIds;
	}

	/**
	 * Gets the pickup runsheet header ids.
	 * 
	 * @return the pickup runsheet header ids
	 */
	public Integer[] getPickupRunsheetHeaderIds() {
		return pickupRunsheetHeaderIds;
	}

	/**
	 * Sets the pickup runsheet header ids.
	 * 
	 * @param pickupRunsheetHeaderIds
	 *            the new pickup runsheet header ids
	 */
	public void setPickupRunsheetHeaderIds(Integer[] pickupRunsheetHeaderIds) {
		this.pickupRunsheetHeaderIds = pickupRunsheetHeaderIds;
	}

	/**
	 * Gets the pickup runsheet dtl ids.
	 * 
	 * @return the pickup runsheet dtl ids
	 */
	public Integer[] getPickupRunsheetDtlIds() {
		return pickupRunsheetDtlIds;
	}

	/**
	 * Sets the pickup runsheet dtl ids.
	 * 
	 * @param pickupRunsheetDtlIds
	 *            the new pickup runsheet dtl ids
	 */
	public void setPickupRunsheetDtlIds(Integer[] pickupRunsheetDtlIds) {
		this.pickupRunsheetDtlIds = pickupRunsheetDtlIds;
	}

	/**
	 * Gets the generate.
	 * 
	 * @return the generate
	 */
	public String[] getGenerate() {
		return generate;
	}

	/**
	 * Sets the generate.
	 * 
	 * @param generate
	 *            the new generate
	 */
	public void setGenerate(String[] generate) {
		this.generate = generate;
	}

	/**
	 * Gets the employee ids.
	 * 
	 * @return the employee ids
	 */
	public Integer[] getEmployeeIds() {
		return employeeIds;
	}

	/**
	 * Sets the employee ids.
	 * 
	 * @param employeeIds
	 *            the new employee ids
	 */
	public void setEmployeeIds(Integer[] employeeIds) {
		this.employeeIds = employeeIds;
	}

	/**
	 * Gets the runsheet nos.
	 * 
	 * @return the runsheet nos
	 */
	public String[] getRunsheetNos() {
		return runsheetNos;
	}

	/**
	 * Sets the runsheet nos.
	 * 
	 * @param runsheetNos
	 *            the new runsheet nos
	 */
	public void setRunsheetNos(String[] runsheetNos) {
		this.runsheetNos = runsheetNos;
	}

	/**
	 * Gets the employee names.
	 * 
	 * @return the employee names
	 */
	public String[] getEmployeeNames() {
		return employeeNames;
	}

	/**
	 * Sets the employee names.
	 * 
	 * @param employeeNames
	 *            the new employee names
	 */
	public void setEmployeeNames(String[] employeeNames) {
		this.employeeNames = employeeNames;
	}

	/**
	 * Gets the runsheet status.
	 * 
	 * @return the runsheet status
	 */
	public String[] getRunsheetStatus() {
		return runsheetStatus;
	}

	/**
	 * Sets the runsheet status.
	 * 
	 * @param runsheetStatus
	 *            the new runsheet status
	 */
	public void setRunsheetStatus(String[] runsheetStatus) {
		this.runsheetStatus = runsheetStatus;
	}

	/**
	 * Gets the pkup assignment header id.
	 * 
	 * @return the pkup assignment header id
	 */
	public Integer[] getPkupAssignmentHeaderId() {
		return pkupAssignmentHeaderId;
	}

	/**
	 * Sets the pkup assignment header id.
	 * 
	 * @param pkupAssignmentHeaderId
	 *            the new pkup assignment header id
	 */
	public void setPkupAssignmentHeaderId(Integer[] pkupAssignmentHeaderId) {
		this.pkupAssignmentHeaderId = pkupAssignmentHeaderId;
	}

	/**
	 * Gets the runsheet no field.
	 * 
	 * @return the runsheet no field
	 */
	public String getRunsheetNoField() {
		return runsheetNoField;
	}

	/**
	 * Sets the runsheet no field.
	 * 
	 * @param runsheetNoField
	 *            the new runsheet no field
	 */
	public void setRunsheetNoField(String runsheetNoField) {
		this.runsheetNoField = runsheetNoField;
	}

	/**
	 * Gets the runsheet status field.
	 * 
	 * @return the runsheet status field
	 */
	public String getRunsheetStatusField() {
		return runsheetStatusField;
	}

	/**
	 * Sets the runsheet status field.
	 * 
	 * @param runsheetStatusField
	 *            the new runsheet status field
	 */
	public void setRunsheetStatusField(String runsheetStatusField) {
		this.runsheetStatusField = runsheetStatusField;
	}

	/**
	 * Gets the pkup assignment header id field.
	 * 
	 * @return the pkup assignment header id field
	 */
	public Integer getPkupAssignmentHeaderIdField() {
		return pkupAssignmentHeaderIdField;
	}

	/**
	 * Sets the pkup assignment header id field.
	 * 
	 * @param pkupAssignmentHeaderIdField
	 *            the new pkup assignment header id field
	 */
	public void setPkupAssignmentHeaderIdField(
			Integer pkupAssignmentHeaderIdField) {
		this.pkupAssignmentHeaderIdField = pkupAssignmentHeaderIdField;
	}

	/**
	 * Gets the pkup assignment dtl id field.
	 * 
	 * @return the pkup assignment dtl id field
	 */
	public Integer getPkupAssignmentDtlIdField() {
		return pkupAssignmentDtlIdField;
	}

	/**
	 * Sets the pkup assignment dtl id field.
	 * 
	 * @param pkupAssignmentDtlIdField
	 *            the new pkup assignment dtl id field
	 */
	public void setPkupAssignmentDtlIdField(Integer pkupAssignmentDtlIdField) {
		this.pkupAssignmentDtlIdField = pkupAssignmentDtlIdField;
	}

	/**
	 * Gets the pickup runsheet header field.
	 * 
	 * @return the pickup runsheet header field
	 */
	public Integer getPickupRunsheetHeaderField() {
		return pickupRunsheetHeaderField;
	}

	/**
	 * Sets the pickup runsheet header field.
	 * 
	 * @param pickupRunsheetHeaderField
	 *            the new pickup runsheet header field
	 */
	public void setPickupRunsheetHeaderField(Integer pickupRunsheetHeaderField) {
		this.pickupRunsheetHeaderField = pickupRunsheetHeaderField;
	}

	/**
	 * Gets the pickup runsheet dtl id field.
	 * 
	 * @return the pickup runsheet dtl id field
	 */
	public Integer getPickupRunsheetDtlIdField() {
		return pickupRunsheetDtlIdField;
	}

	/**
	 * Sets the pickup runsheet dtl id field.
	 * 
	 * @param pickupRunsheetDtlIdField
	 *            the new pickup runsheet dtl id field
	 */
	public void setPickupRunsheetDtlIdField(Integer pickupRunsheetDtlIdField) {
		this.pickupRunsheetDtlIdField = pickupRunsheetDtlIdField;
	}

	/**
	 * Gets the pickup type field.
	 * 
	 * @return the pickupTypeField
	 */
	public String getPickupTypeField() {
		return pickupTypeField;
	}

	/**
	 * Sets the pickup type field.
	 * 
	 * @param pickupTypeField
	 *            the pickupTypeField to set
	 */
	public void setPickupTypeField(String pickupTypeField) {
		this.pickupTypeField = pickupTypeField;
	}

	/**
	 * Gets the runsheet type field.
	 * 
	 * @return the runsheet type field
	 */
	public String getRunsheetTypeField() {
		return runsheetTypeField;
	}

	/**
	 * Sets the runsheet type field.
	 * 
	 * @param runsheetTypeField
	 *            the new runsheet type field
	 */
	public void setRunsheetTypeField(String runsheetTypeField) {
		this.runsheetTypeField = runsheetTypeField;
	}

	/**
	 * Gets the cust name.
	 * 
	 * @return the cust name
	 */
	public String[] getCustName() {
		return custName;
	}

	/**
	 * Sets the cust name.
	 * 
	 * @param custName
	 *            the new cust name
	 */
	public void setCustName(String[] custName) {
		this.custName = custName;
	}

	/**
	 * Gets the cust code.
	 * 
	 * @return the cust code
	 */
	public String[] getCustCode() {
		return custCode;
	}

	/**
	 * Sets the cust code.
	 * 
	 * @param custCode
	 *            the new cust code
	 */
	public void setCustCode(String[] custCode) {
		this.custCode = custCode;
	}

	/**
	 * Gets the order no.
	 * 
	 * @return the order no
	 */
	public String[] getOrderNo() {
		return orderNo;
	}

	/**
	 * Sets the order no.
	 * 
	 * @param orderNo
	 *            the new order no
	 */
	public void setOrderNo(String[] orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * Gets the time.
	 * 
	 * @return the time
	 */
	public String[] getTime() {
		return time;
	}

	/**
	 * Sets the time.
	 * 
	 * @param time
	 *            the new time
	 */
	public void setTime(String[] time) {
		this.time = time;
	}

	/**
	 * Gets the start cn no.
	 * 
	 * @return the start cn no
	 */
	public String[] getStartCnNo() {
		return startCnNo;
	}

	/**
	 * Sets the start cn no.
	 * 
	 * @param startCnNo
	 *            the new start cn no
	 */
	public void setStartCnNo(String[] startCnNo) {
		this.startCnNo = startCnNo;
	}

	/**
	 * Gets the end cn no.
	 * 
	 * @return the end cn no
	 */
	public String[] getEndCnNo() {
		return endCnNo;
	}

	/**
	 * Sets the end cn no.
	 * 
	 * @param endCnNo
	 *            the new end cn no
	 */
	public void setEndCnNo(String[] endCnNo) {
		this.endCnNo = endCnNo;
	}

	/**
	 * Gets the quantity.
	 * 
	 * @return the quantity
	 */
	public Integer[] getQuantity() {
		return quantity;
	}

	/**
	 * Sets the quantity.
	 * 
	 * @param quantity
	 *            the new quantity
	 */
	public void setQuantity(Integer[] quantity) {
		this.quantity = quantity;
	}

	/**
	 * Gets the customer id.
	 * 
	 * @return the customer id
	 */
	public Integer getCustomerId() {
		return customerId;
	}

	/**
	 * Sets the customer id.
	 * 
	 * @param customerId
	 *            the new customer id
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	/**
	 * Gets the cust name field.
	 * 
	 * @return the cust name field
	 */
	public String getCustNameField() {
		return custNameField;
	}

	/**
	 * Sets the cust name field.
	 * 
	 * @param custNameField
	 *            the new cust name field
	 */
	public void setCustNameField(String custNameField) {
		this.custNameField = custNameField;
	}

	/**
	 * Gets the cust code field.
	 * 
	 * @return the cust code field
	 */
	public String getCustCodeField() {
		return custCodeField;
	}

	/**
	 * Sets the cust code field.
	 * 
	 * @param custCodeField
	 *            the new cust code field
	 */
	public void setCustCodeField(String custCodeField) {
		this.custCodeField = custCodeField;
	}

	public String getShippedToCodeField() {
		return shippedToCodeField;
	}

	public void setShippedToCodeField(String shippedToCodeField) {
		this.shippedToCodeField = shippedToCodeField;
	}

	/**
	 * Gets the order no field.
	 * 
	 * @return the order no field
	 */
	public String getOrderNoField() {
		return orderNoField;
	}

	/**
	 * Sets the order no field.
	 * 
	 * @param orderNoField
	 *            the new order no field
	 */
	public void setOrderNoField(String orderNoField) {
		this.orderNoField = orderNoField;
	}

	/**
	 * Gets the time field.
	 * 
	 * @return the time field
	 */
	public String getTimeField() {
		return timeField;
	}

	/**
	 * Sets the time field.
	 * 
	 * @param timeField
	 *            the new time field
	 */
	public void setTimeField(String timeField) {
		this.timeField = timeField;
	}

	/**
	 * Gets the start cn no field.
	 * 
	 * @return the start cn no field
	 */
	public String getStartCnNoField() {
		return startCnNoField;
	}

	/**
	 * Sets the start cn no field.
	 * 
	 * @param startCnNoField
	 *            the new start cn no field
	 */
	public void setStartCnNoField(String startCnNoField) {
		this.startCnNoField = startCnNoField;
	}

	/**
	 * Gets the end cn no field.
	 * 
	 * @return the end cn no field
	 */
	public String getEndCnNoField() {
		return endCnNoField;
	}

	/**
	 * Sets the end cn no field.
	 * 
	 * @param endCnNoField
	 *            the new end cn no field
	 */
	public void setEndCnNoField(String endCnNoField) {
		this.endCnNoField = endCnNoField;
	}

	/**
	 * Gets the quantity field.
	 * 
	 * @return the quantity field
	 */
	public Integer getQuantityField() {
		return quantityField;
	}

	/**
	 * Sets the quantity field.
	 * 
	 * @param quantityField
	 *            the new quantity field
	 */
	public void setQuantityField(Integer quantityField) {
		this.quantityField = quantityField;
	}

	/**
	 * Gets the assignment dtls ids.
	 * 
	 * @return the assignment dtls ids
	 */
	public String getAssignmentDtlsIds() {
		return assignmentDtlsIds;
	}

	/**
	 * Gets the pkup assignment dtl id.
	 * 
	 * @return the pkup assignment dtl id
	 */
	public String[] getPkupAssignmentDtlId() {
		return pkupAssignmentDtlId;
	}

	/**
	 * Sets the pkup assignment dtl id.
	 * 
	 * @param pkupAssignmentDtlId
	 *            the new pkup assignment dtl id
	 */
	public void setPkupAssignmentDtlId(String[] pkupAssignmentDtlId) {
		this.pkupAssignmentDtlId = pkupAssignmentDtlId;
	}

	/**
	 * Sets the assignment dtls ids.
	 * 
	 * @param assignmentDtlsIds
	 *            the new assignment dtls ids
	 */
	public void setAssignmentDtlsIds(String assignmentDtlsIds) {
		this.assignmentDtlsIds = assignmentDtlsIds;
	}

	/*
	 * public Integer getLoginRepOfficeId() { return loginRepOfficeId; } public
	 * void setLoginRepOfficeId(Integer loginRepOfficeId) {
	 * this.loginRepOfficeId = loginRepOfficeId; }
	 */
	/**
	 * Gets the branch ids.
	 * 
	 * @return the branch ids
	 */
	public Integer[] getBranchIds() {
		return branchIds;
	}

	/**
	 * Sets the branch ids.
	 * 
	 * @param branchIds
	 *            the new branch ids
	 */
	public void setBranchIds(Integer[] branchIds) {
		this.branchIds = branchIds;
	}

	/**
	 * Gets the branch name.
	 * 
	 * @return the branch name
	 */
	public String[] getBranchName() {
		return branchName;
	}

	/**
	 * Sets the branch name.
	 * 
	 * @param branchName
	 *            the new branch name
	 */
	public void setBranchName(String[] branchName) {
		this.branchName = branchName;
	}

	/**
	 * Gets the branch id field.
	 * 
	 * @return the branch id field
	 */
	public Integer getBranchIdField() {
		return branchIdField;
	}

	/**
	 * Sets the branch id field.
	 * 
	 * @param branchIdField
	 *            the new branch id field
	 */
	public void setBranchIdField(Integer branchIdField) {
		this.branchIdField = branchIdField;
	}

	/**
	 * Gets the branch name field.
	 * 
	 * @return the branch name field
	 */
	public String getBranchNameField() {
		return branchNameField;
	}

	/**
	 * Sets the branch name field.
	 * 
	 * @param branchNameField
	 *            the new branch name field
	 */
	public void setBranchNameField(String branchNameField) {
		this.branchNameField = branchNameField;
	}

	/**
	 * Gets the error msg.
	 * 
	 * @return the error msg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * Sets the error msg.
	 * 
	 * @param errorMsg
	 *            the new error msg
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * Gets the consg numbers.
	 * 
	 * @return the consg numbers
	 */
	public List<String> getConsgNumbers() {
		return consgNumbers;
	}

	/**
	 * Sets the consg numbers.
	 * 
	 * @param consgNumbers
	 *            the new consg numbers
	 */
	public void setConsgNumbers(List<String> consgNumbers) {
		this.consgNumbers = consgNumbers;
	}

	/**
	 * Gets the inactive consg numbers.
	 * 
	 * @return the inactive consg numbers
	 */
	public List<String> getInactiveConsgNumbers() {
		return inactiveConsgNumbers;
	}

	/**
	 * Sets the inactive consg numbers.
	 * 
	 * @param inactiveConsgNumbers
	 *            the new inactive consg numbers
	 */
	public void setInactiveConsgNumbers(List<String> inactiveConsgNumbers) {
		this.inactiveConsgNumbers = inactiveConsgNumbers;
	}

	/**
	 * Gets the old start serial number.
	 * 
	 * @return the old start serial number
	 */
	public String[] getOldStartSerialNumber() {
		return oldStartSerialNumber;
	}

	/**
	 * Sets the old start serial number.
	 * 
	 * @param oldStartSerialNumber
	 *            the new old start serial number
	 */
	public void setOldStartSerialNumber(String[] oldStartSerialNumber) {
		this.oldStartSerialNumber = oldStartSerialNumber;
	}

	/**
	 * Gets the old quantity.
	 * 
	 * @return the old quantity
	 */
	public Integer[] getOldQuantity() {
		return oldQuantity;
	}

	/**
	 * Sets the old quantity.
	 * 
	 * @param oldQuantity
	 *            the new old quantity
	 */
	public void setOldQuantity(Integer[] oldQuantity) {
		this.oldQuantity = oldQuantity;
	}

	/**
	 * Gets the pickup type.
	 * 
	 * @return the pickupType
	 */
	public String[] getPickupType() {
		return pickupType;
	}

	/**
	 * Sets the pickup type.
	 * 
	 * @param pickupType
	 *            the pickupType to set
	 */
	public void setPickupType(String[] pickupType) {
		this.pickupType = pickupType;
	}

	/**
	 * Gets the row check box.
	 * 
	 * @return the rowCheckBox
	 */
	public Integer[] getRowCheckBox() {
		return rowCheckBox;
	}

	/**
	 * Sets the row check box.
	 * 
	 * @param rowCheckBox
	 *            the rowCheckBox to set
	 */
	public void setRowCheckBox(Integer[] rowCheckBox) {
		this.rowCheckBox = rowCheckBox;
	}

	/**
	 * Gets the runsheet header status.
	 * 
	 * @return the runsheet header status
	 */
	public String getRunsheetHeaderStatus() {
		return runsheetHeaderStatus;
	}

	/**
	 * Sets the runsheet header status.
	 * 
	 * @param runsheetHeaderStatus
	 *            the new runsheet header status
	 */
	public void setRunsheetHeaderStatus(String runsheetHeaderStatus) {
		this.runsheetHeaderStatus = runsheetHeaderStatus;
	}

	/**
	 * Gets the rev pickup order dtl id.
	 * 
	 * @return the revPickupOrderDtlId
	 */
	public Integer[] getRevPickupOrderDtlId() {
		return revPickupOrderDtlId;
	}

	/**
	 * Sets the rev pickup order dtl id.
	 * 
	 * @param revPickupOrderDtlId
	 *            the revPickupOrderDtlId to set
	 */
	public void setRevPickupOrderDtlId(Integer[] revPickupOrderDtlId) {
		this.revPickupOrderDtlId = revPickupOrderDtlId;
	}

	/**
	 * Gets the rev pickup order dtl id field.
	 * 
	 * @return the revPickupOrderDtlIdField
	 */
	public Integer getRevPickupOrderDtlIdField() {
		return revPickupOrderDtlIdField;
	}

	/**
	 * Sets the rev pickup order dtl id field.
	 * 
	 * @param revPickupOrderDtlIdField
	 *            the revPickupOrderDtlIdField to set
	 */
	public void setRevPickupOrderDtlIdField(Integer revPickupOrderDtlIdField) {
		this.revPickupOrderDtlIdField = revPickupOrderDtlIdField;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(PickupRunsheetTO obj1) {
		int returnVal = 0;
		if (StringUtils.isNotEmpty(this.custNameField)) {
			returnVal = this.custNameField.compareTo(obj1.custNameField);
		}
		return returnVal;
	}

	/**
	 * @return the pickupDlvLocation
	 */
	public String[] getPickupDlvLocation() {
		return pickupDlvLocation;
	}

	/**
	 * @param pickupDlvLocation
	 *            the pickupDlvLocation to set
	 */
	public void setPickupDlvLocation(String[] pickupDlvLocation) {
		this.pickupDlvLocation = pickupDlvLocation;
	}

	/**
	 * @return the pickupDlvLocationField
	 */
	public String getPickupDlvLocationField() {
		return pickupDlvLocationField;
	}

	/**
	 * @param pickupDlvLocationField
	 *            the pickupDlvLocationField to set
	 */
	public void setPickupDlvLocationField(String pickupDlvLocationField) {
		this.pickupDlvLocationField = pickupDlvLocationField;
	}

	/**
	 * @return the pickupLocationId
	 */
	public Integer getPickupLocationId() {
		return pickupLocationId;
	}

	/**
	 * @param pickupLocationId
	 *            the pickupLocationId to set
	 */
	public void setPickupLocationId(Integer pickupLocationId) {
		this.pickupLocationId = pickupLocationId;
	}

	/**
	 * @return the pickupDlvLocationIds
	 */
	public Integer[] getPickupDlvLocationIds() {
		return pickupDlvLocationIds;
	}

	/**
	 * @param pickupDlvLocationIds
	 *            the pickupDlvLocationIds to set
	 */
	public void setPickupDlvLocationIds(Integer[] pickupDlvLocationIds) {
		this.pickupDlvLocationIds = pickupDlvLocationIds;
	}

	/**
	 * @return the pickupDlvLocationName
	 */
	public String getPickupDlvLocationName() {
		return pickupDlvLocationName;
	}

	/**
	 * @param pickupDlvLocationName
	 *            the pickupDlvLocationName to set
	 */
	public void setPickupDlvLocationName(String pickupDlvLocationName) {
		this.pickupDlvLocationName = pickupDlvLocationName;
	}

	/**
	 * @return the loginCityId
	 */
	public Integer getLoginCityId() {
		return loginCityId;
	}

	/**
	 * @param loginCityId
	 *            the loginCityId to set
	 */
	public void setLoginCityId(Integer loginCityId) {
		this.loginCityId = loginCityId;
	}

	/**
	 * @return the processNumber
	 */
	public String getProcessNumber() {
		return processNumber;
	}

	/**
	 * @param processNumber
	 *            the processNumber to set
	 */
	public void setProcessNumber(String processNumber) {
		this.processNumber = processNumber;
	}

	/**
	 * @return the assignedEmpIds
	 */
	public List<Integer> getAssignedEmpIds() {
		return assignedEmpIds;
	}

	/**
	 * @param assignedEmpIds the assignedEmpIds to set
	 */
	public void setAssignedEmpIds(List<Integer> assignedEmpIds) {
		this.assignedEmpIds = assignedEmpIds;
	}

	/**
	 * @return the hubOrBranch
	 */
	public String getHubOrBranch() {
		return hubOrBranch;
	}

	/**
	 * @param hubOrBranch the hubOrBranch to set
	 */
	public void setHubOrBranch(String hubOrBranch) {
		this.hubOrBranch = hubOrBranch;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getTimeHrs() {
		return timeHrs;
	}

	public void setTimeHrs(String timeHrs) {
		this.timeHrs = timeHrs;
	}

	public String getTimeMins() {
		return timeMins;
	}

	public void setTimeMins(String timeMins) {
		this.timeMins = timeMins;
	}

	public OfficeTO getLoginOfficeTO() {
		return loginOfficeTO;
	}

	public void setLoginOfficeTO(OfficeTO loginOfficeTO) {
		this.loginOfficeTO = loginOfficeTO;
	}

	public List<EmployeeTO> getAssignedEmployeeTOList() {
		return assignedEmployeeTOList;
	}

	public void setAssignedEmployeeTOList(List<EmployeeTO> assignedEmployeeTOList) {
		this.assignedEmployeeTOList = assignedEmployeeTOList;
	}

	public List<OfficeTO> getBranchTOsList() {
		return branchTOsList;
	}

	public void setBranchTOsList(List<OfficeTO> branchTOsList) {
		this.branchTOsList = branchTOsList;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public Integer getAssignedEmployeeId() {
		return assignedEmployeeId;
	}

	public void setAssignedEmployeeId(Integer assignedEmployeeId) {
		this.assignedEmployeeId = assignedEmployeeId;
	}

	public List<PickupRunsheetHeaderTO> getAssignedRunsheetTOList() {
		return assignedRunsheetTOList;
	}

	public void setAssignedRunsheetTOList(
			List<PickupRunsheetHeaderTO> assignedRunsheetTOList) {
		this.assignedRunsheetTOList = assignedRunsheetTOList;
	}

	public String getTransactionMsg() {
		return transactionMsg;
	}

	public void setTransactionMsg(String transactionMsg) {
		this.transactionMsg = transactionMsg;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(String systemTime) {
		this.systemTime = systemTime;
	}

	public PickupTwoWayWriteTO getPickupTwoWayWriteTO() {
		return pickupTwoWayWriteTO;
	}

	public void setPickupTwoWayWriteTO(PickupTwoWayWriteTO pickupTwoWayWriteTO) {
		this.pickupTwoWayWriteTO = pickupTwoWayWriteTO;
	}

	public String[] getShippedToCode() {
		return shippedToCode;
	}

	public void setShippedToCode(String[] shippedToCode) {
		this.shippedToCode = shippedToCode;
	}

	public Integer getLoggedInUserId() {
		return loggedInUserId;
	}

	public void setLoggedInUserId(Integer loggedInUserId) {
		this.loggedInUserId = loggedInUserId;
	}
	public void setCustomerStatus(String customerStatus) {
		this.customerStatus = customerStatus;
	}
	public String getCustomerStatus() {
		return customerStatus;
	}

	public String getCustCategory() {
		return custCategory;
	}

	public void setCustCategory(String custCategory) {
		this.custCategory = custCategory;
	}

	public String[] getIsNewRow() {
		return isNewRow;
	}

	public void setIsNewRow(String[] isNewRow) {
		this.isNewRow = isNewRow;
	}

	public String getNewRowField() {
		return newRowField;
	}

	public void setNewRowField(String newRowField) {
		this.newRowField = newRowField;
	}

	/*public String getNewRowHdr() {
		return newRowHdr;
	}

	public void setNewRowHdr(String newRowHdr) {
		this.newRowHdr = newRowHdr;
	}*/

	public String[] getDeleteRow() {
		return deleteRow;
	}

	public void setDeleteRow(String[] deleteRow) {
		this.deleteRow = deleteRow;
	}
}
