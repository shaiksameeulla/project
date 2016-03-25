/**
 * 
 */
package com.ff.universe.stockmanagement.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.coloading.CSDSAPColoaderInvoiceDO;
import com.ff.domain.coloading.ColoaderRatesDO;
import com.ff.domain.coloading.SAPColoaderRatesDO;
import com.ff.domain.stockmanagement.masters.CSDSAPItemDO;
import com.ff.domain.stockmanagement.masters.ItemDO;
import com.ff.domain.stockmanagement.masters.ItemTypeDO;
import com.ff.domain.stockmanagement.operations.issue.StockIssueDO;
import com.ff.domain.stockmanagement.operations.transfer.StockTransferDO;
import com.ff.domain.stockmanagement.wrapper.StockCustomerWrapperDO;
import com.ff.to.stockmanagement.StockIssueValidationTO;
import com.ff.to.stockmanagement.StockSearchInputTO;
import com.ff.to.stockmanagement.StockUpdateInputTO;
import com.ff.to.stockmanagement.stockrequisition.StockValidationTO;



/**
 * The Interface StockUniversalDAO.
 *
 * @author mohammes
 */
public interface StockUniversalDAO {

	/**
	 * Checks if is series cancelled.
	 *
	 * @param validationTO the validation to
	 * @return the long
	 * @throws CGSystemException the cG system exception
	 */
	Long isSeriesCancelled(StockValidationTO validationTO)
			throws CGSystemException;
	
	/**
	 * Checks if is series issued to party type.
	 *
	 * @param qryName the qry name
	 * @param validationTO the validation to
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	List<Long> isSeriesIssuedToPartyType(String qryName,StockValidationTO validationTO)
			throws CGSystemException;

	/**
	 * Checks if is series returned.
	 *
	 * @param validationTO the validation to
	 * @param returnType the return type
	 * @return the long
	 * @throws CGSystemException the cG system exception
	 */
	Long isSeriesReturned(StockValidationTO validationTO, String returnType)
			throws CGSystemException;

	/**
	 * Checks if is series acknowledged.
	 *
	 * @param validationTO the validation to
	 * @return the long
	 * @throws CGSystemException the cG system exception
	 */
	Long isSeriesAcknowledged(StockValidationTO validationTO)
			throws CGSystemException;
	
	/**
	 * Checks if is series already issued from branch.
	 *
	 * @param validationTO the validation to
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	List<Long> isSeriesAlreadyIssuedFromBranch(StockValidationTO validationTO)
			throws CGSystemException;

	/**
	 * Gets the latest series date.
	 *
	 * @param qryName the qry name
	 * @param paramValuePair the param value pair
	 * @return the latest series date
	 * @throws CGSystemException the cG system exception
	 */
	Date getLatestSeriesDate(String qryName, Map paramValuePair)
			throws CGSystemException;

	/**
	 * Gets the stock quantity by item and party type.
	 *
	 * @param searchTO the search to
	 * @return the stock quantity by item and party type
	 * @throws CGSystemException the cG system exception
	 */
	Integer getStockQuantityByItemAndPartyType(StockSearchInputTO searchTO)
			throws CGSystemException;

	/**
	 * Update stock by party type.
	 *
	 * @param searchTO the search to
	 * @return the integer
	 * @throws CGSystemException the cG system exception
	 */
	Integer updateStockByPartyType(StockUpdateInputTO searchTO)
			throws CGSystemException;

	/**
	 * Gets the all items by type.
	 *
	 * @param itemDO the item do
	 * @return the all items by type
	 * @throws CGSystemException the cG system exception
	 */
	List<ItemDO> getAllItemsByType(ItemDO itemDO) throws CGSystemException;

	/**
	 * Gets the stock issued dtls.
	 *
	 * @param issueTO the issue to
	 * @return the stock issued dtls
	 * @throws CGSystemException the cG system exception
	 */
	StockIssueDO getStockIssuedDtls(StockIssueValidationTO issueTO)
			throws CGSystemException;

/**
 * Gets the latest date with series.
 *
 * @param qryName the qry name
 * @param validationTO the validation to
 * @return the latest date with series
 * @throws CGSystemException the cG system exception
 */
Map<Long, Date> getLatestDateWithSeries(String qryName,
		StockValidationTO validationTO) throws CGSystemException;

	

	/**
	 * Checks if is series transferred from party type.
	 *
	 * @param qryName the qry name
	 * @param validationTO the validation to
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	List<Long> isSeriesTransferredFromPartyType(String qryName,
			StockValidationTO validationTO) throws CGSystemException;

	/**
	 * Checks if is series exist in branch.
	 *
	 * @param validationTO the validation to
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	List<Long> isSeriesExistInBranch(StockValidationTO validationTO)
			throws CGSystemException;

	/**
	 * Checks if is series available with transfer from party type.
	 *
	 * @param qryName the qry name
	 * @param validationTO the validation to
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	List<Long> isSeriesAvailableWithTransferFromPartyType(String qryName,
			StockValidationTO validationTO) throws CGSystemException;

	/**
	 * Checks if is series exist at transferred to party type.
	 *
	 * @param qryName the qry name
	 * @param validationTO the validation to
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	List<Long> isSeriesExistAtTransferredTOPartyType(String qryName,
			StockValidationTO validationTO) throws CGSystemException;

	/**
	 * Gets the stock receipt details for branch.:it's For Integration purpose
	 *
	 * @param validationTO the validation to
	 * @return the stock receipt details for branch
	 */
	Long getStockReceiptDetailsForBranch(
			StockIssueValidationTO validationTO);

	/**
	 * Gets the stock receipt details under region.
	 *
	 * @param validationTO the validation to
	 * @return the stock receipt details under region
	 */
	Long getStockReceiptDetailsUnderRegion(StockIssueValidationTO validationTO);

	/**
	 * Gets the stock item dtls for stock series.
	 *
	 * @param qry the qry
	 * @param stockSeries the stock series
	 * @return the stock item dtls for stock series
	 */
	ItemDO getStockItemDtlsForStockSeries(String qry, String stockSeries);
	
	CSDSAPItemDO getItemDtlsByCode(String itemCode)throws CGSystemException;

	ItemTypeDO getItemTypeByCode(String itemTypeCode) throws CGSystemException;

	/**
	 * Checks if is series cancelled for integration.
	 *
	 * @param validationTo the validation to
	 * @return the long
	 * @throws CGSystemException the cG system exception
	 */
	Long isSeriesCancelledForIntegration(StockIssueValidationTO validationTo)
			throws CGSystemException;

	/**
	 * Gets the latest transfer date.
	 *
	 * @param serialNumber the serial number
	 * @return the latest transfer date
	 * @throws CGSystemException the cG system exception
	 */
	Date getLatestTransferDateBySerialNumber(String qryname,String serialNumber) throws CGSystemException;
	
	/**
	 * Gets the item type as map.
	 *
	 * @return the item type as map
	 * @throws CGSystemException the cG system exception
	 */
	List<?> getItemTypeAsMap() throws CGSystemException;
	
	/**
	 * Gets the item by type as map.
	 *
	 * @param itemTypeId the item type id
	 * @return the item by type as map
	 * @throws CGSystemException the cG system exception
	 */
	List<?> getItemByTypeAsMap(Integer itemTypeId)throws CGSystemException;
	
	/**
	 * Gets the item by item type and item id.
	 *
	 * @param itemTypeId the item type id
	 * @param itemId the item id
	 * @return the item by item type and item id
	 * @throws CGSystemException the cG system exception
	 */
	ItemDO getItemByItemTypeAndItemId(Integer itemTypeId,Integer itemId) throws CGSystemException;

	/**
	 * Checks if is atleast one consignment booked.
	 *
	 * @param validationTO the validation to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 */
	Boolean isAtleastOneConsignmentBooked(StockValidationTO validationTO)
			throws CGBusinessException;

	/**
	 * Gets the latest date with series without office.
	 *
	 * @param qryName the qry name
	 * @param validationTO the validation to
	 * @return the latest date with series without office
	 * @throws CGSystemException the cG system exception
	 */
	Map<Long, Date> getLatestDateWithSeriesWithoutOffice(String qryName,
			StockValidationTO validationTO) throws CGSystemException;

	
	/**
	 * Gets the contract customer list for stock.
	 *
	 * @param customerDO the customer do
	 * @return the contract customer list for stock
	 * @throws CGSystemException the cG system exception
	 */
	List<StockCustomerWrapperDO> getContractCustomerListForStock(CustomerDO customerDO)
			throws CGSystemException;

	/**
	 * Gets the stock transfer dtls.
	 *
	 * @param issueTO the issue to
	 * @return the stock transfer dtls
	 * @throws CGSystemException the cG system exception
	 */
	StockTransferDO getStockTransferDtls(StockIssueValidationTO issueTO)
			throws CGSystemException;

	Long isSeriesExistInBranchByLeaf(StockValidationTO validationTO)
			throws CGSystemException;

	/**
	 * Checks if is series already issued from branch by leaf.
	 *
	 * @param validationTO the validation to
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean isSeriesAlreadyIssuedFromBranchByLeaf(StockValidationTO validationTO)
			throws CGSystemException;

	/**
	 * Gets the latest date with series leaf.
	 *
	 * @param qryName the qry name
	 * @param validationTO the validation to
	 * @return the latest date with series leaf
	 * @throws CGSystemException the cG system exception
	 */
	Date getLatestDateByLeaf(String qryName,
			StockValidationTO validationTO) throws CGSystemException;

	/**
	 * Gets the latest date byleaf without office.
	 *
	 * @param qryName the qry name
	 * @param validationTO the validation to
	 * @return the latest date byleaf without office
	 * @throws CGSystemException the cG system exception
	 */
	Date getLatestDateByleafWithoutOffice(String qryName,
			StockValidationTO validationTO) throws CGSystemException;

	ColoaderRatesDO getColoaderDtlsByID(Integer id) throws CGSystemException;

	CSDSAPColoaderInvoiceDO getStagingColoaderDtlsByID(Integer id) throws CGSystemException;

	List<CSDSAPColoaderInvoiceDO> getStagingColoaderDtls(String sapStatus) throws CGSystemException;
	
}
