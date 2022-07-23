package com.inventorymanagement.java.dao_composite;

import com.inventorymanagement.java.dao_composite.components.*;


public interface DAO {
    public CategoriesDB Categories();
    public ProductsDB products() ;

    public HistoryDB history() ;

    public UsersDB users() ;
    public RolesDB roles() ;

}
