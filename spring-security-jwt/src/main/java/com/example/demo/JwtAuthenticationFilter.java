package com.example.demo;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("inside JwtAuthenticationFilter " + SecurityContextHolder.getContext().getAuthentication());
		String path = request.getServletPath();
		System.out.println(path);
		List<String> excludedURLs = JwtUtil.getExcludedURLs();
		System.out.println(excludedURLs);
		boolean isExcluded = excludedURLs.stream().anyMatch(url -> url.contains(path));
		System.out.println(isExcluded);
		String skipJwtHeader = request.getHeader("SkipJwt");
		if (isExcluded || "Y".equals(skipJwtHeader)) {
			System.out.println("JWT Auth Filter excluded for url:" + path);
			filterChain.doFilter(request, response);
		} else {
			System.out.println("inside else");
			String authHeader = request.getHeader("Authorization");
			System.out.println(authHeader);
			if (authHeader != null && authHeader.startsWith("Bearer")) {
				System.out.println("JwtAuthenticationFilter inside if");
				String token = authHeader.substring(7);
				System.out.println(token);
				Claims c = JwtUtil.extractUserName(token);
				System.out.println(c);
				String username = c.getSubject();
				System.out.println(username);
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				System.out.println(userDetails);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				request.setAttribute("userDetails", userDetails);
				filterChain.doFilter(request, response);
			}
		}
		
		
	}

}
