package com.example.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.beans.Train;
import com.example.constants.endpoints.AdminEndpoints;
import com.example.exception.restapi.train.ApiTrainException;
import com.example.exception.restapi.train.ApiTrainNotFoundException;
import com.example.service.mvc.ITrainService;

@Controller
@RequestMapping(AdminEndpoints.ADMIN_BASE_URI)
public class AdminController {

	@Autowired
	private ITrainService trainService;

	@GetMapping(value = { "/", AdminEndpoints.SHOW_HOME })
	public String showHomePage() {
		return "admin/admin_home";
	}

	@GetMapping(AdminEndpoints.VIEW_ALL_TRAINS)
	public String viewAllTrainsForward(Map<String, Object> model) {

		List<Train> allTrains = trainService.getAllTrains();

		model.put("allTrains", allTrains);

		return "admin/view_trains";
	}

	@GetMapping(AdminEndpoints.SHOW_SEARCH_TRAIN_BY_NUMBER_FORM)
	public String searchTrainByNumberForward() {
		return "admin/search_train_form";
	}

	@GetMapping(AdminEndpoints.SHOW_SEARCH_TRAIN_BY_NUMBER_RESULT)
	public String searchTrainByNumberResult(
			@RequestParam Long trainNo,
			Map<String, Object> model) {
		Train train = trainService.getTrainByNumber(trainNo);

		model.put("train", train);

		return "admin/search_train_result";
	}

	@GetMapping(AdminEndpoints.SHOW_ADD_TRAIN_FORM)
	public String addTrainForward() {
		return "admin/add_train_form";
	}

	@PostMapping(AdminEndpoints.SAVE_OR_UPDATE_TRAIN)
	public String saveOrUpdateTrain(
			@ModelAttribute Train train,
			Map<String, Object> model) {

		String message = trainService.saveOrUpdateTrain(train);

		model.put("message", message);

		return "admin/display_message";
	}

	@GetMapping(AdminEndpoints.SHOW_UPDATE_TRAIN_INITIAL_FORM)
	public String updateTrainFwd() {
		return "admin/update_train_page";
	}

	@PostMapping(AdminEndpoints.SHOW_TRAIN_EDIT_FORM_TO_UPDATE_BY_NUMBER)
	public String showEditForm(
			@RequestParam Long trainNo,
			Map<String,
					Object> model) {

		Train train = trainService.getTrainByNumber(trainNo);

		model.put("train", train);
		return "admin/edit_train_details_form";
	}

	@GetMapping(AdminEndpoints.SHOW_DELETE_TRAIN_FORM)
	public String deleteTrainForward() {
		return "admin/delete_train_form";
	}

	@PostMapping(AdminEndpoints.DELETE_TRAIN_BY_NUMBER)
	public String deleteTrain(
			@RequestParam Long trainNo,
			Map<String,
					Object> model) {

		String message = trainService.deleteTrain(trainNo);

		model.put("message", message);

		return "admin/display_message";
	}
}