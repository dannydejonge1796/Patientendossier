package com.example.patientendossier.screen;

import com.example.patientendossier.model.Allergy;
import com.example.patientendossier.model.Appointment;
import com.example.patientendossier.model.Care;
import com.example.patientendossier.model.Patient;
import com.example.patientendossier.utility.Utility;
import com.example.patientendossier.utility.Validation;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class AppointmentScreen {

  private final DossierScreen dossier;
  private final Patient patient;
  private final Care care;
  private VBox appointmentPane;
  private InfoPageScreen infoPageScreen;
  private ComboBox<String> comboCareNames;
  private TextField tfDescription;
  private DatePicker dpDate;
  private LocalTime selectedTime;
  private Spinner<Integer> hourSpinner;
  private Spinner<Integer> minuteSpinner;

  public AppointmentScreen(DossierScreen dossier, Patient patient, Care care)
  {
    this.dossier = dossier;
    this.patient = patient;
    this.care = care;

    this.load();
  }

  private void load()
  {
    this.infoPageScreen = new InfoPageScreen();
    this.appointmentPane = infoPageScreen.getVBoxInfoPage();

    infoPageScreen.getBtnAdd().setOnAction(e -> this.loadForm(null));
    infoPageScreen.getLblPage().setText("Afspraken");
//    infoPageScreen.getHBoxTable().getChildren().add(this.loadTableView());
  }

  private void loadForm(Appointment appointment)
  {
    DossierFormScreen dossierForm = new DossierFormScreen();
    this.dossier.getBorderPane().setCenter(dossierForm.getVBoxFormPage());

    dossierForm.getLblPage().setText("Afspraken");

    GridPane gridForm = dossierForm.getGridForm();

    Label lblCares = new Label("Zorgverlener");
    gridForm.add(lblCares, 0, 1);
    lblCares.setPrefWidth(200);

    this.comboCareNames = new ComboBox<>();
    this.comboCareNames.setPrefWidth(800);
    gridForm.add(this.comboCareNames, 1, 1);

    Label lblDescription = new Label("Beschrijving");
    gridForm.add(lblDescription, 0, 2);
    lblDescription.setPrefWidth(200);

    this.tfDescription = new TextField();
    tfDescription.setPrefWidth(800);
    gridForm.add(tfDescription, 1, 2);

    Label lblDate = new Label("Datum");
    gridForm.add(lblDate, 0, 3);
    lblDate.setPrefWidth(200);

    this.dpDate = new DatePicker();
    dpDate.setValue(LocalDate.now());
    dpDate.setDayCellFactory(picker -> new DateCell() {
      @Override
      public void updateItem(LocalDate date, boolean empty) {
        super.updateItem(date, empty);
        setDisable(date.isBefore(LocalDate.now()));
      }
    });
    gridForm.add(dpDate, 1, 3);

    Label lblTime = new Label("Tijd");
    gridForm.add(lblTime, 0, 4);
    lblTime.setPrefWidth(200);

    selectedTime = LocalTime.now();

    SpinnerValueFactory<Integer> hourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, selectedTime.getHour());
    SpinnerValueFactory<Integer> minuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, selectedTime.getMinute());
    this.hourSpinner = new Spinner<>(hourFactory);
    this.minuteSpinner = new Spinner<>(minuteFactory);
    hourSpinner.setEditable(true);
    minuteSpinner.setEditable(true);

    hourSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
      // Update the selected time when the hour value changes
      selectedTime = LocalTime.of(hourSpinner.getValue(), minuteSpinner.getValue());
    });

    minuteSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
      // Update the selected time when the minute value changes
      selectedTime = LocalTime.of(hourSpinner.getValue(), minuteSpinner.getValue());
    });

    HBox hBoxTimeSpinners = new HBox();
    hBoxTimeSpinners.setSpacing(5);
    hBoxTimeSpinners.getChildren().addAll(this.hourSpinner, new Label(":"), this.minuteSpinner);

    gridForm.add(hBoxTimeSpinners, 1, 4);

    Button btnUpdate = new Button();
    gridForm.add(btnUpdate, 1, 5);
    GridPane.setHalignment(btnUpdate, HPos.RIGHT);

//    if (appointment != null) {
//      btnUpdate.setText("Wijzig");
//      comboAllergies.getItems().add(allergy.getName());
//      comboAllergies.setValue(allergy.getName());
//      tfDescription.setText(allergy.getDescription());
//
//      btnUpdate.setOnAction(e -> {
//        if (this.validateForm()) {
//          allergy.setDescription(tfDescription.getText());
//          this.patient.updateAllergy(allergy);
//          this.load();
//          this.dossier.getBorderPane().setCenter(this.allergyPane);
//        }
//      });
//    } else {
//      btnUpdate.setText("Toevoegen");
//
//      ArrayList<String> availableAllergies = new ArrayList<>();
//
//      for (String item : this.care.getAllAllergyNames()) {
//        if (!this.patient.getAllergyNames().contains(item)) {
//          availableAllergies.add(item);
//        }
//      }
//
//      this.comboAllergies.getItems().addAll(availableAllergies);
//
//      btnUpdate.setOnAction(e -> {
//        if (this.validateForm()) {
//
//          String name = comboAllergies.getValue();
//          String description = tfDescription.getText();
//          Allergy newAllergy = new Allergy(name, description);
//
//          this.patient.addAllergy(newAllergy);
//          this.load();
//          this.dossier.getBorderPane().setCenter(this.allergyPane);
//        }
//      });
//    }
  }

//  private boolean validateForm()
//  {
//    if (!new Validation().validateString(comboAllergies.getValue())) {
//      new Utility().showAlert(Alert.AlertType.ERROR, dossier.getBorderPane().getScene().getWindow(), "Error!", "Voer een allergie in!");
//      return false;
//    }
//
//    if (!new Validation().validateString(tfDescription.getText())) {
//      new Utility().showAlert(Alert.AlertType.ERROR, dossier.getBorderPane().getScene().getWindow(), "Error!", "Voer een geldige beschrijving in!");
//      return false;
//    }
//
//    return true;
//  }

//  private TableView<Allergy> loadTableView()
//  {
//    ArrayList<Allergy> allergies = patient.getAllergies();
//    String[] columnNames = {"Naam", "Beschrijving"};
//    String[] propertyNames = {"name", "description"};
//
//    TableView<Allergy> table = new TableScreen().createTableView(allergies, columnNames, propertyNames);
//
//    table.setPrefWidth(960);
//
//    table.setOnMouseClicked(e -> {
//      infoPageScreen.getBtnDelete().setDisable(table.getSelectionModel().getSelectedItem() == null);
//      infoPageScreen.getBtnUpdate().setDisable(table.getSelectionModel().getSelectedItem() == null);
//    });
//
//    infoPageScreen.getBtnDelete().setOnAction(e -> {
//      Allergy selectedAllergy = table.getSelectionModel().getSelectedItem();
//      this.patient.deleteAllergy(selectedAllergy);
//      this.load();
//      this.dossier.getBorderPane().setCenter(this.allergyPane);
//    });
//
//    infoPageScreen.getBtnUpdate().setOnAction(e -> {
//      Allergy selectedAllergy = table.getSelectionModel().getSelectedItem();
//      this.loadForm(selectedAllergy);
//    });
//
//    return table;
//  }


  public VBox getAppointmentPane() {
    return appointmentPane;
  }
}
