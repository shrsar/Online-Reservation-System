package com.example.repository.mvc;

import org.springframework.data.repository.CrudRepository;

import com.example.beans.UserProfile;

public interface IUserProfileRepository extends CrudRepository<UserProfile, Integer> {
}