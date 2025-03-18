package com.nobs.banksampah.service;

import com.nobs.banksampah.model.User;
import com.nobs.banksampah.request.LoginRequest;
import com.nobs.banksampah.request.RefreshTokenRequest;
import com.nobs.banksampah.request.RegisterRequest;
import com.nobs.banksampah.response.ApiResponse;
import com.nobs.banksampah.response.JwtResponse;

public interface AuthService {

  ApiResponse<User> register(RegisterRequest registerRequest);

  ApiResponse<JwtResponse> login(LoginRequest loginRequest);

  ApiResponse<JwtResponse> refreshToken(RefreshTokenRequest refreshTokenRequest);
}
