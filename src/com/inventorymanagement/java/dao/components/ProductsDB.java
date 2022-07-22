
package com.inventorymanagement.java.dao.components;

import com.inventorymanagement.java.models.Product;
import com.inventorymanagement.java.utils.Constants;
import com.inventorymanagement.java.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductsDB {

    private String TableName = Constants.TABLE_PRODUCTS;

    private PreparedStatement preparedStatement = null;
    private Connection connection = DBConnection.getInstance().getConnection();

    public ProductsDB(){}


    // get All products
    public List<Product> getAll() {
        String query = "SELECT * FROM " + TableName + " ORDER BY id DESC ";
        List<Product> productList = new ArrayList<>();

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt(1));
                product.setProductName(resultSet.getString(2));
                product.setProductDescription(resultSet.getString(3));
                product.setPrice(resultSet.getDouble(4));
                product.setNoInStock(resultSet.getInt(5));
                product.setProductCategory(resultSet.getString(6));

                productList.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return productList;
    }

    // adding new product
    public int create(Product product) {
        String query = "INSERT INTO " + TableName + " VALUES(" +
                "?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, product.getProductName());
            preparedStatement.setString(3, product.getProductDescription());
            preparedStatement.setDouble(4, product.getPrice());
            preparedStatement.setInt(5, product.getNoInStock());
            preparedStatement.setString(6, product.getProductCategory());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return -1;
        }
    }

    // editing products
    public int edit(int id, String productName, String productDescription, double price, int noInStock, String category) {
        String query = "update " + TableName + " set " + Product.PRODUCT_NAME + " =?, " +
                Product.PRODUCT_DESCRIPTION + " =?, " + Product.PRODUCT_PRICE + "=?, " + Product.PRODUCT_CATEGORY + " =?, " +
                Product.PRODUCT_NUMBER_IN_STOCK + " =? where id=?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, productName);
            preparedStatement.setString(2, productDescription);
            preparedStatement.setDouble(3, price);
            preparedStatement.setString(4, category);
            preparedStatement.setInt(5, noInStock);
            preparedStatement.setInt(6, id);

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return -1;
        }
    }


    // delete a product
    public int delete(int id) {
        String query = "DELETE FROM " + TableName + "  WHERE " +
                Product.PRODUCT_ID + " = " + id + ";  ";

        try {
            preparedStatement = connection.prepareStatement(query);
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return -1;
        }
    }

}
