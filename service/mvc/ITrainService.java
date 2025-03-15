package com.example.service.mvc;

import java.util.List;

import com.example.beans.Train;
import com.example.exception.mvc.train.TrainException;
import com.example.exception.mvc.train.TrainNotFoundException;
import com.example.exception.restapi.train.ApiTrainException;
import com.example.exception.restapi.train.ApiTrainNotFoundException;

public interface ITrainService {

	public List<Train> getAllTrains();

	public Train getTrainByNumber(Long trainNo)
			throws TrainNotFoundException;

	public String saveOrUpdateTrain(Train train)
			throws TrainException;

	public String deleteTrain(Long trainNo)
			throws TrainNotFoundException;

	public List<Train> getTrainsBetweenStations(
			String fromStation,
			String toStation);
}