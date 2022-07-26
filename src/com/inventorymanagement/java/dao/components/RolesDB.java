package com.inventorymanagement.java.dao.components;

import com.inventorymanagement.java.models.Roles;
import com.inventorymanagement.java.utils.Constants;
import com.inventorymanagement.java.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RolesDB {
    private String TableName = Constants.TABLE_ROLES;

    private PreparedStatement preparedStatement = null;
    private Connection connection = DBConnection.getInstance().getConnection();


    public RolesDB(){}


    // get All roles
    public List<Roles> getAll() {
        String query = "SELECT * FROM " + TableName + " ORDER BY id DESC ";
        List<Roles> rolesList = new ArrayList<>();

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Roles role = new Roles(resultSet.getInt(1), resultSet.getString(2));
                rolesList.add(role);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return rolesList;
    }

    // add role;

    public int create(Roles role) {
        String query = "INSERT INTO " + TableName + " VALUES(" +
                "?, ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, role.getRole_name());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return -1;
        }
    }


    public int delete(int id) {
        String query = "DELETE FROM " + TableName + "  WHERE " +
                Roles.ROLE_ID + " = " + id + ";  ";


        try {
            preparedStatement = connection.prepareStatement(query);
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return -1;
        }


    }
}
