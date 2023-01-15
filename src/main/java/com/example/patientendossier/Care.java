package com.example.patientendossier;

public class Care {

  private Database db;
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
