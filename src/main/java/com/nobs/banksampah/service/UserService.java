package com.nobs.banksampah.service;

import com.nobs.banksampah.model.BankSampah;
import com.nobs.banksampah.model.User;
import com.nobs.banksampah.request.ProfileRequest;
import com.nobs.banksampah.response.ApiResponse;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

  UserDetailsService userDetailsService();

  ApiResponse<User> getProfile();

  ApiResponse<User> updateUserProfile(ProfileRequest request);

  ApiResponse<Double> getUserPoints();

  ApiResponse<List<BankSampah>> getBankSampah();

  ApiResponse<Void> deleteAccount();

  double getUserPoints(String username);

  User updateUserPoints(String username, double poin);

  User resetUserPoints(String username);
}
