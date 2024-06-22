package com.nobs.banksampah.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nobs.banksampah.model.Role;
import com.nobs.banksampah.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);

    User findByRole(Role role);
}
