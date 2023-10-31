package com.example.myhike;

import java.sql.*;
import java.util.HashMap;

public class Database {
    //TODO: return Hike-Object, just returning single String for testing purposes.
    public static String LoadData() {
        //Load postgre Driver
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Define the database connection parameters
        String dbUrl = "jdbc:postgresql://ep-raspy-art-31333345.eu-central-1.aws.neon.tech/MyHike";
        String dbUser = "group_d_main";
        String dbPassword = "4hYq5IaBCWie";

        // Create a SQL query
        String sql = "SELECT * FROM MyHike.hike";

        try (   Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql))
        {
            // Loop through the results and display them
            while (resultSet.next()) {
                // TODO remove printing of data once we use it later on.
                String hikeName = resultSet.getString("hike_name");
                System.out.println("Column Value: " + hikeName + "<br>");
                //out.println("Column Value: " + routeName + "<br>");
                return hikeName;
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            //out.println("An error occurred: " + e.getMessage());
        }
        return null;
    }
}
