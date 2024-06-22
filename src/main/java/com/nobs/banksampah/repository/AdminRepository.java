package com.nobs.banksampah.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nobs.banksampah.model.Admin;

public interface AdminRepository extends MongoRepository<Admin, String> {

    Admin findByUsername(String username);
}
