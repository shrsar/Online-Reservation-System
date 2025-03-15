package com.example.beans;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.example.constants.TicketStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ticket")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "localUserTicketIdGenerator")
	@GenericGenerator(name = "localUserTicketIdGenerator", strategy = "com.example.util.LocalUserTicketIdGenerator")
	@Column(name = "ticket_id")
	private String ticketId;

	@Column(name = "transaction_id")
	private String transactionId;

	@Column(name = "ticket_status")
	@Enumerated(EnumType.STRING)
	private TicketStatus ticketStatus;

	@Column(name = "journey_date")
	private LocalDate journeyDate;

	@Column(name = "seats_required")
	private Integer seatsRequired;

	@Column(name = "seat_type")
	private String seatType;

	@Column(name = "amount")
	private Double ticketAmount;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "trainNo")
	private Train train;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "userId")
	private User user;
}
