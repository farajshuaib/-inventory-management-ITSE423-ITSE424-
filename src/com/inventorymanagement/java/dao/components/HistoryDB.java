
package com.inventorymanagement.java.dao.components;

import com.inventorymanagement.java.models.History;
import com.inventorymanagement.java.utils.Constants;
import com.inventorymanagement.java.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HistoryDB {

    private String TableName = Constants.TABLE_HISTORY;

    private PreparedStatement preparedStatement = null;
    private Connection connection = DBConnection.getInstance().getConnection();

    public HistoryDB(){}

    // get All records
    public List<History> getAll() {
        String query = "SELECT * FROM " + TableName;
        List<History> categoryList = new ArrayList<>();

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                History history = new History();
                history.setId(resultSet.getInt(1));
                history.setProductName(resultSet.getString(2));
                history.setProductPrice(resultSet.getDouble(3));
                history.setDescription(resultSet.getString(4));
                history.setProductCategory(resultSet.getString(5));
                history.setAction(resultSet.getString(6));
                history.setDate(resultSet.getString(7));

                categoryList.add(history);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return categoryList;
    }

    // adding new history
    public int create(History history) {
        String query = "INSERT INTO " + TableName + " VALUES(" +
                "?, ?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, history.getProductName());
            preparedStatement.setDouble(3, history.getProductPrice());
            preparedStatement.setString(4, history.getDescription());
            preparedStatement.setString(5, history.getProductCategory());
            preparedStatement.setString(6, history.getAction());
            preparedStatement.setString(7, history.getDate());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return -1;
        }
    }

    // delete a record
    public int delete(int id) {
        String query = "DELETE FROM " + TableName + "  WHERE " +
                History.RECORD_ID + " = " + id + ";  ";

        try {
            preparedStatement = connection.prepareStatement(query);
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return -1;
        }
    }
}
