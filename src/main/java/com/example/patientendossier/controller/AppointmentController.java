package com.example.patientendossier.controller;

import javafx.scene.layout.VBox;

public class AppointmentController {

  private final VBox appointmentPane;

  AppointmentController()
  {
    InfoPageController infoPageController = new InfoPageController();
    this.appointmentPane = infoPageController.getVBoxInfoPage();

    infoPageController.getLblPage().setText("Afspraken");
  }

  public VBox getAppointmentPane() {
    return appointmentPane;
  }
}
