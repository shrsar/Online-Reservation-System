package com.example.repository.mvc;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.beans.Ticket;
import com.example.beans.User;

public interface ITicketRepository extends PagingAndSortingRepository<Ticket, String> {

	public List<Ticket> findByUser(User user);

}