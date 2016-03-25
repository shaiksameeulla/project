/**
 * 
 */
package com.ff.admin.stockmanagement.common.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.ff.domain.stockmanagement.masters.ItemDO;
import com.ff.domain.stockmanagement.masters.ItemTypeDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.domain.stockmanagement.wrapper.StockHolderWrapperDO;
import com.ff.to.stockmanagement.stockrequisition.StockValidationTO;

/**
 * The Interface StockCommonDAO.
 *
 * @author mohammes
 */
public interface StockCommonDAO {
	
	/**
	 * Gets the item type list.
	 *
	 * @return the item type list
	 * @throws CGSystemException the cG system exception
	 */
	List<ItemTypeDO> getItemTypeList() throws CGSystemException;

	/**
	 * Gets the item list by item type.
	 *
	 * @param itemTypeId the item type id
	 * @return the item list by item type
	 * @throws CGSystemException the cG system exception
	 */
	List<ItemDO> getItemListByItemType(Integer itemTypeId)throws CGSystemException;
	
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
	 * Gets the item by item id.
	 *
	 * @param itemId the item id
	 * @return the item by item id
	 * @throws CGSystemException the cG system exception
	 */
	ItemDO getItemByItemId(Integer itemId) throws CGSystemException;
	
	/**
	 * Gets the item details.
	 *
	 * @return the item details
	 * @throws CGSystemException the cG system exception
	 */
	List<?> getItemDetails()throws CGSystemException;

	/**
	 * Gets the max number from process.
	 *
	 * @param seqGeneratorTo the seq generator to
	 * @param queryName the query name
	 * @return the max number from process
	 * @throws CGSystemException the cG system exception
	 */
	String getMaxNumberFromProcess(SequenceGeneratorConfigTO seqGeneratorTo, String queryName)
			throws CGSystemException;

	/**
	 * Gets the stock standard types.
	 *
	 * @param typeName the type name
	 * @return the stock standard types
	 * @throws CGSystemException the cG system exception
	 */
	List<StockStandardTypeDO> getStockStandardTypes(String typeName)
			throws CGSystemException;

	/**
	 * Gets the stock standard types as map.
	 *
	 * @param typeName the type name
	 * @return the stock standard types as map
	 * @throws CGSystemException the cG system exception
	 */
	List<?> getStockStandardTypesAsMap(String typeName)
			throws CGSystemException;

	/**
	 * Gets the issue type.
	 *
	 * @param validationTo the validation to
	 * @return the issue type
	 * @throws CGSystemException the cG system exception
	 */
	String getIssueType(StockValidationTO validationTo) throws CGSystemException;

	/**
	 * Checks if is series transfer to party type.
	 *
	 * @param Qryname the qryname
	 * @param validationTo the validation to
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	List<Long> isSeriesTransferTOPartyType(String Qryname, StockValidationTO validationTo)
			throws CGSystemException;

	/**
	 * Checks if is series returned to plant.
	 *
	 * @param validationTo the validation to
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	List<Long> isSeriesReturnedTOPlant(StockValidationTO validationTo)
			throws CGSystemException;
	
	/**
	 * Checks if is series returned from plant.
	 *
	 * @param validationTo the validation to
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	List<Long> isSeriesReturnedFromPlant(StockValidationTO validationTo)
			throws CGSystemException;

	/**
	 * Checks if is series issued with issue number.
	 *
	 * @param validationTO the validation to
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	List<Long> isSeriesIssuedWithIssueNumber(StockValidationTO validationTO)
			throws CGSystemException;

	/**
	 * Checks if is series issued with issue number with dtls id.
	 *
	 * @param validationTO the validation to
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	List<Long> isSeriesIssuedWithIssueNumberWithDtlsId(
			StockValidationTO validationTO) throws CGSystemException;

	/**
	 * Checks if is series received with issue number.
	 *
	 * @param validationTo the validation to
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean isSeriesReceivedWithIssueNumber(StockValidationTO validationTo)
			throws CGSystemException;

	List<Long> isSeriesIssuedFromBranchForTransfer(
			StockValidationTO validationTO) throws CGSystemException;

	/**
	 * Checks if is issue number exist for office.
	 *
	 * @param issueNumber the issue number
	 * @param officeId the office id
	 * @return the long
	 * @throws CGSystemException the cG system exception
	 */
	String isIssueNumberExistForOffice(String issueNumber, Integer officeId)
			throws CGSystemException;

	Boolean isSeriesReturnedTOPlantByLeaf(StockValidationTO validationTo)
			throws CGSystemException;

	/**
	 * Checks if is series transfer to party type by leaf.
	 *
	 * @param Qryname the qryname
	 * @param to the to
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean isSeriesTransferTOPartyTypeByLeaf(String Qryname,
			StockValidationTO to) throws CGSystemException;

	/**
	 * Checks if is series returned from plant by leaf.
	 *
	 * @param validationTO the validation to
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean isSeriesReturnedFromPlantByLeaf(StockValidationTO validationTO)
			throws CGSystemException;

	List<Long> isSeriesReceivedWithReceiptNumber(StockValidationTO validationTO)
			throws CGSystemException;

	List<Long> isSeriesReceivedWithReceiptNumberWithDtlsId(
			StockValidationTO validationTO) throws CGSystemException;

	/**
	 * Checks if is series received with latest receipt number.
	 *
	 * @param validationTO the validation to
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	List<Long> isSeriesReceivedWithLatestReceiptNumber(
			StockValidationTO validationTO) throws CGSystemException;

	/**
	 * To get max TXN for stock consolidation
	 * @param numberPrefix TODO
	 * @param runningNumberLength TODO
	 * @param officeId TODO
	 * @return numberList
	 * @throws CGSystemException
	 */
	String getMaxTxNoForStockConsolidation(String numberPrefix, Integer runningNumberLength, Integer officeId) throws CGSystemException;

	/**
	 * Gets the latest stock details by leaf for issue.
	 *
	 * @param validationTO the validation to
	 * @return the latest stock details by leaf for issue
	 * @throws CGSystemException the CG system exception
	 */
	StockHolderWrapperDO getLatestStockDetailsByLeafForIssue(
			StockValidationTO validationTO) throws CGSystemException;

	String getLatestStockDetailsByLeafForIssue1(StockValidationTO validationTO)
			throws CGSystemException;
	
}
