/**
 * 
 */
package com.ff.domain.leads;

import org.apache.commons.lang.StringUtils;

import com.ff.domain.umc.EmployeeUserDO;

/**
 * @author abarudwa
 *
 */
public class EmployeeUserJoinBean implements Comparable<EmployeeUserJoinBean>{
	
	private EmployeeUserDO empUserDo;
	/*private UserDO userDO;*/
	private String userName;
	
	public EmployeeUserJoinBean() {

	}

	

	public EmployeeUserJoinBean(EmployeeUserDO empUserDo, String userName) {
		super();
		this.empUserDo = empUserDo;
		this.userName = userName;
	}



	/**
	 * @return the empUserDo
	 */
	public EmployeeUserDO getEmpUserDo() {
		return empUserDo;
	}
	/**
	 * @param empUserDo the empUserDo to set
	 */
	public void setEmpUserDo(EmployeeUserDO empUserDo) {
		this.empUserDo = empUserDo;
	}



	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}



	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}



	@Override
	public int compareTo(EmployeeUserJoinBean obj1) {
		// TODO Auto-generated method stub
		int returnVal = 0;
		if (StringUtils.isNotEmpty(this.userName)) {
			returnVal = this.userName.compareTo(obj1.userName);
		}
		return returnVal;
	}
	
}
