package com.nobs.banksampah.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.nobs.banksampah.model.User;

public interface UserService {

    UserDetailsService userDetailsService();

    User updateUserPoints(String username, double poin);
}
