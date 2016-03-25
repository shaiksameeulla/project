package com.cg.lbs.bcun.utility;

import java.io.IOException;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;

import com.capgemini.lbs.framework.constants.SplitModelConstant;
import com.capgemini.lbs.framework.utils.SplitModelUtil;


public class CreateHttpLoggingInRequest {
	
	public static final String BRANCH_CODE = "branchCode";
	// private CreateHttpClient userClient;
	/** The http client. */
	private HttpClient httpClient;

	/**
	 * Creates the logging request.
	 *
	 * @return the cookie[]
	 * @throws HttpException the http exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	
	public Cookie[] createLoggingRequest() throws HttpException, IOException {
		// Host Address of the remote machine
		String host = null;
		Cookie[] cookie = null;

		String macAddress = SplitModelUtil.getMacAddress();
		String userName = System.getProperty(SplitModelConstant.USER_NAME);
		// Get HttpClient Object
		// userHTTPClient = userClient.getHTTPClientObject();
		// Setting Proxy Configuration
		//boolean proxyCheck = httpClient.getHostConfiguration().isProxySet();
		//if (true) {
			int proxyPort = SplitModelUtil.getProxyPort();
			String proxyServer = SplitModelUtil.getProxyServer();
			httpClient.getHostConfiguration().setProxy(proxyServer, proxyPort);
			httpClient.getState().clearCookies();
			// set proxy usename and password
			/*
			 * userHTTPClient.getState().setProxyCredentials("my-proxy-realm",
			 * " 10.48.152.184", new
			 * UsernamePasswordCredentials("my-proxy-username",
			 * "my-proxy-password"));
			 */
		//}
		String branchCode="";
		// make a login request to login.htm
		StringBuilder urlBuilder = SplitModelUtil
				.createURLForRequest(SplitModelConstant.LOGIN_REQUEST);

		host = urlBuilder.append(SplitModelConstant.QUESTION_MARK)
				.append(SplitModelConstant.USER_INFORMATION)
				.append(SplitModelConstant.EQUAL_OPERATOR).append(userName)
				.append(SplitModelConstant.AND_OPERATOR)
				.append(BRANCH_CODE)
				.append(SplitModelConstant.EQUAL_OPERATOR)
				.append(branchCode)
				.toString();
		GetMethod get = new GetMethod(host);
		SplitModelUtil.setHeader(get);
		get.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		// get.getParams().setParameter("JSON", j);
		httpClient.executeMethod(get);
		get.releaseConnection();
		cookie = httpClient.getState().getCookies();

		return cookie;
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
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public Boolean isAutheniticatedUserSystemCode() throws Exception {
			Boolean authencateFlag=false;
			
			Cookie[] cookie = createLoggingRequest();
			for (int i = 0; i < cookie.length; i++) {
				String cookieName = cookie[i].getName();
				String cookieValue = cookie[i].getValue();
				
				if (cookieName.equals(SplitModelConstant.USER_STATUS) && cookieValue.equals(SplitModelConstant.AUTHORISED_USER)) {
					authencateFlag=true;					
					// add to session
					
					
					
					
				}

			}
			
			return authencateFlag;
	}

}
