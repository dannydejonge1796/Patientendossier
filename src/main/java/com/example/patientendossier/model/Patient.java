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
    String query =
            "INSERT INTO result (id, patient_number, date, result, made_by)" +
            "VALUES ('" + result.getId() + "', " +
            "'" + result.getPatientNumber() + "', " +
            "'" + result.getDate() + "', " +
            "'" + result.getResult() + "', " +
            "'" + result.getMadeBy() + "')"
    ;

    Application.db.storeData(query);
  }

  public void updateResult(Result result)
  {
    String query =
            "UPDATE result " +
            "SET result = '" + result.getResult() + "', " +
            "made_by = '" + result.getMadeBy() + "', " +
            "date = '" + result.getDate() + "' " +
            "WHERE id = '" + result.getId() + "'"
    ;

    Application.db.storeData(query);
  }

  public void deleteResult(Result result)
  {
    String query =
            "DELETE FROM result " +
            "WHERE id = '" + result.getId() + "'"
    ;

    Application.db.storeData(query);
  }

  public ArrayList<Result> getResults()
  {
    ArrayList<Result> results = new ArrayList<>();

    String query =
            "SELECT * " +
            "FROM result " +
            "WHERE patient_number = '" + this.number + "'"
    ;

    ResultSet result = Application.db.getData(query);

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
    String query =
            "INSERT INTO report (id, patient_number, date, report, made_by)" +
            "VALUES ('" + report.getId() + "', " +
            "'" + report.getPatientNumber() + "', " +
            "'" + report.getDate() + "', " +
            "'" + report.getReport() + "', " +
            "'" + report.getMadeBy() + "')"
    ;

    Application.db.storeData(query);
  }

  public void updateReport(Report report)
  {
    String query =
    "UPDATE report " +
            "SET report = '" + report.getReport() + "', " +
            "made_by = '" + report.getMadeBy() + "', " +
            "date = '" + report.getDate() + "' " +
            "WHERE id = '" + report.getId() + "'"
    ;

    Application.db.storeData(query);
  }

  public void deleteReport(Report report)
  {
    String query =
            "DELETE FROM report " +
            "WHERE id = '" + report.getId() + "'"
    ;

    Application.db.storeData(query);
  }

  public ArrayList<Report> getReports()
  {
    ArrayList<Report> reports = new ArrayList<>();

    String query =
            "SELECT * " +
            "FROM report " +
            "WHERE patient_number = '" + this.number + "'"
    ;

    ResultSet result = Application.db.getData(query);

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
    String query =
            "DELETE FROM appointment " +
            "WHERE number = '" + appointment.getNumber() + "'"
    ;

    Application.db.storeData(query);
  }

  public ArrayList<Appointment> getAppointments()
  {
    ArrayList<Appointment> appointments = new ArrayList<>();

    String query =
            "SELECT * " +
            "FROM appointment " +
            "WHERE patient_number = '" + this.number + "'"
    ;

    ResultSet result = Application.db.getData(query);

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

    Application.db.storeData(query);
  }

  public void updateAppointment(Appointment appointment)
  {
    String query =
            "UPDATE appointment " +
            "SET care_number = '" + appointment.getCareNumber() + "', " +
            "care_lastname = '" + appointment.getCareLastname() + "', " +
            "description = '" + appointment.getDescription() + "', " +
            "date = '" + appointment.getDate() + "', " +
            "time = '" + appointment.getTime() + "' " +
            "WHERE number = '" + appointment.getNumber() + "'"
    ;

    Application.db.storeData(query);
  }

  public void deleteAllergy(Allergy allergy)
  {
    String query =
            "DELETE FROM allergy_patient " +
            "WHERE patient_number = '" + this.number + "' " +
            "AND allergy_name = '" + allergy.getName() + "'"
    ;

    Application.db.storeData(query);
  }

  public void updateAllergy(Allergy allergy)
  {
    String query =
            "UPDATE allergy_patient " +
            "SET description = '" + allergy.getDescription() + "' " +
            "WHERE patient_number = '" + number + "' " +
            "AND allergy_name = '" + allergy.getName() + "'"
    ;

    Application.db.storeData(query);
  }

  public void addAllergy(Allergy allergy)
  {
    String query =
            "INSERT INTO allergy_patient (allergy_name, patient_number, description)" +
            "VALUES ('" + allergy.getName() + "', '" + number + "', '" + allergy.getDescription() + "')"
    ;

    Application.db.storeData(query);
  }

  public ArrayList<String> getAllergyNames()
  {
    ArrayList<String> allergyNames = new ArrayList<>();

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

    ResultSet result = Application.db.getData(query);

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
    ArrayList<Allergy> allergies = new ArrayList<>();

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

    ResultSet result = Application.db.getData(query);

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
  String query =
          "DELETE FROM medicine_patient " +
                  "WHERE patient_number = '" + this.number + "' " +
                  "AND medicine_name = '" + medicine.getName() + "'"
          ;

  Application.db.storeData(query);
}

  public void updateMedicine(Medicine medicine)
  {
    String query =
            "UPDATE medicine_patient " +
                    "SET description = '" + medicine.getDescription() + "', " +
                    "dosage = '" + medicine.getDosage() + "' " +
                    "WHERE patient_number = '" + this.number + "' " +
                    "AND medicine_name = '" + medicine.getName() + "'"
            ;

    Application.db.storeData(query);
  }

  public void addMedicine(Medicine medicine)
  {
    String query =
            "INSERT INTO medicine_patient (medicine_name, patient_number, description, dosage)" +
            "VALUES ('" + medicine.getName() + "', '" + number + "', " +
                    "'" + medicine.getDescription() + "', " +
                    "'" + medicine.getDosage() + "')"
    ;

    Application.db.storeData(query);
  }

  public ArrayList<String> getMedicineNames()
  {
    ArrayList<String> medicineNames = new ArrayList<>();

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

    ResultSet result = Application.db.getData(query);

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
    ArrayList<Medicine> medicines = new ArrayList<>();

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

    ResultSet result = Application.db.getData(query);

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
    String query =
            "DELETE FROM health_patient " +
                    "WHERE patient_number = '" + this.number + "' " +
                    "AND health_name = '" + health.getName() + "'"
            ;

    Application.db.storeData(query);
  }

  public void updateHealth(Health health)
  {
    String query =
            "UPDATE health_patient " +
                    "SET description = '" + health.getDescription() + "' " +
                    "WHERE patient_number = '" + this.number + "' " +
                    "AND health_name = '" + health.getName() + "'"
            ;

    Application.db.storeData(query);
  }

  public void addHealth(Health health)
  {
    String query =
            "INSERT INTO health_patient (health_name, patient_number, description)" +
                    "VALUES ('" + health.getName() + "', '" + number + "', " +
                    "'" + health.getDescription() + "')"
            ;

    Application.db.storeData(query);
  }

  public ArrayList<String> getHealthNames()
  {
    ArrayList<String> healthNames = new ArrayList<>();

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

    ResultSet result = Application.db.getData(query);

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
    ArrayList<Health> healths = new ArrayList<>();

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

    ResultSet result = Application.db.getData(query);

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
    ArrayList<Care> cares = new ArrayList<>();

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

    ResultSet result = Application.db.getData(query);

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
    String query = "INSERT INTO patient (number, firstname, lastname, birthdate, phonenumber, email, password) " +
            "VALUES ('" + this.number + "', '" +
            this.firstname + "', '" +
            this.lastname + "', '" +
            this.birthdate + "', '" +
            this.phonenumber + "', '" +
            this.email + "', '" +
            this.password + "')";

    Application.db.storeData(query);
  }

  public void update()
  {
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
