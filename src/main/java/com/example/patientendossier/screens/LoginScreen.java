package com.example.patientendossier.screens;

import com.example.patientendossier.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginScreen {

  private final Stage stage;
  private final Database db;
  private final Scene patientLoginScene;
  private final Scene carerLoginScene;

  public LoginScreen(Stage stage, Database db)
  {
    this.stage = stage;
    this.db = db;
    this.patientLoginScene = setPatientLoginScene();
    this.carerLoginScene = setCarerLoginScene();
  }

  private Scene setPatientLoginScene()
  {
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25, 25, 25, 25));

    Text txtPatientLogin = new Text("Inloggen voor patiënten");
    addFormFields(grid, txtPatientLogin);

    Hyperlink hlCarerLogin = new Hyperlink("Bent u zorgverlener? Login hier.");
    grid.add(hlCarerLogin, 0, 5);
    GridPane.setHalignment(hlCarerLogin, HPos.RIGHT);

    hlCarerLogin.setOnAction(e -> this.stage.setScene(this.carerLoginScene));

    Button btnLogin = new Button("Inloggen");
    grid.add(btnLogin, 0, 6);

    btnLogin.setOnAction(e -> {
      Utility util = new Utility();
      boolean validated = this.validateFormFields(grid);

      if (validated) {
        TextField tfEmail = (TextField) new Utility().getNodeByRowColumnIndex(0, 2, grid);
        String email = tfEmail.getText();

        TextField tfPassword = (TextField) new Utility().getNodeByRowColumnIndex(0, 4, grid);
        String password = tfPassword.getText();

        Patient patient = new Login(this.db, email, password).loginPatient();

        if (patient == null) {
          util.showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "De combinatie van email en wachtwoord is onjuist!");
        } else {
          this.stage.setScene(new Dossier(this.stage, this.db, patient, "patient").getDossierScene());
        }
      }
    });

    BorderPane borderPane = new BorderPane();
    borderPane.setCenter(grid);

    return new Scene(borderPane);
  }

  private Scene setCarerLoginScene()
  {
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25, 25, 25, 25));

    Text txtCarerLogin = new Text("Inloggen voor zorgverleners");
    addFormFields(grid, txtCarerLogin);

    Hyperlink hlPatientLogin = new Hyperlink("Bent u patiënt? Login hier.");
    grid.add(hlPatientLogin, 0, 5);
    GridPane.setHalignment(hlPatientLogin, HPos.RIGHT);

    hlPatientLogin.setOnAction(e -> this.stage.setScene(this.patientLoginScene));

    Button btnLogin = new Button("Inloggen");
    grid.add(btnLogin, 0, 6);

    btnLogin.setOnAction(e -> {
      Utility util = new Utility();
      boolean validated = this.validateFormFields(grid);
      if (validated) {
        TextField tfEmail = (TextField) new Utility().getNodeByRowColumnIndex(0, 2, grid);
        String email = tfEmail.getText();

        TextField tfPassword = (TextField) new Utility().getNodeByRowColumnIndex(0, 4, grid);
        String password = tfPassword.getText();

        Care care = new Login(this.db, email, password).loginCare();

        if (care == null) {
          util.showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "De combinatie van email en wachtwoord is onjuist!");
        } else {
          this.stage.setScene(new PatientList(this.stage, this.db, care).getPatientListScene());
        }
      }
    });

    BorderPane borderPane = new BorderPane();
    borderPane.setCenter(grid);

    return new Scene(borderPane);
  }

  private void addFormFields(GridPane grid, Text txtCarerLogin)
  {
    txtCarerLogin.setFont(Font.font(32));
    grid.add(txtCarerLogin, 0, 0);

    Label lblEmail = new Label("Email:");
    grid.add(lblEmail, 0, 1);

    TextField tfEmail = new TextField();
    grid.add(tfEmail, 0, 2);

    Label lblPassword = new Label("Wachtwoord:");
    grid.add(lblPassword, 0, 3);

    TextField tfPassword = new TextField();
    grid.add(tfPassword, 0, 4);
  }

  public boolean validateFormFields(GridPane grid)
  {
    Utility util = new Utility();

    TextField tfEmail = (TextField) new Utility().getNodeByRowColumnIndex(0, 2, grid);
    String email = tfEmail.getText();

    if(email.isEmpty()) {
      util.showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer uw emailadres in!");
      return false;
    }

    String emailPattern = "" +
            "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"
            ;

    if(!email.matches(emailPattern)) {
      util.showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Het ingevoerde emailadres is ongeldig!");
      return false;
    }

    TextField tfPassword = (TextField) new Utility().getNodeByRowColumnIndex(0, 4, grid);
    String password = tfPassword.getText();

    if(password.isEmpty()) {
      util.showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer uw wachtwoord in!");
      return false;
    }

//    String passwordPattern = "(?=.*?\\d)(?=.*?[a-zA-Z])(?=.*?\\W).{8,}";
//    if(!password.matches(passwordPattern)) {
//      util.showAlert(
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

    return true;
  }

  public Scene getPatientLoginScene() {
    return patientLoginScene;
  }

  public Scene getCarerLoginScene() {
    return carerLoginScene;
  }
}
