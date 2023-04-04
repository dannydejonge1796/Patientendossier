package com.example.patientendossier.model;

public class Report {

  private final Integer id;
  private final Integer patientNumber;
  private String filename;
  private String description;
  private String madeBy;

  public Report(Integer id, Integer patientNumber, String filename, String description, String madeBy) {
    this.id = id;
    this.patientNumber = patientNumber;
    this.filename = filename;
    this.description = description;
    this.madeBy = madeBy;
  }

  public Integer getPatientNumber() {
    return patientNumber;
  }

  public Integer getId() {
    return id;
  }

  public String getFilename() {
    return filename;
  }

  public String getDescription() {
    return description;
  }

  public String getMadeBy() {
    return madeBy;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setMadeBy(String madeBy) {
    this.madeBy = madeBy;
  }
}
