package com.ff.admin.notification.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.notification.BulkSmsOnDemandTO;
import com.ff.organization.OfficeTO;

public interface BulkSmsOnDemandService {

	public List<RegionTO> getAllRegions() throws CGBusinessException, CGSystemException;

	public List<CityTO> getCitiesByRegion(Integer regionId) throws CGBusinessException, CGSystemException;

	public List<OfficeTO> getAllOfficesByCity(Integer cityId) throws CGBusinessException, CGSystemException;

	public void getConsignmentDetailsByStatus(BulkSmsOnDemandTO smsOnDemandTO) throws CGBusinessException, CGSystemException;

	public String sendBulkSMS(BulkSmsOnDemandTO bulkSmsOnDemandTO) throws CGBusinessException, CGSystemException;

}
