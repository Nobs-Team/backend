package com.nobs.banksampah.util;

public class StringUtil {

  private StringUtil() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  public static String capitalizeFirstLetter(String str) {
    if (str == null || str.isEmpty()) {
      return str;
    }
    return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
  }
}
