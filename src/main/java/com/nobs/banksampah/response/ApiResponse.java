package com.nobs.banksampah.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {

  private String message;
  private T data;
}
