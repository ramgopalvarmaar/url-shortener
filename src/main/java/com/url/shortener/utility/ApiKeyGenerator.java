package com.url.shortener.utility;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

public class ApiKeyGenerator {

	public static String generate(final int keyLen) {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(keyLen);
			SecretKey secretKey = keyGen.generateKey();
			byte[] encoded = secretKey.getEncoded();
			return DatatypeConverter.printHexBinary(encoded).toLowerCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
