package com.nobs.banksampah.response;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String refreshToken;
}
