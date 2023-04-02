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

    this.load();
  }

  private void load()
  {
    this.infoPageScreen = new InfoPageScreen();
    this.healthPane = infoPageScreen.getVBoxInfoPage();

    infoPageScreen.getBtnAdd().setOnAction(e -> this.loadForm(null));
    infoPageScreen.getLblPage().setText("Gezondheidsproblemen");
    infoPageScreen.getHBoxTable().getChildren().add(this.loadTableView());
  }

  private void loadForm(Health health)
  {
    DossierFormScreen dossierForm = new DossierFormScreen();
    this.dossier.getBorderPane().setCenter(dossierForm.getVBoxFormPage());

    dossierForm.getLblPage().setText("Gezondheidsproblemen");

    GridPane gridForm = dossierForm.getGridForm();

    Label lblName = new Label("Gezondheidsprobleem");
    gridForm.add(lblName, 0, 1);
    lblName.setPrefWidth(200);

    this.comboHealths = new ComboBox<>();
    this.comboHealths.setPrefWidth(800);
    gridForm.add(this.comboHealths, 1, 1);

    Label lblDescription = new Label("Beschrijving");
    gridForm.add(lblDescription, 0, 2);
    lblDescription.setPrefWidth(200);

    this.tfDescription = new TextField();
    tfDescription.setPrefWidth(800);
    gridForm.add(tfDescription, 1, 2);

    Button btnUpdate = new Button();
    gridForm.add(btnUpdate, 1, 3);
    GridPane.setHalignment(btnUpdate, HPos.RIGHT);

    if (health != null) {
      btnUpdate.setText("Wijzig");
      this.comboHealths.getItems().add(health.getName());
      comboHealths.setValue(health.getName());
      tfDescription.setText(health.getDescription());

      btnUpdate.setOnAction(e -> {
        if (this.validateForm()) {
          health.setDescription(tfDescription.getText());
          this.patient.updateHealth(health);
          this.load();
          this.dossier.getBorderPane().setCenter(this.healthPane);
        }
      });
    } else {
      btnUpdate.setText("Toevoegen");

      ArrayList<String> availableHealths = new ArrayList<>();

      for (String item : this.care.getAllHealthNames()) {
        if (!this.patient.getHealthNames().contains(item)) {
          availableHealths.add(item);
        }
      }

      this.comboHealths.getItems().addAll(availableHealths);

      btnUpdate.setOnAction(e -> {
        if (this.validateForm()) {

          String name = this.comboHealths.getValue();
          String description = tfDescription.getText();
          Health newHealth = new Health(name, description);

          this.patient.addHealth(newHealth);
          this.load();
          this.dossier.getBorderPane().setCenter(this.healthPane);
        }
      });
    }
  }

  private boolean validateForm()
  {
    if (!new Validation().validateString(this.comboHealths.getValue())) {
      new Utility().showAlert(Alert.AlertType.ERROR, dossier.getBorderPane().getScene().getWindow(), "Error!", "Voer een gezondheidsprobleem in!");
      return false;
    }

    if (!new Validation().validateString(tfDescription.getText())) {
      new Utility().showAlert(Alert.AlertType.ERROR, dossier.getBorderPane().getScene().getWindow(), "Error!", "Voer een geldige beschrijving in!");
      return false;
    }

    return true;
  }

  private TableView<Health> loadTableView()
  {
    ArrayList<Health> healths = patient.getHealths();
    String[] columnNames = {"Naam", "Beschrijving"};
    String[] propertyNames = {"name", "description"};

    TableView<Health> table = new TableScreen().createTableView(healths, columnNames, propertyNames);

    table.setPrefWidth(960);

    table.setOnMouseClicked(e -> {
      infoPageScreen.getBtnDelete().setDisable(table.getSelectionModel().getSelectedItem() == null);
      infoPageScreen.getBtnUpdate().setDisable(table.getSelectionModel().getSelectedItem() == null);
    });

    infoPageScreen.getBtnDelete().setOnAction(e -> {
      Health selectedHealth = table.getSelectionModel().getSelectedItem();
      this.patient.deleteHealth(selectedHealth);
      this.load();
      this.dossier.getBorderPane().setCenter(this.healthPane);
    });

    infoPageScreen.getBtnUpdate().setOnAction(e -> {
      Health selectedHealth = table.getSelectionModel().getSelectedItem();
      this.loadForm(selectedHealth);
    });

    return table;
  }

  public VBox getHealthPane() {
    return healthPane;
  }
}
