package com.example.patientendossier.utility;

public class Validation {

  public boolean validateString(String data)
  {
    //return true als data niet null is en de lengte niet 0 is
    return data != null && data.length() != 0;
  }
}
