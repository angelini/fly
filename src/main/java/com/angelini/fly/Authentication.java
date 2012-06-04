package com.angelini.fly;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Authentication {
	
	private String salt;
	
	private static Logger log = LoggerFactory.getLogger(Authentication.class);
	
	public Authentication() {
		try {
			SecureRandom random = new SecureRandom();
			salt = new BigInteger(130, random).toString(32);
			
		} catch (Exception e) {
			log.error("Error creating Authentication object", e);
			throw new RuntimeException();
		}
	}
	
	public String verifySignature(HttpServletRequest req) {
		String authString = null;
		String authSigned = null;
		Cookie[] cookies = req.getCookies();
		
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(Constants.AUTH_COOKIE)) {
				authString = cookie.getValue();
			}
			
			if (cookie.getName().equals(Constants.AUTH_SIG_COOKIE)) {
				authSigned = cookie.getValue();
			}
		}
		
		if (authString == null || authSigned == null) {
			return null;
		}
		
		if (verifySignature(authString, authSigned)) {
			return authString;
		}
		
		return null;
	}
	
	public boolean verifySignature(String val, String signed) {
		if (signValue(val).equals(signed)) {
			return true;
		}
		
		return false;
	}
	
	public String signValue(String val) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			String combined = val + salt;
			
			md.update(combined.getBytes("UTF8"));
			byte[] hash = md.digest();
			
			return Base64.encodeBase64URLSafeString(hash);
			
		} catch (Exception e) {
			log.error("Exception signing value", e);
			throw new RuntimeException();
		}
	}

}
