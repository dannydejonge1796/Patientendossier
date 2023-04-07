package com.example.patientendossier.screen;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class GlobalElements {

  public HBox getHBoxOne()
  {
    HBox hbox = new HBox();
    hbox.setPadding(new Insets(10, 25, 10, 25));
    hbox.setPrefWidth(1200);
    hbox.setSpacing(10);
    hbox.setStyle("-fx-border-style: solid inside");
    hbox.setStyle("-fx-border-width: 2");
    hbox.setStyle("-fx-border-color: black");

    Region region = new Region();
    HBox.setHgrow(region, Priority.ALWAYS);
    hbox.getChildren().add(region);

    return hbox;
  }
}



