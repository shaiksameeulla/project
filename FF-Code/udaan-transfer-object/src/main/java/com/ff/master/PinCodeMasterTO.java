package com.ff.master;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.CityTO;
import com.ff.geography.StateTO;
import com.ff.organization.OfficeTO;

public class PinCodeMasterTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2420888696841560702L;
	private String pincodeId;
	private String pincodeNo;
	private String regionId;
	private String stateId;
	private String cityId;
	private String location;
	private String[] servicablebranch;
	private Integer[] paperWorkIds;
	private String[] groupIds;
	private Integer createdBy;
	private Integer updatedBy;
	private String createdDate;
	private String updatedate;
	private List<StateTO> stateList;
	private List<CityTO> cityList;
	private List<OfficeTO> serviceableBranchList;
	private String[] group3Ids;
	private String status;
	private String[] group3cityList;
	private String[] group5cityList;

	public String getPincodeId() {
		return pincodeId;
	}

	public void setPincodeId(String pincodeId) {
		this.pincodeId = pincodeId;
	}

	/**
	 * @return the groupIds
	 */
	public String[] getGroupIds() {
		return groupIds;
	}

	/**
	 * @param groupIds the groupIds to set
	 */
	public void setGroupIds(String[] groupIds) {
		this.groupIds = groupIds;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}



	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	public Integer[] getPaperWorkIds() {
		return paperWorkIds;
	}

	public void setPaperWorkIds(Integer[] paperWorkIds) {
		this.paperWorkIds = paperWorkIds;
	}

	
	/**
	 * @return the servicablebranch
	 */
	public String[] getServicablebranch() {
		return servicablebranch;
	}

	/**
	 * @param servicablebranch the servicablebranch to set
	 */
	public void setServicablebranch(String[] servicablebranch) {
		this.servicablebranch = servicablebranch;
	}

	/**
	 * @return the pincodeNo
	 */
	public String getPincodeNo() {
		return pincodeNo;
	}

	/**
	 * @param pincodeNo the pincodeNo to set
	 */
	public void setPincodeNo(String pincodeNo) {
		this.pincodeNo = pincodeNo;
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
	 * @return the createdDate
	 */
	public String getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the updatedate
	 */
	public String getUpdatedate() {
		return updatedate;
	}

	/**
	 * @param updatedate the updatedate to set
	 */
	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}

	/**
	 * @return the stateList
	 */
	public List<StateTO> getStateList() {
		return stateList;
	}

	/**
	 * @param stateList the stateList to set
	 */
	public void setStateList(List<StateTO> stateList) {
		this.stateList = stateList;
	}

	/**
	 * @return the cityList
	 */
	public List<CityTO> getCityList() {
		return cityList;
	}

	/**
	 * @param cityList the cityList to set
	 */
	public void setCityList(List<CityTO> cityList) {
		this.cityList = cityList;
	}

	/**
	 * @return the serviceableBranchList
	 */
	public List<OfficeTO> getServiceableBranchList() {
		return serviceableBranchList;
	}

	/**
	 * @param serviceableBranchList the serviceableBranchList to set
	 */
	public void setServiceableBranchList(List<OfficeTO> serviceableBranchList) {
		this.serviceableBranchList = serviceableBranchList;
	}

	/**
	 * @return the group3Ids
	 */
	public String[] getGroup3Ids() {
		return group3Ids;
	}

	/**
	 * @param group3Ids the group3Ids to set
	 */
	public void setGroup3Ids(String[] group3Ids) {
		this.group3Ids = group3Ids;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the group3cityList
	 */
	public String[] getGroup3cityList() {
		return group3cityList;
	}

	/**
	 * @param group3cityList the group3cityList to set
	 */
	public void setGroup3cityList(String[] group3cityList) {
		this.group3cityList = group3cityList;
	}

	/**
	 * @return the group5cityList
	 */
	public String[] getGroup5cityList() {
		return group5cityList;
	}

	/**
	 * @param group5cityList the group5cityList to set
	 */
	public void setGroup5cityList(String[] group5cityList) {
		this.group5cityList = group5cityList;
	}
	
	
	
}
