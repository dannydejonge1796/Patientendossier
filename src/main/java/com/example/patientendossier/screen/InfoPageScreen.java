package com.example.patientendossier.screen;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.jar.JarEntry;

public class InfoPageScreen {

  private VBox vBoxInfoPage;
  private Label lblPage;
  private Button btnAdd;
  private Button btnDelete;
  private Button btnUpdate;
  private HBox hBoxTable;

  public InfoPageScreen()
  {
    this.createInfoPagePane();
  }

  private void createInfoPagePane()
  {
    this.vBoxInfoPage = new VBox();
    vBoxInfoPage.setMaxWidth(1000);
    vBoxInfoPage.setPadding(new Insets(20,20,20,20));
    vBoxInfoPage.setSpacing(15);

    HBox hBoxTop = new HBox();
    hBoxTop.setPrefWidth(950);

    HBox hBoxLblPage = new HBox();
    this.lblPage = new Label();
    lblPage.setFont(Font.font("Arial", FontWeight.BOLD, 20));
    hBoxLblPage.getChildren().add(lblPage);
    HBox.setHgrow(hBoxLblPage, Priority.SOMETIMES);

    this.btnAdd = new Button("Toevoegen");

    hBoxTop.getChildren().addAll(hBoxLblPage, this.btnAdd);
    vBoxInfoPage.getChildren().add(hBoxTop);

    this.hBoxTable = new HBox();
    hBoxTable.setPrefWidth(950);
    vBoxInfoPage.getChildren().add(hBoxTable);

    HBox hBoxBottom = new HBox();

    this.btnDelete = new Button("Verwijderen");
    this.btnDelete.setDisable(true);

    Region regionBottom = new Region();
    HBox.setHgrow(regionBottom, Priority.ALWAYS);

    this.btnUpdate = new Button("Wijzigen");
    this.btnUpdate.setDisable(true);

    hBoxBottom.getChildren().addAll(btnDelete, regionBottom, btnUpdate);
    vBoxInfoPage.getChildren().add(hBoxBottom);
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

  public Button getBtnUpdate() {
    return btnUpdate;
  }
}
