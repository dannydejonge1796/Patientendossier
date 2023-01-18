package com.example.patientendossier.screens;

import com.example.patientendossier.Care;
import com.example.patientendossier.Database;
import com.example.patientendossier.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class UserLists {

  private Stage stage;
  private Database db;
  private Care care;
  private Scene listScene;

  public UserLists(Stage stage, Database db, Care care) {
    this.stage = stage;
    this.db = db;
    this.care = care;
    this.listScene = this.setListScene();
  }

  private Scene setListScene()
  {
    BorderPane borderPane = new BorderPane();

    borderPane.setBottom(addBottomMenu());

    TabPane tabPane = new TabPane();

    Tab tab1 = new Tab();
    tab1.setText("Patiënten");
    tab1.setContent(addPatListPane());

    Tab tab2 = new Tab();
    tab2.setText("Zorgverleners");
    tab2.setContent(addCareListPane());

    tabPane.getTabs().addAll(tab1, tab2);

    SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
    selectionModel.select(0);

    tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

    borderPane.setCenter(tabPane);

    return new Scene(borderPane);
  }

  private HBox addBottomMenu()
  {
    HBox hbox = new HBox();
    hbox.setPadding(new Insets(10, 25, 10, 25));
    hbox.setPrefWidth(1200);
    hbox.setSpacing(10);
    hbox.setStyle("-fx-border-style: solid inside");
    hbox.setStyle("-fx-border-width: 2");
    hbox.setStyle("-fx-border-color: black");

    Region region = new Region();
    HBox.setHgrow(region, Priority.ALWAYS);
    hbox.getChildren().add(region);

    Button btnLogout = new Button("Uitloggen");
    hbox.getChildren().add(btnLogout);

    btnLogout.setOnAction(e -> {
      stage.setScene(new LoginScreen(this.stage, this.db).getCarerLoginScene());
    });

    return hbox;
  }

  private VBox addPatListPane()
  {
    VBox vBox = new VBox();
    vBox.setPadding(new Insets(25, 25, 25, 25));
    vBox.setSpacing(20);

    Label lblMyPatients = new Label("Mijn patiënten");
    lblMyPatients.setFont(Font.font(24));
    vBox.getChildren().add(lblMyPatients);

    ArrayList<Patient> patients = care.getPatients();
    ObservableList<Patient> olPatients = FXCollections.observableArrayList();
    olPatients.addAll(patients);

    TableView<Patient> table = new TableView<>();

    table.setItems(olPatients);
    //Creating columns
    TableColumn<Patient, String> colNumber = new TableColumn<>("Patiënt nummer");
    TableColumn<Patient, String> colFirstname = new TableColumn<>("Voornaam");
    TableColumn<Patient, String> colLastname = new TableColumn<>("Achternaam");
    TableColumn<Patient, String> colBirthdate = new TableColumn<>("Geboortedatum");
    TableColumn<Patient, String> colPhonenumber = new TableColumn<>("Telefoonnummer");
    TableColumn<Patient, String> colEmail = new TableColumn<>("Email");

    colNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
    colFirstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
    colLastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
    colBirthdate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
    colPhonenumber.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
    colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

    table.getColumns().add(colNumber);
    table.getColumns().add(colFirstname);
    table.getColumns().add(colLastname);
    table.getColumns().add(colBirthdate);
    table.getColumns().add(colPhonenumber);
    table.getColumns().add(colEmail);

    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    table.getSortOrder().add(colLastname);

    vBox.getChildren().add(table);

    Button btnToDossier = new Button("Naar dossier");
    btnToDossier.setDisable(true);
    vBox.getChildren().add(btnToDossier);

    table.setOnMouseClicked(e -> {
      btnToDossier.setDisable(table.getSelectionModel().getSelectedItem() == null);
    });

    btnToDossier.setOnAction(e -> {
      Patient patient = table.getSelectionModel().getSelectedItem();
      System.out.println(patient);
    });

    return vBox;
  }

  private VBox addCareListPane()
  {
    VBox vBox = new VBox();
    vBox.setPadding(new Insets(25, 25, 25, 25));
    vBox.setSpacing(10);

    return vBox;
  }

  public Scene getListScene() {
    return this.listScene;
  }
}
