package com.wdpr.eai.payment.transformer;

import dpr.disney.com.adaptivepayment.common.AddressDetailsType;
import dpr.disney.com.adaptivepayment.common.AddressTypeEnum;
import dpr.disney.com.adaptivepayment.common.CardDescriptorType;
import dpr.disney.com.adaptivepayment.common.CardNumberType;
import dpr.disney.com.adaptivepayment.common.CardSecurityCodeEnum;
import dpr.disney.com.adaptivepayment.common.CardSecurityCodeType;

public class CardDescriptorTypeMapper {
	private static final CardDescriptorTypeMapper INSTANCE = new CardDescriptorTypeMapper();

	private CardDescriptorTypeMapper() {
		super();
	}

	/**
	 * Method Instance
	 * @return CardDescriptorTypeMapper
	 */
	public static CardDescriptorTypeMapper getInstance() {
		return INSTANCE;
	}

	public CardDescriptorType mapCardDescriptorType() {


		CardDescriptorType cardDesc = new CardDescriptorType();

		CardNumberType cardNum = new CardNumberType();
		cardDesc.setCardNumber(cardNum);
		cardNum.setPan("4444333322221111");
		cardDesc.setEmbossedName("Rajesh");


		cardDesc.setBillingAddress(mapBillingAddress());


		cardDesc.setCardExpiry("12/18");

		CardSecurityCodeType cardSecurityCode = new CardSecurityCodeType();
		cardSecurityCode.setType(CardSecurityCodeEnum.CVV_2);


		cardSecurityCode.setCardSecurityValue("123");

		cardDesc.setCardSecurityCode(cardSecurityCode);

		return cardDesc;
	}


	private AddressDetailsType mapBillingAddress() {

		AddressDetailsType billAddress = new AddressDetailsType();

		billAddress.setType(AddressTypeEnum.BILLING);


		billAddress.getAddressLine().add("Addressline1");

		billAddress.getAddressLine().add("Addressline2");

		billAddress.setCity("Orlando");
		billAddress.setStateCode("Florida");
		billAddress.setCountry("US");
		billAddress.setPostalCode("32812");

		billAddress.setVerified(false);
		billAddress.setDefault(false);

		return billAddress;
	}

}
