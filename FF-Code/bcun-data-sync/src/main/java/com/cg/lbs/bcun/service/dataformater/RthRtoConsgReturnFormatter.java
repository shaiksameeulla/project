package com.cg.lbs.bcun.service.dataformater;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.constant.BcunDataFormaterConstants;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.BcunConsignmentReturnDO;
import com.ff.domain.manifest.BcunConsignmentReturnReasonDO;

/**
 * The Class RthRtoConsgReturnFormatter.
 * 
 * @author narmdr
 */
public class RthRtoConsgReturnFormatter extends AbstractDataFormater {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(RthRtoConsgReturnFormatter.class);

	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.dataformater.AbstractDataFormater#formatInsertData(com.capgemini.lbs.framework.domain.CGBaseDO, com.cg.lbs.bcun.service.BcunDatasyncService)
	 */
	@Override
	public CGBaseDO formatInsertData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) throws CGBusinessException {
		LOGGER.trace("RthRtoConsgReturnFormatter::formatInsertData::START------------>:::::::");
		formatUpdateData(baseDO, bcunService);
		LOGGER.trace("RthRtoConsgReturnFormatter::formatInsertData::END------------>:::::::");
		return baseDO;
	}

	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.dataformater.AbstractDataFormater#formatUpdateData(com.capgemini.lbs.framework.domain.CGBaseDO, com.cg.lbs.bcun.service.BcunDatasyncService)
	 */
	@Override
	public CGBaseDO formatUpdateData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) throws CGBusinessException {
		LOGGER.trace("RthRtoConsgReturnFormatter::formatUpdateData::START------------>:::::::");
		BcunConsignmentReturnDO consignmentReturnDO = (BcunConsignmentReturnDO) baseDO;
		getAndSetConsignmentReturn(bcunService, consignmentReturnDO);
		LOGGER.trace("RthRtoConsgReturnFormatter::formatUpdateData::END------------>:::::::");
		return consignmentReturnDO;
	}

	/**
	 * Gets the and set consignment return.
	 *
	 * @param bcunService the bcun service
	 * @param consignmentReturnDO the consignment return do
	 * @return the and set consignment return
	 * @throws CGBusinessException 
	 */
	private void getAndSetConsignmentReturn(BcunDatasyncService bcunService,
			BcunConsignmentReturnDO consignmentReturnDO) throws CGBusinessException {
		LOGGER.info("RthRtoConsgReturnFormatter::getAndSetConsignmentReturn::START------------>:::::::");
		try {
			Integer consignmentReturnId = getConsignmentReturnId(bcunService,
					consignmentReturnDO);
			consignmentReturnDO.setConsignmentReturnId(consignmentReturnId);

			ConsignmentDO consignmentDO = BcunManifestUtils.getConsignment(
					bcunService, consignmentReturnDO.getConsignmentDO());
			consignmentReturnDO.setConsignmentDO(consignmentDO);

			if (!StringUtil.isEmptyInteger(consignmentReturnId)) {
				updateConsignmentReturn(bcunService, consignmentReturnDO);
			} else {
				createConsignmentReturn(bcunService, consignmentReturnDO);
			}
		} catch (Exception e) {
			LOGGER.error("Exception happened in getAndSetConsignmentReturn of RthRtoConsgReturnFormatter..."
					, e);
			throw e;
		}
		LOGGER.info("RthRtoConsgReturnFormatter::getAndSetConsignmentReturn::END------------>:::::::");
	}

	/**
	 * Creates the consignment return.
	 *
	 * @param bcunService the bcun service
	 * @param consignmentReturnDO the consignment return do
	 */
	private void createConsignmentReturn(BcunDatasyncService bcunService,
			BcunConsignmentReturnDO consignmentReturnDO) {
		LOGGER.trace("RthRtoConsgReturnFormatter::createConsignmentReturn::START------------>:::::::");
		for (BcunConsignmentReturnReasonDO consignmentReturnReasonDO : consignmentReturnDO
				.getConsignmentReturnReasonDOs()) {
			consignmentReturnReasonDO.setConsignmentReturnReasonId(null);
			consignmentReturnReasonDO
					.setConsignmentReturnId(consignmentReturnDO
							.getConsignmentReturnId());
		}
		LOGGER.trace("RthRtoConsgReturnFormatter::createConsignmentReturn::END------------>:::::::");
	}

	/**
	 * Update consignment return.
	 *
	 * @param bcunService the bcun service
	 * @param consignmentReturnDO the consignment return do
	 */
	private void updateConsignmentReturn(BcunDatasyncService bcunService,
			BcunConsignmentReturnDO consignmentReturnDO) {
		LOGGER.trace("RthRtoConsgReturnFormatter::updateConsignmentReturn::START------------>:::::::");
		for (BcunConsignmentReturnReasonDO consignmentReturnReasonDO : consignmentReturnDO
				.getConsignmentReturnReasonDOs()) {
			consignmentReturnReasonDO
					.setConsignmentReturnId(consignmentReturnDO
							.getConsignmentReturnId());
			Integer consignmentReturnReasonId = getConsignmentReturnReasonId(
					bcunService, consignmentReturnReasonDO);
			consignmentReturnReasonDO
					.setConsignmentReturnReasonId(consignmentReturnReasonId);
		}
		LOGGER.trace("RthRtoConsgReturnFormatter::updateConsignmentReturn::END------------>:::::::");
	}

	/**
	 * Gets the consignment return reason id.
	 *
	 * @param bcunService the bcun service
	 * @param consignmentReturnReasonDO the consignment return reason do
	 * @return the consignment return reason id
	 */
	private Integer getConsignmentReturnReasonId(
			BcunDatasyncService bcunService,
			BcunConsignmentReturnReasonDO consignmentReturnReasonDO) {
		LOGGER.trace("RthRtoConsgReturnFormatter::getConsignmentReturnReasonId::START------------>:::::::");
		String[] consgReturnReasonParams = { "consignmentReturnId", "reasonId",
				"date", "time" };
		Object[] consgReturnReasonValues = {
				consignmentReturnReasonDO.getConsignmentReturnId(),
				consignmentReturnReasonDO.getReasonId(),
				consignmentReturnReasonDO.getDate(),
				consignmentReturnReasonDO.getTime(), };
		LOGGER.trace("RthRtoConsgReturnFormatter::getConsignmentReturnReasonId For consgReturnReasonParams are => "
				+ Arrays.asList(consgReturnReasonParams)
				+ " and consgReturnReasonValues are => "
				+ Arrays.asList(consgReturnReasonValues)
				+ "::Processing------------>:::::::");

		Integer consignmentReturnReasonId = bcunService
				.getUniqueId(
						BcunDataFormaterConstants.QRY_GET_CONSIGNMENT_RETURN_REASONS_ID_BY_CONSG_RETURN_ID_REASON_ID_DATE_TIME,
						consgReturnReasonParams, consgReturnReasonValues);
		LOGGER.trace("RthRtoConsgReturnFormatter::getConsignmentReturnReasonId::END------------>:::::::");
		return consignmentReturnReasonId;
	}

	/**
	 * Gets the consignment return id.
	 *
	 * @param bcunService the bcun service
	 * @param consignmentReturnDO the consignment return do
	 * @return the consignment return id
	 */
	private Integer getConsignmentReturnId(BcunDatasyncService bcunService,
			BcunConsignmentReturnDO consignmentReturnDO) {
		LOGGER.trace("RthRtoConsgReturnFormatter::getConsignmentReturnId::START------------>:::::::");
		Integer consignmentReturnId = null;
		if (consignmentReturnDO.getConsignmentDO() != null
				&& !StringUtil
						.isEmptyInteger(consignmentReturnDO.getOfficeId())) {
			String[] consgReturnParams = { "consignmentNumber", "officeId",
					"returnType" };
			Object[] consgReturnValues = {
					consignmentReturnDO.getConsignmentDO().getConsgNo(),
					consignmentReturnDO.getOfficeId(),
					consignmentReturnDO.getReturnType() };
			LOGGER.trace("RthRtoConsgReturnFormatter::getConsignmentReturnId For consgReturnParams are => "
					+ Arrays.asList(consgReturnParams)
					+ " and consgReturnValues are => "
					+ Arrays.asList(consgReturnValues)
					+ "::Processing------------>:::::::");

			consignmentReturnId = bcunService
					.getUniqueId(
							BcunDataFormaterConstants.QRY_GET_CONSIGNMENT_RETURN_ID_BY_CONSG_NO_OFFICE_ID_RETURN_TYPE,
							consgReturnParams, consgReturnValues);
		}
		LOGGER.trace("RthRtoConsgReturnFormatter::getConsignmentReturnId::END------------>:::::::");
		return consignmentReturnId;
	}
}