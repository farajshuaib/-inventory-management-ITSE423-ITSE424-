package com.inventorymanagement.java.dao;

import com.inventorymanagement.java.dao.components.*;

public class Main_DAO implements DAO {
    private static Main_DAO instance = new Main_DAO();

    private CategoriesDB categories;
    private ProductsDB products;
    private HistoryDB history;
    private UsersDB users;
    private RolesDB roles;

    private Main_DAO() {
        this.categories = new CategoriesDB();
        this.history = new HistoryDB();
        this.products = new ProductsDB();
        this.users = new UsersDB();
        this.roles = new RolesDB();
    }

    public static Main_DAO getInstance(){
        if(instance == null) {
            instance = new Main_DAO();
        }
        return instance;
    }

    @Override
    public CategoriesDB getCategories() {
        return this.categories;
    }
    @Override
    public ProductsDB getProducts() {
        return this.products;
    }
    @Override
    public HistoryDB getHistory() {
        return this.history;
    }
    @Override
    public UsersDB getUsers() {
        return this.users;
    }
    @Override
    public RolesDB getRoles() {
        return this.roles;
    }
}
