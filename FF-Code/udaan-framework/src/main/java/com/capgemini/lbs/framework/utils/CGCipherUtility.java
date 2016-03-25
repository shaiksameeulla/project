/**
 * 
 */
package com.capgemini.lbs.framework.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author mohammes
 * Dependencis :: jasypt-1.9.2.jar 
 *
 */
public final class CGCipherUtility {
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CGCipherUtility.class);
	
	static String saltPassword=null;
	static{
		saltPassword="UDAAN";
	}
	
	static String getStrandardEncryptedString(String inputPassword, String saltPassword){
		//passwordEncryptor.setAlgorithm("PBEWithMD5AndTripleDES");
		StandardPBEStringEncryptor passwordEncryptor = new StandardPBEStringEncryptor();
		passwordEncryptor.setPassword(saltPassword);
		return  passwordEncryptor.encrypt(inputPassword);
	}

	static String getStrandardDecryptedString(String encryptedMessage, String saltPassword){
		//passwordEncryptor.setAlgorithm("PBEWithMD5AndTripleDES");
		StandardPBEStringEncryptor passwordEncryptor = new StandardPBEStringEncryptor();
		passwordEncryptor.setPassword(saltPassword);
		return  passwordEncryptor.decrypt(encryptedMessage);
	}
	
	static String getBasicEncryptedString(String inputPassword){
		BasicPasswordEncryptor basicPasswordEncryptor = new BasicPasswordEncryptor();
		return  basicPasswordEncryptor.encryptPassword(inputPassword);
	}

	static String getBasicDecryptedString(String encryptedMessage){
		BasicPasswordEncryptor basicPasswordEncryptor = new BasicPasswordEncryptor();
		return  basicPasswordEncryptor.encryptPassword(encryptedMessage);
	}
	
	//below code is for testing purpose
	
	public static void main(String s[]){
		String inputPassword="password123";
		String encryptedpassword=getStrandardEncryptedString(inputPassword, saltPassword);
		LOGGER.debug("encryptedpassword : "+encryptedpassword);
		String decryptedPassword=getStrandardDecryptedString(encryptedpassword, saltPassword);
		LOGGER.debug("decryptedPassword : "+decryptedPassword);
		
		//result : pYZr6vchwD992IjYUrRePRb+nxNBObOE
	}
}
