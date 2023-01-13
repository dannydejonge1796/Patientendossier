package com.example.patientendossier;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

  private Database db;
  private String email;
  private String password;

  public Login(Database db, String email, String password) {
    this.db = db;
    this.email = email;
    this.password = password;
  }

  public Patient loginPatient()
  {
    String query =
      "SELECT * " +
      "FROM patient " +
      "WHERE email = '" + email + "' " +
      "AND password = '" + password + "'"
    ;

    ResultSet result = db.getData(query);

    try {
      if (result.next()) {
        return new Patient(
          result.getInt("number"),
          result.getString("firstname"),
          result.getString("lastname"),
          result.getDate("birthdate"),
          result.getInt("phonenumber"),
          result.getString("email"),
          result.getString("password")
        );
      }
    } catch (SQLException e) {
      System.out.println("Ophalen data mislukt!");
    }

    return null;
  }
}
