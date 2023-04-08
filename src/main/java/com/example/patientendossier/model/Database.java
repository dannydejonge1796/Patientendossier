package com.example.patientendossier.model;

import java.sql.*;

public class Database {

  private Connection conn;

  public Database()
  {
    String user = "root";
    String password= "";
    String port = "3306";
    String db = "dossier";
    String cString = "jdbc:mysql://localhost:" + port + "/" + db + "?user=" + user + "&password=" + password;

    try {
      //Connectie aanmaken met opgegeven data
      this.conn = DriverManager.getConnection(cString);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public ResultSet getData(String query)
  {
    try {
      //Query uitvoeren en resultaat teruggeven
      Statement stm = this.conn.createStatement();
      return stm.executeQuery(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null;
  }

  public void storeData(String query)
  {
    try {
      //Data opslaan d.m.v. query
      Statement stm = this.conn.createStatement();
      stm.execute(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
