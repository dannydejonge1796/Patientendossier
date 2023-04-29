package com.example.patientendossier.model;

import com.example.patientendossier.Application;

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

  public void store()
  {
    //Query voor het aanmaken van een nieuw verslag
    String query =
            "INSERT INTO report (id, patient_number, date, report, made_by)" +
                    "VALUES ('" + this.id + "', " +
                    "'" + this.patientNumber + "', " +
                    "'" + this.date + "', " +
                    "'" + this.report + "', " +
                    "'" + this.madeBy + "')"
            ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public void update()
  {
    //Query voor het bijwerken van een verslag
    String query =
            "UPDATE report " +
                    "SET report = '" + this.report + "', " +
                    "made_by = '" + this.madeBy + "', " +
                    "date = '" + this.date + "' " +
                    "WHERE id = '" + this.id + "'"
            ;
    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public void delete()
  {
    //Query voor het verwijderen van een verslag
    String query =
            "DELETE FROM report " +
                    "WHERE id = '" + this.id + "'"
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

  public String getReport() {
    return report;
  }

  public void setReport(String report) {
    this.report = report;
  }
}
