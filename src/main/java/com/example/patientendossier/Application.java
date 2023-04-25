package com.example.patientendossier;

import com.example.patientendossier.model.Database;
import com.example.patientendossier.screen.LoginScreen;
import javafx.stage.Stage;

import java.util.Objects;

public class Application extends javafx.application.Application {

  public static Database db;

  @Override
  public void init() {
    // Load the global CSS file
    Application.setUserAgentStylesheet(Objects.requireNonNull(getClass().getResource("style/style.css")).toExternalForm());
  }

  @Override
  public void start(Stage stage)
  {
    stage.setTitle("Elektronisch Patiëntendossier");
    stage.setWidth(1280);
    stage.setHeight(720);
    stage.setResizable(false);
    //Initialiseer de database
    db = new Database();
    //Stel de scene in met het patiënten login scherm
    stage.setScene(new LoginScreen(stage).getPatientLoginScene());
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}