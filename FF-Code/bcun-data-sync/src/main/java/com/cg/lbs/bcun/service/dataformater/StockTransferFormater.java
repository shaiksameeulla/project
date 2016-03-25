package com.cg.lbs.bcun.service.dataformater;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.cg.lbs.bcun.constant.BcunDataFormaterConstants;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.stockmanagement.operations.transfer.BcunStockTransferDO;

public class StockTransferFormater extends AbstractDataFormater {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StockTransferFormater.class);
	@Override
	public CGBaseDO formatInsertData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {
		
		BcunStockTransferDO stockTransferDO = (BcunStockTransferDO)baseDO;
		LOGGER.info("StockTransferFormater :: formatInsertData ::  START: For Stock Transfer Number:[ "+stockTransferDO.getStockTransferNumber()+"]");
		stockTransferDO.setStockTransferId(null);
		Map paramValues=new HashMap<>(1);
		paramValues.put(BcunDataFormaterConstants.STOCK_TRANSFER_NUMBER, stockTransferDO.getStockTransferNumber());
		try {
			bcunService.deleteFromDatabase("getStockTransferDtlsByNumber", paramValues);
		} catch (CGBusinessException | CGSystemException e) {
			LOGGER.error("StockTransferFormater :: deleteFromDatabase ::  Exception:",e);
		}
		LOGGER.info("StockTransferFormater :: formatInsertData ::  End: For Stock Transfer Number:[ "+stockTransferDO.getStockTransferNumber()+"]");
		return stockTransferDO;
	}

	@Override
	public CGBaseDO formatUpdateData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {return formatInsertData(baseDO, bcunService);}

}
