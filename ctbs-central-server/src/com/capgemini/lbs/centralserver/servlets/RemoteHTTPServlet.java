/*
 * soagarwa
 */
package com.capgemini.lbs.centralserver.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.centralserver.messageproducer.QueueProducer;
import com.capgemini.lbs.centralserver.util.SpringContextLoaderForCentralServer;
import com.capgemini.lbs.framework.constants.ApplicationConstants;
import com.capgemini.lbs.framework.constants.SplitModelConstant;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.MessageDetail;
import com.capgemini.lbs.framework.exception.MessageType;
import com.capgemini.lbs.framework.exception.MessageWrapper;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class RemoteHTTPServlet.
 */
@SuppressWarnings({ "static-access", "serial" })
public class RemoteHTTPServlet extends HttpServlet {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(RemoteHTTPServlet.class);

	/** The serializer. */
	private JSONSerializer serializer;

	
	/**
	 * Inits the.
	 *
	 * @param req the req
	 * @param res the res
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void init(ServletRequest req, ServletResponse res)
			throws ServletException, java.io.IOException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	/**
	 * Do post.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.debug(
				"Entered into do post method of RemoteHTTPServlet"
				);
		// Load servlet context object
		// wac =

		try {
			// Get Json object from request
			String jason = request.getParameter(SplitModelConstant.JSON);
			// Get a JsonSerializer Object
			serializer = CGJasonConverter.getJsonObject();
			if (jason != null) {
				// type casting the Json object
				JSON userObject = serializer.toJSON(jason);
				// Create Java Object
				Object object = (Object) serializer.toJava(userObject);
				// Crate into Json Object to iterate the Json List
				JSONObject json = JSONObject.fromObject(object);
				// Get Object type from Json list and type cast the list of
				// TO object in the respective object type through reflection
				String objectType = (String) json
				.get(SplitModelConstant.OBJECT_TYPE);
				String jsonobjectType = null;
				if (json.get(SplitModelConstant.JSON_OBJECT_TYPE) != JSONNull
						.getInstance()) {
					jsonobjectType = (String) json
					.get(SplitModelConstant.JSON_OBJECT_TYPE);
				}
				Map<String, Class<?>> map = new HashMap<String, Class<?>>();
				Map<String, Class<?>> objectHirearchyMap = new HashMap<String, Class<?>>();
				try {
					if(!StringUtil.isEmpty(jsonobjectType)){
						objectHirearchyMap.put(SplitModelConstant.JSON_CHILD_OBJECT,
								Class.forName(jsonobjectType));
					}
					objectHirearchyMap.put(SplitModelConstant.BASE_LIST,
							Class.forName(objectType));

					map.putAll(objectHirearchyMap);
					// map.put(SplitModelConstant.BASE_LIST,
				} catch (ClassNotFoundException e) {
					logger.error("RemoteHTTPServlet::doPost::ClassNotFoundException occured:"
							+e.getMessage());
				}
				// Type Cast the Json Object in CGBaseTO object
				CGBaseTO userTO = (CGBaseTO) json.toBean(json, CGBaseTO.class, map);

				// Forwarding the request by checking the request type and getting
				// the
				// TO object for sending the response back to the branch Office
				CGBaseTO bookingDetailsTO = (CGBaseTO) checkrequestType(userTO);
				/*--------Sending response back to the Branch office ------------*/
				// Again typecasting the java object to the json object
				JSON responseObject = serializer.toJSON(bookingDetailsTO);
				/*
				 * Convert the Json object into java object to convert it into
				 * JSONObject to iterate the json list and to remove the stacktrace
				 * object of exception class from it
				 */
				
				Object jsonObject = (Object) serializer.toJava(responseObject);
				JSONObject jasonObject = JSONObject.fromObject(jsonObject);
				if (jasonObject.get(SplitModelConstant.BUSINESS_EXCEPTION) != JSONNull.getInstance()) {
					logger.debug("RemoteHTTPServlet::doPost::no exception in response: " + jasonObject);
					JSONObject json1 = (JSONObject) jasonObject
					.get(SplitModelConstant.BUSINESS_EXCEPTION);
					jasonObject = (JSONObject) jasonObject
					.discard(SplitModelConstant.BUSINESS_EXCEPTION);
					json1.discard(SplitModelConstant.STACK_TRACE);
					json1.discard(SplitModelConstant.CAUSE);
					jasonObject = jasonObject.element(
							SplitModelConstant.BUSINESS_EXCEPTION, json1);
				} else {
					logger.debug("RemoteHTTPServlet::doPost::business exception in response: " + jasonObject);
				}
				// adding the Json object into response
				response.addHeader(SplitModelConstant.RESPONSE_OBJECT,
						jasonObject.toString());

			}
		} catch (Exception e) {
			logger.error("RemoteHTTPServlet::doPost::Exception occured:"
					+e.getMessage());
		}
	}

	/**
	 * Checkrequest type.
	 * 
	 * @param baseTO
	 *            the base to
	 * @return the CG base to
	 */
	private CGBaseTO checkrequestType(CGBaseTO baseTO) {

		String requestType = baseTO.getRequestType();
		logger.debug("entered into checkrequestType ");
		if (StringUtil.equals(requestType,
				ApplicationConstants.VALIDATION_REQUEST)) {
			try {
				
				baseTO = createvalidationRequest(baseTO);
				
			} catch (Exception e) {
				/* if exception comes bind it in in TO and return the TO */
				baseTO = bindExceptionInTO(baseTO, e.getMessage());
				logger.error("RemoteHTTPServlet::checkrequestType::Exception occured:"
						+e.getMessage());
				return baseTO;
			}
			// call if request type is Search
		}else {
			/* Call the JBOSS MQ */
			try {
				logger.debug(" calling queue producer ....");
				QueueProducer.sendMessage(baseTO);
			} catch (Exception e) {
				baseTO = bindExceptionInTO(baseTO,
						SplitModelConstant.ERROR_MESSAGE_FOR_WRITING_IN_MQ);
				logger.error("RemoteHTTPServlet::checkrequestType::Exception occured:"
						+e.getMessage());
			}

		}
		return baseTO;
	}

	/**
	 * Bind exception in to.
	 * 
	 * @param baseTO
	 *            the base to
	 * @param message
	 *            the message
	 * @return the CG base to
	 */
	private CGBaseTO bindExceptionInTO(CGBaseTO baseTO, String message) {
		/* To be removed from here and moved to business Class */
		MessageWrapper messageWrapper = new MessageWrapper();
		MessageDetail msgDetail = new MessageDetail();
		msgDetail.setMessageKey(message);
		msgDetail.setMessageType(MessageType.Error);
		messageWrapper.addMessageDetail(msgDetail);
		// bind the exception in bussiness exception class
		CGBusinessException CGBusinessException = new CGBusinessException(
				messageWrapper);
		baseTO.setBusinessException(CGBusinessException);
		return baseTO;
	}

	/**
	 * Createvalidation request.
	 * 
	 * @param baseTO
	 *            the base to
	 * @return the CG base to
	 * @throws CGBusinessException
	 *             the CG business exception
	 * @throws SecurityException
	 *             the security exception
	 * @throws NoSuchMethodException
	 *             the no such method exception
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 */

	private CGBaseTO createvalidationRequest(CGBaseTO baseTO)
			throws CGBusinessException, SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		/* Reflection code to call the particular method */
		Method method = null;
		if (!StringUtil.isEmpty(baseTO.getBeanId())) {
			Object genericObject;
			genericObject = SpringContextLoaderForCentralServer.getInstance()
					.getSpringContext().getBean(baseTO.getBeanId());
			method = genericObject.getClass().getMethod(baseTO.getMethodName(),
					baseTO.getClass());
			baseTO = (CGBaseTO) method.invoke(genericObject, baseTO);

		}
		return baseTO;
	}
}
