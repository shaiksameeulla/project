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
import com.ff.admin.pndcommission.service.DeliveryCommissionCalculationService;

/**
 * @author hkansagr
 * 
 */
public class DeliveryCommissionCalculationScheduler extends QuartzJobBean {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DeliveryCommissionCalculationScheduler.class);

	/** The deliveryCommissionCalcService. */
	private DeliveryCommissionCalculationService deliveryCommissionCalcService;

	/**
	 * @param deliveryCommissionCalcService
	 *            the deliveryCommissionCalcService to set
	 */
	public void setDeliveryCommissionCalcService(
			DeliveryCommissionCalculationService deliveryCommissionCalcService) {
		this.deliveryCommissionCalcService = deliveryCommissionCalcService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org
	 * .quartz.JobExecutionContext)
	 */
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		LOGGER.trace("DeliveryCommissionCalculationScheduler :: executeInternal() :: START ");
		try {
			generateDlvCommission();
			dataCopyToSAPStagingTable();
		}catch (HttpException e) {
			LOGGER.error("DeliveryCommissionCalculationScheduler::executeInternal::HttpException::" , e);
		} catch (ClassNotFoundException e) {
			LOGGER.error("DeliveryCommissionCalculationScheduler::executeInternal::ClassNotFoundException::", e);
		} catch (IOException e) {
			LOGGER.error("DeliveryCommissionCalculationScheduler::executeInternal::IOException::" , e);
		} catch(Exception e) {
			LOGGER.error("DeliveryCommissionCalculationScheduler::executeInternal::Exception::" , e);
		}
		LOGGER.trace("DeliveryCommissionCalculationScheduler :: executeInternal() :: END ");
	}

	/**
	 * To generate or execute delivery commission calculation.
	 * 
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void generateDlvCommission() throws CGBusinessException,
			CGSystemException,HttpException, ClassNotFoundException, IOException {
		LOGGER.trace("DeliveryCommissionCalculationScheduler :: generateDlvCommission() :: START ");
		deliveryCommissionCalcService.generateDlvCommission();
		LOGGER.trace("DeliveryCommissionCalculationScheduler :: generateDlvCommission() :: END ");
	}

	/**
	 * To copy the data from delivery commission table to SAP staging table.
	 * 
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void dataCopyToSAPStagingTable() throws CGBusinessException,
			CGSystemException,HttpException, ClassNotFoundException, IOException {
		LOGGER.trace("DeliveryCommissionCalculationScheduler :: dataCopyToSAPStagingTable() :: START ");
		deliveryCommissionCalcService.dataCopyToSAPStagingTable();
		LOGGER.trace("DeliveryCommissionCalculationScheduler :: dataCopyToSAPStagingTable() :: END ");
	}

}
