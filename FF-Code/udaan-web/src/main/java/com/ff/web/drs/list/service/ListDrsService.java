/**
 * 
 */
package com.ff.web.drs.list.service;

import java.util.Map;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.to.drs.AbstractDeliveryTO;
import com.ff.to.drs.list.ListDrsHeaderTO;

/**
 * @author mohammes
 *
 */
public interface ListDrsService {

	/**
	 * Gets the all delivery employees.
	 *
	 * @param delvTo the delv to
	 * @return the all delivery employees
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Map<Integer, String> getAllDeliveryEmployees(AbstractDeliveryTO delvTo)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the all drs by office and employee.
	 *
	 * @param drsTo the drs to
	 * @return the all drs by office and employee
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	ListDrsHeaderTO getAllDrsByOfficeAndEmployee(ListDrsHeaderTO drsTo)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the drs party details by date.
	 *
	 * @param delvTo the delv to
	 * @return the drs party details by date
	 * @throws CGBusinessException the CG business exception
	 * @throws CGSystemException the CG system exception
	 */
	Map<String, String> getDrsPartyDetailsByDate(AbstractDeliveryTO delvTo)
			throws CGBusinessException, CGSystemException;

}
