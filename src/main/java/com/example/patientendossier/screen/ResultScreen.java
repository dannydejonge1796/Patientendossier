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

    //Laad de info pagina
    this.load();
  }

  private void load()
  {
    //Maak nieuwe info page
    this.infoPageScreen = new InfoPageScreen();
    this.resultPane = infoPageScreen.getVBoxInfoPage();

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
    infoPageScreen.getLblPage().setText("Uitslagen");
    //Tabel toevoegen aan de daarvoor bestemde pane
    infoPageScreen.getHBoxTable().getChildren().add(this.loadTableView());
  }

  private void loadForm(Result result)
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
      dossier.getBorderPane().setCenter(new ResultScreen(this.dossier, this.patient, this.care).getResultPane());
      //Terug knop weer andere functionaliteit geven
      dossier.setLblBack();
    });

    //Pagina label text aanpassen
    dossierForm.getLblPage().setText("Uitslagen");

    //Form grid uit het dossier form halen
    GridPane gridForm = dossierForm.getGridForm();

    //Label initialiseren
    Label lblResult = new Label("Uitslag");
    //Toevoegen aan grid
    gridForm.add(lblResult, 0, 1);
    //Voorkeur breedte instellen
    lblResult.setPrefWidth(200);

    //Text area initialiseren
    this.taResult = new TextArea();
    //Voorkeur breedte instellen
    taResult.setPrefWidth(800);
    //Toevoegen aan grid
    gridForm.add(taResult, 1, 1);

    //Knop initialiseren
    Button btnUpdate = new Button();
    //Toevoegen aan grid
    gridForm.add(btnUpdate, 1, 2);
    //Knop aan de rechter kant plaatsen
    GridPane.setHalignment(btnUpdate, HPos.RIGHT);

    //Als er een uitslag object is
    if (result != null) {
      //Verander knop text naar wijzig
      btnUpdate.setText("Wijzig");
      //Huidige uitslag als text van het text veld instellen
      taResult.setText(result.getResult());

      //Als op knop wordt gedrukt
      btnUpdate.setOnAction(e -> {
        //Als validatie functie true teruggeeft
        if (this.validateForm()) {
          //Werk de attributen van het object bij met de ingevoerde data
          result.setResult(taResult.getText());
          result.setDate(LocalDate.now());
          result.setMadeBy(this.care.getLastname());

          //Update uitslagen in de database
          this.patient.updateResult(result);
          //Herlaad de pagina
          this.load();
          this.dossier.getBorderPane().setCenter(this.resultPane);
        }
      });
    //Als er geen uitslag object is
    } else {
      //Text van knop is toevoegen
      btnUpdate.setText("Toevoegen");
      //Knop actie instellen
      btnUpdate.setOnAction(event -> {
        //Als validatie functie true teruggeeft
        if (this.validateForm()) {
          //Hoogste uitslag id ophalen en 1 bijvoegen
          Integer id = this.care.getHighestResultId() + 1;
          //Haal data uit velden
          LocalDate date = LocalDate.now();
          Integer patientNumber = this.patient.getNumber();
          String resultText= taResult.getText();
          String madeBy = this.care.getLastname();
          //Maak nieuw uitslag object met data
          Result newResult = new Result(id, patientNumber, resultText, madeBy, date);
          //Voeg de uitslag toe aan de patient in de database
          this.patient.addResult(newResult);
          //Herlaad de pagina
          this.load();
          this.dossier.getBorderPane().setCenter(this.resultPane);
        }
      });
    }
  }

  private boolean validateForm()
  {
    //Check of het veld een string als value heeft
    if (!new Validation().validateString(this.taResult.getText())) {
      //Anders alert met foutmelding
      new Utility().showAlert(Alert.AlertType.ERROR, dossier.getBorderPane().getScene().getWindow(), "Error!", "Voer een verslag in!");
      return false;
    }

    //Alles goed = return true
    return true;
  }

  private TableView<Result> loadTableView()
  {
    //Voeg alle uitslagen van de patient toe aan de lijst
    ArrayList<Result> results = patient.getResults();
    //Maak een array met kolom namen
    String[] columnNames = {"Uitslag", "Gemaakt door", "Datum"};
    //Maak een array met attribute namen van het object
    String[] propertyNames = {"result", "madeBy", "date"};

    //Laad de tabel
    TableView<Result> table = new TableScreen().createTableView(results, columnNames, propertyNames);

    //Wijzig het formaat van de tabel
    table.setPrefWidth(1260);

    //Wanneer een rij wordt aangeklikt, kunnen de knoppen beschikbaar worden gemaakt
    table.setOnMouseClicked(e -> {
      infoPageScreen.getBtnDelete().setDisable(table.getSelectionModel().getSelectedItem() == null);
      infoPageScreen.getBtnUpdate().setDisable(table.getSelectionModel().getSelectedItem() == null);
    });

    //Delete knop actie
    infoPageScreen.getBtnDelete().setOnAction(e -> {
      //Verkrijg de geselecteerde uitslag
      Result selectedResult = table.getSelectionModel().getSelectedItem();
      //Verwijder de uitslag uit de database
      this.patient.deleteResult(selectedResult);
      //Herlaad pagina
      this.load();
      this.dossier.getBorderPane().setCenter(this.resultPane);
    });

    infoPageScreen.getBtnUpdate().setOnAction(e -> {
      //Verkrijg de geselecteerde uitslag
      Result selectedResult = table.getSelectionModel().getSelectedItem();
      //Laad de form pagina en geef de geselecteerde uitslag mee
      this.loadForm(selectedResult);
    });

    return table;
  }

  public VBox getResultPane() {
    return resultPane;
  }
}
