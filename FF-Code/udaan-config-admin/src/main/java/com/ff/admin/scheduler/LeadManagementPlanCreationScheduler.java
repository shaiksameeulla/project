package com.ff.admin.scheduler;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.admin.leads.service.LeadsCommonService;


/**
 * @author sdalli
 *
 */
public class LeadManagementPlanCreationScheduler extends QuartzJobBean {
	
	@SuppressWarnings("unused")
	private final static Logger LOGGER = LoggerFactory
			.getLogger(LeadManagementPlanCreationScheduler.class);
	
	private LeadsCommonService leadsCommonService;

	public void setLeadsCommonService(LeadsCommonService leadsCommonService) {
		this.leadsCommonService = leadsCommonService;
	}




	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		try {
			leadsCommonService.leadManagementPlanCreation();
		} catch (CGBusinessException | CGSystemException e) {
			// TODO Auto-generated catch block	
			LOGGER.error("Exception occurs in LeadManagementPlanCreationScheduler::executeInternal()::" 
					+ e);
		}catch (HttpException e) {
			LOGGER.error("LeadManagementPlanCreationScheduler::executeInternal::HttpException::" , e);
		} catch (ClassNotFoundException e) {
			LOGGER.error("LeadManagementPlanCreationScheduler::executeInternal::ClassNotFoundException::" , e);
		} catch (IOException e) {
			LOGGER.error("LeadManagementPlanCreationScheduler::executeInternal::IOException::" , e);
		} catch(Exception e) {
			LOGGER.error("LeadManagementPlanCreationScheduler::executeInternal::Exception::" , e);
		}
	}

}
