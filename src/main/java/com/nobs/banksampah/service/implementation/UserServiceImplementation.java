package com.nobs.banksampah.service.implementation;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nobs.banksampah.model.User;
import com.nobs.banksampah.repository.UserRepository;
import com.nobs.banksampah.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return (String username) -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User tidak ditemukan"));
    }

    @Override
    public User updateUserPoints(String username, double poin) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User tidak ditemukan"));
        user.setJumlahpoin(user.getJumlahpoin() + poin);

        return userRepository.save(user);
    }

    @Override
    public User resetUserPoints(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User tidak ditemukan"));
        user.setJumlahpoin(0);

        return userRepository.save(user);
    }

    @Override
    public void deleteUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User tidak ditemukan"));
        userRepository.delete(user);
    }

    @Override
    public User updateUserProfile(String username, Map<String, Object> updates) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        // Update hanya field yang ada di updates map
        updates.forEach((key, value) -> {
            switch (key) {
                case "nama" -> user.setNama((String) value);
                case "alamat" -> user.setAlamat((String) value);
                case "norek" -> user.setNorek((String) value);
                case "password" -> {
                    String newPassword = (String) value;
                    user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
                }
                default -> {
                }
            }
        });

        return userRepository.save(user);
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
    }

    @Override
    public double getUserPoints(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User tidak ditemukan"));
        return user.getJumlahpoin();
    }
}
