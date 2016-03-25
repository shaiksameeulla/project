/*
 * @author soagarwa
 */
package com.capgemini.lbs.framework.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.SplitModelConstant;

/**
 * The Class SplitModelUtil.
 * 
 * @author soagarwa
 */
public abstract class SplitModelUtil {

	/** The logger. */
	static Logger logger = LoggerFactory.getLogger(SplitModelUtil.class);

	/* This will return the unique machine address of client machine */

	/**
	 * Gets the mac address.
	 * 
	 * @return the mac address
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@ Deprecated
	public static String getMacAddress() throws IOException {
		String macAddress = null;
		String command = null;
		String osName = System.getProperty("os.name");
		/* For Windows machine */
		if (StringUtils.containsIgnoreCase(osName,
				SplitModelConstant.WINDOW_OS_NAME)) {
			command = SplitModelConstant.IP_CONFIG;
			/* Setting the exe command */
			Process pid = Runtime.getRuntime().exec(command);
			// Buffering the input Stream
			BufferedReader in = new BufferedReader(new InputStreamReader(
					pid.getInputStream()));
			while (true) {
				String line = in.readLine();
				if (line == null)
					break;
				Pattern p = Pattern
						.compile(SplitModelConstant.PHYSICAL_ADDRESS_PATTERN);
				Matcher m = p.matcher(line);
				if (m.matches()) {
					macAddress = m.group(1);
					break;
				}
			}
			/* Closed the buffered stream */
			in.close();
		}
		/* For Linux Machine */
		else {
			command = SplitModelConstant.IF_CONFIG_FOR_LINUX;
			/* Setting the exe command */
			Process pid = Runtime.getRuntime().exec(command);
			// Buffering the input Stream
			BufferedReader in = new BufferedReader(new InputStreamReader(
					pid.getInputStream()));
			while (true) {
				String line = in.readLine();
				if (line == null)
					break;
				Pattern p = Pattern
						.compile(SplitModelConstant.PHYSICAL_ADDRESS_PATTERN_FOR_LINUX);
				Matcher m = p.matcher(line);
				if (m.matches()) {
					macAddress = m.group(0).substring(38);
					break;
				}
			}
			/* Closed the buffered stream */
			in.close();
		}

		return macAddress;
	}
	
	

	/*
	 * This method will check whether system is in online mode or not by
	 * checking the internet connection for particular period of time
	 */

	/**
	 * Check online mode.
	 * 
	 * @return true, if successful
	 */
	public static boolean checkOnlineMode(final String domain, final String userName, final String password) {
		//boolean checkOnlineStatus = false;
		boolean checkOnlineStatus = true;
		/*try {
			 Proxy Address And Proxy Port 
			String proxyServer = getProxyServer();
			int proxyPort = getProxyPort();
			logger.info("Proxy server issssssssssss----------------------------"
					+ proxyServer);
			logger.info("Proxy Port issssssssssss----------------------------"
					+ proxyPort);
			SocketAddress socketAddress = new InetSocketAddress(proxyServer,
					proxyPort);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, socketAddress);
			 Host Adress 
			StringBuilder urlBuilder = SplitModelUtil.createURL(
					null,
					SplitModelConstant.DOMAIN_NAME);

			Authenticator.setDefault(new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					//return new PasswordAuthentication("corp\\soagarwa",
					//		"password".toCharArray());
					
					return new PasswordAuthentication(domain + File.separator + userName  ,
							password.toCharArray());
				}
			});
			logger.debug("SplitModelUtil::checkOnlineMode::checking for user [" + userName + "]");
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url
					.openConnection(proxy);
			 Will recursively check for connectivity for specified time 
			conn.setConnectTimeout(10000);
			logger.debug("SplitModelUtil::checkOnlineMode::reponse code for user [" + userName + "] is: " + conn.getResponseCode());
			logger.info("-----------------" + conn.getContent());
			if (!(conn.getContent().equals(null))) {
				checkOnlineStatus = true;
			}
			logger.debug("SplitModelUtil::checkOnlineMode::availabilty for user [" + userName + "] is: " + checkOnlineStatus);
		} catch (Exception e) {
			 If exception comes i.e. non availability of internet connection 
			logger.info("Online Mode Not Available", e);
			return false;
		}*/
		return checkOnlineStatus;
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
	public static StringBuilder createURL(String request, String requestType) {

		ResourceBundle rb = ResourceBundle
				.getBundle(SplitModelConstant.SPLIT_MODEL_PROPERTIES);
		String domainName = rb.getString(SplitModelConstant.DOMAIN_NAME);
		String serverPath = rb.getString(SplitModelConstant.REMOTE_SERVER);
		String port = rb.getString(SplitModelConstant.HTTP_PORT);
		domainName = rb.getString(requestType);
		StringBuilder urlBuilder = new StringBuilder();
		// build a url
		urlBuilder.append(SplitModelConstant.HTTP);
		urlBuilder.append(serverPath);
		urlBuilder.append(SplitModelConstant.COLON);
		urlBuilder.append(port);
		urlBuilder.append(domainName);
		if(!StringUtil.isStringEmpty(request)){
		urlBuilder.append(request);
		}
		return urlBuilder;
	}

	/**
	 * Creates the url for request.
	 * 
	 * @param request
	 *            the request
	 * @return the string builder
	 */
	public static StringBuilder createURLForRequest(String request) {
		String requestType = SplitModelConstant.DOMAIN_NAME;
		StringBuilder urlBuilder = createURL(request, requestType);

		return urlBuilder;
	}

	/**
	 * Gets the proxy server.
	 * 
	 * @return the proxy server
	 */
	public static String getProxyServer() {
		ResourceBundle rb = ResourceBundle
				.getBundle(SplitModelConstant.SPLIT_MODEL_PROPERTIES);
		return rb.getString(SplitModelConstant.PROXY_SERVER);
	}

	/**
	 * Gets the proxy port.
	 * 
	 * @return the proxy port
	 */
	public static int getProxyPort() {
		ResourceBundle rb = ResourceBundle
				.getBundle(SplitModelConstant.SPLIT_MODEL_PROPERTIES);
		return Integer.parseInt(rb.getString(SplitModelConstant.PROXY_PORT));
	}

	/**
	 * Sets the header.
	 * 
	 * @param m
	 *            the new header
	 */
	public static void setHeader(HttpMethod m) {
		m.setRequestHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; de; rv:1.9.1.3) Gecko/20090824 (CK-IBM) Firefox/3.5.3");
		m.setRequestHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		m.setRequestHeader("Accept-Language",
				"de-de,de;q=0.8,en-us;q=0.5,en;q=0.3");
		m.setRequestHeader("Accept-Encoding", "gzip,deflate");
		m.setRequestHeader("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
		m.setRequestHeader("Keep-Alive", "300");
		m.setRequestHeader("Connection", "keep-alive");
	}
	/**
	 * This returns MACAddress of the loggedin User
	 * @param clientIpAddress
	 * @return
	 * @throws IOException
	 */
	public static String getMacAddress(String clientIpAddress) throws IOException {
		String macAddress = null;
		String command = null;
		String osName = System.getProperty("os.name");
		/* For Windows machine */
		if (StringUtils.containsIgnoreCase(osName,
				SplitModelConstant.WINDOW_OS_NAME)) {
			command = "arp -a ";
			/* Setting the exe command */
			Process pid = Runtime.getRuntime().exec(command+clientIpAddress);
			// Buffering the input Stream
			BufferedReader in = new BufferedReader(new InputStreamReader(pid.getInputStream()));
			int lineCnt = 0;
			while (true) {
				String line = in.readLine();
				if (line == null|| line.equalsIgnoreCase(SplitModelConstant.CLIENT_PHYSICAL_NOADDRESS))
					break;
				else if(lineCnt == 3){
					int i = 0;					
					StringTokenizer stoken1 = new StringTokenizer(line," ");
					while (stoken1.hasMoreTokens()) {
						String tokenStr = stoken1.nextToken();
						//System.out.println(tokenStr+"--i --"+i);
						if(i == 1){
							macAddress = tokenStr;
							break;
						}
						i++;
						
					}
					// Line number 3 has details of ip and mac address
					break;
				}
				lineCnt++;
			}
			/* Closed the buffered stream */
			in.close();
		}
		/* For Linux Machine */
		else {
			command = SplitModelConstant.IF_CONFIG_FOR_LINUX;
			/* Setting the exe command */
			Process pid = Runtime.getRuntime().exec(command);
			// Buffering the input Stream
			BufferedReader in = new BufferedReader(new InputStreamReader(
					pid.getInputStream()));
			int i = 0;
			StringTokenizer stoken1 = new StringTokenizer(in.readLine());
			while (stoken1.hasMoreTokens()) {
				String tokenStr = stoken1.nextToken();
				if(i == 3){
					macAddress = tokenStr;
					break;
				}
				i++;						
			}
			/* Closed the buffered stream */
			in.close();
		}

		return macAddress;
	}
	
	/*
	 * public static boolean isLocalDBUp() { boolean checkLocalDBStatus = false;
	 * try { Proxy Address And Proxy Port String proxyServer = getProxyServer();
	 * int proxyPort = getProxyPort();
	 * logger.info("Proxy server issssssssssss----------------------------"+
	 * proxyServer);
	 * logger.info("Proxy Port issssssssssss----------------------------"+
	 * proxyPort); SocketAddress socketAddress = new
	 * InetSocketAddress(proxyServer, proxyPort); Proxy proxy = new
	 * Proxy(Proxy.Type.HTTP, socketAddress); Host Adress StringBuilder
	 * urlBuilder = SplitModelUtil.createURL( SplitModelConstant.WELCOME_PAGE,
	 * SplitModelConstant.WEB_DOMAIN_NAME);
	 * 
	 * 
	 * Authenticator.setDefault(new Authenticator() { protected
	 * PasswordAuthentication getPasswordAuthentication() { return new
	 * PasswordAuthentication("corp\\soagarwa","password".toCharArray()); }});
	 * 
	 * URL url = new URL(urlBuilder.toString()); HttpURLConnection conn =
	 * (HttpURLConnection) url.openConnection(proxy); Will recursively check for
	 * connectivity for specified time conn.setConnectTimeout(10000);
	 * logger.info("-----------------" + conn.getContent()); if
	 * (!(conn.getContent().equals(null))) { checkLocalDBStatus = true; } }
	 * catch (Exception e) { If exception comes i.e. non availability of
	 * internet connection logger.info("Online Mode Not Available", e); return
	 * false; } return checkLocalDBStatus; }
	 */
	
	public static boolean isTwoWayWriteEnabledforOpps(){
		boolean status_flag=true;
		ResourceBundle rb = ResourceBundle
		.getBundle(SplitModelConstant.SPLIT_MODEL_PROPERTIES);
		String value= rb.getString(SplitModelConstant.IS_TWO_WAYWRITE_ENABLED);
		if(value.equalsIgnoreCase("true")){
			status_flag=true;
		}else{
			status_flag=false;
		}
		return status_flag;
	}
	
	public static String getMaxFetchRecordSize(String pType){
		logger.debug("In split model utils Calling isTwoWayWriteEnabledforOpps:::::: starts"+System.currentTimeMillis());
		ResourceBundle rb = ResourceBundle
		.getBundle(SplitModelConstant.SPLIT_MODEL_PROPERTIES);
		String value= "";
		if(pType.equalsIgnoreCase("FRBOOKING")){
			value=rb.getString(SplitModelConstant.FR_MAX_FETCH_SIZE_DBSYNC);
		}else if(pType.equalsIgnoreCase("DPBOOKING")){
			value=rb.getString(SplitModelConstant.DP_MAX_FETCH_SIZE_DBSYNC);
		}
		logger.debug("In split model utils Calling isTwoWayWriteEnabledforOpps:::::: end"+System.currentTimeMillis());
		return value;
	}
	
}
