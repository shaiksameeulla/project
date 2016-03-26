package com.ff.rate.configuration.rateConfiguration.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.ratemanagement.operations.ba.BAMaterialRateConfigDO;
import com.ff.domain.ratemanagement.operations.ba.BAMaterialRateDetailsDO;
import com.ff.domain.stockmanagement.masters.ItemDO;
import com.ff.domain.stockmanagement.masters.ItemTypeDO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.rate.configuration.rateConfiguration.dao.BAMaterialRateConfigDAO;
import com.ff.to.ratemanagement.operations.rateconfiguration.BAMaterialRateConfigTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.BAMaterialRateDetailsTO;
import com.ff.to.stockmanagement.masters.ItemTO;
import com.ff.to.stockmanagement.masters.ItemTypeTO;

/**
 * @author hkansagr
 */

public class BAMaterialRateConfigServiceImpl implements
		BAMaterialRateConfigService {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BAMaterialRateConfigServiceImpl.class);

	/** The baMaterialRateConfigDAO */
	private BAMaterialRateConfigDAO baMaterialRateConfigDAO;

	/**
	 * @param baMaterialRateConfigDAO
	 *            the baMaterialRateConfigDAO to set
	 */
	public void setBaMaterialRateConfigDAO(
			BAMaterialRateConfigDAO baMaterialRateConfigDAO) {
		this.baMaterialRateConfigDAO = baMaterialRateConfigDAO;
	}

	@Override
	public boolean saveBAMaterialRateDtls(BAMaterialRateConfigTO to)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BAMaterialRateConfigServiceImpl::saveBAMaterialRateDtls()::START");
		boolean result = Boolean.FALSE;
		BAMaterialRateConfigDO domain = new BAMaterialRateConfigDO();
		try {
			convertBAMtrlRateConfigTO2DO(to, domain);
			result = baMaterialRateConfigDAO.saveBAMaterialRateDtls(domain);
			if (result) {
				/* To update previous tariff valid to date */
				if (!StringUtil.isEmptyLong(to.getPrevBAMtrlRateId())) {
					/* To decrease date by 1 day */
					String toDtStr = decreaseDateByOne(to.getFromDateStr());
					to.setToDate(DateUtil
							.slashDelimitedstringToDDMMYYYYFormat(toDtStr));
					boolean res2 = baMaterialRateConfigDAO
							.updateValidToDate(to);
					if (!res2) {
						throw new CGBusinessException(
								RateCommonConstants.ERR_VALID_TO_DATE_CAN_NOT_UPDATED);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurs in BAMaterialRateConfigServiceImpl::saveBAMaterialRateDtls()::"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("BAMaterialRateConfigServiceImpl::saveBAMaterialRateDtls()::END");
		return result;
	}

	/**
	 * To convert BAMaterialRateConfigTO to BAMaterialRateConfigDO
	 * 
	 * @param to
	 * @param domain
	 * @throws Exception
	 */
	private void convertBAMtrlRateConfigTO2DO(BAMaterialRateConfigTO to,
			BAMaterialRateConfigDO domain) {
		LOGGER.trace("BAMaterialRateConfigServiceImpl::convertBAMtrlRateConfigTO2DO()::START");
		/* Valid from date */
		if (!StringUtil.isStringEmpty(to.getFromDateStr())) {
			domain.setValidFromDate(DateUtil
					.slashDelimitedstringToDDMMYYYYFormat(to.getFromDateStr()));
		}
		/* Logged In Office */
		if (!StringUtil.isEmptyInteger(to.getLoggedInOfficeId())) {
			domain.setCreatedOfficeId(to.getLoggedInOfficeId());
		}
		/* Created By */
		if (!StringUtil.isEmptyInteger(to.getCreatedBy())) {
			domain.setCreatedBy(to.getCreatedBy());
		}
		/* Updated By */
		if (!StringUtil.isEmptyInteger(to.getUpdatedBy())) {
			domain.setUpdatedBy(to.getUpdatedBy());
		}

		// Setting Created Date & Updated Date
		domain.setCreatedDate(Calendar.getInstance().getTime());
		domain.setUpdatedDate(Calendar.getInstance().getTime());

		/* Prepare BAMaterialRateDetailsDO set */
		int size = to.getItemTypeIds().length;
		Set<BAMaterialRateDetailsDO> baMtrlRateDtlsDOSet = new HashSet<BAMaterialRateDetailsDO>(
				size);
		for (int i = 0; i < size; i++) {
			if (!StringUtil.isEmptyInteger(to.getItemIds()[i])) {
				BAMaterialRateDetailsDO baMtrlRateDtlsDO = new BAMaterialRateDetailsDO();
				/* BA Material Rate Config */
				baMtrlRateDtlsDO.setBaMaterialRateConfigDO(domain);
				/* Item */
				if (!StringUtil.isEmptyInteger(to.getItemIds()[i])) {
					ItemDO itemDO = new ItemDO();
					itemDO.setItemId(to.getItemIds()[i]);
					baMtrlRateDtlsDO.setItemDO(itemDO);
				}
				/* Rate per unit */
				if (!StringUtil.isEmptyDouble(to.getAmounts()[i])) {
					baMtrlRateDtlsDO.setRatePerUnit(to.getAmounts()[i]);
				}
				/* Row number */
				if (!StringUtil.isEmptyInteger(to.getRowNumber()[i])) {
					baMtrlRateDtlsDO.setRowNumber(to.getRowNumber()[i]);
				}

				// Setting Created Date & Updated Date
				baMtrlRateDtlsDO.setCreatedDate(Calendar.getInstance()
						.getTime());
				baMtrlRateDtlsDO.setUpdatedDate(Calendar.getInstance()
						.getTime());

				baMtrlRateDtlsDOSet.add(baMtrlRateDtlsDO);
			}/* END IF */
		}/* END FOR LOOP */
		domain.setBaMaterialRateDtls(baMtrlRateDtlsDOSet);
		LOGGER.trace("BAMaterialRateConfigServiceImpl::convertBAMtrlRateConfigTO2DO()::END");
	}

	@Override
	public BAMaterialRateConfigTO searchBAMaterialRateDtls(
			BAMaterialRateConfigTO to) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("BAMaterialRateConfigServiceImpl::searchBAMaterialRateDtls()::START");
		try {
			BAMaterialRateConfigDO baMtrlRateConfigDO = baMaterialRateConfigDAO
					.searchBAMaterialRateDtls(to);
			if (!StringUtil.isNull(baMtrlRateConfigDO)) {
				convertBAMtrlRateConfigDO2TO(baMtrlRateConfigDO, to);
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurs in BAMaterialRateConfigServiceImpl::searchBAMaterialRateDtls()::"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("BAMaterialRateConfigServiceImpl::searchBAMaterialRateDtls()::END");
		return to;
	}

	/**
	 * To convert BAMaterialRateConfigDO to BAMaterialRateConfigTO
	 * 
	 * @param baMtrlRateConfigDO
	 * @param baMtrlRateConfigTO
	 * @throws Exception
	 */
	private void convertBAMtrlRateConfigDO2TO(
			BAMaterialRateConfigDO baMtrlRateConfigDO,
			BAMaterialRateConfigTO baMtrlRateConfigTO) throws Exception {
		LOGGER.trace("BAMaterialRateConfigServiceImpl::convertBAMtrlRateConfigDO2TO()::START");
		/* BA Material Rate Config Id. */
		if (!StringUtil.isEmptyLong(baMtrlRateConfigDO.getBaMaterialRateId())) {
			baMtrlRateConfigTO.setBaMaterialRateId(baMtrlRateConfigDO
					.getBaMaterialRateId());
		}
		/* Valid from date */
		if (!StringUtil.isNull(baMtrlRateConfigDO.getValidFromDate())) {
			baMtrlRateConfigTO.setFromDateStr(DateUtil
					.getDDMMYYYYDateToString(baMtrlRateConfigDO
							.getValidFromDate()));
		}
		/* Valid to date */
		if (!StringUtil.isNull(baMtrlRateConfigDO.getValidToDate())) {
			baMtrlRateConfigTO.setToDateStr(DateUtil
					.getDDMMYYYYDateToString(baMtrlRateConfigDO
							.getValidToDate()));
		}
		/* Created Office */
		if (!StringUtil.isEmptyInteger(baMtrlRateConfigDO.getCreatedOfficeId())) {
			baMtrlRateConfigTO.setLoggedInOfficeId(baMtrlRateConfigDO
					.getCreatedOfficeId());
		}
		/* Created By */
		if (!StringUtil.isEmptyInteger(baMtrlRateConfigDO.getCreatedBy())) {
			baMtrlRateConfigTO.setCreatedBy(baMtrlRateConfigDO.getCreatedBy());
		}
		/* Updated By */
		if (!StringUtil.isEmptyInteger(baMtrlRateConfigDO.getUpdatedBy())) {
			baMtrlRateConfigTO.setUpdatedBy(baMtrlRateConfigDO.getUpdatedBy());
		}
		/* Prepare BAMaterialRateDetailsTO List from BAMaterialRateDetailsDO Set */
		if (!StringUtil.isEmptyColletion(baMtrlRateConfigDO
				.getBaMaterialRateDtls())) {
			int size = baMtrlRateConfigDO.getBaMaterialRateDtls().size();
			List<BAMaterialRateDetailsTO> baMtrlRateDtlsTOs = new ArrayList<BAMaterialRateDetailsTO>(
					size);
			convertBAMtrlRateConfigDOSet2TOList(
					baMtrlRateConfigDO.getBaMaterialRateDtls(),
					baMtrlRateDtlsTOs);
			if (!StringUtil.isEmptyInteger(baMtrlRateDtlsTOs.size())) {
				Collections.sort(baMtrlRateDtlsTOs);
				baMtrlRateConfigTO.setBaMtrlDtlsTOList(baMtrlRateDtlsTOs);
			}
		}
		LOGGER.trace("BAMaterialRateConfigServiceImpl::convertBAMtrlRateConfigDO2TO()::END");
	}

	/**
	 * To convert BAMaterialRateDetailsDO Set to BAMaterialRateDetailsTO List
	 * 
	 * @param baMtrlRateDtlsDOs
	 * @param baMtrlRateDtlsTOs
	 * @throws Exception
	 */
	private void convertBAMtrlRateConfigDOSet2TOList(
			Set<BAMaterialRateDetailsDO> baMtrlRateDtlsDOs,
			List<BAMaterialRateDetailsTO> baMtrlRateDtlsTOs) throws Exception {
		LOGGER.trace("BAMaterialRateConfigServiceImpl::convertBAMtrlRateConfigDOSet2TOList()::START");
		for (BAMaterialRateDetailsDO baMtrlRateDtlsDO : baMtrlRateDtlsDOs) {
			BAMaterialRateDetailsTO baMtrlRateDtlsTO = new BAMaterialRateDetailsTO();
			/* Copy all common attributes */
			PropertyUtils.copyProperties(baMtrlRateDtlsTO, baMtrlRateDtlsDO);

			/* BA Material Rate Config DO */
			if (!StringUtil
					.isNull(baMtrlRateDtlsDO.getBaMaterialRateConfigDO())) {
				baMtrlRateDtlsTO.setBaMaterialRateConfigId(baMtrlRateDtlsDO
						.getBaMaterialRateConfigDO().getBaMaterialRateId());
			}
			/* Item */
			ItemDO itemDO = baMtrlRateDtlsDO.getItemDO();
			ItemTO itemTO = new ItemTO();
			PropertyUtils.copyProperties(itemTO, itemDO);
			/* Item Type */
			ItemTypeDO itemTypeDO = itemDO.getItemTypeDO();
			ItemTypeTO itemTypeTO = new ItemTypeTO();
			PropertyUtils.copyProperties(itemTypeTO, itemTypeDO);

			itemTO.setItemTypeTO(itemTypeTO);
			baMtrlRateDtlsTO.setItemTO(itemTO);

			baMtrlRateDtlsTOs.add(baMtrlRateDtlsTO);
		}/* END FOR LOOP */
		LOGGER.trace("BAMaterialRateConfigServiceImpl::convertBAMtrlRateConfigDOSet2TOList()::END");
	}

	/**
	 * To decrease date by one
	 * 
	 * @param fromDt
	 * @return String - toDtStr
	 * @throws Exception
	 */
	private String decreaseDateByOne(String fromDt) {
		LOGGER.trace("BAMaterialRateConfigServiceImpl::decreaseDateByOne()::START");
		String toDtStr = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(fromDt));
			c.add(Calendar.DATE, -1);/* Number of days to reduce */
			toDtStr = sdf.format(c.getTime());/* toDtStr is now the new date */
		} catch (ParseException e) {
			LOGGER.error("Exception occurs in BAMaterialRateConfigServiceImpl::decreaseDateByOne()::"
					+ e.getMessage());
		}
		LOGGER.trace("BAMaterialRateConfigServiceImpl::decreaseDateByOne()::END");
		return toDtStr;
	}

	@Override
	public BAMaterialRateConfigTO searchRenewedBAMaterialRateDtls(
			BAMaterialRateConfigTO to) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("BAMaterialRateConfigServiceImpl::searchRenewedBAMaterialRateDtls()::START");
		try {
			BAMaterialRateConfigDO baMtrlRateConfigDO = baMaterialRateConfigDAO
					.searchRenewedBAMaterialRateDtls(to);
			if (!StringUtil.isNull(baMtrlRateConfigDO)) {
				convertBAMtrlRateConfigDO2TO(baMtrlRateConfigDO, to);
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurs in BAMaterialRateConfigServiceImpl::searchRenewedBAMaterialRateDtls()::"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("BAMaterialRateConfigServiceImpl::searchRenewedBAMaterialRateDtls()::END");
		return to;
	}

}
