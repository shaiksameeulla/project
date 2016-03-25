package com.cg.lbs.bcun.service.dataformater;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.cg.lbs.bcun.constant.BcunDataFormaterConstants;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.stockmanagement.operations.stockreturn.BcunStockReturnDO;
import com.ff.domain.stockmanagement.operations.stockreturn.BcunStockReturnItemDtlsDO;

public class StockReturnFormater extends AbstractDataFormater {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(StockReturnFormater.class);
	@Override
	public CGBaseDO formatInsertData(CGBaseDO baseDO,BcunDatasyncService bcunService) {
		BcunStockReturnDO headerDO = (BcunStockReturnDO) baseDO;
		LOGGER.info("StockReturnFormater ::formatInsertData ::Start For Return Number"+headerDO.getReturnNumber());
		headerDO.setStockReturnId(null);
		
		Set<BcunStockReturnItemDtlsDO> returnItemDtls =headerDO.getReturnItemDtls();
		
		for(BcunStockReturnItemDtlsDO retunDtlsDO :returnItemDtls ){
			retunDtlsDO.setStockReturnItemDtlsId(null);
		}
		Map paramValues=new HashMap<>(1);
		paramValues.put(BcunDataFormaterConstants.RETURN_NNUMBER, headerDO.getReturnNumber());
		try {
			bcunService.deleteFromDatabase("getStockReturnDtlsByNumber", paramValues);
		} catch (CGBusinessException | CGSystemException e) {
			LOGGER.error("StockReturnFormater ::formatInsertData :: Delete Exception",e);
		}
		LOGGER.info("StockReturnFormater ::formatInsertData ::Ends For Return Number"+headerDO.getReturnNumber());
		return headerDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CGBaseDO formatUpdateData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {return formatInsertData(baseDO, bcunService);}

}
