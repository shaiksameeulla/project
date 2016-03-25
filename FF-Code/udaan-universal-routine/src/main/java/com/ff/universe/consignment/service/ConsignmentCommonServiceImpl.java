package com.ff.universe.consignment.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.consignment.ChildConsignmentDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.geography.PincodeTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.VolumetricWeightTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.consignment.dao.ConsignmentCommonDAO;
import com.ff.universe.organization.service.OrganizationCommonService;

public class ConsignmentCommonServiceImpl implements ConsignmentCommonService {
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ConsignmentCommonServiceImpl.class);
	private static final CGBaseDO CNPricingDetailsTO = null;
	private ConsignmentCommonDAO consignmentCommonDAO;
	private OrganizationCommonService organizationCommonService;

	public ConsignmentCommonDAO getConsignmentCommonDAO() {
		return consignmentCommonDAO;
	}

	public void setConsignmentCommonDAO(
			ConsignmentCommonDAO consignmentCommonDAO) {
		this.consignmentCommonDAO = consignmentCommonDAO;
	}

	/**
	 * @param organizationCommonService
	 *            the organizationCommonService to set
	 */
	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	/*
	 * @Override public CNPricingDetailsTO getConsgPrincingDtls(String
	 * consgNumner) throws CGBusinessException, CGSystemException {
	 * CNPricingDetailsDO cnPrincingDtls = null; CNPricingDetailsTO
	 * cnPrincingDtlsTO = null; try { cnPrincingDtls = consignmentCommonDAO
	 * .getConsgPrincingDtls(consgNumner); if
	 * (!StringUtil.isNull(cnPrincingDtls)) { cnPrincingDtlsTO = new
	 * CNPricingDetailsTO();
	 * CGObjectConverter.createToFromDomain(cnPrincingDtls, cnPrincingDtlsTO);
	 * if (!StringUtil.isNull(cnPrincingDtls.getConsignment())) { ConsignmentTO
	 * consgTO = setUpConsignmentDtls(cnPrincingDtls .getConsignment()); //
	 * cnPrincingDtlsTO.setConsigmentTO(consgTO); } } } catch
	 * (CGBusinessException e) { LOGGER.error(
	 * "Error occured in ConsignmentCommonServiceImpl :: getConsgPrincingDtls()..:"
	 * , e); throw new CGBusinessException(e); } catch (CGSystemException e) {
	 * LOGGER.error(
	 * "Error occured in ConsignmentCommonServiceImpl :: getConsgPrincingDtls()..:"
	 * + e.getMessage()); throw new CGSystemException(e); } return
	 * cnPrincingDtlsTO; }
	 */

	@Override
	public ConsignmentTO getConsingmentDtls(String consgNumner)
			throws CGBusinessException, CGSystemException {
		ConsignmentDO consgDO = null;
		ConsignmentTO consgTO = null;
		CNPricingDetailsTO consgPriceDtls = null;
		try {
			consgDO = consignmentCommonDAO.getConsingmentDtls(consgNumner);
			if (!StringUtil.isNull(consgDO)) {
				consgTO = setUpConsignmentDtls(consgDO);
				/** Rate calculation Done at the time of booking only */
				/*if(CGCollectionUtils.isEmpty(consgDO.getConsgRateDtls())){
					consgTO.setIsBulkBookedCN(CommonConstants.YES);
				} else{
					consgTO.setIsBulkBookedCN(CommonConstants.NO);
				}*/
				consgTO.setIsBulkBookedCN(CommonConstants.NO);
				//Set up CN pricing to
				consgPriceDtls =consgTO.getConsgPriceDtls();
				if(StringUtil.isNull(consgPriceDtls)){
					consgPriceDtls = new CNPricingDetailsTO();
				}
				if(!StringUtil.isNull(consgDO.getRateType())){
					consgPriceDtls.setRateType(consgDO.getRateType());
				}if(!StringUtil.isEmptyDouble(consgDO.getCodAmt())){
					consgPriceDtls.setCodAmt(consgDO.getCodAmt());
				}if(!StringUtil.isEmptyDouble(consgDO.getLcAmount())){
					consgPriceDtls.setLcAmount(consgDO.getLcAmount());
				}if(!StringUtil.isEmptyDouble(consgDO.getDeclaredValue())){
					consgPriceDtls.setDeclaredvalue(consgDO.getDeclaredValue());
				}if(!StringUtil.isEmptyDouble(consgDO.getDiscount())){
					consgPriceDtls.setDiscount(consgDO.getDiscount());
				}if(!StringUtil.isEmptyDouble(consgDO.getBaAmt())){
					consgPriceDtls.setBaAmt(consgDO.getBaAmt());
				}
				consgTO.setIsExcessConsg(consgDO.getIsExcessConsg());
				consgTO.setConsgPriceDtls(consgPriceDtls);
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Error occured in ConsignmentCommonServiceImpl :: getConsingmentDtls()..:",
					e);
			throw new CGBusinessException(e);
		} catch (CGSystemException e) {
			LOGGER.error("Error occured in ConsignmentCommonServiceImpl :: getConsingmentDtls()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return consgTO;
	}

	private ConsignmentTO setUpConsignmentDtls(ConsignmentDO consgDO)
			throws CGBusinessException {
		ConsignmentTO consgTO = new ConsignmentTO();

		if (!StringUtil.isEmptyColletion(consgDO.getChildCNs())) {
			StringBuilder chindCNDtls = new StringBuilder();
			for (ChildConsignmentDO childCN : consgDO.getChildCNs()) {
				chindCNDtls.append(childCN.getChildConsgNumber());
				chindCNDtls.append(CommonConstants.COMMA);
				chindCNDtls.append(childCN.getChildConsgWeight());
				chindCNDtls.append(CommonConstants.HASH);
			}
			consgTO.setChildCNsDtls(chindCNDtls.toString());
		}
		convertConsignmentDO2TO(consgDO, consgTO);
		return consgTO;
	}

	/**
	 * @param consgDO
	 * @param consgTO
	 * @throws CGBusinessException
	 */
	private void convertConsignmentDO2TO(ConsignmentDO consgDO,
			ConsignmentTO consgTO) throws CGBusinessException {
		CGObjectConverter.createToFromDomain(consgDO, consgTO);
		if (!StringUtil.isNull(consgDO.getDestPincodeId())) {
			PincodeTO destPin = new PincodeTO();
			CGObjectConverter.createToFromDomain(consgDO.getDestPincodeId(),
					destPin);
			consgTO.setDestPincode(destPin);
		}
		if (!StringUtil.isNull(consgDO.getOrgOffId())) {
			consgTO.setOrgOffId(consgDO.getOrgOffId());
		}

		if (!StringUtil.isNull(consgDO.getConsgType())) {
			ConsignmentTypeTO typeTO = new ConsignmentTypeTO();
			CGObjectConverter
					.createToFromDomain(consgDO.getConsgType(), typeTO);
			consgTO.setTypeTO(typeTO);
		}

		if (!StringUtil.isNull(consgDO.getCnPaperWorkId())) {
			CNPaperWorksTO cnPaperworkTO = new CNPaperWorksTO();
			CGObjectConverter.createToFromDomain(consgDO.getCnPaperWorkId(),
					cnPaperworkTO);
			cnPaperworkTO.setPaperWorkRefNum(consgDO.getPaperWorkRefNo());
			consgTO.setCnPaperWorks(cnPaperworkTO);
		}
		if (!StringUtil.isNull(consgDO.getCnContentId())) {
			CNContentTO cnContentTO = new CNContentTO();
			CGObjectConverter.createToFromDomain(consgDO.getCnContentId(),
					cnContentTO);
			cnContentTO.setOtherContent(consgDO.getOtherCNContent());
			consgTO.setCnContents(cnContentTO);
		}
		if (!StringUtil.isNull(consgDO.getInsuredBy())) {
			InsuredByTO insuredBy = new InsuredByTO();
			CGObjectConverter.createToFromDomain(consgDO.getInsuredBy(),
					insuredBy);
			consgTO.setInsuredByTO(insuredBy);
		}
		if (!StringUtil.isEmptyDouble(consgDO.getVolWeight())) {
			VolumetricWeightTO volWeightDtls = new VolumetricWeightTO();
			volWeightDtls.setVolWeight(consgDO.getVolWeight());
			volWeightDtls.setHeight(consgDO.getHeight());
			volWeightDtls.setLength(consgDO.getLength());
			volWeightDtls.setBreadth(consgDO.getBreath());
			consgTO.setVolWightDtls(volWeightDtls);
		}
		if (!StringUtil.isNull(consgDO.getConsignor())) {
			ConsignorConsigneeTO consignorTO = new ConsignorConsigneeTO();
			CGObjectConverter.createToFromDomain(consgDO.getConsignor(),
					consignorTO);
			consgTO.setConsignorTO(consignorTO);
		}
		if (!StringUtil.isNull(consgDO.getConsignee())) {
			ConsignorConsigneeTO consigneeTO = new ConsignorConsigneeTO();
			CGObjectConverter.createToFromDomain(consgDO.getConsignee(),
					consigneeTO);
			consgTO.setConsigneeTO(consigneeTO);
		}
		Set<ConsignmentBillingRateDO> consgRate = consgDO.getConsgRateDtls();
		if (!CGCollectionUtils.isEmpty(consgRate)) {
			CNPricingDetailsTO consgPriceDtls = setUpRateCompoments(consgRate);
			if (!StringUtil.isEmptyDouble(consgDO.getDeclaredValue()))
				consgPriceDtls.setDeclaredvalue(consgDO.getDeclaredValue());
			if (!StringUtil.isEmptyDouble(consgDO.getDiscount()))
				consgPriceDtls.setDiscount(consgDO.getDiscount());
			consgPriceDtls.setRateType(consgDO.getRateType());
			consgTO.setConsgPriceDtls(consgPriceDtls);
		}
		// set process details
		ProcessTO updatedProcessFrom = null;
		if (!StringUtil.isNull(consgDO.getUpdatedProcess())) {
			updatedProcessFrom = new ProcessTO();
			try {
				PropertyUtils.copyProperties(updatedProcessFrom,
						consgDO.getUpdatedProcess());
				consgTO.setUpdatedProcessFrom(updatedProcessFrom);
			} catch (Exception e) {
				throw new CGBusinessException("PPX0008");
			}
		}

	}

	/**
	 * Gets the consingment dtls.
	 * 
	 * @param consignmentTO
	 *            the consignment to
	 * @return the consingment dtls
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	@Override
	public List<ConsignmentTO> getConsingmentDtls(ConsignmentTO consignmentTO)
			throws CGBusinessException, CGSystemException {
		ConsignmentDO consgDO = null;
		List<ConsignmentTO> consgTOList = null;
		List<ConsignmentDO> consgDOList = null;
		try {
			consgDO = new ConsignmentDO();
			CGObjectConverter.createDomainFromTo(consignmentTO, consgDO);
			if (consignmentTO.getTypeTO() != null) {
				ConsignmentTypeDO consgType = new ConsignmentTypeDO();
				CGObjectConverter.createDomainFromTo(consignmentTO.getTypeTO(),
						consgType);
				consgDO.setConsgType(consgType);
			}

			consgDOList = consignmentCommonDAO.getConsingmentDtls(consgDO);
			if (!CGCollectionUtils.isEmpty(consgDOList)) {
				consgTOList = new ArrayList<>(consgDOList.size());

				for (ConsignmentDO consgDo : consgDOList) {
					ConsignmentTO consgTo = new ConsignmentTO();
					convertConsignmentDO2TO(consgDo, consgTo);
					consgTOList.add(consgTo);

				}

			}

		} catch (Exception e) {
			LOGGER.error("Error occured in ConsignmentCommonServiceImpl :: getConsingmentDtls()..:"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		return consgTOList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.consignment.service.ConsignmentCommonService#
	 * getConsignmentIdByConsgNo(com.ff.consignment.ConsignmentTO)
	 */
	@Override
	public Integer getConsignmentIdByConsgNo(ConsignmentTO consignmentTO)
			throws CGBusinessException, CGSystemException {
		return consignmentCommonDAO.getConsignmentIdByConsgNo(consignmentTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.consignment.service.ConsignmentCommonService#
	 * getConsignmentIdByConsgNo(com.ff.consignment.ConsignmentTO)
	 */
	@Override
	public Integer getChildConsgIdByConsgNo(String consgNumber)
			throws CGBusinessException, CGSystemException {
		return consignmentCommonDAO.getChildConsgIdByConsgNo(consgNumber);
	}

	@Override
	public Integer getConsgOperatingLevel(ConsignmentTO consgTO,
			OfficeTO loggedInOffice) throws CGBusinessException,
			CGSystemException {
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
			if (!StringUtil.isNull(destOfficeOnConsg)
					&& !StringUtil.isEmptyInteger(destOfficeOnConsg
							.getOfficeId())) {
				destOfficeOnConsg = organizationCommonService
						.getOfficeByIdOrCode(destOfficeOnConsg.getOfficeId(),
								CommonConstants.EMPTY_STRING);
				destReportingHubId = destOfficeOnConsg.getReportingHUB();
				destCityId = destOfficeOnConsg.getCityId();
			}
			if (!StringUtil.isNull(originOfficeOnConsg))
				reportingHubId = originOfficeOnConsg.getReportingHUB();
			else
				throw new CGBusinessException("Invalid Office");

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
			LOGGER.error("Error occured in ConsignmentCommonServiceImpl :: getConsgOperatingLevel()..:"
					+ e.getMessage());
			throw e;
		} catch (Exception e) {
			LOGGER.error("Error occured in ConsignmentCommonServiceImpl :: getConsgOperatingLevel()..:"
					+ e.getMessage());
			throw e;
		}
		return consgOpLevel;
	}

	@Override
	public boolean updateConsignmentStatus(String consignmentStatus,
			String processCode, List<String> consgNumbers)
			throws CGBusinessException, CGSystemException {
		return consignmentCommonDAO.updateConsignmentStatus(consignmentStatus,
				processCode, consgNumbers);
	}

	@Override
	public List<ConsignmentTO> getBookedConsignmentsByCustIdDateRange(
			final Integer customerId, Date startDate, Date endDate)
			throws CGBusinessException, CGSystemException {
		List<ConsignmentDO> consignmentDOs = consignmentCommonDAO
				.getBookedConsignmentsByCustIdDateRange(customerId, startDate,
						endDate);
		List<ConsignmentTO> consignmentTOs = convertConsignmentDOsToTOs(consignmentDOs);
		return consignmentTOs;
	}

	@Override
	public List<ConsignmentTO> getBookedTransferredConsignmentsByCustIdDateRange(
			final Integer customerId, Date startDate, Date endDate)
			throws CGBusinessException, CGSystemException {
		List<ConsignmentDO> consignmentDOs = consignmentCommonDAO
				.getBookedTransferredConsignmentsByCustIdDateRange(customerId,
						startDate, endDate);
		List<ConsignmentTO> consignmentTOs = convertConsignmentDOsToTOs(consignmentDOs);
		return consignmentTOs;
	}

	/**
	 * Convert consignment d os to t os.
	 * 
	 * @param consignmentDOs
	 *            the consignment d os
	 * @return the list
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	private List<ConsignmentTO> convertConsignmentDOsToTOs(
			List<ConsignmentDO> consignmentDOs) throws CGBusinessException {
		List<ConsignmentTO> consignmentTOs = null;
		if (!StringUtil.isEmptyColletion(consignmentDOs)) {
			consignmentTOs = new ArrayList<>(consignmentDOs.size());
			for (ConsignmentDO consignmentDO : consignmentDOs) {
				ConsignmentTO consignmentTO = setUpConsignmentDtls(consignmentDO);
				consignmentTOs.add(consignmentTO);
			}
		}
		return consignmentTOs;
	}

	@Override
	public List<ConsignmentDO> saveOrUpdateConsignments(
			List<ConsignmentDO> consignmentDOs) throws CGBusinessException,
			CGSystemException {
		return consignmentCommonDAO.saveOrUpdateConsignments(consignmentDOs);
	}

	public CNPricingDetailsTO setUpRateCompoments(
			Set<ConsignmentBillingRateDO> consgRates) {
		CNPricingDetailsTO consgRateDtls = null;
		for (ConsignmentBillingRateDO rateOutput : consgRates) {
			consgRateDtls = new CNPricingDetailsTO();
			if (StringUtils.equalsIgnoreCase("B",
					rateOutput.getRateCalculatedFor())) {
				consgRateDtls.setAirportHandlingChg(rateOutput
						.getAirportHandlingCharge());
				consgRateDtls.setFinalPrice(rateOutput
						.getGrandTotalIncludingTax());
				consgRateDtls.setCodAmt(rateOutput.getCodAmount());
				consgRateDtls.setFuelChg(rateOutput.getFuelSurcharge());
				consgRateDtls.setServiceTax(rateOutput.getServiceTax());
				consgRateDtls.setRiskSurChg(rateOutput.getRiskSurcharge());
				consgRateDtls.setTopayChg(rateOutput.gettOPayCharge());
				consgRateDtls.setEduCessChg(rateOutput.getEducationCess());
				consgRateDtls.setHigherEduCessChg(rateOutput
						.getHigherEducationCess());
				consgRateDtls.setFreightChg(rateOutput.getFinalSlabRate());
				consgRateDtls.setSplChg(rateOutput.getOtherOrSpecialCharge());
			}
		}
		return consgRateDtls;
	}

	@Override
	public void updateBillingFlagsInConsignment(ConsignmentDO consignmentDO,
			String updatedIn) throws CGBusinessException, CGSystemException {
		LOGGER.trace("ConsignmentCommonServiceImpl :: updateBillingFlagsInConsignment() :: START ::::");
		if (consignmentDO == null) {
			throw new CGBusinessException(
					CommonConstants.UPDATE_BILLING_FLAGS_ERROR_CONSIGNMENTDO_NULL);
		}
		if (StringUtils.isEmpty(updatedIn)) {
			throw new CGBusinessException(
					CommonConstants.UPDATE_BILLING_FLAGS_ERROR_UPDATEDIN);
		}
		Set<ConsignmentBillingRateDO> consgRateDtls = null;
		switch (updatedIn) {
		case CommonConstants.UPDATE_BILLING_FLAGS_CREATE_CN:
			consignmentDO.setBillingStatus(CommonConstants.BILLING_STATUS_TBB);
			consignmentDO.setChangedAfterBillingWtDest(CommonConstants.NO);
			consignmentDO.setChangedAfterNewRateCmpnt(CommonConstants.NO);
			consgRateDtls = consignmentDO.getConsgRateDtls();
			if (!CGCollectionUtils.isEmpty(consgRateDtls)) {
				for (ConsignmentBillingRateDO consignmentBillingRateDO : consgRateDtls) {
					if (!StringUtil.isNull(consignmentBillingRateDO
							.getRateCalculatedFor())
							&& consignmentBillingRateDO
									.getRateCalculatedFor()
									.equalsIgnoreCase(
											CommonConstants.RATE_CALCULATED_FOR_BOOKING)) {
						updateBillingFlagsInConsignmentRate(consignmentBillingRateDO);
					}
				}
			}
			break;
		case CommonConstants.UPDATE_BILLING_FOR_MEC:
			String consgNo = consignmentDO.getConsgNo();
			consignmentDO = consignmentCommonDAO.getConsingmentDtls(consgNo);
			if (!StringUtil.isNull(consignmentDO)
					&& !StringUtil.isNull(consignmentDO.getConsgType())
					&& !(consignmentDO.getConsgType().getConsignmentCode()
							.equalsIgnoreCase(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT))) {
				consignmentDO
						.setBillingStatus(CommonConstants.BILLING_STATUS_TBB);
				consignmentDO.setChangedAfterNewRateCmpnt(CommonConstants.YES);
				consignmentCommonDAO.updateConsignment(consignmentDO);
			}
			break;
		case CommonConstants.UPDATE_BILLING_FLAGS_FOR_RTO:
			consignmentDO.setBillingStatus(CommonConstants.BILLING_STATUS_TBB);
			consgRateDtls = consignmentDO.getConsgRateDtls();
			if (!CGCollectionUtils.isEmpty(consgRateDtls)) {
				for (ConsignmentBillingRateDO consignmentBillingRateDO : consgRateDtls) {
					if (!StringUtil.isNull(consignmentBillingRateDO
							.getRateCalculatedFor())
							&& consignmentBillingRateDO
									.getRateCalculatedFor()
									.equalsIgnoreCase(
											CommonConstants.RATE_CALCULATED_FOR_RTO)) {
						updateBillingFlagsInConsignmentRate(consignmentBillingRateDO);
					}
				}
			}
			break;
		case CommonConstants.UPDATE_BILLING_FLAGS_UPDATE_CN:
			if (!StringUtil.isNull(consignmentDO.getConsgType())
					&& !(consignmentDO.getConsgType().getConsignmentCode()
							.equalsIgnoreCase(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT))) {
				consignmentDO
						.setBillingStatus(CommonConstants.BILLING_STATUS_TBB);
				consignmentDO.setChangedAfterBillingWtDest(CommonConstants.YES);
				consgRateDtls = consignmentDO.getConsgRateDtls();
				if (!CGCollectionUtils.isEmpty(consgRateDtls)) {
					for (ConsignmentBillingRateDO consignmentBillingRateDO : consgRateDtls) {
						if (!StringUtil.isNull(consignmentBillingRateDO
								.getRateCalculatedFor())
								&& consignmentBillingRateDO
										.getRateCalculatedFor()
										.equalsIgnoreCase(
												CommonConstants.RATE_CALCULATED_FOR_BOOKING)) {
							updateBillingFlagsInConsignmentRate(consignmentBillingRateDO);
						}
					}
				}
			}
			break;
		}
		LOGGER.trace("ConsignmentCommonServiceImpl :: updateBillingFlagsInConsignment() :: END ::::");
	}

	@Override
	public void updateBillingFlagsInConsignmentRate(
			ConsignmentBillingRateDO consignmentBillingRateDO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("ConsignmentCommonServiceImpl :: updateBillingFlagsInConsignmentRate() :: START ::::");
		if (consignmentBillingRateDO == null) {
			throw new CGBusinessException(
					CommonConstants.UPDATE_BILLING_FLAGS_ERROR_CONSIGNMENTBILLINGRATEDO_NULL);
		}
		consignmentBillingRateDO.setBilled(CommonConstants.NO);
		LOGGER.trace("ConsignmentCommonServiceImpl :: updateBillingFlagsInConsignmentRate() :: END ::::");
	}

	@Override
	public ConsignmentDO getConsgDtlsByConsgNo(String consgNo)
			throws CGBusinessException, CGSystemException {
		return consignmentCommonDAO.getConsingmentDtls(consgNo);
	}

	@Override
	public ConsignmentDO getConsgDtlsByBookingRefNo(String bookingRefNo)
			throws CGBusinessException, CGSystemException {
		return consignmentCommonDAO.getConsgDtlsByBookingRefNo(bookingRefNo);
	}
	
	@Override
	public String getConsignorMobileNumberByConsgNo(String consgNo,String bookingRefNumber)
			throws CGBusinessException, CGSystemException {
		return consignmentCommonDAO.getConsignorMobileNumberByConsgNo(consgNo, bookingRefNumber);
	}
	@Override
	public String getConsigneeMobileNumberByConsgNo(String consgNo,String bookingRefNumber)
			throws CGBusinessException, CGSystemException {
		return consignmentCommonDAO.getConsigneeMobileNumberByConsgNo(consgNo, bookingRefNumber);
	}

	@Override
	public Date getConsignmentDeliveryDate(String consigNo)
			throws CGBusinessException, CGSystemException {
		return consignmentCommonDAO.getConsignmentDeliveryDate(consigNo);
	}
}
