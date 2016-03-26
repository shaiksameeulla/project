package com.ff.sap.integration.schedular;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.billing.SAPBillSalesOrderStagingDO;
import com.ff.sap.integration.dao.SAPIntegrationDAO;
import com.ff.sap.integration.sd.salesordernumber.bs.SOUpdateSAPIntegrationService;
import com.ff.sap.integration.to.SAPSalesOrderTO;

public class StagingBillSalesOrderProcessingScheduler extends QuartzJobBean {

	Logger LOGGER = Logger.getLogger(StagingBillSalesOrderProcessingScheduler.class);

	private SOUpdateSAPIntegrationService salesOrderNumberSAPIntegrationService;
	private SAPIntegrationDAO sapIntegrationDAO; 

	public void setSalesOrderNumberSAPIntegrationService(
			SOUpdateSAPIntegrationService salesOrderNumberSAPIntegrationService) {
		this.salesOrderNumberSAPIntegrationService = salesOrderNumberSAPIntegrationService;
	}

	public void setSapIntegrationDAO(SAPIntegrationDAO sapIntegrationDAO) {
		this.sapIntegrationDAO = sapIntegrationDAO;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		LOGGER.warn("StagingBillSalesOrderProcessingScheduler :: executeInternal:: START");
		try {
			List<SAPBillSalesOrderStagingDO> sapBillSalesOrderStagingDoList = null;
			do {
				sapBillSalesOrderStagingDoList = sapIntegrationDAO.getSalesOrderDataFromStaging();
				
				if (StringUtil.isEmptyColletion(sapBillSalesOrderStagingDoList)){
					break;
				}
					
				List<SAPSalesOrderTO> sapSalesOrderToList = new ArrayList(sapBillSalesOrderStagingDoList.size());
				Iterator<SAPBillSalesOrderStagingDO> itr = sapBillSalesOrderStagingDoList.iterator();
				while (itr.hasNext()) {
					SAPBillSalesOrderStagingDO sapBillSalesOrderStagingDo = (SAPBillSalesOrderStagingDO) itr.next();
					try {
						SAPSalesOrderTO sapSalesOrderTo = new SAPSalesOrderTO();
						/*PropertyUtils.copyProperties(destinationEntity, sourceEntity);*/ 
						PropertyUtils.copyProperties(sapSalesOrderTo, sapBillSalesOrderStagingDo);
						sapSalesOrderToList.add(sapSalesOrderTo);
					} 
					catch (Exception obj) {
						LOGGER.error("StagingBillSalesOrderProcessingScheduler :: executeInternal::", obj);
					}
				}

				//Save Or Update [code from the previous scheduler]
				salesOrderNumberSAPIntegrationService.saveOrUpdateBillNumberStatus(sapSalesOrderToList);
				
				for (SAPBillSalesOrderStagingDO sAPBillSalesOrderStagingDO : sapBillSalesOrderStagingDoList) {
					sAPBillSalesOrderStagingDO.setSapInbound("C");
				}
				sapIntegrationDAO.saveOrUpdateSalesOrderInStaging(sapBillSalesOrderStagingDoList);
			} while (!StringUtil.isEmptyColletion(sapBillSalesOrderStagingDoList));
		}
		catch (Exception e) {
			LOGGER.error("StagingBillSalesOrderProcessingScheduler :: executeInternal::", e);
		}
		LOGGER.warn("StagingBillSalesOrderProcessingScheduler :: executeInternal:: END");
	}
}
