package com.ff.web.global.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.domain.umc.ApplScreenDO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.ApplScreensTO;
import com.ff.web.global.dao.GlobalServiceDAO;

public class GlobalServiceImpl implements GlobalService {

	private GlobalServiceDAO globalServiceDAO;
	private final static Logger LOGGER = LoggerFactory
			.getLogger(GlobalServiceImpl.class);

	public GlobalServiceDAO getGlobalServiceDAO() {
		return globalServiceDAO;
	}

	public void setGlobalServiceDAO(GlobalServiceDAO globalServiceDAO) {
		this.globalServiceDAO = globalServiceDAO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockStandardTypeTO> getAllStockStandardType(String typeName)
			throws CGBusinessException, CGSystemException {

		List<StockStandardTypeTO> stockStandardTypeTO = null;
		try {
			List<StockStandardTypeDO> StockStandardTypeDOList = globalServiceDAO
					.getAllStockStandardType(typeName);
			if (StockStandardTypeDOList != null
					&& StockStandardTypeDOList.size() > 0) {
				stockStandardTypeTO = (List<StockStandardTypeTO>) CGObjectConverter
						.createTOListFromDomainList(StockStandardTypeDOList,
								StockStandardTypeTO.class);
			}
		} catch (Exception ex) {
			LOGGER.error("ERROR : GlobalServiceImpl.getAllStockStandardType",
					ex);
			throw ex;
		}
		return stockStandardTypeTO;
	}

	@Override
	public ApplScreensTO getScreenByCodeOrName(String screenCode,
			String screenName) throws CGBusinessException, CGSystemException {
		ApplScreenDO applScreenDO = null;
		ApplScreensTO applScreenTO = null;
		applScreenDO = globalServiceDAO.getScreenByCodeOrName(screenCode,
				screenName);
		if (!StringUtil.isNull(applScreenDO)) {
			applScreenTO = new ApplScreensTO();
			applScreenTO = (ApplScreensTO) CGObjectConverter
					.createToFromDomain(applScreenDO, applScreenTO);
		}
		return applScreenTO;
	}

}
