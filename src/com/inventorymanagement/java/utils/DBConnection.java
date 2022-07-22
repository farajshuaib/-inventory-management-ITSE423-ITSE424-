
package com.inventorymanagement.java.utils;

import com.inventorymanagement.java.models.Category;
import com.inventorymanagement.java.models.History;
import com.inventorymanagement.java.models.Product;
import com.inventorymanagement.java.models.User;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    private Properties properties = new Properties();
    private static DBConnection instance = new DBConnection();
    private Connection connection = null;
    private Statement statement = null;

    private DBConnection() {
        getDBDataSource();
        connection();
        createTables();
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
            Alerts.jfxAlertShowAndWait("Error", "Unable to connect to database");
        }
    }

    // automating table creation
    private void createTables() {
        try {
            // the users table
            String usersTable = "CREATE TABLE IF NOT EXISTS  `inventorymanagement`.`" + Constants.TABLE_USERS +
                    "` ( `" + User.USER_ID + "` INT NOT NULL AUTO_INCREMENT , `" + User.USER_FIRST_NAME +
                    "` VARCHAR(32) NOT NULL , `" + User.USER_LAST_NAME + "` VARCHAR(32) NOT NULL , `" +
                    User.USER_EMAIL + "` VARCHAR(64) NOT NULL UNIQUE , `" + User.USER_GENDER + "` VARCHAR(10) NOT NULL ," +
                    " `" + User.USER_NUMBER + "` VARCHAR(15) NOT NULL , `" + User.USER_PASSWORD + "` VARCHAR(1024) NOT NULL," +
                    " PRIMARY KEY (`id`)) ENGINE = InnoDB;";

            // the categories table
            String categoriesTable = "CREATE TABLE IF NOT EXISTS `inventorymanagement`.`" + Constants.TABLE_CATEGORIES +
                    "` ( `" + Category.CATEGORY_ID + "` INT NOT NULL AUTO_INCREMENT , `" + Category.CATEGORY_NAME +
                    "` VARCHAR(32) NOT NULL UNIQUE ,  `" + Category.CATEGORY_DESCRIPTION + "` VARCHAR(64) ," +
                    " PRIMARY KEY (`id`)) ENGINE = InnoDB;";

            // the products table
            String productsTable = "CREATE TABLE IF NOT EXISTS `inventorymanagement`.`" + Constants.TABLE_PRODUCTS +
                    "` ( `" + Product.PRODUCT_ID + "` INT NOT NULL AUTO_INCREMENT , `" +
                    Product.PRODUCT_NAME + "` VARCHAR(32) NOT NULL , `" +
                    Product.PRODUCT_DESCRIPTION + "` VARCHAR(64) NOT NULL , `" + Product.PRODUCT_PRICE + "` DOUBLE NOT NULL , `" +
                    Product.PRODUCT_NUMBER_IN_STOCK + "` INT NOT NULL , `" + Product.PRODUCT_CATEGORY +
                    "` VARCHAR(32) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;";

            String recordTable = "CREATE TABLE IF NOT EXISTS `inventorymanagement`.`" + Constants.TABLE_HISTORY +
                    "` ( `" + History.RECORD_ID + "` INT NOT NULL AUTO_INCREMENT, `" +
                    History.RECORD_PRODUCT_NAME + "` VARCHAR(32) NOT NULL, `" + History.RECORD_PRICE +
                    "` DOUBLE NOT NULL, ` " + History.RECORD_DESCRIPTION + "` VARCHAR(64) NOT NULL, `" +
                    History.RECORD_CATEGORY + "` VARCHAR(30) NOT NULL, `" + History.RECORD_ACTION + "` " +
                    "VARCHAR(32) NOT NULL, `" + History.RECORD_DATE + "`VARCHAR(32) NOT NULL," +
                    " PRIMARY KEY (`id`)) ENGINE = InnoDB;";



            statement.executeUpdate(usersTable);
            statement.executeUpdate(productsTable);
            statement.executeUpdate(categoriesTable);
            statement.executeUpdate(recordTable);

            Logger.getLogger(getClass().getName()).log(Level.INFO, "All tables successfully loaded");
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
//            e.printStackTrace();
        }
    }
}
