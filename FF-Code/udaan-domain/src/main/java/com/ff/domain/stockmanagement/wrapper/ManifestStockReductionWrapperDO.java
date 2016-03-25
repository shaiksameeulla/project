package com.ff.domain.stockmanagement.wrapper;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.ff.domain.consignment.ChildConsignmentDO;
import com.ff.domain.consignment.ConsignmentDO;

/**
 * @author hkansagr
 * 
 */
public class ManifestStockReductionWrapperDO {

	public ManifestStockReductionWrapperDO() {
	}

	/**
	 * @param manifestNo
	 * @param bagLockNo
	 * @param transactionDate
	 * @param transactionCreatedOfficeId
	 */
	public ManifestStockReductionWrapperDO(String manifestNo, String bagLockNo,
			Date transactionDate, Integer transactionCreatedOfficeId) {
		this.manifestNo = manifestNo;
		this.bagLockNo = bagLockNo;
		this.transactionDate = transactionDate;
		this.transactionCreatedOfficeId = transactionCreatedOfficeId;
	}
	public ManifestStockReductionWrapperDO(String manifestNo, String bagLockNo,
			Date transactionDate, Integer transactionCreatedOfficeId,Integer operatingOffice) {
		this.manifestNo = manifestNo;
		this.bagLockNo = bagLockNo;
		this.transactionDate = transactionDate;
		this.transactionCreatedOfficeId = transactionCreatedOfficeId;
		this.operatingOffice=operatingOffice;
	}
	
	public ManifestStockReductionWrapperDO(Integer manifestId,String manifestNo, String bagLockNo,
			Date transactionDate, Integer transactionCreatedOfficeId,Integer operatingOffice) {
		this.manifestId=manifestId;
		this.manifestNo = manifestNo;
		this.bagLockNo = bagLockNo;
		this.transactionDate = transactionDate;
		this.transactionCreatedOfficeId = transactionCreatedOfficeId;
		this.operatingOffice=operatingOffice;
	}

	/**
	 * @param consgDO
	 */
	public ManifestStockReductionWrapperDO(ConsignmentDO consgDO) {
		this.manifestNo = consgDO.getConsgNo();
		this.transactionDate = consgDO.getCreatedDate();
		this.transactionCreatedOfficeId = consgDO.getOrgOffId();
		this.childCNs = (!CGCollectionUtils.isEmpty(consgDO.getChildCNs())) ? consgDO
				.getChildCNs() : null;
	}

	/** The manifest number - OGM/ BPL/ MBPL. */
	private Integer manifestId;
	/** The manifest number - OGM/ BPL/ MBPL. */
	private String manifestNo;

	/** The manifest number - Bag Lock No. */
	private String bagLockNo;

	/** The transaction date. */
	private Date transactionDate;

	/** The transaction created office. */
	private Integer transactionCreatedOfficeId;
	
	private Integer operatingOffice;

	/** The child consignment set. */
	private Set<ChildConsignmentDO> childCNs;

	/**
	 * @return the childCNs
	 */
	public Set<ChildConsignmentDO> getChildCNs() {
		return childCNs;
	}

	/**
	 * @param childCNs
	 *            the childCNs to set
	 */
	public void setChildCNs(Set<ChildConsignmentDO> childCNs) {
		this.childCNs = childCNs;
	}

	/**
	 * @return the transactionCreatedOfficeId
	 */
	public Integer getTransactionCreatedOfficeId() {
		return transactionCreatedOfficeId;
	}

	/**
	 * @param transactionCreatedOfficeId
	 *            the transactionCreatedOfficeId to set
	 */
	public void setTransactionCreatedOfficeId(Integer transactionCreatedOfficeId) {
		this.transactionCreatedOfficeId = transactionCreatedOfficeId;
	}

	/**
	 * @return the bagLockNo
	 */
	public String getBagLockNo() {
		return bagLockNo;
	}

	/**
	 * @param bagLockNo
	 *            the bagLockNo to set
	 */
	public void setBagLockNo(String bagLockNo) {
		this.bagLockNo = bagLockNo;
	}

	/**
	 * @return the manifestNo
	 */
	public String getManifestNo() {
		return manifestNo;
	}

	/**
	 * @param manifestNo
	 *            the manifestNo to set
	 */
	public void setManifestNo(String manifestNo) {
		this.manifestNo = manifestNo;
	}

	/**
	 * @return the transactionDate
	 */
	public Date getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate
	 *            the transactionDate to set
	 */
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * @return the operatingOffice
	 */
	public Integer getOperatingOffice() {
		return operatingOffice;
	}

	/**
	 * @return the manifestId
	 */
	public Integer getManifestId() {
		return manifestId;
	}

	/**
	 * @param manifestId the manifestId to set
	 */
	public void setManifestId(Integer manifestId) {
		this.manifestId = manifestId;
	}

	/**
	 * @param operatingOffice the operatingOffice to set
	 */
	public void setOperatingOffice(Integer operatingOffice) {
		this.operatingOffice = operatingOffice;
	}

}
