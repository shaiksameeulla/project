package com.ff.umc;

import java.util.HashMap;
import java.util.Map;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.organization.OfficeTO;

/**
 * @author nihsingh
 *
 */
public class UserInfoTO extends CGBaseTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1947339135554312054L;
	private UserTO userto;
	private CustomerUserTO custUserTo;
	private EmployeeUserTO empUserTo;
	private OfficeTO officeTo;
	private Map<String, String> configurableParams = new HashMap<String, String>();
	private Integer sessionTimeout;
	private String welcomeUserName;
	
	/**
	 * @desc get Userto
	 * @return userto
	 */
	public UserTO getUserto() {
		return userto;
	}

		
	/**
	 * @desc set Userto
	 * @param userto
	 */
	public void setUserto(UserTO userto) {
		this.userto = userto;
	}

	
	/**
	 * @desc get CustUserTo
	 * @return custUserTo
	 */
	public CustomerUserTO getCustUserTo() {
		return custUserTo;
	}

	/**
	 * @desc set CustUserTo
	 * @param custUserTo
	 */
	public void setCustUserTo(CustomerUserTO custUserTo) {
		this.custUserTo = custUserTo;
	}

	/**
	 * @desc get EmpUserTo
	 * @return empUserTo
	 */
	public EmployeeUserTO getEmpUserTo() {
		return empUserTo;
	}

	/**
	 * @desc set EmpUserTo
	 * @param empUserTo
	 */
	public void setEmpUserTo(EmployeeUserTO empUserTo) {
		this.empUserTo = empUserTo;
	}

	/**
	 * @desc get OfficeTo
	 * @return officeTo
	 */
	public OfficeTO getOfficeTo() {
		return officeTo;
	}

	
	/**
	 * @desc set OfficeTo
	 * @param officeTo
	 */
	public void setOfficeTo(OfficeTO officeTo) {
		this.officeTo = officeTo;
	}

	
	/**
	 * @desc get ConfigurableParams
	 * @return configurableParams
	 */
	public Map<String, String> getConfigurableParams() {
		return configurableParams;
	}

	
	/**
	 * @desc set ConfigurableParams
	 * @param configurableParams
	 */
	public void setConfigurableParams(Map<String, String> configurableParams) {
		this.configurableParams = configurableParams;
	}


	/**
	 * @return the sessionTimeout
	 */
	public Integer getSessionTimeout() {
		return sessionTimeout;
	}


	/**
	 * @param sessionTimeout the sessionTimeout to set
	 */
	public void setSessionTimeout(Integer sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}


	/**
	 * @return the welcomeUserName
	 */
	public String getWelcomeUserName() {
		return welcomeUserName;
	}


	/**
	 * @param welcomeUserName the welcomeUserName to set
	 */
	public void setWelcomeUserName(String welcomeUserName) {
		this.welcomeUserName = welcomeUserName;
	}
	
	

}
