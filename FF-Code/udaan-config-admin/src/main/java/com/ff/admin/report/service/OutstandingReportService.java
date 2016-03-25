package com.ff.admin.report.service;

import java.util.List;

import com.ff.domain.mec.SAPReportDO;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.admin.report.to.OutstandingReportTO;
import com.ff.umc.UserInfoTO;

public interface OutstandingReportService {
	
	public Boolean saveReportData(OutstandingReportTO outstandingReportTO, UserInfoTO userInfoTO)
			throws CGSystemException, CGBusinessException ;
	
	
	public List<SAPReportDO> getReportList() throws CGSystemException;

}
