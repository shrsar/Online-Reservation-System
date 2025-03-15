package com.example.beans;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import com.example.constants.TicketStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {

	@Nullable
	private String ticketId;

	@Nullable
	private String transactionId;

	@Enumerated(EnumType.STRING)
	@Nullable
	private TicketStatus ticketStatus;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate journeyDate;

	private Integer seatsRequired;

	private String seatType;

	@Nullable
	private Double ticketAmount;

	@Nullable
	private TrainDTO trainDTO;
}
