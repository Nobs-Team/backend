package com.nobs.banksampah.service;

import com.nobs.banksampah.model.Trash;

public interface TrashService {

    Trash addTrash(Trash trash);

    Trash getTrashById(String trashId);

    void deleteTrashById(String trashId);
}
