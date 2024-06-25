package com.nobs.banksampah.service;

import java.util.Map;

import com.nobs.banksampah.model.Trash;

public interface TrashService {

    Trash addTrash(Trash trash);

    Trash getTrashById(String trashId);

    void deleteTrashById(String trashId);

    Trash updateTrashById(String trashId, Map<String, Object> updates);
}
