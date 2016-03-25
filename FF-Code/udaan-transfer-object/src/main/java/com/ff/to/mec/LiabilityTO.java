package com.ff.to.mec;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.business.CustomerTO;
import com.ff.geography.RegionTO;

/**
 * @author amimehta
 */

public class LiabilityTO extends CGBaseTO {

	private static final long serialVersionUID = 1L;

	private Integer liabilityId;// hidden
	private String liabilityDate;
	private Integer regionId;
	private String regionName;
	private List<RegionTO> regionList;

	private String custName;
	private List<CustomerTO> custNameList;
	private String custCode;
	private Integer custId;
	private String chqNo;
	private String chqDate;
	private Integer bankId;
	private String bankName;
	private String bankGL;
	private List<LabelValueBean> bankNameList;
	private Double liabilityAmt;
	public int rowCount;
	private Integer loginOfficeId;// hidden
	/** The loggedIn Office Code */
	private String loginOfficeCode;// hidden
	/** The consg id list at grid. */
	private String consgIdListAtGrid;

	private List<LiabilityDetailsTO> liabilityDetailsTOList;

	private String txNumber;
	private String liabilityStatus;
	private Integer liabilityOffice;
	private String officeType;

	private Integer[] consgId = new Integer[rowCount];
	private String[] consgNo = new String[rowCount];
	private String[] bookingDates = new String[rowCount];
	private Double[] codLcAmt = new Double[rowCount];
	private Double[] collectedAmt = new Double[rowCount];
	private Double[] paidAmt = new Double[rowCount];
	private String[] isSelect = new String[rowCount];
	private Integer[] postions = new Integer[rowCount];
	private Integer[] libilityDetailsId = new Integer[rowCount];
	private String[] isChecked = new String[rowCount];
	private Double[] balanceAmount= new Double[rowCount];
	private Integer[] collectionEntriesId= new Integer[rowCount];
	

	private Integer createdBy;
	private Integer updatedBy;

	private String paymentMode;
	// private String paymentModeName;
	private List<LabelValueBean> paymentModeList;
	private String RHOName;
	// If logged in from RHO - default selected region Id
	private Integer selectedRegionId;
	// Previous selected region id
	private Integer prevSelRegionId;

	/** The max paging row allowed in one page for liability */
	private String maxPagingRowAllowed;
	private String transMsg;
	
	private Integer currentPageNumber;
	private Double currentPageAmount;
	private Integer totalNoPages;
	private String navigationType;
	
	private List<LiabilityPageTO> pageList=null;

	/**
	 * @return the bookingDates
	 */
	public String[] getBookingDates() {
		return bookingDates;
	}

	/**
	 * @param bookingDates
	 *            the bookingDates to set
	 */
	public void setBookingDates(String[] bookingDates) {
		this.bookingDates = bookingDates;
	}

	/**
	 * @return the transMsg
	 */
	public String getTransMsg() {
		return transMsg;
	}

	/**
	 * @param transMsg
	 *            the transMsg to set
	 */
	public void setTransMsg(String transMsg) {
		this.transMsg = transMsg;
	}

	/**
	 * @return the prevSelRegionId
	 */
	public Integer getPrevSelRegionId() {
		return prevSelRegionId;
	}

	/**
	 * @param prevSelRegionId
	 *            the prevSelRegionId to set
	 */
	public void setPrevSelRegionId(Integer prevSelRegionId) {
		this.prevSelRegionId = prevSelRegionId;
	}

	/**
	 * @return the maxPagingRowAllowed
	 */
	public String getMaxPagingRowAllowed() {
		return maxPagingRowAllowed;
	}

	/**
	 * @param maxPagingRowAllowed
	 *            the maxPagingRowAllowed to set
	 */
	public void setMaxPagingRowAllowed(String maxPagingRowAllowed) {
		this.maxPagingRowAllowed = maxPagingRowAllowed;
	}

	/**
	 * @return the selectedRegionId
	 */
	public Integer getSelectedRegionId() {
		return selectedRegionId;
	}

	/**
	 * @param selectedRegionId
	 *            the selectedRegionId to set
	 */
	public void setSelectedRegionId(Integer selectedRegionId) {
		this.selectedRegionId = selectedRegionId;
	}

	/**
	 * @return the rHOName
	 */
	public String getRHOName() {
		return RHOName;
	}

	/**
	 * @param rHOName
	 *            the rHOName to set
	 */
	public void setRHOName(String rHOName) {
		RHOName = rHOName;
	}

	/**
	 * @return the officeType
	 */
	public String getOfficeType() {
		return officeType;
	}

	/**
	 * @param officeType
	 *            the officeType to set
	 */
	public void setOfficeType(String officeType) {
		this.officeType = officeType;
	}

	public String getBankGL() {
		return bankGL;
	}

	public void setBankGL(String bankGL) {
		this.bankGL = bankGL;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public Integer getLiabilityId() {
		return liabilityId;
	}

	public void setLiabilityId(Integer liabilityId) {
		this.liabilityId = liabilityId;
	}

	public String getLiabilityDate() {
		return liabilityDate;
	}

	public void setLiabilityDate(String liabilityDate) {
		this.liabilityDate = liabilityDate;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public List<RegionTO> getRegionList() {
		return regionList;
	}

	public void setRegionList(List<RegionTO> regionList) {
		this.regionList = regionList;
	}

	public List<CustomerTO> getCustNameList() {
		return custNameList;
	}

	public void setCustNameList(List<CustomerTO> custNameList) {
		this.custNameList = custNameList;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getChqNo() {
		return chqNo;
	}

	public void setChqNo(String chqNo) {
		this.chqNo = chqNo;
	}

	public String getChqDate() {
		return chqDate;
	}

	public void setChqDate(String chqDate) {
		this.chqDate = chqDate;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public List<LabelValueBean> getBankNameList() {
		return bankNameList;
	}

	public void setBankNameList(List<LabelValueBean> bankNameList) {
		this.bankNameList = bankNameList;
	}

	public Double getLiabilityAmt() {
		return liabilityAmt;
	}

	public void setLiabilityAmt(Double liabilityAmt) {
		this.liabilityAmt = liabilityAmt;
	}

	public List<LiabilityDetailsTO> getLiabilityDetailsTOList() {
		return liabilityDetailsTOList;
	}

	public void setLiabilityDetailsTOList(
			List<LiabilityDetailsTO> liabilityDetailsTOList) {
		this.liabilityDetailsTOList = liabilityDetailsTOList;
	}

	public Integer getLoginOfficeId() {
		return loginOfficeId;
	}

	public void setLoginOfficeId(Integer loginOfficeId) {
		this.loginOfficeId = loginOfficeId;
	}

	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public String getConsgIdListAtGrid() {
		return consgIdListAtGrid;
	}

	public void setConsgIdListAtGrid(String consgIdListAtGrid) {
		this.consgIdListAtGrid = consgIdListAtGrid;
	}

	public Integer[] getConsgId() {
		return consgId;
	}

	public void setConsgId(Integer[] consgId) {
		this.consgId = consgId;
	}

	public String[] getConsgNo() {
		return consgNo;
	}

	public void setConsgNo(String[] consgNo) {
		this.consgNo = consgNo;
	}

	public Double[] getCodLcAmt() {
		return codLcAmt;
	}

	public void setCodLcAmt(Double[] codLcAmt) {
		this.codLcAmt = codLcAmt;
	}

	public Double[] getCollectedAmt() {
		return collectedAmt;
	}

	public void setCollectedAmt(Double[] collectedAmt) {
		this.collectedAmt = collectedAmt;
	}

	public Double[] getPaidAmt() {
		return paidAmt;
	}

	public void setPaidAmt(Double[] paidAmt) {
		this.paidAmt = paidAmt;
	}

	public String[] getIsSelect() {
		return isSelect;
	}

	public void setIsSelect(String[] isSelect) {
		this.isSelect = isSelect;
	}

	public String getTxNumber() {
		return txNumber;
	}

	public void setTxNumber(String txNumber) {
		this.txNumber = txNumber;
	}

	public String getLoginOfficeCode() {
		return loginOfficeCode;
	}

	public void setLoginOfficeCode(String loginOfficeCode) {
		this.loginOfficeCode = loginOfficeCode;
	}

	public String getLiabilityStatus() {
		return liabilityStatus;
	}

	public void setLiabilityStatus(String liabilityStatus) {
		this.liabilityStatus = liabilityStatus;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Integer[] getPostions() {
		return postions;
	}

	public void setPostions(Integer[] postions) {
		this.postions = postions;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public List<LabelValueBean> getPaymentModeList() {
		return paymentModeList;
	}

	public void setPaymentModeList(List<LabelValueBean> paymentModeList) {
		this.paymentModeList = paymentModeList;
	}

	public Integer getLiabilityOffice() {
		return liabilityOffice;
	}

	public void setLiabilityOffice(Integer liabilityOffice) {
		this.liabilityOffice = liabilityOffice;
	}

	/**
	 * @return the libilityDetailsId
	 */
	public Integer[] getLibilityDetailsId() {
		return libilityDetailsId;
	}

	/**
	 * @param libilityDetailsId
	 *            the libilityDetailsId to set
	 */
	public void setLibilityDetailsId(Integer[] libilityDetailsId) {
		this.libilityDetailsId = libilityDetailsId;
	}

	public String[] getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String[] isChecked) {
		this.isChecked = isChecked;
	}

	/**
	 * @return the currentPageNumber
	 */
	public Integer getCurrentPageNumber() {
		return currentPageNumber;
	}

	/**
	 * @param currentPageNumber the currentPageNumber to set
	 */
	public void setCurrentPageNumber(Integer currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
	}

	/**
	 * @return the currentPageAmount
	 */
	public Double getCurrentPageAmount() {
		return currentPageAmount;
	}

	/**
	 * @param currentPageAmount the currentPageAmount to set
	 */
	public void setCurrentPageAmount(Double currentPageAmount) {
		this.currentPageAmount = currentPageAmount;
	}

	/**
	 * @return the totalNoPages
	 */
	public Integer getTotalNoPages() {
		return totalNoPages;
	}

	/**
	 * @param totalNoPages the totalNoPages to set
	 */
	public void setTotalNoPages(Integer totalNoPages) {
		this.totalNoPages = totalNoPages;
	}

	/**
	 * @return the navigationType
	 */
	public String getNavigationType() {
		return navigationType;
	}

	/**
	 * @param navigationType the navigationType to set
	 */
	public void setNavigationType(String navigationType) {
		this.navigationType = navigationType;
	}

	/**
	 * @return the pageList
	 */
	public List<LiabilityPageTO> getPageList() {
		return pageList;
	}

	/**
	 * @param pageList the pageList to set
	 */
	public void setPageList(List<LiabilityPageTO> pageList) {
		this.pageList = pageList;
	}

	/**
	 * @return the balanceAmount
	 */
	public Double[] getBalanceAmount() {
		return balanceAmount;
	}

	/**
	 * @param balanceAmount the balanceAmount to set
	 */
	public void setBalanceAmount(Double[] balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	/**
	 * @return the collectionEntriesId
	 */
	public Integer[] getCollectionEntriesId() {
		return collectionEntriesId;
	}

	/**
	 * @param collectionEntriesId the collectionEntriesId to set
	 */
	public void setCollectionEntriesId(Integer[] collectionEntriesId) {
		this.collectionEntriesId = collectionEntriesId;
	}

	

}
