package com.nobs.banksampah;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.nobs.banksampah.model.Role;
import com.nobs.banksampah.model.User;
import com.nobs.banksampah.repository.UserRepository;

@SpringBootApplication
public class BanksampahApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(BanksampahApplication.class, args);
	}

	public void run(String... args) {
		User adminAccount = userRepository.findByRole(Role.ADMIN);

		if (null == adminAccount) { // Apabila role kosong, buat akun admin
			User user = new User();

			// Tembak hardcode untuk menentukan data admin
			user.setNama("admin");
			user.setUsername("admin");
			user.setRole(Role.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(user);
		}
	}
}
