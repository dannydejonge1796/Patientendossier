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

    TableView<Patient> table = care.getPatTableView(this.care.getPatients());

    vBox.getChildren().add(table);

    Button btnToDossier = new Button("Naar dossier");
    btnToDossier.setDisable(true);
    vBox.getChildren().add(btnToDossier);

    table.setOnMouseClicked(e -> {
      btnToDossier.setDisable(table.getSelectionModel().getSelectedItem() == null);
    });

    btnToDossier.setOnAction(e -> {
      Patient selectedPatient = table.getSelectionModel().getSelectedItem();
      this.stage.setScene(new Dossier(this.stage, this.db, selectedPatient, this.care).getDossierScene());
    });

    return vBox;
  }

  private VBox addCareListPane()
  {
    VBox vBox = new VBox();
    vBox.setPadding(new Insets(25, 25, 25, 25));
    vBox.setSpacing(20);

    Label lblCares = new Label("Zorgverleners");
    lblCares.setFont(Font.font(24));
    vBox.getChildren().add(lblCares);

    TableView<Care> table = care.getCareTableView(this.care.getAllCares());

    vBox.getChildren().add(table);

    Button btn = new Button("#");
    btn.setDisable(true);
    vBox.getChildren().add(btn);

    table.setOnMouseClicked(e -> {
      btn.setDisable(table.getSelectionModel().getSelectedItem() == null);
    });

    btn.setOnAction(e -> {
      Care selectedCare = table.getSelectionModel().getSelectedItem();
      System.out.println(selectedCare);
    });

    return vBox;
  }

  public Scene getListScene() {
    return this.listScene;
  }
}
