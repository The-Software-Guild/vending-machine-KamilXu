package com.c130.vendingmachine.dao;

import java.util.List;
import java.util.Map;
import com.c130.vendingmachine.dto.SingularItem;
import java.math.BigDecimal;

public interface VendingMachineDao {
  
    void removeItem(String itemName) throws VendingMachinePersistenceException;

    List<SingularItem> getAllItems() throws VendingMachinePersistenceException ;
    
    int getItemsFromFile(String itemName) throws VendingMachinePersistenceException;

    SingularItem getItem(String itemName) throws VendingMachinePersistenceException;

    Map<String,BigDecimal> getItemNamePriceQuantity() throws VendingMachinePersistenceException;

}