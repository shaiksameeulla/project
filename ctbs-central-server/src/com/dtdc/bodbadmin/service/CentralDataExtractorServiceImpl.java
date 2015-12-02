/**
 * 
 */
package com.dtdc.bodbadmin.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.ApplicationConstants;
import com.capgemini.lbs.framework.constants.BusinessConstants;
import com.capgemini.lbs.framework.constants.UtilConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.exception.file.FolderRenamingException;
import com.capgemini.lbs.framework.utils.CGStackTraceUtil;
import com.capgemini.lbs.framework.utils.CGXMLUtil;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.capgemini.lbs.framework.utils.FileUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.bodbadmin.dao.CentralDataExtractorDAO;
import com.dtdc.bodbadmin.schema.booking.BookingDetailsData;
import com.dtdc.bodbadmin.schema.delivery.DeliveyManifestData;
import com.dtdc.bodbadmin.schema.dispatch.DispatchDetailsData;
import com.dtdc.bodbadmin.schema.heldupRelease.HeldupReleaseData;
import com.dtdc.bodbadmin.schema.manifest.ManifestData;
import com.dtdc.bodbadmin.schema.purchase.goodsCancellation.GoodsCancellationData;
import com.dtdc.bodbadmin.schema.purchase.goodsIssue.GoodsIssueData;
import com.dtdc.bodbadmin.schema.purchase.goodsRenewal.GoodsRenewalData;
import com.dtdc.bodbadmin.schema.rtoManifest.RtoData;
import com.dtdc.bodbadmin.utility.CentralDataExtractorConstant;
import com.dtdc.bodbadmin.utility.DomainToTransferObjectConverter;
import com.dtdc.bodbadmin.xmlutil.CTBSXMLParser;
import com.dtdc.bodbadmin.ziputil.ZipUtility;
import com.dtdc.domain.booking.BookingDO;
import com.dtdc.domain.dataextraction.DataExtractionDO;
import com.dtdc.domain.dispatch.DispatchDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.RtnToOrgDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.purchase.GoodsIssueDO;
import com.dtdc.domain.purchase.GoodsRenewalDO;
import com.dtdc.domain.purchase.GoodscCancellationDO;
import com.dtdc.domain.transaction.delivery.DeliveryDO;
import com.dtdc.domain.transaction.heldup.HeldUpReleaseDO;
import com.dtdc.to.manifestextractor.DataExtractorTO;

// TODO: Auto-generated Javadoc
/**
 * The Class CentralDataExtractorServiceImpl.
 *
 * @author nisahoo
 */
public class CentralDataExtractorServiceImpl implements
CentralDataExtractorService {

	/** The Constant LOGGER. */
	private static final org.slf4j.Logger LOGGER = LoggerFactory
	.getLogger(CentralDataExtractorServiceImpl.class);

	/** The message prop. */
	private Properties messageProp;
	
	/** The central data extractor dao. */
	private CentralDataExtractorDAO centralDataExtractorDAO;
	
	/** The object converter. */
	private DomainToTransferObjectConverter objectConverter;

	/**
	 * Gets the message prop.
	 *
	 * @return the message prop
	 */
	public Properties getMessageProp() {
		return messageProp;
	}

	/**
	 * Sets the message prop.
	 *
	 * @param messageProp the new message prop
	 */
	public void setMessageProp(Properties messageProp) {
		this.messageProp = messageProp;
	}

	/**
	 * Gets the central data extractor dao.
	 *
	 * @return the central data extractor dao
	 */
	public CentralDataExtractorDAO getCentralDataExtractorDAO() {
		return centralDataExtractorDAO;
	}

	/**
	 * Sets the central data extractor dao.
	 *
	 * @param centralDataExtractorDAO the new central data extractor dao
	 */
	public void setCentralDataExtractorDAO(
			CentralDataExtractorDAO centralDataExtractorDAO) {
		this.centralDataExtractorDAO = centralDataExtractorDAO;
	}

	/**
	 * Gets the object converter.
	 *
	 * @return the object converter
	 */
	public DomainToTransferObjectConverter getObjectConverter() {
		return objectConverter;
	}

	/**
	 * Sets the object converter.
	 *
	 * @param objectConverter the new object converter
	 */
	public void setObjectConverter(
			DomainToTransferObjectConverter objectConverter) {
		this.objectConverter = objectConverter;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.service.CentralDataExtractorService#extractDataForAllBranches()
	 */
	@Override
	public void extractDataForAllBranches() throws CGBusinessException {

		LOGGER.debug("CentralDataExtractorServiceImpl : extractDataForBranch() : START");

		// The following constants for no of records to be fetched from each
		// process (configurable ie. taking the values from
		// properties(centralDataExtraction.properties) file)

		String branchFolder = "";
		try {
			/** Get all the branches */
			List<String> officeCodeList = centralDataExtractorDAO
			.getAllOfficeCodes();

			for (String branchCode : officeCodeList) {

				branchFolder = extractDataForBranch(branchCode);
			}
		}  catch (Exception ex) {
			LOGGER.error("CentralDataExtractorServiceImpl::extractDataForAllBranches::Exception occured:"
					+ex.getMessage());
			
		} finally {
			deleteBranchDirectory(branchFolder);
		}
		LOGGER.debug("CentralDataExtractorServiceImpl : extractDataForBranch() : END");
	}
	
	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.service.CentralDataExtractorService#extractDataForBooking()
	 */
	@Override
	public void extractDataForBooking() throws CGBusinessException {

		LOGGER.debug("CentralDataExtractorServiceImpl : extractDataForBooking() : START");

		// The following constants for no of records to be fetched from each
		// process (configurable ie. taking the values from
		// properties(centralDataExtraction.properties) file)

		String branchFolder = null;
		try {
			/** Get all the branches */
			List<String> officeCodeList = centralDataExtractorDAO
			.getAllOfficeCodes();

			for (String branchCode : officeCodeList) {

				//######################################Booking###################### START
				branchFolder=null;
		        branchFolder = getFolderNameFromProps()
			+ File.separator + branchCode+File.separator+branchCode+ApplicationConstants.CHARACTER_UNDERSCORE+StringUtil.generateRamdomNumber();
				try {
					LOGGER.debug("########Booking Start#######");
					bookingProcess(branchCode, branchFolder);
					LOGGER.debug("########Booking End #######");
				} catch (Exception e) {
					LOGGER.error("CentralDataExtractorServiceImpl::extractDataForBooking::Exception occured:"
							+e.getMessage());
				}finally {
					deleteBranchDirectory(branchFolder);
				}
				//######################################Booking###################### END
			}
		}  catch (Exception ex) {
			LOGGER.error("CentralDataExtractorServiceImpl::extractDataForBooking::Exception occured:"
					+ex.getMessage());
			
		} 
		LOGGER.debug("CentralDataExtractorServiceImpl : extractDataForBooking() : END");
	}
	
	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.service.CentralDataExtractorService#extractDataForDispatch()
	 */
	@Override
	public void extractDataForDispatch() throws CGBusinessException {

		LOGGER.debug("CentralDataExtractorServiceImpl : extractDataForBranch() : START");

		// The following constants for no of records to be fetched from each
		// process (configurable ie. taking the values from
		// properties(centralDataExtraction.properties) file)

		String branchFolder = "";
		try {
			/** Get all the branches */
			List<String> officeCodeList = centralDataExtractorDAO
			.getAllOfficeCodes();

			for (String branchCode : officeCodeList) {

				branchFolder = extractDispacthForBranch(branchCode);
			}
		}  catch (Exception ex) {
			LOGGER.error("CentralDataExtractorServiceImpl::extractDataForDispatch::Exception occured:"
					+ex.getMessage());
			
		} finally {
			deleteBranchDirectory(branchFolder);
		}
		LOGGER.debug("CentralDataExtractorServiceImpl : extractDataForBranch() : END");
	}
	
	/**
	 * Extract dispacth for branch.
	 *
	 * @param branchCode the branch code
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private String extractDispacthForBranch(String branchCode)
	throws CGBusinessException, CGSystemException, IOException {
		String branchFolder;
		LOGGER.debug("##########Data (extractDataForBranch)Extract for Branch = [ "
				+ branchCode + " ]###############");
		branchFolder = getFolderNameFromProps()
		+ File.separator + branchCode+ File.separator + branchCode+ApplicationConstants.CHARACTER_UNDERSCORE+StringUtil.generateRamdomNumber();

		//###################################### DISPATCH ###################### START
		try {
			LOGGER.debug("########DISPATCH START#######");
			dispatchProcess(branchCode,
					branchFolder);
			LOGGER.debug("########DISPATCH END#######");
		} catch (Exception e) {
			LOGGER.error("CentralDataExtractorServiceImpl::extractDispacthForBranch::Exception occured:"
					+e.getMessage());
		}finally {
			deleteBranchDirectory(branchFolder);
		}
		//###################################### DISPATCH ###################### END
			 
			return branchFolder;
		}

	/**
	 * Extract data for branch.
	 *
	 * @param branchCode the branch code
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private String extractDataForBranch(String branchCode)
	throws CGBusinessException, CGSystemException, IOException {
		String branchFolder;
		LOGGER.debug("##########Data (extractDataForBranch)Extract for Branch = [ "
				+ branchCode + " ]###############");
		
			branchFolder=null;
			branchFolder = getFolderNameFromProps()
			+ File.separator + branchCode+File.separator+branchCode+ApplicationConstants.CHARACTER_UNDERSCORE+StringUtil.generateRamdomNumber();
			
			//######################################HELDUP RELEASE###################### START
			try {
				LOGGER.debug("########Heldup Release START#######");
				heldupReleaseProcess(branchCode,
						branchFolder);
				LOGGER.debug("########Heldup Release END#######");
			} catch (Exception e) {
				LOGGER.error("CentralDataExtractorServiceImpl::extractDataForBranch::Exception occured:"
						+e.getMessage());
			}finally {
				deleteBranchDirectory(branchFolder);
			}
			//######################################HELDUP RELEASE###################### END

			branchFolder=null;
			branchFolder = getFolderNameFromProps()
			+ File.separator + branchCode+File.separator+branchCode+ApplicationConstants.CHARACTER_UNDERSCORE+StringUtil.generateRamdomNumber();
			
			//######################################RTO###################### START
			try {
				LOGGER.debug("########RTO START#######");
				returnToOriginProcess(branchCode, branchFolder);
				LOGGER.debug("########RTO END#######");
			} catch (Exception e) {
				LOGGER.error("CentralDataExtractorServiceImpl::extractDataForBranch::Exception occured:"
						+e.getMessage());
			}finally {
				deleteBranchDirectory(branchFolder);
			}
			//######################################RTO###################### END
			branchFolder=null;
			branchFolder = getFolderNameFromProps()
			+ File.separator + branchCode+File.separator+branchCode+ApplicationConstants.CHARACTER_UNDERSCORE+StringUtil.generateRamdomNumber();
			
			//###################################### MANIFEST ###################### START
			try {
				LOGGER.debug("########MANIFEST START#######");
				manifestProcess(branchCode, branchFolder);
				LOGGER.debug("########MANIFEST END#######");
			} catch (Exception e) {
				LOGGER.error("CentralDataExtractorServiceImpl::extractDataForBranch::Exception occured:"
						+e.getMessage());
			}finally {
				deleteBranchDirectory(branchFolder);
			}
			//###################################### MANIFEST ###################### END
			branchFolder=null;
			branchFolder = getFolderNameFromProps()
			+ File.separator + branchCode+File.separator+branchCode+ApplicationConstants.CHARACTER_UNDERSCORE+StringUtil.generateRamdomNumber();

			//###################################### DELIVERY ###################### START
			try {
				LOGGER.debug("########DELIVERY START#######");
				deliveryProcess(branchCode, branchFolder);
				LOGGER.debug("########DELIVERY END#######");
			} catch (Exception e) {
				LOGGER.error("CentralDataExtractorServiceImpl::extractDataForBranch::Exception occured:"
						+e.getMessage());
			}finally {
				deleteBranchDirectory(branchFolder);
			}
			//###################################### DELIVERY ###################### END

			
		 
		return branchFolder;
	}

	/**
	 * Gets the folder name from props.
	 *
	 * @return the folder name from props
	 */
	private String getFolderNameFromProps() {
		return messageProp
		.getProperty(CentralDataExtractorConstant.XML_DATA_BASE_DIR);
	}

	/**
	 * Delivery process.
	 *
	 * @param branchCode the branch code
	 * @param branchFolder the branch folder
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void deliveryProcess(String branchCode, String branchFolder)
	throws  CGBusinessException,
	CGSystemException, IOException {
		List<DeliveryDO> dlvMnfstDOList = null;
		DataExtractionDO deliveryExtractionDO =null;
		Integer maxDlvMnfstRecords = 10;
		DataExtractorTO extractorTo=new DataExtractorTO();
		try {
			String maxDeliveryRecSize = messageProp
			.getProperty(CentralDataExtractorConstant.MAX_FETCH_SIZE_DELIVERY);
			if (!StringUtils.isEmpty(maxDeliveryRecSize)) {
				maxDlvMnfstRecords = Integer.parseInt(maxDeliveryRecSize);
				LOGGER.debug("Max DELIVERY Records to be fetched are (After Conversion )= [ "
						+ maxDlvMnfstRecords + " ]");
			} else {
				LOGGER.debug("Max DELIVERY Records to be fetched are (Default values )= [ "
						+ maxDlvMnfstRecords + " ]");
			}
			if (!StringUtil.isEmptyInteger(maxDlvMnfstRecords)) {
				LOGGER.debug("Fetching DELIVERY Records From DB");
				dlvMnfstDOList = getDeliveryMnfstDtlsByBranchCode(branchCode,
						maxDlvMnfstRecords);
				LOGGER.debug("##Fetching Process Completed From DB Record (Delivery), No Of Records:"
						+ ((dlvMnfstDOList != null && !dlvMnfstDOList.isEmpty()) ? dlvMnfstDOList
								.size() : "0"));
			}

			/**
			 *        Create the XML files for
			 * Delivery Details Data
			 */
			if (dlvMnfstDOList != null && !dlvMnfstDOList.isEmpty()) {
				LOGGER.debug("Delivery DO Data Size from Central DB = ["
						+ dlvMnfstDOList.size() + " ]");
				createDlvMnfstXMLForBranch(dlvMnfstDOList, branchFolder, extractorTo);
			}

			deliveryExtractionDO = prepareExtractedData(branchCode, branchFolder,
					deliveryExtractionDO, extractorTo);

			if (deliveryExtractionDO != null&& dlvMnfstDOList != null && dlvMnfstDOList.size() > 0) {
				/** Save ZIP File */
				centralDataExtractorDAO
				.saveExtractedDataForBranch(deliveryExtractionDO, null);

				centralDataExtractorDAO
				.updateDlvMnfstWithReadByLocal(dlvMnfstDOList);

				/** Delete the XML Files in the Branch Folder */
				deleteBranchDirectory(branchFolder);

			}
		} catch (CGBusinessException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::CGBusinessException::Exception occured:"
					+e.getMessage());
			throw e;
		} catch (CGSystemException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::deliveryProcess::CGSystemException occured:"
					+e.getMessage());
			throw e;

		} catch (IOException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::deliveryProcess::IOException occured:"
					+e.getMessage());
			throw e;
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "deliveryProcess", UtilConstants.LOGGER_LEVEL_ERROR);
			LOGGER.error("CentralDataExtractorServiceImpl::deliveryProcess::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException();
		} finally {
			extractorTo = null;
			deleteBranchDirectory(branchFolder);
		}
	}

	/**
	 * Dispatch process.
	 *
	 * @param branchCode the branch code
	 * @param branchFolder the branch folder
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void dispatchProcess(String branchCode,
			String branchFolder) throws 
			CGBusinessException,CGSystemException, IOException {
		List<DispatchDO> dispatchDOList = null;
		DataExtractionDO dispatchExtractionDO =null;
		Integer maxDispatchRecords = 10;
		DataExtractorTO extractorTo=new DataExtractorTO();
		
		try {
			String maxDispatchSize = messageProp
			.getProperty(CentralDataExtractorConstant.MAX_FETCH_SIZE_DISPACTH);
			if (!StringUtils.isEmpty(maxDispatchSize)) {
				maxDispatchRecords = Integer.parseInt(maxDispatchSize);
				LOGGER.debug("Max Dispatch Records to be fetched are (After Conversion )= [ "
						+ maxDispatchRecords + " ]");
			} else {
				LOGGER.debug("Max Dispatch Records to be fetched are (Default values )= [ "
						+ maxDispatchRecords + " ]");
			}

			if (!StringUtil.isEmptyInteger(maxDispatchRecords)) {
				LOGGER.debug("Fetching Goods Renewal Records From DB");
				// FETCHING DATA FROM CENTRAL DATABASE
				dispatchDOList = getDispatchDataByBranchCode(branchCode,
						maxDispatchRecords);
				LOGGER.debug("##Fetching Process Completed From DB Record (dispatchProcess), No Of Records:"
						+ ((dispatchDOList != null && !dispatchDOList.isEmpty()) ? dispatchDOList
								.size() : "0"));
			}

			/** Create the XML files for Dispatch Details Data */
			if (dispatchDOList != null && !dispatchDOList.isEmpty()) {
				LOGGER.debug("Dispatch DO Data Size from Central DB = ["
						+ dispatchDOList.size() + " ]");
				createDispatchXMLForBranch(dispatchDOList, branchFolder, extractorTo);
			}

			dispatchExtractionDO = prepareExtractedData(branchCode, branchFolder,
					dispatchExtractionDO, extractorTo);
			if (dispatchExtractionDO != null && dispatchDOList != null && !dispatchDOList.isEmpty()){
				/** Save ZIP File */
				centralDataExtractorDAO
				.saveExtractedDataForBranch(dispatchExtractionDO, null);
				centralDataExtractorDAO
				.updateDispatchDetailsWithReadByLocal(dispatchDOList);

				/** Delete the XML Files in the Branch Folder */
				deleteBranchDirectory(branchFolder);

			}
		}catch (CGBusinessException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::dispatchProcess::CGBusinessException occured:"
					+e.getMessage());
			throw e;
		}catch (CGSystemException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::dispatchProcess::CGSystemException occured:"
					+e.getMessage());
			throw e;

		}
		catch (IOException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::dispatchProcess::IOException occured:"
					+e.getMessage());
			throw e;
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "dispatchProcess", UtilConstants.LOGGER_LEVEL_ERROR);
			LOGGER.error("CentralDataExtractorServiceImpl::dispatchProcess::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException();
		} finally {
			deleteBranchDirectory(branchFolder);
		}
	}

	/**
	 * Manifest process.
	 *
	 * @param branchCode the branch code
	 * @param branchFolder the branch folder
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void manifestProcess(String branchCode, String branchFolder)
	throws  CGBusinessException,CGSystemException, IOException {
		List<ManifestDO> manifestDOList = null;
		DataExtractionDO manifestExtractionDO =null;
		Integer maxManifestRecords = 10;
		DataExtractorTO extractorTo=new DataExtractorTO();
		
		try {
			String maxmnfstRecords = messageProp
			.getProperty(CentralDataExtractorConstant.MAX_FETCH_SIZE_MANIFEST);
			LOGGER.debug("Max Manifest Records to be fetched are (From Properties File )= [ "
					+ maxmnfstRecords + " ]");	

			if (!StringUtils.isEmpty(maxmnfstRecords)) {
				maxManifestRecords = Integer.parseInt(maxmnfstRecords);
				LOGGER.debug("Max Manifest Records to be fetched are (After Conversion )= [ "
						+ maxManifestRecords + " ]");
			} else {
				LOGGER.debug("Max Manifest Records to be fetched are (Default values )= [ "
						+ maxManifestRecords + " ]");
			}
			if (!StringUtil.isEmptyInteger(maxManifestRecords)) {
				LOGGER.debug("Fetching Manifest Records From DB");
				// FETCHING DATA FROM CENTRAL DATABASE
				manifestDOList = getOutgoingManifetsForBranch(branchCode,
						maxManifestRecords);
				LOGGER.debug("##Fetching Process Completed From DB Record (manifestProcess), No Of Records:"
						+ ((manifestDOList != null && !manifestDOList.isEmpty()) ? manifestDOList
								.size() : "0"));
			}			
			if(manifestDOList != null && !manifestDOList.isEmpty()) {
				createManifestXMLForBranch(manifestDOList,branchFolder, extractorTo); 
			}
			manifestExtractionDO = prepareExtractedData(branchCode, branchFolder,
					manifestExtractionDO, extractorTo);

			if (manifestExtractionDO != null&& manifestDOList != null && !manifestDOList.isEmpty()) {
				/** Save ZIP File */
				centralDataExtractorDAO
				.saveExtractedDataForBranch(manifestExtractionDO, null);

				centralDataExtractorDAO
				.updateManifestsWithReadByLocal(manifestDOList);
				/** Delete the XML Files in the Branch Folder */
				deleteBranchDirectory(branchFolder);

			}
		}catch (CGBusinessException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::manifestProcess::CGBusinessException occured:"
					+e.getMessage());
			throw e;
		} catch (CGSystemException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::manifestProcess::CGSystemException occured:"
					+e.getMessage());
			throw e;

		} catch (IOException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::manifestProcess::IOException occured:"
					+e.getMessage());
			throw e;
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "manifestProcess", UtilConstants.LOGGER_LEVEL_ERROR);
			LOGGER.error("CentralDataExtractorServiceImpl::manifestProcess::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException();
		} finally {
			extractorTo=null;
			deleteBranchDirectory(branchFolder);
		}
	}

	/**
	 * Return to origin process.
	 *
	 * @param branchCode the branch code
	 * @param branchFolder the branch folder
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void returnToOriginProcess(String branchCode, String branchFolder)
	throws  CGSystemException,
	CGBusinessException, IOException {
		
		List<RtnToOrgDO> rtnOrgDOList = null;
		DataExtractionDO rtoExtractionDO =null;
		Integer maxRtoRecords = 10;
		DataExtractorTO extractorTo=new DataExtractorTO();
		
		try {
			String maxRtoSize = messageProp
			.getProperty(CentralDataExtractorConstant.MAX_FETCH_SIZE_RTO);
			if (!StringUtils.isEmpty(maxRtoSize)) {
				maxRtoRecords = Integer.parseInt(maxRtoSize);
				LOGGER.debug("Max RTO Records to be fetched are (After Conversion )= [ "
						+ maxRtoRecords + " ]");
			} else {
				LOGGER.debug("Max RTO Records to be fetched are (Default values )= [ "
						+ maxRtoRecords + " ]");
			}
			if (!StringUtil.isEmptyInteger(maxRtoRecords)) {
				LOGGER.debug("Fetching RTORecords From DB");
				// FETCHING DATA FROM CENTRAL DATABASE
				rtnOrgDOList = getRtoDataForBranchCode(branchCode,
						maxRtoRecords);
				LOGGER.debug("##Fetching Process Completed From DB Record (returnToOriginProcess), No Of Records:"
						+ ((rtnOrgDOList != null && !rtnOrgDOList.isEmpty()) ? rtnOrgDOList
								.size() : "0"));
			}

			if (rtnOrgDOList != null && !rtnOrgDOList.isEmpty()) {
				LOGGER.debug("ReturnToOrigin DO ListSize from Central DB = ["
						+ rtnOrgDOList.size() + " ]");
				createReturnToOriginXMLForBranch(rtnOrgDOList, branchFolder, extractorTo);
			}
			rtoExtractionDO = prepareExtractedData(branchCode, branchFolder,
					rtoExtractionDO, extractorTo);


			if (rtoExtractionDO != null && rtnOrgDOList != null && !rtnOrgDOList.isEmpty()) {
				/** Save ZIP File */
				centralDataExtractorDAO
				.saveExtractedDataForBranch(rtoExtractionDO, null);

				centralDataExtractorDAO
				.updateReturnToOriginWithReadByLocal(rtnOrgDOList);

				deleteBranchDirectory(branchFolder);

			}
		} catch (CGBusinessException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::returnToOriginProcess::CGBusinessException occured:"
					+e.getMessage());
			throw e;
		} catch (CGSystemException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::returnToOriginProcess::CGSystemException occured:"
					+e.getMessage());
			throw e;

		} catch (IOException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::returnToOriginProcess::IOException occured:"
					+e.getMessage());
			throw e;
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "returnToOriginProcess", UtilConstants.LOGGER_LEVEL_ERROR);
			LOGGER.error("CentralDataExtractorServiceImpl::returnToOriginProcess::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException();
		} finally {
			extractorTo = null;
			deleteBranchDirectory(branchFolder);
		}
	}

	/**
	 * Heldup release process.
	 *
	 * @param branchCode the branch code
	 * @param branchFolder the branch folder
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void heldupReleaseProcess(String branchCode, String branchFolder)
	throws  CGBusinessException,
	CGSystemException, IOException {
		
		List<HeldUpReleaseDO> heldUpReleaseDOList = null;
		DataExtractionDO heldupRelExtractionDO =null;
		Integer maxHeldupRecords = 10;
		DataExtractorTO extractorTo=new DataExtractorTO();
		
		try {
			String maxHRRecords = messageProp
			.getProperty(CentralDataExtractorConstant.MAX_FETCH_SIZE_HELDUP_RELEASE);
			LOGGER.debug("Max Heldup/Release Records to be fetched are (From Properties File )= [ "
					+ maxHRRecords + " ]");

			if (!StringUtils.isEmpty(maxHRRecords)) {
				maxHeldupRecords = Integer.parseInt(maxHRRecords);
				LOGGER.debug("Max Heldup/Release Records to be fetched are (After Conversion )= [ "
						+ maxHeldupRecords + " ]");
			} else {
				LOGGER.debug("Max Heldup/Release Records to be fetched are (Default values )= [ "
						+ maxHeldupRecords + " ]");
			}
			if (!StringUtil.isEmptyInteger(maxHeldupRecords)) {
				LOGGER.debug("Fetching Heldup Release Records From DB");
				heldUpReleaseDOList = getHeldUpDtlsByBranchCode(branchCode,
						maxHeldupRecords);
				LOGGER.debug("##Fetching Process Completed From DB Record (heldupReleaseProcess), No Of Records:"
						+ ((heldUpReleaseDOList != null && !heldUpReleaseDOList
								.isEmpty()) ? heldUpReleaseDOList.size() : "0"));
			}

			if (heldUpReleaseDOList != null && !heldUpReleaseDOList.isEmpty()) {
				LOGGER.debug("HeldUpRelease DO ListSize from Central DB = ["
						+ heldUpReleaseDOList.size() + " ]");
				createHeldUpXMLForBranch(heldUpReleaseDOList, branchFolder, extractorTo);
			}

			heldupRelExtractionDO = prepareExtractedData(branchCode, branchFolder,
					heldupRelExtractionDO, extractorTo);

			if (heldupRelExtractionDO != null && heldUpReleaseDOList != null && !heldUpReleaseDOList.isEmpty()) {
				/** Save ZIP File */
				centralDataExtractorDAO
				.saveExtractedDataForBranch(heldupRelExtractionDO, null);

				centralDataExtractorDAO
				.updateHeldUpWithReadByLocal(heldUpReleaseDOList);

				/** Delete the XML Files in the Branch Folder */
				deleteBranchDirectory(branchFolder);

			}
		}  catch (CGBusinessException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::CGBusinessException::Exception occured:"
					+e.getMessage());
			throw e;
		} catch (CGSystemException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::heldupReleaseProcess::CGSystemException occured:"
					+e.getMessage());
			throw e;

		} catch (IOException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::heldupReleaseProcess::IOException occured:"
					+e.getMessage());
			throw e;
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "bookingProcess", UtilConstants.LOGGER_LEVEL_ERROR);
			LOGGER.error("CentralDataExtractorServiceImpl::bookingProcess::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException();
		} finally {
			deleteBranchDirectory(branchFolder);
			extractorTo = null;
		}
	}

	/**
	 * Booking process.
	 *
	 * @param branchCode the branch code
	 * @param branchFolder the branch folder
	 * @throws CGBusinessException the cG business exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void bookingProcess(String branchCode, String branchFolder)
	throws  CGBusinessException, IOException {
		
		List<BookingDO> bookingDOList = null;
		DataExtractionDO bookingExtractionDO =null;
		Integer maxBookingRecords = 10;
		List<Integer> bookingIdList = null;
		DataExtractorTO extractorTo=new DataExtractorTO();
		try {
			String maxBookingFetch = messageProp
			.getProperty(CentralDataExtractorConstant.MAX_FETCH_SIZE_BOOKING);
			LOGGER.debug("Max Booking Records to be fetched are (From Properties File )= [ "
					+ maxBookingFetch + " ]");

			if (!StringUtils.isEmpty(maxBookingFetch)) {
				maxBookingRecords = Integer.parseInt(maxBookingFetch);
				LOGGER.debug("Max Booking Records to be fetched are (After Conversion )= [ "
						+ maxBookingRecords + " ]");
			} else {
				LOGGER.debug("Max Booking Records to be fetched are (Default values )= [ "
						+ maxBookingRecords + " ]");
			}
			if (!StringUtil.isEmptyInteger(maxBookingRecords)) {
				LOGGER.debug("Fetching Booking Records From DB");
				bookingDOList =	getBookingDataForBranchCode(branchCode,maxBookingRecords);
				LOGGER.debug("##Fetching Process Completed From DB Record (Booking), No Of Records:"
						+ ((bookingDOList != null && !bookingDOList
								.isEmpty()) ? bookingDOList.size() : "0"));
			
			}
			

			/** Create the XML files for Booking Data */
			if(bookingDOList != null && !bookingDOList.isEmpty()){
				LOGGER.trace("Toal Booking Records Found from DB are :["
						+ bookingDOList.size() + " ]");
				createBookingXMLForBranch(bookingDOList,branchFolder, extractorTo); 
				 bookingIdList = new ArrayList<Integer>(bookingDOList.size());
					for (BookingDO bookingDo :bookingDOList){
						LOGGER.trace("Toal Booking Records with to string :["+bookingDo.toString());
						bookingIdList.add(bookingDo.getBookingId());
					}
			}else{
				LOGGER.debug("Booking Xml Creation is not happened due to no record found in DB");
			}

			bookingExtractionDO = prepareExtractedData(branchCode, branchFolder,
					bookingExtractionDO,extractorTo);
		
			
			if (bookingExtractionDO != null && bookingIdList != null && !bookingIdList.isEmpty()) {
				/** Save ZIP File */
				centralDataExtractorDAO
				.saveExtractedDataForBranch(bookingExtractionDO, null);
				centralDataExtractorDAO
				.updateReadByLocalFlagInBooking(bookingIdList);
				deleteBranchDirectory(branchFolder);

			}
		}  catch (CGBusinessException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::bookingProcess::CGBusinessException occured:"
					+e.getMessage());
			throw e;
		}  catch (IOException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::bookingProcess::IOException occured:"
					+e.getMessage());
			throw e;
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "bookingProcess", UtilConstants.LOGGER_LEVEL_ERROR);
			LOGGER.error("CentralDataExtractorServiceImpl::bookingProcess::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException();
		} finally {
			extractorTo=null;
			deleteBranchDirectory(branchFolder);
		}
	}

	/**
	 * Prepare extracted data.
	 *
	 * @param branchCode the branch code
	 * @param branchFolder the branch folder
	 * @param dataExtractionDO the data extraction do
	 * @param extractorTo TODO
	 * @return the data extraction do
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private DataExtractionDO prepareExtractedData(String branchCode,
			String branchFolder, DataExtractionDO dataExtractionDO, DataExtractorTO extractorTo)
	throws IOException {
		/** Validate XML files are created for Branch */
		LOGGER.debug("prepareExtractedData ::-- branchCode :["+branchCode+"]" +"branchFolder :["+branchFolder+"]-----START");
		boolean xmlFilesCreated = checkFilesCreatedForBranch(branchFolder);
		LOGGER.debug("prepareExtractedData ::-- branchCode :["+branchCode+"]" +"branchFolder :["+branchFolder+"]-----is XmlFilesCreated ["+xmlFilesCreated+"]");
		if (xmlFilesCreated) {
			/** Zip the files in memory */
			byte[] inMemoryZipFile = ZipUtility
			.createInMemoryZipFile(branchFolder);

			/** Create the Extracted Data for save */
			dataExtractionDO = createDataExtractionDOForSave(
					inMemoryZipFile, branchCode, extractorTo);
		}
		
		LOGGER.debug("prepareExtractedData ::-- branchCode :["+branchCode+"]" +"branchFolder :["+branchFolder+"]----->END");
		return dataExtractionDO;
	}

	/**
	 * Delete branch directory.
	 *
	 * @param branchFolderPath the branch folder path
	 */
	private void deleteBranchDirectory(String branchFolderPath) {
		new File(branchFolderPath);
		try {
			FileUtils.renameFolder(branchFolderPath,branchFolderPath+ApplicationConstants.CHARACTER_UNDERSCORE+ApplicationConstants.DIR_PROCESSED);
		} catch (FolderRenamingException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::deleteBranchDirectory::Exception occured:"
					+e.getMessage());
		}
		
		
	}

	/**
	 * Check files created for branch.
	 *
	 * @param branchFolderPath the branch folder path
	 * @return true, if successful
	 */
	private boolean checkFilesCreatedForBranch(String branchFolderPath) {
		boolean fileExistsStatus = false;

		File branchFolder = new File(branchFolderPath);
		if (branchFolder.isDirectory()) {
			int noOfXMLFiles = branchFolder.listFiles().length;

			if (noOfXMLFiles > 0) {
				fileExistsStatus = true;
			}
		}

		return fileExistsStatus;
	}

	/**
	 * Creates the data extraction do for save.
	 *
	 * @param inMemoryZipFile the in memory zip file
	 * @param branchCode the branch code
	 * @param extractorTo the extractor to
	 * @return the data extraction do
	 */
	private DataExtractionDO createDataExtractionDOForSave(
			byte[] inMemoryZipFile, String branchCode, DataExtractorTO extractorTo) {

		LOGGER.debug("CentralDataExtractorServiceImpl : createDataExtractionDOForSave() : START");
		DataExtractionDO extractionDO = null;

		if (inMemoryZipFile != null) {
			extractionDO = new DataExtractionDO();
			extractionDO.setBranchCode(branchCode);
			extractionDO
			.setDataStatus(CentralDataExtractorConstant.EXTRACTED_DATA_UNREAD_STATUS);
			extractionDO.setExtractedDate(DateFormatterUtil.getCurrentDate());
			extractionDO.setExtractedData(inMemoryZipFile);
			extractionDO.setProcessName(extractorTo !=null && !StringUtil.isEmpty(extractorTo.getProcessName())?extractorTo.getProcessName().toUpperCase():null);
			extractionDO.setProcessFileName(extractorTo !=null && !StringUtil.isEmpty(extractorTo.getProcessFileName())?extractorTo.getProcessFileName().toUpperCase():null);
		}
		LOGGER.debug("CentralDataExtractorServiceImpl : createDataExtractionDOForSave() : END");
		return extractionDO;
	}

	/**
	 * Gets the outgoing manifets for branch.
	 *
	 * @param branchCode the branch code
	 * @param maxRecord the max record
	 * @return the outgoing manifets for branch
	 * @throws CGSystemException the cG system exception
	 */
	private List<ManifestDO> getOutgoingManifetsForBranch(String branchCode, Integer maxRecord)throws CGSystemException {
		LOGGER.debug("CentralDataExtractorServiceImpl : getOutgoingManifetsForBranch() : Start");
		List<ManifestDO> manifestDOList = centralDataExtractorDAO
		.getOutgoingManifestsForBranch(branchCode, maxRecord);
		LOGGER.debug("CentralDataExtractorServiceImpl : getOutgoingManifetsForBranch() : END");
		return manifestDOList;
	}



	/**
	 * Creates the manifest xml for branch.
	 *
	 * @param manifestDOList the manifest do list
	 * @param branchFolderPath the branch folder path
	 * @param dataExtractorTo the data extractor to
	 * @throws CGBusinessException the cG business exception
	 */
	private void createManifestXMLForBranch(List<ManifestDO> manifestDOList,
			String branchFolderPath, DataExtractorTO dataExtractorTo) throws CGBusinessException {
		try {
			// Create ManifestTO which will create XML for MANIFEST Data
			ManifestData jaxbObj = objectConverter
			.createdManifestTO(manifestDOList);

			if (jaxbObj != null) {


				String manifestXMLFileName = messageProp
				.getProperty(CentralDataExtractorConstant.MANIFEST_FILENAME);

				String dateTimeFormat = messageProp
				.getProperty(CentralDataExtractorConstant.XMLFILE_TIMESTAMP_FORMAT);

				File branchFolder = new File(branchFolderPath);
				if (!branchFolder.isDirectory()) {
					branchFolder.mkdirs();
				}

				String manifestXMLPath = CGXMLUtil.createFileNameWithTimestamp(
						branchFolder.getAbsolutePath() + File.separator
						+ manifestXMLFileName, dateTimeFormat,
						ApplicationConstants.CHARACTER_UNDERSCORE);

				/** Create the XML for Manifest */
				populateProcessInfo(manifestXMLPath,dataExtractorTo);
				new CTBSXMLParser()
				.marshalObjectToXML(jaxbObj, manifestXMLPath);

			}
		} catch (Exception e) {
			LOGGER.error("CentralDataExtractorServiceImpl::createManifestXMLForBranch::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}
	}

	/**
	 * Creates the booking xml for branch.
	 *
	 * @param bookingDOList the booking do list
	 * @param branchFolderPath the branch folder path
	 * @param extractorTo the extractor to
	 * @throws CGBusinessException the cG business exception
	 */
	private void createBookingXMLForBranch(List<BookingDO> bookingDOList,
			String branchFolderPath, DataExtractorTO extractorTo) throws CGBusinessException {
		try {
			LOGGER.debug("CentralDataExtractorServiceImpl : createBookingXMLForBranch():START for the path ["+branchFolderPath+"]");
			LOGGER.debug("CentralDataExtractorServiceImpl : createBookingXMLForBranch(): JAX -B about to created");
			LOGGER.debug("CentralDataExtractorServiceImpl : createBookingXMLForBranch(): objectConverter object "+objectConverter);
			BookingDetailsData bookingDataTO = objectConverter
			.createdBookingJaxObj(bookingDOList);
			LOGGER.debug("CentralDataExtractorServiceImpl : createBookingXMLForBranch(): JAX -B created");
			if (bookingDataTO != null) {

				
				String bookingXMLFileName = messageProp
				.getProperty(CentralDataExtractorConstant.BOOKING_FILENAME);
				LOGGER.debug("CentralDataExtractorServiceImpl : createBookingXMLForBranch(): bookingXMLFileName"+bookingXMLFileName);
				String dateTimeFormat = messageProp
				.getProperty(CentralDataExtractorConstant.XMLFILE_TIMESTAMP_FORMAT);
				LOGGER.debug("CentralDataExtractorServiceImpl : createBookingXMLForBranch(): dateTimeFormat"+dateTimeFormat);
				LOGGER.debug("CentralDataExtractorServiceImpl : createBookingXMLForBranch(): Folder is about  to created");
				LOGGER.debug("CentralDataExtractorServiceImpl : createBookingXMLForBranch(): Folder is about  to created wit path "+branchFolderPath);
				File branchFolder = new File(branchFolderPath);
				if (!branchFolder.isDirectory()) {
					LOGGER.debug("CentralDataExtractorServiceImpl : createBookingXMLForBranch(): Folder is about  to created wit path  folder is created");
					branchFolder.mkdirs();
				}
				LOGGER.debug("CentralDataExtractorServiceImpl : createBookingXMLForBranch(): Folder is about  to created wit path  folder created"+branchFolderPath);
				String bookingXMLPath = CGXMLUtil.createFileNameWithTimestamp(
						branchFolder.getAbsolutePath() + File.separator
						+ bookingXMLFileName, dateTimeFormat,
						ApplicationConstants.CHARACTER_UNDERSCORE);
				populateProcessInfo(bookingXMLPath,extractorTo);
				LOGGER.debug("CentralDataExtractorServiceImpl : createBookingXMLForBranch(): Booking Xml path ["+bookingXMLPath+"] start" );
				/** Create the XML for Booking */

				new CTBSXMLParser().marshalObjectToXML(bookingDataTO,
						bookingXMLPath);

				LOGGER.debug("CentralDataExtractorServiceImpl : createBookingXMLForBranch(): CTBSXMLParser["+bookingXMLPath+"] end");
			}
		} catch (Exception e) {
			LOGGER.error("CentralDataExtractorServiceImpl::createBookingXMLForBranch::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}
		
		LOGGER.debug("CentralDataExtractorServiceImpl : createBookingXMLForBranch():END");
	}

//	}
//	private String getProcessName(String processXMLPath) {
//		String fileName =processXMLPath.substring(processXMLPath.lastIndexOf(File.separator)+1);
//		return (!StringUtil.isEmpty(fileName)?fileName.substring(0,fileName.indexOf(ApplicationConstants.CHARACTER_UNDERSCORE)):null);
//	}
	/**
 * Populate process info.
 *
 * @param processXMLPath the process xml path
 * @param extractorTo the extractor to
 */
private void populateProcessInfo(String processXMLPath,DataExtractorTO extractorTo) {
		if(extractorTo!=null && !StringUtil.isEmpty(processXMLPath)){
			String processFileName = processXMLPath.substring(processXMLPath.lastIndexOf(File.separator)+1);
			if(!StringUtil.isEmpty(processFileName)){
				extractorTo.setProcessFileName(processFileName);
				extractorTo.setProcessName((!StringUtil.isEmpty(processFileName)?processFileName.substring(0,processFileName.indexOf(ApplicationConstants.CHARACTER_UNDERSCORE)):null));
			}
		}
	}
	

	/**
	 * Creates the goods cancl xml for branch.
	 *
	 * @param goodsCanclDOList the goods cancl do list
	 * @param branchFolderPath the branch folder path
	 * @param isFrModule the is fr module
	 * @throws CGBusinessException the cG business exception
	 */
	private void createGoodsCanclXMLForBranch(
			List<GoodscCancellationDO> goodsCanclDOList, String branchFolderPath,Boolean isFrModule)
	throws CGBusinessException {
		LOGGER.debug("CentralDataExtractorServiceImpl : createGoodsCanclXMLForBranch():START");
		try {
			// Create Goods Cancl jax obj which will create XML for Goods
			// Cancellation Data

			if (goodsCanclDOList != null && !goodsCanclDOList.isEmpty()) {

				GoodsCancellationData jaxbObj = new GoodsCancellationData();

				jaxbObj = objectConverter
				.createdGoodsCanclJaxObj(goodsCanclDOList);
				LOGGER.debug("CentralDataExtractorServiceImpl : createGoodsCanclXMLForBranch() :CREATING JAXB OBJ ");
				String goodsCanclXMLFileName = messageProp
				.getProperty(CentralDataExtractorConstant.GOODS_CANCELLATION_FILENAME);

				
				if(isFrModule){
					goodsCanclXMLFileName = converToFRModuleFileName(goodsCanclXMLFileName);
					
				}
				String dateTimeFormat = messageProp
				.getProperty(CentralDataExtractorConstant.XMLFILE_TIMESTAMP_FORMAT);

				File branchFolder = new File(branchFolderPath);
				if (!branchFolder.isDirectory()) {
					branchFolder.mkdirs();
				}

				String goodsCanclXMLPath = CGXMLUtil
				.createFileNameWithTimestamp(
						branchFolder.getAbsolutePath() + File.separator
						+ goodsCanclXMLFileName,
						dateTimeFormat,
						ApplicationConstants.CHARACTER_UNDERSCORE);
				LOGGER.debug("CentralDataExtractorServiceImpl : createGoodsRenewalXMLForBranch() :GOODS CANCELLATION XML PATH : "
						+ goodsCanclXMLPath);
				/** Create the XML for GOODS CANCELLATION */
				new CTBSXMLParser().marshalObjectToXML(jaxbObj,
						goodsCanclXMLPath);
				LOGGER.debug("CentralDataExtractorServiceImpl : createGoodsCanclXMLForBranch():END");
			}
		} catch (Exception e) {
			LOGGER.error("CentralDataExtractorServiceImpl : createGoodsCanclXMLForBranch() : Error while creating XML for Goods Cancellation..."
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
	}

	/**
	 * Creates the dispatch xml for branch.
	 *
	 * @param dispatchDOList the dispatch do list
	 * @param branchFolderPath the branch folder path
	 * @param extractorTo the extractor to
	 * @throws CGBusinessException the cG business exception
	 */
	private void createDispatchXMLForBranch(List<DispatchDO> dispatchDOList,
			String branchFolderPath, DataExtractorTO extractorTo) throws CGBusinessException {
		LOGGER.debug("CentralDataExtractorServiceImpl : createDispatchXMLForBranch():START");
		DispatchDetailsData jaxbObj = null;
		try {
		
			jaxbObj = objectConverter.createdDispatchJaxObj(dispatchDOList);

			if (jaxbObj != null) {
				String dispatchXMLFileName = messageProp
				.getProperty(CentralDataExtractorConstant.DISPATCH_DETAILS_FILENAME);

				String dateTimeFormat = messageProp
				.getProperty(CentralDataExtractorConstant.XMLFILE_TIMESTAMP_FORMAT);

				File branchFolder = new File(branchFolderPath);
				if (!branchFolder.isDirectory()) {
					branchFolder.mkdirs();
				}

				String dispatchXMLPath = CGXMLUtil.createFileNameWithTimestamp(
						branchFolder.getAbsolutePath() + File.separator
						+ dispatchXMLFileName, dateTimeFormat,
						ApplicationConstants.CHARACTER_UNDERSCORE);

				populateProcessInfo(dispatchXMLPath,extractorTo);
				/** Create the XML for Booking */
				new CTBSXMLParser()
				.marshalObjectToXML(jaxbObj, dispatchXMLPath);
				LOGGER.debug("CentralDataExtractorServiceImpl : createDispatchXMLForBranch():END");
			}
		} catch (Exception e) {
			LOGGER.error("CentralDataExtractorServiceImpl : createDispatchXMLForBranch() : Error while creating XML for DispatchDO ..."
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
	}

	/**
	 * Creates the goods renewal xml for branch.
	 * 
	 * @param goodsRenewalDOList
	 *            the goods renewal do list
	 * @param branchFolderPath
	 *            the branch folder path
	 * @param isFrModule TODO
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	private void createGoodsRenewalXMLForBranch(
			List<GoodsRenewalDO> goodsRenewalDOList, String branchFolderPath, Boolean isFrModule)
	throws CGBusinessException {
		LOGGER.debug("CentralDataExtractorServiceImpl : createGoodsRenewalXMLForBranch() :START");
		try {
			// Create Goods Renewal jax obj which will create XML for Goods
			// Renewal Data
			if (goodsRenewalDOList != null && !goodsRenewalDOList.isEmpty()) {

				GoodsRenewalData jaxbObj = new GoodsRenewalData();
				LOGGER.debug("CentralDataExtractorServiceImpl : createGoodsRenewalXMLForBranch() :CREATING JAXB OBJ ");
				jaxbObj = objectConverter
				.createdGoodsRenewalJaxObj(goodsRenewalDOList);

				String goodsRenewalXMLFileName = messageProp
				.getProperty(CentralDataExtractorConstant.GOODS_RENEWAL_FILENAME);
				
				if(isFrModule){
					goodsRenewalXMLFileName = converToFRModuleFileName(goodsRenewalXMLFileName);
					
				}
				String dateTimeFormat = messageProp
				.getProperty(CentralDataExtractorConstant.XMLFILE_TIMESTAMP_FORMAT);

				File branchFolder = new File(branchFolderPath);
				if (!branchFolder.isDirectory()) {
					branchFolder.mkdirs();
				}

				String goodsRenewalXMLPath = CGXMLUtil
				.createFileNameWithTimestamp(
						branchFolder.getAbsolutePath() + File.separator
						+ goodsRenewalXMLFileName,
						dateTimeFormat,
						ApplicationConstants.CHARACTER_UNDERSCORE);

				/** Create the XML for GOODS RENEWAL */
				LOGGER.debug("CentralDataExtractorServiceImpl : createGoodsRenewalXMLForBranch() :GOODS RENEWAL XML PATH : "
						+ goodsRenewalXMLPath);
				new CTBSXMLParser().marshalObjectToXML(jaxbObj,
						goodsRenewalXMLPath);
				LOGGER.debug("CentralDataExtractorServiceImpl : createGoodsRenewalXMLForBranch():END");
			}
		} catch (Exception e) {
			LOGGER.error("CentralDataExtractorServiceImpl : createGoodsRenewalXMLForBranch() : Error while creating XML for Goods Cancellation..."
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
	}

	/**
	 * Conver to fr module file name.
	 *
	 * @param goodsRenewalXMLFileName the goods renewal xml file name
	 * @return the string
	 */
	private String converToFRModuleFileName(String goodsRenewalXMLFileName) {
		StringBuffer strFileName = new StringBuffer(goodsRenewalXMLFileName);
		strFileName.insert(goodsRenewalXMLFileName.lastIndexOf(ApplicationConstants.CHARACTER_DOT),CentralDataExtractorConstant.FR_MODULE );
	return strFileName.toString();
	}

	/**
	 * Gets the held up dtls by branch code.
	 *
	 * @param branchCode the branch code
	 * @param maxRecords the max records
	 * @return the held up dtls by branch code
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<HeldUpReleaseDO> getHeldUpDtlsByBranchCode(String branchCode,
			Integer maxRecords) throws CGBusinessException, CGSystemException {
		LOGGER.info("CentralDataExtractorServiceImpl : getHeldUpDtlsByBranchCode():START");
		List<HeldUpReleaseDO> heldUpDOList = centralDataExtractorDAO
		.getHeldUpDtlsByBranchCode(branchCode, maxRecords);
		LOGGER.info("CentralDataExtractorServiceImpl : getHeldUpDtlsByBranchCode():END");
		return heldUpDOList;
	}

	/**
	 * Gets the goods renewal data for branch code.
	 *
	 * @param branchList the branch list
	 * @param maxSize the max size
	 * @return the goods renewal data for branch code
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private List<GoodsRenewalDO> getGoodsRenewalDataForBranchCode(
			List<String> branchList, Integer maxSize) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("CentralDataExtractorServiceImpl : getGoodsRenewalDataForBranchCode():START");
		List<GoodsRenewalDO> goodsRenewalDOList = centralDataExtractorDAO
		.getGoodsRenewalDataByBranchCode(branchList, maxSize);
		LOGGER.debug("CentralDataExtractorServiceImpl : getGoodsRenewalDataForBranchCode():END");
		return goodsRenewalDOList;
	}
	

	/**
	 * Gets the goods cancl data for branch code.
	 *
	 * @param officesList the offices list
	 * @param maxSize the max size
	 * @return the goods cancl data for branch code
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private List<GoodscCancellationDO> getGoodsCanclDataForBranchCode(
			List<String> officesList, Integer maxSize) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("CentralDataExtractorServiceImpl : getGoodsCanclDataForBranchCode():START");
		List<GoodscCancellationDO> goodsCanclDOList = centralDataExtractorDAO
		.getGoodsCanclDataByBranchCode(officesList, maxSize);
		LOGGER.debug("CentralDataExtractorServiceImpl : getGoodsCanclDataForBranchCode():END");
		return goodsCanclDOList;
	}
	
	/**
	 * Gets the goods cancl data for fr module.
	 *
	 * @param frList the fr list
	 * @param maxSize the max size
	 * @return the goods cancl data for fr module
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private List<GoodscCancellationDO> getGoodsCanclDataForFRModule(
			List<String> frList, Integer maxSize) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("CentralDataExtractorServiceImpl : getGoodsCanclDataForBranchCode():START");
		List<GoodscCancellationDO> goodsCanclDOList = centralDataExtractorDAO.getGoodsCanclDataForFrModule(frList, maxSize);
		LOGGER.debug("CentralDataExtractorServiceImpl : getGoodsCanclDataForBranchCode():END");
		return goodsCanclDOList;
	}

	/**
	 * Gets the dispatch data by branch code.
	 *
	 * @param branchCode the branch code
	 * @param maxRecord the max record
	 * @return the dispatch data by branch code
	 * @throws CGSystemException the cG system exception
	 */
	private List<DispatchDO> getDispatchDataByBranchCode(String branchCode,
			Integer maxRecord)throws CGSystemException {
		List<DispatchDO> dispatchDOList = centralDataExtractorDAO
		.getDispatchDataByBranchCode(branchCode, maxRecord);
		return dispatchDOList;
	}

	/**
	 * Added by Narasimha Rao Kattunga Getting delivery manifest details by
	 * branch code.
	 *
	 * @param branchCode the branch code
	 * @param maxRecords the max records
	 * @return the delivery mnfst dtls by branch code
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<DeliveryDO> getDeliveryMnfstDtlsByBranchCode(String branchCode,
			Integer maxRecords) throws CGBusinessException, CGSystemException {
		LOGGER.info("CentralDataExtractorServiceImpl : getDeliveryMnfstDtlsByBranchCode():START");
		List<DeliveryDO> dlvMnfstDOList = centralDataExtractorDAO
		.getDeliveryManifestDtlsByBranchCode(branchCode, maxRecords);
		LOGGER.info("CentralDataExtractorServiceImpl : getDeliveryMnfstDtlsByBranchCode():END");
		return dlvMnfstDOList;
	}

	/**
	 * Creates the held up xml for branch.
	 *
	 * @param heldUpDOList the held up do list
	 * @param branchFolderPath the branch folder path
	 * @param extractorTo the extractor to
	 * @throws CGBusinessException the cG business exception
	 */
	private void createHeldUpXMLForBranch(List<HeldUpReleaseDO> heldUpDOList,
			String branchFolderPath, DataExtractorTO extractorTo) throws CGBusinessException {
		try {
			// createHeldUpXMLForBranch which will create XML for
			// createHeldUpData
			HeldupReleaseData jaxbObj = objectConverter
			.createdHeldUpReleaseJaxObj(heldUpDOList);

			if (jaxbObj != null) {
				String heldUpXMLFileName = messageProp
				.getProperty(CentralDataExtractorConstant.HELDUP_FILENAME);

				String dateTimeFormat = messageProp
				.getProperty(CentralDataExtractorConstant.XMLFILE_TIMESTAMP_FORMAT);

				File branchFolder = new File(branchFolderPath);
				if (!branchFolder.isDirectory()) {
					branchFolder.mkdirs();
				}

				String heldUpXMLPath = CGXMLUtil.createFileNameWithTimestamp(
						branchFolder.getAbsolutePath() + File.separator
						+ heldUpXMLFileName, dateTimeFormat,
						ApplicationConstants.CHARACTER_UNDERSCORE);
				populateProcessInfo(heldUpXMLPath,extractorTo);
				/** Create the XML for Heldup */
				new CTBSXMLParser().marshalObjectToXML(jaxbObj, heldUpXMLPath);

			}
		} catch (Exception e) {
			LOGGER.error("CentralDataExtractorServiceImpl::createHeldUpXMLForBranch::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}
	}

	/**
	 * Creates the goods issue xml for branch.
	 *
	 * @param goodsIssueDoList the goods issue do list
	 * @param branchFolderPath the branch folder path
	 * @param isFrModule the is fr module
	 * @throws CGBusinessException the cG business exception
	 */
	private void createGoodsIssueXMLForBranch(
			List<GoodsIssueDO> goodsIssueDoList, String branchFolderPath,Boolean isFrModule)
	throws CGBusinessException {
		try {
			LOGGER.debug("CentralDataExtractorServiceImpl :createGoodsIssueXMLForBranch   ####START");
			// Create Goodsissue which will create XML for Goodsissue Data
			GoodsIssueData jaxbObj = objectConverter
			.createdGoodsIssueJaxObj(goodsIssueDoList);
			LOGGER.debug("CentralDataExtractorServiceImpl :createGoodsIssueXMLForBranch jaxbObj created");
			if (jaxbObj != null) {
				String goodsXMLFileName = messageProp
				.getProperty(CentralDataExtractorConstant.GOODS_ISSUE_FILENAME);

				
				if(isFrModule){
					goodsXMLFileName = converToFRModuleFileName(goodsXMLFileName);
					
				}
				String dateTimeFormat = messageProp
				.getProperty(CentralDataExtractorConstant.XMLFILE_TIMESTAMP_FORMAT);

				File branchFolder = new File(branchFolderPath);
				if (branchFolder != null && !branchFolder.exists()) {
					branchFolder.mkdirs();
				}
				LOGGER.debug(" Branch file creation ...Folder path :"
						+ branchFolder);
				String goodsIssueXMLPath = CGXMLUtil
				 .createFileNameWithTimestamp(
						 branchFolder.getAbsolutePath() + File.separator
						 + goodsXMLFileName, dateTimeFormat,
						 ApplicationConstants.CHARACTER_UNDERSCORE);
				 LOGGER.debug("CentralDataExtractorServiceImpl :createGoodsIssueXMLForBranch preparing XML");
				 /** Create the XML for Goods Issue */
				 new CTBSXMLParser().marshalObjectToXML(jaxbObj,
						 goodsIssueXMLPath);

			}
		} catch (Exception e) {
			LOGGER.error("CentralDataExtractorServiceImpl::createGoodsIssueXMLForBranch::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("CentralDataExtractorServiceImpl :createGoodsIssueXMLForBranch   ####END");
	}

	/**
	 * Gets the goods issue data for branch code.
	 *
	 * @param branchCodeList the branch code list
	 * @param maxSize the max size
	 * @return the goods issue data for branch code
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private List<GoodsIssueDO> getGoodsIssueDataForBranchCode(
			List branchCodeList, Integer maxSize) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("CentralDataExtractorServiceImpl : getGoodsIssueDataForBranchCode():START");
		List<GoodsIssueDO> goodsIssueDoList = centralDataExtractorDAO
		.getGoodsIssueDataByBranchCode(branchCodeList, maxSize);
		LOGGER.debug("CentralDataExtractorServiceImpl : getGoodsIssueDataForBranchCode():END");
		return goodsIssueDoList;
	}
	
	/**
	 * Gets the goods issue data for fr module.
	 *
	 * @param frListList the fr list list
	 * @param maxSize the max size
	 * @return the goods issue data for fr module
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private List<GoodsIssueDO> getGoodsIssueDataForFRModule(
			List<String> frListList, Integer maxSize) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("CentralDataExtractorServiceImpl : getGoodsIssueDataForFRModule():START");
		List<GoodsIssueDO> goodsIssueDoList = centralDataExtractorDAO
		.getGoodsIssueDataForFrModule(frListList, maxSize);
		LOGGER.debug("CentralDataExtractorServiceImpl : getGoodsIssueDataForFRModule():END");
		return goodsIssueDoList;
	}

	/**
	 * Creates the return to origin xml for branch.
	 *
	 * @param rtnOrgDOList the rtn org do list
	 * @param branchFolderPath the branch folder path
	 * @param dataExtractorTo the data extractor to
	 * @throws CGBusinessException the cG business exception
	 */
	private void createReturnToOriginXMLForBranch(
			List<RtnToOrgDO> rtnOrgDOList, String branchFolderPath, DataExtractorTO dataExtractorTo)
	throws CGBusinessException {
		try {
			// Create Goodsissue which will create XML for Goodsissue Data
			RtoData jaxbObj = objectConverter
			.createdReturnToOriginJaxObj(rtnOrgDOList);
			if (jaxbObj != null) {
				String returnToOriginXMLFileName = messageProp
				.getProperty(CentralDataExtractorConstant.RETURN_TO_ORIGIN_FILENAME);

				String dateTimeFormat = messageProp
				.getProperty(CentralDataExtractorConstant.XMLFILE_TIMESTAMP_FORMAT);

				File branchFolder = new File(branchFolderPath);
				if (!branchFolder.isDirectory()) {
					branchFolder.mkdirs();
				}

				String returnToOriginXMLPath = CGXMLUtil
				.createFileNameWithTimestamp(
						branchFolder.getAbsolutePath() + File.separator
						+ returnToOriginXMLFileName,
						dateTimeFormat,
						ApplicationConstants.CHARACTER_UNDERSCORE);

				populateProcessInfo(returnToOriginXMLPath,dataExtractorTo);
				// ** Create the XML for Goods Issue *//*
				new CTBSXMLParser().marshalObjectToXML(jaxbObj,
						returnToOriginXMLPath);

			}
		} catch (Exception e) {
			LOGGER.error("CentralDataExtractorServiceImpl::createReturnToOriginXMLForBranch::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}
	}

	/**
	 * Gets the rto data for branch code.
	 *
	 * @param branchCode the branch code
	 * @param maxSize the max size
	 * @return the rto data for branch code
	 * @throws CGSystemException the cG system exception
	 */
	private List<RtnToOrgDO> getRtoDataForBranchCode(String branchCode,
			Integer maxSize) throws CGSystemException {
		List<RtnToOrgDO> rtnToOrgDOList = centralDataExtractorDAO
		.getRtoDataForBranchCode(branchCode, maxSize);
		return rtnToOrgDOList;
	}

	/**
	 * Gets the booking data for branch code.
	 *
	 * @param branchCode the branch code
	 * @param maxSize the max size
	 * @return the booking data for branch code
	 * @throws CGSystemException the cG system exception
	 */
	private List<BookingDO> getBookingDataForBranchCode(String branchCode,
			Integer maxSize)throws CGSystemException {
		LOGGER.debug("CentralDataExtractorServiceImpl : getBookingDataForBranchCode() : START");
		List<BookingDO> bookingDOList = null;
		try {
			bookingDOList = centralDataExtractorDAO
			.getBookingDetailsByOfficeCode(branchCode, maxSize);
		} catch (CGSystemException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::getBookingDataForBranchCode::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("CentralDataExtractorServiceImpl : getBookingDataForBranchCode() : END");
		LOGGER.debug(branchCode, "BookingDoList Size : " + bookingDOList !=null?bookingDOList.size(): null);
		
		return bookingDOList;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.service.CentralDataExtractorService#extractGoodsIssueDataForAllBranches()
	 */
	@Override
	public void extractGoodsIssueDataForAllBranches()
	throws CGBusinessException {
		LOGGER.debug("CentralDataExtractorServiceImpl : extractGoodsIssueDataForAllBranches() : START");

		String branchFolder = "";
		try {

			/** Get all the branches */
			List<String> officeCodeList = centralDataExtractorDAO
			.getAllOfficeCodes();

			for (String branchCode : officeCodeList) {

				branchFolder = extractingGoodsIssueDataForBranch(branchCode);
			}

		} catch (IOException ex) {
			LOGGER.error("CentralDataExtractorServiceImpl::extractGoodsIssueDataForAllBranches::Exception occured:"
					+ex.getMessage());
		} catch (Exception ex) {
			CGStackTraceUtil.getStackTraceException(ex,  LOGGER, "extractGoodsIssueDataForAllBranches", UtilConstants.LOGGER_LEVEL_ERROR);
			LOGGER.error("CentralDataExtractorServiceImpl::extractGoodsIssueDataForAllBranches::Exception occured:"
					+ex.getMessage());
			throw new CGBusinessException();
		} finally {
			deleteBranchDirectory(branchFolder);
		}
		LOGGER.debug("CentralDataExtractorServiceImpl : extractGoodsIssueDataForAllBranches() : END");

	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.service.CentralDataExtractorService#extractingGoodsIssueDataForBranch(String)
	 */
	@Override
	public String extractingGoodsIssueDataForBranch(String branchCode)
	throws CGBusinessException, CGSystemException, IOException {
		
		List<String> officesList = getAllOfficesForGoodsProcess(branchCode); 
		String branchFolder;
		branchFolder = messageProp
		.getProperty(CentralDataExtractorConstant.XML_FILE_BASE_MULTIPLE_LOCATION)
		+ File.separator + branchCode+File.separator+branchCode+ApplicationConstants.CHARACTER_UNDERSCORE+StringUtil.generateRamdomNumber();
		// The following constants for no of records to be fetched from each
		// process (configurable ie. taking the values from
		// properties(centralDataExtraction.properties) file)
		
		try {
		
		LOGGER.debug("Data Extract for Branch = [ " + branchCode + " ]");

		//######################################Goods Issue###################### START
		List<GoodsIssueDO> goodsIssueDOList = null;
		Integer maxGoodsIssueRecords = 10;
		String maxIssueRecords = messageProp
		.getProperty(CentralDataExtractorConstant.MAX_FETCH_SIZE_GOODS_ISSUE);

		LOGGER.debug("Max goods issue Records to be fetched are (From Properties File )= [ "
				+ maxIssueRecords + " ]");

		if (!StringUtils.isEmpty(maxIssueRecords)) {
			maxGoodsIssueRecords = Integer.parseInt(maxIssueRecords);
			LOGGER.debug("Max goods issue Records to be fetched are (After Conversion )= [ "
					+ maxGoodsIssueRecords + " ]");
		} else {
			LOGGER.debug("Max goods issue Records to be fetched are (Default values )= [ "
					+ maxGoodsIssueRecords + " ]");
		}

		if (!StringUtil.isEmptyInteger(maxGoodsIssueRecords)) {
			LOGGER.debug("Fetching Goods Issue Records From DB");
			goodsIssueDOList = getGoodsIssueDataForBranchCode(officesList,
					maxGoodsIssueRecords);
			LOGGER.debug("##Fetching Process(Goods Issue) Completed From DB Record ,Total Records Are :"
					+ ((goodsIssueDOList != null && !goodsIssueDOList.isEmpty()) ? goodsIssueDOList
							.size() : "0"));
		}
		
		if (goodsIssueDOList != null && !goodsIssueDOList.isEmpty()) {
			LOGGER.debug("Goodsissue DO ListSize from Central DB = ["
					+ goodsIssueDOList.size() + " ]");
			createGoodsIssueXMLForBranch(goodsIssueDOList, branchFolder,false);
		}
		
		//######################################Goods Issue###################### END
		
		//######################################Goods Cancellation###################### START
		
		List<GoodscCancellationDO> goodsCanclDOList = null;
		Integer maxGoodsCancelRecords = 10;
		String maxGoodsCanclRecords = messageProp
		.getProperty(CentralDataExtractorConstant.MAX_FETCH_SIZE_GOODS_CANCELLATION);
		if (!StringUtils.isEmpty(maxGoodsCanclRecords)) {
			maxGoodsCancelRecords = Integer.parseInt(maxGoodsCanclRecords);
			LOGGER.debug("Max goods Cancellation Records to be fetched are (After Conversion )= [ "
					+ maxGoodsCancelRecords + " ]");
		} else {
			LOGGER.debug("Max goods goods Cancellation Records to be fetched are (Default values )= [ "
					+ maxGoodsCancelRecords + " ]");
		}

		if (!StringUtil.isEmptyInteger(maxGoodsCancelRecords)) {
			LOGGER.debug("Fetching Goods Cancellation Records From DB");
			// FETCHING DATA FROM CENTRAL DATABASE
			goodsCanclDOList = getGoodsCanclDataForBranchCode(officesList,
					maxGoodsCancelRecords);
			LOGGER.debug("Fetching Process Completed From DB Record ,Total Records Are :"
					+ ((goodsCanclDOList != null && !goodsCanclDOList.isEmpty()) ? goodsCanclDOList
							.size() : "0"));
		}
		
		
		/** Create the XML files for Goods Cancellation Data */
		if (goodsCanclDOList != null && !goodsCanclDOList.isEmpty()
				&& goodsCanclDOList.size() > 0) {
			LOGGER.debug("Goods Cancellation Data Size from Central DB = ["
					+ goodsCanclDOList.size() + " ]");
			createGoodsCanclXMLForBranch(goodsCanclDOList, branchFolder,false);
		}
		
		//######################################Goods Cancellation###################### END
		
		//######################################Goods Renewal###################### START
		
		List<GoodsRenewalDO> goodsRenewalDOList = null;
		Integer maxGoodsRenewalRecords = 10;
		
		String maxGoodsRenewalSize = messageProp
		.getProperty(CentralDataExtractorConstant.MAX_FETCH_SIZE_GOODS_RENEWAL);
		
		if (!StringUtils.isEmpty(maxGoodsRenewalSize)) {
			maxGoodsRenewalRecords = Integer.parseInt(maxGoodsRenewalSize);
			LOGGER.debug("Max goods Renewal Records to be fetched are (After Conversion )= [ "
					+ maxGoodsRenewalRecords + " ]");
		} else {
			LOGGER.debug("Max goods goods Renewal Records to be fetched are (Default values )= [ "
					+ maxGoodsRenewalRecords + " ]");
		}

		if (!StringUtil.isEmptyInteger(maxGoodsRenewalRecords)) {
			LOGGER.debug("Fetching Goods Renewal Records From DB");
			// FETCHING DATA FROM CENTRAL DATABASE
			goodsRenewalDOList = getGoodsRenewalDataForBranchCode(officesList,
					maxGoodsRenewalRecords);
			LOGGER.debug("Fetching Process Completed From DB Record ,Total Records Are :"
					+ ((goodsRenewalDOList != null && !goodsRenewalDOList
							.isEmpty()) ? goodsRenewalDOList.size() : "0"));
		}
		/** Create the XML files for Goods Renewal Data */
		if (goodsRenewalDOList != null && !goodsRenewalDOList.isEmpty()
				&& goodsRenewalDOList.size() > 0) {
			LOGGER.debug("Goods Renewal Data Size from Central DB = ["
					+ goodsRenewalDOList.size() + " ]");
			createGoodsRenewalXMLForBranch(goodsRenewalDOList, branchFolder, false);
		}

		//######################################Goods Renewal###################### END

			
			/** Validate XML files are created for Branch */
			boolean xmlFilesCreated = checkFilesCreatedForBranch(branchFolder);
			if (xmlFilesCreated) {
				/** Zip the files in memory */
				byte[] inMemoryZipFile = ZipUtility
				.createInMemoryZipFile(branchFolder);
				/** Create the Extracted Data for save */
				List<DataExtractionDO> dataExtractionDoList = new ArrayList<DataExtractionDO>();
				if (inMemoryZipFile != null && inMemoryZipFile.length > 0) {
					if (officesList != null && !officesList.isEmpty()) {
						for (String officeCode : officesList) {
							DataExtractionDO dataExtractionDO = createDataExtractionDOForSave(
									inMemoryZipFile, officeCode, null);
							dataExtractionDO.setProcessName(CentralDataExtractorConstant.GOODS_ISSUE_PROCESS);
							dataExtractionDoList.add(dataExtractionDO);
						}
					} else {
						DataExtractionDO dataExtractionDO = createDataExtractionDOForSave(
								inMemoryZipFile, branchCode, null);
						dataExtractionDO.setProcessName(CentralDataExtractorConstant.GOODS_ISSUE_PROCESS);
						dataExtractionDoList.add(dataExtractionDO);
					}
				}
				LOGGER.debug("Data prepared for the Office = [ " + branchCode
						+ " ]");
				if (dataExtractionDoList != null
						&& !dataExtractionDoList.isEmpty()) {
					/** Save ZIP File */
					centralDataExtractorDAO
					.saveExtractedDataForBranchList(dataExtractionDoList);

					/**
					 * Update the Central DB Data Status as Read By Local -
					 * GOODS RENEWAL / CANCELLATION - PURCHASE MODULE
					 */
					centralDataExtractorDAO
					.updateGoodsRenewalWithReadByLocal(goodsRenewalDOList);
					centralDataExtractorDAO
					.updateGoodsCanclWithReadByLocal(goodsCanclDOList);
					centralDataExtractorDAO
					.updateGoodsIssueWithReadByLocal(goodsIssueDOList);
					/** Delete the XML Files in the Branch Folder */
					deleteBranchDirectory(branchFolder);

				}
			}
		} catch (CGBusinessException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::extractingGoodsIssueDataForBranch::Exception occured:"
					+e.getMessage());
			throw e;
		} catch (CGSystemException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::extractingGoodsIssueDataForBranch::Exception occured:"
					+e.getMessage());
			throw e;

		} catch (IOException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::extractingGoodsIssueDataForBranch::Exception occured:"
					+e.getMessage());
			throw e;
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "extractingGoodsIssueDataForBranch", UtilConstants.LOGGER_LEVEL_ERROR);
			LOGGER.error("CentralDataExtractorServiceImpl::extractingGoodsIssueDataForBranch::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException();
		} finally {
			deleteBranchDirectory(branchFolder);
		}
		return branchFolder;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.service.CentralDataExtractorService#getAllOfficesForGoodsProcess(String)
	 */
	@Override
	public List<String> getAllOfficesForGoodsProcess(String branchCode)
	throws CGBusinessException, CGSystemException {
		OfficeDO officeDetails = centralDataExtractorDAO
		.getOfficeDetailsByBranchCode(branchCode);
		LOGGER.debug("########CentralDataExtractorServiceImpl ::getAllOfficesForGoodsProcess :: Input Branch ["
				+ branchCode + "]");
		List<String> officeList = new ArrayList<String>(0);
		if (officeDetails != null) {
			LOGGER.debug("########CentralDataExtractorServiceImpl ::getAllOfficesForGoodsProcess :: Input Branch ["
					+ branchCode
					+ "]"
					+ "\tBranch Type :["
					+ officeDetails.getOffType() + "]");
			if (officeDetails.getOffType().equalsIgnoreCase(
					BusinessConstants.OFFICE_TYPE_BO)) {
				officeList = centralDataExtractorDAO
				.getAllSiblingsOfBo(branchCode);
			} else if (officeDetails.getOffType().equalsIgnoreCase(
					BusinessConstants.OFFICE_TYPE_RO)) {
				officeList = centralDataExtractorDAO
				.getAllDescendentsOfRo(branchCode);
				officeList.add(branchCode);
			} else {
				officeList.add(branchCode);
			}
		} else {
			officeList.add(branchCode);
		}
		LOGGER.debug("########CentralDataExtractorServiceImpl ::getAllOfficesForGoodsProcess ::List of Branches ["
				+ officeList.toString() + "]");
		return officeList;
	}

	/**
	 * Added by Narasimha Rao Kattunga Creating delivery manifest for branch.
	 *
	 * @param dlvMnfstList the dlv mnfst list
	 * @param branchFolderPath the branch folder path
	 * @param dataExtractorTo TODO
	 * @throws CGBusinessException the cG business exception
	 */
	private void createDlvMnfstXMLForBranch(List<DeliveryDO> dlvMnfstList,
			String branchFolderPath, DataExtractorTO dataExtractorTo) throws CGBusinessException {
		LOGGER.debug("CentralDataExtractorServiceImpl : createDlvMnfstXMLForBranch():START");
		try {
			if (dlvMnfstList != null && !dlvMnfstList.isEmpty()) {
				DeliveyManifestData jaxbObj = null;
				jaxbObj = objectConverter.createdDlvMnfstJaxObj(dlvMnfstList);
				// TODO
				String dlvMnfstXMLFileName = messageProp
				.getProperty(CentralDataExtractorConstant.DELIVERY_DETAILS_FILENAME);
				String dateTimeFormat = messageProp
				.getProperty(CentralDataExtractorConstant.XMLFILE_TIMESTAMP_FORMAT);
				File branchFolder = new File(branchFolderPath);
				if (!branchFolder.isDirectory()) {
					branchFolder.mkdirs();
				}
				String dlvMnfstFile = CGXMLUtil.createFileNameWithTimestamp(
						branchFolder.getAbsolutePath() + File.separator
						+ dlvMnfstXMLFileName, dateTimeFormat,
						ApplicationConstants.CHARACTER_UNDERSCORE);
				populateProcessInfo(dlvMnfstFile,dataExtractorTo);
				new CTBSXMLParser()
				.marshalObjectToXML(jaxbObj, dlvMnfstFile);
				LOGGER.debug("CentralDataExtractorServiceImpl : createDlvMnfstXMLForBranch():END");
			}
		} catch (Exception e) {
			LOGGER.error("CentralDataExtractorServiceImpl : createDlvMnfstXMLForBranch() : Error while creating XML for DeliveryDO ..."
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.service.CentralDataExtractorService#extractGoodsIssueDataForAllFracnhisees()
	 */
	@Override
	public void extractGoodsIssueDataForAllFracnhisees()
	throws CGBusinessException {
		LOGGER.debug("CentralDataExtractorServiceImpl : extractGoodsIssueDataForAllFracnhisees() : START");

		String branchFolder = "";
		try {

			/** Get all the branches */
			List<String> frCodeList = centralDataExtractorDAO.getAllFranchiseeCodes();

			for (String franchiseeCode : frCodeList) {

				try {
					LOGGER.debug("CentralDataExtractorServiceImpl : for franchisee :["+franchiseeCode+"] START");
					branchFolder = extractingGoodsIssueDataForFranchisee(franchiseeCode,frCodeList);
					LOGGER.debug("CentralDataExtractorServiceImpl : for franchisee :["+franchiseeCode+"] END");
				} catch (IOException ex) {
					LOGGER.error("CentralDataExtractorServiceImpl::extractGoodsIssueDataForAllFracnhisees::Exception occured:"
							+ex.getMessage());
				} catch (Exception ex) {
					LOGGER.error("CentralDataExtractorServiceImpl::extractGoodsIssueDataForAllFracnhisees::Exception occured:"
							+ex.getMessage());

				} finally {
					deleteBranchDirectory(branchFolder);
				}
			}

		} catch (Exception ex) {
			CGStackTraceUtil.getStackTraceException(ex,  LOGGER, "extractGoodsIssueDataForAllFracnhisees", UtilConstants.LOGGER_LEVEL_ERROR);
			LOGGER.error("CentralDataExtractorServiceImpl::extractGoodsIssueDataForAllFracnhisees::Exception occured:"
					+ex.getMessage());
			throw new CGBusinessException();
		} finally {
			deleteBranchDirectory(branchFolder);
		}
		LOGGER.debug("CentralDataExtractorServiceImpl : extractGoodsIssueDataForAllFracnhisees() : END");

	}
	
	/**
	 * Extracting goods issue data for franchisee.
	 *
	 * @param franchiseeCode the franchisee code
	 * @param frCodeList the fr code list
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String extractingGoodsIssueDataForFranchisee(String franchiseeCode,List<String> frCodeList)
	throws CGBusinessException, CGSystemException, IOException {
		
		
		String branchFolder=null;
		try {
			LOGGER.debug("CentralDataExtractorServiceImpl : extractingGoodsIssueDataForFranchisee():goodsIssueFranchiseeProcess : START");
			branchFolder = goodsIssueFranchiseeProcess(franchiseeCode,
					frCodeList);
		
		}  catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "extractingGoodsIssueDataForFranchisee", UtilConstants.LOGGER_LEVEL_ERROR);
			LOGGER.error("CentralDataExtractorServiceImpl::extractingGoodsIssueDataForFranchisee::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException();
		} finally {
			deleteBranchDirectory(branchFolder);
		}
		LOGGER.debug("CentralDataExtractorServiceImpl : extractingGoodsIssueDataForFranchisee():goodsIssueFranchiseeProcess : END");
		
		//######################################Goods Issue###################### END
		
		//######################################Goods Cancellation###################### START
		 branchFolder=null;
		try {
			LOGGER.debug("CentralDataExtractorServiceImpl : extractingGoodsIssueDataForFranchisee():goodsCancellationFranchiseeProcess : START");
			branchFolder = goodsCancellationFranchiseeProcess(franchiseeCode,
					frCodeList);
	}  catch (Exception e) {
		LOGGER.error("CentralDataExtractorServiceImpl::extractingGoodsIssueDataForFranchisee::Exception occured:"
				+e.getMessage());
	} finally {
		deleteBranchDirectory(branchFolder);
	}
	LOGGER.debug("CentralDataExtractorServiceImpl : goodsCancellationFranchiseeProcess():goodsCancellationFranchiseeProcess : END");
		//######################################Goods Cancellation###################### END
		
		//######################################Goods Renewal###################### START
	 branchFolder=null;
	try {
		LOGGER.debug("CentralDataExtractorServiceImpl : goodsCancellationFranchiseeProcess():goodsRenewalFranchiseeProcess : START");
		branchFolder = goodsRenewalFranchiseeProcess(franchiseeCode,
				frCodeList);
		}  catch (Exception e) {
			LOGGER.error("CentralDataExtractorServiceImpl::extractingGoodsIssueDataForFranchisee::Exception occured:"
					+e.getMessage());
		} finally {
			deleteBranchDirectory(branchFolder);
		}
		LOGGER.debug("CentralDataExtractorServiceImpl : goodsCancellationFranchiseeProcess():goodsRenewalFranchiseeProcess : END");
		return branchFolder;
	}

	/**
	 * Goods renewal franchisee process.
	 *
	 * @param franchiseeCode the franchisee code
	 * @param frCodeList the fr code list
	 * @return the string
	 * @throws NumberFormatException the number format exception
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private String goodsRenewalFranchiseeProcess(String franchiseeCode,
			List<String> frCodeList) throws NumberFormatException,
			CGSystemException, CGBusinessException, IOException {
		String branchFolder=null;
		try {
			branchFolder = messageProp
			.getProperty(CentralDataExtractorConstant.XML_FILE_BASE_MULTIPLE_LOCATION)
			+ File.separator + franchiseeCode+File.separator+franchiseeCode+ApplicationConstants.CHARACTER_UNDERSCORE+StringUtil.generateRamdomNumber();

			List<GoodsRenewalDO> goodsRenewalDOList = null;
			List<Integer> goodsRenewalIdList = null;
			Integer maxGoodsRenewalRecords = 10;
			
			String maxGoodsRenewalSize = messageProp
			.getProperty(CentralDataExtractorConstant.MAX_FETCH_SIZE_GOODS_RENEWAL);
			
			if (!StringUtils.isEmpty(maxGoodsRenewalSize)) {
				maxGoodsRenewalRecords = Integer.parseInt(maxGoodsRenewalSize);
				LOGGER.debug("Max goods Renewal Records to be fetched are (After Conversion )= [ "
						+ maxGoodsRenewalRecords + " ]");
			} else {
				LOGGER.debug("Max goods goods Renewal Records to be fetched are (Default values )= [ "
						+ maxGoodsRenewalRecords + " ]");
			}

			if (!StringUtil.isEmptyInteger(maxGoodsRenewalRecords)) {
				LOGGER.debug("Fetching Goods Renewal Records From DB");
				// FETCHING DATA FROM CENTRAL DATABASE
				goodsRenewalDOList = centralDataExtractorDAO.getGoodsRenewalDataForFrModule(frCodeList, maxGoodsRenewalRecords);
				LOGGER.debug("Fetching Process Completed From DB Record ,Total Records Are :"
						+ ((goodsRenewalDOList != null && !goodsRenewalDOList
								.isEmpty()) ? goodsRenewalDOList.size() : "0"));
			}
			/** Create the XML files for Goods Renewal Data */
			if (goodsRenewalDOList != null && !goodsRenewalDOList.isEmpty()
					&& goodsRenewalDOList.size() > 0) {
				LOGGER.debug("Goods Renewal Data Size from Central DB = ["
						+ goodsRenewalDOList.size() + " ]");
				createGoodsRenewalXMLForBranch(goodsRenewalDOList, branchFolder, false);
				goodsRenewalIdList = getGoodsRewalIdList(goodsRenewalDOList);
			}

			//######################################Goods Renewal###################### END

				
				/** Validate XML files are created for Branch */
				boolean xmlFilesCreated = checkFilesCreatedForBranch(branchFolder);
				if (xmlFilesCreated) {
					/** Zip the files in memory */
					byte[] inMemoryZipFile = ZipUtility
					.createInMemoryZipFile(branchFolder);
					/** Create the Extracted Data for save */
					List<DataExtractionDO> dataExtractionDoList = new ArrayList<DataExtractionDO>();
					if (inMemoryZipFile != null && inMemoryZipFile.length > 0) {
						
							DataExtractionDO dataExtractionDO = createDataExtractionDOForSave(
									inMemoryZipFile, franchiseeCode, null);
							dataExtractionDO.setProcessName(CentralDataExtractorConstant.GOODS_RENEWAL_PROCESS);
							dataExtractionDoList.add(dataExtractionDO);
					}
					LOGGER.debug("Data prepared for the Office = [ " + franchiseeCode
							+ " ]");
					if (dataExtractionDoList != null
							&& !dataExtractionDoList.isEmpty()) {
						/** Save ZIP File */
						centralDataExtractorDAO
						.saveExtractedDataForBranchList(dataExtractionDoList);

						/**
						 * Update the Central DB Data Status as Read By Local -
						 * GOODS RENEWAL / CANCELLATION - PURCHASE MODULE
						 */
						centralDataExtractorDAO.updateGoodsRenewalWithReadByFranchisee(goodsRenewalIdList);
						/** Delete the XML Files in the Branch Folder */
						deleteBranchDirectory(branchFolder);

					}
				}
		} catch (CGBusinessException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::goodsRenewalFranchiseeProcess::CGBusinessException occured:"
					+e.getMessage());
			throw e;
		} catch (CGSystemException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::goodsRenewalFranchiseeProcess::CGSystemException occured:"
					+e.getMessage());
			throw e;

		} catch (IOException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::goodsRenewalFranchiseeProcess::IOException occured:"
					+e.getMessage());
			throw e;
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "goodsRenewalFranchiseeProcess", UtilConstants.LOGGER_LEVEL_ERROR);
			LOGGER.error("CentralDataExtractorServiceImpl::goodsRenewalFranchiseeProcess::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException();
		} finally {
			deleteBranchDirectory(branchFolder);
		}
		return branchFolder;
	}

	/**
	 * Goods cancellation franchisee process.
	 *
	 * @param franchiseeCode the franchisee code
	 * @param frCodeList the fr code list
	 * @return the string
	 * @throws NumberFormatException the number format exception
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private String goodsCancellationFranchiseeProcess(String franchiseeCode,
			List<String> frCodeList) throws NumberFormatException,
			CGBusinessException, CGSystemException, IOException {
		String branchFolder=null;
		LOGGER.debug("Data Extract for franchisee = [ " + franchiseeCode + " ]");

		try {
			branchFolder = messageProp
			.getProperty(CentralDataExtractorConstant.XML_FILE_BASE_MULTIPLE_LOCATION)
			+ File.separator + franchiseeCode+File.separator+franchiseeCode+ApplicationConstants.CHARACTER_UNDERSCORE+StringUtil.generateRamdomNumber();

			List<GoodscCancellationDO> goodsCanclDOList = null;
			List<Integer> goodsCanclIdList = null;
			Integer maxGoodsCancelRecords = 10;
			String maxGoodsCanclRecords = messageProp
			.getProperty(CentralDataExtractorConstant.MAX_FETCH_SIZE_GOODS_CANCELLATION);
			if (!StringUtils.isEmpty(maxGoodsCanclRecords)) {
				maxGoodsCancelRecords = Integer.parseInt(maxGoodsCanclRecords);
				LOGGER.debug("Max goods Cancellation Records to be fetched are (After Conversion )= [ "
						+ maxGoodsCancelRecords + " ]");
			} else {
				LOGGER.debug("Max goods goods Cancellation Records to be fetched are (Default values )= [ "
						+ maxGoodsCancelRecords + " ]");
			}

			if (!StringUtil.isEmptyInteger(maxGoodsCancelRecords)) {
				LOGGER.debug("Fetching Goods Cancellation Records From DB");
				// FETCHING DATA FROM CENTRAL DATABASE
				goodsCanclDOList = getGoodsCanclDataForFRModule(frCodeList,maxGoodsCancelRecords);
				LOGGER.debug("Fetching Process Completed From DB Record ,Total Records Are :"
						+ ((goodsCanclDOList != null && !goodsCanclDOList.isEmpty()) ? goodsCanclDOList
								.size() : "0"));
			}


			/** Create the XML files for Goods Cancellation Data */
			if (goodsCanclDOList != null && !goodsCanclDOList.isEmpty()
					&& goodsCanclDOList.size() > 0) {
				LOGGER.debug("Goods Cancellation Data Size from Central DB = ["
						+ goodsCanclDOList.size() + " ]");
				createGoodsCanclXMLForBranch(goodsCanclDOList, branchFolder,true);
				goodsCanclIdList = getGoodsCancelIdList(goodsCanclDOList);
			}
			/** Validate XML files are created for Branch */
			boolean xmlFilesCreated = checkFilesCreatedForBranch(branchFolder);
			if (xmlFilesCreated) {
				/** Zip the files in memory */
				byte[] inMemoryZipFile = ZipUtility
				.createInMemoryZipFile(branchFolder);
				/** Create the Extracted Data for save */
				List<DataExtractionDO> dataExtractionDoList = new ArrayList<DataExtractionDO>();
				if (inMemoryZipFile != null && inMemoryZipFile.length > 0) {

					DataExtractionDO dataExtractionDO = createDataExtractionDOForSave(
							inMemoryZipFile, franchiseeCode, null);
					dataExtractionDO.setProcessName(CentralDataExtractorConstant.GOODS_CANCELLATION_PROCESS);
					dataExtractionDoList.add(dataExtractionDO);
				}
				LOGGER.debug("Data prepared for the Office = [ " + franchiseeCode
						+ " ]");
				if (dataExtractionDoList != null
						&& !dataExtractionDoList.isEmpty()) {
					/** Save ZIP File */
					centralDataExtractorDAO
					.saveExtractedDataForBranchList(dataExtractionDoList);

					/**
					 * Update the Central DB Data Status as Read By Local -
					 * GOODS RENEWAL / CANCELLATION - PURCHASE MODULE
					 */
					centralDataExtractorDAO.updateGoodsCanclWithReadByFranchisee(goodsCanclIdList);
					/** Delete the XML Files in the Branch Folder */
					deleteBranchDirectory(branchFolder);

				}
			}
		} catch (CGBusinessException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::goodsCancellationFranchiseeProcess::CGBusinessException occured:"
					+e.getMessage());
			throw e;
		} catch (CGSystemException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::goodsCancellationFranchiseeProcess::CGSystemException occured:"
					+e.getMessage());
			throw e;

		} catch (IOException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::goodsCancellationFranchiseeProcess::IOException occured:"
					+e.getMessage());
			throw e;
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "goodsCancellationFranchiseeProcess", UtilConstants.LOGGER_LEVEL_ERROR);
			LOGGER.error("CentralDataExtractorServiceImpl::goodsCancellationFranchiseeProcess::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException();
		} finally {
			deleteBranchDirectory(branchFolder);
		}
		return branchFolder;
	}

	/**
	 * Goods issue franchisee process.
	 *
	 * @param franchiseeCode the franchisee code
	 * @param frCodeList the fr code list
	 * @return the string
	 * @throws NumberFormatException the number format exception
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private String goodsIssueFranchiseeProcess(String franchiseeCode,
			List<String> frCodeList) throws NumberFormatException,
			CGBusinessException, CGSystemException, IOException {
		String branchFolder=null;
		
		
		try {
			branchFolder = messageProp
			.getProperty(CentralDataExtractorConstant.XML_FILE_BASE_MULTIPLE_LOCATION)
			+ File.separator + franchiseeCode+File.separator+franchiseeCode+ApplicationConstants.CHARACTER_UNDERSCORE+StringUtil.generateRamdomNumber();
			// The following constants for no of records to be fetched from each
			// process (configurable ie. taking the values from
			// properties(centralDataExtraction.properties) file)

			LOGGER.debug("Data Extract for franchisee = [ " + franchiseeCode + " ]");

			//######################################Goods Issue###################### START
			List<GoodsIssueDO> goodsIssueDOList = null;
			List<Integer> goodsIssueIdList = null;
			Integer maxGoodsIssueRecords = 10;
			String maxIssueRecords = messageProp
			.getProperty(CentralDataExtractorConstant.MAX_FETCH_SIZE_GOODS_ISSUE);

			LOGGER.debug("Max goods issue Records to be fetched are (From Properties File )= [ "
					+ maxIssueRecords + " ]");

			if (!StringUtils.isEmpty(maxIssueRecords)) {
				maxGoodsIssueRecords = Integer.parseInt(maxIssueRecords);
				LOGGER.debug("Max goods issue Records to be fetched are (After Conversion )= [ "
						+ maxGoodsIssueRecords + " ]");
			} else {
				LOGGER.debug("Max goods issue Records to be fetched are (Default values )= [ "
						+ maxGoodsIssueRecords + " ]");
			}

			if (!StringUtil.isEmptyInteger(maxGoodsIssueRecords)) {
				LOGGER.debug("Fetching Goods Issue Records From DB");
				goodsIssueDOList = getGoodsIssueDataForFRModule(frCodeList,
						maxGoodsIssueRecords);
				LOGGER.debug("##Fetching Process(Goods Issue) Completed From DB Record ,Total Records Are :"
						+ ((goodsIssueDOList != null && !goodsIssueDOList.isEmpty()) ? goodsIssueDOList
								.size() : "0"));

			}

			if (goodsIssueDOList != null && !goodsIssueDOList.isEmpty()) {
				LOGGER.debug("Goodsissue DO ListSize from Central DB = ["
						+ goodsIssueDOList.size() + " ]");
				createGoodsIssueXMLForBranch(goodsIssueDOList, branchFolder,true);
				goodsIssueIdList = getGoodsIssueIdList(goodsIssueDOList);
			}


			/** Validate XML files are created for Branch */
			boolean xmlFilesCreated = checkFilesCreatedForBranch(branchFolder);
			if (xmlFilesCreated) {
				/** Zip the files in memory */
				byte[] inMemoryZipFile = ZipUtility
				.createInMemoryZipFile(branchFolder);
				/** Create the Extracted Data for save */
				List<DataExtractionDO> dataExtractionDoList = new ArrayList<DataExtractionDO>();
				if (inMemoryZipFile != null && inMemoryZipFile.length > 0) {

					DataExtractionDO dataExtractionDO = createDataExtractionDOForSave(
							inMemoryZipFile, franchiseeCode, null);
					dataExtractionDO.setProcessName(CentralDataExtractorConstant.GOODS_ISSUE_PROCESS);
					dataExtractionDoList.add(dataExtractionDO);
				}
				LOGGER.debug("Data prepared for the Office = [ " + franchiseeCode
						+ " ]");
				if (dataExtractionDoList != null
						&& !dataExtractionDoList.isEmpty()) {
					/** Save ZIP File */
					centralDataExtractorDAO
					.saveExtractedDataForBranchList(dataExtractionDoList);

					/**
					 * Update the Central DB Data Status as Read By Local -
					 * GOODS RENEWAL / CANCELLATION - PURCHASE MODULE
					 */
					centralDataExtractorDAO.updateGoodsIssueWithReadByFranchisee(goodsIssueIdList);
					/** Delete the XML Files in the Branch Folder */
					deleteBranchDirectory(branchFolder);

				}
			}
		} catch (CGBusinessException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::goodsIssueFranchiseeProcess::CGBusinessException occured:"
					+e.getMessage());
			throw e;
		} catch (CGSystemException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::goodsIssueFranchiseeProcess::CGSystemException occured:"
					+e.getMessage());
			throw e;

		} catch (IOException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::goodsIssueFranchiseeProcess::IOException occured:"
					+e.getMessage());
			throw e;
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "goodsIssueFranchiseeProcess", UtilConstants.LOGGER_LEVEL_ERROR);
			LOGGER.error("CentralDataExtractorServiceImpl::goodsIssueFranchiseeProcess::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException();
		} finally {
			deleteBranchDirectory(branchFolder);
		}
		return branchFolder;
	}

	/**
	 * Gets the goods issue id list.
	 *
	 * @param goodsIssueDo the goods issue do
	 * @return the goods issue id list
	 */
	private List<Integer> getGoodsIssueIdList(List<GoodsIssueDO> goodsIssueDo){
		List<Integer> goodsIssueIdList= null;
		
		if(!StringUtil.isEmptyList(goodsIssueDo)){
			goodsIssueIdList =  new ArrayList<Integer>(goodsIssueDo.size());
		}
		for(GoodsIssueDO issueDo :goodsIssueDo){
			goodsIssueIdList.add(issueDo.getGoodsIssueId());
		}
		
		return goodsIssueIdList;
		
	}
	
	/**
	 * Gets the goods cancel id list.
	 *
	 * @param goodsCancelDoList the goods cancel do list
	 * @return the goods cancel id list
	 */
	private List<Integer> getGoodsCancelIdList(List<GoodscCancellationDO> goodsCancelDoList){
		List<Integer> goodsCancelIdList= null;
		
		if(!StringUtil.isEmptyList(goodsCancelDoList)){
			goodsCancelIdList =  new ArrayList<Integer>(goodsCancelDoList.size());
		}
		for(GoodscCancellationDO cancelDo :goodsCancelDoList){
			goodsCancelIdList.add(cancelDo.getGoodsCanclId());
		}
		
		return goodsCancelIdList;
		
	}
	
	/**
	 * Gets the goods rewal id list.
	 *
	 * @param goodsRenwalDoList the goods renwal do list
	 * @return the goods rewal id list
	 */
	private List<Integer> getGoodsRewalIdList(List<GoodsRenewalDO> goodsRenwalDoList){
		List<Integer> goodsRenewalIdList= null;
		
		if(!StringUtil.isEmptyList(goodsRenwalDoList)){
			goodsRenewalIdList =  new ArrayList<Integer>(goodsRenwalDoList.size());
		}
		for(GoodsRenewalDO renewal :goodsRenwalDoList){
			goodsRenewalIdList.add(renewal.getGoodsRenewalId());
		}
		
		return goodsRenewalIdList;
		
	}

	@Override
	public void extractBookingDataForFRModule() throws CGBusinessException {

		LOGGER.debug("CentralDataExtractorServiceImpl : extractDataForBooking() : START");

		// The following constants for no of records to be fetched from each
		// process (configurable ie. taking the values from
		// properties(centralDataExtraction.properties) file)

		String branchFolder = null;
		try {
			List<String> frCodeList = centralDataExtractorDAO.getAllFranchiseeCodes();

			for (String franchiseeCode : frCodeList) {

				//######################################Booking###################### START
				
				branchFolder = messageProp
				.getProperty(CentralDataExtractorConstant.XML_FILE_BASE_MULTIPLE_LOCATION)
				+ File.separator + franchiseeCode+File.separator+franchiseeCode+ApplicationConstants.CHARACTER_UNDERSCORE
				+StringUtil.generateRamdomNumber();
				
			/*	branchFolder=null;
		        branchFolder = getFolderNameFromProps()
			+ File.separator + franchiseeCode+File.separator+franchiseeCode+ApplicationConstants.CHARACTER_UNDERSCORE+StringUtil.generateRamdomNumber();*/
				try {
					LOGGER.debug("########Booking Start#######");
					bookingFranchiseeProcess(franchiseeCode, frCodeList, branchFolder);
					LOGGER.debug("########Booking End #######");
				} catch (Exception e) {
					LOGGER.error("CentralDataExtractorServiceImpl::extractDataForBooking::Exception occured:"
							+e.getMessage());
				}finally {
					deleteBranchDirectory(branchFolder);
				}
				//######################################Booking###################### END
			}
		}  catch (Exception ex) {
			LOGGER.error("CentralDataExtractorServiceImpl::extractDataForBooking::Exception occured:"
					+ex.getMessage());
			
		} 
		LOGGER.debug("CentralDataExtractorServiceImpl : extractDataForBooking() : END");
	}
    
	private void bookingFranchiseeProcess(String franchiseeCode, List<String> frCodeList,
			String branchFolder)
	throws  CGBusinessException, IOException {
		
		List<BookingDO> bookingDOList = null;
		DataExtractionDO bookingExtractionDO =null;
		Integer maxBookingRecords = 10;
		List<Integer> bookingIdList = null;
		DataExtractorTO extractorTo=new DataExtractorTO();
		try {
			String maxBookingFetch = messageProp
			.getProperty(CentralDataExtractorConstant.MAX_FETCH_SIZE_BOOKING);
			LOGGER.debug("Max Booking Records to be fetched are (From Properties File )= [ "
					+ maxBookingFetch + " ]");

			if (!StringUtils.isEmpty(maxBookingFetch)) {
				maxBookingRecords = Integer.parseInt(maxBookingFetch);
				LOGGER.debug("Max Booking Records to be fetched are (After Conversion )= [ "
						+ maxBookingRecords + " ]");
			} else {
				LOGGER.debug("Max Booking Records to be fetched are (Default values )= [ "
						+ maxBookingRecords + " ]");
			}
			if (!StringUtil.isEmptyInteger(maxBookingRecords)) {
				LOGGER.debug("Fetching Booking Records From DB");
				bookingDOList =	getBookingDataForFranchiseeCode(frCodeList,maxBookingRecords);
				LOGGER.debug("##Fetching Process Completed From DB Record (Booking), No Of Records:"
						+ ((bookingDOList != null && !bookingDOList
								.isEmpty()) ? bookingDOList.size() : "0"));
			
			}
			

			/** Create the XML files for Booking Data */
			if(bookingDOList != null && !bookingDOList.isEmpty()){
				LOGGER.trace("Toal Booking Records Found from DB are :["
						+ bookingDOList.size() + " ]");
				createBookingXMLForFranchisee(bookingDOList,branchFolder, true, extractorTo); 
				 bookingIdList = new ArrayList<Integer>(bookingDOList.size());
					for (BookingDO bookingDo :bookingDOList){
						LOGGER.trace("Toal Booking Records with to string :["+bookingDo.toString());
						bookingIdList.add(bookingDo.getBookingId());
					}
			}else{
				LOGGER.debug("Booking Xml Creation is not happened due to no record found in DB");
			}

			List<DataExtractionDO> dataExtractionDoList = new ArrayList<DataExtractionDO>();
			bookingExtractionDO = prepareExtractedData(franchiseeCode, branchFolder,
					bookingExtractionDO,extractorTo);

			bookingExtractionDO.setProcessName(CentralDataExtractorConstant.BOOKING_PROCESS);
			dataExtractionDoList.add(bookingExtractionDO);
			LOGGER.debug("Data prepared for the Office = [ " + franchiseeCode
					+ " ]");
			if (dataExtractionDoList != null
					&& !dataExtractionDoList.isEmpty()) {
				/** Save ZIP File */
				centralDataExtractorDAO
				.saveExtractedDataForBranchList(dataExtractionDoList);

				/**
				 * Update the Central DB Data Status as Read By Local -
				 * GOODS RENEWAL / CANCELLATION - PURCHASE MODULE
				 */
				centralDataExtractorDAO.updateFrSyncFlagInBookingForFranchisee(bookingIdList);
				/** Delete the XML Files in the Branch Folder */
				deleteBranchDirectory(branchFolder);

			}
			
		}  catch (CGBusinessException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::bookingFranchiseeProcess::CGBusinessException occured:"
					+e.getMessage());
			throw e;
		}  catch (IOException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::bookingFranchiseeProcess::IOException occured:"
					+e.getMessage());
			throw e;
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "bookingProcess", UtilConstants.LOGGER_LEVEL_ERROR);
			LOGGER.error("CentralDataExtractorServiceImpl::bookingFranchiseeProcess::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException();
		} finally {
			extractorTo=null;
			deleteBranchDirectory(branchFolder);
		}
	}
	
	private List<BookingDO> getBookingDataForFranchiseeCode(List<String> frCodeList,
			Integer maxSize)throws CGSystemException {
		LOGGER.debug("CentralDataExtractorServiceImpl : getBookingDataForFranchiseeCode() : START");
		List<BookingDO> bookingDOList = null;
		try {
			bookingDOList = centralDataExtractorDAO
			.getBookingDetailsByFranchiseeCode(frCodeList, maxSize);
		} catch (CGSystemException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::getBookingDataForFranchiseeCode::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("CentralDataExtractorServiceImpl : getBookingDataForFranchiseeCode() : END");
		//LOGGER.debug(frCodeList, "BookingDoList Size : " + bookingDOList !=null?bookingDOList.size(): null);
		
		return bookingDOList;
	}

	private void createBookingXMLForFranchisee(
			List<BookingDO> bookingDOList, String branchFolderPath,Boolean isFrModule, DataExtractorTO extractorTo)
	throws CGBusinessException {
		try {
			LOGGER.debug("CentralDataExtractorServiceImpl :createGoodsIssueXMLForBranch   ####START");
			// Create Goodsissue which will create XML for Goodsissue Data
			BookingDetailsData jaxbObj = objectConverter
			.createdBookingJaxObj(bookingDOList);
			LOGGER.debug("CentralDataExtractorServiceImpl :createGoodsIssueXMLForBranch jaxbObj created");
			if (jaxbObj != null) {
				String bookingXMLFileName = messageProp
				.getProperty(CentralDataExtractorConstant.BOOKING_FILENAME);

				
				if(isFrModule){
					bookingXMLFileName = convertToFRModuleFileName(bookingXMLFileName);
					
				}
				String dateTimeFormat = messageProp
				.getProperty(CentralDataExtractorConstant.XMLFILE_TIMESTAMP_FORMAT);

				File branchFolder = new File(branchFolderPath);
				if (branchFolder != null && !branchFolder.exists()) {
					branchFolder.mkdirs();
				}
				LOGGER.debug(" Branch file creation ...Folder path :"
						+ branchFolder);
				String bookingXMLPath = CGXMLUtil
				 .createFileNameWithTimestamp(
						 branchFolder.getAbsolutePath() + File.separator
						 + bookingXMLFileName, dateTimeFormat,
						 ApplicationConstants.CHARACTER_UNDERSCORE);
				 LOGGER.debug("CentralDataExtractorServiceImpl :createGoodsIssueXMLForBranch preparing XML");
				 populateProcessInfo(bookingXMLPath,extractorTo);
				 /** Create the XML for Booking */
				 new CTBSXMLParser().marshalObjectToXML(jaxbObj,
						 bookingXMLPath);

			}
		} catch (Exception e) {
			LOGGER.error("CentralDataExtractorServiceImpl::createGoodsIssueXMLForBranch::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("CentralDataExtractorServiceImpl :createGoodsIssueXMLForBranch   ####END");
	}
	
	private String convertToFRModuleFileName(String xmlFileName) {
		StringBuffer strFileName = new StringBuffer(xmlFileName);
		strFileName.insert(xmlFileName.lastIndexOf(""),CentralDataExtractorConstant.FR_MODULE );
	return strFileName.toString();
	}
	
	@Override
	public void extractDeliveryDataForFRModule() throws CGBusinessException {

		LOGGER.debug("CentralDataExtractorServiceImpl : extractDataForBooking() : START");

		// The following constants for no of records to be fetched from each
		// process (configurable ie. taking the values from
		// properties(centralDataExtraction.properties) file)

		String branchFolder = null;
		try {
			List<String> frCodeList = centralDataExtractorDAO.getAllFranchiseeCodes();

			for (String franchiseeCode : frCodeList) {

				//######################################Booking###################### START
				
				branchFolder = messageProp
				.getProperty(CentralDataExtractorConstant.XML_FILE_BASE_MULTIPLE_LOCATION)
				+ File.separator + franchiseeCode+File.separator+franchiseeCode+ApplicationConstants.CHARACTER_UNDERSCORE
				+StringUtil.generateRamdomNumber();
				
			/*	branchFolder=null;
		        branchFolder = getFolderNameFromProps()
			+ File.separator + franchiseeCode+File.separator+franchiseeCode+ApplicationConstants.CHARACTER_UNDERSCORE+StringUtil.generateRamdomNumber();*/
				try {
					LOGGER.debug("########Booking Start#######");
					deliveryFranchiseeProcess(franchiseeCode, frCodeList, branchFolder);
					LOGGER.debug("########Booking End #######");
				} catch (Exception e) {
					LOGGER.error("CentralDataExtractorServiceImpl::extractDataForBooking::Exception occured:"
							+e.getMessage());
				}finally {
					deleteBranchDirectory(branchFolder);
				}
				//######################################Booking###################### END
			}
		}  catch (Exception ex) {
			LOGGER.error("CentralDataExtractorServiceImpl::extractDataForBooking::Exception occured:"
					+ex.getMessage());
			
		} 
		LOGGER.debug("CentralDataExtractorServiceImpl : extractDataForBooking() : END");
	}
	
	private void deliveryFranchiseeProcess(String franchiseeCode, List<String> frCodeList,
			String branchFolder)
	throws  CGBusinessException, IOException {
		
		List<DeliveryDO> deliveryDOList = null;
		DataExtractionDO deliveryExtractionDO =null;
		Integer maxDeliveryRecords = 10;
		List<Integer> deliveryIdList = null;
		DataExtractorTO extractorTo=new DataExtractorTO();
		try {
			String maxDeliveryFetch = messageProp
			.getProperty(CentralDataExtractorConstant.MAX_FETCH_SIZE_BOOKING);
			LOGGER.debug("Max Delivery Records to be fetched are (From Properties File )= [ "
					+ maxDeliveryFetch + " ]");

			if (!StringUtils.isEmpty(maxDeliveryFetch)) {
				maxDeliveryRecords = Integer.parseInt(maxDeliveryFetch);
				LOGGER.debug("Max Delivery Records to be fetched are (After Conversion )= [ "
						+ maxDeliveryRecords + " ]");
			} else {
				LOGGER.debug("Max Delivery Records to be fetched are (Default values )= [ "
						+ maxDeliveryRecords + " ]");
			}
			if (!StringUtil.isEmptyInteger(maxDeliveryRecords)) {
				LOGGER.debug("Fetching Delivery Records From DB");
				deliveryDOList = getDeliveryDataForFranchiseeCode(frCodeList,maxDeliveryRecords);
				LOGGER.debug("##Fetching Process Completed From DB Record (Delivery), No Of Records:"
						+ ((deliveryDOList != null && !deliveryDOList
								.isEmpty()) ? deliveryDOList.size() : "0"));
			
			}
			
			/** Create the XML files for Delivery Data */
			if(deliveryDOList != null && !deliveryDOList.isEmpty()){
				LOGGER.trace("Total delivery Records Found from DB are :["
						+ deliveryDOList.size() + " ]");
				createDeliveryXMLForFranchisee(deliveryDOList,branchFolder, true, extractorTo); 
				deliveryIdList = new ArrayList<Integer>(deliveryDOList.size());
					for (DeliveryDO deliveryDo :deliveryDOList){
						LOGGER.trace("Total delivery Records with to string :["+deliveryDo.toString());
						deliveryIdList.add(deliveryDo.getDeliveryId());
					}
			}else{
				LOGGER.debug("Delivery Xml Creation is not happened due to no record found in DB");
			}

			List<DataExtractionDO> dataExtractionDoList = new ArrayList<DataExtractionDO>();
			deliveryExtractionDO = prepareExtractedData(franchiseeCode, branchFolder,
					deliveryExtractionDO,extractorTo);

			deliveryExtractionDO.setProcessName(CentralDataExtractorConstant.DELIVERY_PROCESS);
			dataExtractionDoList.add(deliveryExtractionDO);
			LOGGER.debug("Data prepared for the FR = [ " + franchiseeCode
					+ " ]");
			if (dataExtractionDoList != null
					&& !dataExtractionDoList.isEmpty()) {
				/** Save ZIP File */
				centralDataExtractorDAO
				.saveExtractedDataForBranchList(dataExtractionDoList);

				/**
				 * Update the Central DB Data Status as Read By Local -
				 * GOODS RENEWAL / CANCELLATION - PURCHASE MODULE
				 */
				centralDataExtractorDAO.updateFrSyncFlagInDeliveryForFranchisee(deliveryIdList);
				/** Delete the XML Files in the Branch Folder */
				deleteBranchDirectory(branchFolder);

			}
			
		}  catch (CGBusinessException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::deliveryFranchiseeProcess::CGBusinessException occured:"
					+e.getMessage());
			throw e;
		}  catch (IOException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::deliveryFranchiseeProcess::IOException occured:"
					+e.getMessage());
			throw e;
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "deliveryProcess", UtilConstants.LOGGER_LEVEL_ERROR);
			LOGGER.error("CentralDataExtractorServiceImpl::deliveryFranchiseeProcess::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException();
		} finally {
			extractorTo=null;
			deleteBranchDirectory(branchFolder);
		}
	}

	private List<DeliveryDO> getDeliveryDataForFranchiseeCode(
			List<String> frCodeList, Integer maxDeliveryRecords) throws CGSystemException {
		
		LOGGER.debug("CentralDataExtractorServiceImpl : getDeliveryDataForFranchiseeCode() : START");
		List<DeliveryDO> deliveryDOList = null;
		try {
			deliveryDOList = centralDataExtractorDAO
			.getDeliveryDetailsByFranchiseeCode(frCodeList, maxDeliveryRecords);
		} catch (CGSystemException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::getDeliveryDataForFranchiseeCode::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("CentralDataExtractorServiceImpl : getDeliveryDataForFranchiseeCode() : END");
		//LOGGER.debug(frCodeList, "BookingDoList Size : " + bookingDOList !=null?bookingDOList.size(): null);
		
		return deliveryDOList;
	}
	
	private void createDeliveryXMLForFranchisee(
			List<DeliveryDO> deliveryDOList, String branchFolderPath,Boolean isFrModule, DataExtractorTO extractorTo)
	throws CGBusinessException {
		try {
			LOGGER.debug("CentralDataExtractorServiceImpl :createDeliveryXMLForFranchisee   ####START");
			// Create Goodsissue which will create XML for Goodsissue Data
			DeliveyManifestData jaxbObj = null;
			jaxbObj = objectConverter.createdDlvMnfstJaxObj(deliveryDOList);
			
			LOGGER.debug("CentralDataExtractorServiceImpl :createDeliveryXMLForFranchisee jaxbObj created");
			if (jaxbObj != null) {
				String deliveryXMLFileName = messageProp
				.getProperty(CentralDataExtractorConstant.DELIVERY_FR_FILENAME);

				
				if(isFrModule){
					deliveryXMLFileName = convertToFRModuleFileName(deliveryXMLFileName);
					
				}
				String dateTimeFormat = messageProp
				.getProperty(CentralDataExtractorConstant.XMLFILE_TIMESTAMP_FORMAT);

				File branchFolder = new File(branchFolderPath);
				if (branchFolder != null && !branchFolder.exists()) {
					branchFolder.mkdirs();
				}
				LOGGER.debug(" Branch file creation ...Folder path :"
						+ branchFolder);
				String deliveryXMLPath = CGXMLUtil
				 .createFileNameWithTimestamp(
						 branchFolder.getAbsolutePath() + File.separator
						 + deliveryXMLFileName, dateTimeFormat,
						 ApplicationConstants.CHARACTER_UNDERSCORE);
				 LOGGER.debug("CentralDataExtractorServiceImpl :createDeliveryXMLForFranchisee preparing XML");
				 populateProcessInfo(deliveryXMLPath,extractorTo);
				 /** Create the XML for Booking */
				 new CTBSXMLParser().marshalObjectToXML(jaxbObj,
						 deliveryXMLPath);

			}
		} catch (Exception e) {
			LOGGER.error("CentralDataExtractorServiceImpl::createDeliveryXMLForFranchisee::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("CentralDataExtractorServiceImpl :createDeliveryXMLForFranchisee   ####END");
	}

	@Override
	public void extractManifestDataForFRModule() throws CGBusinessException {

		LOGGER.debug("CentralDataExtractorServiceImpl : extractManifestDataForFRModule() : START");

		// The following constants for no of records to be fetched from each
		// process (configurable ie. taking the values from
		// properties(centralDataExtraction.properties) file)

		String branchFolder = null;
		try {
			List<String> frCodeList = centralDataExtractorDAO.getAllFranchiseeCodes();

			for (String franchiseeCode : frCodeList) {

				//######################################Booking###################### START
				
				branchFolder = messageProp
				.getProperty(CentralDataExtractorConstant.XML_FILE_BASE_MULTIPLE_LOCATION)
				+ File.separator + franchiseeCode+File.separator+franchiseeCode+ApplicationConstants.CHARACTER_UNDERSCORE
				+StringUtil.generateRamdomNumber();
				
			/*	branchFolder=null;
		        branchFolder = getFolderNameFromProps()
			+ File.separator + franchiseeCode+File.separator+franchiseeCode+ApplicationConstants.CHARACTER_UNDERSCORE+StringUtil.generateRamdomNumber();*/
				try {
					LOGGER.debug("########Booking Start#######");
					//bookingFranchiseeProcess(franchiseeCode, frCodeList, branchFolder);
					manifestFranchiseeProcess(franchiseeCode, frCodeList, branchFolder);
					LOGGER.debug("########Booking End #######");
				} catch (Exception e) {
					LOGGER.error("CentralDataExtractorServiceImpl::extractManifestDataForFRModule::Exception occured:"
							+e.getMessage());
				}finally {
					deleteBranchDirectory(branchFolder);
				}
				//######################################Booking###################### END
			}
		}  catch (Exception ex) {
			LOGGER.error("CentralDataExtractorServiceImpl::extractManifestDataForFRModule::Exception occured:"
					+ex.getMessage());
			
		} 
		LOGGER.debug("CentralDataExtractorServiceImpl : extractManifestDataForFRModule() : END");
	}
	
	private void manifestFranchiseeProcess(String franchiseeCode, List<String> frCodeList,
			String branchFolder)
	throws  CGBusinessException, IOException {
		
		List<ManifestDO> manifestDOList = null;
		DataExtractionDO manfiestExtractionDO =null;
		Integer maxManifestRecords = 10;
		List<Integer> manifestIdList = null;
		DataExtractorTO extractorTo=new DataExtractorTO();
		try {
			String maxManifestFetch = messageProp
			.getProperty(CentralDataExtractorConstant.MAX_FETCH_SIZE_MANIFEST);
			LOGGER.debug("Max Manifest Records to be fetched are (From Properties File )= [ "
					+ maxManifestFetch + " ]");

			if (!StringUtils.isEmpty(maxManifestFetch)) {
				maxManifestRecords = Integer.parseInt(maxManifestFetch);
				LOGGER.debug("Max Manifest Records to be fetched are (After Conversion )= [ "
						+ maxManifestRecords + " ]");
			} else {
				LOGGER.debug("Max Manifest Records to be fetched are (Default values )= [ "
						+ maxManifestRecords + " ]");
			}
			if (!StringUtil.isEmptyInteger(maxManifestRecords)) {
				LOGGER.debug("Fetching Manifest Records From DB");
				manifestDOList =	getManifestDataForFranchiseeCode(frCodeList,maxManifestRecords);
				LOGGER.debug("##Fetching Process Completed From DB Record (Manifest), No Of Records:"
						+ ((manifestDOList != null && !manifestDOList
								.isEmpty()) ? manifestDOList.size() : "0"));
			
			}
			

			/** Create the XML files for Booking Data */
			if(manifestDOList != null && !manifestDOList.isEmpty()){
				LOGGER.trace("Toal Manifest Records Found from DB are :["
						+ manifestDOList.size() + " ]");
				createManifestXMLForFranchisee(manifestDOList,branchFolder, true,extractorTo); 
				manifestIdList = new ArrayList<Integer>(manifestDOList.size());
					for (ManifestDO manifestDO :manifestDOList){
						LOGGER.trace("Toal Manifest Records with to string :["+manifestDO.toString());
						manifestIdList.add(manifestDO.getManifestId());
					}
			}else{
				LOGGER.debug("Manifest Xml Creation is not happened due to no record found in DB");
			}

			List<DataExtractionDO> dataExtractionDoList = new ArrayList<DataExtractionDO>();
			manfiestExtractionDO = prepareExtractedData(franchiseeCode, branchFolder,
					manfiestExtractionDO,extractorTo);

			manfiestExtractionDO.setProcessName(CentralDataExtractorConstant.MANIFEST_PROCESS);
			dataExtractionDoList.add(manfiestExtractionDO);
			LOGGER.debug("Data prepared for the Office = [ " + franchiseeCode
					+ " ]");
			if (dataExtractionDoList != null
					&& !dataExtractionDoList.isEmpty()) {
				/** Save ZIP File */
				centralDataExtractorDAO
				.saveExtractedDataForBranchList(dataExtractionDoList);

				/**
				 * Update the Central DB Data Status as Read By Local -
				 * GOODS RENEWAL / CANCELLATION - PURCHASE MODULE
				 */
				centralDataExtractorDAO.updateFrSyncFlagInManifestForFranchisee(manifestIdList);
				/** Delete the XML Files in the Branch Folder */
				deleteBranchDirectory(branchFolder);

			}
			
		}  catch (CGBusinessException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::manifestFranchiseeProcess::CGBusinessException occured:"
					+e.getMessage());
			throw e;
		}  catch (IOException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::manifestFranchiseeProcess::IOException occured:"
					+e.getMessage());
			throw e;
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "manifestProcess", UtilConstants.LOGGER_LEVEL_ERROR);
			LOGGER.error("CentralDataExtractorServiceImpl::manifestFranchiseeProcess::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException();
		} finally {
			extractorTo=null;
			deleteBranchDirectory(branchFolder);
		}
	}
	
	private List<ManifestDO> getManifestDataForFranchiseeCode(List<String> frCodeList,
			Integer maxSize)throws CGSystemException {
		LOGGER.debug("CentralDataExtractorServiceImpl : getBookingDataForFranchiseeCode() : START");
		List<ManifestDO> manifestDOList = null;
		try {
			manifestDOList = centralDataExtractorDAO
			.getManifestDetailsByFranchiseeCode(frCodeList, maxSize);
		} catch (CGSystemException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::getBookingDataForFranchiseeCode::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("CentralDataExtractorServiceImpl : getBookingDataForFranchiseeCode() : END");
		//LOGGER.debug(frCodeList, "BookingDoList Size : " + bookingDOList !=null?bookingDOList.size(): null);
		
		return manifestDOList;
	}
	
	private void createManifestXMLForFranchisee(
			List<ManifestDO> manifestDOList, String branchFolderPath,Boolean isFrModule, DataExtractorTO extractorTo)
	throws CGBusinessException {
		try {
			LOGGER.debug("CentralDataExtractorServiceImpl :createManifestXMLForFranchisee   ####START");
			// Create Goodsissue which will create XML for Goodsissue Data
			ManifestData jaxbObj = objectConverter
			.createdManifestTO(manifestDOList);
			
			LOGGER.debug("CentralDataExtractorServiceImpl :createManifestXMLForFranchisee jaxbObj created");
			if (jaxbObj != null) {
				String manifestXMLFileName = messageProp
				.getProperty(CentralDataExtractorConstant.MANIFESTFR_FILENAME);

				
				if(isFrModule){
					manifestXMLFileName = convertToFRModuleFileName(manifestXMLFileName);
					
				}
				String dateTimeFormat = messageProp
				.getProperty(CentralDataExtractorConstant.XMLFILE_TIMESTAMP_FORMAT);

				File branchFolder = new File(branchFolderPath);
				if (branchFolder != null && !branchFolder.exists()) {
					branchFolder.mkdirs();
				}
				LOGGER.debug(" Branch file creation ...Folder path :"
						+ branchFolder);
				String manifestXMLPath = CGXMLUtil
				 .createFileNameWithTimestamp(
						 branchFolder.getAbsolutePath() + File.separator
						 + manifestXMLFileName, dateTimeFormat,
						 ApplicationConstants.CHARACTER_UNDERSCORE);
				 LOGGER.debug("CentralDataExtractorServiceImpl :createManifestXMLForFranchisee preparing XML");
				 populateProcessInfo(manifestXMLPath,extractorTo);
						 			 		
				 /** Create the XML for Manifest */
				 new CTBSXMLParser().marshalObjectToXML(jaxbObj,
						 manifestXMLPath);

			}
		} catch (Exception e) {
			LOGGER.error("CentralDataExtractorServiceImpl::createManifestXMLForFranchisee::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("CentralDataExtractorServiceImpl :createManifestXMLForFranchisee   ####END");
	}
	


	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.service.CentralDataExtractorService#extractDataForDispatch()
	 */
	
	@Override
	public void extractDispatchDataForFRModule() throws CGBusinessException {

		LOGGER.debug("CentralDataExtractorServiceImpl : extractDispatchDataForFRModule() : START");

		// The following constants for no of records to be fetched from each
		// process (configurable ie. taking the values from
		// properties(centralDataExtraction.properties) file)

		String branchFolder = null;
		try {
			List<String> frCodeList = centralDataExtractorDAO.getAllFranchiseeCodes();

			for (String franchiseeCode : frCodeList) {

				//######################################Booking###################### START
				
				branchFolder = messageProp
				.getProperty(CentralDataExtractorConstant.XML_FILE_BASE_MULTIPLE_LOCATION)
				+ File.separator + franchiseeCode+File.separator+franchiseeCode+ApplicationConstants.CHARACTER_UNDERSCORE
				+StringUtil.generateRamdomNumber();
				
			/*	branchFolder=null;
		        branchFolder = getFolderNameFromProps()
			+ File.separator + franchiseeCode+File.separator+franchiseeCode+ApplicationConstants.CHARACTER_UNDERSCORE+StringUtil.generateRamdomNumber();*/
				try {
					LOGGER.debug("########Dispatch Start#######");
					dispatchFranchiseeProcess(franchiseeCode, frCodeList, branchFolder);
					LOGGER.debug("########Dispatch End #######");
				} catch (Exception e) {
					LOGGER.error("CentralDataExtractorServiceImpl::extractDispatchDataForFRModule::Exception occured:"
							+e.getMessage());
				}finally {
					deleteBranchDirectory(branchFolder);
				}
				//######################################Booking###################### END
			}
		}  catch (Exception ex) {
			LOGGER.error("CentralDataExtractorServiceImpl::extractDispatchDataForFRModule::Exception occured:"
					+ex.getMessage());
			
		} 
		LOGGER.debug("CentralDataExtractorServiceImpl : extractDispatchDataForFRModule() : END");
	}
	
	
	/**
	 * Dispatch process.
	 *
	 * @param branchCode the branch code
	 * @param branchFolder the branch folder
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void dispatchFranchiseeProcess(String franchiseeCode, List<String> frCodeList,
			String branchFolder) throws 
			CGBusinessException,CGSystemException, IOException {

		
		List<DispatchDO> dispatchDOList = null;
		DataExtractionDO dispatchExtractionDO =null;
		Integer maxDispatchRecords = 10;
		List<Integer> dispatchIdList = null;
		DataExtractorTO extractorTo=new DataExtractorTO();
		try {
			String maxDispatchFetch = messageProp
			.getProperty(CentralDataExtractorConstant.MAX_FETCH_SIZE_DISPACTH);
			LOGGER.debug("Max Dispatch Records to be fetched are (From Properties File )= [ "
					+ maxDispatchFetch + " ]");

			if (!StringUtils.isEmpty(maxDispatchFetch)) {
				maxDispatchRecords = Integer.parseInt(maxDispatchFetch);
				LOGGER.debug("Max Dispatch Records to be fetched are (After Conversion )= [ "
						+ maxDispatchRecords + " ]");
			} else {
				LOGGER.debug("Max Dispatch Records to be fetched are (Default values )= [ "
						+ maxDispatchRecords + " ]");
			}
			if (!StringUtil.isEmptyInteger(maxDispatchRecords)) {
				LOGGER.debug("Fetching Dispatch Records From DB");
				dispatchDOList =	getDispatchDataByFranchiseeCode(frCodeList,maxDispatchRecords);
				LOGGER.debug("##Fetching Process Completed From DB Record (Dispatch), No Of Records:"
						+ ((dispatchDOList != null && !dispatchDOList
								.isEmpty()) ? dispatchDOList.size() : "0"));
			
			}
			

			/** Create the XML files for Booking Data */
			if(dispatchDOList != null && !dispatchDOList.isEmpty()){
				LOGGER.trace("Toal Dispatch Records Found from DB are :["
						+ dispatchDOList.size() + " ]");
				createDispatchXMLForFranchisee(dispatchDOList,branchFolder, true,extractorTo); 
				dispatchIdList = new ArrayList<Integer>(dispatchDOList.size());
					for (DispatchDO dispatchDO :dispatchDOList){
						LOGGER.trace("Toal Dispatch Records with to string :["+dispatchDO.toString());
						dispatchIdList.add(dispatchDO.getDispatchId());
					}
			}else{
				LOGGER.debug("Dispatch Xml Creation is not happened due to no record found in DB");
			}

			List<DataExtractionDO> dataExtractionDoList = new ArrayList<DataExtractionDO>();
			dispatchExtractionDO = prepareExtractedData(franchiseeCode, branchFolder,
					dispatchExtractionDO,extractorTo);

			dispatchExtractionDO.setProcessName(CentralDataExtractorConstant.DISPATCH_PROCESS);
			dataExtractionDoList.add(dispatchExtractionDO);
			LOGGER.debug("Data prepared for the Office = [ " + franchiseeCode
					+ " ]");
			if (dataExtractionDoList != null
					&& !dataExtractionDoList.isEmpty()) {
				/** Save ZIP File */
				centralDataExtractorDAO
				.saveExtractedDataForBranchList(dataExtractionDoList);

				/**
				 * Update the Central DB Data Status as Read By Local -
				 * GOODS RENEWAL / CANCELLATION - PURCHASE MODULE
				 */
				centralDataExtractorDAO.updateFrSyncFlagInDispatchForFranchisee(dispatchIdList);
				/** Delete the XML Files in the Branch Folder */
				deleteBranchDirectory(branchFolder);

			}
			
		}  catch (CGBusinessException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::dispatchFranchiseeProcess::CGBusinessException occured:"
					+e.getMessage());
			throw e;
		}  catch (IOException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::dispatchFranchiseeProcess::IOException occured:"
					+e.getMessage());
			throw e;
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "dispatchProcess", UtilConstants.LOGGER_LEVEL_ERROR);
			LOGGER.error("CentralDataExtractorServiceImpl::dispatchFranchiseeProcess::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException();
		} finally {
			extractorTo=null;
			deleteBranchDirectory(branchFolder);
		}
	
	}
	
	/**
	 * Gets the dispatch data by branch code.
	 *
	 * @param branchCode the branch code
	 * @param maxRecord the max record
	 * @return the dispatch data by branch code
	 * @throws CGSystemException the cG system exception
	 */
	private List<DispatchDO> getDispatchDataByFranchiseeCode(List<String> frCodeList,
			Integer maxRecord)throws CGSystemException {
		
		LOGGER.debug("CentralDataExtractorServiceImpl : getDispatchDataByFranchiseeCode() : START");
		List<DispatchDO> dispatchDOList = null;
		try {
			dispatchDOList = centralDataExtractorDAO
			.getDispatchDataByFranchiseeCode(frCodeList, maxRecord);
		} catch (CGSystemException e) {
			LOGGER.error("CentralDataExtractorServiceImpl::getDispatchDataByFranchiseeCode::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("CentralDataExtractorServiceImpl : getDispatchDataByFranchiseeCode() : END");
		//LOGGER.debug(frCodeList, "BookingDoList Size : " + bookingDOList !=null?bookingDOList.size(): null);
		
		return dispatchDOList;
		
	}
	
	private void createDispatchXMLForFranchisee(List<DispatchDO> dispatchDOList,
			String branchFolderPath, Boolean isFrModule, DataExtractorTO extractorTo) throws CGBusinessException {
		LOGGER.debug("CentralDataExtractorServiceImpl : createDispatchXMLForFranchisee():START");
		DispatchDetailsData jaxbObj = null;
		try {
		
			jaxbObj = objectConverter.createdDispatchJaxObj(dispatchDOList);

			if (jaxbObj != null) {
				String dispatchXMLFileName = messageProp
				.getProperty(CentralDataExtractorConstant.DISPATCHFR_DETAILS_FILENAME);

				String dateTimeFormat = messageProp
				.getProperty(CentralDataExtractorConstant.XMLFILE_TIMESTAMP_FORMAT);

				File branchFolder = new File(branchFolderPath);
				if (!branchFolder.isDirectory()) {
					branchFolder.mkdirs();
				}

				String dispatchXMLPath = CGXMLUtil.createFileNameWithTimestamp(
						branchFolder.getAbsolutePath() + File.separator
						+ dispatchXMLFileName, dateTimeFormat,
						ApplicationConstants.CHARACTER_UNDERSCORE);

				populateProcessInfo(dispatchXMLPath,extractorTo);
				/** Create the XML for Booking */
				new CTBSXMLParser()
				.marshalObjectToXML(jaxbObj, dispatchXMLPath);
				LOGGER.debug("CentralDataExtractorServiceImpl : createDispatchXMLForBranch():END");
			}
		} catch (Exception e) {
			LOGGER.error("CentralDataExtractorServiceImpl : createDispatchXMLForBranch() : Error while creating XML for DispatchDO ..."
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
	}
}
