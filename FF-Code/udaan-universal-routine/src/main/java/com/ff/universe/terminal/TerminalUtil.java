package com.ff.universe.terminal;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

public class TerminalUtil {
	private static final Logger LOGGER = Logger.getLogger(TerminalUtil.class);
	
	public static String getWeighingMachineReading(String resourceUrl) {
		LOGGER.debug("TerminalUtil::getWeighingMachineReading::start====>");
		return getTerminalReading(resourceUrl);
	}
	
	public static String getTerminalMacAddress(String resourceUrl) {
		LOGGER.debug("TerminalUtil::getTerminalMacAddress::=====>");
		return getTerminalReading(resourceUrl);
	}
	
	private static String getTerminalReading(String resulrceUrl) {
		String response = null;
		URLConnection conn = null;
		DataInputStream in = null;
		try {
			URL url = new URL(resulrceUrl);
			LOGGER.debug("TerminalUtil::getTerminalReading::requesting for terminal at url [" + url + "]");
			conn = url.openConnection();
			conn.setConnectTimeout(2000); //milisecs
			in = new DataInputStream(conn.getInputStream());
			response = in.readLine();
			LOGGER.debug("TerminalUtil::getTerminalReading::received response from terminal machine [" + response + "]");
		} catch (MalformedURLException e) {
			response = "-1";
			LOGGER.error("TerminalUtil::getTerminalReading::MalformedURLException::" + e.getMessage());
		} catch (IOException e) {
			response = "-1";
			LOGGER.error("TerminalUtil::getTerminalReading::IOException::" + e.getMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				}catch (MalformedURLException e) {
					LOGGER.error("TerminalUtil::getTerminalReading::MalformedURLException in connection closed::",e);	
				}catch (IOException e) {
					LOGGER.error("TerminalUtil::getTerminalReading::MalformedURLException in connection closed::",e);	
				}
				
			}

		}
		return response;
	}
}
