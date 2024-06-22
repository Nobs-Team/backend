package com.nobs.banksampah.service.implementation;

import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nobs.banksampah.model.Role;
import com.nobs.banksampah.model.User;
import com.nobs.banksampah.repository.UserRepository;
import com.nobs.banksampah.request.LoginRequest;
import com.nobs.banksampah.request.RefreshTokenRequest;
import com.nobs.banksampah.request.RegisterRequest;
import com.nobs.banksampah.response.JwtResponse;
import com.nobs.banksampah.service.AuthService;
import com.nobs.banksampah.service.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public User register(RegisterRequest registerRequest) {
        User user = new User();

        user.setNama(registerRequest.getNama());
        user.setUsername(registerRequest.getUsername());
        user.setRole(Role.USER); // Role set otomatis menjadi user
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setNorek(registerRequest.getNorek());
        user.setAlamat(registerRequest.getAlamat());
        user.setJumlahpoin(0);

        return userRepository.save(user);
    }

    public JwtResponse login(LoginRequest loginRequest) {
        // Autentikasi akun
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        var user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Password atau email salah"));
        // Integrasi token ke user
        var token = jwtService.generateToken(user);
        // Membuat refresh token
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtResponse jwtResponse = new JwtResponse();
        // Eksekusi token ke user
        jwtResponse.setToken(token);
        jwtResponse.setRefreshToken(refreshToken);

        return jwtResponse;
    }

    public JwtResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String username = jwtService.extractUsername(refreshTokenRequest.getToken());
        User user = userRepository.findByUsername(username).orElseThrow();

        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var token = jwtService.generateToken(user);
            JwtResponse jwtResponse = new JwtResponse();
            // Eksekusi token ke user
            jwtResponse.setToken(token);
            jwtResponse.setRefreshToken(refreshTokenRequest.getToken());

            return jwtResponse;
        }

        return null;
    }
}
