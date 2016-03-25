package com.cg.lbs.bcun.service.dataformater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.consignment.ConsignmentDO;

/**
 * The Class ConsignmentLocalFormatter.
 *
 * @author narmdr
 */
public class ConsignmentLocalFormatter extends AbstractDataFormater {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ConsignmentLocalFormatter.class);

	@Override
	public CGBaseDO formatInsertData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) throws CGBusinessException {
		LOGGER.info("ConsignmentLocalFormatter::formatInsertData::START------------>:::::::");
		ConsignmentDO consignmentDO = (ConsignmentDO) baseDO;
		ConsignmentDO consignmentNewDO = BcunManifestUtils.getConsignment(bcunService, consignmentDO);
		LOGGER.info("ConsignmentLocalFormatter::formatInsertData::END------------>:::::::");
		return consignmentNewDO;
	}
	
	@Override
	public CGBaseDO formatUpdateData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) throws CGBusinessException {
		LOGGER.info("ConsignmentLocalFormatter::formatUpdateData::START------------>:::::::");
		ConsignmentDO consignmentNewDO = (ConsignmentDO) formatInsertData(baseDO, bcunService);
		LOGGER.info("ConsignmentLocalFormatter::formatUpdateData::END------------>:::::::");
		return consignmentNewDO;
	}

}