package com.example.service.restapi.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.beans.Train;
import com.example.exception.restapi.train.ApiTrainNotFoundException;
import com.example.repository.mvc.ITrainRepository;
import com.example.service.restapi.IApiTrainService;

@Service
public class ApiTrainServiceImpl implements IApiTrainService {

	@Autowired
	private ITrainRepository trainRepository;

	@Override
	public List<Train> getAllTrains() {
		return (List<Train>) trainRepository.findAll();
	}

	@Override
	public Train getTrainByNumber(Long trainNo) {
		Optional<Train> train = trainRepository.findById(trainNo);
		if (train.isPresent())
			return train.get();

		String userFriendlyMessage = "Train Not Found with the Number : " + trainNo;
		throw new ApiTrainNotFoundException(Thread.currentThread().getStackTrace(), userFriendlyMessage);
	}

	@Override
	public List<Train> getTrainsBetweenStations(String fromStation, String toStation) {

		return trainRepository.findTrainsBetweenStations(fromStation, toStation);
	}
}