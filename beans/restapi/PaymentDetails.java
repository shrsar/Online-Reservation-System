package com.example.beans.restapi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetails {

	private String paymentMethod;

	@JsonIgnore
	private String cardNumber;

	@JsonIgnore
	private String expiryDate;

	@JsonIgnore
	private String cvv;

	@Override
	public String toString() {
		return "PaymentDetails(paymentMethod=" + paymentMethod +
				", cardNumber=**** **** **** " + (cardNumber != null ? cardNumber.substring(cardNumber.length() - 4) : "****") +
				", expiryDate=**/**)";
	}
}
