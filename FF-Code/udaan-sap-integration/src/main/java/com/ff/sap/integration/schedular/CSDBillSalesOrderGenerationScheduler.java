package com.ff.sap.integration.schedular;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.sd.service.SDSAPIntegrationService;
import com.ff.sap.integration.to.SAPBillingConsgSummaryTO;

/**
 * @author CBHURE
 *
 */
public class CSDBillSalesOrderGenerationScheduler  extends QuartzJobBean {

	Logger logger = Logger.getLogger(SAPExpenseScheduler.class);
	public SDSAPIntegrationService sdSAPIntegrationServiceForUpdateSalesOrder;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.debug("BillCreationScheduler - for bill generation scheduler :: CSDBillSalesOrderGenerationScheduler :: executeInternal :: start =======>");
		
		SAPBillingConsgSummaryTO sapBillConsgSummaryTO = new SAPBillingConsgSummaryTO();
		sapBillConsgSummaryTO.setSapStatus(SAPIntegrationConstants.SAP_STATUS);
		sapBillConsgSummaryTO.setMaxCheck(SAPIntegrationConstants.MAX_CHECK);
		try {
			sdSAPIntegrationServiceForUpdateSalesOrder.updateBillSalesOrderNumber();
		}catch (HttpException e) {
			logger.error("CSDBillSalesOrderGenerationScheduler::executeInternal::HttpException::" ,e);
		} catch (ClassNotFoundException e) {
			logger.error("CSDBillSalesOrderGenerationScheduler::executeInternal::ClassNotFoundException::",e);
		} catch (IOException e) {
			logger.error("CSDBillSalesOrderGenerationScheduler::executeInternal::IOException::" ,e);
		} catch(Exception e) {
			logger.error("CSDBillSalesOrderGenerationScheduler::executeInternal::Exception::" , e);
		}
		logger.debug("BillCreationScheduler - for bill generation scheduler :: CSDBillSalesOrderGenerationScheduler :: executeInternal :: End =======>");
	}

	/**
	 * @param sdSAPIntegrationServiceForUpdateSalesOrder the sdSAPIntegrationServiceForUpdateSalesOrder to set
	 */
	public void setSdSAPIntegrationServiceForUpdateSalesOrder(
			SDSAPIntegrationService sdSAPIntegrationServiceForUpdateSalesOrder) {
		this.sdSAPIntegrationServiceForUpdateSalesOrder = sdSAPIntegrationServiceForUpdateSalesOrder;
	}
	
	
	
}
