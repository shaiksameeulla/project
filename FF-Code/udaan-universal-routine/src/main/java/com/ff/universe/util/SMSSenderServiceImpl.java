/**
* 
 */
package com.ff.universe.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.universe.constant.SMSConstants;

/**
* @author abarudwa
* 
 */
public class SMSSenderServiceImpl implements SMSSenderService {
    private final static Logger LOGGER = LoggerFactory
                    .getLogger(SMSSenderServiceImpl.class);

    @Override
    public Boolean sendSMS(String num, String msg, HttpServletResponse response)
                    throws CGSystemException, CGBusinessException {
                LOGGER.trace("SMSSenderServiceImpl :: sendSMS() :: START");
                boolean isSentSMS = Boolean.FALSE;
                try {
                    String queryString = "senderID=%s&" + "msisdn=%s&" + "msg=%s&"
                                    + "userid=%s&" + "pwd=%s";
                    String query = String.format(queryString, URLEncoder.encode(
                                    SMSConstants.SENDER_ID, SMSConstants.CHARSET), URLEncoder
                                    .encode(num, SMSConstants.CHARSET), URLEncoder.encode(msg,
                                    SMSConstants.CHARSET), URLEncoder.encode(
                                    SMSConstants.USER_ID, SMSConstants.CHARSET), URLEncoder
                                    .encode(SMSConstants.PWD, SMSConstants.CHARSET));

                    /*
                     * String url = SMSConstants.HOST + "&senderID=" +
                     * SMSConstants.SENDER_ID + "&msisdn=" + num + "&msg=" + msg +
                     * "&userid=" + SMSConstants.USER_ID + "&pwd=" + SMSConstants.PWD;
                     */

                    HttpURLConnection urlCon = (HttpURLConnection)new URL(SMSConstants.HOST).openConnection();
                    urlCon.setDoOutput(true);
                    urlCon.setRequestMethod("POST");
                    urlCon.setRequestProperty("Accept-Charset", SMSConstants.CHARSET);
                    urlCon.setRequestProperty("Content-Type",
                                    "application/x-www-form-urlencoded;charset="
                                                    + SMSConstants.CHARSET);
                    
                    DataOutputStream wr = new DataOutputStream (
                                  urlCon.getOutputStream ());
                    wr.writeBytes (query);
                    wr.flush ();
                    wr.close ();
                    
                    int status = urlCon.getResponseCode();
                    LOGGER.debug("status is=="+status);
                    if(status == HttpStatus.SC_OK) {
                    	isSentSMS = Boolean.TRUE;
                                
                    } else {
                    	isSentSMS = Boolean.FALSE;
                    	throw new CGBusinessException(SMSConstants.ERROR_IN_SENDING_SMS);
                    }
                    
                } catch (IOException e) {
                    LOGGER.error("SMSSenderServiceImpl :: sendSMS() :: ", e);
                    throw new CGBusinessException(e);
                } 
                LOGGER.trace("SMSSenderServiceImpl :: sendSMS() :: END");
                return isSentSMS;
    }
    
    public static void main(String args[]) {
                SMSSenderServiceImpl obj = new SMSSenderServiceImpl();
                try {
                    obj.sendSMS("9820422254", "hi", null);
                } catch (CGSystemException | CGBusinessException e) {
                	LOGGER.error("SMSSenderServiceImpl :: sendSMS() :: ####testing code##### ERROR",e);
                }
    }

}
