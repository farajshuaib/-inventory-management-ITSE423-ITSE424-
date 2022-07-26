package com.inventorymanagement.java.dao;

import com.inventorymanagement.java.dao.components.*;


public interface DAO {
    public CategoriesDB getCategories();
    public ProductsDB getProducts() ;

    public HistoryDB getHistory() ;

    public UsersDB getUsers() ;
    public RolesDB getRoles() ;

}
