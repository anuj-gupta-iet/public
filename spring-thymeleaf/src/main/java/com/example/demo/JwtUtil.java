package com.example.demo;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	private static final String secret = "anujsecret";
	
	public static Map<String, LoginForm> userMap;
	
	static {
		List<LoginForm> users = Arrays.asList(
			new LoginForm("anuj-gupta-iet", "Welcome@1234", Arrays.asList("ADMIN", "USER")),
			new LoginForm("papag", "papag", Arrays.asList("USER"))
		);
		userMap = users.stream().collect(Collectors.toMap(LoginForm::getUsername, Function.identity()));
	}

	public static String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("roles", userMap.get(username).getRoles());
		Calendar cal = Calendar.getInstance();
		Date issueDt = cal.getTime();
		cal.add(Calendar.MINUTE, 1);
		Date expDt = cal.getTime();
		String token = Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(issueDt)
				.setExpiration(expDt)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
		return token;
	}

	public static Claims extractClaims(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody();
		return claims;
	}

	public List<String> getExcludedURLs() {
		return Arrays.asList("/health", "/generateToken", "/h2-console");
	}
}
