package com.example.patientendossier;

import com.example.patientendossier.screen.LoginScreen;
import com.example.patientendossier.model.Database;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
  @Override
  public void start(Stage stage) {
    stage.setTitle("Elektronisch Patiëntendossier");
    stage.setWidth(1280);
    stage.setHeight(720);
    stage.setResizable(false);
    Database db = new Database();
    stage.setScene(new LoginScreen(stage, db).getPatientLoginScene());
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}