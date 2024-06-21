package com.nobs.banksampah.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nobs.banksampah.model.UserModel;

public interface UserRepository extends MongoRepository<UserModel, String> {

}
