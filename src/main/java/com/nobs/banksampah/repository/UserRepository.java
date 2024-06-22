package com.nobs.banksampah.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nobs.banksampah.model.User;

public interface UserRepository extends MongoRepository<User, String> {

}
