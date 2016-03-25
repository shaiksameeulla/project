package com.cg.lbs.bcun.utility;


import java.io.IOException;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.SplitModelConstant;
import com.capgemini.lbs.framework.to.CGBaseTO;


/**
 * @author mohammal
 * Feb 12, 2013
 * 
 */
public class UserHttpClient {
	
	/** The http client. */
	private HttpClient httpClient;
	// private CreateHttpClient userClient;
	/** The json client. */
	private UserHttpClientUtil httpClientUtill;
	
	/** The create logged in request. */
	private CreateHttpLoggingInRequest createLoggedInRequest;
	
	/** The logger. */
	Logger logger = LoggerFactory.getLogger(UserHttpClient.class);

	/**
	 * Creates the remote request.
	 *
	 * @param baseTO the base to
	 * @return the CG base to
	 * @throws HttpException the http exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException the class not found exception
	 */
	public CGBaseTO createRemoteRequest(CGBaseTO baseTO)
			throws HttpException, IOException, ClassNotFoundException {

		// Get HttpClient Object
		// userHTTPClient = userClient.getHTTPClientObject();
		/*
		 * Get Cookie from HttpState Validate the cookie to check the logged in
		 * status if it is logged in no need to send the logges in request call
		 * the restfull service else make a http get request and bind the *
		 * session
		 */
		Cookie[] cookie = httpClient.getState().getCookies();
		logger.debug("Called createRemoteRequest........... cookies : "+cookie);
		if (cookie != null && cookie.length != 0) {
			//logger.debug("Called createRemoteRequest........... inside outer if-block : ");
			for (int i = 0; i < cookie.length; i++) {
				String cookieName = cookie[i].getName();
				String cookieValue = cookie[i].getValue();
				if (cookieName.equals(SplitModelConstant.USER_STATUS)
						&& cookieValue
								.equals(SplitModelConstant.USER_LOGGED_IN)) {
					//logger.debug("Called createRemoteRequest...........  inside inner if-block");
					logger.debug("Called createRemoteRequest........... cookieName : "+cookieName);
					logger.debug("Called createRemoteRequest........... cookieValue : "+cookieValue);
					baseTO = httpClientUtill.parseClientRequest(baseTO);
					//logger.info("Call RestFull Service Facade...........No need to make looged in call");
					//logger.debug("Called createRemoteRequest...........  exit inner if-block");
				}else{
					//logger.debug("Called createRemoteRequest...........  start else-block");
					logger.debug("Called createRemoteRequest........... cookieValue : "+cookieValue);
					httpClient.getState().clearCookies();
					//logger.debug("Called createRemoteRequest...........  start inner else-block");
					cookie = createLoggedInRequest.createLoggingRequest();
				}
			}
			//logger.debug("Called createRemoteRequest........... exiting outer if-block : ");
		} else {
			//logger.debug("Called createRemoteRequest...........  inside outer if-block");
			cookie = createLoggedInRequest.createLoggingRequest();
			for (int i = 0; i < cookie.length; i++) {
				//logger.debug("Called createRemoteRequest...........  inside outer if-block---for loop");
				logger.debug(cookie[i].toString());
				String cookieName = cookie[i].getName();
				String cookieValue = cookie[i].getValue();
				if (cookieName.equals(SplitModelConstant.USER_STATUS)
						&& cookieValue
								.equals(SplitModelConstant.USER_LOGGED_IN)) {
					//logger.debug("Called createRemoteRequest...........  inside outer if-block---for loop-if ");
					baseTO = httpClientUtill.parseClientRequest(baseTO);
				}

			}
			//logger.debug("Called createRemoteRequest...........  exiting outer if-block");
		}
		return baseTO;

	}

	

	public void setHttpClientUtill(UserHttpClientUtil httpClientUtill) {
		this.httpClientUtill = httpClientUtill;
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
	 * @param createLoggedInRequest the new creates the logged in request
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
	 * @param httpClient the new http client
	 */
	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}


}
