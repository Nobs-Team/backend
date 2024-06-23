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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public User register(RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

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

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        var user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password"));

        var token = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(token);
        jwtResponse.setRefreshToken(refreshToken);

        return jwtResponse;
    }

    @Override
    public JwtResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String username = jwtService.extractUsername(refreshTokenRequest.getToken());
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token"));

        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var token = jwtService.generateToken(user);
            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setToken(token);
            jwtResponse.setRefreshToken(refreshTokenRequest.getToken());

            return jwtResponse;
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
    }
}
