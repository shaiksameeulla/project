package com.ff.admin.stockmanagement.stockreduction.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.stockmanagement.operations.reduction.StockConsumptionLevelDO;
import com.ff.to.stockmanagement.StockReductionInputTO;

/**
 * @author hkansagr
 * 
 */
public interface StockReductionService {

	
	/**
	 * To consolidate and save stock details date wise, office wise, material
	 * wise to SAP staging table
	 * 
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	void consolidateStockConsumptionDtls() throws CGBusinessException,
			CGSystemException;

	StockReductionInputTO prepareStockReductionInputTO();

	List<StockConsumptionLevelDO> prepareStockConsumptionlevelDtlsFromManifest(
			StockReductionInputTO stockReductionInputTo) throws CGBusinessException,
			CGSystemException;

	void saveStockConsumptionLevelDtlsFromManifestConsignment(
			List<StockConsumptionLevelDO> stockLevelDOs);

	List<StockConsumptionLevelDO> getStockReductionDtlsFromConsignment(
			StockReductionInputTO to) throws CGBusinessException,
			CGSystemException;

	List<StockConsumptionLevelDO> getStockReductionDtlsFromComail(
			StockReductionInputTO to) throws CGBusinessException,
			CGSystemException;

}
