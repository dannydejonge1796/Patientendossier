package com.example.patientendossier.model;

import com.example.patientendossier.Application;

public class Medicine {

  private final String name;
  private String description;
  private String dosage;

  public Medicine(String name, String description, String dosage) {
    this.name = name;
    this.description = description;
    this.dosage = dosage;
  }

  public void delete(Patient patient)
  {
    //Query voor het verwijderen van een medicijn uit een patient
    String query =
            "DELETE FROM medicine_patient " +
                    "WHERE patient_number = '" + patient.getNumber() + "' " +
                    "AND medicine_name = '" + this.name + "'"
            ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public void update(Patient patient)
  {
    //Query voor het bijwerken van een medicijn van een patient
    String query =
            "UPDATE medicine_patient " +
                    "SET description = '" + this.description + "', " +
                    "dosage = '" + this.dosage + "' " +
                    "WHERE patient_number = '" + patient.getNumber() + "' " +
                    "AND medicine_name = '" + this.name + "'"
            ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public void store(Patient patient)
  {
    //Query voor het toevoegen van een medicijn aan een patient
    String query =
            "INSERT INTO medicine_patient (medicine_name, patient_number, description, dosage)" +
                    "VALUES ('" + this.name + "', '" + patient.getNumber() + "', " +
                    "'" + this.description + "', " +
                    "'" + this.dosage + "')"
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
