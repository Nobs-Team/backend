package com.nobs.banksampah.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Trash")
public class Trash {

  @Id private String id;
  private String jenis;
  private String keterangan;
  private double poin;
}
