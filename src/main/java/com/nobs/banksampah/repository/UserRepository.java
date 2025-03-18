package com.nobs.banksampah.repository;

import com.nobs.banksampah.model.Role;
import com.nobs.banksampah.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

  boolean existsByUsername(String username);

  Optional<User> findByUsername(String username);

  User findByRole(Role role);

  List<User> findAllByRole(Role role);
}
