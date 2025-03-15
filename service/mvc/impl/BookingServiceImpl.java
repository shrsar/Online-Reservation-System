package com.example.service.mvc.impl;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.beans.Ticket;
import com.example.beans.Train;
import com.example.beans.User;
import com.example.constants.TicketStatus;
import com.example.exception.mvc.booking.BookingFailedException;
import com.example.exception.mvc.booking.NoEnoughSeatsForBooking;
import com.example.exception.restapi.booking.ApiBookingFailedException;
import com.example.exception.restapi.booking.ApiNoEnoughSeatsForBooking;
import com.example.repository.mvc.ITicketRepository;
import com.example.service.mvc.IBookingService;
import com.example.service.mvc.ITrainService;

@Service
public class BookingServiceImpl implements IBookingService {

	@Autowired
	private ITicketRepository ticketRepository;

	@Autowired
	private ITrainService trainService;

	@Override
	@Transactional
	public Ticket bookTicket(Ticket ticket) {

		Train train = trainService.getTrainByNumber(ticket.getTrain().getTrainNo());

		train.setFromStation(ticket.getTrain().getFromStation());
		train.setToStation(ticket.getTrain().getToStation());

		Integer seatsAvailable = train.getSeats();

		Ticket ticketBookingResult = null;

		Predicate<Ticket> areSeatsUnavailable =
				requestedTicket  -> requestedTicket.getSeatsRequired() > train.getSeats();

		if (areSeatsUnavailable.test(ticket)) {
			String userFriendlyMessage = "Only " + seatsAvailable + " seats are available on this train!";
			throw new NoEnoughSeatsForBooking(Thread.currentThread().getStackTrace(), userFriendlyMessage);
		} else {
			seatsAvailable = seatsAvailable - ticket.getSeatsRequired();

			train.setSeats(seatsAvailable);

			try {
				String transactionId = UUID.randomUUID().toString();
				Double fare = train.getFare();
				Integer seatsRequired = ticket.getSeatsRequired();
				Double totalAmount = fare * seatsRequired;

				ticket.setTransactionId(transactionId);
				ticket.setTicketStatus(TicketStatus.BOOKED);
				ticket.setTicketAmount(totalAmount);

				ticketBookingResult = ticketRepository.save(ticket);

				trainService.saveOrUpdateTrain(train);

				ticketBookingResult.setTrain(train);

			} catch (Exception e) {
				e.printStackTrace();
				String userFriendlyMessage = "Booking failed for the train number: " + train.getTrainNo();
				throw new BookingFailedException(Thread.currentThread().getStackTrace(), userFriendlyMessage);
			}

		}
		return ticketBookingResult;
	}

	@Override
	public List<Ticket> getAllTickets() {
		return (List<Ticket>) ticketRepository.findAll();
	}

	@Override
	public List<Ticket> getTicketsByUser(User user) {
		return ticketRepository.findByUser(user);
	}

}