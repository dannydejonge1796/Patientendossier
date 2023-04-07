package com.example.patientendossier.model;

import java.time.LocalDate;

public class Report {

  private final Integer id;
  private final Integer patientNumber;
  private String report;
  private String madeBy;
  private LocalDate date;

  public Report(Integer id, Integer patientNumber, String report, String madeBy, LocalDate date) {
    this.id = id;
    this.patientNumber = patientNumber;
    this.report = report;
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

  public String getReport() {
    return report;
  }

  public void setReport(String report) {
    this.report = report;
  }
}
