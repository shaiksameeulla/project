package com.ff.rate.configuration.ratebenchmarkdiscount.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.ratemanagement.operations.rateBenchmarkDiscount.RegionRateBenchMarkDiscountDO;
import com.ff.geography.RegionTO;
import com.ff.organization.EmployeeTO;
import com.ff.to.ratemanagement.masters.RateIndustryCategoryTO;
import com.ff.to.ratemanagement.operations.rateBenchmarkDiscount.RegionRateBenchMarkDiscountTO;

/**
 * @author preegupt
 *
 */
public interface RegionRateBenchMarkDiscountService {

	/**@Desc:getting the region list 
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<RegionTO> getAllRegions() throws CGBusinessException,
			CGSystemException;

	/**@Desc:saving & submitting the details 
	 * @param rbmdTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	String saveOrUpdateRateBenchMarkDiscount(
			RegionRateBenchMarkDiscountTO rbmdTO) throws CGBusinessException,
			CGSystemException;

	/**@Desc:getting the Rate Industry Category List 
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<RateIndustryCategoryTO> getRateIndustryCategoryList()
			throws CGBusinessException, CGSystemException;

	/**@Desc:getting the Employee Details
	 * @param empCode
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	EmployeeTO getEmployeeDetails(String empCode) throws CGBusinessException,
			CGSystemException;

	/**@Desc:getting the  Discount Details
	 * @param rbmdTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<RegionRateBenchMarkDiscountTO> getDiscountDetails(
			RegionRateBenchMarkDiscountTO rbmdTO) throws CGBusinessException,
			CGSystemException;

	RegionRateBenchMarkDiscountDO getRateBenchMarkDiscountServiceByRegion(
			Integer regionId) throws CGBusinessException, CGSystemException;
	
	List<RegionRateBenchMarkDiscountDO> checkEmpIdRegionalApprover(Integer empId)
			throws CGBusinessException, CGSystemException;
}
