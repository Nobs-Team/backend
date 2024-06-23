package com.nobs.banksampah.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nobs.banksampah.model.Trash;
import com.nobs.banksampah.model.User;
import com.nobs.banksampah.repository.UserRepository;
import com.nobs.banksampah.response.ApiResponse;
import com.nobs.banksampah.service.TrashService;
import com.nobs.banksampah.util.StringUtil;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final TrashService trashService;

    @GetMapping("/getAdmin")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponse<Map<String, String>>> welcomeAdmin() {
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

    @PostMapping("/addTrash")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponse<Trash>> addTrash(@RequestBody Trash trash) {
        Trash addedTrash = trashService.addTrash(trash);
        ApiResponse<Trash> response = new ApiResponse<>(true, "Trash added successfully", addedTrash);

        return ResponseEntity.ok(response);
    }

    // Penanganan kesalahan khusus
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Void>> handleResponseStatusException(ResponseStatusException ex) {
        ApiResponse<Void> response = new ApiResponse<>(false, ex.getReason());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // Menggunakan HttpStatus.BAD_REQUEST untuk 400
    }
}
