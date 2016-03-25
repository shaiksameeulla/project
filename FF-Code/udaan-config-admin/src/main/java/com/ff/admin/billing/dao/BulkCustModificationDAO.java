package com.ff.admin.billing.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.geography.CityDO;

public interface BulkCustModificationDAO {

	List<CityDO> getCitysByStateId(Integer stateId) throws CGSystemException;
}
