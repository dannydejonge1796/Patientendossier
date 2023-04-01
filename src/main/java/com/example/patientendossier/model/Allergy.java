package com.example.patientendossier.model;

public class Allergy {

  private final String name;
  private String description;

  public Allergy(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
