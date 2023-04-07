package com.example.patientendossier.model;

import java.time.LocalDate;

public class Result {

  private final Integer id;
  private final Integer patientNumber;
  private String result;
  private String madeBy;
  private LocalDate date;

  public Result(Integer id, Integer patientNumber, String result, String madeBy, LocalDate date) {
    this.id = id;
    this.patientNumber = patientNumber;
    this.result = result;
    this.madeBy = madeBy;
    this.date = date;
  }

  public Integer getPatientNumber() {
    return patientNumber;
  }

  public Integer getId() {
    return id;
  }

  public String getMadeBy() {
    return madeBy;
  }

  public void setMadeBy(String madeBy) {
    this.madeBy = madeBy;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }
}
