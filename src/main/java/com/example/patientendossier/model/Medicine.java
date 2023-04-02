package com.example.patientendossier.model;

public class Medicine {

  private final String name;
  private String description;
  private String dosage;

  public Medicine(String name, String description, String dosage) {
    this.name = name;
    this.description = description;
    this.dosage = dosage;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getDosage() {
    return dosage;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setDosage(String dosage) {
    this.dosage = dosage;
  }
}
