package com.example.patientendossier.model;

import com.example.patientendossier.Application;

public class Health {

  private final String name;
  private String description;

  public Health(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public void delete(Patient patient)
  {
    //Een gezondheidsprobleem van een patient verwijderen
    String query =
            "DELETE FROM health_patient " +
                    "WHERE patient_number = '" + patient.getNumber() + "' " +
                    "AND health_name = '" + this.name + "'"
            ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public void update(Patient patient)
  {
    //Een gezondheidsprobleem van een patient bijwerken
    String query =
            "UPDATE health_patient " +
                    "SET description = '" + this.description + "' " +
                    "WHERE patient_number = '" + patient.getNumber() + "' " +
                    "AND health_name = '" + this.name + "'"
            ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public void store(Patient patient)
  {
    //Een gezondheidsprobleem toevoegen aan een patient
    String query =
            "INSERT INTO health_patient (health_name, patient_number, description)" +
                    "VALUES ('" + this.name + "', '" + patient.getNumber() + "', " +
                    "'" + this.description + "')"
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
