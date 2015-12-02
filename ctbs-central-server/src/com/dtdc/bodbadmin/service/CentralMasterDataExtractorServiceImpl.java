/**
 * 
 */
package com.dtdc.bodbadmin.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import net.sf.json.JSONSerializer;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.ApplicationConstants;
import com.capgemini.lbs.framework.constants.BusinessConstants;
import com.capgemini.lbs.framework.constants.UtilConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.exception.file.FolderRenamingException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.frameworkbaseTO.UserTO;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
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
import com.dtdc.domain.login.UserDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.RtnToOrgDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.purchase.GoodsIssueDO;
import com.dtdc.domain.purchase.GoodsRenewalDO;
import com.dtdc.domain.purchase.GoodscCancellationDO;
import com.dtdc.domain.transaction.delivery.DeliveryDO;
import com.dtdc.domain.transaction.heldup.HeldUpReleaseDO;
import com.dtdc.to.manifestextractor.DataExtractorTO;

/**
 * @author nisahoo
 * 
 */
public class CentralMasterDataExtractorServiceImpl implements
CentralDataExtractorService {

	private static final org.slf4j.Logger LOGGER = LoggerFactory
	.getLogger(CentralMasterDataExtractorServiceImpl.class);
	public transient JSONSerializer serializer;
	private Properties messageProp;
	private CentralDataExtractorDAO centralDataExtractorDAO;
	private DomainToTransferObjectConverter objectConverter;

	public Properties getMessageProp() {
		return messageProp;
	}

	public void setMessageProp(Properties messageProp) {
		this.messageProp = messageProp;
	}

	public CentralDataExtractorDAO getCentralDataExtractorDAO() {
		return centralDataExtractorDAO;
	}

	public void setCentralDataExtractorDAO(
			CentralDataExtractorDAO centralDataExtractorDAO) {
		this.centralDataExtractorDAO = centralDataExtractorDAO;
	}

	public DomainToTransferObjectConverter getObjectConverter() {
		return objectConverter;
	}

	public void setObjectConverter(
			DomainToTransferObjectConverter objectConverter) {
		this.objectConverter = objectConverter;
	}

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
			LOGGER.error("Error occured while fetching Central DB Data..."
					+ ex.getMessage());
			
		} finally {
			deleteBranchDirectory(branchFolder);
		}
		LOGGER.debug("CentralDataExtractorServiceImpl : extractDataForBranch() : END");
	}
	
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
					LOGGER.debug("########Booking Exception #######"+e.getMessage());
				}finally {
					deleteBranchDirectory(branchFolder);
				}
				//######################################Booking###################### END
			}
		}  catch (Exception ex) {
			LOGGER.error("Error occured while fetching Central DB Data..."
					+ ex.getMessage());
			
		} 
		
		LOGGER.debug("CentralDataExtractorServiceImpl : extractDataForBooking() : END");
	}
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
			LOGGER.error("Error occured while fetching Central DB Data..."
					+ ex.getMessage());
			
		} finally {
			deleteBranchDirectory(branchFolder);
		}
		LOGGER.debug("CentralDataExtractorServiceImpl : extractDataForBranch() : END");
	}
	
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
			LOGGER.error("########DISPATCH Exception #######"+e.getMessage());
		}finally {
			deleteBranchDirectory(branchFolder);
		}
		//###################################### DISPATCH ###################### END
			 
			return branchFolder;
		}

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
				LOGGER.error("########Heldup Release Exception #######"+e.getMessage());
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
				LOGGER.error("########RTO Exception #######"+e.getMessage());
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
				LOGGER.error("########MANIFEST Exception #######"+e.getMessage());
			}finally {
				deleteBranchDirectory(branchFolder);
			}
			//###################################### MANIFEST ###################### END
			branchFolder=null;
			branchFolder = getFolderNameFromProps()
			+ File.separator + branchCode+File.separator+branchCode+ApplicationConstants.CHARACTER_UNDERSCORE+StringUtil.generateRamdomNumber();
			
			//###################################### USER ###################### START
			try {
				LOGGER.debug("########USER START#######");
				userProcess(branchCode, branchFolder);
				LOGGER.debug("########USER END#######");
			} catch (Exception e) {
				LOGGER.error("########MANIFEST Exception #######"+e.getMessage());
			}finally {
				deleteBranchDirectory(branchFolder);
			}
			//###################################### USER ###################### END
			
			
			//###################################### DELIVERY ###################### START
			try {
				LOGGER.debug("########DELIVERY START#######");
				deliveryProcess(branchCode, branchFolder);
				LOGGER.debug("########DELIVERY END#######");
			} catch (Exception e) {
				LOGGER.error("########DELIVERY Exception #######"+e.getMessage());
			}finally {
				deleteBranchDirectory(branchFolder);
			}
			//###################################### DELIVERY ###################### END

			
		 
		return branchFolder;
	}

	private String getFolderNameFromProps() {
		return messageProp
		.getProperty(CentralDataExtractorConstant.XML_DATA_BASE_DIR);
	}

	/**
	 * @param branchCode
	 * @param branchFolder
	 * @throws NumberFormatException
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * @throws IOException
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
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (extractDataForBranch)while fetching Central DB Data...(CGBusinessException):"
					+ e.getMessage());
			throw e;
		} catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error occured(extractDataForBranch) while fetching Central DB Data...(CGSystemException):"
					+ e.getMessage());
			throw e;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (extractDataForBranch) while fetching Central DB Data--(IOException)..."
					+ e.getMessage());
			throw e;
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "deliveryProcess", UtilConstants.LOGGER_LEVEL_ERROR);
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (extractDataForBranch)while fetching Central DB Data..."
					+ e.getMessage());
			throw new CGBusinessException();
		} finally {
			extractorTo = null;
			deleteBranchDirectory(branchFolder);
		}
	}

	/**
	 * @param branchCode
	 * @param branchFolder
	 * @return
	 * @throws NumberFormatException
	 * @throws CGBusinessException
	 * @throws IOException
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
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (extractDataForBranch)while fetching Central DB Data...(CGBusinessException):"
					+ e.getMessage());
			throw e;
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error occured(extractDataForBranch) while fetching Central DB Data...(CGSystemException):"
					+ e.getMessage());
			throw e;

		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (extractDataForBranch) while fetching Central DB Data--(IOException)..."
					+ e.getMessage());
			throw e;
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "dispatchProcess", UtilConstants.LOGGER_LEVEL_ERROR);
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (extractDataForBranch)while fetching Central DB Data..."
					+ e.getMessage());
			throw new CGBusinessException();
		} finally {
			deleteBranchDirectory(branchFolder);
		}
	}

	/**
	 * @param branchCode
	 * @param branchFolder
	 * @throws NumberFormatException
	 * @throws CGBusinessException
	 * @throws IOException
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
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (extractDataForBranch)while fetching Central DB Data...(CGBusinessException):"
					+ e.getMessage());
			throw e;
		} catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error occured(extractDataForBranch) while fetching Central DB Data...(CGSystemException):"
					+ e.getMessage());
			throw e;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (extractDataForBranch) while fetching Central DB Data--(IOException)..."
					+ e.getMessage());
			throw e;
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "manifestProcess", UtilConstants.LOGGER_LEVEL_ERROR);
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (extractDataForBranch)while fetching Central DB Data..."
					+ e.getMessage());
			throw new CGBusinessException();
		} finally {
			extractorTo=null;
			deleteBranchDirectory(branchFolder);
		}
	}

	/**
	 * @param branchCode
	 * @param branchFolder
	 * @throws NumberFormatException
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 * @throws IOException
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
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (extractDataForBranch)while fetching Central DB Data...(CGBusinessException):"
					+ e.getMessage());
			throw e;
		} catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error occured(extractDataForBranch) while fetching Central DB Data...(CGSystemException):"
					+ e.getMessage());
			throw e;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (extractDataForBranch) while fetching Central DB Data--(IOException)..."
					+ e.getMessage());
			throw e;
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "returnToOriginProcess", UtilConstants.LOGGER_LEVEL_ERROR);
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (extractDataForBranch)while fetching Central DB Data..."
					+ e.getMessage());
			throw new CGBusinessException();
		} finally {
			extractorTo = null;
			deleteBranchDirectory(branchFolder);
		}
	}

	/**
	 * @param branchCode
	 * @param branchFolder
	 * @return
	 * @throws NumberFormatException
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * @throws IOException
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
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (heldupReleaseProcess)while fetching Central DB Data...(CGBusinessException):"
					+ e.getMessage());
			throw e;
		} catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error occured(heldupReleaseProcess) while fetching Central DB Data...(CGSystemException):"
					+ e.getMessage());
			throw e;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (heldupReleaseProcess) while fetching Central DB Data--(IOException)..."
					+ e.getMessage());
			throw e;
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "heldupReleaseProcess", UtilConstants.LOGGER_LEVEL_ERROR);
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (heldupReleaseProcess)while fetching Central DB Data..."
					+ e.getMessage());
			throw new CGBusinessException();
		} finally {
			deleteBranchDirectory(branchFolder);
			extractorTo = null;
		}
	}

	/**
	 * @param branchCode
	 * @param branchFolder
	 * @throws NumberFormatException
	 * @throws CGBusinessException
	 * @throws IOException
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
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (bookingProcess)while fetching Central DB Data...(CGBusinessException):"
					+ e.getMessage());
			throw e;
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (bookingProcess) while fetching Central DB Data--(IOException)..."
					+ e.getMessage());
			throw e;
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "bookingProcess", UtilConstants.LOGGER_LEVEL_ERROR);
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (bookingProcess)while fetching Central DB Data..."
					+ e.getMessage());
			throw new CGBusinessException();
		} finally {
			extractorTo=null;
			deleteBranchDirectory(branchFolder);
		}
	}
	
	private void userProcess(String branchCode, String branchFolder)
	throws  CGBusinessException,CGSystemException, IOException {
		List<UserDO> userDOList = null;
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
				userDOList = getUsersForBranch(branchCode,
						maxManifestRecords);
				
				LOGGER.debug("##Fetching Process Completed From DB Record (manifestProcess), No Of Records:"
						+ ((userDOList != null && !userDOList.isEmpty()) ? userDOList
								.size() : "0"));
			}			
			if(userDOList != null && !userDOList.isEmpty()) {
				createUserXMLForBranch(userDOList,branchFolder, extractorTo); 
			}
			manifestExtractionDO = prepareExtractedData(branchCode, branchFolder,
					manifestExtractionDO, extractorTo);

			if (manifestExtractionDO != null&& userDOList != null && !userDOList.isEmpty()) {
				/** Save ZIP File */
				centralDataExtractorDAO
				.saveExtractedDataForBranch(manifestExtractionDO, null);

				centralDataExtractorDAO
				.updateUserReadByLocal(userDOList);
				/** Delete the XML Files in the Branch Folder */
				deleteBranchDirectory(branchFolder);

			}
		}catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (extractDataForBranch)while fetching Central DB Data...(CGBusinessException):"
					+ e.getMessage());
			throw e;
		} catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error occured(extractDataForBranch) while fetching Central DB Data...(CGSystemException):"
					+ e.getMessage());
			throw e;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (extractDataForBranch) while fetching Central DB Data--(IOException)..."
					+ e.getMessage());
			throw e;
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "userProcess", UtilConstants.LOGGER_LEVEL_ERROR);
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (extractDataForBranch)while fetching Central DB Data..."
					+ e.getMessage());
			throw new CGBusinessException();
		} finally {
			extractorTo=null;
			deleteBranchDirectory(branchFolder);
		}
	}
	/**
	 * @param branchCode
	 * @param branchFolder
	 * @param dataExtractionDO
	 * @param extractorTo TODO
	 * @return
	 * @throws IOException
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

	private void deleteBranchDirectory(String branchFolderPath) {
		File branchFolder = new File(branchFolderPath);
		try {
			FileUtils.renameFolder(branchFolderPath,branchFolderPath+ApplicationConstants.CHARACTER_UNDERSCORE+ApplicationConstants.DIR_PROCESSED);
		} catch (FolderRenamingException e) {
			LOGGER.debug("CentralDataExtractorServiceImpl :deleteBranchDirectory--> renameFolder() : Exception"+e.getMessage());
		}
		
		
	}

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

	private List<ManifestDO> getOutgoingManifetsForBranch(String branchCode, Integer maxRecord)throws CGSystemException {
		LOGGER.debug("CentralDataExtractorServiceImpl : getOutgoingManifetsForBranch() : Start");
		List<ManifestDO> manifestDOList = centralDataExtractorDAO
		.getOutgoingManifestsForBranch(branchCode, maxRecord);
		LOGGER.debug("CentralDataExtractorServiceImpl : getOutgoingManifetsForBranch() : END");
		return manifestDOList;
	}



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
			LOGGER.error("Error while creating XML for Manifest..."
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
	}

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
			LOGGER.error("Error while creating XML for Booking..."
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		
		LOGGER.debug("CentralDataExtractorServiceImpl : createBookingXMLForBranch():END");
	}

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
	 * @param goodsCanclDOList
	 *            the goods cancl do list
	 * @param branchFolderPath
	 *            the branch folder path
	 * @throws CGBusinessException
	 *             the cG business exception
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
	 * @param goodsRenewalXMLFileName
	 */
	private String converToFRModuleFileName(String goodsRenewalXMLFileName) {
		StringBuffer strFileName = new StringBuffer(goodsRenewalXMLFileName);
		strFileName.insert(goodsRenewalXMLFileName.lastIndexOf(ApplicationConstants.CHARACTER_DOT),CentralDataExtractorConstant.FR_MODULE );
	return strFileName.toString();
	}

	List<HeldUpReleaseDO> getHeldUpDtlsByBranchCode(String branchCode,
			Integer maxRecords) throws CGBusinessException, CGSystemException {
		LOGGER.info("CentralDataExtractorServiceImpl : getHeldUpDtlsByBranchCode():START");
		List<HeldUpReleaseDO> heldUpDOList = centralDataExtractorDAO
		.getHeldUpDtlsByBranchCode(branchCode, maxRecords);
		LOGGER.info("CentralDataExtractorServiceImpl : getHeldUpDtlsByBranchCode():END");
		return heldUpDOList;
	}

	private List<GoodsRenewalDO> getGoodsRenewalDataForBranchCode(
			List<String> branchList, Integer maxSize) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("CentralDataExtractorServiceImpl : getGoodsRenewalDataForBranchCode():START");
		List<GoodsRenewalDO> goodsRenewalDOList = centralDataExtractorDAO
		.getGoodsRenewalDataByBranchCode(branchList, maxSize);
		LOGGER.debug("CentralDataExtractorServiceImpl : getGoodsRenewalDataForBranchCode():END");
		return goodsRenewalDOList;
	}
	

	private List<GoodscCancellationDO> getGoodsCanclDataForBranchCode(
			List<String> officesList, Integer maxSize) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("CentralDataExtractorServiceImpl : getGoodsCanclDataForBranchCode():START");
		List<GoodscCancellationDO> goodsCanclDOList = centralDataExtractorDAO
		.getGoodsCanclDataByBranchCode(officesList, maxSize);
		LOGGER.debug("CentralDataExtractorServiceImpl : getGoodsCanclDataForBranchCode():END");
		return goodsCanclDOList;
	}
	
	private List<GoodscCancellationDO> getGoodsCanclDataForFRModule(
			List<String> frList, Integer maxSize) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("CentralDataExtractorServiceImpl : getGoodsCanclDataForBranchCode():START");
		List<GoodscCancellationDO> goodsCanclDOList = centralDataExtractorDAO.getGoodsCanclDataForFrModule(frList, maxSize);
		LOGGER.debug("CentralDataExtractorServiceImpl : getGoodsCanclDataForBranchCode():END");
		return goodsCanclDOList;
	}

	private List<DispatchDO> getDispatchDataByBranchCode(String branchCode,
			Integer maxRecord)throws CGSystemException {
		List<DispatchDO> dispatchDOList = centralDataExtractorDAO
		.getDispatchDataByBranchCode(branchCode, maxRecord);
		return dispatchDOList;
	}

	/**
	 * Added by Narasimha Rao Kattunga Getting delivery manifest details by
	 * branch code
	 */
	List<DeliveryDO> getDeliveryMnfstDtlsByBranchCode(String branchCode,
			Integer maxRecords) throws CGBusinessException, CGSystemException {
		LOGGER.info("CentralDataExtractorServiceImpl : getDeliveryMnfstDtlsByBranchCode():START");
		List<DeliveryDO> dlvMnfstDOList = centralDataExtractorDAO
		.getDeliveryManifestDtlsByBranchCode(branchCode, maxRecords);
		LOGGER.info("CentralDataExtractorServiceImpl : getDeliveryMnfstDtlsByBranchCode():END");
		return dlvMnfstDOList;
	}

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
			LOGGER.error("Error while creating XML for Heldup..."
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
	}

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
			LOGGER.error("Error while creating XML for createGoodsIssueXMLForBranch..."
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("CentralDataExtractorServiceImpl :createGoodsIssueXMLForBranch   ####END");
	}

	private List<GoodsIssueDO> getGoodsIssueDataForBranchCode(
			List branchCodeList, Integer maxSize) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("CentralDataExtractorServiceImpl : getGoodsIssueDataForBranchCode():START");
		List<GoodsIssueDO> goodsIssueDoList = centralDataExtractorDAO
		.getGoodsIssueDataByBranchCode(branchCodeList, maxSize);
		LOGGER.debug("CentralDataExtractorServiceImpl : getGoodsIssueDataForBranchCode():END");
		return goodsIssueDoList;
	}
	
	private List<GoodsIssueDO> getGoodsIssueDataForFRModule(
			List<String> frListList, Integer maxSize) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("CentralDataExtractorServiceImpl : getGoodsIssueDataForFRModule():START");
		List<GoodsIssueDO> goodsIssueDoList = centralDataExtractorDAO
		.getGoodsIssueDataForFrModule(frListList, maxSize);
		LOGGER.debug("CentralDataExtractorServiceImpl : getGoodsIssueDataForFRModule():END");
		return goodsIssueDoList;
	}

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
			LOGGER.error("Error while creating XML for createGoodsIssueXMLForBranch..."
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
	}

	private List<RtnToOrgDO> getRtoDataForBranchCode(String branchCode,
			Integer maxSize) throws CGSystemException {
		List<RtnToOrgDO> rtnToOrgDOList = centralDataExtractorDAO
		.getRtoDataForBranchCode(branchCode, maxSize);
		return rtnToOrgDOList;
	}

	private List<BookingDO> getBookingDataForBranchCode(String branchCode,
			Integer maxSize)throws CGSystemException {
		LOGGER.debug("CentralDataExtractorServiceImpl : getBookingDataForBranchCode() : START");
		List<BookingDO> bookingDOList = null;
		try {
			bookingDOList = centralDataExtractorDAO
			.getBookingDetailsByOfficeCode(branchCode, maxSize);
		} catch (CGSystemException e) {
			LOGGER.error("getBookingDataForBranchCode Exception"+e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("CentralDataExtractorServiceImpl : getBookingDataForBranchCode() : END");
		LOGGER.debug(branchCode, "BookingDoList Size : " + bookingDOList !=null?bookingDOList.size(): null);
		
		return bookingDOList;
	}

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
			LOGGER.error("Error occured while fetching Central DB Data.(extractGoodsIssueDataForAllBranches)..(IOException)"
					+ ex.getMessage());
		} catch (Exception ex) {
			CGStackTraceUtil.getStackTraceException(ex,  LOGGER, "extractGoodsIssueDataForAllBranches", UtilConstants.LOGGER_LEVEL_ERROR);
			LOGGER.error("Error occured while fetching Central DB Data(extractGoodsIssueDataForAllBranches)..."
					+ ex.getMessage());
			throw new CGBusinessException();
		} finally {
			deleteBranchDirectory(branchFolder);
		}
		LOGGER.debug("CentralDataExtractorServiceImpl : extractGoodsIssueDataForAllBranches() : END");

	}

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
			// TODO Auto-generated catch block
			LOGGER.error("Error occured while fetching Central DB Data...(CGBusinessException):"
					+ e.getMessage());
			throw e;
		} catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error occured while fetching Central DB Data...(CGSystemException):"
					+ e.getMessage());
			throw e;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error occured while fetching Central DB Data--(IOException)..."
					+ e.getMessage());
			throw e;
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "extractingGoodsIssueDataForBranch", UtilConstants.LOGGER_LEVEL_ERROR);
			// TODO Auto-generated catch block
			LOGGER.error("Error occured while fetching Central DB Data..."
					+ e.getMessage());
			throw new CGBusinessException();
		} finally {
			deleteBranchDirectory(branchFolder);
		}
		return branchFolder;
	}

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
	 * Added by Narasimha Rao Kattunga Creating delivery manifest for branch
	 * @param dataExtractorTo TODO
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
					LOGGER.error("Error occured while fetching Central DB Data.(extractGoodsIssueDataForAllFracnhisees)..(IOException)"
							+ ex.getMessage());
				} catch (Exception ex) {
					LOGGER.error("Error occured while fetching Central DB Data(extractGoodsIssueDataForAllFracnhisees)..."
							+ ex.getMessage());

				} finally {
					deleteBranchDirectory(branchFolder);
				}
			}

		} catch (Exception ex) {
			CGStackTraceUtil.getStackTraceException(ex,  LOGGER, "extractGoodsIssueDataForAllFracnhisees", UtilConstants.LOGGER_LEVEL_ERROR);
			LOGGER.error("Error occured while fetching Central DB Data(extractGoodsIssueDataForAllFracnhisees)..."
					+ ex.getMessage());
			throw new CGBusinessException();
		} finally {
			deleteBranchDirectory(branchFolder);
		}
		LOGGER.debug("CentralDataExtractorServiceImpl : extractGoodsIssueDataForAllFracnhisees() : END");

	}
	
	public String extractingGoodsIssueDataForFranchisee(String franchiseeCode,List<String> frCodeList)
	throws CGBusinessException, CGSystemException, IOException {
		
		
		String branchFolder=null;
		try {
			LOGGER.debug("CentralDataExtractorServiceImpl : extractingGoodsIssueDataForFranchisee():goodsIssueFranchiseeProcess : START");
			branchFolder = goodsIssueFranchiseeProcess(franchiseeCode,
					frCodeList);
		
		}  catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "extractGoodsIssueDataForAllFracnhisees", UtilConstants.LOGGER_LEVEL_ERROR);
			// TODO Auto-generated catch block
			LOGGER.error("Error occured while fetching Central DB Data..."
					+ e.getMessage());
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
		// TODO Auto-generated catch block
		LOGGER.error("Error occured while fetching Central DB Data..."
				+ e.getMessage());
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
			// TODO Auto-generated catch block
			LOGGER.error("Error occured while fetching Central DB Data..."
					+ e.getMessage());
		} finally {
			deleteBranchDirectory(branchFolder);
		}
		LOGGER.debug("CentralDataExtractorServiceImpl : goodsCancellationFranchiseeProcess():goodsRenewalFranchiseeProcess : END");
		return branchFolder;
	}

	/**
	 * @param franchiseeCode
	 * @param frCodeList
	 * @return
	 * @throws NumberFormatException
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 * @throws IOException
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
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (goodsRenewalFranchiseeProcess)while fetching Central DB Data...(CGBusinessException):"
					+ e.getMessage());
			throw e;
		} catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error occured(goodsRenewalFranchiseeProcess) while fetching Central DB Data...(CGSystemException):"
					+ e.getMessage());
			throw e;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (goodsRenewalFranchiseeProcess) while fetching Central DB Data--(IOException)..."
					+ e.getMessage());
			throw e;
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "goodsRenewalFranchiseeProcess", UtilConstants.LOGGER_LEVEL_ERROR);
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (goodsRenewalFranchiseeProcess)while fetching Central DB Data..."
					+ e.getMessage());
			throw new CGBusinessException();
		} finally {
			deleteBranchDirectory(branchFolder);
		}
		return branchFolder;
	}

	/**
	 * @param franchiseeCode
	 * @param frCodeList
	 * @return
	 * @throws NumberFormatException
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * @throws IOException
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
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (goodsCancellationFranchiseeProcess)while fetching Central DB Data...(CGBusinessException):"
					+ e.getMessage());
			throw e;
		} catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error occured(goodsCancellationFranchiseeProcess) while fetching Central DB Data...(CGSystemException):"
					+ e.getMessage());
			throw e;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (goodsCancellationFranchiseeProcess) while fetching Central DB Data--(IOException)..."
					+ e.getMessage());
			throw e;
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "goodsCancellationFranchiseeProcess", UtilConstants.LOGGER_LEVEL_ERROR);
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (goodsCancellationFranchiseeProcess)while fetching Central DB Data..."
					+ e.getMessage());
			throw new CGBusinessException();
		} finally {
			deleteBranchDirectory(branchFolder);
		}
		return branchFolder;
	}

	/**
	 * @param franchiseeCode
	 * @param frCodeList
	 * @return
	 * @throws NumberFormatException
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * @throws IOException
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
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (goodsIssueFranchiseeProcess)while fetching Central DB Data...(CGBusinessException):"
					+ e.getMessage());
			throw e;
		} catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error occured(goodsIssueFranchiseeProcess) while fetching Central DB Data...(CGSystemException):"
					+ e.getMessage());
			throw e;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (goodsIssueFranchiseeProcess) while fetching Central DB Data--(IOException)..."
					+ e.getMessage());
			throw e;
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "goodsIssueFranchiseeProcess", UtilConstants.LOGGER_LEVEL_ERROR);
			// TODO Auto-generated catch block
			LOGGER.error("Error occured (goodsIssueFranchiseeProcess)while fetching Central DB Data..."
					+ e.getMessage());
			throw new CGBusinessException();
		} finally {
			deleteBranchDirectory(branchFolder);
		}
		return branchFolder;
	}

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

	private void createUserXMLForBranch(List<UserDO> userDOList,
			String branchFolderPath, DataExtractorTO dataExtractorTo) throws CGBusinessException {
		try {
			// Create ManifestTO which will create XML for MANIFEST Data
//			ManifestData jaxbObj = objectConverter
//			.createdManifestTO(userDOList);
//			
			Iterator itr = userDOList.iterator();
			List<CGBaseTO> baseToList =new ArrayList<CGBaseTO>();
			
			CGBaseTO BaseTO = (CGBaseTO) new UserTO();
			
			while (itr.hasNext()) {
				UserDO userDo= (UserDO)itr.next();
				try {
					UserTO userTo =new UserTO();
					PropertyUtils.copyProperties(userTo, userDo);
					baseToList.add(userTo);
				} catch (Exception obj) {
					CGStackTraceUtil.getStackTraceException(obj,  LOGGER, "createUserXMLForBranch", UtilConstants.LOGGER_LEVEL_ERROR);
					throw new CGBusinessException(obj.getMessage());
				}
				
			}
				BaseTO.setBaseList(baseToList);
				BaseTO.setObjectType(UserTO.class);
				BaseTO.setRequestType(ApplicationConstants.VALIDATION_REQUEST);
				BaseTO.setBeanId(CentralDataExtractorConstant.SERVICE_NAME);
				BaseTO.setClassType(CentralDataExtractorConstant.SERVICE_CLASS_NAME);
				BaseTO.setMethodName(CentralDataExtractorConstant.LOAD_DATA_FOR_BRANCH_METHOD);
				String userXMLFileName = messageProp
				.getProperty(CentralDataExtractorConstant.USER_FILENAME);

				String dateTimeFormat = messageProp
				.getProperty(CentralDataExtractorConstant.XMLFILE_TIMESTAMP_FORMAT);
				
				serializer = CGJasonConverter.getJsonObject();
				String userJsonObj = serializer.toJSON(BaseTO).toString();
				
				File branchFolder = new File(branchFolderPath);
				if (!branchFolder.isDirectory()) {
					branchFolder.mkdirs();
				}
				String userXMLPath = CGXMLUtil.createFileNameWithTimestamp(
						branchFolder.getAbsolutePath() + File.separator
						+ userXMLFileName, dateTimeFormat,
						ApplicationConstants.CHARACTER_UNDERSCORE);

				/** Create the XML for Manifest */
				
				populateProcessInfo(userXMLPath,dataExtractorTo);
				
				FileOutputStream os = null, os1 = null;
				
					if (userJsonObj != null) {
						File test = new File(userXMLPath);
						if (!test.exists()) {
							test.createNewFile();
						}
						byte buf[] = userJsonObj.getBytes(); 
						os = new FileOutputStream(test);
						for (int i=0; i < buf.length; i += 2) { 
							os.write(buf[i]);
						}
						if (os != null) {
							os.close();
						}
						os1 = new FileOutputStream(test); 
						os1.write(buf); 
						os1.close(); 
						
						if (os1 != null) {
							os1.close();
						}
						System.out.println(os);
					}

			
		} catch (Exception e) {
			LOGGER.error("Error while creating XML for Manifest..."
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
	}

	private List<UserDO> getUsersForBranch(String branchCode,
			Integer maxSize)throws CGSystemException {
		LOGGER.debug("CentralDataExtractorServiceImpl : getBookingDataForBranchCode() : START");
		List<UserDO> userDOList = null;
		try {
			userDOList = centralDataExtractorDAO
			.getUserDetailsByOfficeCode(branchCode, maxSize);
		} catch (CGSystemException e) {
			LOGGER.error("getBookingDataForBranchCode Exception"+e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("CentralDataExtractorServiceImpl : getBookingDataForBranchCode() : END");
		LOGGER.debug(branchCode, "BookingDoList Size : " + userDOList !=null?userDOList.size(): null);
		
		return userDOList;
	}

	@Override
	public void extractBookingDataForFRModule() throws CGBusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void extractManifestDataForFRModule() throws CGBusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void extractDeliveryDataForFRModule() throws CGBusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void extractDispatchDataForFRModule() throws CGBusinessException {
		// TODO Auto-generated method stub
		
	}

}
