
package com.inventorymanagement.java.controllers;

import com.inventorymanagement.java.dao.Main_DAO;
import com.inventorymanagement.java.dao.components.UsersDB;
import com.inventorymanagement.java.models.User;
import com.inventorymanagement.java.utils.Alerts;
import com.inventorymanagement.java.utils.Constants;
import com.inventorymanagement.java.utils.LayoutsActions;
import com.inventorymanagement.java.utils.MyScene;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsersController extends LayoutsActions {

    UsersDB usersDB = Main_DAO.getInstance().users();
    @FXML
    private StackPane primaryPane;
    List<User> getUsersList = null;
    ObservableList<UsersController.RecursiveUser> UsersList = null;
    @FXML
    private MenuItem menuEditBtn;
    @FXML
    private MenuItem menuDeleteBtn;
    @FXML
    private JFXTextField searchField;
    @FXML
    private JFXButton addBtn;
    @FXML
    private TreeTableColumn<UsersController.RecursiveUser, String> col_id;
    @FXML
    private TreeTableColumn<UsersController.RecursiveUser, String> col_first_name;
    @FXML
    private TreeTableColumn<UsersController.RecursiveUser, String> col_last_name;
    @FXML
    private TreeTableColumn<UsersController.RecursiveUser, String> col_email;
    @FXML
    private TreeTableColumn<UsersController.RecursiveUser, String> col_phone_number;
    @FXML
    private TreeTableColumn<UsersController.RecursiveUser, String> col_gender;
    @FXML
    private JFXTreeTableView<UsersController.RecursiveUser> tableView;



    public void initialize() {
        ProgressBar progressBar = new ProgressBar();
        // initializing list objects
        UsersList = FXCollections.observableArrayList();

        // fetching the list from database
        getUsersList = usersDB.getAll();

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
            Parent parent = null;
            try {
                parent = FXMLLoader.load(getClass().getResource(Constants.REGISTRATION_FXML_DIR));
                Stage stage = (Stage) mainPane.getScene().getWindow();
                stage.setScene(MyScene.getScene(parent));
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

        });

        // editing for edit btn
        menuEditBtn.setOnAction(event -> {
            if (tableView.getSelectionModel().getFocusedIndex() == -1) {
                Alerts.jfxAlert("Error", "No item selected");
                return;
            }

            List<UsersController.RecursiveUser> selectedUser = new ArrayList<>();
            selectedUser.add(tableView.getSelectionModel().getSelectedItem().getValue());

            BoxBlur blur = new BoxBlur(3.0, 3.0, 3);
            mainPane.setEffect(blur);

        });

        // deleting event
        menuDeleteBtn.setOnAction(event -> {
            if (tableView.getSelectionModel().getFocusedIndex() == -1) {
                Alerts.jfxAlert("Error", "No user selected");
                return;
            }

            int userId = Integer.parseInt(tableView.getSelectionModel().getSelectedItem().getValue().getId());
            BoxBlur blur = new BoxBlur(3.0, 3.0, 3);
            mainPane.setEffect(blur);

            JFXDialogLayout content = new JFXDialogLayout();
            JFXDialog dialog = new JFXDialog(primaryPane, content, JFXDialog.DialogTransition.TOP);
            content.setAlignment(Pos.CENTER);
            content.setHeading(new Text("Delete User"));
            dialog.setOverlayClose(false);
            VBox box = new VBox();
            box.setSpacing(15);
            box.setAlignment(Pos.CENTER);

            Label text = new Label("Are you sure you want to delete this user ?");

            box.getChildren().addAll(text);
            box.setSpacing(30.0);
            content.setBody(box);

            JFXButton saveBtn = new JFXButton("Ok");
            JFXButton cancelBtn = new JFXButton("Cancel");

            saveBtn.getStyleClass().add("dial-btn");
            cancelBtn.getStyleClass().add("dial-btn");
            saveBtn.setOnAction(event1 -> {


                if (usersDB.delete(userId) != 1) {
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
        UsersList.removeAll(UsersList);
        getUsersList = usersDB.getAll();
        getUsersList.forEach(user -> {
            UsersList.add(new UsersController.RecursiveUser(String.valueOf(user.getId()), user.getFirstName(),
                    user.getLastName(), user.getEmail(), user.getGender(), user.getNumber()));
        });
    }

    private void setTable() {
        this.col_id.setCellValueFactory(param -> param.getValue().getValue().id);
        this.col_first_name.setCellValueFactory(param -> param.getValue().getValue().firstName);
        this.col_last_name.setCellValueFactory(param -> param.getValue().getValue().LastName);
        this.col_email.setCellValueFactory(param -> param.getValue().getValue().Email);
        this.col_phone_number.setCellValueFactory(param -> param.getValue().getValue().Number);
        this.col_gender.setCellValueFactory(param -> param.getValue().getValue().Gender);

        getUsersList.forEach(user -> {
            UsersList.add(new RecursiveUser(
                    String.valueOf(user.getId()),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getNumber(),
                    user.getGender()
                    )
            );
        });

        TreeItem<UsersController.RecursiveUser> root = new RecursiveTreeItem<>(UsersList, RecursiveTreeObject::getChildren);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableView.setRoot(root);
        tableView.setShowRoot(false);

        searchField.textProperty().addListener((observable, oldValue, newValue) ->
                tableView.setPredicate(modelTreeItem ->
                                modelTreeItem.getValue().id.toString().toLowerCase().contains(newValue)
                                | modelTreeItem.getValue().firstName.toString().toLowerCase().contains(newValue)
                                | modelTreeItem.getValue().LastName.toString().toLowerCase().contains(newValue)
                                | modelTreeItem.getValue().Email.toString().toLowerCase().contains(newValue)));
    }



    static class RecursiveUser extends RecursiveTreeObject<UsersController.RecursiveUser> {
        public StringProperty id, firstName, LastName, Email, Gender, Number;



        public RecursiveUser(String id, String firstName, String LastName, String Email, String Gender, String Number) {
            this.id = new SimpleStringProperty(id);
            this.firstName = new SimpleStringProperty(firstName);
            this.LastName = new SimpleStringProperty(LastName);
            this.Email = new SimpleStringProperty(Email);
            this.Gender = new SimpleStringProperty(Gender);
            this.Number = new SimpleStringProperty(Number);
        }

        public String getId() {
            return id.get();
        }




    }





}
