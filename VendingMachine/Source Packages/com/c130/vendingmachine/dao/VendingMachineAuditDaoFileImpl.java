package com.c130.vendingmachine.dao;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class VendingMachineAuditDaoFileImpl implements VendingMachineAuditDao {

    private final String AUDIT_FILE;
    public VendingMachineAuditDaoFileImpl() {
        this.AUDIT_FILE = "audit.txt";
    }
    
    @Override
    public void writeAudit(String entryAudit) throws VendingMachinePersistenceException {
         PrintWriter writeToAudit;
         try {
        	 writeToAudit = new PrintWriter(new FileWriter(AUDIT_FILE, true));
         } catch (IOException e) {
             throw new VendingMachinePersistenceException("Could not persist audit information", e);
         }         LocalDateTime timestamp = LocalDateTime.now();
         writeToAudit.println(timestamp.toString() + " - " + entryAudit);
         writeToAudit.flush();
    }
}