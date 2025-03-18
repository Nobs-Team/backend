package com.nobs.banksampah;

import com.nobs.banksampah.model.Role;
import com.nobs.banksampah.model.User;
import com.nobs.banksampah.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BanksampahApplication implements CommandLineRunner {

  @Autowired private UserRepository userRepository;

  public static void main(String[] args) {
    SpringApplication.run(BanksampahApplication.class, args);
  }

  @Override
  public void run(String... args) {
    User adminAccount = userRepository.findByRole(Role.ADMIN);

    if (null == adminAccount) {
      User user = new User();
      user.setNama("admin");
      user.setUsername("admin");
      user.setRole(Role.ADMIN);
      user.setPassword(new BCryptPasswordEncoder().encode("admin"));
      userRepository.save(user);
    }
  }
}
