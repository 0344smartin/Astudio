package com.example.sinresacaapplication;

public class Ingredient {
    private final String name;
    private final String amount;

    public String getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public Ingredient(String name, String amount) {
        this.name = name;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return  name + ": " + amount ;
    }
}
