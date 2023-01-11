module com.example.patientendossier {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.sql;

  opens com.example.patientendossier to javafx.fxml;
  exports com.example.patientendossier;
}