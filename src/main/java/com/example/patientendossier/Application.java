package com.example.patientendossier;

import com.example.patientendossier.model.Database;
import com.example.patientendossier.screen.LoginScreen;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {

  public static Database db;

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