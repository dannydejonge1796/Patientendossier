package com.example.patientendossier.screen;

import com.example.patientendossier.model.Allergy;
import com.example.patientendossier.model.Care;
import com.example.patientendossier.model.Patient;
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

    this.load();
  }

  private void load()
  {
    this.infoPageScreen = new InfoPageScreen();
    this.allergyPane = infoPageScreen.getVBoxInfoPage();

    infoPageScreen.getBtnAdd().setOnAction(e -> this.loadForm(null));
    infoPageScreen.getLblPage().setText("Allergieën");
    infoPageScreen.getHBoxTable().getChildren().add(this.loadTableView());
  }

  private void loadForm(Allergy allergy)
  {
    DossierFormScreen dossierForm = new DossierFormScreen();
    this.dossier.getBorderPane().setCenter(dossierForm.getVBoxFormPage());

    dossierForm.getLblPage().setText("Allergieën");

    GridPane gridForm = dossierForm.getGridForm();

    Label lblName = new Label("Allergie");
    gridForm.add(lblName, 0, 1);
    lblName.setPrefWidth(200);

    this.comboAllergies = new ComboBox<>();
    comboAllergies.setPrefWidth(800);
    gridForm.add(comboAllergies, 1, 1);

    Label lblDescription = new Label("Beschrijving");
    gridForm.add(lblDescription, 0, 2);
    lblDescription.setPrefWidth(200);

    this.tfDescription = new TextField();
    tfDescription.setPrefWidth(800);
    gridForm.add(tfDescription, 1, 2);

    Button btnUpdate = new Button();
    gridForm.add(btnUpdate, 1, 3);
    GridPane.setHalignment(btnUpdate, HPos.RIGHT);

    if (allergy != null) {
      btnUpdate.setText("Wijzig");
      comboAllergies.getItems().add(allergy.getName());
      comboAllergies.setValue(allergy.getName());
      tfDescription.setText(allergy.getDescription());

      btnUpdate.setOnAction(e -> {
        if (this.validateForm()) {
          allergy.setDescription(tfDescription.getText());
          this.patient.updateAllergy(allergy);
          this.load();
          this.dossier.getBorderPane().setCenter(this.allergyPane);
        }
      });
    } else {
      btnUpdate.setText("Toevoegen");

      ArrayList<String> availableAllergies = new ArrayList<>();

      for (String item : this.patient.getAllergyNames()) {
        if (!this.care.getAllAllergyNames().contains(item)) {
          availableAllergies.add(item);
        }
      }

      for (String item : this.care.getAllAllergyNames()) {
        if (!this.patient.getAllergyNames().contains(item)) {
          availableAllergies.add(item);
        }
      }

      this.comboAllergies.getItems().addAll(availableAllergies);

      btnUpdate.setOnAction(e -> {
        if (this.validateForm()) {

          String name = comboAllergies.getValue();
          String description = tfDescription.getText();
          Allergy newAllergy = new Allergy(name, description);

          this.patient.addAllergy(newAllergy);
          this.load();
          this.dossier.getBorderPane().setCenter(this.allergyPane);
        }
      });
    }
  }

  private boolean validateForm()
  {
    if (!new Validation().validateString(comboAllergies.getValue())) {
      return false;
    }
    return new Validation().validateString(tfDescription.getText());
  }

  private TableView<Allergy> loadTableView()
  {
    ArrayList<Allergy> allergies = patient.getAllergies();
    String[] columnNames = {"Naam", "Beschrijving"};
    String[] propertyNames = {"name", "description"};

    TableView<Allergy> table = new TableScreen().createTableView(allergies, columnNames, propertyNames);

    table.setOnMouseClicked(e -> {
      infoPageScreen.getBtnDelete().setDisable(table.getSelectionModel().getSelectedItem() == null);
      infoPageScreen.getBtnUpdate().setDisable(table.getSelectionModel().getSelectedItem() == null);
    });

    infoPageScreen.getBtnDelete().setOnAction(e -> {
      Allergy selectedAllergy = table.getSelectionModel().getSelectedItem();
      this.patient.deleteAlergy(selectedAllergy);
      this.load();
      this.dossier.getBorderPane().setCenter(this.allergyPane);
    });

    infoPageScreen.getBtnUpdate().setOnAction(e -> {
      Allergy selectedAllergy = table.getSelectionModel().getSelectedItem();
      this.loadForm(selectedAllergy);
    });

    return table;
  }

  public VBox getAllergyPane() {
    return allergyPane;
  }
}
