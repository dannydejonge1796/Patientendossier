package com.example.patientendossier.screens;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginScreen {

  private Stage stage;
  private Scene patientLoginScene;
  private Scene carerLoginScene;

  public LoginScreen(Stage stage)
  {
    this.stage = stage;

    BorderPane borderPane = new BorderPane();
    borderPane.setCenter(addPatientLoginPane());

    this.patientLoginScene = new Scene(borderPane);
  }

  public GridPane addPatientLoginPane()
  {
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25, 25, 25, 25));

    Text txtPatientLogin = new Text("Inloggen voor patiënten");
    txtPatientLogin.setFont(Font.font(32));
    grid.add(txtPatientLogin, 0, 0);

    Label lblEmail = new Label("Email:");
    grid.add(lblEmail, 0, 1);

    TextField tfEmail = new TextField();
    grid.add(tfEmail, 0, 2);

    Label lblPassword = new Label("Wachtwoord:");
    grid.add(lblPassword, 0, 3);

    TextField tfPassword = new TextField();
    grid.add(tfPassword, 0, 4);

    Hyperlink hlCarerLogin = new Hyperlink("Bent u zorgverlener? Login hier.");
    grid.add(hlCarerLogin, 0, 5);
    GridPane.setHalignment(hlCarerLogin, HPos.RIGHT);

    Button btnLogin = new Button("Inloggen");
    grid.add(btnLogin, 0, 6);

    return grid;
  }

  public Scene getPatientLoginScene() {
    return patientLoginScene;
  }
}
