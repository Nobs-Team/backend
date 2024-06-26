package com.nobs.banksampah.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "Trash")
public class Trash {

    @Id
    private String id;

    private String jenis;
    private String keterangan;
    private double poin;
}
