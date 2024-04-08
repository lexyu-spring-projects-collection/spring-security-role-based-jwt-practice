package com.lex.practice.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * @author : Lex Yu
 */
public class KeyGeneratorUtility {

	public static KeyPair generateRsaKey(){
		KeyPair keyPair = null;

		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(4096);
			keyPair = keyPairGenerator.generateKeyPair();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return keyPair;
	}
}
