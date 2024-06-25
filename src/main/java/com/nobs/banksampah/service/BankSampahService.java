package com.nobs.banksampah.service;

import java.util.Map;

import com.nobs.banksampah.model.BankSampah;

public interface BankSampahService {

    BankSampah addBankSampah(BankSampah bankSampah);

    void deleteBankSampahById(String bankSampahId);

    BankSampah updateBankSampahById(String bankSampahId, Map<String, Object> updates);
}
