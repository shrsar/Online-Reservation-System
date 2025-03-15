package com.example.service.restapi.impl;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.beans.Train;
import com.example.beans.restapi.ApiTicket;
import com.example.constants.TicketStatus;
import com.example.exception.restapi.booking.ApiBookingFailedException;
import com.example.exception.restapi.booking.ApiNoEnoughSeatsForBooking;
import com.example.repository.restapi.IApiTicketRespository;
import com.example.request.TrainBookingApiRequest;
import com.example.service.mvc.ITrainService;
import com.example.service.restapi.IApiBookingService;

@Service
public class ApiBookingServiceImpl implements IApiBookingService {

	@Autowired
	private ITrainService trainService;

	@Autowired
	private IApiTicketRespository apiTicketRespository;

	@Override
	@Transactional
	public ApiTicket bookApiTicket(TrainBookingApiRequest trainBookingApiRequest) {

		Train trainForBooking = trainService.getTrainByNumber(trainBookingApiRequest.getTrainNo());

		Integer seatsAvailable = trainForBooking.getSeats();
		Integer seatsRequired = trainBookingApiRequest.getSeatsRequired();

		ApiTicket apiTicketResult = null;

		if (seatsAvailable < seatsRequired) {
			String userFriendlyMessage = "Only " + seatsAvailable + " seats are available on this train!";
			throw new ApiNoEnoughSeatsForBooking(Thread.currentThread().getStackTrace(), userFriendlyMessage);
		} else {
			seatsAvailable = seatsAvailable - seatsRequired;

			trainForBooking.setSeats(seatsAvailable);

			try {

				String transactionId = UUID.randomUUID().toString();
				Double fare = trainForBooking.getFare();
				Double totalAmount = fare * seatsRequired;

				ApiTicket apiTicket = new ApiTicket();
				apiTicket.setTransactionId(transactionId);
				apiTicket.setTicketStatus(TicketStatus.BOOKED);
				apiTicket.setJourneyDate(trainBookingApiRequest.getJourneyDate());
				apiTicket.setSeatsRequired(seatsRequired);
				apiTicket.setSeatType(trainBookingApiRequest.getSeatType());
				apiTicket.setTicketAmount(totalAmount);

				apiTicket.setTrain(trainForBooking);

				apiTicketResult = apiTicketRespository.save(apiTicket);

				trainService.saveOrUpdateTrain(trainForBooking);

			} catch (Exception e) {
				String userFriendlyMessage = "Booking failed for the train number: " + trainForBooking.getTrainNo();
				throw new ApiBookingFailedException(Thread.currentThread().getStackTrace(), userFriendlyMessage);
			}
		}

		return apiTicketResult;
	}
}