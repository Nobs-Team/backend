package com.nobs.banksampah.service.implementation;

import com.nobs.banksampah.model.BankSampah;
import com.nobs.banksampah.model.User;
import com.nobs.banksampah.repository.BankSampahRepository;
import com.nobs.banksampah.repository.UserRepository;
import com.nobs.banksampah.request.ProfileRequest;
import com.nobs.banksampah.response.ApiResponse;
import com.nobs.banksampah.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImplementation implements UserService {

  private static final String USER_NOTFOUND = "User not found";
  private final UserRepository userRepository;
  private final BankSampahRepository bankSampahRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetailsService userDetailsService() {
    return this::getUserByUsername;
  }

  @Override
  public ApiResponse<User> getProfile() {
    String username = getAuthenticatedUsername();
    User user = getUserByUsername(username);
    log.info("Profile fetched successfully for user: {}", username);

    return ApiResponse.<User>builder().message("Profile fetched successfully").data(user).build();
  }

  @Override
  public ApiResponse<User> updateUserProfile(ProfileRequest request) {
    String username = getAuthenticatedUsername();
    User user = getUserByUsername(username);

    if (request.getNama() != null) {
      user.setNama(request.getNama());
    }
    if (request.getAlamat() != null) {
      user.setAlamat(request.getAlamat());
    }
    if (request.getPassword() != null) {
      user.setPassword(passwordEncoder.encode(request.getPassword()));
    }
    userRepository.save(user);
    log.info("Profile updated successfully for user: {}", username);

    return ApiResponse.<User>builder().message("Profile updated successfully").data(user).build();
  }

  @Override
  public ApiResponse<Double> getUserPoints() {
    String username = getAuthenticatedUsername();
    double jumlahPoin = getUserByUsername(username).getJumlahpoin();
    log.info("User: {} has {} points", username, jumlahPoin);

    return ApiResponse.<Double>builder()
        .message("Points fetched successfully")
        .data(jumlahPoin)
        .build();
  }

  @Override
  public ApiResponse<List<BankSampah>> getBankSampah() {
    List<BankSampah> bankSampahList = bankSampahRepository.findAll();
    log.info("Fetched {} Bank Sampah entries", bankSampahList.size());

    return ApiResponse.<List<BankSampah>>builder()
        .message("Bank Sampah Lists fetched successfully")
        .data(bankSampahList)
        .build();
  }

  @Transactional
  @Override
  public ApiResponse<Void> deleteAccount() {
    String username = getAuthenticatedUsername();
    User user = getUserByUsername(username);
    userRepository.delete(user);
    log.info("Account deleted successfully for user: {}", username);

    return ApiResponse.<Void>builder().message("Account deleted successfully").build();
  }

  @Override
  public double getUserPoints(String username) {
    double points = getUserByUsername(username).getJumlahpoin();
    log.info("User: {} has {} points", username, points);

    return points;
  }

  @Transactional
  @Override
  public User updateUserPoints(String username, double poin) {
    User user = getUserByUsername(username);
    user.setJumlahpoin(user.getJumlahpoin() + poin);
    User updatedUser = userRepository.save(user);
    log.info("User: {} now has {} points", username, updatedUser.getJumlahpoin());

    return userRepository.save(user);
  }

  @Transactional
  @Override
  public User resetUserPoints(String username) {
    User user = getUserByUsername(username);
    user.setJumlahpoin(0);
    User updatedUser = userRepository.save(user);
    log.info("User: {} points reset to 0", username);

    return updatedUser;
  }

  private User getUserByUsername(String username) {
    return userRepository
        .findByUsername(username)
        .orElseThrow(
            () -> {
              log.error("User not found with username: {}", username);
              return new UsernameNotFoundException(USER_NOTFOUND);
            });
  }

  private String getAuthenticatedUsername() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    log.debug("Authenticated username: {}", username);

    return username;
  }
}
