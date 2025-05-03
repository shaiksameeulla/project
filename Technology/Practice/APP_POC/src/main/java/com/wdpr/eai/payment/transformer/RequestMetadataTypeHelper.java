package com.wdpr.eai.payment.transformer;
import com.wdpr.eai.payment.security.EncryptionCustomHandler;
import com.wdpr.eai.payment.util.JSONConverter;
import com.wdpr.payment.helper.PropertiesUtil;

import dpr.disney.com.adaptivepayment.common.AlgorithmTypeEnum;
import dpr.disney.com.adaptivepayment.common.ConversationDetailsType;
import dpr.disney.com.adaptivepayment.common.CryptoMetadataType;
import dpr.disney.com.adaptivepayment.common.KeyInfoType;
import dpr.disney.com.adaptivepayment.common.LineOfBusinessTypeEnum;
import dpr.disney.com.adaptivepayment.common.LocationInfoType;
import dpr.disney.com.adaptivepayment.common.PersonIDType;
import dpr.disney.com.adaptivepayment.common.PersonProfileType;
import dpr.disney.com.adaptivepayment.common.TransformationTypeEnum;
import dpr.disney.com.adaptivepayment.common.request.ChannelTypeEnum;
import dpr.disney.com.adaptivepayment.common.request.ClientDetailsType;
import dpr.disney.com.adaptivepayment.common.request.OriginDetailsType;
import dpr.disney.com.adaptivepayment.common.request.RequestIdentityType;
import dpr.disney.com.adaptivepayment.common.request.RequestMetadataType;
import dpr.disney.com.adaptivepayment.common.request.RequestSourceType;

public class RequestMetadataTypeHelper {
	
	public static RequestMetadataType metaData= new RequestMetadataType();
	
	private static RequestMetadataType createId() {

		RequestIdentityType metaDataId = new RequestIdentityType();
		
		metaDataId.setId("88e8c4dc-a334-219-623c-68245c77294");

		ConversationDetailsType conversationDetails = new ConversationDetailsType();
		metaDataId.setConversationDetails(conversationDetails);

		conversationDetails.setConversationID("bESBOUKuaO4T_j6JV54aRnI");
		metaData.setLocalBusTime("2018-05-16T20:58:01-0700");




		final PersonProfileType person = new PersonProfileType();
		final PersonIDType personId = new PersonIDType();
		personId.setId("JOSEN027");
		person.setId(personId);
		metaData.setPersonData(person);
		metaData.setId(metaDataId);
		
		return metaData;
	}
	
	private static void createSource(){
		
		RequestSourceType src = new RequestSourceType();
		
		//TODO: need real values in place of hard coded values
		//client
		ClientDetailsType clt = new ClientDetailsType();
		clt.setId("DLPSTsdfsf65877395712d4a0a9d191d95d32");
		clt.setName("TBX");
		clt.setBusinessSegmentCD("DLP");
		clt.setCompanyCode("WDW");
		clt.getLineOfBusinessCodes().add(LineOfBusinessTypeEnum.RESORT_RESERVATION);
		src.setClient(clt);
		
		//TODO: per Dhiren on 6/9 don't map or delete until they hear from payment team
		//requestOrigin
		OriginDetailsType reqOrigin = new OriginDetailsType();
		
		reqOrigin.setChannelType(ChannelTypeEnum.CALL_CENTER);
		reqOrigin.setElectronicCommIndicator("08");
		LocationInfoType loc = new LocationInfoType();
		
		loc.setLocationId("LOC1");
		loc.setTimeZone("EST");
		reqOrigin.setLocation(loc);
		src.setRequestOrigin(reqOrigin);
		metaData.setSource(src);
		//sourceDevice - do not send any value if source_device_id is not available in PaymentRQ
		//DeviceDetailsType srcDevice = new DeviceDetailsType();
		//src.setSourceDevice(srcDevice);
		//srcDevice.setId(PaymentAdapterDefaults.CAPTURE_SOURCE_DEVICE.getName());
	}
	
	private static void createCryptoMetaData() {
		
		CryptoMetadataType cryptoMD = new CryptoMetadataType();
		
		
		KeyInfoType keyInfo = new KeyInfoType();
		
		keyInfo.setAsymmetricKeyAlgorithm(AlgorithmTypeEnum.RSA);
		keyInfo.setAsymmetricKeyAlias(EncryptionCustomHandler.getPropertyValue("encryption.alias"));
		// 11/5/2015 getPvtKeyAlias
		keyInfo.setAsymmetricKeyTransformation(TransformationTypeEnum.RSA_ECB_PKCS_1_PADDING);
		keyInfo.setSymmetricKeyAlgorithm(AlgorithmTypeEnum.DES);
		keyInfo.setSymmetricKeyTransformation(TransformationTypeEnum.DES_ECB_PKCS_5_PADDING);
		
		//TODO: code creation of ASymmetricKeyContent (signature)
		try {
			keyInfo.setSymmetricKeyContent(EncryptionCustomHandler.getHexEncodedEncryptedSymmetricKey());
			//TODO
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		cryptoMD.setKeyInfo(keyInfo);
		metaData.setCryptoMetadata(cryptoMD);
		
	}
	public static RequestMetadataType build() {
		createId();
		createCryptoMetaData();
		createSource();
		System.out.println(JSONConverter.convertJavaToJSON(metaData));
		return metaData;
		
	}

}
