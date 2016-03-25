package com.cg.lbs.bcun.service.dataformater;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.constant.BcunDataFormaterConstants;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.loadmanagement.BcunLoadConnectedDO;
import com.ff.domain.loadmanagement.BcunLoadMovementDO;
import com.ff.domain.manifest.BcunLoadManifestDO;

/**
 * The Class DispatchReceiveFormatter.
 * 
 * @author narmdr
 */
public class DispatchReceiveFormatter extends AbstractDataFormater {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(DispatchReceiveFormatter.class);

	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.dataformater.AbstractDataFormater#formatInsertData(com.capgemini.lbs.framework.domain.CGBaseDO, com.cg.lbs.bcun.service.BcunDatasyncService)
	 */
	@Override
	public CGBaseDO formatInsertData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {
		LOGGER.trace("DispatchReceiveFormatter::formatInsertData::START------------>:::::::");
		BcunLoadMovementDO loadMovementDO = (BcunLoadMovementDO) baseDO;
		getAndSetLoadMovement(bcunService, loadMovementDO);
		LOGGER.trace("DispatchReceiveFormatter::formatInsertData::END------------>:::::::");
		return loadMovementDO;
	}

	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.dataformater.AbstractDataFormater#formatUpdateData(com.capgemini.lbs.framework.domain.CGBaseDO, com.cg.lbs.bcun.service.BcunDatasyncService)
	 */
	@Override
	public CGBaseDO formatUpdateData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {
		LOGGER.trace("DispatchReceiveFormatter::formatUpdateData::START------------>:::::::");
		formatInsertData(baseDO, bcunService);
		LOGGER.trace("DispatchReceiveFormatter::formatUpdateData::END------------>:::::::");
		return baseDO;
	}

	/**
	 * Gets the and set load movement.
	 *
	 * @param bcunService the bcun service
	 * @param loadMovementDO the load movement do
	 * @return the and set load movement
	 */
	private void getAndSetLoadMovement(BcunDatasyncService bcunService,
			BcunLoadMovementDO loadMovementDO) {
		LOGGER.info("DispatchReceiveFormatter::getAndSetLoadMovement::START------------>:::::::");
		try {
			Integer loadMovementId = getLoadMovementId(bcunService,
					loadMovementDO);
			loadMovementDO.setLoadMovementId(loadMovementId);

			if (!StringUtil.isEmptyInteger(loadMovementId)) {
				updateLoadMovement(bcunService, loadMovementDO);
			} else {
				createLoadMovement(bcunService, loadMovementDO);
			}
		} catch (Exception e) {
			LOGGER.error("Exception happened in getAndSetLoadMovement of DispatchReceiveFormatter..."
					, e);
			throw e;
		}
		LOGGER.info("DispatchReceiveFormatter::getAndSetLoadMovement::END------------>:::::::");
	}

	/**
	 * Update load movement.
	 *
	 * @param bcunService the bcun service
	 * @param loadMovementDO the load movement do
	 */
	private void updateLoadMovement(BcunDatasyncService bcunService,
			BcunLoadMovementDO loadMovementDO) {
		LOGGER.trace("DispatchReceiveFormatter::updateLoadMovement::START------------>:::::::");
		for (BcunLoadConnectedDO loadConnectedDO : loadMovementDO
				.getLoadConnectedDOs()) {
			loadConnectedDO.setLoadMovementId(loadMovementDO
					.getLoadMovementId());
			getAndSetLoadConnectedId(bcunService, loadConnectedDO);
			getAndSetManifest(bcunService, loadConnectedDO);
		}
		LOGGER.trace("DispatchReceiveFormatter::updateLoadMovement::END------------>:::::::");
	}

	/**
	 * Gets the and set load connected id.
	 *
	 * @param bcunService the bcun service
	 * @param loadConnectedDO the load connected do
	 * @return the and set load connected id
	 */
	private void getAndSetLoadConnectedId(BcunDatasyncService bcunService,
			BcunLoadConnectedDO loadConnectedDO) {
		LOGGER.trace("DispatchReceiveFormatter::getAndSetLoadConnectedId::START------------>:::::::");
		String[] params = { "loadMovementId", "manifestNumber", };
		Object[] values = { loadConnectedDO.getLoadMovementId(),
				loadConnectedDO.getManifestDO().getManifestNo() };

		Integer loadConnectedId = bcunService.getUniqueId(
				"getLoadConnectedId4Load", params, values);
		loadConnectedDO.setLoadConnectedId(loadConnectedId);
		LOGGER.trace("DispatchReceiveFormatter::getAndSetLoadConnectedId::END------------>:::::::");
	}

	/**
	 * Gets the load movement id.
	 *
	 * @param bcunService the bcun service
	 * @param loadMovementDO the load movement do
	 * @return the load movement id
	 */
	private Integer getLoadMovementId(BcunDatasyncService bcunService,
			BcunLoadMovementDO loadMovementDO) {
		LOGGER.trace("DispatchReceiveFormatter::getLoadMovementId::START------------>:::::::");
		Integer loadMovementId = null;
		List<String> loadMovementParams = new ArrayList<String>();
		List<Object> loadMovementValues = new ArrayList<>();
		String query = null;

		if (StringUtils.equals(loadMovementDO.getMovementDirection(),
				BcunDataFormaterConstants.DISPATCH_DIRECTION)) {// dispatch

			LOGGER.trace("DispatchReceiveFormatter::getLoadMovementId For Dispatch::START------------>:::::::");
			loadMovementParams.add("gatePassNumber");
			loadMovementParams.add("movementDirection");
			loadMovementParams.add("officeId");
			loadMovementValues.add(loadMovementDO.getGatePassNumber());
			loadMovementValues.add(loadMovementDO.getMovementDirection());
			loadMovementValues.add(loadMovementDO.getOriginOfficeId());
			query = BcunDataFormaterConstants.QRY_LOAD_MOVEMENT_ID_FOR_DISPATCH;

			LOGGER.trace("DispatchReceiveFormatter::getLoadMovementId For Dispatch loadMovementParams are => "
					+ loadMovementParams
					+ " and loadMovementValues are => "
					+ loadMovementValues + "::END------------>:::::::");

		} else if (StringUtils.equals(loadMovementDO.getMovementDirection(),
				BcunDataFormaterConstants.RECEIVE_DIRECTION)
				&& StringUtils.equals(loadMovementDO.getReceiveType(),
						BcunDataFormaterConstants.RECEIVE_TYPE_LOCAL)) {// Receive
																		// Local
			LOGGER.trace("DispatchReceiveFormatter::getLoadMovementId For Receive Local::START------------>:::::::");
			loadMovementParams.add("gatePassNumber");
			loadMovementParams.add("movementDirection");
			loadMovementParams.add("receiveType");
			loadMovementParams.add("destOfficeId");
			loadMovementValues.add(loadMovementDO.getGatePassNumber());
			loadMovementValues.add(loadMovementDO.getMovementDirection());
			loadMovementValues.add(loadMovementDO.getReceiveType());
			loadMovementValues.add(loadMovementDO.getDestOfficeId());
			query = BcunDataFormaterConstants.QRY_LOAD_MOVEMENT_ID_FOR_RECEIVE_LOCAL;
			LOGGER.trace("DispatchReceiveFormatter::getLoadMovementId For Receive Local loadMovementParams are => "
					+ loadMovementParams
					+ " and loadMovementValues are => "
					+ loadMovementValues + "::END------------>:::::::");

		} else if (StringUtils.equals(loadMovementDO.getMovementDirection(),
				BcunDataFormaterConstants.RECEIVE_DIRECTION)
				&& StringUtils.equals(loadMovementDO.getReceiveType(),
						BcunDataFormaterConstants.RECEIVE_TYPE_OUTSTATION)) {// Receive
																				// Outstation
			LOGGER.trace("DispatchReceiveFormatter::getLoadMovementId For Receive Outstation::START------------>:::::::");
			loadMovementParams.add("receiveNumber");
			loadMovementParams.add("movementDirection");
			loadMovementParams.add("receiveType");
			loadMovementParams.add("destOfficeId");
			loadMovementValues.add(loadMovementDO.getReceiveNumber());
			loadMovementValues.add(loadMovementDO.getMovementDirection());
			loadMovementValues.add(loadMovementDO.getReceiveType());
			loadMovementValues.add(loadMovementDO.getDestOfficeId());
			query = BcunDataFormaterConstants.QRY_LOAD_MOVEMENT_ID_FOR_RECEIVE_OUTSTATION;
			LOGGER.trace("DispatchReceiveFormatter::getLoadMovementId For Receive Outstation loadMovementParams are => "
					+ loadMovementParams
					+ " and loadMovementValues are => "
					+ loadMovementValues + "::END------------>:::::::");
		}

		if (StringUtils.isNotBlank(query)) {
			String[] paramArr = new String[loadMovementParams.size()];
			paramArr = loadMovementParams.toArray(paramArr);
			loadMovementId = bcunService.getUniqueId(query, paramArr,
					loadMovementValues.toArray());
		}
		LOGGER.trace("DispatchReceiveFormatter::getLoadMovementId::END------------>:::::::");
		return loadMovementId;
	}

	/**
	 * Creates the load movement.
	 *
	 * @param bcunService the bcun service
	 * @param loadMovementDO the load movement do
	 */
	private void createLoadMovement(BcunDatasyncService bcunService,
			BcunLoadMovementDO loadMovementDO) {
		LOGGER.trace("DispatchReceiveFormatter::createLoadMovement::START------------>:::::::");
		// loadMovementDO.setLoadMovementId(null);
		for (BcunLoadConnectedDO loadConnectedDO : loadMovementDO
				.getLoadConnectedDOs()) {
			loadConnectedDO.setLoadConnectedId(null);
			loadConnectedDO.setLoadMovementId(null);
			getAndSetManifest(bcunService, loadConnectedDO);
		}
		LOGGER.trace("DispatchReceiveFormatter::createLoadMovement::END------------>:::::::");
	}

	/**
	 * Gets the and set manifest.
	 *
	 * @param bcunService the bcun service
	 * @param loadConnectedDO the load connected do
	 * @return the and set manifest
	 */
	@SuppressWarnings("unchecked")
	public static void getAndSetManifest(BcunDatasyncService bcunService,
			BcunLoadConnectedDO loadConnectedDO) {
		LOGGER.trace("DispatchReceiveFormatter::getAndSetManifest::START------------>:::::::");
		if (loadConnectedDO.getManifestDO() == null) {
			return;
		}
		String[] params = { "manifestNo", "operatingOffice", "manifestType",
				"manifestProcessCode" };
		Object[] values = { loadConnectedDO.getManifestDO().getManifestNo(),
				loadConnectedDO.getManifestDO().getOperatingOffice(),
				loadConnectedDO.getManifestDO().getManifestType(),
				loadConnectedDO.getManifestDO().getManifestProcessCode() };

		LOGGER.trace("DispatchReceiveFormatter::getAndSetManifest For Manifest params are => "
				+ Arrays.asList(params)
				+ " and values are => "
				+ Arrays.asList(values)
				+ "::Processing------------>:::::::");

		List<BcunLoadManifestDO> manifestDos = (List<BcunLoadManifestDO>) bcunService
				.getDataByNamedQueryAndNamedParam(
						"getManifestByOperatOfficeNoTypeAndProcess4Load",
						params, values);
		BcunLoadManifestDO manifestDO = (!StringUtil
				.isEmptyColletion(manifestDos)) ? manifestDos.get(0) : null;
		if (manifestDO != null) {
			loadConnectedDO.setManifestDO(manifestDO);
		} else {
			//create manifest
			//While creation of manifest By Dispatch Process.
			//Dispatch is not responsible to create manifest Blob so marked as Y.
			//When actual manifest will come it will create Blob for manifest.
			loadConnectedDO.getManifestDO().setDtToBranch(CommonConstants.CHARACTER_R);
			loadConnectedDO.getManifestDO().setDtToCentral(CommonConstants.CHARACTER_R);
			loadConnectedDO.getManifestDO().setManifestId(null);
			loadConnectedDO.getManifestDO().setManifestEmbeddedIn(null);
		}
		LOGGER.trace("DispatchReceiveFormatter::getAndSetManifest::END------------>:::::::");
	}

}