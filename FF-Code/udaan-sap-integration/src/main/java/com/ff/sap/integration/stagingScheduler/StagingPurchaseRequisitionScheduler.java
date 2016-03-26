package com.ff.sap.integration.stagingScheduler;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.stockmanagement.operations.requisition.SAPStockRequisitionDO;
import com.ff.sap.integration.material.stock.StockSAPIntegrationService;
import com.ff.sap.integration.to.SAPStockRequisitionTO;

/**
 * @author CBHURE
 *
 */
public class StagingPurchaseRequisitionScheduler  extends QuartzJobBean {

	Logger logger = Logger.getLogger(StagingPurchaseRequisitionScheduler.class);
	public StockSAPIntegrationService stockSAPIntService;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)throws JobExecutionException {
		logger.debug("StagingPurchaseRequisitionScheduler :: executeInternal :: start=======>");
		SAPStockRequisitionTO sapPRTO = new SAPStockRequisitionTO();
		sapPRTO.setSapStatus("N");
		List<SAPStockRequisitionDO> sapStockReqDos = null;
		try {
		//	sapStockReqDos = stockSAPIntService.findStkRequisitionFromStaging(sapPRTO);
			
//			stockSAPIntService.sendPRdataToSAPPI(sapStockReqDos);
			
			logger.debug("Staging PR SCHEDULER LIST SIZE ------>"+sapStockReqDos.size());
			
			if(!StringUtil.isEmptyColletion(sapStockReqDos)){
				
			}
		} catch (Exception e) {
			logger.error("StagingPurchaseRequisitionScheduler :: executeInternal :: error::",e);
		}
		logger.debug("StagingPurchaseRequisitionScheduler :: executeInternal :: end=======>");
	}

	/**
	 * @param stockSAPIntService the stockSAPIntService to set
	 */
	public void setStockSAPIntService(StockSAPIntegrationService stockSAPIntService) {
		this.stockSAPIntService = stockSAPIntService;
	}
	
}
