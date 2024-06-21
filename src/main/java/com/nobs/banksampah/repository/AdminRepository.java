package com.nobs.banksampah.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nobs.banksampah.model.AdminModel;

public interface AdminRepository extends MongoRepository<AdminModel, String> {

    AdminModel findByUsername(String username);
}
