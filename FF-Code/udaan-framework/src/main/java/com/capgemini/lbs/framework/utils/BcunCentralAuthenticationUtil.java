package com.capgemini.lbs.framework.utils;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.constants.SplitModelConstant;


public class BcunCentralAuthenticationUtil {
	
	/**
	 * Logger to log the massages
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(BcunCentralAuthenticationUtil.class);
	public static String officeCode;
	public static Boolean serverStatusFlag=false;
	public static Properties bcunProperties;
	// private CreateHttpClient userClient;
	/** The http client. */
	public static HttpClient httpClient;
	
	
	public static Boolean  getBcunCentralAuthenticationStatus(){
		
		if(!serverStatusFlag){
			try {
				LOGGER.debug("BcunBranchAuthenticationUtil ::getBcunCentralAuthenticationStatus:: start preparing Athentication ");
				createLoggingRequest();
			} catch (Exception e) {
				LOGGER.error("BcunBranchAuthenticationUtil ::getBcunBranchAuthenticationStatus:: EXCEPTION ",e);
			}
		}
		LOGGER.debug("BcunBranchAuthenticationUtil ::getBcunCentralAuthenticationStatus:: Final STATUS "+serverStatusFlag);
		return serverStatusFlag;
	}

	/**
	 * Creates the logging request.
	 *
	 * @return the cookie[]
	 * @throws HttpException the http exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	
	public static void createLoggingRequest() throws HttpException, IOException {
		// Host Address of the remote machine
		String url = null;
		Integer httpStatusCode = -1;
		
		String userName = System.getProperty(SplitModelConstant.USER_NAME);
		
		// make a login request to login.htm
		StringBuilder urlBuilder = createURLForRequest(SplitModelConstant.BCUN_LOGIN_SERVLET);

		url = urlBuilder.append(SplitModelConstant.QUESTION_MARK)
				.append(SplitModelConstant.USER_INFORMATION)
				.append(SplitModelConstant.EQUAL_OPERATOR).append(userName)
				.append(SplitModelConstant.AND_OPERATOR)
				.append(SplitModelConstant.USER_BRANCH_CODE)
				.append(SplitModelConstant.EQUAL_OPERATOR)
				.append(officeCode)
				.toString();
		
		PostMethod postRequest = new PostMethod(url);
		//Setting header of http post
		SplitModelUtil.setHeader(postRequest);
		//Setting the cooky policy
		postRequest.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		
		
		try{
				httpStatusCode = httpClient.executeMethod(postRequest);
				extractResponseFromServer(httpStatusCode, postRequest);
			}catch (HttpException e) {
			LOGGER.error("unable to connect with central server...:(HttpException)::"+httpStatusCode,e);
		    } catch (Exception e) {
		    	LOGGER.error("unable to connect with central server...:(IOException)::"+httpStatusCode,e);
		    } finally {
		      // Release the connection.
		    	try {
		    		if(postRequest!=null){
		    			postRequest.releaseConnection();
		    		}
				} catch (Exception e) {
					LOGGER.error("HttpRequestUtil ::Exception Occurred at the time of Connection releasing:(Exception)::"+httpStatusCode,e);
				}
		    }  
		
		

	}

	static void extractResponseFromServer(Integer httpStatusCode,
			PostMethod postRequest) {
		Header headerInfoForIdStr=null;
		LOGGER.info("HttpRequestUtil :: extractResponseFromServer:: httpStatusCode"+httpStatusCode);
		if(httpStatusCode == HttpStatus.SC_OK){
			headerInfoForIdStr = postRequest.getResponseHeader(SplitModelConstant.RESPONSE_FROM_SERVER);
		}
		if(headerInfoForIdStr!=null){
			String result=headerInfoForIdStr.getValue();
			LOGGER.info("HttpRequestUtil :: extractResponseFromServer headerInfoForIdStr"+headerInfoForIdStr +"And header value :"+result);
			if(!StringUtil.isStringEmpty(result) && result.equalsIgnoreCase(FrameworkConstants.SUCCESS_FLAG)){
				serverStatusFlag=true;
			}

		}
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
	 * @param httpClient the new http client
	 */
	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	/**
	 * @return the officeCode
	 */
	public static String getOfficeCode() {
		return officeCode;
	}

	/**
	 * @param officeCode the officeCode to set
	 */
	public static void setOfficeCode(String officeCode) {
		BcunCentralAuthenticationUtil.officeCode = officeCode;
	}
	/**
	 * Spring's setter injection
	 * @param bcunProperties
	 */
	public void setBcunProperties(Properties bcunProperties) {
		this.bcunProperties = bcunProperties;
	}
	/**
	 * Creates the url.
	 * 
	 * @param request
	 *            the request
	 * @param requestType
	 *            the request type
	 * @return the string builder
	 */
	public static StringBuilder createURLForRequest(String requestHandler) {

		String host = bcunProperties.getProperty("central.server.host.name");
		//Central server port name
		String port = bcunProperties.getProperty("central.server.application.port");
		//Central server context name
		String ctx = bcunProperties.getProperty("central.server.application.name");
		StringBuilder urlBuilder = new StringBuilder();
		// build a url
		urlBuilder.append(SplitModelConstant.HTTP);
		urlBuilder.append(host);
		urlBuilder.append(SplitModelConstant.COLON);
		urlBuilder.append(port);
		urlBuilder.append("/");
		urlBuilder.append(ctx);
		urlBuilder.append(requestHandler);
		return urlBuilder;
	}
}
