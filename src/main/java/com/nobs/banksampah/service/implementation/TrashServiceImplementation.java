package com.nobs.banksampah.service.implementation;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.nobs.banksampah.model.Trash;
import com.nobs.banksampah.repository.TrashRepository;
import com.nobs.banksampah.service.TrashService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrashServiceImplementation implements TrashService {

    private final TrashRepository trashRepository;

    @Override
    public Trash addTrash(Trash trash) {
        return trashRepository.save(trash);
    }

    @Override
    public Trash getTrashById(String trashId) {
        return trashRepository.findById(trashId)
                .orElseThrow(() -> new RuntimeException("Sampah tidak ditemukan"));
    }

    @Override
    public void deleteTrashById(String trashId) {
        Trash trash = trashRepository.findById(trashId)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
        trashRepository.delete(trash);
    }

    @Override
    public Trash updateTrashById(String trashId, Map<String, Object> updates) {
        Trash trash = trashRepository.findById(trashId)
                .orElseThrow(() -> new RuntimeException("Sampah tidak ditemukan"));

        updates.forEach((key, value) -> {
            switch (key) {
                case "jenis" -> trash.setJenis((String) value);
                case "keterangan" -> trash.setKeterangan((String) value);
                case "poin" -> trash.setPoin((double) value);
                default -> {
                }
            }
        });

        return trashRepository.save(trash);
    }
}
