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
 * The Class AutoRequisitionScheduler.
 *
 * @author mohammes
 */
public class AutoRequisitionScheduler extends QuartzJobBean {

	/** The auto requisition service. */
	private transient AutoRequisitionService autoRequisitionService;
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AutoRequisitionScheduler.class);
	
	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		LOGGER.debug("AutoRequisitionScheduler ::executeInternal ::START ");
		try {
			generateAutoRequisition();
		}  catch (HttpException e) {
			LOGGER.error("AutoRequisitionScheduler::executeInternal::HttpException::" , e);
		} catch (ClassNotFoundException e) {
			LOGGER.error("AutoRequisitionScheduler::executeInternal::ClassNotFoundException::" , e);
		} catch (IOException e) {
			LOGGER.error("AutoRequisitionScheduler::executeInternal::IOException::" , e);
		} catch(Exception e) {
			LOGGER.error("AutoRequisitionScheduler::executeInternal::Exception::" , e);
		}
		LOGGER.debug("AutoRequisitionScheduler ::executeInternal ::END ");
	}
	
	/**
	 * Generate auto requisition.
	 *
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private void generateAutoRequisition() throws CGBusinessException,
	CGSystemException,HttpException, ClassNotFoundException, IOException {
		boolean result=false;
		LOGGER.debug("AutoRequisitionScheduler :: generateAutoRequisition :: START");
		List<Integer> officeIdList= autoRequisitionService.getOfficeDtlsForAutoReq();
		if(!CGCollectionUtils.isEmpty(officeIdList)){
			for(Integer officeId:officeIdList){
				try {
					result= autoRequisitionService.generateAutoRequisitionByOffice(officeId);
				} catch (Exception e) {
					LOGGER.error("AutoRequisitionScheduler ## generateAutoRequisition ## Exception Occurred for officeId :["+officeId+"] & Exception :[",e+"]");
				}
				LOGGER.info("AutoRequisitionScheduler :: generateAutoRequisition :: for officeId :["+officeId+"]"+"Status :"+result);
			}

		}else{
			LOGGER.warn("AutoRequisitionScheduler ## generateAutoRequisition ## Office details does not exist");
		}
		LOGGER.debug("AutoRequisitionScheduler :: generateAutoRequisition :: END");

	}
	
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

}
