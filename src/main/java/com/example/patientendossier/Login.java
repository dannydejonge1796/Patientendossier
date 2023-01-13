package com.example.patientendossier;

public class Login {

  private String email;
  private String password;

  public Login(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public Patient loginPatient()
  {
    String query =
      "SELECT 1 FROM patient" +
      "WHERE";


  }
}
