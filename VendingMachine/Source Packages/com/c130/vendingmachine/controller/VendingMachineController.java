package com.c130.vendingmachine.controller;

import com.c130.vendingmachine.dao.VendingMachinePersistenceException;
import com.c130.vendingmachine.dto.SingularItem;
import com.c130.vendingmachine.service.InsufficientFundsException;
import com.c130.vendingmachine.service.NoItemInventoryException;
import com.c130.vendingmachine.service.VendingMachineServiceLayer;
import com.c130.vendingmachine.ui.VendingMachineView;
import java.math.BigDecimal;
import java.util.Map;

public class VendingMachineController {

    private VendingMachineView view;
    private VendingMachineServiceLayer service;

    public VendingMachineController(VendingMachineView view, VendingMachineServiceLayer service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        boolean keepGoing = true;
        String itemSelection = "";
        BigDecimal currentAmountinMachine;
        view.displayMenuBanner();
        try {
        	getSelection();
        } catch (VendingMachinePersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
        currentAmountinMachine = view.getMoney();
            while (keepGoing) {
            try {
                itemSelection = view.getItemSelection();
                if ((itemSelection.equals("exit")) || (itemSelection.equals("Exit"))) {
                    keepGoing = false;
                    break;
                }
                getItem(itemSelection, currentAmountinMachine);
                keepGoing = false;
                break;

            } catch (InsufficientFundsException | NoItemInventoryException | VendingMachinePersistenceException e) {
                view.displayErrorMessage(e.getMessage());
                view.displayPleaseTryAgainMessage();
            }
            }
            view.displayExitMessage();

    }
    
    private void getSelection() throws VendingMachinePersistenceException {
        Map<String, BigDecimal> items = service.getItems();
        view.displayMenu(items);
    }    
    
    private void getItem(String itemName, BigDecimal currentAmountinMachine) throws InsufficientFundsException, NoItemInventoryException, VendingMachinePersistenceException {
        SingularItem selectedItem = service.getSingularItem(itemName, currentAmountinMachine);
        Map<BigDecimal, BigDecimal> changeAsCoin = service.getChangeAsCoin(selectedItem, currentAmountinMachine);
        view.displayChangeAsCoins(changeAsCoin);
        view.displayChangeMessage();
    }
    

}
  