package com.example.patientendossier.model;

import com.example.patientendossier.Application;

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

  public void store()
  {
    //Query voor het maken van een nieuwe uitslag
    String query =
            "INSERT INTO result (id, patient_number, date, result, made_by)" +
                    "VALUES ('" + this.id + "', " +
                    "'" + this.patientNumber + "', " +
                    "'" + this.date + "', " +
                    "'" + this.result + "', " +
                    "'" + this.madeBy + "')"
            ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public void update()
  {
    //Query voor het bijwerken van een uitslag
    String query =
            "UPDATE result " +
                    "SET result = '" + this.result + "', " +
                    "made_by = '" + this.madeBy + "', " +
                    "date = '" + this.date + "' " +
                    "WHERE id = '" + this.id + "'"
            ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public void delete()
  {
    //Query voor het verwijderen van een uitslag
    String query =
            "DELETE FROM result " +
                    "WHERE id = '" + this.getId() + "'"
            ;

    //Roep store functie in db class
    Application.db.storeData(query);
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
