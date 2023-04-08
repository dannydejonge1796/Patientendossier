package com.example.patientendossier.utility;

import javafx.scene.control.Alert;
import javafx.stage.Window;

public class Utility {

  public void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
    //Maak alert aan
    Alert alert = new Alert(alertType);
    //Stel de titel in
    alert.setTitle(title);
    //Stel de header text in
    alert.setHeaderText(null);
    //Stel de content text in
    alert.setContentText(message);
    //Stel in in welk window de alert moet worden weergegeven
    alert.initOwner(owner);
    //Weergeef de alert
    alert.show();
  }
}
