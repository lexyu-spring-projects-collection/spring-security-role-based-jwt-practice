package com.lex.practice.utils;

import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * @author : Lex Yu
 */
@Component
public class RSAKeyProperties {
	private RSAPublicKey publicKey;
	private RSAPrivateKey privateKey;

	public RSAKeyProperties() {
		KeyPair pair = KeyGeneratorUtility.generateRsaKey();

		this.publicKey = (RSAPublicKey) pair.getPublic();
		this.privateKey = (RSAPrivateKey) pair.getPrivate();
	}

	public RSAPublicKey getPublicKey() {
		return publicKey;
	}

	public RSAPrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPublicKey(RSAPublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public void setPrivateKey(RSAPrivateKey privateKey) {
		this.privateKey = privateKey;
	}
}
