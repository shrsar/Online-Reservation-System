package com.example.service.mvc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.beans.User;
import com.example.constants.UserRole;
import com.example.exception.mvc.login.UserNotFoundException;
import com.example.repository.mvc.IUserRepository;
import com.example.service.mvc.IUserManagementService;

@Service
public class UserManagementServiceImpl implements IUserManagementService {

	@Autowired
	private IUserRepository userRepository;

	@Override
	public User getUserById(Integer userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(
						Thread.currentThread().getStackTrace(),
						"User not found with ID: " + userId));
	}

	@Override
	public User registerNewCustomer(User user) {

		user.setRole(UserRole.CUSTOMER);
		User result = userRepository.save(user);
		return result;
	}
}