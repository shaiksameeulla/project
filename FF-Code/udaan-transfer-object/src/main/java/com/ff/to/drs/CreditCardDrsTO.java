package com.ff.to.drs;

import java.util.List;



/**
 * @author nihsingh
 *
 */
public class CreditCardDrsTO extends AbstractDeliveryTO {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6835020080921259485L;

	
	/**
	 * The Row consignee Name
	 */
	private  String   consigneeName [] = new String[rowCount];
	/**
	 * The Row consignee Mailing address 
	 */
	private  String   consigneeMailingAddress [] = new String[rowCount];
	/**
	 *  The Row consignee non Mailing address 
	 */
	private  String   consigneeNonMailingAddress [] = new String[rowCount];
	
	
	
	
	 List<CreditCardDrsDetailsTO> creditCardDrsdetailsToList;

	
	/**
	 * @return creditCardDrsdetailsToList
	 */
	public List<CreditCardDrsDetailsTO> getCreditCardDrsdetailsToList() {
		return creditCardDrsdetailsToList;
	}

	/**
	 * @param creditCardDrsdetailsToList
	 */
	public void setCreditCardDrsdetailsToList(
			List<CreditCardDrsDetailsTO> creditCardDrsdetailsToList) {
		this.creditCardDrsdetailsToList = creditCardDrsdetailsToList;
	}

	/**
	 * @return consigneeName
	 */
	public String[] getConsigneeName() {
		return consigneeName;
	}

	/**
	 * @param consigneeName
	 */
	public void setConsigneeName(String[] consigneeName) {
		this.consigneeName = consigneeName;
	}

	/**
	 * @return consigneeMailingAddress
	 */
	public String[] getConsigneeMailingAddress() {
		return consigneeMailingAddress;
	}

	/**
	 * @param consigneeMailingAddress
	 */
	public void setConsigneeMailingAddress(String[] consigneeMailingAddress) {
		this.consigneeMailingAddress = consigneeMailingAddress;
	}

	/**
	 * @return consigneeNonMailingAddress
	 */
	public String[] getConsigneeNonMailingAddress() {
		return consigneeNonMailingAddress;
	}

	/**
	 * @param consigneeNonMailingAddress
	 */
	public void setConsigneeNonMailingAddress(String[] consigneeNonMailingAddress) {
		this.consigneeNonMailingAddress = consigneeNonMailingAddress;
	}
}
