package com.example.patientendossier.controller;

import com.example.patientendossier.model.Allergy;
import com.example.patientendossier.model.Care;
import com.example.patientendossier.model.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class AllergyController {

  private final DossierController dossier;
  private final Patient patient;
  private final Care care;
  private final VBox allergyPane;

  AllergyController(DossierController dossier, Patient patient, Care care)
  {
    this.dossier = dossier;
    this.patient = patient;
    this.care = care;

    InfoPageController infoPageController = new InfoPageController();
    this.allergyPane = infoPageController.getVBoxInfoPage();

    infoPageController.getBtnAdd().setOnAction(e -> this.loadForm(null));
    infoPageController.getLblPage().setText("Allergieën");
    infoPageController.getHBoxTable().getChildren().add(this.loadTableView());
  }

  private void loadForm(Allergy allergy)
  {
    DossierFormController dossierForm = new DossierFormController();
    this.dossier.getBorderPane().setCenter(dossierForm.getVBoxFormPage());

    dossierForm.getLblPage().setText("Allergieën");

    GridPane gridForm = dossierForm.getGridForm();

    Label lblName = new Label("Allergie");
    gridForm.add(lblName, 0, 1);
    lblName.setPrefWidth(200);

    ComboBox<String> comboAllergies = new ComboBox<>();
    comboAllergies.getItems().addAll(care.getAllAllergyNames());
    comboAllergies.setPrefWidth(800);
    gridForm.add(comboAllergies, 1, 1);

    Label lblDescription = new Label("Beschrijving");
    gridForm.add(lblDescription, 0, 2);
    lblDescription.setPrefWidth(200);

    TextField tfDescription = new TextField();
    tfDescription.setPrefWidth(800);
    gridForm.add(tfDescription, 1, 2);
  }

  private TableView<Allergy> loadTableView()
  {
    TableView<Allergy> table = new TableView<>();

    ObservableList<Allergy> olAllergies = FXCollections.observableArrayList();
    olAllergies.addAll(patient.getAllergies());

    table.setItems(olAllergies);

    TableColumn<Allergy, String> colName = new TableColumn<>("Naam");
    TableColumn<Allergy, String> colDescription = new TableColumn<>("Beschrijving");

    colName.setCellValueFactory(new PropertyValueFactory<>("name"));
    colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

    table.getColumns().add(colName);
    table.getColumns().add(colDescription);

    table.setPrefWidth(950);

    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    table.getSortOrder().add(colName);

    return table;
  }

  public VBox getAllergyPane() {
    return allergyPane;
  }
}
