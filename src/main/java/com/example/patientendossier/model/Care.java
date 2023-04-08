package com.example.patientendossier.model;

import com.example.patientendossier.Application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Care {

  private final Integer number;
  private String firstname;
  private String lastname;
  private LocalDate birthdate;
  private String profession;
  private Integer phonenumber;
  private String email;
  private String password;

  public Care(Integer number, String firstname, String lastname, LocalDate birthdate, String profession, Integer phonenumber, String email, String password)
  {
    this.number = number;
    this.firstname = firstname;
    this.lastname = lastname;
    this.birthdate = birthdate;
    this.profession = profession;
    this.phonenumber = phonenumber;
    this.email = email;
    this.password = password;
  }

  public void removeCare(Integer careNumber)
  {
    String query =
            "DELETE FROM care " +
            "WHERE number = '" + careNumber + "'"
    ;

    Application.db.storeData(query);
  }

  public Integer getHighestResultId()
  {
    String query =
            "SELECT MAX(id) as highest_id " +
            "FROM result"
    ;

    ResultSet result = Application.db.getData(query);

    try {
      if (result.next()) {
        return result.getInt("highest_id");
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return null;
  }

  public Integer getHighestReportId()
  {
    String query =
            "SELECT MAX(id) as highest_id " +
                    "FROM report"
            ;

    ResultSet result = Application.db.getData(query);

    try {
      if (result.next()) {
        return result.getInt("highest_id");
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return null;
  }

  public ArrayList<Appointment> getAppointments()
  {
    ArrayList<Appointment> appointments = new ArrayList<>();

    String query =
            "SELECT * " +
                    "FROM appointment " +
                    "WHERE care_number = '" + this.number + "'"
            ;

    ResultSet result = Application.db.getData(query);

    try {
      while (result.next()) {
        appointments.add(new Appointment(
                result.getInt("number"),
                result.getInt("patient_number"),
                result.getInt("care_number"),
                result.getString("care_lastname"),
                result.getString("description"),
                result.getDate("date").toLocalDate(),
                result.getTime("time").toLocalTime()
        ));
      }
    } catch (SQLException e) {
      System.out.println("Ophalen deze data mislukt!");
    }

    return appointments;
  }

  public Integer getHighestAppointmentNumber()
  {
    String query =
            "SELECT MAX(number) as highest_number " +
            "FROM appointment"
    ;

    ResultSet result = Application.db.getData(query);

    try {
      if (result.next()) {
        return result.getInt("highest_number");
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return null;
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

  public ArrayList<String> getCareStrings()
  {
    ArrayList<String> careStrings = new ArrayList<>();

    String query =
            "SELECT " +
                    "care.number," +
                    "care.lastname " +
            "FROM care"
    ;

    ResultSet result = Application.db.getData(query);

    try {
      while (result.next()) {
        careStrings.add(result.getString("number") + ", " + result.getString("lastname"));
      }
    } catch (SQLException e) {
      System.out.println("Ophalen data mislukt!");
    }

    return careStrings;
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
          result.getDate("birthdate").toLocalDate(),
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

  public void store()
  {
    String query = "INSERT INTO care (number, firstname, lastname, birthdate, profession, phonenumber, email, password) " +
            "VALUES ('" + this.number + "', '" +
            this.firstname + "', '" +
            this.lastname + "', '" +
            this.birthdate + "', '" +
            this.profession + "', '" +
            this.phonenumber + "', '" +
            this.email + "', '" +
            this.password + "')"
    ;

    Application.db.storeData(query);
  }

  public void update()
  {
    String query =
            "UPDATE care " +
            "SET " +
            "firstname = '" + this.firstname + "', " +
            "lastname = '" + this.lastname + "', " +
            "birthdate = '" + this.birthdate + "', " +
            "profession = '" + this.profession + "', " +
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

  public LocalDate getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(LocalDate birthdate) {
    this.birthdate = birthdate;
  }
}
