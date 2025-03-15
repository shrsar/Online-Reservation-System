package com.example.response;

import java.time.LocalDate;

import com.example.constants.TicketStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainBookingApiResponse {

	private String ticketId;

	private String transactionId;

	private TicketStatus ticketStatus;

	private LocalDate journeyDate;

	private Integer seatsBooked;

	private String seatType;

	private Double ticketAmount;

	private Long trainNo;

	private String trainName;

	private String fromStation;

	private String toStation;

}