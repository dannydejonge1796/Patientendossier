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
  private Tab tab2;
  private final Stage stage;
  private final Care care;
  private final Scene listScene;
  private BorderPane borderPane;
  private Button btnBack;
  private TabPane tabPane;

  public CareScreen(Stage stage, Care care) {
    this.stage = stage;
    this.care = care;
    this.listScene = this.setListScene();
  }

  private Scene setListScene()
  {
    //Borderpane initialiseren
    this.borderPane = new BorderPane();
    //Menu beneden toevoegen
    borderPane.setBottom(addBottomMenu());

    //Tabs aanmaken
    this.tabPane = new TabPane();

    this.tab1 = new Tab();
    tab1.setText("Patiënten");
    tab1.setContent(addPatListPane());

    this.tab2 = new Tab();
    tab2.setText("Zorgverleners");
    tab2.setContent(addCareListPane());

    Tab tab3 = new Tab();
    tab3.setText("Afspraken");
    tab3.setContent(addAppointmentPane());

    //Tabs toevoegen aan de tab pane
    tabPane.getTabs().addAll(tab1, tab2, tab3);

    //Eerste tab selecteren
    SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
    selectionModel.select(0);

    //Tabs kunnen niet worden gesloten
    tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

    //Tab pane aan borderpane toevoegen
    borderPane.setCenter(tabPane);

    //Scene teruggeven
    return new Scene(borderPane);
  }

  private VBox addAppointmentPane()
  {
    //Vbox aanmaken
    VBox vBox = new VBox();
    vBox.setPadding(new Insets(25, 25, 25, 25));
    vBox.setSpacing(20);

    //Label van pagina aanmaken
    Label lblMyPatients = new Label("Mijn afspraken");
    lblMyPatients.setFont(Font.font(24));

    //Label toevoegen aan vbox
    vBox.getChildren().add(lblMyPatients);

    //Lijst met alle afspraak objecten van een zorgverlener aanmaken
    ArrayList<Appointment> appointments = this.care.getAppointments();
    //Array van kolomnamen
    String[] columnNames = {"Nummer", "Patiënt nummer", "Zorgverlener nummer", "Zorgverlener", "Beschrijving", "Datum", "Tijd"};
    //Array van attribuut namen
    String[] propertyNames = {"number", "patientNumber", "careNumber", "careLastname", "description", "date", "time"};
    //Tabel aanmaken
    TableView<Appointment> table = new TableScreen().createTableView(appointments, columnNames, propertyNames);
    //Tabel toevoegen aan vbox
    vBox.getChildren().add(table);

    return vBox;
  }

  private HBox addBottomMenu()
  {
    //HBox ophalen die vaker gebruikt wordt uit global elements class
    HBox hbox = new GlobalElements().getHBoxOne();

    //Uitlog knop toevoegen
    this.btnBack = new Button();
    hbox.getChildren().add(btnBack);
    this.setBtnBack();

    return hbox;
  }

  private void setBtnBack()
  {
    //Text knop instellen
    this.btnBack.setText("Uitloggen");
    //Actie terug naar zorgverlener login instellen
    this.btnBack.setOnAction(e -> stage.setScene(new LoginScreen(this.stage).getCarerLoginScene()));
  }

  private VBox addPatListPane()
  {
    //Vbox aanmaken
    VBox vBox = new VBox();
    vBox.setPadding(new Insets(25, 25, 25, 25));
    vBox.setSpacing(20);

    //HBox aanmaken
    HBox hBoxTop = new HBox();

    //Pagina label instellen
    Label lblMyPatients = new Label("Mijn patiënten");
    lblMyPatients.setFont(Font.font(24));
    //Label toevoegen
    hBoxTop.getChildren().add(lblMyPatients);

    //Region aanmaken en toevoegen voor ruimte tussen label en knop
    Region regionTop = new Region();
    HBox.setHgrow(regionTop, Priority.ALWAYS);
    hBoxTop.getChildren().add(regionTop);

    //Patient aanmaken knop aanmaken en toevoegen
    Button btnAddPatient = new Button("Nieuwe patiënt aanmaken");
    hBoxTop.getChildren().add(btnAddPatient);

    //HBox toevoegen aan VBox
    vBox.getChildren().add(hBoxTop);

    //Lijst met alle patient objecten waarvoor een zorgverlener bevoegd is
    ArrayList<Patient> patients = this.care.getPatients();
    //Array met kolom namen
    String[] patientColumnNames = {"Patiënt nummer", "Voornaam", "Achternaam", "Geboortedatum", "Telefoonnummer", "Email"};
    //Array met attribuut namen
    String[] patientPropertyNames = {"number", "firstname", "lastname", "birthdate", "phonenumber", "email"};
    //Tabel aanmaken
    TableView<Patient> table = new TableScreen().createTableView(patients, patientColumnNames, patientPropertyNames);
    //Tabel toevoegen aan vbox
    vBox.getChildren().add(table);
    //HBox aanmaken
    HBox hBoxBottom = new HBox();

    //Patient verwijder-knop aanmaken en toevoegen
    Button btnRemoveFromMyPatients = new Button("Verwijder uit mijn patiënten");
    btnRemoveFromMyPatients.setDisable(true);
    hBoxBottom.getChildren().add(btnRemoveFromMyPatients);

    //Region aanmaken en toevoegen voor ruimte tussen de 2 knoppen
    Region regionBottom = new Region();
    HBox.setHgrow(regionBottom, Priority.ALWAYS);
    hBoxBottom.getChildren().add(regionBottom);

    //Naar het dossier knop aanmaken en toevoegen
    Button btnToDossier = new Button("Naar dossier");
    btnToDossier.setDisable(true);
    hBoxBottom.getChildren().add(btnToDossier);
    //HBox beneden toevoegen aan VBox
    vBox.getChildren().add(hBoxBottom);

    //Wanneer een patient is geselecteerd, maak de knoppen verwijder en naar dossier beschikbaar
    table.setOnMouseClicked(e -> {
      btnRemoveFromMyPatients.setDisable(table.getSelectionModel().getSelectedItem() == null);
      btnToDossier.setDisable(table.getSelectionModel().getSelectedItem() == null);
    });

    //Actie knop naar dossier
    btnToDossier.setOnAction(e -> {
      //Geselecteerde patient object ophalen
      Patient selectedPatient = table.getSelectionModel().getSelectedItem();
      //Dossier scherm inladen
      this.stage.setScene(new DossierScreen(this.stage, selectedPatient, this.care).getDossierScene());
    });

    btnRemoveFromMyPatients.setOnAction(e -> {
      //Geselecteerde patient nummer ophalen
      Integer selectedPatientNumber = table.getSelectionModel().getSelectedItem().getNumber();
      //Bevoegdheid van de zorgverlener verwijderen
      care.unAuthorizePatient(this.care.getNumber(), selectedPatientNumber);
      //Lijst met patients herladen
      this.tab1.setContent(addPatListPane());
    });

    //Patient toevoegen form inladen
    btnAddPatient.setOnAction(e -> this.prepareForm("patient", null));

    return vBox;
  }

  private void returnToTab(int tabNumber)
  {
    //Tab selecteren
    SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
    selectionModel.select(tabNumber);

    //Borderpane instellen met de tab pane
    borderPane.setCenter(tabPane);
    //Functionaliteit van terug knop wijzigen
    this.setBtnBack();
  }

  private VBox addCareListPane()
  {
    //VBox aanmaken
    VBox vBox = new VBox();
    vBox.setPadding(new Insets(25, 25, 25, 25));
    vBox.setSpacing(20);

    //HBox bovenste gedeelte aanmaken
    HBox hBoxTop = new HBox();

    //Label van de pagina aanmaken en toevoegen
    Label lblCares = new Label("Zorgverleners");
    lblCares.setFont(Font.font(24));
    hBoxTop.getChildren().add(lblCares);

    //Region aanmaken en toevoegen voor ruimte tussen het label en de knop
    Region regionTop = new Region();
    HBox.setHgrow(regionTop, Priority.ALWAYS);
    hBoxTop.getChildren().add(regionTop);

    //Knop om nieuwe zorgverlener aan te maken aanmaken en toevoegen
    Button btnAddCare = new Button("Nieuwe zorgverlener aanmaken");
    hBoxTop.getChildren().add(btnAddCare);

    //HBox boven toevoegen aan VBox
    vBox.getChildren().add(hBoxTop);

    //Lijst van alle zorgverleners aanmaken
    ArrayList<Care> cares = this.care.getAllCares();
    //Array met kolom namen aanmaken
    String[] careColumnNames = {"Zorgverlener nummer", "Voornaam", "Achternaam", "Geboortedatum", "Beroep", "Telefoonnummer", "Email"};
    //Array met attribuut namen aanmaken
    String[] carePropertyNames = {"number", "firstname", "lastname", "birthdate", "profession", "phonenumber", "email"};
    //Tabel aanmaken
    TableView<Care> table = new TableScreen().createTableView(cares, careColumnNames, carePropertyNames);
    //Tabel toevoegen aan VBox
    vBox.getChildren().add(table);

    //HBox beneden aanmaken
    HBox hBoxBottom = new HBox();

    //Verwijder zorgverlener knop aanmaken en toevoegen
    Button btnDeleteCare = new Button("Verwijder");
    btnDeleteCare.setDisable(true);
    hBoxBottom.getChildren().add(btnDeleteCare);

    //Region aanmaken en toevoegen voor ruimte tussen de twee knoppen
    Region regionBottom = new Region();
    HBox.setHgrow(regionBottom, Priority.ALWAYS);
    hBoxBottom.getChildren().add(regionBottom);

    //Wijzig zorgverlener knop aanmaken en toevoegen
    Button btnUpdate = new Button("Wijzig");
    btnUpdate.setDisable(true);
    hBoxBottom.getChildren().add(btnUpdate);

    //HBox beneden toevoegen aan VBox
    vBox.getChildren().add(hBoxBottom);

    //Wanneer een zorgverlener is geselecteerd, maak knoppen delete en update beschikbaar
    table.setOnMouseClicked(e -> {
      btnDeleteCare.setDisable(table.getSelectionModel().getSelectedItem() == null);
      btnUpdate.setDisable(table.getSelectionModel().getSelectedItem() == null);
    });

    //Actie voor delete-knop
    btnDeleteCare.setOnAction(e -> {
      //Geselecteerd care object ophalen
      Care selectedCare = table.getSelectionModel().getSelectedItem();
      //Zorgverlener verwijderen uit de database
      care.removeCare(selectedCare.getNumber());
      //Als de geselecteerde care gelijk is aan deze care
      if (selectedCare.equals(this.care)) {
        //Terug naar login scherm
        stage.setScene(new LoginScreen(this.stage).getCarerLoginScene());
      } else {
        //Anders herlaad de zorgverlener lijst
        this.tab2.setContent(addCareListPane());
      }
    });

    //Actie voor de update-knop
    btnUpdate.setOnAction(e -> {
      //Geselecteerde care object ophalen
      Care selectedCare = table.getSelectionModel().getSelectedItem();
      //Formulier laden en geselecteerde care meegeven
      this.prepareForm("care", selectedCare);
    });

    //Formulier inladen als op zorgverlener toevoegen is gedrukt
    btnAddCare.setOnAction(e -> this.prepareForm("care", null));

    return vBox;
  }

  private void prepareForm(String mode, Care careUpdate)
  {
    //Nieuw formulier scherm aanmaken
    DossierFormScreen dossierFormScreen = new DossierFormScreen();
    //Breedte aanpassen
    dossierFormScreen.getVBoxFormPage().setMaxWidth(1200);

    //Als we een patient aan het updaten zijn
    if (mode.equals("patient")) {
      //Verander het label naar patient
      dossierFormScreen.getLblPage().setText("Patiënt toevoegen");
      //Als we een zorgverlener aan het updaten zijn
    } else if (mode.equals("care")) {
      //Als er een care object is meegegeven
      if (careUpdate != null) {
        //Verander label naar zorgverlener updaten
        dossierFormScreen.getLblPage().setText("Zorgverlener updaten");
      } else {
        //Verander label naar zorgverlener toevoegen
        dossierFormScreen.getLblPage().setText("Zorgverlener toevoegen");
      }
    }

    //Verwijder alles uit de form pane
    dossierFormScreen.getFormPane().getChildren().clear();

    //Haal het profiel update form uit de class en geef patient en care mee
    ProfileFormScreen profileFormScreen = new ProfileFormScreen(null, careUpdate);

    //Haal het profiel form op in de juiste modus
    GridPane gridProfile = profileFormScreen.getProfileForm(mode);
    //Haal het wachtwoord form op
    GridPane gridPassword = profileFormScreen.getUpdatePasswordForm();

    //Voeg de formulieren toe aan het scherm
    dossierFormScreen.getFormPane().getChildren().add(gridProfile);
    dossierFormScreen.getFormPane().getChildren().add(gridPassword);

    //Submit knop toevoegen en juiste text instellen op basis van modus en meegegeven object
    Button btnSave = new Button("Toevoegen");
    if (mode.equals("care") && careUpdate != null) {
      btnSave.setText("Update");
    }

    //Knop toevoegen
    dossierFormScreen.getFormPane().getChildren().add(btnSave);

    //Submit knop actie instellen
    btnSave.setOnAction(e2 -> {
      //Als validaties true geven
      if (profileFormScreen.validateProfile(gridProfile) && profileFormScreen.validateNewPassword(gridPassword)) {
        //Als de modus patient is
        if (mode.equals("patient")) {
          //Functie om patient aan te maken aanroepen
          //Deze care meegeven om patient te linken aan deze care
          profileFormScreen.createNewPatient(this.care);
          //Herlaad pagina
          tab1.setContent(addPatListPane());
          this.returnToTab(0);
          //Als modus care is maar er geen care object is
        } else if (mode.equals("care") && careUpdate == null) {
          //Functie voor het maken van een nieuwe care aanroepen
          profileFormScreen.createNewCare();
          //Herlaad pagina
          tab2.setContent(addCareListPane());
          this.returnToTab(1);
          //Als er nu wel een care object is meegegeven
        } else if (mode.equals("care")) {
          //Functie aanroepen om de care te updaten
          profileFormScreen.updateCare();
          //Herlaad pagina
          tab2.setContent(addCareListPane());
          this.returnToTab(1);
        }
      }
    });

    //Form pagina instellen als het center van de borderpane
    this.borderPane.setCenter(dossierFormScreen.getVBoxFormPage());

    //Terug knop opnieuw instellen
    this.btnBack.setText("Vorige");
    int returnTo = mode.equals("patient") ? 0 : 1;
    this.btnBack.setOnAction(e2 -> this.returnToTab(returnTo));
  }

  public Scene getListScene() {
    return this.listScene;
  }
}
