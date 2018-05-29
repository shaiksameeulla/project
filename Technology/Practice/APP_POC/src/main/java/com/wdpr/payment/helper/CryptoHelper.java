package com.wdpr.payment.helper;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;

public class CryptoHelper {
	private static KeyStore ppkeyStore;

	public static Map<String, String> encryptMessage(boolean useSymKey, String message, Key symmetricKey,
			String keystoretype, String keystore, String pwd, String encryptalias) throws Exception {
		HashMap result = new HashMap();
		if (useSymKey) {
			Key symkey = null;
			if (null == symmetricKey) {
				symkey = getSymmetricKey();
			} else {
				symkey = symmetricKey;
			}

			if (null == ppkeyStore) {
				ppkeyStore = loadKeyStore(keystoretype, keystore, pwd.toCharArray());
			}

			String encryptedMsg1 = encryptBySymKey(message.getBytes(StandardCharsets.UTF_8), symkey);
			String hexaSymString = getHexEncodedEncryptedSymmetricKey(symkey, ppkeyStore, encryptalias);
			result.put("hexaSymmetricKey", hexaSymString);//encrypting symmetric key with APP Public key
			result.put("EncryptedMessage", encryptedMsg1);
		} else {
			String encryptedMsg2 = encryptByPublicKey(message.getBytes(StandardCharsets.UTF_8), ppkeyStore,
					encryptalias);
			result.put("EncryptedMessage", encryptedMsg2);
		}

		return result;
	}

	public static Key getSymmetricKey() throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("DES");
		boolean keySize = true;
		kgen.init(56);
		SecretKey key = kgen.generateKey();
		byte[] aesKey = key.getEncoded();
		SecretKeySpec desKeySpec = new SecretKeySpec(aesKey, "DES");
		return desKeySpec;
	}

	private static String encryptBySymKey(byte[] jsonBytes, Key symKey) throws Exception {
		Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		desCipher.init(1, symKey);
		byte[] encryptedMessageBytes = desCipher.doFinal(jsonBytes);
		return getHexEncodedString(encryptedMessageBytes);
	}

	private static String encryptByPublicKey(byte[] jsonBytes, KeyStore truststore, String alias) throws Exception {
		PublicKey publicKey = getPublicKey(truststore, alias);
		Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		rsaCipher.init(1, publicKey);
		byte[] encryptedMessageBytes = rsaCipher.doFinal(jsonBytes);
		return getHexEncodedString(encryptedMessageBytes);
	}

	private static String getHexEncodedString(byte[] bytes) throws Exception {
		String hexString = null;
		hexString = Hex.encodeHexString(bytes);
		return hexString;
	}

	public static PublicKey getPublicKey(KeyStore truststore, String alias) throws Exception {
		PublicKey publicKey = null;
		if (null != truststore) {
			Certificate cert = truststore.getCertificate(alias);
			if (null != cert) {
				publicKey = cert.getPublicKey();
			}
		}

		return publicKey;
	}

	private static String getHexEncodedEncryptedSymmetricKey(Key symKey, KeyStore truststore, String alias)
			throws Exception {
		PublicKey publicKey = getPublicKey(truststore, alias);
		Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		rsaCipher.init(1, publicKey);
		byte[] symmKeyBytes = symKey.getEncoded();
		byte[] encryptedKey = rsaCipher.doFinal(symmKeyBytes);
		String hexSymmKey = getHexEncodedString(encryptedKey);
		return hexSymmKey;
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
}