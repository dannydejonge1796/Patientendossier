package com.example.patientendossier.controller;

import javafx.scene.control.ScrollPane;

public class ResultController {

  private final ScrollPane resultPane;

  ResultController()
  {
    InfoPageController infoPageController = new InfoPageController();
    this.resultPane = infoPageController.getInfoPagePane();

    infoPageController.getLblPage().setText("Uitslagen");
  }

  public ScrollPane getResultPane() {
    return resultPane;
  }
}
