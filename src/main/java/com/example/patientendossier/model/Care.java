package com.example.patientendossier.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Care {

  private final Database db;
  private Integer number;
  private String firstname;
  private String lastname;
  private String profession;
  private Integer phonenumber;
  private String email;
  private String password;

  public Care(Database db, Integer number, String firstname, String lastname, String profession, Integer phonenumber, String email, String password) {
    this.db = db;
    this.number = number;
    this.firstname = firstname;
    this.lastname = lastname;
    this.profession = profession;
    this.phonenumber = phonenumber;
    this.email = email;
    this.password = password;
  }

  public void unAuthorizePatient(Integer careNumber, Integer patientNumber)
  {
    String query =
      "DELETE FROM care_patient " +
      "WHERE " +
        "careNumber = '" + careNumber + "' " +
      "AND " +
        "patientNumber = '" + patientNumber + "'"
    ;

    db.storeData(query);
  }

  public void authorizePatient(Integer careNumber, Integer patientNumber)
  {
    String query =
      "INSERT INTO care_patient " +
        "(careNumber, patientNumber) " +
        "VALUES " +
        "('" + careNumber + "', '" + patientNumber + "')"
    ;

    db.storeData(query);
  }

  public ArrayList<Patient> getPatients()
  {
    ArrayList<Patient> patients = new ArrayList<>();

    String query =
      "SELECT " +
        "patient.patientNumber, " +
        "patient.firstname, " +
        "patient.lastname, " +
        "patient.birthdate, " +
        "patient.phonenumber, " +
        "patient.email, " +
        "patient.password " +
      "FROM " +
        "patient AS patient, " +
        "care AS care, " +
        "care_patient AS carePatient " +
      "WHERE care.careNumber = '" + this.number + "' " +
      "AND care.careNumber = carePatient.careNumber " +
      "AND patient.patientNumber = carePatient.patientNumber"
    ;

    ResultSet result = db.getData(query);

    try {
      while (result.next()) {
        patients.add(new Patient(
          this.db,
          result.getInt("patientNumber"),
          result.getString("firstname"),
          result.getString("lastname"),
          result.getDate("birthdate").toLocalDate(),
          result.getInt("phonenumber"),
          result.getString("email"),
          result.getString("password")
        ));
      }
    } catch (SQLException e) {
      System.out.println("Ophalen data mislukt!");
    }

    return patients;
  }

  public ArrayList<Care> getAllCares()
  {
    ArrayList<Care> cares = new ArrayList<>();

    String query =
      "SELECT * " +
      "FROM care"
    ;

    ResultSet result = db.getData(query);

    try {
      while (result.next()) {
        cares.add(new Care(
          this.db,
          result.getInt("careNumber"),
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

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getProfession() {
    return profession;
  }

  public void setProfession(String profession) {
    this.profession = profession;
  }

  public Integer getPhonenumber() {
    return phonenumber;
  }

  public void setPhonenumber(Integer phonenumber) {
    this.phonenumber = phonenumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}