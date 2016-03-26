package com.ff.umc.service;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.ff.umc.constants.UmcConstants;

public class PasswordUtil  {

	private static Random rgen = new Random();
	private static byte decision, numValue;
	private static char charValue;
	//private Properties passwordPolicy;
	private static final int PASS_LENGTH = 8;
	//private ManageUserDAO userDo;
	private final static Logger LOGGER = Logger.getLogger(PasswordUtil.class);

	/*public void setPasswordPolicy(Properties passwordPolicy) {
		this.passwordPolicy = passwordPolicy;
	}*/
	
	public static String generatePassword(String passlengthStr) {
		LOGGER.debug("PasswordGeneratorServiceImpl::generatePassword::Start=======>");
		int length = PASS_LENGTH;
		//String passlengthStr = //passwordPolicy.getProperty("password.length");
		if (passlengthStr != null && !passlengthStr.isEmpty()) {
			length = Integer.parseInt(passlengthStr);
			}
		StringBuilder sb = new StringBuilder();
		while (sb.length() < length) {
			decision = (byte) rgen.nextInt(2);
			numValue = (byte) rgen.nextInt(10);
			charValue = (char) (rgen.nextInt(25) + 65);
			sb.append((decision % 2 == 0) ? (charValue + "") : (numValue + ""));
		}
		LOGGER.debug("PasswordGeneratorServiceImpl::generatePassword::end=======>");
		return sb.toString();
	}
	
	
	public static String getValidPassword() {
		LOGGER.debug("PasswordGeneratorServiceImpl::getValidPassword::Start=======>");
		String password = null;
		while (true) {
			password = generatePassword("8");
			if (isValidPassword(password,""))
				break;
		}
		LOGGER.debug("PasswordGeneratorServiceImpl::getValidPassword::end=======>");
		return password;
	}
	
	public static boolean isValidPassword(String password, String invalidpassword) {
		LOGGER.debug("PasswordGeneratorServiceImpl::isValidPassword::Start=======>");
		boolean isValid = true;
	/*	String invalidpassword = passwordPolicy
				.getProperty("password.invalid.string");*/
		if (invalidpassword != null && !invalidpassword.isEmpty()) {
			String[] invalidpass = invalidpassword.split(",");
			for (String invalidStr : invalidpass) {
				if (password.contains(invalidStr)) {
					isValid = false;
				}
			}
		}
		LOGGER.debug("PasswordGeneratorServiceImpl::isValidPassword::end=======>");
		return isValid;
	}
	

	public static String getSHAEncryptedPassword(String password)throws CGBusinessException {
		MessageDigest md = null;
		try {
			LOGGER.debug("PasswordGeneratorServiceImpl::getSHAEncryptedPassword::Start=======>");
			md = MessageDigest.getInstance(UmcConstants.SHA);
			md.reset();
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("PasswordGeneratorServiceImpl::getSHAEncryptedPassword::error: " + e.getMessage());
			throw new CGBusinessException(e);
		}
		try {
			byte[] theTextToDigestAsBytes = password
					.getBytes(UmcConstants.UTF_8);/* encoding */
			md.update(theTextToDigestAsBytes);// md.update( int ) processes only
												// the low order 8-bits.
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("PasswordGeneratorServiceImpl::getSHAEncryptedPassword::error: " + e.getMessage());
			throw new CGBusinessException(e);
		}
		byte[] digest = md.digest();
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < digest.length; i++) {
			String hex = Integer.toHexString(0xFF & digest[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		LOGGER.debug("PasswordGeneratorServiceImpl::getSHAEncryptedPassword::end=======>");
		return hexString.toString();
		}
	}
