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
import com.ff.admin.coloading.service.ColoadingRateCalculationService;

public class ColoadingMonthlyRateCalProcessScheduler extends QuartzJobBean {

	private ColoadingRateCalculationService coloadingRateCalculationService ;
	private final static Logger LOGGER = LoggerFactory.getLogger(ColoadingMonthlyRateCalProcessScheduler.class);
	

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		LOGGER.debug("ColoadingMonthlyRateCalProcessScheduler::executeInternal::START----->");
		try {
			coloadingRateCalculationService.calculateAndSaveRateForVehicleMonthly();
		} catch (CGBusinessException | CGSystemException e) {
			LOGGER.error("Exception occurs in ColoadingMonthlyRateCalProcessScheduler::executeInternal()::" 
					+ e);
		}catch (HttpException e) {
			LOGGER.error("ColoadingMonthlyRateCalProcessScheduler::executeInternal::HttpException::" , e);
		} catch (ClassNotFoundException e) {
			LOGGER.error("ColoadingMonthlyRateCalProcessScheduler::executeInternal::ClassNotFoundException::" , e);
		} catch (IOException e) {
			LOGGER.error("ColoadingMonthlyRateCalProcessScheduler::executeInternal::IOException::" , e);
		} catch(Exception e) {
			LOGGER.error("ColoadingMonthlyRateCalProcessScheduler::executeInternal::Exception::" , e);
		}
		LOGGER.debug("ColoadingMonthlyRateCalProcessScheduler::executeInternal::END----->");

	}


	public ColoadingRateCalculationService getColoadingRateCalculationService() {
		return coloadingRateCalculationService;
	}


	public void setColoadingRateCalculationService(
			ColoadingRateCalculationService coloadingRateCalculationService) {
		this.coloadingRateCalculationService = coloadingRateCalculationService;
	}

	

}
