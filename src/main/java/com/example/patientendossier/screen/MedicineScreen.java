package com.example.patientendossier.screen;

import com.example.patientendossier.model.Care;
import com.example.patientendossier.model.Patient;
import javafx.scene.layout.VBox;

public class MedicineScreen {

  private DossierScreen dossier;
  private Patient patient;
  private Care care;
  private final VBox medicinePane;

  MedicineScreen(DossierScreen dossier, Patient patient, Care care)
  {
    this.dossier = dossier;
    this.patient = patient;
    this.care = care;

    InfoPageScreen infoPageScreen = new InfoPageScreen();
    this.medicinePane = infoPageScreen.getVBoxInfoPage();

    infoPageScreen.getLblPage().setText("Medicijnen");
  }

  public VBox getMedicinePane() {
    return medicinePane;
  }
}
