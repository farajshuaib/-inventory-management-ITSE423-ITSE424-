package com.inventorymanagement.java.interfaces;

import com.inventorymanagement.java.dao.components.CategoriesDB;
import com.inventorymanagement.java.dao.components.HistoryDB;
import com.inventorymanagement.java.dao.components.ProductsDB;
import com.inventorymanagement.java.dao.components.UsersDB;


public interface DAO {

    public CategoriesDB Categories();
    public ProductsDB products() ;

    public HistoryDB history() ;

    public UsersDB users() ;

}
