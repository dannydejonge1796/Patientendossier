package com.example.patientendossier.screen;

import com.example.patientendossier.model.Care;
import com.example.patientendossier.model.Patient;
import com.example.patientendossier.model.Result;
import com.example.patientendossier.utility.Utility;
import com.example.patientendossier.utility.Validation;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;

public class ResultScreen {

  private final DossierScreen dossier;
  private final Patient patient;
  private final Care care;
  private VBox resultPane;
  private InfoPageScreen infoPageScreen;
  private TextArea taResult;

  public ResultScreen(DossierScreen dossier, Patient patient, Care care)
  {
    this.dossier = dossier;
    this.patient = patient;
    this.care = care;

    this.load();
  }

  private void load()
  {
    this.infoPageScreen = new InfoPageScreen();
    this.resultPane = infoPageScreen.getVBoxInfoPage();

    if (this.care != null) {
      infoPageScreen.getBtnAdd().setVisible(true);
      infoPageScreen.getBtnDelete().setVisible(true);
      infoPageScreen.getBtnUpdate().setVisible(true);
    }

    infoPageScreen.getBtnAdd().setOnAction(e -> this.loadForm(null));
    infoPageScreen.getLblPage().setText("Uitslagen");
    infoPageScreen.getHBoxTable().getChildren().add(this.loadTableView());
  }

  private void loadForm(Result result)
  {
    DossierFormScreen dossierForm = new DossierFormScreen();
    this.dossier.getBorderPane().setCenter(dossierForm.getVBoxFormPage());

    dossierForm.getLblPage().setText("Uitslagen");

    GridPane gridForm = dossierForm.getGridForm();

    Label lblResult = new Label("Uitslag");
    gridForm.add(lblResult, 0, 1);
    lblResult.setPrefWidth(200);

    this.taResult = new TextArea();
    taResult.setPrefWidth(800);
    gridForm.add(taResult, 1, 1);

    Button btnUpdate = new Button();
    gridForm.add(btnUpdate, 1, 2);
    GridPane.setHalignment(btnUpdate, HPos.RIGHT);

    if (result != null) {
      btnUpdate.setText("Wijzig");
      taResult.setText(result.getResult());

      btnUpdate.setOnAction(e -> {
        if (this.validateForm()) {
          result.setResult(taResult.getText());
          result.setDate(LocalDate.now());
          result.setMadeBy(this.care.getLastname());

          this.patient.updateResult(result);
          this.load();
          this.dossier.getBorderPane().setCenter(this.resultPane);
        }
      });
    } else {
      btnUpdate.setText("Toevoegen");

      btnUpdate.setOnAction(event -> {
        if (this.validateForm()) {

          Integer id = this.care.getHighestResultId() + 1;
          LocalDate date = LocalDate.now();
          Integer patientNumber = this.patient.getNumber();
          String resultText= taResult.getText();
          String madeBy = this.care.getLastname();
          Result newResult = new Result(id, patientNumber, resultText, madeBy, date);

          this.patient.addResult(newResult);
          this.load();
          this.dossier.getBorderPane().setCenter(this.resultPane);
        }
      });
    }
  }

  private boolean validateForm()
  {
    if (!new Validation().validateString(this.taResult.getText())) {
      new Utility().showAlert(Alert.AlertType.ERROR, dossier.getBorderPane().getScene().getWindow(), "Error!", "Voer een verslag in!");
      return false;
    }

    return true;
  }

  private TableView<Result> loadTableView()
  {
    ArrayList<Result> results = patient.getResults();
    String[] columnNames = {"Uitslag", "Gemaakt door", "Datum"};
    String[] propertyNames = {"result", "madeBy", "date"};

    TableView<Result> table = new TableScreen().createTableView(results, columnNames, propertyNames);

    table.setPrefWidth(960);

    table.setOnMouseClicked(e -> {
      infoPageScreen.getBtnDelete().setDisable(table.getSelectionModel().getSelectedItem() == null);
      infoPageScreen.getBtnUpdate().setDisable(table.getSelectionModel().getSelectedItem() == null);
    });

    infoPageScreen.getBtnDelete().setOnAction(e -> {
      Result selectedResult = table.getSelectionModel().getSelectedItem();
      this.patient.deleteResult(selectedResult);
      this.load();
      this.dossier.getBorderPane().setCenter(this.resultPane);
    });

    infoPageScreen.getBtnUpdate().setOnAction(e -> {
      Result selectedResult = table.getSelectionModel().getSelectedItem();
      this.loadForm(selectedResult);
    });

    return table;
  }

  public VBox getResultPane() {
    return resultPane;
  }
}
