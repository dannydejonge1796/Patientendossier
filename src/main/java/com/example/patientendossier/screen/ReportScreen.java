package com.example.patientendossier.screen;

import javafx.scene.layout.VBox;

public class ReportScreen {

  private final VBox reportPane;

  ReportScreen()
  {
    InfoPageScreen infoPageScreen = new InfoPageScreen();
    this.reportPane = infoPageScreen.getVBoxInfoPage();

    infoPageScreen.getLblPage().setText("Verslagen");
  }

  public VBox getReportPane() {
    return reportPane;
  }
}
