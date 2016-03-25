package com.ff.pickup;

import java.util.Date;
import java.util.List;
import java.util.Set;
import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.organization.OfficeTO;


public class PickupOrderDetailsTO extends CGBaseTO implements Comparable<PickupOrderDetailsTO>
{
	
	
	  /**
	 * 
	 */
	private static final long serialVersionUID = -5493711636919033660L;
	
	private Integer detailId;
	  private Integer headerId;
	  private String orderNumber;
	  private String customerNo;
	  private String consignnorName;
	  private String address;
	  private Integer city;
	  private Integer pincode;
	  private String telephone;
	  private String mobile;
	  private Integer assignedBranch;
	  private String email;
	  private Integer consignmentType;
	  private Integer materialVal;
	  private String materialDesc;
	  private String insuaranceRefNum;
	  private String remarks;
	  private String dataTransferStatus;
	  private String orderRequestStatus;
      private List<OfficeTO> lstassignmentBranchTO;
      private Set<ReversePickupOrderBranchTO> orederBranchTOs;
      private List<String> errorCodes;
      
      
      // for display on save of upload
      
    

	
	private String cityName;
      private String pincodeName;
      private String consignmentName;
      
      // for confirm pickup order
      
      private Integer originatingRegionId;
      private Integer originatingHubId;
      private Date requestDate;
      
      private String originatingRegionName;
      private String originatingHubName;
      private String originatingBranchName;
      
      private Integer revOrderBranchId;
      private Integer assignedOfficeId;
      
      
      
      
      
	/**
	 * @return the originatingRegionId
	 */
	public Integer getOriginatingRegionId() {
		return originatingRegionId;
	}
	/**
	 * @param originatingRegionId the originatingRegionId to set
	 */
	public void setOriginatingRegionId(Integer originatingRegionId) {
		this.originatingRegionId = originatingRegionId;
	}
	/**
	 * @return the originatingHubId
	 */
	public Integer getOriginatingHubId() {
		return originatingHubId;
	}
	/**
	 * @param originatingHubId the originatingHubId to set
	 */
	public void setOriginatingHubId(Integer originatingHubId) {
		this.originatingHubId = originatingHubId;
	}
	
	/**
	 * @return the originatingRegionName
	 */
	public String getOriginatingRegionName() {
		return originatingRegionName;
	}
	/**
	 * @param originatingRegionName the originatingRegionName to set
	 */
	public void setOriginatingRegionName(String originatingRegionName) {
		this.originatingRegionName = originatingRegionName;
	}
	/**
	 * @return the originatingHubName
	 */
	public String getOriginatingHubName() {
		return originatingHubName;
	}
	/**
	 * @param originatingHubName the originatingHubName to set
	 */
	public void setOriginatingHubName(String originatingHubName) {
		this.originatingHubName = originatingHubName;
	}
	/**
	 * @return the detailId
	 */
	public Integer getDetailId() {
		return detailId;
	}
	/**
	 * @param detailId the detailId to set
	 */
	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}
	/**
	 * @return the headerId
	 */
	public Integer getHeaderId() {
		return headerId;
	}
	/**
	 * @param headerId the headerId to set
	 */
	public void setHeaderId(Integer headerId) {
		this.headerId = headerId;
	}
	/**
	 * @return the orderNumber
	 */
	public String getOrderNumber() {
		return orderNumber;
	}
	/**
	 * @param orderNumber the orderNumber to set
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	/**
	 * @return the customerNo
	 */
	public String getCustomerNo() {
		return customerNo;
	}
	/**
	 * @param customerNo the customerNo to set
	 */
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	/**
	 * @return the consignnorName
	 */
	public String getConsignnorName() {
		return consignnorName;
	}
	/**
	 * @param consignnorName the consignnorName to set
	 */
	public void setConsignnorName(String consignnorName) {
		this.consignnorName = consignnorName;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the city
	 */
	public Integer getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(Integer city) {
		this.city = city;
	}
	/**
	 * @return the pincode
	 */
	public Integer getPincode() {
		return pincode;
	}
	/**
	 * @param pincode the pincode to set
	 */
	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}
	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the assignedBranch
	 */
	public Integer getAssignedBranch() {
		return assignedBranch;
	}
	/**
	 * @param assignedBranch the assignedBranch to set
	 */
	public void setAssignedBranch(Integer assignedBranch) {
		this.assignedBranch = assignedBranch;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the consignmentType
	 */
	public Integer getConsignmentType() {
		return consignmentType;
	}
	/**
	 * @param consignmentType the consignmentType to set
	 */
	public void setConsignmentType(Integer consignmentType) {
		this.consignmentType = consignmentType;
	}
	/**
	 * @return the materialVal
	 */
	public Integer getMaterialVal() {
		return materialVal;
	}
	/**
	 * @param materialVal the materialVal to set
	 */
	public void setMaterialVal(Integer materialVal) {
		this.materialVal = materialVal;
	}
	/**
	 * @return the materialDesc
	 */
	public String getMaterialDesc() {
		return materialDesc;
	}
	/**
	 * @param materialDesc the materialDesc to set
	 */
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	/**
	 * @return the insuaranceRefNum
	 */
	public String getInsuaranceRefNum() {
		return insuaranceRefNum;
	}
	/**
	 * @param insuaranceRefNum the insuaranceRefNum to set
	 */
	public void setInsuaranceRefNum(String insuaranceRefNum) {
		this.insuaranceRefNum = insuaranceRefNum;
	}
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return the dataTransferStatus
	 */
	public String getDataTransferStatus() {
		return dataTransferStatus;
	}
	/**
	 * @param dataTransferStatus the dataTransferStatus to set
	 */
	public void setDataTransferStatus(String dataTransferStatus) {
		this.dataTransferStatus = dataTransferStatus;
	}
	/**
	 * @return the orderRequestStatus
	 */
	public String getOrderRequestStatus() {
		return orderRequestStatus;
	}
	/**
	 * @param orderRequestStatus the orderRequestStatus to set
	 */
	public void setOrderRequestStatus(String orderRequestStatus) {
		this.orderRequestStatus = orderRequestStatus;
	}
	/**
	 * @return the lstassignmentBranchTO
	 */
	public List<OfficeTO> getLstassignmentBranchTO() {
		return lstassignmentBranchTO;
	}
	/**
	 * @param lstassignmentBranchTO the lstassignmentBranchTO to set
	 */
	public void setLstassignmentBranchTO(List<OfficeTO> lstassignmentBranchTO) {
		this.lstassignmentBranchTO = lstassignmentBranchTO;
	}
	/**
	 * @return the orederBranchTOs
	 */
	public Set<ReversePickupOrderBranchTO> getOrederBranchTOs() {
		return orederBranchTOs;
	}
	/**
	 * @param orederBranchTOs the orederBranchTOs to set
	 */
	public void setOrederBranchTOs(Set<ReversePickupOrderBranchTO> orederBranchTOs) {
		this.orederBranchTOs = orederBranchTOs;
	}
	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * @return the pincodeName
	 */
	public String getPincodeName() {
		return pincodeName;
	}
	/**
	 * @param pincodeName the pincodeName to set
	 */
	public void setPincodeName(String pincodeName) {
		this.pincodeName = pincodeName;
	}
	/**
	 * @return the consignmentName
	 */
	public String getConsignmentName() {
		return consignmentName;
	}
	/**
	 * @param consignmentName the consignmentName to set
	 */
	public void setConsignmentName(String consignmentName) {
		this.consignmentName = consignmentName;
	}
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
	 * @return the originatingBranchName
	 */
	public String getOriginatingBranchName() {
		return originatingBranchName;
	}
	/**
	 * @param originatingBranchName the originatingBranchName to set
	 */
	public void setOriginatingBranchName(String originatingBranchName) {
		this.originatingBranchName = originatingBranchName;
	}
	/**
	 * @return the revOrderBranchId
	 */
	public Integer getRevOrderBranchId() {
		return revOrderBranchId;
	}
	/**
	 * @param revOrderBranchId the revOrderBranchId to set
	 */
	public void setRevOrderBranchId(Integer revOrderBranchId) {
		this.revOrderBranchId = revOrderBranchId;
	}
	/**
	 * @return the assignedOfficeId
	 */
	public Integer getAssignedOfficeId() {
		return assignedOfficeId;
	}
	/**
	 * @param assignedOfficeId the assignedOfficeId to set
	 */
	public void setAssignedOfficeId(Integer assignedOfficeId) {
		this.assignedOfficeId = assignedOfficeId;
	}
	
	@Override
	public int compareTo(PickupOrderDetailsTO to) {
		// TODO Auto-generated method stub
		int value = this.requestDate.compareTo(to.requestDate);
		return value;
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
 


}
