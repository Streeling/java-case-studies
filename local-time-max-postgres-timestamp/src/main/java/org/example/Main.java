package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

public class Main {
  public static void main(String[] args) throws ClassNotFoundException, SQLException {
    // https://www.geeksforgeeks.org/how-to-use-preparedstatement-in-java/
    Class.forName("org.postgresql.Driver");
    Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/demo", "postgres", "postgres");
    String query = "SELECT * FROM date_max WHERE created_at <= ?";
    PreparedStatement myStmt = con.prepareStatement(query);

    LocalDateTime localDateTime = LocalDateTime.of(LocalDate.of(2023, 8, 03), LocalTime.MAX);
    // No
    myStmt.setTimestamp(1, Timestamp.valueOf(localDateTime));
    // Yes
//    myStmt.setTimestamp(1, new Timestamp(localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000));
    ResultSet myRs = myStmt.executeQuery();
    while (myRs.next()) {
      String value = myRs.getString("value");
      // No: will print 3 and 4 but should print only 3
      // Yes: will print 3
      System.out.println(value);
    }
    con.close();
  }
}