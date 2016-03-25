/**
 * 
 */
package com.ff.admin.stockmanagement.common.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.ff.business.CustomerTO;
import com.ff.domain.stockmanagement.masters.ItemDO;
import com.ff.geography.CityTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.to.ratemanagement.operations.StockRateTO;
import com.ff.to.stockmanagement.masters.ItemTO;
import com.ff.to.stockmanagement.masters.ItemTypeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.to.stockmanagement.stockrequisition.StockValidationTO;

/**
 * The Interface StockCommonService.
 * 
 * @author mohammes
 */
public interface StockCommonService {

	/**
	 * Gets the item type list.
	 * 
	 * @return the item type list
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	List<ItemTypeTO> getItemTypeList() throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the item list by item type.
	 * 
	 * @param itemTypeId
	 *            the item type id
	 * @return the item list by item type
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	List<ItemTO> getItemListByItemType(Integer itemTypeId)
			throws CGSystemException, CGBusinessException;

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
	 * Gets the item by item id.
	 * 
	 * @param itemId
	 *            the item id
	 * @return the item by item id
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	ItemTO getItemByItemId(Integer itemId) throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the item dtls as map.
	 * 
	 * @return the item dtls as map
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Map<Integer, String> getItemDtlsAsMap() throws CGSystemException,
			CGBusinessException;

	/**
	 * Stock process number generator.
	 * 
	 * @param to
	 *            the to
	 * @return the string
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	String stockProcessNumberGenerator(SequenceGeneratorConfigTO to)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the stock standard types as map.
	 * 
	 * @param typeName
	 *            the type name
	 * @return the stock standard types as map
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Map<String, String> getStockStandardTypesAsMap(String typeName)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the stock standard types.
	 * 
	 * @param typeName
	 *            the type name
	 * @return the stock standard types
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	List<StockStandardTypeTO> getStockStandardTypes(String typeName)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the all franchisees.
	 * 
	 * @param franchiseeTo
	 *            the franchisee to
	 * @return the all franchisees
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	List<CustomerTO> getAllFranchisees(CustomerTO franchiseeTo)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the all business associates.
	 * 
	 * @param baTo
	 *            the ba to
	 * @return the all business associates
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	List<CustomerTO> getAllBusinessAssociates(CustomerTO baTo)
			throws CGSystemException, CGBusinessException;

	/**
	 * Checks if is series valid for issue.
	 * 
	 * @param to
	 *            the to
	 * @return the string
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	String isSeriesValidForIssue(StockValidationTO to)
			throws CGSystemException, CGBusinessException;

	/**
	 * Checks if is series transferred to party type.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the boolean
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Boolean isSeriesTransferredTOPartyType(StockValidationTO validationTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Checks if is series valid for stock return.
	 * 
	 * @param to
	 *            the to
	 * @return the string
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	String isSeriesValidForStockReturn(StockValidationTO to)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the stock quantity by item and party type.
	 * 
	 * @param partyType
	 *            the party type
	 * @param partyTypeId
	 *            the party type id
	 * @param itemId
	 *            the item id
	 * @return the stock quantity by item and party type
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Integer getStockQuantityByItemAndPartyType(String partyType,
			Integer partyTypeId, Integer itemId) throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the all item details.
	 * 
	 * @param itemTO
	 *            the item to
	 * @return the all item details
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	List<ItemTO> getAllItemDetails(ItemTO itemTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the all cnote item details.
	 * 
	 * @param itemTypeCode
	 *            the item type code
	 * @return the all cnote item details
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Map<Integer, String> getAllCnoteItemDetails(String itemTypeCode)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the all employee for issue.
	 * 
	 * @param empTO
	 *            the emp to
	 * @return the all employee for issue
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	List<EmployeeTO> getAllEmployeeForIssue(EmployeeTO empTO)
			throws CGSystemException, CGBusinessException;

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
	 * Gets the all employee dtls by logged in office.
	 * 
	 * @param officeId
	 *            the office id
	 * @return the all employee dtls by logged in office
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Map<Integer, String> getAllEmployeeDtlsByLoggedInOffice(Integer officeId)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the all customer dtls by logged in office.
	 * 
	 * @param officeId
	 *            the office id
	 * @return the all customer dtls by logged in office
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Map<Integer, String> getAllCustomerDtlsByLoggedInOffice(Integer officeId)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the stock transfer from standard types as map.
	 * 
	 * @return the stock transfer from standard types as map
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Map<String, String> getStockTransferFromStandardTypesAsMap()
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the stock transfer to standard types as map.
	 * 
	 * @return the stock transfer to standard types as map
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Map<String, String> getStockTransferTOStandardTypesAsMap()
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the all business associates by logged in office.
	 * 
	 * @param loggedInOffice
	 *            the logged in office
	 * @param officeType TODO
	 * @return the all business associates by logged in office
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Map<Integer, String> getAllBusinessAssociatesByLoggedInOffice(
			Integer loggedInOffice, String officeType) throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the party type details by party type.
	 * 
	 * @param partyType
	 *            the party type
	 * @param loggedInOffice
	 *            the logged in office
	 * @param officeType TODO
	 * @return the party type details by party type
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Map<Integer, String> getPartyTypeDetailsByPartyType(String partyType,
			Integer loggedInOffice, String officeType) throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the all serial number item map.
	 * 
	 * @return the all serial number item map
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Map<Integer, String> getAllSerialNumberItemMap() throws CGSystemException,
			CGBusinessException;

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
	 * Gets the item id by item series.
	 * 
	 * @param series
	 *            the series
	 * @return the item id by item series
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Integer getItemIdByItemSeries(String series) throws CGSystemException,
			CGBusinessException;

	/**
	 * Checks if is series valid for transfer.
	 * 
	 * @param to
	 *            the to
	 * @return the string
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	String isSeriesValidForTransfer(StockValidationTO to)
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
	 * Checks if is series cancelled.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the boolean
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Boolean isSeriesCancelled(StockValidationTO validationTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Checks if is series issued with issue number.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the boolean
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Boolean isSeriesIssuedWithIssueNumber(StockValidationTO validationTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Checks if is series issued with issue number with dtls id.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the boolean
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Boolean isSeriesIssuedWithIssueNumberWithDtlsId(
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
	 * Gets the all employee dtls emp to.
	 * 
	 * @param empTO
	 *            the emp to
	 * @return the all employee dtls emp to
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Map<Integer, String> getAllEmployeeDtlsEmpTO(EmployeeTO empTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the city by id.
	 * 
	 * @param cityId
	 *            the city id
	 * @param cityCode TODO
	 * @return the city by id
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	CityTO getCityById(Integer cityId, String cityCode) throws CGSystemException,
			CGBusinessException;

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
	 * Send email by plain text.
	 * 
	 * @param from
	 *            the from
	 * @param to
	 *            the to
	 * @param subject
	 *            the subject
	 * @param body
	 *            the body
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public void sendEmail(MailSenderTO emailTO) throws CGBusinessException,
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
	 * Gets the customer list by logged in office.
	 * 
	 * @param officeId
	 *            the office id
	 * @return the customer list by logged in office
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	List<CustomerTO> getCustomerListByLoggedInOffice(Integer officeId)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the customer detils.
	 * 
	 * @param customerTO
	 *            the customer to
	 * @return the customer detils
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	CustomerTO getCustomerDetils(CustomerTO customerTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the all customer by logged in office.
	 * 
	 * @param officeId
	 *            the office id
	 * @param officeType TODO
	 * @return the all customer by logged in office
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	List<CustomerTO> getAllCustomerByLoggedInOffice(Integer officeId, String officeType)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the stock customer dtls by logged in office.
	 * 
	 * @param officeId
	 *            the office id
	 * @param officeType TODO
	 * @return the stock customer dtls by logged in office
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Map<Integer, String> getStockCustomerDtlsByLoggedInOffice(Integer officeId, String officeType)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the customer fr and ba details by party type.
	 * 
	 * @param partyType
	 *            the party type
	 * @param loggedInOffice
	 *            the logged in office
	 * @param officeType TODO
	 * @return the customer fr and ba details by party type
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	List<CustomerTO> getCustomerFrAndBADetailsByPartyType(String partyType,
			Integer loggedInOffice, String officeType) throws CGSystemException,
			CGBusinessException;

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
	 * Checks if is issue number exist for office.
	 * 
	 * @param issueNumber
	 *            the issue number
	 * @param officeId
	 *            the office id
	 * @return the long
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	String isIssueNumberExistForOffice(String issueNumber, Integer officeId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Checks if is series valid for stock issue.
	 * 
	 * @param to
	 *            the to
	 * @return the string
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	String isSeriesValidForStockIssue(StockValidationTO to)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the corporate office for stock.
	 * 
	 * @return the corporate office for stock
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	OfficeTO getCorporateOfficeForStock() throws CGBusinessException,
			CGSystemException;

	/**
	 * Checks if is series valid for stock return from branch.
	 * 
	 * @param to
	 *            the to
	 * @return the string
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	String isSeriesValidForStockReturnFromBranch(StockValidationTO to)
			throws CGSystemException, CGBusinessException;

	/**
	 * Checks if is series received with receipt number.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the boolean
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Boolean isSeriesReceivedWithReceiptNumber(StockValidationTO validationTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Checks if is series received with receipt number with dtls id.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the boolean
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Boolean isSeriesReceivedWithReceiptNumberWithDtlsId(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * Checks if is series received with latest receipt number.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the boolean
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Boolean isSeriesReceivedWithLatestReceiptNumber(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException;

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

	/**
	 * To generate transaction number
	 * @param prefixNumber TODO
	 * @param runningNumberlength TODO
	 * @param officeId TODO
	 * @return generatedTxNo
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	String getMaxTxNoForStockConsolidation(String prefixNumber, Integer runningNumberlength, Integer officeId) throws CGBusinessException, CGSystemException;

	/**
	 * Gets the office dtls by office code.
	 *
	 * @param officeCode the office code
	 * @return the office dtls by office code
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	OfficeTO getOfficeDtlsByOfficeCode(String officeCode)
			throws CGBusinessException, CGSystemException;

}
