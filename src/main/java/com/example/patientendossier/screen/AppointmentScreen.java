package com.example.patientendossier.screen;

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

    if (this.care != null) {
      infoPageScreen.getBtnAdd().setVisible(true);
      infoPageScreen.getBtnDelete().setVisible(true);
      infoPageScreen.getBtnUpdate().setVisible(true);
    }

    infoPageScreen.getBtnAdd().setOnAction(e -> this.loadForm(null));
    infoPageScreen.getLblPage().setText("Afspraken");
    infoPageScreen.getHBoxTable().getChildren().add(this.loadTableView());
  }

  private void loadForm(Appointment appointment)
  {
    DossierFormScreen dossierForm = new DossierFormScreen();
    this.dossier.getBorderPane().setCenter(dossierForm.getVBoxFormPage());

    Button btnBack = this.dossier.getBtnBack();
    btnBack.setOnAction(e -> {
      dossier.getBorderPane().setCenter(new AppointmentScreen(this.dossier, this.patient, this.care).getAppointmentPane());
      dossier.setBtnBack();
    });

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

    this.hourSpinner = new Spinner<>();
    this.minuteSpinner = new Spinner<>();
    this.hourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, selectedTime.getHour()));
    this.minuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, selectedTime.getMinute()));
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

    if (appointment != null) {
      btnUpdate.setText("Wijzig");

      this.comboCareNames.getItems().addAll(care.getCareStrings());
      this.comboCareNames.setValue(appointment.getCareNumber() + ", " + appointment.getCareLastname());

      this.tfDescription.setText(appointment.getDescription());

      this.dpDate.setValue(appointment.getDate());

      this.hourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, appointment.getTime().getHour()));
      this.minuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, appointment.getTime().getMinute()));

      btnUpdate.setOnAction(e -> {
        if (this.validateForm()) {

          String[] stringParts = comboCareNames.getValue().split(",\\s*");

          Integer formCareNumber = Integer.parseInt(stringParts[0]);
          String lastname = stringParts[1];

          appointment.setCareNumber(formCareNumber);
          appointment.setCareLastname(lastname);
          appointment.setDescription(tfDescription.getText());
          appointment.setDate(dpDate.getValue());
          appointment.setTime(selectedTime);

          this.patient.updateAppointment(appointment);
          this.load();
          this.dossier.getBorderPane().setCenter(this.appointmentPane);
        }
      });
    } else {
      btnUpdate.setText("Toevoegen");

      this.comboCareNames.getItems().addAll(care.getCareStrings());

      btnUpdate.setOnAction(e -> {
        if (this.validateForm()) {

          Integer appointmentNumber = care.getHighestAppointmentNumber() + 1;
          Integer patientNumber = patient.getNumber();

          String[] stringParts = comboCareNames.getValue().split(",\\s*");

          Integer careNumber = Integer.parseInt(stringParts[0]);
          String careLastName = stringParts[1];
          String description = tfDescription.getText();
          LocalDate date = dpDate.getValue();
          LocalTime time = selectedTime;

          Appointment newAppointment = new Appointment(
                  appointmentNumber, patientNumber, careNumber, careLastName, description, date, time
          );

          this.patient.addAppointment(newAppointment);
          this.load();
          this.dossier.getBorderPane().setCenter(this.appointmentPane);
        }
      });
    }
  }

  private boolean validateForm()
  {
    if (!new Validation().validateString(comboCareNames.getValue())) {
      new Utility().showAlert(Alert.AlertType.ERROR, dossier.getBorderPane().getScene().getWindow(), "Error!", "Voer een zorgverlener in!");
      return false;
    }

    if (!new Validation().validateString(tfDescription.getText())) {
      new Utility().showAlert(Alert.AlertType.ERROR, dossier.getBorderPane().getScene().getWindow(), "Error!", "Voer een geldige beschrijving in!");
      return false;
    }

    return true;
  }

  private TableView<Appointment> loadTableView()
  {
    ArrayList<Appointment> appointments = patient.getAppointments();
    String[] columnNames = {"Nummer", "Patiënt nummer", "Zorgverlener nummer", "Zorgverlener", "Beschrijving", "Datum", "Tijd"};
    String[] propertyNames = {"number", "patientNumber", "careNumber", "careLastname", "description", "date", "time"};

    TableView<Appointment> table = new TableScreen().createTableView(appointments, columnNames, propertyNames);

    table.setPrefWidth(960);

    table.setOnMouseClicked(e -> {
      infoPageScreen.getBtnDelete().setDisable(table.getSelectionModel().getSelectedItem() == null);
      infoPageScreen.getBtnUpdate().setDisable(table.getSelectionModel().getSelectedItem() == null);
    });

    infoPageScreen.getBtnDelete().setOnAction(e -> {
      Appointment selectedAppointment = table.getSelectionModel().getSelectedItem();
      this.patient.deleteAppointment(selectedAppointment);
      this.load();
      this.dossier.getBorderPane().setCenter(this.appointmentPane);
    });

    infoPageScreen.getBtnUpdate().setOnAction(e -> {
      Appointment selectedAppointment = table.getSelectionModel().getSelectedItem();
      this.loadForm(selectedAppointment);
    });

    return table;
  }

  public VBox getAppointmentPane() {
    return appointmentPane;
  }
}
