/**
 * 
 */
package com.ff.universe.print.service;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;

/**
 * @author hkansagr
 * 
 */
public class PrintJobUniversalServiceImpl implements PrintJobUniversalService, Runnable {

	/** The Logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PrintJobUniversalServiceImpl.class);

	/** The printer Http Client */
	private HttpClient printerHttpClient = null;

	/** The thread t used for multi-threading */
	private Thread t = null;
	
	/** The PostMEthod used for sending request to Jetty server */
	private PostMethod postRequest = null;
	
	/**
	 * @param printerHttpClient
	 *            the printerHttpClient to set
	 */
	public void setPrinterHttpClient(HttpClient printerHttpClient) {
		this.printerHttpClient = printerHttpClient;
	}

	@Override
	public void executePrintJob(String printJobUrl, String template)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PrintJobUniversalServiceImpl :: executePrintJob(String,String) :: START");
		try {
			// Created http post method
			PostMethod postRequest = new PostMethod(printJobUrl);
			setHeader(postRequest);
			// Setting the cooky policy
			postRequest.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
			postRequest.addParameter(CommonConstants.PRINT_STR, template);
			
			int httpStatusCode = printerHttpClient.executeMethod(postRequest);
			LOGGER.info("Printer Server Reply Code : " + httpStatusCode);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in PrintJobUniversalServiceImpl :: executePrintJob(String,String) ..EXCEPTION : ",
					e);
			throw new CGBusinessException(e);
		}
		LOGGER.trace("PrintJobUniversalServiceImpl :: executePrintJob(String,String) :: END");
	}

	
	@Override
	public void executePrintJobInParallel(String printJobUrl, byte[] template)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PrintJobUniversalServiceImpl :: executePrintJob(String,byte[]) :: START");
		try {
			// Created http post method
			this.postRequest = new PostMethod(printJobUrl);
			setHeader(postRequest);
			// Setting the cooky policy
			postRequest.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
			postRequest.setRequestHeader("Content-type","application/octet-stream");
			postRequest.setRequestEntity(new ByteArrayRequestEntity(template));
			t = new Thread(this);
			t.start();
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in PrintJobUniversalServiceImpl :: executePrintJob(String,byte[]) ..EXCEPTION : ",
					e);
			throw new CGBusinessException(e);
		}
		LOGGER.trace("PrintJobUniversalServiceImpl :: executePrintJob(String,byte[]) :: END");
	}
	
	/**
	 * Used to setting the request header
	 * 
	 * @param m
	 */
	private void setHeader(HttpMethod m) {
		LOGGER.trace("PrintJobUniversalServiceImpl :: setHeader() :: START");
		// Setting request header's user agent
		m.setRequestHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; de; rv:1.9.1.3) Gecko/20090824 (CK-IBM) Firefox/3.5.3");
		// Setting request header's content type
		m.setRequestHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		// Setting request header's agent language as US English
		m.setRequestHeader("Accept-Language",
				"de-de,de;q=0.8,en-us;q=0.5,en;q=0.3");
		// Setting request header's agent encoding type
		m.setRequestHeader("Accept-Encoding", "gzip,deflate");
		// Setting request header's user agent allowed Character set utf-8
		m.setRequestHeader("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
		// Setting request header's user agent cookies alive time
		m.setRequestHeader("Keep-Alive", "300");
		// Setting request header's user agent connection active
		m.setRequestHeader("Connection", "keep-alive");
		LOGGER.trace("PrintJobUniversalServiceImpl :: setHeader() :: END");
	}
	
	
	@Override
	public void run() {
		try {
			int httpStatusCode = printerHttpClient.executeMethod(this.postRequest);
			LOGGER.info("Printer Server Reply Code : " + httpStatusCode);
		}
		catch (Exception e) {
			LOGGER.error("PrintJobUniversalServiceImpl :: run() :: ERROR",e);
		}
	}

}
