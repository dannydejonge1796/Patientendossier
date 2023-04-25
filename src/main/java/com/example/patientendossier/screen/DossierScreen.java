package com.example.patientendossier.screen;

import com.example.patientendossier.model.Care;
import com.example.patientendossier.model.Patient;
import com.example.patientendossier.utility.Utility;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.ArrayList;

public class DossierScreen {

  private final Stage stage;
  private final Patient patient;
  private final Care care;
  private final Scene dossierScene;
  private final BorderPane borderPane;
  private Label lblBack;

  public DossierScreen(Stage stage, Patient patient, Care care)
  {
    this.stage = stage;
    this.patient = patient;
    this.care = care;
    this.borderPane = new BorderPane();
    this.dossierScene = setDossierScene();
  }

  private Scene setDossierScene()
  {
    //Nav toevoegen
    this.borderPane.setTop(this.getNav());

    //Menu midden toevoegen aan border pane
    this.borderPane.setCenter(this.getProfilePane());

    //Maak nieuwe scene met deze borderpane
    return new Scene(this.borderPane);
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

    Label lblProfile = new Label("Profiel");
    lblProfile.getStyleClass().add("navLabel");
    lblProfile.setCursor(Cursor.HAND);
    lblProfile.setOnMouseClicked(event -> {
      //Juiste scherm als center van border pane instellen
      this.borderPane.setCenter(this.getProfilePane());
      //Als je ingelogd bent als zorgverlener
      if (care != null) {
        //Functie om functionaliteit knop in te stellen
        this.setLblBack();
      }
    });

    Label lblApp = new Label("Afspraken");
    lblApp.getStyleClass().add("navLabel");
    lblApp.setCursor(Cursor.HAND);
    lblApp.setOnMouseClicked(event -> this.borderPane.setCenter(new AppointmentScreen(this, this.patient, this.care).getAppointmentPane()));

    Label lblReport = new Label("Verslagen");
    lblReport.getStyleClass().add("navLabel");
    lblReport.setCursor(Cursor.HAND);
    lblReport.setOnMouseClicked(event -> this.borderPane.setCenter(new ReportScreen(this, this.patient, this.care).getReportPane()));

    Label lblResult = new Label("Uitslagen");
    lblResult.getStyleClass().add("navLabel");
    lblResult.setCursor(Cursor.HAND);
    lblResult.setOnMouseClicked(event -> this.borderPane.setCenter(new ResultScreen(this, this.patient, this.care).getResultPane()));

    Label lblMedic = new Label("Medische gegevens");
    lblMedic.getStyleClass().add("navLabel");
    lblMedic.setCursor(javafx.scene.Cursor.HAND);

    ContextMenu contextMenuMedic = new ContextMenu();
    contextMenuMedic.getStyleClass().add("context-menu");
    MenuItem medicineItem = new MenuItem("Medicijnen");
    MenuItem allergyItem = new MenuItem("Allergieën");
    MenuItem healthItem = new MenuItem("Gezondheidsproblemen");
    contextMenuMedic.getItems().addAll(medicineItem, allergyItem, healthItem);

    medicineItem.setStyle("-fx-border-color: lightgray; -fx-border-width: 0 0 1 0;");
    allergyItem.setStyle("-fx-border-color: lightgray; -fx-border-width: 0 0 1 0;");
    healthItem.setStyle("-fx-border-color: lightgray; -fx-border-width: 0 0 1 0;");

    lblMedic.setOnMouseClicked(e -> {
      double x = lblMedic.getScene().getWindow().getX() + lblMedic.localToScene(0, 0).getX() + 16;
      double y = lblMedic.getScene().getWindow().getY() + lblMedic.localToScene(0, 0).getY() + (lblMedic.getHeight() * 2.8);
      contextMenuMedic.show(lblMedic, x, y);
    });

    medicineItem.setOnAction(e -> this.borderPane.setCenter(new MedicineScreen(this, this.patient, this.care).getMedicinePane()));

    allergyItem.setOnAction(e -> this.borderPane.setCenter(new AllergyScreen(this, this.patient, this.care).getAllergyPane()));

    healthItem.setOnAction(e -> this.borderPane.setCenter(new HealthScreen(this, this.patient, this.care).getHealthPane()));

    //Maak een label om terug te gaan
    this.lblBack = new Label();

    if (care == null) {
      lblBack.setText("Uitloggen");
      lblBack.setOnMouseClicked(e -> this.stage.setScene(new LoginScreen(this.stage).getPatientLoginScene()));
    } else {
      lblBack.setText("Vorige");
      this.setLblBack();
    }

    lblBack.getStyleClass().add("navLabel");
    lblBack.setCursor(Cursor.HAND);

    // Create a separate HBox for lblBack
    HBox lblBackContainer = new HBox();
    HBox.setHgrow(lblBackContainer, Priority.SOMETIMES);
    lblBackContainer.setAlignment(Pos.CENTER_RIGHT);

// Add lblBack to lblBackContainer
    lblBackContainer.getChildren().add(lblBack);

    //Menus toevoegen aan menubalk
    hBoxNav.getChildren().addAll(lblProfile, lblApp, lblReport, lblResult, lblMedic, lblBackContainer);

    return hBoxNav;
  }

  public void setLblBack()
  {
    //Terug naar de patients / zorgverlener lijst
    lblBack.setOnMouseClicked(e -> this.stage.setScene(new CareScreen(this.stage, this.care).getListScene()));
  }

  private ScrollPane getProfilePane()
  {
    //Scroll pane aanmaken
    ScrollPane scroll = new ScrollPane();
    scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

    //VBox aanmaken
    VBox vBox = new VBox();
    vBox.setMaxWidth(1000);
    vBox.setPadding(new Insets(25, 25, 25, 25));
    vBox.setSpacing(15);

    //Welkomst text aanmaken en toevoegen aan VBox
    Text txtWelcome = new Text("Welkom in je patiëntendossier " + patient.getFirstname() + " " + patient.getLastname() + "!");
    txtWelcome.setFont(Font.font("Arial", FontWeight.BOLD, 20));
    vBox.getChildren().add(txtWelcome);

    //Profiel form class initialiseren
    ProfileFormScreen profileFormScreen = new ProfileFormScreen(this.patient, null);

    //Profiel form patient ophalen
    GridPane profileFormGrid = profileFormScreen.getProfileForm("patient");
    //Submit knop ophalen
    Button btnUpdate = profileFormScreen.getBtnUpdateProfile();
    //Als op wijzig knop wordt gedrukt update profiel functie aanroepen
    btnUpdate.setOnAction(e -> updateProfile(profileFormGrid, profileFormScreen));

    //Wachtwoord form ophalen
    GridPane passFormGrid = profileFormScreen.getUpdatePasswordForm();
    //Submit knop ophalen
    Button btnUpdatePass = profileFormScreen.getBtnUpdatePassword();
    //Als op knop wordt gedrukt update password functie aanroepen
    btnUpdatePass.setOnAction(e -> updatePassword(passFormGrid, profileFormScreen));

    //Forms toevoegen aan de VBox
    vBox.getChildren().add(profileFormGrid);
    vBox.getChildren().add(passFormGrid);

    //Als je ingelogd bent als zorgverlener
    if (this.care != null) {
      //Maak nieuw label voor sectie van machtiging en voeg deze toe
      Text txtAuthorize = new Text("Machtig zorgverlener");
      txtAuthorize.setFont(Font.font("Arial", FontWeight.BOLD, 16));
      vBox.getChildren().add(txtAuthorize);

      //Haal alle zorgverleners van een patient op en stop ze in de lijst
      ArrayList<Care> caresOfPatient = patient.getCareOfPatient();
      //Array met kolom namen
      String[] careColumnNames = {"Zorgverlener nummer", "Voornaam", "Achternaam", "Geboortedatum", "Beroep", "Telefoonnummer", "Email"};
      //Array met attribuut namen
      String[] carePropertyNames = {"number", "firstname", "lastname", "birthdate", "profession", "phonenumber", "email"};
      //Maak de tabel aan
      TableView<Care> table = new TableScreen().createTableView(caresOfPatient, careColumnNames, carePropertyNames);
      //Voeg de tabel toe aan de VBox
      vBox.getChildren().add(table);
      //Maak een HBox aan voor de opties beneden de tabel
      HBox hBoxBottom = new HBox();
      //Maak een knop om de machtiging te verwijderen en voeg deze toe aan de HBox
      Button btnUnAuthorize = new Button("Verwijder machtiging");
      btnUnAuthorize.setDisable(true);
      hBoxBottom.getChildren().add(btnUnAuthorize);

      //Maak een regio aan voor ruimte tussen de twee nodes en voeg deze toe
      Region regionBottom = new Region();
      HBox.setHgrow(regionBottom, Priority.ALWAYS);
      hBoxBottom.getChildren().add(regionBottom);

      //Maak een VBox aan
      VBox vBoxBottom = new VBox();
      vBoxBottom.setSpacing(10);

      //Stop alle zorgverleners in een lijst
      ArrayList<Care> cares = care.getAllCares();

      //Maak een combo box aan voor de zorgverleners
      ComboBox<String> comboAuthorizeCare = new ComboBox<>();

      //Loop door alle zorgverleners heen
      for (Care currentCare : cares) {
        boolean add = true;
        //Loop door alle zorgverleners van een patient heen
        for (Care careOfPatient : caresOfPatient) {
          //Als de twee zorgverleners gelijk zijn moet deze niet worden toegevoegd om dubbele te voorkomen
          if (currentCare.getNumber().equals(careOfPatient.getNumber())) {
            add = false;
            break;
          }
        }
        //Als hij mag worden toegevoegd, voeg de zorgverlener toe aan de combobox
        if (add) {
          comboAuthorizeCare.getItems().add(currentCare.getNumber().toString() + ", " + currentCare.getLastname());
        }
      }
      //Selecteer de eerste value als default
      comboAuthorizeCare.getSelectionModel().selectFirst();
      //Voeg de combobox toe aan de vbox
      vBoxBottom.getChildren().add(comboAuthorizeCare);

      //Maak een knop om de zorgverlener een machtiging te geven en voeg deze toe
      Button btnAuthorize = new Button("Machtig zorgverlener");
      vBoxBottom.getChildren().add(btnAuthorize);

      //Voeg de vbox toe aan de HBox
      hBoxBottom.getChildren().add(vBoxBottom);
      //Voeg de HBox toe aan de VBox van de pagina
      vBox.getChildren().add(hBoxBottom);

      //Als een zorgverlener is geselecteerd, maak knop om machtiging in te trekken beschikbaar
      table.setOnMouseClicked(e -> btnUnAuthorize.setDisable(table.getSelectionModel().getSelectedItem() == null));

      //Knop om machtiging in te trekken actie
      btnUnAuthorize.setOnAction(e -> {
        //Haal het nummer op van de geselecteerde zorgverlener
        Integer selectedCareNumber = table.getSelectionModel().getSelectedItem().getNumber();

        //Als de geselecteerde zorgverlener niet gelijk is aan de ingelogde zorgverlener
        if (!selectedCareNumber.equals(care.getNumber())) {
          //Roep functie om machtiging in te trekken aan
          care.unAuthorizePatient(selectedCareNumber, this.patient.getNumber());
          //Herlaad de profiel pagina
          this.borderPane.setCenter(this.getProfilePane());
        } else {
          //Foutmelding
          new Utility().showAlert(Alert.AlertType.ERROR, vBox.getScene().getWindow(), "Error!", "U kunt uzelf niet ontmachtigen vanuit het dossier!");
        }
      });

      //Wanneer op knop machtigen wordt gedrukt
      btnAuthorize.setOnAction(e -> {
        //Haal de geselecteerde care uit de combobox
        String selectedCare = comboAuthorizeCare.getSelectionModel().getSelectedItem();
        //Check of de value niet null is
        if (selectedCare != null) {
          //Verkrijg het unieke nummer uit de string
          Integer selectedCareNumber = Integer.parseInt(selectedCare.split(",")[0]);

          //Roep de functie aan om te machtigen
          care.authorizePatient(selectedCareNumber, this.patient.getNumber());
          //Herlaad de profiel pagina
          this.borderPane.setCenter(this.getProfilePane());
        }
      });
    }

    //Voeg de vbox van de pagina toe aan de scroll pane
    scroll.setContent(vBox);

    return scroll;
  }

  private void updateProfile(GridPane grid, ProfileFormScreen profileFormScreen)
  {
    //Valideer de ingevoerde data
    boolean validated = profileFormScreen.validateProfile(grid);
    //Als alles is goedgekeurd
    if (validated) {
      //Roep functie aan om patient te updaten
      profileFormScreen.updatePatientProfile();
      //Herlaad profiel pagina
      this.borderPane.setCenter(this.getProfilePane());
    }
  }

  private void updatePassword(GridPane grid, ProfileFormScreen profileFormScreen)
  {
    //Valideer de ingevoerde data
    boolean validated = profileFormScreen.validateNewPassword(grid);
    //Als alles is goedgekeurd
    if (validated) {
      //Roep functie aan om patient te updaten
      profileFormScreen.updatePatientPassword();
      //Herlaad profiel pagina
      this.borderPane.setCenter(this.getProfilePane());
    }
  }

  public Label getLblBack() {
    return lblBack;
  }

  public BorderPane getBorderPane() {
    return borderPane;
  }

  public Scene getDossierScene() {
    return dossierScene;
  }
}
