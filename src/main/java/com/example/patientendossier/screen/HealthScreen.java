package com.example.patientendossier.screen;

import com.example.patientendossier.model.Care;
import com.example.patientendossier.model.Health;
import com.example.patientendossier.model.Patient;
import com.example.patientendossier.utility.Utility;
import com.example.patientendossier.utility.Validation;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class HealthScreen {

  private final DossierScreen dossier;
  private final Patient patient;
  private final Care care;
  private VBox healthPane;
  private InfoPageScreen infoPageScreen;
  private ComboBox<String> comboHealths;
  private TextField tfDescription;

  public HealthScreen(DossierScreen dossier, Patient patient, Care care)
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
    this.healthPane = infoPageScreen.getVBoxInfoPage();

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
    infoPageScreen.getLblPage().setText("Gezondheidsproblemen");
    //Tabel toevoegen aan de daarvoor bestemde pane
    infoPageScreen.getHBoxTable().getChildren().add(this.loadTableView());
  }

  private void loadForm(Health health)
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
      dossier.getBorderPane().setCenter(new HealthScreen(this.dossier, this.patient, this.care).getHealthPane());
      //Terug knop weer andere functionaliteit geven
      dossier.setLblBack();
    });
    //Pagina label text aanpassen
    dossierForm.getLblPage().setText("Gezondheidsproblemen");

    //Form grid uit het dossier form halen
    GridPane gridForm = dossierForm.getGridForm();
    //Label initialiseren
    Label lblName = new Label("Gezondheidsprobleem");
    //Toevoegen aan grid
    gridForm.add(lblName, 0, 1);
    //Voorkeur breedte instellen
    lblName.setPrefWidth(200);

    //Combobox initialiseren
    this.comboHealths = new ComboBox<>();
    //Voorkeur breedte instellen
    this.comboHealths.setPrefWidth(800);
    //Toevoegen aan grid
    gridForm.add(this.comboHealths, 1, 1);

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
    //Als er een health object is
    if (health != null) {
      //Verander knop text naar wijzig
      btnUpdate.setText("Wijzig");
      //Voeg alleen de huidige naam van het gezondheidsprobleem toe aan de combo
      this.comboHealths.getItems().add(health.getName());
      //Zet deze als default value
      comboHealths.setValue(health.getName());
      //Huidige descriptie als text van het text veld instellen
      tfDescription.setText(health.getDescription());

      //Als op knop wordt gedrukt
      btnUpdate.setOnAction(e -> {
        //Als validatie functie true teruggeeft
        if (this.validateForm()) {
          //Update de description van het health object met de ingevoerde data
          health.setDescription(tfDescription.getText());
          //Update gezondheidsproblemen in de database
          health.update(this.patient);
          //Herlaad pagina
          this.load();
          this.dossier.getBorderPane().setCenter(this.healthPane);
        }
      });
    //Als er geen health object is
    } else {
      //Text van knop is toevoegen
      btnUpdate.setText("Toevoegen");

      //Initialiseer lijst van nog niet toegewezen gezondheidsproblemen
      ArrayList<String> availableHealths = new ArrayList<>();
      //Loop door alle bestaande gezondheidsproblemen heen
      for (String item : this.care.getAllHealthNames()) {
        //Als de patient het gezondheidsprobleem nog niet heeft
        if (!this.patient.getHealthNames().contains(item)) {
          //Voeg toe aan lijst
          availableHealths.add(item);
        }
      }

      //Voeg alle beschikbare gezondheidsproblemen toe aan de combobox
      this.comboHealths.getItems().addAll(availableHealths);

      //Als op de knop gedrukt wordt
      btnUpdate.setOnAction(e -> {
        //Als validatie functie true teruggeeft
        if (this.validateForm()) {
          //Haal de values uit de velden
          String name = this.comboHealths.getValue();
          String description = tfDescription.getText();

          //Maak nieuw health object
          Health newHealth = new Health(name, description);

          //Voeg het gezondheidsprobleem toe aan de patient in de database
          newHealth.store(this.patient);
          //Herlaad pagina
          this.load();
          this.dossier.getBorderPane().setCenter(this.healthPane);
        }
      });
    }
  }

  private boolean validateForm()
  {
    //Check of de combobox een string als value heeft
    if (!new Validation().validateString(this.comboHealths.getValue())) {
      //Anders alert met foutmelding
      new Utility().showAlert(Alert.AlertType.ERROR, dossier.getBorderPane().getScene().getWindow(), "Error!", "Voer een gezondheidsprobleem in!");
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

  private TableView<Health> loadTableView()
  {
    //Voeg alle gezondheidsproblemen van de patient toe aan de lijst
    ArrayList<Health> healths = patient.getHealths();
    //Maak een array met kolom namen
    String[] columnNames = {"Naam", "Beschrijving"};
    //Maak een array met attribute namen van het object
    String[] propertyNames = {"name", "description"};

    //Laad de tabel
    TableView<Health> table = new TableScreen().createTableView(healths, columnNames, propertyNames);

    //Wijzig het formaat van de tabel
    table.setPrefWidth(1260);

    //Wanneer een rij wordt aangeklikt, kunnen de knoppen beschikbaar worden gemaakt
    table.setOnMouseClicked(e -> {
      infoPageScreen.getBtnDelete().setDisable(table.getSelectionModel().getSelectedItem() == null);
      infoPageScreen.getBtnUpdate().setDisable(table.getSelectionModel().getSelectedItem() == null);
    });

    //Delete knop actie
    infoPageScreen.getBtnDelete().setOnAction(e -> {
      //Verkrijg het geselecteerde gezondheidsprobleem
      Health selectedHealth = table.getSelectionModel().getSelectedItem();
      //Verwijder het gezondheidsprobleem uit de db
      selectedHealth.delete(this.patient);
      //Herlaad pagina
      this.load();
      this.dossier.getBorderPane().setCenter(this.healthPane);
    });

    //Wanneer op update wordt geklikt
    infoPageScreen.getBtnUpdate().setOnAction(e -> {
      //Verkrijg het geselecteerde gezondheidsprobleem
      Health selectedHealth = table.getSelectionModel().getSelectedItem();
      //Laad de form pagina en geef het geselecteerde gezondheidsprobleem mee
      this.loadForm(selectedHealth);
    });

    return table;
  }

  public VBox getHealthPane() {
    return healthPane;
  }
}
