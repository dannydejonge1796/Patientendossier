package com.example.patientendossier.screen;

import com.example.patientendossier.model.Care;
import com.example.patientendossier.model.Login;
import com.example.patientendossier.model.Patient;
import com.example.patientendossier.utility.Utility;
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
  private final Scene patientLoginScene;
  private final Scene carerLoginScene;
  private TextField tfEmail;
  private PasswordField pfPassword;

  public LoginScreen(Stage stage)
  {
    this.stage = stage;
    this.carerLoginScene = this.initCarerLoginScene();
    this.patientLoginScene = this.initPatientLoginScene();
  }

  private Scene initPatientLoginScene()
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

    hlCarerLogin.setOnAction(e -> this.stage.setScene(this.initCarerLoginScene()));

    Button btnLogin = new Button("Inloggen");
    grid.add(btnLogin, 0, 6);

    btnLogin.setOnAction(e -> {
      Utility util = new Utility();
      boolean validated = this.validateFormFields(grid);

      if (validated) {
        String email = tfEmail.getText();
        String password = pfPassword.getText();

        Patient patient = new Login(email, password).loginPatient();

        if (patient == null) {
          util.showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "De combinatie van email en wachtwoord is onjuist!");
        } else {
          this.stage.setScene(new DossierScreen(this.stage, patient, null).getDossierScene());
        }
      }
    });

    BorderPane borderPane = new BorderPane();
    borderPane.setCenter(grid);

    return new Scene(borderPane);
  }

  private Scene initCarerLoginScene()
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

    hlPatientLogin.setOnAction(e -> this.stage.setScene(this.initPatientLoginScene()));

    Button btnLogin = new Button("Inloggen");
    grid.add(btnLogin, 0, 6);

    btnLogin.setOnAction(e -> {
      Utility util = new Utility();
      boolean validated = this.validateFormFields(grid);
      if (validated) {
        String email = tfEmail.getText();
        String password = pfPassword.getText();

        Care care = new Login(email, password).loginCare();

        if (care == null) {
          util.showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "De combinatie van email en wachtwoord is onjuist!");
        } else {
          this.stage.setScene(new CareScreen(this.stage, care).getListScene());
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

    this.tfEmail = new TextField();
    grid.add(tfEmail, 0, 2);

    Label lblPassword = new Label("Wachtwoord:");
    grid.add(lblPassword, 0, 3);

    this.pfPassword = new PasswordField();
    grid.add(pfPassword, 0, 4);
  }

  public boolean validateFormFields(GridPane grid)
  {
    Utility util = new Utility();

    String email = tfEmail.getText();

    if(email.isEmpty()) {
      util.showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer uw emailadres in!");
      return false;
    }

    String emailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"
    ;

    if(!email.matches(emailPattern)) {
      util.showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Het ingevoerde emailadres is ongeldig!");
      return false;
    }

    String password = pfPassword.getText();

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
