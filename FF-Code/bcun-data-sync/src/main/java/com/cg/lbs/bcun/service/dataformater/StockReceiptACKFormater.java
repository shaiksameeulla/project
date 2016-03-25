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
import com.ff.domain.stockmanagement.operations.receipt.BcunStockReceiptDO;
import com.ff.domain.stockmanagement.operations.receipt.BcunStockReceiptItemDtlsDO;

public class StockReceiptACKFormater extends AbstractDataFormater {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StockReceiptACKFormater.class);

	@Override
	public CGBaseDO formatInsertData(CGBaseDO baseDO,BcunDatasyncService bcunService) {
		BcunStockReceiptDO headerDO = (BcunStockReceiptDO) baseDO;
		LOGGER.info("StockReceiptACKFormater :: formatInsertData ::Start For Receitp number:"+headerDO.getAcknowledgementNumber());
		headerDO.setStockReceiptId(null);
		
		Set<BcunStockReceiptItemDtlsDO> stockReceiptItemDtlsDOs = headerDO.getStockReceiptItemDtls();
		
		for(BcunStockReceiptItemDtlsDO stockIssueItemDtlsDO : stockReceiptItemDtlsDOs ){
			stockIssueItemDtlsDO.setStockReceiptItemDtlsId(null);
		}
		
		Map paramValues=new HashMap<>(1);
		paramValues.put(BcunDataFormaterConstants.ACKNOWLEDGEMENT_NUMBER, headerDO.getAcknowledgementNumber());
		try {
			bcunService.deleteFromDatabase("getStockReceiptDtlsByNumber", paramValues);
		} catch (CGBusinessException | CGSystemException e) {
			LOGGER.error("StockReceiptACKFormater :: Delete ::Exception:",e);
		}
		LOGGER.info("StockReceiptACKFormater :: formatInsertData ::END For Receitp number:"+headerDO.getAcknowledgementNumber());
		return headerDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CGBaseDO formatUpdateData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {
		 return formatInsertData(baseDO,bcunService);
	}

}
