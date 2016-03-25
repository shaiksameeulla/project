/**
 * 
 */
package com.ff.admin.scheduler;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.ff.admin.stockmanagement.common.service.StockScheduledService;
import com.ff.domain.stockmanagement.operations.issue.StockIssuePaymentDetailsDO;

/**
 * @author mohammes
 *
 */
public class StockExpenseIntegratorScheduler extends QuartzJobBean{

	private static final Logger LOGGER = LoggerFactory
			.getLogger(StockExpenseIntegratorScheduler.class);
	
	private transient StockScheduledService stockScheduledService;
	/**
	 * @return the stockScheduledService
	 */
	public StockScheduledService getStockScheduledService() {
		return stockScheduledService;
	}
	/**
	 * @param stockScheduledService the stockScheduledService to set
	 */
	public void setStockScheduledService(StockScheduledService stockScheduledService) {
		this.stockScheduledService = stockScheduledService;
	}
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		LOGGER.info("StockExpenseIntegratorScheduler ::Scheduler ::START ");
		try {
			stockExpenseIntegrator();
		} catch (HttpException e) {
			LOGGER.error("StockExpenseIntegratorScheduler::executeInternal::HttpException::" ,e);
		} catch (ClassNotFoundException e) {
			LOGGER.error("StockExpenseIntegratorScheduler::executeInternal::ClassNotFoundException::"  ,e);
		} catch (IOException e) {
			LOGGER.error("StockExpenseIntegratorScheduler::executeInternal::IOException::"  ,e);
		} catch(Exception e) {
			LOGGER.error("StockExpenseIntegratorScheduler::executeInternal::Exception::" , e);
		}
		LOGGER.info("StockExpenseIntegratorScheduler ::Scheduler ::END ");
		
	}
	/**
	 * stockExpenseIntegrator
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void stockExpenseIntegrator() throws CGBusinessException, CGSystemException,HttpException, ClassNotFoundException, IOException{
		List<StockIssuePaymentDetailsDO> paymentDtls =stockScheduledService.getStockPaymentDetails();
		if(!CGCollectionUtils.isEmpty(paymentDtls)){
			Map<String,Integer> paymentTypeMap=stockScheduledService.getPaymentModeTypeForCollection();
			for(StockIssuePaymentDetailsDO paymentDO :paymentDtls){
				try {
					stockScheduledService.createExpenseFromStockPayment(paymentTypeMap, paymentDO);
				} catch (Exception e) {
					LOGGER.error("StockExpenseIntegratorScheduler ::Scheduler ::Exception:: ",e);
				}
			}
		}
	}
	

}
