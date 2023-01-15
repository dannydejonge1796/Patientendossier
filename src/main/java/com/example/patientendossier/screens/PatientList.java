package com.example.patientendossier.screens;

import com.example.patientendossier.Care;
import com.example.patientendossier.Database;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PatientList {

  private Stage stage;
  private Database db;
  private Care care;
  private BorderPane borderPane;
  private Scene patientListScene;

  public PatientList(Stage stage, Database db, Care care) {
    this.stage = stage;
    this.db = db;
    this.care = care;
    this.borderPane = new BorderPane();
    this.patientListScene = this.setPatientListScene();
  }

  private Scene setPatientListScene()
  {
    this.borderPane.setTop(addTopMenu());
    this.borderPane.setCenter(addPatListPane());

    return new Scene(this.borderPane);
  }

  private HBox addTopMenu()
  {
    HBox hbox = new HBox();
    hbox.setPadding(new Insets(25, 25, 25, 25));
    hbox.setSpacing(10);
    hbox.setStyle("-fx-border-style: solid inside");
    hbox.setStyle("-fx-border-width: 2");
    hbox.setStyle("-fx-border-color: black");

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

    return vBox;
  }

  public Scene getPatientListScene() {
    return patientListScene;
  }
}
