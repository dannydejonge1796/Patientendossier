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

  private final Stage stage;
  private Scene patientLoginScene;
  private Scene carerLoginScene;

  public LoginScreen(Stage stage)
  {
    this.stage = stage;
    this.patientLoginScene = null;
    this.carerLoginScene = null;
  }

  public void setPatientLoginScene()
  {
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25, 25, 25, 25));

    Text txtPatientLogin = new Text("Inloggen voor patiënten");
    addFormFields(grid, txtPatientLogin);

    Hyperlink hlCarerLogin = new Hyperlink("Bent u zorgverlener? Login hier.");
    grid.add(hlCarerLogin, 0, 5);
    GridPane.setHalignment(hlCarerLogin, HPos.RIGHT);

    hlCarerLogin.setOnAction(e -> {
      if (this.carerLoginScene != null) {
        this.stage.setScene(this.carerLoginScene);
      } else {
        this.setCarerLoginScene();
      }
    });

    Button btnLogin = new Button("Inloggen");
    grid.add(btnLogin, 0, 6);

    BorderPane borderPane = new BorderPane();
    borderPane.setCenter(grid);

    this.patientLoginScene = new Scene(borderPane);
    this.stage.setScene(this.patientLoginScene);
  }

  public void setCarerLoginScene()
  {
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25, 25, 25, 25));

    Text txtCarerLogin = new Text("Inloggen voor zorgverleners");
    addFormFields(grid, txtCarerLogin);

    Hyperlink hlPatientLogin = new Hyperlink("Bent u patiënt? Login hier.");
    grid.add(hlPatientLogin, 0, 5);
    GridPane.setHalignment(hlPatientLogin, HPos.RIGHT);

    hlPatientLogin.setOnAction(e -> this.stage.setScene(this.patientLoginScene));

    Button btnLogin = new Button("Inloggen");
    grid.add(btnLogin, 0, 6);

    BorderPane borderPane = new BorderPane();
    borderPane.setCenter(grid);

    this.carerLoginScene = new Scene(borderPane);
    this.stage.setScene(this.carerLoginScene);
  }

  private void addFormFields(GridPane grid, Text txtCarerLogin)
  {
    txtCarerLogin.setFont(Font.font(32));
    grid.add(txtCarerLogin, 0, 0);

    Label lblEmail = new Label("Email:");
    grid.add(lblEmail, 0, 1);

    TextField tfEmail = new TextField();
    grid.add(tfEmail, 0, 2);

    Label lblPassword = new Label("Wachtwoord:");
    grid.add(lblPassword, 0, 3);

    TextField tfPassword = new TextField();
    grid.add(tfPassword, 0, 4);
  }
}
