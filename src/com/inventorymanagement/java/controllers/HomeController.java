package com.inventorymanagement.java.controllers;

import com.inventorymanagement.java.dao.components.CategoriesDB;
import com.inventorymanagement.java.dao.Main_DAO;
import com.inventorymanagement.java.dao.components.ProductsDB;
import com.inventorymanagement.java.main.Launcher;
import com.inventorymanagement.java.models.Category;
import com.inventorymanagement.java.models.Product;
import com.inventorymanagement.java.utils.Constants;
import com.inventorymanagement.java.utils.MyScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HomeController {

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

    @FXML
    private AnchorPane mainPane;

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


    private void setStageDraggable() {
        mainPane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        mainPane.setOnMouseDragged(event -> {
            Launcher.stage.setX(event.getScreenX() - xOffset);
            Launcher.stage.setY(event.getScreenY() - yOffset);
            Launcher.stage.setOpacity(0.5f);
        });

        mainPane.setOnMouseReleased(event -> {
            Launcher.stage.setOpacity(1);
        });
    }

    public void homeBtnEvent(MouseEvent mouseEvent) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource(Constants.HOME_FXML_DIR));
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.setScene(MyScene.getScene(parent));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void productBtnEvent(MouseEvent mouseEvent) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource(Constants.PRODUCT_FXML_DIR));
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.setScene(MyScene.getScene(parent));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    // category handler
    public void categoryBtnEvent(MouseEvent mouseEvent) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource(Constants.CATEGORY_FXML_DIR));
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.setScene(MyScene.getScene(parent));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    // history handler
    public void historyBtnEvent(MouseEvent mouseEvent) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource(Constants.HISTORY_FXML_DIR));
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.setScene(MyScene.getScene(parent));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
