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

  public DossierFormScreen()
  {
    this.createFormPane();
  }

  private void createFormPane()
  {
    this.vBoxFormPage = new VBox();
    vBoxFormPage.setMaxWidth(980);
    vBoxFormPage.setPadding(new Insets(25,25,25,25));
    vBoxFormPage.setSpacing(15);

    this.lblPage = new Label();
    lblPage.setFont(Font.font("Arial", FontWeight.BOLD, 20));
    vBoxFormPage.getChildren().add(lblPage);

    this.gridForm = new GridPane();
    gridForm.setHgap(15);
    gridForm.setVgap(15);

    vBoxFormPage.getChildren().add(gridForm);
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
}
