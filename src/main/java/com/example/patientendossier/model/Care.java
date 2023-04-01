package com.example.patientendossier.model;

import com.example.patientendossier.Application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Care {

  private Integer number;
  private String firstname;
  private String lastname;
  private String profession;
  private Integer phonenumber;
  private String email;
  private String password;

  public Care(Integer number, String firstname, String lastname, String profession, Integer phonenumber, String email, String password)
  {
    this.number = number;
    this.firstname = firstname;
    this.lastname = lastname;
    this.profession = profession;
    this.phonenumber = phonenumber;
    this.email = email;
    this.password = password;
  }

  public ArrayList<String> getAllAllergyNames()
  {
    ArrayList<String> allergyNames = new ArrayList<>();

    String query =
            "SELECT allergy.name " +
            "FROM allergy"
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

  public ArrayList<String> getAllHealthNames()
  {
    ArrayList<String> healthNames = new ArrayList<>();

    String query =
            "SELECT health.name " +
                    "FROM health"
            ;

    ResultSet result = Application.db.getData(query);

    try {
      while (result.next()) {
        healthNames.add(result.getString("name"));
      }
    } catch (SQLException e) {
      System.out.println("Ophalen data mislukt!");
    }

    return healthNames;
  }

  public ArrayList<String> getAllMedicineNames()
  {
    ArrayList<String> medicineNames = new ArrayList<>();

    String query =
            "SELECT medicine.name " +
                    "FROM medicine"
            ;

    ResultSet result = Application.db.getData(query);

    try {
      while (result.next()) {
        medicineNames.add(result.getString("name"));
      }
    } catch (SQLException e) {
      System.out.println("Ophalen data mislukt!");
    }

    return medicineNames;
  }

  public void unAuthorizePatient(Integer careNumber, Integer patientNumber)
  {
    String query =
      "DELETE FROM care_patient " +
      "WHERE " +
        "care_number = '" + careNumber + "' " +
      "AND " +
        "patient_number = '" + patientNumber + "'"
    ;

    Application.db.storeData(query);
  }

  public void authorizePatient(Integer careNumber, Integer patientNumber)
  {
    String query =
      "INSERT INTO care_patient " +
        "(care_number, patient_number) " +
        "VALUES " +
        "('" + careNumber + "', '" + patientNumber + "')"
    ;

    Application.db.storeData(query);
  }

  public ArrayList<Patient> getPatients()
  {
    ArrayList<Patient> patients = new ArrayList<>();

    String query =
      "SELECT " +
        "patient.number, " +
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
      "WHERE care.number = '" + this.number + "' " +
      "AND care.number = carePatient.care_number " +
      "AND patient.number = carePatient.patient_number"
    ;

    ResultSet result = Application.db.getData(query);

    try {
      while (result.next()) {
        patients.add(new Patient(
          result.getInt("number"),
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
