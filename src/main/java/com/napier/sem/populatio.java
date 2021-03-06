package com.napier.sem;

import java.sql.*;
import com.napier.sem.entities.*;

public class populatio {
    /**
     * Connection to MySQL database.
     */
    public Connection con = null;


    /**
     * Connect to the MySQL database.
     */
    public void connect(String location) {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + location + "/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                // Close connection
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database"); }
        }
    }

    public static void main(String[] args) {
        // Create new Application
        populatio a = new populatio(); //Initialte a new populatio object

        // Connect to database
            if (args.length < 1)
            {
                a.connect("localhost:3306");
            }
            else
            {
            a.connect(args[0]);
        }

        City c = new City(); //Create new placeholder city
        c = c.getCity(25, a.con); //Populate placeholder with city based on ID, also pass on MySQL connection to City.java
        c.displayCity(c); //Display information in console

        // Disconnect from database
        a.disconnect();
    }
}