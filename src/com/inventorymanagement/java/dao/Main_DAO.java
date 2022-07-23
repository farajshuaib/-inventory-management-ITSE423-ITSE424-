package com.inventorymanagement.java.dao;

import com.inventorymanagement.java.dao.components.*;

public class Main_DAO implements DAO {
    private static Main_DAO instance = new Main_DAO();
    protected Main_DAO() {}

    public static Main_DAO getInstance(){
        if(instance == null) {
            instance = new Main_DAO();
        }
        return instance;
    }

    @Override
    public CategoriesDB Categories() {
        return new CategoriesDB();
    }
    @Override
    public ProductsDB products() {
        return new ProductsDB();
    }
    @Override
    public HistoryDB history() {
        return new HistoryDB();
    }
    @Override
    public UsersDB users() {
        return new UsersDB();
    }
    @Override
    public RolesDB roles() {
        return new RolesDB();
    }
}
