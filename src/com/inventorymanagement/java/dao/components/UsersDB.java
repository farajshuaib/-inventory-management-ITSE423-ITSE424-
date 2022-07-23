
package com.inventorymanagement.java.dao.components;

import com.inventorymanagement.java.models.User;
import com.inventorymanagement.java.utils.Constants;
import com.inventorymanagement.java.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsersDB {
    private String TableName = Constants.TABLE_USERS;
    private PreparedStatement preparedStatement = null;
    private Connection connection = DBConnection.getInstance().getConnection();

    public UsersDB(){
    }

    public List<User> getAll(){
        String query = "SELECT * FROM " + TableName + " ORDER BY id DESC ";
        List<User> usersList = new ArrayList<>();

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8)
                        );
                usersList.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return usersList;
    }

    // adding new user
    public int create(User user) {
        String query = "INSERT INTO " + TableName + " VALUES(" +
                "?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getGender());
            preparedStatement.setString(6, user.getNumber());
            preparedStatement.setString(7, user.getPassword());
            preparedStatement.setString(8, user.getRole());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return -1;
        }
    }

    // editing user
    public int edit(int id, String firstName, String lastName, String gender, String number, String role) {
        String query = "update " + TableName + " set " + User.USER_FIRST_NAME + " =?, " +
                User.USER_LAST_NAME + " =?, " + User.USER_GENDER + "=?, " + User.USER_NUMBER + " =? " +
                User.ROLE + " =? " +
                " where id=?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, gender);
            preparedStatement.setString(4, number);
            preparedStatement.setString(5, role);
            preparedStatement.setInt(6, id);

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return -1;
        }
    }

    public int delete(int id) {
        String query = "DELETE FROM " + TableName + "  WHERE " +
                User.USER_ID + " = " + id + ";  ";


        try {
            preparedStatement = connection.prepareStatement(query);
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return -1;
        }


    }

    //check if the user exist
    public int userExist(String email) {
        String query = "SELECT COUNT(" + User.USER_EMAIL + ") FROM " + TableName +
                " WHERE " + User.USER_EMAIL + " = '" + email + "'";

        try {
            preparedStatement = connection.prepareStatement(query);
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return -1;
        }
    }

    public Boolean authenticate(String email, String password) throws SQLException {
        String query = "SELECT * FROM " + TableName + " WHERE email = '" + email + "' and " +
                "password = '" + password + "'";

        Statement statement = connection.createStatement();
        statement.executeQuery(query);
        if (statement.getResultSet().next())
            return true;
        else
            return false;
    }
}
