package com.nobs.banksampah.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nobs.banksampah.model.User;
import com.nobs.banksampah.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/getUser")
    @Secured("ROLE_USER")
    public ResponseEntity<String> welcomeUser() {
        // Mendapatkan Authentication object dari SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Mengambil user dari repository berdasarkan username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        // Mengembalikan response dengan nama user
        return ResponseEntity.ok("Selamat datang, " + user.getNama());
    }

    @GetMapping("/getPoin")
    @Secured("ROLE_USER")
    public ResponseEntity<String> getPoin() {
        // Mendapatkan Authentication object dari SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Mengambil user dari repository berdasarkan username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        // Mendapatkan poin user
        double poin = user.getJumlahpoin();
        String formattedPoin = String.format("%.0f", poin);

        // Mengembalikkan poin
        return ResponseEntity.ok("Poin anda: " + formattedPoin);
    }
}
