package com.nobs.banksampah.controller;

import com.nobs.banksampah.model.User;
import com.nobs.banksampah.request.LoginRequest;
import com.nobs.banksampah.request.RefreshTokenRequest;
import com.nobs.banksampah.request.RegisterRequest;
import com.nobs.banksampah.response.ApiResponse;
import com.nobs.banksampah.response.JwtResponse;
import com.nobs.banksampah.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthContoller {

  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<ApiResponse<User>> register(@RequestBody RegisterRequest registerRequest) {
    return ResponseEntity.ok(authService.register(registerRequest));
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<JwtResponse>> login(@RequestBody LoginRequest loginRequest) {
    return ResponseEntity.ok(authService.login(loginRequest));
  }

  @PostMapping("/refresh")
  public ResponseEntity<ApiResponse<JwtResponse>> refresh(
      @RequestBody RefreshTokenRequest refreshTokenRequest) {
    return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
  }
}
