package com.example.patientendossier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Care {

  private final Database db;
  private Integer number;
  private String firstname;
  private String lastname;
  private String profession;
  private Integer phonenumber;
  private String email;
  private String password;

  public Care(Database db, Integer number, String firstname, String lastname, String profession, Integer phonenumber, String email, String password) {
    this.db = db;
    this.number = number;
    this.firstname = firstname;
    this.lastname = lastname;
    this.profession = profession;
    this.phonenumber = phonenumber;
    this.email = email;
    this.password = password;
  }

  public void unAuthorizePatient(Integer careNumber, Integer patientNumber)
  {
    String query =
      "DELETE FROM care_patient " +
      "WHERE " +
        "careNumber = '" + careNumber + "' " +
      "AND " +
        "patientNumber = '" + patientNumber + "'"
    ;

    db.storeData(query);
  }

  public void authorizePatient(Integer careNumber, Integer patientNumber)
  {
    String query =
      "INSERT INTO care_patient " +
        "(careNumber, patientNumber) " +
        "VALUES " +
        "('" + careNumber + "', '" + patientNumber + "')"
    ;

    db.storeData(query);
  }

  public TableView<Patient> getPatTableView(ArrayList<Patient> patients)
  {
    ObservableList<Patient> olPatients = FXCollections.observableArrayList();
    olPatients.addAll(patients);

    TableView<Patient> table = new TableView<>();

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

  public ArrayList<Patient> getPatients()
  {
    ArrayList<Patient> patients = new ArrayList<>();

    String query =
      "SELECT " +
        "patient.patientNumber, " +
        "patient.firstname, " +
        "patient.lastname, " +
        "patient.birthdate, " +
        "patient.phonenumber, " +
        "patient.email, " +
        "patient.password " +
      "FROM " +
        "patient AS patient, " +
        "care AS care, " +
        "care_patient AS carePatient " +
      "WHERE care.careNumber = '" + this.number + "' " +
      "AND care.careNumber = carePatient.careNumber " +
      "AND patient.patientNumber = carePatient.patientNumber"
    ;

    ResultSet result = db.getData(query);

    try {
      while (result.next()) {
        patients.add(new Patient(
          this.db,
          result.getInt("patientNumber"),
          result.getString("firstname"),
          result.getString("lastname"),
          result.getDate("birthdate").toLocalDate(),
          result.getInt("phonenumber"),
          result.getString("email"),
          result.getString("password")
        ));
      }
    } catch (SQLException e) {
      System.out.println("Ophalen data mislukt!");
    }

    return patients;
  }

  public ArrayList<Care> getAllCares()
  {
    ArrayList<Care> cares = new ArrayList<>();

    String query =
      "SELECT * " +
      "FROM care"
    ;

    ResultSet result = db.getData(query);

    try {
      while (result.next()) {
        cares.add(new Care(
          this.db,
          result.getInt("careNumber"),
          result.getString("firstname"),
          result.getString("lastname"),
          result.getString("profession"),
          result.getInt("phonenumber"),
          result.getString("email"),
          result.getString("password")
        ));
      }
    } catch (SQLException e) {
      System.out.println("Ophalen data mislukt!");
    }

    return cares;
  }

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getProfession() {
    return profession;
  }

  public void setProfession(String profession) {
    this.profession = profession;
  }

  public Integer getPhonenumber() {
    return phonenumber;
  }

  public void setPhonenumber(Integer phonenumber) {
    this.phonenumber = phonenumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
