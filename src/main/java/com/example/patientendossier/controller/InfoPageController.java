package com.example.patientendossier.controller;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class InfoPageController {

  private final ScrollPane infoPagePane;
  private Label lblPage;
  private HBox hBoxTable;

  InfoPageController()
  {
    this.infoPagePane = new ScrollPane();
    this.createInfoPagePane();
  }

  private void createInfoPagePane()
  {
    VBox vBoxInfoPage = new VBox();
    vBoxInfoPage.setPadding(new Insets(25,25,25,25));
    vBoxInfoPage.setSpacing(15);

    this.lblPage = new Label();
    lblPage.setFont(Font.font("Arial", FontWeight.BOLD, 20));
    vBoxInfoPage.getChildren().add(lblPage);

    this.hBoxTable = new HBox();
    vBoxInfoPage.getChildren().add(hBoxTable);

    this.infoPagePane.setContent(vBoxInfoPage);
  }

  public ScrollPane getInfoPagePane() {
    return infoPagePane;
  }

  public Label getLblPage() {
    return lblPage;
  }

  public HBox gethBoxTable() {
    return hBoxTable;
  }
}
