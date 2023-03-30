package com.example.patientendossier.controller;

import javafx.scene.layout.VBox;

public class ReportController {

  private final VBox reportPane;

  ReportController()
  {
    InfoPageController infoPageController = new InfoPageController();
    this.reportPane = infoPageController.getVBoxInfoPage();

    infoPageController.getLblPage().setText("Verslagen");
  }

  public VBox getReportPane() {
    return reportPane;
  }
}
