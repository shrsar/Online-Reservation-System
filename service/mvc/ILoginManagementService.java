package com.example.service.mvc;

import com.example.beans.User;
import com.example.exception.mvc.login.UserNotFoundException;

public interface ILoginManagementService {

	public User authenticateUser(String username, String plainPassword);

	public void storeUserSession(
			Integer userId,
			String sessionId) throws UserNotFoundException;

	public User getUserbySessionId(String sessionId);

	public String hashPassword(String plainPassword);

	public boolean isPasswordHashMatching(
			String plainPassword,
			String hashedPassword);
}