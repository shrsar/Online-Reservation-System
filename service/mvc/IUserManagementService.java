package com.example.service.mvc;

import com.example.beans.User;
import com.example.exception.mvc.login.UserNotFoundException;

public interface IUserManagementService {

	public User getUserById(Integer userId)
			throws UserNotFoundException;

	public User registerNewCustomer(User user);
}