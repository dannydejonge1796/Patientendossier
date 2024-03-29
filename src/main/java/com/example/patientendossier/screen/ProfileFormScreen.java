package com.example.patientendossier.screen;

import com.example.patientendossier.model.Care;
import com.example.patientendossier.model.Patient;
import com.example.patientendossier.utility.Utility;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.Random;

public class ProfileFormScreen {

  private final Patient patient;
  private final Care care;

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
    //Nieuwe grid aanmaken
    GridPane grid = new GridPane();
    grid.setHgap(15);
    grid.setVgap(15);

    ColumnConstraints col1 = new ColumnConstraints();
    col1.setPercentWidth(20); // Set column 1 width to 20%
    ColumnConstraints col2 = new ColumnConstraints();
    col2.setPercentWidth(80); // Set column 2 width to 80%

    grid.getColumnConstraints().addAll(col1, col2);

    //Als het formulier wordt gebruikt voor zorgverleners, hebben we een optelling van 1 nodig
    //Als het formulier wordt gebruikt om een nieuwe patiënt toe te voegen, hebben we een aftrekking van 1 nodig
    int rowIdxModifier = 0;
    if (patient == null) {
      rowIdxModifier = -1;
    }

    //Label voor pagina aanmaken en toevoegen
    Text txtProfile = new Text("Persoonlijke gegevens");
    txtProfile.getStyleClass().add("subHeader");
    grid.add(txtProfile, 0, 0);

    //Label voor veld aanmaken en toevoegen
    Label lblFirstname = new Label("Voornaam:");
    grid.add(lblFirstname, 0, 2 + rowIdxModifier);
    //Text veld aanmaken en toevoegen
    this.tfFirstname = new TextField();
    grid.add(tfFirstname, 1, 2 + rowIdxModifier);

    //Label voor veld aanmaken en toevoegen
    Label lbLastname = new Label("Achternaam:");
    grid.add(lbLastname, 0, 3 + rowIdxModifier);
    //Text veld aanmaken en toevoegen
    this.tfLastname = new TextField();
    grid.add(tfLastname, 1, 3 + rowIdxModifier);

    //Label voor veld aanmaken en toevoegen
    Label lblBirth = new Label("Geboortedatum:");
    grid.add(lblBirth, 0, 4 + rowIdxModifier);

    //Datepicker aanmaken en toevoegen
    this.dpBirth = new DatePicker();
    grid.add(dpBirth, 1, 4 + rowIdxModifier);

    //Als het form wordt gebruikt voor zorgverleners
    if (mode.equals("care")) {
      //Voeg label beroep toe
      Label lblProfession = new Label("Beroep");
      grid.add(lblProfession, 0, 5 + rowIdxModifier);
      //Voeg text veld beroep toe
      this.tfProfession = new TextField();
      grid.add(tfProfession, 1, 5 + rowIdxModifier);

      rowIdxModifier = 0;
    }

    //Label voor veld aanmaken en toevoegen
    Label lblPhone = new Label("Telefoonnummer:");
    grid.add(lblPhone, 0, 5 + rowIdxModifier);
    //Text veld aanmaken en toevoegen
    this.tfPhone = new TextField();
    grid.add(tfPhone, 1, 5 + rowIdxModifier);

    //Label voor veld aanmaken en toevoegen
    Label lblEmail = new Label("Email:");
    grid.add(lblEmail, 0, 6 + rowIdxModifier);
    //Text veld aanmaken en toevoegen
    this.tfEmail = new TextField();
    grid.add(tfEmail, 1, 6 + rowIdxModifier);

    //Als er een patient object is meegegeven
    if (patient != null) {
      //Label voor patient nummer aanmaken en toevoegen
      Label lblNumber = new Label("Uw patiëntnummer:");
      grid.add(lblNumber, 0, 1);

      //Text veld aanmaken die niet te wijzigen is en toevoegen
      TextField tfNumber = new TextField();
      tfNumber.setEditable(false);
      grid.add(tfNumber, 1, 1);

      //Waardes van de velden invullen met huidige data
      tfNumber.setText(patient.getNumber().toString());
      tfFirstname.setText(patient.getFirstname());
      tfLastname.setText(patient.getLastname());
      dpBirth.setValue(patient.getBirthdate());
      tfPhone.setText(patient.getPhonenumber().toString());
      tfEmail.setText(patient.getEmail());

      //Nieuwe submit knop aanmaken en toevoegen
      this.btnUpdateProfile = new Button();
      this.btnUpdateProfile.getStyleClass().add("btnPrimary");
      //Set font awesome icon as graphic
      btnUpdateProfile.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.EDIT));

      grid.add(btnUpdateProfile, 1, 7 + rowIdxModifier);
      GridPane.setHalignment(btnUpdateProfile, HPos.RIGHT);
    }

    //Als er een care object is meegegeven
    if (care != null) {
      //Waardes van de velden invullen met huidige data
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
    //Nieuwe grid aanmaken
    GridPane grid = new GridPane();
    grid.setHgap(15);
    grid.setVgap(15);

    ColumnConstraints col1 = new ColumnConstraints();
    col1.setPercentWidth(20); // Set column 1 width to 20%
    ColumnConstraints col2 = new ColumnConstraints();
    col2.setPercentWidth(80); // Set column 2 width to 80%

    grid.getColumnConstraints().addAll(col1, col2);

    //Label van het form aanmaken en toevoegen
    Text txtChangePass = new Text("Wachtwoord");
    txtChangePass.getStyleClass().add("subHeader");
    grid.add(txtChangePass, 0, 0);

    //Label voor veld aanmaken en toevoegen
    Label lblNewPass = new Label("Nieuw wachtwoord:");
    grid.add(lblNewPass, 0, 1);
    //Wachtwoord veld aanmaken en toevoegen
    this.pfNewPass = new PasswordField();
    grid.add(pfNewPass, 1, 1);

    //Label voor veld aanmaken en toevoegen
    Label lblConfirmPass = new Label("Bevestig nieuw wachtwoord:");
    grid.add(lblConfirmPass, 0, 2);
    //Wachtwoord veld aanmaken en toevoegen
    this.pfConfirmPass = new PasswordField();
    grid.add(pfConfirmPass, 1, 2);

    //Als er een patient object is meegegeven
    if (patient != null) {
      //Maak knop wijzig en voeg toe aan grid
      this.btnUpdatePassword = new Button();
      btnUpdatePassword.getStyleClass().add("btnPrimary");
      //Set font awesome icon as graphic
      btnUpdatePassword.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.EDIT));

      grid.add(btnUpdatePassword, 1, 3);
      GridPane.setHalignment(btnUpdatePassword, HPos.RIGHT);
    }

    return grid;
  }

  public boolean validateProfile(GridPane grid)
  {
    //Haal waarde op uit veld
    String firstname = tfFirstname.getText();
    //Als veld leeg is
    if (firstname.isEmpty()) {
      //Foutmelding
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer uw voornaam in!");
      return false;
    }
    //Regex code voor check
    String regexFirstname = "^[a-zA-Z][a-zA-Z\\s]*$";
    //Als data voldoet aan de regex
    if (!firstname.matches(regexFirstname)) {
      //Foutmelding
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer een geldige voornaam in!");
      return false;
    }

    //Haal waarde op uit veld
    String lastname = tfLastname.getText();
    //Als veld leeg is
    if (lastname.isEmpty()) {
      //Foutmelding
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer uw achternaam in!");
      return false;
    }
    //Regex code voor check
    String regexLastname = "^[a-zA-Z][a-zA-Z\\s]*$";
    //Als data voldoet aan de regex
    if (!lastname.matches(regexLastname)) {
      //Foutmelding
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer een geldige achternaam in!");
      return false;
    }

    //Haal waarde op uit veld
    LocalDate birth = this.dpBirth.getValue();
    //Als veld null is
    if (birth == null) {
      //Foutmelding
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer uw geboortedatum in!");
      return false;
    }

    //Haal waarde op uit veld
    String phone = tfPhone.getText();
    //Als veld leeg is
    if (phone.isEmpty()) {
      //Foutmelding
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer uw telefoonnummer in!");
      return false;
    }
    //Regex code voor check
    String regexPhone = "^[0-9]*$";
    //Als data voldoet aan de regex
    if (!phone.matches(regexPhone)) {
      //Foutmelding
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer een geldig telefoonnummer in!");
      return false;
    }

    //Haal waarde op uit veld
    String email = tfEmail.getText();
    //Als veld leeg is
    if (email.isEmpty()) {
      //Foutmelding
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer uw email in!");
      return false;
    }
    //Regex code voor check
    String emailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"
            ;
    //Als data voldoet aan de regex
    if(!email.matches(emailPattern)) {
      //Foutmelding
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer een geldige email in!");
      return false;
    }

    //Als alles goed is true teruggeven
    return true;
  }

  public boolean validateNewPassword(GridPane grid)
  {
    //Haal waarde op uit veld
    String newPass = this.pfNewPass.getText();
    String confirmPass = this.pfConfirmPass.getText();

    //Als veld leeg is
    if(newPass.isEmpty()) {
      //Foutmelding
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer een nieuw wachtwoord in!");
      return false;
    }

    //Als veld leeg is
    if(confirmPass.isEmpty()) {
      //Foutmelding
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Voer uw wachtwoord nogmaals in om te bevestigen!");
      return false;
    }

//    //Regex code voor check
//    String passwordPattern = "(?=.*?\\d)(?=.*?[a-zA-Z])(?=.*?\\W).{8,}";
//    //Als wachtwoord niet voldoet aan de vereisten
//    if(!newPass.matches(passwordPattern)) {
//      //Foutmelding
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

    //Als de twee wachtwoorden niet gelijk zijn
    if (!newPass.equals(confirmPass)) {
      //Foutmelding
      new Utility().showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "De ingevoerde wachtwoorden komen niet overeen!");
      return false;
    }

    //Als alles goed is true teruggeven
    return true;
  }

  public void createNewPatient(Care authorizeCare)
  {
    //Random patiënten nummer genereren
    int number = this.createRandomNumber();

    //Patient object aanmaken met ingevoerde data
    Patient newPatient = new Patient(
            number,
            tfFirstname.getText(),
            tfLastname.getText(),
            dpBirth.getValue(),
            Integer.parseInt(tfPhone.getText()),
            tfEmail.getText(),
            new Utility().hashPassword(pfNewPass.getText())
    );

    //Patient opslaan in de database
    newPatient.store();
    //De zorgverlener die de patient aanmaakt, wordt gemachtigd
    authorizeCare.authorizePatient(authorizeCare.getNumber(), number);
  }

  public void updatePatientProfile()
  {
    //Waardes van attributen wijzigen in het object
    patient.setFirstname(tfFirstname.getText());
    patient.setLastname(tfLastname.getText());
    patient.setBirthdate(dpBirth.getValue());
    patient.setPhonenumber(Integer.parseInt(tfPhone.getText()));
    patient.setEmail(tfEmail.getText());
    //Patient updaten in de database
    patient.update();
  }

  public void updatePatientPassword()
  {
    //Wachtwoord wijzigen in het object
    patient.setPassword(new Utility().hashPassword(pfNewPass.getText()));
    //Update de patient in de database
    patient.update();
  }

  public void createNewCare()
  {
    //Maak nieuw object met ingevoerde data
    Care newCare = new Care(
            this.createRandomNumber(),
            tfFirstname.getText(),
            tfLastname.getText(),
            dpBirth.getValue(),
            tfProfession.getText(),
            Integer.parseInt(tfPhone.getText()),
            tfEmail.getText(),
            new Utility().hashPassword(pfNewPass.getText())
    );
    //Voeg de zorgverlener toe aan de database
    newCare.store();
  }

  public void updateCare()
  {
    //Waardes van attributen in het object wijzigen met ingevoerde data
    care.setFirstname(tfFirstname.getText());
    care.setLastname(tfLastname.getText());
    care.setBirthdate(dpBirth.getValue());
    care.setProfession(tfProfession.getText());
    care.setPhonenumber(Integer.parseInt(tfPhone.getText()));
    care.setEmail(tfEmail.getText());
    care.setPassword(new Utility().hashPassword(pfNewPass.getText()));
    //Zorgverlener bijwerken in de database
    care.update();
  }

  private Integer createRandomNumber()
  {
    //Random object initialiseren
    Random random = new Random();
    //String builder aanmaken
    StringBuilder stringBuilder = new StringBuilder();
    //Begin de string met een 1
    stringBuilder.append(1);
    //Loop 9 keer
    for (int i = 0; i < 9; i++) {
      //Random nummer tussen 0-10
      int randomNumber = random.nextInt(10);
      //Toevoegen aan string
      stringBuilder.append(randomNumber);
    }
    //Eindresultaat naar integer omvormen en teruggeven
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
