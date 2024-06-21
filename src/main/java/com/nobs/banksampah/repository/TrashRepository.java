package com.nobs.banksampah.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nobs.banksampah.model.TrashModel;

public interface TrashRepository extends MongoRepository<TrashModel, String> {

}
