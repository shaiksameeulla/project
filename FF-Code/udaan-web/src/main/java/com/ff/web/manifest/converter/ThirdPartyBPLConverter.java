package com.ff.web.manifest.converter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.ConsignmentModificationTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.manifest.ThirdPartyBPLDetailsTO;
import com.ff.manifest.ThirdPartyBPLOutManifestTO;
import com.ff.organization.OfficeTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.web.manifest.Utils.ManifestUtil;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.service.OutManifestCommonService;

public class ThirdPartyBPLConverter extends OutManifestBaseConverter {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ThirdPartyBPLConverter.class);

	/** The outManifestCommonService. */
	private static OutManifestCommonService outManifestCommonService = null;

	/**
	 * @param outManifestCommonService
	 *            the outManifestCommonService to set
	 */
	public static void setOutManifestCommonService(
			OutManifestCommonService outManifestCommonService) {
		ThirdPartyBPLConverter.outManifestCommonService = outManifestCommonService;
	}

	/**
	 * To convert ConsignmentModificationTO to ThirdPartyBPLDetailsTO
	 * 
	 * @param cnModificationTO
	 * @return thirdPartyBPLDetailsTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public static ThirdPartyBPLDetailsTO thirdPartyOutManifestBPLDtlsConverter(
			ConsignmentModificationTO cnModificationTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("ThirdPartyBPLConverter :: thirdPartyOutManifestBPLDtlsConverter() :: START");
		ThirdPartyBPLDetailsTO thirdPartyBPLDetailsTO = new ThirdPartyBPLDetailsTO();
		ConsignmentTO consignmentTO = cnModificationTO.getConsigmentTO();
		if (!StringUtil.isNull(consignmentTO)) {

			// To check whether consignment is delivered or not.
			if (!StringUtil.isNull(consignmentTO.getConsgStatus())
					&& consignmentTO
							.getConsgStatus()
							.equalsIgnoreCase(
									UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED)) {
				throw new CGBusinessException(
						ManifestErrorCodesConstants.CN_ALREADY_DELIVERED);
			}

			// Set Consignment Number
			thirdPartyBPLDetailsTO.setConsgNo(consignmentTO.getConsgNo());

			// Set Weight
			thirdPartyBPLDetailsTO.setWeight(consignmentTO.getFinalWeight());

			// Set No. Of Pcs.
			thirdPartyBPLDetailsTO.setNoOfPcs(consignmentTO.getNoOfPcs());
			// Set Child CN
			if (StringUtils.isNotEmpty(consignmentTO.getChildCNsDtls())) {
				thirdPartyBPLDetailsTO.setChildCn(consignmentTO
						.getChildCNsDtls());
			}
			// Set Booking Type
			thirdPartyBPLDetailsTO.setBookingType(cnModificationTO
					.getBookingType());

			// Set Emebedded Type for Consignment
			thirdPartyBPLDetailsTO
					.setEmbeddedType(OutManifestConstants.SCANNED_TYPE_C);

			// Set Content
			if (!StringUtil.isNull(consignmentTO.getCnContents())) {
				thirdPartyBPLDetailsTO.setCnContent(consignmentTO
						.getCnContents().getCnContentName());
			}

			// Set Paper Work
			if (!StringUtil.isNull(consignmentTO.getCnPaperWorks())) {
				thirdPartyBPLDetailsTO.setPaperWork(consignmentTO
						.getCnPaperWorks().getCnPaperWorkName());
			}

			// Set Declared Values
			if (!StringUtil.isEmptyDouble(consignmentTO.getDeclaredValue())) {
				thirdPartyBPLDetailsTO.setDeclaredValues(consignmentTO
						.getDeclaredValue());
			}

			// Set COD Amounts
			if (!StringUtil.isEmptyDouble(consignmentTO.getCodAmt())) {
				thirdPartyBPLDetailsTO.setCodAmts(consignmentTO.getCodAmt());
			}

			// Set BA Amounts
			if (!StringUtil.isEmptyDouble(consignmentTO.getBaAmt())) {
				thirdPartyBPLDetailsTO
						.setBaAmounts(consignmentTO.getBaAmt());
			}

			// Set Topay Amounts
			if (!StringUtil.isEmptyDouble(consignmentTO.getTopayAmt())) {
				thirdPartyBPLDetailsTO
						.setToPayAmts(consignmentTO.getTopayAmt());
			}
		} else {
			throw new CGBusinessException(
					ManifestErrorCodesConstants.CONSGNMENT_DTLS_NOT_EXIST);
		}
		LOGGER.trace("ThirdPartyBPLConverter :: thirdPartyOutManifestBPLDtlsConverter() :: END");
		return thirdPartyBPLDetailsTO;
	}

	/**
	 * To convert thirdPartyBPLOutManifestTO to ThirdPartyBPLDetailsTO list
	 * 
	 * @param thirdPartyBPLOutManifestTO
	 * @return thirdPartyBPLDetailsTOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public static List<ThirdPartyBPLDetailsTO> thirdPartyBPLDtlsListConverter(
			ThirdPartyBPLOutManifestTO thirdPartyBPLOutManifestTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("ThirdPartyBPLConverter :: thirdPartyBPLDtlsListConverter() :: START");
		ThirdPartyBPLDetailsTO thirdPartyBPLDetailsTO = null;
		List<ThirdPartyBPLDetailsTO> thirdPartyBPLDetailsTOs = null;
		ThirdPartyBPLDetailsTO thirdPartyBPLManifestDetailsTO = null;
		if (!StringUtil.isNull(thirdPartyBPLOutManifestTO)) {
			thirdPartyBPLDetailsTOs = new ArrayList<ThirdPartyBPLDetailsTO>();
			if (thirdPartyBPLOutManifestTO.getConsgNos() != null
					&& thirdPartyBPLOutManifestTO.getConsgNos().length > 0) {
				for (int rowCount = 0; rowCount < thirdPartyBPLOutManifestTO
						.getConsgNos().length; rowCount++) {
					if (StringUtils.isNotEmpty(thirdPartyBPLOutManifestTO
							.getConsgNos()[rowCount])) {
						// Setting the common grid level attributes for Third
						// Party
						thirdPartyBPLDetailsTO = (ThirdPartyBPLDetailsTO) setUpManifestDtlsTOs(
								thirdPartyBPLOutManifestTO, rowCount);
						thirdPartyBPLDetailsTO
								.setConsgId(thirdPartyBPLOutManifestTO
										.getConsgIds()[rowCount]);
						thirdPartyBPLDetailsTO
								.setNoOfPcs(thirdPartyBPLOutManifestTO
										.getNoOfPcs()[rowCount]);
						thirdPartyBPLDetailsTO
								.setOldWeight(thirdPartyBPLOutManifestTO
										.getOldWeights()[rowCount]);
						thirdPartyBPLDetailsTO
								.setCnContent(thirdPartyBPLOutManifestTO
										.getCnContent()[rowCount]);
						thirdPartyBPLDetailsTO
								.setCnContentId(thirdPartyBPLOutManifestTO
										.getCnContentIds()[rowCount]);
						thirdPartyBPLDetailsTO
								.setCnContent(thirdPartyBPLOutManifestTO
										.getCnContent()[rowCount]);

						thirdPartyBPLDetailsTO
								.setDeclaredValues(thirdPartyBPLOutManifestTO
										.getDeclaredValues()[rowCount]);
						// thirdPartyBPLDetailsTO.setPaperWork(thirdPartyBPLOutManifestTO.getPaperWorks()[rowCount]);
						thirdPartyBPLDetailsTO
								.setToPayAmts(thirdPartyBPLOutManifestTO
										.getToPayAmts()[rowCount]);// pay amt
																	// FIX ME:
						thirdPartyBPLDetailsTO
								.setCodAmts(thirdPartyBPLOutManifestTO
										.getCodAmts()[rowCount]);
						thirdPartyBPLDetailsTO
								.setPosition(thirdPartyBPLOutManifestTO
										.getPosition()[rowCount]);

						thirdPartyBPLDetailsTO
								.setConsgManifestedId(thirdPartyBPLOutManifestTO
										.getConsgManifestedIds()[rowCount]);

						thirdPartyBPLDetailsTOs.add(thirdPartyBPLDetailsTO);
					}
				}
			}
			if (thirdPartyBPLOutManifestTO.getManifestNos() != null
					&& thirdPartyBPLOutManifestTO.getManifestNos().length > 0) {
				for (int rowCount = 0; rowCount < thirdPartyBPLOutManifestTO
						.getManifestNos().length; rowCount++) {
					if (StringUtils.isNotEmpty(thirdPartyBPLOutManifestTO
							.getManifestNos()[rowCount])) {
						// Setting the common grid level attributes for Third
						// Party
						thirdPartyBPLManifestDetailsTO = new ThirdPartyBPLDetailsTO();
						thirdPartyBPLManifestDetailsTO
								.setManifestId(thirdPartyBPLOutManifestTO
										.getManifestIds()[rowCount]);
						thirdPartyBPLManifestDetailsTO
								.setManifestNo(thirdPartyBPLOutManifestTO
										.getManifestNos()[rowCount]);
						thirdPartyBPLManifestDetailsTO
								.setWeight(thirdPartyBPLOutManifestTO
										.getWeights()[rowCount]);
						// Added 2908 for setting MapEmbeddedManifestId
						if (!StringUtil.isNull(thirdPartyBPLOutManifestTO
								.getManifestMappedEmbeddeId()[rowCount])) {
							thirdPartyBPLDetailsTO
									.setMapEmbeddedManifestId(thirdPartyBPLOutManifestTO
											.getManifestMappedEmbeddeId()[rowCount]);

						}

						thirdPartyBPLManifestDetailsTO
								.setPosition(thirdPartyBPLOutManifestTO
										.getPosition()[rowCount]);
						thirdPartyBPLDetailsTOs
								.add(thirdPartyBPLManifestDetailsTO);
					}
				}
			}
		}
		LOGGER.trace("ThirdPartyBPLConverter :: thirdPartyBPLDtlsListConverter() :: END");
		return thirdPartyBPLDetailsTOs;
	}

	/**
	 * To prepare ManifestDO from ThirdPartyBPLOutManifestTO
	 * 
	 * @param thirdPartyBPLTO
	 * @return manifestDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public static ManifestDO prepareManifestDOList(
			ThirdPartyBPLOutManifestTO thirdPartyBPLTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("ThirdPartyBPLConverter :: prepareManifestDOList() :: START");

		/* Load Lot Id */
		thirdPartyBPLTO.setLoadNoId(thirdPartyBPLTO.getLoadNo());

		// Setting Common attributes
		ManifestDO manifestDO = OutManifestBaseConverter
				.outManifestTransferObjConverter(thirdPartyBPLTO);
		// Setting the fields which are specific to TPDX screen
		if (!StringUtil.isEmptyInteger(thirdPartyBPLTO.getLoginOfficeId())) {
			OfficeDO officeDO = new OfficeDO();
			officeDO.setOfficeId(thirdPartyBPLTO.getLoginOfficeId());
			manifestDO.setDestOffice(officeDO);
		}
		/* Setting No. of elements. */
		manifestDO.setNoOfElements(thirdPartyBPLTO.getNoOfElements());
		/* Setting final manifest weight */
		manifestDO.setManifestWeight(thirdPartyBPLTO.getFinalWeight());
		LOGGER.trace("ThirdPartyBPLConverter :: prepareManifestDOList() :: END");
		return manifestDO;
	}

	/**
	 * To prepare ManifestProcessDO from thirdPartyBPLTO
	 * 
	 * @param thirdPartyBPLTO
	 * @return manifestProcessDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	/*public static ManifestProcessDO prepareManifestProcessDOList(
			ThirdPartyBPLOutManifestTO thirdPartyBPLTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("ThirdPartyBPLConverter :: prepareManifestProcessDOList() :: START");
		ManifestProcessDO manifestProcessDO = null;
		 Process Number if manifest is new. 
		generateAndSetProcessNumber(thirdPartyBPLTO);
		// Setting Common attributes
		manifestProcessDO = OutManifestBaseConverter
				.outManifestBaseTransferObjConverter(thirdPartyBPLTO);
		manifestProcessDO.setLoadLotId(thirdPartyBPLTO.getLoadNo());
		manifestProcessDO.setDestOfficeId(thirdPartyBPLTO.getLoginOfficeId());
		manifestProcessDO.setNoOfElements(thirdPartyBPLTO.getNoOfElements());
		manifestProcessDO.setManifestWeight(thirdPartyBPLTO.getFinalWeight());
		manifestProcessDO.setVendorId(thirdPartyBPLTO.getVendorId());
		manifestProcessDO
				.setThirdPartyType(thirdPartyBPLTO.getThirdPartyType());
		LOGGER.trace("ThirdPartyBPLConverter :: prepareManifestProcessDOList() :: END");
		return manifestProcessDO;
	}*/

	/**
	 * To convert ManifestDO to ThirdPartyBPLOutManifestTO
	 * 
	 * @param manifestDO
	 * @return thirdPartyBPLOutManifestTO
	 * @throws CGBusinessException
	 */
	public static ThirdPartyBPLOutManifestTO thirdPartyBPLDomainConverter(
			ManifestDO manifestDO) throws CGBusinessException {
		LOGGER.trace("ThirdPartyBPLConverter :: thirdPartyBPLDomainConverter() :: START");
		// To get header details
		ThirdPartyBPLOutManifestTO thirdPartyBPLOutManifestTO = (ThirdPartyBPLOutManifestTO) outManifestDomainConverter(
				manifestDO, ManifestUtil.getOutManifestThirdPartyBPLFactory());
		LOGGER.trace("ThirdPartyBPLConverter :: thirdPartyBPLDomainConverter() :: START");
		return thirdPartyBPLOutManifestTO;
	}

	/**
	 * To generate and set process number for manifest process
	 * 
	 * @param thirdPartyBPLTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public static void generateAndSetProcessNumber(
			ThirdPartyBPLOutManifestTO thirdPartyBPLTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("ThirdPartyBPLConverter :: generateAndSetProcessNumber() :: START");
		ProcessTO processTO = new ProcessTO();
		OfficeTO officeTO = new OfficeTO();
		if (!StringUtil.isStringEmpty(thirdPartyBPLTO.getOfficeCode())) {
			officeTO.setOfficeCode(thirdPartyBPLTO.getOfficeCode());
		}
		processTO
				.setProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_BPL);
		String processNumber = outManifestCommonService.createProcessNumber(
				processTO, officeTO);
		thirdPartyBPLTO.setProcessNo(processNumber);
		LOGGER.trace("ThirdPartyBPLConverter :: generateAndSetProcessNumber() :: END");
	}

	/**
	 * To convert ConsignmentTO to ThirdPartyBPLDetailsTO during scan
	 * 
	 * @param consTO
	 * @return thirdPartyBplDtlsTO
	 */
	public static ThirdPartyBPLDetailsTO thirdPartyBplGridDetailsForInManifConsg(
			ConsignmentTO consTO) {
		LOGGER.trace("ThirdPartyBPLConverter :: thirdPartyBplGridDetailsForInManifConsg() :: START");
		ThirdPartyBPLDetailsTO thirdPartyBplDtlsTO = new ThirdPartyBPLDetailsTO();
		if (!StringUtil.isNull(consTO)) {
			thirdPartyBplDtlsTO.setConsgNo(consTO.getConsgNo());
			thirdPartyBplDtlsTO.setConsgId(consTO.getConsgId());
		}
		if (!StringUtil.isNull(consTO.getDestPincode())) {
			// thirdPartyBplDtlsTO.setPincode(consTO.getDestPincode().getPincode());
			thirdPartyBplDtlsTO.setPincodeId(consTO.getDestPincode()
					.getPincodeId());
		}
		if (!StringUtil.isNull(consTO.getDestCity())) {
			thirdPartyBplDtlsTO.setDestCityId(consTO.getDestCity().getCityId());
			thirdPartyBplDtlsTO.setDestCity(consTO.getDestCity().getCityName());
		}
		if (!StringUtil.isNull(consTO.getFinalWeight())) {
			thirdPartyBplDtlsTO.setWeight(consTO.getFinalWeight());
			thirdPartyBplDtlsTO.setBkgWeight(consTO.getFinalWeight());
		}
		thirdPartyBplDtlsTO.setNoOfPcs(consTO.getNoOfPcs());

		if (StringUtils.isNotEmpty(consTO.getChildCNsDtls())) {
			thirdPartyBplDtlsTO.setChildCn(consTO.getChildCNsDtls());
		}
		thirdPartyBplDtlsTO.setCnContentId(consTO.getCnContents()
				.getCnContentId());
		thirdPartyBplDtlsTO.setCnContent(consTO.getCnContents()
				.getCnContentName());
		if (!StringUtil.isNull(consTO.getCnPaperWorks())) {
			thirdPartyBplDtlsTO.setPaperWorkId(consTO.getCnPaperWorks()
					.getCnPaperWorkId());
			thirdPartyBplDtlsTO.setPaperWork(consTO.getCnPaperWorks()
					.getCnPaperWorkName());
		}
		LOGGER.trace("ThirdPartyBPLConverter :: thirdPartyBplGridDetailsForInManifConsg() :: START");
		return thirdPartyBplDtlsTO;
	}

}
