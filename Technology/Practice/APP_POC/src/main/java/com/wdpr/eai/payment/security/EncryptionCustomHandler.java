package com.wdpr.eai.payment.security;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import com.wdpr.eai.payment.util.JSONConverter;
import com.wdpr.payment.helper.CryptoHelper;
import com.wdpr.payment.helper.PropertiesUtil;

import dpr.disney.com.adaptivepayment.common.CardDescriptorType;

public class EncryptionCustomHandler {
	
	private final static String SYMM_TRANSFORMATION = "DES/ECB/PKCS5Padding";
    private final static String ASYMM_TRANSFORMATION = "RSA/ECB/PKCS1Padding";
    // Payment's public key?
    private final static String DES = "DES";
    private final static String RSA = "RSA";
    private final static String AES = "AES";
    private static Cipher aesCipher;
    private static Cipher rsaCipher;
    private static Key aesKeySpec;
    private final static String PVT_ALGORITHM = "SHA256withRSA";
    private final static String CERT_TYPE = "X.509";
    private final static String BYTES_CHARSET = StandardCharsets.UTF_8.name();
    
    
    private static Properties appProperties = null;
	
	public String encryptCardDescriptor(CardDescriptorType infoToEncrypt) {
		
		String encryptedCardDescriptor = null;
		encryptedCardDescriptor = getHexEncodedEncryptedCardDescriptor(true, infoToEncrypt);
		return encryptedCardDescriptor;
	}
	
	// get the key only once
    private static Key getSymmetricKey(final String algType) throws NoSuchAlgorithmException {
        if (null == aesKeySpec) {
            if (algType.equals(AES)) {
                final KeyGenerator kgen = KeyGenerator.getInstance(AES);
                final int keySize = 128;
                kgen.init(keySize);
                final SecretKey key = kgen.generateKey();
                final byte[] aesKey = key.getEncoded();
                aesKeySpec = new SecretKeySpec(aesKey, AES);
            } else if (algType.equals(DES)) {
                final KeyGenerator kgen = KeyGenerator.getInstance(DES);
                final int keySize = 56;
                kgen.init(keySize);
                final SecretKey key = kgen.generateKey();
                final byte[] aesKey = key.getEncoded();
                aesKeySpec = new SecretKeySpec(aesKey, DES);
            }
        }
        return aesKeySpec;
    }
    
    public static String getHexEncodedEncryptedSymmetricKey() {
        
    	try {
	    	// get public key from certificate
	    	final PublicKey publicKey = getPublicKeyfromKeyStore();
	
	        //aad5 - Brian told me they are only using DES so if they change
	        //the type then this needs to change
	        aesKeySpec = getSymmetricKey(DES);
	        
	        // encrypt symmetric key
	        rsaCipher = Cipher.getInstance(ASYMM_TRANSFORMATION);
	        
	        rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
	        final byte[] symmKeyBytes = aesKeySpec.getEncoded();
	        final byte[] encryptedKey = rsaCipher.doFinal(symmKeyBytes);
	        final String hexSymmKey = getHexEncodedString(encryptedKey);
	
	        return hexSymmKey;
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	return null;
    }
    
    /**
     * get the encrypted card descriptor
     *
     * @return
     */
    public static String getHexEncodedEncryptedCardDescriptor(final boolean useSymmetricKey,
            final CardDescriptorType cardDescriptor)  {

    	try {
	        if (useSymmetricKey) {
	            // encrypt card info
	            aesCipher = Cipher.getInstance(SYMM_TRANSFORMATION);
	            aesCipher.init(Cipher.ENCRYPT_MODE, getSymmetricKey(DES));
	            final byte[] encryptedCardInfo = aesCipher.doFinal(JSONConverter.convertJavaToJSON(cardDescriptor).getBytes(
	                    BYTES_CHARSET));
	            final String hexCardInfo = getHexEncodedString(encryptedCardInfo);
	            //final String hexCardInfo = encryptedCardInfo.toString();
	            
	            return hexCardInfo;
	        }
	        //final PublicKey publicKey = getPublicKey(PaymentPropertyReader.getPublicKeyFile(msgHdr), null);
	        final PublicKey publicKey = getPublicKeyfromKeyStore();
	        //rsaCipher = Cipher.getInstance(ASYMM_TRANSFORMATION);
	        rsaCipher = Cipher.getInstance(ASYMM_TRANSFORMATION);
	        rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
	        final byte[] encryptedCardInfo = rsaCipher.doFinal(JSONConverter.convertJavaToJSON(cardDescriptor).getBytes());
	        final String hexCardInfo = getHexEncodedString(encryptedCardInfo);
	        
	
	        return hexCardInfo;
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    return null;
    }
    
    
    /**
     * get the encrypted card number
     *
     * @return
     */
    public static String getHexEncodedEncryptedCardNumber(final boolean useSymmetricKey,
            final String cardNumber){
    	try {
	        if (useSymmetricKey) {
	            // encrypt card info
	            aesCipher = Cipher.getInstance(SYMM_TRANSFORMATION);
	            aesCipher.init(Cipher.ENCRYPT_MODE, getSymmetricKey(DES));
	            final byte[] encryptedCardInfo = aesCipher.doFinal(cardNumber.getBytes(
	                    BYTES_CHARSET));
	            return getHexEncodedString(encryptedCardInfo);
	        }
	        final PublicKey publicKey = getPublicKeyfromKeyStore();
	        rsaCipher = Cipher.getInstance(ASYMM_TRANSFORMATION);
	        rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
	        final byte[] encryptedCardInfo = rsaCipher.doFinal(cardNumber.getBytes());
	        return getHexEncodedString(encryptedCardInfo);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	return null;
    }
    
    /**
     * hex encoding operations
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static String getHexEncodedString(final String str) {
    	String encodedString = null;
        try {
			encodedString = getHexEncodedString(str.getBytes(BYTES_CHARSET));
		} catch (UnsupportedEncodingException e) {
    		e.printStackTrace();
		}
        return encodedString;
    }

    public static String getHexEncodedString(final byte[] bytes) {
        String hexString = null;
        char[] encChar = Hex.encodeHex(bytes);
        hexString = new String(encChar);
        return hexString;
    }
    
    public static byte[] getEncryptedSignature(String request){
		try {
			KeyStore keyStore=null;
			 PrivateKey privateKey=null;
			/*KeyStore keyStore = EncryptionHandler.loadKeyStore(".jks",
					PaymentPropertyReader.getKeystoreFile(msgHdr), 
					PaymentPropertyReader.getKeystorePWD(msgHdr).toCharArray());
	        PrivateKey privateKey = EncryptionHandler.getPrivateKey(keyStore, PaymentPropertyReader.getPvtKeyAlias(msgHdr),
	        		PaymentPropertyReader.getKeystorePWD(msgHdr));*/
			// EncryptionCustomHandler.getPrivateKey(keyStore, alias, password)
	        // create signature with private key
	        Signature dsa = Signature.getInstance(PVT_ALGORITHM);
	        dsa.initSign(privateKey);
	        
	        dsa.update(request.getBytes());
	        byte[] bytes = dsa.sign();

	        validate( bytes, request);
	        
	        return bytes;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
    
    
    static boolean validate (byte[] privateSig, String msg) 
    {
    	boolean isValid = false;
    	try {
	    	PublicKey pubKey = getPublicKeyfromKeyStore();
	        final Signature sig = Signature.getInstance("SHA256withRSA");
	     
	        sig.initVerify(pubKey);
	        sig.update(msg.getBytes());
	
	        // validate that the signatures match
	        isValid = sig.verify(privateSig);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	
    	return isValid;
    }
    

    /**
     * get private key from keystore used for creating signature
     *
     * @param keyStore
     * @param alias
     * @param password
     * @return
     */
    public static PrivateKey getPrivateKey(final KeyStore keyStore, final String alias, final String password) {
        PrivateKey pKey = null;
        try {
	        final KeyStore.ProtectionParameter protParam = new KeyStore.PasswordProtection(password.toCharArray());
	        final KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias, protParam);
	        pKey = pkEntry.getPrivateKey();
        } catch (Exception ex) {
    		ex.printStackTrace();
    	}
        return pKey;
    }

    
    /**
     * get private key from keystore used for creating signature
     *
     * @param keyStore
     * @param alias
     * @param password
     * @return
     */
    public static PublicKey getTempPublicKey(final KeyStore keyStore, final String alias, final String password) {
        Certificate cert = null;
        try {
        	cert = keyStore.getCertificate(alias);
        } catch (Exception ex) {
    		ex.printStackTrace();
    	}
        return cert.getPublicKey();
    }
    
  
    /**
     * get private key from keystore used for creating signature
     *
     * @param keyStore
     * @param alias
     * @param password
     * @return
     */
    public static PublicKey getPublicKeyfromKeyStore() {
    	
    	PublicKey publicKey = null;
        try {
        	String ppKeystore = getPropertyValue("pp.keystore.file");
    		String pwd = getPropertyValue("pp.keystore.password");
    		String encryptAlias = getPropertyValue("pp.keystore.alias");
    		String keystoretype = getPropertyValue("pp.keystore.type");
        	
        	appProperties  = PropertiesUtil.loadProperties(null);
        	publicKey= CryptoHelper.getPublicKey(CryptoHelper.loadKeyStore(keystoretype,ppKeystore,pwd.toCharArray()), encryptAlias);
        	return publicKey;
        } catch (Exception ex) {
    		ex.printStackTrace();
    	}
    return null;
    }
    
    
    public static String getPropertyValue(String key) {
		if(appProperties == null) {
			appProperties  = PropertiesUtil.loadProperties(null);
		}
		return appProperties.getProperty(key, (String) null);
	}
    
    
    
    
    	
   
    
    public static String getSymmetricKeyTransformation() {
        return SYMM_TRANSFORMATION;
    }

    public static String getAsymmetricTransformation() {
        return ASYMM_TRANSFORMATION;
    }

    public static String getSymmetricKeyAlgorithm() {
        return DES;
    }

    public static String getAsymmetricAlgorithm() {
        return RSA;
    }

}
