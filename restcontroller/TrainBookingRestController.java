package com.example.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.beans.restapi.ApiTicket;
import com.example.request.TrainBookingApiRequest;
import com.example.response.TrainBookingApiResponse;
import com.example.service.restapi.IApiBookingService;
import com.example.util.ObjectConverterUtils;

@RestController
@RequestMapping("/api/train-booking")
public class TrainBookingRestController {

	@Autowired
	private IApiBookingService apiBookingService;

	@PostMapping("/confirm")
	public ResponseEntity<?> confirmTrainTicketBooking(
			@RequestBody TrainBookingApiRequest trainBookingApiRequest) {

		// passing to service for booking
		ApiTicket apiTicket = apiBookingService.bookApiTicket(trainBookingApiRequest);

		TrainBookingApiResponse ticketBookingResult = ObjectConverterUtils.convertApiTicketToApiResponse(apiTicket);

		return ResponseEntity.ok().body(ticketBookingResult);
	}
	
}
