package com.c130.vendingmachine.ui;

import java.math.BigDecimal;
import java.util.Map;

public class VendingMachineView {
    
    private UserIO io;
    
    public VendingMachineView (UserIO io){
        this.io = io;
    }

    public BigDecimal getMoney() {
        return io.readBigDecimal("Please insert money into the vending machine");
    }
    
    public void displayMenuBanner() {
        io.print("---------- Selection Menu ----------");
    }
    
    public void displayMenu(Map<String, BigDecimal> itemNamePriceQuantity){
    	itemNamePriceQuantity.entrySet().forEach(item ->{
        System.out.println(item.getKey() + ": €" +item.getValue());
        });
    }
    
    public String getItemSelection(){
        return io.readString("\nWhat would you like to buy?\nType exit to quit");
    }

    public void displayInsufficientFundsMessage(BigDecimal amountEntered){
        io.print("Insufficent funds, there is "+ amountEntered + "€ in the machine.");
    }
    
    public void displayOOSMessage(String name){
        io.print("Error, " + name + " is out of stock.");
    }   

    public void displayChangeAsCoins(Map<BigDecimal, BigDecimal> changeAsCoins) {
    	changeAsCoins.entrySet().forEach(entry ->{
                 System.out.println("Given in " + entry.getValue() + " 00." + entry.getKey() + " cent coins");
         });
    }
    
    public void displayErrorMessage (String errorMsg) {
        io.print(errorMsg);
    }
    
    public void displayPleaseTryAgainMessage() {
        io.print("Please select a valid item.");
    }
    
    public void displayChangeMessage() {
        io.print("Please take your change from the machine.");
    }
    
    public void displayExitMessage() {
        io.print("Thank you for your purchase!");
    }


}