package com.example.beans;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainDTO {

	@Nullable
	private Long trainNo;

	@Nullable
	private String trainName;

	@Nullable
	private String fromStation;

	@Nullable
	private String toStation;

	@Nullable
	private Integer seats;

	@Nullable
	private Double fare;
}