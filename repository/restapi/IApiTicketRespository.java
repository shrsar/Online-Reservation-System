package com.example.repository.restapi;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.beans.restapi.ApiTicket;

public interface IApiTicketRespository extends PagingAndSortingRepository<ApiTicket, String> {

}
