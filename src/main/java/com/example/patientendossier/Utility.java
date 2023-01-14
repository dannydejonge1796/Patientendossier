package com.example.patientendossier;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

public class Utility {

  public void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.initOwner(owner);
    alert.show();
  }

  public Node getNodeByRowColumnIndex(final int column, final int row, GridPane gridPane)
  {
    Node result = null;
    ObservableList<Node> children = gridPane.getChildren();
    for(Node node : children) {
      if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
        result = node;
        break;
      }
    }
    return result;
  }
}
