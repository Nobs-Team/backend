package com.nobs.banksampah.controller;

import com.nobs.banksampah.model.BankSampah;
import com.nobs.banksampah.model.User;
import com.nobs.banksampah.request.ProfileRequest;
import com.nobs.banksampah.response.ApiResponse;
import com.nobs.banksampah.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/profile")
  @Secured("ROLE_USER")
  public ResponseEntity<ApiResponse<User>> getProfile() {
    return ResponseEntity.ok(userService.getProfile());
  }

  @PutMapping("/profile")
  @Secured("ROLE_USER")
  public ResponseEntity<ApiResponse<User>> updateProfile(@RequestBody ProfileRequest request) {
    return ResponseEntity.ok(userService.updateUserProfile(request));
  }

  @GetMapping("/points")
  @Secured("ROLE_USER")
  public ResponseEntity<ApiResponse<Double>> getPoin() {
    return ResponseEntity.ok(userService.getUserPoints());
  }

  @GetMapping("/banksampah")
  @Secured("ROLE_USER")
  public ResponseEntity<ApiResponse<List<BankSampah>>> getBankSampah() {
    return ResponseEntity.ok(userService.getBankSampah());
  }

  @DeleteMapping("/profile")
  @Secured("ROLE_USER")
  public ResponseEntity<ApiResponse<Void>> deleteAccount() {
    return ResponseEntity.ok(userService.deleteAccount());
  }
}
