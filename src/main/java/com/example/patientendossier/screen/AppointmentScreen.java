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

    //Laad de info pagina
    this.load();
  }

  private void load()
  {
    //Maak nieuwe info page
    this.infoPageScreen = new InfoPageScreen();
    this.appointmentPane = infoPageScreen.getVBoxInfoPage();

    //Als je ingelogd bent als zorgverlener
    if (this.care != null) {
      //Toevoeg, bijwerk en delete knop zichtbaar maken
      infoPageScreen.getBtnAdd().setVisible(true);
      infoPageScreen.getBtnDelete().setVisible(true);
      infoPageScreen.getBtnUpdate().setVisible(true);
    }

    //Wanneer op toevoeg knop wordt gedrukt form inlaad functie aanroepen
    infoPageScreen.getBtnAdd().setOnAction(e -> this.loadForm(null));
    //Label aanpassen naar huidige pagina
    infoPageScreen.getLblPage().setText("Afspraken");
    //Tabel toevoegen aan de daarvoor bestemde pane
    infoPageScreen.getHBoxTable().getChildren().add(this.loadTableView());
  }

  private void loadForm(Appointment appointment)
  {
    //Form pagina aanmaken en in de borderpane stoppen
    DossierFormScreen dossierForm = new DossierFormScreen();
    //Pagina wordt weergegeven
    this.dossier.getBorderPane().setCenter(dossierForm.getVBoxFormPage());

    //Terug knop ophalen
    Label lblBack = this.dossier.getLblBack();
    //Actie functie van terugknop wijzigen
    lblBack.setOnMouseClicked(e -> {
      //Info pagina in borderpane stoppen en wordt dan weergegeven
      dossier.getBorderPane().setCenter(new AppointmentScreen(this.dossier, this.patient, this.care).getAppointmentPane());
      //Terug knop weer andere functionaliteit geven
      dossier.setLblBack();
    });

    //Pagina label text aanpassen
    dossierForm.getLblPage().setText("Afspraken");

    //Form grid uit het dossier form halen
    GridPane gridForm = dossierForm.getGridForm();

    //Label initialiseren
    Label lblCares = new Label("Zorgverlener");
    //Toevoegen aan grid
    gridForm.add(lblCares, 0, 1);
    //Voorkeur breedte instellen
    lblCares.setPrefWidth(200);

    //Combobox initialiseren
    this.comboCareNames = new ComboBox<>();
    //Voorkeur breedte instellen
    this.comboCareNames.setPrefWidth(800);
    //Toevoegen aan grid
    gridForm.add(this.comboCareNames, 1, 1);

    //Label initialiseren
    Label lblDescription = new Label("Beschrijving");
    //Toevoegen aan grid
    gridForm.add(lblDescription, 0, 2);
    //Voorkeur breedte instellen
    lblDescription.setPrefWidth(200);

    //Textfield initialiseren
    this.tfDescription = new TextField();
    //Voorkeur breedte instellen
    tfDescription.setPrefWidth(800);
    //Toevoegen aan grid
    gridForm.add(tfDescription, 1, 2);

    //Label initialiseren
    Label lblDate = new Label("Datum");
    //Toevoegen aan grid
    gridForm.add(lblDate, 0, 3);
    //Voorkeur breedte instellen
    lblDate.setPrefWidth(200);

    //Datepicker initialiseren
    this.dpDate = new DatePicker();
    //Datepicker default value met huidige datum invullen
    dpDate.setValue(LocalDate.now());
    //Alleen datums groter of gelijk aan vandaag toestaan
    dpDate.setDayCellFactory(picker -> new DateCell() {
      @Override
      public void updateItem(LocalDate date, boolean empty) {
        super.updateItem(date, empty);
        setDisable(date.isBefore(LocalDate.now()));
      }
    });
    //Toevoegen aan grid
    gridForm.add(dpDate, 1, 3);

    //Label initialiseren
    Label lblTime = new Label("Tijd");
    //Toevoegen aan grid
    gridForm.add(lblTime, 0, 4);
    //Voorkeur breedte instellen
    lblTime.setPrefWidth(200);

    //Huidige tijd ophalen
    selectedTime = LocalTime.now();

    //Spinners initialiseren
    this.hourSpinner = new Spinner<>();
    this.minuteSpinner = new Spinner<>();
    //Spinner van 0 - 23, default is huidig uur
    this.hourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, selectedTime.getHour()));
    //Spinner van 0-59, default is huidige minuut
    this.minuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, selectedTime.getMinute()));
    //Bewerkbaar maken
    hourSpinner.setEditable(true);
    minuteSpinner.setEditable(true);

    //Wanneer value van uur spinner veranderd
    hourSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
      //Werkt tijd bij
      selectedTime = LocalTime.of(hourSpinner.getValue(), minuteSpinner.getValue());
    });
    //Wanneer value van minuut spinner veranderd
    minuteSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
      //Werk tijd bij
      selectedTime = LocalTime.of(hourSpinner.getValue(), minuteSpinner.getValue());
    });

    //HBox voor de spinners initialiseren
    HBox hBoxTimeSpinners = new HBox();
    //Spacing instellen
    hBoxTimeSpinners.setSpacing(5);
    //Spinner toevoegen
    hBoxTimeSpinners.getChildren().addAll(this.hourSpinner, new Label(":"), this.minuteSpinner);
    //Toevoegen aan de grid
    gridForm.add(hBoxTimeSpinners, 1, 4);

    //Knop initialiseren
    Button btnUpdate = new Button();
    //Toevoegen aan grid
    gridForm.add(btnUpdate, 1, 5);
    //Knop aan de rechter kant plaatsen
    GridPane.setHalignment(btnUpdate, HPos.RIGHT);

    //Als er een afspraak object is
    if (appointment != null) {
      //Verander knop text naar wijzig
      btnUpdate.setText("Wijzig");

      //Alle nummers en achternamen van zorgverleners toevoegen aan de combobox
      this.comboCareNames.getItems().addAll(care.getCareStrings());
      //Huidige zorgverlener als default instellen
      this.comboCareNames.setValue(appointment.getCareNumber() + ", " + appointment.getCareLastname());

      //Huidige descriptie als text van het text veld instellen
      this.tfDescription.setText(appointment.getDescription());

      //Huidige datum als value van de datepicker instellen
      this.dpDate.setValue(appointment.getDate());

      //Uur uit de appointment instellen voor de uur-spinner
      this.hourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, appointment.getTime().getHour()));
      //Minuut uit de appointment instellen voor de minuut-spinner
      this.minuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, appointment.getTime().getMinute()));

      //Als op knop wordt gedrukt
      btnUpdate.setOnAction(e -> {
        //Als validatie functie true teruggeeft
        if (this.validateForm()) {
          //Split de zorgverlener string in tweeën
          String[] stringParts = comboCareNames.getValue().split(",\\s*");
          Integer formCareNumber = Integer.parseInt(stringParts[0]);
          String lastname = stringParts[1];

          //Update de attributen van het object met de ingevoerde data
          appointment.setCareNumber(formCareNumber);
          appointment.setCareLastname(lastname);
          appointment.setDescription(tfDescription.getText());
          appointment.setDate(dpDate.getValue());
          appointment.setTime(selectedTime);

          //Update afspraken in de database
          this.patient.updateAppointment(appointment);

          //Herlaad de pagina
          this.load();
          this.dossier.getBorderPane().setCenter(this.appointmentPane);
        }
      });
    //Als er geen allergy object is
    } else {
      //Text van knop is toevoegen
      btnUpdate.setText("Toevoegen");

      //Voeg alle nummers en achternamen van zorgverleners toe aan de combobox
      this.comboCareNames.getItems().addAll(care.getCareStrings());

      //Als op de knop gedrukt wordt
      btnUpdate.setOnAction(e -> {
        //Als validatie functie true teruggeeft
        if (this.validateForm()) {
          //Haal het hoogste afspraak nummer uit de db en voeg hier 1 aan toe
          Integer appointmentNumber = care.getHighestAppointmentNumber() + 1;
          //Verkrijg het patient nummer van deze patient
          Integer patientNumber = patient.getNumber();

          //Haal de values uit de velden
          String[] stringParts = comboCareNames.getValue().split(",\\s*");
          Integer careNumber = Integer.parseInt(stringParts[0]);
          String careLastName = stringParts[1];
          String description = tfDescription.getText();
          LocalDate date = dpDate.getValue();
          LocalTime time = selectedTime;

          //Maak een nieuw afspraak object
          Appointment newAppointment = new Appointment(
                  appointmentNumber, patientNumber, careNumber, careLastName, description, date, time
          );

          //Voeg de afspraak toe aan de patient en de zorgverlener in de database
          this.patient.addAppointment(newAppointment);
          //Herlaad de paigna
          this.load();
          this.dossier.getBorderPane().setCenter(this.appointmentPane);
        }
      });
    }
  }

  private boolean validateForm()
  {
    //Check of de combobox een string als value heeft
    if (!new Validation().validateString(comboCareNames.getValue())) {
      //Anders alert met foutmelding
      new Utility().showAlert(Alert.AlertType.ERROR, dossier.getBorderPane().getScene().getWindow(), "Error!", "Voer een zorgverlener in!");
      return false;
    }

    //Check of het veld een string als value heeft
    if (!new Validation().validateString(tfDescription.getText())) {
      //Anders alert met foutmelding
      new Utility().showAlert(Alert.AlertType.ERROR, dossier.getBorderPane().getScene().getWindow(), "Error!", "Voer een geldige beschrijving in!");
      return false;
    }

    //Alles goed = return true
    return true;
  }

  private TableView<Appointment> loadTableView()
  {
    //Voeg alle afspraken van de patient toe aan de lijst
    ArrayList<Appointment> appointments = patient.getAppointments();
    //Maak een array met kolom namen
    String[] columnNames = {"Nummer", "Patiënt nummer", "Zorgverlener nummer", "Zorgverlener", "Beschrijving", "Datum", "Tijd"};
    //Maak een array met attribute namen van het object
    String[] propertyNames = {"number", "patientNumber", "careNumber", "careLastname", "description", "date", "time"};

    //Laad de tabel
    TableView<Appointment> table = new TableScreen().createTableView(appointments, columnNames, propertyNames);

    //Wijzig het formaat van de tabel
    table.setPrefWidth(960);

    //Wanneer een rij wordt aangeklikt, kunnen de knoppen beschikbaar worden gemaakt
    table.setOnMouseClicked(e -> {
      infoPageScreen.getBtnDelete().setDisable(table.getSelectionModel().getSelectedItem() == null);
      infoPageScreen.getBtnUpdate().setDisable(table.getSelectionModel().getSelectedItem() == null);
    });

    //Delete knop actie
    infoPageScreen.getBtnDelete().setOnAction(e -> {
      //Verkrijg de geselecteerde afspraak
      Appointment selectedAppointment = table.getSelectionModel().getSelectedItem();
      //Verwijder de afspraak uit de database
      this.patient.deleteAppointment(selectedAppointment);
      //Herlaad de pagina
      this.load();
      this.dossier.getBorderPane().setCenter(this.appointmentPane);
    });

    //Wanneer op update wordt geklikt
    infoPageScreen.getBtnUpdate().setOnAction(e -> {
      //Verkrijg de geselecteerde afspraak
      Appointment selectedAppointment = table.getSelectionModel().getSelectedItem();
      //Laad de form pagina en geef de geselecteerde afspraak mee
      this.loadForm(selectedAppointment);
    });

    return table;
  }

  public VBox getAppointmentPane() {
    return appointmentPane;
  }
}
