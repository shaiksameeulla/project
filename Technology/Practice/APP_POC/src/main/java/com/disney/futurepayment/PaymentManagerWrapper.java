package com.wdpr.payment.sdk;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wdpr.payment.auth.AuthTokenResponse;
import com.wdpr.payment.auth.AuthzSecurityClient;
import com.wdpr.payment.helper.AppSdkSignatureHelper;
import com.wdpr.payment.helper.CryptoHelper;
import com.wdpr.payment.helper.HttpHelper;
import com.wdpr.payment.util.PaymentUtil;
import com.wdpr.payment.vo.ConfigurationVO;

import dpr.disney.com.adaptivepayment.common.AlgorithmTypeEnum;
import dpr.disney.com.adaptivepayment.common.CryptoMetadataType;
import dpr.disney.com.adaptivepayment.common.KeyInfoType;
import dpr.disney.com.adaptivepayment.common.TransformationTypeEnum;
import dpr.disney.com.adaptivepayment.common.request.BaseRequestType;
import dpr.disney.com.adaptivepayment.inquiry.request.GetCardBalanceRequestType;
import dpr.disney.com.adaptivepayment.inquiry.request.GetTransactionDetailsRequestType;
import dpr.disney.com.adaptivepayment.inquiry.request.GetTransactionSummaryRequestType;
import dpr.disney.com.adaptivepayment.inquiry.request.RefineSearchCriteriaRequestType;
import dpr.disney.com.adaptivepayment.inquiry.request.ResolveCardIssuerRequestType;
import dpr.disney.com.adaptivepayment.inquiry.response.GetCardBalanceResponseType;
import dpr.disney.com.adaptivepayment.inquiry.response.GetTransactionDetailsResponseType;
import dpr.disney.com.adaptivepayment.inquiry.response.GetTransactionSummaryResultType;
import dpr.disney.com.adaptivepayment.inquiry.response.RefineSearchCriteriaResultType;
import dpr.disney.com.adaptivepayment.inquiry.response.ResolveCardIssuerResponseType;
import dpr.disney.com.adaptivepayment.misc.request.RefundRequestType;
import dpr.disney.com.adaptivepayment.misc.response.RefundResponseType;
import dpr.disney.com.adaptivepayment.payment.request.AcknowledgeTransactionRequestType;
import dpr.disney.com.adaptivepayment.payment.request.PaymentRequestType;
import dpr.disney.com.adaptivepayment.payment.request.PerformFraudCheckRequestType;
import dpr.disney.com.adaptivepayment.payment.request.VoidPaymentRequestType;
import dpr.disney.com.adaptivepayment.payment.response.AcknowledgeTransactionResponseType;
import dpr.disney.com.adaptivepayment.payment.response.PaymentResponseType;
import dpr.disney.com.adaptivepayment.payment.response.PerformFraudCheckResponseType;
import dpr.disney.com.adaptivepayment.payment.response.VoidPaymentResponseType;
import dpr.disney.com.adaptivepayment.paymentsession.request.EstablishSessionRequestType;
import dpr.disney.com.adaptivepayment.paymentsession.request.FinalizeSessionRequestType;
import dpr.disney.com.adaptivepayment.paymentsession.request.GetSessionByTokenRequestType;
import dpr.disney.com.adaptivepayment.paymentsession.response.EstablishSessionResponseType;
import dpr.disney.com.adaptivepayment.paymentsession.response.FinalizeSessionResponseType;
import dpr.disney.com.adaptivepayment.paymentsession.response.GetSessionByTokenResponseType;

public class PaymentManagerWrapper {
	private static ObjectMapper staticMaper = new ObjectMapper();
	private ObjectMapper mapper;
	private Properties appProperties;
	private AuthTokenResponse authzResponse;
	private Map<String, String> customProperties;

	public PaymentManagerWrapper() {
		this((ConfigurationVO) null);
	}

	public PaymentManagerWrapper(ConfigurationVO config) {
		if (null != config && null != config.getPropertyFile()) {
			this.appProperties = PaymentUtil.loadProperties(config.getPropertyFile());
		} else {
			this.appProperties = PaymentUtil.loadProperties((String) null);
		}

		HttpHelper.setHTTPConfiguration(this.appProperties);
	}

	public AcknowledgeTransactionResponseType acknowledgeTxn(AcknowledgeTransactionRequestType request)
			throws Exception {
		AcknowledgeTransactionResponseType response = null;
		String acknowledgeTxnURL = this.getPropertyValue("app.context.path")
				+ this.getPropertyValue("payments.acknowledge.txn.url");
		response = (AcknowledgeTransactionResponseType) this.doHttpPost(request, acknowledgeTxnURL,
				AcknowledgeTransactionResponseType.class, false);
		return response;
	}

	public void addSymmetricKeytoMetadata(BaseRequestType request, String symmetricKey) {
		if (null != request.getMetadata()) {
			CryptoMetadataType cryptoData = request.getMetadata().getCryptoMetadata();
			if (null == cryptoData) {
				cryptoData = new CryptoMetadataType();
				request.getMetadata().setCryptoMetadata(cryptoData);
			}

			KeyInfoType keyInfo = cryptoData.getKeyInfo();
			if (null == keyInfo) {
				keyInfo = new KeyInfoType();
				cryptoData.setKeyInfo(keyInfo);
			}

			keyInfo.setAsymmetricKeyAlgorithm(AlgorithmTypeEnum.RSA);
			keyInfo.setAsymmetricKeyAlias(this.getPropertyValue("pp.cert.alias"));
			keyInfo.setSymmetricKeyAlgorithm(AlgorithmTypeEnum.DES);
			keyInfo.setSymmetricKeyContent(symmetricKey);
			keyInfo.setSymmetricKeyTransformation(TransformationTypeEnum.DES_ECB_PKCS_5_PADDING);
			keyInfo.setAsymmetricKeyTransformation(TransformationTypeEnum.RSA_ECB_PKCS_1_PADDING);
		}
	}

	public GetCardBalanceResponseType balanceInquiry(GetCardBalanceRequestType request) throws Exception {
		GetCardBalanceResponseType response = null;
		String balanceInquiryURL = this.getPropertyValue("app.context.path")
				+ this.getPropertyValue("payments.balance.inquiry.url");
		response = (GetCardBalanceResponseType) this.doHttpPost(request, balanceInquiryURL,
				GetCardBalanceResponseType.class, false);
		return response;
	}

	public VoidPaymentResponseType cancelPayment(VoidPaymentRequestType request, boolean isRetry) throws Exception {
		VoidPaymentResponseType response = null;
		String cancelURL = this.getPropertyValue("app.context.path") + this.getPropertyValue("payments.cancel.url");
		response = (VoidPaymentResponseType) this.doHttpPost(request, cancelURL, VoidPaymentResponseType.class,
				isRetry);
		return response;
	}

	private <T> T doHttpPost(BaseRequestType request, String path, Class<T> responseType, boolean isRetry)
			throws Exception {
		String jsonPayload = this.generateJson(request);
		String signature = this.generateSignature(jsonPayload);
		String oAuthToken = this.getAuthzToken();
		HashMap headers = new HashMap();
		headers.put("X-Message-Signature", signature);
		headers.put("Client-Cert-Alias", this.getPropertyValue("client.cert.alias"));
		headers.put("Authorization", "BEARER " + oAuthToken);
		headers.put("Content-Type", "application/json");
		if (isRetry) {
			headers.put("retry", "true");
		}

		if (null != request.getMetadata() && null != request.getMetadata().getId()
				&& null != request.getMetadata().getId().getConversationDetails()) {
			headers.put("X-CorrelationId", request.getMetadata().getId().getConversationDetails().getCorrelationID());
			headers.put("X-Conversation-Id",
					request.getMetadata().getId().getConversationDetails().getConversationID());
		}

		String truststore = this.getPropertyValue("truststore.file");
		String trustPwd = this.getPropertyValue("truststore.password");
		String JsonResponse = HttpHelper.makePostCall(jsonPayload, path, headers, truststore, trustPwd);
		Object responseObject = staticMaper.readValue(JsonResponse, responseType);
		return responseObject;
	}

	public Map<String, String> encryptJavaObject(Object pojo, boolean useSymmetricKey, Key symmetricKey)
			throws Exception {
		String jsonMsg = null;
		if (pojo instanceof String) {
			jsonMsg = (String) pojo;
		} else {
			jsonMsg = this.generateJson(pojo);
		}

		String ppKeystore = this.getPropertyValue("pp.keystore.file");
		String pwd = this.getPropertyValue("pp.keystore.password");
		String encryptAlias = this.getPropertyValue("pp.keystore.alias");
		String keystoretype = this.getPropertyValue("pp.keystore.type");
		Map result = CryptoHelper.encryptMessage(useSymmetricKey, jsonMsg, symmetricKey, keystoretype, ppKeystore, pwd,
				encryptAlias);
		return result;
	}

	public EstablishSessionResponseType establishSession(EstablishSessionRequestType request) throws Exception {
		EstablishSessionResponseType response = null;
		String establishSessionURL = this.getPropertyValue("session.context.path")
				+ this.getPropertyValue("session.establish.url");
		response = (EstablishSessionResponseType) this.doHttpPost(request, establishSessionURL,
				EstablishSessionResponseType.class, false);
		return response;
	}

	public FinalizeSessionResponseType finalizeSession(FinalizeSessionRequestType request) throws Exception {
		String getSessionURL = this.getPropertyValue("session.context.path")
				+ this.getPropertyValue("session.finalize.url");
		FinalizeSessionResponseType response = (FinalizeSessionResponseType) this.doHttpPost(request, getSessionURL,
				FinalizeSessionResponseType.class, false);
		return response;
	}

	public FinalizeSessionResponseType finalizeSessionStatus(FinalizeSessionRequestType request) throws Exception {
		String finalizeURL = this.getPropertyValue("session.context.path")
				+ this.getPropertyValue("session.finalize.status.url");
		System.out.println("URL: " + finalizeURL);
		FinalizeSessionResponseType response = (FinalizeSessionResponseType) this.doHttpPost(request, finalizeURL,
				FinalizeSessionResponseType.class, false);
		return response;
	}

	private String generateJson(Object obj) throws JsonProcessingException {
		return staticMaper.writeValueAsString(obj);
	}

	private String generateSignature(String requestJson) throws Exception {
		return AppSdkSignatureHelper.createSignature(requestJson, this.appProperties);
	}

	public String getAuthzToken() throws Exception {
		if (this.isTokenExpired()) {
			this.authzResponse = AuthzSecurityClient.getAuthzToken(this.appProperties);
		}

		return this.authzResponse.getAccessToken();
	}

	public GetTransactionDetailsResponseType getBatchRecDetails(GetTransactionDetailsRequestType request)
			throws Exception {
		GetTransactionDetailsResponseType response = null;
		String getClientDtlsURL = this.getPropertyValue("app.context.path")
				+ this.getPropertyValue("adminui.get.batch.details.url");
		response = (GetTransactionDetailsResponseType) this.doHttpPost(request, getClientDtlsURL,
				GetTransactionDetailsResponseType.class, false);
		return response;
	}

	public GetTransactionSummaryResultType getBatchRecSummary(GetTransactionSummaryRequestType request)
			throws Exception {
		GetTransactionSummaryResultType response = null;
		String getClientDtlsURL = this.getPropertyValue("app.context.path")
				+ this.getPropertyValue("adminui.get.batch.records.url");
		response = (GetTransactionSummaryResultType) this.doHttpPost(request, getClientDtlsURL,
				GetTransactionSummaryResultType.class, false);
		return response;
	}

	public RefineSearchCriteriaResultType getClientDetails(RefineSearchCriteriaRequestType request) throws Exception {
		RefineSearchCriteriaResultType response = null;
		String getClientDtlsURL = this.getPropertyValue("app.context.path")
				+ this.getPropertyValue("adminui.get.client.details.url");
		response = (RefineSearchCriteriaResultType) this.doHttpPost(request, getClientDtlsURL,
				RefineSearchCriteriaResultType.class, false);
		return response;
	}

	private String getPropertyValue(String key) {
		return this.appProperties.getProperty(key, (String) null);
	}

	public GetSessionByTokenResponseType getSessionByToken(GetSessionByTokenRequestType request) throws Exception {
		String getSessionURL = this.getPropertyValue("session.context.path")
				+ this.getPropertyValue("session.retrieve.url");
		GetSessionByTokenResponseType response = (GetSessionByTokenResponseType) this.doHttpPost(request, getSessionURL,
				GetSessionByTokenResponseType.class, false);
		return response;
	}

	private boolean isTokenExpired() {
		if (null == this.authzResponse) {
			return true;
		} else {
			long tokenStartTime = this.authzResponse.getTokenStartTime();
			long tokenInvokeTime = (new Date()).getTime();
			long tokenExpiretime = this.authzResponse.getExpiresIn();
			long difference = tokenInvokeTime - tokenStartTime;
			return difference > tokenExpiretime;
		}
	}

	public PaymentResponseType makePaymentAuthorization(PaymentRequestType request, boolean isRetry) throws Exception {
		PaymentResponseType response = null;
		String authURL = this.getPropertyValue("app.context.path")
				+ this.getPropertyValue("payments.authorization.url");
		response = (PaymentResponseType) this.doHttpPost(request, authURL, PaymentResponseType.class, isRetry);
		return response;
	}

	public PerformFraudCheckResponseType performFraudCheck(PerformFraudCheckRequestType req, boolean isRetry)
			throws Exception {
		PerformFraudCheckResponseType resp = null;
		String fraudCheckURL = this.getPropertyValue("app.context.path")
				+ this.getPropertyValue("payments.fraudcheck.url");
		resp = (PerformFraudCheckResponseType) this.doHttpPost(req, fraudCheckURL, PerformFraudCheckResponseType.class,
				isRetry);
		return resp;
	}

	public RefundResponseType processRefund(RefundRequestType request, boolean isRetry) throws Exception {
		RefundResponseType response = null;
		String cancelURL = this.getPropertyValue("app.context.path") + this.getPropertyValue("payments.refund.url");
		response = (RefundResponseType) this.doHttpPost(request, cancelURL, RefundResponseType.class, isRetry);
		return response;
	}

	public ResolveCardIssuerResponseType resolveCardIssuer(ResolveCardIssuerRequestType request) throws Exception {
		ResolveCardIssuerResponseType response = null;
		String cancelURL = this.getPropertyValue("app.context.path")
				+ this.getPropertyValue("payments.card.resolve.url");
		response = (ResolveCardIssuerResponseType) this.doHttpPost(request, cancelURL,
				ResolveCardIssuerResponseType.class, false);
		return response;
	}

	public void setCustomProperties(Map<String, String> customProperties) {
		this.customProperties = customProperties;
		this.appProperties.putAll(this.customProperties);
	}

	static {
		staticMaper.findAndRegisterModules();
		staticMaper.setSerializationInclusion(Include.NON_NULL);
		staticMaper.disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);
		staticMaper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
		staticMaper.disable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
		staticMaper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
		staticMaper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		staticMaper.enable(new Feature[]{Feature.ESCAPE_NON_ASCII});
	}
}