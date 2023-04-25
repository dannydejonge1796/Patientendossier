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

    //Laad de info pagina
    this.load();
  }

  private void load()
  {
    //Maak nieuwe info page
    this.infoPageScreen = new InfoPageScreen();
    this.reportPane = infoPageScreen.getVBoxInfoPage();

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
    infoPageScreen.getLblPage().setText("Verslagen");
    //Tabel toevoegen aan de daarvoor bestemde pane
    infoPageScreen.getHBoxTable().getChildren().add(this.loadTableView());
  }

  private void loadForm(Report report)
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
      dossier.getBorderPane().setCenter(new ReportScreen(this.dossier, this.patient, this.care).getReportPane());
      //Terug knop weer andere functionaliteit geven
      dossier.setLblBack();
    });

    //Pagina label text aanpassen
    dossierForm.getLblPage().setText("Verslagen");

    //Form grid uit het dossier form halen
    GridPane gridForm = dossierForm.getGridForm();

    //Label initialiseren
    Label lblReport = new Label("Verslag");
    //Toevoegen aan grid
    gridForm.add(lblReport, 0, 1);
    //Voorkeur breedte instellen
    lblReport.setPrefWidth(200);

    //Text area initialiseren
    this.taReport = new TextArea();
    //Voorkeur breedte instellen
    taReport.setPrefWidth(800);
    //Toevoegen aan grid
    gridForm.add(taReport, 1, 1);

    //Knop initialiseren
    Button btnUpdate = new Button();
    //Toevoegen aan grid
    gridForm.add(btnUpdate, 1, 2);
    //Knop aan de rechter kant plaatsen
    GridPane.setHalignment(btnUpdate, HPos.RIGHT);

    //Als er een verslag object is
    if (report != null) {
      //Verander knop text naar wijzig
      btnUpdate.setText("Wijzig");
      //Huidige verslag als text van het text veld instellen
      taReport.setText(report.getReport());

      //Als op knop wordt gedrukt
      btnUpdate.setOnAction(e -> {
        //Als validatie functie true teruggeeft
        if (this.validateForm()) {
          //Werk de attributen van het object bij met de ingevoerde data
          report.setReport(taReport.getText());
          report.setDate(LocalDate.now());
          report.setMadeBy(this.care.getLastname());

          //Update verslagen in de database
          this.patient.updateReport(report);
          //Herlaad de pagina
          this.load();
          this.dossier.getBorderPane().setCenter(this.reportPane);
        }
      });
     //Als er geen verslag object is
    } else {
      //Text van knop is toevoegen
      btnUpdate.setText("Toevoegen");

      //Knop actie instellen
      btnUpdate.setOnAction(event -> {
        //Als validatie functie true teruggeeft
        if (this.validateForm()) {
          //Hoogste verslag id ophalen en 1 bijvoegen
          Integer id = this.care.getHighestReportId() + 1;
          //Haal data uit velden
          LocalDate date = LocalDate.now();
          Integer patientNumber = this.patient.getNumber();
          String reportText = taReport.getText();
          String madeBy = this.care.getLastname();
          //Maak nieuw verslag object met data
          Report newReport = new Report(id, patientNumber, reportText, madeBy, date);

          //Voeg het verslag toe aan de patient in de database
          this.patient.addReport(newReport);
          //Herlaad de pagina
          this.load();
          this.dossier.getBorderPane().setCenter(this.reportPane);
        }
      });
    }
  }

  private boolean validateForm()
  {
    //Check of het veld een string als value heeft
    if (!new Validation().validateString(taReport.getText())) {
      //Anders alert met foutmelding
      new Utility().showAlert(Alert.AlertType.ERROR, dossier.getBorderPane().getScene().getWindow(), "Error!", "Voer een verslag in!");
      return false;
    }

    //Alles goed = return true
    return true;
  }

  private TableView<Report> loadTableView()
  {
    //Voeg alle verslagen van de patient toe aan de lijst
    ArrayList<Report> reports = patient.getReports();
    //Maak een array met kolom namen
    String[] columnNames = {"Verslag", "Gemaakt door", "Datum"};
    //Maak een array met attribute namen van het object
    String[] propertyNames = {"report", "madeBy", "date"};

    //Laad de tabel
    TableView<Report> table = new TableScreen().createTableView(reports, columnNames, propertyNames);

    //Wijzig het formaat van de tabel
    table.setPrefWidth(960);

    //Wanneer een rij wordt aangeklikt, kunnen de knoppen beschikbaar worden gemaakt
    table.setOnMouseClicked(e -> {
      infoPageScreen.getBtnDelete().setDisable(table.getSelectionModel().getSelectedItem() == null);
      infoPageScreen.getBtnUpdate().setDisable(table.getSelectionModel().getSelectedItem() == null);
    });

    //Delete knop actie
    infoPageScreen.getBtnDelete().setOnAction(e -> {
      //Verkrijg het geselecteerde verslag
      Report selectedReport = table.getSelectionModel().getSelectedItem();
      //Verwijder het verslag uit de database
      this.patient.deleteReport(selectedReport);
      //Herlaad pagina
      this.load();
      this.dossier.getBorderPane().setCenter(this.reportPane);
    });

    infoPageScreen.getBtnUpdate().setOnAction(e -> {
      //Verkrijg het geselecteerde verslag
      Report selectedReport = table.getSelectionModel().getSelectedItem();
      //Laad de form pagina en geef het geselecteerde verslag mee
      this.loadForm(selectedReport);
    });

    return table;
  }

  public VBox getReportPane() {
    return reportPane;
  }
}
