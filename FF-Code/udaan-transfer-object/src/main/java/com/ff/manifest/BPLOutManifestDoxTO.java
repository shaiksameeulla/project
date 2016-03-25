package com.ff.manifest;

import java.util.List;

import com.ff.organization.OfficeTO;
import com.ff.tracking.ProcessTO;
// TODO: Auto-generated Javadoc
/**
 * The Class BPLOutManifestDoxTO.
 */
public class BPLOutManifestDoxTO extends OutManifestBaseTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6239565036339691515L;
	// Specific to BPL out manifest document fields
	/** The dest office type. */
	private String destOfficeType;
	/** The bpl manifest type. */
	private String bplManifestType;
	
	/** The region code. */
	private String regionCode;
	
	/** The error msg. */
	private String errorMsg;
	
	/** The is route serviced city. */
	private boolean isRouteServicedCity;
	
	/** The manifest embed in. */
	private Integer manifestEmbedIn;
	
	private Integer operatingOffice;
	
	private Integer originOfficeId;

	
	/** The rowcount. */
	private int rowcount;
	
	private String coMail;
	
	/** The bpl nos. */
	private String[] bplNos = new String[rowcount];
	
	/** The bag lock nos. */
	private String[] bagLockNos = new String[rowcount];
	

	/** The product series. */
	private String[] productSeries =new String[rowcount];


	// List of Child details of BPL out manifest document
	/** The bpl out manifest dox details to list. */
	private List<BPLOutManifestDoxDetailsTO> bplOutManifestDoxDetailsTOList;
	
	/** The consig total. */
	private Long consigTotal ;
	

	private String destinationOffcId;

	private OfficeTO originOfficeTO;
	private ProcessTO processTO;
	private OfficeTO loggedInOfficeTO;
	private Boolean isInManifest;
	private Integer manifestProcessId;
	
	/** The manifest open type. */
	private String manifestOpenType;
	
	/**
	 * @return the manifestOpenType
	 */
	public String getManifestOpenType() {
		return manifestOpenType;
	}

	/**
	 * @param manifestOpenType the manifestOpenType to set
	 */
	public void setManifestOpenType(String manifestOpenType) {
		this.manifestOpenType = manifestOpenType;
	}

	/**
	 * Gets the dest office type.
	 *
	 * @return the dest office type
	 */
	public String getDestOfficeType() {
		return destOfficeType;
	}

	/**
	 * Sets the dest office type.
	 *
	 * @param destOfficeType the new dest office type
	 */
	public void setDestOfficeType(String destOfficeType) {
		this.destOfficeType = destOfficeType;
	}

	/**
	 * Gets the region code.
	 *
	 * @return the region code
	 */
	public String getRegionCode() {
		return regionCode;
	}

	/**
	 * Sets the region code.
	 *
	 * @param regionCode the new region code
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	/**
	 * Gets the bpl out manifest dox details to list.
	 *
	 * @return the bpl out manifest dox details to list
	 */
	public List<BPLOutManifestDoxDetailsTO> getBplOutManifestDoxDetailsTOList() {
		return bplOutManifestDoxDetailsTOList;
	}

	/**
	 * Sets the bpl out manifest dox details to list.
	 *
	 * @param bplOutManifestDoxDetailsTOList the new bpl out manifest dox details to list
	 */
	public void setBplOutManifestDoxDetailsTOList(
			List<BPLOutManifestDoxDetailsTO> bplOutManifestDoxDetailsTOList) {
		this.bplOutManifestDoxDetailsTOList = bplOutManifestDoxDetailsTOList;
	}

	/**
	 * Gets the error msg.
	 *
	 * @return the error msg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * Sets the error msg.
	 *
	 * @param errorMsg the new error msg
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * Gets the bpl manifest type.
	 *
	 * @return the bpl manifest type
	 */
	public String getBplManifestType() {
		return bplManifestType;
	}

	/**
	 * Sets the bpl manifest type.
	 *
	 * @param bplManifestType the new bpl manifest type
	 */
	public void setBplManifestType(String bplManifestType) {
		this.bplManifestType = bplManifestType;
	}

	/**
	 * Checks if is route serviced city.
	 *
	 * @return true, if is route serviced city
	 */
	public boolean isRouteServicedCity() {
		return isRouteServicedCity;
	}

	/**
	 * Sets the route serviced city.
	 *
	 * @param isRouteServicedCity the new route serviced city
	 */
	public void setRouteServicedCity(boolean isRouteServicedCity) {
		this.isRouteServicedCity = isRouteServicedCity;
	}

	/**
	 * Gets the rowcount.
	 *
	 * @return the rowcount
	 */
	public int getRowcount() {
		return rowcount;
	}

	/**
	 * Sets the rowcount.
	 *
	 * @param rowcount the new rowcount
	 */
	public void setRowcount(int rowcount) {
		this.rowcount = rowcount;
	}

	/**
	 * Gets the bpl nos.
	 *
	 * @return the bpl nos
	 */
	public String[] getBplNos() {
		return bplNos;
	}

	/**
	 * Sets the bpl nos.
	 *
	 * @param bplNos the new bpl nos
	 */
	public void setBplNos(String[] bplNos) {
		this.bplNos = bplNos;
	}

	/**
	 * Gets the bag lock nos.
	 *
	 * @return the bag lock nos
	 */
	public String[] getBagLockNos() {
		return bagLockNos;
	}

	
	/**
	 * Gets the product series.
	 *
	 * @return the product series
	 */
	public String[] getProductSeries() {
		return productSeries;
	}

	/**
	 * Sets the product series.
	 *
	 * @param productSeries the new product series
	 */
	public void setProductSeries(String[] productSeries) {
		this.productSeries = productSeries;
	}
	/**
	 * Sets the bag lock nos.
	 *
	 * @param bagLockNos the new bag lock nos
	 */
	public void setBagLockNos(String[] bagLockNos) {
		this.bagLockNos = bagLockNos;
	}

	/**
	 * Gets the manifest embed in.
	 *
	 * @return the manifest embed in
	 */
	public Integer getManifestEmbedIn() {
		return manifestEmbedIn;
	}

	/**
	 * Sets the manifest embed in.
	 *
	 * @param manifestEmbedIn the new manifest embed in
	 */
	public void setManifestEmbedIn(Integer manifestEmbedIn) {
		this.manifestEmbedIn = manifestEmbedIn;
	}

	/**
	 * Gets the consig total.
	 *
	 * @return the consig total
	 */
	public Long getConsigTotal() {
		return consigTotal;
	}

	/**
	 * Sets the consig total.
	 *
	 * @param consigTotal the new consig total
	 */
	public void setConsigTotal(Long consigTotal) {
		this.consigTotal = consigTotal;
	}

	public String getDestinationOffcId() {
		return destinationOffcId;
	}

	public void setDestinationOffcId(String destinationOffcId) {
		this.destinationOffcId = destinationOffcId;
	}

	public String getCoMail() {
		return coMail;
	}

	public void setCoMail(String coMail) {
		this.coMail = coMail;
	}

	/**
	 * @return the originOfficeTO
	 */
	public OfficeTO getOriginOfficeTO() {
		return originOfficeTO;
	}

	/**
	 * @param originOfficeTO the originOfficeTO to set
	 */
	public void setOriginOfficeTO(OfficeTO originOfficeTO) {
		this.originOfficeTO = originOfficeTO;
	}

	/**
	 * @return the processTO
	 */
	public ProcessTO getProcessTO() {
		return processTO;
	}

	/**
	 * @param processTO the processTO to set
	 */
	public void setProcessTO(ProcessTO processTO) {
		this.processTO = processTO;
	}

	/**
	 * @return the loggedInOfficeTO
	 */
	public OfficeTO getLoggedInOfficeTO() {
		return loggedInOfficeTO;
	}

	/**
	 * @param loggedInOfficeTO the loggedInOfficeTO to set
	 */
	public void setLoggedInOfficeTO(OfficeTO loggedInOfficeTO) {
		this.loggedInOfficeTO = loggedInOfficeTO;
	}

	public Boolean getIsInManifest() {
		return isInManifest;
	}

	public void setIsInManifest(Boolean isInManifest) {
		this.isInManifest = isInManifest;
	}

	/**
	 * @return the manifestProcessId
	 */
	public Integer getManifestProcessId() {
		return manifestProcessId;
	}

	/**
	 * @param manifestProcessId the manifestProcessId to set
	 */
	public void setManifestProcessId(Integer manifestProcessId) {
		this.manifestProcessId = manifestProcessId;
	}

	public Integer getOperatingOffice() {
		return operatingOffice;
	}

	public void setOperatingOffice(Integer operatingOffice) {
		this.operatingOffice = operatingOffice;
	}

	public Integer getOriginOfficeId() {
		return originOfficeId;
	}

	public void setOriginOfficeId(Integer originOfficeId) {
		this.originOfficeId = originOfficeId;
	}

	

	

}
