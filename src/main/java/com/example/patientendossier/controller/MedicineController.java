package com.example.patientendossier.controller;

import javafx.scene.control.ScrollPane;

public class MedicineController {

  private final ScrollPane medicinePane;

  MedicineController()
  {
    InfoPageController infoPageController = new InfoPageController();
    this.medicinePane = infoPageController.getInfoPagePane();

    infoPageController.getLblPage().setText("Medicijnen");
  }

  public ScrollPane getMedicinePane() {
    return medicinePane;
  }
}
