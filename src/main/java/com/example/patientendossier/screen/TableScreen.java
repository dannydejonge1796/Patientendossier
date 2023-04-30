package com.example.patientendossier.screen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class TableScreen {

  public <T> TableView<T> createTableView(ArrayList<T> data, String[] columnNames, String[] propertyNames)
  {
    //Nieuwe tabel view aanmaken
    TableView<T> table = new TableView<>();
    //Nieuwe observable list aanmaken
    ObservableList<T> olData = FXCollections.observableArrayList();

    //Voeg alles van de arraylist met objecten toe aan de observable lijst
    olData.addAll(data);
    //Stop de observable lijst in de tabel
    table.setItems(olData);

    //Loop door de kolomnamen heen die zijn meegegeven
    for (int i = 0; i < columnNames.length; i++) {
      //Maak een nieuwe kolom aan
      TableColumn<T, Object> column = new TableColumn<>(columnNames[i]);
      //Stop de waardes van de attributen onder de juiste kolom
      column.setCellValueFactory(new PropertyValueFactory<>(propertyNames[i]));

      // Definieer een TableCell-fabriek voor een tabelkolom
      column.setCellFactory(tc -> new TableCell<>() {

        // Maak een Text-object aan om de celinhoud weer te geven
        private final Text text = new Text();

        // Override de updateItem methode om de inhoud van de cel bij te werken
        @Override
        protected void updateItem(Object item, boolean empty) {
          super.updateItem(item, empty);

          // Controleer of de cel leeg is
          if (item != null) {
            // Als de cel niet leeg is, stel de tekst in en pas de wrapping width aan
            text.setText(item.toString());
            text.wrappingWidthProperty().bind(widthProperty());
            setGraphic(text);
          } else {
            // Als de cel leeg is, verwijder dan de inhoud van de cel
            setGraphic(null);
          }
        }
      });

      table.getColumns().add(column);
    }

    // Stel het kolomformaat in zodat het past binnen de breedte van de tabel
    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    // Voeg een sorteerbare kolom toe aan de tabel en stel deze in als standaard
    table.getSortOrder().add(new TableColumn<>(columnNames[columnNames.length - 1]));

    return table;
  }
}
