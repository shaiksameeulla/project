package com.ff.to.mec.expense;

import com.capgemini.lbs.framework.utils.StringUtil;

/**
 * @author hkansagr
 */

public class EmployeeExpenseDetailTO extends ExpenseTO implements Comparable<EmployeeExpenseDetailTO>{
	
	private static final long serialVersionUID = 1L;

	/** The employeeId. */
	private Integer employeeId;
	
	/** The employeeName. */
	private String employeeName;
		
	
	/**
	 * @return the employeeId
	 */
	public Integer getEmployeeId() {
		return employeeId;
	}
	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	/**
	 * @return the employeeName
	 */
	public String getEmployeeName() {
		return employeeName;
	}
	/**
	 * @param employeeName the employeeName to set
	 */
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	@Override
	public int compareTo(EmployeeExpenseDetailTO obj) {
		int result = 0;
		if(!StringUtil.isEmptyInteger(position) 
				&& !StringUtil.isEmptyInteger(obj.getPosition())){
			result = this.position.compareTo(obj.position);
		} else if(!StringUtil.isEmptyLong(expenseEntriesId) 
				&& !StringUtil.isEmptyLong(obj.getExpenseEntriesId())){
			result = this.expenseEntriesId.compareTo(obj.expenseEntriesId) ;
		}
		return result;
	}

}
