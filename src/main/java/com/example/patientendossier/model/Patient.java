package com.example.patientendossier.model;

import com.example.patientendossier.Application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Patient {

  private final Integer number;
  private String firstname;
  private String lastname;
  private LocalDate birthdate;
  private Integer phonenumber;
  private String email;
  private String password;

  public Patient(Integer number, String firstname, String lastname, LocalDate birthdate, Integer phonenumber, String email, String password)
  {
    this.number = number;
    this.firstname = firstname;
    this.lastname = lastname;
    this.birthdate = birthdate;
    this.phonenumber = phonenumber;
    this.email = email;
    this.password = password;
  }

  public void addResult(Result result)
  {
    //Query voor het maken van een nieuwe uitslag
    String query =
            "INSERT INTO result (id, patient_number, date, result, made_by)" +
            "VALUES ('" + result.getId() + "', " +
            "'" + result.getPatientNumber() + "', " +
            "'" + result.getDate() + "', " +
            "'" + result.getResult() + "', " +
            "'" + result.getMadeBy() + "')"
    ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public void updateResult(Result result)
  {
    //Query voor het bijwerken van een uitslag
    String query =
            "UPDATE result " +
            "SET result = '" + result.getResult() + "', " +
            "made_by = '" + result.getMadeBy() + "', " +
            "date = '" + result.getDate() + "' " +
            "WHERE id = '" + result.getId() + "'"
    ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public void deleteResult(Result result)
  {
    //Query voor het verwijderen van een uitslag
    String query =
            "DELETE FROM result " +
            "WHERE id = '" + result.getId() + "'"
    ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public ArrayList<Result> getResults()
  {
    //Lijst met uitslag objecten initialiseren
    ArrayList<Result> results = new ArrayList<>();

    //Query voor het ophalen van alle uitslagen van deze patient
    String query =
            "SELECT * " +
            "FROM result " +
            "WHERE patient_number = '" + this.number + "'"
    ;

    //Data ophaal functie aanroepen in db class
    ResultSet result = Application.db.getData(query);

    //Voor elke row die is gevonden een uitslag object aanmaken en in de lijst stoppen
    try {
      while (result.next()) {
        results.add(new Result(
                result.getInt("id"),
                result.getInt("patient_number"),
                result.getString("result"),
                result.getString("made_by"),
                result.getDate("date").toLocalDate()
        ));
      }
    } catch (SQLException e) {
      System.out.println("Ophalen data mislukt!");
    }

    return results;
  }

  public void addReport(Report report)
  {
    //Query voor het aanmaken van een nieuw verslag
    String query =
            "INSERT INTO report (id, patient_number, date, report, made_by)" +
            "VALUES ('" + report.getId() + "', " +
            "'" + report.getPatientNumber() + "', " +
            "'" + report.getDate() + "', " +
            "'" + report.getReport() + "', " +
            "'" + report.getMadeBy() + "')"
    ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public void updateReport(Report report)
  {
    //Query voor het bijwerken van een verslag
    String query =
    "UPDATE report " +
            "SET report = '" + report.getReport() + "', " +
            "made_by = '" + report.getMadeBy() + "', " +
            "date = '" + report.getDate() + "' " +
            "WHERE id = '" + report.getId() + "'"
    ;
    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public void deleteReport(Report report)
  {
    //Query voor het verwijderen van een verslag
    String query =
            "DELETE FROM report " +
            "WHERE id = '" + report.getId() + "'"
    ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public ArrayList<Report> getReports()
  {
    //Lijst met verslag objecten initialiseren
    ArrayList<Report> reports = new ArrayList<>();

    //Query voor het ophalen van alle verslagen van deze patient
    String query =
            "SELECT * " +
            "FROM report " +
            "WHERE patient_number = '" + this.number + "'"
    ;

    //Data ophaal functie aanroepen in db class
    ResultSet result = Application.db.getData(query);

    //Van alle rijen in het resultaat een verslag object maken en in de lijst stoppen
    try {
      while (result.next()) {
        reports.add(new Report(
                result.getInt("id"),
                result.getInt("patient_number"),
                result.getString("report"),
                result.getString("made_by"),
                result.getDate("date").toLocalDate()
        ));
      }
    } catch (SQLException e) {
      System.out.println("Ophalen data mislukt!");
    }

    return reports;
  }

  public void deleteAppointment(Appointment appointment)
  {
    //Query voor het verwijderen van een afspraak
    String query =
            "DELETE FROM appointment " +
            "WHERE number = '" + appointment.getNumber() + "'"
    ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public ArrayList<Appointment> getAppointments()
  {
    //Lijst met afspraak objecten initialiseren
    ArrayList<Appointment> appointments = new ArrayList<>();

    //Query voor het ophalen van alle afspraken van deze patient
    String query =
            "SELECT * " +
            "FROM appointment " +
            "WHERE patient_number = '" + this.number + "'"
    ;

    //Data ophaal functie aanroepen in db class
    ResultSet result = Application.db.getData(query);

    //Voor elke gevonden afspraak een object aanmaken en in de lijst stoppen
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

  public void addAppointment(Appointment appointment)
  {
    //Query voor het aanmaken van een nieuwe afspraak
    String query =
            "INSERT INTO appointment (number, patient_number, care_number, care_lastname, description, date, time)" +
            "VALUES ('" + appointment.getNumber() + "', " +
                    "'" + appointment.getPatientNumber() + "', " +
                    "'" + appointment.getCareNumber() + "', " +
                    "'" + appointment.getCareLastname() + "', " +
                    "'" + appointment.getDescription() + "', " +
                    "'" + appointment.getDate() + "', " +
                    "'" + appointment.getTime() + "')"
    ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public void updateAppointment(Appointment appointment)
  {
    //Query voor het bijwerken van een bestaande afspraak
    String query =
            "UPDATE appointment " +
            "SET care_number = '" + appointment.getCareNumber() + "', " +
            "care_lastname = '" + appointment.getCareLastname() + "', " +
            "description = '" + appointment.getDescription() + "', " +
            "date = '" + appointment.getDate() + "', " +
            "time = '" + appointment.getTime() + "' " +
            "WHERE number = '" + appointment.getNumber() + "'"
    ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public void deleteAllergy(Allergy allergy)
  {
    //Query voor het verwijderen van een allergie van deze patient
    String query =
            "DELETE FROM allergy_patient " +
            "WHERE patient_number = '" + this.number + "' " +
            "AND allergy_name = '" + allergy.getName() + "'"
    ;
    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public void updateAllergy(Allergy allergy)
  {
    //Query om een allergie van deze patient bij te werken
    String query =
            "UPDATE allergy_patient " +
            "SET description = '" + allergy.getDescription() + "' " +
            "WHERE patient_number = '" + number + "' " +
            "AND allergy_name = '" + allergy.getName() + "'"
    ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public void addAllergy(Allergy allergy)
  {
    //Query om allergie toe te voegen aan deze patient
    String query =
            "INSERT INTO allergy_patient (allergy_name, patient_number, description)" +
            "VALUES ('" + allergy.getName() + "', '" + number + "', '" + allergy.getDescription() + "')"
    ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public ArrayList<String> getAllergyNames()
  {
    //Lijst met namen van allergieën van deze patient initialiseren
    ArrayList<String> allergyNames = new ArrayList<>();

    //Query om alle allergieën van deze patient op te halen
    String query =
            "SELECT " +
                    "allergy.name " +
            "FROM " +
                    "patient AS patient, " +
                    "allergy AS allergy, " +
                    "allergy_patient AS allergyPatient " +
            "WHERE allergyPatient.patient_number = '" + this.number + "' " +
            "AND allergy.name = allergyPatient.allergy_name " +
            "GROUP BY allergy.name"
    ;

    //Data ophaal functie aanroepen van db class
    ResultSet result = Application.db.getData(query);

    //Loop door de resultaten en stop de namen in de lijst
    try {
      while (result.next()) {
        allergyNames.add(result.getString("name"));
      }
    } catch (SQLException e) {
      System.out.println("Ophalen data mislukt!");
    }

    return allergyNames;
  }

  public ArrayList<Allergy> getAllergies()
  {
    //Lijst om alle allergieën van deze patient op te slaan initialiseren
    ArrayList<Allergy> allergies = new ArrayList<>();

    //Query voor het ophalen van alle allergieën van deze patient
    String query =
      "SELECT " +
        "allergy.name, " +
        "allergyPatient.description " +
      "FROM " +
        "patient AS patient, " +
        "allergy AS allergy, " +
        "allergy_patient AS allergyPatient " +
      "WHERE allergyPatient.patient_number = '" + this.number + "' " +
      "AND allergy.name = allergyPatient.allergy_name " +
      "GROUP BY allergy.name"
    ;

    //Data ophaal functie aanroepen in db class
    ResultSet result = Application.db.getData(query);

    //Allergie objecten aanmaken voor alle rijen en in lijst stoppen
    try {
      while (result.next()) {
        allergies.add(new Allergy(
          result.getString("name"),
          result.getString("description")
        ));
      }
    } catch (SQLException e) {
      System.out.println("Ophalen data mislukt!");
    }

    return allergies;
  }

public void deleteMedicine(Medicine medicine)
{
  //Query voor het verwijderen van een medicijn uit een patient
  String query =
          "DELETE FROM medicine_patient " +
          "WHERE patient_number = '" + this.number + "' " +
          "AND medicine_name = '" + medicine.getName() + "'"
  ;

  //Roep store functie in db class
  Application.db.storeData(query);
}

  public void updateMedicine(Medicine medicine)
  {
    //Query voor het bijwerken van een medicijn van een patient
    String query =
            "UPDATE medicine_patient " +
                    "SET description = '" + medicine.getDescription() + "', " +
                    "dosage = '" + medicine.getDosage() + "' " +
                    "WHERE patient_number = '" + this.number + "' " +
                    "AND medicine_name = '" + medicine.getName() + "'"
            ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public void addMedicine(Medicine medicine)
  {
    //Query voor het toevoegen van een medicijn aan een patient
    String query =
            "INSERT INTO medicine_patient (medicine_name, patient_number, description, dosage)" +
            "VALUES ('" + medicine.getName() + "', '" + number + "', " +
                    "'" + medicine.getDescription() + "', " +
                    "'" + medicine.getDosage() + "')"
    ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public ArrayList<String> getMedicineNames()
  {
    //Lijst van namen van medicijnen van de patient initialiseren
    ArrayList<String> medicineNames = new ArrayList<>();

    //Query voor het ophalen van de namen van medicijnen van de patient
    String query =
            "SELECT " +
                    "medicine.name " +
                    "FROM " +
                    "patient AS patient, " +
                    "medicine AS medicine, " +
                    "medicine_patient AS medicinePatient " +
                    "WHERE medicinePatient.patient_number = '" + this.number + "' " +
                    "AND medicine.name = medicinePatient.medicine_name " +
                    "GROUP BY medicine.name"
            ;

    //Data ophaal functie aanroepen in db class
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

  public ArrayList<Medicine> getMedicines()
  {
    //Lijst van medicijn objecten van een patient
    ArrayList<Medicine> medicines = new ArrayList<>();

    //Query om alle medicijnen van een patient op te halen
    String query =
            "SELECT " +
                    "medicine.name, " +
                    "medicinePatient.description, " +
                    "medicinePatient.dosage " +
                    "FROM " +
                    "patient AS patient, " +
                    "medicine AS medicine, " +
                    "medicine_patient AS medicinePatient " +
                    "WHERE medicinePatient.patient_number = '" + this.number + "' " +
                    "AND medicine.name = medicinePatient.medicine_name " +
                    "GROUP BY medicine.name"
            ;

    //Data ophaal functie aanroepen in db class
    ResultSet result = Application.db.getData(query);

    //Door resultaten lopen, medicijn objecten aanmaken en toevoegen aan de lijst
    try {
      while (result.next()) {
        medicines.add(new Medicine(
                result.getString("name"),
                result.getString("description"),
                result.getString("dosage")
        ));
      }
    } catch (SQLException e) {
      System.out.println("Ophalen data mislukt!");
    }

    return medicines;
  }

  public void deleteHealth(Health health)
  {
    //Een gezondheidsprobleem van een patient verwijderen
    String query =
            "DELETE FROM health_patient " +
                    "WHERE patient_number = '" + this.number + "' " +
                    "AND health_name = '" + health.getName() + "'"
            ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public void updateHealth(Health health)
  {
    //Een gezondheidsprobleem van een patient bijwerken
    String query =
            "UPDATE health_patient " +
                    "SET description = '" + health.getDescription() + "' " +
                    "WHERE patient_number = '" + this.number + "' " +
                    "AND health_name = '" + health.getName() + "'"
            ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public void addHealth(Health health)
  {
    //Een gezondheidsprobleem toevoegen aan een patient
    String query =
            "INSERT INTO health_patient (health_name, patient_number, description)" +
                    "VALUES ('" + health.getName() + "', '" + number + "', " +
                    "'" + health.getDescription() + "')"
            ;

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public ArrayList<String> getHealthNames()
  {
    //Lijst met namen van gezondheidsproblemen van een patient initialiseren
    ArrayList<String> healthNames = new ArrayList<>();

    //Query voor het ophalen van alle namen van gezondheidsproblemen van een patient
    String query =
            "SELECT " +
                    "health.name " +
                    "FROM " +
                    "patient AS patient, " +
                    "health AS health, " +
                    "health_patient AS healthPatient " +
                    "WHERE healthPatient.patient_number = '" + this.number + "' " +
                    "AND health.name = healthPatient.health_name " +
                    "GROUP BY health.name"
            ;

    //Data ophaal functie aanroepen in de db class
    ResultSet result = Application.db.getData(query);

    //Door resultaten heen lopen en de namen toevoegen aan de lijst
    try {
      while (result.next()) {
        healthNames.add(result.getString("name"));
      }
    } catch (SQLException e) {
      System.out.println("Ophalen data mislukt!");
    }

    return healthNames;
  }

  public ArrayList<Health> getHealths()
  {
    //Lijst met objecten van gezondheidsproblemen van een patient initialiseren
    ArrayList<Health> healths = new ArrayList<>();

    //Query voor het ophalen van alle gezondheidsproblemen van een patient
    String query =
            "SELECT " +
                    "health.name, " +
                    "healthPatient.description " +
                    "FROM " +
                    "patient AS patient, " +
                    "health AS health, " +
                    "health_patient AS healthPatient " +
                    "WHERE healthPatient.patient_number = '" + this.number + "' " +
                    "AND health.name = healthPatient.health_name " +
                    "GROUP BY health.name";

    //Data ophaal functie aanroepen in de db class
    ResultSet result = Application.db.getData(query);

    //Door de resultaten heen lopen, gezondheidsprobleem objecten aanmaken en toevoegen aan de lijst
    try {
      while (result.next()) {
        healths.add(new Health(
                result.getString("name"),
                result.getString("description")
        ));
      }
    } catch (SQLException e) {
      System.out.println("Ophalen data mislukt!");
    }

    return healths;
  }

  public ArrayList<Care> getCareOfPatient()
  {
    //Lijst met care objecten van een patient initialiseren
    ArrayList<Care> cares = new ArrayList<>();

    //Query om alle zorgverleners van een patient op te halen
    String query =
      "SELECT " +
        "care.number, " +
        "care.firstname, " +
        "care.lastname, " +
        "care.birthdate, " +
        "care.profession, " +
        "care.phonenumber, " +
        "care.email, " +
        "care.password " +
      "FROM " +
        "patient AS patient, " +
        "care AS care, " +
        "care_patient AS carePatient " +
      "WHERE carePatient.patient_number = '" + this.number + "' " +
      "AND care.number = carePatient.care_number " +
      "GROUP BY care.number"
    ;

    //Data ophaal functie aanroepen in db class
    ResultSet result = Application.db.getData(query);

    //Door resultaten heen lopen, care objecten aanmaken en toevoegen aan de lijst
    try {
      while (result.next()) {
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
    //Query om deze patient toe te voegen aan de database
    String query = "INSERT INTO patient (number, firstname, lastname, birthdate, phonenumber, email, password) " +
            "VALUES ('" + this.number + "', '" +
            this.firstname + "', '" +
            this.lastname + "', '" +
            this.birthdate + "', '" +
            this.phonenumber + "', '" +
            this.email + "', '" +
            this.password + "')";

    //Roep store functie in db class
    Application.db.storeData(query);
  }

  public void update()
  {
    //Query om deze patient te updaten in de database
    String query =
      "UPDATE patient " +
      "SET " +
        "firstname = '" + this.firstname + "', " +
        "lastname = '" + this.lastname + "', " +
        "birthdate = '" + this.birthdate + "', " +
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

  public String getLastname() {
    return lastname;
  }

  public LocalDate getBirthdate() {
    return birthdate;
  }

  public Integer getPhonenumber() {
    return phonenumber;
  }

  public String getEmail() {
    return email;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public void setBirthdate(LocalDate birthdate) {
    this.birthdate = birthdate;
  }

  public void setPhonenumber(Integer phonenumber) {
    this.phonenumber = phonenumber;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
