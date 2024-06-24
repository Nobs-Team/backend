package com.nobs.banksampah.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "BankSampah")
public class BankSampah {

    @Id
    private String id;

    private String nama;
    private String alamat;
}
