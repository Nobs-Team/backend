package com.nobs.banksampah.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "Trash")
public class Trash {

    private String jenis;
    private String keterangan;
    private double poin;
}
