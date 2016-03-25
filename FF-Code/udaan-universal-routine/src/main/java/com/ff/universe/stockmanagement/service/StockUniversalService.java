/**
 * 
 */
package com.ff.universe.stockmanagement.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.ff.business.CustomerTO;
import com.ff.domain.stockmanagement.masters.ItemDO;
import com.ff.geography.CityTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.PaymentModeTO;
import com.ff.to.ratemanagement.operations.StockRateTO;
import com.ff.to.stockmanagement.StockIssueValidationTO;
import com.ff.to.stockmanagement.StockSearchInputTO;
import com.ff.to.stockmanagement.StockUpdateInputTO;
import com.ff.to.stockmanagement.masters.ItemTO;
import com.ff.to.stockmanagement.stockrequisition.StockValidationTO;

/**
 * The Interface StockUniversalService.
 * 
 * @author mohammes
 */
/**
 * @author hkansagr
 * 
 */
public interface StockUniversalService {

	/**
	 * Checks if is series issued.
	 * 
	 * @param validationTo
	 *            the validation to
	 * @return the stock issue validation to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	StockIssueValidationTO isSeriesIssued(StockIssueValidationTO validationTo)
			throws CGBusinessException, CGSystemException;

	/**
	 * Checks if is series cancelled.
	 * 
	 * @param validationTo
	 *            the validation to
	 * @return the boolean
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	Boolean isSeriesCancelled(StockValidationTO validationTo)
			throws CGBusinessException, CGSystemException;

	/**
	 * Checks if is series acknowledged.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the boolean
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	Boolean isSeriesAcknowledged(StockValidationTO validationTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Checks if is series returned to plant.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the boolean
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	Boolean isSeriesReturnedToPlant(StockValidationTO validationTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Checks if is series returned from plant.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the boolean
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	Boolean isSeriesReturnedFromPlant(StockValidationTO validationTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Checks if is series issued to party type.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the list
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	List<Long> isSeriesIssuedToPartyType(StockValidationTO validationTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Checks if is series already issued from branch.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the list
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	List<Long> isSeriesAlreadyIssuedFromBranch(StockValidationTO validationTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the latest series issue date for branch.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the latest series issue date for branch
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Date getLatestSeriesIssueDateForBranch(StockValidationTO validationTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the latest series return from date for branch.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the latest series return from date for branch
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Date getLatestSeriesReturnFromDateForBranch(StockValidationTO validationTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the latest series return to date for branch.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the latest series return to date for branch
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Date getLatestSeriesReturnTODateForBranch(StockValidationTO validationTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Checks if is series consumed.
	 * 
	 * @param validationTo
	 *            the validation to
	 * @return the boolean
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Boolean isSeriesConsumed(StockValidationTO validationTo)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the latest series transfer to date for branch.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the latest series transfer to date for branch
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Date getLatestSeriesTransferTODateForBranch(StockValidationTO validationTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the stock quantity by item and party type.
	 * 
	 * @param searchTO
	 *            the search to
	 * @return the stock quantity by item and party type
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Integer getStockQuantityByItemAndPartyType(StockSearchInputTO searchTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Update stock by party type.
	 * 
	 * @param searchTO
	 *            the search to
	 * @return the boolean
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	Boolean updateStockByPartyType(StockUpdateInputTO searchTO)
			throws CGSystemException;

	/**
	 * Gets the all items by type.
	 * 
	 * @param itemTO
	 *            the item to
	 * @return the all items by type
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	List<ItemTO> getAllItemsByType(ItemTO itemTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the all customer for issue.
	 * 
	 * @param customerTO
	 *            the customer to
	 * @return the all customer for issue
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	List<CustomerTO> getAllCustomerForIssue(CustomerTO customerTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the all employee for issue.
	 * 
	 * @param employeeTo
	 *            the employee to
	 * @return the all employee for issue
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	List<EmployeeTO> getAllEmployeeForIssue(EmployeeTO employeeTo)
			throws CGSystemException, CGBusinessException;

	/**
	 * Validate stock.
	 * 
	 * @param validationTo
	 *            the validation to
	 * @return the stock issue validation to
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	StockIssueValidationTO validateStock(StockIssueValidationTO validationTo)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the item details by item series.
	 * 
	 * @param series
	 *            the series
	 * @return the item details by item series
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	ItemTO getItemDetailsByItemSeries(String series) throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the latest date with series.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the latest date with series
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Map<Long, Date> getLatestDateWithSeries(StockValidationTO validationTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Checks if is series transferred from party type.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the list
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	List<Long> isSeriesTransferredFromPartyType(StockValidationTO validationTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Checks if is series exist in branch.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the boolean
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	Boolean isSeriesExistInBranch(StockValidationTO validationTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Checks if is series available with transfer from party type.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the list
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	List<Long> isSeriesAvailableWithTransferFromPartyType(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * Checks if is series exist at transferred to party type.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the list
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	List<Long> isSeriesExistAtTransferredTOPartyType(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the office details.
	 * 
	 * @param officeId
	 *            the office id
	 * @return the office details
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	OfficeTO getOfficeDetails(Integer officeId) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the latest date with series in receipt.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the latest date with series in receipt
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Map<Long, Date> getLatestDateWithSeriesInReceipt(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the latest date with series in return.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the latest date with series in return
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Map<Long, Date> getLatestDateWithSeriesInReturn(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the city.
	 * 
	 * @param cityTO
	 *            the city to
	 * @return the city
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	CityTO getCity(CityTO cityTO) throws CGSystemException, CGBusinessException;

	/**
	 * Gets the item length by material.
	 * 
	 * @param itemCode
	 *            the item code
	 * @param product
	 *            the product
	 * @return the item length by material
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	Integer getItemLengthByMaterial(String itemCode, String product)
			throws CGBusinessException, CGSystemException;

	/**
	 * Checks if is series cancelled for integration.
	 * 
	 * @param validationTo
	 *            the validation to
	 * @return the long
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public Boolean isSeriesCancelledForIntegration(
			StockIssueValidationTO validationTo) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the latest transfer date by serial number.
	 * 
	 * @param serialNumber
	 *            the serial number
	 * @return the latest transfer date by serial number
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public Date getLatestTransferDateBySerialNumber(String serialNumber)
			throws CGBusinessException, CGSystemException;

	Date getLatestIssuedDateBySerialNumber(String serialNumber)
			throws CGBusinessException, CGSystemException;

	Date getLatestReturnedDateBySerialNumber(String serialNumber)
			throws CGBusinessException, CGSystemException;

	/**
	 * Send email.
	 * 
	 * @param emailTO
	 *            the email to
	 * @throws CGBusinessException
	 *             ,CGSystemException
	 */
	void sendEmail(MailSenderTO emailTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the office under office id as map.
	 * 
	 * @param officeId
	 *            the office id
	 * @return the office under office id as map
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	Map<Integer, String> getOfficeUnderOfficeIdAsMap(Integer officeId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the latest transfer date with series.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the latest transfer date with series
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Map<Long, Date> getLatestTransferDateWithSeries(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the latest date with series in return to rho.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the latest date with series in return to rho
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Map<Long, Date> getLatestDateWithSeriesInReturnToRHO(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the item type as map.
	 * 
	 * @return the item type as map
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Map<Integer, String> getItemTypeAsMap() throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the item by type as map.
	 * 
	 * @param itemTypeId
	 *            the item type id
	 * @return the item by type as map
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Map<Integer, String> getItemByTypeAsMap(Integer itemTypeId)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the item by item type and item id.
	 * 
	 * @param itemTypeId
	 *            the item type id
	 * @param itemId
	 *            the item id
	 * @return the item by item type and item id
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	ItemTO getItemByItemTypeAndItemId(Integer itemTypeId, Integer itemId)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the latest receipt date by serial number.
	 * 
	 * @param serialNumber
	 *            the serial number
	 * @return the latest receipt date by serial number
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	Date getLatestReceiptDateBySerialNumber(String serialNumber)
			throws CGBusinessException, CGSystemException;

	/**
	 * Checks if is series consumed for stock.
	 * 
	 * @param validationTo
	 *            the validation to
	 * @return the boolean
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Boolean isSeriesConsumedForStock(StockValidationTO validationTo)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the latest date with series in receipt without office.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the latest date with series in receipt without office
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Map<Long, Date> getLatestDateWithSeriesInReceiptWithoutOffice(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the latest date with series in return without office.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the latest date with series in return without office
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Map<Long, Date> getLatestDateWithSeriesInReturnWithoutOffice(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * Calculate rate for ba material.
	 * 
	 * @param stockRateTo
	 *            the stock rate to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	void calculateRateForBAMaterial(StockRateTO stockRateTo)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the payment mode dtls.
	 * 
	 * @param processCode
	 *            the process code
	 * @return the payment mode dtls
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<PaymentModeTO> getPaymentModeDtls(String processCode)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the contract customer list for stock.
	 * 
	 * @param customerTO
	 *            the customer to
	 * @return the contract customer list for stock
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	List<CustomerTO> getContractCustomerListForStock(CustomerTO customerTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the tax components.
	 * 
	 * @param cityTo
	 *            the city to
	 * @param taxDate
	 *            the tax date
	 * @return the tax components
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	Map<String, Double> getTaxComponents(CityTO cityTo, Date taxDate)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the config param value by name.
	 * 
	 * @param paramName
	 *            the param name
	 * @return the config param value by name
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	String getConfigParamValueByName(String paramName)
			throws CGBusinessException, CGSystemException;

	/**
	 * Checks if is series exist in branch by leaf.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the boolean
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	Boolean isSeriesExistInBranchByLeaf(StockValidationTO validationTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Checks if is series already issued from branch by leaf.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the boolean
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Boolean isSeriesAlreadyIssuedFromBranchByLeaf(StockValidationTO validationTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the latest transfer date by leaf.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the latest transfer date by leaf
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Date getLatestTransferDateByLeaf(StockValidationTO validationTO)
			throws CGSystemException, CGBusinessException;

	Date getLatestStockIssueDateWithLeaf(StockValidationTO validationTO)
			throws CGSystemException, CGBusinessException;

	Date getLatestReceiptDateByLeaf(StockValidationTO validationTO)
			throws CGSystemException, CGBusinessException;

	Date getLatestStockReturnDateByLeaf(StockValidationTO validationTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the latest receipt date by leaf without office.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the latest receipt date by leaf without office
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Date getLatestReceiptDateByLeafWithoutOffice(StockValidationTO validationTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the offices by office.
	 * 
	 * @param officeTO
	 *            the office to
	 * @return the offices by office
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<OfficeTO> getOfficesByOffice(OfficeTO officeTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get item details by material number
	 * 
	 * @param materialNo
	 * @return itemDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	ItemDO getItemDtlsByMaterialNumber(String materialNo)
			throws CGBusinessException, CGSystemException;

}
