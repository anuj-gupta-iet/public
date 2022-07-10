package com.example.demo;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

	public static String getLoggedinUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println(principal);
		return principal.toString();
		/*String name = null;
		if (principal instanceof MyUserDetails) {
			return ((MyUserDetails) principal);
		} else {
			
			return null;
		}*/
	}

}
