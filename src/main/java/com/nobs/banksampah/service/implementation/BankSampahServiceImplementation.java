package com.nobs.banksampah.service.implementation;

import java.util.Map;

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

    @Override
    public BankSampah updateBankSampahById(String bankSampahId, Map<String, Object> updates) {
        BankSampah bankSampah = bankSampahRepository.findById(bankSampahId)
                .orElseThrow(() -> new RuntimeException("Bank sampah tidak ditemukan"));

        // Update hanya field yang ada di updates map
        updates.forEach((key, value) -> {
            switch (key) {
                case "nama" -> bankSampah.setNama((String) value);
                case "alamat" -> bankSampah.setAlamat((String) value);
                default -> {
                }
            }
        });

        return bankSampahRepository.save(bankSampah);
    }
}
