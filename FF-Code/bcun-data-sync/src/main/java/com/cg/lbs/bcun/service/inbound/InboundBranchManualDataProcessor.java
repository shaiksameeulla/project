/*
 * 
 */
package com.cg.lbs.bcun.service.inbound;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.cg.lbs.bcun.to.InboundConfigPropertyTO;

/**
 * @author mohammes
 * Oct 15, 2014
 * Used to process the in bound data from the branch office.
 */
public class InboundBranchManualDataProcessor {

	/**
	 *  Log the message of the process.
	 */
	private Logger logger = LoggerFactory.getLogger(InboundBranchManualDataProcessor.class);
	
	
	/**
	 * BCUN service which will process the opration.
	 */
	private BcunDatasyncService bcunService;
	
	private InboundBranchDrsDataProcessor inboundDrsDataProcessor;
	private InboundBranchBookingDataProcessor inboundBookingDataProcessor;
	private InboundBranchManifestDataProcessor inboundManifestDataProcessor;
	private InboundBranchDataProcessor inboundOtherDataProcessor;
	private InboundBranchPickupDataProcessor inboundPickupDataProcessor;
	
	
	/**
	 * Starting points to setup the process
	 */
	public String proceedDatasync()  throws CGBusinessException,
	CGSystemException{
		logger.info("InboundBranchManualDataProcessor::proceedDatasync::start");
		//Starting file creation for all the configured process
		String zipFilePath=null;
		try {
			logger.debug("InboundBranchManualDataProcessor::proceedDatasync::starting file creation....");
			zipFilePath=createProcessFiles();
			logger.debug("InboundBranchManualDataProcessor::proceedDatasync::file creation completed....");
		} catch (Exception ex) {
			logger.error("InboundBranchManualDataProcessor::proceedDatasync::error while creating files::" +ex.getMessage());
			throw ex;
		}
		
		logger.info("InboundBranchManualDataProcessor::processDatasync::end");
		return zipFilePath;
	}
	
	
	/**
	 * Create the file contains process data as a JSON string.
	 */
	
	@SuppressWarnings("unchecked")
	private String createProcessFiles()  throws CGBusinessException,
	CGSystemException{
		String zipFilePath=null;
		//Reading all the process configuration
		logger.debug("InboundBranchManualDataProcessor::createProcessFiles::START");
		List<InboundConfigPropertyTO> configProps = (List<InboundConfigPropertyTO>)bcunService.getBcunConfigProps();
		logger.trace("InboundBranchDrsDataProcessor::createProcessFiles::configProps::" + configProps);
		try {
			inboundDrsDataProcessor.proceedDatasync();
		} catch (Exception e) {
			logger.error("InboundBranchManualDataProcessor::createProcessFiles::Exception for Drs  process",e);
		} 
		try {
			inboundBookingDataProcessor.proceedDatasync();
		} catch (Exception e) {
			logger.error("InboundBranchManualDataProcessor::createProcessFiles::Exception for Booking  process",e);
		} 
		try {
			inboundManifestDataProcessor.proceedDatasync();
		} catch (Exception e) {
			logger.error("InboundBranchManualDataProcessor::createProcessFiles::Exception for Manifest  process",e);
		} 
		try {
			inboundPickupDataProcessor.proceedDatasync();
		} catch (Exception e) {
			logger.error("InboundBranchManualDataProcessor::createProcessFiles::Exception for Pickup  process",e);
		} 
		try {
			inboundOtherDataProcessor.proceedDatasync();
		} catch (Exception e) {
			logger.error("InboundBranchManualDataProcessor::createProcessFiles::Exception for others  process",e);
		} 
		
		try {
			
			zipFilePath=bcunService.prepareInboundBranchData();
		} catch (Exception e) {
			logger.error("InboundBranchManualDataProcessor::ZIP File creation::prepareInboundBranchData::Exception ",e);
			throw e;
		} 
		
return zipFilePath;
	}


	/**
	 * @param inboundDrsDataProcessor the inboundDrsDataProcessor to set
	 */
	public void setInboundDrsDataProcessor(
			InboundBranchDrsDataProcessor inboundDrsDataProcessor) {
		this.inboundDrsDataProcessor = inboundDrsDataProcessor;
	}


	/**
	 * @param inboundBookingDataProcessor the inboundBookingDataProcessor to set
	 */
	public void setInboundBookingDataProcessor(
			InboundBranchBookingDataProcessor inboundBookingDataProcessor) {
		this.inboundBookingDataProcessor = inboundBookingDataProcessor;
	}


	/**
	 * @param inboundManifestDataProcessor the inboundManifestDataProcessor to set
	 */
	public void setInboundManifestDataProcessor(
			InboundBranchManifestDataProcessor inboundManifestDataProcessor) {
		this.inboundManifestDataProcessor = inboundManifestDataProcessor;
	}


	/**
	 * @param inboundOtherDataProcessor the inboundOtherDataProcessor to set
	 */
	public void setInboundOtherDataProcessor(
			InboundBranchDataProcessor inboundOtherDataProcessor) {
		this.inboundOtherDataProcessor = inboundOtherDataProcessor;
	}
	
	/**
	 * Spring's setter injection
	 * @param bcunService
	 */
	public void setBcunService(BcunDatasyncService bcunService) {
		this.bcunService = bcunService;
	}


	/**
	 * @param inboundPickupDataProcessor the inboundPickupDataProcessor to set
	 */
	public void setInboundPickupDataProcessor(
			InboundBranchPickupDataProcessor inboundPickupDataProcessor) {
		this.inboundPickupDataProcessor = inboundPickupDataProcessor;
	}
}
