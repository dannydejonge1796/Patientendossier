package com.example.patientendossier.screens;

import com.example.patientendossier.Care;
import com.example.patientendossier.Database;
import com.example.patientendossier.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class UserLists {

  private Stage stage;
  private Database db;
  private Care care;
  private Scene listScene;

  public UserLists(Stage stage, Database db, Care care) {
    this.stage = stage;
    this.db = db;
    this.care = care;
    this.listScene = this.setListScene();
  }

  private Scene setListScene()
  {
    BorderPane borderPane = new BorderPane();

    borderPane.setBottom(addBottomMenu());

    TabPane tabPane = new TabPane();

    Tab tab1 = new Tab();
    tab1.setText("Patiënten");
    tab1.setContent(addPatListPane());

    Tab tab2 = new Tab();
    tab2.setText("Zorgverleners");
    tab2.setContent(addCareListPane());

    tabPane.getTabs().addAll(tab1, tab2);

    SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
    selectionModel.select(0);

    tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

    borderPane.setCenter(tabPane);

    return new Scene(borderPane);
  }

  private HBox addBottomMenu()
  {
    HBox hbox = new HBox();
    hbox.setPadding(new Insets(10, 25, 10, 25));
    hbox.setPrefWidth(1200);
    hbox.setSpacing(10);
    hbox.setStyle("-fx-border-style: solid inside");
    hbox.setStyle("-fx-border-width: 2");
    hbox.setStyle("-fx-border-color: black");

    Region region = new Region();
    HBox.setHgrow(region, Priority.ALWAYS);
    hbox.getChildren().add(region);

    Button btnLogout = new Button("Uitloggen");
    hbox.getChildren().add(btnLogout);

    btnLogout.setOnAction(e -> {
      stage.setScene(new LoginScreen(this.stage, this.db).getCarerLoginScene());
    });

    return hbox;
  }

  private VBox addPatListPane()
  {
    VBox vBox = new VBox();
    vBox.setPadding(new Insets(25, 25, 25, 25));
    vBox.setSpacing(20);

    HBox hBoxTop = new HBox();

    Label lblMyPatients = new Label("Mijn patiënten");
    lblMyPatients.setFont(Font.font(24));
    hBoxTop.getChildren().add(lblMyPatients);

    Region regionTop = new Region();
    HBox.setHgrow(regionTop, Priority.ALWAYS);
    hBoxTop.getChildren().add(regionTop);

    Button btnAddPatient = new Button("Nieuwe patiënt aanmaken");
    btnAddPatient.setStyle("-fx-background-color: green;");
    btnAddPatient.setTextFill(Color.WHITE);
    hBoxTop.getChildren().add(btnAddPatient);

    vBox.getChildren().add(hBoxTop);

    TableView<Patient> table = care.getPatTableView(this.care.getPatients());

    vBox.getChildren().add(table);

    HBox hBoxBottom = new HBox();

    Button btnRemoveFromMyPatients = new Button("Verwijder uit mijn patiënten");
    btnRemoveFromMyPatients.setStyle("-fx-background-color: darkred;");
    btnRemoveFromMyPatients.setTextFill(Color.WHITE);
    btnRemoveFromMyPatients.setDisable(true);
    hBoxBottom.getChildren().add(btnRemoveFromMyPatients);

    Region regionBottom = new Region();
    HBox.setHgrow(regionBottom, Priority.ALWAYS);
    hBoxBottom.getChildren().add(regionBottom);

    Button btnToDossier = new Button("Naar dossier");
    btnToDossier.setDisable(true);
    hBoxBottom.getChildren().add(btnToDossier);

    vBox.getChildren().add(hBoxBottom);

    table.setOnMouseClicked(e -> {
      btnRemoveFromMyPatients.setDisable(table.getSelectionModel().getSelectedItem() == null);
      btnToDossier.setDisable(table.getSelectionModel().getSelectedItem() == null);
    });

    btnToDossier.setOnAction(e -> {
      Patient selectedPatient = table.getSelectionModel().getSelectedItem();
      this.stage.setScene(new Dossier(this.stage, this.db, selectedPatient, this.care).getDossierScene());
    });

    btnRemoveFromMyPatients.setOnAction(e -> {

    });

    btnAddPatient.setOnAction(e -> {

    });

    return vBox;
  }

  private VBox addCareListPane()
  {
    VBox vBox = new VBox();
    vBox.setPadding(new Insets(25, 25, 25, 25));
    vBox.setSpacing(20);

    HBox hBoxTop = new HBox();

    Label lblCares = new Label("Zorgverleners");
    lblCares.setFont(Font.font(24));
    hBoxTop.getChildren().add(lblCares);

    Region regionTop = new Region();
    HBox.setHgrow(regionTop, Priority.ALWAYS);
    hBoxTop.getChildren().add(regionTop);

    Button btnAddCare = new Button("Nieuwe zorgverlener aanmaken");
    btnAddCare.setStyle("-fx-background-color: green;");
    btnAddCare.setTextFill(Color.WHITE);
    hBoxTop.getChildren().add(btnAddCare);

    vBox.getChildren().add(hBoxTop);

    TableView<Care> table = care.getCareTableView(this.care.getAllCares());

    vBox.getChildren().add(table);

    HBox hBoxBottom = new HBox();

    Region regionBottom = new Region();
    HBox.setHgrow(regionBottom, Priority.ALWAYS);
    hBoxBottom.getChildren().add(regionBottom);

    Button btnUpdate = new Button("Wijzig");
    btnUpdate.setDisable(true);
    hBoxBottom.getChildren().add(btnUpdate);

    vBox.getChildren().add(hBoxBottom);

    table.setOnMouseClicked(e -> {
      btnUpdate.setDisable(table.getSelectionModel().getSelectedItem() == null);
    });

    btnUpdate.setOnAction(e -> {
      Care selectedCare = table.getSelectionModel().getSelectedItem();
      System.out.println(selectedCare);
    });

    btnAddCare.setOnAction(e -> {

    });

    return vBox;
  }

  public Scene getListScene() {
    return this.listScene;
  }
}
