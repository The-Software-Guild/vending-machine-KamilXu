package com.c130.vendingmachine.service;

import com.c130.vendingmachine.dao.VendingMachinePersistenceException;
import com.c130.vendingmachine.dto.SingularItem;
import java.math.BigDecimal;
import java.util.Map;

public interface VendingMachineServiceLayer {

    void checkAmountEntered(SingularItem singularItem, BigDecimal currentAmount) throws InsufficientFundsException;

    Map<BigDecimal, BigDecimal> getChangeAsCoin(SingularItem singularItem, BigDecimal currentAmount);
    
    SingularItem getSingularItem(String itemName, BigDecimal currentAmount) throws InsufficientFundsException, NoItemInventoryException, VendingMachinePersistenceException;
   
    void removeItem(String itemName) throws NoItemInventoryException, VendingMachinePersistenceException;
    
    Map<String, BigDecimal>  getItems() throws VendingMachinePersistenceException;
}
