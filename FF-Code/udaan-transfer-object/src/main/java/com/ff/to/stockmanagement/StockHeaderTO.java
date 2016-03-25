/**
 * 
 */
package com.ff.to.stockmanagement;

import java.util.Map;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.capgemini.lbs.framework.utils.StringUtil;

/**
 * The Class StockHeaderTO.
 *
 * @author mohammes
 */
public abstract class StockHeaderTO extends CGBaseTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1354356456L;
	
	/** The row count. */
	public int rowCount;
	
	/** The row item type id. */
	private  Integer  rowItemTypeId [] = new Integer[rowCount];
	
	/** The row item id. */
	private Integer rowItemId[] = new Integer[rowCount];
	
	/** The row number. */
	private  Integer rowNumber[] = new Integer[rowCount];
	
	/** The row uom. */
	private  String rowUom [] = new String[rowCount];
	
	/** The row remarks. */
	private String rowRemarks [] = new String[rowCount];
	
	/** The row approve remarks. */
	private String rowApproveRemarks[] = new String[rowCount];
	
	/** The row requested quantity. */
	private  Integer rowRequestedQuantity[] = new Integer[rowCount];
	
	/** The row approved quantity. */
	private Integer rowApprovedQuantity[] = new Integer[rowCount];
	
	/** The row description. */
	private String rowDescription[] = new String[rowCount];
	
	/** The row issued quantity. */
	private Integer rowIssuedQuantity[] = new Integer[rowCount];
	
	/** The row start serial number. */
	private String rowStartSerialNumber[] = new String[rowCount];
	
	/** The row end serial number. */
	private String rowEndSerialNumber[] = new String[rowCount];
	
	/** The checkbox. */
	private Integer checkbox[] = new Integer[rowCount];
	
	/** The row trans create date. */
	private String rowTransCreateDate[] = new String[rowCount];
	
	/** The row receiving quantity. */
	private Integer rowReceivingQuantity[] = new Integer[rowCount];
	
	/** The row returning quantity. */
	private Integer rowReturningQuantity[] = new Integer[rowCount];
	
	/** The row series. */
	private String rowSeries[] = new String[rowCount];
	
	/** The row series length. */
	private Integer rowSeriesLength[] = new Integer[rowCount];
	
	/** The row is item has series. */
	private String rowIsItemHasSeries[] = new String[rowCount];
	
	/** The row stock item dtls id. */
	private Long rowStockItemDtlsId[]= new Long[rowCount];
	
	/** The row current stock quantity. */
	private Integer rowCurrentStockQuantity[] = new Integer[rowCount];
	
	/** The row balance quantity. */
	private Integer rowBalanceQuantity[] = new Integer[rowCount];
	
	/**   it holds text which represents whether series of which type like CNote/stickers..ets */
	private  String  rowSeriesType [] = new String[rowCount];
	
	/** The row office product. */
	private  String  rowOfficeProduct [] = new String[rowCount];
	
	/** The row start leaf. */
	private  Long  rowStartLeaf [] = new Long[rowCount];
	
	/** The row end leaf. */
	private  Long rowEndLeaf [] = new Long[rowCount];
	
	private Integer rowBalanceReturnQuantity[]=new Integer[rowCount];
	
	
	/** The no series. */
	private String noSeries;
	
	/** The logged in office code. */
	private String loggedInOfficeCode;
	
	/** The issued to branch. */
	private String issuedToBranch;
	
	/** The issued to fr. */
	private String issuedToFr;
	
	/** The issued to emp. */
	private String issuedToEmp;
	
	/** The issued to ba. */
	private String issuedToBa;
	
	/** The issued to customer. */
	private String issuedToCustomer;
	
	/** The ba allowed series. */
	private String baAllowedSeries;
	
	/** The emp allowed series. */
	private String empAllowedSeries;
	
	/** The credit cust allowed series. */
	private String creditCustAllowedSeries;
	
	/** The acc cust allowed series. */
	private String accCustAllowedSeries;
	
	/** The franchisee allowed series. */
	private String franchiseeAllowedSeries;
	
	private String creditCardCustomerAllowedSeries;
	private String lcCustomerAllowedSeries;
	private String codCustomerAllowedSeries;
	private String govtEntityCustomerAllowedSeries;
	private String reverseLogCustomerAllowedSeries;
	
	private String customerType;
	
	private String creditCustomerType;
	private String accCustomerType;
	
	private String creditCardCustomerType;
	private String lcCustomerType;
	private String codCustomerType;

	private String govtEntityCustomerType;
	private String reverseLogCustomerType;
	
	private String normalCnoteIdentifier;
	
	//Material type category **** useful for Serial number validations/at the time of saving
	/** The consignment type. */
	private String consignmentType;
	
	/** The ogm stickers. */
	private String ogmStickers;
	
	/** The bpl stickers. */
	private String bplStickers;
	
	/** The mbpl stickers. */
	private String mbplStickers;
	
	/** The comail number. */
	private String comailNumber;
	
	/** The bag loc number. */
	private String bagLocNumber;
	
	
	/** The region code. */
	private String regionCode;
	
	/** The rho office code. */
	private String rhoOfficeCode;
	
	/** The city code. */
	private String cityCode;//for series validations
	
	/** The transaction from type. */
	private String transactionFromType;//it holds information from which table data is fetching
	
	/** The transaction pr type. */
	private String transactionPRType;
	
	/** The transaction issue type. */
	private String transactionIssueType;
	
	/** The transaction return type. */
	private String transactionReturnType;
	
	/** The transaction receipt type. */
	private String transactionReceiptType;
	
	/** The can update. */
	private  String canUpdate;
	
	/** The approve flag yes. */
	private String approveFlagYes;
	
	private String officeType;
	public String officeRhoType;
	public String officeTypeCo;
	
	public Map<Integer,String> itemMap;
	private String shippedToCode;	
	
	/** The user info. For Logging Purpose */
	private String userInfo;
	
	private String todayDate;//for Future date validations
	
	private String requisitionNumber;
	private String stockIssueNumber;
	
	private String acknowledgementNumber;
	private String stockReturnNumber;
	
	/**
	 * Gets the row receiving quantity.
	 *
	 * @return the rowReceivingQuantity
	 */
	public Integer[] getRowReceivingQuantity() {
		return rowReceivingQuantity;
	}
	
	/**
	 * Sets the row receiving quantity.
	 *
	 * @param rowReceivingQuantity the rowReceivingQuantity to set
	 */
	public void setRowReceivingQuantity(Integer[] rowReceivingQuantity) {
		this.rowReceivingQuantity = rowReceivingQuantity;
	}
	
	/**
	 * Gets the row returning quantity.
	 *
	 * @return the rowReturningQuantity
	 */
	public Integer[] getRowReturningQuantity() {
		return rowReturningQuantity;
	}
	
	/**
	 * Sets the row returning quantity.
	 *
	 * @param rowReturningQuantity the rowReturningQuantity to set
	 */
	public void setRowReturningQuantity(Integer[] rowReturningQuantity) {
		this.rowReturningQuantity = rowReturningQuantity;
	}
	
	/**
	 * Gets the row count.
	 *
	 * @return the row count
	 */
	public int getRowCount() {
		return rowCount;
	}
	
	/**
	 * Sets the row count.
	 *
	 * @param rowCount the new row count
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	
	/**
	 * Gets the row item type id.
	 *
	 * @return the row item type id
	 */
	public Integer[] getRowItemTypeId() {
		return rowItemTypeId;
	}
	
	/**
	 * @return the empAllowedSeries
	 */
	public String getEmpAllowedSeries() {
		return empAllowedSeries;
	}

	

	/**
	 * @return the customerType
	 */
	public String getCustomerType() {
		return customerType;
	}

	/**
	 * @param customerType the customerType to set
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 * @param empAllowedSeries the empAllowedSeries to set
	 */
	public void setEmpAllowedSeries(String empAllowedSeries) {
		this.empAllowedSeries = empAllowedSeries;
	}


	/**
	 * Sets the row item type id.
	 *
	 * @param rowItemTypeId the new row item type id
	 */
	public void setRowItemTypeId(Integer[] rowItemTypeId) {
		this.rowItemTypeId = rowItemTypeId;
	}
	
	/**
	 * Gets the row item id.
	 *
	 * @return the row item id
	 */
	public Integer[] getRowItemId() {
		return rowItemId;
	}
	
	/**
	 * Sets the row item id.
	 *
	 * @param rowItemId the new row item id
	 */
	public void setRowItemId(Integer[] rowItemId) {
		this.rowItemId = rowItemId;
	}
	
	/**
	 * Gets the row number.
	 *
	 * @return the row number
	 */
	public Integer[] getRowNumber() {
		return rowNumber;
	}
	
	/**
	 * Sets the row number.
	 *
	 * @param rowNumber the new row number
	 */
	public void setRowNumber(Integer[] rowNumber) {
		this.rowNumber = rowNumber;
	}
	
	/**
	 * Gets the row uom.
	 *
	 * @return the row uom
	 */
	public String[] getRowUom() {
		return rowUom;
	}
	
	/**
	 * Sets the row uom.
	 *
	 * @param rowUom the new row uom
	 */
	public void setRowUom(String[] rowUom) {
		this.rowUom = rowUom;
	}
	
	/**
	 * Gets the row remarks.
	 *
	 * @return the row remarks
	 */
	public String[] getRowRemarks() {
		return rowRemarks;
	}
	
	/**
	 * Sets the row remarks.
	 *
	 * @param rowRemarks the new row remarks
	 */
	public void setRowRemarks(String[] rowRemarks) {
		this.rowRemarks = rowRemarks;
	}
	
	/**
	 * Gets the row approve remarks.
	 *
	 * @return the row approve remarks
	 */
	public String[] getRowApproveRemarks() {
		return rowApproveRemarks;
	}
	
	/**
	 * @return the officeTypeCo
	 */
	public String getOfficeTypeCo() {
		return officeTypeCo;
	}

	/**
	 * @param officeTypeCo the officeTypeCo to set
	 */
	public void setOfficeTypeCo(String officeTypeCo) {
		this.officeTypeCo = officeTypeCo;
	}

	/**
	 * Sets the row approve remarks.
	 *
	 * @param rowApproveRemarks the new row approve remarks
	 */
	public void setRowApproveRemarks(String[] rowApproveRemarks) {
		this.rowApproveRemarks = rowApproveRemarks;
	}
	
	/**
	 * Gets the row requested quantity.
	 *
	 * @return the row requested quantity
	 */
	public Integer[] getRowRequestedQuantity() {
		return rowRequestedQuantity;
	}
	
	/**
	 * Sets the row requested quantity.
	 *
	 * @param rowRequestedQuantity the new row requested quantity
	 */
	public void setRowRequestedQuantity(Integer[] rowRequestedQuantity) {
		this.rowRequestedQuantity = rowRequestedQuantity;
	}
	
	/**
	 * Gets the row approved quantity.
	 *
	 * @return the row approved quantity
	 */
	public Integer[] getRowApprovedQuantity() {
		return rowApprovedQuantity;
	}
	
	/**
	 * Sets the row approved quantity.
	 *
	 * @param rowApprovedQuantity the new row approved quantity
	 */
	public void setRowApprovedQuantity(Integer[] rowApprovedQuantity) {
		this.rowApprovedQuantity = rowApprovedQuantity;
	}
	
	/**
	 * @return the shippedToCode
	 */
	public String getShippedToCode() {
		return shippedToCode;
	}

	/**
	 * @param shippedToCode the shippedToCode to set
	 */
	public void setShippedToCode(String shippedToCode) {
		this.shippedToCode = shippedToCode;
	}

	/**
	 * Gets the row description.
	 *
	 * @return the row description
	 */
	public String[] getRowDescription() {
		return rowDescription;
	}
	
	/**
	 * Sets the row description.
	 *
	 * @param rowDescription the new row description
	 */
	public void setRowDescription(String[] rowDescription) {
		this.rowDescription = rowDescription;
	}
	
	/**
	 * Gets the checkbox.
	 *
	 * @return the checkbox
	 */
	public Integer[] getCheckbox() {
		return checkbox;
	}
	
	/**
	 * Sets the checkbox.
	 *
	 * @param checkbox the new checkbox
	 */
	public void setCheckbox(Integer[] checkbox) {
		this.checkbox = checkbox;
	}
	
	/**
	 * Gets the row trans create date.
	 *
	 * @return the row trans create date
	 */
	public String[] getRowTransCreateDate() {
		return rowTransCreateDate;
	}
	
	/**
	 * Sets the row trans create date.
	 *
	 * @param rowTransCreateDate the new row trans create date
	 */
	public void setRowTransCreateDate(String[] rowTransCreateDate) {
		this.rowTransCreateDate = rowTransCreateDate;
	}
	
	/**
	 * Gets the row start serial number.
	 *
	 * @return the row start serial number
	 */
	public String[] getRowStartSerialNumber() {
		return rowStartSerialNumber;
	}
	
	/**
	 * @return the userInfo
	 */
	public String getUserInfo() {
		return userInfo;
	}

	/**
	 * @param userInfo the userInfo to set
	 */
	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * Sets the row start serial number.
	 *
	 * @param rowStartSerialNumber the new row start serial number
	 */
	public void setRowStartSerialNumber(String[] rowStartSerialNumber) {
		this.rowStartSerialNumber = rowStartSerialNumber;
	}
	
	/**
	 * Gets the row end serial number.
	 *
	 * @return the row end serial number
	 */
	public String[] getRowEndSerialNumber() {
		return rowEndSerialNumber;
	}
	
	/**
	 * Sets the row end serial number.
	 *
	 * @param rowEndSerialNumber the new row end serial number
	 */
	public void setRowEndSerialNumber(String[] rowEndSerialNumber) {
		this.rowEndSerialNumber = rowEndSerialNumber;
	}
	
	/**
	 * Gets the row issued quantity.
	 *
	 * @return the row issued quantity
	 */
	public Integer[] getRowIssuedQuantity() {
		return rowIssuedQuantity;
	}
	
	/**
	 * Sets the row issued quantity.
	 *
	 * @param rowIssuedQuantity the new row issued quantity
	 */
	public void setRowIssuedQuantity(Integer[] rowIssuedQuantity) {
		this.rowIssuedQuantity = rowIssuedQuantity;
	}
	
	/**
	 * Gets the row series.
	 *
	 * @return the row series
	 */
	public String[] getRowSeries() {
		return rowSeries;
	}
	
	/**
	 * Sets the row series.
	 *
	 * @param rowSeries the new row series
	 */
	public void setRowSeries(String[] rowSeries) {
		this.rowSeries = rowSeries;
	}
	
	/**
	 * Gets the row series length.
	 *
	 * @return the row series length
	 */
	public Integer[] getRowSeriesLength() {
		return rowSeriesLength;
	}
	
	/**
	 * Sets the row series length.
	 *
	 * @param rowSeriesLength the new row series length
	 */
	public void setRowSeriesLength(Integer[] rowSeriesLength) {
		this.rowSeriesLength = rowSeriesLength;
	}
	
	/**
	 * Gets the row is item has series.
	 *
	 * @return the row is item has series
	 */
	public String[] getRowIsItemHasSeries() {
		return rowIsItemHasSeries;
	}
	
	/**
	 * Gets the logged in office code.
	 *
	 * @return the logged in office code
	 */
	public String getLoggedInOfficeCode() {
		return loggedInOfficeCode;
	}
	
	/**
	 * Sets the logged in office code.
	 *
	 * @param loggedInOfficeCode the new logged in office code
	 */
	public void setLoggedInOfficeCode(String loggedInOfficeCode) {
		this.loggedInOfficeCode = loggedInOfficeCode;
	}
	
	/**
	 * Sets the row is item has series.
	 *
	 * @param rowIsItemHasSeries the new row is item has series
	 */
	public void setRowIsItemHasSeries(String[] rowIsItemHasSeries) {
		this.rowIsItemHasSeries = rowIsItemHasSeries;
	}
	
	/**
	 * Gets the no series.
	 *
	 * @return the no series
	 */
	public String getNoSeries() {
		return noSeries;
	}
	
	/**
	 * Sets the no series.
	 *
	 * @param noSeries the new no series
	 */
	public void setNoSeries(String noSeries) {
		this.noSeries = noSeries;
	}
	
	/**
	 * Gets the issued to branch.
	 *
	 * @return the issued to branch
	 */
	public String getIssuedToBranch() {
		return issuedToBranch;
	}
	
	/**
	 * Sets the issued to branch.
	 *
	 * @param issuedToBranch the new issued to branch
	 */
	public void setIssuedToBranch(String issuedToBranch) {
		this.issuedToBranch = issuedToBranch;
	}
	
	/**
	 * Gets the issued to fr.
	 *
	 * @return the issued to fr
	 */
	public String getIssuedToFr() {
		return issuedToFr;
	}
	
	/**
	 * Sets the issued to fr.
	 *
	 * @param issuedToFr the new issued to fr
	 */
	public void setIssuedToFr(String issuedToFr) {
		this.issuedToFr = issuedToFr;
	}
	
	/**
	 * Gets the issued to emp.
	 *
	 * @return the issued to emp
	 */
	public String getIssuedToEmp() {
		return issuedToEmp;
	}
	
	/**
	 * Sets the issued to emp.
	 *
	 * @param issuedToEmp the new issued to emp
	 */
	public void setIssuedToEmp(String issuedToEmp) {
		this.issuedToEmp = issuedToEmp;
	}
	
	/**
	 * Gets the issued to ba.
	 *
	 * @return the issued to ba
	 */
	public String getIssuedToBa() {
		return issuedToBa;
	}
	
	/**
	 * Sets the issued to ba.
	 *
	 * @param issuedToBa the new issued to ba
	 */
	public void setIssuedToBa(String issuedToBa) {
		this.issuedToBa = issuedToBa;
	}
	
	/**
	 * Gets the issued to customer.
	 *
	 * @return the issued to customer
	 */
	public String getIssuedToCustomer() {
		return issuedToCustomer;
	}
	
	/**
	 * Sets the issued to customer.
	 *
	 * @param issuedToCustomer the new issued to customer
	 */
	public void setIssuedToCustomer(String issuedToCustomer) {
		this.issuedToCustomer = issuedToCustomer;
	}
	
	/**
	 * Gets the row stock item dtls id.
	 *
	 * @return the row stock item dtls id
	 */
	public Long[] getRowStockItemDtlsId() {
		return rowStockItemDtlsId;
	}
	
	/**
	 * Sets the row stock item dtls id.
	 *
	 * @param rowStockItemDtlsId the new row stock item dtls id
	 */
	public void setRowStockItemDtlsId(Long[] rowStockItemDtlsId) {
		this.rowStockItemDtlsId = rowStockItemDtlsId;
	}
	
	/**
	 * Gets the row current stock quantity.
	 *
	 * @return the row current stock quantity
	 */
	public Integer[] getRowCurrentStockQuantity() {
		return rowCurrentStockQuantity;
	}
	
	/**
	 * Sets the row current stock quantity.
	 *
	 * @param rowCurrentStockQuantity the new row current stock quantity
	 */
	public void setRowCurrentStockQuantity(Integer[] rowCurrentStockQuantity) {
		this.rowCurrentStockQuantity = rowCurrentStockQuantity;
	}
	
	/**
	 * Gets the ba allowed series.
	 *
	 * @return the ba allowed series
	 */
	public String getBaAllowedSeries() {
		return baAllowedSeries;
	}
	
	/**
	 * Sets the ba allowed series.
	 *
	 * @param baAllowedSeries the new ba allowed series
	 */
	public void setBaAllowedSeries(String baAllowedSeries) {
		this.baAllowedSeries = baAllowedSeries;
	}
	
	/**
	 * Gets the consignment type.
	 *
	 * @return the consignmentType
	 */
	public String getConsignmentType() {
		return consignmentType;
	}
	
	/**
	 * @return the itemMap
	 */
	public Map<Integer, String> getItemMap() {
		return itemMap;
	}

	/**
	 * @param itemMap the itemMap to set
	 */
	public void setItemMap(Map<Integer, String> itemMap) {
		this.itemMap = itemMap;
	}

	/**
	 * Sets the consignment type.
	 *
	 * @param consignmentType the consignmentType to set
	 */
	public void setConsignmentType(String consignmentType) {
		this.consignmentType = consignmentType;
	}
	
	/**
	 * Gets the row office product.
	 *
	 * @return the rowOfficeProduct
	 */
	public String[] getRowOfficeProduct() {
		return rowOfficeProduct;
	}
	
	/**
	 * Sets the row office product.
	 *
	 * @param rowOfficeProduct the rowOfficeProduct to set
	 */
	public void setRowOfficeProduct(String[] rowOfficeProduct) {
		this.rowOfficeProduct = rowOfficeProduct;
	}
	
	
	/**
	 * Gets the row series type.
	 *
	 * @return the rowSeriesType
	 */
	public String[] getRowSeriesType() {
		return rowSeriesType;
	}
	
	/**
	 * Sets the row series type.
	 *
	 * @param rowSeriesType the rowSeriesType to set
	 */
	public void setRowSeriesType(String[] rowSeriesType) {
		this.rowSeriesType = rowSeriesType;
	}
	
	/**
	 * Gets the row start leaf.
	 *
	 * @return the rowStartLeaf
	 */
	public Long[] getRowStartLeaf() {
		return rowStartLeaf;
	}
	
	/**
	 * Sets the row start leaf.
	 *
	 * @param rowStartLeaf the rowStartLeaf to set
	 */
	public void setRowStartLeaf(Long[] rowStartLeaf) {
		this.rowStartLeaf = rowStartLeaf;
	}
	
	/**
	 * Gets the row end leaf.
	 *
	 * @return the rowEndLeaf
	 */
	public Long[] getRowEndLeaf() {
		return rowEndLeaf;
	}
	
	/**
	 * Sets the row end leaf.
	 *
	 * @param rowEndLeaf the rowEndLeaf to set
	 */
	public void setRowEndLeaf(Long[] rowEndLeaf) {
		this.rowEndLeaf = rowEndLeaf;
	}
	
	/**
	 * Gets the region code.
	 *
	 * @return the regionCode
	 */
	public String getRegionCode() {
		return regionCode;
	}
	
	/**
	 * Sets the region code.
	 *
	 * @param regionCode the regionCode to set
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	
	/**
	 * Gets the ogm stickers.
	 *
	 * @return the ogmStickers
	 */
	public String getOgmStickers() {
		return ogmStickers;
	}
	
	/**
	 * Gets the bpl stickers.
	 *
	 * @return the bplStickers
	 */
	public String getBplStickers() {
		return bplStickers;
	}
	
	/**
	 * Gets the comail number.
	 *
	 * @return the comailNumber
	 */
	public String getComailNumber() {
		return comailNumber;
	}
	
	/**
	 * Gets the bag loc number.
	 *
	 * @return the bagLocNumber
	 */
	public String getBagLocNumber() {
		return bagLocNumber;
	}
	
	/**
	 * Sets the ogm stickers.
	 *
	 * @param ogmStickers the ogmStickers to set
	 */
	public void setOgmStickers(String ogmStickers) {
		this.ogmStickers = ogmStickers;
	}
	
	/**
	 * Sets the bpl stickers.
	 *
	 * @param bplStickers the bplStickers to set
	 */
	public void setBplStickers(String bplStickers) {
		this.bplStickers = bplStickers;
	}
	
	/**
	 * Sets the comail number.
	 *
	 * @param comailNumber the comailNumber to set
	 */
	public void setComailNumber(String comailNumber) {
		this.comailNumber = comailNumber;
	}
	
	/**
	 * Sets the bag loc number.
	 *
	 * @param bagLocNumber the bagLocNumber to set
	 */
	public void setBagLocNumber(String bagLocNumber) {
		this.bagLocNumber = bagLocNumber;
	}
	
	/**
	 * Gets the row balance quantity.
	 *
	 * @return the rowBalanceQuantity
	 */
	public Integer[] getRowBalanceQuantity() {
		return rowBalanceQuantity;
	}
	
	/**
	 * Sets the row balance quantity.
	 *
	 * @param rowBalanceQuantity the rowBalanceQuantity to set
	 */
	public void setRowBalanceQuantity(Integer[] rowBalanceQuantity) {
		this.rowBalanceQuantity = rowBalanceQuantity;
	}
	
	/**
	 * Gets the transaction from type.
	 *
	 * @return the transactionFromType
	 */
	public String getTransactionFromType() {
		return transactionFromType;
	}
	
	/**
	 * Sets the transaction from type.
	 *
	 * @param transactionFromType the transactionFromType to set
	 */
	public void setTransactionFromType(String transactionFromType) {
		this.transactionFromType = transactionFromType;
	}
	
	/**
	 * Gets the transaction issue type.
	 *
	 * @return the transactionIssueType
	 */
	public String getTransactionIssueType() {
		return transactionIssueType;
	}
	
	/**
	 * Gets the transaction return type.
	 *
	 * @return the transactionReturnType
	 */
	public String getTransactionReturnType() {
		return transactionReturnType;
	}
	
	/**
	 * Gets the transaction receipt type.
	 *
	 * @return the transactionReceiptType
	 */
	public String getTransactionReceiptType() {
		return transactionReceiptType;
	}
	
	/**
	 * Sets the transaction issue type.
	 *
	 * @param transactionIssueType the transactionIssueType to set
	 */
	public void setTransactionIssueType(String transactionIssueType) {
		this.transactionIssueType = transactionIssueType;
	}
	
	/**
	 * Sets the transaction return type.
	 *
	 * @param transactionReturnType the transactionReturnType to set
	 */
	public void setTransactionReturnType(String transactionReturnType) {
		this.transactionReturnType = transactionReturnType;
	}
	
	/**
	 * Sets the transaction receipt type.
	 *
	 * @param transactionReceiptType the transactionReceiptType to set
	 */
	public void setTransactionReceiptType(String transactionReceiptType) {
		this.transactionReceiptType = transactionReceiptType;
	}
	
	/**
	 * Gets the transaction pr type.
	 *
	 * @return the transactionPRType
	 */
	public String getTransactionPRType() {
		return transactionPRType;
	}
	
	/**
	 * Sets the transaction pr type.
	 *
	 * @param transactionPRType the transactionPRType to set
	 */
	public void setTransactionPRType(String transactionPRType) {
		this.transactionPRType = transactionPRType;
	}
	
	/**
	 * Gets the can update.
	 *
	 * @return the canUpdate
	 */
	public String getCanUpdate() {
		return canUpdate;
	}
	
	/**
	 * Sets the can update.
	 *
	 * @param canUpdate the canUpdate to set
	 */
	public void setCanUpdate(String canUpdate) {
		this.canUpdate = canUpdate;
	}
	
	/**
	 * Gets the rho office code.
	 *
	 * @return the rhoOfficeCode
	 */
	public String getRhoOfficeCode() {
		return rhoOfficeCode;
	}
	
	/**
	 * Sets the rho office code.
	 *
	 * @param rhoOfficeCode the rhoOfficeCode to set
	 */
	public void setRhoOfficeCode(String rhoOfficeCode) {
		this.rhoOfficeCode = rhoOfficeCode;
	}
	
	/**
	 * Gets the city code.
	 *
	 * @return the cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}
	
	/**
	 * Sets the city code.
	 *
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	/**
	 * Gets the mbpl stickers.
	 *
	 * @return the mbplStickers
	 */
	public String getMbplStickers() {
		return mbplStickers;
	}
	
	/**
	 * Sets the mbpl stickers.
	 *
	 * @param mbplStickers the mbplStickers to set
	 */
	public void setMbplStickers(String mbplStickers) {
		this.mbplStickers = mbplStickers;
	}

	/**
	 * @return the approveFlagYes
	 */
	public String getApproveFlagYes() {
		return approveFlagYes;
	}

	/**
	 * @param approveFlagYes the approveFlagYes to set
	 */
	public void setApproveFlagYes(String approveFlagYes) {
		this.approveFlagYes = approveFlagYes;
	}

	/**
	 * @return the officeType
	 */
	public String getOfficeType() {
		return officeType;
	}

	/**
	 * @param officeType the officeType to set
	 */
	public void setOfficeType(String officeType) {
		this.officeType = officeType;
	}

	/**
	 * @return the officeRhoType
	 */
	public String getOfficeRhoType() {
		return officeRhoType;
	}

	/**
	 * @param officeRhoType the officeRhoType to set
	 */
	public void setOfficeRhoType(String officeRhoType) {
		this.officeRhoType = officeRhoType;
	}

	/**
	 * @return the todayDate
	 */
	public String getTodayDate() {
		return todayDate;
	}

	/**
	 * @param todayDate the todayDate to set
	 */
	public void setTodayDate(String todayDate) {
		this.todayDate = todayDate;
	}

	/**
	 * @return the franchiseeAllowedSeries
	 */
	public String getFranchiseeAllowedSeries() {
		return franchiseeAllowedSeries;
	}

	/**
	 * @param franchiseeAllowedSeries the franchiseeAllowedSeries to set
	 */
	public void setFranchiseeAllowedSeries(String franchiseeAllowedSeries) {
		this.franchiseeAllowedSeries = franchiseeAllowedSeries;
	}

	/**
	 * @return the creditCustAllowedSeries
	 */
	public String getCreditCustAllowedSeries() {
		return creditCustAllowedSeries;
	}

	/**
	 * @return the accCustAllowedSeries
	 */
	public String getAccCustAllowedSeries() {
		return accCustAllowedSeries;
	}

	/**
	 * @param creditCustAllowedSeries the creditCustAllowedSeries to set
	 */
	public void setCreditCustAllowedSeries(String creditCustAllowedSeries) {
		this.creditCustAllowedSeries = creditCustAllowedSeries;
	}

	/**
	 * @param accCustAllowedSeries the accCustAllowedSeries to set
	 */
	public void setAccCustAllowedSeries(String accCustAllowedSeries) {
		this.accCustAllowedSeries = accCustAllowedSeries;
	}

	/**
	 * @return the creditCustomerType
	 */
	public String getCreditCustomerType() {
		return creditCustomerType;
	}

	/**
	 * @return the accCustomerType
	 */
	public String getAccCustomerType() {
		return accCustomerType;
	}

	/**
	 * @param creditCustomerType the creditCustomerType to set
	 */
	public void setCreditCustomerType(String creditCustomerType) {
		this.creditCustomerType = creditCustomerType;
	}

	/**
	 * @param accCustomerType the accCustomerType to set
	 */
	public void setAccCustomerType(String accCustomerType) {
		this.accCustomerType = accCustomerType;
	}

	/**
	 * @return the creditCardCustomerType
	 */
	public String getCreditCardCustomerType() {
		return creditCardCustomerType;
	}

	/**
	 * @return the lcCustomerType
	 */
	public String getLcCustomerType() {
		return lcCustomerType;
	}

	/**
	 * @return the codCustomerType
	 */
	public String getCodCustomerType() {
		return codCustomerType;
	}

	/**
	 * @return the govtEntityCustomerType
	 */
	public String getGovtEntityCustomerType() {
		return govtEntityCustomerType;
	}

	/**
	 * @return the reverseLogCustomerType
	 */
	public String getReverseLogCustomerType() {
		return reverseLogCustomerType;
	}

	/**
	 * @return the normalCnoteIdentifier
	 */
	public String getNormalCnoteIdentifier() {
		return normalCnoteIdentifier;
	}

	/**
	 * @param creditCardCustomerType the creditCardCustomerType to set
	 */
	public void setCreditCardCustomerType(String creditCardCustomerType) {
		this.creditCardCustomerType = creditCardCustomerType;
	}

	/**
	 * @param lcCustomerType the lcCustomerType to set
	 */
	public void setLcCustomerType(String lcCustomerType) {
		this.lcCustomerType = lcCustomerType;
	}

	/**
	 * @param codCustomerType the codCustomerType to set
	 */
	public void setCodCustomerType(String codCustomerType) {
		this.codCustomerType = codCustomerType;
	}

	/**
	 * @param govtEntityCustomerType the govtEntityCustomerType to set
	 */
	public void setGovtEntityCustomerType(String govtEntityCustomerType) {
		this.govtEntityCustomerType = govtEntityCustomerType;
	}

	/**
	 * @param reverseLogCustomerType the reverseLogCustomerType to set
	 */
	public void setReverseLogCustomerType(String reverseLogCustomerType) {
		this.reverseLogCustomerType = reverseLogCustomerType;
	}

	/**
	 * @param normalCnoteIdentifier the normalCnoteIdentifier to set
	 */
	public void setNormalCnoteIdentifier(String normalCnoteIdentifier) {
		this.normalCnoteIdentifier = normalCnoteIdentifier;
	}

	/**
	 * @return the creditCardCustomerAllowedSeries
	 */
	public String getCreditCardCustomerAllowedSeries() {
		return creditCardCustomerAllowedSeries;
	}

	/**
	 * @return the lcCustomerAllowedSeries
	 */
	public String getLcCustomerAllowedSeries() {
		return lcCustomerAllowedSeries;
	}

	/**
	 * @return the codCustomerAllowedSeries
	 */
	public String getCodCustomerAllowedSeries() {
		return codCustomerAllowedSeries;
	}

	/**
	 * @return the govtEntityCustomerAllowedSeries
	 */
	public String getGovtEntityCustomerAllowedSeries() {
		return govtEntityCustomerAllowedSeries;
	}

	/**
	 * @return the reverseLogCustomerAllowedSeries
	 */
	public String getReverseLogCustomerAllowedSeries() {
		return reverseLogCustomerAllowedSeries;
	}

	/**
	 * @param creditCardCustomerAllowedSeries the creditCardCustomerAllowedSeries to set
	 */
	public void setCreditCardCustomerAllowedSeries(
			String creditCardCustomerAllowedSeries) {
		this.creditCardCustomerAllowedSeries = creditCardCustomerAllowedSeries;
	}

	/**
	 * @param lcCustomerAllowedSeries the lcCustomerAllowedSeries to set
	 */
	public void setLcCustomerAllowedSeries(String lcCustomerAllowedSeries) {
		this.lcCustomerAllowedSeries = lcCustomerAllowedSeries;
	}

	/**
	 * @param codCustomerAllowedSeries the codCustomerAllowedSeries to set
	 */
	public void setCodCustomerAllowedSeries(String codCustomerAllowedSeries) {
		this.codCustomerAllowedSeries = codCustomerAllowedSeries;
	}

	/**
	 * @param govtEntityCustomerAllowedSeries the govtEntityCustomerAllowedSeries to set
	 */
	public void setGovtEntityCustomerAllowedSeries(
			String govtEntityCustomerAllowedSeries) {
		this.govtEntityCustomerAllowedSeries = govtEntityCustomerAllowedSeries;
	}

	/**
	 * @param reverseLogCustomerAllowedSeries the reverseLogCustomerAllowedSeries to set
	 */
	public void setReverseLogCustomerAllowedSeries(
			String reverseLogCustomerAllowedSeries) {
		this.reverseLogCustomerAllowedSeries = reverseLogCustomerAllowedSeries;
	}
	public String getRequisitionNumber() {
		return requisitionNumber;
	}

	public void setRequisitionNumber(String requisitionNumber) {
		if(!StringUtil.isStringEmpty(requisitionNumber)){
			this.requisitionNumber = requisitionNumber.toUpperCase();
		}
		//this.requisitionNumber = requisitionNumber;
	}

	public String getStockIssueNumber() {
		return stockIssueNumber;
	}

	public void setStockIssueNumber(String stockIssueNumber) {
		if(!StringUtil.isStringEmpty(stockIssueNumber)){
			this.stockIssueNumber = stockIssueNumber.toUpperCase();
		}
		//this.stockIssueNumber = stockIssueNumber;
	}

	/**
	 * @return the acknowledgementNumber
	 */
	public String getAcknowledgementNumber() {
		return acknowledgementNumber;
	}

	/**
	 * @return the stockReturnNumber
	 */
	public String getStockReturnNumber() {
		return stockReturnNumber;
	}

	/**
	 * @param acknowledgementNumber the acknowledgementNumber to set
	 */
	public void setAcknowledgementNumber(String acknowledgementNumber) {
		if(!StringUtil.isStringEmpty(acknowledgementNumber)){
			this.acknowledgementNumber = acknowledgementNumber.toUpperCase();
		}
		//this.acknowledgementNumber = acknowledgementNumber;
	}

	/**
	 * @param stockReturnNumber the stockReturnNumber to set
	 */
	public void setStockReturnNumber(String stockReturnNumber) {
		if(!StringUtil.isStringEmpty(stockReturnNumber)){
			this.stockReturnNumber = stockReturnNumber.toUpperCase();
		}
		//this.stockReturnNumber = stockReturnNumber;
	}

	/**
	 * @return the rowBalanceReturnQuantity
	 */
	public Integer[] getRowBalanceReturnQuantity() {
		return rowBalanceReturnQuantity;
	}

	/**
	 * @param rowBalanceReturnQuantity the rowBalanceReturnQuantity to set
	 */
	public void setRowBalanceReturnQuantity(Integer[] rowBalanceReturnQuantity) {
		this.rowBalanceReturnQuantity = rowBalanceReturnQuantity;
	}

}
