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

public class ColoadingRateCalProcessScheduler extends QuartzJobBean {

	private ColoadingRateCalculationService coloadingRateCalculationService ;
	private final static Logger LOGGER = LoggerFactory.getLogger(ColoadingRateCalProcessScheduler.class);
	

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		LOGGER.debug("ColoadingRateCalProcessScheduler::executeInternal::START----->");
		try {
			coloadingRateCalculationService.calculateAndSaveRateForAir();
			coloadingRateCalculationService.calculateAndSaveRateForTrain();
			coloadingRateCalculationService.calculateAndSaveRateForSurface();
			coloadingRateCalculationService.calculateAndSaveRateForVehicle();
		} catch (CGBusinessException | CGSystemException e) {
			LOGGER.error("Exception occurs in ColoadingRateCalProcessScheduler::executeInternal()::" 
					+ e);
		}catch (HttpException e) {
			LOGGER.error("ColoadingRateCalProcessScheduler::executeInternal::HttpException::" , e);
		} catch (ClassNotFoundException e) {
			LOGGER.error("ColoadingRateCalProcessScheduler::executeInternal::ClassNotFoundException::" , e);
		} catch (IOException e) {
			LOGGER.error("ColoadingRateCalProcessScheduler::executeInternal::IOException::" , e);
		} catch(Exception e) {
			LOGGER.error("ColoadingRateCalProcessScheduler::executeInternal::Exception::" , e);
		}
		LOGGER.debug("ColoadingRateCalProcessScheduler::executeInternal::END----->");

	}


	public ColoadingRateCalculationService getColoadingRateCalculationService() {
		return coloadingRateCalculationService;
	}


	public void setColoadingRateCalculationService(
			ColoadingRateCalculationService coloadingRateCalculationService) {
		this.coloadingRateCalculationService = coloadingRateCalculationService;
	}

	

}
