package com.ff.admin.billing.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.business.CustomerTO;
import com.ff.geography.CityTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.to.billing.CustModificationTO;


public interface CustModificationService {

	public CustModificationTO getConsignmentDetails(String consgNo)throws CGBusinessException,CGSystemException;
	public List<CityTO> getCitysByRegion(Integer regionId) throws CGBusinessException, CGSystemException;
	public List<CustomerTO> getCustomerDetails(Integer officeId, Integer cityId)throws CGBusinessException,CGSystemException;
	public List<ConsignmentTypeTO> getConsignmentType() throws CGBusinessException, CGSystemException;
}
