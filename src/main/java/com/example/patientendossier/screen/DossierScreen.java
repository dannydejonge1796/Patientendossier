package com.example.patientendossier.screen;

import com.example.patientendossier.model.Care;
import com.example.patientendossier.model.Patient;
import com.example.patientendossier.utility.Utility;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class DossierScreen {

  private final Stage stage;
  private final Patient patient;
  private final Care care;
  private final Scene dossierScene;
  private final BorderPane borderPane;
  private Button btnBack;

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
    //Menu beneden toevoegen aan border pane
    this.borderPane.setBottom(this.getBottomMenu());
    //Menu links toevoegen aan border pane
    this.borderPane.setLeft(this.getLeftMenu());
    //Menu midden toevoegen aan border pane
    this.borderPane.setCenter(this.getProfilePane());

    //Maak nieuwe scene met deze borderpane
    return new Scene(this.borderPane);
  }

  private HBox getBottomMenu()
  {
    //Herbruikbare HBox ophalen in class
    HBox hbox = new GlobalElements().getHBoxOne();

    //Als je ingelogd bent als patient
    if (this.care == null) {
      //Uitlog knop aanmaken, toevoegen
      Button btnLogout = new Button("Uitloggen");
      hbox.getChildren().add(btnLogout);
      //Terug naar login als op knop gedrukt wordt
      btnLogout.setOnAction(e -> this.stage.setScene(new LoginScreen(this.stage).getPatientLoginScene()));
    } else {
      //Terug knop aanmaken en toevoegen
      this.btnBack = new Button("Vorige");
      hbox.getChildren().add(btnBack);
      //Functie aanroepen om functionaliteit van de knop in te stellen
      this.setBtnBack();
    }

    return hbox;
  }

  public void setBtnBack()
  {
    //Terug naar de patients / zorgverlener lijst
    btnBack.setOnAction(e -> this.stage.setScene(new CareScreen(this.stage, this.care).getListScene()));
  }

  private VBox getLeftMenu()
  {
    //VBox aanmaken voor menu left
    VBox vbox = new VBox();
    vbox.setMinWidth(250);
    vbox.setPadding(new Insets(25, 25, 25, 25));
    vbox.setSpacing(5);
    vbox.setStyle("-fx-border-style: solid inside");
    vbox.setStyle("-fx-border-width: 2");
    vbox.setStyle("-fx-border-color: black");

    //Label profiel aanmaken en toevoegen
    Text txtProfile = new Text("Profiel");
    txtProfile.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    vbox.getChildren().add(txtProfile);

    //Lijst met hyperlinks voor profiel categorie
    ArrayList<Hyperlink> profileItems = new ArrayList<>();
    profileItems.add(new Hyperlink("Persoonlijke gegevens"));

    //Alle links in lijst toevoegen
    for (Hyperlink item : profileItems) {
      vbox.getChildren().add(item);
      VBox.setMargin(item, new Insets(0, 0, 0, 20));
    }

    //Hyperlink actie instellen
    profileItems.get(0).setOnAction(e -> {
      //Juist scherm als center van border pane instellen
      this.borderPane.setCenter(this.getProfilePane());
      //Als je ingelogd bent als zorgverlener
      if (care != null) {
        //Functie om functionaliteit knop in te stellen
        this.setBtnBack();
      }
    });

    //Label afspraken aanmaken en toevoegen
    Text txtAppointments = new Text("Afspraken");
    txtAppointments.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    vbox.getChildren().add(txtAppointments);

    //Lijst van hyperlinks onder categorie afspraken
    ArrayList<Hyperlink> appointmentItems = new ArrayList<>();
    appointmentItems.add(new Hyperlink("Afspraken"));

    //Hyperlinks in de lijst toevoegen
    for (Hyperlink item : appointmentItems) {
      vbox.getChildren().add(item);
      VBox.setMargin(item, new Insets(0, 0, 0, 20));
    }

    //Geklikte scherm als center van border pane instellen
    appointmentItems.get(0).setOnAction(e -> this.borderPane.setCenter(new AppointmentScreen(this, this.patient, this.care).getAppointmentPane()));

    //Label verslagen aanmaken en toevoegen
    Text txtReport = new Text("Verslagen");
    txtReport.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    vbox.getChildren().add(txtReport);

    //Lijst van hyperlinks onder categorie verslagen
    ArrayList<Hyperlink> reportItems = new ArrayList<>();
    reportItems.add(new Hyperlink("Verslagen"));

    //Hyperlinks in de lijst toevoegen
    for (Hyperlink item : reportItems) {
      vbox.getChildren().add(item);
      VBox.setMargin(item, new Insets(0, 0, 0, 20));
    }

    //Geklikte scherm als center van border pane instellen
    reportItems.get(0).setOnAction(e -> this.borderPane.setCenter(new ReportScreen(this, this.patient, this.care).getReportPane()));

    //Label uitslagen aanmaken en toevoegen
    Text txtResults = new Text("Uitslagen");
    txtResults.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    vbox.getChildren().add(txtResults);

    //Lijst van hyperlinks onder categorie uitslagen
    ArrayList<Hyperlink> resultItems = new ArrayList<>();
    resultItems.add(new Hyperlink("Uitslagen"));

    //Hyperlinks in de lijst toevoegen
    for (Hyperlink item : resultItems) {
      vbox.getChildren().add(item);
      VBox.setMargin(item, new Insets(0, 0, 0, 20));
    }

    //Geklikte scherm als center van border pane instellen
    resultItems.get(0).setOnAction(e -> this.borderPane.setCenter(new ResultScreen(this, this.patient, this.care).getResultPane()));

    //Label medische gegevens aanmaken en toevoegen
    Text txtMedicInfo = new Text("Medische gegevens");
    txtMedicInfo.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    vbox.getChildren().add(txtMedicInfo);

    //Lijst van hyperlinks onder categorie medische gegevens aanmaken
    ArrayList<Hyperlink> medicInfoItems = new ArrayList<>();
    medicInfoItems.add(new Hyperlink("Medicijnen"));
    medicInfoItems.add(new Hyperlink("Allergieën"));
    medicInfoItems.add(new Hyperlink("Gezondheidsproblemen"));

    //Hyperlinks in de lijst toevoegen
    for (Hyperlink item : medicInfoItems) {
      vbox.getChildren().add(item);
      VBox.setMargin(item, new Insets(0, 0, 0, 20));
    }

    //Geklikte scherm als center van border pane instellen
    medicInfoItems.get(0).setOnAction(e -> this.borderPane.setCenter(new MedicineScreen(this, this.patient, this.care).getMedicinePane()));
    medicInfoItems.get(1).setOnAction(e -> this.borderPane.setCenter(new AllergyScreen(this, this.patient, this.care).getAllergyPane()));
    medicInfoItems.get(2).setOnAction(e -> this.borderPane.setCenter(new HealthScreen(this, this.patient, this.care).getHealthPane()));

    return vbox;
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

  public Button getBtnBack() {
    return btnBack;
  }

  public BorderPane getBorderPane() {
    return borderPane;
  }

  public Scene getDossierScene() {
    return dossierScene;
  }
}
