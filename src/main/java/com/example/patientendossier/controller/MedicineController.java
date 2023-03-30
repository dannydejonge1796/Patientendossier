package com.example.patientendossier.controller;

import com.example.patientendossier.model.Care;
import com.example.patientendossier.model.Patient;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class MedicineController {

  private DossierController dossier;
  private Patient patient;
  private Care care;
  private final VBox medicinePane;

  MedicineController(DossierController dossier, Patient patient, Care care)
  {
    this.dossier = dossier;
    this.patient = patient;
    this.care = care;

    InfoPageController infoPageController = new InfoPageController();
    this.medicinePane = infoPageController.getVBoxInfoPage();

    infoPageController.getLblPage().setText("Medicijnen");
  }

  public VBox getMedicinePane() {
    return medicinePane;
  }
}
