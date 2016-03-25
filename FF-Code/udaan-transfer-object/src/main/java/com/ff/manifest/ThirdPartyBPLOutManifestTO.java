package com.ff.manifest;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.ff.tracking.ProcessTO;

/**
 * The Class ThirdPartyBPLOutManifestTO.
 */
public class ThirdPartyBPLOutManifestTO extends OutManifestBaseTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3701459151802979307L;
	// Specific to Third Party BPL out manifest fields
	/** The load no. */
	private Integer loadNo;

	/** The load list. */
	private List<LabelValueBean> loadList;

	/** The third party type list. */
	private List<LabelValueBean> thirdPartyTypeList;

	/** The third party name list. */
	private List<LabelValueBean> thirdPartyNameList;

	/** The third party type. */
	// private String thirdPartyType;

	/** The third party name. */
	// private String thirdPartyName;

	/**
	 * the header level manifest's process code
	 */
	private String manifestProcessCode;

	/** The embedded type. */
	private String[] embeddedType = new String[super.rowCount]; // M-manifest, C
																// -
	// consignment

	/** The isCnUpdated. */
	private String[] isCnUpdated = new String[rowCount];// hidden for noOfPcs.

	/** The child cns. */
	private String[] childCns = new String[rowCount];// hidden for noOfPcs.
	
	private Double[] baAmounts = new Double[rowCount];

	/** The ProcessTO. */
	private ProcessTO processTO;

	/** The grid position - to maintain order in grid */
	private String gridPosition;

	// print
	private double totalWt;
	private int totalConsg;
	private Long totalComail;
	private int totalPacket;
	private String vendorCode;

	/**
	 * @return the gridPosition
	 */
	public String getGridPosition() {
		return gridPosition;
	}

	/**
	 * @param gridPosition
	 *            the gridPosition to set
	 */
	public void setGridPosition(String gridPosition) {
		this.gridPosition = gridPosition;
	}

	/**
	 * @return the processTO
	 */
	public ProcessTO getProcessTO() {
		return processTO;
	}

	/**
	 * @param processTO
	 *            the processTO to set
	 */
	public void setProcessTO(ProcessTO processTO) {
		this.processTO = processTO;
	}

	/**
	 * Gets the isCnUpdated
	 * 
	 * @return the isCnUpdated
	 */
	public String[] getIsCnUpdated() {
		return isCnUpdated;
	}

	/**
	 * Sets the isCnUpdated
	 * 
	 * @param isCnUpdated
	 *            the isCnUpdated to set
	 */
	public void setIsCnUpdated(String[] isCnUpdated) {
		this.isCnUpdated = isCnUpdated;
	}

	/**
	 * Gets the Child CNs
	 * 
	 * @return the childCns
	 */
	public String[] getChildCns() {
		return childCns;
	}

	/**
	 * Sets the Child CNs
	 * 
	 * @param childCns
	 *            the childCns to set
	 */
	public void setChildCns(String[] childCns) {
		this.childCns = childCns;
	}

	/**
	 * Gets the embedded type.
	 * 
	 * @return the embedded type
	 */
	public String[] getEmbeddedType() {
		return embeddedType;
	}

	/**
	 * Sets the embedded type.
	 * 
	 * @param embeddedType
	 *            the new embedded type
	 */
	public void setEmbeddedType(String[] embeddedType) {
		this.embeddedType = embeddedType;
	}

	public String getManifestProcessCode() {
		return manifestProcessCode;
	}

	public void setManifestProcessCode(String manifestProcessCode) {
		this.manifestProcessCode = manifestProcessCode;
	}

	/** The scanned grid item no. */
	private String[] scannedGridItemNo = new String[rowCount];

	// Internal use for transferring data from UI to Action

	/**
	 * Gets the scanned grid item no.
	 * 
	 * @return the scanned grid item no
	 */
	public String[] getScannedGridItemNo() {
		return scannedGridItemNo;
	}

	/**
	 * Sets the scanned grid item no.
	 * 
	 * @param scannedGridItemNo
	 *            the new scanned grid item no
	 */
	public void setScannedGridItemNo(String[] scannedGridItemNo) {
		this.scannedGridItemNo = scannedGridItemNo;
	}

	/**
	 * Gets the cn content ids.
	 * 
	 * @return the cn content ids
	 */
	public Integer[] getCnContentIds() {
		return cnContentIds;
	}

	/**
	 * Sets the cn content ids.
	 * 
	 * @param cnContentIds
	 *            the new cn content ids
	 */
	public void setCnContentIds(Integer[] cnContentIds) {
		this.cnContentIds = cnContentIds;
	}

	/**
	 * Gets the third party name list.
	 * 
	 * @return the third party name list
	 */
	public List<LabelValueBean> getThirdPartyNameList() {
		return thirdPartyNameList;
	}

	/**
	 * Sets the third party name list.
	 * 
	 * @param thirdPartyNameList
	 *            the new third party name list
	 */
	public void setThirdPartyNameList(List<LabelValueBean> thirdPartyNameList) {
		this.thirdPartyNameList = thirdPartyNameList;
	}

	/** The no of pcs. */
	private Integer[] noOfPcs = new Integer[rowCount];

	/** The cn content. */
	private String[] cnContent = new String[rowCount];

	/** The cn content ids. */
	private Integer[] cnContentIds = new Integer[rowCount];// Content //
															// Description

	/** The declared values. */
	private Double[] declaredValues = new Double[rowCount];

	/** The paper works. */
	private String[] paperWorks = new String[rowCount];

	/** The paper work ids. */
	private Integer[] paperWorkIds = new Integer[rowCount];

	/** The to pay amts. */
	private Double[] toPayAmts = new Double[rowCount];

	/** The cod amts. */
	private Double[] codAmts = new Double[rowCount];

	/** The octroi Amts. */
	private Double[] octroiAmts = new Double[rowCount];

	/** The service charges. */
	private Double[] serviceCharges = new Double[rowCount];

	/** The state taxes. */
	private Double[] stateTaxes = new Double[rowCount];

	/**
	 * @return the octroiAmts
	 */
	public Double[] getOctroiAmts() {
		return octroiAmts;
	}

	/**
	 * @param octroiAmts
	 *            the octroiAmts to set
	 */
	public void setOctroiAmts(Double[] octroiAmts) {
		this.octroiAmts = octroiAmts;
	}

	/**
	 * Gets the paper work ids
	 * 
	 * @return the paperWorkIds
	 */
	public Integer[] getPaperWorkIds() {
		return paperWorkIds;
	}

	/**
	 * Sets the paper work ids
	 * 
	 * @param paperWorkIds
	 *            the paperWorkIds to set
	 */
	public void setPaperWorkIds(Integer[] paperWorkIds) {
		this.paperWorkIds = paperWorkIds;
	}

	/**
	 * Gets the cn content.
	 * 
	 * @return the cn content
	 */
	public String[] getCnContent() {
		return cnContent;
	}

	/**
	 * Sets the cn content.
	 * 
	 * @param cnContent
	 *            the new cn content
	 */
	public void setCnContent(String[] cnContent) {
		this.cnContent = cnContent;
	}

	/**
	 * Gets the load list.
	 * 
	 * @return the load list
	 */
	public List<LabelValueBean> getLoadList() {
		return loadList;
	}

	/**
	 * Sets the load list.
	 * 
	 * @param loadList
	 *            the new load list
	 */
	public void setLoadList(List<LabelValueBean> loadList) {
		this.loadList = loadList;
	}

	/**
	 * Gets the third party type list.
	 * 
	 * @return the third party type list
	 */
	public List<LabelValueBean> getThirdPartyTypeList() {
		return thirdPartyTypeList;
	}

	/**
	 * Gets the third party bpl details list to.
	 * 
	 * @return the third party bpl details list to
	 */
	public List<ThirdPartyBPLDetailsTO> getThirdPartyBPLDetailsListTO() {
		return thirdPartyBPLDetailsListTO;
	}

	/**
	 * Sets the third party bpl details list to.
	 * 
	 * @param thirdPartyBPLDetailsListTO
	 *            the new third party bpl details list to
	 */
	public void setThirdPartyBPLDetailsListTO(
			List<ThirdPartyBPLDetailsTO> thirdPartyBPLDetailsListTO) {
		this.thirdPartyBPLDetailsListTO = thirdPartyBPLDetailsListTO;
	}

	/**
	 * Sets the third party type list.
	 * 
	 * @param thirdPartyTypeList
	 *            the new third party type list
	 */
	public void setThirdPartyTypeList(List<LabelValueBean> thirdPartyTypeList) {
		this.thirdPartyTypeList = thirdPartyTypeList;
	}

	// List of Child details of Third Party BPL out manifest
	/** The third party bpl details list to. */
	private List<ThirdPartyBPLDetailsTO> thirdPartyBPLDetailsListTO;

	/**
	 * Gets the load no.
	 * 
	 * @return the load no
	 */
	public Integer getLoadNo() {
		return loadNo;
	}

	/**
	 * Sets the load no.
	 * 
	 * @param loadNo
	 *            the new load no
	 */
	public void setLoadNo(Integer loadNo) {
		this.loadNo = loadNo;
	}

	/**
	 * Gets the no of pcs.
	 * 
	 * @return the no of pcs
	 */
	public Integer[] getNoOfPcs() {
		return noOfPcs;
	}

	/**
	 * Sets the no of pcs.
	 * 
	 * @param noOfPcs
	 *            the new no of pcs
	 */
	public void setNoOfPcs(Integer[] noOfPcs) {
		this.noOfPcs = noOfPcs;
	}

	/**
	 * Gets the declared values.
	 * 
	 * @return the declared values
	 */
	public Double[] getDeclaredValues() {
		return declaredValues;
	}

	/**
	 * Sets the declared values.
	 * 
	 * @param declaredValues
	 *            the new declared values
	 */
	public void setDeclaredValues(Double[] declaredValues) {
		this.declaredValues = declaredValues;
	}

	/**
	 * Gets the paper works.
	 * 
	 * @return the paper works
	 */
	public String[] getPaperWorks() {
		return paperWorks;
	}

	/**
	 * Sets the paper works.
	 * 
	 * @param paperWorks
	 *            the new paper works
	 */
	public void setPaperWorks(String[] paperWorks) {
		this.paperWorks = paperWorks;
	}

	/**
	 * Gets the to pay amts.
	 * 
	 * @return the to pay amts
	 */
	public Double[] getToPayAmts() {
		return toPayAmts;
	}

	/**
	 * Sets the to pay amts.
	 * 
	 * @param toPayAmts
	 *            the new to pay amts
	 */
	public void setToPayAmts(Double[] toPayAmts) {
		this.toPayAmts = toPayAmts;
	}

	/**
	 * Gets the cod amts.
	 * 
	 * @return the cod amts
	 */
	public Double[] getCodAmts() {
		return codAmts;
	}

	/**
	 * Sets the cod amts.
	 * 
	 * @param codAmts
	 *            the new cod amts
	 */
	public void setCodAmts(Double[] codAmts) {
		this.codAmts = codAmts;
	}

	/**
	 * Gets the service charges.
	 * 
	 * @return the service charges
	 */
	public Double[] getServiceCharges() {
		return serviceCharges;
	}

	/**
	 * Sets the service charges.
	 * 
	 * @param serviceCharges
	 *            the new service charges
	 */
	public void setServiceCharges(Double[] serviceCharges) {
		this.serviceCharges = serviceCharges;
	}

	/**
	 * Gets the state taxes.
	 * 
	 * @return the state taxes
	 */
	public Double[] getStateTaxes() {
		return stateTaxes;
	}

	/**
	 * Sets the state taxes.
	 * 
	 * @param stateTaxes
	 *            the new state taxes
	 */
	public void setStateTaxes(Double[] stateTaxes) {
		this.stateTaxes = stateTaxes;
	}

	public double getTotalWt() {
		return totalWt;
	}

	public void setTotalWt(double totalWt) {
		this.totalWt = totalWt;
	}

	public int getTotalConsg() {
		return totalConsg;
	}

	public void setTotalConsg(int totalConsg) {
		this.totalConsg = totalConsg;
	}

	public Long getTotalComail() {
		return totalComail;
	}

	public void setTotalComail(Long totalComail) {
		this.totalComail = totalComail;
	}

	public int getTotalPacket() {
		return totalPacket;
	}

	public void setTotalPacket(int totalPacket) {
		this.totalPacket = totalPacket;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	/**
	 * @return the baAmounts
	 */
	public Double[] getBaAmounts() {
		return baAmounts;
	}

	/**
	 * @param baAmounts the baAmounts to set
	 */
	public void setBaAmounts(Double[] baAmounts) {
		this.baAmounts = baAmounts;
	}

	
}
