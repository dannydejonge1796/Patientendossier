package com.example.patientendossier.screen;

import com.example.patientendossier.Application;
import com.example.patientendossier.model.Appointment;
import com.example.patientendossier.model.Care;
import com.example.patientendossier.model.Patient;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class CareScreen {

  private final Stage stage;
  private final Care care;
  private final Scene listScene;
  private BorderPane borderPane;
  private Label lblBack;

  public CareScreen(Stage stage, Care care) {
    this.stage = stage;
    this.care = care;
    this.listScene = this.setListScene();
  }

  private Scene setListScene()
  {
    //Borderpane initialiseren
    this.borderPane = new BorderPane();
    //Nav toevoegen
    this.borderPane.setTop(getNav());

    //Patiënt lijst aan center borderpane toevoegen
    borderPane.setCenter(addPatListPane());

    //Borderpane aan scene toevoegen
    Scene scene = new Scene(borderPane);

    //Style aan scene linken
    scene.getStylesheets().add(Objects.requireNonNull(Application.class.getResource("style/style.css")).toExternalForm());

    //Scene teruggeven
    return scene;
  }

  private HBox getNav()
  {
    //Menubar aanmaken
    HBox hBoxNav = new HBox();
    hBoxNav.setPrefHeight(100);
    hBoxNav.setPadding(new Insets(0,15,0,15));
    hBoxNav.setStyle("-fx-background-color: #21a8cc");
    hBoxNav.setAlignment(Pos.CENTER_LEFT);

    // Logo afbeelding toevoegen
    InputStream inputStream = getClass().getResourceAsStream("/com/example/patientendossier/images/logo.png");
    if (inputStream != null) {
      ImageView logo = new ImageView(new Image(inputStream));
      logo.setFitHeight(70); // Pas de hoogte van de afbeelding aan
      logo.getStyleClass().add("navIcon");
      logo.setSmooth(true);
      logo.setPreserveRatio(true); // Behoud de originele verhoudingen van de afbeelding
      hBoxNav.getChildren().add(logo);
    } else {
      System.err.println("Unable to load logo image.");
    }

    //Maak een label voor de graphic van het menu
    Label lblPatient = new Label("Patiënten");
    lblPatient.getStyleClass().add("navLabel");
    lblPatient.setCursor(Cursor.HAND);
    lblPatient.setOnMouseClicked(event -> borderPane.setCenter(addPatListPane()));

    //Maak een label voor de graphic van het menu
    Label lblCare = new Label("Zorgverleners");
    lblCare.getStyleClass().add("navLabel");
    lblCare.setCursor(Cursor.HAND);
    lblCare.setOnMouseClicked(event -> borderPane.setCenter(addCareListPane()));

    //Maak een label voor de graphic van het menu
    Label lblAppointment = new Label("Afspraken");
    lblAppointment.getStyleClass().add("navLabel");
    lblAppointment.setCursor(Cursor.HAND);
    lblAppointment.setOnMouseClicked(event -> borderPane.setCenter(addAppointmentPane()));

    //Maak een label om terug te gaan
    this.lblBack = new Label("Uitloggen");
    lblBack.getStyleClass().add("navLabel");
    lblBack.setCursor(Cursor.HAND);

    // Create a separate HBox for lblBack
    HBox lblBackContainer = new HBox();
    HBox.setHgrow(lblBackContainer, Priority.SOMETIMES);
    lblBackContainer.setAlignment(Pos.CENTER_RIGHT);

// Add lblBack to lblBackContainer
    lblBackContainer.getChildren().add(lblBack);

    //Menus toevoegen aan menubalk
    hBoxNav.getChildren().addAll(lblPatient, lblCare, lblAppointment, lblBackContainer);

    return hBoxNav;
  }

  private VBox addAppointmentPane()
  {
    this.setLblBack();

    //Vbox aanmaken
    VBox vBox = new VBox();
    vBox.setPadding(new Insets(25, 25, 25, 25));
    vBox.setSpacing(20);

    //Label van pagina aanmaken
    Label lblMyApp = new Label("Mijn afspraken");
    lblMyApp.getStyleClass().add("carePageLabel");

    //Label toevoegen aan vbox
    vBox.getChildren().add(lblMyApp);

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

  private void setLblBack()
  {
    //Text label instellen
    this.lblBack.setText("Uitloggen");
    //Actie terug naar zorgverlener login instellen
    this.lblBack.setOnMouseClicked(e -> stage.setScene(new LoginScreen(this.stage).getCarerLoginScene()));
  }

  private VBox addPatListPane()
  {
    this.setLblBack();

    //Vbox aanmaken
    VBox vBox = new VBox();
    vBox.setPadding(new Insets(25, 25, 25, 25));
    vBox.setSpacing(20);

    //HBox aanmaken
    HBox hBoxTop = new HBox();

    //Pagina label instellen
    Label lblMyPatients = new Label("Mijn patiënten");
    lblMyPatients.getStyleClass().add("carePageLabel");
    //Label toevoegen
    hBoxTop.getChildren().add(lblMyPatients);

    //Region aanmaken en toevoegen voor ruimte tussen label en knop
    Region regionTop = new Region();
    HBox.setHgrow(regionTop, Priority.ALWAYS);
    hBoxTop.getChildren().add(regionTop);

    //Patient aanmaken knop aanmaken en toevoegen
    Button btnAddPatient = new Button();
    btnAddPatient.getStyleClass().add("btnPrimary");
    //Fontawesome icon als graphic instellen
    btnAddPatient.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.PLUS));
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
    Button btnRemoveFromMyPatients = new Button();
    btnRemoveFromMyPatients.getStyleClass().add("btnPrimary");
    btnRemoveFromMyPatients.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.MINUS));
    btnRemoveFromMyPatients.setDisable(true);
    hBoxBottom.getChildren().add(btnRemoveFromMyPatients);

    //Region aanmaken en toevoegen voor ruimte tussen de 2 knoppen
    Region regionBottom = new Region();
    HBox.setHgrow(regionBottom, Priority.ALWAYS);
    hBoxBottom.getChildren().add(regionBottom);

    //Naar het dossier knop aanmaken en toevoegen
    Button btnToDossier = new Button("Naar dossier");
    btnToDossier.getStyleClass().add("btnPrimary");
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
      this.borderPane.setCenter(addPatListPane());
    });

    //Patient toevoegen form inladen
    btnAddPatient.setOnAction(e -> this.prepareForm("patient", null));

    return vBox;
  }

  private void returnToPage(String page)
  {
    //Borderpane instellen met de juiste pagina
    switch (page) {
      case "patient" -> borderPane.setCenter(addPatListPane());
      case "care" -> borderPane.setCenter(addCareListPane());
      case "appointment" -> borderPane.setCenter(addAppointmentPane());
    }

    //Functionaliteit van terug knop wijzigen
    this.setLblBack();
  }

  private VBox addCareListPane()
  {
    this.setLblBack();

    //VBox aanmaken
    VBox vBox = new VBox();
    vBox.setPadding(new Insets(25, 25, 25, 25));
    vBox.setSpacing(20);

    //HBox bovenste gedeelte aanmaken
    HBox hBoxTop = new HBox();

    //Label van de pagina aanmaken en toevoegen
    Label lblCares = new Label("Zorgverleners");
    lblCares.getStyleClass().add("carePageLabel");
    hBoxTop.getChildren().add(lblCares);

    //Region aanmaken en toevoegen voor ruimte tussen het label en de knop
    Region regionTop = new Region();
    HBox.setHgrow(regionTop, Priority.ALWAYS);
    hBoxTop.getChildren().add(regionTop);

    //Knop om nieuwe zorgverlener aan te maken aanmaken en toevoegen
    Button btnAddCare = new Button();
    btnAddCare.getStyleClass().add("btnPrimary");
    //Fontawesome icon als graphic instellen
    btnAddCare.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.PLUS));
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
    Button btnDeleteCare = new Button();
    btnDeleteCare.getStyleClass().add("btnPrimary");
    btnDeleteCare.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.MINUS));
    btnDeleteCare.setDisable(true);
    hBoxBottom.getChildren().add(btnDeleteCare);

    //Region aanmaken en toevoegen voor ruimte tussen de twee knoppen
    Region regionBottom = new Region();
    HBox.setHgrow(regionBottom, Priority.ALWAYS);
    hBoxBottom.getChildren().add(regionBottom);

    //Wijzig zorgverlener knop aanmaken en toevoegen
    Button btnUpdate = new Button();
    btnUpdate.getStyleClass().add("btnPrimary");
    btnUpdate.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.EDIT));
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
        this.borderPane.setCenter(addCareListPane());
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

    dossierFormScreen.getFormPane().setSpacing(20);

    //Voeg de formulieren toe aan het scherm
    dossierFormScreen.getFormPane().getChildren().add(gridProfile);
    dossierFormScreen.getFormPane().getChildren().add(gridPassword);

    //Submit knop toevoegen en juiste text instellen op basis van modus en meegegeven object
    Button btnSave = new Button("Toevoegen");
    btnSave.getStyleClass().add("btnPrimary");
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
          this.returnToPage("patient");
          //Als modus care is maar er geen care object is
        } else if (mode.equals("care") && careUpdate == null) {
          //Functie voor het maken van een nieuwe care aanroepen
          profileFormScreen.createNewCare();
          //Herlaad pagina
          this.returnToPage("care");
          //Als er nu wel een care object is meegegeven
        } else if (mode.equals("care")) {
          //Functie aanroepen om de care te updaten
          profileFormScreen.updateCare();
          //Herlaad pagina
          this.returnToPage("care");
        }
      }
    });

    //Form pagina instellen als het center van de borderpane
    this.borderPane.setCenter(dossierFormScreen.getVBoxFormPage());

    //Terug item opnieuw instellen
    this.lblBack.setText("Terug");
    String returnTo = mode.equals("patient") ? "patient" : "care";
    this.lblBack.setOnMouseClicked(e2 -> this.returnToPage(returnTo));
  }

  public Scene getListScene() {
    return this.listScene;
  }
}
