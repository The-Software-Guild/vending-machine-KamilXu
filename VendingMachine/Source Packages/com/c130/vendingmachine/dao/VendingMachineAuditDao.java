package com.c130.vendingmachine.dao;

public interface VendingMachineAuditDao {
	public void writeAudit(String entry) throws VendingMachinePersistenceException;
}