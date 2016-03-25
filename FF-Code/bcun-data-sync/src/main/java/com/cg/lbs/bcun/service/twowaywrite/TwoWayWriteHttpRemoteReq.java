package com.cg.lbs.bcun.service.twowaywrite;

import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.SplitModelConstant;
import com.cg.lbs.bcun.constant.BcunConstant;
import com.cg.lbs.bcun.service.outbound.HttpRequestUtil;


/**
 * The Class TwoWayWriteHttpRemoteReq.
 * 
 * @author narmdr
 */
public class TwoWayWriteHttpRemoteReq {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(TwoWayWriteHttpRemoteReq.class);
	/** The http client. */
	private transient HttpClient httpClient;

	/**
	 * Helper class to create http request
	 */
	private transient HttpRequestUtil requestUtil;
	
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
	 * @return the requestUtil
	 */
	public HttpRequestUtil getRequestUtil() {
		return requestUtil;
	}

	/**
	 * @param requestUtil the requestUtil to set
	 */
	public void setRequestUtil(HttpRequestUtil requestUtil) {
		this.requestUtil = requestUtil;
	}

	/**
	 * Two way write request queue.
	 *
	 * @param jsonStr the json str
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String twoWayWriteRequestQueue(String jsonStr) throws IOException {
		logger.trace("TwoWayWriteHttpRemoteReq::twoWayWriteRequestQueue::START------------>:::::::");
		
		Header resposnseFromServer = null;
		Integer httpStatusCode = -1;
		String responseStr = CommonConstants.FAILURE;
		
		/*int proxyPort = SplitModelUtil.getProxyPort();
		String proxyServer = SplitModelUtil.getProxyServer();
		httpClient.getHostConfiguration().setProxy(proxyServer, proxyPort);
		httpClient.getState().clearCookies();*/

		//StringBuilder urlBuilder = SplitModelUtil.createURLForRequest(SplitModelConstant.TWO_WRITE_REMOTE_SERVLET_REQUEST);
		
		//Central server Address of the remote machine
		String branchRequestUrl = requestUtil.getBaseUrl(BcunConstant.TWO_WRITE_REMOTE_SERVLET_NAME);
		logger.trace("TwoWayWriteHttpRemoteReq::twoWayWriteRequestQueue::URL to connect : "
				+ branchRequestUrl + "------------>:::::::");


		PostMethod postRequest = new PostMethod(branchRequestUrl);
		
		try{
			postRequest.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
			postRequest.addParameter(SplitModelConstant.JSON, jsonStr);
			
			try {
				httpStatusCode = httpClient.executeMethod(postRequest);
			} catch (Exception ex ) {
				//responseStr = CommonConstants.FAILURE;
				logger.error("TwoWayWriteHttpRemoteReq ...........  inside twoWayWriteRequestQueue----Executed the post method Response Exception::",ex);
			}
			logger.debug("TwoWayWriteHttpRemoteReq ...........  inside twoWayWriteRequestQueue----Executed the post method Response httpStatusCode:"+httpStatusCode);
			if(postRequest!=null && postRequest.getStatusLine()!=null){
				logger.debug("TwoWayWriteHttpRemoteReq ...........  inside twoWayWriteRequestQueue----Executed the post method Response postRequest.getStatusCode():"+postRequest.getStatusCode());
				logger.debug("TwoWayWriteHttpRemoteReq ...........  inside twoWayWriteRequestQueue----Executed the post method Response postRequest.getStatusText():"+postRequest.getStatusText());
				logger.debug("TwoWayWriteHttpRemoteReq ...........  inside twoWayWriteRequestQueue----Executed the post method Response postRequest.isAborted():"+postRequest.isAborted());
			}else{
				logger.error("TwoWayWriteHttpRemoteReq ###################inside twoWayWriteRequestQueue##############Executed the post method Response :: Response not received/status line of response is invalid");
			}
			if(httpStatusCode == -1) {
				logger.debug("unable to connect with central server...:"+httpStatusCode);
			} else if (httpStatusCode == HttpStatus.SC_OK) {
				logger.debug("TwoWayWriteHttpRemoteReq ...........  inside twoWayWriteRequestQueue----Method success: postRequest StatusLine"  + postRequest.getStatusLine());
				resposnseFromServer = postRequest.getResponseHeader(SplitModelConstant.RESPONSE_FROM_SERVER);				
				if (resposnseFromServer != null) {
					responseStr = resposnseFromServer.getValue();
				}
			}
		}catch (Exception e) {
			logger.error("TwoWayWriteHttpRemoteReq ...........unable to connect with central server...:(Exception)::"
							+ httpStatusCode, e);
		} finally {
			// Release the connection.
			try {
				if (postRequest != null) {
					postRequest.releaseConnection();
				}
			} catch (Exception e) {
				logger.error( "TwoWayWriteHttpRemoteReq ::Exception Occurred at the time of Connection releasing:(Exception)::"
								+ httpStatusCode, e);
			}
		}
		
		
		/*resposnseFromServer = postRequest.getResponseHeader(SplitModelConstant.RESPONSE_FROM_SERVER);
		
		if (resposnseFromServer != null) {
			responseStr = resposnseFromServer.getValue();
		}
		postRequest.releaseConnection();	*/	

		logger.trace("TwoWayWriteHttpRemoteReq::twoWayWriteRequestQueue::END------------>:::::::");
		return responseStr;
	}	
}
