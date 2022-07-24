
package com.inventorymanagement.java.controllers;

import com.inventorymanagement.java.dao_composite.Main_DAO;
import com.inventorymanagement.java.dao_composite.components.CategoriesDB;
import com.inventorymanagement.java.models.Category;
import com.inventorymanagement.java.models.User;
import com.inventorymanagement.java.utils.Alerts;
import com.inventorymanagement.java.utils.LayoutsActions;
import com.inventorymanagement.java.utils.observeUserData.Observer;
import com.inventorymanagement.java.utils.observeUserData.UserData;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CategoryController extends LayoutsActions implements Initializable, Observer {
    User userData = UserData.getState();
    CategoriesDB categoriesDB = Main_DAO.getInstance().Categories();
    @FXML
    private StackPane primaryPane;
    List<Category> getCategoryList = null;
    ObservableList<RecursiveCategory> categoryList = null;
    @FXML
    private MenuItem menuEditBtn;
    @FXML
    private MenuItem menuDeleteBtn;
    @FXML
    private JFXTextField searchField;
    @FXML
    private JFXButton addBtn;
    @FXML
    private JFXButton refreshBtn;
    @FXML
    private TreeTableColumn<RecursiveCategory, String> col_description;

    @FXML
    private TreeTableColumn<RecursiveCategory, String> col_name;
    @FXML
    private TreeTableColumn<RecursiveCategory, String> col_id;
    @FXML
    private JFXTreeTableView<RecursiveCategory> tableView;

    private void setButtonsVisibility(){
        categoryButton.setVisible(userData != null ? userData.getRole().equals("admin") : false);
        usersButton.setVisible(userData != null ? userData.getRole().equals("admin"): false);
        historyButton.setVisible(userData != null ? userData.getRole().equals("admin"): false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UserData.addObserver(this);
        setButtonsVisibility();

        categoryList = FXCollections.observableArrayList();

        // fetching the list from database
        getCategoryList = categoriesDB.getAll();

        // setting table objects
        setTable();

        // set events
        setBtnEvents();

        // setting the stage draggable
        setStageDraggable();
    }

    private void setBtnEvents() {
        // event for add btn
        addBtn.setOnMouseClicked(event -> {

            BoxBlur blur = new BoxBlur(3.0, 3.0, 3);
            mainPane.setEffect(blur);

            JFXDialogLayout content = new JFXDialogLayout();
            JFXDialog dialog = new JFXDialog(primaryPane, content, JFXDialog.DialogTransition.TOP);
            content.setAlignment(Pos.CENTER);
            content.setHeading(new Text("Add Category"));
            dialog.setOverlayClose(false);
            VBox box = new VBox();
            box.setSpacing(15);
            box.setAlignment(Pos.CENTER);

            JFXTextField categoryNameField = new JFXTextField();
            categoryNameField.setPromptText("Category Name");
            categoryNameField.setLabelFloat(true);

            JFXTextField categoryDescriptionField = new JFXTextField();
            categoryDescriptionField.setPromptText("Category Description");
            categoryDescriptionField.setLabelFloat(true);

            box.getChildren().addAll(categoryNameField, categoryDescriptionField);
            box.setSpacing(30.0);
            content.setBody(box);

            JFXButton saveBtn = new JFXButton("Save");
            JFXButton cancelBtn = new JFXButton("Cancel");

            saveBtn.getStyleClass().add("dial-btn");
            cancelBtn.getStyleClass().add("dial-btn");


            saveBtn.setOnAction(event1 -> {
                if (categoryNameField.getText().isEmpty() || categoryNameField.getText().trim().isEmpty()) {
                    Alerts.jfxAlert("Error", "Product Name Field cannot be empty");
                    return;
                }

                Category category = new Category(0, categoryNameField.getText(), categoryDescriptionField.getText());

                if (categoriesDB.create(category) != 1) {
                    Alerts.jfxAlert("Error", "An error occurred");
                    return;
                }

                refreshAction();
                dialog.close();
            });

            cancelBtn.setOnAction(event1 -> {
                dialog.close();
            });

            content.setActions(saveBtn, cancelBtn);

            dialog.setOnDialogClosed(event1 -> {
                mainPane.setEffect(null);
            });
            dialog.show();
        });

        // editing for edit btn
        menuEditBtn.setOnAction(event -> {
            if (tableView.getSelectionModel().getFocusedIndex() == -1) {
                Alerts.jfxAlert("Error", "No item selected");
                return;
            }

            List<RecursiveCategory> selectedProduct = new ArrayList<>();
            selectedProduct.add(tableView.getSelectionModel().getSelectedItem().getValue());

            BoxBlur blur = new BoxBlur(3.0, 3.0, 3);
            mainPane.setEffect(blur);

            JFXDialogLayout content = new JFXDialogLayout();
            JFXDialog dialog = new JFXDialog(primaryPane, content, JFXDialog.DialogTransition.TOP);
            content.setAlignment(Pos.CENTER);
            content.setHeading(new Text("Edit Category"));
            dialog.setOverlayClose(false);
            VBox box = new VBox();
            box.setSpacing(15);
            box.setAlignment(Pos.CENTER);

            JFXTextField categoryIdField = new JFXTextField();
            categoryIdField.setPromptText("Category Id");
            categoryIdField.setLabelFloat(true);
            categoryIdField.setDisable(true);
            categoryIdField.setText(tableView.getSelectionModel().getSelectedItem().getValue().getId());

            JFXTextField categoryNameField = new JFXTextField();
            categoryNameField.setPromptText("Category Name");
            categoryNameField.setLabelFloat(true);
            categoryNameField.setText(tableView.getSelectionModel().getSelectedItem().getValue().getCategoryName());

            JFXTextField categoryDescriptionField = new JFXTextField();
            categoryDescriptionField.setPromptText("Category Description");
            categoryDescriptionField.setLabelFloat(true);
            categoryDescriptionField.setText(tableView.getSelectionModel().getSelectedItem().getValue().getCategoryDescription());

            box.getChildren().addAll(categoryIdField, categoryNameField, categoryDescriptionField);
            box.setSpacing(30.0);
            content.setBody(box);

            JFXButton saveBtn = new JFXButton("Save");
            JFXButton cancelBtn = new JFXButton("Cancel");

            saveBtn.getStyleClass().add("dial-btn");
            cancelBtn.getStyleClass().add("dial-btn");


            saveBtn.setOnAction(event1 -> {
                if (categoryNameField.getText().isEmpty() || categoryNameField.getText().trim().isEmpty()) {
                    Alerts.jfxAlert("Error", "Product Name Field cannot be empty");
                    return;
                }

                if (categoriesDB.edit(Integer.parseInt(categoryIdField.getText()),
                        categoryNameField.getText(), categoryDescriptionField.getText()) != 1) {
                    Alerts.jfxAlert("Error", "An error occurred");
                    return;
                }

                refreshAction();
                dialog.close();
            });

            cancelBtn.setOnAction(event1 -> {
                dialog.close();
            });

            content.setActions(saveBtn, cancelBtn);

            dialog.setOnDialogClosed(event1 -> {
                mainPane.setEffect(null);
            });
            dialog.show();
        });

        // deleting event
        menuDeleteBtn.setOnAction(event -> {
            if (tableView.getSelectionModel().getFocusedIndex() == -1) {
                Alerts.jfxAlert("Error", "No item selected");
                return;
            }

            int categoryId = Integer.parseInt(tableView.getSelectionModel().getSelectedItem().getValue().getId());
            BoxBlur blur = new BoxBlur(3.0, 3.0, 3);
            mainPane.setEffect(blur);

            JFXDialogLayout content = new JFXDialogLayout();
            JFXDialog dialog = new JFXDialog(primaryPane, content, JFXDialog.DialogTransition.TOP);
            content.setAlignment(Pos.CENTER);
            content.setHeading(new Text("Delete Category"));
            dialog.setOverlayClose(false);
            VBox box = new VBox();
            box.setSpacing(15);
            box.setAlignment(Pos.CENTER);

            Label text = new Label("Are you sure you want to delete this item ?");

            box.getChildren().addAll(text);
            box.setSpacing(30.0);
            content.setBody(box);

            JFXButton saveBtn = new JFXButton("Ok");
            JFXButton cancelBtn = new JFXButton("Cancel");

            saveBtn.getStyleClass().add("dial-btn");
            cancelBtn.getStyleClass().add("dial-btn");
            saveBtn.setOnAction(event1 -> {


                if (categoriesDB.delete(categoryId) != 1) {
                    Alerts.jfxAlert("Error", "An error occurred");
                    return;
                }
                refreshAction();
                dialog.close();
            });

            cancelBtn.setOnAction(event1 -> {
                dialog.close();
            });

            content.setActions(saveBtn, cancelBtn);

            dialog.setOnDialogClosed(event1 -> {
                mainPane.setEffect(null);
            });
            dialog.show();
        });
    }

    public void refreshAction() {
        // event for refresh
        categoryList.removeAll(categoryList);
        getCategoryList = categoriesDB.getAll();
        getCategoryList.forEach(categories -> {
            categoryList.add(new RecursiveCategory(String.valueOf(categories.getId()), categories.getCategoryName(),
                    categories.getCategoryDescription()));
        });
    }

    private void setTable() {
        col_id.setCellValueFactory(param -> param.getValue().getValue().id);
        col_name.setCellValueFactory(param -> param.getValue().getValue().categoryName);
        col_description.setCellValueFactory(param -> param.getValue().getValue().categoryDescription);

        getCategoryList.forEach(category -> {
            categoryList.add(new RecursiveCategory(String.valueOf(category.getId()), category.getCategoryName(),
                    category.getCategoryDescription()));
        });

        TreeItem<RecursiveCategory> root = new RecursiveTreeItem<>(categoryList, RecursiveTreeObject::getChildren);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableView.setRoot(root);
        tableView.setShowRoot(false);

        searchField.textProperty().addListener((observable, oldValue, newValue) ->
                tableView.setPredicate(modelTreeItem ->
                        modelTreeItem.getValue().id.getValue().toLowerCase().contains(newValue)
                                | modelTreeItem.getValue().categoryName.getValue().toLowerCase().contains(newValue)));
    }

    @Override
    public void update(){
        this.userData = UserData.getState();
    }


    static class RecursiveCategory extends RecursiveTreeObject<RecursiveCategory> {
        private StringProperty id, categoryName, categoryDescription;

        public RecursiveCategory(String id, String categoryName, String categoryDescription) {
            this.id = new SimpleStringProperty(id);
            this.categoryName = new SimpleStringProperty(categoryName);
            this.categoryDescription = new SimpleStringProperty(categoryDescription);
        }

        public String getId() {
            return id.get();
        }

        public void setId(String id) {
            this.id.set(id);
        }

        public String getCategoryName() {
            return categoryName.get();
        }
        public String getCategoryDescription() {
            return categoryDescription.get();
        }

    }
}
