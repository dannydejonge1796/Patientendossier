package com.example.patientendossier.controller;

import com.example.patientendossier.model.Care;
import com.example.patientendossier.model.Database;
import com.example.patientendossier.model.Patient;
import com.example.patientendossier.utility.Utility;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;

public class DossierController {

  private Stage stage;
  private Database db;
  private Patient patient;
  private Care care;
  private Scene dossierScene;
  private BorderPane borderPane;

  public DossierController(Stage stage, Database db, Patient patient, Care care) {
    this.stage = stage;
    this.db = db;
    this.patient = patient;
    this.care = care;
    this.borderPane = new BorderPane();
    this.dossierScene = setDossierScene();
  }

  private Scene setDossierScene()
  {
    this.borderPane.setBottom(addBottomMenu());
    this.borderPane.setLeft(addLeftMenu());
    this.borderPane.setCenter(createProfilePane());

    return new Scene(this.borderPane);
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

    if (this.care == null) {
      Button btnLogout = new Button("Uitloggen");
      hbox.getChildren().add(btnLogout);
      btnLogout.setOnAction(e -> this.stage.setScene(new LoginController(this.stage, this.db).getPatientLoginScene()));
    } else {
      Button btnBack = new Button("Vorige");
      hbox.getChildren().add(btnBack);
      btnBack.setOnAction(e -> this.stage.setScene(new UserController(this.stage, this.db, this.care).getListScene()));
    }

    return hbox;
  }

  private VBox addLeftMenu()
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

    profileItems.get(0).setOnAction(e -> this.borderPane.setCenter(this.createProfilePane()));

    Text txtAppointments = new Text("Afspraken");
    txtAppointments.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    vbox.getChildren().add(txtAppointments);

    ArrayList<Hyperlink> appointmentItems = new ArrayList<>();
    appointmentItems.add(new Hyperlink("Afspraken"));

    for (Hyperlink item : appointmentItems) {
      vbox.getChildren().add(item);
      VBox.setMargin(item, new Insets(0, 0, 0, 20));
    }

    appointmentItems.get(0).setOnAction(e -> this.borderPane.setCenter(new AppointmentController().getAppointmentPane()));

    Text txtReport = new Text("Verslagen");
    txtReport.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    vbox.getChildren().add(txtReport);

    ArrayList<Hyperlink> reportItems = new ArrayList<>();
    reportItems.add(new Hyperlink("Verslagen"));

    for (Hyperlink item : reportItems) {
      vbox.getChildren().add(item);
      VBox.setMargin(item, new Insets(0, 0, 0, 20));
    }

    reportItems.get(0).setOnAction(e -> this.borderPane.setCenter(new ReportController().getReportPane()));

    Text txtResults = new Text("Uitslagen");
    txtResults.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    vbox.getChildren().add(txtResults);

    ArrayList<Hyperlink> resultItems = new ArrayList<>();
    resultItems.add(new Hyperlink("Uitslagen"));

    for (Hyperlink item : resultItems) {
      vbox.getChildren().add(item);
      VBox.setMargin(item, new Insets(0, 0, 0, 20));
    }

    resultItems.get(0).setOnAction(e -> this.borderPane.setCenter(new ResultController().getResultPane()));

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

    medicInfoItems.get(0).setOnAction(e -> this.borderPane.setCenter(new MedicineController().getMedicinePane()));
    medicInfoItems.get(1).setOnAction(e -> this.borderPane.setCenter(new AllergyController().getAllergyPane()));
    medicInfoItems.get(2).setOnAction(e -> this.borderPane.setCenter(new HealthController().getHealthPane()));

    return vbox;
  }

  private ScrollPane createProfilePane()
  {
    ScrollPane scroll = new ScrollPane();

    VBox vBox = new VBox();
    vBox.setMaxWidth(980);
    vBox.setPadding(new Insets(25, 25, 25, 25));
    vBox.setSpacing(15);

    Text txtWelcome = new Text("Welkom in je patiëntendossier " + patient.getFirstname() + " " + patient.getLastname() + "!");
    txtWelcome.setFont(Font.font("Arial", FontWeight.BOLD, 20));
    vBox.getChildren().add(txtWelcome);

    PatientController patientController = new PatientController(this.patient);

    GridPane profileFormGrid = patientController.addProfileForm();
    Button btnUpdate = (Button) new Utility().getNodeByRowColumnIndex(1,7,profileFormGrid);
    btnUpdate.setOnAction(e -> updateProfile(profileFormGrid, patientController));

    GridPane passFormGrid = patientController.addUpdatePasswordForm();
    Button btnUpdatePass = (Button) new Utility().getNodeByRowColumnIndex(1,3,passFormGrid);
    btnUpdatePass.setOnAction(e -> updatePassword(passFormGrid, patientController));

    vBox.getChildren().add(profileFormGrid);
    vBox.getChildren().add(passFormGrid);

    if (this.care != null) {
      Text txtAuthorize = new Text("Machtig zorgverlener");
      txtAuthorize.setFont(Font.font("Arial", FontWeight.BOLD, 16));
      vBox.getChildren().add(txtAuthorize);

      ArrayList<Care> caresOfPatient = patient.getCareOfPatient();
      TableView<Care> table = new CareController().getCareTableView(caresOfPatient);
      vBox.getChildren().add(table);

      HBox hBoxBottom = new HBox();

      Button btnUnAuthorize = new Button("Verwijder machtiging");
      btnUnAuthorize.setStyle("-fx-background-color: darkred;");
      btnUnAuthorize.setTextFill(Color.WHITE);
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

      table.setOnMouseClicked(e -> {
        btnUnAuthorize.setDisable(table.getSelectionModel().getSelectedItem() == null);
      });

      btnUnAuthorize.setOnAction(e -> {
        Integer selectedCareNumber = table.getSelectionModel().getSelectedItem().getNumber();

        if (!selectedCareNumber.equals(care.getNumber())) {
          care.unAuthorizePatient(selectedCareNumber, this.patient.getNumber());
          this.borderPane.setCenter(createProfilePane());
        } else {
          new Utility().showAlert(Alert.AlertType.ERROR, vBox.getScene().getWindow(), "Error!", "U kunt uzelf niet ontmachtigen vanuit het dossier!");
        }
      });

      btnAuthorize.setOnAction(e -> {
        String selectedCare = comboAuthorizeCare.getSelectionModel().getSelectedItem();
        if (selectedCare != null) {
          Integer selectedCareNumber = Integer.parseInt(selectedCare.split(",")[0]);

          care.authorizePatient(selectedCareNumber, this.patient.getNumber());
          this.borderPane.setCenter(createProfilePane());
        }
      });
    }

    scroll.setContent(vBox);

    return scroll;
  }

  private void updateProfile(GridPane grid, PatientController patientController)
  {
    boolean validated = patientController.validateProfile(grid);

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

      this.borderPane.setCenter(createProfilePane());
    }
  }

  private void updatePassword(GridPane grid, PatientController patientController)
  {
    boolean validated = patientController.validateNewPassword(grid);

    if (validated) {
      String newPass = ((TextField) new Utility().getNodeByRowColumnIndex(1, 1, grid)).getText();

      patient.setPassword(newPass);
      patient.update();

      this.borderPane.setCenter(createProfilePane());
    }
  }

  public Scene getDossierScene() {
    return dossierScene;
  }
}
