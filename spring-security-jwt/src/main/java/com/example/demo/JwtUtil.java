package com.example.demo;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {

	private static final String secret = "anujsecret";

	public static String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		Calendar cal = Calendar.getInstance();
		Date issueDt = cal.getTime();
		cal.add(Calendar.MINUTE, 120);
		Date expDt = cal.getTime();
		String token = Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(issueDt).setExpiration(expDt)
				.signWith(SignatureAlgorithm.HS256, secret).compact();
		return token;
	}

	public static Claims extractUserName(String token) {
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return claims;
	}

	public static List<String> getExcludedURLs() {
		return Arrays.asList("/health", "/generateToken", "/h2-console");
	}
}
