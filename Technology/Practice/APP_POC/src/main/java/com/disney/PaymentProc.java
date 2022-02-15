package com.disney;
import java.security.Key;
import java.util.Map;

import com.wdpr.eai.payment.transformer.CardDescriptorTypeMapper;
import com.wdpr.eai.payment.transformer.PaymentReqXform;
import com.wdpr.eai.payment.transformer.RequestMetadataTypeHelper;
import com.wdpr.eai.payment.util.JSONConverter;
import com.wdpr.payment.helper.AppConstant;
import com.wdpr.payment.helper.CryptoHelper;
import com.wdpr.payment.sdk.PaymentManager;

import dpr.disney.com.adaptivepayment.common.CardDescriptorType;
import dpr.disney.com.adaptivepayment.common.DescriptorType;
import dpr.disney.com.adaptivepayment.payment.PaymentDetailsType;
import dpr.disney.com.adaptivepayment.payment.request.PaymentRequestType;
import dpr.disney.com.adaptivepayment.payment.response.PaymentResponseType;

public class PaymentProc  {
	
	public static  void main(String s[]) {
		process();
	}

	public static void process() {

		try	{
			PaymentRequestType paymentRequestType = PaymentReqXform.getInstance().transform();
			
			
			PaymentDetailsType pmtDetailsType = paymentRequestType.getRequestDetails().getPaymentData().getPayments().getPayment().get(0);
			final PaymentManager pmtMgr = new PaymentManager();
			CardDescriptorType cardDesc = CardDescriptorTypeMapper.getInstance().mapCardDescriptorType();
			
			System.out.println("CC :"+JSONConverter.convertJavaToJSON(cardDesc));

            final Key symmKey = CryptoHelper.getSymmetricKey();
            final Map<String, String> encrypMsg = pmtMgr.encryptJavaObject(cardDesc, true, symmKey);

            pmtDetailsType.getTenderDetails().getPaymentCardDetails().getCardDetails().
            	setEncryptedCardDescriptor(encrypMsg.get(AppConstant.ENC_MSG));
            pmtMgr.addSymmetricKeytoMetadata(paymentRequestType, encrypMsg.get(AppConstant.SYM_KEY));

            System.out.println("PaymentRQ: "+JSONConverter.convertJavaToJSON(paymentRequestType));

			PaymentResponseType paymentResponseType = pmtMgr.makePaymentAuthorization(paymentRequestType, false);

			if (paymentResponseType != null) {
				System.out.println("Got Response from APP");
				System.out.println(JSONConverter.convertJavaToJSON(paymentResponseType));
				
				if(paymentResponseType.getMetadata() !=null) {
					if(paymentResponseType.getMetadata().getStatus()!=null) {
						if(paymentResponseType.getMetadata().getStatus().getStatusCode() == "ERROR") {
						for (DescriptorType type :paymentResponseType.getMetadata().getStatus().getErrors()) {
							System.out.println(type.getName() +"-->"+type.getValue());
						}
						}else {
							System.out.println(paymentResponseType.getMetadata().getStatus().getStatusCode());
						}
					}
				}
			} else {
	    		System.out.println("error");
	    	}

		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	
}
