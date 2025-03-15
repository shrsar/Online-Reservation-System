package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.beans.User;
import com.example.beans.UserRegistrationDTO;
import com.example.constants.endpoints.RegistrationEndpoints;
import com.example.service.mvc.ILoginManagementService;
import com.example.service.mvc.IUserManagementService;
import com.example.util.FileUploadUtils;
import com.example.util.UserUtils;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(RegistrationEndpoints.REGISTRATION_BASE_URI)
@Slf4j
public class RegistrationController {

	@Autowired
	private IUserManagementService userManagementService;

	@Autowired
	private ILoginManagementService loginManagementService;

	@GetMapping(RegistrationEndpoints.SHOW_REGISTRATION_FORM)
	public String showRegistarionForm() {

		return "register_user";
	}

	@PostMapping(RegistrationEndpoints.REGISTER_NEW_CUSTOMER)
	public String registerNewCustomer(
			@Valid @ModelAttribute("userRegistrationDTO") UserRegistrationDTO userRegistrationDTO,
			BindingResult bindingResult,
			Map<String, Object> model) {

		if (bindingResult.hasErrors()) {

			log.warn("Validation errors occurred during registration for user: {}", userRegistrationDTO.getUsername());

			Map<String, String> errorMap = new HashMap<>();
			bindingResult.getFieldErrors().forEach(error ->
					errorMap.put(error.getField(), error.getDefaultMessage())
			);
			model.put("errorMap", errorMap);
			return "register_user";
		}

		byte[] imageBytes = FileUploadUtils.convertToByteArray(userRegistrationDTO.getImage());

		User user = UserUtils.createUser(
				userRegistrationDTO.getUsername(),
				loginManagementService.hashPassword(userRegistrationDTO.getPassword()),
				userRegistrationDTO.getFirstName(),
				userRegistrationDTO.getLastName(),
				userRegistrationDTO.getAddress(),
				userRegistrationDTO.getPhoneNumber(),
				imageBytes);

		User userRegistrationResult = userManagementService.registerNewCustomer(user);

		model.put("userRegResult", userRegistrationResult);

		return "user_registration_success";
	}
}