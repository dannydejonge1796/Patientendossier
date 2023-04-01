package com.example.patientendossier.screen;

import com.example.patientendossier.model.Care;
import com.example.patientendossier.model.Patient;
import javafx.scene.layout.VBox;

public class HealthScreen {

  private DossierScreen dossier;
  private Patient patient;
  private Care care;
  private final VBox healthPane;

  public HealthScreen(DossierScreen dossier, Patient patient, Care care)
  {
    this.dossier = dossier;
    this.patient = patient;
    this.care = care;

    InfoPageScreen infoPageScreen = new InfoPageScreen();
    this.healthPane = infoPageScreen.getVBoxInfoPage();

    infoPageScreen.getLblPage().setText("Gezondheidsproblemen");
  }

  public VBox getHealthPane() {
    return healthPane;
  }
}
