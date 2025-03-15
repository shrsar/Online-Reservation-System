package com.example.repository.mvc;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.example.beans.Train;

public interface ITrainRepository extends PagingAndSortingRepository<Train, Long> {

	@Query("FROM com.example.beans.Train "
			+ "WHERE ( UPPER(fromStation)=UPPER(:from) "
			+ "AND UPPER(toStation)=UPPER(:to)) "
			+ "OR (UPPER(fromStation)=UPPER(:to) "
			+ "AND UPPER(toStation)=UPPER(:from))")
	public List<Train> findTrainsBetweenStations(
			@Param("from") String fromStation,
			@Param("to") String toStation);
}