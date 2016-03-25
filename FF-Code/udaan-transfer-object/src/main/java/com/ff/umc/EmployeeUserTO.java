package com.ff.umc;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.organization.EmployeeTO;

/**
 * @author nihsingh
 *
 */
public class EmployeeUserTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3003052201569211240L;
	private Integer userId;
	private Integer empUserId;
	private String status;
	private EmployeeTO empTO;
	private UserTO userTO;
	private List<UserTO> userToList;
	int rowCount;
	private String[] userNames = new String[rowCount];
	
	private Integer employeeUserId;
	
	/**
	 * @desc get UserToList
	 * @return List<UserTO>
	 */
	public List<UserTO> getUserToList() {
		return userToList;
	}

	/**
	 * @desc set UserToList
	 * @param userToList
	 */
	public void setUserToList(List<UserTO> userToList) {
		this.userToList = userToList;
	}

	

	/**
	 * @desc get UserTO
	 * @return UserTO
	 */
	public UserTO getUserTO() {
		return userTO;
	}

	
	/**
	 * @desc set UserTO
	 * @param userTO
	 */
	public void setUserTO(UserTO userTO) {
		this.userTO = userTO;
	}

	
	/**
	 * @desc get UserId
	 * @return userId
	 */
	public Integer getUserId() {
		return userId;
	}

	
	/**
	 * @desc set UserId
	 * @param userId
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @desc get Status
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	
	/**
	 * @desc set Status
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	
	/**
	 * @desc get EmpTO
	 * @return EmployeeTO
	 */
	public EmployeeTO getEmpTO() {
		if (empTO == null)
			empTO = new EmployeeTO();
		return empTO;
	}

	
	/**
	 * @desc set EmpTO
	 * @param empTO
	 */
	public void setEmpTO(EmployeeTO empTO) {
		this.empTO = empTO;
	}

	
	/**
	 * @desc get RowCount
	 * @return rowCount
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * @desc set RowCount
	 * @param rowCount
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * @desc get UserNames
	 * @return String[] for userNames
	 */
	public String[] getUserNames() {
		return userNames;
	}

	
	/**
	 * @desc set UserNames
	 * @param userNames
	 */
	public void setUserNames(String[] userNames) {
		this.userNames = userNames;
	}

	/**
	 * @desc get EmployeeUserId
	 * @return employeeUserId
	 */
	public Integer getEmployeeUserId() {
		return employeeUserId;
	}

	
	/**
	 * @desc set EmployeeUserId
	 * @param employeeUserId
	 */
	public void setEmployeeUserId(Integer employeeUserId) {
		this.employeeUserId = employeeUserId;
	}

	public Integer getEmpUserId() {
		return empUserId;
	}

	public void setEmpUserId(Integer empUserId) {
		this.empUserId = empUserId;
	}

}
