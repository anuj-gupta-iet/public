package com.example.demo;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {

	private static final String secret = "anujsecret";

	public static String generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
		Map<String, Object> claims = new HashMap<>();
		// we are setting authorities as claims in jwt token itself so that we
		// dont have to go to DB each time and we get user and its roles from
		// token itself
		claims.put("authorities", authorities);
		Calendar cal = Calendar.getInstance();
		Date issueDt = cal.getTime();
		cal.add(Calendar.MINUTE, 120); // token expiry time 2 hrs
		Date expDt = cal.getTime();
		String token = Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(issueDt).setExpiration(expDt)
				.signWith(SignatureAlgorithm.HS256, secret).compact();
		return token;
	}

	public static Claims extractUserName(String token) {
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return claims;
	}

}
