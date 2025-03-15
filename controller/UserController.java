package com.example.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.beans.Ticket;
import com.example.beans.TicketDTO;
import com.example.beans.Train;
import com.example.beans.TrainDTO;
import com.example.beans.User;
import com.example.constants.endpoints.UserEndpoints;
import com.example.exception.restapi.booking.ApiBookingFailedException;
import com.example.exception.restapi.booking.ApiNoEnoughSeatsForBooking;
import com.example.exception.restapi.train.ApiTrainNotFoundException;
import com.example.service.mvc.IBookingService;
import com.example.service.mvc.ILoginManagementService;
import com.example.service.mvc.ITrainService;

@Controller
@RequestMapping(UserEndpoints.USER_BASE_URI)
public class UserController {

	@Autowired
	private ITrainService trainService;

	@Autowired
	private IBookingService bookingService;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private ILoginManagementService loginManagementService;

	@GetMapping(value = { "/", UserEndpoints.SHOW_HOME })
	public String showHomePage() {
		return "user/user_home";
	}

	@GetMapping(UserEndpoints.VIEW_ALL_TRAINS)
	public String viewAllTrainsForward(Map<String, Object> model) {

		System.out.println("UserController.viewAllTrainsForward()");

		List<Train> trainsList = trainService.getAllTrains();

		trainsList.forEach(System.out::println);

		model.put("pageHeading", "All Available running Trains - displaying to user..");
		model.put("trainsList", trainsList);

		return "user/view_trains";
	}

	@GetMapping(value = { UserEndpoints.SHOW_FIND_TRAINS_BETWEEN_TWO_STATIONS_FORM,
			UserEndpoints.SHOW_TRAIN_FAIR_ENQUERY_FORM })
	public String findTrainsbetweenStaionsForward
			(Map<String, Object> model,
			 HttpServletRequest request) {

		System.out.println(request.getRequestURI());

		String pageHeading;
		String submitButtonValue;

		if (request.getRequestURI().equals(servletContext.getContextPath() + "/user/findTrainsbetweenStaionsFwd")) {
			pageHeading = "Search Trains Between Stations";
			submitButtonValue = "SEARCH TRAINS";
		} else {
			pageHeading = "Train Fare Enquiry";
			submitButtonValue = "SHOW FARE";
		}

		model.put("pageHeading", pageHeading);
		model.put("submitButtonValue", submitButtonValue);

		return "user/trains_btwn_stations_form";
	}

	@GetMapping(UserEndpoints.FIND_TRAINS_BETWEEN_TWO_STATIONS_RESULT)
	public String findTrainsbetweenStaions(
			@RequestParam String fromStation,
			@RequestParam String toStation,
			Map<String, Object> model) {

		List<Train> trainsList = trainService.getTrainsBetweenStations(fromStation, toStation);
		trainsList.forEach(System.out::println);

		model.put("pageHeading",
				"Trains Between stations.." + fromStation.toUpperCase() + " & " + toStation.toUpperCase());
		model.put("trainsList", trainsList);

		return "user/view_trains";
	}

	@GetMapping(UserEndpoints.SHOW_TRAIN_PRE_BOOKING_FORM)
	public String showPreBookingFormForTrain(
			@RequestParam Long trainNo,
			@RequestParam String fromStation,
			@RequestParam String toStation,
			HttpSession session,
			Map<String, Object> model) {

		System.out.println("UserController.showPreBookingFormForTrain()+ " + trainNo);

		TrainDTO trainDTO = new TrainDTO();
		trainDTO.setTrainNo(trainNo);
		trainDTO.setFromStation(fromStation);
		trainDTO.setToStation(toStation);

		String sessionId = session.getAttribute("sessionId").toString();
		User user = loginManagementService.getUserbySessionId(sessionId);

		model.put("preBookingDetails", trainDTO);
		model.put("user", user);

		return "user/train_pre_booking_form";
	}

	@PostMapping(UserEndpoints.PROCEED_TRAIN_BOOKING)
	public String proceedTrainBookingForUser(
			@ModelAttribute TicketDTO ticketDTO,
			@RequestParam Long trainNo,
			@RequestParam String fromStation,
			@RequestParam String toStation,
			Map<String, Object> model) {

		TrainDTO trainDTO = new TrainDTO();
		trainDTO.setTrainNo(trainNo);
		trainDTO.setFromStation(fromStation);
		trainDTO.setToStation(toStation);

		model.put("ticketDTO", ticketDTO);
		model.put("trainDTO", trainDTO);

		return "user/payment_inputs_form";
	}

	@PostMapping(UserEndpoints.CONFIRM_TRAIN_BOOKING)
	public String confirmTrainBooking(
			@ModelAttribute("trainDTO") TrainDTO trainDTO,
			@ModelAttribute("ticketDTO") TicketDTO ticketDTO,
			HttpSession session,
			Map<String, Object> model) {

		Train train = new Train();
		BeanUtils.copyProperties(trainDTO, train);

		Ticket ticket = new Ticket();
		BeanUtils.copyProperties(ticketDTO, ticket);

		ticket.setTrain(train);

		String sessionId = session.getAttribute("sessionId").toString();
		User user = loginManagementService.getUserbySessionId(sessionId);

		ticket.setUser(user);

		Ticket ticketBookingResult = bookingService.bookTicket(ticket);

		model.put("ticketBookingResult", ticketBookingResult);

		return "user/ticket_booking_result";
	}

	@GetMapping(UserEndpoints.SHOW_TICKET_BOOKING_HISTORY)
	public String getAllTicketsBooked(
			HttpSession session,
			Map<String,
					Object> model) {

		String sessionId = session.getAttribute("sessionId").toString();
		User user = loginManagementService.getUserbySessionId(sessionId);

		List<Ticket> ticketsList = bookingService.getTicketsByUser(user);

		model.put("pageHeading", "Ticket Booking History");
		model.put("ticketsList", ticketsList);

		return "user/view_all_tickets";
	}

	@GetMapping(value = {
			UserEndpoints.SHOW_TRAIN_SEATS_AVAILABILITY_CHECK_FORM,
			UserEndpoints.SHOW_SEARCH_TRAIN_BY_NUMBER_FORM }
	)
	public String showTrainNumberinputForm(
			HttpServletRequest request,
			Map<String, Object> model) {

		String pageHeading;
		String submitButtonValue;

		if (request.getRequestURI().equals("/user/trainSeatsAvailablityCheckFwd")) {
			pageHeading = "Train Seats Availability Check !";
			submitButtonValue = "CHECK SEATS AVAILABLE";
		} else {
			pageHeading = "Search Trains!";
			submitButtonValue = "SEARCH TRAIN";
		}

		model.put("pageHeading", pageHeading);
		model.put("submitButtonValue", submitButtonValue);

		return "user/train_number_input_form";
	}

	@GetMapping(UserEndpoints.SEARCH_TRAIN_BY_NUMBER_RESULT)
	public String searchTrainByNumber(
			@RequestParam Long trainNo,
			Map<String,
					Object> model) {

		Train train = trainService.getTrainByNumber(trainNo);

		model.put("train", train);

		return "user/display_train_details";
	}

}