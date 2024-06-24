package com.nobs.banksampah.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nobs.banksampah.model.BankSampah;
import com.nobs.banksampah.model.User;
import com.nobs.banksampah.repository.BankSampahRepository;
import com.nobs.banksampah.repository.UserRepository;
import com.nobs.banksampah.response.ApiResponse;
import com.nobs.banksampah.util.StringUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final BankSampahRepository bankSampahRepository;

    @GetMapping("/getName")
    @Secured("ROLE_USER")
    public ResponseEntity<ApiResponse<Map<String, String>>> welcomeUser() {
        // Mendapatkan Authentication object dari SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Mengambil user dari repository berdasarkan username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User tidak ditemukan"));

        // Capitalize the first letter of the user's name
        String capitalizedNama = StringUtil.capitalizeFirstLetter(user.getNama());

        // Membuat response map
        Map<String, String> data = new HashMap<>();
        data.put("name", capitalizedNama);

        // Membuat ApiResponse
        ApiResponse<Map<String, String>> response = new ApiResponse<>(true, "Login successful", data);

        // Mengembalikan response dengan nama user dalam format JSON
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
        ApiResponse<Map<String, String>> response = new ApiResponse<>(true, "Poin retrieved successfully", data);

        // Mengembalikkan poin dalam format JSON
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getBankSampah")
    @Secured("ROLE_USER")
    public ResponseEntity<ApiResponse<List<BankSampah>>> getBankSampah() {
        // Mendapatkan data bank sampah
        List<BankSampah> bankSampah = bankSampahRepository.findAll();

        // Membuat API response
        ApiResponse<List<BankSampah>> response = new ApiResponse<>(true, "Bank sampah retrieved successfully",
                bankSampah);

        return ResponseEntity.ok(response);
    }

    // Penanganan kesalahan khusus
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Void>> handleResponseStatusException(ResponseStatusException ex) {
        ApiResponse<Void> response = new ApiResponse<>(false, ex.getReason());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // Menggunakan HttpStatus.BAD_REQUEST untuk 400
    }
}
