package com.example.patientendossier.controller;

import javafx.scene.control.ScrollPane;

public class HealthController {

  private final ScrollPane healthPane;

  HealthController()
  {
    InfoPageController infoPageController = new InfoPageController();
    this.healthPane = infoPageController.getInfoPagePane();

    infoPageController.getLblPage().setText("Gezondheidsproblemen");
  }

  public ScrollPane getHealthPane() {
    return healthPane;
  }
}
