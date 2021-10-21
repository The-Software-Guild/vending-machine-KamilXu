package com.c130.vendingmachine.service;

import com.c130.vendingmachine.dao.VendingMachineAuditDao;
import com.c130.vendingmachine.dao.VendingMachineDao;
import com.c130.vendingmachine.dao.VendingMachinePersistenceException;
import com.c130.vendingmachine.dto.CalculateChange;
import com.c130.vendingmachine.dto.SingularItem;
import java.math.BigDecimal;
import java.util.Map;


public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer{
    private VendingMachineAuditDao auditDao;
    private VendingMachineDao dao;

    public VendingMachineServiceLayerImpl(VendingMachineAuditDao auditDao, VendingMachineDao dao) {
        this.auditDao = auditDao;
        this.dao = dao;
    }

    @Override
    public void checkAmountEntered(SingularItem singularItem, BigDecimal currentAmount) throws InsufficientFundsException {
        if (singularItem.getPrice().compareTo(currentAmount) == 1) {
            throw new InsufficientFundsException (
            "Error, " + currentAmount + " is not Enough.");  
        }
    }
    
    @Override
    public Map<BigDecimal, BigDecimal> getChangeAsCoin(SingularItem singularItem, BigDecimal currentAmount) {
        BigDecimal itemCost = singularItem.getPrice();
        Map<BigDecimal, BigDecimal> changeAsCoins = CalculateChange.changeAsCoins(itemCost, currentAmount);
        return changeAsCoins;
    }
    
    @Override
    public SingularItem getSingularItem(String itemName, BigDecimal currentAmount) throws InsufficientFundsException, NoItemInventoryException, VendingMachinePersistenceException {
        SingularItem wantedItem = dao.getItem(itemName);
        if (wantedItem == null) {
            throw new NoItemInventoryException (
                "Error, item " + itemName + " does not exist.");
        }
        checkAmountEntered(wantedItem,currentAmount);
        removeItem(itemName);
        return wantedItem;
    }
    
    
    @Override
    public void removeItem (String itemName) throws NoItemInventoryException, VendingMachinePersistenceException {
        if (dao.getItemsFromFile(itemName)>0) {
            dao.removeItem(itemName);
            auditDao.writeAudit(itemName + " was Successfully Removed");
        } else {
            throw new NoItemInventoryException (
            "Error, " + itemName + " is not in stock.");
        }
    }
    
    @Override
    public Map<String, BigDecimal>  getItems () throws VendingMachinePersistenceException{
         Map<String, BigDecimal> items = dao.getItemNamePriceQuantity();
         return items;
    }
}