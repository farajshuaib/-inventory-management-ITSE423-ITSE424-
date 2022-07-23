
package com.inventorymanagement.java.controllers;

import com.inventorymanagement.java.dao_composite.Main_DAO;
import com.inventorymanagement.java.dao_composite.components.HistoryDB;
import com.inventorymanagement.java.models.History;
import com.inventorymanagement.java.utils.LayoutsActions;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class HistoryController extends LayoutsActions implements Initializable {

    HistoryDB historyDB =  Main_DAO.getInstance().history();
    List<History> getHistoryList = null;
    ObservableList<RecursiveHistory> recordList = null;
    @FXML
    private TreeTableColumn<RecursiveHistory, String> col_action;
    @FXML
    private TreeTableColumn<RecursiveHistory, String> col_date;
    @FXML
    private TreeTableColumn<RecursiveHistory, String> col_description;
    @FXML
    private TreeTableColumn<RecursiveHistory, String> col_category;
    @FXML
    private TreeTableColumn<RecursiveHistory, String> col_price;
    @FXML
    private TreeTableColumn<RecursiveHistory, String> col_name;

    @FXML
    private TreeTableColumn<RecursiveHistory, String> col_id;
    @FXML
    private JFXTreeTableView<RecursiveHistory> tableView;
    @FXML
    private JFXTextField searchField;

    public void initialize(URL location, ResourceBundle resources) {
        if(userData == null) return;
        categoryButton.setVisible(userData.getRole().equals("admin"));
        usersButton.setVisible(userData.getRole().equals("admin"));
        historyButton.setVisible(userData.getRole().equals("admin"));

        recordList = FXCollections.observableArrayList();
        getHistoryList = historyDB.getAll();

        //set table values
        setTable();
        //set stage draggable
        setStageDraggable();
    }

    public void refreshAction() {
        // event for refresh
        recordList.removeAll(recordList);
        getHistoryList = historyDB.getAll();
//        String.valueOf(record.getId()), record.getProductName(),
//                String.valueOf(record.getPrice()), record.getProductDescription(),
//                String.valueOf(record.getNoInStock()), record.getProductCategory())
        getHistoryList.forEach(history -> {
            recordList.add(new RecursiveHistory(String.valueOf(history.getId()), history.getProductName(),
                    String.valueOf(history.getProductPrice()), history.getDescription(),
                    history.getProductCategory(), history.getAction(), history.getDate()
            ));
        });
    }

    // setting table values
    private void setTable() {
        col_id.setCellValueFactory(param -> param.getValue().getValue().id);
        col_name.setCellValueFactory(param -> param.getValue().getValue().productName);
        col_description.setCellValueFactory(param -> param.getValue().getValue().productDescription);
        col_price.setCellValueFactory(param -> param.getValue().getValue().productPrice);
        col_category.setCellValueFactory(param -> param.getValue().getValue().productCategory);
        col_action.setCellValueFactory(param -> param.getValue().getValue().action);
        col_date.setCellValueFactory(param -> param.getValue().getValue().date);


        getHistoryList.forEach(history -> {
            recordList.add(new RecursiveHistory(String.valueOf(history.getId()), history.getProductName(),
                    String.valueOf(history.getProductPrice()), history.getDescription(),
                    history.getProductCategory(), history.getAction(),
                    LocalDateTime.parse(history.getDate()).format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss"))
            ));
        });

        TreeItem<RecursiveHistory> root = new RecursiveTreeItem<>(recordList, RecursiveTreeObject::getChildren);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableView.setRoot(root);
        tableView.setShowRoot(false);

        searchField.textProperty().addListener((observable, oldValue, newValue) ->
                tableView.setPredicate(modelTreeItem ->
                        modelTreeItem.getValue().id.getValue().toLowerCase().contains(newValue)
                                | modelTreeItem.getValue().productName.getValue().toLowerCase().contains(newValue)));
    }


    class RecursiveHistory extends RecursiveTreeObject<RecursiveHistory> {
        private StringProperty id, productName, productPrice, productDescription, productCategory, action, date;

        public RecursiveHistory(
                String id, String productName,
                String productPrice, String productDescription,
                String productCategory,
                String action, String date
        ) {
            this.id = new SimpleStringProperty(id);
            this.productName = new SimpleStringProperty(productName);
            this.productPrice = new SimpleStringProperty(productPrice);
            this.productDescription = new SimpleStringProperty(productDescription);
            this.productCategory = new SimpleStringProperty(productCategory);
            this.action = new SimpleStringProperty(action);
            this.date = new SimpleStringProperty(date);
        }

        public String getId() {
            return id.get();
        }

        public void setId(String id) {
            this.id.set(id);
        }

        public StringProperty idProperty() {
            return id;
        }

        public String getProductName() {
            return productName.get();
        }

        public void setProductName(String productName) {
            this.productName.set(productName);
        }

        public StringProperty productNameProperty() {
            return productName;
        }

        public String getProductPrice() {
            return productPrice.get();
        }

        public void setProductPrice(String productPrice) {
            this.productPrice.set(productPrice);
        }

        public StringProperty productPriceProperty() {
            return productPrice;
        }

        public String getProductDescription() {
            return productDescription.get();
        }

        public void setProductDescription(String productDescription) {
            this.productDescription.set(productDescription);
        }

        public StringProperty productDescriptionProperty() {
            return productDescription;
        }

        public String getProductCategory() {
            return productCategory.get();
        }

        public void setProductCategory(String productCategory) {
            this.productCategory.set(productCategory);
        }

        public StringProperty productCategoryProperty() {
            return productCategory;
        }

        public String getAction() {
            return action.get();
        }

        public void setAction(String action) {
            this.action.set(action);
        }

        public StringProperty actionProperty() {
            return action;
        }

        public String getDate() {
            return date.get();
        }

        public void setDate(String date) {
            this.date.set(date);
        }

        public StringProperty dateProperty() {
            return date;
        }
    }
}
