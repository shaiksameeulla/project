package com.cg.lbs.bcun.utility;


import java.io.IOException;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.SplitModelConstant;
import com.capgemini.lbs.framework.to.CGBaseTO;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.SplitModelUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.to.OutboundBranchDataTO;



/**
 * @author mohammal
 * Feb 12, 2013
 * 
 */
public class UserHttpClientUtil {
	
	/** The http client. */
	private HttpClient httpClient;

	/** The serializer. */
	public JSONSerializer serializer;
	// private CreateHttpClient userClient;
	/** The create logged in request. */
	private CreateHttpLoggingInRequest createLoggedInRequest;
	Logger logger = LoggerFactory.getLogger(UserHttpClientUtil.class);
	/**
	 * Parses the client request.
	 * 
	 * @param userTO
	 *            the user to
	 * @return the byte array of the file sent from server
	 * @throws HttpException
	 *             the http exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public CGBaseTO parseClientRequest(CGBaseTO baseTo)
			throws HttpException, IOException, ClassNotFoundException {

		OutboundBranchDataTO branchData = new OutboundBranchDataTO();
		Cookie[] cookie = null;
		// Get a JsonSerializer Object
		serializer = CGJasonConverter.getJsonObject();
		//logger.debug("DataExtractorClient ----Called parseClientRequest...........  inside parseClientRequest ");
		cookie = httpClient.getState().getCookies();
		if (cookie != null && cookie.length != 0) {
			//logger.debug("DataExtractorClient ----Called parseClientRequest...........  inside parseClientRequest---inside if block ");
			logger.debug("DataExtractorClient ----Called parseClientRequest...........  inside parseClientRequest---invoking postRequestToServer");
			// Post the Request to Server
			byte[] fileData = postRequestToServer(cookie, baseTo, branchData);
			
			
			branchData.setFileData(fileData);

		}
		return null;
	}

	/**
	 * Post request to server.
	 * 
	 * @param cookie
	 *            the cookie
	 * @param userTO
	 *            the user to
	 * @param extractorTo TODO
	 * @return the header
	 * @throws HttpException
	 *             the http exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	@SuppressWarnings("static-access")
	private byte[] postRequestToServer(Cookie[] cookie, CGBaseTO userTO, OutboundBranchDataTO extractorTo)
			throws HttpException, IOException, ClassNotFoundException {
		logger.debug("DataExtractorClient ----Called postRequestToServer...........  inside postRequestToServer");
		String jSessionId = null;
		for (int i = 0; i < cookie.length; i++) {
			if (cookie[i].getName()
					.equals(SplitModelConstant.JSESSIONID_COOKIE)) {
				jSessionId = cookie[i].getValue();
			}
		}
		//logger.debug("DataExtractorClient ...........  inside postRequestToServer----preparing url to central server");
		// create a host Address url for the remote machine
		StringBuilder urlBuilder = SplitModelUtil
				.createURLForRequest(SplitModelConstant.DATA_EXTRACTOR_SERVICE_FACADE);
		logger.debug("DataExtractorClient ...........  inside postRequestToServer----prepared url to central server urlBuilder :"+urlBuilder);
		PostMethod postRequest = new PostMethod(urlBuilder.toString());
		SplitModelUtil.setHeader(postRequest);
		JSON jsonObject = serializer.toJSON(userTO);
		logger.debug("DataExtractorClient ...........  inside postRequestToServer----jsonObject :"+jsonObject);
		postRequest.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
		// Adding parameter in post request
		postRequest
				.addParameter(SplitModelConstant.JSON, jsonObject.toString());
		postRequest.addParameter(SplitModelConstant.JSESSION_ID, jSessionId);
		//logger.debug("DataExtractorClient ...........  inside postRequestToServer----Executing the post method :");
		// Executing the post method
		Integer httpStatusCode = httpClient.executeMethod(postRequest);
		 logger.debug("DataExtractorClient ...........  inside postRequestToServer----Executed the post method Response statusCode:"+httpStatusCode);
		 if (httpStatusCode != HttpStatus.SC_OK) {
		        System.err.println("Method failed: " + postRequest.getStatusLine());
		        logger.error("DataExtractorClient ...........  inside postRequestToServer----Method failed: " + postRequest.getStatusLine());
		      }
		byte[] fileData = postRequest.getResponseBody();
		logger.debug("DataExtractorClient ...........  inside postRequestToServer----Executed the post method Response postRequest.getStatusCode():"+postRequest.getStatusCode());
		logger.debug("DataExtractorClient ...........  inside postRequestToServer----Executed the post method Response postRequest.getStatusText():"+postRequest.getStatusText());
		logger.debug("DataExtractorClient ...........  inside postRequestToServer----Executed the post method Response postRequest.isAborted():"+postRequest.isAborted());
		logger.debug("DataExtractorClient ...........  inside postRequestToServer----Executed the post method Response httpStatusCode:"+httpStatusCode);
		logger.debug("DataExtractorClient ...........  inside postRequestToServer----Executed the post method(user requested Method) userTO.getMethodName():"+userTO.getMethodName());
		
		//httpStatusCode = statusCode;
		Boolean isAborted= (Boolean)postRequest.isAborted();
		extractorTo.setIsAborted(isAborted);
		extractorTo.setHttpStatusCode(httpStatusCode);
		Header  headerInfoForIdStr = postRequest.getResponseHeader("dataExtctrIdStr");
		Header  headerInfoForIdStrArray = postRequest.getResponseHeader("dataExtctrIdStrArr");
		if(!StringUtil.isNull(headerInfoForIdStr)){
			extractorTo.setDataExtctrIdStr(headerInfoForIdStr.getValue());
			logger.debug("DataExtractorClient ...........  inside postRequestToServer----Executed the post method(user requested Method) retrived the id list:"+headerInfoForIdStr.getValue());
		}
		/*if(!StringUtil.isNull(headerInfoForIdStrArray)){
			extractorTo.setDataExtctrIdStrArray(headerInfoForIdStrArray.getValue());
			logger.debug("DataExtractorClient ...........  inside postRequestToServer----Executed the post method(user requested Method) retrived the id list:"+headerInfoForIdStrArray.getValue());
		}
		*/
		if(postRequest.isAborted()||postRequest.getStatusCode() != HttpStatus.SC_OK){
			fileData =null;
		}
		postRequest.releaseConnection();
		return fileData;
	}

	/**
	 * Gets the creates the logged in request.
	 * 
	 * @return the creates the logged in request
	 */
	public CreateHttpLoggingInRequest getCreateLoggedInRequest() {
		return createLoggedInRequest;
	}

	/**
	 * Sets the creates the logged in request.
	 * 
	 * @param createLoggedInRequest
	 *            the new creates the logged in request
	 */
	public void setCreateLoggedInRequest(
			CreateHttpLoggingInRequest createLoggedInRequest) {
		this.createLoggedInRequest = createLoggedInRequest;
	}

	/**
	 * Gets the http client.
	 * 
	 * @return the http client
	 */
	public HttpClient getHttpClient() {
		return httpClient;
	}

	/**
	 * Sets the http client.
	 * 
	 * @param httpClient
	 *            the new http client
	 */
	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

}
