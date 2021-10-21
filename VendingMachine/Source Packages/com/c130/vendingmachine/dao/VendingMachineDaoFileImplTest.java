package com.c130.vendingmachine.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.c130.vendingmachine.dto.SingularItem;

class VendingMachineDaoFileImplTest {
	
	public VendingMachineDaoFileImplTest() {
    }

    //Vending Machine data from new file not touching our original Vending Machine
    VendingMachineDao testDao = new VendingMachineDaoFileImpl("testVendingM.txt");
    
	//===TESTS===//
    
	@Test //Add item, this test case should fail after an addition to the vending machine stock
	void testGetItemWithDetails() throws VendingMachinePersistenceException{
		//Given
		SingularItem testWater = new SingularItem("Water"); 
		testWater.setPrice(new BigDecimal("1.00"));
		testWater.setQuantity(19);
		//When
		SingularItem getWater = testDao.getItem("Water");	
		//Then
		assertEquals(getWater, testWater, "In this Test Case the Item tested should be Water, be Priced at 1.00, and there should be 19 in Stock.");
	}
	
	@Test //check whole Vending Machine item names with stock
	public void testGetItemNamePriceQuantity() throws VendingMachinePersistenceException {
    //Given
    Map<String,BigDecimal> items = testDao.getItemNamePriceQuantity();
    //When
    //Then
    assertEquals(items.size(),5,"There should be 5 items.");
    assertTrue(items.containsKey("Water") && items.containsKey("Mars") && items.containsKey("Snickers") && items.containsKey("Monster Energy") && items.containsKey("Taytos"));
	}
	
	@Test //Remove an item
	void testRemoveItem() throws VendingMachinePersistenceException{
		//Given
		int VMBeforeRemoval = testDao.getItemsFromFile("Water");
		//When
		testDao.removeItem("Water");
		int VMAfterRemoval = testDao.getItemsFromFile("Water");
		//Then
		assertTrue(VMBeforeRemoval>VMAfterRemoval,"There should be more items before removal");
        assertEquals(VMBeforeRemoval,VMAfterRemoval+1,"They should be the same if the removed item was added");
	}

}
