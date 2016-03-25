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
import com.ff.admin.service.PickUpCommissionCalculationService;


public class PickUpCommissionCalculationScheduler extends QuartzJobBean {

	
	
	private  PickUpCommissionCalculationService pickUpCommissionService;
	
	

	/**
	 * @param pickUpCommissionService the pickUpCommissionService to set
	 */
	public void setPickUpCommissionService(
			PickUpCommissionCalculationService pickUpCommissionService) {
		this.pickUpCommissionService = pickUpCommissionService;
	}

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AutoRequisitionScheduler.class);
	
	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		LOGGER.debug("PickUpCommissionCalculationScheduler ::executeInternal ::START ");
		try {
			generatePickUpCommission();
		}  catch (HttpException e) {
			LOGGER.error("PickUpCommissionCalculationScheduler::executeInternal::HttpException::" , e);
		} catch (ClassNotFoundException e) {
			LOGGER.error("PickUpCommissionCalculationScheduler::executeInternal::ClassNotFoundException::" , e);
		} catch (IOException e) {
			LOGGER.error("PickUpCommissionCalculationScheduler::executeInternal::IOException::" , e);
		} catch(Exception e) {
			LOGGER.error("PickUpCommissionCalculationScheduler::executeInternal::Exception::" , e);
		}
		LOGGER.debug("PickUpCommissionCalculationScheduler ::executeInternal ::END ");
	}
	
	/**
	 * Generate auto requisition.
	 *
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private void generatePickUpCommission() throws CGBusinessException,
	CGSystemException,HttpException, ClassNotFoundException, IOException {
		boolean result=false;
		LOGGER.debug("PickUpCommissionCalculationScheduler :: generatePickUpCommission :: START");
		 pickUpCommissionService.generatePickUpCount();
		
		LOGGER.debug("PickUpCommissionCalculationScheduler :: generatePickUpCommission :: END");

	}
	
	
	
}
