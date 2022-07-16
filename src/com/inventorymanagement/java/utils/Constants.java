
package com.inventorymanagement.java.utils;

public final class Constants {
    private static Constants instance;

    private Constants(){}

    //Get the only object available
    public static Constants getInstance(){
        if(instance == null){
            instance = new Constants();
        }
        return instance;
    }
    public static String MAIN_FXML_DIR = "/com/inventorymanagement/resources/fxml/main.fxml";
    // icon
    public static final String STAGE_ICON = "/com/inventorymanagement/resources/images/aoo_icon.ico";
    public static String LOGIN_FXML_DIR = "/com/inventorymanagement/resources/fxml/login.fxml";
    public static String ISSUE_FXML_DIR = "/com/inventorymanagement/resources/fxml/issues.fxml";
    public static String HISTORY_FXML_DIR = "/com/inventorymanagement/resources/fxml/history.fxml";
    public static String CATEGORY_FXML_DIR = "/com/inventorymanagement/resources/fxml/categories.fxml";
    public static String AUTH_FXML_DIR = "/com/inventorymanagement/resources/fxml/auth.fxml";

    //css
    public static String STYLESHEET_DIR = "/com/inventorymanagement/resources/styles/styles.css";

    //config
    public static String DATABASE_CONFIG_DIR = "dbConfig.properties";
}
