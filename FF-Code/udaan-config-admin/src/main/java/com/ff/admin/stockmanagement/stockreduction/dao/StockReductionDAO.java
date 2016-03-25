package com.ff.admin.stockmanagement.stockreduction.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.booking.StockBookingDO;
import com.ff.domain.manifest.ComailDO;
import com.ff.domain.manifest.StockManifestDO;
import com.ff.domain.stockmanagement.operations.reduction.SAPStockConsolidationDO;
import com.ff.domain.stockmanagement.operations.reduction.StockConsumptionLevelDO;
import com.ff.domain.stockmanagement.wrapper.StockHolderWrapperDO;
import com.ff.to.stockmanagement.StockReductionInputTO;

/**
 * @author hkansagr
 * 
 */
public interface StockReductionDAO {


	/**
	 * To save or update stock consumption level details
	 * 
	 * @param stockLevelDOs
	 * @return boolean
	 * @throws CGSystemException
	 */
	boolean saveOrUpdateStockConsumptionLevel(
			List<StockConsumptionLevelDO> stockLevelDOs)
			throws CGSystemException;

	/**
	 * To get consignment stock reduction details it contains CN/ Child CN
	 * Number(s)
	 * 
	 * @param to
	 * @return consgObjects
	 * @throws CGSystemException
	 */
	List<StockBookingDO> getConsgStockReductionDtls(
			StockReductionInputTO to) throws CGSystemException;

	/**
	 * To save stock consumption level details even it throws exception
	 * 
	 * @param stockConsumptionLevelDO
	 * @return boolean
	 * @throws CGSystemException
	 */
	boolean saveStockConsumptionLevel(
			StockConsumptionLevelDO stockConsumptionLevelDO)
			throws CGSystemException;

	/**
	 * To get stock consolidation details date wise, office wise, item wise
	 * 
	 * @return stockLevelDOs
	 * @throws CGSystemException
	 */
	List<StockConsumptionLevelDO> getStockConsolidationDtls()
			throws CGSystemException;

	/**
	 * To save or update SAP stock consolidation details
	 * 
	 * @param sapStockConsolidationDOs
	 * @return boolean
	 * @throws CGSystemException
	 */
	boolean saveOrUpdateSAPStockConsolidationDtls(
			List<SAPStockConsolidationDO> sapStockConsolidationDOs)
			throws CGSystemException;

	/**
	 * To save stock consolidation details even if exception occurs
	 * 
	 * @param stckConsolidationDO
	 * @return boolean
	 * @throws CGSystemException
	 */
	boolean saveStockConsolidationDtls(
			SAPStockConsolidationDO stckConsolidationDO)
			throws CGSystemException;

	/**
	 * Gets the stock holding details.
	 *
	 * @param queryName the query name
	 * @param stockNumber the stock number
	 * @param itemId the item id
	 * @return the stock holding details
	 * @throws CGSystemException the cG system exception
	 */
	List<StockHolderWrapperDO> getStockHoldingDetails(String queryName,
			String stockNumber, Integer itemId) throws CGSystemException;

	List<StockManifestDO> getManifestDetailsForStock(StockReductionInputTO to)
			throws CGSystemException;

	List<String> getChildConsignmentDtls(String parentConsg)
			throws CGSystemException;

	boolean updateStockManifestAndBookingFlag(StockConsumptionLevelDO stockConsumptionLevelDO);

	List<ComailDO> getComailStockReductionDtls(StockReductionInputTO to)
			throws CGSystemException;

	boolean updateStockConsumptionLevelList(
			List<StockConsumptionLevelDO> stockLevelDOs)
			throws CGSystemException;

}
