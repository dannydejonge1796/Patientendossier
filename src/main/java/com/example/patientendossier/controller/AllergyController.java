package com.example.patientendossier.controller;

import com.example.patientendossier.model.Allergy;
import com.example.patientendossier.model.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AllergyController {

  private final Patient patient;
  private final ScrollPane allergyPane;

  AllergyController(Patient patient)
  {
    this.patient = patient;

    InfoPageController infoPageController = new InfoPageController();
    this.allergyPane = infoPageController.getInfoPagePane();

    infoPageController.getBtnAdd().setOnAction(e -> this.loadForm());
    infoPageController.getLblPage().setText("Allergieën");
    infoPageController.getHBoxTable().getChildren().add(this.loadTableView());
  }

  private void loadForm()
  {
    DossierFormController dossierForm = new DossierFormController();


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

    table.setPrefWidth(960);

    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    table.getSortOrder().add(colName);

    return table;
  }

  public ScrollPane getAllergyPane() {
    return allergyPane;
  }
}
