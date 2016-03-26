package com.ff.web.manifest.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.ConsignmentModificationTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.BookingConsignmentDO;
import com.ff.domain.manifest.GridItemOrderDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.manifest.BranchOutManifestDoxDetailsTO;
import com.ff.manifest.BranchOutManifestDoxTO;
import com.ff.manifest.ManifestFactoryTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.OutManifestDoxTO;
import com.ff.manifest.OutManifestValidate;
import com.ff.universe.manifest.dao.ManifestUniversalDAO;
import com.ff.universe.manifest.service.OutManifestUniversalService;
import com.ff.web.consignment.service.ConsignmentService;
import com.ff.web.manifest.Utils.ManifestUtil;
import com.ff.web.manifest.action.BranchManifestPage;
import com.ff.web.manifest.action.BranchManifestPageContent;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.converter.BranchOutManifestDoxConverter;
import com.ff.web.manifest.converter.OutManifestBaseConverter;
//import com.ff.manifest.ManifestInputsTO;

public class BranchOutManifestDoxServiceImpl implements
		BranchOutManifestDoxService {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BranchOutManifestDoxServiceImpl.class);

	private ManifestUniversalDAO manifestUniversalDAO;
	private OutManifestUniversalService outManifestUniversalService;
	private ConsignmentService consignmentService;
	private OutManifestCommonService outManifestCommonService;
	/** The manifestCommonService. */
	private ManifestCommonService manifestCommonService;
	
	

	/**
	 * @param manifestCommonService
	 *            the manifestCommonService to set
	 */
	public void setManifestCommonService(
			ManifestCommonService manifestCommonService) {
		this.manifestCommonService = manifestCommonService;
	}

	/**
	 * @param outManifestCommonService
	 *            the outManifestCommonService to set
	 */
	public void setOutManifestCommonService(
			OutManifestCommonService outManifestCommonService) {
		this.outManifestCommonService = outManifestCommonService;
	}

	public void setOutManifestUniversalService(
			OutManifestUniversalService outManifestUniversalService) {
		this.outManifestUniversalService = outManifestUniversalService;
	}

	public void setManifestUniversalDAO(
			ManifestUniversalDAO manifestUniversalDAO) {
		this.manifestUniversalDAO = manifestUniversalDAO;
	}

	public void setConsignmentService(ConsignmentService consignmentService) {
		this.consignmentService = consignmentService;
	}

	public BranchOutManifestDoxDetailsTO getConsignmentDtls(
			OutManifestValidate cnValidateTO) throws CGBusinessException,
			CGSystemException {

		BranchOutManifestDoxDetailsTO branchOutManifestDoxDtlTO = null;
		// Get the booking details
		branchOutManifestDoxDtlTO = getBookingDtlsByConsignmentNo(cnValidateTO);
		if (StringUtil.isNull(branchOutManifestDoxDtlTO)) {
			// TODO: get the incoming details if booking details are not found
		}
		return branchOutManifestDoxDtlTO;
	}

	public BranchOutManifestDoxDetailsTO getBookingDtlsByConsignmentNo(
			OutManifestValidate cnValidateTO) throws CGBusinessException,
			CGSystemException {
		BranchOutManifestDoxDetailsTO branchOutManifestDoxDtlTO = null;
		/*
		 * List<ConsignmentModificationTO> consignmentModificationTOs =
		 * outManifestUniversalService .getBookingDtls(manifestFactoryTO);
		 */
		ConsignmentModificationTO consModifcatnTO = cnValidateTO
				.getConsignmentModificationTO();
		if (!StringUtil.isNull(consModifcatnTO)) {
			/*
			 * branchOutManifestDoxDtlTO = (BranchOutManifestDoxDetailsTO)
			 * OutManifestBaseConverter .cnDtlsToOutMnfstDtlBaseConverter(
			 * consignmentModificationTOs.get(0), manifestFactoryTO);
			 */

			branchOutManifestDoxDtlTO = (BranchOutManifestDoxDetailsTO) BranchOutManifestDoxConverter
					.cnDtlsToBranchOutMnfstDtlBaseConverter(consModifcatnTO);

		}
		return branchOutManifestDoxDtlTO;
	}

	@Override
	public BranchOutManifestDoxTO searchManifestDtls(ManifestInputs manifestTO)
			throws CGBusinessException, CGSystemException {

		LOGGER.trace("BranchOutManifestDoxServiceImpl :: searchManifestDtls() :: START------------>:::::::");
		ManifestDO manifestDO = null;
		BranchOutManifestDoxTO branchOutManifestDoxTO = null;
		Long startTime = System.currentTimeMillis();
		// manifestDO = manifestUniversalDAO.searchManifestDtls(manifestTO);
		manifestDO = OutManifestBaseConverter.prepateManifestDO(manifestTO);
		manifestDO = manifestCommonService.getDoxManifest(manifestDO);

		if (!StringUtil.isNull(manifestDO)) {
			GridItemOrderDO gridItemOrderDO = new GridItemOrderDO();
			gridItemOrderDO.setGridPosition(manifestDO.getGridItemPosition());
			gridItemOrderDO.setConsignmentDOs(manifestDO.getConsignments());
			gridItemOrderDO.setComailDOs(manifestDO.getComails());
			gridItemOrderDO = manifestCommonService.arrangeOrder(gridItemOrderDO,  ManifestConstants.ACTION_SEARCH);
			manifestDO.setConsignments(gridItemOrderDO.getConsignmentDOs());
			manifestDO.setComails(gridItemOrderDO.getComailDOs());
			branchOutManifestDoxTO = BranchOutManifestDoxConverter
					.branchOutManifestDoxDomainConverter(manifestDO);
		} else {
			throw new CGBusinessException(
					ManifestErrorCodesConstants.MANIFEST_DETAILS_NOT_FOUND);
		}
		Long endTime = System.currentTimeMillis();
		LOGGER.trace("BranchOutManifestDoxServiceImpl :: searchManifestDtls() :: END------------>:::::::::::::Time Diff:["+(endTime-startTime)+"]");
		return branchOutManifestDoxTO;
	}

	public BranchOutManifestDoxDetailsTO getInManifestdConsignmentDtls(
			ManifestFactoryTO manifestFactoryTO) throws CGBusinessException,
			CGSystemException {

		BranchOutManifestDoxDetailsTO branchOutManifstDetailsTO = null;

		/*List<ConsignmentTO> consignmtTOs = outManifestUniversalService
				.getConsgDtls(manifestFactoryTO);*/
		ConsignmentTO consignmntTO=outManifestUniversalService.getConsingmentDtls(manifestFactoryTO.getConsgNumber());

		branchOutManifstDetailsTO = convertConsgDtlsTOListToBranchOutDetailsTO(
				consignmntTO, manifestFactoryTO);

		return branchOutManifstDetailsTO;
	}

	private BranchOutManifestDoxDetailsTO convertConsgDtlsTOListToBranchOutDetailsTO(
			ConsignmentTO consignmntTO,
			ManifestFactoryTO manifestFactoryTO) throws CGBusinessException,
			CGSystemException {
		BranchOutManifestDoxDetailsTO barnchOutManifestDetailsTO = null;

		if (!StringUtil.isNull(consignmntTO)) {
			barnchOutManifestDetailsTO = (BranchOutManifestDoxDetailsTO) BranchOutManifestDoxConverter
					.branchOutGridDetailsForInManifConsg(consignmntTO);
		}
		return barnchOutManifestDetailsTO;
	}

	/*public void deleteFromComailManifest(Integer[] comailmanifstIdArray) {

		manifestUniversalDAO.deleteFromComailManifest(comailmanifstIdArray);
	}*/

	public String saveOrUpdateBranchOutManifestDox(ManifestDO manifest,
			BranchOutManifestDoxTO branchOutManifestDoxTO, List<BookingDO> allBooking,
			List<ConsignmentDO> pickupConsignment,
			Set<ConsignmentDO> allConsignments) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("BranchOutManifestDoxServiceImpl :: saveOrUpdateBranchOutManifestDox() :: START------------>:::::::");
		/** Define variables */
		
		Date currentDate = new Date();
		Boolean searchedManifest = Boolean.FALSE;
		ManifestDO manifestDO = null;

		/**
		 * Validate Manifest Number whether it is Open/Closed /New get the
		 * Complete manifest DO... from Database
		 */
		if (!StringUtil.isEmptyInteger(manifest.getManifestId())) {
			searchedManifest = Boolean.TRUE;
		}

		/** If manifest is not already searched i.e. the ID is not set */
		manifestDO = manifestCommonService.getManifestForCreation(manifest);

		if (!StringUtil.isNull(manifestDO)) {
			if (StringUtils.equalsIgnoreCase(manifestDO.getManifestStatus(),
					OutManifestConstants.CLOSE)) {
				throw new CGBusinessException(
						ManifestErrorCodesConstants.MANIFEST_ALREADY_CLOSED);
			} else if (StringUtils.equalsIgnoreCase(
					manifestDO.getManifestStatus(), OutManifestConstants.OPEN)
					&& !searchedManifest) {
				/**
				 * If the manifest status is Open throw a Business exception
				 * indicating the manifest is closed.
				 */
				throw new CGBusinessException(
						ManifestErrorCodesConstants.MANIFEST_ALREADY_EXISTS);
			}
		}else{
			manifest.setCreatedDate(currentDate);
			manifest.setUpdatedDate(currentDate);
		}

		// added by niharika bcoz it was savin status O if u close the manifest
		if (!StringUtil.isNull(manifestDO)) {
			if (manifest.getManifestStatus().equalsIgnoreCase(
					OutManifestConstants.CLOSE)
					&& manifestDO.getManifestStatus().equalsIgnoreCase(
							OutManifestConstants.OPEN)) {
				manifestDO.setManifestStatus(manifest.getManifestStatus());
			}
			if (!StringUtil.isNull(manifest.getManifestWeight())) {
				manifestDO.setManifestWeight(manifest.getManifestWeight());
			}
			if(!StringUtil.isNull(manifest.getGridItemPosition())){
			manifestDO.setGridItemPosition(manifest.getGridItemPosition().toString());
			}
			if(!StringUtil.isNull(manifest.getComails())){
				manifestDO.setComails(manifest.getComails());
			}
			if(!StringUtil.isNull(manifest.getNoOfElements())){
				manifestDO.setNoOfElements(manifest.getNoOfElements());
				}
			manifestDO.setUpdatedDate(currentDate);
			
		}

		if (StringUtil.isNull(manifestDO)) {
			manifestDO = manifest;
		}

		/**
		 * Call the method to update booking and create Consignments for PICKUP
		 * - BULK
		 */
		/*Set<ConsignmentDO> newConsignment = new LinkedHashSet<ConsignmentDO>();

		for (ConsignmentDO consignmentDO : allConsignments) {

			newConsignment.add(consignmentDO);
		}*/
		//manifestDO.setConsignments(newConsignment);
		manifestDO.setConsignments(allConsignments);
		// Set Grid Position
		
		//to prevent duplicate entry whil savin in manifestProcess--start
			/*	List<ManifestProcessDO> manifestProcessDOs = null;
				ManifestProcessDO manifestProcessDO = null;*/

				/*if(StringUtil.isEmptyInteger(branchOutManifestDoxTO.getManifestId())){
				 Manifest Creation 
					manifestProcessDOs =BranchOutManifestDoxConverter
							.prepareManifestProcessDOList(branchOutManifestDoxTO);
					manifestProcessDO = manifestProcessDOs.get(0);
					manifestProcessDO.setCreatedDate(currentDate);
				} else {
				 Manifest update 
					ManifestInputs manifestTO = prepareForManifestProcess(branchOutManifestDoxTO);
					manifestProcessDOs = manifestUniversalDAO
							.getManifestProcessDtls(manifestTO);
					if(!StringUtil.isNull(manifestProcessDOs.get(0))){
					manifestProcessDO = manifestProcessDOs.get(0);
					manifestProcessDO.setUpdatedDate(currentDate);
					}
				}*/
				
				//to set DT_TO_CENTRAL as Y while saving
				ManifestUtil.validateAndSetTwoWayWriteFlag(manifestDO);
				/*ManifestUtil.validateAndSetTwoWayWriteFlag(manifestProcessDO);*/

				//to prevent duplicate entry whil savin in manifestProcess--end

				
				
				
		/** SAVING MANIFST and MANIFEST PROCESS */
		boolean result = manifestCommonService.saveManifest(manifestDO);
		
		branchOutManifestDoxTO.setTwoWayManifestId(manifestDO.getManifestId());
		/*branchOutManifestDoxTO.setManifestProcessId(manifestProcessDO.getManifestProcessId());*/
		
		if (!result) {
			throw new CGBusinessException(
					ManifestErrorCodesConstants.MANIFEST_NOT_SAVED);
		}

		LOGGER.trace("BranchOutManifestDoxServiceImpl :: saveOrUpdateBranchOutManifestDox() :: END------------>:::::::");
		return manifestDO.getManifestStatus();
	}

	public List<BookingConsignmentDO> readAllConsignments(
			OutManifestDoxTO outmanifestDoxTO) throws CGBusinessException,
			CGSystemException {
		List<BookingConsignmentDO> bookingConsignmentDOs = new ArrayList<BookingConsignmentDO>();
		for (int i = 0; i < outmanifestDoxTO.getConsgNos().length; i++) {
			if (!StringUtil.isStringEmpty(outmanifestDoxTO.getConsgNos()[i])) {
				BookingConsignmentDO bookingConsignmentDO = new BookingConsignmentDO();
				String consgNo = outmanifestDoxTO.getConsgNos()[i];
				ConsignmentDO consignmentDO = manifestCommonService
						.getConsignment(consgNo);
				bookingConsignmentDO.setConsignmentDO(consignmentDO);
				if (StringUtil.isNull(consignmentDO)
						|| (consignmentDO.getUpdatedProcess().getProcessCode())
								.equals("UPPU")) {
					BookingDO bookingDO = manifestCommonService
							.getBookingConsignment(consgNo);
					bookingConsignmentDO.setBookingDO(bookingDO);
				}
				bookingConsignmentDOs.add(bookingConsignmentDO);
			}
		}
		return bookingConsignmentDOs;
	}

	private ManifestInputs prepareForManifestProcess(
			BranchOutManifestDoxTO branchOutManifestDoxTO) {
		ManifestInputs manifestInputs = new ManifestInputs();
		manifestInputs.setManifestNumber(branchOutManifestDoxTO.getManifestNo());
		manifestInputs.setLoginOfficeId(branchOutManifestDoxTO.getLoginOfficeId());
		manifestInputs.setManifestType(CommonConstants.MANIFEST_TYPE_OUT);
		manifestInputs.setManifestDirection(branchOutManifestDoxTO
				.getManifestDirection());
		return manifestInputs;
	}

	
	public List<BranchManifestPage> preparePrint(BranchOutManifestDoxTO branchOutManifestDoxTO) throws CGBusinessException{
		List<BranchOutManifestDoxDetailsTO> branchDetailsTOs = branchOutManifestDoxTO.getBranchOutManifestDoxDetailsTOList();
		//Collections.sort(deliveryDetailsTOs);
		Integer k=1;
		Integer srNo=1;
		Integer midSize=branchDetailsTOs.size()/2;	Integer size=branchDetailsTOs.size();
		if( size % 2 == 0 ){
			
		}else{
			midSize=midSize+1;
		}
		List<BranchManifestPage> pageList = new ArrayList<BranchManifestPage>();
		List<BranchManifestPageContent> leftCol = new ArrayList<BranchManifestPageContent>();
		List<BranchManifestPageContent> rightCol = new ArrayList<BranchManifestPageContent>();
		BranchManifestPage page = new BranchManifestPage();
		for(BranchOutManifestDoxDetailsTO branchDetailsTO : branchDetailsTOs){
			BranchManifestPageContent  pageContent =new BranchManifestPageContent();
			pageContent.setSrNo(srNo++);
			if(!StringUtil.isNull(branchDetailsTO.getConsgNo())){
				pageContent.setConsigment(branchDetailsTO.getConsgNo());
			}	
			
			if(!StringUtil.isEmptyDouble(branchDetailsTO.getLcAmount())){
				pageContent.setAmount(branchDetailsTO.getLcAmount());
			}	
			if(k<=midSize){
				leftCol.add(pageContent);
				page.setFirstCol(leftCol);
			}else if(k>midSize && k<=branchDetailsTOs.size()){
				rightCol.add(pageContent);
				page.setSecondCol(rightCol);
				if(k==branchDetailsTOs.size()){
					pageList.add(page);
					
				}
					
			}	
			k++;
	}
		
		if(k==2){
			pageList.add(page);	
		}
		return pageList;
 }		
}
