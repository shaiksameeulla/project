package com.ff.to.billing;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.business.CustomerTO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;

/**
 * @author shahnsha
 *
 */
public class CustModificationTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String congNo;
	String currentDate;
	String bkgWtKg;
	String bkgWtGm;
	Double bkgFinalWeight;
	Double bkgDeclaredValue;
	RegionTO bkgRegionTO = new RegionTO();
	CustomerTO bkgCustTO = new CustomerTO();
	Integer cnOrgOffice;
	Integer cityId;
	String bkgCnType;
	
	String isExcessConsg;
	Integer exStationId;
	Integer exOffice;
	String exBookingDate;
	List<CityTO> cityList;
	List<CustomerTO> customerTOs;
	
	CustomerTO newCustTO = new CustomerTO();
	Double newCnFinalWeight;
	String newWtKg;
	String newWtGm;
	Double newDeclaredValue;
	String newShipToCode;
	ConsignmentTypeTO cnTypeTO = new ConsignmentTypeTO();
	
	String sucessMessage;
	String errorMessage;
	
	String isCustEditable = "Y";
	String isWeightEditable = "Y";
	String isDecValEditable = "Y";
	String isCnTypeEditable = "Y";
	
	String screenName;
	
	public String getCongNo() {
		return congNo;
	}
	public void setCongNo(String congNo) {
		this.congNo = congNo;
	}
	public String getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	public String getBkgWtKg() {
		return bkgWtKg;
	}
	public void setBkgWtKg(String bkgWtKg) {
		this.bkgWtKg = bkgWtKg;
	}
	public String getBkgWtGm() {
		return bkgWtGm;
	}
	public void setBkgWtGm(String bkgWtGm) {
		this.bkgWtGm = bkgWtGm;
	}
	public Double getBkgFinalWeight() {
		return bkgFinalWeight;
	}
	public void setBkgFinalWeight(Double bkgFinalWeight) {
		this.bkgFinalWeight = bkgFinalWeight;
	}
	public Double getBkgDeclaredValue() {
		return bkgDeclaredValue;
	}
	public void setBkgDeclaredValue(Double bkgDeclaredValue) {
		this.bkgDeclaredValue = bkgDeclaredValue;
	}
	public RegionTO getBkgRegionTO() {
		return bkgRegionTO;
	}
	public void setBkgRegionTO(RegionTO bkgRegionTO) {
		this.bkgRegionTO = bkgRegionTO;
	}
	public CustomerTO getBkgCustTO() {
		return bkgCustTO;
	}
	public void setBkgCustTO(CustomerTO bkgCustTO) {
		this.bkgCustTO = bkgCustTO;
	}
	public Integer getCnOrgOffice() {
		return cnOrgOffice;
	}
	public void setCnOrgOffice(Integer cnOrgOffice) {
		this.cnOrgOffice = cnOrgOffice;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getBkgCnType() {
		return bkgCnType;
	}
	public void setBkgCnType(String bkgCnType) {
		this.bkgCnType = bkgCnType;
	}
	public String getIsExcessConsg() {
		return isExcessConsg;
	}
	public void setIsExcessConsg(String isExcessConsg) {
		this.isExcessConsg = isExcessConsg;
	}
	public Integer getExStationId() {
		return exStationId;
	}
	public void setExStationId(Integer exStationId) {
		this.exStationId = exStationId;
	}
	public Integer getExOffice() {
		return exOffice;
	}
	public void setExOffice(Integer exOffice) {
		this.exOffice = exOffice;
	}
	public String getExBookingDate() {
		return exBookingDate;
	}
	public void setExBookingDate(String exBookingDate) {
		this.exBookingDate = exBookingDate;
	}
	public List<CityTO> getCityList() {
		return cityList;
	}
	public void setCityList(List<CityTO> cityList) {
		this.cityList = cityList;
	}
	public List<CustomerTO> getCustomerTOs() {
		return customerTOs;
	}
	public void setCustomerTOs(List<CustomerTO> customerTOs) {
		this.customerTOs = customerTOs;
	}
	public CustomerTO getNewCustTO() {
		return newCustTO;
	}
	public void setNewCustTO(CustomerTO newCustTO) {
		this.newCustTO = newCustTO;
	}
	public Double getNewCnFinalWeight() {
		return newCnFinalWeight;
	}
	public void setNewCnFinalWeight(Double newCnFinalWeight) {
		this.newCnFinalWeight = newCnFinalWeight;
	}
	public String getNewWtKg() {
		return newWtKg;
	}
	public void setNewWtKg(String newWtKg) {
		this.newWtKg = newWtKg;
	}
	public String getNewWtGm() {
		return newWtGm;
	}
	public void setNewWtGm(String newWtGm) {
		this.newWtGm = newWtGm;
	}
	public Double getNewDeclaredValue() {
		return newDeclaredValue;
	}
	public void setNewDeclaredValue(Double newDeclaredValue) {
		this.newDeclaredValue = newDeclaredValue;
	}
	public String getNewShipToCode() {
		return newShipToCode;
	}
	public void setNewShipToCode(String newShipToCode) {
		this.newShipToCode = newShipToCode;
	}
	public ConsignmentTypeTO getCnTypeTO() {
		return cnTypeTO;
	}
	public void setCnTypeTO(ConsignmentTypeTO cnTypeTO) {
		this.cnTypeTO = cnTypeTO;
	}
	public String getSucessMessage() {
		return sucessMessage;
	}
	public void setSucessMessage(String sucessMessage) {
		this.sucessMessage = sucessMessage;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getIsCustEditable() {
		return isCustEditable;
	}
	public void setIsCustEditable(String isCustEditable) {
		this.isCustEditable = isCustEditable;
	}
	public String getIsWeightEditable() {
		return isWeightEditable;
	}
	public void setIsWeightEditable(String isWeightEditable) {
		this.isWeightEditable = isWeightEditable;
	}
	public String getIsDecValEditable() {
		return isDecValEditable;
	}
	public void setIsDecValEditable(String isDecValEditable) {
		this.isDecValEditable = isDecValEditable;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String getIsCnTypeEditable() {
		return isCnTypeEditable;
	}
	public void setIsCnTypeEditable(String isCnTypeEditable) {
		this.isCnTypeEditable = isCnTypeEditable;
	}
}
