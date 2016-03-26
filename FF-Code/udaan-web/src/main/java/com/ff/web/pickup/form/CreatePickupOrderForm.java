package com.ff.web.pickup.form;

import java.util.List;

import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.business.CustomerTO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.organization.OfficeTO;
import com.ff.pickup.PickupOrderDetailsTO;
import com.ff.pickup.PickupOrderTO;
import com.ff.pickup.ReversePickupCustomerTO;
import com.ff.pickup.ReversePickupOrderBranchTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.umc.UserLoginTO;

public class CreatePickupOrderForm extends CGBaseForm{
	private static final long serialVersionUID = 1L;
	
	
	private CustomerTO customerTO;
	private PincodeTO pincodeTO;
	
	private CityTO cityTO;
	private OfficeTO officeTO;
	private PickupOrderDetailsTO pickupOrderDetailsTO;
	private PickupOrderTO pickupOrderTO;
	private ReversePickupCustomerTO reversePickupCustomerTO;
	private UserLoginTO userLoginTO;
	private ConsignmentTypeTO consignmentTypeTO;
	private List<LabelValueBean> officeTOs;
	private List<CityTO> cityTOs;
	private List<CustomerTO> customerTOs;
	private FormFile fileUpload;
	private String checkbox;
	private String orderNum;
	private String srNo;
	private List<ReversePickupOrderBranchTO> orderBranchTOs; 
	
	public CreatePickupOrderForm(){
		setCustomerTO(new CustomerTO());
		setPincodeTO(new PincodeTO());
		setOfficeTO(new OfficeTO());
		setPickupOrderDetailsTO(new PickupOrderDetailsTO());
		setPickupOrderTO(new PickupOrderTO());
		setReversePickupCustomerTO(new ReversePickupCustomerTO());
		setUserLoginTO(new UserLoginTO());	
		setConsignmentTypeTO(new ConsignmentTypeTO());
		setCityTO(new CityTO());
		
	}
	

	
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
	 * @return the pincodeTO
	 */
	public PincodeTO getPincodeTO() {
		return pincodeTO;
	}
	/**
	 * @param pincodeTO the pincodeTO to set
	 */
	public void setPincodeTO(PincodeTO pincodeTO) {
		this.pincodeTO = pincodeTO;
	}
	/**
	 * @return the officeTO
	 */
	public OfficeTO getOfficeTO() {
		return officeTO;
	}
	/**
	 * @param officeTO the officeTO to set
	 */
	public void setOfficeTO(OfficeTO officeTO) {
		this.officeTO = officeTO;
	}
	/**
	 * @return the pickupOrderDetailsTO
	 *//*
	public PickupOrderDetailsTO getPickupOrderDetailsTO() {
		return pickupOrderDetailsTO;
	}
	*//**
	 * @param pickupOrderDetailsTO the pickupOrderDetailsTO to set
	 *//*
	public void setPickupOrderDetailsTO(PickupOrderDetailsTO pickupOrderDetailsTO) {
		this.pickupOrderDetailsTO = pickupOrderDetailsTO;
	}*/
	/**
	 * @return the pickupOrderTO
	 */
	public PickupOrderTO getPickupOrderTO() {
		return pickupOrderTO;
	}
	/**
	 * @param pickupOrderTO the pickupOrderTO to set
	 */
	public void setPickupOrderTO(PickupOrderTO pickupOrderTO) {
		this.pickupOrderTO = pickupOrderTO;
	}
	/**
	 * @return the reversePickupCustomerTO
	 */
	public ReversePickupCustomerTO getReversePickupCustomerTO() {
		return reversePickupCustomerTO;
	}
	/**
	 * @param reversePickupCustomerTO the reversePickupCustomerTO to set
	 */
	public void setReversePickupCustomerTO(
			ReversePickupCustomerTO reversePickupCustomerTO) {
		this.reversePickupCustomerTO = reversePickupCustomerTO;
	}
	/**
	 * @return the userLoginTO
	 */
	public UserLoginTO getUserLoginTO() {
		return userLoginTO;
	}
	/**
	 * @param userLoginTO the userLoginTO to set
	 */
	public void setUserLoginTO(UserLoginTO userLoginTO) {
		this.userLoginTO = userLoginTO;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the consignmentTypeTO
	 */
	public ConsignmentTypeTO getConsignmentTypeTO() {
		return consignmentTypeTO;
	}

	/**
	 * @param consignmentTypeTO the consignmentTypeTO to set
	 */
	public void setConsignmentTypeTO(ConsignmentTypeTO consignmentTypeTO) {
		this.consignmentTypeTO = consignmentTypeTO;
	}

	

	

	/**
	 * @return the checkbox
	 */
	public String getCheckbox() {
		return checkbox;
	}

	/**
	 * @param checkbox the checkbox to set
	 */
	public void setCheckbox(String checkbox) {
		this.checkbox = checkbox;
	}

	

	/**
	 * @return the cityTO
	 */
	public CityTO getCityTO() {
		return cityTO;
	}

	/**
	 * @param cityTO the cityTO to set
	 */
	public void setCityTO(CityTO cityTO) {
		this.cityTO = cityTO;
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
	 * @return the cityTOs
	 */
	public List<CityTO> getCityTOs() {
		return cityTOs;
	}

	/**
	 * @param cityTOs the cityTOs to set
	 */
	public void setCityTOs(List<CityTO> cityTOs) {
		this.cityTOs = cityTOs;
	}



	/**
	 * @return the pickupOrderDetailsTO
	 */
	public PickupOrderDetailsTO getPickupOrderDetailsTO() {
		return pickupOrderDetailsTO;
	}



	/**
	 * @param pickupOrderDetailsTO the pickupOrderDetailsTO to set
	 */
	public void setPickupOrderDetailsTO(PickupOrderDetailsTO pickupOrderDetailsTO) {
		this.pickupOrderDetailsTO = pickupOrderDetailsTO;
	}



	/**
	 * @return the orderBranchTOs
	 */
	public List<ReversePickupOrderBranchTO> getOrderBranchTOs() {
		return orderBranchTOs;
	}



	/**
	 * @param orderBranchTOs the orderBranchTOs to set
	 */
	public void setOrderBranchTOs(List<ReversePickupOrderBranchTO> orderBranchTOs) {
		this.orderBranchTOs = orderBranchTOs;
	}



	/**
	 * @return the fileUpload
	 */
	public FormFile getFileUpload() {
		return fileUpload;
	}



	/**
	 * @param fileUpload the fileUpload to set
	 */
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}



	/**
	 * @return the officeTOs
	 */
	public List<LabelValueBean> getOfficeTOs() {
		return officeTOs;
	}



	/**
	 * @param officeTOs the officeTOs to set
	 */
	public void setOfficeTOs(List<LabelValueBean> officeTOs) {
		this.officeTOs = officeTOs;
	}



	/**
	 * @return the orderNum
	 */
	public String getOrderNum() {
		return orderNum;
	}



	/**
	 * @param orderNum the orderNum to set
	 */
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}




	

}
