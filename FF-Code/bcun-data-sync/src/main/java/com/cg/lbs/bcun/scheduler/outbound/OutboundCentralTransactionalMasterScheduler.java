package com.cg.lbs.bcun.scheduler.outbound;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cg.lbs.bcun.service.outbound.OutboundCentralTransactionalMasterDataProcessor;

/**
 * @author mohammal
 * Jan 15, 2013
 * Out bound central scheduler is used to process the data which
 * has to be transfered to branches. This scheduler will fetch the
 * data from database and create a xml file containing JSON string
 * and zip that file and store in a table with respect to branch
 * for which data belong to.Same data will be returned when any 
 * request will be received from branch server for that process.
 */
public class OutboundCentralTransactionalMasterScheduler extends QuartzJobBean  {

	/**
	 * Used to log the message
	 */
	private static final Logger logger = LoggerFactory.getLogger(OutboundCentralTransactionalMasterScheduler.class);
	
	/**
	 * Object used to process data at central server
	 */
	private OutboundCentralTransactionalMasterDataProcessor dataProcessor;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		try{
		logger.info("OutboundCentralTransactionalMasterScheduler::executeInternal::start=====>");
		//Starting process
		dataProcessor.proceedOutbountTransactionMasterDataPreparation();
		logger.info("OutboundCentralTransactionalMasterScheduler::executeInternal::end=====>");
		}catch (HttpException e) {
			logger.error("OutboundCentralTransactionalMasterScheduler::executeInternal::HttpException::" , e );
		} catch (ClassNotFoundException e) {
			logger.error("OutboundCentralTransactionalMasterScheduler::executeInternal::ClassNotFoundException::" , e );
		} catch (IOException e) {
			logger.error("OutboundCentralTransactionalMasterScheduler::executeInternal::IOException::" , e );
		} catch(Exception e) {
			logger.error("OutboundCentralTransactionalMasterScheduler::executeInternal::Exception::" , e);
		}
	}

	/**
	 * @param dataProcessor the dataProcessor to set
	 */
	public void setDataProcessor(
			OutboundCentralTransactionalMasterDataProcessor dataProcessor) {
		this.dataProcessor = dataProcessor;
	}

}
