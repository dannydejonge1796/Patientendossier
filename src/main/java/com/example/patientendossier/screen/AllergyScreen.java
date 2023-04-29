package com.example.patientendossier.screen;

import com.example.patientendossier.model.Allergy;
import com.example.patientendossier.model.Care;
import com.example.patientendossier.model.Patient;
import com.example.patientendossier.utility.Utility;
import com.example.patientendossier.utility.Validation;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class AllergyScreen {

  private final DossierScreen dossier;
  private final Patient patient;
  private final Care care;
  private VBox allergyPane;
  private InfoPageScreen infoPageScreen;
  private ComboBox<String> comboAllergies;
  private TextField tfDescription;

  public AllergyScreen(DossierScreen dossier, Patient patient, Care care)
  {
    this.dossier = dossier;
    this.patient = patient;
    this.care = care;
    //Laad de info pagina
    this.load();
  }

  private void load()
  {
    //Maak nieuwe info page
    this.infoPageScreen = new InfoPageScreen();
    this.allergyPane = infoPageScreen.getVBoxInfoPage();
    //Als je ingelogd bent als zorgverlener
    if (this.care != null) {
      //Toevoeg, bijwerk en delete knop zichtbaar maken
      infoPageScreen.getBtnAdd().setVisible(true);
      infoPageScreen.getBtnDelete().setVisible(true);
      infoPageScreen.getBtnUpdate().setVisible(true);
    }
    //Wanneer op toevoeg knop wordt gedrukt form inlaad functie aanroepen
    infoPageScreen.getBtnAdd().setOnAction(e -> this.loadForm(null));
    //Label aanpassen naar huidige pagina
    infoPageScreen.getLblPage().setText("Allergieën");
    //Tabel toevoegen aan de daarvoor bestemde pane
    infoPageScreen.getHBoxTable().getChildren().add(this.loadTableView());
  }

  private void loadForm(Allergy allergy)
  {
    //Form pagina aanmaken en in de borderpane stoppen
    DossierFormScreen dossierForm = new DossierFormScreen();
    //Pagina wordt weergegeven
    this.dossier.getBorderPane().setCenter(dossierForm.getVBoxFormPage());
    //Terug knop ophalen
    Label lblBack = this.dossier.getLblBack();
    //Actie functie van terugknop wijzigen
    lblBack.setOnMouseClicked(e -> {
      //Info pagina in borderpane stoppen en wordt dan weergegeven
      dossier.getBorderPane().setCenter(new AllergyScreen(this.dossier, this.patient, this.care).getAllergyPane());
      //Terug knop weer andere functionaliteit geven
      dossier.setLblBack();
    });
    //Pagina label text aanpassen
    dossierForm.getLblPage().setText("Allergieën");
    //Form grid uit het dossier form halen
    GridPane gridForm = dossierForm.getGridForm();
    //Label initialiseren
    Label lblName = new Label("Allergie");
    //Toevoegen aan grid
    gridForm.add(lblName, 0, 1);
    //Voorkeur breedte instellen
    lblName.setPrefWidth(200);
    //Combobox initialiseren
    this.comboAllergies = new ComboBox<>();
    //Voorkeur breedte instellen
    comboAllergies.setPrefWidth(800);
    //Toevoegen aan grid
    gridForm.add(comboAllergies, 1, 1);
    //Label initialiseren
    Label lblDescription = new Label("Beschrijving");
    //Toevoegen aan grid
    gridForm.add(lblDescription, 0, 2);
    //Voorkeur breedte instellen
    lblDescription.setPrefWidth(200);
    //Textfield initialiseren
    this.tfDescription = new TextField();
    //Voorkeur breedte instellen
    tfDescription.setPrefWidth(800);
    //Toevoegen aan grid
    gridForm.add(tfDescription, 1, 2);

    //Knop initialiseren
    Button btnUpdate = new Button();
    btnUpdate.getStyleClass().add("btnPrimary");
    //Toevoegen aan grid
    gridForm.add(btnUpdate, 1, 3);
    //Knop aan de rechter kant plaatsen
    GridPane.setHalignment(btnUpdate, HPos.RIGHT);

    //Als er een allergy object is
    if (allergy != null) {
      //Verander knop text naar wijzig
      btnUpdate.setText("Wijzig");
      //Voeg alleen de huidige allergie naam toe aan de combo
      comboAllergies.getItems().add(allergy.getName());
      //Zet deze als default value
      comboAllergies.setValue(allergy.getName());
      //Huidige descriptie als text van het text veld instellen
      tfDescription.setText(allergy.getDescription());
      //Als op knop wordt gedrukt
      btnUpdate.setOnAction(e -> {
        //Als validatie functie true teruggeeft
        if (this.validateForm()) {
          //Update de description van het allergy object met de ingevoerde data
          allergy.setDescription(tfDescription.getText());
          //Update allergieën in de database
          allergy.update(this.patient);
          //Herlaad de pagina
          this.load();
          this.dossier.getBorderPane().setCenter(this.allergyPane);
        }
      });
    //Als er geen allergy object is
    } else {
      //Text van knop is toevoegen
      btnUpdate.setText("Toevoegen");
      //Initialiseer lijst van nog niet toegewezen allergieën
      ArrayList<String> availableAllergies = new ArrayList<>();
      //Loop door alle bestaande allergieën heen
      for (String item : this.care.getAllAllergyNames()) {
        //Als de patient de allergie nog niet heeft
        if (!this.patient.getAllergyNames().contains(item)) {
          //Voeg toe aan lijst
          availableAllergies.add(item);
        }
      }
      //Voeg alle beschikbare allergieën toe aan de combobox
      this.comboAllergies.getItems().addAll(availableAllergies);
      //Als op de knop gedrukt wordt
      btnUpdate.setOnAction(e -> {
        //Als validatie functie true teruggeeft
        if (this.validateForm()) {
          //Haal de values uit de velden
          String name = comboAllergies.getValue();
          String description = tfDescription.getText();
          //Maak nieuw allergy object
          Allergy newAllergy = new Allergy(name, description);
          //Voeg de allergy toe aan de patient in de database
          newAllergy.store(this.patient);
          //Herlaad de pagina
          this.load();
          this.dossier.getBorderPane().setCenter(this.allergyPane);
        }
      });
    }
  }

  private boolean validateForm()
  {
    //Check of de combobox een string als value heeft
    if (!new Validation().validateString(comboAllergies.getValue())) {
      //Anders alert met foutmelding
      new Utility().showAlert(Alert.AlertType.ERROR, dossier.getBorderPane().getScene().getWindow(), "Error!", "Voer een allergie in!");
      return false;
    }

    //Check of het veld een string als value heeft
    if (!new Validation().validateString(tfDescription.getText())) {
      //Anders alert met foutmelding
      new Utility().showAlert(Alert.AlertType.ERROR, dossier.getBorderPane().getScene().getWindow(), "Error!", "Voer een geldige beschrijving in!");
      return false;
    }

    //Alles goed = return true
    return true;
  }

  private TableView<Allergy> loadTableView()
  {
    //Voeg alle allergieën van de patient toe aan de lijst
    ArrayList<Allergy> allergies = patient.getAllergies();
    //Maak een array met kolom namen
    String[] columnNames = {"Naam", "Beschrijving"};
    //Maak een array met attribute namen van het object
    String[] propertyNames = {"name", "description"};
    //Laad de tabel
    TableView<Allergy> table = new TableScreen().createTableView(allergies, columnNames, propertyNames);
    //Wijzig het formaat van de tabel
    table.setPrefWidth(1260);
    //Wanneer een rij wordt aangeklikt, kunnen de knoppen beschikbaar worden gemaakt
    table.setOnMouseClicked(e -> {
      infoPageScreen.getBtnDelete().setDisable(table.getSelectionModel().getSelectedItem() == null);
      infoPageScreen.getBtnUpdate().setDisable(table.getSelectionModel().getSelectedItem() == null);
    });
    //Delete knop actie
    infoPageScreen.getBtnDelete().setOnAction(e -> {
      //Verkrijg de geselecteerde allergy
      Allergy selectedAllergy = table.getSelectionModel().getSelectedItem();
      //Verwijder de allergy uit de database
      selectedAllergy.delete(this.patient);
      //Herlaad pagina
      this.load();
      this.dossier.getBorderPane().setCenter(this.allergyPane);
    });
    //Wanneer op update wordt geklikt
    infoPageScreen.getBtnUpdate().setOnAction(e -> {
      //Verkrijg de geselecteerde allergy
      Allergy selectedAllergy = table.getSelectionModel().getSelectedItem();
      //Laad de form pagina en geef de geselecteerde allergy mee
      this.loadForm(selectedAllergy);
    });

    return table;
  }

  public VBox getAllergyPane() {
    return allergyPane;
  }
}
