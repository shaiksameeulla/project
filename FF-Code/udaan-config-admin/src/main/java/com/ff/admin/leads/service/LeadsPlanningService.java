package com.ff.admin.leads.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.leads.LeadTO;
import com.ff.leads.PlanTO;
import com.ff.leads.ViewUpdateFeedbackTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;


public interface LeadsPlanningService {

	String savePlan(final ViewUpdateFeedbackTO viewUpdateFeedbackTO)throws CGSystemException,CGBusinessException;
	
	LeadTO getLeadDetails(final String leadNumber) throws CGSystemException,CGBusinessException;
	
	List<StockStandardTypeTO> getFeedbackList()throws CGSystemException, CGBusinessException;
	
	List<PlanTO> getLeadsPlanDtlsByleadNumber(final String leadNumber) throws CGSystemException,CGBusinessException;

}
