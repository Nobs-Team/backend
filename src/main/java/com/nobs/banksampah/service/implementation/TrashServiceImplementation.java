package com.nobs.banksampah.service.implementation;

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
}