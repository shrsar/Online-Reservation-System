package com.example.beans.restapi;

import java.time.LocalDate;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.example.beans.Train;
import com.example.constants.TicketStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "api_ticket")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiTicket {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "apiUserTicketIdGenerator")
	@GenericGenerator(name = "apiUserTicketIdGenerator", strategy = "com.example.util.ApiUserTicketIdGenerator")
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
}
