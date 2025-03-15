package com.example.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.beans.Train;
import com.example.exception.mvc.train.TrainNotFoundException;
import com.example.exception.restapi.train.ApiTrainNotFoundException;
import com.example.service.mvc.ITrainService;

@RestController
@RequestMapping("/api/trains")
public class TrainRestController {

	@Autowired
	private ITrainService trainService;

	@GetMapping("/list")
	public ResponseEntity<?> getAllTrains() {
		List<Train> trains = trainService.getAllTrains();
		return ResponseEntity.ok(trains);
	}

	@GetMapping("/{trainNo}")
	public ResponseEntity<?> getTrainByNumber(
			@PathVariable Long trainNo) throws TrainNotFoundException {

		Train train = trainService.getTrainByNumber(trainNo);
		return ResponseEntity.ok(train);
	}

	@GetMapping("/between/{source}/{destination}")
	public ResponseEntity<?> getTrainsBetweenStations(
			@PathVariable("source") String fromStation,
			@PathVariable("destination") String toStation) {

		List<Train> trains = trainService.getTrainsBetweenStations(fromStation, toStation);
		return ResponseEntity.ok(trains);
	}
}