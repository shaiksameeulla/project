package com.ff.report.customer.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.organization.OfficeTO;


public interface CustomerReportServiceDAO {

	
	public List<ProductDO> getAllProducts() throws CGBusinessException,
	CGSystemException;

	public List<OfficeTO> getAllOffices(Integer userId, Integer cityId) throws CGBusinessException,
	CGSystemException;
	
}
