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
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class ReportScreen {

  private final DossierScreen dossier;
  private final Patient patient;
  private final Care care;
  private VBox reportPane;
  private InfoPageScreen infoPageScreen;
  private FileChooser fileChooser;
  private TextField tfDescription;
  private File selectedFile;
  private String fileFormat;

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

    Label lblFile = new Label("Bestand");
    gridForm.add(lblFile, 0, 1);
    lblFile.setPrefWidth(200);

    VBox vBoxUpload = new VBox();
    vBoxUpload.setSpacing(5);

    Button uploadButton = new Button("Upload");
    Label fileStatus = new Label("Selecteer een bestand");

    vBoxUpload.getChildren().addAll(uploadButton, fileStatus);

    gridForm.add(vBoxUpload, 1, 1);

    this.selectedFile = null;

    uploadButton.setOnAction(event -> {
      // Create a FileChooser
      this.fileChooser = new FileChooser();
      fileChooser.setTitle("Select document");
      fileChooser.getExtensionFilters().addAll(
              new FileChooser.ExtensionFilter("Word Documents", "*.docx", "*.doc"),
              new FileChooser.ExtensionFilter("PDF Documents", "*.pdf")
      );
      // Show the file chooser dialog
      selectedFile = fileChooser.showOpenDialog(this.dossier.getBorderPane().getScene().getWindow());
      if (selectedFile != null) {
        fileStatus.setText(selectedFile.getAbsolutePath());
      }
    });

    Label lblDescription = new Label("Beschrijving");
    gridForm.add(lblDescription, 0, 2);
    lblDescription.setPrefWidth(200);

    this.tfDescription = new TextField();
    tfDescription.setPrefWidth(800);
    gridForm.add(tfDescription, 1, 2);

    Button btnUpdate = new Button();
    gridForm.add(btnUpdate, 1, 3);
    GridPane.setHalignment(btnUpdate, HPos.RIGHT);

    if (report != null) {
      btnUpdate.setText("Wijzig");
      tfDescription.setText(report.getDescription());

      btnUpdate.setOnAction(e -> {
        if (this.validateForm()) {
          report.setDescription(tfDescription.getText());
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

          if (selectedFile != null) {
            StringBuilder builder = new StringBuilder();
            // Copy the selected file to the resources folder
            try {
              Path sourcePath = Paths.get(selectedFile.getAbsolutePath());
              Path resourcesPath = Paths.get(Objects.requireNonNull(getClass().getResource("/reports/")).toURI());

              String fileName = sourcePath.getFileName().toString();
              int dotIndex = fileName.lastIndexOf('.');
              if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
                fileFormat = fileName.substring(dotIndex + 1);
              }

              String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

              Random random = new Random();
              for (int i = 0; i < 16; i++) {
                int index = random.nextInt(ALPHA_NUMERIC_STRING.length());
                char randomChar = ALPHA_NUMERIC_STRING.charAt(index);
                builder.append(randomChar);
              }

              Path destinationPath = Paths.get(resourcesPath.toString(), builder + fileFormat);
              Files.copy(sourcePath, destinationPath);
              // Handle the copied file
              fileStatus.setText(selectedFile.getAbsolutePath());
              String filename = builder + fileFormat;
            } catch (IOException | NullPointerException | SecurityException | IllegalArgumentException | java.net.URISyntaxException e) {
              System.err.println("Error copying file: " + e.getMessage());
            }
          }

          Integer id = this.care.getHighestReportId() + 1;
          Integer patientNumber = this.patient.getNumber();

          String description = tfDescription.getText();
          String madeBy = this.care.getLastname();
          Report newReport = new Report(id, patientNumber, filename, description, madeBy);

          this.patient.addReport(newReport);
          this.load();
          this.dossier.getBorderPane().setCenter(this.reportPane);
        }
      });
    }
  }

  private boolean validateForm()
  {
    if (!new Validation().validateString(tfDescription.getText())) {
      new Utility().showAlert(Alert.AlertType.ERROR, dossier.getBorderPane().getScene().getWindow(), "Error!", "Voer een geldige beschrijving in!");
      return false;
    }

    return true;
  }

  private TableView<Report> loadTableView()
  {
    ArrayList<Report> reports = patient.getReports();
    String[] columnNames = {"Bestand", "Beschrijving", "Door"};
    String[] propertyNames = {"filename", "description", "madeBy"};

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
