package com.example.patientendossier.screen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class TableScreen {

  public <T> TableView<T> createTableView(ArrayList<T> data, String[] columnNames, String[] propertyNames)
  {
    TableView<T> table = new TableView<>();
    ObservableList<T> olData = FXCollections.observableArrayList();

    olData.addAll(data);
    table.setItems(olData);

    for (int i = 0; i < columnNames.length; i++) {
      TableColumn<T, String> column = new TableColumn<>(columnNames[i]);
      column.setCellValueFactory(new PropertyValueFactory<>(propertyNames[i]));
      table.getColumns().add(column);
    }

    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    table.getSortOrder().add(new TableColumn<>(columnNames[columnNames.length - 1]));

    return table;
  }
}
