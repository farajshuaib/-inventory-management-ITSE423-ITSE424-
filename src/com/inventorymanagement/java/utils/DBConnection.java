
package com.inventorymanagement.java.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBConnection {
    private Properties properties = new Properties();
    private static DBConnection instance = new DBConnection();
    private Connection connection = null;
    private Statement statement = null;

    private DBConnection() {
        getDBDataSource();
        connection();
    }

    public static DBConnection getInstance() {
        if(instance == null){
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        if(connection == null){
            connection();
        }
        return connection;
    }

    private void getDBDataSource() {
        try (FileReader reader = new FileReader(Constants.DATABASE_CONFIG_DIR)) {
            properties.load(reader);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getUser() {
        return properties.getProperty("db.username");
    }

    private String getPassword() {
        return properties.getProperty("db.password");
    }

    private String getDatasource() {
        return properties.getProperty("db.datasource");
    }



    // the connection method which made the connection
    private void connection() {
        try {
            Connection connection = DriverManager.getConnection(getDatasource(), getUser(), getPassword());
            this.connection = connection;
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Error :" + "Unable to connect to database => " + e);
        }
    }
}
