package com.example.patientendossier.model;

import com.example.patientendossier.Application;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {

  private final Integer number;
  private final Integer patientNumber;
  private Integer careNumber;
  private String careLastname;
  private String description;
  private LocalDate date;
  private LocalTime time;

  public Appointment(Integer number, Integer patientNumber, Integer careNumber, String careLastname,
                     String description, LocalDate date, LocalTime time)
  {
    this.number = number;
    this.patientNumber = patientNumber;
    this.careNumber = careNumber;
    this.careLastname = careLastname;
    this.description = description;
    this.date = date;
    this.time = time;
  }

  public void delete()
  {
    //Query voor het verwijderen van een afspraak
    String query =
            "DELETE FROM appointment " +
                    "WHERE number = '" + this.number + "'"
            ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public void store()
  {
    //Query voor het aanmaken van een nieuwe afspraak
    String query =
            "INSERT INTO appointment (number, patient_number, care_number, care_lastname, description, date, time)" +
                    "VALUES ('" + this.number + "', " +
                    "'" + this.patientNumber + "', " +
                    "'" + this.careNumber + "', " +
                    "'" + this.careLastname + "', " +
                    "'" + this.description + "', " +
                    "'" + this.date + "', " +
                    "'" + this.time + "')"
            ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public void update()
  {
    //Query voor het bijwerken van een bestaande afspraak
    String query =
            "UPDATE appointment " +
                    "SET care_number = '" + this.careNumber + "', " +
                    "care_lastname = '" + this.careLastname + "', " +
                    "description = '" + this.description + "', " +
                    "date = '" + this.date + "', " +
                    "time = '" + this.time + "' " +
                    "WHERE number = '" + this.number + "'"
            ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public void setCareNumber(Integer careNumber) {
    this.careNumber = careNumber;
  }

  public void setCareLastname(String careLastname) {
    this.careLastname = careLastname;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getCareNumber() {
    return careNumber;
  }

  public String getCareLastname() {
    return careLastname;
  }

  public String getDescription() {
    return description;
  }

  public LocalDate getDate() {
    return date;
  }

  public LocalTime getTime() {
    return time;
  }

  public Integer getNumber() {
    return number;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public void setTime(LocalTime time) {
    this.time = time;
  }

  public Integer getPatientNumber() {
    return patientNumber;
  }
}
