/**
 * 
 */
package com.ff.to.stockmanagement.stockrequisition;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author mohammes
 *
 */
public class StockValidationTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5663162407838913507L;

	
	
	
	private Integer loggedInOfficeId;//loggedInOfficeId
	private String issuedTOType;//issuedTOType ie IssuedTo column in Stock issue screen
	private String receipientCode;// user entered code in the screen
	
	
	private Long startLeaf;//A series without Office/Product ex: A000A1234567 -->1234567
	private Long endLeaf;//A series without Office/Product ex: A000A1234567 -->1234567
	private String officeProduct ;//A series without Office/Product ex: A000A1234567 -->A000A
	private List<String> seriesList;// list of Series start to end
	private List<Long> leafList;// list of Series start to end
	private Long leaf;// leaf of series
	
	
	private Integer itemId;//ie Fr,BO,BA,EMP,Customer id
	private Integer partyTypeId;//ie Fr,BO,BA,EMP,Customer id
	private String startSerialNumber;//Start Serial number
	private String endSerialNumber;//Start Serial number
	private Integer quantity;//Quantity  of issuing series
	private Long itemDetailsId;//for Stock validations it holds PK
	private String seriesType;//for cn,sticker/comail number etc
	private String regionCode;//for region code
	private String officeCode;//for office code
	
	private String transactionType;//for Stock Receipt series validations
	
	private String rhoCode;  // logged in office RHO Code (Optional Mandatory User input )
	private String cityCode;  // logged in office City Code (Optional Mandatory User input)
	
	private Long stockReceiptItemDetailsId;//for Stock validations it holds PK
	private String transactionNumber;//for Stock validations it holds issue number/requisition number etc
	
	private String transferTO;
	
	private Integer expectedSeriesLength;
	
	private String forRhoScreen;
	private String screenName;
	
	/**
	 * @return the screenName
	 */
	public String getScreenName() {
		return screenName;
	}
	/**
	 * @param screenName the screenName to set
	 */
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public Integer getLoggedInOfficeId() {
		return loggedInOfficeId;
	}
	public void setLoggedInOfficeId(Integer loggedInOfficeId) {
		this.loggedInOfficeId = loggedInOfficeId;
	}
	
	
	public String getReceipientCode() {
		return receipientCode;
	}
	public void setReceipientCode(String receipientCode) {
		this.receipientCode = receipientCode;
	}
	public Long getStartLeaf() {
		return startLeaf;
	}
	public void setStartLeaf(Long startLeaf) {
		this.startLeaf = startLeaf;
	}
	public Long getEndLeaf() {
		return endLeaf;
	}
	public void setEndLeaf(Long endLeaf) {
		this.endLeaf = endLeaf;
	}
	public String getOfficeProduct() {
		return officeProduct;
	}
	public void setOfficeProduct(String officeProduct) {
		this.officeProduct = officeProduct;
	}
	public List<String> getSeriesList() {
		return seriesList;
	}
	public void setSeriesList(List<String> seriesList) {
		this.seriesList = seriesList;
	}
	public String getStartSerialNumber() {
		return startSerialNumber;
	}
	public void setStartSerialNumber(String startSerialNumber) {
		this.startSerialNumber = startSerialNumber;
	}
	
	public String getIssuedTOType() {
		return issuedTOType;
	}
	public void setIssuedTOType(String issuedTOType) {
		this.issuedTOType = issuedTOType;
	}
	public Integer getPartyTypeId() {
		return partyTypeId;
	}
	public void setPartyTypeId(Integer partyTypeId) {
		this.partyTypeId = partyTypeId;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Long getItemDetailsId() {
		return itemDetailsId;
	}
	public void setItemDetailsId(Long itemDetailsId) {
		this.itemDetailsId = itemDetailsId;
	}
	public List<Long> getLeafList() {
		return leafList;
	}
	public void setLeafList(List<Long> leafList) {
		this.leafList = leafList;
	}
	public Long getLeaf() {
		return leaf;
	}
	public void setLeaf(Long leaf) {
		this.leaf = leaf;
	}
	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the seriesType
	 */
	public String getSeriesType() {
		return seriesType;
	}
	/**
	 * @param seriesType the seriesType to set
	 */
	public void setSeriesType(String seriesType) {
		this.seriesType = seriesType;
	}
	/**
	 * @return the regionCode
	 */
	public String getRegionCode() {
		return regionCode;
	}
	/**
	 * @param regionCode the regionCode to set
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	/**
	 * @return the officeCode
	 */
	public String getOfficeCode() {
		return officeCode;
	}
	/**
	 * @param officeCode the officeCode to set
	 */
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}
	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}
	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	/**
	 * @return the stockReceiptItemDetailsId
	 */
	public Long getStockReceiptItemDetailsId() {
		return stockReceiptItemDetailsId;
	}
	/**
	 * @param stockReceiptItemDetailsId the stockReceiptItemDetailsId to set
	 */
	public void setStockReceiptItemDetailsId(Long stockReceiptItemDetailsId) {
		this.stockReceiptItemDetailsId = stockReceiptItemDetailsId;
	}
	/**
	 * @return the transactionNumber
	 */
	public String getTransactionNumber() {
		return transactionNumber;
	}
	/**
	 * @param transactionNumber the transactionNumber to set
	 */
	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	/**
	 * @return the rhoCode
	 */
	public String getRhoCode() {
		return rhoCode;
	}
	/**
	 * @return the cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}
	/**
	 * @param rhoCode the rhoCode to set
	 */
	public void setRhoCode(String rhoCode) {
		this.rhoCode = rhoCode;
	}
	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	/**
	 * @return the expectedSeriesLength
	 */
	public Integer getExpectedSeriesLength() {
		return expectedSeriesLength;
	}
	/**
	 * @param expectedSeriesLength the expectedSeriesLength to set
	 */
	public void setExpectedSeriesLength(Integer expectedSeriesLength) {
		this.expectedSeriesLength = expectedSeriesLength;
	}
	/**
	 * @return the transferTO
	 */
	public String getTransferTO() {
		return transferTO;
	}
	/**
	 * @param transferTO the transferTO to set
	 */
	public void setTransferTO(String transferTO) {
		this.transferTO = transferTO;
	}
	/**
	 * @return the forRhoScreen
	 */
	public String getForRhoScreen() {
		return forRhoScreen;
	}
	/**
	 * @param forRhoScreen the forRhoScreen to set
	 */
	public void setForRhoScreen(String forRhoScreen) {
		this.forRhoScreen = forRhoScreen;
	}
	/**
	 * @return the endSerialNumber
	 */
	public String getEndSerialNumber() {
		return endSerialNumber;
	}
	/**
	 * @param endSerialNumber the endSerialNumber to set
	 */
	public void setEndSerialNumber(String endSerialNumber) {
		this.endSerialNumber = endSerialNumber;
	}
	
	
	
	
	
	
}
