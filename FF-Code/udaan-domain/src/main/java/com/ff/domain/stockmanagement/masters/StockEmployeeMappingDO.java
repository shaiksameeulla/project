/**
 * 
 */
package com.ff.domain.stockmanagement.masters;


/**
 * The Class StockEmployeeMappingDO.
 *
 * @author mohammes
 */
public class StockEmployeeMappingDO extends StockBaseDO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7704036666872616966L;
	//private EmployeeDO employeeDO;

	/** The employee id. */
	private Integer employeeId;

	/**
	 * Gets the employee id.
	 *
	 * @return the employee id
	 */
	public Integer getEmployeeId() {
		return employeeId;
	}

	/**
	 * Sets the employee id.
	 *
	 * @param employeeId the new employee id
	 */
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
}
