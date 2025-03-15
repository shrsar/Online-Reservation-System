package com.example.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.beans.User;
import com.example.beans.UserProfile;
import com.example.constants.UserRole;
import com.example.service.mvc.ILoginManagementService;

@Component
public class UserUtils {

	@Autowired
	private ILoginManagementService loginManagementService;

	public static User createUser(
			String username,
			String password,
			String firstName,
			String lastName,
			String address,
			String phoneNumber,
			byte[] imageBytes) {

		User user = new User();
		user.setUsername(username);
		user.setPassword(password);

		UserProfile userProfile = new UserProfile();
		userProfile.setFirstName(firstName);
		userProfile.setLastName(lastName);
		userProfile.setAddress(address);
		userProfile.setPhoneNumber(phoneNumber);

		if (imageBytes != null) {
			userProfile.setImage(imageBytes);
		}

		user.setUserProfile(userProfile);

		return user;
	}

	public UserRole getUserRoleBySessionId(String sessionId) {

		User user = null;
		UserRole userRole = null;

		if (sessionId != null) {
			user = loginManagementService.getUserbySessionId(sessionId);
		}

		if (user != null) {
			if (user.getRole() == UserRole.ADMIN)
				userRole = UserRole.ADMIN;
			else if (user.getRole() == UserRole.CUSTOMER)
				userRole = UserRole.CUSTOMER;
		}
		return userRole;
	}
}