package com.example.patientendossier.model;

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
