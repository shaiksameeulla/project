/**
 * 
 */
package com.ff.to.drs;

import com.capgemini.lbs.framework.utils.StringUtil;


/**
 * @author mohammes
 *
 */
public class RtoCodDrsDetailsTO extends AbstractDeliveryDetailTO implements Comparable<RtoCodDrsDetailsTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -418729549369123311L;
	
	private String referenceNumber;
	
	/** The consignor name. */
	private String consignorName;
	
	/** The consignor code. */
	private String consignorCode;
	
	/** The vendor code. */
	private String vendorCode;
	
	/** The vendor name. */
	private String vendorName;
	
	private Double codAmount;
	/** The lc amount. */
	private Double lcAmount;
	
	/** The to pay amount. */
	private Double toPayAmount;
	
	
	

	/**
	 * @return the referenceNumber
	 */
	public String getReferenceNumber() {
		return referenceNumber;
	}

	/**
	 * @param referenceNumber the referenceNumber to set
	 */
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	
	public int compareTo(RtoCodDrsDetailsTO arg0) {
		int result=0;
		if(!StringUtil.isEmptyInteger(rowNumber) && !StringUtil.isEmptyInteger(arg0.getRowNumber())) {
			result = this.rowNumber.compareTo(arg0.rowNumber);
		}
		else if(!StringUtil.isEmptyLong(deliveryDetailId) && !StringUtil.isEmptyLong(arg0.getDeliveryDetailId())) {
			result = this.deliveryDetailId.compareTo(arg0.deliveryDetailId);
		}
		return result;
	}

	/**
	 * @return the consignorName
	 */
	public String getConsignorName() {
		return consignorName;
	}

	/**
	 * @return the consignorCode
	 */
	public String getConsignorCode() {
		return consignorCode;
	}

	/**
	 * @return the vendorCode
	 */
	public String getVendorCode() {
		return vendorCode;
	}

	/**
	 * @return the vendorName
	 */
	public String getVendorName() {
		return vendorName;
	}

	/**
	 * @return the codAmount
	 */
	public Double getCodAmount() {
		return codAmount;
	}

	/**
	 * @param consignorName the consignorName to set
	 */
	public void setConsignorName(String consignorName) {
		this.consignorName = consignorName;
	}

	/**
	 * @param consignorCode the consignorCode to set
	 */
	public void setConsignorCode(String consignorCode) {
		this.consignorCode = consignorCode;
	}

	/**
	 * @param vendorCode the vendorCode to set
	 */
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	/**
	 * @param vendorName the vendorName to set
	 */
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	/**
	 * @param codAmount the codAmount to set
	 */
	public void setCodAmount(Double codAmount) {
		this.codAmount = codAmount;
	}

	/**
	 * @return the lcAmount
	 */
	public Double getLcAmount() {
		return lcAmount;
	}

	/**
	 * @param lcAmount the lcAmount to set
	 */
	public void setLcAmount(Double lcAmount) {
		this.lcAmount = lcAmount;
	}

	/**
	 * @return the toPayAmount
	 */
	public Double getToPayAmount() {
		return toPayAmount;
	}

	/**
	 * @param toPayAmount the toPayAmount to set
	 */
	public void setToPayAmount(Double toPayAmount) {
		this.toPayAmount = toPayAmount;
	}
	
}
