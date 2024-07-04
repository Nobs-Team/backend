package com.nobs.banksampah.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nobs.banksampah.model.BankSampah;
import com.nobs.banksampah.model.User;
import com.nobs.banksampah.repository.BankSampahRepository;
import com.nobs.banksampah.repository.UserRepository;
import com.nobs.banksampah.response.ApiResponse;
import com.nobs.banksampah.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final BankSampahRepository bankSampahRepository;
    private final UserService userService;

    @GetMapping("/profile")
    @Secured("ROLE_USER")
    public ResponseEntity<ApiResponse<User>> getProfile() {
        // Mendapatkan Authentication object dari SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Mendapatkan user dari service berdasarkan username
        User user = userService.getUserByUsername(username);

        // Membuat API response
        ApiResponse<User> response = new ApiResponse<>(true, "Berhasil mendapatkan data profil", user);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/profile")
    @Secured("ROLE_USER")
    public ResponseEntity<ApiResponse<User>> updateProfile(@RequestBody Map<String, Object> updates) {
        // Mendapatkan Authentication object dari SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Simpan perubahan pada repository menggunakan service
        User updatedUser = userService.updateUserProfile(username, updates);

        // Membuat API response
        ApiResponse<User> response = new ApiResponse<>(true, "Update profil berhasil", updatedUser);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getPoin")
    @Secured("ROLE_USER")
    public ResponseEntity<ApiResponse<Map<String, String>>> getPoin() {
        // Mendapatkan Authentication object dari SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Mengambil user dari repository berdasarkan username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User tidak ditemukan"));

        // Mendapatkan poin user
        double poin = user.getJumlahpoin();
        String formattedPoin = String.format("%.0f", poin);

        // Membuat response map
        Map<String, String> data = new HashMap<>();
        data.put("poin", formattedPoin);

        // Membuat ApiResponse
        ApiResponse<Map<String, String>> response = new ApiResponse<>(true, "Berhasil mendapat data poin", data);

        // Mengembalikkan poin dalam format JSON
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getBankSampah")
    @Secured("ROLE_USER")
    public ResponseEntity<ApiResponse<List<BankSampah>>> getBankSampah() {
        // Mendapatkan data bank sampah
        List<BankSampah> bankSampah = bankSampahRepository.findAll();

        // Membuat API response
        ApiResponse<List<BankSampah>> response = new ApiResponse<>(true, "Berhasil menadapat data Bank Sampah",
                bankSampah);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteAccount")
    @Secured("ROLE_USER")
    public ResponseEntity<ApiResponse<Void>> deleteAccount() {
        // Mendapatkan authentication object dari SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Hapus user
        userService.deleteUserByUsername(username);

        // Membuat API response
        ApiResponse<Void> response = new ApiResponse<>(true, "Akun berhasil dihapus");

        return ResponseEntity.ok(response);
    }

    // Penanganan kesalahan khusus
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Void>> handleResponseStatusException(ResponseStatusException ex) {
        ApiResponse<Void> response = new ApiResponse<>(false, ex.getReason());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // Menggunakan HttpStatus.BAD_REQUEST untuk 400
    }
}
