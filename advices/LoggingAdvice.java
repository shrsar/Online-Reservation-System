package com.example.advices;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAdvice {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAdvice.class);

	@Before("within(com.example.controller.*)")
	public void logControllerMethodEntry(JoinPoint joinPoint) {
		LOGGER.debug("Entering method: {}.{}", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName());
	}

	@Before("within(com.example.service.mvc.*)")
	public void logServiceMethodEntry(JoinPoint joinPoint) {
		LOGGER.debug("Entering method: {}.{}", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName());
	}

	@Before("within(com.example.repository.mvc.*)")
	public void logRepositoryMethodEntry(JoinPoint joinPoint) {
		LOGGER.debug("Entering method: {}.{}", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName());
	}

	@AfterReturning(pointcut = "execution(* com.example..*(..)) && !within(com.example.advices.LoggingAdvice)", returning = "result")
	public void logMethodExit(JoinPoint joinPoint, Object result) {
		LOGGER.debug("Exiting method: {}.{}", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName());
		LOGGER.debug("Returned value: {}", result);
	}

	@AfterThrowing(pointcut = "execution(* com.example..*(..)) && !within(com.example.advices.LoggingAdvice)", throwing = "ex")
	public void logMethodException(JoinPoint joinPoint, Exception ex) {
		LOGGER.error("Exception in method: {}.{}", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName());
		LOGGER.error("Exception message: {}", ex.getMessage());
	}
}
