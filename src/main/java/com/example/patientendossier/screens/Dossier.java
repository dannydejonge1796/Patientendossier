package com.example.patientendossier.screens;

import com.example.patientendossier.Patient;

public class Dossier {

  private Patient patient;

  public Dossier(Patient patient) {
    this.patient = patient;

    System.out.println(this.patient.getNumber());
  }
}
