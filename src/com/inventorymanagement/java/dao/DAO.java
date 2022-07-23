package com.inventorymanagement.java.dao;

import com.inventorymanagement.java.dao.components.*;


public interface DAO {
    public CategoriesDB Categories();
    public ProductsDB products() ;

    public HistoryDB history() ;

    public UsersDB users() ;
    public RolesDB roles() ;

}
