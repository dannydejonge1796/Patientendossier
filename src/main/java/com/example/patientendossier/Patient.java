package com.example.patientendossier;

import java.time.LocalDate;
import java.util.Date;

public class Patient {

  private Integer number;
  private String firstname;
  private String lastname;
  private LocalDate birthdate;
  private Integer phonenumber;
  private String email;
  private String password;

  public Patient(Integer number, String firstname, String lastname, LocalDate birthdate, Integer phonenumber, String email, String password) {
    this.number = number;
    this.firstname = firstname;
    this.lastname = lastname;
    this.birthdate = birthdate;
    this.phonenumber = phonenumber;
    this.email = email;
    this.password = password;
  }

  public void store() {

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

  public String getPassword() {
    return password;
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
