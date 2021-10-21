package com.c130.vendingmachine.service;

public class NoItemInventoryException extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoItemInventoryException(String message) {
        super(message);
    }
    
    public NoItemInventoryException(String message, Throwable cause) {
        super(message,cause);
    }
}