package com.nobs.banksampah.service.implementation;

import com.nobs.banksampah.model.BankSampah;
import com.nobs.banksampah.model.Role;
import com.nobs.banksampah.model.Trash;
import com.nobs.banksampah.model.User;
import com.nobs.banksampah.repository.BankSampahRepository;
import com.nobs.banksampah.repository.TrashRepository;
import com.nobs.banksampah.repository.UserRepository;
import com.nobs.banksampah.request.PointRequest;
import com.nobs.banksampah.request.ResetPointRequest;
import com.nobs.banksampah.response.ApiResponse;
import com.nobs.banksampah.service.AdminService;
import com.nobs.banksampah.service.UserService;
import com.nobs.banksampah.util.StringUtil;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
@Slf4j
public class AdminServiceImplementation implements AdminService {

  private static final String USER_NOTFOUND = "User not found";
  private static final String TRASH_NOTFOUND = "Trash not found";
  private static final String BANKSAMPAH_NOTFOUND = "BankSampah not found";
  private final TrashRepository trashRepository;
  private final BankSampahRepository bankSampahRepository;
  private final UserRepository userRepository;
  private final UserService userService;

  @Override
  public ApiResponse<Map<String, String>> dashboard() {
    User user = getAuthenticatedUser();

    return ApiResponse.<Map<String, String>>builder()
        .message("Login successfully")
        .data(Map.of("name", StringUtil.capitalizeFirstLetter(user.getNama())))
        .build();
  }

  @Override
  public ApiResponse<User> addPoints(PointRequest request) {
    User updatedUser =
        userService.updateUserPoints(
            request.getUsername(), getTrashById(request.getTrashId()).getPoin());
    log.info("Points added successfully for user '{}'", updatedUser.getUsername());

    return ApiResponse.<User>builder().message("Points added").data(updatedUser).build();
  }

  @Override
  public ApiResponse<User> resetPoints(ResetPointRequest request) {
    if (userService.getUserPoints(request.getUsername()) <= 50) {
      log.warn(
          "Attempt to reset points failed for '{}', reason: Minimum 50 points required",
          request.getUsername());
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Minimum 50 points per user");
    }
    log.info("Reset points successfully for user '{}'", request.getUsername());

    return ApiResponse.<User>builder()
        .message("Reset points successfully")
        .data(userService.resetUserPoints(request.getUsername()))
        .build();
  }

  @Override
  public ApiResponse<List<User>> getUsers() {
    List<User> users = userRepository.findAllByRole(Role.USER);
    log.info("Total users found: {}", users.size());

    return ApiResponse.<List<User>>builder()
        .message("Users fetched successfully")
        .data(users)
        .build();
  }

  @Transactional
  @Override
  public ApiResponse<Trash> addTrash(Trash trash) {
    trashRepository.save(trash);
    log.info("Trash added successfully with ID '{}'", trash.getId());

    return ApiResponse.<Trash>builder().message("Trash added").data(trash).build();
  }

  @Transactional
  @Override
  public ApiResponse<Void> deleteTrashById(String trashId) {
    trashRepository.delete(getTrashById(trashId));
    log.info("Trash with ID '{}' deleted successfully", trashId);

    return ApiResponse.<Void>builder().message("Trash deleted").build();
  }

  @Transactional
  @Override
  public ApiResponse<Trash> updateTrashById(String trashId, Map<String, Object> updates) {
    Trash trash = getTrashById(trashId);
    updates.forEach(
        (key, value) -> {
          switch (key) {
            case "jenis" -> trash.setJenis((String) value);
            case "keterangan" -> trash.setKeterangan((String) value);
            case "poin" -> {
              if (value instanceof Number number) {
                double newPoin = number.doubleValue();
                if (newPoin < 0) {
                  log.warn("Invalid update: Point cannot be negative (ID: {})", trashId);
                  throw new IllegalArgumentException("Point cannot be negative");
                }
                trash.setPoin(newPoin);
              } else {
                log.warn("Invalid value for 'point' on trash ID '{}'", trashId);
                throw new IllegalArgumentException("Invalid value for 'point'");
              }
            }
            default -> {
              log.warn("Invalid field '{}' in update request for trash ID '{}'", key, trashId);
              throw new IllegalArgumentException("Invalid field: " + key);
            }
          }
        });
    trashRepository.save(trash);

    return ApiResponse.<Trash>builder().message("Trash updated").data(trash).build();
  }

  @Transactional
  @Override
  public ApiResponse<BankSampah> addBankSampah(BankSampah bankSampah) {
    bankSampahRepository.save(bankSampah);
    log.info("Bank Sampah added successfully with ID '{}'", bankSampah.getId());

    return ApiResponse.<BankSampah>builder().message("Bank Sampah added").data(bankSampah).build();
  }

  @Transactional
  @Override
  public ApiResponse<Void> deleteBankSampahById(String bankSampahId) {
    bankSampahRepository.delete(getBankSampahById(bankSampahId));
    log.info("Bank Sampah with ID '{}' deleted successfully", bankSampahId);

    return ApiResponse.<Void>builder().message("Bank Sampah deleted").build();
  }

  @Transactional
  @Override
  public ApiResponse<BankSampah> updateBankSampahById(
      String bankSampahId, Map<String, Object> updates) {
    BankSampah bankSampah = getBankSampahById(bankSampahId);
    updates.forEach(
        (key, value) -> {
          switch (key) {
            case "nama" -> bankSampah.setNama((String) value);
            case "alamat" -> bankSampah.setAlamat((String) value);
            default -> {
              log.warn(
                  "Invalid field '{}' in update request for Bank Sampah ID '{}'",
                  key,
                  bankSampahId);
              throw new IllegalArgumentException("Invalid field: " + key);
            }
          }
        });
    bankSampahRepository.save(bankSampah);
    log.info("Bank Sampah with ID '{}' updated successfully", bankSampahId);

    return ApiResponse.<BankSampah>builder()
        .message("Bank Sampah updated")
        .data(bankSampah)
        .build();
  }

  private User getAuthenticatedUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    return userRepository
        .findByUsername(authentication.getName())
        .orElseThrow(
            () -> {
              log.warn("User '{}' not found", authentication.getName());
              return new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
            });
  }

  private Trash getTrashById(String trashId) {
    return trashRepository
        .findById(trashId)
        .orElseThrow(
            () -> {
              log.warn("Trash with ID '{}' not found", trashId);
              return new ResponseStatusException(HttpStatus.NOT_FOUND, TRASH_NOTFOUND);
            });
  }

  private BankSampah getBankSampahById(String bankSampahId) {
    return bankSampahRepository
        .findById(bankSampahId)
        .orElseThrow(
            () -> {
              log.warn("Bank Sampah with ID '{}' not found", bankSampahId);
              return new ResponseStatusException(HttpStatus.NOT_FOUND, BANKSAMPAH_NOTFOUND);
            });
  }
}
