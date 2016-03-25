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
import com.ff.domain.stockmanagement.operations.issue.BcunStockIssueDO;
import com.ff.domain.stockmanagement.operations.issue.BcunStockIssueItemDtlsDO;

public class StockIssueFormater extends AbstractDataFormater {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(StockIssueFormater.class);
	@Override
	public CGBaseDO formatInsertData(CGBaseDO baseDO,BcunDatasyncService bcunService) {
		BcunStockIssueDO headerDO = (BcunStockIssueDO) baseDO;
		headerDO.setStockIssueId(null);
		LOGGER.info("StockIssueFormater ::formatInsertData::Start :: For issue Number "+headerDO.getStockIssueNumber());
		
		
		String[] params = {BcunDataFormaterConstants.STOCK_ISSUE_NUMBER};
		Object[] values = { headerDO.getStockIssueNumber() };
		Set<BcunStockIssueItemDtlsDO> issueItemDtlsDOs =headerDO.getIssueItemDtlsDO();
		for(BcunStockIssueItemDtlsDO stockIssueItemDtlsDO :issueItemDtlsDOs ){
			stockIssueItemDtlsDO.setStockIssueItemDtlsId(null);
		}
		Map paramValues=new HashMap<>(1);
		paramValues.put(BcunDataFormaterConstants.STOCK_ISSUE_NUMBER, headerDO.getStockIssueNumber());
		try {
			bcunService.deleteFromDatabase(BcunDataFormaterConstants.QRY_STOCK_ISSUE_BY_NUMBER, paramValues);
		} catch (CGBusinessException | CGSystemException e) {
			LOGGER.error("StockIssueFormater ::formatInsertData::delete from DB :: exception ",e);
		}
		LOGGER.info("StockIssueFormater ::formatInsertData::End :: For issue Number "+headerDO.getStockIssueNumber());
		return headerDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CGBaseDO formatUpdateData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {
		return formatInsertData(baseDO, bcunService);
	}
	
}
