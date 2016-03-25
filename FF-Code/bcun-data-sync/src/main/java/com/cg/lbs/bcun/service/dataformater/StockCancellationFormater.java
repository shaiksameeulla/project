package com.cg.lbs.bcun.service.dataformater;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.constant.BcunDataFormaterConstants;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.stockmanagement.operations.cancel.BcunStockCancellationDO;

public class StockCancellationFormater extends AbstractDataFormater {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(StockCancellationFormater.class);
	@Override
	public CGBaseDO formatInsertData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {
		
		return formatUpdateData(baseDO, bcunService);
	}

	@Override
	public CGBaseDO formatUpdateData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {
		BcunStockCancellationDO headerDO = (BcunStockCancellationDO)baseDO;
		LOGGER.info("StockCancellationFormater::formatUpdateData ::Start For  cancellation Number:: "+headerDO.getCancellationNumber());
		String cancellationNumber = null;
		BcunStockCancellationDO BcunStockCancellationDO = (BcunStockCancellationDO)baseDO;
		BcunStockCancellationDO.setStockCancelledId(null);
		
		if(!StringUtil.isNull(headerDO.getCancellationNumber())){
			cancellationNumber=headerDO.getCancellationNumber();
			Map paramValues=new HashMap<>(1);
			paramValues.put(BcunDataFormaterConstants.CANCELLATIO_NNUMBER,cancellationNumber);
			try {
				bcunService.deleteFromDatabase("getStockCancellationByNumberForBCN", paramValues);
			} catch (CGBusinessException | CGSystemException e) {
				LOGGER.error("StockCancellationFormater::formatUpdateData ::Delete Cancellation Number:: Exception :",e);
			}
			
		}
		LOGGER.info("StockCancellationFormater::formatUpdateData ::End For  cancellation Number:: "+headerDO.getCancellationNumber());
		return headerDO;
	}

}
