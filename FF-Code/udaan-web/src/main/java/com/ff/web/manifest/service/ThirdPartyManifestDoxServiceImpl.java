package com.ff.web.manifest.service;

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
import com.ff.business.LoadMovementVendorTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.GridItemOrderDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.geography.CityTO;
import com.ff.manifest.ManifestFactoryTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.ManifestProcessTO;
import com.ff.manifest.OutManifestValidate;
import com.ff.manifest.ThirdPartyOutManifestDoxDetailsTO;
import com.ff.manifest.ThirdPartyOutManifestDoxTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.manifest.dao.ManifestUniversalDAO;
import com.ff.universe.manifest.service.OutManifestUniversalService;
import com.ff.web.manifest.Utils.ManifestUtil;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.converter.OutManifestBaseConverter;
import com.ff.web.manifest.converter.ThirdPartyOutManifestDoxConverter;

/**
 * @author cbhure
 * 
 */
public class ThirdPartyManifestDoxServiceImpl implements
		ThirdPartyManifestDoxService {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(ThirdPartyManifestDoxServiceImpl.class);

	private OutManifestUniversalService outManifestUniversalService;
	private OutManifestCommonService outManifestCommonService;
	private ManifestUniversalDAO manifestUniversalDAO;
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
	 * @param outManifestUniversalService
	 */
	public void setOutManifestUniversalService(
			OutManifestUniversalService outManifestUniversalService) {
		this.outManifestUniversalService = outManifestUniversalService;
	}

	/**
	 * @param outManifestCommonService
	 */
	public void setOutManifestCommonService(
			OutManifestCommonService outManifestCommonService) {
		this.outManifestCommonService = outManifestCommonService;
	}

	/**
	 * @param manifestUniversalDAO
	 */
	public void setManifestUniversalDAO(
			ManifestUniversalDAO manifestUniversalDAO) {
		this.manifestUniversalDAO = manifestUniversalDAO;
	}

	@Override
	public String saveOrUpdateOutManifestTPDX(
			ThirdPartyOutManifestDoxTO thirdPartyOutManifestDoxTO)
			throws CGSystemException {
		String trasnStatus = CommonConstants.EMPTY_STRING;
		// boolean isSaved = Boolean.FALSE;
		try {
			if (!StringUtil.isNull(thirdPartyOutManifestDoxTO)) {

				List<ConsignmentTypeTO> consignmanetTypeTOs = outManifestUniversalService
						.getConsignmentTypes(thirdPartyOutManifestDoxTO
								.getConsignmentTypeTO());

				// Setting Consignment type id
				if (!StringUtil.isEmptyList(consignmanetTypeTOs)) {
					ConsignmentTypeTO consignmentTypeTO = consignmanetTypeTOs
							.get(0);
					thirdPartyOutManifestDoxTO
							.setConsignmentTypeTO(consignmentTypeTO);
				}

				// Setting process id
				ProcessTO processTO = new ProcessTO();
				processTO
						.setProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_DOX);
				processTO = outManifestUniversalService.getProcess(processTO);
				thirdPartyOutManifestDoxTO.setProcessId(processTO
						.getProcessId());
				thirdPartyOutManifestDoxTO.setProcessCode(processTO
						.getProcessCode());

				OfficeTO officeTO = new OfficeTO();
				officeTO.setOfficeId(thirdPartyOutManifestDoxTO
						.getLoginOfficeId());
				if (!StringUtil.isStringEmpty(thirdPartyOutManifestDoxTO
						.getOfficeCode())) {
					officeTO.setOfficeCode(thirdPartyOutManifestDoxTO
							.getOfficeCode());
				}

				String processNumber = outManifestCommonService
						.createProcessNumber(processTO, officeTO);
				thirdPartyOutManifestDoxTO.setProcessNo(processNumber);

				// to calc operating level
				Integer operatingLevel = 0;
				operatingLevel = outManifestUniversalService
						.calcOperatingLevel(thirdPartyOutManifestDoxTO,
								officeTO);
				thirdPartyOutManifestDoxTO.setOperatingLevel(operatingLevel);

				// Prepare ManifestDO
				List<ManifestDO> manifestDOs = ThirdPartyOutManifestDoxConverter
						.prepareManifestDOList(thirdPartyOutManifestDoxTO);

				// Prepare ManifestProcessDO
				/*List<ManifestProcessDO> manifestProcessDOs = ThirdPartyOutManifestDoxConverter
						.prepareManifestProcessDOList(thirdPartyOutManifestDoxTO);*/

				// Saving Manifest
				/*
				 * if (!StringUtil.isEmptyList(manifestDOs)) { manifestDOs =
				 * manifestUniversalDAO .saveOrUpdateManifest(manifestDOs); }
				 */

				// Saving manifest process
				/*
				 * if (!StringUtil.isEmptyList(manifestDOs)) {
				 * manifestProcessDOs = manifestUniversalDAO
				 * .saveOrUpdateManifestProcess(manifestProcessDOs); }
				 */
				if (!StringUtil.isEmptyList(manifestDOs)) {
					thirdPartyOutManifestDoxTO.setManifestId(manifestDOs.get(0)
							.getManifestId());
				}

			/*	if (!StringUtil.isEmptyList(manifestProcessDOs)) {
					ManifestProcessTO manifestProcessTO = new ManifestProcessTO();
					manifestProcessTO.setManifestProcessId(manifestProcessDOs
							.get(0).getManifestProcessId());
					thirdPartyOutManifestDoxTO
							.setManifestProcessTo(manifestProcessTO);
				}
*/
				trasnStatus = (manifestDOs.get(0).getManifestId() != null) ? manifestDOs
						.get(0).getManifestId().toString()
						: CommonConstants.SUCCESS;
			}
		} catch (Exception e) {
			trasnStatus = CommonConstants.FAILURE;
			LOGGER.error("ThirdPartyManifestDoxServiceImpl :: saveOrUpdateOutManifestTPDX()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return trasnStatus;
	}

	@Override
	public ThirdPartyOutManifestDoxDetailsTO getConsignmentDtls(
			OutManifestValidate cnValidateTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("ThirdPartyManifestDoxServiceImpl::getConsignmentDtls()::START");
		ThirdPartyOutManifestDoxDetailsTO thirdPartyOutManifestDoxDetailTO = null;

		ConsignmentModificationTO consModifcatnTO = cnValidateTO
				.getConsignmentModificationTO();
		if (!StringUtil.isNull(consModifcatnTO)) {
			thirdPartyOutManifestDoxDetailTO = convertBookingDtlsTOListToThirdPartDoxTO(consModifcatnTO);
		}

		LOGGER.debug("ThirdPartyManifestDoxServiceImpl::getConsignmentDtls()::END");
		return thirdPartyOutManifestDoxDetailTO;
	}

	/**
	 * @desc convert bookingdtlsToList to thirdpartyoutmanifestdoxto
	 * @param consignmentModificationTOs
	 *            will be passed with all the booking details in it
	 * @param manifestFactoryTO
	 *            will be passed with following details in it.
	 * 
	 *            <ul>
	 *            <li>login Office Id
	 *            <li>consignment no
	 *            <li>manifest type
	 *            <li>consignment type
	 *            </ul>
	 * 
	 * @return thirdPartyOutManifestDoxTo
	 * @throws CGBusinessException
	 *             - Any violation of common Business Rules while converting
	 *             consignmentModificationTOs to thirdPartyOutManifestDoxTo
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	private ThirdPartyOutManifestDoxDetailsTO convertBookingDtlsTOListToThirdPartDoxTO(
			ConsignmentModificationTO consignmentModificationTO)
			throws CGBusinessException, CGSystemException {

		ThirdPartyOutManifestDoxDetailsTO thirdPartyOutManifestDoxDetailsTo = null;

		if (!StringUtil.isNull(consignmentModificationTO)) {

			thirdPartyOutManifestDoxDetailsTo = (ThirdPartyOutManifestDoxDetailsTO) OutManifestBaseConverter
					.cnDtlsToOutMnfstDtlBaseConverter(
							consignmentModificationTO,
							ManifestUtil.getOutManifestThirdPartyDoxFactory());

			CityTO destCity = new CityTO();
			destCity.setCityId(consignmentModificationTO.getConsigmentTO()
					.getDestPincode().getCityId());

			CityTO cityTO = outManifestCommonService.getCity(destCity);

			if (!StringUtil.isNull(cityTO)) {
				thirdPartyOutManifestDoxDetailsTo.setDestCityId(cityTO
						.getCityId());
				thirdPartyOutManifestDoxDetailsTo.setDestCity(cityTO
						.getCityName());
			}
			
			if(!StringUtil.isEmptyDouble(consignmentModificationTO.getConsigmentTO().getBaAmt())){
				thirdPartyOutManifestDoxDetailsTo.setBaAmounts(consignmentModificationTO.getConsigmentTO().getBaAmt());
			}

		}

		return thirdPartyOutManifestDoxDetailsTo;
	}

	@Override
	public ThirdPartyOutManifestDoxTO searchManifestDtls(
			ManifestInputs manifestTO) throws CGBusinessException,
			CGSystemException {

		ManifestDO manifestDO = null;
		ThirdPartyOutManifestDoxTO thirdPartyOutManifestDoxTO = null;
		/* manifestDO = manifestUniversalDAO.searchManifestDtls(manifestTO); */

		if (!StringUtil.isNull(manifestDO)) {
			thirdPartyOutManifestDoxTO = ThirdPartyOutManifestDoxConverter
					.thirdPartyOutDomainConverter(manifestDO);
		}
		// Manifest Process Do to Third Party Out Manifest Dox To
		if (!StringUtils.equalsIgnoreCase(CommonConstants.PROCESS_DISPATCH,
				manifestTO.getManifestProcessCode())) {
			/*List<ManifestProcessDO> manifestProcessDOs = manifestUniversalDAO
					.getManifestProcessDtls(manifestTO);
			List<ManifestProcessTO> manifestProcessTos = null;
			if (!StringUtil.isEmptyList(manifestProcessDOs)) {
				// FIXME commented by HIMAL to remove compilation error
				
				 * manifestProcessTos = OutManifestBaseConverter
				 * .manifestProcessTransferObjConverter(manifestProcessDOs);
				 
			}
			ManifestProcessTO manifestProcessTo = manifestProcessTos.get(0);*/

			// To set third party type and name in thirdPartyOutManifestDoxTO
/*
			if (!StringUtil.isEmptyInteger(manifestProcessTo.getBaId())) {
				int baID = manifestProcessTo.getBaId();
				List<CustomerTO> baTO = outManifestCommonService
						.getDtlsForTPBA(baID);
				CustomerTO baTo = baTO.get(0);
				if (!StringUtil.isNull(baTo)) {
					manifestProcessTo.setBusinessName(baTo.getBusinessName());
					thirdPartyOutManifestDoxTO.setThirdPartyName(baTo
							.getBusinessName());
					thirdPartyOutManifestDoxTO
							.setVendorId(baTo.getCustomerId());
				}
			} else if (!StringUtil.isEmptyInteger(manifestProcessTo
					.getFranchiseeId())) {
				int frID = manifestProcessTo.getFranchiseeId();
				List<CustomerTO> frTO = outManifestCommonService
						.getDtlsForTPFR(frID);
				CustomerTO frTo = frTO.get(0);
				if (!StringUtil.isNull(frTo)) {
					manifestProcessTo.setBusinessName(frTo.getBusinessName());
					thirdPartyOutManifestDoxTO.setThirdPartyName(frTo
							.getBusinessName());
					thirdPartyOutManifestDoxTO
							.setVendorId(frTo.getCustomerId());

				}
			} else */
			ManifestProcessTO manifestProcessTo =  null;
			if (!StringUtil.isEmptyInteger(manifestDO.getVendorId())) {
				int ccID = manifestDO.getVendorId();
				List<LoadMovementVendorTO> ccTO = outManifestCommonService
						.getDtlsForTPCC(ccID);
				LoadMovementVendorTO ccTo = ccTO.get(0);
				if (!StringUtil.isNull(ccTo)) {
					manifestProcessTo = new ManifestProcessTO();
					manifestProcessTo.setBusinessName(ccTo.getBusinessName());
					thirdPartyOutManifestDoxTO.setThirdPartyName(ccTo
							.getBusinessName());
					thirdPartyOutManifestDoxTO.setVendorId(ccTo.getVendorId());
				}
			}
			thirdPartyOutManifestDoxTO.setManifestProcessTo(manifestProcessTo);
		}

		return thirdPartyOutManifestDoxTO;
	}

	public ThirdPartyOutManifestDoxDetailsTO getInManifestdConsignmentDtls(
			ManifestFactoryTO manifestFactoryTO) throws CGBusinessException,
			CGSystemException {

		ThirdPartyOutManifestDoxDetailsTO thirdPartyOutManifstDoxDetailsTO = null;

		/*List<ConsignmentTO> consignmtTOs = outManifestUniversalService
				.getConsgDtls(manifestFactoryTO);*/
		ConsignmentTO consignmntTO=outManifestUniversalService.getConsingmentDtls(manifestFactoryTO.getConsgNumber());
		if (!StringUtil.isNull(consignmntTO)) {
			thirdPartyOutManifstDoxDetailsTO = convertConsgDtlsTOListToThirdPartyOutManifstDoxDetailsTO(
					consignmntTO, manifestFactoryTO);
		} else {
			throw new CGBusinessException(
					ManifestErrorCodesConstants.ALLOW_PARCEL_TYPE_CONSG);
		}

		return thirdPartyOutManifstDoxDetailsTO;
	}

	private ThirdPartyOutManifestDoxDetailsTO convertConsgDtlsTOListToThirdPartyOutManifstDoxDetailsTO(
			ConsignmentTO consignmentTO,
			ManifestFactoryTO manifestFactoryTO) throws CGBusinessException,
			CGSystemException {
		ThirdPartyOutManifestDoxDetailsTO thirdPartyOutManifestDoxDetailsTO = null;

		if (!StringUtil.isNull(consignmentTO)) {
			thirdPartyOutManifestDoxDetailsTO = (ThirdPartyOutManifestDoxDetailsTO) ThirdPartyOutManifestDoxConverter
					.thirdPartyGridDetailsForInManifConsg(consignmentTO);
		}
		return thirdPartyOutManifestDoxDetailsTO;
	}

	public String saveOrUpdateThirdPartyOutManifestDox(ManifestDO manifest,
			ThirdPartyOutManifestDoxTO thirdPartyOutManifestDoxTO, List<BookingDO> allBooking,
			List<ConsignmentDO> pickupConsignment,
			Set<ConsignmentDO> allConsignments) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("ThirdPartyOutManifestDoxServiceImpl :: saveOrUpdateThirdPartyOutManifestDox() :: START------------>:::::::");
		/** Define variables */
		Boolean searchedManifest = Boolean.FALSE;
		ManifestDO manifestDO = null;
		Date currentDate = new Date();
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
			
			if(!StringUtil.isNull(manifest.getNoOfElements())){
				manifestDO.setNoOfElements(manifest.getNoOfElements());
			}
			manifestDO.setUpdatedDate(currentDate);
		}

		if (StringUtil.isNull(manifestDO)) {
			manifestDO = manifest;
		}

		
		manifestDO.setConsignments(allConsignments);
		// Set Grid Position
		
		//to prevent duplicate entry whil savin in manifestProcess--start
		/*List<ManifestProcessDO> manifestProcessDOs = null;
		ManifestProcessDO manifestProcessDO = null;

		if(StringUtil.isEmptyInteger(thirdPartyOutManifestDoxTO.getManifestId())){
		 Manifest Creation 
			manifestProcessDOs = ThirdPartyOutManifestDoxConverter
					.prepareManifestProcessDOList(thirdPartyOutManifestDoxTO);
			manifestProcessDO = manifestProcessDOs.get(0);
			manifestProcessDO.setCreatedDate(currentDate);
		} else {
		 Manifest update 
			ManifestInputs manifestTO = prepareForManifestProcess(thirdPartyOutManifestDoxTO);
			manifestProcessDOs = manifestUniversalDAO
					.getManifestProcessDtls(manifestTO);
			if(!StringUtil.isNull(manifestProcessDOs.get(0))){
			manifestProcessDO = manifestProcessDOs.get(0);
			manifestProcessDO.setUpdatedDate(currentDate);
			}
		}*/

		//to set DT_TO_CENTRAL Y while saving
		ManifestUtil.validateAndSetTwoWayWriteFlag(manifestDO);
		/*ManifestUtil.validateAndSetTwoWayWriteFlag(manifestProcessDO);*/
		//to prevent duplicate entry whil savin in manifestProcess--end
		
		/** SAVING MANIFST and MANIFEST PROCESS */
		boolean result = manifestCommonService.saveManifest(manifestDO);
		
		//for two waY write
		thirdPartyOutManifestDoxTO.setTwoWayManifestId(manifestDO.getManifestId());
		/*thirdPartyOutManifestDoxTO.setManifestProcessId(manifestProcessDO.getManifestProcessId());*/
		if (!result) {
			throw new CGBusinessException(
					ManifestErrorCodesConstants.MANIFEST_NOT_SAVED);
		}

		LOGGER.trace("ThirdPartyOutManifestDoxServiceImpl :: saveOrUpdateThirdPartyOutManifestDox() :: END------------>:::::::");
		return manifestDO.getManifestStatus();
	}

	@Override
	public ThirdPartyOutManifestDoxTO searchThirdPartyManifestDtls(
			ManifestInputs manifestTO) throws CGBusinessException,
			CGSystemException {

		LOGGER.trace("ThirdPartyOutManifestDoxServiceImpl :: searchThirdPartyManifestDtls() :: START------------>:::::::");
		ManifestDO manifestDO = null;
		ThirdPartyOutManifestDoxTO thirdPartyOutManifestDoxTO = null;
		Long startTime = System.currentTimeMillis();
		// manifestDO = manifestUniversalDAO.searchManifestDtls(manifestTO);
		manifestDO = OutManifestBaseConverter.prepateManifestDO(manifestTO);
		manifestDO = manifestCommonService.getDoxManifest(manifestDO);

		if (!StringUtil.isNull(manifestDO)) {
			GridItemOrderDO gridItemOrderDO = new GridItemOrderDO();
			gridItemOrderDO.setGridPosition(manifestDO.getGridItemPosition());
			gridItemOrderDO.setConsignmentDOs(manifestDO.getConsignments());

			gridItemOrderDO = manifestCommonService.arrangeOrder(
					gridItemOrderDO, ManifestConstants.ACTION_SEARCH);
			manifestDO.setConsignments(gridItemOrderDO.getConsignmentDOs());

			thirdPartyOutManifestDoxTO = ThirdPartyOutManifestDoxConverter
					.thirdPartyOutDomainConverter(manifestDO);

		} else {
			throw new CGBusinessException(
					ManifestErrorCodesConstants.MANIFEST_DETAILS_NOT_FOUND);
		}
		Long endTime = System.currentTimeMillis();
		LOGGER.trace("ThirdPartyOutManifestDoxServiceImpl :: searchManifestDtls() :: END------------>:::::::::::::Time Diff:["
				+ (endTime - startTime) + "]");
		return thirdPartyOutManifestDoxTO;
	}
	
	private ManifestInputs prepareForManifestProcess(
			ThirdPartyOutManifestDoxTO thirdPartyOutManifestDoxTO) {
		ManifestInputs manifestInputs = new ManifestInputs();
		manifestInputs.setManifestNumber(thirdPartyOutManifestDoxTO.getManifestNo());
		manifestInputs.setLoginOfficeId(thirdPartyOutManifestDoxTO.getLoginOfficeId());
		manifestInputs.setManifestType(CommonConstants.MANIFEST_TYPE_OUT);
		manifestInputs.setManifestDirection(thirdPartyOutManifestDoxTO
				.getManifestDirection());
		return manifestInputs;
	}

}
