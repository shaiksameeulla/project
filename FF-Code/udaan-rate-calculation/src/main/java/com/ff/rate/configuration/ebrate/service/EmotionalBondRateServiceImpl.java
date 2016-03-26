package com.ff.rate.configuration.ebrate.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingPreferenceDetailsTO;
import com.ff.domain.booking.BookingPreferenceDetailsDO;
import com.ff.domain.geography.StateDO;
import com.ff.domain.ratemanagement.masters.EBRateConfigDO;
import com.ff.domain.ratemanagement.masters.EBRatePreferenceDO;
import com.ff.rate.configuration.common.constants.RateErrorConstants;
import com.ff.rate.configuration.common.service.RateCommonService;
import com.ff.rate.configuration.ebrate.constants.EmotionalBondRateConstants;
import com.ff.rate.configuration.ebrate.dao.EmotionalBondRateDAO;
import com.ff.to.ratemanagement.operations.ratequotation.EBRateConfigTO;
import com.ff.to.ratemanagement.operations.ratequotation.EBRatePreferenceTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateTaxComponentTO;

public class EmotionalBondRateServiceImpl implements EmotionalBondRateService {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(EmotionalBondRateServiceImpl.class);

	private EmotionalBondRateDAO emotionalBondRateDAO;
	private RateCommonService rateCommonService;

	public EmotionalBondRateDAO getEmotionalBondRateDAO() {
		return emotionalBondRateDAO;
	}

	public void setEmotionalBondRateDAO(
			EmotionalBondRateDAO emotionalBondRateDAO) {
		this.emotionalBondRateDAO = emotionalBondRateDAO;
	}

	public RateCommonService getRateCommonService() {
		return rateCommonService;
	}

	public void setRateCommonService(RateCommonService rateCommonService) {
		this.rateCommonService = rateCommonService;
	}

	@Override
	public Boolean deactivatePreferences(List<Integer> prefIds)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("EmotionalBondRateServiceImpl::deactivatePreferences::START------------>:::::::");
		Boolean transStatus = Boolean.FALSE;
		transStatus = emotionalBondRateDAO.deactivatePreferences(prefIds);
		LOGGER.trace("EmotionalBondRateServiceImpl::deactivatePreferences::END------------>:::::::");
		return transStatus;
	}

	@Override
	public List<RateTaxComponentTO> loadDefaultTaxComponent(String stateId)
			throws CGBusinessException, CGSystemException {
		List<RateTaxComponentTO> taxComponentTOs = null;
		try {
			LOGGER.trace("EmotionalBondRateServiceImpl::loadDefaultTaxComponent::START------------>:::::::");
			if (StringUtil.isStringEmpty(stateId)) {
				taxComponentTOs = rateCommonService
						.loadDefaultRateTaxComponentValue(null);
			} else {
				taxComponentTOs = rateCommonService
						.loadDefaultRateTaxComponentValue(Integer
								.parseInt(stateId));
			}

		} catch (Exception e) {
			LOGGER.error("Error occured in EmotionalBondRateServiceImpl :: loadDefaultTaxComponent()..:"
					+ e.getMessage());
			throw e;
		}
		LOGGER.trace("EmotionalBondRateServiceImpl::loadDefaultTaxComponent::END------------>:::::::");
		return taxComponentTOs;
	}

	@Override
	public boolean saveOrUpdateEBRate(EBRateConfigTO ebRateConfigTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("EmotionalBondRateServiceImpl::saveOrUpdateEBRate::START------------>:::::::");
		EBRateConfigDO ebRateConfigDO = new EBRateConfigDO();
		boolean isSaved = Boolean.FALSE;
		try {
			preferencesDetailsList(ebRateConfigTO, ebRateConfigDO);

			if (!StringUtil.isNull(ebRateConfigTO.getCurrentEBRateConfigId())
					&& (ebRateConfigTO.getIsRenew().equals(CommonConstants.YES))
					&& (ebRateConfigTO.getSaveMode()
							.equals(EmotionalBondRateConstants.SUBMITED))) {
				try {

					String dateStr = ebRateConfigTO.getValidFromDateStr();
					SimpleDateFormat sdf = new SimpleDateFormat(
							FrameworkConstants.DDMMYYYY_SLASH_FORMAT);
					Calendar c = Calendar.getInstance();
					c.setTime(sdf.parse(dateStr));
					c.add(Calendar.DATE, -1); // number of days to minus
					dateStr = sdf.format(c.getTime());

					Date date = DateUtil.getDateFromString(dateStr,
							FrameworkConstants.DDMMYYYY_SLASH_FORMAT);
					emotionalBondRateDAO.updateEBRateTODate(
							ebRateConfigTO.getCurrentEBRateConfigId(), date);
				} catch (Exception e) {
					LOGGER.error("EmotionalBondRateServiceImpl :: saveOrUpdateEBRate()::::::"
							+ e.getMessage());
					throw e;
				}
			}

			if (!StringUtil.isNull(ebRateConfigDO)) {
				ebRateConfigDO = emotionalBondRateDAO
						.saveOrUpdateEBRate(ebRateConfigDO);
				isSaved = Boolean.TRUE;
			}

			if (!StringUtil.isNull(ebRateConfigDO)) {

				ebRateConfigTO.setEbRateConfigId(ebRateConfigDO
						.getEbRateConfigId());
				ebRateConfigTO.setStatus(ebRateConfigDO.getStatus());
				int i = 0;
				Integer[] rateId = new Integer[ebRateConfigDO
						.getPreferenceDOSet().size()];
				for (EBRatePreferenceDO preferenceDO : ebRateConfigDO
						.getPreferenceDOSet()) {
					rateId[i++] = (preferenceDO.getEbRatePrefId());
				}
				ebRateConfigTO.setEbPrefRateId(rateId);
				int j = 0;
				Integer[] ratePrefId = new Integer[ebRateConfigDO
						.getPreferenceDOSet().size()];
				for (EBRatePreferenceDO preferenceDO : ebRateConfigDO
						.getPreferenceDOSet()) {
					ratePrefId[j++] = (preferenceDO
							.getBookingPreferenceDetailsDO().getPreferenceId());
				}
				ebRateConfigTO.setPrefId(ratePrefId);
			}
		} catch (Exception e) {
			LOGGER.error("EmotionalBondRateServiceImpl :: saveOrUpdateEBRate()::::::"
					+ e.getMessage());
		}

		LOGGER.trace("EmotionalBondRateServiceImpl::saveOrUpdateEBRate::END------------>:::::::");
		return isSaved;

	}

	private String getPrefCode(EBRateConfigTO ebRateConfigTO,
			List<String> seqNOs) {
		LOGGER.trace("EmotionalBondRateServiceImpl::getPrefCode::START------------>:::::::");
		String seqNo = EmotionalBondRateConstants.EB_RATE_PREF + seqNOs.get(0);
		LOGGER.trace("EmotionalBondRateServiceImpl::getPrefCode::END------------>:::::::");
		return seqNo;

	}

	public List<String> generateSequenceNo(Integer noOfSeq, String process)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("EmotionalBondRateServiceImpl::generateSequenceNo::START------------>:::::::");
		List<String> sequenceNumber = null;
		SequenceGeneratorConfigTO sequenceGeneratorConfigTO = new SequenceGeneratorConfigTO();
		sequenceGeneratorConfigTO.setProcessRequesting(process);
		sequenceGeneratorConfigTO.setNoOfSequencesToBegenerated(noOfSeq);
		sequenceGeneratorConfigTO.setRequestDate(new Date());
		sequenceGeneratorConfigTO = rateCommonService
				.getGeneratedSequence(sequenceGeneratorConfigTO);
		sequenceNumber = sequenceGeneratorConfigTO.getGeneratedSequences();
		LOGGER.trace("EmotionalBondRateServiceImpl::generateSequenceNo::END------------>:::::::");
		return sequenceNumber;
	}

	private void preferencesDetailsList(EBRateConfigTO ebRateConfigTO,
			EBRateConfigDO ebRateConfigDO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("EmotionalBondRateServiceImpl::preferencesDetailsList::START------------>:::::::");
		Set<EBRatePreferenceDO> preferenceDOs = new HashSet<EBRatePreferenceDO>();
		if (!StringUtil.isNull(ebRateConfigTO)) {
			int consgLen=ebRateConfigTO.getPrefNames().length;
			if (ebRateConfigTO.getPrefNames() != null
					&& consgLen > 0) {
				for (int rowCount = 0; rowCount < consgLen; rowCount++) {
					EBRatePreferenceDO preferenceDO = new EBRatePreferenceDO();
					BookingPreferenceDetailsDO bookingPreferenceDetailsDO = new BookingPreferenceDetailsDO();
					if (StringUtils
							.isNotEmpty(ebRateConfigTO.getPrefNames()[rowCount])) {

						if (StringUtil.isStringEmpty(ebRateConfigTO
								.getPrefCodes()[rowCount])) {
							List<String> seqNOs = generateSequenceNo(
									EmotionalBondRateConstants.ONE,
									EmotionalBondRateConstants.EMOTIONAL_BOND_PREF_CODE);
							String prefCode = getPrefCode(ebRateConfigTO,
									seqNOs);
							ebRateConfigTO.getPrefCodes()[rowCount] = prefCode;
						}

						bookingPreferenceDetailsDO
								.setPreferenceCode(ebRateConfigTO
										.getPrefCodes()[rowCount]);

						if (!StringUtil.isEmptyInteger(ebRateConfigTO
								.getPrefId()[rowCount])) {
							bookingPreferenceDetailsDO
									.setPreferenceId(ebRateConfigTO.getPrefId()[rowCount]);
						}

						if (ebRateConfigTO.getPrefNames() != null
								&& StringUtils.isNotEmpty(ebRateConfigTO
										.getPrefNames()[rowCount])
								&& ebRateConfigTO.getPrefNames().length > 0) {
							bookingPreferenceDetailsDO
									.setPreferenceName(ebRateConfigTO
											.getPrefNames()[rowCount]);
						}

						if (ebRateConfigTO.getPrefDescription() != null
								&& StringUtils.isNotEmpty(ebRateConfigTO
										.getPrefDescription()[rowCount])
								&& ebRateConfigTO.getPrefDescription().length > 0) {
							bookingPreferenceDetailsDO
									.setDescription(ebRateConfigTO
											.getPrefDescription()[rowCount]);
						}

						if (ebRateConfigTO.getAmount() != null
								&& !StringUtil.isEmptyDouble(ebRateConfigTO
										.getAmount()[rowCount])
								&& ebRateConfigTO.getAmount().length > 0) {
							preferenceDO
									.setRate(ebRateConfigTO.getAmount()[rowCount]);
						}

						if (ebRateConfigTO.getEbPrefRateId() != null
								&& !StringUtil.isEmptyInteger(ebRateConfigTO
										.getEbPrefRateId()[rowCount])
								&& ebRateConfigTO.getEbPrefRateId().length > 0) {
							preferenceDO.setEbRatePrefId(ebRateConfigTO
									.getEbPrefRateId()[rowCount]);
						}

						bookingPreferenceDetailsDO
								.setStatus(EmotionalBondRateConstants.ACTIVE);

						preferenceDO
								.setBookingPreferenceDetailsDO(bookingPreferenceDetailsDO);

						preferenceDO.setEbRateConfigDO(ebRateConfigDO);
						preferenceDO.setApplicability(CommonConstants.YES);

						preferenceDOs.add(preferenceDO);
					}
				}

				ebRateConfigDO.setPreferenceDOSet(preferenceDOs);
			}

			if (!StringUtil.isEmptyInteger(ebRateConfigTO.getOriginState())) {
				StateDO stateDO = new StateDO();
				stateDO.setStateId(ebRateConfigTO.getOriginState());
				ebRateConfigDO.setOriginState(stateDO);
			}else if(!StringUtil.isEmptyInteger(ebRateConfigTO.getCurStateId())){
				StateDO stateDO = new StateDO();
				stateDO.setStateId(ebRateConfigTO.getCurStateId());
				ebRateConfigDO.setOriginState(stateDO);
			}

			ebRateConfigDO.setValidFromDate(DateUtil.stringToDateFormatter(
					ebRateConfigTO.getValidFromDateStr(),
					FrameworkConstants.DDMMYYYY_SLASH_FORMAT));

			ebRateConfigDO.setStatus(ebRateConfigTO.getSaveMode());
			
			if(!StringUtil.isStringEmpty(ebRateConfigTO.getSaveMode()) && ebRateConfigTO.getSaveMode().equalsIgnoreCase(EmotionalBondRateConstants.CREATED)){
				ebRateConfigDO.setDtToBranch(CommonConstants.YES);
			}
			else if(!StringUtil.isStringEmpty(ebRateConfigTO.getSaveMode()) &&  ebRateConfigTO.getSaveMode().equalsIgnoreCase(EmotionalBondRateConstants.SUBMITED)){
				ebRateConfigDO.setDtToBranch(CommonConstants.NO);
			}else{
				ebRateConfigDO.setDtToBranch(CommonConstants.YES);
			}

			if (!(StringUtil.isNull(ebRateConfigTO.getEbRateConfigId()))) {
				ebRateConfigDO.setEbRateConfigId(ebRateConfigTO
						.getEbRateConfigId());

			}

			if (!(StringUtil.isNull(ebRateConfigTO.getStateTaxApplicable()))) {
				if (ebRateConfigTO.getStateTaxApplicable().equals("on")) {
					ebRateConfigDO.setStateTaxApplicable(CommonConstants.YES);
				} else {
					ebRateConfigDO.setStateTaxApplicable(CommonConstants.NO);
				}
			}
			if (!(StringUtil
					.isNull(ebRateConfigTO.getSurchargeOnSTApplicable()))) {
				if (ebRateConfigTO.getSurchargeOnSTApplicable().equals("on")) {
					ebRateConfigDO
							.setSurchargeSTtaxApplicable(CommonConstants.YES);
				} else {
					ebRateConfigDO
							.setSurchargeSTtaxApplicable(CommonConstants.NO);
				}
			}
			if (!(StringUtil.isNull(ebRateConfigTO.getCessTaxApplicable()))) {
				if (ebRateConfigTO.getCessTaxApplicable().equals("on")) {
					ebRateConfigDO.setCessTaxApplicable(CommonConstants.YES);
				} else {
					ebRateConfigDO.setCessTaxApplicable(CommonConstants.NO);
				}
			}
			if (!(StringUtil.isNull(ebRateConfigTO.getHcesstaxApplicable()))) {
				if (ebRateConfigTO.getHcesstaxApplicable().equals("on")) {
					ebRateConfigDO.setHcesstaxApplicable(CommonConstants.YES);
				} else {
					ebRateConfigDO.setHcesstaxApplicable(CommonConstants.NO);
				}
			}
			if (!(StringUtil.isNull(ebRateConfigTO.getServiceTaxApplicable()))) {
				if (ebRateConfigTO.getServiceTaxApplicable().equals("on")) {
					ebRateConfigDO.setServiceTaxApplicable(CommonConstants.YES);
				} else {
					ebRateConfigDO.setServiceTaxApplicable(CommonConstants.NO);
				}
			}

		}
		LOGGER.trace("EmotionalBondRateServiceImpl::preferencesDetailsList::END------------>:::::::");
	}

	@Override
	public EBRateConfigTO loadDefaultEBRates(String stateId, String action,
			String ebRateConfigId) throws CGBusinessException,
			CGSystemException {
		EBRateConfigTO ebRateConfigTO = new EBRateConfigTO();
		List<EBRateConfigDO> ebRateConfigDOs = null;
		EBRateConfigDO ebRateConfigDO = new EBRateConfigDO();

		try {
			LOGGER.trace("EmotionalBondRateServiceImpl::loadDefaultEBRates::START------------>:::::::");
			if (StringUtil.isStringEmpty(stateId)) {
				ebRateConfigDOs = emotionalBondRateDAO.loadDefaultEBRates(null);
			} else {
				ebRateConfigDOs = emotionalBondRateDAO
						.loadDefaultEBRates(Integer.parseInt(stateId));
			}

			if (!StringUtil.isNull(action)
					&& action
							.equalsIgnoreCase(EmotionalBondRateConstants.RENEWED)) {
				ebRateConfigDO = emotionalBondRateDAO
						.isRateRenewed(ebRateConfigDOs);
				if (StringUtil.isNull(ebRateConfigDO)) {
					if (!CGCollectionUtils.isEmpty(ebRateConfigDOs)) {
						if (!StringUtil.isNull(ebRateConfigId)) {
							for (int i = 0; i < ebRateConfigDOs.size(); i++) {
								if (ebRateConfigDOs.get(i).getEbRateConfigId() == Integer.parseInt(ebRateConfigId)) {
									ebRateConfigDO = ebRateConfigDOs.get(i);
									break;
								}
							}
							Set<EBRatePreferenceDO> ebrate=ebRateConfigDO.getPreferenceDOSet();
							Set<EBRatePreferenceDO> newEbRateSet = new HashSet<EBRatePreferenceDO>();
							
							for (EBRatePreferenceDO eb:ebrate) {
								eb.setEbRatePrefId(null);
								eb.setEbRateConfigDO(ebRateConfigDO);
								newEbRateSet.add(eb);
							}
							
							ebRateConfigDO.setValidFromDate(DateUtil.getFutureDate(1));
							ebRateConfigDO.setPreferenceDOSet(newEbRateSet);
							ebRateConfigDO.setEbRateConfigId(null);
							ebRateConfigDO.setStatus(EmotionalBondRateConstants.CREATED);
						ebRateConfigDO = emotionalBondRateDAO
									.saveOrUpdateEBRate(ebRateConfigDO);
						}
					} else {
						throw new CGBusinessException(
								RateErrorConstants.EB_RATE_NOT_CONFIGURED);
					}
				}
			} else {

				if (!CGCollectionUtils.isEmpty(ebRateConfigDOs)) {
					if (ebRateConfigDOs.size() == 1) {
						ebRateConfigDO = ebRateConfigDOs.get(0);

					} else if ((!StringUtil.isNull(ebRateConfigDOs.get(1)
							.getValidToDate()))
							&& (ebRateConfigDOs
									.get(1)
									.getValidToDate()
									.compareTo(
											DateUtil.stringToDDMMYYYYFormat(DateUtil
													.getCurrentDateInYYYYMMDDHHMM())) < 0)
							&& (ebRateConfigDOs.get(0).getStatus()
									.equals(EmotionalBondRateConstants.SUBMITED))) {
						ebRateConfigDO = ebRateConfigDOs.get(0);
					} else {
						ebRateConfigDO = ebRateConfigDOs.get(1);
					}

				} else {
					throw new CGBusinessException(
							RateErrorConstants.EB_RATE_NOT_CONFIGURED);
				}
			}

			if (!StringUtil.isNull(ebRateConfigDO)) {
				ebRateConfigTO = defaultEBRatesDomainConverter(ebRateConfigDO);
			} else {
				throw new CGBusinessException(
						RateErrorConstants.EB_RATE_NOT_CONFIGURED);
			}

		} catch (CGBusinessException ex) {
			LOGGER.error("Error occured in EmotionalBondRateServiceImpl :: loadDefaultEBRates()..:"
					+ ex.getMessage());
			throw ex;
		}
		LOGGER.trace("EmotionalBondRateServiceImpl::loadDefaultEBRates::END------------>:::::::");
		return ebRateConfigTO;
	}

	private EBRateConfigTO defaultEBRatesDomainConverter(
			EBRateConfigDO ebRateConfigDO) {
		LOGGER.trace("EmotionalBondRateServiceImpl::defaultEBRatesDomainConverter::START------------>:::::::");
		EBRateConfigTO ebRateConfigTO = new EBRateConfigTO();
		List<EBRatePreferenceTO> preferenceTOList = new ArrayList<EBRatePreferenceTO>();

		if (!StringUtil.isNull(ebRateConfigDO.getEbRateConfigId())) {
			ebRateConfigTO
					.setEbRateConfigId(ebRateConfigDO.getEbRateConfigId());
		}

		if (!StringUtil.isNull(ebRateConfigDO.getOriginState())) {

			ebRateConfigTO.setOriginState(ebRateConfigDO.getOriginState()
					.getStateId());
			ebRateConfigTO.setCurStateId(ebRateConfigDO.getOriginState()
					.getStateId());
		}

		if (!StringUtil.isNull(ebRateConfigDO.getServiceTaxApplicable())) {
			ebRateConfigTO.setServiceTaxApplicable(ebRateConfigDO
					.getServiceTaxApplicable());
		}
		if (!StringUtil.isNull(ebRateConfigDO.getCessTaxApplicable())) {
			ebRateConfigTO.setCessTaxApplicable(ebRateConfigDO
					.getCessTaxApplicable());
		}
		if (!StringUtil.isNull(ebRateConfigDO.getStateTaxApplicable())) {
			ebRateConfigTO.setStateTaxApplicable(ebRateConfigDO
					.getStateTaxApplicable());
		}
		if (!StringUtil.isNull(ebRateConfigDO.getHcesstaxApplicable())) {
			ebRateConfigTO.setHcesstaxApplicable(ebRateConfigDO
					.getHcesstaxApplicable());
		}
		if (!StringUtil.isNull(ebRateConfigDO.getSurchargeSTtaxApplicable())) {
			ebRateConfigTO.setSurchargeOnSTApplicable(ebRateConfigDO
					.getSurchargeSTtaxApplicable());
		}
		if (!StringUtil.isNull(ebRateConfigDO.getValidFromDate())) {

			ebRateConfigTO.setValidFromDateStr(DateUtil.getDDMMYYYYDateString(
					ebRateConfigDO.getValidFromDate(),
					FrameworkConstants.DDMMYYYY_SLASH_FORMAT));
		}
		if (!StringUtil.isNull(ebRateConfigDO.getStatus())) {
			ebRateConfigTO.setStatus(ebRateConfigDO.getStatus());
		}

		for (EBRatePreferenceDO ebRatePreferenceDO : ebRateConfigDO
				.getPreferenceDOSet()) {
			if (!StringUtil.isNull(ebRatePreferenceDO.getApplicability())
					&& ebRatePreferenceDO.getApplicability().equals(
							CommonConstants.YES)) {

				BookingPreferenceDetailsTO detailsTO = new BookingPreferenceDetailsTO();
				EBRatePreferenceTO preferenceTO = new EBRatePreferenceTO();
				preferenceTO.setEbRatePrefId(ebRatePreferenceDO
						.getEbRatePrefId());
				preferenceTO.setApplicability(ebRatePreferenceDO
						.getApplicability());
				preferenceTO.setRate(ebRatePreferenceDO.getRate());
				detailsTO.setPreferenceId(ebRatePreferenceDO
						.getBookingPreferenceDetailsDO().getPreferenceId());
				detailsTO.setPreferenceCode(ebRatePreferenceDO
						.getBookingPreferenceDetailsDO().getPreferenceCode());
				detailsTO.setPreferenceName(ebRatePreferenceDO
						.getBookingPreferenceDetailsDO().getPreferenceName());
				detailsTO.setDescription(ebRatePreferenceDO
						.getBookingPreferenceDetailsDO().getDescription());
				detailsTO.setStatus(ebRatePreferenceDO
						.getBookingPreferenceDetailsDO().getStatus());

				preferenceTO.setPreferenceDetailsTO(detailsTO);

				preferenceTOList.add(preferenceTO);

			}
			Collections.sort(preferenceTOList);
		}

		ebRateConfigTO.setPreferenceTOs(preferenceTOList);
		LOGGER.trace("EmotionalBondRateServiceImpl::defaultEBRatesDomainConverter::END------------>:::::::");
		return ebRateConfigTO;
	}

}
