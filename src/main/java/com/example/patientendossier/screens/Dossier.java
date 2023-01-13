package com.example.patientendossier.screens;

import com.example.patientendossier.Patient;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Dossier {

  private Stage stage;
  private Patient patient;
  private Scene dossierScene;
  private String role;

  public Dossier(Stage stage, Patient patient, String role) {
    this.stage = stage;
    this.patient = patient;
    this.role = role;
    this.dossierScene = setDossierScene();
  }

  private Scene setDossierScene()
  {
    BorderPane borderPane = new BorderPane();

    borderPane.setTop(addTopMenu());
    borderPane.setLeft(addLeftMenu(borderPane));
    borderPane.setCenter(addProfilePane());

    return new Scene(borderPane);
  }

  private HBox addTopMenu()
  {
    HBox hbox = new HBox();
    hbox.setPadding(new Insets(25, 25, 25, 25));
    hbox.setSpacing(10);
    hbox.setStyle("-fx-border-style: solid inside");
    hbox.setStyle("-fx-border-width: 2");
    hbox.setStyle("-fx-border-color: black");

    Button btnBack = new Button("Uitloggen");
    hbox.getChildren().add(btnBack);

    return hbox;
  }

  private VBox addLeftMenu(BorderPane borderPane)
  {
    VBox vbox = new VBox();
    vbox.setMinWidth(250);
    vbox.setPadding(new Insets(25, 25, 25, 25));
    vbox.setSpacing(5);
    vbox.setStyle("-fx-border-style: solid inside");
    vbox.setStyle("-fx-border-width: 2");
    vbox.setStyle("-fx-border-color: black");

    Text txtProfile = new Text("Profiel");
    txtProfile.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    vbox.getChildren().add(txtProfile);

    ArrayList<Hyperlink> profileItems = new ArrayList<>();
    profileItems.add(new Hyperlink("Persoonlijke gegevens"));

    for (Hyperlink item : profileItems) {
      vbox.getChildren().add(item);
      VBox.setMargin(item, new Insets(0, 0, 0, 20));
    }

    profileItems.get(0).setOnAction(e -> {});

    Text txtReport = new Text("Verslagen");
    txtReport.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    vbox.getChildren().add(txtReport);

    ArrayList<Hyperlink> reportItems = new ArrayList<>();
    reportItems.add(new Hyperlink("Verslagen"));

    for (Hyperlink item : reportItems) {
      vbox.getChildren().add(item);
      VBox.setMargin(item, new Insets(0, 0, 0, 20));
    }

    reportItems.get(0).setOnAction(e -> {});

    Text txtResults = new Text("Uitslagen");
    txtResults.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    vbox.getChildren().add(txtResults);

    ArrayList<Hyperlink> resultItems = new ArrayList<>();
    resultItems.add(new Hyperlink("Uitslagen"));

    for (Hyperlink item : resultItems) {
      vbox.getChildren().add(item);
      VBox.setMargin(item, new Insets(0, 0, 0, 20));
    }

    resultItems.get(0).setOnAction(e -> {});

    Text txtAppointments = new Text("Afspraken");
    txtAppointments.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    vbox.getChildren().add(txtAppointments);

    ArrayList<Hyperlink> appointmentItems = new ArrayList<>();
    appointmentItems.add(new Hyperlink("Afspraken"));

    for (Hyperlink item : appointmentItems) {
      vbox.getChildren().add(item);
      VBox.setMargin(item, new Insets(0, 0, 0, 20));
    }

    appointmentItems.get(0).setOnAction(e -> {});

    Text txtMedicInfo = new Text("Medische gegevens");
    txtMedicInfo.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    vbox.getChildren().add(txtMedicInfo);

    ArrayList<Hyperlink> medicInfoItems = new ArrayList<>();
    medicInfoItems.add(new Hyperlink("Medicijnen"));
    medicInfoItems.add(new Hyperlink("Allergieën"));
    medicInfoItems.add(new Hyperlink("Gezondheidsproblemen"));

    for (Hyperlink item : medicInfoItems) {
      vbox.getChildren().add(item);
      VBox.setMargin(item, new Insets(0, 0, 0, 20));
    }

    medicInfoItems.get(0).setOnAction(e -> {});
    medicInfoItems.get(1).setOnAction(e -> {});
    medicInfoItems.get(2).setOnAction(e -> {});

    return vbox;
  }

  private GridPane addProfilePane()
  {
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25, 25, 25, 25));

    return grid;
  }

  public Scene getDossierScene() {
    return dossierScene;
  }
}
