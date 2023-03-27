package com.example.patientendossier.controller;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;

public class AllergyController {

  private final ScrollPane allergyPane;

  AllergyController()
  {
    InfoPageController infoPageController = new InfoPageController();
    this.allergyPane = infoPageController.getInfoPagePane();

    infoPageController.getLblPage().setText("Allergieën");
//    infoPageController.gethBoxTable().getChildren().add(this.loadTableView());
  }

//  private TableView<String> loadTableView()
//  {
//
//  }

  public ScrollPane getAllergyPane() {
    return allergyPane;
  }
}
