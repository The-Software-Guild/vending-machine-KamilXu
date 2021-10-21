package com.c130.vendingmachine.dao;

import com.c130.vendingmachine.dto.SingularItem;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class VendingMachineDaoFileImpl implements VendingMachineDao {
    private Map <String, SingularItem> singularItems = new HashMap<>();
    public static final String DELIMITER = "::";
    private final String VENDING_MACHINE_FILE;
    
    public VendingMachineDaoFileImpl() {
        VENDING_MACHINE_FILE = "VendingMachine.txt";
    }

    //JunitTesting
    public VendingMachineDaoFileImpl(String textFile) {
        VENDING_MACHINE_FILE = "VendingMachine.txt";
    }
    
    @Override
    public int getItemsFromFile(String file) throws VendingMachinePersistenceException {
        loadVM();
        return singularItems.get(file).getQuantity();
    }

    @Override
    public void removeItem(String file) throws VendingMachinePersistenceException {
        loadVM();
        int beforeDeficit = singularItems.get(file).getQuantity();
        singularItems.get(file).setQuantity(beforeDeficit-1);
        writeVM();
    }
    
    @Override
    public SingularItem getItem(String file) throws VendingMachinePersistenceException {
        loadVM();
        return singularItems.get(file);
    }

    @Override
    public Map<String,BigDecimal> getItemNamePriceQuantity() throws VendingMachinePersistenceException{
        loadVM();
        Map<String, BigDecimal> namePriceQuantity = singularItems.entrySet().stream().filter(map -> map.getValue().getQuantity() > 0).collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue().getPrice()));
        return namePriceQuantity;       
    }

    private String marshallFromFile (SingularItem singleItem) {
        String stringItem = singleItem.getName() + DELIMITER;
        stringItem += singleItem.getPrice() + DELIMITER;
        stringItem += singleItem.getQuantity();
        return stringItem;
    }
    
    private SingularItem unmarshallFromFile (String stringItem){
        String [] itemField = stringItem.split("::");
        String itemName = itemField[0];
        SingularItem itemFromFile = new SingularItem(itemName);
        BigDecimal price = new BigDecimal(itemField[1]);
        itemFromFile.setPrice(price);
        itemFromFile.setQuantity(Integer.parseInt(itemField[2]));
        return itemFromFile;
    }
    
    private void loadVM() throws VendingMachinePersistenceException {
        Scanner scanner;
        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(VENDING_MACHINE_FILE)));
        }catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException(
                    "Error, Data did Not Load.", e);
        }
        
        String loadLine;
        SingularItem loadItem;
        while (scanner.hasNextLine()){
        	loadLine = scanner.nextLine();
            loadItem = unmarshallFromFile(loadLine);
            singularItems.put(loadItem.getName(), loadItem);
        }
        
        scanner.close();
	}
    
    @Override
    public List<SingularItem> getAllItems() throws VendingMachinePersistenceException {
        loadVM();
        return new ArrayList<SingularItem>(singularItems.values());
    }
    
    private void writeVM() throws VendingMachinePersistenceException {
        PrintWriter write;
        try {
        	write = new PrintWriter(new FileWriter(VENDING_MACHINE_FILE));
        }catch(IOException e) {
            throw new VendingMachinePersistenceException("Error, Data did Not Save.", e);
        }
        String stringItem;
        List <SingularItem> itemList = this.getAllItems();
        for (SingularItem loadItem : itemList) {
        	stringItem = marshallFromFile(loadItem);
            write.println(stringItem);
            write.flush();
        }
        write.close();
	}
}