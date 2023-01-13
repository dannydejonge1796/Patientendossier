package com.example.patientendossier;

import com.example.patientendossier.screens.LoginScreen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Index extends Application {
  @Override
  public void start(Stage stage) throws IOException {
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