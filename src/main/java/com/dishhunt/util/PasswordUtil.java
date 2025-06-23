package com.dishhunt.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {
	public static String hash(String password) {
		try {
			MessageDigest md  = MessageDigest.getInstance("SHA-256");
			byte[] hashedBytes = md.digest(password.getBytes());
			
			// Convert to hex string
			StringBuilder sb = new StringBuilder();
			for (byte b : hashedBytes) {
				sb.append(String.format("%02x",  b));
			}
			
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("SHA-256 algorithm not available", e);
		}
	}
	
	public static boolean verify(String plainPassword, String hashedPassword) {
		return hash(plainPassword).equals(hashedPassword);
	}
}
