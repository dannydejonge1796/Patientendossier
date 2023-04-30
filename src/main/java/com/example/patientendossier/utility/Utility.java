package com.example.patientendossier.utility;

import javafx.scene.control.Alert;
import javafx.stage.Window;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

  public String hashPassword(String password) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] passwordBytes = password.getBytes();
      byte[] hashedBytes = md.digest(passwordBytes);

      // Convert the byte array to a hexadecimal string representation
      StringBuilder sb = new StringBuilder();
      for (byte b : hashedBytes) {
        sb.append(String.format("%02x", b));
      }
      return sb.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
  }
}
