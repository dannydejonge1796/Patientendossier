package com.example.patientendossier.screens;

import com.example.patientendossier.Care;
import com.example.patientendossier.Database;
import com.example.patientendossier.Patient;
import com.example.patientendossier.Utility;
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

public class Dossier {

  private Stage stage;
  private Database db;
  private Patient patient;
  private Care care;
  private Scene dossierScene;
  private BorderPane borderPane;

  public Dossier(Stage stage, Database db, Patient patient, Care care) {
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
    this.borderPane.setCenter(addProfilePane());

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
      btnLogout.setOnAction(e -> this.stage.setScene(new LoginScreen(this.stage, this.db).getPatientLoginScene()));
    } else {
      Button btnBack = new Button("Vorige");
      hbox.getChildren().add(btnBack);
      btnBack.setOnAction(e -> this.stage.setScene(new UserLists(this.stage, this.db, this.care).getListScene()));
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

    profileItems.get(0).setOnAction(e -> this.borderPane.setCenter(addProfilePane()));

    Text txtAppointments = new Text("Afspraken");
    txtAppointments.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    vbox.getChildren().add(txtAppointments);

    ArrayList<Hyperlink> appointmentItems = new ArrayList<>();
    appointmentItems.add(new Hyperlink("Afspraken"));

    for (Hyperlink item : appointmentItems) {
      vbox.getChildren().add(item);
      VBox.setMargin(item, new Insets(0, 0, 0, 20));
    }

    appointmentItems.get(0).setOnAction(e -> {});

    Text txtReport = new Text("Verslagen");
    txtReport.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    vbox.getChildren().add(txtReport);

    ArrayList<Hyperlink> reportItems = new ArrayList<>();
    reportItems.add(new Hyperlink("Verslagen"));

    for (Hyperlink item : reportItems) {
      vbox.getChildren().add(item);
      VBox.setMargin(item, new Insets(0, 0, 0, 20));
    }

    reportItems.get(0).setOnAction(e -> {});

    Text txtResults = new Text("Uitslagen");
    txtResults.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    vbox.getChildren().add(txtResults);

    ArrayList<Hyperlink> resultItems = new ArrayList<>();
    resultItems.add(new Hyperlink("Uitslagen"));

    for (Hyperlink item : resultItems) {
      vbox.getChildren().add(item);
      VBox.setMargin(item, new Insets(0, 0, 0, 20));
    }

    resultItems.get(0).setOnAction(e -> {});

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

    medicInfoItems.get(0).setOnAction(e -> {});
    medicInfoItems.get(1).setOnAction(e -> {});
    medicInfoItems.get(2).setOnAction(e -> {});

    return vbox;
  }

  private ScrollPane addProfilePane()
  {
    ScrollPane scroll = new ScrollPane();

    VBox vBox = new VBox();
    vBox.setMaxWidth(980);
    vBox.setPadding(new Insets(25, 25, 25, 25));
    vBox.setSpacing(15);

    Text txtWelcome = new Text("Welkom in je patiëntendossier " + patient.getFirstname() + " " + patient.getLastname() + "!");
    txtWelcome.setFont(Font.font("Arial", FontWeight.BOLD, 20));
    vBox.getChildren().add(txtWelcome);

    GridPane grid = new GridPane();
    grid.setHgap(15);
    grid.setVgap(15);

    Text txtProfile = new Text("Persoonlijke gegevens");
    txtProfile.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    grid.add(txtProfile, 0, 0);

    Label lblNumber = new Label("Uw patiëntnummer:");
    grid.add(lblNumber, 0, 1);
    TextField tfNumber = new TextField(patient.getNumber().toString());
    tfNumber.setPrefWidth(1200);
    tfNumber.setEditable(false);
    grid.add(tfNumber, 1, 1);

    Label lblFirstname = new Label("Voornaam:");
    grid.add(lblFirstname, 0, 2);
    TextField tfFirstname = new TextField(patient.getFirstname());
    tfFirstname.setPrefWidth(1200);
    grid.add(tfFirstname, 1, 2);

    Label lbLastname = new Label("Achternaam:");
    grid.add(lbLastname, 0, 3);
    TextField tfLastname = new TextField(patient.getLastname());
    tfLastname.setPrefWidth(1200);
    grid.add(tfLastname, 1, 3);

    Label lblBirth = new Label("Geboortedatum:");
    grid.add(lblBirth, 0, 4);
    DatePicker dpBirth = new DatePicker();
    dpBirth.setValue(patient.getBirthdate());
    dpBirth.setPrefWidth(1200);
    grid.add(dpBirth, 1, 4);

    Label lblPhone = new Label("Telefoonnummer:");
    grid.add(lblPhone, 0, 5);
    TextField tfPhone = new TextField(patient.getPhonenumber().toString());
    tfPhone.setPrefWidth(1200);
    grid.add(tfPhone, 1, 5);

    Label lblEmail = new Label("Email:");
    grid.add(lblEmail, 0, 6);
    TextField tfEmail = new TextField(patient.getEmail());
    tfEmail.setPrefWidth(1200);
    grid.add(tfEmail, 1, 6);

    Button btnUpdate = new Button("Wijzig");
    grid.add(btnUpdate, 1, 7);
    btnUpdate.setOnAction(e -> updateProfile(grid));
    GridPane.setHalignment(btnUpdate, HPos.RIGHT);

    Text txtChangePass = new Text("Wachtwoord wijzigen");
    txtChangePass.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    grid.add(txtChangePass, 0, 8);

    Label lblNewPass = new Label("Nieuw wachtwoord:");
    grid.add(lblNewPass, 0, 9);
    PasswordField pfNewPass = new PasswordField();
    pfNewPass.setPrefWidth(1200);
    grid.add(pfNewPass, 1, 9);

    Label lblConfirmPass = new Label("Bevestig nieuw wachtwoord:");
    grid.add(lblConfirmPass, 0, 10);
    PasswordField pfConfirmPass = new PasswordField();
    pfConfirmPass.setPrefWidth(1200);
    grid.add(pfConfirmPass, 1, 10);

    Button btnUpdatePass = new Button("Wijzig");
    grid.add(btnUpdatePass, 1, 11);
    btnUpdatePass.setOnAction(e -> updatePassword(grid));
    GridPane.setHalignment(btnUpdatePass, HPos.RIGHT);

    vBox.getChildren().add(grid);

    if (this.care != null) {
      Text txtAuthorize = new Text("Machtig zorgverlener");
      txtAuthorize.setFont(Font.font("Arial", FontWeight.BOLD, 16));
      vBox.getChildren().add(txtAuthorize);

      ArrayList<Care> caresOfPatient = patient.getCareOfPatient();
      TableView<Care> table = care.getCareTableView(caresOfPatient);
      vBox.getChildren().add(table);

      HBox hBoxBottom = new HBox();

      Button btnRemoveAuthorization = new Button("Verwijder machtiging");
      btnRemoveAuthorization.setStyle("-fx-background-color: darkred;");
      btnRemoveAuthorization.setTextFill(Color.WHITE);
      btnRemoveAuthorization.setDisable(true);
      hBoxBottom.getChildren().add(btnRemoveAuthorization);

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
        btnRemoveAuthorization.setDisable(table.getSelectionModel().getSelectedItem() == null);
      });

      btnRemoveAuthorization.setOnAction(e -> {

      });

      btnAuthorize.setOnAction(e -> {
        String selectedCare = comboAuthorizeCare.getSelectionModel().getSelectedItem();
        if (selectedCare != null) {
          Integer selectedCareNumber = Integer.parseInt(selectedCare.split(",")[0]);

          patient.addCareToPatient(selectedCareNumber);
          this.borderPane.setCenter(addProfilePane());
        }
      });
    }

    scroll.setContent(vBox);
    return scroll;
  }

  private void updateProfile(GridPane grid)
  {
    boolean validated = validateProfile(grid);

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

      this.borderPane.setCenter(addProfilePane());
    }
  }

  private void updatePassword(GridPane grid)
  {
    boolean validated = validateNewPassword(grid);

    if (validated) {
      String newPass = ((TextField) new Utility().getNodeByRowColumnIndex(1, 9, grid)).getText();

      patient.setPassword(newPass);
      patient.update();

      this.borderPane.setCenter(addProfilePane());
    }
  }

  private boolean validateProfile(GridPane grid)
  {
    String firstname = ((TextField) new Utility().getNodeByRowColumnIndex(1, 2, grid)).getText();
    if (firstname.isEmpty()) {
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer uw voornaam in!");
      return false;
    }
    String regexFirstname = "^[a-zA-Z][a-zA-Z\\s]*$";
    if (!firstname.matches(regexFirstname)) {
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer een geldige voornaam in!");
      return false;
    }

    String lastname = ((TextField) new Utility().getNodeByRowColumnIndex(1, 3, grid)).getText();
    if (lastname.isEmpty()) {
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer uw achternaam in!");
      return false;
    }
    String regexLastname = "^[a-zA-Z][a-zA-Z\\s]*$";
    if (!lastname.matches(regexLastname)) {
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer een geldige achternaam in!");
      return false;
    }

    LocalDate birth = ((DatePicker) new Utility().getNodeByRowColumnIndex(1, 4, grid)).getValue();
    if (birth == null) {
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer uw geboortedatum in!");
      return false;
    }

    String phone = ((TextField) new Utility().getNodeByRowColumnIndex(1, 5, grid)).getText();
    if (phone.isEmpty()) {
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer uw telefoonnummer in!");
      return false;
    }
    String regexPhone = "^[0-9]*$";
    if (!phone.matches(regexPhone)) {
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer een geldig telefoonnummer in!");
      return false;
    }

    String email = ((TextField) new Utility().getNodeByRowColumnIndex(1, 6, grid)).getText();
    if (email.isEmpty()) {
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer uw email in!");
      return false;
    }
    String emailPattern = "" +
            "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"
            ;
    if(!email.matches(emailPattern)) {
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer een geldige email in!");
      return false;
    }

    return true;
  }

  private boolean validateNewPassword(GridPane grid)
  {
    String newPass = ((TextField) new Utility().getNodeByRowColumnIndex(1, 9, grid)).getText();
    String confirmPass = ((TextField) new Utility().getNodeByRowColumnIndex(1, 10, grid)).getText();

    if(newPass.isEmpty()) {
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer een nieuw wachtwoord in!");
      return false;
    }

    if(confirmPass.isEmpty()) {
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer uw wachtwoord nogmaals in om te bevestigen!");
      return false;
    }

//    String passwordPattern = "(?=.*?\\d)(?=.*?[a-zA-Z])(?=.*?\\W).{8,}";
//    if(!newPass.matches(passwordPattern)) {
//      new Utility().showAlert(
//        Alert.AlertType.ERROR,
//        grid.getScene().getWindow(),
//        "Error!",
//        "Het wachtwoord moet minimaal 1 cijfer hebben, " +
//        "1 letter hebben, " +
//        "1 symbool hebben, " +
//        "8 karakters hebben en " +
//        "mag niet enkel nummers bevatten!"
//      );
//      return false;
//    }

    if (!newPass.equals(confirmPass)) {
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "De ingevoerde wachtwoorden komen niet overeen!");
      return false;
    }

    return true;
  }

  public Scene getDossierScene() {
    return dossierScene;
  }
}
