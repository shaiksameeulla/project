package com.capgemini.lbs.centralserver.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.capgemini.lbs.framework.constants.ApplicationConstants;
import com.capgemini.lbs.framework.constants.BusinessConstants;
import com.capgemini.lbs.framework.constants.SplitModelConstant;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.to.manifestextractor.DataExtractorTO;

// TODO: Auto-generated Javadoc
/**
 * Servlet implementation class CentralDataExtractorServlet.
 */
public class CentralDataExtractorServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
	.getLogger(CentralDataExtractorServlet.class);
	
	/** The serializer. */
	private JSONSerializer serializer;
	
	/** The spring application context. */
	private WebApplicationContext springApplicationContext = null;
	
	/**
	 * Inits the.
	 *
	 * @throws ServletException the servlet exception
	 */
	public void init() throws ServletException {
		super.init();
		springApplicationContext = WebApplicationContextUtils
		.getRequiredWebApplicationContext(getServletContext());
	}
	
	
	/**
	 * Do get.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
		// TODO Auto-generated method stu
	
		/**
		 * Do post.
		 *
		 * @param request the request
		 * @param response the response
		 * @throws ServletException the servlet exception
		 * @throws IOException Signals that an I/O exception has occurred.
		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
		 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String jason = request.getParameter(SplitModelConstant.JSON);
		Boolean isValidRequest = Boolean.TRUE;
		CGBaseTO userTO=null;
		CGBaseTO branchExtractionTo=null;
		// Get a JsonSerializer Object
		logger.debug(
				"inside CentralDataExtractorServlet (doPost) "
				);
		logger.debug(" CentralDataExtractorServlet :doPost --> request params jason : "+jason
				);
		serializer = CGJasonConverter.getJsonObject();
		if (jason != null) {
			// type casting the Json object
			JSON userObject = serializer.toJSON(jason);
			logger.debug(" CentralDataExtractorServlet :doPost --> userObject : "+userObject
			);
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
				logger.error("CentralDataExtractorServlet::doPost::Exception occured:"
						+e.getMessage());
			}
			// Type Cast the Json Object in CGBaseTO object
			try {
				userTO = (CGBaseTO) json.toBean(json, CGBaseTO.class, map);
			} catch (Exception e) {
				logger.error("CentralDataExtractorServlet::doPost::Exception occured:"
						+e.getMessage());
				isValidRequest = Boolean.FALSE;
			}

			try {
				// Forwarding the request by checking the request type and getting
				// the
				// TO object for sending the response back to the branch Office
				logger.debug(
				"Forwarding the request by checking the request type and getting the TO object for sending the response back to the branch Office");
				response.setContentType("application/octet-stream");
				String disHeader = "Attachment; Filename=\"filename\"";
				response.setHeader("Content-Disposition", disHeader);
				
				ServletOutputStream out = response.getOutputStream();
				
				if(isValidRequest){
				branchExtractionTo = (CGBaseTO) checkrequestType(userTO);
				
				DataExtractorTO dataExtractorTO = (DataExtractorTO)branchExtractionTo;
				if(dataExtractorTO.getListOfIds()!= null && !dataExtractorTO.getListOfIds().isEmpty()){
					response.setHeader(BusinessConstants.DATA_EXTCTR_ID_STR,dataExtractorTO.getDataExtctrIdStr());
					response.addHeader(BusinessConstants.DATA_EXTCTR_ID_STR_ARR,dataExtractorTO.getListOfIds().toString());
					}
				out.write(dataExtractorTO.getFileData());
				
				
				logger.debug(
						"exiting from servlet and sending the response as output stream  (In binary format): "+(dataExtractorTO.getFileData()!= null ?dataExtractorTO.getFileData().length :0) );
				}
				
				//Please dont delete the following commented code : The following code will help to identify whether the transit status changed to to read or not(if it return true : success)
				
				

				
				out.flush();
				out.close();
			} catch (Exception e) {
				logger.error("CentralDataExtractorServlet::doPost::Exception occured:"
						+e.getMessage());
				e.printStackTrace();
			}
			

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
		logger.debug(" CentralDataExtractorServlet :doPost --> inside checkrequestType method: "+baseTO
		);
		String requestType = baseTO.getRequestType();
		if (StringUtil.equals(requestType,
				ApplicationConstants.VALIDATION_REQUEST)) {
			try {
				baseTO = createvalidationRequest(baseTO);
			} catch (Exception e) {
				logger.error("CentralDataExtractorServlet::checkrequestType::Exception occured:"
						+e.getMessage());
				return baseTO;
			}
			// call if request type is Search
		} 

		return baseTO;
	}
	
	/**
	 * Createvalidation request.
	 *
	 * @param baseTO the base to
	 * @return the cG base to
	 * @throws CGBusinessException the cG business exception
	 * @throws SecurityException the security exception
	 * @throws NoSuchMethodException the no such method exception
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws InvocationTargetException the invocation target exception
	 */
	private CGBaseTO createvalidationRequest(CGBaseTO baseTO)
			throws CGBusinessException, SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		/* Reflection code to call the particular method */
		logger.debug(" CentralDataExtractorServlet :doPost -->  checkrequestType ---> createvalidationRequest method: ");
		Method method = null;
		if (!StringUtil.isEmpty(baseTO.getBeanId())) {
			Object genericObject;
			try {
				logger.debug(" CentralDataExtractorServlet :doPost -->  checkrequestType ---> createvalidationRequest method:--getting Bean ID ");
				genericObject = springApplicationContext
						.getBean(baseTO.getBeanId());

				method = genericObject.getClass().getMethod(
						baseTO.getMethodName(), baseTO.getClass());
				logger.debug(" CentralDataExtractorServlet :doPost -->  checkrequestType ---> createvalidationRequest method:--invoking service ");
				synchronized (this) {
					baseTO = (CGBaseTO) method.invoke(genericObject, baseTO);
				}
				
				
				logger.debug(" CentralDataExtractorServlet :doPost -->  checkrequestType ---> createvalidationRequest method:--got response baseTO "+baseTO);
			} catch (Exception e) {
				logger.error("CentralDataExtractorServlet::createvalidationRequest::Exception occured:"
						+e.getMessage());
			}
		}
		return baseTO;
	}

}
