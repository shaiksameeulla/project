package com.ff.admin.heldup.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.to.heldup.HeldUpTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.tracking.ProcessTO;

/**
 * The Interface HeldUpService.
 *
 * @author narmdr
 */
public interface HeldUpService {

	/**
	 * Gets the employees of office.
	 *
	 * @param officeTO the office to
	 * @return the employees of office
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<EmployeeTO> getEmployeesOfOffice(final OfficeTO officeTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the reasons by reason type.
	 *
	 * @param reasonTO the reason to
	 * @return the reasons by reason type
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<ReasonTO> getReasonsByReasonType(final ReasonTO reasonTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the std types by type name.
	 *
	 * @param typeName the type name
	 * @return the std types by type name
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<StockStandardTypeTO> getStdTypesByTypeName(final String typeName)
			throws CGBusinessException, CGSystemException;

	/**
	 * Save or update held up.
	 *
	 * @param heldUpTO the held up to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	void saveOrUpdateHeldUp(final HeldUpTO heldUpTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the process.
	 *
	 * @param processTO the process to
	 * @return the process
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	ProcessTO getProcess(final ProcessTO processTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Find held up number.
	 *
	 * @param heldUpTO the held up to
	 * @return the held up to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	HeldUpTO findHeldUpNumber(final HeldUpTO heldUpTO) throws CGBusinessException,
			CGSystemException;

	String createProcessNumber(final ProcessTO processTO, OfficeTO officeTO)
			throws CGBusinessException, CGSystemException;
}
