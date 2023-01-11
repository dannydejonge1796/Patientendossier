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
    stage.setTitle("Patiëntendossier");
    stage.setWidth(1280);
    stage.setHeight(720);
    stage.setResizable(false);
    stage.setScene(new LoginScreen(stage).getPatientLoginScene());
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}