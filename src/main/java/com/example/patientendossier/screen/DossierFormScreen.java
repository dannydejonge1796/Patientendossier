package com.example.patientendossier.screen;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

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
    vBoxFormPage.setMaxWidth(1260);
    vBoxFormPage.setPadding(new Insets(25,25,25,25));
    vBoxFormPage.setSpacing(15);

    //Nieuw label aanmaken en toevoegen
    this.lblPage = new Label();
    lblPage.getStyleClass().add("carePageLabel");
    vBoxFormPage.getChildren().add(lblPage);

    //Pane voor form aanmaken
    this.formPane = new VBox();

    //Grid voor form aanmaken
    this.gridForm = new GridPane();
    gridForm.setHgap(15);
    gridForm.setVgap(15);

    ColumnConstraints col1 = new ColumnConstraints();
    col1.setPercentWidth(20); // Set column 1 width to 20%
    ColumnConstraints col2 = new ColumnConstraints();
    col2.setPercentWidth(80); // Set column 2 width to 80%

    gridForm.getColumnConstraints().addAll(col1, col2);

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
