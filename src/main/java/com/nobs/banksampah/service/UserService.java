package com.nobs.banksampah.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.nobs.banksampah.model.User;

public interface UserService {

    UserDetailsService userDetailsService();

    User updateUserPoints(String username, double poin);

    User resetUserPoints(String username);

    void deleteUserByUsername(String username);

    User updateUserProfile(String username, Map<String, Object> updates);

    User getUserById(String id);

    double getUserPoints(String username);
}
