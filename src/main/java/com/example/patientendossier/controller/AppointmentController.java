package com.example.patientendossier.controller;

import javafx.scene.control.ScrollPane;

public class AppointmentController {

  private final ScrollPane appointmentPane;

  AppointmentController()
  {
    InfoPageController infoPageController = new InfoPageController();
    this.appointmentPane = infoPageController.getInfoPagePane();

    infoPageController.getLblPage().setText("Afspraken");
  }

  public ScrollPane getAppointmentPane() {
    return appointmentPane;
  }
}
