package com.inventorymanagement.java.controllers;

import com.inventorymanagement.java.dao.Main_DAO;
import com.inventorymanagement.java.dao.components.CategoriesDB;
import com.inventorymanagement.java.dao.components.ProductsDB;
import com.inventorymanagement.java.models.Category;
import com.inventorymanagement.java.models.Product;
import com.inventorymanagement.java.utils.LayoutsActions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;

import java.util.List;

public class HomeController extends LayoutsActions {

    ProductsDB productsDB = Main_DAO.getInstance().products();
    CategoriesDB categoriesDB = Main_DAO.getInstance().Categories();
    List<Category> categoryList = null;
    List<Product> productsList = null;
    double xOffset;
    double yOffset;
    @FXML
    private StackPane primaryPane;

    @FXML
    private PieChart pieChart;



    public void initialize() {
        // fetching the list from database
        categoryList = categoriesDB.getAll();
        productsList = productsDB.getAll();

        setChartContent();

        // set events
        setBtnEvents();

        // setting the stage draggable
        setStageDraggable();
    }

    public void refreshAction() {
        // event for refresh
        categoryList = categoriesDB.getAll();
        productsList = productsDB.getAll();
        setChartContent();
    }


    private void setBtnEvents() {

    }

    private void  setChartContent(){

        ObservableList<PieChart.Data> chartData  = FXCollections.observableArrayList();

        // looping into the categories and set each category with their product data...
        categoryList.forEach(category -> {
            int numberOfProductInCurrentCategory = (int) productsList.stream().filter(product -> product.getProductCategory().equals(category.getCategoryName().toLowerCase())).count();
            chartData.add(new PieChart.Data(category.getCategoryName(), numberOfProductInCurrentCategory));
        });

        pieChart.setData(chartData);
    }







}
