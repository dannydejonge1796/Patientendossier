package com.example.patientendossier.screen;

import com.example.patientendossier.model.Patient;
import com.example.patientendossier.utility.Utility;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.time.LocalDate;

public class PatientScreen {

  private final Patient patient;

  public PatientScreen(Patient patient) {
    this.patient = patient;
  }

  public GridPane getProfileForm()
  {
    GridPane grid = new GridPane();
    grid.setHgap(15);
    grid.setVgap(15);

    Text txtProfile = new Text("Persoonlijke gegevens");
    txtProfile.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    grid.add(txtProfile, 0, 0);

    Label lblFirstname = new Label("Voornaam:");
    grid.add(lblFirstname, 0, 2);
    TextField tfFirstname = new TextField();
    grid.add(tfFirstname, 1, 2);

    Label lbLastname = new Label("Achternaam:");
    grid.add(lbLastname, 0, 3);
    TextField tfLastname = new TextField();
    grid.add(tfLastname, 1, 3);

    Label lblBirth = new Label("Geboortedatum:");
    grid.add(lblBirth, 0, 4);

    DatePicker dpBirth = new DatePicker();
    dpBirth.setPrefWidth(950);
    grid.add(dpBirth, 1, 4);

    Label lblPhone = new Label("Telefoonnummer:");
    grid.add(lblPhone, 0, 5);
    TextField tfPhone = new TextField();
    grid.add(tfPhone, 1, 5);

    Label lblEmail = new Label("Email:");
    grid.add(lblEmail, 0, 6);
    TextField tfEmail = new TextField();
    grid.add(tfEmail, 1, 6);

    if (patient != null) {
      Label lblNumber = new Label("Uw patiëntnummer:");
      grid.add(lblNumber, 0, 1);

      TextField tfNumber = new TextField();
      tfNumber.setPrefWidth(950);
      tfNumber.setEditable(false);
      grid.add(tfNumber, 1, 1);

      tfNumber.setText(patient.getNumber().toString());
      tfFirstname.setText(patient.getFirstname());
      tfLastname.setText(patient.getLastname());
      dpBirth.setValue(patient.getBirthdate());
      tfPhone.setText(patient.getPhonenumber().toString());
      tfEmail.setText(patient.getEmail());

      Button btnUpdate = new Button("Wijzig");
      grid.add(btnUpdate, 1, 7);
      GridPane.setHalignment(btnUpdate, HPos.RIGHT);
    }

    return grid;
  }

  public GridPane getUpdatePasswordForm()
  {
    GridPane grid = new GridPane();
    grid.setHgap(15);
    grid.setVgap(15);

    Text txtChangePass = new Text("Wachtwoord");
    txtChangePass.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    grid.add(txtChangePass, 0, 0);

    Label lblNewPass = new Label("Nieuw wachtwoord:");
    grid.add(lblNewPass, 0, 1);
    PasswordField pfNewPass = new PasswordField();
    pfNewPass.setPrefWidth(950);
    grid.add(pfNewPass, 1, 1);

    Label lblConfirmPass = new Label("Bevestig nieuw wachtwoord:");
    grid.add(lblConfirmPass, 0, 2);
    PasswordField pfConfirmPass = new PasswordField();
    grid.add(pfConfirmPass, 1, 2);

    if (patient != null) {
      Button btnUpdatePass = new Button("Wijzig");
      grid.add(btnUpdatePass, 1, 3);
      GridPane.setHalignment(btnUpdatePass, HPos.RIGHT);
    }

    return grid;
  }

  public boolean validateProfile(GridPane grid)
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
    String emailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"
            ;
    if(!email.matches(emailPattern)) {
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer een geldige email in!");
      return false;
    }

    return true;
  }

  public boolean validateNewPassword(GridPane grid)
  {
    String newPass = ((TextField) new Utility().getNodeByRowColumnIndex(1, 1, grid)).getText();
    String confirmPass = ((TextField) new Utility().getNodeByRowColumnIndex(1, 2, grid)).getText();

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
//        "Het wachtwoord moet minimaal 1 cijfer hebben," +
//        "1 letter hebben," +
//        "1 symbool hebben," +
//        "8 karakters hebben en" +
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
}
