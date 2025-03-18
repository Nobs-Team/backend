package com.nobs.banksampah.repository;

import com.nobs.banksampah.model.Trash;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrashRepository extends MongoRepository<Trash, String> {}
