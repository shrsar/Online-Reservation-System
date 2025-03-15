package com.example.service.restapi;

import com.example.beans.restapi.ApiTicket;
import com.example.exception.restapi.booking.ApiBookingFailedException;
import com.example.exception.restapi.booking.ApiNoEnoughSeatsForBooking;
import com.example.request.TrainBookingApiRequest;

public interface IApiBookingService {

	public ApiTicket bookApiTicket(TrainBookingApiRequest trainBookingApiRequest)
			throws ApiNoEnoughSeatsForBooking, ApiBookingFailedException;
}