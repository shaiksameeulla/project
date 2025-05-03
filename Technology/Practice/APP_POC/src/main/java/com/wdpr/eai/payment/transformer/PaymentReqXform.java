package com.wdpr.eai.payment.transformer;

import java.math.BigDecimal;

import dpr.disney.com.adaptivepayment.common.AmountType;
import dpr.disney.com.adaptivepayment.common.AuthorizationDetailsType;
import dpr.disney.com.adaptivepayment.common.AuthorizationModeTypeEnum;
import dpr.disney.com.adaptivepayment.common.AuthorizationTypeEnum;
import dpr.disney.com.adaptivepayment.common.CaptureMethodEnum;
import dpr.disney.com.adaptivepayment.common.CardDisplayType;
import dpr.disney.com.adaptivepayment.common.CardTxnTypeEnum;
import dpr.disney.com.adaptivepayment.common.CardTypeEnum;
import dpr.disney.com.adaptivepayment.common.DescriptorType;
import dpr.disney.com.adaptivepayment.common.DeviceDetailsType;
import dpr.disney.com.adaptivepayment.common.IssuerNameEnum;
import dpr.disney.com.adaptivepayment.common.PaymentCardType;
import dpr.disney.com.adaptivepayment.common.PaymentMethodTypeEnum;
import dpr.disney.com.adaptivepayment.common.ReservationDetailsType;
import dpr.disney.com.adaptivepayment.common.TransactionType;
import dpr.disney.com.adaptivepayment.common.request.RequestTypeEnum;
import dpr.disney.com.adaptivepayment.payment.CardTenderType;
import dpr.disney.com.adaptivepayment.payment.PaymentDataType;
import dpr.disney.com.adaptivepayment.payment.PaymentDetailsType;
import dpr.disney.com.adaptivepayment.payment.PaymentsType;
import dpr.disney.com.adaptivepayment.payment.TenderType;
import dpr.disney.com.adaptivepayment.payment.request.PaymentRequestDetailsType;
import dpr.disney.com.adaptivepayment.payment.request.PaymentRequestType;


public class PaymentReqXform {
	
	private static final PaymentReqXform INSTANCE = new PaymentReqXform();
	private String DEFAULT_CURRENCY_CODE = "USD";   
	private static final String CREDIT_DOC_NUM = "creditDocNum";
	private static final String TERM_REF_DATA = "termRefData";
	
	private static final String reservationNum = "10042906";
	
	private PaymentReqXform() {
		super();
	}
	
	/**
	 * Method Instance
	 * @return PaymentXform
	 */
    public static PaymentReqXform getInstance()
    {
        return INSTANCE;
    }
    
    public PaymentRequestType transform(){
    	
    	PaymentRequestType payment = new PaymentRequestType();
    	
    	
    	
    	payment.setMetadata(RequestMetadataTypeHelper.build());
    	
    	payment.setRequestType(RequestTypeEnum.AUTHORIZATION);
    	
    	//request details
    	payment.setRequestDetails(mapRequestDetails());
    	
    		payment.getRequestDetails().getPaymentData().getTransactionDetails().getReservationDetails()
    			.setNumberOfNights(2);
    	
    	return payment;
    }

    
    private PaymentRequestDetailsType mapRequestDetails() {
    	
    	PaymentRequestDetailsType reqDetails = new PaymentRequestDetailsType();
    	
    	PaymentDataType paymentData = new PaymentDataType();
    	reqDetails.setPaymentData(paymentData);
    	
    	// TransactionType
    	TransactionType transactionType = new TransactionType();
    	paymentData.setTransactionDetails(transactionType);
    	
    	//it's empty in the sample msg, but exists
    	//paymentData.setAccountDetails(new AccountDetailsType());

    	paymentData.setTransactionDetails(mapTransactionDetails());
    	
    	PaymentsType payments = new PaymentsType();
    	paymentData.setPayments(payments);
    	
    	//payment
    	//Will only ever have one payment in the list because PaymentRQ only
    	//supports one payment per request_
    	payments.getPayment().add(mapPayment());
    	
    	return reqDetails;
    }

    private PaymentDetailsType mapPayment() {
    	
    	PaymentDetailsType pmt = new PaymentDetailsType();
    	
    	//amount
    	AmountType amount = new AmountType();
    	pmt.setAmount(amount);
    	amount.setAmount(BigDecimal.valueOf(100));
    	
    		amount.setCurrencyCode(DEFAULT_CURRENCY_CODE);
    	
    	
    	//device  "captureDeviceDetails":{"id":"3533","serialNumber":"2"}
    	DeviceDetailsType device = new DeviceDetailsType();
    	pmt.setCaptureDeviceDetails(device);
    	//limit of 4 chars for terminal number
    	device.setId("1234");
    	device.setSerialNumber("1");
    	
    	//tender details
    	pmt.setTenderDetails(mapTenderDetails());
    	
    	//payment method
    	pmt.setPaymentMethod(PaymentMethodTypeEnum.CARD);
    	
    	return pmt;
    }
    
    private TenderType mapTenderDetails() {
    	
    	TenderType tenderType = new TenderType();
    	CardTenderType cardTenderType = new CardTenderType();
    	tenderType.setPaymentCardDetails(cardTenderType);
    	
    	cardTenderType.setCardDetails(mapCardDetails());
    	
    	cardTenderType.setAuthorizationInfo(mapAuthorizationInfo());
    	
    	return tenderType;
    }
    
    private PaymentCardType mapCardDetails() {
    	
    	PaymentCardType paymentCardType = new PaymentCardType();
    	paymentCardType.setCardType(CardTypeEnum.CREDIT_CARD);
    	
    	paymentCardType.setIssuerName(IssuerNameEnum.VIS);
    	
    	CardDisplayType cardDisplayType = new CardDisplayType();
    	paymentCardType.setDisplayDetails(cardDisplayType);
    	cardDisplayType.setCardType(CardTypeEnum.CREDIT_CARD);
    	
    	return paymentCardType;
    }
    
    private AuthorizationDetailsType mapAuthorizationInfo(){
    	
    	AuthorizationDetailsType authInfo = new AuthorizationDetailsType();
    	authInfo.setEnforcePositiveAuth(false);
    	authInfo.setCardTxnType(CardTxnTypeEnum.CARD_NOT_PRESENT);
    	authInfo.setCaptureMethod(CaptureMethodEnum.KEYED);
    	authInfo.setType(AuthorizationTypeEnum.CHARGE_AUTH);
    	authInfo.setMode(AuthorizationModeTypeEnum.ONLINE);
    	
    	return authInfo;
    }
    
    private TransactionType mapTransactionDetails() {
    	
    	TransactionType transactionType = new TransactionType();
    	transactionType.setOriginatingSystemName("SBC");
    	
    	//PurchaseDetailsType purchaseDetailsType = new PurchaseDetailsType();
    	//transactionType.setPurchaseDetails(purchaseDetailsType);
    	
    	//purchaseDetailsType.setOrderID(itinKey.getParentId());
    	
    	/*
    	 * "purchaseDetails": {}, 
    	 * "reservationDetails": { 
    	 * "reservationNumber": "10042906"
    	 * 	},
		*/

    	ReservationDetailsType reservationDetailsType = new ReservationDetailsType();
    	
    		reservationDetailsType.setReservationNumber(reservationNum);
    		
    		// two freeform text fields that are sent to Stratus in the AF4 
    		DescriptorType creditDocdescriptorType = new DescriptorType();
        	creditDocdescriptorType.setName(CREDIT_DOC_NUM);
        	creditDocdescriptorType.setValue(reservationNum);
        	
        	transactionType.getTransCharacteristics().add(creditDocdescriptorType);
        	
        	DescriptorType termRefdescriptorType = new DescriptorType();
        	termRefdescriptorType.setName(TERM_REF_DATA);
        	termRefdescriptorType.setValue(reservationNum);
        	transactionType.getTransCharacteristics().add(termRefdescriptorType);
    		
    	transactionType.setReservationDetails(reservationDetailsType);

    	return transactionType;
    }
    
   
}
