package com.example.service.mvc;

import java.util.List;

import com.example.beans.Ticket;
import com.example.beans.User;
import com.example.exception.mvc.booking.BookingFailedException;
import com.example.exception.mvc.booking.NoEnoughSeatsForBooking;
import com.example.exception.restapi.booking.ApiBookingFailedException;
import com.example.exception.restapi.booking.ApiNoEnoughSeatsForBooking;

public interface IBookingService {

	public Ticket bookTicket(Ticket ticket)
			throws NoEnoughSeatsForBooking, BookingFailedException;

	public List<Ticket> getAllTickets();

	public List<Ticket> getTicketsByUser(User user);
}