package com.ff.admin.scheduler;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.universe.mec.service.MECUniversalService;

public class ExpenseCollectionScheduler extends QuartzJobBean {

	/** The LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExpenseCollectionScheduler.class);

	/** The MEC universal service. */
	private transient MECUniversalService mecUniversalService;

	/**
	 * @param mecUniversalService
	 *            the mecUniversalService to set
	 */
	public void setMecUniversalService(MECUniversalService mecUniversalService) {
		this.mecUniversalService = mecUniversalService;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0) {
		LOGGER.trace("ExpenseCollectionScheduler :: Scheduler :: START");
		try {
			expenseCollectionIntegrator();
		} catch (HttpException e) {
			LOGGER.error("ExpenseCollectionScheduler::executeInternal::HttpException::" , e);
		} catch (ClassNotFoundException e) {
			LOGGER.error("ExpenseCollectionScheduler::executeInternal::ClassNotFoundException::" , e);
		} catch (IOException e) {
			LOGGER.error("ExpenseCollectionScheduler::executeInternal::IOException::" , e);
		} catch(Exception e) {
			LOGGER.error("ExpenseCollectionScheduler::executeInternal::Exception::" , e);
		}
		LOGGER.trace("ExpenseCollectionScheduler :: Scheduler :: END");
	}

	/**
	 * The expense collection integrator
	 * 
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void expenseCollectionIntegrator() throws CGBusinessException,
			CGSystemException,HttpException, ClassNotFoundException, IOException {
		LOGGER.trace("ExpenseCollectionScheduler :: expenseCollectionIntegrator() :: START");
		List<DeliveryDetailsDO> deliveryDtlsDoList = mecUniversalService
				.getDrsDtlsForExpenseTypeCollectoin();
		if (!CGCollectionUtils.isEmpty(deliveryDtlsDoList)) {
			Map<String, Integer> paymentTypeMap = mecUniversalService
					.getPaymentModeTypeForCollection();
			/**
			 * To validate expense type consignment(s) details from delivery
			 * details.
			 */
			mecUniversalService.validateExpenseConsgDtls(deliveryDtlsDoList,
					paymentTypeMap);
		}
		LOGGER.trace("ExpenseCollectionScheduler :: expenseCollectionIntegrator() :: END");
	}

}
