package com.example.patientendossier.controller;

import com.example.patientendossier.model.Care;
import com.example.patientendossier.model.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class CareController {

  public TableView<Patient> getPatTableView(ArrayList<Patient> patients)
  {
    TableView<Patient> table = new TableView<>();

    ObservableList<Patient> olPatients = FXCollections.observableArrayList();
    olPatients.addAll(patients);

    table.setItems(olPatients);
    //Creating columns
    TableColumn<Patient, String> colNumber = new TableColumn<>("Patiënt nummer");
    TableColumn<Patient, String> colFirstname = new TableColumn<>("Voornaam");
    TableColumn<Patient, String> colLastname = new TableColumn<>("Achternaam");
    TableColumn<Patient, String> colBirthdate = new TableColumn<>("Geboortedatum");
    TableColumn<Patient, String> colPhonenumber = new TableColumn<>("Telefoonnummer");
    TableColumn<Patient, String> colEmail = new TableColumn<>("Email");

    colNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
    colFirstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
    colLastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
    colBirthdate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
    colPhonenumber.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
    colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

    table.getColumns().add(colNumber);
    table.getColumns().add(colFirstname);
    table.getColumns().add(colLastname);
    table.getColumns().add(colBirthdate);
    table.getColumns().add(colPhonenumber);
    table.getColumns().add(colEmail);

    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    table.getSortOrder().add(colLastname);

    return table;
  }

  public TableView<Care> getCareTableView(ArrayList<Care> cares)
  {
    ObservableList<Care> olCares = FXCollections.observableArrayList();
    olCares.addAll(cares);

    TableView<Care> table = new TableView<>();

    table.setItems(olCares);
    //Creating columns
    TableColumn<Care, String> colNumber = new TableColumn<>("Zorgverlener nummer");
    TableColumn<Care, String> colFirstname = new TableColumn<>("Voornaam");
    TableColumn<Care, String> colLastname = new TableColumn<>("Achternaam");
    TableColumn<Care, String> colProfession = new TableColumn<>("Beroep");
    TableColumn<Care, String> colPhonenumber = new TableColumn<>("Telefoonnummer");
    TableColumn<Care, String> colEmail = new TableColumn<>("Email");

    colNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
    colFirstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
    colLastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
    colProfession.setCellValueFactory(new PropertyValueFactory<>("profession"));
    colPhonenumber.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
    colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

    table.getColumns().add(colNumber);
    table.getColumns().add(colFirstname);
    table.getColumns().add(colLastname);
    table.getColumns().add(colProfession);
    table.getColumns().add(colPhonenumber);
    table.getColumns().add(colEmail);

    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    table.getSortOrder().add(colLastname);

    return table;
  }
}
