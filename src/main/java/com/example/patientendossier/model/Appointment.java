package com.example.patientendossier.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {

  private Integer number;
  private Integer patientNumber;
  private Integer careNumber;
  private String description;
  private LocalDate date;
  private LocalTime time;

  public Appointment(Integer number, Integer patientNumber, Integer careNumber, String description, LocalDate date, LocalTime time)
  {
    this.number = number;
    this.patientNumber = patientNumber;
    this.careNumber = careNumber;
    this.description = description;
    this.date = date;
    this.time = time;
  }
}
