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
import com.ff.admin.billing.service.ReBillingService;

public class ReBillingScheduler extends QuartzJobBean {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ReBillingScheduler.class);
	private ReBillingService reBillingService;
	
	

	public void setReBillingService(ReBillingService reBillingService) {
		this.reBillingService = reBillingService;
	}



	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		LOGGER.debug("ReBillingScheduler::executeInternal::START----->");
		try{
			
		     reBillingService.getRebillingDetails();
		
		}catch(CGBusinessException | CGSystemException e){
			LOGGER.error("Exception occurs in ReBillingScheduler::executeInternal()::" 
					+ e);
		}catch (HttpException e) {
			LOGGER.error("ReBillingScheduler::executeInternal::HttpException::" , e);
		} catch (ClassNotFoundException e) {
			LOGGER.error("ReBillingScheduler::executeInternal::ClassNotFoundException::" , e);
		} catch (IOException e) {
			LOGGER.error("ReBillingScheduler::executeInternal::IOException::" , e);
		} catch(Exception e) {
			LOGGER.error("ReBillingScheduler::executeInternal::Exception::" , e);
		}
        
		LOGGER.debug("ReBillingScheduler::executeInternal::END----->");
	}

}
