package com.nobs.banksampah.service;

import com.nobs.banksampah.model.BankSampah;
import com.nobs.banksampah.model.Trash;
import com.nobs.banksampah.model.User;
import com.nobs.banksampah.request.PointRequest;
import com.nobs.banksampah.request.ResetPointRequest;
import com.nobs.banksampah.response.ApiResponse;
import java.util.List;
import java.util.Map;

public interface AdminService {

  ApiResponse<Map<String, String>> dashboard();

  ApiResponse<User> addPoints(PointRequest request);

  ApiResponse<User> resetPoints(ResetPointRequest request);

  ApiResponse<List<User>> getUsers();

  ApiResponse<Trash> addTrash(Trash trash);

  ApiResponse<Void> deleteTrashById(String trashId);

  ApiResponse<Trash> updateTrashById(String trashId, Map<String, Object> updates);

  ApiResponse<BankSampah> addBankSampah(BankSampah bankSampah);

  ApiResponse<Void> deleteBankSampahById(String bankSampahId);

  ApiResponse<BankSampah> updateBankSampahById(String bankSampahId, Map<String, Object> updates);
}
