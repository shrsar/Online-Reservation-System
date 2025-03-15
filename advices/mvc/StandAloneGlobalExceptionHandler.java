package com.example.advices.mvc;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.constants.UserRole;
import com.example.exception.mvc.booking.BookingException;
import com.example.exception.mvc.booking.BookingFailedException;
import com.example.exception.mvc.booking.NoEnoughSeatsForBooking;
import com.example.exception.mvc.login.LoginFailedException;
import com.example.exception.mvc.login.UserNotFoundException;
import com.example.exception.mvc.train.TrainException;
import com.example.exception.mvc.train.TrainNotFoundException;
import com.example.util.ExceptionLoggerUtil;
import com.example.util.UserUtils;

@ControllerAdvice
public class StandAloneGlobalExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(StandAloneGlobalExceptionHandler.class);

	@Autowired
	private UserUtils userUtils;

	@ExceptionHandler(TrainNotFoundException.class)
	public String handleTrainNotFoundException(
			TrainNotFoundException e,
			HttpServletRequest request,
			Model model) {

		System.out.println("StandAloneGlobalExceptionHandler.handleTrainNotFoundException");
		ExceptionLoggerUtil.logException(e, request.getRequestURI());

		String sessionId = (String) request.getSession().getAttribute("sessionId");
		UserRole userRole = userUtils.getUserRoleBySessionId(sessionId);
		String message = "Something went wrong.";
		String viewPage = null;

		if (userRole != null) {
			if (userRole == UserRole.ADMIN) {
				viewPage = "admin/display_message";
			} else if (userRole == UserRole.CUSTOMER) {
				viewPage = "user/display_message";
			}
			message = e.getUserFriendlyMessage();
		}
		model.addAttribute("message", message);
		return viewPage;
	}

	@ExceptionHandler(TrainException.class)
	public String handleTrainException(
			TrainException e,
			HttpServletRequest request,
			Model model) {
		ExceptionLoggerUtil.logException(e, request.getRequestURI());
		String message = e.getUserFriendlyMessage();
		model.addAttribute("message", message);
		return "admin/display_message";
	}

	@ExceptionHandler({
			BookingException.class,
			NoEnoughSeatsForBooking.class,
			BookingFailedException.class
	})
	public String handleNoEnoughSeatsForBooking(
			BookingException e,
			HttpServletRequest request,
			Model model) {

		e.printStackTrace();
		LOGGER.error("Exception Occurred for the URL: {}", request.getRequestURI(), e);
		String message = e.getUserFriendlyMessage();
		model.addAttribute("message", message);
		return "user/display_message";
	}

	@ExceptionHandler({
			LoginFailedException.class,
			UserNotFoundException.class
	})
	public String handleLonginRelatedExceptions(
			LoginFailedException e,
			HttpServletRequest request,
			Model model) {
		ExceptionLoggerUtil.logException(e, request.getRequestURI());
		String sessionId = (String) request.getSession().getAttribute("sessionId");
		UserRole userRole = userUtils.getUserRoleBySessionId(sessionId);
		String message = "Something went wrong.";
		String viewPage = null;

		System.out.println("***********************************************");
		if (userRole != null) {
			if (userRole == UserRole.ADMIN) {
				viewPage = "admin/display_message";
			} else if (userRole == UserRole.CUSTOMER) {
				viewPage = "user/display_message";
			}
			message = e.getUserFriendlyMessage();
		}
		model.addAttribute("message", message);
		return viewPage;
	}
}
