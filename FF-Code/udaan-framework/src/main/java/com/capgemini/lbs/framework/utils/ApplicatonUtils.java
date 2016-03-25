/*
 * @author soagarwa
 */
package com.capgemini.lbs.framework.utils;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;

/**
 * The Class ApplicatonUtils.
 */
public abstract class ApplicatonUtils {

	/**
	 * Generate sub session id.
	 * 
	 * @param session
	 *            the session
	 * @return the string
	 */
	public static String generateSubSessionId(HttpSession session) {
		Enumeration<String> existingSubSessions = session.getAttributeNames();
		String lastSubSessionId;
		StringBuilder subSessionId = new StringBuilder();
		int contextCount = 1;
		while (existingSubSessions.hasMoreElements()) {
			lastSubSessionId = existingSubSessions.nextElement();
			if (lastSubSessionId.contains("conversationContext"))
				++contextCount;
		}
		subSessionId.append("conversationContext");
		subSessionId.append("-");

		subSessionId.append(contextCount);
		return subSessionId.toString();
	}

	/**
	 * @return
	 * @throws IOException
	 */
	@ Deprecated
	public static String getNodeId() throws IOException {

		return SplitModelUtil.getMacAddress();
		/*
		InetAddress address = InetAddress.getLocalHost();
		NetworkInterface ni = NetworkInterface.getByInetAddress(address);
		byte[] MAC_ADDRinByte = ni.getHardwareAddress();
		StringBuffer MAC_48_Format_HEX = new StringBuffer();

		// Convert from byte to HEX format
		String MAC_ADDRinHEX = Hex.encodeHexString(MAC_ADDRinByte);


		for (int i = 0; i < MAC_ADDRinHEX.length(); i = i + 2) {
			MAC_48_Format_HEX.append(MAC_ADDRinHEX.charAt(i));
			MAC_48_Format_HEX.append(MAC_ADDRinHEX.charAt(i + 1));
			MAC_48_Format_HEX.append((i < MAC_ADDRinHEX.length() - 2) ? "-"
					: "");
		}
		return MAC_48_Format_HEX.toString();*/
		
	}
	
	/**
	 * @return
	 */
	public static boolean isWindowsOS() {
		boolean isWindowsOs = false;
		String osName = System.getProperty("os.name");
		if(osName.toLowerCase().contains("windows")){
			isWindowsOs = true;
		}
		return isWindowsOs;
	}
	
	/**
	 * @param location
	 * @throws IOException
	 */
	public static void createFileLocationIfNotExist(String location) throws IOException {
		File file = new File(location);
		if(!file.exists()) {
			FileUtils.forceMkdir(file);
		} 
		file = null;
	}
}
