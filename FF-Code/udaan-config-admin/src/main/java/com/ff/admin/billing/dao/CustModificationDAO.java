package com.ff.admin.billing.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.business.CustomerBillingDO;
import com.ff.domain.geography.RegionDO;

public interface CustModificationDAO {
	public List<CustomerBillingDO> getCustomerDetails(Integer cityId, Integer officeId)throws CGSystemException;
	public List<RegionDO> getRegionByRegionCode(String regionCode)throws CGSystemException;
}
