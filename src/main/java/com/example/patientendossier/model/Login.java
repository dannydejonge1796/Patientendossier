package com.example.patientendossier.model;

import com.example.patientendossier.Application;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

  private final String email;
  private final String password;

  public Login(String email, String password)
  {
    this.email = email;
    this.password = password;
  }

  public Patient loginPatient()
  {
    //Query om een patient op te halen met het ingevoerde email en wachtwoord
    String query =
      "SELECT * " +
      "FROM patient " +
      "WHERE email = '" + this.email + "' " +
      "AND password = '" + this.password + "'"
    ;

    //Data ophaal functie aanroepen in de database
    ResultSet result = Application.db.getData(query);

    //Patient object aanmaken van het resultaat en dit object terug geven
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
    //Query om een zorgverlener op te halen met het ingevoerde email en wachtwoord
    String query =
      "SELECT * " +
      "FROM care " +
      "WHERE email = '" + this.email + "' " +
      "AND password = '" + this.password + "'"
    ;

    //Data ophaal functie aanroepen in de database
    ResultSet result = Application.db.getData(query);

    //Care object aanmaken van het resultaat en dit object terug geven
    try {
      if (result.next()) {
        return new Care(
          result.getInt("number"),
          result.getString("firstname"),
          result.getString("lastname"),
          result.getDate("birthdate").toLocalDate(),
          result.getString("profession"),
          result.getInt("phonenumber"),
          result.getString("email"),
          result.getString("password")
        );
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null;
  }
}
