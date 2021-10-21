package com.c130.vendingmachine.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.c130.vendingmachine.dao.VendingMachineDao;
import com.c130.vendingmachine.dao.VendingMachineDaoFileImpl;
import com.c130.vendingmachine.dao.VendingMachinePersistenceException;
import com.c130.vendingmachine.dto.SingularItem;

class VendingMachineServiceLayerImplTest {

	//Vending Machine data from new file not touching our original Vending Machine
	//Allows us to use Dao and ServiceLayer functions
    VendingMachineDao testDao = new VendingMachineDaoFileImpl("testVendingM.txt");
    VendingMachineServiceLayer testServiceLayer = new VendingMachineServiceLayerImpl(null, testDao);
	
    @Test
	void testCheckAmountEntered() {
		//Given
		SingularItem testWater = new SingularItem("Water"); 
		testWater.setPrice(new BigDecimal("1.00"));
		testWater.setQuantity(19);
		//When
		BigDecimal enoughMoney = new BigDecimal("1.01");
        BigDecimal notEnoughMoney = new BigDecimal("0.99");
		//Then
        //Not Enough
        try {
        	testServiceLayer.checkAmountEntered(testWater, enoughMoney);
        } catch (InsufficientFundsException e){
            fail("Amount entered is enough for purchase");
        }
        //Enough
        try {
        	testServiceLayer.checkAmountEntered(testWater, notEnoughMoney);
            fail("Error, Insufficient Funds Exception");
        } catch (InsufficientFundsException e){}
	}

	@Test
	void testCheckCoins() {
		Map<BigDecimal, BigDecimal> changeinCoins = new HashMap<>();
		changeinCoins.put(new BigDecimal("0.25"), new BigDecimal("2"));
		changeinCoins.put(new BigDecimal("0.10"), new BigDecimal("1"));
		changeinCoins.put(new BigDecimal("0.05"), new BigDecimal("1"));
		changeinCoins.put(new BigDecimal("0.01"), new BigDecimal("1"));
		//Then
        assertEquals(changeinCoins.size(), 4, "There should be 4 coins");

	}

	@Test
	void testItemInStock() throws InsufficientFundsException, VendingMachinePersistenceException, NoItemInventoryException{
		SingularItem testWater = new SingularItem("Snickers");
		testWater.setPrice(new BigDecimal("1.00"));
		testWater.setQuantity(0);
		
		BigDecimal money = new BigDecimal("2.00");

		SingularItem itemWanted = testServiceLayer.getSingularItem("Water", money);
		assertNotNull(itemWanted, "item change shouldnt be null");
	    assertEquals(money, 2.00,"Item Water is not in stock and was not purchased");
	}

	@Test
	void testRemoveItem() throws VendingMachinePersistenceException{
        try{
            testServiceLayer.removeItem("Water");
            fail("There is no Water left, thrown exception");
        } catch (NoItemInventoryException e) {  
        }
        
        try{
            testServiceLayer.removeItem("Mars");
        } catch (NoItemInventoryException e) {
            if (testDao.getItemsFromFile("Mars")>0){
                fail("Mars bars is in stock, no exception");
            }
        }
	}
}
