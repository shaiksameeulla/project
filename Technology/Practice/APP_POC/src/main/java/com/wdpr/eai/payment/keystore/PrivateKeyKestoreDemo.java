package com.wdpr.eai.payment.keystore;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Properties;

import org.apache.commons.codec.binary.Hex;

import com.wdpr.payment.helper.PropertiesUtil;

/**
 * 
 */

/**
 * @author mohammes
 *
 */
public class PrivateKeyKestoreDemo {

	private static KeyStore ppkeyStore;
	
	private static Properties props;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		props = PropertiesUtil.loadProperties(null);
		try {
			ppkeyStore=loadKeyStore(props.getProperty("client.keystore.type"),
					props.getProperty("client.keystore.file"),
					props.getProperty("client.keystore.password").toCharArray());
			
			String encryptedStr = createSignature("Shaik Sameeulla", props);
			System.out.println(encryptedStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	public static KeyStore loadKeyStore(String keyStoreType, String fileName, char[] password) throws Exception {
		KeyStore keyStore = null;
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);

		try {
			keyStore = KeyStore.getInstance(keyStoreType);
			if (null == keyStore) {
				throw new Exception("No Truststore found at " + fileName);
			}

			keyStore.load(is, password);
		} finally {
			if (null != is) {
				is.close();
			}

		}

		return keyStore;
	}
	

	private static PrivateKey privateKey;

	public static String createSignature(String msg, Properties props) throws Exception {
		if (null == privateKey) {
			privateKey = getPrivateKey(ppkeyStore, props.getProperty("client.keystore.alias"),
					props.getProperty("client.keystore.password"));
		}

		Signature dsa1 = Signature.getInstance("SHA256withRSA");
		dsa1.initSign(privateKey);
		dsa1.update(msg.getBytes(StandardCharsets.UTF_8));
		byte[] bytes = dsa1.sign();
		return getHexEncodedString(bytes);
	}

	private static PrivateKey getPrivateKey(KeyStore keyStore, String alias, String password) throws Exception {
		PasswordProtection protParam = new PasswordProtection(password.toCharArray());
		PrivateKeyEntry pkEntry = (PrivateKeyEntry) keyStore.getEntry(alias, protParam);
		return pkEntry.getPrivateKey();
	}

	private static String getHexEncodedString(byte[] bytes) throws Exception {
		String hexString = null;
		hexString = Hex.encodeHexString(bytes);
		return hexString;
	}


}
