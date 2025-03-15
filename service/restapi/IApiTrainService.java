package com.example.service.restapi;

import java.util.List;

import com.example.beans.Train;
import com.example.exception.restapi.train.ApiTrainNotFoundException;

public interface IApiTrainService {

	public List<Train> getAllTrains();

	public Train getTrainByNumber(Long trainNo)
			throws ApiTrainNotFoundException;

	public List<Train> getTrainsBetweenStations(
			String fromStation,
			String toStation);
}