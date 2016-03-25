/*
 * @author mohammes
 */
package com.capgemini.lbs.framework.webaction;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.config.MessageResourcesConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.ModuleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.exception.MessageDetail;
import com.capgemini.lbs.framework.exception.MessageWrapper;
import com.capgemini.lbs.framework.exception.ParamInfo;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;

/**
 * The Class CGDispatchAction.
 */
public abstract class CGBaseAction extends DispatchAction {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(CGBaseAction.class);
	/** The spring application context. */
	public WebApplicationContext springApplicationContext = null;
	
	/*** for Globalization***/
	public static Map<String, String> configurableParams = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.Action#setServlet(org.apache.struts.action.
	 * ActionServlet)
	 */
	public void setServlet(ActionServlet actionServlet) {
		super.setServlet(actionServlet);
		if (actionServlet != null) {
			ServletContext servletContext = actionServlet.getServletContext();
			springApplicationContext = WebApplicationContextUtils
					.getRequiredWebApplicationContext(servletContext);

		}
	}

	/**
	 * Gets the spring application context.
	 * 
	 * @return the spring application context
	 */
	public WebApplicationContext getSpringApplicationContext() {
		return springApplicationContext;
	}

	/**
	 * Sets the spring application context.
	 * 
	 * @param springWebApplicationContext
	 *            the new spring application context
	 */
	public void setSpringApplicationContext(
			WebApplicationContext springWebApplicationContext) {
		this.springApplicationContext = springWebApplicationContext;
	}

	// Added by Anwar
	public Object getBean(String beanName) {
		if (beanName == null) {
			return null;
		} else if (springApplicationContext != null) {
			return springApplicationContext.getBean(beanName);
		} else {
			return null;
		}
	}

	/**
	 * @param request
	 */
	protected void saveActionMessage(HttpServletRequest request) {
		ActionMessages actionMessages = (ActionMessages) request
				.getAttribute(FrameworkConstants.ERROR_MESSAGE);
		ActionMessages actionWarnings = (ActionMessages) request
				.getAttribute(FrameworkConstants.WARNING_MESSAGE);
		ActionMessages actionInfo = (ActionMessages) request
				.getAttribute(FrameworkConstants.INFO_MESSAGE);

		saveMessages(request, actionMessages);
		saveMessages(request, actionWarnings);
		saveMessages(request, actionInfo);
	}

	// Added by Anwar
	/**
	 * @param code
	 * @return
	 */
	public ActionMessages setActionMessage(String code) {
		ActionMessage actionMessage = new ActionMessage(code);
		ActionMessages actionMessages = getActionMessages(actionMessage);
		return actionMessages;
	}

	/**
	 * @param code
	 * @param value0
	 * @return
	 */
	public ActionMessages setActionMessage(String code, Object value0) {
		ActionMessage actionMessage = value0 != null ? new ActionMessage(code,
				value0) : new ActionMessage(code);
		ActionMessages actionMessages = getActionMessages(actionMessage);
		return actionMessages;
	}

	/**
	 * @param code
	 * @param values
	 * @return
	 */
	public ActionMessages setActionMessage(String code, Object[] values) {
		ActionMessage actionMessage = new ActionMessage(code, values);
		ActionMessages actionMessages = getActionMessages(actionMessage);
		return actionMessages;
	}

	/**
	 * @param actionMessage
	 * @return
	 */
	private ActionMessages getActionMessages(ActionMessage actionMessage) {
		ActionMessages actionMessages = new ActionMessages();
		actionMessages.add(ActionMessages.GLOBAL_MESSAGE, actionMessage);
		return actionMessages;
	}

	/**
	 * Purpose : extract message from CGBusinessException ie when service layer
	 * throws CGBusiness Exception then Exception will be caught in the action
	 * class And Extracts the message and propagate to UI
	 * 
	 */
	public void getBusinessError(final HttpServletRequest request,
			CGBusinessException e) {
		MessageWrapper messageWrapper = e.getBusinessMessage();
		ActionMessage actionMessage = null;
		if (messageWrapper != null) {
			List<MessageDetail> msgdetails = messageWrapper.getMessageList();
			if (!StringUtil.isEmptyList(msgdetails)) {
				MessageDetail messageDetail = msgdetails.get(0);
				String mkey = messageDetail.getMessageKey();
				
				if (!StringUtil.isEmptyList(messageDetail.getParams())) {
					Object params[] = new Object[messageDetail.getParams()
							.size()];
					int i = 0;
					for (ParamInfo parmName : messageDetail.getParams()) {
						params[i] = parmName.getValue();
						++i;
					}
					actionMessage = new ActionMessage(mkey, params);
				} else {
					actionMessage = new ActionMessage(mkey);
				}
				setActionMessage(request, actionMessage);

			}
		}else{
			String errorCode=e.getMessage();
			if(!StringUtil.isStringEmpty(errorCode)){
				actionMessage = new ActionMessage(errorCode);
				setActionMessage(request, actionMessage);
			}
			
		}

	}

	private void setActionMessage(final HttpServletRequest request,
			ActionMessage actionMessage) {
		ActionMessages actionMessages = new ActionMessages();
		actionMessages
				.add(ActionMessages.GLOBAL_MESSAGE, actionMessage);
		request.setAttribute(CommonConstants.ERROR_MESSAGE,
				actionMessages);
	}
	/**
	 * getSystemException :: for Form Submission
	 * @param request
	 * @param exception
	 */
	public void getSystemException(final HttpServletRequest request,
			CGSystemException exception) {
		String	mkey =ExceptionUtil.getExceptionDetails(exception);
		if(!StringUtil.isStringEmpty(mkey)){
			setActionMessage(request, new ActionMessage(mkey));
		}

	}
	
	/**
	 * Gets the generic exception.
	 *
	 * @param request the request
	 * @param exception the exception
	 * @return the generic exception
	 */
	public void getGenericException(final HttpServletRequest request,
			Exception exception) {
		String	mkey =ExceptionUtil.getGenericException(exception);
		if(!StringUtil.isStringEmpty(mkey)){
			setActionMessage(request, new ActionMessage(mkey));
		}

	}
	/**
	 * getSystemException ::  for Ajax Submission
	 * @param request
	 * @param exception
	 */
	public String getSystemExceptionMessage(final HttpServletRequest request,
			CGSystemException exception) {
		String message=null;
		String	mkey =ExceptionUtil.getExceptionDetails(exception);
		if(!StringUtil.isStringEmpty(mkey)){
			message= getMessageFromBundle(request, mkey, null,FrameworkConstants.FRAMWEORK_MESSAGE_BUNDLE_KEY);
		}
		return message;
	}
	
	/**
	 * Gets the generic exception message.
	 *
	 * @param request the request
	 * @param exception the exception
	 * @return the generic exception message
	 */
	public String getGenericExceptionMessage(final HttpServletRequest request,
			Exception exception) {
		String message=null;
		String	mkey =ExceptionUtil.getGenericException(exception);
		if(!StringUtil.isStringEmpty(mkey)){
			message= getMessageFromBundle(request, mkey, null,FrameworkConstants.FRAMWEORK_MESSAGE_BUNDLE_KEY);
		}
		return message;
	}

	/**
	 * Gets the business error from wrapper. Get's the messages from errorBundle
	 * 
	 * @param request
	 *            the request
	 * @param e
	 *            the e
	 * @return the business error from wrapper
	 */
	public String getBusinessErrorFromWrapper(final HttpServletRequest request,
			CGBusinessException e) {
		final MessageWrapper messageWrapper = e.getBusinessMessage();
		final String messageBundleName=e.getBundleName();
		String msg = null;
		Object params[] = null;
		if (messageWrapper != null) {
			List<MessageDetail> msgdetails = messageWrapper.getMessageList();
			if (!StringUtil.isEmptyList(msgdetails)) {
				MessageDetail messageDetail = msgdetails.get(0);
				String mkey = messageDetail.getMessageKey();
				if (!StringUtil.isEmptyList(messageDetail.getParams())) {
					params = new Object[messageDetail.getParams().size()];
					int i = 0;
					for (ParamInfo parmName : messageDetail.getParams()) {
						params[i] = parmName.getValue();
						++i;
					}

				}
				if(!StringUtil.isStringEmpty(messageBundleName)){
					msg = getMessageFromBundle(request, mkey, params,messageBundleName);
				}else{
					msg = getMessageFromErrorBundle(request, mkey, params);
				}
				
			}
		}else{
			msg = getMessageFromErrorBundle(request, e.getMessage(), null);
		}
		return msg;

	}

	/**
	 * @param request
	 * @param actionMessage
	 */

	@SuppressWarnings("unused")
	public void prepareActionMessage(HttpServletRequest request,
			ActionMessage actionMessage) {
		if (actionMessage != null) {
			ActionMessages actionMessages = new ActionMessages();
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, actionMessage);
			request.setAttribute(CommonConstants.INFO_MESSAGE, actionMessages);
		}
	}
	public void prepareActionMessage(HttpServletRequest request,
			String messageKey) {
		if (!StringUtil.isStringEmpty(messageKey)) {
			ActionMessages actionMessages = new ActionMessages();
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(messageKey));
			request.setAttribute(CommonConstants.INFO_MESSAGE, actionMessages);
		}
	}

/**	
 * getMessageFromErrorBundle :: public method which will call
 * @param request
 * @param mkey
 * @param params
 * @return
 */
	@SuppressWarnings("unused")
	public String getMessageFromErrorBundle(HttpServletRequest request,
			String mkey, Object params[]) {
		String msg=null;
		LOGGER.trace("CGBaseAction:: prepareCommonException:: getMessageFromErrorBundle ::mKey:["+mkey+"] & Params ["+params+"]");
		msg = getMessageFromResourceBundles(request, mkey, params);
		return msg;
	}

	/**
	 * Looks for ErrorKey IN all ResourceBundles which are loaded in Struts config
	 * @param request
	 * @param mkey
	 * @param params
	 * @param msg
	 * @return
	 */
	private String getMessageFromResourceBundles(HttpServletRequest request,
			String mkey, Object[] params) {
		 String msg=null;
		MessageResourcesConfig[] configs = getModuleConfigResources(request);
		for(MessageResourcesConfig config:configs){
			String bundleKey =config.getKey();
			LOGGER.trace("CGBaseAction:: prepareCommonException:: getMessageFromErrorBundle ::getMessageFromResource :: looking for mKey:["+mkey+"] IN resource Key ["+bundleKey+"]");
			msg = getMessageFromBundle(request, mkey, params,bundleKey);
			if(!StringUtil.isStringEmpty(msg)){
				LOGGER.trace("CGBaseAction:: prepareCommonException:: getMessageFromErrorBundle ::getMessageFromResource :: Key:["+mkey+"] Exists resource Key ["+bundleKey+"]");
				break;
			}
		}
		return msg;
	}

	/**
	 * @param request
	 * @param mkey
	 * @param params
	 * @param bundleKey
	 * @return
	 */
	public String getMessageFromBundle(HttpServletRequest request,
			String mkey, Object[] params, String bundleKey) {
		String msg=null;
		MessageResources bundleName = getResources(request, bundleKey);
		if(!StringUtil.isNull(bundleName)){
			if (!StringUtil.isEmpty(params) ) {
				msg = bundleName.getMessage(mkey, params);
			} else {
				msg = bundleName.getMessage(mkey);
			}
		}
		return msg;
	}

	/**
	 * @param request
	 * @return
	 */
	private MessageResourcesConfig[] getModuleConfigResources(
			HttpServletRequest request) {
		ModuleConfig moduleConfig = getModuleConfig(request);
		MessageResourcesConfig[]configs=moduleConfig.findMessageResourcesConfigs();
		return configs;
	}

	/**
	 * @param request
	 * @return
	 */
	private ModuleConfig getModuleConfig(HttpServletRequest request) {
		ServletContext context = getServlet().getServletContext();
		ModuleConfig moduleConfig =
	            ModuleUtils.getInstance().getModuleConfig(request, context);
		return moduleConfig;
	}

	public MessageResources getResourceBundle(HttpServletRequest request) {
		MessageResources errorMessages = getResources(request, "errorBundle");
		return errorMessages;
	}
	
	/**
	 * prepareCommonException:: it gives Json String for Common Exception
	 * @param jsonResult
	 * @return
	 */
	public String prepareCommonException(String flag,String message) {
		String jsonResult=null;
		try{
			JSONObject detailObj = new JSONObject(); 
			detailObj.put(flag, message);
			jsonResult  = detailObj.toString();
		}catch (Exception jsonExcep) {
			LOGGER.error("CGBaseAction:: prepareCommonException::  ::Exception", jsonExcep);
		}
		return jsonResult;
	}
	
	/**
	 * prepareActionMessage
	 * @param request
	 * @param messageKey
	 * @param params
	 */
	public void prepareActionMessage(HttpServletRequest request,String messageKey,Object params[]) {
		if (!StringUtil.isStringEmpty(messageKey)){
			if(!StringUtil.isEmpty(params)){
				prepareActionMessage(request, new ActionMessage(messageKey,params));
			}else{
				prepareActionMessage(request, messageKey);
			}
		}
	}

	/**
	 * To compose report url from configurable param.
	 * 
	 * @param configParams
	 * @return reportUrl
	 */
	public String composeReportUrl(Map<String, String> configParams) {
		StringBuffer reportUrl = new StringBuffer();
		if (configParams != null && !configParams.isEmpty()) {
			reportUrl
					.append(FrameworkConstants.HTTP)
					.append(configParams
							.get(FrameworkConstants.UDAAN_REPORT_IP_ADDRESS))
					.append(CommonConstants.CHARACTER_COLON)
					.append(configParams
							.get(FrameworkConstants.UDAAN_REPORT_PORT))
					.append(CommonConstants.SLASH_CONST)
					.append(FrameworkConstants.APP_NAME_UDAAN_REPORT)
					.append(CommonConstants.SLASH_CONST);
		}
		return reportUrl.toString();
	}
	/**
	 * @param deliveryType
	 * @param dlvType
	 */
	public JSONArray prepareJsonArray(Map<String, String> deliveryType) {
		JSONArray dlvType= new JSONArray();
		for(Map.Entry mapEntry:deliveryType.entrySet()){
			JSONObject detailObj = new JSONObject();
			detailObj.put("KEY", mapEntry.getKey());
			detailObj.put("VALUE", mapEntry.getValue());
			dlvType.add(detailObj);
		}
		return dlvType;
	}
	public JSONArray prepareJsonArrayForMaster(Map<Integer, String> deliveryType) {
		JSONArray dlvType= new JSONArray();
		for(Map.Entry mapEntry:deliveryType.entrySet()){
			JSONObject detailObj = new JSONObject();
			detailObj.put("KEY", mapEntry.getKey()+"");
			detailObj.put("VALUE", mapEntry.getValue());
			dlvType.add(detailObj);
		}
		return dlvType;
	}

	/**
	 * To prepare string like adding blank space, set length, trim extra long
	 * char etc.
	 * 
	 * @param str
	 * @param blockLength
	 * @return prepared string
	 */
	public String prepareString(String str, int blockLength) {
		LOGGER.debug("CGBaseAction :: prepareString() :: START");
		StringBuffer sb = null;
		if (str.length() < blockLength) {
			sb = new StringBuffer(str);
			for (int i = str.length(); i < blockLength; i++) {
				sb.append(" ");
			}
		} else {
			sb = new StringBuffer(str.subSequence(0, blockLength));
		}
		LOGGER.debug("CGBaseAction :: prepareString() :: START");
		return sb.toString();
	}

	/**
	 * To prepare and get print template relative path
	 * 
	 * @param request
	 * @param tempateName
	 * @return inputTemplatePath
	 */
	public String getPrintTemplatePath(HttpServletRequest request,
			String tempateName) {
		LOGGER.debug("CGBaseAction :: getPrintTemplatePath() :: START");
		String fs = File.separator;
		String inputTemplatePath = request.getRealPath("") + fs + "WEB-INF"
				+ fs + "classes" + fs + "print" + fs + "template" + fs
				+ tempateName;
		LOGGER.debug("CGBaseAction :: getPrintTemplatePath() :: END");
		return inputTemplatePath;
	}
	
}
