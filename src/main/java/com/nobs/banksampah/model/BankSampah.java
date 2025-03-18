package com.nobs.banksampah.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "BankSampah")
public class BankSampah {

  @Id private String id;
  private String nama;
  private String alamat;
}
