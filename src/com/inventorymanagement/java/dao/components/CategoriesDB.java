
package com.inventorymanagement.java.dao.components;

import com.inventorymanagement.java.models.Category;
import com.inventorymanagement.java.utils.Constants;
import com.inventorymanagement.java.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoriesDB {

    private String TableName = Constants.TABLE_CATEGORIES;

    private PreparedStatement preparedStatement = null;
    private Connection connection = DBConnection.getInstance().getConnection();


    public CategoriesDB(){}


    // get All categories
    public List<Category> getAll() {
        String query = "SELECT * FROM " + TableName + " ORDER BY id DESC ";
        List<Category> categoryList = new ArrayList<>();

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt(1));
                category.setCategoryName(resultSet.getString(2));
                category.setCategoryDescription(resultSet.getString(3));

                categoryList.add(category);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return categoryList;
    }

    // add category;

    public int create(Category category) {
        String query = "INSERT INTO " + TableName + " VALUES(" +
                "?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, category.getCategoryName());
            preparedStatement.setString(3, category.getCategoryDescription());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return -1;
        }
    }

    //edit category
    public int edit(int id, String categoryName, String categoryDescription) {
        String query = "update " + TableName + " set " + Category.CATEGORY_NAME + " =?, " +
                Category.CATEGORY_DESCRIPTION + " =? " +
                " where id=?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, categoryName);
            preparedStatement.setString(2, categoryDescription);
            preparedStatement.setInt(3, id);

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return -1;
        }
    }

    // delete a category
    public int delete(int id) {
        String query = "DELETE FROM " + TableName + "  WHERE " +
                Category.CATEGORY_ID + " = " + id + ";  ";


        try {
            preparedStatement = connection.prepareStatement(query);
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return -1;
        }


    }
}
