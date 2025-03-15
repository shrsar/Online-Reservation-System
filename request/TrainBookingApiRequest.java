package com.example.request;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.example.beans.restapi.PaymentDetails;

import lombok.Data;

@Component
@Data
public class TrainBookingApiRequest {

	private String userName;

	private LocalDate journeyDate;

	private Integer seatsRequired;

	private String seatType;

	private Long trainNo;

	private PaymentDetails paymentDetails;

}