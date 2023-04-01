package com.example.patientendossier.screen;

import javafx.scene.layout.VBox;

public class ResultScreen {

  private final VBox resultPane;

  ResultScreen()
  {
    InfoPageScreen infoPageScreen = new InfoPageScreen();
    this.resultPane = infoPageScreen.getVBoxInfoPage();

    infoPageScreen.getLblPage().setText("Uitslagen");
  }

  public VBox getResultPane() {
    return resultPane;
  }
}
