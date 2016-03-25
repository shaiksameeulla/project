package com.capgemini.lbs.framework.sms;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.SmsConstants;
import com.capgemini.lbs.framework.dao.sms.SmsSenderDAO;
import com.capgemini.lbs.framework.domain.SmsDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SmsSenderTO;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class SmsSenderServiceImpl.
 * 
 * @author narmdr
 */
public class SmsSenderServiceImpl implements SmsSenderService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SmsSenderServiceImpl.class);

	/** The configurable params. */
	private static Map<String, String> configurableParams;
	
	/** The sms sender dao. */
	private SmsSenderDAO smsSenderDAO;
//	static {
//		 loadConfigParams();
//	}

	/**
 * Sets the sms sender dao.
 *
 * @param smsSenderDAO the smsSenderDAO to set
 */
	

	/**
	 * Load config params.
	 *
	 * @throws CGSystemException the cG system exception
	 */
	private void loadConfigParams() throws CGSystemException {
		// TODO Auto-generated method stub
		if (configurableParams != null) {
			return;
		}
		List<String> paramNames = new ArrayList<>();
		paramNames.add(SmsConstants.SMS_HOST);
		paramNames.add(SmsConstants.SMS_SENDER_ID);
		paramNames.add(SmsConstants.SMS_USER_ID);
		paramNames.add(SmsConstants.SMS_PASSWORD);
		paramNames.add(SmsConstants.SMS_CHARSET);

		List<?> configParams = smsSenderDAO
				.getConfigurableParamMapByNames(paramNames);
		configurableParams = prepareMapFromConfigParams(configParams);
	}

	public SmsSenderDAO getSmsSenderDAO() {
		return smsSenderDAO;
	}

	public void setSmsSenderDAO(SmsSenderDAO smsSenderDAO) {
		this.smsSenderDAO = smsSenderDAO;
	}

	/**
	 * Prepare map from config params.
	 *
	 * @param configParams the config params
	 * @return the map
	 */
	private static Map<String, String> prepareMapFromConfigParams(
			List<?> configParams) {
		Map<String, String> configMap = null;
		if (!StringUtil.isEmptyList(configParams)) {
			configMap = new HashMap<String, String>(configParams.size());
			for (Object configP : configParams) {
				Map map = (Map) configP;
				configMap.put((String) map.get(SmsConstants.PARAM_NAME),
						(String) map.get(SmsConstants.PARAM_VALUE));
			}
		}
		return configMap;
	}

	/**
	 * Send sms.
	 *
	 * @param smsSenderTO the sms sender to
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public void sendSms(SmsSenderTO smsSenderTO) throws CGSystemException,
			CGBusinessException {
		LOGGER.trace("SmsSenderServiceImpl::sendSms::START------------>:::::::");
		String isSmsSent = CommonConstants.NO;
		try {
			//smsSenderTO.setIsSmsSent(CommonConstants.NO);
			if (configurableParams == null) {
				loadConfigParams();
				if (configurableParams == null) {
					throw new CGBusinessException(
							"Unable to load config params.");
				}
			}
			String queryString = "senderID=%s&" + "msisdn=%s&" + "msg=%s&"
					+ "userid=%s&" + "pwd=%s";

			String query = String.format(queryString, URLEncoder.encode(
					configurableParams.get(SmsConstants.SMS_SENDER_ID),
					configurableParams.get(SmsConstants.SMS_CHARSET)),
					URLEncoder.encode(smsSenderTO.getContactNumber(),
							configurableParams.get(SmsConstants.SMS_CHARSET)),
					URLEncoder.encode(smsSenderTO.getMessage(),
							configurableParams.get(SmsConstants.SMS_CHARSET)),
					URLEncoder.encode(
							configurableParams.get(SmsConstants.SMS_USER_ID),
							configurableParams.get(SmsConstants.SMS_CHARSET)),
					URLEncoder.encode(
							configurableParams.get(SmsConstants.SMS_PASSWORD),
							configurableParams.get(SmsConstants.SMS_CHARSET)));

			HttpURLConnection urlCon = (HttpURLConnection) new URL(
					configurableParams.get(SmsConstants.SMS_HOST))
					.openConnection();
			urlCon.setDoOutput(true);
			urlCon.setRequestMethod("POST");
			urlCon.setRequestProperty("Accept-Charset",
					configurableParams.get(SmsConstants.SMS_CHARSET));
			urlCon.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset="
							+ configurableParams.get(SmsConstants.SMS_CHARSET));

			DataOutputStream wr = new DataOutputStream(urlCon.getOutputStream());
			wr.writeBytes(query);
			wr.flush();
			wr.close();

			int status = urlCon.getResponseCode();

			if (status == HttpStatus.SC_OK) {
				isSmsSent = CommonConstants.YES;
			}/*
			 * else { isSmsSent = CommonConstants.NO; //throw new
			 * CGBusinessException(SmsConstants.ERROR_IN_SENDING_SMS); }
			 */
			smsSenderTO.setStatusCode(Integer.toString(status));
			smsSenderTO.setIsSmsSent(isSmsSent);

		} catch (IOException e) {
			LOGGER.error(
					"Exception happened in sendSms of SmsSenderServiceImpl...",
					e);
			throw new CGBusinessException(e);
		}
		LOGGER.trace("SmsSenderServiceImpl::sendSms::END------------>:::::::");
	}

	/* (non-Javadoc)
	 * @see com.capgemini.lbs.framework.sms.SmsSenderService#prepareAndSendSms(com.capgemini.lbs.framework.to.SmsSenderTO)
	 */
	@Override
	public void prepareAndSendSms(SmsSenderTO smsSenderTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("SmsSenderServiceImpl::prepareAndSendSms::START------------>:::::::");
		try {
			if(smsSenderTO.isImmediateSend()){
				sendSms(smsSenderTO);				
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured while sending SMS::SmsSenderServiceImpl::prepareAndSendSms() :: ",
					e);
		}
		SmsDO smsDO = new SmsDO();
		CGObjectConverter.createDomainFromTo(smsSenderTO, smsDO);
		smsDO.setCreatedDate(DateUtil.getCurrentDate());
		smsDO.setUpdatedDate(DateUtil.getCurrentDate());
		setDefaultValueToSmsDO(smsDO);
		smsSenderDAO.saveSmsDetails(smsDO);
		LOGGER.trace("SmsSenderServiceImpl::prepareAndSendSms::END------------>:::::::");
	}

	/**
	 * Sets the default value to sms do.
	 *
	 * @param smsDO the new default value to sms do
	 */
	private void setDefaultValueToSmsDO(SmsDO smsDO) {
		if(StringUtils.isBlank(smsDO.getStatusCode())){
			smsDO.setStatusCode("000");//SMS not sent code
		}
		if(StringUtils.isBlank(smsDO.getIsSmsSent())){
			smsDO.setIsSmsSent(CommonConstants.NO);//SMS not sent code
		}
	}

}
