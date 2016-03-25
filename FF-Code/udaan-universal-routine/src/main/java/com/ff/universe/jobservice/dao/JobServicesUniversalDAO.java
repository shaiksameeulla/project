package com.ff.universe.jobservice.dao;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.jobservice.JobServicesDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;

public interface JobServicesUniversalDAO {

	boolean saveOrUpdateJobService(JobServicesDO jobServicesDO) throws CGSystemException;

	List<JobServicesDO> searchJobService(String processCode, String jobNumber,
			Date fromDate, Date toDate) throws CGSystemException;

	List<StockStandardTypeDO> getJobProcessList(String stdTypeName) throws CGSystemException;

	JobServicesDO getJobServiceDetails(String jobNumber) throws CGSystemException;

}
