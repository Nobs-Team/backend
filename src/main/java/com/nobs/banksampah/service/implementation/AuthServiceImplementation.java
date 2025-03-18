package com.nobs.banksampah.service.implementation;

import com.nobs.banksampah.model.Role;
import com.nobs.banksampah.model.User;
import com.nobs.banksampah.repository.UserRepository;
import com.nobs.banksampah.request.LoginRequest;
import com.nobs.banksampah.request.RefreshTokenRequest;
import com.nobs.banksampah.request.RegisterRequest;
import com.nobs.banksampah.response.ApiResponse;
import com.nobs.banksampah.response.JwtResponse;
import com.nobs.banksampah.service.AuthService;
import com.nobs.banksampah.service.JwtService;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImplementation implements AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  @Override
  public ApiResponse<User> register(RegisterRequest registerRequest) {
    if (userRepository.existsByUsername(registerRequest.getUsername())) {
      log.warn(
          "Registration failed: Username '{}' is already taken", registerRequest.getUsername());
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
    }

    User user =
        User.builder()
            .nama(registerRequest.getNama())
            .username(registerRequest.getUsername())
            .role(Role.USER)
            .password(passwordEncoder.encode(registerRequest.getPassword()))
            .alamat(registerRequest.getAlamat())
            .jumlahpoin(0)
            .build();
    userRepository.save(user);
    log.info("Registration successful for username: {}", registerRequest.getUsername());

    return ApiResponse.<User>builder().message("Register success").data(user).build();
  }

  @Override
  public ApiResponse<JwtResponse> login(LoginRequest loginRequest) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              loginRequest.getUsername(), loginRequest.getPassword()));

      User user =
          userRepository
              .findByUsername(loginRequest.getUsername())
              .orElseThrow(
                  () -> {
                    log.warn("Login failed: Username '{}' not found", loginRequest.getUsername());
                    return new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Invalid username or password");
                  });
      log.info("Login successful for username: {}", loginRequest.getUsername());

      return ApiResponse.<JwtResponse>builder()
          .message("Login success")
          .data(
              new JwtResponse(
                  jwtService.generateToken(user),
                  jwtService.generateRefreshToken(new HashMap<>(), user)))
          .build();

    } catch (AuthenticationException e) {
      log.error("Login failed for username '{}': {}", loginRequest.getUsername(), e.getMessage());
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
    }
  }

  @Override
  public ApiResponse<JwtResponse> refreshToken(RefreshTokenRequest refreshTokenRequest) {
    String username = jwtService.extractUsername(refreshTokenRequest.getToken());
    log.info("Extracted username from token: {}", username);
    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(
                () -> {
                  log.warn("Refresh token failed: Username '{}' not found", username);
                  return new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
                });

    if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
      var token = jwtService.generateToken(user);
      JwtResponse jwtResponse = new JwtResponse();
      jwtResponse.setToken(token);
      jwtResponse.setRefreshToken(refreshTokenRequest.getToken());
      log.info("Refresh token successful for username: {}", username);

      return ApiResponse.<JwtResponse>builder()
          .message("Refresh token success")
          .data(jwtResponse)
          .build();
    }
    log.warn("Refresh token failed: Invalid token for username: {}", username);
    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
  }
}
