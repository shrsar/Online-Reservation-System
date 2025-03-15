package com.example.util;

import java.io.Serializable;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;

@Component
public class ApiUserTicketIdGenerator implements IdentifierGenerator {

	private static final String PREFIX = "A-";
	private static long counter = 1;

	@Override
	public Serializable generate(
			SharedSessionContractImplementor session,
			Object object) {

		long timestamp = System.currentTimeMillis();

		long uniqueValue = (timestamp << 20) | (counter & 0xFFFFF);

		counter = (counter + 1) & 0xFFFFF;

		return PREFIX + String.format("%010d", uniqueValue);
	}
}