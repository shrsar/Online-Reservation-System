package com.example.service.mvc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.beans.User;
import com.example.exception.mvc.login.UserNotFoundException;
import com.example.repository.mvc.IUserRepository;
import com.example.service.mvc.ILoginManagementService;
import com.example.service.mvc.IUserManagementService;
import com.example.util.CommonUtils;

@Service
public class LoginManagementServiceImpl implements ILoginManagementService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IUserManagementService userManagementService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User authenticateUser(
			String username,
			String plainPassword) {

		User userFromDB = userRepository.findByUsername(username);

		boolean isPasswordMatched = isPasswordHashMatching(plainPassword, userFromDB.getPassword());

		if (isPasswordMatched)
			return userFromDB;

		return null;
	}

	@Override
	public void storeUserSession(
			Integer userId,
			String sessionId) {

		User user = userManagementService.getUserById(userId);

		if (CommonUtils.isNotNull.test(user))
			userRepository.updateSessionIdByUserId(userId, sessionId);
		else {
			String userFriendlyMessage = "User Not found for the Id: " + userId;
			throw new UserNotFoundException(Thread.currentThread().getStackTrace(), userFriendlyMessage);
		}
	}

	@Override
	public User getUserbySessionId(String sessionId) {
		User user = userRepository.findBySessionId(sessionId);
		return user;
	}

	@Override
	public String hashPassword(String plainPassword) {

		return passwordEncoder.encode(plainPassword);
	}

	@Override
	public boolean isPasswordHashMatching(
			String plainPassword,
			String hashedPassword) {

		return passwordEncoder.matches(plainPassword, hashedPassword);
	}
}