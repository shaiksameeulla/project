
package com.cg.lbs.bcun.service.dataformater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.delivery.DeliveryNavigatorDO;

public class DrsNavigatorFormatter extends AbstractDataFormater {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DrsNavigatorFormatter.class);
	@Override
	public CGBaseDO formatInsertData(CGBaseDO baseDO,
			BcunDatasyncService bcunService)throws CGBusinessException,CGSystemException {
		LOGGER.info("DrsNavigatorFormatter::formatInsertData:: START");
		// TODO Auto-generated method stub
		DeliveryNavigatorDO headerDO = (DeliveryNavigatorDO) baseDO;
		headerDO.setDeliveryNavigatorId(null);
		
		LOGGER.debug("DrsNavigatorFormatter::formatInsertData:: headerDO::"+headerDO.getDrsNumber());
		
		LOGGER.info("DrsNavigatorFormatter::formatInsertData:: END");
		return headerDO;
	}

	@Override
	public CGBaseDO formatUpdateData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) throws CGBusinessException,CGSystemException{
	return formatInsertData(baseDO, bcunService);
	}

}
