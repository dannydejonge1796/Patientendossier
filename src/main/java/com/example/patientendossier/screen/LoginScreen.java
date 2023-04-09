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
    //Grid{ane aanmaken
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25, 25, 25, 25));

    //Pagina label aanmaken en toevoegen
    Text txtPatientLogin = new Text("Inloggen voor patiënten");
    //Functie aanroepen om form velden aan de grid toe te voegen
    addFormFields(grid, txtPatientLogin);

    //Hyperlink om te switchen naar de zorgverlener login pagina
    Hyperlink hlCarerLogin = new Hyperlink("Bent u zorgverlener? Login hier.");
    hlCarerLogin.setOnAction(e -> this.stage.setScene(this.initCarerLoginScene()));
    //Hyperlink toevoegen
    grid.add(hlCarerLogin, 0, 5);
    GridPane.setHalignment(hlCarerLogin, HPos.RIGHT);

    //Knop om in te loggen aanmaken
    Button btnLogin = new Button("Inloggen");
    grid.add(btnLogin, 0, 6);

    //Actie voor login knop
    btnLogin.setOnAction(e -> {
      //Utility class initialiseren
      Utility util = new Utility();
      //Als form gevalideerd is
      if (this.validateFormFields(grid)) {
        //Data uit velden halen
        String email = tfEmail.getText();
        String password = pfPassword.getText();
        //Patient object op proberen te halen met ingevoerde gegevens
        Patient patient = new Login(email, password).loginPatient();
        //Als er geen patient gevonden is
        if (patient == null) {
          //Foutmelding
          util.showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "De combinatie van email en wachtwoord is onjuist!");
        } else {
          //Anders dossier inladen
          this.stage.setScene(new DossierScreen(this.stage, patient, null).getDossierScene());
        }
      }
    });

    //Grid aan borderpane toevoegen
    BorderPane borderPane = new BorderPane();
    borderPane.setCenter(grid);

    return new Scene(borderPane);
  }

  private Scene initCarerLoginScene()
  {
    //Grid{ane aanmaken
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25, 25, 25, 25));

    //Pagina label aanmaken en toevoegen
    Text txtCarerLogin = new Text("Inloggen voor zorgverleners");
    //Functie aanroepen om form velden aan de grid toe te voegen
    addFormFields(grid, txtCarerLogin);

    //Hyperlink om te switchen naar de patient login pagina
    Hyperlink hlPatientLogin = new Hyperlink("Bent u patiënt? Login hier.");
    hlPatientLogin.setOnAction(e -> this.stage.setScene(this.initPatientLoginScene()));
    //Hyperlink toevoegen
    grid.add(hlPatientLogin, 0, 5);
    GridPane.setHalignment(hlPatientLogin, HPos.RIGHT);

    //Knop om in te loggen aanmaken
    Button btnLogin = new Button("Inloggen");
    grid.add(btnLogin, 0, 6);

    //Actie voor login knop
    btnLogin.setOnAction(e -> {
      //Utility class initialiseren
      Utility util = new Utility();
      //Als form gevalideerd is
      if (this.validateFormFields(grid)) {
        //Data uit velden halen
        String email = tfEmail.getText();
        String password = pfPassword.getText();

        //Care object op proberen te halen met ingevoerde gegevens
        Care care = new Login(email, password).loginCare();

        //Als er geen care gevonden is
        if (care == null) {
          //Foutmelding
          util.showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "De combinatie van email en wachtwoord is onjuist!");
        } else {
          //Anders dossier inladen
          this.stage.setScene(new CareScreen(this.stage, care).getListScene());
        }
      }
    });

    //Grid aan borderpane toevoegen
    BorderPane borderPane = new BorderPane();
    borderPane.setCenter(grid);

    return new Scene(borderPane);
  }

  private void addFormFields(GridPane grid, Text txtCarerLogin)
  {
    //Text toevoegen aan grid
    txtCarerLogin.setFont(Font.font(32));
    grid.add(txtCarerLogin, 0, 0);

    //Label aanmaken en toevoegen aan grid
    Label lblEmail = new Label("Email:");
    grid.add(lblEmail, 0, 1);

    //Tf initialiseren en toevoegen aan grid
    this.tfEmail = new TextField();
    grid.add(tfEmail, 0, 2);

    //Label aanmaken en toevoegen aan grid
    Label lblPassword = new Label("Wachtwoord:");
    grid.add(lblPassword, 0, 3);

    //Pf initialiseren en toevoegen aan grid
    this.pfPassword = new PasswordField();
    grid.add(pfPassword, 0, 4);
  }

  public boolean validateFormFields(GridPane grid)
  {
    //Utility class initialiseren
    Utility util = new Utility();

    //Data ophalen uit text veld
    String email = tfEmail.getText();

    //Als data leeg is
    if(email.isEmpty()) {
      //Foutmelding
      util.showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer uw emailadres in!");
      return false;
    }

    //Regex code voor email check
    String emailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"
    ;

    //Als data geen email is
    if(!email.matches(emailPattern)) {
      //Foutmelding
      util.showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Het ingevoerde emailadres is ongeldig!");
      return false;
    }

    //Data ophalen uit het wachtwoord veld
    String password = pfPassword.getText();

    //Als data leeg is
    if(password.isEmpty()) {
      //Foutmelding
      util.showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer uw wachtwoord in!");
      return false;
    }

//    //Regex voor password check
//    String passwordPattern = "(?=.*?\\d)(?=.*?[a-zA-Z])(?=.*?\\W).{8,}";

//    //Als data niet voldoet aan de eisen
//    if(!password.matches(passwordPattern)) {
//      //Foutmelding
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
