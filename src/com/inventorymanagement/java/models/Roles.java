package com.inventorymanagement.java.models;

public class Roles {
    public static final String ROLE_ID = "id";
    public static final String ROLE_NAME = "name";

    private int id;

    private String role_name;

    public Roles(int id, String role_name){
        this.id = id;
        this.role_name = role_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }
}
