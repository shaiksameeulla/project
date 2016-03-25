package com.ff.admin.scheduler;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import com.ff.rate.configuration.ratecontract.service.RateContractService;

public class ContractExpiryEmailScheduler extends QuartzJobBean {

	private RateContractService rateContractService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ContractExpiryEmailScheduler.class);
	/**
	 * @return the rateContractService
	 */
	public RateContractService getRateContractService() {
		return rateContractService;
	}

	/**
	 * @param rateContractService
	 *            the rateContractService to set
	 */
	public void setRateContractService(RateContractService rateContractService) {
		this.rateContractService = rateContractService;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		LOGGER.debug("ContractExpiryEmailScheduler ::executeInternal ::START ");
		try {
			rateContractService.contractExpiryEmailTrigger();
		} catch (HttpException e) {
			LOGGER.error("ContractExpiryEmailScheduler::executeInternal::HttpException::" , e);
		} catch (ClassNotFoundException e) {
			LOGGER.error("ContractExpiryEmailScheduler::executeInternal::ClassNotFoundException::" , e);
		} catch (IOException e) {
			LOGGER.error("ContractExpiryEmailScheduler::executeInternal::IOException::" , e);
		} catch(Exception e) {
			LOGGER.error("ContractExpiryEmailScheduler::executeInternal::Exception::" , e);
		}
		LOGGER.debug("ContractExpiryEmailScheduler ::executeInternal ::END ");
	
	}

}
