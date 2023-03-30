package com.example.patientendossier.controller;

import com.example.patientendossier.model.Care;
import com.example.patientendossier.model.Patient;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class HealthController {

  private DossierController dossier;
  private Patient patient;
  private Care care;
  private final VBox healthPane;

  public HealthController(DossierController dossier, Patient patient, Care care)
  {
    this.dossier = dossier;
    this.patient = patient;
    this.care = care;

    InfoPageController infoPageController = new InfoPageController();
    this.healthPane = infoPageController.getVBoxInfoPage();

    infoPageController.getLblPage().setText("Gezondheidsproblemen");
  }

  public VBox getHealthPane() {
    return healthPane;
  }
}
