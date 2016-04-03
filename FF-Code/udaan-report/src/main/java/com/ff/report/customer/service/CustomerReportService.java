package com.ff.report.customer.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;

import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ProductTO;

public interface CustomerReportService {
	
	List<OfficeTO> getAllOffices(Integer userId, Integer cityId) throws CGBusinessException, CGSystemException;
	
	List<ProductTO> getProducts() throws CGBusinessException, CGSystemException;

}
