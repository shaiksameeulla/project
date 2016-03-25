/**
 * 
 */
package com.ff.admin.scheduler;

import java.io.IOException;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.ff.admin.stockmanagement.autorequisition.service.AutoRequisitionService;

/**
 * @author mohammes
 *
 */
public class RequisitionConsolidatorScheduler extends QuartzJobBean {

	/** The auto requisition service. */
	private transient AutoRequisitionService autoRequisitionService;
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RequisitionConsolidatorScheduler.class);
	
	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	
	
	/**
	 * Gets the auto requisition service.
	 *
	 * @return the autoRequisitionService
	 */
	public AutoRequisitionService getAutoRequisitionService() {
		return autoRequisitionService;
	}
	
	/**
	 * Sets the auto requisition service.
	 *
	 * @param autoRequisitionService the autoRequisitionService to set
	 */
	public void setAutoRequisitionService(
			AutoRequisitionService autoRequisitionService) {
		this.autoRequisitionService = autoRequisitionService;
	}
	
	/**
	 * consolidateRequisition ::
	 * i) Consolidate all requisitions under the RHO and generates Unique Requisition Number (Using RHO CODE) and send to SAP(through interface)
	 * ii) Same information will be process Using Stock Receipt RHO Screen.							
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public void consolidateRequisition() throws CGSystemException, CGBusinessException,HttpException, ClassNotFoundException, IOException{
		LOGGER.info("RequisitionConsolidatorScheduler::consolidateRequisition:: START");
		List<String> rhoCodeList =autoRequisitionService.getAllRHOCodes();
		LOGGER.trace("RequisitionConsolidatorScheduler::consolidateRequisition:: RHO Details ["+(CGCollectionUtils.isEmpty(rhoCodeList)?"NO-Records Found":rhoCodeList.toString())+"]");
		for(String rhoCode:rhoCodeList){
			try {
				LOGGER.debug("RequisitionConsolidatorScheduler::consolidateRequisition:: Consolidating for the RHO :["+rhoCode+"]");
				Boolean isProcessed=autoRequisitionService.consolidateAutoRequisitionByRHO(rhoCode);
				LOGGER.debug("RequisitionConsolidatorScheduler::consolidateRequisition:: Consolidating for the RHO :["+rhoCode+"] has been completed with isProcessed status ["+isProcessed+"]");
			} catch (Exception e) {
				LOGGER.error("RequisitionConsolidatorScheduler::consolidateRequisition:: Exception",e);
			}
		}

		LOGGER.info("RequisitionConsolidatorScheduler::consolidateRequisition:: END");
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		LOGGER.info("RequisitionConsolidatorScheduler::consolidateRequisition:: Schedular starts");
		try {
			consolidateRequisition();
		} catch (HttpException e) {
			LOGGER.error("RequisitionConsolidatorScheduler::executeInternal::HttpException::",e);
		} catch (ClassNotFoundException e) {
			LOGGER.error("RequisitionConsolidatorScheduler::executeInternal::ClassNotFoundException::",e);
		} catch (IOException e) {
			LOGGER.error("RequisitionConsolidatorScheduler::executeInternal::IOException::" ,e);
		} catch(Exception e) {
			LOGGER.error("RequisitionConsolidatorScheduler::executeInternal::Exception::" , e);
		}
		LOGGER.info("RequisitionConsolidatorScheduler::consolidateRequisition:: Schedular ENDS");
		
	}

}
