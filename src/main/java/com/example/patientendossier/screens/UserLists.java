package com.example.patientendossier.screens;

import com.example.patientendossier.Care;
import com.example.patientendossier.Database;
import com.example.patientendossier.Patient;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

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
    vBox.setSpacing(10);

    ArrayList<Patient> patients = care.getPatients();

    for (Patient patient : patients) {
      System.out.println(patient.getNumber());
    }

    return vBox;
  }

  private VBox addCareListPane()
  {
    VBox vBox = new VBox();
    vBox.setPadding(new Insets(25, 25, 25, 25));
    vBox.setSpacing(10);

    return vBox;
  }

  public Scene getListScene() {
    return this.listScene;
  }
}
