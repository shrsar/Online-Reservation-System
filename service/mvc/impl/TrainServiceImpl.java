package com.example.service.mvc.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.beans.Train;
import com.example.exception.mvc.train.TrainException;
import com.example.exception.mvc.train.TrainNotFoundException;
import com.example.exception.restapi.train.ApiTrainException;
import com.example.exception.restapi.train.ApiTrainNotFoundException;
import com.example.repository.mvc.ITrainRepository;
import com.example.service.mvc.ITrainService;

@Service
public class TrainServiceImpl implements ITrainService {

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
		throw new TrainNotFoundException(Thread.currentThread().getStackTrace(), userFriendlyMessage);
	}

	@Override
	public String saveOrUpdateTrain(Train train) {
		try {
			Train savedTrain = trainRepository.save(train);
			if (train.getTrainNo() == null)
				return "Train added successfully with train Number: " + savedTrain.getTrainNo();
			else
				return "Train details updated successfully for the train Number: " + train.getTrainNo();

		} catch (Exception e) {
			throw new TrainException(e.getStackTrace(),
					"Error occurred while saving or updating a train with  train Number: " + train.getTrainNo());
		}
	}

	@Override
	public String deleteTrain(Long trainNo)
			throws TrainNotFoundException {

		String userFriendlyMessage = null;

		try {
			trainRepository.deleteById(trainNo);
			return "Train with Number: " + trainNo + " deleted successfully";
		} catch (EmptyResultDataAccessException erd) {
			userFriendlyMessage = "Train Not Found with the Number : " + trainNo;
			throw new TrainNotFoundException(erd.getStackTrace(), userFriendlyMessage);
		} catch (Exception e) {
			userFriendlyMessage = "Exception occurred while deleting train with Number: " + trainNo;
			throw new TrainException(e.getStackTrace(), userFriendlyMessage);
		}
	}

	@Override
	public List<Train> getTrainsBetweenStations(
			String fromStation,
			String toStation) {

		return trainRepository.findTrainsBetweenStations(fromStation, toStation);
	}
}