package com.example.patientendossier.controller;

import javafx.scene.control.ScrollPane;

public class AllergyController {

  private final ScrollPane allergyPane;

  AllergyController()
  {
    InfoPageController infoPageController = new InfoPageController();
    this.allergyPane = infoPageController.getInfoPagePane();

    infoPageController.getLblPage().setText("Allergieën");
  }

  public ScrollPane getAllergyPane() {
    return allergyPane;
  }
}
