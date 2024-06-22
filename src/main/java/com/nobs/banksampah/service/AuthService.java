package com.nobs.banksampah.service;

import com.nobs.banksampah.model.User;
import com.nobs.banksampah.request.LoginRequest;
import com.nobs.banksampah.request.RefreshTokenRequest;
import com.nobs.banksampah.request.RegisterRequest;
import com.nobs.banksampah.response.JwtResponse;

public interface AuthService {

    User register(RegisterRequest registerRequest);

    JwtResponse login(LoginRequest loginRequest);

    JwtResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
