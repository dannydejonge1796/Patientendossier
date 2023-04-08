package com.example.patientendossier.screen;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class GlobalElements {

  public HBox getHBoxOne()
  {
    //Init HBox
    HBox hbox = new HBox();
    //Stel padding in
    hbox.setPadding(new Insets(10, 25, 10, 25));
    //Stel voorkeur breedte in
    hbox.setPrefWidth(1200);
    //Stel spacing in
    hbox.setSpacing(10);
    //Border instellen
    hbox.setStyle("-fx-border-style: solid inside");
    hbox.setStyle("-fx-border-width: 2");
    hbox.setStyle("-fx-border-color: black");
    //Init region
    Region region = new Region();
    //Strek uit over beschikbare breedte
    HBox.setHgrow(region, Priority.ALWAYS);
    //Voeg toe aan HBox
    hbox.getChildren().add(region);
    //HBox teruggeven
    return hbox;
  }
}



