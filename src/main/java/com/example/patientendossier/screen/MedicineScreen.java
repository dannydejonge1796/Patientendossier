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

    this.load();
  }

  private void load()
  {
    this.infoPageScreen = new InfoPageScreen();
    this.medicinePane = infoPageScreen.getVBoxInfoPage();

    if (this.care != null) {
      infoPageScreen.getBtnAdd().setVisible(true);
      infoPageScreen.getBtnDelete().setVisible(true);
      infoPageScreen.getBtnUpdate().setVisible(true);
    }

    infoPageScreen.getBtnAdd().setOnAction(e -> this.loadForm(null));
    infoPageScreen.getLblPage().setText("Medicijnen");
    infoPageScreen.getHBoxTable().getChildren().add(this.loadTableView());
  }

  private void loadForm(Medicine medicine)
  {
    DossierFormScreen dossierForm = new DossierFormScreen();
    this.dossier.getBorderPane().setCenter(dossierForm.getVBoxFormPage());

    dossierForm.getLblPage().setText("Medicijnen");

    GridPane gridForm = dossierForm.getGridForm();

    Label lblName = new Label("Medicijn");
    gridForm.add(lblName, 0, 1);
    lblName.setPrefWidth(200);

    this.comboMedicines = new ComboBox<>();
    this.comboMedicines.setPrefWidth(800);
    gridForm.add(this.comboMedicines, 1, 1);

    Label lblDescription = new Label("Beschrijving");
    gridForm.add(lblDescription, 0, 2);
    lblDescription.setPrefWidth(200);

    this.tfDescription = new TextField();
    tfDescription.setPrefWidth(800);
    gridForm.add(tfDescription, 1, 2);

    Label lblDosage = new Label("Dosering");
    gridForm.add(lblDosage, 0, 3);
    lblDosage.setPrefWidth(200);

    this.tfDosage = new TextField();
    tfDosage.setPrefWidth(800);
    gridForm.add(tfDosage, 1, 3);

    Button btnUpdate = new Button();
    gridForm.add(btnUpdate, 1, 4);
    GridPane.setHalignment(btnUpdate, HPos.RIGHT);

    if (medicine != null) {
      btnUpdate.setText("Wijzig");
      comboMedicines.getItems().add(medicine.getName());
      comboMedicines.setValue(medicine.getName());
      tfDescription.setText(medicine.getDescription());
      tfDosage.setText(medicine.getDosage());

      btnUpdate.setOnAction(e -> {
        if (this.validateForm()) {
          medicine.setDescription(tfDescription.getText());
          medicine.setDosage(tfDosage.getText());
          this.patient.updateMedicine(medicine);
          this.load();
          this.dossier.getBorderPane().setCenter(this.medicinePane);
        }
      });
    } else {
      btnUpdate.setText("Toevoegen");

      ArrayList<String> availableMedicines = new ArrayList<>();

      for (String item : this.care.getAllMedicineNames()) {
        if (!this.patient.getMedicineNames().contains(item)) {
          availableMedicines.add(item);
        }
      }

      this.comboMedicines.getItems().addAll(availableMedicines);

      btnUpdate.setOnAction(e -> {
        if (this.validateForm()) {

          String name = comboMedicines.getValue();
          String description = tfDescription.getText();
          String dosage = tfDosage.getText();
          Medicine newMedicine = new Medicine(name, description, dosage);

          this.patient.addMedicine(newMedicine);
          this.load();
          this.dossier.getBorderPane().setCenter(this.medicinePane);
        }
      });
    }
  }

  private boolean validateForm()
  {
    if (!new Validation().validateString(comboMedicines.getValue())) {
      new Utility().showAlert(Alert.AlertType.ERROR, dossier.getBorderPane().getScene().getWindow(), "Error!", "Voer een medicijn in!");
      return false;
    }

    if (!new Validation().validateString(tfDescription.getText())) {
      new Utility().showAlert(Alert.AlertType.ERROR, dossier.getBorderPane().getScene().getWindow(), "Error!", "Voer een geldige beschrijving in!");
      return false;
    }

    if (!new Validation().validateString(tfDosage.getText())) {
      new Utility().showAlert(Alert.AlertType.ERROR, dossier.getBorderPane().getScene().getWindow(), "Error!", "Voer een geldige dosering in!");
      return false;
    }

    return true;
  }

  private TableView<Medicine> loadTableView()
  {
    ArrayList<Medicine> medicines = patient.getMedicines();
    String[] columnNames = {"Naam", "Beschrijving", "Dosering"};
    String[] propertyNames = {"name", "description", "dosage"};

    TableView<Medicine> table = new TableScreen().createTableView(medicines, columnNames, propertyNames);

    table.setPrefWidth(960);

    table.setOnMouseClicked(e -> {
      infoPageScreen.getBtnDelete().setDisable(table.getSelectionModel().getSelectedItem() == null);
      infoPageScreen.getBtnUpdate().setDisable(table.getSelectionModel().getSelectedItem() == null);
    });

    infoPageScreen.getBtnDelete().setOnAction(e -> {
      Medicine selectedMedicine = table.getSelectionModel().getSelectedItem();
      this.patient.deleteMedicine(selectedMedicine);
      this.load();
      this.dossier.getBorderPane().setCenter(this.medicinePane);
    });

    infoPageScreen.getBtnUpdate().setOnAction(e -> {
      Medicine selectedMedicine = table.getSelectionModel().getSelectedItem();
      this.loadForm(selectedMedicine);
    });

    return table;
  }

  public VBox getMedicinePane() {
    return medicinePane;
  }
}
