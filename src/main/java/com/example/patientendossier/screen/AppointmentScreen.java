package com.example.patientendossier.screen;

import javafx.scene.layout.VBox;

public class AppointmentScreen {

  private final VBox appointmentPane;

  AppointmentScreen()
  {
    InfoPageScreen infoPageScreen = new InfoPageScreen();
    this.appointmentPane = infoPageScreen.getVBoxInfoPage();

    infoPageScreen.getLblPage().setText("Afspraken");
  }

  public VBox getAppointmentPane() {
    return appointmentPane;
  }
}
