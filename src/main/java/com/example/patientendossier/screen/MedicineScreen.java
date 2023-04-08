package com.example.patientendossier.screen;

import com.example.patientendossier.model.Care;
import com.example.patientendossier.model.Medicine;
import com.example.patientendossier.model.Patient;
import com.example.patientendossier.utility.Utility;
import com.example.patientendossier.utility.Validation;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class MedicineScreen {

  private final DossierScreen dossier;
  private final Patient patient;
  private final Care care;
  private VBox medicinePane;
  private InfoPageScreen infoPageScreen;
  private ComboBox<String> comboMedicines;
  private TextField tfDescription;
  private TextField tfDosage;

  public MedicineScreen(DossierScreen dossier, Patient patient, Care care)
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
    this.medicinePane = infoPageScreen.getVBoxInfoPage();
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
    infoPageScreen.getLblPage().setText("Medicijnen");
    //Tabel toevoegen aan de daarvoor bestemde pane
    infoPageScreen.getHBoxTable().getChildren().add(this.loadTableView());
  }

  private void loadForm(Medicine medicine)
  {
    //Form pagina aanmaken en in de borderpane stoppen
    DossierFormScreen dossierForm = new DossierFormScreen();
    //Pagina wordt weergegeven
    this.dossier.getBorderPane().setCenter(dossierForm.getVBoxFormPage());
    //Terug knop ophalen
    Button btnBack = this.dossier.getBtnBack();
    //Actie functie van terugknop wijzigen
    btnBack.setOnAction(e -> {
      //Info pagina in borderpane stoppen en wordt dan weergegeven
      dossier.getBorderPane().setCenter(new MedicineScreen(this.dossier, this.patient, this.care).getMedicinePane());
      //Terug knop weer andere functionaliteit geven
      dossier.setBtnBack();
    });
    //Pagina label text aanpassen
    dossierForm.getLblPage().setText("Medicijnen");
    //Form grid uit het dossier form halen
    GridPane gridForm = dossierForm.getGridForm();
    //Label initialiseren
    Label lblName = new Label("Medicijn");
    //Toevoegen aan grid
    gridForm.add(lblName, 0, 1);
    //Voorkeur breedte instellen
    lblName.setPrefWidth(200);
    //Combobox initialiseren
    this.comboMedicines = new ComboBox<>();
    //Voorkeur breedte instellen
    this.comboMedicines.setPrefWidth(800);
    //Toevoegen aan grid
    gridForm.add(this.comboMedicines, 1, 1);
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
    //Label initialiseren
    Label lblDosage = new Label("Dosering");
    //Toevoegen aan grid
    gridForm.add(lblDosage, 0, 3);
    //Voorkeur breedte instellen
    lblDosage.setPrefWidth(200);
    //Textfield initialiseren
    this.tfDosage = new TextField();
    //Voorkeur breedte instellen
    tfDosage.setPrefWidth(800);
    //Toevoegen aan grid
    gridForm.add(tfDosage, 1, 3);
    //Knop initialiseren
    Button btnUpdate = new Button();
    //Toevoegen aan grid
    gridForm.add(btnUpdate, 1, 4);
    //Knop aan de rechter kant plaatsen
    GridPane.setHalignment(btnUpdate, HPos.RIGHT);
    //Als er een medicine object is
    if (medicine != null) {
      //Verander knop text naar wijzig
      btnUpdate.setText("Wijzig");
      //Voeg alleen de huidige medicijn naam toe aan de combo
      comboMedicines.getItems().add(medicine.getName());
      //Zet deze als default value
      comboMedicines.setValue(medicine.getName());
      //Huidige descriptie als text van het text veld instellen
      tfDescription.setText(medicine.getDescription());
      //Huidige dosering als text van het text veld instellen
      tfDosage.setText(medicine.getDosage());
      //Als op knop wordt gedrukt
      btnUpdate.setOnAction(e -> {
        //Als validatie functie true teruggeeft
        if (this.validateForm()) {
          //Update alle data in het object
          medicine.setDescription(tfDescription.getText());
          medicine.setDosage(tfDosage.getText());
          //Update medicijnen in de database
          this.patient.updateMedicine(medicine);
          //Herlaad pagina
          this.load();
          this.dossier.getBorderPane().setCenter(this.medicinePane);
        }
      });
    //Als er geen medicijn object is
    } else {
      //Text van knop is toevoegen
      btnUpdate.setText("Toevoegen");
      //Initialiseer lijst van nog niet toegewezen medicijnen
      ArrayList<String> availableMedicines = new ArrayList<>();
      //Loop door alle bestaande medicijnen heen
      for (String item : this.care.getAllMedicineNames()) {
        //Als de patient het medicijn nog niet gebruikt
        if (!this.patient.getMedicineNames().contains(item)) {
          //Voeg toe aan lijst
          availableMedicines.add(item);
        }
      }
      //Voeg alle beschikbare medicijnen toe aan de combobox
      this.comboMedicines.getItems().addAll(availableMedicines);
      //Als op de knop gedrukt wordt
      btnUpdate.setOnAction(e -> {
        //Als validatie functie true teruggeeft
        if (this.validateForm()) {
          //Haal de values uit de velden
          String name = comboMedicines.getValue();
          String description = tfDescription.getText();
          String dosage = tfDosage.getText();
          //Maak nieuw medicine object
          Medicine newMedicine = new Medicine(name, description, dosage);
          //Voeg het medicijn toe aan de patient in de database
          this.patient.addMedicine(newMedicine);
          //Herlaad de pagina
          this.load();
          this.dossier.getBorderPane().setCenter(this.medicinePane);
        }
      });
    }
  }

  private boolean validateForm()
  {
    //Check of de combobox een string als value heeft
    if (!new Validation().validateString(comboMedicines.getValue())) {
      //Anders alert met foutmelding
      new Utility().showAlert(Alert.AlertType.ERROR, dossier.getBorderPane().getScene().getWindow(), "Error!", "Voer een medicijn in!");
      return false;
    }

    //Check of het veld een string als value heeft
    if (!new Validation().validateString(tfDescription.getText())) {
      //Anders alert met foutmelding
      new Utility().showAlert(Alert.AlertType.ERROR, dossier.getBorderPane().getScene().getWindow(), "Error!", "Voer een geldige beschrijving in!");
      return false;
    }

    //Check of het veld een string als value heeft
    if (!new Validation().validateString(tfDosage.getText())) {
      //Anders alert met foutmelding
      new Utility().showAlert(Alert.AlertType.ERROR, dossier.getBorderPane().getScene().getWindow(), "Error!", "Voer een geldige dosering in!");
      return false;
    }

    return true;
  }

  private TableView<Medicine> loadTableView()
  {
    //Voeg alle medicijnen van de patient toe aan de lijst
    ArrayList<Medicine> medicines = patient.getMedicines();
    //Maak een array met kolom namen
    String[] columnNames = {"Naam", "Beschrijving", "Dosering"};
    //Maak een array met attribute namen van het object
    String[] propertyNames = {"name", "description", "dosage"};
    //Laad de tabel
    TableView<Medicine> table = new TableScreen().createTableView(medicines, columnNames, propertyNames);
    //Wijzig het formaat van de tabel
    table.setPrefWidth(960);
    //Wanneer een rij wordt aangeklikt, kunnen de knoppen beschikbaar worden gemaakt
    table.setOnMouseClicked(e -> {
      infoPageScreen.getBtnDelete().setDisable(table.getSelectionModel().getSelectedItem() == null);
      infoPageScreen.getBtnUpdate().setDisable(table.getSelectionModel().getSelectedItem() == null);
    });

    //Delete knop actie
    infoPageScreen.getBtnDelete().setOnAction(e -> {
      //Verkrijg de geselecteerde medicine
      Medicine selectedMedicine = table.getSelectionModel().getSelectedItem();
      //Verwijder het medicijn uit de database
      this.patient.deleteMedicine(selectedMedicine);
      //Herlaad de pagina
      this.load();
      this.dossier.getBorderPane().setCenter(this.medicinePane);
    });

    //Wanneer op update wordt geklikt
    infoPageScreen.getBtnUpdate().setOnAction(e -> {
      //Verkrijg het geselecteerde medicijn
      Medicine selectedMedicine = table.getSelectionModel().getSelectedItem();
      //Laad de form pagina en geef het geselecteerde medicijn mee
      this.loadForm(selectedMedicine);
    });

    return table;
  }

  public VBox getMedicinePane() {
    return medicinePane;
  }
}
