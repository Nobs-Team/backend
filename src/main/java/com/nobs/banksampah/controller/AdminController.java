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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nobs.banksampah.model.BankSampah;
import com.nobs.banksampah.model.Role;
import com.nobs.banksampah.model.Trash;
import com.nobs.banksampah.model.User;
import com.nobs.banksampah.repository.UserRepository;
import com.nobs.banksampah.response.ApiResponse;
import com.nobs.banksampah.service.BankSampahService;
import com.nobs.banksampah.service.TrashService;
import com.nobs.banksampah.service.UserService;
import com.nobs.banksampah.util.StringUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final TrashService trashService;
    private final UserService userService;
    private final BankSampahService bankSampahService;

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

    @DeleteMapping("/deleteTrash/{trashId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponse<Void>> deleteTrash(@PathVariable String trashId) {
        // Menghapus data sampah
        trashService.deleteTrashById(trashId);

        // Membuat API response
        ApiResponse<Void> response = new ApiResponse<>(true, "Trash deleted successfully");

        return ResponseEntity.ok(response);
    }

    @PutMapping("/addPoints")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponse<User>> addPoints(@RequestBody Map<String, Object> request) {
        String username = (String) request.get("username");
        String trashId = (String) request.get("trashId");

        // Mendapatkan data sampah berdasarkan ID
        Trash trash = trashService.getTrashById(trashId);

        // Menambahkan poin ke user dari data sampah yang ada di database
        User updatedUser = userService.updateUserPoints(username, trash.getPoin());

        // Membuat API response
        ApiResponse<User> response = new ApiResponse<>(true, "Points added successfully", updatedUser);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/resetPoints")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponse<User>> resetPoints(@RequestBody Map<String, Object> request) {
        // Mendapatkan username
        String username = (String) request.get("username");

        // Reset poin
        User updatePoint = userService.resetUserPoints(username);

        // Membuat API response
        ApiResponse<User> response = new ApiResponse<>(true, "Points exchange successfully", updatePoint);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getUsers")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponse<List<User>>> getUsers() {
        // Mendapatkan semua user dengan role USER dari repository
        List<User> usersWithUserRole = userRepository.findAllByRole(Role.USER);

        // Membuat Api Response
        ApiResponse<List<User>> response = new ApiResponse<>(true, "Users retrieved successfully", usersWithUserRole);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/addBankSampah")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponse<BankSampah>> addBankSampah(@RequestBody BankSampah bankSampah) {
        // Menambahkan bank sampah
        BankSampah addBankSampah = bankSampahService.addBankSampah(bankSampah);

        // Membuat API response
        ApiResponse<BankSampah> response = new ApiResponse<>(true, "Bank Sampah added successfully", addBankSampah);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteBankSampah/{bankSampahId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponse<Void>> deleteBankSampah(@PathVariable String bankSampahId) {
        // Menghapus data bank sampah
        bankSampahService.deleteBankSampahById(bankSampahId);

        // Membuat API response
        ApiResponse<Void> response = new ApiResponse<>(true, "Bank sampah deleted successfully");

        return ResponseEntity.ok(response);
    }

    // Penanganan kesalahan khusus
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Void>> handleResponseStatusException(ResponseStatusException ex) {
        ApiResponse<Void> response = new ApiResponse<>(false, ex.getReason());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // Menggunakan HttpStatus.BAD_REQUEST untuk 400
    }
}
