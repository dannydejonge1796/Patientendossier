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

import java.time.LocalDate;
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
    this.borderPane.setBottom(this.getBottomMenu());
    this.borderPane.setLeft(this.getLeftMenu());
    this.borderPane.setCenter(this.getProfilePane());

    return new Scene(this.borderPane);
  }

  private HBox getBottomMenu()
  {
    HBox hbox = new GlobalElements().getHBoxOne();

    if (this.care == null) {
      Button btnLogout = new Button("Uitloggen");
      hbox.getChildren().add(btnLogout);
      btnLogout.setOnAction(e -> this.stage.setScene(new LoginScreen(this.stage).getPatientLoginScene()));
    } else {
      this.btnBack = new Button("Vorige");
      hbox.getChildren().add(btnBack);
      this.setBtnBack();
    }

    return hbox;
  }

  public void setBtnBack()
  {
    btnBack.setOnAction(e -> this.stage.setScene(new CareScreen(this.stage, this.care).getListScene()));
  }

  private VBox getLeftMenu()
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

    profileItems.get(0).setOnAction(e -> {
      this.borderPane.setCenter(this.getProfilePane());
      if (care != null) {
        this.setBtnBack();
      }
    });

    Text txtAppointments = new Text("Afspraken");
    txtAppointments.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    vbox.getChildren().add(txtAppointments);

    ArrayList<Hyperlink> appointmentItems = new ArrayList<>();
    appointmentItems.add(new Hyperlink("Afspraken"));

    for (Hyperlink item : appointmentItems) {
      vbox.getChildren().add(item);
      VBox.setMargin(item, new Insets(0, 0, 0, 20));
    }

    appointmentItems.get(0).setOnAction(e -> this.borderPane.setCenter(new AppointmentScreen(this, this.patient, this.care).getAppointmentPane()));

    Text txtReport = new Text("Verslagen");
    txtReport.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    vbox.getChildren().add(txtReport);

    ArrayList<Hyperlink> reportItems = new ArrayList<>();
    reportItems.add(new Hyperlink("Verslagen"));

    for (Hyperlink item : reportItems) {
      vbox.getChildren().add(item);
      VBox.setMargin(item, new Insets(0, 0, 0, 20));
    }

    reportItems.get(0).setOnAction(e -> this.borderPane.setCenter(new ReportScreen(this, this.patient, this.care).getReportPane()));

    Text txtResults = new Text("Uitslagen");
    txtResults.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    vbox.getChildren().add(txtResults);

    ArrayList<Hyperlink> resultItems = new ArrayList<>();
    resultItems.add(new Hyperlink("Uitslagen"));

    for (Hyperlink item : resultItems) {
      vbox.getChildren().add(item);
      VBox.setMargin(item, new Insets(0, 0, 0, 20));
    }

    resultItems.get(0).setOnAction(e -> this.borderPane.setCenter(new ResultScreen(this, this.patient, this.care).getResultPane()));

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

    medicInfoItems.get(0).setOnAction(e -> this.borderPane.setCenter(new MedicineScreen(this, this.patient, this.care).getMedicinePane()));
    medicInfoItems.get(1).setOnAction(e -> this.borderPane.setCenter(new AllergyScreen(this, this.patient, this.care).getAllergyPane()));
    medicInfoItems.get(2).setOnAction(e -> this.borderPane.setCenter(new HealthScreen(this, this.patient, this.care).getHealthPane()));

    return vbox;
  }

  private ScrollPane getProfilePane()
  {
    ScrollPane scroll = new ScrollPane();
    scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

    VBox vBox = new VBox();
    vBox.setMaxWidth(1000);
    vBox.setPadding(new Insets(25, 25, 25, 25));
    vBox.setSpacing(15);

    Text txtWelcome = new Text("Welkom in je patiëntendossier " + patient.getFirstname() + " " + patient.getLastname() + "!");
    txtWelcome.setFont(Font.font("Arial", FontWeight.BOLD, 20));
    vBox.getChildren().add(txtWelcome);

    ProfileFormScreen profileFormScreen = new ProfileFormScreen(this.patient, null);

    GridPane profileFormGrid = profileFormScreen.getProfileForm();
    Button btnUpdate = profileFormScreen.getBtnUpdateProfile();
    btnUpdate.setOnAction(e -> updateProfile(profileFormGrid, profileFormScreen));

    GridPane passFormGrid = profileFormScreen.getUpdatePasswordForm();
    Button btnUpdatePass = profileFormScreen.getBtnUpdatePassword();
    btnUpdatePass.setOnAction(e -> updatePassword(passFormGrid, profileFormScreen));

    vBox.getChildren().add(profileFormGrid);
    vBox.getChildren().add(passFormGrid);

    if (this.care != null) {
      Text txtAuthorize = new Text("Machtig zorgverlener");
      txtAuthorize.setFont(Font.font("Arial", FontWeight.BOLD, 16));
      vBox.getChildren().add(txtAuthorize);

      ArrayList<Care> caresOfPatient = patient.getCareOfPatient();
      String[] careColumnNames = {"Zorgverlener nummer", "Voornaam", "Achternaam", "Geboortedatum", "Beroep", "Telefoonnummer", "Email"};
      String[] carePropertyNames = {"number", "firstname", "lastname", "birthdate", "profession", "phonenumber", "email"};

      TableView<Care> table = new TableScreen().createTableView(caresOfPatient, careColumnNames, carePropertyNames);
      vBox.getChildren().add(table);

      HBox hBoxBottom = new HBox();

      Button btnUnAuthorize = new Button("Verwijder machtiging");
      btnUnAuthorize.setDisable(true);
      hBoxBottom.getChildren().add(btnUnAuthorize);

      Region regionBottom = new Region();
      HBox.setHgrow(regionBottom, Priority.ALWAYS);
      hBoxBottom.getChildren().add(regionBottom);

      VBox vBoxBottom = new VBox();
      vBoxBottom.setSpacing(10);

      ArrayList<Care> cares = care.getAllCares();

      ComboBox<String> comboAuthorizeCare = new ComboBox<>();

      for (Care currentCare : cares) {
        boolean add = true;
        for (Care careOfPatient : caresOfPatient) {
          if (currentCare.getNumber().equals(careOfPatient.getNumber())) {
            add = false;
            break;
          }
        }
        if (add) {
          comboAuthorizeCare.getItems().add(currentCare.getNumber().toString() + ", " + currentCare.getLastname());
        }
      }

      comboAuthorizeCare.getSelectionModel().selectFirst();
      vBoxBottom.getChildren().add(comboAuthorizeCare);

      Button btnAuthorize = new Button("Machtig zorgverlener");
      vBoxBottom.getChildren().add(btnAuthorize);

      hBoxBottom.getChildren().add(vBoxBottom);
      vBox.getChildren().add(hBoxBottom);

      table.setOnMouseClicked(e -> btnUnAuthorize.setDisable(table.getSelectionModel().getSelectedItem() == null));

      btnUnAuthorize.setOnAction(e -> {
        Integer selectedCareNumber = table.getSelectionModel().getSelectedItem().getNumber();

        if (!selectedCareNumber.equals(care.getNumber())) {
          care.unAuthorizePatient(selectedCareNumber, this.patient.getNumber());
          this.borderPane.setCenter(this.getProfilePane());
        } else {
          new Utility().showAlert(Alert.AlertType.ERROR, vBox.getScene().getWindow(), "Error!", "U kunt uzelf niet ontmachtigen vanuit het dossier!");
        }
      });

      btnAuthorize.setOnAction(e -> {
        String selectedCare = comboAuthorizeCare.getSelectionModel().getSelectedItem();
        if (selectedCare != null) {
          Integer selectedCareNumber = Integer.parseInt(selectedCare.split(",")[0]);

          care.authorizePatient(selectedCareNumber, this.patient.getNumber());
          this.borderPane.setCenter(this.getProfilePane());
        }
      });
    }

    scroll.setContent(vBox);

    return scroll;
  }

  private void updateProfile(GridPane grid, ProfileFormScreen profileFormScreen)
  {
    boolean validated = profileFormScreen.validateProfile(grid);

    if (validated) {
      String firstname = ((TextField) new Utility().getNodeByRowColumnIndex(1, 2, grid)).getText();
      String lastname = ((TextField) new Utility().getNodeByRowColumnIndex(1, 3, grid)).getText();
      LocalDate birth = ((DatePicker) new Utility().getNodeByRowColumnIndex(1, 4, grid)).getValue();
      String phone = ((TextField) new Utility().getNodeByRowColumnIndex(1, 5, grid)).getText();
      String email = ((TextField) new Utility().getNodeByRowColumnIndex(1, 6, grid)).getText();

      patient.setFirstname(firstname);
      patient.setLastname(lastname);
      patient.setBirthdate(birth);
      patient.setPhonenumber(Integer.parseInt(phone));
      patient.setEmail(email);
      patient.update();

      this.borderPane.setCenter(this.getProfilePane());
    }
  }

  private void updatePassword(GridPane grid, ProfileFormScreen profileFormScreen)
  {
    boolean validated = profileFormScreen.validateNewPassword(grid);

    if (validated) {
      String newPass = ((TextField) new Utility().getNodeByRowColumnIndex(1, 1, grid)).getText();

      patient.setPassword(newPass);
      patient.update();

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
