package com.nobs.banksampah.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nobs.banksampah.model.Trash;

public interface TrashRepository extends MongoRepository<Trash, String> {

}
