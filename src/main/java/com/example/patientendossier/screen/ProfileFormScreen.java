package com.example.patientendossier.screen;

import com.example.patientendossier.model.Care;
import com.example.patientendossier.model.Patient;
import com.example.patientendossier.utility.Utility;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.Random;

public class ProfileFormScreen {

  private final Patient patient;
  private final Care care;

  private TextField tfNumber;
  private TextField tfFirstname;
  private TextField tfLastname;
  private TextField tfProfession;
  private DatePicker dpBirth;
  private TextField tfPhone;
  private TextField tfEmail;
  private PasswordField pfNewPass;
  private PasswordField pfConfirmPass;
  private Button btnUpdateProfile;
  private Button btnUpdatePassword;

  public ProfileFormScreen(Patient patient, Care care) {
    this.patient = patient;
    this.care = care;
  }

  public GridPane getProfileForm(String mode)
  {
    GridPane grid = new GridPane();
    grid.setHgap(15);
    grid.setVgap(15);

    int compensation = 0;
    if (mode.equals("care")) {
      compensation = 1;
    }

    Text txtProfile = new Text("Persoonlijke gegevens");
    txtProfile.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    grid.add(txtProfile, 0, 0);

    Label lblFirstname = new Label("Voornaam:");
    grid.add(lblFirstname, 0, 2);
    this.tfFirstname = new TextField();
    grid.add(tfFirstname, 1, 2);

    Label lbLastname = new Label("Achternaam:");
    grid.add(lbLastname, 0, 3);
    this.tfLastname = new TextField();
    grid.add(tfLastname, 1, 3);

    Label lblBirth = new Label("Geboortedatum:");
    grid.add(lblBirth, 0, 4);

    this.dpBirth = new DatePicker();
    dpBirth.setPrefWidth(950);
    grid.add(dpBirth, 1, 4);

    if (mode.equals("care")) {
      Label lblProfession = new Label("Beroep");
      grid.add(lblProfession, 0, 5);

      this.tfProfession = new TextField();
      grid.add(tfProfession, 1, 5);
    }

    Label lblPhone = new Label("Telefoonnummer:");
    grid.add(lblPhone, 0, 5 + compensation);
    this.tfPhone = new TextField();
    grid.add(tfPhone, 1, 5 + compensation);

    Label lblEmail = new Label("Email:");
    grid.add(lblEmail, 0, 6 + compensation);
    this.tfEmail = new TextField();
    grid.add(tfEmail, 1, 6 + compensation);

    if (patient != null) {
      Label lblNumber = new Label("Uw patiëntnummer:");
      grid.add(lblNumber, 0, 1);

      this.tfNumber = new TextField();
      tfNumber.setPrefWidth(950);
      tfNumber.setEditable(false);
      grid.add(tfNumber, 1, 1);

      tfNumber.setText(patient.getNumber().toString());
      tfFirstname.setText(patient.getFirstname());
      tfLastname.setText(patient.getLastname());
      dpBirth.setValue(patient.getBirthdate());
      tfPhone.setText(patient.getPhonenumber().toString());
      tfEmail.setText(patient.getEmail());

      this.btnUpdateProfile = new Button("Wijzig");
      grid.add(btnUpdateProfile, 1, 7 + compensation);
      GridPane.setHalignment(btnUpdateProfile, HPos.RIGHT);
    }

    if (care != null) {
      tfNumber.setText(care.getNumber().toString());
      tfFirstname.setText(care.getFirstname());
      tfLastname.setText(care.getLastname());
      tfProfession.setText(care.getProfession());
      dpBirth.setValue(care.getBirthdate());
      tfPhone.setText(care.getPhonenumber().toString());
      tfEmail.setText(care.getEmail());
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
    this.pfNewPass = new PasswordField();
    pfNewPass.setPrefWidth(950);
    grid.add(pfNewPass, 1, 1);

    Label lblConfirmPass = new Label("Bevestig nieuw wachtwoord:");
    grid.add(lblConfirmPass, 0, 2);
    this.pfConfirmPass = new PasswordField();
    grid.add(pfConfirmPass, 1, 2);

    if (patient != null) {
      this.btnUpdatePassword = new Button("Wijzig");
      grid.add(btnUpdatePassword, 1, 3);
      GridPane.setHalignment(btnUpdatePassword, HPos.RIGHT);
    }

    return grid;
  }

  public boolean validateProfile(GridPane grid)
  {
    String firstname = tfFirstname.getText();
    if (firstname.isEmpty()) {
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer uw voornaam in!");
      return false;
    }
    String regexFirstname = "^[a-zA-Z][a-zA-Z\\s]*$";
    if (!firstname.matches(regexFirstname)) {
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer een geldige voornaam in!");
      return false;
    }

    String lastname = tfLastname.getText();
    if (lastname.isEmpty()) {
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer uw achternaam in!");
      return false;
    }
    String regexLastname = "^[a-zA-Z][a-zA-Z\\s]*$";
    if (!lastname.matches(regexLastname)) {
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer een geldige achternaam in!");
      return false;
    }

    LocalDate birth = this.dpBirth.getValue();
    if (birth == null) {
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer uw geboortedatum in!");
      return false;
    }

    String phone = tfPhone.getText();
    if (phone.isEmpty()) {
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer uw telefoonnummer in!");
      return false;
    }
    String regexPhone = "^[0-9]*$";
    if (!phone.matches(regexPhone)) {
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer een geldig telefoonnummer in!");
      return false;
    }

    String email = tfEmail.getText();
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
    String newPass = this.pfNewPass.getText();
    String confirmPass = this.pfConfirmPass.getText();

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

  public void createNewPatient(Care authorizeCare)
  {
    int number = this.createRandomNumber();
    String firstname = tfFirstname.getText();
    String lastname = tfLastname.getText();
    LocalDate birth = dpBirth.getValue();
    String phoneString = tfPhone.getText();
    Integer phone = Integer.parseInt(phoneString);
    String email = tfEmail.getText();
    String newPass = pfNewPass.getText();

    Patient newPatient = new Patient(
            number, firstname, lastname, birth, phone, email, newPass
    );
    newPatient.store();
    authorizeCare.authorizePatient(authorizeCare.getNumber(), number);
  }

  public void updatePatientProfile()
  {
    String firstname = tfFirstname.getText();
    String lastname = tfLastname.getText();
    LocalDate birth = dpBirth.getValue();
    String phoneString = tfPhone.getText();
    Integer phone = Integer.parseInt(phoneString);
    String email = tfEmail.getText();

    patient.setFirstname(firstname);
    patient.setLastname(lastname);
    patient.setBirthdate(birth);
    patient.setPhonenumber(phone);
    patient.setEmail(email);
    patient.update();
  }

  public void updatePatientPassword()
  {
    String newPass = pfNewPass.getText();
    patient.setPassword(newPass);

    patient.update();
  }

  public void createNewCare()
  {
    int number = this.createRandomNumber();
    String firstname = tfFirstname.getText();
    String lastname = tfLastname.getText();
    LocalDate birth = dpBirth.getValue();
    String profession = tfProfession.getText();
    String phoneString = tfPhone.getText();
    Integer phone = Integer.parseInt(phoneString);
    String email = tfEmail.getText();
    String newPass = pfNewPass.getText();

    Care newCare = new Care(
            number, firstname, lastname, birth, profession, phone, email, newPass
    );

    newCare.store();
  }

  private Integer createRandomNumber()
  {
    Random random = new Random();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(1);
    for (int i = 0; i < 9; i++) {
      int randomNumber = random.nextInt(10);
      stringBuilder.append(randomNumber);
    }
    String randomString = stringBuilder.toString();

    return Integer.parseInt(randomString);
  }

  public Button getBtnUpdateProfile() {
    return btnUpdateProfile;
  }

  public Button getBtnUpdatePassword() {
    return btnUpdatePassword;
  }
}
