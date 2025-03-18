package com.nobs.banksampah.request;

import lombok.Data;

@Data
public class RegisterRequest {

  private String nama;
  private String username;
  private String password;
  private String alamat;
  private String norek;
}
