package com.example.demo;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String path = request.getServletPath();
		System.out.println(path);
		List<String> excludedURLs = Arrays.asList("/generateToken", "/health");
		boolean isExcluded = excludedURLs.stream().anyMatch(url -> url.contains(path));
		if (isExcluded) {
			filterChain.doFilter(request, response);
		} else {
			String authHeader = request.getHeader("Authorization");
			if (authHeader == null || !authHeader.startsWith("Bearer")) {
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				response.getWriter().write("JWT Token not present in request header");
				return;
			} else {
				String jwtToken = authHeader.substring(7);
				Claims claims;
				try {
					claims = JwtUtil.extractUserName(jwtToken);
				} catch (Exception e) {
					response.setStatus(HttpStatus.BAD_REQUEST.value());
					response.getWriter().write("JWT Token not valid : "+e.getMessage());
					return;
				}
				System.out.println(claims); // {sub=anuj, exp=1680696741, iat=1680689541, authorities=[{authority=ROLE_USER}, {authority=ROLE_ADMIN}]}
				String username = claims.getSubject();
				List<Map<String, String>> authoritiesMap = (List<Map<String, String>>) claims.get("authorities");
				System.out.println(authoritiesMap); // [{authority=ROLE_USER}, {authority=ROLE_ADMIN}]
				
				// converting map to list
				List<SimpleGrantedAuthority> grantedAuthorities = authoritiesMap
						.stream().map((m) -> {
							String val = m.get("authority");
							return val;
						}).map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList());

				// no need to set password
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						username, null, grantedAuthorities);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				filterChain.doFilter(request, response);
			}
		}
		
	}

}
