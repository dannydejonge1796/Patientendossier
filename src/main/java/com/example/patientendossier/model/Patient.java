package com.example.patientendossier.model;

import com.example.patientendossier.Application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Patient {

  private final Integer number;
  private String firstname;
  private String lastname;
  private LocalDate birthdate;
  private Integer phonenumber;
  private String email;
  private String password;

  public Patient(Integer number, String firstname, String lastname, LocalDate birthdate, Integer phonenumber, String email, String password)
  {
    this.number = number;
    this.firstname = firstname;
    this.lastname = lastname;
    this.birthdate = birthdate;
    this.phonenumber = phonenumber;
    this.email = email;
    this.password = password;
  }

  public void updateAllergy(Allergy allergy)
  {
    String query =
            "UPDATE allergy_patient " +
            "SET description = '" + allergy.getDescription() + "' " +
            "WHERE patient_number = '" + number + "' " +
            "AND allergy_name = '" + allergy.getName() + "'"
    ;

    Application.db.storeData(query);
  }

  public void addAllergy(Allergy allergy)
  {
    String query =
            "INSERT INTO allergy_patient (allergy_name, patient_number, description)" +
            "VALUES ('" + allergy.getName() + "', '" + number + "', '" + allergy.getDescription() + "')"
    ;

    Application.db.storeData(query);
  }

  public ArrayList<String> getAllergyNames()
  {
    ArrayList<String> allergyNames = new ArrayList<>();

    String query =
            "SELECT " +
                    "allergy.name " +
            "FROM " +
                    "patient AS patient, " +
                    "allergy AS allergy, " +
                    "allergy_patient AS allergyPatient " +
            "WHERE allergyPatient.patient_number = '" + this.number + "' " +
            "AND allergy.name = allergyPatient.allergy_name " +
            "GROUP BY allergy.name"
    ;

    ResultSet result = Application.db.getData(query);

    try {
      while (result.next()) {
        allergyNames.add(result.getString("name"));
      }
    } catch (SQLException e) {
      System.out.println("Ophalen data mislukt!");
    }

    return allergyNames;
  }

  public ArrayList<Allergy> getAllergies()
  {
    ArrayList<Allergy> allergies = new ArrayList<>();

    String query =
      "SELECT " +
        "allergy.name, " +
        "allergyPatient.description " +
      "FROM " +
        "patient AS patient, " +
        "allergy AS allergy, " +
        "allergy_patient AS allergyPatient " +
      "WHERE allergyPatient.patient_number = '" + this.number + "' " +
      "AND allergy.name = allergyPatient.allergy_name " +
      "GROUP BY allergy.name"
    ;

    ResultSet result = Application.db.getData(query);

    try {
      while (result.next()) {
        allergies.add(new Allergy(
          result.getString("name"),
          result.getString("description")
        ));
      }
    } catch (SQLException e) {
      System.out.println("Ophalen data mislukt!");
    }

    return allergies;
  }

//  public ArrayList<Medicine> getMedicines()
//  {
//
//  }
//
//  public ArrayList<Health> getHealthProblems()
//  {
//
//  }

  public ArrayList<Care> getCareOfPatient()
  {
    ArrayList<Care> cares = new ArrayList<>();

    String query =
      "SELECT " +
        "care.number, " +
        "care.firstname, " +
        "care.lastname, " +
        "care.profession, " +
        "care.phonenumber, " +
        "care.email, " +
        "care.password " +
      "FROM " +
        "patient AS patient, " +
        "care AS care, " +
        "care_patient AS carePatient " +
      "WHERE carePatient.patient_number = '" + this.number + "' " +
      "AND care.number = carePatient.care_number " +
      "GROUP BY care.number"
    ;

    ResultSet result = Application.db.getData(query);

    try {
      while (result.next()) {
        cares.add(new Care(
          result.getInt("number"),
          result.getString("firstname"),
          result.getString("lastname"),
          result.getString("profession"),
          result.getInt("phonenumber"),
          result.getString("email"),
          result.getString("password")
        ));
      }
    } catch (SQLException e) {
      System.out.println("Ophalen data mislukt!");
    }

    return cares;
  }

  public void store() {

  }

  public void update()
  {
    String query =
      "UPDATE patient " +
      "SET " +
        "firstname = '" + this.firstname + "', " +
        "lastname = '" + this.lastname + "', " +
        "birthdate = '" + this.birthdate + "', " +
        "phonenumber = '" + this.phonenumber + "', " +
        "email = '" + this.email + "', " +
        "password = '" + this.password + "' " +
      "WHERE number = '" + this.number + "'"
    ;

    Application.db.storeData(query);
  }

  public Integer getNumber() {
    return number;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public LocalDate getBirthdate() {
    return birthdate;
  }

  public Integer getPhonenumber() {
    return phonenumber;
  }

  public String getEmail() {
    return email;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public void setBirthdate(LocalDate birthdate) {
    this.birthdate = birthdate;
  }

  public void setPhonenumber(Integer phonenumber) {
    this.phonenumber = phonenumber;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
