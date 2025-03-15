package com.example.controller;

import java.util.Base64;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.beans.User;
import com.example.constants.endpoints.UserProfileEndpoints;
import com.example.service.mvc.ILoginManagementService;

@Controller
@RequestMapping(UserProfileEndpoints.USER_PROFILE_BASE_URI)
public class UserProfileController {

	@Autowired
	private ILoginManagementService loginManagementService;

	@GetMapping(UserProfileEndpoints.VIEW_USER_PROFILE)
	public String showUserProfile(
			HttpSession session,
			Map<String, Object> model) {

		String sessionId = (String) session.getAttribute("sessionId");

		User user = null;
		if (sessionId != null) {

			user = loginManagementService.getUserbySessionId(sessionId);
		}

		if (Objects.nonNull(sessionId) && Objects.nonNull(user)) {
			model.put("user", user);
			byte[] imageByteArray = user.getUserProfile().getImage();

			if (Objects.nonNull(imageByteArray)) {
				model.put("userImage", Base64.getEncoder().encodeToString(imageByteArray));
			}

		}
		return "display_profile";
	}
}