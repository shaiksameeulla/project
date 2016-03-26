package com.ff.web.manifest.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.LoadMovementVendorTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.ConsignmentManifestDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.manifest.OutManifestDetailBaseTO;
import com.ff.manifest.ThirdPartyOutManifestDoxDetailsTO;
import com.ff.manifest.ThirdPartyOutManifestDoxTO;
import com.ff.organization.OfficeTO;
import com.ff.web.manifest.Utils.ManifestUtil;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.service.OutManifestCommonService;

public class ThirdPartyOutManifestDoxConverter extends OutManifestBaseConverter {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(ThirdPartyOutManifestDoxConverter.class);

	private static OutManifestCommonService outManifestCommonService = null;

	// Constructing To's Based on Array's

	/**
	 * @return the outManifestCommonService
	 */
	public static OutManifestCommonService getOutManifestCommonService() {
		return outManifestCommonService;
	}

	/**
	 * @param outManifestCommonService
	 *            the outManifestCommonService to set
	 */
	public static void setOutManifestCommonService(
			OutManifestCommonService outManifestCommonService) {
		ThirdPartyOutManifestDoxConverter.outManifestCommonService = outManifestCommonService;
	}

	/**
	 * @param thirdPartyOutManifestDoxTO
	 *            will be passed with following details
	 *            <ul>
	 *            <li>consignmentTypeTO
	 *            <li>manifest type
	 *            <li>ManifestNo
	 *            <li>Weights
	 *            <li>Pincodes
	 *            <li>PincodeIds
	 *            <li>login Office Id
	 *            <li>login office name
	 *            <li>Current date
	 *            <li>Third Party Type
	 *            <li>Third Party Name
	 *            <li>Load No
	 *            <li>Consginment No
	 *            </ul>
	 * 
	 * @return thirdPartyOutManifestDoxDetailsTOs
	 * @throws CGBusinessException
	 *             - in case of any violation of Business rule
	 * @throws CGSystemException
	 *             - in case there is no connection found with server
	 */
	public static List<ThirdPartyOutManifestDoxDetailsTO> thirdPartyOutManifestDoxDtlsConverter(
			ThirdPartyOutManifestDoxTO thirdPartyOutManifestDoxTO)
			throws CGBusinessException, CGSystemException {

		ThirdPartyOutManifestDoxDetailsTO thirdPartyOutManifestDoxDetailsTO = null;
		List<ThirdPartyOutManifestDoxDetailsTO> thirdPartyOutManifestDoxDetailsTOs = null;
		if (!StringUtil.isNull(thirdPartyOutManifestDoxTO)) {
			thirdPartyOutManifestDoxDetailsTOs = new ArrayList<ThirdPartyOutManifestDoxDetailsTO>();
			if (thirdPartyOutManifestDoxTO.getConsgNos() != null
					&& thirdPartyOutManifestDoxTO.getConsgNos().length > 0) {
				for (int rowCount = 0; rowCount < thirdPartyOutManifestDoxTO
						.getConsgNos().length; rowCount++) {
					if (StringUtils.isNotEmpty(thirdPartyOutManifestDoxTO
							.getConsgNos()[rowCount])) {
						// Setting the common grid level attributes for Third
						thirdPartyOutManifestDoxDetailsTO = (ThirdPartyOutManifestDoxDetailsTO) setUpManifestDtlsTOs(
								thirdPartyOutManifestDoxTO, rowCount);
						thirdPartyOutManifestDoxDetailsTO
								.setOldWeight(thirdPartyOutManifestDoxTO
										.getOldWeights()[rowCount]);
						
						thirdPartyOutManifestDoxDetailsTO
								.setPosition(thirdPartyOutManifestDoxTO
										.getPosition()[rowCount]);
						thirdPartyOutManifestDoxDetailsTOs
								.add(thirdPartyOutManifestDoxDetailsTO);
					}
				}
			}
		}
		return thirdPartyOutManifestDoxDetailsTOs;
	}

	/**
	 * @param thirdPartyOutManifestDoxTO
	 *            will be passed with following details
	 * 
	 *            <ul>
	 *            <li>consignmentTypeTO
	 *            <li>manifest type
	 *            <li>ManifestNo
	 *            <li>Weights
	 *            <li>Pincodes
	 *            <li>PincodeIds
	 *            <li>login Office Id
	 *            <li>login office name
	 *            <li>Current date
	 *            <li>Third Party Type
	 *            <li>Third Party Name
	 *            <li>Load No
	 *            <li>Consginment No
	 *            </ul>
	 * 
	 * @return manifestDOs - all the details of manifest
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public static List<ManifestDO> prepareManifestDOList(
			ThirdPartyOutManifestDoxTO thirdPartyOutManifestDoxTO)
			throws CGBusinessException, CGSystemException {

		List<ManifestDO> manifestDOs = new ArrayList<>();
		ManifestDO manifestDO = null;

		/* Setting Logged In City Id as Destination City Id */
		thirdPartyOutManifestDoxTO
				.setDestinationCityId(thirdPartyOutManifestDoxTO
						.getLoginCityId());

		// Setting Common attributes
		manifestDO = OutManifestBaseConverter
				.outManifestTransferObjConverter(thirdPartyOutManifestDoxTO);

		// Setting the fields which are specific to TPDX screen

		if (!StringUtil.isEmptyInteger(thirdPartyOutManifestDoxTO
				.getLoginOfficeId())) {
			OfficeDO officeDO = new OfficeDO();
			officeDO.setOfficeId(thirdPartyOutManifestDoxTO.getLoginOfficeId());
			manifestDO.setDestOffice(officeDO);
		}

		/*
		 * CityDO cityDO = new CityDO();
		 * cityDO.setCityId(thirdPartyOutManifestDoxTO.getLoginCityId());
		 * manifestDO.setDestinationCity(cityDO);
		 */

		// To update consg. wt if required.
		boolean result = Boolean.FALSE;
		for (OutManifestDetailBaseTO to : thirdPartyOutManifestDoxTO
				.getThirdPartyOutManifestDoxDetailsToList()) {
			try {
				if (to.getWeight().doubleValue() != to.getOldWeight()
						.doubleValue()) {
					to.setDestOfficeId(manifestDO.getDestOffice().getOfficeId());
					to.setGridOriginOfficeId(manifestDO.getOriginOffice()
							.getOfficeId());
					result = outManifestCommonService.updateConsgWeight(to,
							thirdPartyOutManifestDoxTO.getProcessId());
					if (!result) {/* FIXME remove this later if not required */
						LOGGER.info("Consignment weight not updated for consg. Id :"
								+ to.getConsgId());
					}
				}
			} catch (Exception e) {
				LOGGER.error("Exception occurs in BranchOutManifestParcelConverter::prepareManifestDOList()::"
						+ e.getMessage());
			}
		}

		// Set ConsignmentManifestDO
		int noOfElements = CommonConstants.ZERO;
		Set<ConsignmentManifestDO> cnManifestSet = null;
		if (thirdPartyOutManifestDoxTO.getConsgIds() != null
				&& thirdPartyOutManifestDoxTO.getConsgIds().length > 0) {
			cnManifestSet = ManifestUtil
					.setConsignmentManifestDtlsForTPDX(thirdPartyOutManifestDoxTO);
			noOfElements += cnManifestSet.size();
		}

		// FIXME commented by HIMAL to remove compilation error
		// manifestDO.setManifestConsgDtls(cnManifestSet);

		manifestDO.setNoOfElements(noOfElements);

		manifestDOs.add(manifestDO);

		return manifestDOs;
	}

	/**
	 * @param thirdPartyOutManifestDoxTO
	 *            will be passed with following details
	 * 
	 *            <ul>
	 *            <li>login Office Id
	 *            <li>login office name
	 *            <li>Current date
	 *            <li>Third Party Type
	 *            <li>Third Party Name
	 *            <li>Load No
	 *            <li>Consginment No
	 *            </ul>
	 * 
	 * @return manifestProcessDOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	/*public static List<ManifestProcessDO> prepareManifestProcessDOList(
			ThirdPartyOutManifestDoxTO thirdPartyOutManifestDoxTO)
			throws CGBusinessException, CGSystemException {
		List<ManifestProcessDO> manifestProcessDOs = new ArrayList<>();
		ManifestProcessDO manifestProcessDO = null;
		// Setting Common attributes
		manifestProcessDO = OutManifestBaseConverter
				.outManifestBaseTransferObjConverter(thirdPartyOutManifestDoxTO);
		manifestProcessDO.setLoadLotId(thirdPartyOutManifestDoxTO.getLoadNo());
		String thirdPartyType = thirdPartyOutManifestDoxTO.getThirdPartyType();

		manifestProcessDO.setThirdPartyType(thirdPartyType);
		manifestProcessDO.setVendorId(StringUtil
				.parseInteger(thirdPartyOutManifestDoxTO.getThirdPartyName()));
		manifestProcessDO.setDestOfficeId(thirdPartyOutManifestDoxTO
				.getLoginOfficeId());
		manifestProcessDOs.add(manifestProcessDO);
		return manifestProcessDOs;
	}*/

	/**
	 * @desc converting manifestDo to thirdPartyOutManifestDoxTO
	 * @param manifestDO
	 *            will have have all the details from manifest table and
	 *            consignment manifested table
	 * @return thirdPartyOutManifestDoxTO
	 * @throws CGBusinessException
	 * @throws CGSystemException 
	 */
	public static ThirdPartyOutManifestDoxTO thirdPartyOutDomainConverter(
			ManifestDO manifestDO) throws CGBusinessException, CGSystemException {

		 int cosignCount=0;
		// Set the common attributes for the header
		ThirdPartyOutManifestDoxTO thirdPartyOutManifestDoxTO = (ThirdPartyOutManifestDoxTO) outManifestDomainConverter(
				manifestDO, ManifestUtil.getOutManifestThirdPartyDoxFactory());
		// Set the specific attributes for header
		thirdPartyOutManifestDoxTO.setManifestId(manifestDO.getManifestId());
		if (!StringUtil.isEmptyInteger(manifestDO.getLoadLotId())){
			thirdPartyOutManifestDoxTO.setLoadNo(manifestDO.getLoadLotId());
		}
		
		/* Set vendor id and third party name */
		if(!StringUtil.isNull(manifestDO.getVendorId())){
		List<LoadMovementVendorTO> partyListTO = outManifestCommonService
				.getDtlsForTPCC(manifestDO.getVendorId());
		
		LoadMovementVendorTO vendorTO = null;
		if(!StringUtil.isEmptyList(partyListTO)){
		vendorTO = partyListTO.get(0);
		}
		if (!StringUtil.isNull(vendorTO)) {
			thirdPartyOutManifestDoxTO.setThirdPartyName(vendorTO.getBusinessName());
			thirdPartyOutManifestDoxTO.setVendorId(vendorTO.getVendorId());
			thirdPartyOutManifestDoxTO.setVendorCode(vendorTO.getVendorCode());
		}
		}
		

		// prepare consignment details
				Set<ConsignmentDO> consignmentDOs = null;
		
		// set the attributes for detail TO
		List<ThirdPartyOutManifestDoxDetailsTO> thirdPartyOutManifestDoxDetailTOs = new ArrayList<>(
				manifestDO.getNoOfElements());
		if (!StringUtil.isEmptyColletion(manifestDO.getConsignments())) {
			consignmentDOs = manifestDO.getConsignments();
		for (ConsignmentDO consignment : consignmentDOs) {
			ThirdPartyOutManifestDoxDetailsTO thirdPartyOutManifestDoxDtlTO = (ThirdPartyOutManifestDoxDetailsTO) ManifestUtil
					.consignmentDomainConverter(
							consignment,
							ManifestUtil
									.prepareFactoryInputs(
											OutManifestConstants.THIRD_PARTY_MANIFEST,
				
											CommonConstants.CONSIGNMENT_TYPE_DOCUMENT));
		//setting the specific details for detailTO from cnPrice(one way)
			if(!StringUtil.isNull(consignment.getDestPincodeId())){
				thirdPartyOutManifestDoxDtlTO.setPincodeId(consignment.getDestPincodeId().getPincodeId());
			}
			
			if(!StringUtil.isEmptyDouble(consignment.getCodAmt())){
				thirdPartyOutManifestDoxDtlTO.setCodAmts(consignment.getCodAmt());
			}
			if(!StringUtil.isEmptyDouble(consignment.getLcAmount())){
				thirdPartyOutManifestDoxDtlTO.setLcAmts(consignment.getLcAmount());
			}
			if(!StringUtil.isEmptyDouble(consignment.getTopayAmt())){
				thirdPartyOutManifestDoxDtlTO.setToPayAmts(consignment.getTopayAmt());
			}
			if(!StringUtil.isEmptyDouble(consignment.getBaAmt())){
				thirdPartyOutManifestDoxDtlTO.setBaAmounts(consignment.getBaAmt());
			}
			
			
			
			
			
			// Total weight for printer
			if (!StringUtil.isEmptyDouble(thirdPartyOutManifestDoxDtlTO.getWeight())) {
				 thirdPartyOutManifestDoxDtlTO.getWeight();
			}
			if (!StringUtil.isStringEmpty(manifestDO.getOriginOffice().getOfficeName())) {
				thirdPartyOutManifestDoxDtlTO.setOrgOfficeName(manifestDO.getOriginOffice().getOfficeName());
			}
			
			if(!StringUtil.isEmptyInteger(consignment.getOrgOffId())){
				OfficeTO officeTO =  outManifestCommonService.getOfficeDetails(consignment.getOrgOffId());
				if(!StringUtil.isStringEmpty(officeTO.getOfficeName())){
					thirdPartyOutManifestDoxDtlTO.setConsgOrgOffice(officeTO.getOfficeName());
				}
			}
			cosignCount++;
			thirdPartyOutManifestDoxDetailTOs.add(thirdPartyOutManifestDoxDtlTO);
		}/* END of FOR EACH */
		}//end of if
		
		
		
		
			
				//thirdPartyOutManifestDoxTO.setPrintConsigCount(cosignCount);
				Collections.sort(thirdPartyOutManifestDoxDetailTOs);
				thirdPartyOutManifestDoxTO.setThirdPartyOutManifestDoxDetailsToList(thirdPartyOutManifestDoxDetailTOs);
				thirdPartyOutManifestDoxTO.setPrintRowCount(cosignCount);
				//branchOutManifestDoxTO.setTotalConsg(count);
				//branchOutManifestDoxTO.setTotalLcAmount(lcTotal);
				//branchOutManifestDoxTO.setConsigTotalWt(Double.parseDouble(new DecimalFormat("##.###").format(consgToatalWt)));
				LOGGER.trace("OutManifestDoxConverter :: outManifestDoxDomainConverter() :: END------------>:::::::");
				return thirdPartyOutManifestDoxTO;
		
		
	}


	/**
	 * Third party grid details for in manif consg.
	 * 
	 * @param consTO
	 *            the cons to
	 * @return the third party out manifest dox details to
	 */
	public static ThirdPartyOutManifestDoxDetailsTO thirdPartyGridDetailsForInManifConsg(
			ConsignmentTO consTO) {
		ThirdPartyOutManifestDoxDetailsTO thirdPartyOutManifestDoxDetailsTO = new ThirdPartyOutManifestDoxDetailsTO();

		if (!StringUtil.isNull(consTO)) {
			thirdPartyOutManifestDoxDetailsTO.setConsgNo(consTO.getConsgNo());
			thirdPartyOutManifestDoxDetailsTO.setConsgId(consTO.getConsgId());
		}

		if (!StringUtil.isNull(consTO.getDestPincode())) {
			// branchOutParcelManifestDetailsTO.setPincode(consTO.getDestPincode().getPincode());
			thirdPartyOutManifestDoxDetailsTO.setPincodeId(consTO
					.getDestPincode().getPincodeId());
		}

		if (!StringUtil.isNull(consTO.getDestCity())) {
			thirdPartyOutManifestDoxDetailsTO.setDestCityId(consTO
					.getDestCity().getCityId());
			thirdPartyOutManifestDoxDetailsTO.setDestCity(consTO.getDestCity()
					.getCityName());
		}

		if (!StringUtil.isNull(consTO.getFinalWeight())) {
			thirdPartyOutManifestDoxDetailsTO
					.setWeight(consTO.getFinalWeight());
			thirdPartyOutManifestDoxDetailsTO.setBkgWeight(consTO
					.getFinalWeight());
		}

		return thirdPartyOutManifestDoxDetailsTO;

	}
	
	public static ManifestDO prepareManifestDO(
			ThirdPartyOutManifestDoxTO thirdPartyOutmanifestDoxTO, ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("ThirdPartyOutManifestDoxConverter :: prepareManifestDO() :: START------------>:::::::");

		/* Setting HEADER attributes */
		manifestDO = outManifestTransferObjConverter(thirdPartyOutmanifestDoxTO,
				manifestDO);
		if(!StringUtil.isEmptyInteger(thirdPartyOutmanifestDoxTO.getLoadNo())){
			manifestDO.setLoadLotId(thirdPartyOutmanifestDoxTO.getLoadNo());
		}
		
		String manifestStatus = CommonConstants.EMPTY_STRING;

		int noOfConsg = 0;
		if (!StringUtil.isEmptyColletion(manifestDO.getConsignments())
				&& !StringUtil.isEmptyInteger(manifestDO.getConsignments()
						.size())) {
			noOfConsg = manifestDO.getConsignments().size();
		}
		
		

		int noOfElements = noOfConsg;

		

		Double manifestWeight = 0.0;
		for (ThirdPartyOutManifestDoxDetailsTO thirdPartyOutDoxDetailsTO : thirdPartyOutmanifestDoxTO
				.getThirdPartyOutManifestDoxDetailsToList()) {
			if (!StringUtil.isEmptyDouble(thirdPartyOutDoxDetailsTO.getWeight()))
				manifestWeight = manifestWeight + thirdPartyOutDoxDetailsTO.getWeight();
		}
		thirdPartyOutmanifestDoxTO.setFinalWeight(manifestWeight);
		manifestDO.setManifestWeight(manifestWeight);

		manifestStatus = thirdPartyOutmanifestDoxTO.getManifestStatus();
		if (StringUtils.isNotEmpty(thirdPartyOutmanifestDoxTO.getMaxCNsAllowed())
				&& StringUtils.isNotEmpty(thirdPartyOutmanifestDoxTO
						.getMaxComailsAllowed())) {
			int maxCNsAllowed = Integer.parseInt(thirdPartyOutmanifestDoxTO
					.getMaxCNsAllowed());
		
			// if Comail is checked all the rows in the grid are considered as
			// co-mails only and max grid size is max CNs and max Co-mails.
			if (noOfConsg == maxCNsAllowed) {
				manifestStatus = OutManifestConstants.CLOSE;
			}
		}

		manifestDO.setManifestStatus(manifestStatus);
		// manifestDO.setComails(comailSet);
		manifestDO.setNoOfElements(noOfElements);
		thirdPartyOutmanifestDoxTO.setNoOfElements(noOfElements);
		thirdPartyOutmanifestDoxTO.setManifestStatus(manifestStatus);
		LOGGER.trace("ThirdPartyOutManifestDoxConverter :: prepareManifestDO() :: END------------>:::::::");
		return manifestDO;
	}

}
