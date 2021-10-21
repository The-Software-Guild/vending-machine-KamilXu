package com.c130.vendingmachine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CalculateChange {
    
    public static BigDecimal changeDue (BigDecimal itemCost, BigDecimal value) {
        BigDecimal changeDue = (value.subtract(itemCost)).multiply(new BigDecimal("100"));
        System.out.println("Change: €" + (changeDue.divide(new BigDecimal("100"),2,RoundingMode.HALF_UP).toString()));
        return changeDue;
    }
    
    public static Map<BigDecimal, BigDecimal> changeAsCoins (BigDecimal itemCost, BigDecimal money) {
        EnumCoins[] coinArray = EnumCoins.values();
        ArrayList <String> coinList = new ArrayList<>();
        for (EnumCoins enumCoins : coinArray) {
            coinList.add(enumCoins.toString());
        }

        ArrayList<BigDecimal> coins = new ArrayList<BigDecimal>(); 
        for (String coin:coinList) {
        	coins.add(new BigDecimal(coin));
        }
          
        BigDecimal changeDue = changeDue(itemCost, money);
        BigDecimal amountOfCoins;
        BigDecimal zero = new BigDecimal("0");
        Map <BigDecimal, BigDecimal> amountAsACoin = new HashMap<>();

        for (BigDecimal singularCoin : coins) {
            if (changeDue.compareTo(singularCoin) >= 0) {
                if (!changeDue.remainder(singularCoin).equals(zero)) {
                	amountOfCoins = changeDue.divide(singularCoin,0,RoundingMode.DOWN);
                	amountAsACoin.put(singularCoin, amountOfCoins);
                    changeDue = changeDue.remainder(singularCoin);
                    if (changeDue.compareTo(zero)<0) {  
                        break;
                    }
                } else if (changeDue.remainder(singularCoin).equals(zero)) {
                	amountOfCoins = changeDue.divide(singularCoin,0,RoundingMode.DOWN);
                	amountAsACoin.put(singularCoin, amountOfCoins);
                    if ((changeDue.compareTo(zero))<0) {
                        break;
                    }
                }
            } else {
                ;
            }
        }
        return amountAsACoin;
    }

}