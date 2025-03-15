package com.example.repository.mvc;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.beans.User;

public interface IUserRepository extends CrudRepository<User, Integer> {

	public User findByUsername(String username);

	public User findByUsernameAndPassword(
			String username,
			String password);

	@Transactional
	@Modifying
	@Query("UPDATE com.example.beans.User SET sessionId=:sessionId WHERE userId=:userId")
	public void updateSessionIdByUserId(
			Integer userId,
			String sessionId);

	public User findBySessionId(String sessionId);

}