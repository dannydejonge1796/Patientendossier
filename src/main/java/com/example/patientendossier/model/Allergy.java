package com.example.patientendossier.model;

import com.example.patientendossier.Application;

public class Allergy {

  private String name;
  private String description;

  public Allergy(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public void store()
  {

  }

  public void update()
  {

  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
