package com.ff.web.jobservice.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.jobservices.JobServicesTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;


/**
 * @author rmaladi
 *
 */
public interface JobServicesService 
{

	List<JobServicesTO> getJobServicesList(String processCode, String jobNumber,
			String fromDate, String toDate) throws CGBusinessException, CGSystemException;

	List<StockStandardTypeTO> getJobProcessList(String stdTypeName) throws CGBusinessException, CGSystemException;

	JobServicesTO getJobResponseFile(String jobNumber) throws CGBusinessException, CGSystemException;
}
