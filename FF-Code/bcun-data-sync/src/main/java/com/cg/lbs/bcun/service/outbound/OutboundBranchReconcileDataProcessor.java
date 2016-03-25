package com.cg.lbs.bcun.service.outbound;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.httpclient.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.domain.CGBcunInbundDO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.FileUtils;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.cg.lbs.bcun.to.BcunConfigPropertyTO;
import com.cg.lbs.bcun.to.BcunFileSequenceTO;
import com.cg.lbs.bcun.to.ReconcillationConfigPropertyTO;
import com.cg.lbs.bcun.utility.ReconcillationPropertyReader;
import com.ff.domain.bcun.reconcillation.BcunReconcillationBlobDO;
import com.ff.domain.bcun.reconcillation.BcunReconcillationDO;

/**
 * @author bmodala
 * 18 Aug,2015
 * Process out bound branch data
 */
public class OutboundBranchReconcileDataProcessor {

	
	/**
	 * BCUN service to process the request
	 */
	private BcunDatasyncService bcunService;
	
	/**
	 * LOGGER to log the process execution information
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OutboundBranchReconcileDataProcessor.class);
	
	/**
	 * Proceed scheduling task
	 * @throws HttpException while posting data to central server
	 * @throws ClassNotFoundException while loading/formating instance of configured class 
	 * @throws IOException while posting request to central server
	 */
	public  void processBranchdata() throws HttpException, ClassNotFoundException, IOException {
		LOGGER.debug("OutboundBranchReconcileDataProcessor::processBranchdata::start=====>");
		insertFileDataIntoDatabase();
		LOGGER.debug("OutboundBranchReconcileDataProcessor::processBranchdata::end=====>");
	}
	
	/**
	 * 
	 */
	private void insertFileDataIntoDatabase() {
		//Getting file location
		LOGGER.trace("OutboundBranchReconcileDataProcessor::processBranchdata::insertFileDataIntoDatabase::START=====>");
		String fileLocation = bcunService.getBaseFileLocation();
		//Processing file and uploading it to central
		LOGGER.trace("OutboundBranchReconcileDataProcessor::processBranchdata::insertFileDataIntoDatabase::fileLocation=====>"+fileLocation);
		String[] fileNames = FileUtils.getAllFilesNames(fileLocation, "bcunOutbound");
		List<BcunFileSequenceTO> fileList = getFileListInSequence(fileNames);
		if(fileList != null && !fileList.isEmpty()){
			LOGGER.trace("OutboundBranchReconcileDataProcessor::processBranchdata::insertFileDataIntoDatabase::File List=====>"+fileList);
			processFileAndUploadToDatabase(fileList, fileLocation);
		}else{
			LOGGER.warn("####### OutboundBranchReconcileDataProcessor::processBranchdata::insertFileDataIntoDatabase::No files process #########");
		}
		LOGGER.trace("OutboundBranchReconcileDataProcessor::processBranchdata::insertFileDataIntoDatabase::END=====>");
	}  
	
	private void processFileAndUploadToDatabase(List<BcunFileSequenceTO> fileList, String baseLocation) {
		LOGGER.debug("OutboundBranchReconcileDataProcessor::processFileAndUploadToDatabase::START#####");
		if(fileList != null && !fileList.isEmpty()) {
			LOGGER.debug("OutboundBranchReconcileDataProcessor::proceedFileProcessing::total file has to be proceed=" + fileList.size());
			for(BcunFileSequenceTO fileTo : fileList) {
				//boolean isInboundFile = fileName.startsWith("DataSync-Outbound-");
				try {
					String fileName = fileTo.getFileName();
					LOGGER.debug("OutboundBranchReconcileDataProcessor::processFileAndUploadToDatabase::Processing File :["+fileName+"]");
					//boolean isReProcessFiles = fileName.contains(RE_PROCESS_FLAG_NAME);
					readFileAndUpdateDB(fileName, baseLocation);
				} catch (Exception e) {
					LOGGER.error("OutboundBranchReconcileDataProcessor::processFileAndUploadToDatabase::Exception::::"+e);
				}
				
			} 
		}
		LOGGER.debug("OutboundBranchReconcileDataProcessor::processFileAndUploadToDatabase::END#####");
	}
	
	@SuppressWarnings("unchecked")
	protected void readFileAndUpdateDB(String fileName, String baseLocation) {
		try {
			LOGGER.debug("OutboundBranchReconcileDataProcessor::proceedFileProcessing::processing file name: " + fileName+"Started");
			
			//Getting process name based on file name
			String processName = bcunService.getProcessName(fileName);
			//Reading in bound properties entry 
			//String className = this.getClass().getName();
			//reconcillation starts
			boolean isReconcileData =processName.contains("Reconcillation");
			//reconcillation ends
			ReconcillationConfigPropertyTO confTo = null;  
			if(isReconcileData){
				confTo = ReconcillationPropertyReader.getReconcillationConfigPropertyByEachPropertyKey(processName);
			}
			//Reading process data from file
			String filePath = baseLocation + File.separator + fileName; //getFilePath(fileName, baseLocation);
			LOGGER.debug("OutboundBranchReconcileDataProcessor::proceedFileProcessing::filePath:["+filePath+"]");
			File processFile = new File(filePath);
			List<CGBaseDO> baseList = (List<CGBaseDO>)bcunService.getProcessDOListFromFile(processFile, confTo.getBlobPreparationDomain());
			LOGGER.debug("OutboundBranchReconcileDataProcessor::proceedFileProcessing::updating db for file: " + fileName);
			//Getting configured formatter class name 
			//Reconcillaton starts
			if(isReconcileData && !CGCollectionUtils.isEmpty(baseList)){		
				Set<BcunReconcillationBlobDO> blobCentralDataSet = new HashSet<BcunReconcillationBlobDO>((List)baseList);	
				Set<BcunReconcillationBlobDO> blobBranchDataSet=null;
				BcunReconcillationBlobDO blobDo=blobCentralDataSet.iterator().next();
				List<CGBcunInbundDO> transactionNumber =null;
				
				confTo.setTransactionOfficeId(blobDo.getTransactionOfficeId());
				confTo.setTransactionDate(blobDo.getTransactionDate());	
				List<CGBaseDO> cgBaseDo= bcunService.getReconcillationDataForBlob(confTo);
				//update the central Cn count starts
				updateCentralCNCountAtBranch(baseList, blobDo);
				if(!CGCollectionUtils.isEmpty(cgBaseDo)){
					blobBranchDataSet =new HashSet<>((List)cgBaseDo); 
					if(blobBranchDataSet.removeAll(blobCentralDataSet)){
						transactionNumber =new ArrayList<>(blobBranchDataSet.size());
						for (BcunReconcillationBlobDO bcunReconcillationBlobDO : blobBranchDataSet) {
							CGBcunInbundDO inboundDO= new CGBcunInbundDO();
							inboundDO.setBusinesskey(bcunReconcillationBlobDO.getTransactionNumber());
							transactionNumber.add(inboundDO);
						}
					}else {
						transactionNumber =new ArrayList<>(blobBranchDataSet.size());
						for (BcunReconcillationBlobDO bcunReconcillationBlobDO : blobBranchDataSet) {
							if(DateUtil.DayDifferenceBetweenTwoDates(bcunReconcillationBlobDO.getTransactionDate(), new Date())>=2L){
								CGBcunInbundDO inboundDO= new CGBcunInbundDO();
								inboundDO.setBusinesskey(bcunReconcillationBlobDO.getTransactionNumber());
								transactionNumber.add(inboundDO);
							}
						}
					}
					if(!CGCollectionUtils.isEmpty(transactionNumber)){
					bcunService.updateBcunDataTransferFlag(transactionNumber, confTo.getNamedQuerydtToCentralUpdate());
					}
				}
			}
			bcunService.renameFile(fileName, baseLocation, "pr");
		} catch (Exception ex) {
			LOGGER.error("OutboundBranchReconcileDataProcessor::proceedFileProcessing::moved to er : EXCEPTION", ex);
		}
	}
	@SuppressWarnings("unchecked")
	private void updateCentralCNCountAtBranch(List<CGBaseDO> baseList,
			BcunReconcillationBlobDO blobDo) {
		LOGGER.debug("OutboundBranchReconcileDataProcessor::updateCentralCountAtBranch::starting point");
		try{
			String namedQueryString="getReconcillationDetailsByProcess";  
			String[] params ={"transoffice","transDate","processName"};
			Object[] values ={blobDo.getTransactionOfficeId(),blobDo.getTransactionDate(),blobDo.getProcessName()};
			List<CGBaseDO> reconcillationList = (List<CGBaseDO>) bcunService.getDataByNamedQueryAndNamedParam(namedQueryString, params, values);
			if(!CGCollectionUtils.isEmpty(reconcillationList)){
				BcunReconcillationDO  srcReconcillationDO= (BcunReconcillationDO)reconcillationList.get(0);	
				srcReconcillationDO.setCentralCNCount(BigInteger.valueOf(baseList.size()));
				if(srcReconcillationDO.getBranchCNCount()!=null && srcReconcillationDO.getBranchCNCount().intValue() == srcReconcillationDO.getCentralCNCount().intValue()){
					srcReconcillationDO.setIsCountMatched(FrameworkConstants.ENUM_YES);	
				}
				reconcillationList.add(srcReconcillationDO);
				bcunService.saveOrUpdateTransferedEntities(reconcillationList);
			}	
		}catch (Exception ex) {
			LOGGER.error("OutboundBranchReconcileDataProcessor::updateCentralCountAtBranch::Exception::",ex);
		}
	}
	private List<BcunFileSequenceTO> getFileListInSequence(String[] fileNames) {
		if(fileNames == null || fileNames.length == 0)
			return null;
		
		List<BcunFileSequenceTO> fileList = new ArrayList<BcunFileSequenceTO>();
		for(String fileName : fileNames) {
			if(fileName.contains("Reconcil") && !fileName.startsWith("pr")&& !fileName.startsWith("er")) {
				try {
					String processName = bcunService.getProcessName(fileName);
					BcunConfigPropertyTO confTo = ReconcillationPropertyReader.getReconcillationConfigPropertyByEachPropertyKey(processName);
					int sequence = confTo.getSequence();
					BcunFileSequenceTO sequenceTO = new BcunFileSequenceTO();
					sequenceTO.setSequenceNumber(sequence);
					sequenceTO.setFileName(fileName);
					fileList.add(sequenceTO);
				} catch (Exception ex) {
					LOGGER.error("OutboundBranchReconcileDataProcessor::getFileListInSequence::Exception::",ex);
				}
				
			}
		}
		Collections.sort(fileList);
		return fileList;
	}
	/**
	 * Spring's setter injection
	 * @param bcunService
	 */
	public void setBcunService(BcunDatasyncService bcunService) {
		this.bcunService = bcunService;
	}
}
