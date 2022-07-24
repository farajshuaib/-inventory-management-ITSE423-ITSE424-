package com.inventorymanagement.java.controllers;

import com.inventorymanagement.java.dao_composite.Main_DAO;
import com.inventorymanagement.java.dao_composite.components.CategoriesDB;
import com.inventorymanagement.java.dao_composite.components.ProductsDB;
import com.inventorymanagement.java.models.Category;
import com.inventorymanagement.java.models.Product;
import com.inventorymanagement.java.models.User;
import com.inventorymanagement.java.utils.LayoutsActions;
import com.inventorymanagement.java.utils.observeUserData.Observer;
import com.inventorymanagement.java.utils.observeUserData.UserData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController extends LayoutsActions implements Initializable, Observer {
    User userData = UserData.getState();

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

    @Override
    public void update(){
        this.userData = UserData.getState();
    }


    private void setButtonsVisibility(){
        categoryButton.setVisible(userData != null ? userData.getRole().equals("admin") : false);
        usersButton.setVisible(userData != null ? userData.getRole().equals("admin"): false);
        historyButton.setVisible(userData != null ? userData.getRole().equals("admin"): false);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UserData.addObserver(this);
        setButtonsVisibility();


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
