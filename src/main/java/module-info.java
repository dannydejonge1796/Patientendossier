module com.example.patientendossier {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.sql;
  requires fontawesomefx;

  opens com.example.patientendossier to javafx.fxml;
  exports com.example.patientendossier;
  exports com.example.patientendossier.utility;
  opens com.example.patientendossier.utility to javafx.fxml;
  exports com.example.patientendossier.model;
  opens com.example.patientendossier.model to javafx.fxml;
}