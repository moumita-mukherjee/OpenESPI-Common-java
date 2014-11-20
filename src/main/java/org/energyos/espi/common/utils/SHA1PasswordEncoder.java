/*****************************************************************************
 *                                                                           *
 *                       Copyright (c) 2012-2013 London Hydro                *
 *                            ALL RIGHTS RESERVED                            *
 *                                                                           *
 *****************************************************************************
 *	File Name:	SHA1PasswordEncoder.java
 *
 *	Facility:	London Hydro Web App
 *
 *	Purpose:	This module contains a class implementation
 *
 *	Author:		vpinchevski, Affinity Systems
 *
 *  Revision History
 *
 *  	Date			Author			Description
 *	----------------	---------------------	--------------------
 *	Jun 26, 2013		vpinchevski		Original version
 *
 */
package org.energyos.espi.common.utils;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.authentication.encoding.BasePasswordEncoder;

public class SHA1PasswordEncoder extends BasePasswordEncoder {
	// The higher the number of iterations the more
	// expensive computing the hash is for us
	// and also for a brute force attack.
	private static final int iterations = 1024;
	private static final int desiredKeyLen = 256;

	@Override
	public String encodePassword(String rawPass, Object salt)
			throws RuntimeException {		
		if (rawPass != null && rawPass.equals(salt)) {
			return rawPass;
		}		
		String saltedPass = mergePasswordAndSalt(rawPass, salt, false);		
		try {
			return hash(rawPass, Base64.encodeBase64(saltedPass.getBytes()));
		} catch (Exception e) {
			throw new RuntimeException("Failed encoding the password due to", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.londonhydro.utils.toolbox.PasswordEncoder#isPasswordValid(java.lang
	 * .String, java.lang.String, java.lang.Object)
	 */
	@Override
	public boolean isPasswordValid(String encPass, String rawPass, Object salt)
			throws RuntimeException {
		String pass1 = "" + encPass;
		String pass2 = encodePassword(rawPass, salt);

		return pass1.equals(pass2);
	}

	private static String hash(String password, byte[] salt) throws Exception {
		if (password == null || password.length() == 0)
			throw new IllegalArgumentException(
					"Empty passwords are not supported.");
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		SecretKey key = f.generateSecret(new PBEKeySpec(password.toCharArray(),
				salt, iterations, desiredKeyLen));
		return new String(Base64.encodeBase64(key.getEncoded()));
	}

	public static void main(String[] args) {

		SHA1PasswordEncoder encoder = new SHA1PasswordEncoder();
		System.out.println(encoder.encodePassword("Tester1234", "calvin.lawrence@gmail.com"));
		java.security.SecureRandom rand = new java.security.SecureRandom();
		String salt = new String(rand.generateSeed(32));
		String enc = encoder.encodePassword("pass", salt);
		// Avoid adding dependency at runtime
		junit.framework.Assert.assertTrue(new SHA1PasswordEncoder()
				.isPasswordValid(enc, "pass", salt));
	}

}
