package com.example.patientendossier.screen;

import com.example.patientendossier.model.Care;
import com.example.patientendossier.model.Patient;
import com.example.patientendossier.model.Report;
import com.example.patientendossier.utility.Utility;
import com.example.patientendossier.utility.Validation;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;

public class ReportScreen {

  private final DossierScreen dossier;
  private final Patient patient;
  private final Care care;
  private VBox reportPane;
  private InfoPageScreen infoPageScreen;
  private TextArea taReport;

  public ReportScreen(DossierScreen dossier, Patient patient, Care care)
  {
    this.dossier = dossier;
    this.patient = patient;
    this.care = care;

    this.load();
  }

  private void load()
  {
    this.infoPageScreen = new InfoPageScreen();
    this.reportPane = infoPageScreen.getVBoxInfoPage();

    if (this.care != null) {
      infoPageScreen.getBtnAdd().setVisible(true);
      infoPageScreen.getBtnDelete().setVisible(true);
      infoPageScreen.getBtnUpdate().setVisible(true);
    }

    infoPageScreen.getBtnAdd().setOnAction(e -> this.loadForm(null));
    infoPageScreen.getLblPage().setText("Verslagen");
    infoPageScreen.getHBoxTable().getChildren().add(this.loadTableView());
  }

  private void loadForm(Report report)
  {
    DossierFormScreen dossierForm = new DossierFormScreen();
    this.dossier.getBorderPane().setCenter(dossierForm.getVBoxFormPage());

    dossierForm.getLblPage().setText("Verslagen");

    GridPane gridForm = dossierForm.getGridForm();

    Label lblReport = new Label("Verslag");
    gridForm.add(lblReport, 0, 1);
    lblReport.setPrefWidth(200);

    this.taReport = new TextArea();
    taReport.setPrefWidth(800);
    gridForm.add(taReport, 1, 1);

    Button btnUpdate = new Button();
    gridForm.add(btnUpdate, 1, 2);
    GridPane.setHalignment(btnUpdate, HPos.RIGHT);

    if (report != null) {
      btnUpdate.setText("Wijzig");
      taReport.setText(report.getReport());

      btnUpdate.setOnAction(e -> {
        if (this.validateForm()) {
          report.setReport(taReport.getText());
          report.setDate(LocalDate.now());
          report.setMadeBy(this.care.getLastname());

          this.patient.updateReport(report);
          this.load();
          this.dossier.getBorderPane().setCenter(this.reportPane);
        }
      });
    } else {
      btnUpdate.setText("Toevoegen");

      btnUpdate.setOnAction(event -> {
        if (this.validateForm()) {

          Integer id = this.care.getHighestReportId() + 1;
          LocalDate date = LocalDate.now();
          Integer patientNumber = this.patient.getNumber();
          String reportText = taReport.getText();
          String madeBy = this.care.getLastname();
          Report newReport = new Report(id, patientNumber, reportText, madeBy, date);

          this.patient.addReport(newReport);
          this.load();
          this.dossier.getBorderPane().setCenter(this.reportPane);
        }
      });
    }
  }

  private boolean validateForm()
  {
    if (!new Validation().validateString(taReport.getText())) {
      new Utility().showAlert(Alert.AlertType.ERROR, dossier.getBorderPane().getScene().getWindow(), "Error!", "Voer een verslag in!");
      return false;
    }

    return true;
  }

  private TableView<Report> loadTableView()
  {
    ArrayList<Report> reports = patient.getReports();
    String[] columnNames = {"Verslag", "Gemaakt door", "Datum"};
    String[] propertyNames = {"report", "madeBy", "date"};

    TableView<Report> table = new TableScreen().createTableView(reports, columnNames, propertyNames);

    table.setPrefWidth(960);

    table.setOnMouseClicked(e -> {
      infoPageScreen.getBtnDelete().setDisable(table.getSelectionModel().getSelectedItem() == null);
      infoPageScreen.getBtnUpdate().setDisable(table.getSelectionModel().getSelectedItem() == null);
    });

    infoPageScreen.getBtnDelete().setOnAction(e -> {
      Report selectedReport = table.getSelectionModel().getSelectedItem();
      this.patient.deleteReport(selectedReport);
      this.load();
      this.dossier.getBorderPane().setCenter(this.reportPane);
    });

    infoPageScreen.getBtnUpdate().setOnAction(e -> {
      Report selectedReport = table.getSelectionModel().getSelectedItem();
      this.loadForm(selectedReport);
    });

    return table;
  }

  public VBox getReportPane() {
    return reportPane;
  }
}
