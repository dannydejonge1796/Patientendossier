package com.example.patientendossier.controller;

import javafx.scene.control.ScrollPane;

public class ReportController {

  private final ScrollPane reportPane;

  ReportController()
  {
    InfoPageController infoPageController = new InfoPageController();
    this.reportPane = infoPageController.getInfoPagePane();

    infoPageController.getLblPage().setText("Verslagen");
  }

  public ScrollPane getReportPane() {
    return reportPane;
  }
}
