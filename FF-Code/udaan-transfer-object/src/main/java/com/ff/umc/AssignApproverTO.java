package com.ff.umc;

/**
 * Author : Rohini Maladi
 * Date : Nov - 05 - 2012
 */
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.organization.OfficeTO;

public class AssignApproverTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 609185750067781040L;

	private Integer userId;
	private Integer userName;
	private String empName;
	private String empCode;
	private Integer regionalOfficeIds;
	private Integer cityIds;
	private Integer officeIds;
	private String mappingTo;
	private Integer appScreen;
	private String empFirstName;
	private String empLastName;

	int rowCount;
	private Integer[] applScreenAry = new Integer[rowCount];
	private Integer[] regOfficeAry = new Integer[rowCount];
	private Integer[] cityAry = new Integer[rowCount];
	private Integer[] officeAry = new Integer[rowCount];
	
	
	List<LabelValueBean> regionalOfficeList;
	List<ApplScreensTO> AssignApplScreensList;
	
	private String regionalOfficeIdsStr;
	private String cityIdsStr;
	private String officeIdsStr;
	private String screenIdsStr;
	
	
	
	public String getEmpFirstName() {
		return empFirstName;
	}
	public void setEmpFirstName(String empFirstName) {
		this.empFirstName = empFirstName;
	}
	public String getEmpLastName() {
		return empLastName;
	}
	public void setEmpLastName(String empLastName) {
		this.empLastName = empLastName;
	}
	public Integer[] getApplScreenAry() {
		return applScreenAry;
	}
	public void setApplScreenAry(Integer[] applScreenAry) {
		this.applScreenAry = applScreenAry;
	}
	public Integer[] getRegOfficeAry() {
		return regOfficeAry;
	}
	public void setRegOfficeAry(Integer[] regOfficeAry) {
		this.regOfficeAry = regOfficeAry;
	}
	public Integer[] getCityAry() {
		return cityAry;
	}
	public void setCityAry(Integer[] cityAry) {
		this.cityAry = cityAry;
	}
	public Integer[] getOfficeAry() {
		return officeAry;
	}
	public void setOfficeAry(Integer[] officeAry) {
		this.officeAry = officeAry;
	}
	public String getScreenIdsStr() {
		return screenIdsStr;
	}
	public void setScreenIdsStr(String screenIdsStr) {
		this.screenIdsStr = screenIdsStr;
	}
	public String getRegionalOfficeIdsStr() {
		return regionalOfficeIdsStr;
	}
	public void setRegionalOfficeIdsStr(String regionalOfficeIdsStr) {
		this.regionalOfficeIdsStr = regionalOfficeIdsStr;
	}
	public String getCityIdsStr() {
		return cityIdsStr;
	}
	public void setCityIdsStr(String cityIdsStr) {
		this.cityIdsStr = cityIdsStr;
	}
	public String getOfficeIdsStr() {
		return officeIdsStr;
	}
	public void setOfficeIdsStr(String officeIdsStr) {
		this.officeIdsStr = officeIdsStr;
	}
	public List<ApplScreensTO> getAssignApplScreensList() {
		return AssignApplScreensList;
	}
	public void setAssignApplScreensList(List<ApplScreensTO> assignApplScreensList) {
		AssignApplScreensList = assignApplScreensList;
	}
	public List<LabelValueBean> getRegionalOfficeList() {
		return regionalOfficeList;
	}
	public void setRegionalOfficeList(List<LabelValueBean> regionalOfficeList) {
		this.regionalOfficeList = regionalOfficeList;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getUserName() {
		return userName;
	}
	public void setUserName(Integer userName) {
		this.userName = userName;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public Integer getRegionalOfficeIds() {
		return regionalOfficeIds;
	}
	public void setRegionalOfficeIds(Integer regionalOfficeIds) {
		this.regionalOfficeIds = regionalOfficeIds;
	}
	public Integer getCityIds() {
		return cityIds;
	}
	public void setCityIds(Integer cityIds) {
		this.cityIds = cityIds;
	}
	public Integer getOfficeIds() {
		return officeIds;
	}
	public void setOfficeIds(Integer officeIds) {
		this.officeIds = officeIds;
	}
	public String getMappingTo() {
		return mappingTo;
	}
	public void setMappingTo(String mappingTo) {
		this.mappingTo = mappingTo;
	}
	public Integer getAppScreen() {
		return appScreen;
	}
	public void setAppScreen(Integer appScreen) {
		this.appScreen = appScreen;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}	
	}

