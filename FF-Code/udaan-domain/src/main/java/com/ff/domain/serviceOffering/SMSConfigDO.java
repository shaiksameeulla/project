/**
 * 
 */
package com.ff.domain.serviceOffering;

import com.capgemini.lbs.framework.domain.CGMasterDO;

/**
 * The Class SMSConfigDO.
 *
 * @author sdalli
 */
public class SMSConfigDO extends CGMasterDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8501090960839492134L;
	private Integer smsId;
	private String shiftStartTime;
	private String shiftEndTime;
	private String moduleName;
	private Integer mobileNumber;
	private String employeeName;
	private String city;
	public Integer getSmsId() {
		return smsId;
	}
	public void setSmsId(Integer smsId) {
		this.smsId = smsId;
	}
	public String getShiftStartTime() {
		return shiftStartTime;
	}
	public void setShiftStartTime(String shiftStartTime) {
		this.shiftStartTime = shiftStartTime;
	}
	public String getShiftEndTime() {
		return shiftEndTime;
	}
	public void setShiftEndTime(String shiftEndTime) {
		this.shiftEndTime = shiftEndTime;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public Integer getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(Integer mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	
}

