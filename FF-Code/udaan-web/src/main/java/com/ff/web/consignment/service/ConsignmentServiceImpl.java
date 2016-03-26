package com.ff.web.consignment.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.consignment.ChildConsignmentDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.consignment.ConsignmentDOXDO;
import com.ff.domain.serviceOffering.CNContentDO;
import com.ff.domain.serviceOffering.CNPaperWorksDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.serviceOffering.InsuredByDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.VolumetricWeightTO;
import com.ff.universe.consignment.dao.ConsignmentCommonDAO;
import com.ff.universe.consignment.service.ConsignmentCommonService;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.utils.BookingUtils;
import com.ff.web.consignment.dao.ConsignmentDAO;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;

public class ConsignmentServiceImpl implements ConsignmentService {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ConsignmentServiceImpl.class);
	private ConsignmentDAO consignmentDAO;
	private ConsignmentCommonDAO consignmentCommonDAO;
	private ConsignmentCommonService consignmentCommonService;

	private OrganizationCommonService organizationCommonService;

	/*
	 * @Override public String saveOrUpdateConsignment(ConsignmentTO consg)
	 * throws CGBusinessException, CGSystemException { ConsignmentDO consgDO =
	 * null; boolean isConsgAdded = Boolean.FALSE; String transStatus = ""; try
	 * { consgDO = BookingUtils.setUpConsignment(consg, consignmentDAO); if
	 * (consgDO != null) { isConsgAdded =
	 * consignmentDAO.saveOrUpdateConsignment(consgDO); if (isConsgAdded)
	 * transStatus = CommonConstants.SUCCESS; else transStatus =
	 * CommonConstants.FAILURE; } } catch (Exception ex) { LOGGER.error(
	 * "ERROR : ConsignmentServiceImpl.saveOrUpdateConsignment()", ex);
	 * transStatus = CommonConstants.FAILURE; } return transStatus; }
	 */

	/*
	 * public String saveOrUpdateConsgPrincingDtls( List<CNPricingDetailsTO>
	 * cnPrincingDtls) throws CGBusinessException, CGSystemException {
	 * List<CNPricingDetailsDO> cnPricingDtls = new ArrayList(); boolean
	 * isConsgAdded = Boolean.FALSE; String transStatus = ""; ConsignmentDO
	 * consgDO = null; try { // Check for weather needs to be re-calculate the
	 * rate or not for (CNPricingDetailsTO consgPricingDetailsTO :
	 * cnPrincingDtls) { CNPricingDetailsDO consgPricingDetailsDO = new
	 * CNPricingDetailsDO();
	 * 
	 * if (consgPricingDetailsTO.isReCalcRateReq()) { //RateCalculationResultTO
	 * cnRateResults = calcConsgRate(consgPricingDetailsTO
	 * .getRateCalcInputs()); if (cnRateResults != null) { consgPricingDetailsTO
	 * = setUpConsgRateDtls( consgPricingDetailsTO, cnRateResults); } }
	 * 
	 * consgPricingDetailsDO = BookingUtils
	 * .setUpCNPricingDetails(consgPricingDetailsTO); if (consgPricingDetailsTO
	 * != null) { if (consgPricingDetailsTO.getConsigmentTO() != null) consgDO =
	 * BookingUtils.setUpConsignment( consgPricingDetailsTO.getConsigmentTO(),
	 * consignmentDAO); consgPricingDetailsDO.setConsignment(consgDO); }
	 * cnPricingDtls.add(consgPricingDetailsDO); } isConsgAdded = consignmentDAO
	 * .saveOrUpdateCNPricingDtls(cnPricingDtls); if (isConsgAdded) transStatus
	 * = CommonConstants.SUCCESS; else transStatus = CommonConstants.FAILURE;
	 * 
	 * } catch (Exception ex) { LOGGER.error(
	 * "ERROR : ConsignmentServiceImpl.saveOrUpdateCNPrincingDtls()", ex);
	 * transStatus = CommonConstants.FAILURE; } return transStatus; }
	 */

	/*
	 * private RateCalculationResultTO calcConsgRate( RateCalculationInputTO
	 * rateCalcInputs) throws CGBusinessException, CGSystemException { // TODO.
	 * Integration with rate calculation RateCalculationResultTO cnRateDtls =
	 * null; cnRateDtls = new RateCalculationResultTO(); cnRateDtls = new
	 * RateCalculationResultTO(); cnRateDtls.setFreightChg(12.000);
	 * cnRateDtls.setFuelChg(10.000); cnRateDtls.setRiskSurChg(13.000);
	 * cnRateDtls.setTopayChg(11.000); cnRateDtls.setAirportHandlingChg(15.999);
	 * cnRateDtls.setServiceTax(20.000); cnRateDtls.setEduCessChg(21.000);
	 * cnRateDtls.setHigherEduCessChg(22.000);
	 * cnRateDtls.setFinalPrice(100.000); return cnRateDtls; }
	 */

	/*
	 * private CNPricingDetailsTO setUpConsgRateDtls( CNPricingDetailsTO
	 * cnPrincingDtls, RateCalculationResultTO rateCalcResultTO) {
	 * cnPrincingDtls.setFinalPrice((rateCalcResultTO.getFinalPrice() != null &&
	 * rateCalcResultTO.getFinalPrice() > 0 ? rateCalcResultTO .getFinalPrice()
	 * : CommonConstants.ZERO));
	 * cnPrincingDtls.setDiscount((rateCalcResultTO.getDiscount() != null &&
	 * rateCalcResultTO.getDiscount() > 0 ? rateCalcResultTO .getDiscount() :
	 * CommonConstants.ZERO));
	 * cnPrincingDtls.setFuelChg((rateCalcResultTO.getFuelChg() != null &&
	 * rateCalcResultTO.getFuelChg() > 0 ? rateCalcResultTO .getFuelChg() :
	 * CommonConstants.ZERO));
	 * cnPrincingDtls.setServiceTax((rateCalcResultTO.getServiceTax() != null &&
	 * rateCalcResultTO.getServiceTax() > 0 ? rateCalcResultTO .getServiceTax()
	 * : CommonConstants.ZERO));
	 * cnPrincingDtls.setRiskSurChg((rateCalcResultTO.getRiskSurChg() != null &&
	 * rateCalcResultTO.getRiskSurChg() > 0 ? rateCalcResultTO .getRiskSurChg()
	 * : CommonConstants.ZERO));
	 * cnPrincingDtls.setCodChg((rateCalcResultTO.getCodChg() != null &&
	 * rateCalcResultTO.getCodChg() > 0 ? rateCalcResultTO .getCodChg() :
	 * CommonConstants.ZERO));
	 * cnPrincingDtls.setTopayChg((rateCalcResultTO.getTopayChg() != null &&
	 * rateCalcResultTO.getTopayChg() > 0 ? rateCalcResultTO .getTopayChg() :
	 * CommonConstants.ZERO)); cnPrincingDtls
	 * .setAirportHandlingChg((rateCalcResultTO .getAirportHandlingChg() != null
	 * && rateCalcResultTO.getAirportHandlingChg() > 0 ? rateCalcResultTO
	 * .getAirportHandlingChg() : CommonConstants.ZERO));
	 * cnPrincingDtls.setSplChg((rateCalcResultTO.getSlpChg() != null &&
	 * rateCalcResultTO.getSlpChg() > 0 ? rateCalcResultTO .getSlpChg() :
	 * CommonConstants.ZERO));
	 * cnPrincingDtls.setFreightChg((rateCalcResultTO.getFreightChg() != null &&
	 * rateCalcResultTO.getFreightChg() > 0 ? rateCalcResultTO .getFreightChg()
	 * : CommonConstants.ZERO));
	 * cnPrincingDtls.setEduCessChg((rateCalcResultTO.getEduCessChg() != null &&
	 * rateCalcResultTO.getEduCessChg() > 0 ? rateCalcResultTO .getEduCessChg()
	 * : CommonConstants.ZERO)); cnPrincingDtls
	 * .setHigherEduCessChg((rateCalcResultTO.getHigherEduCessChg() != null &&
	 * rateCalcResultTO.getHigherEduCessChg() > 0 ? rateCalcResultTO
	 * .getHigherEduCessChg() : CommonConstants.ZERO)); return cnPrincingDtls; }
	 */

	/**
	 * @return the consignmentCommonService
	 */
	public ConsignmentCommonService getConsignmentCommonService() {
		return consignmentCommonService;
	}

	/**
	 * @param consignmentCommonService
	 *            the consignmentCommonService to set
	 */
	public void setConsignmentCommonService(
			ConsignmentCommonService consignmentCommonService) {
		this.consignmentCommonService = consignmentCommonService;
	}

	/**
	 * @return the consignmentDAO
	 */
	public ConsignmentDAO getConsignmentDAO() {
		return consignmentDAO;
	}

	/**
	 * @param consignmentDAO
	 *            the consignmentDAO to set
	 */
	public void setConsignmentDAO(ConsignmentDAO consignmentDAO) {
		this.consignmentDAO = consignmentDAO;
	}

	/**
	 * @return the consignmentCommonDAO
	 */
	public ConsignmentCommonDAO getConsignmentCommonDAO() {
		return consignmentCommonDAO;
	}

	/**
	 * @param consignmentCommonDAO
	 *            the consignmentCommonDAO to set
	 */
	public void setConsignmentCommonDAO(
			ConsignmentCommonDAO consignmentCommonDAO) {
		this.consignmentCommonDAO = consignmentCommonDAO;
	}

	/**
	 * @return the organizationCommonService
	 */
	public OrganizationCommonService getOrganizationCommonService() {
		return organizationCommonService;
	}

	/**
	 * @param organizationCommonService
	 *            the organizationCommonService to set
	 */
	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	@Override
	public List<Integer> createConsingment(List<ConsignmentTO> consgTOs)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ConsignmentServiceImpl::createConsingment::START------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		ConsignmentDO consgDO = null;
		// boolean isConsgAdded = Boolean.FALSE;
		// String transStatus = "";
		List<Integer> successCnIds = null;
		List<ConsignmentDO> consgDOs = new ArrayList(consgTOs.size());

		for (ConsignmentTO consg : consgTOs) {
			consgDO = BookingUtils.setUpConsignment(consg);
			consgDOs.add(consgDO);
		}
		if (!StringUtil.isEmptyColletion(consgDOs)) {
			successCnIds = consignmentDAO.saveOrUpdateConsignment(consgDOs);
		}

		LOGGER.debug("ConsignmentServiceImpl::createConsingment::END------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		return successCnIds;
	}

	@Override
	public Integer getConsgOperatingLevel(ConsignmentTO consgTO,
			OfficeTO loggedInOffice) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("ConsignmentServiceImpl::getConsgOperatingLevel::START------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		Integer consgOpLevel = 0;
		Integer reportingHubId = 0;
		OfficeTO originOfficeOnConsg = null;
		OfficeTO destOfficeOnConsg = null;
		Integer destReportingHubId = 0;
		Integer destCityId = null;
		Integer originCityId = 0;
		Integer loggedInCityId = 0;
		try {
			Integer loggedInOfficeId = loggedInOffice.getOfficeId();
			loggedInCityId = loggedInOffice.getCityId();
			destOfficeOnConsg = consgTO.getDestOffice();
			originOfficeOnConsg = organizationCommonService
					.getOfficeByIdOrCode(consgTO.getOrgOffId(),
							CommonConstants.EMPTY_STRING);
			originCityId = originOfficeOnConsg.getCityId();
			if (!StringUtil.isNull(destOfficeOnConsg)) {
				destOfficeOnConsg = organizationCommonService
						.getOfficeByIdOrCode(destOfficeOnConsg.getOfficeId(),
								CommonConstants.EMPTY_STRING);
				destReportingHubId = destOfficeOnConsg.getReportingHUB();
				destCityId = destOfficeOnConsg.getCityId();
			}
			if (!StringUtil.isNull(originOfficeOnConsg))
				reportingHubId = originOfficeOnConsg.getReportingHUB();
			else
				throw new CGBusinessException(
						ManifestErrorCodesConstants.INVALID_OFFICE);

			// Consignment Operating at Origin Branch Level
			if (consgTO.getOrgOffId().intValue() == loggedInOfficeId.intValue()) {
				consgOpLevel = CommonConstants.CONSIGNMENT_OPERATING_AT_ORIGIN_BRANCH;
			}
			// Consignment Operating at Origin Hub Level
			else if (reportingHubId.intValue() == loggedInOfficeId.intValue()) {
				consgOpLevel = CommonConstants.CONSIGNMENT_OPERATING_AT_ORIGIN_HUB;
			}
			// Consignment Operating at Destination Hub Level
			else if (destReportingHubId.intValue() == loggedInOfficeId
					.intValue()) {
				consgOpLevel = CommonConstants.CONSIGNMENT_OPERATING_AT_DESTINATION_HUB;
			}
			// Consignment Operating at Destination Office Level
			else if (destOfficeOnConsg.getOfficeId().intValue() == loggedInOfficeId
					.intValue()) {
				consgOpLevel = CommonConstants.CONSIGNMENT_OPERATING_AT_DESTINATION_BRANCH;
			}
			// Consignment Operating at Transhipment Office Level
			else if (originCityId.intValue() != loggedInCityId.intValue()
					|| destCityId.intValue() != loggedInCityId.intValue()) {
				consgOpLevel = CommonConstants.CONSIGNMENT_OPERATING_AT_TRANSHIPMENT_HUB;
			}
		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in ConsignmentServiceImpl :: getConsgOperatingLevel()..:"
					+ e.getMessage());
			throw e;
		} catch (Exception e) {
			LOGGER.error("Error occured in ConsignmentServiceImpl :: getConsgOperatingLevel()..:"
					+ e.getMessage());
			throw e;
		}
		LOGGER.debug("ConsignmentServiceImpl::getConsgOperatingLevel::END------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		return consgOpLevel;
	}

	/*
	 * @Override public Integer getRateComponentIdByCode(String rateCompCode)
	 * throws CGBusinessException, CGSystemException { LOGGER.debug(
	 * "OutManifestCommonServiceImpl :: getRateComponentIdByCode() :: START------------>:::::::"
	 * ); Integer rateCompId = null; try { RateComponentDO componentDO =
	 * consignmentDAO .getRateComponentByCode(rateCompCode); if
	 * (!StringUtil.isNull(componentDO)) { rateCompId =
	 * componentDO.getRateComponentId(); } } catch (Exception ex) {
	 * LOGGER.error(
	 * "ERROR :: OutManifestCommonServiceImpl :: getRateComponentIdByCode() ::"
	 * + ex.getMessage()); } LOGGER.debug(
	 * "OutManifestCommonServiceImpl :: getRateComponentIdByCode() :: END------------>:::::::"
	 * ); return rateCompId; }
	 */

	@Override
	public List<Integer> saveOrUpdateConsignment(
			List<ConsignmentTO> consingments) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("ConsignmentServiceImpl::saveOrUpdateConsignment::START------------>:::::::");
		List<ConsignmentDO> consgDOsForUpdate = null;
		List<Integer> successCnsIds = null;
		consgDOsForUpdate = callUpdateConsignmentProcess(consingments);
		if (consgDOsForUpdate != null) {
			successCnsIds = consignmentDAO
					.saveOrUpdateConsignment(consgDOsForUpdate);
		}

		LOGGER.trace("ConsignmentServiceImpl::saveOrUpdateConsignment::END------------>:::::::");
		return successCnsIds;
	}
	@Override
	public List<Integer> updateConsignmentForViewEdit(
			List<ConsignmentTO> consingments) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("ConsignmentServiceImpl::saveOrUpdateConsignment::START------------>:::::::");
		List<ConsignmentDO> consgDOsForUpdate = null;
		List<Integer> successCnsIds = null;
		consgDOsForUpdate = updateConsignmentForViewAndEdit(consingments);
		if (consgDOsForUpdate != null) {
			successCnsIds = consignmentDAO
					.saveOrUpdateConsignment(consgDOsForUpdate);
		}

		LOGGER.trace("ConsignmentServiceImpl::saveOrUpdateConsignment::END------------>:::::::");
		return successCnsIds;
	}


	public List<ConsignmentDO> callUpdateConsignmentProcess(
			List<ConsignmentTO> consingments) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("ConsignmentServiceImpl::callUpdateConsignmentProcess::START------------>:::::::");
		List<ConsignmentDO> consgDOsForUpdate = new ArrayList(consingments.size());
		for (ConsignmentTO consingment : consingments) {
			ConsignmentDO consgDO = consignmentCommonDAO
					.getConsingmentDtls(consingment.getConsgNo());
			if (StringUtil.isNull(consgDO)) {
				throw new CGBusinessException("Invalid consignment");
			}
			
			if ((!consingment.getDestPincode().getPincodeId()
					.equals(consgDO.getDestPincodeId().getPincodeId()))
					|| (!StringUtil.isEmptyDouble(consingment.getFinalWeight()
							.doubleValue())
							&& !StringUtil.isEmptyDouble(consgDO
									.getFinalWeight().doubleValue()) && !consingment
							.getFinalWeight().equals(
									consgDO.getFinalWeight().doubleValue()))) {
				consignmentCommonService
						.updateBillingFlagsInConsignment(consgDO,
								CommonConstants.UPDATE_BILLING_FLAGS_UPDATE_CN);

			}
			// Updating pincode
			if (consingment.getDestPincode().getPincodeId().intValue() != consgDO
					.getDestPincodeId().getPincodeId().intValue()) {
				consgDO.getDestPincodeId().setPincodeId(
						consingment.getDestPincode().getPincodeId());
			}
			consgDO.setDtUpdateToCentral(CommonConstants.YES);
			consgDO.setDtToCentral(CommonConstants.NO);
			// Updating weight
			/*
			 * if (consingment.getFinalWeight().doubleValue() > consgDO
			 * .getFinalWeight().doubleValue()) {
			 */
			if (!StringUtil.isEmptyDouble(consingment.getFinalWeight())) {
				consgDO.setFinalWeight(consingment.getFinalWeight());
			}
			if (!StringUtil.isEmptyDouble(consingment.getActualWeight())) {
				consgDO.setActualWeight(consingment.getActualWeight());
			}
			if (!StringUtil.isEmptyDouble(consingment.getDiscount())) {
				consgDO.setDiscount(consingment.getDiscount());
			}else if (!StringUtil.isNull(consingment.getDiscount()) && consingment.getDiscount() == 0.0){
				consgDO.setDiscount(consingment.getDiscount());
			}
			
			if (consingment.getVolWeight() != null
					&& consingment.getVolWeight() > 0) {
				consgDO.setVolWeight(consingment.getVolWeight());
				consgDO.setHeight(consingment.getHeight());
				consgDO.setLength(consingment.getLength());
				consgDO.setBreath(consingment.getBreath());
			}

			// }
			// Child Consignments
			if (consingment.getNoOfPcs() != consgDO.getNoOfPcs()) {
				if (!StringUtil.isEmptyColletion(consingment.getChildTOSet())) {
					Set<ChildConsignmentDO> chilsCns = BookingUtils
							.setUpChildConsignments(
									consingment.getChildTOSet(), consignmentDAO);
					consgDO.setChildCNs(chilsCns);
				}
			}

			if (consingment.getNoOfPcs() != null
					&& consingment.getNoOfPcs() > 0) {
				consgDO.setNoOfPcs(consingment.getNoOfPcs());
			}

			// Setting volweight
			if (!StringUtil.isNull(consingment.getVolWightDtls())) {
				consgDO.setVolWeight(consingment.getVolWightDtls()
						.getVolWeight());
				consgDO.setHeight(consingment.getVolWightDtls().getHeight());
				consgDO.setBreath(consingment.getVolWightDtls().getBreadth());
				consgDO.setLength(consingment.getVolWightDtls().getLength());
			}
			if (!StringUtil.isNull(consingment.getCnContents())
					&& !StringUtil.isEmptyInteger(consingment.getCnContents()
							.getCnContentId())
					&& consingment.getCnContents().getCnContentId() > 0) {
				CNContentDO cnContent = new CNContentDO();
				cnContent.setCnContentId(consingment.getCnContents()
						.getCnContentId());
				consgDO.setCnContentId(cnContent);
				if (StringUtils.isNotBlank(consingment.getCnContents()
						.getOtherContent())) {
					consgDO.setOtherCNContent(consingment.getCnContents()
							.getOtherContent());
				}else{
					consgDO.setOtherCNContent(null);
				}
			}
			if (!StringUtil.isNull(consingment.getCnPaperWorks())
					&& !StringUtil.isEmptyInteger(consingment.getCnPaperWorks()
							.getCnPaperWorkId())
					&& consingment.getCnPaperWorks().getCnPaperWorkId() > 0) {
				CNPaperWorksDO paperWork = new CNPaperWorksDO();
				paperWork.setCnPaperWorkId(consingment.getCnPaperWorks()
						.getCnPaperWorkId());
				consgDO.setCnPaperWorkId(paperWork);
				consgDO.setPaperWorkRefNo(consingment.getCnPaperWorks()
						.getPaperWorkRefNum());
			}
			if (!StringUtil.isNull(consingment.getInsuredByTO())
					&& !StringUtil.isEmptyInteger(consingment.getInsuredByTO()
							.getInsuredById())) {
				InsuredByDO insuredBy = new InsuredByDO();
				insuredBy.setInsuredById(consingment.getInsuredByTO()
						.getInsuredById());
				consgDO.setInsuredBy(insuredBy);
				consgDO.setInsurencePolicyNo(consingment.getInsuredByTO()
						.getPolicyNo());
			}
			// Setting consigee and consignor
			updateConsigneeConsignor(consingment, consgDO);
			// Set CN Pricing details
			updateCnPricingDtls(consingment, consgDO);
			if (StringUtils.isNotBlank(consingment.getRemarks())) {
				consgDO.setRemarks(consingment.getRemarks());
			}
			if (StringUtils.isNotBlank(consingment.getMobileNo())) {
				consgDO.setMobileNo(consingment.getMobileNo());
			}
			if (StringUtils.isNotBlank(consingment.getReceivedStatus())) {
				consgDO.setReceivedStatus(consingment.getReceivedStatus());
			}
			// Re Calculating Consg Rate and updating
			if (consingment.isReCalcRateReq()) {
				if (!CGCollectionUtils.isEmpty(consingment
						.getConsgRateOutputTOs())) {
					Set<ConsignmentBillingRateDO> consgRateDtls = BookingUtils
							.setupConsgRateDtls(consingment.getConsgNo(),
									consingment.getConsgRateOutputTOs());
					if (!CGCollectionUtils.isEmpty(consgRateDtls)) {
						consgRateDtls.iterator().next()
								.setConsignmentDO(consgDO);
						Integer consgRateId = consgDO.getConsgRateDtls()
								.iterator().next().getConsignmentRateId();
						consgRateDtls.iterator().next()
								.setConsignmentRateId(consgRateId);
						consgDO.setConsgRateDtls(consgRateDtls);
					}
				}
			}

			consgDOsForUpdate.add(consgDO);
		}
		LOGGER.trace("ConsignmentServiceImpl::callUpdateConsignmentProcess::END------------>:::::::");
		return consgDOsForUpdate;
	}
	
	public List<ConsignmentDO> updateConsignmentForViewAndEdit(
			List<ConsignmentTO> consingments) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("ConsignmentServiceImpl::callUpdateConsignmentProcess::START------------>:::::::");
		List<ConsignmentDO> consgDOsForUpdate = new ArrayList(consingments.size());
		for (ConsignmentTO consingment : consingments) {
			ConsignmentDO consgDO = consignmentCommonDAO
					.getConsingmentDtls(consingment.getConsgNo());
			if (StringUtil.isNull(consgDO)) {
				throw new CGBusinessException("Invalid consignment");
			}

			ConsignmentTypeDO typeDO = consgDO.getConsgType();
			if (!StringUtil.isEmptyInteger(consingment.getConsgTypeId())
					&& !StringUtil.isEmptyInteger(typeDO.getConsignmentId()) ) {
				if (!consingment.getConsgTypeId().equals(
						typeDO.getConsignmentId())) {
					ConsignmentTypeDO consgType = new ConsignmentTypeDO();
					consgType.setConsignmentId(consingment.getConsgTypeId());
					consgType.setConsignmentCode(consingment.getConsgTypeCode());
					consgDO.setConsgType(consgType);
				}
			}
			
			if ((!consingment.getDestPincode().getPincodeId()
					.equals(consgDO.getDestPincodeId().getPincodeId()))
					|| (!StringUtil.isEmptyDouble(consingment.getFinalWeight()
							.doubleValue())
							&& !StringUtil.isEmptyDouble(consgDO
									.getFinalWeight().doubleValue()) && !consingment
							.getFinalWeight().equals(
									consgDO.getFinalWeight().doubleValue()))) {
				consignmentCommonService
						.updateBillingFlagsInConsignment(consgDO,
								CommonConstants.UPDATE_BILLING_FLAGS_UPDATE_CN);

			}
			
			if (!StringUtil.isEmptyDouble(consingment.getDeclaredValue())) {
				consgDO.setDeclaredValue(consingment.getDeclaredValue());
			}else if (consingment.getDiscount() == 0.0){
				consgDO.setDeclaredValue(consingment.getDeclaredValue());
			}
			
			// Updating pincode
			if (consingment.getDestPincode().getPincodeId().intValue() != consgDO
					.getDestPincodeId().getPincodeId().intValue()) {
				consgDO.getDestPincodeId().setPincodeId(
						consingment.getDestPincode().getPincodeId());
			}
			consgDO.setDtUpdateToCentral(CommonConstants.YES);
			consgDO.setDtToCentral(CommonConstants.NO);
			// Updating weight
			/*
			 * if (consingment.getFinalWeight().doubleValue() > consgDO
			 * .getFinalWeight().doubleValue()) {
			 */
			if (!StringUtil.isEmptyDouble(consingment.getFinalWeight())) {
				consgDO.setFinalWeight(consingment.getFinalWeight());
			}
			if (!StringUtil.isEmptyDouble(consingment.getActualWeight())) {
				consgDO.setActualWeight(consingment.getActualWeight());
			}
			if (!StringUtil.isEmptyDouble(consingment.getDiscount())) {
				consgDO.setDiscount(consingment.getDiscount());
			}else if (consingment.getDiscount() == 0.0){
				consgDO.setDiscount(consingment.getDiscount());
			}
			
			if (consingment.getVolWightDtls() != null) {
				VolumetricWeightTO volTO=consingment.getVolWightDtls();
				consgDO.setVolWeight(volTO.getVolWeight());
				consgDO.setHeight(volTO.getHeight());
				consgDO.setLength(volTO.getLength());
				consgDO.setBreath(volTO.getBreadth());
			}else{
				consgDO.setVolWeight(0.0);
				consgDO.setHeight(0.0);
				consgDO.setLength(0.0);
				consgDO.setBreath(0.0);
			}

			// Child Consignments
			if (!StringUtil.isEmptyColletion(consingment.getChildTOSet())) {
				Set<ChildConsignmentDO> chilsCns = BookingUtils
						.setUpChildConsignments(consingment.getChildTOSet(),
								consignmentDAO);
				consgDO.setChildCNs(chilsCns);
			} else {
				Set<ChildConsignmentDO> chilsCns = new HashSet<ChildConsignmentDO>();
				consgDO.setChildCNs(chilsCns);
			}

			if (consingment.getNoOfPcs() != null
					&& consingment.getNoOfPcs() > 0) {
				consgDO.setNoOfPcs(consingment.getNoOfPcs());
			}else{
				consgDO.setNoOfPcs(consingment.getNoOfPcs());
			}

			// Setting volweight
			/*if (!StringUtil.isNull(consingment.getVolWightDtls())) {
				consgDO.setVolWeight(consingment.getVolWightDtls()
						.getVolWeight());
				consgDO.setHeight(consingment.getVolWightDtls().getHeight());
				consgDO.setBreath(consingment.getVolWightDtls().getBreadth());
				consgDO.setLength(consingment.getVolWightDtls().getLength());
			}*/
			if (!StringUtil.isNull(consingment.getCnContents())
					&& !StringUtil.isEmptyInteger(consingment.getCnContents()
							.getCnContentId())
					&& consingment.getCnContents().getCnContentId() > 0) {
				CNContentDO cnContent = new CNContentDO();
				cnContent.setCnContentId(consingment.getCnContents()
						.getCnContentId());
				consgDO.setCnContentId(cnContent);
				if (StringUtils.isNotBlank(consingment.getCnContents()
						.getOtherContent())) {
					consgDO.setOtherCNContent(consingment.getCnContents()
							.getOtherContent());
				}else{
					consgDO.setOtherCNContent(null);
				}
			} else {
				CNContentDO cnContent = new CNContentDO();
				consgDO.setCnContentId(null);
				consgDO.setOtherCNContent("");
			}
			if (!StringUtil.isNull(consingment.getCnPaperWorks())
					&& !StringUtil.isEmptyInteger(consingment.getCnPaperWorks()
							.getCnPaperWorkId())
					&& consingment.getCnPaperWorks().getCnPaperWorkId() > 0) {
				CNPaperWorksDO paperWork = new CNPaperWorksDO();
				paperWork.setCnPaperWorkId(consingment.getCnPaperWorks()
						.getCnPaperWorkId());
				consgDO.setCnPaperWorkId(paperWork);
				consgDO.setPaperWorkRefNo(consingment.getCnPaperWorks()
						.getPaperWorkRefNum());
			}else if(!StringUtil.isNull(consingment.getCnPaperWorks())
					&& StringUtil.isEmptyInteger(consingment.getCnPaperWorks()
							.getCnPaperWorkId())){
				CNPaperWorksDO paperWork = new CNPaperWorksDO();
				consgDO.setCnPaperWorkId(null);
				consgDO.setPaperWorkRefNo("");
				
			}
			if (!StringUtil.isNull(consingment.getInsuredByTO())
					&& !StringUtil.isEmptyInteger(consingment.getInsuredByTO()
							.getInsuredById())) {
				InsuredByDO insuredBy = new InsuredByDO();
				insuredBy.setInsuredById(consingment.getInsuredByTO()
						.getInsuredById());
				consgDO.setInsuredBy(insuredBy);
				consgDO.setInsurencePolicyNo(consingment.getInsuredByTO()
						.getPolicyNo());
			}else if (!StringUtil.isNull(consingment.getInsuredByTO())
					&& StringUtil.isEmptyInteger(consingment.getInsuredByTO()
							.getInsuredById())) {
				InsuredByDO insuredBy = new InsuredByDO();
				consgDO.setInsuredBy(null);
				consgDO.setInsurencePolicyNo("");
				
			}
			// Setting consigee and consignor
			updateConsigneeConsignor(consingment, consgDO);
			// Set CN Pricing details
			updateCnPricingDtls(consingment, consgDO);
			if (StringUtils.isNotBlank(consingment.getRemarks())) {
				consgDO.setRemarks(consingment.getRemarks());
			}
			if (StringUtils.isNotBlank(consingment.getMobileNo())) {
				consgDO.setMobileNo(consingment.getMobileNo());
			}
			if (StringUtils.isNotBlank(consingment.getReceivedStatus())) {
				consgDO.setReceivedStatus(consingment.getReceivedStatus());
			}
			
			if (StringUtils.isNotEmpty(consingment.getRefNo())){
				consgDO.setRefNo(consingment.getRefNo());
			}
			// Re Calculating Consg Rate and updating
			if (consingment.isReCalcRateReq()) {
				if (!CGCollectionUtils.isEmpty(consingment
						.getConsgRateOutputTOs())) {
					Set<ConsignmentBillingRateDO> consgRateDtls = BookingUtils
							.setupConsgRateDtls(consingment.getConsgNo(),
									consingment.getConsgRateOutputTOs());
					if (!CGCollectionUtils.isEmpty(consgRateDtls)) {
						consgRateDtls.iterator().next()
								.setConsignmentDO(consgDO);
						Integer consgRateId = consgDO.getConsgRateDtls()
								.iterator().next().getConsignmentRateId();
						Integer createdBy = consgDO.getConsgRateDtls()
								.iterator().next().getCreatedBy();
						Integer updatedBy = consgDO.getConsgRateDtls()
								.iterator().next().getUpdatedBy();
						consgRateDtls.iterator().next()
								.setCreatedBy(createdBy);
						consgRateDtls.iterator().next()
								.setUpdatedBy(updatedBy);
						consgRateDtls.iterator().next()
								.setConsignmentRateId(consgRateId);
						consgRateDtls.iterator().next().setUpdatedDate(new Date());
						consgDO.setConsgRateDtls(consgRateDtls);
					}
				}
			}
			consgDO.setDtFromOpsman(consgDO.getDtFromOpsman());
			consgDO.setUpdatedDate(new Date());
			consgDOsForUpdate.add(consgDO);
		}
		LOGGER.trace("ConsignmentServiceImpl::callUpdateConsignmentProcess::END------------>:::::::");
		return consgDOsForUpdate;
	}

	public List<ConsignmentDOXDO> updateConsignmentProcessForOutManifest(
			List<ConsignmentTO> consingments) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("ConsignmentServiceImpl::updateConsignmentProcessForOutManifest::START------------>:::::::");
		List<ConsignmentDOXDO> consgDOsForUpdate = new ArrayList<ConsignmentDOXDO>();
		for (ConsignmentTO consingment : consingments) {
			ConsignmentDOXDO consgDO = consignmentCommonDAO
					.getConsingmentByConsgNo(consingment.getConsgNo());
			if (StringUtil.isNull(consgDO)) {
				throw new CGBusinessException("Invalid consignment");
			}

			/* Updating pincode */
			if (consingment.getDestPincode().getPincodeId().intValue() != consgDO
					.getDestPincodeId().getPincodeId().intValue()) {
				consgDO.getDestPincodeId().setPincodeId(
						consingment.getDestPincode().getPincodeId());
			}
			/* Updating weight */
			if (consingment.getFinalWeight().doubleValue() > consgDO
					.getFinalWeight().doubleValue()) {
				consgDO.setFinalWeight(consingment.getFinalWeight());
				consgDO.setActualWeight(consingment.getFinalWeight());
			}
			/* Updating process */
			if (!StringUtil.isNull(consingment.getUpdatedProcessFrom())
					&& !StringUtil.isEmptyInteger(consingment
							.getUpdatedProcessFrom().getProcessId())) {
				ProcessDO updatedProcessDO = new ProcessDO();
				updatedProcessDO.setProcessId(consingment
						.getUpdatedProcessFrom().getProcessId());
				consgDO.setUpdatedProcess(updatedProcessDO);
			}
			/* Setting consigee */
			if (!StringUtil.isNull(consingment.getConsigneeTO())
					&& !StringUtil.isNull(consgDO.getConsignee())) {
				if (StringUtils.isNotEmpty(consingment.getConsigneeTO()
						.getFirstName()))
					consgDO.getConsignee().setFirstName(
							consingment.getConsigneeTO().getFirstName());
				if (StringUtils.isNotEmpty(consingment.getConsigneeTO()
						.getAddress()))
					consgDO.getConsignee().setAddress(
							consingment.getConsigneeTO().getAddress());
				if (StringUtils.isNotEmpty(consingment.getConsigneeTO()
						.getPhone()))
					consgDO.getConsignee().setPhone(
							consingment.getConsigneeTO().getPhone());
				if (StringUtils.isNotEmpty(consingment.getConsigneeTO()
						.getMobile()))
					consgDO.getConsignee().setMobile(
							consingment.getConsigneeTO().getMobile());
			}
			/* Updating consg status */
			consgDO.setConsgStatus("B");

			consgDO.setDtUpdateToCentral(CommonConstants.YES);
			consgDO.setDtToCentral(CommonConstants.NO);
			consgDOsForUpdate.add(consgDO);
		}
		LOGGER.trace("ConsignmentServiceImpl::updateConsignmentProcessForOutManifest::END------------>:::::::");
		return consgDOsForUpdate;
	}

	private void updateConsigneeConsignor(ConsignmentTO consingment,
			ConsignmentDO consgDO) {
		LOGGER.trace("ConsignmentServiceImpl::updateConsigneeConsignor::START------------>:::::::");
		if (!StringUtil.isNull(consingment.getConsigneeTO())
				&& !StringUtil.isNull(consgDO.getConsignee())) {
			consgDO.getConsignee().setFirstName(
					consingment.getConsigneeTO().getFirstName());
			consgDO.getConsignee().setAddress(
					consingment.getConsigneeTO().getAddress());
			consgDO.getConsignee().setPhone(
					consingment.getConsigneeTO().getPhone());
			consgDO.getConsignee().setMobile(
					consingment.getConsigneeTO().getMobile());
		} else if (!StringUtil.isNull(consingment.getConsigneeTO())) {
			if (!StringUtil.isStringEmpty(consingment.getConsigneeTO()
					.getFirstName())
					|| !StringUtil.isStringEmpty(consingment.getConsigneeTO()
							.getAddress())
					|| !StringUtil.isStringEmpty(consingment.getConsigneeTO()
							.getPhone())
					|| !StringUtil.isStringEmpty(consingment.getConsigneeTO()
							.getMobile())) {
				ConsigneeConsignorDO newCne = new ConsigneeConsignorDO();
				newCne.setFirstName(consingment.getConsigneeTO().getFirstName());
				newCne.setAddress(consingment.getConsigneeTO().getAddress());
				newCne.setPhone(consingment.getConsigneeTO().getPhone());
				newCne.setMobile(consingment.getConsigneeTO().getMobile());
				newCne.setPartyType(BookingConstants.CONSIGNEE);
				consgDO.setConsignee(newCne);
			}
		}
		if (!StringUtil.isNull(consingment.getConsignorTO())
				&& !StringUtil.isNull(consgDO.getConsignor())) {
			consgDO.getConsignor().setFirstName(
					consingment.getConsignorTO().getFirstName());
			consgDO.getConsignor().setLastName(
					consingment.getConsignorTO().getLastName());
			consgDO.getConsignor().setAddress(
					consingment.getConsignorTO().getAddress());
			consgDO.getConsignor().setPhone(
					consingment.getConsignorTO().getPhone());
			consgDO.getConsignor().setMobile(
					consingment.getConsignorTO().getMobile());
		} else if (!StringUtil.isNull(consingment.getConsignorTO())) {
			if (!StringUtil.isStringEmpty(consingment.getConsignorTO()
					.getFirstName())
					|| !StringUtil.isStringEmpty(consingment.getConsignorTO()
							.getAddress())
					|| !StringUtil.isStringEmpty(consingment.getConsignorTO()
							.getPhone())
					|| !StringUtil.isStringEmpty(consingment.getConsignorTO()
							.getMobile())) {
				ConsigneeConsignorDO newCne = new ConsigneeConsignorDO();
				newCne.setFirstName(consingment.getConsignorTO().getFirstName());
				newCne.setAddress(consingment.getConsignorTO().getAddress());
				newCne.setPhone(consingment.getConsignorTO().getPhone());
				newCne.setMobile(consingment.getConsignorTO().getMobile());
				newCne.setPartyType(BookingConstants.CONSIGNOR);
				consgDO.setConsignor(newCne);
			}
		}
		LOGGER.trace("ConsignmentServiceImpl::updateConsigneeConsignor::END------------>:::::::");
	}

	private void updateCnPricingDtls(ConsignmentTO consingment,
			ConsignmentDO consgDO) {
		LOGGER.trace("ConsignmentServiceImpl::updateCnPricingDtls::START------------>:::::::");

		CNPricingDetailsTO cnPricingDetailsTO = consingment.getConsgPriceDtls();
		if (cnPricingDetailsTO != null) {
			if (!StringUtil.isEmptyDouble(cnPricingDetailsTO.getCodAmt())) {
				consgDO.setCodAmt(cnPricingDetailsTO.getCodAmt());
			}
			if (!StringUtil
					.isEmptyDouble(cnPricingDetailsTO.getDeclaredvalue())) {
				consgDO.setDeclaredValue(cnPricingDetailsTO.getDeclaredvalue());
			}
		}

		if (!StringUtil.isEmptyDouble(consingment.getCodAmt())) {
			consgDO.setCodAmt(consingment.getCodAmt());
		}

		String cnSeries = consgDO.getConsgNo().substring(4, 5);
		if (cnPricingDetailsTO != null
				&& !StringUtil
						.isEmptyDouble(cnPricingDetailsTO.getFinalPrice())
				&& StringUtils.equalsIgnoreCase(cnSeries, "T")) {
			consgDO.setTopayAmt(cnPricingDetailsTO.getFinalPrice());

		} else if (!StringUtil.isEmptyDouble(consingment.getTopayAmt())) {
			consgDO.setTopayAmt(consingment.getTopayAmt());
		}

		if (!StringUtil.isEmptyDouble(consingment.getDeclaredValue())) {
			consgDO.setDeclaredValue(consingment.getDeclaredValue());
		}
		if (!StringUtil.isEmptyDouble(consingment.getLcAmount())) {
			consgDO.setLcAmount(consingment.getLcAmount());
		}
		if (StringUtils.isNotBlank(consingment.getLcBankName())) {
			consgDO.setLcBankName(consingment.getLcBankName());
		}
		if (!StringUtil.isEmptyDouble(consingment.getBaAmt())) {
			consgDO.setBaAmt(consingment.getBaAmt());
		}
		if (!StringUtil.isEmptyDouble(consingment.getPrice())) {
			consgDO.setPrice(consingment.getPrice());
		}
		if (!StringUtil.isEmptyDouble(consingment.getSplChg())) {
			consgDO.setSplChg(consingment.getSplChg());
		}

		LOGGER.trace("ConsignmentServiceImpl::updateCnPricingDtls::START------------>:::::::");
	}

	public Integer getChildConsgIdByConsgNo(String consgNumber)
			throws CGSystemException {
		return consignmentDAO.getChildConsgIdByConsgNo(consgNumber);
	}

	@Override
	public List<ConsignmentDO> updateConsignments(
			List<ConsignmentTO> consingments) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("ConsignmentServiceImpl::updateConsignments::START------------>:::::::");
		List<ConsignmentDO> consignmentDOs = null;
		try {
			consignmentDOs = callUpdateConsignmentProcess(consingments);
		} catch (Exception ex) {
			LOGGER.error("Exception happened in updateConsignments of ConsignmentServiceImpl..."
					, ex);
			throw ex;
		}
		LOGGER.trace("ConsignmentServiceImpl::updateConsignments::END------------>:::::::");
		return consignmentDOs;
	}

	@Override
	public String updateConsignmentForOutDoxMF(List<ConsignmentTO> consingments)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("ConsignmentServiceImpl::updateConsignmentForOutDoxMF::START------------>:::::::");
		List<ConsignmentDOXDO> consgDOsForUpdate = null;
		boolean isConsgAdded = Boolean.FALSE;
		String transStatus = "";
		try {
			consgDOsForUpdate = updateConsignmentProcessForOutManifest(consingments);
			// List<ConsignmentDOXDO> consignmentUpdateDOs =
			// copyConsignmentsForUpdate(consgDOsForUpdate);
			if (consgDOsForUpdate != null) {
				isConsgAdded = consignmentDAO
						.updateConsignmentForOutDoxMF(consgDOsForUpdate);
				if (isConsgAdded)
					transStatus = CommonConstants.SUCCESS;
				else
					transStatus = CommonConstants.FAILURE;
			}
		} catch (Exception ex) {
			LOGGER.error(
					"ERROR : ConsignmentServiceImpl.saveOrUpdateConsignment()",
					ex);
			transStatus = CommonConstants.FAILURE;
			throw new CGBusinessException(ex);
		}
		LOGGER.trace("ConsignmentServiceImpl::updateConsignmentForOutDoxMF::END------------>:::::::");
		return transStatus;
	}
}
