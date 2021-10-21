package com.c130.vendingmachine.dto;

public enum EnumCoins {
	QUARTER(25), DIME(10), NICKEL(5), PENNY(1);
    private int coinValue;
    
    EnumCoins (int coinValue) {
        this.coinValue = coinValue;
    }
    
    public int getCoinValue() {
		return coinValue;
	}
	
	public int setCoinValue(int coinValue) {
		return this.coinValue = coinValue;
	}
     
    public String toString(){
        switch (this) {
            case QUARTER:
                return "25";
            case DIME:
                return "10";
            case NICKEL:
                return "5";
            case PENNY:
                return "1";
        }
        return null;
    }
}
