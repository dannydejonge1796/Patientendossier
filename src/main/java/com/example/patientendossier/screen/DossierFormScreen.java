package com.example.patientendossier.screen;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class DossierFormScreen {

  private VBox vBoxFormPage;
  private Label lblPage;
  private GridPane gridForm;
  private VBox formPane;

  public DossierFormScreen()
  {
    this.createFormPane();
  }

  private void createFormPane()
  {
    //Nieuwe VBox aanmaken
    this.vBoxFormPage = new VBox();
    vBoxFormPage.setMaxWidth(1000);
    vBoxFormPage.setPadding(new Insets(20,20,20,20));
    vBoxFormPage.setSpacing(15);

    //Nieuw label aanmaken en toevoegen
    this.lblPage = new Label();
    lblPage.setFont(Font.font("Arial", FontWeight.BOLD, 20));
    vBoxFormPage.getChildren().add(lblPage);

    //Pane voor form aanmaken
    this.formPane = new VBox();

    //Grid voor form aanmaken
    this.gridForm = new GridPane();
    gridForm.setHgap(15);
    gridForm.setVgap(15);

    //Grid aan form pane toevoegen
    formPane.getChildren().add(gridForm);

    //Form pane aan VBox toevoegen
    vBoxFormPage.getChildren().add(formPane);
  }

  public VBox getVBoxFormPage() {
    return vBoxFormPage;
  }

  public GridPane getGridForm() {
    return gridForm;
  }

  public Label getLblPage() {
    return lblPage;
  }

  public VBox getFormPane() {
    return formPane;
  }
}
