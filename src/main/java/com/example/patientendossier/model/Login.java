package com.example.patientendossier.model;

import com.example.patientendossier.Application;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

  private final String email;
  private final String password;

  public Login(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public Patient loginPatient()
  {
    String query =
      "SELECT * " +
      "FROM patient " +
      "WHERE email = '" + this.email + "' " +
      "AND password = '" + this.password + "'"
    ;

    ResultSet result = Application.db.getData(query);

    try {
      if (result.next()) {
        return new Patient(
          result.getInt("number"),
          result.getString("firstname"),
          result.getString("lastname"),
          result.getDate("birthdate").toLocalDate(),
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

  public Care loginCare()
  {
    String query =
      "SELECT * " +
      "FROM care " +
      "WHERE email = '" + this.email + "' " +
      "AND password = '" + this.password + "'"
    ;

    ResultSet result = Application.db.getData(query);

    try {
      if (result.next()) {
        return new Care(
          result.getInt("number"),
          result.getString("firstname"),
          result.getString("lastname"),
          result.getString("profession"),
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
