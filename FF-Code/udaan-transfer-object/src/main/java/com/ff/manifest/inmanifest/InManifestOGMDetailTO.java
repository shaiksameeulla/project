/**
 * 
 */
package com.ff.manifest.inmanifest;

import com.capgemini.lbs.framework.utils.StringUtil;

/**
 * @author uchauhan
 *
 */
public class InManifestOGMDetailTO extends InManifestDetailsTO implements Comparable<InManifestOGMDetailTO>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4420042453933987251L;
	private String consignmentNumber;
	private String mobileNumber;
	private Integer consignmentManifestId;
	private Integer comailManifestId;
	private Integer comailId;
	private String comailNo;
	private String isCN;
	private Integer position;

	private Integer productId;
	private Double toPayAmt;
	private Double codAmt;//cod/lc amt
	private Double baAmt;
	private String lcBankName;
	
	
	/**
	 * @return the consignmentNumber
	 */
	public String getConsignmentNumber() {
		return consignmentNumber;
	}
	/**
	 * @param consignmentNumber the consignmentNumber to set
	 */
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}
	/**
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}
	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	/**
	 * @return the consignmentManifestId
	 */
	public Integer getConsignmentManifestId() {
		return consignmentManifestId;
	}
	/**
	 * @param consignmentManifestId the consignmentManifestId to set
	 */
	public void setConsignmentManifestId(Integer consignmentManifestId) {
		this.consignmentManifestId = consignmentManifestId;
	}
	/**
	 * @return the comailId
	 */
	public Integer getComailId() {
		return comailId;
	}
	/**
	 * @param comailId the comailId to set
	 */
	public void setComailId(Integer comailId) {
		this.comailId = comailId;
	}
	/**
	 * @return the comailNo
	 */
	public String getComailNo() {
		return comailNo;
	}
	/**
	 * @param comailNo the comailNo to set
	 */
	public void setComailNo(String comailNo) {
		this.comailNo = comailNo;
	}
	/**
	 * @return the isCN
	 */
	public String getIsCN() {
		return isCN;
	}
	/**
	 * @param isCN the isCN to set
	 */
	public void setIsCN(String isCN) {
		this.isCN = isCN;
	}
	/**
	 * @return the comailManifestId
	 */
	public Integer getComailManifestId() {
		return comailManifestId;
	}
	/**
	 * @param comailManifestId the comailManifestId to set
	 */
	public void setComailManifestId(Integer comailManifestId) {
		this.comailManifestId = comailManifestId;
	}
	/**
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Double getToPayAmt() {
		return toPayAmt;
	}
	public void setToPayAmt(Double toPayAmt) {
		this.toPayAmt = toPayAmt;
	}
	public Double getCodAmt() {
		return codAmt;
	}
	public void setCodAmt(Double codAmt) {
		this.codAmt = codAmt;
	}
	public String getLcBankName() {
		return lcBankName;
	}
	public void setLcBankName(String lcBankName) {
		this.lcBankName = lcBankName;
	}	
	@Override
	public int compareTo(InManifestOGMDetailTO arg0) {
		int returnVal = 0;
		if(!StringUtil.isEmptyInteger(position) &&
				!StringUtil.isEmptyInteger(arg0.getPosition())){
			returnVal = position.compareTo(arg0.position);
		}
		return returnVal;
	}
	/**
	 * @return the baAmt
	 */
	public Double getBaAmt() {
		return baAmt;
	}
	/**
	 * @param baAmt the baAmt to set
	 */
	public void setBaAmt(Double baAmt) {
		this.baAmt = baAmt;
	}
	
}
