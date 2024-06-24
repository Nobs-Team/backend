package com.nobs.banksampah.service.implementation;

import org.springframework.stereotype.Service;

import com.nobs.banksampah.model.BankSampah;
import com.nobs.banksampah.repository.BankSampahRepository;
import com.nobs.banksampah.service.BankSampahService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankSampahServiceImplementation implements BankSampahService {

    private final BankSampahRepository bankSampahRepository;

    @Override
    public BankSampah addBankSampah(BankSampah bankSampah) {
        return bankSampahRepository.save(bankSampah);
    }

    @Override
    public void deleteBankSampahById(String bankSampahId) {
        BankSampah bankSampah = bankSampahRepository.findById(bankSampahId)
                .orElseThrow(() -> new RuntimeException("Bank sampah tidak ditemukan"));
        bankSampahRepository.delete(bankSampah);
    }
}
