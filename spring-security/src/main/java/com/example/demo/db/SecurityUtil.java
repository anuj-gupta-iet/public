package com.example.demo.db;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

	public static String getLoggedinUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println(principal);
		String name = null;
		if (principal instanceof MyUserDetails) {
			System.out.println("Inside if");
			name = ((MyUserDetails) principal).getName();
		} else {
			System.out.println("Inside else");
			name = principal.toString();
		}
		return name;
	}

	public static Boolean isLoggedinUserPrivileged() {
		Boolean isLoggedinUserPrivileged = false;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
		if (principal instanceof MyUserDetails) {
			authorities = ((MyUserDetails) principal).getAuthorities();
			System.out.println("-------");
			for (GrantedAuthority authority : authorities) {
				if (authority.getAuthority().equals("ROLE_USER_PRIVILEGED")) {
					isLoggedinUserPrivileged = true;
				}
			}
			System.out.println("-------");
		}
		return isLoggedinUserPrivileged;
	}

}
