package com.nobs.banksampah.controller;

import com.nobs.banksampah.model.BankSampah;
import com.nobs.banksampah.model.Trash;
import com.nobs.banksampah.model.User;
import com.nobs.banksampah.request.PointRequest;
import com.nobs.banksampah.request.ResetPointRequest;
import com.nobs.banksampah.response.ApiResponse;
import com.nobs.banksampah.service.AdminService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

  private final AdminService adminService;

  @GetMapping("/dashboard")
  @Secured("ROLE_ADMIN")
  public ResponseEntity<ApiResponse<Map<String, String>>> dashboard() {
    return ResponseEntity.ok(adminService.dashboard());
  }

  @PutMapping("/addPoints")
  @Secured("ROLE_ADMIN")
  public ResponseEntity<ApiResponse<User>> addPoints(@RequestBody PointRequest request) {
    return ResponseEntity.ok(adminService.addPoints(request));
  }

  @PutMapping("/resetPoints")
  @Secured("ROLE_ADMIN")
  public ResponseEntity<ApiResponse<User>> resetPoints(@RequestBody ResetPointRequest request) {
    return ResponseEntity.ok(adminService.resetPoints(request));
  }

  @PostMapping("/addTrash")
  @Secured("ROLE_ADMIN")
  public ResponseEntity<ApiResponse<Trash>> addTrash(@RequestBody Trash trash) {
    return ResponseEntity.ok(adminService.addTrash(trash));
  }

  @DeleteMapping("/deleteTrash/{trashId}")
  @Secured("ROLE_ADMIN")
  public ResponseEntity<ApiResponse<Void>> deleteTrash(@PathVariable String trashId) {
    return ResponseEntity.ok(adminService.deleteTrashById(trashId));
  }

  @PutMapping("/editSampah/{trashId}")
  @Secured("ROLE_ADMIN")
  public ResponseEntity<ApiResponse<Trash>> editSampah(
      @PathVariable String trashId, @RequestBody Map<String, Object> updates) {
    return ResponseEntity.ok(adminService.updateTrashById(trashId, updates));
  }

  @GetMapping("/getUsers")
  @Secured("ROLE_ADMIN")
  public ResponseEntity<ApiResponse<List<User>>> getUsers() {
    return ResponseEntity.ok(adminService.getUsers());
  }

  @PostMapping("/addBankSampah")
  @Secured("ROLE_ADMIN")
  public ResponseEntity<ApiResponse<BankSampah>> addBankSampah(@RequestBody BankSampah bankSampah) {
    return ResponseEntity.ok(adminService.addBankSampah(bankSampah));
  }

  @DeleteMapping("/deleteBankSampah/{bankSampahId}")
  @Secured("ROLE_ADMIN")
  public ResponseEntity<ApiResponse<Void>> deleteBankSampah(@PathVariable String bankSampahId) {
    return ResponseEntity.ok(adminService.deleteBankSampahById(bankSampahId));
  }

  @PutMapping("/editBankSampah/{bankSampahId}")
  @Secured("ROLE_ADMIN")
  public ResponseEntity<ApiResponse<BankSampah>> editBankSampah(
      @PathVariable String bankSampahId, @RequestBody Map<String, Object> updates) {
    return ResponseEntity.ok(adminService.updateBankSampahById(bankSampahId, updates));
  }
}
