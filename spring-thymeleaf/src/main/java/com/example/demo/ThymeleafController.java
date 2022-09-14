package com.example.demo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.jsonwebtoken.Claims;

@Controller
public class ThymeleafController {

	@RequestMapping(value = { "/login", "/" }, method = RequestMethod.GET)
	public String getLoginPage() {
		return "login";
	}

	@RequestMapping(value = { "/login" }, method = RequestMethod.POST)
	public String login(@ModelAttribute(name = "loginForm") LoginForm loginForm, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		String username = loginForm.getUsername();
		String password = loginForm.getPassword();

		System.out.println(username + "::" + password);

		if (password.equals(JwtUtil.userMap.get(username).getPassword())) {
			String token = JwtUtil.generateToken(username);
			Cookie cookie = new Cookie("AuthCookie", token);
			response.addCookie(cookie);
			model.addAttribute("username", username);
			return "home";
		} else {
			model.addAttribute("invalidCredentials", true);
			return "login";
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAdminData", method = RequestMethod.GET)
	public String getAdminData(Model model, HttpServletRequest request) {
		String adminMessage = "";
		try {
			Optional<Cookie> authCookie = Arrays.stream(request.getCookies())
					.filter(cookie -> "AuthCookie".equals(cookie.getName())).findFirst();
			if (authCookie.isPresent()) {
				String token = authCookie.get().getValue();
				String username = JwtUtil.extractClaims(token).getSubject();
				System.out.println(username);
				Claims claims = JwtUtil.extractClaims(token);
				System.out.println(claims);
				List<String> roles = (List<String>) claims.get("roles");
				System.out.println(roles);
				if(roles.contains("ADMIN")) {
					adminMessage = "Admin Data from Server";
					model.addAttribute("adminMessage", adminMessage);
					return "home";
				}else {
					adminMessage = "You don't have Admin role";
					model.addAttribute("adminMessage", adminMessage);
					return "home";
				}
			}
			return "home";
		} catch (Exception e) {
			adminMessage = "Token Expired";
			model.addAttribute("adminMessage", adminMessage);
			return "home";
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getUserData", method = RequestMethod.GET)
	public String getUserData(Model model, HttpServletRequest request) {
		String userMessage = "";
		try {
			Optional<Cookie> authCookie = Arrays.stream(request.getCookies())
					.filter(cookie -> "AuthCookie".equals(cookie.getName())).findFirst();
			if (authCookie.isPresent()) {
				String token = authCookie.get().getValue();
				String username = JwtUtil.extractClaims(token).getSubject();
				System.out.println(username);
				Claims claims = JwtUtil.extractClaims(token);
				System.out.println(claims);
				List<String> roles = (List<String>) claims.get("roles");
				System.out.println(roles);
				if(roles.contains("USER")) {
					userMessage = "User Data from Server";
					model.addAttribute("userMessage", userMessage);
					return "home";
				}else {
					userMessage = "You don't have User role";
					model.addAttribute("userMessage", userMessage);
					return "home";
				}
			}
			return "home";
		} catch (Exception e) {
			userMessage = "Token Expired";
			model.addAttribute("userMessage", userMessage);
			return "home";
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String getLogoutPage(HttpServletResponse response) {
		System.out.println("getLogoutPage");
		Cookie cookie = new Cookie("AuthCookie", "");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return "login";
	}

}
