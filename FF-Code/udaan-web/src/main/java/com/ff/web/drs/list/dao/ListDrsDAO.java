/**
 * 
 */
package com.ff.web.drs.list.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.organization.EmployeeDO;
import com.ff.to.drs.AbstractDeliveryTO;
import com.ff.to.drs.list.ListDrsHeaderTO;

/**
 * @author mohammes
 *
 */
public interface ListDrsDAO {

	/**
	 * Gets the all delivery employees.
	 *
	 * @param delvTo the delv to
	 * @return the all delivery employees
	 * @throws CGSystemException the cG system exception
	 */
	List<EmployeeDO> getAllDeliveryEmployees(AbstractDeliveryTO delvTo)
			throws CGSystemException;

	/**
	 * Gets the all drs by office and employee.
	 *
	 * @param drsTo the drs to
	 * @return the all drs by office and employee
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<String> getAllDrsByOfficeAndEmployee(ListDrsHeaderTO drsTo)
			throws CGBusinessException, CGSystemException;

	List<?> getDrsForDtlsByDate(AbstractDeliveryTO delvTo)
			throws CGSystemException;

}
