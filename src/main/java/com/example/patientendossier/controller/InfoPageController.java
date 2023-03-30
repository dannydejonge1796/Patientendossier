package com.example.patientendossier.controller;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class InfoPageController {

  private VBox vBoxInfoPage;
  private Label lblPage;
  private Button btnAdd;
  private Button btnDelete;
  private HBox hBoxTable;

  public InfoPageController()
  {
    this.createInfoPagePane();
  }

  private void createInfoPagePane()
  {
    this.vBoxInfoPage = new VBox();
    vBoxInfoPage.setMaxWidth(1000);
    vBoxInfoPage.setPadding(new Insets(25,25,25,25));
    vBoxInfoPage.setSpacing(15);

    HBox hBoxTop = new HBox();
    hBoxTop.setPrefWidth(950);

    HBox hBoxLblPage = new HBox();
    this.lblPage = new Label();
    lblPage.setFont(Font.font("Arial", FontWeight.BOLD, 20));
    hBoxLblPage.getChildren().add(lblPage);
    HBox.setHgrow(hBoxLblPage, Priority.SOMETIMES);

    this.btnAdd = new Button("Toevoegen");
    btnAdd.setStyle("-fx-background-color: green;");
    btnAdd.setTextFill(Color.WHITE);

    hBoxTop.getChildren().addAll(hBoxLblPage, this.btnAdd);
    vBoxInfoPage.getChildren().add(hBoxTop);

    this.hBoxTable = new HBox();
    hBoxTable.setPrefWidth(950);
    vBoxInfoPage.getChildren().add(hBoxTable);

    this.btnDelete = new Button("Verwijderen");
    btnDelete.setStyle("-fx-background-color: darkred;");
    btnDelete.setTextFill(Color.WHITE);
    this.btnDelete.setDisable(true);
    vBoxInfoPage.getChildren().add(btnDelete);
  }

  public VBox getVBoxInfoPage() {
    return vBoxInfoPage;
  }

  public Label getLblPage() {
    return lblPage;
  }

  public HBox getHBoxTable() {
    return hBoxTable;
  }

  public Button getBtnAdd() {
    return btnAdd;
  }

  public Button getBtnDelete() {
    return btnDelete;
  }
}
