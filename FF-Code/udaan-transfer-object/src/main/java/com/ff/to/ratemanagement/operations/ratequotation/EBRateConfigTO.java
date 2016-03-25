package com.ff.to.ratemanagement.operations.ratequotation;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class EBRateConfigTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5117371348035304574L;
	private Integer ebRateConfigId;
	private Integer originState;;
	private String serviceTaxApplicable;
	private String cessTaxApplicable;
	private String hcesstaxApplicable;
	private String stateTaxApplicable;
	private String surchargeOnSTApplicable;
	private String validFromDateStr;
	private List<LabelValueBean> originList;
	private String prefIdAtGrid;
	private int rowCount;
	private String validToDateStr;
	private String status;
	private Integer currentEBRateConfigId;
	private String stateCode;
	private String saveMode;
	private String isRenew;
	private String action;
	private String successMessage;

	private List<EBRatePreferenceTO> preferenceTOs;
	private List<String> seqPrefCode;
	private String[] prefCodes = new String[rowCount];
	private String[] prefNames = new String[rowCount];
	private String[] prefDescription = new String[rowCount];
	private Integer[] prefId = new Integer[rowCount];
	private Double[] amount = new Double[rowCount];
	private String[] applicabilitys = new String[rowCount];
	private Integer[] ebPrefRateId = new Integer[rowCount];
	private Integer curStateId;
	
	
	
	/**
	 * @return the curStateId
	 */
	public Integer getCurStateId() {
		return curStateId;
	}

	/**
	 * @param curStateId the curStateId to set
	 */
	public void setCurStateId(Integer curStateId) {
		this.curStateId = curStateId;
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the isRenew
	 */
	public String getIsRenew() {
		return isRenew;
	}

	/**
	 * @param isRenew the isRenew to set
	 */
	public void setIsRenew(String isRenew) {
		this.isRenew = isRenew;
	}

	/**
	 * @return the saveMode
	 */
	public String getSaveMode() {
		return saveMode;
	}

	/**
	 * @param saveMode
	 *            the saveMode to set
	 */
	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	/**
	 * @return the ebPrefRateId
	 */
	public Integer[] getEbPrefRateId() {
		return ebPrefRateId;
	}

	/**
	 * @param ebPrefRateId
	 *            the ebPrefRateId to set
	 */
	public void setEbPrefRateId(Integer[] ebPrefRateId) {
		this.ebPrefRateId = ebPrefRateId;
	}

	/**
	 * @return the applicabilitys
	 */
	public String[] getApplicabilitys() {
		return applicabilitys;
	}

	/**
	 * @param applicabilitys
	 *            the applicabilitys to set
	 */
	public void setApplicabilitys(String[] applicabilitys) {
		this.applicabilitys = applicabilitys;
	}

	/**
	 * @return the preferenceTOs
	 */
	public List<EBRatePreferenceTO> getPreferenceTOs() {
		return preferenceTOs;
	}

	/**
	 * @param preferenceTOs
	 *            the preferenceTOs to set
	 */
	public void setPreferenceTOs(List<EBRatePreferenceTO> preferenceTOs) {
		this.preferenceTOs = preferenceTOs;
	}

	/**
	 * @return the stateCode
	 */
	public String getStateCode() {
		return stateCode;
	}

	/**
	 * @param stateCode
	 *            the stateCode to set
	 */
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	/**
	 * @return the seqPrefCode
	 */
	public List<String> getSeqPrefCode() {
		return seqPrefCode;
	}

	/**
	 * @param seqPrefCode
	 *            the seqPrefCode to set
	 */
	public void setSeqPrefCode(List<String> seqPrefCode) {
		this.seqPrefCode = seqPrefCode;
	}

	/**
	 * @return the currentEBRateConfigId
	 */
	public Integer getCurrentEBRateConfigId() {
		return currentEBRateConfigId;
	}

	/**
	 * @param currentEBRateConfigId
	 *            the currentEBRateConfigId to set
	 */
	public void setCurrentEBRateConfigId(Integer currentEBRateConfigId) {
		this.currentEBRateConfigId = currentEBRateConfigId;
	}

	/**
	 * @return the validFromDateStr
	 */
	public String getValidFromDateStr() {
		return validFromDateStr;
	}

	/**
	 * @param validFromDateStr
	 *            the validFromDateStr to set
	 */
	public void setValidFromDateStr(String validFromDateStr) {
		this.validFromDateStr = validFromDateStr;
	}

	/**
	 * @return the validToDateStr
	 */
	public String getValidToDateStr() {
		return validToDateStr;
	}

	/**
	 * @param validToDateStr
	 *            the validToDateStr to set
	 */
	public void setValidToDateStr(String validToDateStr) {
		this.validToDateStr = validToDateStr;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the rowCount
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * @param rowCount
	 *            the rowCount to set
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * @return the prefCodes
	 */
	public String[] getPrefCodes() {
		return prefCodes;
	}

	/**
	 * @param prefCodes
	 *            the prefCodes to set
	 */
	public void setPrefCodes(String[] prefCodes) {
		this.prefCodes = prefCodes;
	}

	/**
	 * @return the prefNames
	 */
	public String[] getPrefNames() {
		return prefNames;
	}

	/**
	 * @param prefNames
	 *            the prefNames to set
	 */
	public void setPrefNames(String[] prefNames) {
		this.prefNames = prefNames;
	}

	/**
	 * @return the prefDescription
	 */
	public String[] getPrefDescription() {
		return prefDescription;
	}

	/**
	 * @param prefDescription
	 *            the prefDescription to set
	 */
	public void setPrefDescription(String[] prefDescription) {
		this.prefDescription = prefDescription;
	}

	/**
	 * @return the prefId
	 */
	public Integer[] getPrefId() {
		return prefId;
	}

	/**
	 * @param prefId
	 *            the prefId to set
	 */
	public void setPrefId(Integer[] prefId) {
		this.prefId = prefId;
	}

	/**
	 * @return the amount
	 */
	public Double[] getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(Double[] amount) {
		this.amount = amount;
	}

	

	/**
	 * @return the prefIdAtGrid
	 */
	public String getPrefIdAtGrid() {
		return prefIdAtGrid;
	}

	/**
	 * @param prefIdAtGrid the prefIdAtGrid to set
	 */
	public void setPrefIdAtGrid(String prefIdAtGrid) {
		this.prefIdAtGrid = prefIdAtGrid;
	}

	/**
	 * @return the originList
	 */
	public List<LabelValueBean> getOriginList() {
		return originList;
	}

	/**
	 * @param originList
	 *            the originList to set
	 */
	public void setOriginList(List<LabelValueBean> originList) {
		this.originList = originList;
	}

	/**
	 * @return the stateTaxApplicable
	 */
	public String getStateTaxApplicable() {
		return stateTaxApplicable;
	}

	/**
	 * @param stateTaxApplicable
	 *            the stateTaxApplicable to set
	 */
	public void setStateTaxApplicable(String stateTaxApplicable) {
		this.stateTaxApplicable = stateTaxApplicable;
	}

	/**
	 * @return the surchargeOnSTApplicable
	 */
	public String getSurchargeOnSTApplicable() {
		return surchargeOnSTApplicable;
	}

	/**
	 * @param surchargeOnSTApplicable
	 *            the surchargeOnSTApplicable to set
	 */
	public void setSurchargeOnSTApplicable(String surchargeOnSTApplicable) {
		this.surchargeOnSTApplicable = surchargeOnSTApplicable;
	}

	/**
	 * @return the ebRateConfigId
	 */
	public Integer getEbRateConfigId() {
		return ebRateConfigId;
	}

	/**
	 * @param ebRateConfigId
	 *            the ebRateConfigId to set
	 */
	public void setEbRateConfigId(Integer ebRateConfigId) {
		this.ebRateConfigId = ebRateConfigId;
	}

	/**
	 * @return the originState
	 */
	public Integer getOriginState() {
		return originState;
	}

	/**
	 * @param originState
	 *            the originState to set
	 */
	public void setOriginState(Integer originState) {
		this.originState = originState;
	}

	/**
	 * @return the serviceTaxApplicable
	 */
	public String getServiceTaxApplicable() {
		return serviceTaxApplicable;
	}

	/**
	 * @param serviceTaxApplicable
	 *            the serviceTaxApplicable to set
	 */
	public void setServiceTaxApplicable(String serviceTaxApplicable) {
		this.serviceTaxApplicable = serviceTaxApplicable;
	}

	/**
	 * @return the cessTaxApplicable
	 */
	public String getCessTaxApplicable() {
		return cessTaxApplicable;
	}

	/**
	 * @param cessTaxApplicable
	 *            the cessTaxApplicable to set
	 */
	public void setCessTaxApplicable(String cessTaxApplicable) {
		this.cessTaxApplicable = cessTaxApplicable;
	}

	/**
	 * @return the hcesstaxApplicable
	 */
	public String getHcesstaxApplicable() {
		return hcesstaxApplicable;
	}

	/**
	 * @param hcesstaxApplicable
	 *            the hcesstaxApplicable to set
	 */
	public void setHcesstaxApplicable(String hcesstaxApplicable) {
		this.hcesstaxApplicable = hcesstaxApplicable;
	}

}
