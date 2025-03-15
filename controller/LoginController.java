package com.example.controller;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.beans.User;
import com.example.constants.UserRole;
import com.example.constants.endpoints.LoginEndpoints;
import com.example.service.mvc.ILoginManagementService;

@Controller
public class LoginController {

	@Autowired
	private ILoginManagementService loginManagementService;

	@GetMapping(value = { LoginEndpoints.LOGIN, "/" })
	public String showLoginPage() {
		return "login";
	}

	@PostMapping(LoginEndpoints.LOGIN)
	public String login(
			@RequestParam String username,
			@RequestParam String password,
			HttpSession session,
			Map<String, Object> model) {

		User user = loginManagementService.authenticateUser(username, password);

		System.out.println("LoginController.login()");

		if (user != null) {

			String sessionId = UUID.randomUUID().toString();
			session.setAttribute("sessionId", sessionId);
			session.setAttribute("user", user);

			session.setMaxInactiveInterval(10 * 60);

			loginManagementService.storeUserSession(user.getUserId(), sessionId);

			return "redirect:/home";
		} else {
			model.put("loginFailedMessage", "Login Failed, Invalid username or Password");
			return "/login";
		}
	}

	@GetMapping(LoginEndpoints.SHOW_HOME)
	public String showHome(HttpSession session) {
		String sessionId = (String) session.getAttribute("sessionId");
		System.out.println(sessionId);

		User user = null;

		if (sessionId != null) {
			user = loginManagementService.getUserbySessionId(sessionId);
		}

		if (sessionId != null && user != null) {
			if (user.getRole() == UserRole.ADMIN) {
				return "admin/admin_home";
			} else if (user.getRole() == UserRole.CUSTOMER) {
				return "user/user_home";
			}
		}
		return "redirect:/login";
	}

	@RequestMapping(LoginEndpoints.LOGOUT)
	public String logout(
			HttpSession session,
			Map<String,
					Object> model) {

		model.put("logoutMessage", "Logout Success, Login again below if needed");

		session.invalidate();

		return "login";
	}
}