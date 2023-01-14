package com.example.patientendossier.screens;

import com.example.patientendossier.Patient;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

  private VBox addProfilePane()
  {
    VBox vBox = new VBox();
    vBox.setPadding(new Insets(25, 25, 25, 25));
    vBox.setSpacing(10);

    Text txtWelcome = new Text("Welkom in je patiëntendossier " + patient.getFirstname() + " " + patient.getLastname() + "!");
    txtWelcome.setFont(Font.font("Arial", FontWeight.BOLD, 20));
    vBox.getChildren().add(txtWelcome);

    GridPane grid = new GridPane();
    grid.setHgap(20);
    grid.setVgap(10);

    Text txtProfile = new Text("Persoonlijke gegevens");
    txtProfile.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    grid.add(txtProfile, 0, 0);

    Label lblNumber = new Label("Uw patiëntnummer:");
    grid.add(lblNumber, 0, 1);
    TextField tfNumber = new TextField(patient.getNumber().toString());
    tfNumber.setPrefWidth(1200);
    tfNumber.setEditable(false);
    grid.add(tfNumber, 1, 1);

    Label lblFirstname = new Label("Voornaam:");
    grid.add(lblFirstname, 0, 2);
    TextField tfFirstname = new TextField(patient.getFirstname());
    tfFirstname.setPrefWidth(1200);
    grid.add(tfFirstname, 1, 2);

    Label lbLastname = new Label("Achternaam:");
    grid.add(lbLastname, 0, 3);
    TextField tfLastname = new TextField(patient.getLastname());
    tfLastname.setPrefWidth(1200);
    grid.add(tfLastname, 1, 3);

    Label lblBirth = new Label("Geboortedatum:");
    grid.add(lblBirth, 0, 4);
    DatePicker dpBirth = new DatePicker();
    dpBirth.setValue(patient.getBirthdate());
    dpBirth.setPrefWidth(1200);
    grid.add(dpBirth, 1, 4);

    Label lblPhone = new Label("Telefoonnummer:");
    grid.add(lblPhone, 0, 5);
    TextField tfPhone = new TextField(patient.getPhonenumber().toString());
    tfPhone.setPrefWidth(1200);
    grid.add(tfPhone, 1, 5);

    Label lblEmail = new Label("Email:");
    grid.add(lblEmail, 0, 6);
    TextField tfEmail = new TextField(patient.getEmail());
    tfEmail.setPrefWidth(1200);
    grid.add(tfEmail, 1, 6);

    Button btnUpdate = new Button("Wijzig");
    grid.add(btnUpdate, 1, 7);
    GridPane.setHalignment(btnUpdate, HPos.RIGHT);

    Text txtChangePass = new Text("Wachtwoord wijzigen");
    txtChangePass.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    grid.add(txtChangePass, 0, 8);

    Label lblNewPass = new Label("Nieuw wachtwoord:");
    grid.add(lblNewPass, 0, 9);
    PasswordField pfNewPass = new PasswordField();
    pfNewPass.setPrefWidth(1200);
    grid.add(pfNewPass, 1, 9);

    Label lblConfirmPass = new Label("Bevestig nieuw wachtwoord:");
    grid.add(lblConfirmPass, 0, 10);
    PasswordField pfConfirmPass = new PasswordField();
    pfConfirmPass.setPrefWidth(1200);
    grid.add(pfConfirmPass, 1, 10);

    Button btnUpdatePass = new Button("Wijzig");
    grid.add(btnUpdatePass, 1, 11);
    GridPane.setHalignment(btnUpdatePass, HPos.RIGHT);

    vBox.getChildren().add(grid);

    return vBox;
  }

  public Scene getDossierScene() {
    return dossierScene;
  }
}
