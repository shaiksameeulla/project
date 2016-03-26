package com.ff.web.jobservice.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.jobservices.JobServicesTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.universe.jobservice.service.JobServicesUniversalService;

/**
 * @author rmaladi
 * 
 */

public class JobServicesServiceImpl implements JobServicesService
{
	private final static Logger LOGGER = LoggerFactory
			.getLogger(JobServicesServiceImpl.class);
	
	private JobServicesUniversalService jobServicesUniversalService;

	/**
	 * @return the jobServicesUniversalService
	 */
	public JobServicesUniversalService getJobServicesUniversalService() {
		return jobServicesUniversalService;
	}

	/**
	 * @param jobServicesUniversalService the jobServicesUniversalService to set
	 */
	public void setJobServicesUniversalService(
			JobServicesUniversalService jobServicesUniversalService) {
		this.jobServicesUniversalService = jobServicesUniversalService;
	}

	@Override
	public List<JobServicesTO> getJobServicesList(String processCode,
			String jobNumber, String fromDate, String toDate)
			throws CGBusinessException, CGSystemException {
		
		return jobServicesUniversalService.searchJobService(processCode, jobNumber, fromDate, toDate);
	}

	@Override
	public List<StockStandardTypeTO> getJobProcessList(String stdTypeName)
			throws CGBusinessException, CGSystemException {
		return jobServicesUniversalService.getJobProcessList(stdTypeName);
	}
	
	@Override
	public JobServicesTO getJobResponseFile(String jobNumber)
			throws CGBusinessException, CGSystemException {
		return jobServicesUniversalService.getJobResponseFile(jobNumber);
	}		
}

