package com.example.patientendossier.model;

import com.example.patientendossier.Application;

public class Allergy {

  private final String name;
  private String description;

  public Allergy(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public void delete(Patient patient)
  {
    //Query voor het verwijderen van een allergie van deze patient
    String query =
            "DELETE FROM allergy_patient " +
                    "WHERE patient_number = '" + patient.getNumber() + "' " +
                    "AND allergy_name = '" + this.name + "'"
            ;
    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public void update(Patient patient)
  {
    //Query om een allergie van deze patient bij te werken
    String query =
            "UPDATE allergy_patient " +
                    "SET description = '" + this.description + "' " +
                    "WHERE patient_number = '" + patient.getNumber() + "' " +
                    "AND allergy_name = '" + this.name + "'"
            ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public void store(Patient patient)
  {
    //Query om allergie toe te voegen aan deze patient
    String query =
            "INSERT INTO allergy_patient (allergy_name, patient_number, description)" +
                    "VALUES ('" + this.name + "', '" + patient.getNumber() + "', '" + this.description + "')"
            ;

    //Roep store functie in db class
    Application.db.storeData(query);
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
