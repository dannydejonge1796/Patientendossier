package com.example.patientendossier.controller;

import javafx.scene.layout.VBox;

public class ResultController {

  private final VBox resultPane;

  ResultController()
  {
    InfoPageController infoPageController = new InfoPageController();
    this.resultPane = infoPageController.getVBoxInfoPage();

    infoPageController.getLblPage().setText("Uitslagen");
  }

  public VBox getResultPane() {
    return resultPane;
  }
}
