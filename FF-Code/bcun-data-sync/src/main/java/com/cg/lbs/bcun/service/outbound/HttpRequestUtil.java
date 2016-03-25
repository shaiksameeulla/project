package com.cg.lbs.bcun.service.outbound;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import net.sf.json.JSONSerializer;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.SplitModelConstant;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.constant.BcunConstant;
import com.cg.lbs.bcun.to.OutboundBranchDataTO;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author mohammal
 * Feb 16, 2013
 * Helper class provides help to Http request  creation
 */
public class HttpRequestUtil {
	
	/**
	 * Logger to log the massages
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestUtil.class);
	
	private JSONSerializer serializer;
	/** The bcun data sync properties. */
	private Properties bcunProperties;
	/** The http client. */
	private HttpClient httpClient;
	
	private String requestBranchCode;
	/**
	 * Prepare the base URL with the help of BCUN property file
	 * @return base URL of the central application server
	 */
	public String getBaseUrl(String servletName) {
		//Central server host name
		String host = bcunProperties.getProperty("central.server.host.name");
		//Central server port name
		String port = bcunProperties.getProperty("central.server.application.port");
		//Central server context name
		String ctx = bcunProperties.getProperty("central.server.application.name");

		StringBuilder urlBuilder = new StringBuilder();
		//Preparing full base URL from components
		urlBuilder.append(BcunConstant.HTTP);
		urlBuilder.append(host);
		urlBuilder.append(BcunConstant.COLON);
		urlBuilder.append(port);
		urlBuilder.append("/");
		urlBuilder.append(ctx);
		//Central server requestHandler url
		urlBuilder.append("/");//
		urlBuilder.append(servletName);
		String url = urlBuilder.toString();
		urlBuilder = null;
		LOGGER.debug("HttpRequestUtil::getBaseUrl::url::" + url);
		return url;
	}

	/**
	 * Used to fetch the requested branch URL
	 * @return URL of requested branch
	 */
	public String branchRequestUrl () {
		String branchCode = requestBranchCode;//bcunProperties.getProperty("branch.office.code");
		int maxRecords = getMaxRowFetchCount();
		String branchUrl = getBaseUrl("requestHandler.ht") + "?branchCode=" + branchCode + "&maxRecords=" + maxRecords;
		LOGGER.debug("HttpRequestUtil::branchRequestUrl::url::" + branchUrl);
		return branchUrl;
	}
	
	public String statusUpdateUrl () {
		String branchCode = requestBranchCode;//bcunProperties.getProperty("branch.office.code");
		int maxRecords = getMaxRowFetchCount();
		String branchUrl = getBaseUrl("statusHandler.ht") + "?branchCode=" + branchCode + "&maxRecords=" + maxRecords;
		LOGGER.debug("HttpRequestUtil::branchRequestUrl::url::" + branchUrl);
		return branchUrl;
	}
	/**
	 * Prepare TO to send as a request parameter from branch to central server
	 * @return TO contains request information 
	 */
	public OutboundBranchDataTO prepareHttpRequestTO() {
		OutboundBranchDataTO requestTO = new OutboundBranchDataTO();
		//Getting office code 
		String branchCode = requestBranchCode;//bcunProperties.getProperty("branch.office.code");
		requestTO.setBranchCode(branchCode);
		//Getting mac row count
		int maxRecords = getMaxRowFetchCount();
		requestTO.setMaxRecords(maxRecords);
		
		return requestTO;
	}
	
	private int getMaxRowFetchCount () {
		int count = 0;
		try {
			String maxRowFetchCount = bcunProperties.getProperty("max.record.fetch.count");
			if(maxRowFetchCount == null || maxRowFetchCount.isEmpty()) {
				count = 0;//Set default value here
			} else {
				count = Integer.parseInt(maxRowFetchCount);
			}
		} catch (Exception ex) {
			LOGGER.error("HttpRequestUtil::getMaxRowFetchCount Exception",ex);
			count = 0;//Set default value here
		}
		return count;
	}
	
	/**
	 * Used  to post the request to the central server
	 * @param url of the central server
	 * @param requestTO contains branch specific request information.
	 * @return server response as a byte array
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 * @throws HttpException when error in connecting with central server
	 * @throws IOException while connecting with central server
	 */
	public byte[] postRequestToServer(String url, OutboundBranchDataTO requestTO) throws JsonGenerationException, JsonMappingException, IOException {
		String uniqueId=requestTO!=null ?requestTO.getUniqueRequestId():null;
		LOGGER.debug("HttpRequestUtil:: postRequestToServer::####Start##### with url ["+url+"] and with the request Id ["+uniqueId+"]" );
		byte[] serverResponse = null;
		//Created http post method
		PostMethod postRequest = new PostMethod(url);
		//Setting header of http post
		setHeader(postRequest);
		//Setting the cooky policy
		postRequest.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		//Creating object mapper
		ObjectMapper mapper = new ObjectMapper();
		//Creating writer
		StringWriter writer = new StringWriter();
		//Writing content to the writer with the help of mapper
		mapper.writeValue(writer, requestTO);
		String jsonString = writer.toString();
		Integer httpStatusCode = -1;
		try{

			postRequest.addParameter(SplitModelConstant.JSON, jsonString);
			try {
				httpStatusCode = httpClient.executeMethod(postRequest);
			} catch (Exception ex ) {
				LOGGER.error("HttpRequestUtil ...........  inside postRequestToServer----Executed the post method Response Exception::the request Id ["+uniqueId+"]",ex);
			//statusRedirector(url, uniqueId, postRequest, jsonString);
			}
			LOGGER.debug("HttpRequestUtil ...........  inside postRequestToServer----Executed the post method Response httpStatusCode:"+httpStatusCode);
			if(postRequest!=null && postRequest.getStatusLine()!=null){
				LOGGER.debug("HttpRequestUtil ...........  inside postRequestToServer----Executed the post method Response postRequest.getStatusCode():"+postRequest.getStatusCode());
				LOGGER.debug("HttpRequestUtil ...........  inside postRequestToServer----Executed the post method Response postRequest.getStatusText():"+postRequest.getStatusText());
				LOGGER.debug("HttpRequestUtil ...........  inside postRequestToServer----Executed the post method Response postRequest.isAborted():"+postRequest.isAborted());
			}else{
				LOGGER.error("HttpRequestUtil ###################inside postRequestToServer##############Executed the post method Response :: Response not received/status line of response is invalid");
			}
			requestTO.setHttpStatusCode(httpStatusCode);
			if(httpStatusCode == -1) {
				LOGGER.debug("unable to connect with central server...:"+httpStatusCode);
			} else if (httpStatusCode == HttpStatus.SC_OK) {
				LOGGER.debug("HttpRequestUtil ...........  inside postRequestToServer----Method success: the request Id ["+uniqueId+"]"  + postRequest.getStatusLine());
				serverResponse = postRequest.getResponseBody();
				getPacketIdList(requestTO, postRequest, httpStatusCode);//FIXME For testing
			}else{
				LOGGER.error("HttpRequestUtil ...........  inside postRequestToServer----Method failed: the request Id ["+uniqueId+"]"  + postRequest.getStatusLine());
				getPacketIdList(requestTO, postRequest, httpStatusCode);
			}
		}catch (HttpException e) {
			LOGGER.error("unable to connect with central server...:(HttpException)::"+httpStatusCode,e);
		    } catch (IOException e) {
		    	LOGGER.error("unable to connect with central server...:(IOException)::"+httpStatusCode,e);
		    } catch (Exception e) {
		    	LOGGER.error("unable to connect with central server...:(Exception)::"+httpStatusCode,e);
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
		
		LOGGER.debug("HttpRequestUtil:: postRequestToServer::####END##### with url ["+url+"] the request Id ["+uniqueId+"]" );
		return serverResponse;
	}

	/*private String statusRedirector(String url, String uniqueId,
			PostMethod postRequest, String jsonString) {
		if(!StringUtil.isStringEmpty(url) && url.contains("statusHandler.ht")){
			//redirect request other port
			url=url.replace("8075", "9075");
			postRequest.setPath(url);
			postRequest.addParameter(SplitModelConstant.JSON, jsonString);
			try {
				int httpStatusCode1 = httpClient.executeMethod(postRequest);
				LOGGER.error("HttpRequestUtil ...........  inside postRequestToServer----Reponse codeAfter redirection for ["+uniqueId+"]"+httpStatusCode1);
			} catch (Exception redirectedEx ) {
				LOGGER.error("HttpRequestUtil ...........  inside postRequestToServer----redirectedException for ["+uniqueId+"]",redirectedEx);
			}
		}
		return url;
	}*/

	/**
	 * @param requestTO
	 * @param postRequest
	 * @param jsonString
	 * @param httpStatusCode
	 */
	private void getPacketIdList(OutboundBranchDataTO requestTO,
			PostMethod postRequest,Integer httpStatusCode) {
		//requestTO.setHttpStatusCode(httpStatusCode);
		String uniqueId=requestTO!=null ?requestTO.getUniqueRequestId():null;
		Header  headerInfoForIdStr = postRequest.getResponseHeader(BcunConstant.DATA_ETRACTION_ID);
		Header  headerInfoForIdList = postRequest.getResponseHeader(BcunConstant.DATA_ETRACTION_ID_LIST);
		if(!StringUtil.isNull(headerInfoForIdStr)){
			List<Long> idList=null;
			requestTO.setDataExtctrIdStr(headerInfoForIdStr.getValue());
			String result=headerInfoForIdStr.getValue().toString().replace("[", "").replace("]", "");
			String idStr[]=result.split(",");
			idList = new ArrayList<>(idStr.length);
			for(String str:idStr){
				idList.add(new Long(str.trim()));
			}
			requestTO.setPacketIds(idList);
			LOGGER.debug("HttpRequestUtil ...........  inside postRequestToServer----Executed the post method(user requested Method) retrived the id list:"+headerInfoForIdStr.getValue()+" for the request Id ["+uniqueId+"]" );
		}
		if(!StringUtil.isNull(headerInfoForIdList) && CGCollectionUtils.isEmpty(requestTO.getPacketIds())){
			String jsonIdArray = new String(headerInfoForIdStr.getValue());
			//Converting JSON string into TO 
			ObjectMapper resultMapper = new ObjectMapper(); 
			Object requiredObject = null;
			try {
				//converting JSON string to java object with the help of mapper  
				requiredObject = resultMapper.readValue(jsonIdArray, Long[].class);
				Long [] packetIds= (Long [])requiredObject;
				requestTO.setPacketIds(Arrays.asList(packetIds));
				LOGGER.debug("HttpRequestUtil ...........  inside postRequestToServer----Executed the post method(user requested Method) retrived the id list:"+requestTO.getPacketIds()+"the request Id ["+uniqueId+"]" );
			}catch(Exception e){
				LOGGER.error("HttpRequestUtil::getPacketIdList:: Exception",e);
			}
		}
	}
	
	/**
	 * Used to setting the request header
	 * @param m
	 */
	private void setHeader(HttpMethod m) {
		//Setting request header's user agent
		m.setRequestHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; de; rv:1.9.1.3) Gecko/20090824 (CK-IBM) Firefox/3.5.3");
		//Setting request header's content type
		m.setRequestHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		//Setting request header's agent language as US English
		m.setRequestHeader("Accept-Language",
				"de-de,de;q=0.8,en-us;q=0.5,en;q=0.3");
		//Setting request header's agent encoding type
		m.setRequestHeader("Accept-Encoding", "gzip,deflate");
		//Setting request header's user agent allowed Character set utf-8
		m.setRequestHeader("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
		//Setting request header's user agent cookies alive time
		m.setRequestHeader("Keep-Alive", "300");
		//Setting request header's user agent connection active
		m.setRequestHeader("Connection", "keep-alive");
	}
	
	/**
	 * Spring's setter injection
	 * @param bcunProperties
	 */
	public void setBcunProperties(Properties bcunProperties) {
		this.bcunProperties = bcunProperties;
	}

	/**
	 * Spring's setter injection
	 * @param httpClient
	 */
	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public void setRequestBranchCode(String requestBranchCode) {
		this.requestBranchCode = requestBranchCode;
	}
}
