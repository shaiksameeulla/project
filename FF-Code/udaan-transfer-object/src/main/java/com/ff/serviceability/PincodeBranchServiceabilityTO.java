package com.ff.serviceability;

import java.util.Date;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.organization.OfficeTO;

public class PincodeBranchServiceabilityTO extends CGBaseTO {
	
	private static final long serialVersionUID = -2753299598766361116L;
	private OfficeTO officeTO;
	private CityTO cityTO;
	private PincodeTO pincodeTO;
	private Integer createdBy;
	private Integer updateBy;
	private Date updateDate;
	private Date creationDate;
	private String dtToBranch;
	private String status;
	
	public OfficeTO getOfficeTO() {
		if(officeTO == null){
			officeTO = new OfficeTO();
		}
		return officeTO;
	}
	public void setOfficeTO(OfficeTO officeTO) {
		this.officeTO = officeTO;
	}
	
	public CityTO getCityTO() {
		if(cityTO == null){
			cityTO = new CityTO();
		}
		return cityTO;
	}
	
	public void setCityTO(CityTO cityTO) {
		this.cityTO = cityTO;
	}
	
	/**
	 * @return the pincodeTO
	 */
	public PincodeTO getPincodeTO() {
		if(pincodeTO ==  null){
			pincodeTO = new PincodeTO();
		}
		return pincodeTO;
	}
	/**
	 * @param pincodeTO the pincodeTO to set
	 */
	public void setPincodeTO(PincodeTO pincodeTO) {
		this.pincodeTO = pincodeTO;
	}
	
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Integer getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getDtToBranch() {
		return dtToBranch;
	}
	public void setDtToBranch(String dtToBranch) {
		this.dtToBranch = dtToBranch;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

}
