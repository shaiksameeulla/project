package com.ff.web.pickup.service;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.pickup.PickupRunsheetTO;
import com.ff.web.pickup.action.GeneratePickUpPage;

/**
 * The Interface GenerateRunsheetService.
 */
public interface GenerateRunsheetService {
	public List<EmployeeTO> getBranchPickupEmployees(Integer loginOfficeId,
			List<Integer> createdForOfficeIds) throws CGBusinessException,
			CGSystemException;

	public List<OfficeTO> getBranchesUnderHUB(Integer loginOfficeId)
			throws CGBusinessException, CGSystemException;

	public PickupRunsheetTO getAssignedRunsheets(PickupRunsheetTO pkupRunsheetTO)
			throws CGBusinessException, CGSystemException;

	public PickupRunsheetTO savePickupRunsheet(
			PickupRunsheetTO pkupRunsheetTO) throws CGBusinessException,
			CGSystemException;

	public List<GeneratePickUpPage> preparePrint(
			List<List<PickupRunsheetTO>> runsheetList)
			throws CGSystemException, CGBusinessException;

}
