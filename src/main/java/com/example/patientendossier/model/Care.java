package com.example.patientendossier.model;

import com.example.patientendossier.Application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Care {

  private final Integer number;
  private String firstname;
  private String lastname;
  private LocalDate birthdate;
  private String profession;
  private Integer phonenumber;
  private String email;
  private String password;

  public Care(Integer number, String firstname, String lastname, LocalDate birthdate, String profession, Integer phonenumber, String email, String password)
  {
    this.number = number;
    this.firstname = firstname;
    this.lastname = lastname;
    this.birthdate = birthdate;
    this.profession = profession;
    this.phonenumber = phonenumber;
    this.email = email;
    this.password = password;
  }

  public void removeCare(Integer careNumber)
  {
    //Query om een zorgverlener te verwijderen
    String query =
            "DELETE FROM care " +
            "WHERE number = '" + careNumber + "'"
    ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public Integer getHighestResultId()
  {
    //Query om het hoogste uitslag id te verkrijgen
    String query =
            "SELECT MAX(id) as highest_id " +
            "FROM result"
    ;

    //Data ophaal functie aanroepen in de database class
    ResultSet result = Application.db.getData(query);

    //Het resultaat teruggeven
    try {
      if (result.next()) {
        return result.getInt("highest_id");
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return null;
  }

  public Integer getHighestReportId()
  {
    //Query om het hoogste verslag id te verkrijgen
    String query =
            "SELECT MAX(id) as highest_id " +
                    "FROM report"
            ;

    //Data ophaal functie aanroepen in de database class
    ResultSet result = Application.db.getData(query);

    //Resultaat teruggeven
    try {
      if (result.next()) {
        return result.getInt("highest_id");
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return null;
  }

  public ArrayList<Appointment> getAppointments()
  {
    //Lijst om alle afspraken van deze zorgverlener op te slaan initialiseren
    ArrayList<Appointment> appointments = new ArrayList<>();

    //Query voor het ophalen van alle afspraken van deze zorgverlener
    String query =
            "SELECT * " +
                    "FROM appointment " +
                    "WHERE care_number = '" + this.number + "'"
            ;

    //Data ophaal functie aanroepen in db class
    ResultSet result = Application.db.getData(query);

    //Door alle resultaten lopen, afspraak objecten aanmaken en in de lijst stoppen
    try {
      while (result.next()) {
        appointments.add(new Appointment(
                result.getInt("number"),
                result.getInt("patient_number"),
                result.getInt("care_number"),
                result.getString("care_lastname"),
                result.getString("description"),
                result.getDate("date").toLocalDate(),
                result.getTime("time").toLocalTime()
        ));
      }
    } catch (SQLException e) {
      System.out.println("Ophalen deze data mislukt!");
    }

    return appointments;
  }

  public Integer getHighestAppointmentNumber()
  {
    //Query om het hoogste afspraaknummer te vinden
    String query =
            "SELECT MAX(number) as highest_number " +
            "FROM appointment"
    ;

    //Data ophaal functie aanroepen in de database class
    ResultSet result = Application.db.getData(query);

    //Resultaat teruggeven
    try {
      if (result.next()) {
        return result.getInt("highest_number");
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return null;
  }

  public ArrayList<String> getAllAllergyNames()
  {
    //Lijst met namen van alle allergieën initialiseren
    ArrayList<String> allergyNames = new ArrayList<>();

    //Query voor het ophalen van de namen van alle allergieën
    String query =
            "SELECT allergy.name " +
            "FROM allergy"
    ;

    //Data ophaal functie aanroepen in de database class
    ResultSet result = Application.db.getData(query);

    //Door resultaten heen lopen en de namen toevoegen aan de lijst
    try {
      while (result.next()) {
        allergyNames.add(result.getString("name"));
      }
    } catch (SQLException e) {
      System.out.println("Ophalen data mislukt!");
    }

    return allergyNames;
  }

  public ArrayList<String> getAllHealthNames()
  {
    //Lijst met namen van alle gezondheidsproblemen initialiseren
    ArrayList<String> healthNames = new ArrayList<>();

    //Query voor het ophalen van de namen van alle gezondheidsproblemen
    String query =
            "SELECT health.name " +
            "FROM health"
    ;

    //Data ophaal functie aanroepen in de database class
    ResultSet result = Application.db.getData(query);

    //Door resultaten lopen en namen in de lijst stoppen
    try {
      while (result.next()) {
        healthNames.add(result.getString("name"));
      }
    } catch (SQLException e) {
      System.out.println("Ophalen data mislukt!");
    }

    return healthNames;
  }

  public ArrayList<String> getAllMedicineNames()
  {
    //Lijst met namen van alle medicijnen initialiseren
    ArrayList<String> medicineNames = new ArrayList<>();

    //Query voor het ophalen van alle namen van medicijnen
    String query =
            "SELECT medicine.name " +
                    "FROM medicine"
            ;

    //Data ophaal functie aanroepen in de database class
    ResultSet result = Application.db.getData(query);

    //Door resultaten lopen en namen toevoegen aan de lijst
    try {
      while (result.next()) {
        medicineNames.add(result.getString("name"));
      }
    } catch (SQLException e) {
      System.out.println("Ophalen data mislukt!");
    }

    return medicineNames;
  }

  public void unAuthorizePatient(Integer careNumber, Integer patientNumber)
  {
    //Query om een bevoegdheid van een zorgverlener om het dossier te bekijken te beëindigen
    String query =
      "DELETE FROM care_patient " +
      "WHERE " +
        "care_number = '" + careNumber + "' " +
      "AND " +
        "patient_number = '" + patientNumber + "'"
    ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public void authorizePatient(Integer careNumber, Integer patientNumber)
  {
    //Query om een zorgverlener bevoegdheid te geven een dossier van een patient te kunnen inzien
    String query =
      "INSERT INTO care_patient " +
        "(care_number, patient_number) " +
        "VALUES " +
        "('" + careNumber + "', '" + patientNumber + "')"
    ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public ArrayList<Patient> getPatients()
  {
    //Lijst met alle patients van een zorgverlener initialiseren
    ArrayList<Patient> patients = new ArrayList<>();

    //Query om alle patients van een zorgverlener op te halen
    String query =
      "SELECT * " +
      "FROM " +
        "patient AS patient, " +
        "care AS care, " +
        "care_patient AS carePatient " +
      "WHERE care.number = '" + this.number + "' " +
      "AND care.number = carePatient.care_number " +
      "AND patient.number = carePatient.patient_number"
    ;

    //Data ophaal functie aanroepen in db class
    ResultSet result = Application.db.getData(query);

    //Door alle resultaten lopen, patient objecten aanmaken en toevoegen aan de lijst
    try {
      while (result.next()) {
        patients.add(new Patient(
          result.getInt("number"),
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

  public ArrayList<String> getCareStrings()
  {
    //Lijst met zorgverlener nummers en achternamen van alle zorgverleners initialiseren
    ArrayList<String> careStrings = new ArrayList<>();

    //Query om de zorgverlener nummers en achternamen van alle zorgverleners op te halen
    String query =
            "SELECT " +
                    "care.number," +
                    "care.lastname " +
            "FROM care"
    ;

    //Data ophaal functie aanroepen in de db class
    ResultSet result = Application.db.getData(query);

    // Door alle resultaten heen lopen,
    // een string van de nummers en achternamen maken,
    // toevoegen aan de lijst
    try {
      while (result.next()) {
        careStrings.add(result.getString("number") + ", " + result.getString("lastname"));
      }
    } catch (SQLException e) {
      System.out.println("Ophalen data mislukt!");
    }

    return careStrings;
  }

  public ArrayList<Care> getAllCares()
  {
    //Lijst met alle care objecten initialiseren
    ArrayList<Care> cares = new ArrayList<>();

    //Query om alle zorgverleners op te halen
    String query =
      "SELECT * " +
      "FROM care"
    ;

    //Data ophaal functie in de db class aanroepen
    ResultSet result = Application.db.getData(query);

    //Door resultaten heen lopen,
    try {
      while (result.next()) {
        //Care objecten aanmaken,
        //Toevoegen aan de lijst
        cares.add(new Care(
          result.getInt("number"),
          result.getString("firstname"),
          result.getString("lastname"),
          result.getDate("birthdate").toLocalDate(),
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

  public void store()
  {
    //Query om deze zorgverlener op te slaan in de database
    String query = "INSERT INTO care (number, firstname, lastname, birthdate, profession, phonenumber, email, password) " +
            "VALUES ('" + this.number + "', '" +
            this.firstname + "', '" +
            this.lastname + "', '" +
            this.birthdate + "', '" +
            this.profession + "', '" +
            this.phonenumber + "', '" +
            this.email + "', '" +
            this.password + "')"
    ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public void update()
  {
    //Query om deze zorgverlener bij te werken in de database
    String query =
            "UPDATE care " +
            "SET " +
            "firstname = '" + this.firstname + "', " +
            "lastname = '" + this.lastname + "', " +
            "birthdate = '" + this.birthdate + "', " +
            "profession = '" + this.profession + "', " +
            "phonenumber = '" + this.phonenumber + "', " +
            "email = '" + this.email + "', " +
            "password = '" + this.password + "' " +
            "WHERE number = '" + this.number + "'"
    ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public Integer getNumber() {
    return number;
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

  public LocalDate getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(LocalDate birthdate) {
    this.birthdate = birthdate;
  }
}
