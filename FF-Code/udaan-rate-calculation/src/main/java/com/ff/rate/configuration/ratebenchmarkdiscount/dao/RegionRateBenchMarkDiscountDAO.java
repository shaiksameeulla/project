package com.ff.rate.configuration.ratebenchmarkdiscount.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.ratemanagement.masters.RateIndustryCategoryDO;
import com.ff.domain.ratemanagement.operations.rateBenchmarkDiscount.RegionRateBenchMarkDiscountDO;
import com.ff.to.ratemanagement.operations.rateBenchmarkDiscount.RegionRateBenchMarkDiscountTO;

/**
 * @author preegupt
 *
 */
public interface RegionRateBenchMarkDiscountDAO {

	/**@Desc:saving & submitting the details 
	 * @param discountDO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	boolean saveOrUpdateRateBenchMark(
			List<RegionRateBenchMarkDiscountDO> discountDO)
			throws CGBusinessException, CGSystemException;

	/**@Desc:getting the Rate Industry Category List 
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<RateIndustryCategoryDO> getRateIndustryCategoryList()
			throws CGBusinessException, CGSystemException;

	/**@Desc:getting the  Discount Details
	 * @param rbmdTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<RegionRateBenchMarkDiscountDO> getDiscountDetails(
			RegionRateBenchMarkDiscountTO rbmdTO) throws CGBusinessException,
			CGSystemException;

	List<RegionRateBenchMarkDiscountDO> getDiscountDetailsbyRegionId(
			Integer regionId) throws CGSystemException;

	List<RegionRateBenchMarkDiscountDO> checkEmpIdRegionalApprover(Integer empId)
			throws CGSystemException;

	
}
