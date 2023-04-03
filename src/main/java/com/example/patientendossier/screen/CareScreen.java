package com.example.patientendossier.screen;

import com.example.patientendossier.model.Appointment;
import com.example.patientendossier.model.Care;
import com.example.patientendossier.model.Patient;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CareScreen {

  private Tab tab1;
  private final Stage stage;
  private final Care care;
  private final Scene listScene;

  public CareScreen(Stage stage, Care care) {
    this.stage = stage;
    this.care = care;
    this.listScene = this.setListScene();
  }

  private Scene setListScene()
  {
    BorderPane borderPane = new BorderPane();
    borderPane.setBottom(addBottomMenu());

    TabPane tabPane = new TabPane();

    this.tab1 = new Tab();
    tab1.setText("Patiënten");
    tab1.setContent(addPatListPane());

    Tab tab2 = new Tab();
    tab2.setText("Zorgverleners");
    tab2.setContent(addCareListPane());

    Tab tab3 = new Tab();
    tab3.setText("Afspraken");
    tab3.setContent(addAppointmentPane());

    tabPane.getTabs().addAll(tab1, tab2, tab3);

    SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
    selectionModel.select(0);

    tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

    borderPane.setCenter(tabPane);

    return new Scene(borderPane);
  }

  private VBox addAppointmentPane()
  {
    VBox vBox = new VBox();
    vBox.setPadding(new Insets(25, 25, 25, 25));
    vBox.setSpacing(20);

    Label lblMyPatients = new Label("Mijn afspraken");
    lblMyPatients.setFont(Font.font(24));

    vBox.getChildren().add(lblMyPatients);

    ArrayList<Appointment> appointments = this.care.getAppointments();
    String[] columnNames = {"Nummer", "Patiënt nummer", "Zorgverlener nummer", "Zorgverlener", "Beschrijving", "Datum", "Tijd"};
    String[] propertyNames = {"number", "patientNumber", "careNumber", "careLastname", "description", "date", "time"};

    TableView<Appointment> table = new TableScreen().createTableView(appointments, columnNames, propertyNames);

    vBox.getChildren().add(table);

    return vBox;
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

    btnLogout.setOnAction(e -> stage.setScene(new LoginScreen(this.stage).getCarerLoginScene()));

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
    hBoxTop.getChildren().add(btnAddPatient);

    vBox.getChildren().add(hBoxTop);

    ArrayList<Patient> patients = this.care.getPatients();
    String[] patientColumnNames = {"Patiënt nummer", "Voornaam", "Achternaam", "Geboortedatum", "Telefoonnummer", "Email"};
    String[] patientPropertyNames = {"number", "firstname", "lastname", "birthdate", "phonenumber", "email"};

    TableView<Patient> table = new TableScreen().createTableView(patients, patientColumnNames, patientPropertyNames);

    vBox.getChildren().add(table);

    HBox hBoxBottom = new HBox();

    Button btnRemoveFromMyPatients = new Button("Verwijder uit mijn patiënten");
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
      this.stage.setScene(new DossierScreen(this.stage, selectedPatient, this.care).getDossierScene());
    });

    btnRemoveFromMyPatients.setOnAction(e -> {
      Integer selectedPatientNumber = table.getSelectionModel().getSelectedItem().getNumber();

      care.unAuthorizePatient(this.care.getNumber(), selectedPatientNumber);
      this.tab1.setContent(addPatListPane());
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
    hBoxTop.getChildren().add(btnAddCare);

    vBox.getChildren().add(hBoxTop);

    ArrayList<Care> cares = this.care.getAllCares();
    String[] careColumnNames = {"Zorgverlener nummer", "Voornaam", "Achternaam", "Beroep", "Telefoonnummer", "Email"};
    String[] carePropertyNames = {"number", "firstname", "lastname", "profession", "phonenumber", "email"};

    TableView<Care> table = new TableScreen().createTableView(cares, careColumnNames, carePropertyNames);

    vBox.getChildren().add(table);

    HBox hBoxBottom = new HBox();

    Region regionBottom = new Region();
    HBox.setHgrow(regionBottom, Priority.ALWAYS);
    hBoxBottom.getChildren().add(regionBottom);

    Button btnUpdate = new Button("Wijzig");
    btnUpdate.setDisable(true);
    hBoxBottom.getChildren().add(btnUpdate);

    vBox.getChildren().add(hBoxBottom);

    table.setOnMouseClicked(e -> btnUpdate.setDisable(table.getSelectionModel().getSelectedItem() == null));

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
