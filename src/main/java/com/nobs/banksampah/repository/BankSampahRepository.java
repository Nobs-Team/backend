package com.nobs.banksampah.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nobs.banksampah.model.BankSampah;

public interface BankSampahRepository extends MongoRepository<BankSampah, String> {

}
