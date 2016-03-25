package com.ff.to.drs;

import com.capgemini.lbs.framework.utils.StringUtil;


/**
 * @author nihsingh
 *
 */
public class CreditCardDrsDetailsTO extends AbstractDeliveryDetailTO implements Comparable<CreditCardDrsDetailsTO>{
	
	private static final long serialVersionUID = -4187295493691823311L;
	
	
	/** The consignee name. */
	private String consigneeName;
		/**
	 * The consigneeMailingAddress
	 */
	private String consigneeMailingAddress;
	/**
	 * The consigneeNonMailingAddress
	 */
	private String consigneeNonMailingAddress;
	
	private String consigneeMobileNumber;
	
	
	/**
	 * @return consigneeMailingAddress
	 */
	public String getConsigneeMailingAddress() {
		return consigneeMailingAddress;
	}
	/**
	 * @param consigneeMailingAddress
	 */
	public void setConsigneeMailingAddress(String consigneeMailingAddress) {
		this.consigneeMailingAddress = consigneeMailingAddress;
	}
	/**
	 * @return consigneeNonMailingAddress
	 */
	public String getConsigneeNonMailingAddress() {
		return consigneeNonMailingAddress;
	}
	/**
	 * @param consigneeNonMailingAddress
	 */
	public void setConsigneeNonMailingAddress(String consigneeNonMailingAddress) {
		this.consigneeNonMailingAddress = consigneeNonMailingAddress;
	}
	
	
	
	@Override
	public int compareTo(CreditCardDrsDetailsTO arg0) {
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
	 * @return the consigneeMobileNumber
	 */
	public String getConsigneeMobileNumber() {
		return consigneeMobileNumber;
	}
	/**
	 * @param consigneeMobileNumber the consigneeMobileNumber to set
	 */
	public void setConsigneeMobileNumber(String consigneeMobileNumber) {
		this.consigneeMobileNumber = consigneeMobileNumber;
	}
	/**
	 * @return the consigneeName
	 */
	public String getConsigneeName() {
		return consigneeName;
	}
	/**
	 * @param consigneeName the consigneeName to set
	 */
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	
	
}
