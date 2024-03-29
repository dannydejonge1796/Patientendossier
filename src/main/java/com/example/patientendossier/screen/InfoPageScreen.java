package com.example.patientendossier.screen;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class InfoPageScreen {

  private VBox vBoxInfoPage;
  private Label lblPage;
  private Button btnAdd;
  private Button btnDelete;
  private Button btnUpdate;
  private HBox hBoxTable;

  public InfoPageScreen()
  {
    this.createInfoPagePane();
  }

  private void createInfoPagePane()
  {
    //Vbox aanmaken
    this.vBoxInfoPage = new VBox();
    vBoxInfoPage.setMaxWidth(1260);
    vBoxInfoPage.setPadding(new Insets(25,25,25,25));
    vBoxInfoPage.setSpacing(15);

    //HBox voor bovenste gedeelte aanmaken
    HBox hBoxTop = new HBox();
    hBoxTop.setPrefWidth(1260);

    //HBox aanmaken voor label
    HBox hBoxLblPage = new HBox();
    //Nieuwe label aanmaken en toevoegen
    this.lblPage = new Label();
    lblPage.getStyleClass().add("carePageLabel");
    hBoxLblPage.getChildren().add(lblPage);
    //HBox in de volledige breedte laten uitstrekken
    HBox.setHgrow(hBoxLblPage, Priority.SOMETIMES);

    //Knop toevoegen aanmaken en op onzichtbaar instellen
    this.btnAdd = new Button();
    //Set font awesome icon as graphic
    btnAdd.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.PLUS));
    this.btnAdd.getStyleClass().add("btnPrimary");


    this.btnAdd.setVisible(false);
    //Label en knop toevoegen
    hBoxTop.getChildren().addAll(hBoxLblPage, this.btnAdd);
    //HBox toevoegen aan de vbox van de pagina
    vBoxInfoPage.getChildren().add(hBoxTop);

    //Een pane aanmaken waar de tabel moet komen en vervolgens toevoegen
    this.hBoxTable = new HBox();
    hBoxTable.setPrefWidth(1260);
    vBoxInfoPage.getChildren().add(hBoxTable);

    //Een HBox aanmaken voor het onderste gedeelte van het scherm
    HBox hBoxBottom = new HBox();

    //Knop verwijderen aanmaken, op onzichtbaar zetten en onbeschikbaar maken
    this.btnDelete = new Button();
    btnDelete.getStyleClass().add("btnPrimary");
    //Set font awesome icon as graphic
    btnDelete.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.MINUS));
    this.btnDelete.setVisible(false);
    this.btnDelete.setDisable(true);

    //Region aanmaken voor ruimte tussen de twee knoppen
    Region regionBottom = new Region();
    HBox.setHgrow(regionBottom, Priority.ALWAYS);

    //Knop wijzigen aanmaken, op onzichtbaar zetten en onbeschikbaar maken
    this.btnUpdate = new Button();
    btnUpdate.getStyleClass().add("btnPrimary");
    //Set font awesome icon as graphic
    btnUpdate.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.EDIT));
    this.btnUpdate.setVisible(false);
    this.btnUpdate.setDisable(true);

    //Twee knoppen en region toevoegen aan de HBox van het onderste gedeelte
    hBoxBottom.getChildren().addAll(btnDelete, regionBottom, btnUpdate);
    //HBox toevoegen aan de vbox van de pagina
    vBoxInfoPage.getChildren().add(hBoxBottom);
  }

  public VBox getVBoxInfoPage() {
    return vBoxInfoPage;
  }

  public Label getLblPage() {
    return lblPage;
  }

  public HBox getHBoxTable() {
    return hBoxTable;
  }

  public Button getBtnAdd() {
    return btnAdd;
  }

  public Button getBtnDelete() {
    return btnDelete;
  }

  public Button getBtnUpdate() {
    return btnUpdate;
  }
}
