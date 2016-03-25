package com.ff.pickup;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.organization.OfficeTO;


public class PickupOrderTO extends CGBaseTO
{
	
	  /**
	 * 
	 */
	private static final long serialVersionUID = -6538554507539185605L;
	private Boolean isValidHeader;
	private List<String> errorCodes;
		private Integer requestHeaderId;
	  private Date requestDate;
	  private String requestDateStr;
	  private Integer originatingOffice;
	  private Integer deliveryOffice;
	  private Integer originatingRegion;
	  private Integer originatingHub; 
	  private Integer customer;
	  private Integer numOfOrders;
	  private Set<PickupOrderDetailsTO> detailsTO;
	  private Integer pickupOrderNo;
	  private Integer originBranch;
	  private Integer deliveryBranch;
	  private List<List<OfficeTO>> lstOffice;
	  private Boolean bulk;
	  private List<List> errList;
	  private Integer loggedInOfficeId;
	  private Integer loggedRegionOfficeId;
	  private Integer loggedInhubOfficeId;
	  private List<PickupOrderDetailsTO> validBookings;
	  private List<PickupOrderDetailsTO> invalidBookings;
	 
	  private String region;
	  private String hub;
	  private String branch;
	  private String deliveryOfficeName;
	  private String officeCode;
	  private Integer customerId;
	  private Integer loggedInUserId;
	  private String customerCode;
	  private Map<String,Integer> codeIdMap;
	  private String isError = "N" ;
	  private String fileName;
	  private int count;
	  private Integer orderBranchId[] = new Integer[count];
	  private Integer assignedOfficeId[] = new Integer[count];
	  
	  private boolean createFlag = Boolean.FALSE;
	  private Integer checkbox[] = new Integer[count];
	  private  String orderNumber[]= new String[count];
	  private  String consignnorName[]= new String[count];
	  private  String address[]= new String[count];
	  private  String pincode[]= new String[count];
	  private  String city[]= new String[count];
	  private  String telephone[]= new String[count];
	  private  String mobile[]= new String[count];
	  private  String assignedBranch[]= new String[count];
	  private  String email[]= new String[count];
	  private  String consignmentType[]= new String[count];
	  private  String materialDesc[]= new String[count];
	  private  String insuaranceRefNum[]= new String[count];
	  private  String remarks[]= new String[count]; 
	  
	  private  Integer pincodeId[]= new Integer[count];
	  
	  private  Integer cityId[]= new Integer[count];
      private  Integer assignedBranchId[]= new Integer[count];
      private  Integer consignmentTypeId[]= new Integer[count];
      
      // for confirm order request
      private  boolean flag = Boolean.FALSE; 
      private  String customerName;
      private  String reqDate[]= new String[count];
      private  String regionName[]= new String[count];
      private  String hubName[]= new String[count];
      private  String branchName[]= new String[count];
      
      private  Integer regionId[]= new Integer[count];
      private  Integer hubId[]= new Integer[count];
      private  Integer branchId[]= new Integer[count];
      
      private String confirm[] = new String[count];
      private String srNoArr[] = new String[count];
      private String srNo;
      private boolean isUpdated = Boolean.FALSE;
    //Two-way write
      private PickupTwoWayWriteTO pickupTwoWayWriteTO;
      
      public String getRequestDateStr() {
  		return requestDateStr;
  	}
  	public void setRequestDateStr(String requestDateStr) {
  		this.requestDateStr = requestDateStr;
  	}
      
      /**
  	 * @return the orderBranchId
  	 */
  	public Integer[] getOrderBranchId() {
  		return orderBranchId;
  	}
  	/**
  	 * @param orderBranchId the orderBranchId to set
  	 */
  	public void setOrderBranchId(Integer[] orderBranchId) {
  		this.orderBranchId = orderBranchId;
  	}
  	/**
  	 * @return the assignedOfficeId
  	 */
  	public Integer[] getAssignedOfficeId() {
  		return assignedOfficeId;
  	}
  	/**
  	 * @param assignedOfficeId the assignedOfficeId to set
  	 */
  	public void setAssignedOfficeId(Integer[] assignedOfficeId) {
  		this.assignedOfficeId = assignedOfficeId;
  	}
   
	/**
	 * @return the regionId
	 */
	public Integer[] getRegionId() {
		return regionId;
	}
	/**
	 * @param regionId the regionId to set
	 */
	public void setRegionId(Integer[] regionId) {
		this.regionId = regionId;
	}
	/**
	 * @return the hubId
	 */
	public Integer[] getHubId() {
		return hubId;
	}
	/**
	 * @param hubId the hubId to set
	 */
	public void setHubId(Integer[] hubId) {
		this.hubId = hubId;
	}
	/**
	 * @return the branchId
	 */
	public Integer[] getBranchId() {
		return branchId;
	}
	/**
	 * @param branchId the branchId to set
	 */
	public void setBranchId(Integer[] branchId) {
		this.branchId = branchId;
	}
	/**
	 * @return the regionName
	 */
	public String[] getRegionName() {
		return regionName;
	}
	/**
	 * @param regionName the regionName to set
	 */
	public void setRegionName(String[] regionName) {
		this.regionName = regionName;
	}
	/**
	 * @return the hubName
	 */
	public String[] getHubName() {
		return hubName;
	}
	/**
	 * @param hubName the hubName to set
	 */
	public void setHubName(String[] hubName) {
		this.hubName = hubName;
	}
	/**
	 * @return the branchName
	 */
	public String[] getBranchName() {
		return branchName;
	}
	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String[] branchName) {
		this.branchName = branchName;
	}
	private Map<Integer,String> orderNum;
      
      
	
	
	/**
	 * @return the requestDate
	 */
	public Date getRequestDate() {
		return requestDate;
	}
	/**
	 * @param requestDate the requestDate to set
	 */
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	/**
	 * @return the originatingRegion
	 */
	public Integer getOriginatingRegion() {
		return originatingRegion;
	}
	/**
	 * @param originatingRegion the originatingRegion to set
	 */
	public void setOriginatingRegion(Integer originatingRegion) {
		this.originatingRegion = originatingRegion;
	}
	/**
	 * @return the originatingHub
	 */
	public Integer getOriginatingHub() {
		return originatingHub;
	}
	/**
	 * @param originatingHub the originatingHub to set
	 */
	public void setOriginatingHub(Integer originatingHub) {
		this.originatingHub = originatingHub;
	}
	/**
	 * @return the customer
	 */
	public Integer getCustomer() {
		return customer;
	}
	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Integer customer) {
		this.customer = customer;
	}
	/**
	 * @return the numOfOrders
	 */
	public Integer getNumOfOrders() {
		return numOfOrders;
	}
	/**
	 * @param numOfOrders the numOfOrders to set
	 */
	public void setNumOfOrders(Integer numOfOrders) {
		this.numOfOrders = numOfOrders;
	}
	/**
	 * @return the detailsTO
	 */
	public Set<PickupOrderDetailsTO> getDetailsTO() {
		return detailsTO;
	}
	/**
	 * @param detailsTO the detailsTO to set
	 */
	public void setDetailsTO(Set<PickupOrderDetailsTO> detailsTO) {
		this.detailsTO = detailsTO;
	}
	/**
	 * @return the pickupOrderNo
	 */
	public Integer getPickupOrderNo() {
		return pickupOrderNo;
	}
	/**
	 * @param pickupOrderNo the pickupOrderNo to set
	 */
	public void setPickupOrderNo(Integer pickupOrderNo) {
		this.pickupOrderNo = pickupOrderNo;
	}
	/**
	 * @return the originBranch
	 */
	public Integer getOriginBranch() {
		return originBranch;
	}
	/**
	 * @param originBranch the originBranch to set
	 */
	public void setOriginBranch(Integer originBranch) {
		this.originBranch = originBranch;
	}
	/**
	 * @return the deliveryBranch
	 */
	public Integer getDeliveryBranch() {
		return deliveryBranch;
	}
	/**
	 * @param deliveryBranch the deliveryBranch to set
	 */
	public void setDeliveryBranch(Integer deliveryBranch) {
		this.deliveryBranch = deliveryBranch;
	}
	
	/**
	 * @return the bulk
	 */
	public Boolean getBulk() {
		return bulk;
	}
	/**
	 * @param bulk the bulk to set
	 */
	public void setBulk(Boolean bulk) {
		this.bulk = bulk;
	}
	/**
	 * @return the errList
	 */
	public List<List> getErrList() {
		return errList;
	}
	/**
	 * @param errList the errList to set
	 */
	public void setErrList(List<List> errList) {
		this.errList = errList;
	}
	/**
	 * @return the loggedInOfficeId
	 */
	public Integer getLoggedInOfficeId() {
		return loggedInOfficeId;
	}
	/**
	 * @param loggedInOfficeId the loggedInOfficeId to set
	 */
	public void setLoggedInOfficeId(Integer loggedInOfficeId) {
		this.loggedInOfficeId = loggedInOfficeId;
	}
	/**
	 * @return the loggedRegionOfficeId
	 */
	public Integer getLoggedRegionOfficeId() {
		return loggedRegionOfficeId;
	}
	/**
	 * @param loggedRegionOfficeId the loggedRegionOfficeId to set
	 */
	public void setLoggedRegionOfficeId(Integer loggedRegionOfficeId) {
		this.loggedRegionOfficeId = loggedRegionOfficeId;
	}
	/**
	 * @return the loggedInhubOfficeId
	 */
	public Integer getLoggedInhubOfficeId() {
		return loggedInhubOfficeId;
	}
	/**
	 * @param loggedInhubOfficeId the loggedInhubOfficeId to set
	 */
	public void setLoggedInhubOfficeId(Integer loggedInhubOfficeId) {
		this.loggedInhubOfficeId = loggedInhubOfficeId;
	}
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	/**
	 * @return the orderNumber
	 */
	public String[] getOrderNumber() {
		return orderNumber;
	}
	/**
	 * @param orderNumber the orderNumber to set
	 */
	public void setOrderNumber(String[] orderNumber) {
		this.orderNumber = orderNumber;
	}
	/**
	 * @return the consignnorName
	 */
	public String[] getConsignnorName() {
		return consignnorName;
	}
	/**
	 * @param consignnorName the consignnorName to set
	 */
	public void setConsignnorName(String[] consignnorName) {
		this.consignnorName = consignnorName;
	}
	/**
	 * @return the address
	 */
	public String[] getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String[] address) {
		this.address = address;
	}
	/**
	 * @return the pincode
	 */
	public String[] getPincode() {
		return pincode;
	}
	/**
	 * @param pincode the pincode to set
	 */
	public void setPincode(String[] pincode) {
		this.pincode = pincode;
	}
	/**
	 * @return the city
	 */
	public String[] getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String[] city) {
		this.city = city;
	}
	/**
	 * @return the telephone
	 */
	public String[] getTelephone() {
		return telephone;
	}
	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String[] telephone) {
		this.telephone = telephone;
	}
	/**
	 * @return the mobile
	 */
	public String[] getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String[] mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the assignedBranch
	 */
	public String[] getAssignedBranch() {
		return assignedBranch;
	}
	/**
	 * @param assignedBranch the assignedBranch to set
	 */
	public void setAssignedBranch(String[] assignedBranch) {
		this.assignedBranch = assignedBranch;
	}
	/**
	 * @return the email
	 */
	public String[] getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String[] email) {
		this.email = email;
	}
	/**
	 * @return the consignmentType
	 */
	public String[] getConsignmentType() {
		return consignmentType;
	}
	/**
	 * @param consignmentType the consignmentType to set
	 */
	public void setConsignmentType(String[] consignmentType) {
		this.consignmentType = consignmentType;
	}
	/**
	 * @return the materialDesc
	 */
	public String[] getMaterialDesc() {
		return materialDesc;
	}
	/**
	 * @param materialDesc the materialDesc to set
	 */
	public void setMaterialDesc(String[] materialDesc) {
		this.materialDesc = materialDesc;
	}
	/**
	 * @return the insuaranceRefNum
	 */
	public String[] getInsuaranceRefNum() {
		return insuaranceRefNum;
	}
	/**
	 * @param insuaranceRefNum the insuaranceRefNum to set
	 */
	public void setInsuaranceRefNum(String[] insuaranceRefNum) {
		this.insuaranceRefNum = insuaranceRefNum;
	}
	/**
	 * @return the remarks
	 */
	public String[] getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return the pincodeId
	 */
	public Integer[] getPincodeId() {
		return pincodeId;
	}
	/**
	 * @param pincodeId the pincodeId to set
	 */
	public void setPincodeId(Integer[] pincodeId) {
		this.pincodeId = pincodeId;
	}
	/**
	 * @return the cityId
	 */
	public Integer[] getCityId() {
		return cityId;
	}
	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(Integer[] cityId) {
		this.cityId = cityId;
	}
	/**
	 * @return the assignedBranchId
	 */
	public Integer[] getAssignedBranchId() {
		return assignedBranchId;
	}
	/**
	 * @param assignedBranchId the assignedBranchId to set
	 */
	public void setAssignedBranchId(Integer[] assignedBranchId) {
		this.assignedBranchId = assignedBranchId;
	}
	/**
	 * @return the consignmentTypeId
	 */
	public Integer[] getConsignmentTypeId() {
		return consignmentTypeId;
	}
	/**
	 * @param consignmentTypeId the consignmentTypeId to set
	 */
	public void setConsignmentTypeId(Integer[] consignmentTypeId) {
		this.consignmentTypeId = consignmentTypeId;
	}
	/**
	 * @return the orderNum
	 */
	public Map<Integer,String> getOrderNum() {
		return orderNum;
	}
	/**
	 * @param orderNum2 the orderNum to set
	 */
	public void setOrderNum(Map<Integer, String> orderNum2) {
		this.orderNum = orderNum2;
	}
	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}
	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}
	/**
	 * @return the hub
	 */
	public String getHub() {
		return hub;
	}
	/**
	 * @param hub the hub to set
	 */
	public void setHub(String hub) {
		this.hub = hub;
	}
	/**
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}
	/**
	 * @param branch the branch to set
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}
	/**
	 * @return the officeCode
	 */
	public String getOfficeCode() {
		return officeCode;
	}
	/**
	 * @param officeCode the officeCode to set
	 */
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}
	/**
	 * @return the customerId
	 */
	public Integer getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	/**
	 * @return the reqDate
	 */
	public String[] getReqDate() {
		return reqDate;
	}
	/**
	 * @param reqDate the reqDate to set
	 */
	public void setReqDate(String reqDate[]) {
		this.reqDate = reqDate;
	}
	/**
	 * @return the checkbox
	 */
	public Integer[] getCheckbox() {
		return checkbox;
	}
	/**
	 * @param checkbox the checkbox to set
	 */
	public void setCheckbox(Integer checkbox[]) {
		this.checkbox = checkbox;
	}
	/**
	 * @return the confirm
	 */
	public String[] getConfirm() {
		return confirm;
	}
	/**
	 * @param confirm the confirm to set
	 */
	public void setConfirm(String confirm[]) {
		this.confirm = confirm;
	}
	public Integer getRequestHeaderId() {
		return requestHeaderId;
	}
	public void setRequestHeaderId(Integer requestHeaderId) {
		this.requestHeaderId = requestHeaderId;
	}
	public Integer getLoggedInUserId() {
		return loggedInUserId;
	}
	public void setLoggedInUserId(Integer loggedInUserId) {
		this.loggedInUserId = loggedInUserId;
	}
	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}
	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	/**
	 * @return the codeIdMap
	 */
	public Map<String,Integer> getCodeIdMap() {
		return codeIdMap;
	}
	/**
	 * @param codeIdMap the codeIdMap to set
	 */
	public void setCodeIdMap(Map<String,Integer> codeIdMap) {
		this.codeIdMap = codeIdMap;
	}
	
	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * @return the flag
	 */
	public boolean isFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	/**
	 * @return the lstOffice
	 */
	public List<List<OfficeTO>> getLstOffice() {
		return lstOffice;
	}
	/**
	 * @param lstOffice the lstOffice to set
	 */
	public void setLstOffice(List<List<OfficeTO>> lstOffice) {
		this.lstOffice = lstOffice;
	}
	/**
	 * @return the originatingOffice
	 */
	public Integer getOriginatingOffice() {
		return originatingOffice;
	}
	/**
	 * @param originatingOffice the originatingOffice to set
	 */
	public void setOriginatingOffice(Integer originatingOffice) {
		this.originatingOffice = originatingOffice;
	}
	/**
	 * @return the deliveryOffice
	 */
	public Integer getDeliveryOffice() {
		return deliveryOffice;
	}
	/**
	 * @param deliveryOffice the deliveryOffice to set
	 */
	public void setDeliveryOffice(Integer deliveryOffice) {
		this.deliveryOffice = deliveryOffice;
	}
	/**
	 * @return the deliveryOfficeName
	 */
	public String getDeliveryOfficeName() {
		return deliveryOfficeName;
	}
	/**
	 * @param deliveryOfficeName the deliveryOfficeName to set
	 */
	public void setDeliveryOfficeName(String deliveryOfficeName) {
		this.deliveryOfficeName = deliveryOfficeName;
	}
	/**
	 * @return the createFlag
	 */
	public boolean isCreateFlag() {
		return createFlag;
	}
	/**
	 * @param createFlag the createFlag to set
	 */
	public void setCreateFlag(boolean createFlag) {
		this.createFlag = createFlag;
	}
	/**
	 * @return the srNo
	 */
	public String getSrNo() {
		return srNo;
	}
	/**
	 * @param srNo the srNo to set
	 */
	public void setSrNo(String srNo) {
		this.srNo = srNo;
	}
	/**
	 * @return the srNoArr
	 */
	public String[] getSrNoArr() {
		return srNoArr;
	}
	/**
	 * @param srNoArr the srNoArr to set
	 */
	public void setSrNoArr(String srNoArr[]) {
		this.srNoArr = srNoArr;
	}
	/**
	 * @return the isError
	 */
	public String getIsError() {
		return isError;
	}
	/**
	 * @param isError the isError to set
	 */
	public void setIsError(String isError) {
		this.isError = isError;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the isValidHeader
	 */
	public Boolean getIsValidHeader() {
		return isValidHeader;
	}
	/**
	 * @param isValidHeader the isValidHeader to set
	 */
	public void setIsValidHeader(Boolean isValidHeader) {
		this.isValidHeader = isValidHeader;
	}
	/**
	 * @return the errorCodes
	 */
	public List<String> getErrorCodes() {
		return errorCodes;
	}
	/**
	 * @param errorCodes the errorCodes to set
	 */
	public void setErrorCodes(List<String> errorCodes) {
		this.errorCodes = errorCodes;
	}
	/**
	 * @return the validBookings
	 */
	public List<PickupOrderDetailsTO> getValidBookings() {
		return validBookings;
	}
	/**
	 * @param validBookings the validBookings to set
	 */
	public void setValidBookings(List<PickupOrderDetailsTO> validBookings) {
		this.validBookings = validBookings;
	}
	/**
	 * @return the invalidBookings
	 */
	public List<PickupOrderDetailsTO> getInvalidBookings() {
		return invalidBookings;
	}
	/**
	 * @param invalidBookings the invalidBookings to set
	 */
	public void setInvalidBookings(List<PickupOrderDetailsTO> invalidBookings) {
		this.invalidBookings = invalidBookings;
	}
	public boolean isUpdated() {
		return isUpdated;
	}
	public void setUpdated(boolean isUpdated) {
		this.isUpdated = isUpdated;
	}
	public PickupTwoWayWriteTO getPickupTwoWayWriteTO() {
		return pickupTwoWayWriteTO;
	}
	public void setPickupTwoWayWriteTO(PickupTwoWayWriteTO pickupTwoWayWriteTO) {
		this.pickupTwoWayWriteTO = pickupTwoWayWriteTO;
	}
}
